package com.lexxdigital.easyfooduserapps.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.lexxdigital.easyfooduserapps.utility.SharedPreferencesClass;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseInstanceService";
    SharedPreferencesClass sharedPreferences;


    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        FirebaseMessaging.getInstance().subscribeToTopic("all");

        Log.e("fc_id", "Refreshed token: " + refreshedToken);
        sharedPreferences = new SharedPreferencesClass(this);
        storeRegIdInPref(refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        Log.e("FIREBASETOKEN ", refreshedToken.toString());

        sharedPreferences.setBoolean(sharedPreferences.IS_FB_TOKEN_UPDATE, false);
        sharedPreferences.setString(sharedPreferences.FB_TOKEN_ID, refreshedToken);

        if (sharedPreferences.getBoolean(sharedPreferences.IS_FB_TOKEN_UPDATE) || sharedPreferences.getString(sharedPreferences.FB_TOKEN_ID).equals("")) {
            return;
        }

    }


    private void storeRegIdInPref(String token) {
        sharedPreferences.setBoolean(sharedPreferences.IS_FB_TOKEN_UPDATE, false);
        sharedPreferences.setString(sharedPreferences.FB_TOKEN_ID, token);
    }
}