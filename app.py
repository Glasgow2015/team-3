from flask import Flask, request, redirect, jsonify, render_template, g, url_for
from wtforms import Form, TextField, FloatField, SelectMultipleField, \
                    FileField, RadioField, BooleanField, IntegerField, validators
from wtforms.ext.dateutil.fields import DateTimeField
import twilio.twiml
import rethinkdb as r
import json
import os
import protocol
import time
from rethinkdb.errors import RqlRuntimeError

DB_URI = os.getenv('RETHINKDB_URL', "rethinkdb://localhost:28015")
DB_HOST, DB_PORT = DB_URI.split('//')[1].split(':')

# Functions
def pretty_date(time=False):
    """
    Get a datetime object or a int() Epoch timestamp and return a
    pretty string like 'an hour ago', 'Yesterday', '3 months ago',
    'just now', etc
    """
    from datetime import datetime
    now = datetime.now()
    if type(time) is int:
        diff = now - datetime.fromtimestamp(time)
    elif isinstance(time,datetime):
        diff = now - time
    elif not time:
        diff = now - now
    second_diff = diff.seconds
    day_diff = diff.days

    if day_diff < 0:
        return ''

    if day_diff == 0:
        if second_diff < 10:
            return "just now"
        if second_diff < 60:
            return str(second_diff) + " seconds ago"
        if second_diff < 120:
            return "a minute ago"
        if second_diff < 3600:
            return str(second_diff / 60) + " minutes ago"
        if second_diff < 7200:
            return "an hour ago"
        if second_diff < 86400:
            return str(second_diff / 3600) + " hours ago"
    if day_diff == 1:
        return "Yesterday"
    if day_diff < 7:
        return str(day_diff) + " days ago"
    if day_diff < 31:
        return str(day_diff / 7) + " weeks ago"
    if day_diff < 365:
        return str(day_diff / 30) + " months ago"
    return str(day_diff / 365) + " years ago"


# Forms
class NewApiaryForm(Form):
    name = TextField('Name', [validators.Required()])
    lat = FloatField('Latitude', [validators.Required()])
    lon = FloatField('Longitude', [validators.Required()])
    environment = SelectMultipleField('Environment (within 3 kilometers)', choices=[ \
        ('water','Water Supply'), \
        ('miombo_woodlands','Miombo Woodlands'), \
        ('closed_forests','Closed Forests'), \
        ('grassland','Grassland'), \
        ('forest_plantation','Forest Plantation'), \
        ('sisal_plantation','Sisal Plantation'), \
        ('orchard','Mixed Crops'), \
        ('pesticides','Farmers with Pesticides'), \
    ])
    accessibility = SelectMultipleField('Accessibility of Apiary', choices=[ \
        ('vehicle','By Vehicle'), \
        ('cycle','Bycicle or Motorcycle'), \
        ('foot','By Foot'), \
    ])
    apiaryType = SelectMultipleField('Type', choices=[ \
        ('natural_nest','Natural Nest'), \
        ('tree','Tree'), \
        ('breast_height','Breast Height'), \
        ('bee_house','Bee Height'), \
        ('honey_badger_stand','Honey Badger Stand'), \
    ])

class NewHiveForm(Form):
    number = TextField('Number', [validators.Required()])
    lat = FloatField('Latitude', [validators.Required()])
    lon = FloatField('Longitude', [validators.Required()])
    image = FileField('Image', [])
    hive_type = RadioField('Hive Type', choices=[ \
        ('traditional_hive','Traditional Hive'), \
        ('top_bar_hive','Top Bar Hive'), \
        ('top_bar_hive_with_queen_excluder','Top Bar Hive With Queen Excluder'), \
        ('langstroth_hive','Langstroth Hive'), \
        ('other','Other') \
    ])
    sun_exposure = RadioField('Sun Exposure', choices=[ \
        ('shady','Shady'), \
        ('partial_shade','Partial Shade'), \
        ('sunny','Sunny') \
    ])

class NewInspectionForm(Form):
    weather_conditions = RadioField('Weather Conditions', choices=[ \
        ('sunny','Sunny'), \
        ('partly_cloudy','Partly Cloudy'), \
        ('cloudy','Cloudy'), \
        ('rain','Rain'), \
        ('windy','Windy') 
    ])
    state_of_hive = RadioField('State of Hive', choices=[ \
        ('not_in_use','Not in use/not installed'), \
        ('not_yet_occupied','Not yet occupied'), \
        ('occupied','Occupied'), \
        ('absconed','Absconed'), \
        ('robbed','Dead, because of robbing'), \
        ('honey_badger','Dead, because of honey badger'), \
        ('mites','Dead, because of mites'), \
        ('beetle','Dead, because of beetle'), \
        ('ants','Dead, because of ants or other insects'), \
        ('fire','Dead, because of fire'), \
        ('flood','Dead, because of flood'), \
        ('unknown','Dead, because of unknown')
    ])
    strength_of_colony = RadioField('Strength of the Colony', choices=[ \
        ('strong','Strong'), \
        ('moderate','Moderate'), \
        ('weak','Weak'), \
        ('critical','Critical')
    ])    
    temper_of_hive = RadioField('Temper of the Hive', choices=[ \
        ('calm','Calm'), \
        ('nervous','Nervous'), \
        ('angry','Angry')
    ])
    queen_cells = BooleanField('Queen cells in brood cells', [])
    conditions_of_honey_stores = RadioField('Conditions of Honey Stores', choices=[ \
        ('high','High'), \
        ('average','Average'), \
        ('low','Low') \
    ])
    conditions_of_pollen_stores = RadioField('Conditions of Pollen Stores', choices=[ \
        ('high','High'), \
        ('average','Average'), \
        ('low','Low')
    ])
    small_hive_beetle = RadioField('Pests: Small Hive Beetle', choices=[ \
        ('light','Light'), \
        ('moderate','Moderate'), \
        ('heavy','Heavy')
    ])
    varrao_mites = RadioField('Pests: Varrao Mites', choices=[ \
        ('light','Light'), \
        ('moderate','Moderate'), \
        ('heavy','Heavy')
    ])
    safari_ants = BooleanField('Pests: Safari Ants', [])
    chalk_brood = BooleanField('Diseases: Chalk Brood', [])
    hive_condition = RadioField('Hive Condition', choices=[ \
        ('good','Good'), \
        ('fair','Fair'), \
        ('poor','Poor'), \
        ('damaged','Damaged')
    ])
    protective_clothing_condition = RadioField('Protective Clothing and Beekeeping Tools Condition', choices=[ \
        ('good','Good'), \
        ('fair','Fair'), \
        ('poor','Poor'), \
        ('damaged','Damaged')
    ])

class NewHarvestForm(Form):
    quantity = IntegerField('Number of Ripe Combs Harvested', [validators.Required()])
    beekeeper_clothing = BooleanField('Protective Clothing Available for Beekeeper', [])
    assistant_clothing = BooleanField('Protective Clothing Available all Assistants', [])
    smoker_available = BooleanField('Smoker Available', [])
    clean_airtight_buckets_available_number = IntegerField('Number of Clean, Airtight Buckets Available for Harvest', [validators.Required()])

app = Flask(__name__)
DB = "dbase"

# db setup; only run once
def dbSetup():
    connection = r.connect(DB_HOST, DB_PORT)
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
        g.rdb_conn = r.connect(DB_HOST, DB_PORT, DB)
    except RqlDriverError:
        abort(503, "Database connection could be established.")

@app.teardown_request
def teardown_request(exception):
    try:
        g.rdb_conn.close()
    except AttributeError:
        pass

@app.route('/new_apiary', methods=["GET", "POST"])
def new_apiary():
    form = NewApiaryForm(request.form)
    if request.method == "POST" and form.validate():
        r.table('apiaries').insert(form.data).run(g.rdb_conn)
        return redirect("/list_apiaries", code=302) 
    return render_template("new_apiary.html", form=form)

@app.route('/')
@app.route('/list_apiaries')
def list_apiaries():
    apiaries = r.table('apiaries').run(g.rdb_conn)
    return render_template("list_apiaries.html", data=apiaries)

@app.route('/delete_apiary/<string:id>')
def delete_apiary(id):
    r.table('apiaries').get(id).delete().run(g.rdb_conn)
    return redirect('/', code=302)

@app.route('/delete_hive/<string:parentid>/<string:id>')
def delete_hive(parentid, id):
    r.table('hives').get(id).delete().run(g.rdb_conn)
    return redirect('/list_hives/{}'.format(parentid), code=302)

@app.route('/list_hives/<string:apiaryid>')
def list_hives(apiaryid):
    apiary = r.table('apiaries').get(apiaryid).run(g.rdb_conn)
    hives = r.table('hives').filter({"apiary_id":apiaryid}).run(g.rdb_conn)
    return render_template("list_hives.html", parent=apiary, data=hives, data2=hives)

@app.route('/list_harvests/<string:apiaryid>')
def list_harvests(apiaryid):
    apiary = r.table('apiaries').get(apiaryid).run(g.rdb_conn)
    hives = r.table('harvests').filter({"apiary_id":apiaryid}).run(g.rdb_conn)
    return render_template("list_harvests.html", parent=apiary, data=hives)

@app.route('/list_inspections/<string:hivenum>')
def list_inspections(hivenum):
    inspections = r.table('inspections').filter({"hive":hivenum}).run(g.rdb_conn)
    new_inspections = []
    for inspection in inspections:
        pretty = pretty_date(int(inspection["date_of_inspection"]))
        inspection["pretty_date"] = pretty
        new_inspections.append(inspection)
    return render_template("list_inspections.html", data=new_inspections, hivenum=hivenum)

@app.route('/new_hive/<string:apiaryid>', methods=["GET", "POST"])
def new_hive(apiaryid):
    form = NewHiveForm(request.form)
    if request.method == "POST" and form.validate():
        data = form.data
        data['apiary_id'] = apiaryid
        data['date_of_installation'] = time.time()
        r.table('hives').insert(data).run(g.rdb_conn)
        return redirect("/list_hives/{}".format(apiaryid), code=302) 
    return render_template("new_hive.html", form=form)

@app.route('/new_harvest/<string:apiaryid>', methods=["GET", "POST"])
def new_harvest(apiaryid):
    form = NewHarvestForm(request.form)
    if request.method == "POST" and form.validate():
        data = form.data
        data['apiary_id'] = apiaryid
        data['harvest_date'] = time.time()
        r.table('harvests').insert(data).run(g.rdb_conn)
        return redirect("/", code=302) 
    return render_template("new_harvest.html", form=form)

@app.route('/new_inspection/<string:hiveid>', methods=["GET", "POST"])
def new_inspection(hiveid):
    form = NewInspectionForm(request.form)
    if request.method == "POST" and form.validate():
        data = form.data
        data['hive'] = hiveid
        data['date_of_inspection'] = time.time()
        r.table('inspections').insert(data).run(g.rdb_conn)
        return redirect("/", code=302) 
    return render_template("new_inspection.html", form=form)

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
                apiary = list(r.table('apiaries').limit(1).run(g.rdb_conn))[0][u'id']
                harvest_dict['apiary_id'] = apiary
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
