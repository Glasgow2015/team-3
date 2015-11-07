from flask import Flask, request, redirect, jsonify, render_template, g
import twilio.twiml
import rethinkdb as r
import json
import os
import protocol
from rethinkdb.errors import RqlRuntimeError

app = Flask(__name__)
DB = "dbase"

# db setup; only run once
def dbSetup():
    connection = r.connect(host="localhost", port=28015)
    try:
        r.db_create(DB).run(connection)
        r.db(DB).table_create('inspections').run(connection)
        r.db(DB).table_create('harvests').run(connection)
        r.db(DB).table_create('hives').run(connection)
        r.db(DB).table_create('apiaries').run(connection)
        print 'Database setup completed'
    except RqlRuntimeError:
        print 'Database already exists.'
    finally:
        connection.close()
dbSetup()

@app.before_request
def before_request():
    try:
        g.rdb_conn = r.connect(host="localhost", port=28015, db=DB)
    except RqlDriverError:
        abort(503, "Database connection could be established.")

@app.teardown_request
def teardown_request(exception):
    try:
        g.rdb_conn.close()
    except AttributeError:
        pass

@app.route('/', methods=["GET"])
def hello():
    return render_template("index.html") 

@app.route('/new_apiary', methods=["GET", "POST"])
def new_apiary():
    if request.method == "GET":
        return render_template("new_apiary.html") 
    else:
        return render_template("new_apiary.html", alert="Hive Created!")

@app.route("/twilio", methods=['GET','POST'])
def twilio_response():
    try:
        phone_number = request.values.get('From')
        endpoint, message_body = request.values.get('Body').split('|', 1)
        resp = twilio.twiml.Response()

        if endpoint.strip() == "add_inspection":
            try:
                insp_dict = protocol.inspection_from_twilio(message_body)
                r.table('inspections').insert(insp_dict).run(g.rdb_conn)
                resp.message("OK")
            except Exception as ex:
                resp.message("FAIL Bad format: {}".format(str(ex)))
        elif endpoint.strip() == "add_harvest":
            try:
                harvest_dict = protocol.harvest_from_twilio(message_body)
                r.table('harvests').insert(harvest_dict).run(g.rdb_conn)
                resp.message("OK")
            except Exception as ex:
                resp.message("FAIL Bad format: {}".format(str(ex)))
        else:
            resp.message("FAIL No such operation")

        return str(resp)
    except Exception as e:
        resp = twilio.twiml.Response()
        resp.message("FAIL Unknown Error lol {}".format(str(e)))
        return str(resp)
