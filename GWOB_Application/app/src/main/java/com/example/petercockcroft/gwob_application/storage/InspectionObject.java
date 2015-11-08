package com.example.petercockcroft.gwob_application.storage;

import java.util.Calendar;

/**
 * Created by robin on 07/11/15.
 */
public class InspectionObject {
    // Codes from server side.
    String[] state_of_hive_codes = new String[]{"notinuse","notoccupied","occupied","absconded","robbed","honeybadgered","mites","beetle","ants","fire","flood", "justdead"};

    String[] colony_strength_codes = new String[] {"strong","moderate","weak","critical"};
    String[] temper_of_hives_codes = new String[] {"calm","nervous","angry"};
    String[] comb_conditions_codes = new String[] {"high","average","low"};
    String[] pest_codes = new String[] {"light","moderate","heavy"};
    String[] hive_or_clothing_conditions_code = new String[] {"good","fair","poor","damaged"};


    int hive_number;
    Calendar date;
    int weather_condition;
    int state_of_hive;
    int strength_of_colony;
    int temper;
    int queen_in_da_hood;
    int cond_honey_store;
    int cond_pollen_store;
    int small_hive_beetle;
    int varrao_mite;
    int safari_ants;
    int chalk_brood;
    int hive_condition;
    int equipment_condition;

    public InspectionObject(int hive_number, Calendar date, int weather_condition, int state_of_hive, int strength_of_colony, int temper, int queen_in_da_hood, int cond_honey_store, int cond_pollen_store, int small_hive_beetle, int varrao_mite, int safari_ants, int chalk_brood, int hove_condition, int equipment_condition) {
        this.hive_number = hive_number;
        this.date = date;
        this.weather_condition = weather_condition;
        this.state_of_hive = state_of_hive;
        this.strength_of_colony = strength_of_colony;
        this.temper = temper;
        this.queen_in_da_hood = queen_in_da_hood;
        this.cond_honey_store = cond_honey_store;
        this.cond_pollen_store = cond_pollen_store;
        this.small_hive_beetle = small_hive_beetle;
        this.varrao_mite = varrao_mite;
        this.safari_ants = safari_ants;
        this.chalk_brood = chalk_brood;
        this.hive_condition = hove_condition;
        this.equipment_condition = equipment_condition;
    }

    @Override
    public String toString() {
        String output = "" +
                hive_number + "|" +
                date.getTimeInMillis() / 1000 + "|" +
                getWeatherCondition(weather_condition) + "|" +
                getStateOfHive(state_of_hive) + "|" +
                getStrengthOfColony(strength_of_colony) + "|" +
                getTemperOfHive(temper) + "|" +
                getBoolean(queen_in_da_hood) + "|" +
                getCombCondition(cond_honey_store) + "|" +
                getCombCondition(cond_pollen_store) + "|" +
                getMagnitude(small_hive_beetle) + "|" +
                getMagnitude(varrao_mite) + "|" +
                getBoolean(safari_ants) + "|" +
                getBoolean(chalk_brood) + "|" +
                getCondition(hive_condition) + "|" +
                getCondition(equipment_condition);

        return output;
    }

    private String getCondition(int condition) {
        if (condition < hive_or_clothing_conditions_code.length) {
            return hive_or_clothing_conditions_code[condition];
        }

        System.err.println("getCombCondition error: " + condition);
        return "";
    }

    private String getMagnitude(int mag) {
        if (mag < pest_codes.length) {
            return pest_codes[mag];
        }

        System.err.println("getCombCondition error: " + mag);
        return "";
    }

    private String getCombCondition(int comb_cond) {
        if (comb_cond < comb_conditions_codes.length) {
            return comb_conditions_codes[comb_cond];
        }

        System.err.println("getCombCondition error: " + comb_cond);
        return "";
    }

    private String getBoolean(int bool) {
        if (bool == 0) return "YES";
        else return "NO";
    }

    private String getTemperOfHive(int temper) {
        if (temper < temper_of_hives_codes.length) {
            return temper_of_hives_codes[temper];
        }

        System.err.println("getTemperOfHive error: " + temper);
        return "";
    }

    private String getStrengthOfColony(int strength_of_colony) {
        if (strength_of_colony < colony_strength_codes.length) {
            return colony_strength_codes[strength_of_colony];
        }

        System.err.println("getStrengthOfColony error: " + strength_of_colony);
        return "";
    }

    private String getStateOfHive(int state_of_hive) {

        if (state_of_hive < state_of_hive_codes.length) {
            return state_of_hive_codes[state_of_hive];
        }

        System.err.println("getStateOfHive error: " + state_of_hive);
        return "";
    }

    private String getWeatherCondition(int weather_condition) {

        switch (weather_condition) {
            case 0: return "sunny";
            case 1: return "partlycloudy";
            case 2: return "cloudy";
            case 3: return "rain";
            case 4: return "windy";
        }
        return "";
    }
}
