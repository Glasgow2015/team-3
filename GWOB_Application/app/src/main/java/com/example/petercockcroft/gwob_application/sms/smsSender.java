package com.example.petercockcroft.gwob_application.sms;

import android.telephony.SmsManager;

/**
 * Created by petercockcroft on 08/11/15.
 */
public class smsSender {
    public static void sendSms(String message){
        String phoneNumber = "9999999999";
        String smsBody = message;

        // Get the default instance of SmsManager
        SmsManager smsManager = SmsManager.getDefault();
        // Send a text based SMS
        smsManager.sendTextMessage(phoneNumber, null, smsBody, null, null);
    }
}
