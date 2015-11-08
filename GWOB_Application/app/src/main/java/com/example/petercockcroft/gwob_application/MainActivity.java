package com.example.petercockcroft.gwob_application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petercockcroft.gwob_application.storage.StorageManager;

public class MainActivity extends AppCompatActivity {

    View choiceInspection;
    View choiceHarvest;
    View choiceReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StorageManager.init(this.getApplication());

        setContentView(R.layout.choices);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        choiceInspection = findViewById(R.id.choice_one);
        ((TextView)choiceInspection.findViewById(R.id.choice_name)).setText("Oh yeah bitches! Inspection.");
        ((ImageView)choiceInspection.findViewById(R.id.hexPic)).setImageResource(R.drawable.glass);
        choiceInspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InspectionForm.class);
                startActivity(intent);
            }
        });

        choiceHarvest = findViewById(R.id.choice_two);
        ((TextView)choiceHarvest.findViewById(R.id.choice_name)).setText("Oh yeah bitches! Harvest.");
        ((ImageView)choiceHarvest.findViewById(R.id.hexPic)).setImageResource(R.drawable.hex);
        choiceHarvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HarvestForm.class);
                startActivity(intent);
            }
        });

        choiceReport = findViewById(R.id.choice_three);
        ((ImageView)choiceReport.findViewById(R.id.hexPic)).setImageResource(R.drawable.report);
        ((TextView)choiceReport.findViewById(R.id.choice_name)).setText("REPOAAARTS");
        choiceReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO add report class.
            }
        });

    }

}
