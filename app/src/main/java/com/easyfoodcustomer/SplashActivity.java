package com.easyfoodcustomer;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.easyfoodcustomer.cart_db.DatabaseHelper;
import com.easyfoodcustomer.dashboard.DashboardActivity;
import com.easyfoodcustomer.search_post_code.SearchPostCodeActivity;
import com.easyfoodcustomer.utility.Constants;
import com.easyfoodcustomer.utility.SharedPreferencesClass;
import com.newrelic.agent.android.NewRelic;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.content.ContentValues.TAG;

public class SplashActivity extends AppCompatActivity {
    private SharedPreferencesClass prefManager;
    private DatabaseHelper db;
    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        printHashKey(SplashActivity.this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        db = new DatabaseHelper(this);

        try {
            Log.e("VersionName", "" + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //if(response.body().getAndroid_version().equals(getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(),0).versionName))

        NewRelic.withApplicationToken(
                "eu01xxae9ccb44aafd9f746b5862b2dcb19769290d"
        ).start(this.getApplicationContext());

        FirebaseInstanceId.getInstance().getToken();
        prefManager = new SharedPreferencesClass(getApplicationContext());
        Constants.setStatusBarGradiant(SplashActivity.this);
        final String islonch, islogin;
        islonch = prefManager.isFirstTimeLaunch();
        islogin = prefManager.isloginpref();
       /* if (prefManager.getInt(SharedPreferencesClass.IS_FOR_TABLE) )
            prefManager.setInt(SharedPreferencesClass.IS_FOR_TABLE, 0);*/
        Log.e("LoginValidate", "onCreate: islonch: " + islonch + "islonch: " + islonch);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (islogin != null) {
                    Constants.switchActivity(SplashActivity.this, DashboardActivity.class);
                    finish();
                } /*else if (islonch != null) {
                    Constants.switchActivity(SplashActivity.this, SearchPostCodeActivity.class);
                    finish();
                }*/ else {
                    Constants.switchActivity(SplashActivity.this, SearchPostCodeActivity.class);
                    finish();
                }
            }
        }, 400);
    }


    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }
    }
}
