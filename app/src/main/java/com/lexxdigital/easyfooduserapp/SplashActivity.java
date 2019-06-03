package com.lexxdigital.easyfooduserapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.Dash;
import com.google.firebase.iid.FirebaseInstanceId;
import com.lexxdigital.easyfooduserapp.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapp.dashboard.DashboardActivity;
import com.lexxdigital.easyfooduserapp.inrodution_slide.IntroActivity;
import com.lexxdigital.easyfooduserapp.inrodution_slide.PrefManager;
import com.lexxdigital.easyfooduserapp.login.LoginActivity;
import com.lexxdigital.easyfooduserapp.restaurant_details.RestaurantDetailsActivity;
import com.lexxdigital.easyfooduserapp.utility.Constants;
import com.lexxdigital.easyfooduserapp.utility.GlobalValues;
import com.lexxdigital.easyfooduserapp.utility.SharedPreferencesClass;

public class SplashActivity extends AppCompatActivity {
    private SharedPreferencesClass prefManager;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
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
