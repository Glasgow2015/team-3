package com.example.petercockcroft.gwob_application;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Toast;

import com.example.petercockcroft.gwob_application.storage.HarvestObject;
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

public class HarvestForm extends SingleInputFormActivity {
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
                        InputType.TYPE_CLASS_NUMBER,
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
                        new String[]{
                                getResources().getString(R.string.boolean_positive),
                                getResources().getString(R.string.boolean_negative)
                        },
                        R.string.harvest_cloth_bee_title,
                        R.string.harvest_cloth_bee_error,
                        R.string.harvest_cloth_bee_detail
                )
        );

        // Protective clothing available for all assistants YES/NO.
        steps.add(
                new OptionStep(context, DATA_KEY_CLOTH_ASSIST,
                        new String[]{
                                getResources().getString(R.string.boolean_positive),
                                getResources().getString(R.string.boolean_negative)
                        },
                        R.string.harvest_cloth_assist_title,
                        R.string.harvest_cloth_assist_error,
                        R.string.harvest_cloth_assist_detail
                )
        );

        // Smoker available, YES/NO.
        steps.add(
                new OptionStep(context, DATA_KEY_SMOKER,
                        new String[]{
                                getResources().getString(R.string.boolean_positive),
                                getResources().getString(R.string.boolean_negative)
                        },
                        R.string.harvest_smoker_title,
                        R.string.harvest_smoker_error,
                        R.string.harvest_smoker_detail
                )
        );

        // Number of clean, airtight buckets available for harvest.
        steps.add(
                new TextStep(context, DATA_KEY_BUCKETS,
                        InputType.TYPE_CLASS_NUMBER,
                        R.string.harvest_num_buck_title,
                        R.string.harvest_num_buck_error,
                        R.string.harvest_num_buck_detail,
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

        return steps;
    }

    @Override
    protected void onFormFinished(Bundle data) {
        // Extract information from form.
        int day = DateStep.day(data, DATA_KEY_DATE),
                month = DateStep.month(data, DATA_KEY_DATE),
                year = DateStep.year(data, DATA_KEY_DATE),
                numOfCombs = Integer.parseInt(TextStep.text(data, DATA_KEY_RIPE_COMBS)),
                cloth_bee = OptionStep.selectedOption(data, DATA_KEY_CLOTH_BEE),
                cloth_ass = OptionStep.selectedOption(data, DATA_KEY_CLOTH_ASSIST),
                smoker = OptionStep.selectedOption(data, DATA_KEY_SMOKER),
                numOfBuckets = Integer.parseInt(TextStep.text(data, DATA_KEY_BUCKETS));


        // Create Harvest object.
        HarvestObject results = new HarvestObject(new GregorianCalendar(year, month, day),
                numOfCombs, cloth_bee, cloth_ass, smoker, numOfBuckets);
        // Add element to storage.
        StorageManager.addRecordToStorage(results);
        Toast.makeText(this, "Form finished: " +
                        DateStep.day(data, DATA_KEY_DATE) + ", " +
                        TextStep.text(data, DATA_KEY_RIPE_COMBS) + ", " +
                        OptionStep.selectedOption(data, DATA_KEY_CLOTH_BEE) + ", " +
                        OptionStep.selectedOption(data, DATA_KEY_CLOTH_ASSIST) + ", " +
                        OptionStep.selectedOption(data, DATA_KEY_SMOKER) + ", " +
                        TextStep.text(data, DATA_KEY_BUCKETS),
                Toast.LENGTH_LONG).show();
        Log.d("MainActivity", "data: " + data.toString());
    }
}