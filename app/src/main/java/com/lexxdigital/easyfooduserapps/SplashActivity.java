package com.lexxdigital.easyfooduserapps;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.lexxdigital.easyfooduserapps.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapps.dashboard.DashboardActivity;
import com.lexxdigital.easyfooduserapps.inrodution_slide.IntroActivity;
import com.lexxdigital.easyfooduserapps.login.LoginActivity;
import com.lexxdigital.easyfooduserapps.utility.Constants;
import com.lexxdigital.easyfooduserapps.utility.SharedPreferencesClass;
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
        NewRelic.withApplicationToken(
                "eu01xxae9ccb44aafd9f746b5862b2dcb19769290d"
        ).start(this.getApplicationContext());

        FirebaseInstanceId.getInstance().getToken();
        prefManager = new SharedPreferencesClass(getApplicationContext());
        Constants.setStatusBarGradiant(SplashActivity.this);
        final String islonch, islogin;
        islonch = prefManager.isFirstTimeLaunch();
        islogin = prefManager.isloginpref();
        Log.e("LoginValidate", "onCreate: islonch: " + islonch + "islonch: " + islonch);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (islonch != null && islogin != null) {
                    Constants.switchActivity(SplashActivity.this, DashboardActivity.class);
                    finish();
                } else if (islonch != null) {
                    Constants.switchActivity(SplashActivity.this, LoginActivity.class);
                    finish();
                } else {
                    Constants.switchActivity(SplashActivity.this, IntroActivity.class);
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
