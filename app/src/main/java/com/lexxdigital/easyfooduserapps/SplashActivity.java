package com.lexxdigital.easyfooduserapps;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.lexxdigital.easyfooduserapps.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapps.dashboard.DashboardActivity;
import com.lexxdigital.easyfooduserapps.inrodution_slide.IntroActivity;
import com.lexxdigital.easyfooduserapps.login.LoginActivity;
import com.lexxdigital.easyfooduserapps.utility.Constants;
import com.lexxdigital.easyfooduserapps.utility.SharedPreferencesClass;

public class SplashActivity extends AppCompatActivity {
    private SharedPreferencesClass prefManager;
    private DatabaseHelper db;
    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        db = new DatabaseHelper(this);
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                GlobalValues.getInstance().getDb().menuMaster().nuke();
                GlobalValues.getInstance().getDb().menuProductMaster().nuke();
                GlobalValues.getInstance().getDb().productSizeAndModifierMaster().nuke();
//                db.deleteCart();

            }
        }).start();*/
//        db.deleteCart();
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
}
