package com.example.petercockcroft.gwob_application.storage;

import android.os.Parcel;
import android.os.Parcelable;

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
        String output = "" + date.getTime() + "|" +
                numOfCombs + "|" +
                getTrueFalse(cloth_beekeeper) + "|" +
                getTrueFalse(cloth_assist) + "|" +
                getTrueFalse(isSmoker) + "|" +
                numOfBuckets;
        return output;
    }

    private String getTrueFalse(int inp) {
        if (inp != 0) {
            return "YES";
        } else {
            return "NO";
        }
    }
}