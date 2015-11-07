from flask import Flask, request, redirect, jsonify, render_template
import os
import twilio.twiml
import rethinkdb as r

app = Flask(__name__)

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
