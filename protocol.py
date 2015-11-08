import time
import datetime

def harvest_from_twilio(input):
    elements = input.split('|')
    return {
        "harvest_date": elements[0].strip(),
        "quantity": int(elements[1].strip()),
        "beekeeper_clothing": elements[2].strip().lower() == "yes",
        "assistant_clothing": elements[3].strip().lower() == "yes",
        "smoker_available": elements[4].strip().lower() == "yes",
        "clean_airtight_buckets_available_number": int(elements[5].strip())
    }

weather_conditions = ["sunny","partly_cloudy","cloudy","rain","windy"]
hive_states = ["notinuse","notoccupied","occupied","absconded","robbed","honeybadgered","mites","beetle","ants","fire","flood","unknown"]
colony_strength = ["strong","moderate","weak","critical"]
temper_of_hives = ["calm","nervous","angry"]
comb_conditions = ["high","average","low"]
pests = ["light","moderate","heavy"]
hive_or_clothing_conditions = ["good","fair","poor","damaged"]

def inspection_from_twilio(input):
    elements = input.split('|')

    if elements[2].strip().lower() not in weather_conditions:
        raise Exception("weather_condition ({}) not valid".format(elements[2].strip().lower()))

    if elements[3].strip().lower() not in hive_states:
        raise Exception("hive_state ({}) not valid".format(elements[3].strip().lower()))

    if elements[5].strip().lower() not in temper_of_hives:
        raise Exception("hive_temper ({}) not valid".format(elements[5].strip().lower()))

    if elements[7].strip().lower() not in comb_conditions:
        raise Exception("honey_store_condition ({}) not valid".format(elements[7].strip().lower()))

    if elements[8].strip().lower() not in comb_conditions:
        raise Exception("pollen_store_condition ({}) not valid".format(elements[8].strip().lower()))

    if elements[9].strip().lower() not in pests:
        raise Exception("small_hive_beetle ({}) not valid".format(elements[9].strip().lower()))

    if elements[10].strip().lower() not in pests:
        raise Exception("varrao_mites ({}) not valid".format(elements[10].strip().lower()))

    if elements[13].strip().lower() not in hive_or_clothing_conditions:
        raise Exception("hive_condition ({}) not valid".format(elements[13].strip().lower()))

    if elements[14].strip().lower() not in hive_or_clothing_conditions:
        raise Exception("clothing_tools_condition ({}) not valid".format(elements[14].strip().lower()))

    return {
        "hive_number": int(elements[0].strip()),
        "date_of_inspection": float(elements[1].strip()),
        "weather_conditions": elements[2].strip().lower(),
        "state_of_hive": elements[3].strip().lower(),
        "strength_of_colony": elements[4].strip().lower(),
        "temper_of_hive": elements[5].strip().lower(),
        "queen_cells": elements[6].strip().lower() == "yes",
        "conditions_of_honey_stores": elements[7].strip().lower(),
        "conditions_of_pollen_stores": elements[8].strip().lower(),
        "small_hive_beetle": elements[9].strip().lower(),
        "varrao_mites": elements[10].strip().lower(),
        "safari_ants": elements[11].strip().lower() == "yes",
        "chalk_brood": elements[12].strip().lower() == "yes",
        "hive_condition": elements[13].strip().lower(),
        "protective_clothing_condition": elements[14].strip().lower()
    }
def harvest_to_twilio (input):
    return "|".join("{}" * 6).format(
        input["harvest_date"], \
        input["quantity"], \
        "YES" if input["beekeeper_clothing"] else "NO", \
        "YES" if input["assistant_clothing"] else "NO", \
        "YES" if input["smoker_available"] else "NO", \
        input["clean_airtight_buckets_available_number"]
        )

def inspection_to_twilio (input):
    return "|".join("{}" * 15).format(
        input["hive"], \
        input["inspection_date"], \
        input["weather_condition"], \
        input["hive_state"], \
        input["colony_strength"], \
        input["hive_temper"], \
        "YES" if input["queen"] else "NO", \
        input["honey_store_condition"], \
        input["pollen_store_condition"], \
        input["small_hive_beetle"], \
        input["varrao_mites"], \
        "YES" if input["safari_ants"] else "NO", \
        "YES" if input["chalk_brood"] else "NO", \
        input["hive_condition"], \
        input["clothing_tools_condition"]
    )

# print inspection_to_twilio(inspection_from_twilio("2 | 10/10/10 | windy| occupied | strong | calm | yes | high | high | light | light | no | no |good |good"))

# print harvest_to_twilio(harvest_from_twilio("10/10/15|2|yes|no|yes|8"))


