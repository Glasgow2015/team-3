package com.example.petercockcroft.gwob_application;

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

public class HarvestForm extends SingleInputFormActivity {

    private static final String DATA_KEY_HEIGHT = "height";
    private static final String DATA_KEY_EULA = "eula";
    private static final String DATA_KEY_EMAIL = "email";
    private static final String DATA_KEY_PASSWORD = "password";
    private static final String DATA_KEY_BIRTHDAY = "birthday";
    private static final String DATA_KEY_CITY = "city";


    private static final String DATA_KEY_DATE = "date";
    private static final String DATA_KEY_RIPE_COMBS = "combs";
    private static final String DATA_KEY_CLOTH_BEE = "cloth_beekeeper";
    private static final String DATA_KEY_CLOTH_ASSIST = "cloth_assistents";
    private static final String DATA_KEY_SMOKER = "smoker";
    private static final String DATA_KEY_BUCKETS = "buckets";




    @Override
    protected List<Step> getSteps(Context context) {
        List<Step> steps = new ArrayList<Step>();

        // Add harvest date.
        steps.add(
                new DateStep(context, DATA_KEY_DATE,
                        R.string.harvest_date_title,
                        R.string.harvest_date_error,
                        R.string.harvest_date_detail,
                        new DateStep.StepChecker() {
                            @Override
                            public boolean check(int year, int month, int day) {
                                Calendar today = new GregorianCalendar();
                                Calendar harvest_date = new GregorianCalendar(year, month, day);
                                return today.after(harvest_date);
                            }
                        })
        );


        // Quantity of ripe combs.
        steps.add(
                new TextStep(context, DATA_KEY_RIPE_COMBS,
                        InputType.TYPE_NUMBER_VARIATION_NORMAL,
                        R.string.harvest_quant_title,
                        R.string.harvest_quant_error,
                        R.string.harvest_quant_detail,
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

        // Protective clothing available for beekeeper YES/NO.
        steps.add(
                new OptionStep(context, DATA_KEY_CLOTH_BEE,
                        new String[]{"Yes", "No"},
                        R.string.harvest_cloth_bee_title,
                        R.string.harvest_cloth_assist_error,
                        R.string.harvest_cloth_assist_detail
                )
        );

        // Protective clothing available for all assistants YES/NO.

        // Smoker available, YES/NO.

        // Number of clean, airtight buckets available for harvest.


        steps.add(
                new CheckBoxStep(context, DATA_KEY_EULA, R.string.eula, R.string.eula_title, R.string.eula_error, R.string.eula_details, new CheckBoxStep.StepChecker() {
                    @Override
                    public boolean check(boolean input) {
                        return input;
                    }
                })
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
                new DateStep(context, DATA_KEY_BIRTHDAY, R.string.birthday, R.string.birthday_error, R.string.birthday_details, new DateStep.StepChecker() {
                    @Override
                    public boolean check(int year, int month, int day) {
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
    protected void onFormFinished(Bundle data) {
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