package com.example.petercockcroft.gwob_application.storage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by robin on 07/11/15.
 */
public class StorageManager {
    public static final String PREFS_NAME = "MyPrefsFile";
    private static SharedPreferences prefs = null;

    private static String HARVEST_DATA_KEY = "harvest";

    public static void init(Context context) {
        // Restore preferences
        prefs = context.getSharedPreferences(PREFS_NAME, 0);
    }


    public static int addRecordToStorage(HarvestObject record) {
        // Serialize record.
        String oldString = prefs.getString(HARVEST_DATA_KEY, "");
        if (oldString.length() != 0) {
            oldString += "\n";
        }
        oldString += record.toString();

        prefs.edit().putString(HARVEST_DATA_KEY, oldString).apply();
        return 0;
    }
}
