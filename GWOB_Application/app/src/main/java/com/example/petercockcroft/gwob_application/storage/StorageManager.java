package com.example.petercockcroft.gwob_application.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.SmsManager;

/**
 * Created by robin on 07/11/15.
 */
public class StorageManager {
    public static final String PREFS_NAME = "MyPrefsFile";
    private static SharedPreferences prefs = null;

    private static String HARVEST_DATA_KEY = "harvest";
    private static String INSPECTION_DATA_KEY = "inspection";

    public static void init(Context context) {
        // Restore preferences
        prefs = context.getSharedPreferences(PREFS_NAME, 0);
    }


    public static int addRecordToStorage(Object record) {
        String key;

        if (record instanceof HarvestObject) {
            key = HARVEST_DATA_KEY;

        } else if (record instanceof InspectionObject) {
            key = INSPECTION_DATA_KEY;

        } else {
            // Error occurred.
            System.err.println("addRecordToStorage: Error occurred, record wrong type.");
            return 1;
        }

        // Serialize record.
        String oldString = prefs.getString(key, "");
        if (oldString.length() != 0) {
            oldString += "\n";
        }
        oldString += record.toString();

        prefs.edit().putString(key, oldString).apply();
        return 0;
    }


}
