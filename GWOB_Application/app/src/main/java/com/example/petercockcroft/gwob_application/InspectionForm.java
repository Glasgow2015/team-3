package com.example.petercockcroft.gwob_application;

/**
 * Created by petercockcroft on 07/11/15.
 */

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Toast;

import com.example.petercockcroft.gwob_application.storage.InspectionObject;
import com.example.petercockcroft.gwob_application.storage.StorageManager;
import com.heinrichreimersoftware.singleinputform.SingleInputFormActivity;
import com.heinrichreimersoftware.singleinputform.steps.CheckBoxStep;
import com.heinrichreimersoftware.singleinputform.steps.DateStep;
import com.heinrichreimersoftware.singleinputform.steps.OptionStep;
import com.heinrichreimersoftware.singleinputform.steps.SeekBarStep;
import com.heinrichreimersoftware.singleinputform.steps.Step;
import com.heinrichreimersoftware.singleinputform.steps.TextStep;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class InspectionForm extends SingleInputFormActivity {

    private static final String DATA_KEY_HEIGHT = "height";
    private static final String DATA_KEY_EULA = "eula";
    private static final String DATA_KEY_EMAIL = "email";
    private static final String DATA_KEY_PASSWORD = "password";
    private static final String DATA_KEY_BIRTHDAY = "birthday";
    private static final String DATA_KEY_CITY = "city";

    private static final String DATA_KEY_HIVE_NUMBER = "hive_no";
    private static final String DATA_KEY_DATE_INSPECTION = "inspection date";
    private static final String DATA_KEY_WEATHER_CONDITIONS = "weather conditions";
    private static final String DATA_KEY_STATE_OF_HIVE = "state of hive";
    private static final String DATA_KEY_STRENGTH = "strength of hive";
    private static final String DATA_KEY_TEMPER = "temper of hive";
    private static final String DATA_KEY_QUEEN_CELLS = "queen cells";
    private static final String DATA_KEY_HONEY_STORES = "honey stores";
    private static final String DATA_KEY_POLLEN_STORES = "pollen stores";
    private static final String DATA_KEY_HIVE_BEETLE = "hive beetle";
    private static final String DATA_KEY_VARRAO_MITES = "varrao mites";
    private static final String DATA_KEY_SAFARI_ANTS = "safari ants";
    private static final String DATA_KEY_CHALK_BROOD = "chalk brood";
    private static final String DATA_KEY_HIVE_CONDITION = "hive condition";
    private static final String DATA_KEY_CLOTHING_CONDITION = "clothing tools condition";

    @Override
    protected List<Step> getSteps(Context context) {
        List<Step> steps = new ArrayList<Step>();
        steps.add(
                new TextStep(context, DATA_KEY_HIVE_NUMBER,
                        InputType.TYPE_CLASS_NUMBER,
                        R.string.hive_no,
                        R.string.hive_error,
                        R.string.hive_details,
                        new TextStep.StepChecker() {
                            @Override
                            public boolean check(String input) {
                                int result;
                                try {
                                    result = Integer.parseInt(input);
                                } catch (NumberFormatException e) {
                                    return false;
                                } catch (NullPointerException e) {
                                    return false;
                                }
                                // Check if positive.
                                if (result < 0) return false;
                                else return true;
                            }
                        })
        );

        steps.add(
                new DateStep(context, DATA_KEY_DATE_INSPECTION,
                        R.string.inspection_date,
                        R.string.inspection_date_error_future,
                        R.string.inspection_date_details,
                        new DateStep.StepChecker() {
                            @Override
                            public boolean check(int year, int month, int day) {
                                Calendar today = new GregorianCalendar();
                                Calendar harvest_date = new GregorianCalendar(year, month, day);
                                return today.after(harvest_date);
                            }
                        })
        );

        steps.add(
                new OptionStep(context, DATA_KEY_WEATHER_CONDITIONS,
                        new String[]{
                                "Sunny",
                                "Partly Cloudy",
                                "Cloudy",
                                "Rain",
                                "Windy"
                        },
                        R.string.weather_conditions,
                        R.string.weather_conditions_error,
                        R.string.weather_conditions_details
                )
        );

        steps.add(
                new OptionStep(context, DATA_KEY_STATE_OF_HIVE,
                        new String[]{
                                "Not in use",
                                "Not yet occupied",
                                "Occupied",
                                "Absconded",
                                "Dead, Robbing",
                                "Dead, Honey Badger",
                                "Dead, Mites",
                                "Dead, Beetle",
                                "Dead, Insects",
                                "Dead, Fire",
                                "Dead, Flood",
                                "Dead, Unknown"
                        },
                        R.string.state_of_hive,
                        R.string.state_of_hive_error,
                        R.string.state_of_hive_details
                )
        );

        steps.add(
                new OptionStep(context, DATA_KEY_STRENGTH,
                        new String[]{
                                "Strong",
                                "Moderate",
                                "Weak",
                                "Critical"
                        },
                        R.string.strength_of_hive,
                        R.string.strength_of_hive_error,
                        R.string.strength_of_hive_details
                )
        );

        steps.add(
                new OptionStep(context, DATA_KEY_TEMPER,
                        new String[]{
                                "Calm",
                                "Nervous",
                                "Angry"
                        },
                        R.string.temper_of_hive,
                        R.string.temper_of_hive_error,
                        R.string.temper_of_hive_details
                )
        );

        steps.add(
                new OptionStep(context, DATA_KEY_QUEEN_CELLS,
                        new String[]{
                                getResources().getString(R.string.boolean_positive),
                                getResources().getString(R.string.boolean_negative)
                        },
                        R.string.queen_cells,
                        R.string.queen_cells_error,
                        R.string.queen_cells_hive_details
                )
        );

        steps.add(
                new OptionStep(context, DATA_KEY_HONEY_STORES,
                        new String[]{
                                "High",
                                "Average",
                                "Low"
                        },
                        R.string.honey_stores,
                        R.string.honey_stores_error,
                        R.string.honey_stores_details
                )
        );

        steps.add(
                new OptionStep(context, DATA_KEY_POLLEN_STORES,
                        new String[]{
                                "High",
                                "Average",
                                "Low"
                        },
                        R.string.pollen_stores,
                        R.string.pollen_stores_error,
                        R.string.pollen_stores_details
                )
        );

        steps.add(
                new OptionStep(context, DATA_KEY_HIVE_BEETLE,
                        new String[]{
                                "Light",
                                "Moderate",
                                "Heavy"
                        },
                        R.string.hive_beetle,
                        R.string.hive_beetle_error,
                        R.string.hive_beetle_description
                )
        );

        steps.add(
                new OptionStep(context, DATA_KEY_VARRAO_MITES,
                        new String[]{
                                "Light",
                                "Moderate",
                                "Heavy"
                        },
                        R.string.varrao_mites,
                        R.string.varrao_mites_error,
                        R.string.varrao_mites_description
                )
        );

        steps.add(
                new OptionStep(context, DATA_KEY_SAFARI_ANTS,
                        new String[]{
                                getResources().getString(R.string.boolean_positive),
                                getResources().getString(R.string.boolean_negative)
                        },
                        R.string.safari_ants,
                        R.string.safari_ants_error,
                        R.string.safari_ants_description
                )
        );

        steps.add(
                new OptionStep(context, DATA_KEY_CHALK_BROOD,
                        new String[]{
                                getResources().getString(R.string.boolean_positive),
                                getResources().getString(R.string.boolean_negative)
                        },
                        R.string.chalk_brood,
                        R.string.chalk_brood_error,
                        R.string.chalk_brood_description
                )
        );

        steps.add(
                new OptionStep(context, DATA_KEY_HIVE_CONDITION,
                        new String[]{
                                "Good",
                                "Fair",
                                "Poor",
                                "Damaged"
                        },
                        R.string.hive_condition,
                        R.string.hive_condition_error,
                        R.string.hive_condition_description
                )
        );


        steps.add(
                new OptionStep(context, DATA_KEY_CLOTHING_CONDITION,
                        new String[]{
                                "Good",
                                "Fair",
                                "Poor",
                                "Damaged"
                        },
                        R.string.clothing_tools_condition,
                        R.string.clothing_tools_condition_error,
                        R.string.clothing_tools_condition_description
                )
        );

        return steps;
    }

    @Override
    protected void onFormFinished(Bundle data) {
        int hive_number = Integer.parseInt(TextStep.text(data, DATA_KEY_HIVE_NUMBER));
        Calendar inspection_date = new GregorianCalendar(
                DateStep.year(data, DATA_KEY_DATE_INSPECTION),
                DateStep.month(data, DATA_KEY_DATE_INSPECTION),
                DateStep.day(data, DATA_KEY_DATE_INSPECTION));

        int weather_condition = OptionStep.selectedOption(data, DATA_KEY_WEATHER_CONDITIONS),
                state_of_hive = OptionStep.selectedOption(data, DATA_KEY_STATE_OF_HIVE),
                hive_strength = OptionStep.selectedOption(data, DATA_KEY_STRENGTH),
                hive_temper = OptionStep.selectedOption(data, DATA_KEY_TEMPER),
                queen_celles = OptionStep.selectedOption(data, DATA_KEY_QUEEN_CELLS),
                honey_stores = OptionStep.selectedOption(data, DATA_KEY_HONEY_STORES),
                pollen_stores = OptionStep.selectedOption(data, DATA_KEY_POLLEN_STORES),
                hive_beetle = OptionStep.selectedOption(data, DATA_KEY_HIVE_BEETLE),
                varrao_mites = OptionStep.selectedOption(data, DATA_KEY_VARRAO_MITES),
                safari_ants = OptionStep.selectedOption(data, DATA_KEY_SAFARI_ANTS),
                chalk_brood = OptionStep.selectedOption(data, DATA_KEY_CHALK_BROOD),
                hive_cond = OptionStep.selectedOption(data, DATA_KEY_HIVE_CONDITION),
                cloth_cond = OptionStep.selectedOption(data, DATA_KEY_CLOTHING_CONDITION);

        InspectionObject inspection = new InspectionObject(
                hive_number, inspection_date, weather_condition,
                state_of_hive, hive_strength, hive_temper,
                queen_celles, honey_stores, pollen_stores, hive_beetle, varrao_mites,
                safari_ants, chalk_brood, hive_cond, cloth_cond
        );

        // Add inspection to storage.
        int retc = StorageManager.addRecordToStorage(inspection);
        System.out.println(retc);
    }
}