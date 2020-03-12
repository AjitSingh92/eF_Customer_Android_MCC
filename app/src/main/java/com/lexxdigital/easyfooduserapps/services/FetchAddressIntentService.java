package com.lexxdigital.easyfooduserapps.services;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import com.lexxdigital.easyfooduserapps.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FetchAddressIntentService extends IntentService {
    static final int FAILURE_RESULT = 1;
    static final int SUCCESS_RESULT = 0;
    private static final String PACKAGE_NAME = "com.lexxdigital.easyfooduserapp.locationaddress";
    static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";
    static final String RESULT_DATA_POSTCODE_KEY = PACKAGE_NAME + ".RESULT_DATA_POSTCODE_KEY";
    static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";
    private static final String TAG = "FetchAddressIS";

    private ResultReceiver mReceiver;


    public FetchAddressIntentService() {
        super(TAG);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        String errorMessage = "";

        mReceiver = intent.getParcelableExtra(RECEIVER);

        if (mReceiver == null) {
            Log.wtf(TAG, "No receiver received. There is nowhere to send the results.");
            return;
        }


        Location location = intent.getParcelableExtra(LOCATION_DATA_EXTRA);


        if (location == null) {
            errorMessage = getString(R.string.no_location_data_provided);
            Log.wtf(TAG, errorMessage);
            deliverResultToReceiver(FAILURE_RESULT, errorMessage, null);
            return;
        }


        Geocoder geocoder = new Geocoder(this, Locale.getDefault());


        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1);
        } catch (IOException ioException) {

            errorMessage = getString(R.string.service_not_available);
            Log.e(TAG, errorMessage, ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            errorMessage = getString(R.string.invalid_lat_long_used);
            Log.e(TAG, errorMessage + ". " +
                    "Latitude = " + location.getLatitude() +
                    ", Longitude = " + location.getLongitude(), illegalArgumentException);
        }


        if (addresses == null || addresses.size() == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = getString(R.string.no_address_found);
                Log.e(TAG, errorMessage);
            }
            deliverResultToReceiver(FAILURE_RESULT, errorMessage, null);
        } else {
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<>();

            for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }
            Log.i(TAG, getString(R.string.address_found));
            deliverResultToReceiver(SUCCESS_RESULT, TextUtils.join(System.getProperty("line.separator"), addressFragments), address.getPostalCode());
        }
    }


    private void deliverResultToReceiver(int resultCode, String message, String postCode) {
        Bundle bundle = new Bundle();
        bundle.putString(RESULT_DATA_KEY, message);
        bundle.putString(RESULT_DATA_POSTCODE_KEY, postCode);
        mReceiver.send(resultCode, bundle);
    }
}