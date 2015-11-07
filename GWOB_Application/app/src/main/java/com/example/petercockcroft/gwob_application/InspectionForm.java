package com.example.petercockcroft.gwob_application;

/**
 * Created by petercockcroft on 07/11/15.
 */
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Toast;

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
    private static final String DATA_KEY_HIVE_BEETLE= "hive beetle";
    private static final String DATA_KEY_VARRAO_MITES = "varrao mites";
    private static final String DATA_KEY_SAFARI_ANTS = "safari ants";
    private static final String DATA_KEY_CHALK_BROOD = "chalk brood";



    @Override
    protected List<Step> getSteps(Context context){
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
                                } catch(NumberFormatException e) {
                                    return false;
                                } catch(NullPointerException e) {
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
                new OptionStep(context, DATA_KEY_STATE_OF_HIVE ,
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
                                "High",
                                "Average",
                                "Low"
                        },
                        R.string.queen_cells,
                        R.string.queen_cells_error,
                        R.string.queen_cells_hive_details
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
                new TextStep(context, DATA_KEY_EMAIL, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, R.string.email, R.string.email_error, R.string.email_details, new TextStep.StepChecker() {
                    @Override
                    public boolean check(String input) {
                        return android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches();
                    }
                })
        );
        steps.add(
                new TextStep(context, DATA_KEY_PASSWORD, InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD, R.string.password, R.string.password_error, R.string.password_details, new TextStep.StepChecker() {
                    @Override
                    public boolean check(String input) {
                        return input.length() >= 5;
                    }
                })
        );
        steps.add(
                new DateStep(context, DATA_KEY_BIRTHDAY, R.string.birthday, R.string.birthday_error, R.string.birthday_details, new DateStep.StepChecker(){
                    @Override
                    public boolean check(int year, int month, int day){
                        Calendar today = new GregorianCalendar();
                        Calendar birthday = new GregorianCalendar(year, month, day);
                        today.add(Calendar.YEAR, -14);
                        return today.after(birthday);
                    }
                })
        );
        steps.add(
                new SeekBarStep(context, DATA_KEY_HEIGHT, 150, 180, R.string.height, R.string.height_error, R.string.height_details, new SeekBarStep.StepChecker() {
                    @Override
                    public boolean check(int progress) {
                        return progress >= 160;
                    }
                })
        );
        steps.add(
                new TextStep(context, DATA_KEY_CITY, InputType.TYPE_CLASS_TEXT, R.string.city, R.string.city_error, R.string.city_details)
        );

        return steps;
    }

    @Override
    protected void onFormFinished(Bundle data){
        Toast.makeText(this, "Form finished: " +
                        CheckBoxStep.checked(data, DATA_KEY_EULA) + ", " +
                        TextStep.text(data, DATA_KEY_EMAIL) + ", " +
                        TextStep.text(data, DATA_KEY_PASSWORD) + ", " +
                        DateStep.day(data, DATA_KEY_BIRTHDAY) + "." + DateStep.month(data, DATA_KEY_BIRTHDAY) + "." + DateStep.year(data, DATA_KEY_BIRTHDAY) + ", " +
                        SeekBarStep.progress(data, DATA_KEY_HEIGHT) + ", " +
                        TextStep.text(data, DATA_KEY_CITY),
                Toast.LENGTH_LONG).show();
        Log.d("MainActivity", "data: " + data.toString());
    }
}