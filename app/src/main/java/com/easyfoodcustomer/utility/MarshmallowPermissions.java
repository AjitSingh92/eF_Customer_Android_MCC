package com.easyfoodcustomer.utility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static androidx.core.app.ActivityCompat.requestPermissions;

/**
 * Created by Tanzanite1 on 5/17/2016.
 */
public class MarshmallowPermissions {
    public static final int ACCESS_FINE_LOCATION__PERMISSION_REQUEST_CODE = 1;
    public static final int PHONE_PERMISSION_REQUEST_CODE = 2;
    public  static  final  int READ_STOREGE__PERMISSION_REQUEST_CODE = 3;
    public  static  final  int WRITE_STOREGE__PERMISSION_REQUEST_CODE = 4;
    public  static  final  int RECEIVE_SMS__PERMISSION_REQUEST_CODE = 5;
    public  static  final  int GET_ACCOUNTS_PERMISSION_REQUEST_CODE = 6;
    public  static  final int ACCESS_WIFI_STATE = 7;
    public  static  final  int CAMERA_PERMISSION_REQUEST_CODE = 8;
    public static final int ACCESS_CONTACTS_PERMISSION_REQUEST_CODE = 9;

    public MarshmallowPermissions() {

    }

    public void requestAccessFineLocationPermission(Context mContext) {
        requestPermissions((Activity) mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION__PERMISSION_REQUEST_CODE);
    }

    public boolean checkAccessFineLocationPermission(Context mContext) {
        int result = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;

        }
    }

    public boolean checkAccessReadstorePermission(Context mContext) {
        int result = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {

            return false;
        }
    }

    public void requestReadstorageLocationPermission(Context mContext) {
        requestPermissions((Activity) mContext, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STOREGE__PERMISSION_REQUEST_CODE);
    }

    public boolean checkwifiStatePermission(Context mContext) {
        int result = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_WIFI_STATE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {

            return false;
        }
    }

    public void requestwifiStatePermission(Context mContext) {
        requestPermissions((Activity) mContext, new String[]{Manifest.permission.ACCESS_WIFI_STATE}, WRITE_STOREGE__PERMISSION_REQUEST_CODE);
    }
    public void requestPhoneStatePermission(Context mContext) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.READ_PHONE_STATE}, PHONE_PERMISSION_REQUEST_CODE);
    }

    public boolean checkPhoneStatePermission(Context mContext) {
        int result = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }




    public boolean checkAccessWritestorePermission(Context mContext) {
        int result = ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {

            return false;
        }
    }

    public void requestWritestoreLocationPermission(Context mContext) {
        ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_STOREGE__PERMISSION_REQUEST_CODE);
    }


    public boolean checkAccessreadstorePermission(Context mContext) {
        int result = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {

            return false;
        }
    }

    public void requestreadstoreLocationPermission(Context mContext) {
        ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STOREGE__PERMISSION_REQUEST_CODE);
    }


    public void requestReceiveSmsPermission(Context mContext) {
        ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.RECEIVE_SMS}, RECEIVE_SMS__PERMISSION_REQUEST_CODE);
    }

    public boolean checkReceiveSmsPermission(Context mContext) {
        int result = ContextCompat.checkSelfPermission(mContext, Manifest.permission.RECEIVE_SMS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;

        } else {

            return false;

        }
    }

    public boolean checkCameraPermission(Context mContext) {
        int result = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;

        } else {

            return false;

        }
    }

    public void requestCameraPermission(Context mContext) {
        ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
    }

    public void requestReadContactsPermission(Context mContext) {
        ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.READ_CONTACTS}, ACCESS_CONTACTS_PERMISSION_REQUEST_CODE);
    }

    public boolean checkReadContactsPermission(Context mContext) {
        int result = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_CONTACTS );
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

}
