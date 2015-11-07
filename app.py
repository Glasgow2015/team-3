from flask import Flask, request, redirect, jsonify, render_template
import twilio.twiml
import rethinkdb as r
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

@app.route('/')
def hello():
    return 'python/flask'


@app.route("/twilio", methods=['GET','POST'])
def twilio_response():
    try:
        phone_number = request.values.get('From')
        message_body = request.values.get('Body')

        response = "YOLO: {}".format(message_body)

        resp = twilio.twiml.Response()
        resp.message(response)
        return str(resp)
    except Exception as e:
        resp = twilio.twiml.Response()
        resp.message("Sorry :(! An error occurred, please try again later.")
        return resp
