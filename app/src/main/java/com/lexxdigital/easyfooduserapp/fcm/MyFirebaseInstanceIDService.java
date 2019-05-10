package com.lexxdigital.easyfooduserapp.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.lexxdigital.easyfooduserapp.utility.SharedPreferencesClass;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseInstanceService";
    SharedPreferencesClass sharedPreferences;


    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        FirebaseMessaging.getInstance().subscribeToTopic("all");

        Log.e("fc_id", "Refreshed token: " + refreshedToken);
        sharedPreferences = new SharedPreferencesClass(this);
        storeRegIdInPref(refreshedToken);
        /*If you want to send messages to this application instance or manage this apps subscriptions on the server side, send the Instance ID token to your app server.*/

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        Log.e("FIREBASETOKEN ", refreshedToken.toString());

        sharedPreferences.setBoolean(sharedPreferences.IS_FB_TOKEN_UPDATE, false);
        sharedPreferences.setString(sharedPreferences.FB_TOKEN_ID, refreshedToken);

        if (sharedPreferences.getBoolean(sharedPreferences.IS_FB_TOKEN_UPDATE) || sharedPreferences.getString(sharedPreferences.FB_TOKEN_ID).equals("")) {
            return;
        }
/*

        API api = RestClient.getClient().create(API.class);
        UpdateFbTokenRequest data = new UpdateFbTokenRequest("1", BaseApp.getInstance().sharedPref().getString(SharedPref.FB_TOKEN_ID));

        api.updateFirebaseId(data).enqueue(new Callback<UpdateFirebaseIdResponse>() {
            @Override
            public void onResponse(Call<UpdateFirebaseIdResponse> call, Response<UpdateFirebaseIdResponse> response) {
                if (response.body().getStatus() && response.body().getData().getStatus()) {
                    BaseApp.getInstance().sharedPref().setBoolean(SharedPref.IS_FB_TOKEN_UPDATE, true);
                    Log.e("Token", "Token UPDATED");

                }
            }

            @Override
            public void onFailure(Call<UpdateFirebaseIdResponse> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }
        });
*/

    }


    private void storeRegIdInPref(String token) {
        sharedPreferences.setBoolean(sharedPreferences.IS_FB_TOKEN_UPDATE, false);
        sharedPreferences.setString(sharedPreferences.FB_TOKEN_ID, token);
    }
}