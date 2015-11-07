package com.example.petercockcroft.gwob_application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.petercockcroft.gwob_application.storage.InspectionManager;

public class MainActivity extends AppCompatActivity {

    View choiceInspection;
    View choiceHarvest;
    View choiceReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.choices);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        choiceInspection = findViewById(R.id.choice_one);
        choiceInspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InspectionForm.class);
                startActivity(intent);
            }
        });
        choiceHarvest = findViewById(R.id.choice_two);
        choiceHarvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HarvestForm.class);
                startActivity(intent);
            }
        });
        choiceReport = findViewById(R.id.choice_three);
        choiceReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO add report class.
            }
        });

    }

}
