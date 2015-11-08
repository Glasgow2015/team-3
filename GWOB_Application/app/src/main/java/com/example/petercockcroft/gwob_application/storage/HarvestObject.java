package com.example.petercockcroft.gwob_application.storage;

import java.util.Calendar;

/**
 * Created by robin on 07/11/15.
 */
public class HarvestObject {
    Calendar date;
    int numOfCombs;
    int cloth_beekeeper;
    int cloth_assist;
    int isSmoker;
    int numOfBuckets;

    public HarvestObject(Calendar date,
                         int numOfCombs,
                         int cloth_beekeeper,
                         int cloth_assist,
                         int isSmoker,
                         int numOfBuckets) {
        super();
        this.date = date;
        this.numOfCombs = numOfCombs;
        this.cloth_beekeeper = cloth_beekeeper;
        this.cloth_assist = cloth_assist;
        this.isSmoker = isSmoker;
        this.numOfBuckets = numOfBuckets;
    }

    @Override
    public String toString() {
        // TODO
        String output = "" + date.getTimeInMillis() / 1000 + "|" +
                numOfCombs + "|" +
                getTrueFalse(cloth_beekeeper) + "|" +
                getTrueFalse(cloth_assist) + "|" +
                getTrueFalse(isSmoker) + "|" +
                numOfBuckets;
        return output;
    }

    private String getTrueFalse(int inp) {
        if (inp != 0) {
            return "NO";
        } else {
            return "YES";
        }
    }
}
