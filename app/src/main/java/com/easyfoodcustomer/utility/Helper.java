package com.easyfoodcustomer.utility;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import com.google.android.material.snackbar.Snackbar;

import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static android.content.ContentValues.TAG;
import static com.easyfoodcustomer.utility.Constants.capitalize;


/**
 * Created by dheerajpandey on 6/22/18.
 */

public class Helper {
    private static Snackbar snackbar;
    private static Dialog dialog;
    private static Toast mToast;
    private static boolean isApplied;


    private static boolean isPopUp;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    static String finalUrl;


    /**
     * for showing the messages in the bottom
     */
    public static void showSnackBar(View view1, String message) {
        try {
            snackbar = Snackbar.make(view1, message, Snackbar.LENGTH_LONG);
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * for showing the messages in the bottom
     */
    public static void showSnackBar(TextView view1, String message) {
        try {
            snackbar = Snackbar.make(view1, message, Snackbar.LENGTH_LONG);
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    public static String getCurrentDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        return dayOfTheWeek;
    }

    public static String formatDateTime(String inputDate) {
        String outputDate = null;
      /*  "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
        2019-06-17T11:01:24.000Z*/

        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        Date date = null;
        try {
            date = inputFormat.parse(inputDate);
            outputDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDate;
    }

    public static String newformatDateTime(String inputDate) {
        String outputDate = null;
      /*  "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
        2019-06-17T11:01:24.000Z*/

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        Date date = null;
        try {
            date = inputFormat.parse(inputDate);
            outputDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDate;
    }


    public static String formatDateTime(Date inputDate) {
        String outPutDate = null;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat outPutFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        outPutFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            outPutDate = outPutFormat.format(inputDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outPutDate;
    }


    public static String formatLastSyncDate(Date inputDate) {
        String outPutDate = null;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat outPutFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            outPutDate = outPutFormat.format(inputDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outPutDate;


    }

    public static boolean isPreOrder(final String startTime, final String endTime) {

        try {

            Date dStart = new SimpleDateFormat("HH:mm").parse(startTime);
            Date dEnd = new SimpleDateFormat("HH:mm").parse(endTime);
            Calendar calendarStart = Calendar.getInstance();
            Calendar calendarEnd = Calendar.getInstance();


            calendarStart.setTime(dStart);
            calendarEnd.setTime(dEnd);
            calendarStart.add(Calendar.DATE, 1);
            calendarEnd.add(Calendar.DATE, 1);

            DateFormat df = new SimpleDateFormat("HH:mm");
            String currentTime = df.format(Calendar.getInstance().getTime());
            Date d = new SimpleDateFormat("HH:mm").parse(currentTime);
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(d);
            currentCalendar.add(Calendar.DATE, 1);


            Date x = currentCalendar.getTime();
            if (x.after(calendarStart.getTime()) && x.before(calendarEnd.getTime())) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }


    /*for checking the validation of the email format  entered by the user*/
    public static boolean isValidEmail(String emailAddress) {
        return Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
    }


    public static void startDialer(Activity activity, String s) {
        s = s.replaceAll("[^\\d.]", "");
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + s));
        activity.startActivity(intent);
    }

    public static boolean isValidName(String name) {
        // Pattern pattern = Pattern.compile(new String("^[a-zA-Z\\s]*$"));
        Pattern pattern = Pattern.compile(new String("^[a-zA-Zء-ي\\s]*$"));

        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public static boolean isValidBusinessName(String businessName) {
        Pattern pattern = Pattern.compile(new String("^[a-zA-Z0-9\\s]*$"));
        Matcher matcher = pattern.matcher(businessName);
        return matcher.matches();
    }

    public static String parseLongDate(long input) {
        SimpleDateFormat outFormat = new SimpleDateFormat("dd MMM yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(input * 1000L);
        return outFormat.format(calendar.getTime());
    }


    public static boolean isValidNo(String businessName) {
        Pattern pattern = Pattern.compile(new String("^[0-9\\s]*$"));
        Matcher matcher = pattern.matcher(businessName);
        return matcher.matches();
    }


    public static void setTextViewDrawableColor(final EditText editText, final int color) {
        for (Drawable drawable : editText.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
            }
        }
    }


    public static String getImagePath(Uri uri, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = activity.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }


    public static void openUrlInBrowser(Activity activity, String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(browserIntent);
    }


    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager in = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            View view = activity.findViewById(android.R.id.content);
            if (in != null) {
                in.hideSoftInputFromWindow(view.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Throwable e) {
        }

    }

    public static boolean isInternetOn(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;
        if (cm != null
        ) {
            netInfo = cm.getActiveNetworkInfo();
        }
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static String formatDate(String inputDate) {
        String outputDate = null;
      /*  "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
        2019-06-17T11:01:24.000Z*/
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM,dd HH:mm", Locale.getDefault());
        Date date = null;
        try {
            date = inputFormat.parse(inputDate);
            outputDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDate;
    }

    public static String getDifferenceBetweenTime(String str_date) {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = formatter.parse(str_date);
            return DateUtils.getRelativeTimeSpanString(date.getTime(), System.currentTimeMillis(), 0).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Today is " + date.getTime());
        return "";

    }

    /**
     * This method is to return duration in minute and seconds for the playing time
     *
     * @param seconds specifies the total millisceonds played
     */

    public static String formatPlayingDuration(int seconds) {
        // int seconds = milliseconds / 1000;
        int minutes = seconds / 60;
        int displayedSeconds = seconds % 60;
        if (minutes == 0)
            return "00:" + addZero(displayedSeconds);
        return addZero(minutes) + ":" + addZero(displayedSeconds);

    }

    /**
     * This method is to return number with zero or not zero i.e. to make a number in two digit
     *
     * @param number specifies the number that needs to be foramtted
     */
    private static String addZero(int number) {
        if (number < 10)
            return "0" + number;
        return "" + number;
    }


    public String getDurationString(int seconds) {
        //   int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;
        return twoDigitString(minutes) + " : " + twoDigitString(seconds);
    }


    private String twoDigitString(int number) {
        if (number == 0) {
            return "00";
        }
        if (number / 10 == 0) {
            return "0" + number;
        }
        return String.valueOf(number);
    }

    public static String getYouTubeId(String youTubeUrl) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return "error";
        }
    }


    //  "https://www.youtube.com/watch?v=EPGUNW8GYoE&feature=emb_logo"
    //  "https://player.vimeo.com/video/205947326

    public static boolean isYoutubeUrl(String youTubeURl) {
        boolean success;
        String pattern = "^(http(s)?:\\/\\/)?((w){3}.)?youtu(be|.be)?(\\.com)?\\/.+";
        if (!youTubeURl.isEmpty() && youTubeURl.matches(pattern)) {
            success = true;
        } else {
            // Not Valid youtube URL
            success = false;
        }
        return success;
    }

    public static boolean isVimeoUrl(String youTubeURl) {
        boolean success;
        String pattern = "^(http(s)?:\\/\\/)?((w){3}.)?player?(\\.vimeo)?(\\.com)?\\/.+";
        if (!youTubeURl.isEmpty() && youTubeURl.matches(pattern)) {
            success = true;
        } else {
            // Not Valid youtube URL
            success = false;
        }
        return success;
    }

    /*------------------------------------------- Method to print Hash key-----------------------------------------------------*/
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


    /*-------------------------------------xxxxxxxxxxxxxxxxxx----------------------------------------------------------------------*/


    public static boolean isRecent(long currentTimeStamp) {
/*
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(currentTimeStamp);

        Calendar now = Calendar.getInstance();

        final String timeFormatString = "h:mm aa";
        final String dateTimeFormatString = "EEEE, MMMM d, h:mm aa";
        final long HOURS = 60 * 60 * 60;

        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE)) {
            return true;
        } else {
            return false;
        }*/


        long epochInMillis = currentTimeStamp * 1000;
        Calendar now = Calendar.getInstance();
        Calendar timeToCheck = Calendar.getInstance();
        timeToCheck.setTimeInMillis(epochInMillis);

        if (now.get(Calendar.YEAR) == timeToCheck.get(Calendar.YEAR)) {
            if (now.get(Calendar.DAY_OF_YEAR) == timeToCheck.get(Calendar.DAY_OF_YEAR)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}


