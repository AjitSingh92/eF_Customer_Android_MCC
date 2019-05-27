package com.lexxdigital.easyfooduserapp.utility;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.dashboard.DashboardActivity;

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
    public static final String ADDRESS_TYPE = "ADDRESS_TYPE";
    public static final String ADD_ADDRESS = "add_address";
    public static final String LOGIN_WITH_OTHER = "other";
    public static final String LOGIN_WITH_FB = "fb";
    public static final String LOGIN_WITH_GPLUS = "gplus";


    public static final String DEVELOPER_KEY = "AIzaSyB_0zHJbmm00TBeEaSq0PXF3wUU0IGRKn4";//"AIzaSyCzSy55IUBXlKj2bgRtVpX8pSC7pbCvetU";
    public static final String STRIPE_PUBLISH_KEY = "pk_test_DeSdrzpJiSsiGuXxZ2Id7xAV"; // For testing stripe payment
    //    public static final String STRIPE_PUBLISH_KEY = "pk_live_KVhKkdTGEbjB20Ux6f6funsR"; // For real stripe payment
    public static final String NOTIFICATION_TYPE = "NOTIFICATION_TYPE";
    public static final String NOTIFICATION_ORDER_ID = "NOTIFICATION_ORDER_ID";
    public static final int NOTIFICATION_ID = 10000;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 10000;
    public static int ORDER_STATUS = 0;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.bg_gradient);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            //  window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
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
            //  transaction.commit();
            transaction.commitAllowingStateLoss();
        }
    }

    public static String getTodayDay() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sd = new SimpleDateFormat("EEEE");
        String dayofweek = sd.format(c.getTime());

        return dayofweek;
    }


    public static String getPostalCodeByCoordinates(Context context, double lat, double lon) throws IOException {

        Geocoder mGeocoder = new Geocoder(context, Locale.getDefault());
        String zipcode = null;
        Address address = null;

        if (mGeocoder != null) {

            List<Address> addresses = mGeocoder.getFromLocation(lat, lon, 5);

            if (addresses != null && addresses.size() > 0) {

                for (int i = 0; i < addresses.size(); i++) {
                    address = addresses.get(i);
                    if (address.getPostalCode() != null) {
                        zipcode = address.getPostalCode();
                        Log.d("Postal code", "Postal code: " + address.getPostalCode());
                        break;
                    }

                }
                return zipcode;
            }
        }

        return null;
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
        //Find the currently focused view, so we can grab the correct window token from it.

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String getDayMonth(String date) {


        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

        Date readDate = null;
        try {
            readDate = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String dayMonth = readDate.toString().substring(0, 10);

        return dayMonth;
    }

}
