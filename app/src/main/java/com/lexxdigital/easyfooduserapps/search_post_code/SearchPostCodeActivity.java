package com.lexxdigital.easyfooduserapps.search_post_code;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.api.EditProfileInterface;
import com.lexxdigital.easyfooduserapps.api.RestaurantsDealsInterface;
import com.lexxdigital.easyfooduserapps.dashboard.DashboardActivity;
import com.lexxdigital.easyfooduserapps.model.UpdateCustomerPostcodeRequest;
import com.lexxdigital.easyfooduserapps.model.UpdateCustomerPostcodeResponse;
import com.lexxdigital.easyfooduserapps.model.edit_account_request.EditAccountRequest;
import com.lexxdigital.easyfooduserapps.model.edit_account_response.EditAccountResponse;
import com.lexxdigital.easyfooduserapps.model.landing_page_request.CheckDeliveryPostcodeRequest;
import com.lexxdigital.easyfooduserapps.model.landing_page_response.CheckDeliveryPostcodeResponse;
import com.lexxdigital.easyfooduserapps.search_post_code.api.SearchPostCodeInterface;
import com.lexxdigital.easyfooduserapps.search_post_code.model.search_request.SearchPostCodeRequest;
import com.lexxdigital.easyfooduserapps.search_post_code.model.search_response.SearchPostCodeResponse;
import com.lexxdigital.easyfooduserapps.services.AppLocationService;
import com.lexxdigital.easyfooduserapps.utility.ApiClient;
import com.lexxdigital.easyfooduserapps.utility.Constants;
import com.lexxdigital.easyfooduserapps.utility.GlobalValues;
import com.lexxdigital.easyfooduserapps.utility.LocationUtil.PermissionUtils;
import com.lexxdigital.easyfooduserapps.utility.SharedPreferencesClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPostCodeActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback,
        PermissionUtils.PermissionResultCallback {

    @BindView(R.id.search_post_et)
    EditText searchPostEt;
    @BindView(R.id.searchicon)
    ImageView searchicon;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.btn_search_cancel)
    ImageView btnSearchCancel;
    private Dialog dialog;
    SharedPreferencesClass sharedPreferencesClass;
    String postalCode;
    boolean validPostcode = true;
    AppLocationService appLocationService;
    // LogCat tag
    private static final String TAG = SearchPostCodeActivity.class.getSimpleName();

    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;

    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;

    double latitude;
    double longitude;
    private GlobalValues val;

    ArrayList<String> permissions = new ArrayList<>();
    PermissionUtils permissionUtils;

    boolean isPermissionGranted;
    private boolean doubleBackToExitPressedOnce = false;
    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_post_code);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        ButterKnife.bind(this);
        appLocationService = new AppLocationService(SearchPostCodeActivity.this);
        val = (GlobalValues) getApplicationContext();
        dialog = new Dialog(SearchPostCodeActivity.this);
        dialog.setTitle("");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (validPostcode) {
                    val.setIsFromDealPage(false);
                    startActivity(new Intent(SearchPostCodeActivity.this, DashboardActivity.class));
                    finish();
                    overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                }
            }
        });
        sharedPreferencesClass = new SharedPreferencesClass(this);
        String postalcodePref = sharedPreferencesClass.getPostalCode();
        if (postalcodePref != null) {
            searchPostEt.setText(postalcodePref);
        }
        if (val.getIsFromDealPage()) {
            btnSearchCancel.setVisibility(View.VISIBLE);
        } else {
            btnSearchCancel.setVisibility(View.INVISIBLE);
        }

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        searchPostEt.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        permissionUtils = new PermissionUtils(SearchPostCodeActivity.this);

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        permissionUtils.check_permission(permissions, "Need GPS permission for getting your location", 1);

        if (checkPlayServices()) {
            buildGoogleApiClient();
        }
    }

    public void callSearchPostAPI(final String postcode) {
        SearchPostCodeInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(SearchPostCodeInterface.class);

        SearchPostCodeRequest request = new SearchPostCodeRequest();
        request.setPostCode(postcode);

        Call<SearchPostCodeResponse> call3 = apiInterface.mSearchPost(request);
        call3.enqueue(new Callback<SearchPostCodeResponse>() {
            @Override
            public void onResponse(Call<SearchPostCodeResponse> call, Response<SearchPostCodeResponse> response) {
                try {

                    if (response.body().getSuccess()) {
                        String pstcode = response.body().getData().getPostcode();
                        DashboardActivity.getInstance().setLocation(postcode);
                        checkRestaurantDeliveryPostcode(pstcode);

                    } else {
                        dialog.hide();
                        errorDialog("Invalid Postcode", null);
                    }
                } catch (Exception e) {
                    dialog.hide();
                    alertDialogNoRestaurant();
                }
            }

            @Override
            public void onFailure(Call<SearchPostCodeResponse> call, Throwable t) {
                Log.e("Error <>>>", ">>>>>" + t.getMessage());
                dialog.hide();
                alertDialogNoRestaurant();
            }
        });
    }

    public void showDialog(String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(SearchPostCodeActivity.this);
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


    private void getLocation() {

        if (isPermissionGranted) {

            try {
                mLastLocation = LocationServices.FusedLocationApi
                        .getLastLocation(mGoogleApiClient);
            } catch (SecurityException e) {
                e.printStackTrace();
            }

        }

    }

    public Address getAddress(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            return addresses.get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


    public void getAddress() {

        Address locationAddress = getAddress(latitude, longitude);

        if (locationAddress != null) {
            String address = locationAddress.getAddressLine(0);
            String address1 = locationAddress.getAddressLine(1);
            String city = locationAddress.getLocality();
            String state = locationAddress.getAdminArea();
            String country = locationAddress.getCountryName();
            postalCode = locationAddress.getPostalCode();

            String currentLocation;

            if (!TextUtils.isEmpty(address)) {
                currentLocation = address;

                if (!TextUtils.isEmpty(address1))
                    currentLocation += "\n" + address1;

                if (!TextUtils.isEmpty(city)) {
                    currentLocation += "\n" + city;

                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation += " - " + postalCode;
                } else {
                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation += "\n" + postalCode;
                }

                if (!TextUtils.isEmpty(state))
                    currentLocation += "\n" + state;

                if (!TextUtils.isEmpty(country))
                    currentLocation += "\n" + country;

                searchPostEt.setText(postalCode);


            }

        }

    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        mGoogleApiClient.connect();

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {

                final Status status = locationSettingsResult.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:

                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(SearchPostCodeActivity.this, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {

                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });


    }


    /**
     * Method to verify google play services on the device
     */

    private boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(this, resultCode,
                        PLAY_SERVICES_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                    default:
                        break;
                }
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {
        getLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        permissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


    @Override
    public void PermissionGranted(int request_code) {
        isPermissionGranted = true;
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
    }

    @Override
    public void PermissionDenied(int request_code) {
    }

    @Override
    public void NeverAskAgain(int request_code) {
    }


    @OnClick(R.id.btn_search)
    public void onViewClicked() {
        if (searchPostEt.getText().toString().trim().equalsIgnoreCase("")) {
            showDialog("Please enter postcode.");
        } else {
            if (Constants.isInternetConnectionAvailable(3000)) {
                dialog.show();
                callSearchPostAPI(searchPostEt.getText().toString());
            } else {
                Constants.showDialog(SearchPostCodeActivity.this, "Please check internet connection.");
            }
        }
    }

    @Override
    public void onBackPressed() {

        if (val.getIsFromDealPage()) {
            if (validPostcode) {
                val.setIsFromDealPage(false);
                startActivity(new Intent(SearchPostCodeActivity.this, DashboardActivity.class));
                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
            return;
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                finish();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }


    @OnClick({R.id.btn_search_cancel, R.id.searchicon, R.id.btn_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_search_cancel:
                val.setIsFromDealPage(false);
                startActivity(new Intent(SearchPostCodeActivity.this, DashboardActivity.class));
                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                break;
            case R.id.searchicon:
                getLocation();

                if (mLastLocation != null) {
                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();
                    getAddress();

                } else {
                    mLastLocation = appLocationService.getLocation(LocationManager.NETWORK_PROVIDER);

                    if (checkPlayServices()) {
                        buildGoogleApiClient();
                    }

                }
                break;
        }
    }

    public void alertDialogNoRestaurant() {
        LayoutInflater factory = LayoutInflater.from(SearchPostCodeActivity.this);
        final View mDialogView = factory.inflate(R.layout.no_resturent_popup, null);
        final AlertDialog allergyDialog = new AlertDialog.Builder(SearchPostCodeActivity.this).create();
        allergyDialog.setView(mDialogView);
        allergyDialog.setCancelable(false);
        allergyDialog.setCanceledOnTouchOutside(false);
        allergyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //   final TextView ok_tv = (TextView)  mDialogView.findViewById(R.id.okTv);
        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                allergyDialog.dismiss();
                validPostcode = false;

            }
        });
        mDialogView.findViewById(R.id.cross_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allergyDialog.dismiss();
                validPostcode = false;
            }
        });

        allergyDialog.show();
    }

    public void errorDialog(String msg1, String msg2) {
        View dialogView = LayoutInflater.from(SearchPostCodeActivity.this).inflate(R.layout.no_resturent_popup, null);
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView.getRootView());
        dialog.setCancelable(false);


        TextView tvMsg1, tvMsg2;

        tvMsg1 = dialogView.findViewById(R.id.tv_msg1);
        tvMsg2 = dialogView.findViewById(R.id.tv_msg2);

        tvMsg1.setText(msg1);
        tvMsg2.setText(msg2);
        if (msg2 == null) {
            tvMsg2.setVisibility(View.GONE);
        }

        dialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                validPostcode = false;

            }
        });
        dialogView.findViewById(R.id.cross_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                validPostcode = false;
            }
        });

        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    public void updateAccountDetail() {
        EditProfileInterface apiInterface = ApiClient.getClient(this).create(EditProfileInterface.class);
        EditAccountRequest request = new EditAccountRequest();
        request.setCustomerId(val.getLoginResponse().getData().getUserId());
        request.setFirstName(val.getLoginResponse().getData().getFirstName());
        request.setLastName(val.getLoginResponse().getData().getLastName());
        request.setPhoneNumber(val.getLoginResponse().getData().getPhoneNumber());
        request.setProfilePic(val.getLoginResponse().getData().getProfilePic());
        request.setDob(val.getLoginResponse().getData().getDob());
        request.setPost_code(sharedPreferencesClass.getPostalCode());


        Call<EditAccountResponse> call3 = apiInterface.mupdate(request);
        call3.enqueue(new Callback<EditAccountResponse>() {
            @Override
            public void onResponse(Call<EditAccountResponse> call, Response<EditAccountResponse> response) {

            }

            @Override
            public void onFailure(Call<EditAccountResponse> call, Throwable t) {

            }
        });
    }

    public void checkRestaurantDeliveryPostcode(String postalCode) {
        final String postcode = postalCode;
        RestaurantsDealsInterface apiInterface = ApiClient.getClient(this).create(RestaurantsDealsInterface.class);
        CheckDeliveryPostcodeRequest request = new CheckDeliveryPostcodeRequest();
        request.setPostCode(postalCode);

        Call<CheckDeliveryPostcodeResponse> call3 = apiInterface.getCheckDeliveryPostcode(request);
        call3.enqueue(new Callback<CheckDeliveryPostcodeResponse>() {
            @Override
            public void onResponse(Call<CheckDeliveryPostcodeResponse> call, Response<CheckDeliveryPostcodeResponse> response) {
                if (response.body().getSuccess()) {

                    if (response.body().getData().getIsDelivering() == 1) {
                        val.setPostCode(postcode);
                        if (sharedPreferencesClass.getPostalCode() == null || sharedPreferencesClass.getPostalCode().equals("")) {
                            sharedPreferencesClass.setPostalCode(postcode);

                            updateAccountDetail(); // Update Postal code

                            updatePostCodeOnServer(postcode);

                        } else {
                            dialog.hide();

                            sharedPreferencesClass.setPostalCode(postcode);

                            updateAccountDetail();
                            Intent i = new Intent(SearchPostCodeActivity.this, DashboardActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                            finish();
                        }

                    } else {
                        errorDialog("We currently are not delivering in your location", null);
                    }
                } else {
                    dialog.hide();
                    alertDialogNoRestaurant();
                }
            }

            @Override
            public void onFailure(Call<CheckDeliveryPostcodeResponse> call, Throwable t) {
                dialog.hide();
                alertDialogNoRestaurant();

            }
        });
    }

    private void updatePostCodeOnServer(String postalCode) {
        RestaurantsDealsInterface apiInterface = ApiClient.getClient(this).create(RestaurantsDealsInterface.class);
        UpdateCustomerPostcodeRequest request = new UpdateCustomerPostcodeRequest(sharedPreferencesClass.getString(sharedPreferencesClass.USER_ID), postalCode);

        Call<UpdateCustomerPostcodeResponse> call3 = apiInterface.updateCustomerPostcode(request);
        call3.enqueue(new Callback<UpdateCustomerPostcodeResponse>() {
            @Override
            public void onResponse(Call<UpdateCustomerPostcodeResponse> call, Response<UpdateCustomerPostcodeResponse> response) {
                if (response.body().getSuccess()) {

                    dialog.hide();

                    Intent i = new Intent(SearchPostCodeActivity.this, DashboardActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                    finish();
                } else {
                    dialog.hide();
                    alertDialogNoRestaurant();
                }
            }

            @Override
            public void onFailure(Call<UpdateCustomerPostcodeResponse> call, Throwable t) {
                dialog.hide();
                alertDialogNoRestaurant();
                Log.e("Error <>>>", ">>>>>" + t.getMessage());
            }
        });
    }

}
