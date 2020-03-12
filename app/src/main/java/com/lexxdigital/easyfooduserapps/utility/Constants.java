package com.lexxdigital.easyfooduserapps.utility;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.lexxdigital.easyfooduserapps.R;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

public class Constants {

    public Constants() {
    }

    public static final String EDIT_ADDRESS = "EditAddress";
    public static final String ADDRESS = "address";
    public static final String LOGIN_WITH_OTHER = "other";
    public static final String LOGIN_WITH_FB = "fb";
    public static final String LOGIN_WITH_GPLUS = "gplus";


    public static final String DEVELOPER_KEY = "AIzaSyB_0zHJbmm00TBeEaSq0PXF3wUU0IGRKn4";
    //public static final String STRIPE_PUBLISH_KEY = "pk_test_DeSdrzpJiSsiGuXxZ2Id7xAV";
    public static final String STRIPE_PUBLISH_KEY = "pk_live_KVhKkdTGEbjB20Ux6f6funsR";

    public static final String NOTIFICATION_TYPE = "NOTIFICATION_TYPE";
    public static final String NOTIFICATION_ORDER_ID = "NOTIFICATION_ORDER_ID";
    public static final String PAYMENT_MODE = "PAYMENT_MODE";
    public static final String RESTAURENT_NAME = "RESTAURENT_NAME";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String CUSTOMER_ID = "CUSTOMER_ID";
    public static final String TOTAL_COST = "TOTAL_COST";
    public static final String ORDER_TYPE = "ORDER_TYPE";
    public static final String ORDER_TIME = "ORDER_TIME";
    public static final int NOTIFICATION_ID = 10000;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 10000;
    public static int ORDER_STATUS = 0;
    public static int MAX_LENGTH = 0;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.bg_gradient);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }

    public static boolean isValidEmail(String email) {

        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public static void switchActivity(Activity fromActivity, Class<?> toActivity) {
        Intent intent = new Intent(fromActivity, toActivity);
        fromActivity.startActivity(intent);
        fromActivity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    public static void switchActivityWithIntent(Activity fromActivity, Intent intent) {
        fromActivity.startActivity(intent);
        fromActivity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    public static void back(Activity fromActivity) {
        fromActivity.finish();
        fromActivity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    public static void fragmentCall(Fragment fragment, FragmentManager fm) {
        if (fragment != null) {
            FragmentTransaction transaction = fm.beginTransaction().addToBackStack(null);
            transaction.setCustomAnimations(R.anim.pull_in_left, R.anim.push_out_right);
            transaction.replace(R.id.frameLayout, fragment);
            transaction.commitAllowingStateLoss();
        }
    }

    public static String getTodayDay() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sd = new SimpleDateFormat("EEEE");
        String dayofweek = sd.format(c.getTime());

        return dayofweek;
    }


    public static boolean isInternetConnectionAvailable(int timeOut) {
        InetAddress inetAddress = null;
        try {
            Future<InetAddress> future = Executors.newSingleThreadExecutor().submit(new Callable<InetAddress>() {
                @Override
                public InetAddress call() {
                    try {
                        return InetAddress.getByName("google.com");
                    } catch (UnknownHostException e) {
                        return null;
                    }
                }
            });
            inetAddress = future.get(timeOut, TimeUnit.MILLISECONDS);
            future.cancel(true);
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        } catch (TimeoutException e) {
        }
        return inetAddress != null && !inetAddress.equals("");
    }

    public static void showDialog(Activity activity, String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog2, int id) {
                        dialog2.cancel();

                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    public static void hideKeyboard(Context activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static String capitalize(String line) {

        return Character.toUpperCase(line.charAt(0)) + line.substring(1).toLowerCase();
    }
}
