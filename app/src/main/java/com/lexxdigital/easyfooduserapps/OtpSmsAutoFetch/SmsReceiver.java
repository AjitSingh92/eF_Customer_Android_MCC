package com.lexxdigital.easyfooduserapps.OtpSmsAutoFetch;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsReceiver extends BroadcastReceiver {

    private static SmsListener mListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data  = intent.getExtras();

        Object[] pdus = (Object[]) data.get("pdus");

        for(int i=0;i<pdus.length;i++){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);

            String sender = smsMessage.getDisplayOriginatingAddress();
            //You must check here if the sender is your provider and not another one with same text.

            String messageBody = smsMessage.getMessageBody();
            Pattern pattern = Pattern.compile("(\\d{4})");
            Matcher matcher = pattern.matcher(messageBody);
            String val = "";
            if (matcher.find()) {
                val = matcher.group(0);
            }

            //Pass on the text to our listener.
            if (mListener!=null)
            mListener.messageReceived(val);
        }

    }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }

    public static void unbindListener() {
        mListener = null;
    }
}
