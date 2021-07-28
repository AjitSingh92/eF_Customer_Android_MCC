package com.easyfoodcustomer.search_post_code;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;

import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easyfoodcustomer.databinding.LayoutDialogNorestaurantBinding;
import com.easyfoodcustomer.databinding.LayoutServestyleDialogBinding;
import com.easyfoodcustomer.order_details_activity.OrderDetailActivity;
import com.easyfoodcustomer.restaurant_details.RestaurantDetailsActivity;
import com.easyfoodcustomer.search_post_code.model.search_response.GuestTokenBean;
import com.easyfoodcustomer.utility.MarshmallowPermissions;
import com.easyfoodcustomer.utility.PrefManager;
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
import com.easyfoodcustomer.R;
import com.easyfoodcustomer.api.EditProfileInterface;
import com.easyfoodcustomer.api.RestaurantsDealsInterface;
import com.easyfoodcustomer.dashboard.DashboardActivity;
import com.easyfoodcustomer.model.UpdateCustomerPostcodeRequest;
import com.easyfoodcustomer.model.UpdateCustomerPostcodeResponse;
import com.easyfoodcustomer.model.edit_account_request.EditAccountRequest;
import com.easyfoodcustomer.model.edit_account_response.EditAccountResponse;
import com.easyfoodcustomer.model.landing_page_request.CheckDeliveryPostcodeRequest;
import com.easyfoodcustomer.model.landing_page_response.CheckDeliveryPostcodeResponse;
import com.easyfoodcustomer.search_post_code.api.SearchPostCodeInterface;
import com.easyfoodcustomer.search_post_code.model.search_request.SearchPostCodeRequest;
import com.easyfoodcustomer.search_post_code.model.search_response.SearchPostCodeResponse;
import com.easyfoodcustomer.services.AppLocationService;
import com.easyfoodcustomer.utility.ApiClient;
import com.easyfoodcustomer.utility.Constants;
import com.easyfoodcustomer.utility.GlobalValues;
import com.easyfoodcustomer.utility.LocationUtil.PermissionUtils;
import com.easyfoodcustomer.utility.SharedPreferencesClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.easyfoodcustomer.utility.Helper.isInternetOn;
import static com.easyfoodcustomer.utility.MarshmallowPermissions.CAMERA_PERMISSION_REQUEST_CODE;
import static com.easyfoodcustomer.utility.UserContants.AUTH_TOKEN;
import static com.easyfoodcustomer.utility.UserContants.IS_FROM_DEAL_PAGE;
import static com.easyfoodcustomer.utility.UserContants.IS_FROM_TABLE;
import static com.easyfoodcustomer.utility.UserContants.POST_CODE_NEW;

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

    @BindView(R.id.btn_order_to_your_tabel)
    RelativeLayout btnOrderToYourTabel;

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
    private MarshmallowPermissions marshmallowPermissions;

    double latitude;
    double longitude;
    private GlobalValues val;

    ArrayList<String> permissions = new ArrayList<>();
    PermissionUtils permissionUtils;

    boolean isPermissionGranted;
    private boolean doubleBackToExitPressedOnce = false;
    FirebaseAnalytics mFirebaseAnalytics;
    boolean isFromTrack = false;
    private String restaurantId, serverStyle, restaurentName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_post_code);
        getDataFromIntent();
        appLocationService = new AppLocationService(SearchPostCodeActivity.this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        permissionUtils = new PermissionUtils(SearchPostCodeActivity.this);
        marshmallowPermissions = new MarshmallowPermissions();
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);


        permissionUtils.check_permission(permissions, "Need GPS permission for getting your location", 1);

        if (checkPlayServices()) {
            buildGoogleApiClient();
        }

        ButterKnife.bind(this);

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
                    PrefManager.getInstance(SearchPostCodeActivity.this).savePreference(IS_FROM_DEAL_PAGE, false);
                    //val.setIsFromDealPage(false);
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
        if (PrefManager.getInstance(SearchPostCodeActivity.this).getPreference(IS_FROM_DEAL_PAGE, false)) {
            btnSearchCancel.setVisibility(View.VISIBLE);
        } else {
            btnSearchCancel.setVisibility(View.INVISIBLE);
        }

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        searchPostEt.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        if (PrefManager.getInstance(SearchPostCodeActivity.this).getPreference(AUTH_TOKEN, "") == null || PrefManager.getInstance(SearchPostCodeActivity.this).getPreference(AUTH_TOKEN, "").isEmpty()) {
            if (isInternetOn(SearchPostCodeActivity.this)) {
                dialog.show();
                getGuestToken();
            } else {
                Constants.showDialog(SearchPostCodeActivity.this, "Please check internet connection.");
            }
        }

    }

    private void getDataFromIntent() {
        isFromTrack = getIntent().getBooleanExtra("IS_FROM_TRACK", false);
        if (isFromTrack) {
            restaurantId = getIntent().getStringExtra("RESTAURANTID");
            restaurentName = getIntent().getStringExtra("RESTAURANTNAME");
            serverStyle = getIntent().getStringExtra("DELIVERY_OPTIONS");
            serveStylePopup(serverStyle);

        }

    }


    public void callSearchPostAPI(final String postcode, final Integer isFromTabelBooking) {
        SearchPostCodeInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(SearchPostCodeInterface.class);

        SearchPostCodeRequest request = new SearchPostCodeRequest();
        request.setPostCode(postcode);

        Call<SearchPostCodeResponse> call3 = apiInterface.mSearchPost(PrefManager.getInstance(SearchPostCodeActivity.this).getPreference(AUTH_TOKEN, ""), request);
        call3.enqueue(new Callback<SearchPostCodeResponse>() {
            @Override
            public void onResponse(Call<SearchPostCodeResponse> call, Response<SearchPostCodeResponse> response) {
                try {

                    if (response.body().getSuccess()) {
                        dialog.hide();
                        String pstcode = response.body().getData().getPostcode();
                        val.setPostCode(pstcode);
                        if (DashboardActivity.getInstance() != null)
                            DashboardActivity.getInstance().setLocation(postcode);

                        //checkRestaurantDeliveryPostcode(pstcode, isFromTabelBooking);
                        PrefManager.getInstance(SearchPostCodeActivity.this).savePreference(IS_FROM_TABLE, isFromTabelBooking);
                        sharedPreferencesClass.setInt(SharedPreferencesClass.IS_FOR_TABLE, isFromTabelBooking);
                        sharedPreferencesClass.setPostalCode(pstcode);
                        PrefManager.getInstance(SearchPostCodeActivity.this).savePreference(POST_CODE_NEW, postcode);
                        // updateAccountDetail();
                        Intent i = new Intent(SearchPostCodeActivity.this, DashboardActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                        finish();


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

    public void getGuestToken() {
        SearchPostCodeInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(SearchPostCodeInterface.class);


        Call<GuestTokenBean> call3 = apiInterface.getGuestToken();
        call3.enqueue(new Callback<GuestTokenBean>() {
            @Override
            public void onResponse(Call<GuestTokenBean> call, Response<GuestTokenBean> response) {
                try {

                    if (response.body().isSuccess()) {
                        dialog.hide();
                        // val.setAuthKey(response.body().getKey());
                        PrefManager.getInstance(SearchPostCodeActivity.this).savePreference(AUTH_TOKEN, response.body().getKey());


                    } else {
                        dialog.hide();
                        //errorDialog("Invalid Postcode", null);
                    }
                } catch (Exception e) {
                    dialog.hide();
                    //alertDialogNoRestaurant();
                }
            }

            @Override
            public void onFailure(Call<GuestTokenBean> call, Throwable t) {
                Log.e("Error <>>>", ">>>>>" + t.getMessage());
                dialog.hide();
                //alertDialogNoRestaurant();
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


    public void getAddress(final int isFromTabel) {

        Address locationAddress = getAddress(latitude, longitude);

        if (locationAddress != null) {
            String address = locationAddress.getAddressLine(0);
            String address1 = locationAddress.getAddressLine(1);
            String city = locationAddress.getLocality();
            String state = locationAddress.getAdminArea();
            String country = locationAddress.getCountryName();
            postalCode = locationAddress.getPostalCode();
            //postalCode = "NN141Aj";

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

                sharedPreferencesClass.setPostalCode(postalCode);
                val.setPostCode(postalCode);
                PrefManager.getInstance(SearchPostCodeActivity.this).savePreference(IS_FROM_TABLE, isFromTabel);
                sharedPreferencesClass.setInt(SharedPreferencesClass.IS_FOR_TABLE, isFromTabel);

                // dialog.hide();
                sharedPreferencesClass.setPostalCode(postalCode);

                // updateAccountDetail();

                if (isFromTabel == 1) {
                    PrefManager.getInstance(SearchPostCodeActivity.this).savePreference(IS_FROM_TABLE, 0);
                    if (marshmallowPermissions.checkCameraPermission(SearchPostCodeActivity.this)) {
                        Intent i = new Intent(SearchPostCodeActivity.this, RestaurantQrActivity.class);
                        //i.putExtra("POST_CODE", postalCode);
                        i.putExtra("POST_CODE", postalCode);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                        //finish();
                        //launchGooglePhotosPicker((Activity) context);
                        //startActivity(new Intent(SearchPostCodeActivity.this, RestaurantQrActivity.class));
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA},
                                    CAMERA_PERMISSION_REQUEST_CODE);
                        }
                    }
                } else {
                    PrefManager.getInstance(SearchPostCodeActivity.this).savePreference(IS_FROM_TABLE, 0);
                    Intent i = new Intent(SearchPostCodeActivity.this, DashboardActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                    finish();
                }
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

        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        startActivity(new Intent(SearchPostCodeActivity.this, RestaurantQrActivity.class));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return;
            }


        }


    }


    @Override
    public void PermissionGranted(int request_code) {
        isPermissionGranted = true;
        //getLocation();
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
            if (isInternetOn(SearchPostCodeActivity.this)) {
                dialog.show();
                callSearchPostAPI(searchPostEt.getText().toString(), 0);
            } else {
                Constants.showDialog(SearchPostCodeActivity.this, "Please check internet connection.");
            }
        }
    }

    @OnClick(R.id.btn_order_to_your_tabel)
    public void onViewClick() {
       /* if (searchPostEt.getText().toString().trim().equalsIgnoreCase("")) {
            showDialog("Please enter postcode.");
        } else {*/

        if (isInternetOn(SearchPostCodeActivity.this)) {

            if (checkPlayServices()) {
                // dialog.show();
                getLocation();

                if (mLastLocation != null) {
                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();
                    getAddress(1);

                } else {
                    Toast.makeText(SearchPostCodeActivity.this, "Please wait we are fetching your location", Toast.LENGTH_SHORT).show();
                    mLastLocation = appLocationService.getLocation(LocationManager.NETWORK_PROVIDER, SearchPostCodeActivity.this);
                    getLocation();
                    if (checkPlayServices()) {
                        buildGoogleApiClient();
                    }

                }
            }


            //callSearchPostAPI(searchPostEt.getText().toString(), 1);
        } else {
            Constants.showDialog(SearchPostCodeActivity.this, "Please check internet connection.");
        }


       /* if (marshmallowPermissions.checkCameraPermission(SearchPostCodeActivity.this)) {
            //launchGooglePhotosPicker((Activity) context);
            startActivity(new Intent(SearchPostCodeActivity.this, RestaurantQrActivity.class));
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        CAMERA_PERMISSION_REQUEST_CODE);
            }
        }*/




     /*   try {

            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

            startActivityForResult(intent, 0);

        } catch (Exception e) {

            Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
            Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
            startActivity(marketIntent);

        }*/
        //}
    }


    @Override
    public void onBackPressed() {

        if (PrefManager.getInstance(SearchPostCodeActivity.this).getPreference(IS_FROM_DEAL_PAGE, false)) {
            if (validPostcode) {
                PrefManager.getInstance(SearchPostCodeActivity.this).savePreference(IS_FROM_DEAL_PAGE, false);
                //val.setIsFromDealPage(false);
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
                PrefManager.getInstance(SearchPostCodeActivity.this).savePreference(IS_FROM_DEAL_PAGE, false);
                //val.setIsFromDealPage(false);
                startActivity(new Intent(SearchPostCodeActivity.this, DashboardActivity.class));
                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                break;
            case R.id.searchicon:
                if (checkPlayServices()) {
                    getLocation();
                    if (mLastLocation != null) {
                        latitude = mLastLocation.getLatitude();
                        longitude = mLastLocation.getLongitude();
                        getAddress(0);

                    } else {
                        mLastLocation = appLocationService.getLocation(LocationManager.NETWORK_PROVIDER, SearchPostCodeActivity.this);

                        if (checkPlayServices()) {
                            buildGoogleApiClient();
                        }

                    }
                }
                break;
        }
    }

/*    public void alertDialogNoRestaurant() {
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
    }*/

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


        Call<EditAccountResponse> call3 = apiInterface.mupdate(PrefManager.getInstance(SearchPostCodeActivity.this).getPreference(AUTH_TOKEN, ""), request);
        call3.enqueue(new Callback<EditAccountResponse>() {
            @Override
            public void onResponse(Call<EditAccountResponse> call, Response<EditAccountResponse> response) {

            }

            @Override
            public void onFailure(Call<EditAccountResponse> call, Throwable t) {

            }
        });
    }

    /*public void checkRestaurantDeliveryPostcode(String postalCode, final Integer isFromTabel) {
        final String postcode = postalCode;
        RestaurantsDealsInterface apiInterface = ApiClient.getClient(this).create(RestaurantsDealsInterface.class);
        CheckDeliveryPostcodeRequest request = new CheckDeliveryPostcodeRequest();
        request.setPostCode(postalCode);

        Call<CheckDeliveryPostcodeResponse> call3 = apiInterface.getCheckDeliveryPostcode(PrefManager.getInstance(SearchPostCodeActivity.this).getPreference(AUTH_TOKEN, ""), request);
        call3.enqueue(new Callback<CheckDeliveryPostcodeResponse>() {
            @Override
            public void onResponse(Call<CheckDeliveryPostcodeResponse> call, Response<CheckDeliveryPostcodeResponse> response) {
                if (response.body().getSuccess()) {
                    dialog.hide();
                    if (response.body().getData().getIsDelivering() == 1) {
                        val.setPostCode(postcode);
                        PrefManager.getInstance(SearchPostCodeActivity.this).savePreference(IS_FROM_TABLE, isFromTabel);
                        sharedPreferencesClass.setInt(SharedPreferencesClass.IS_FOR_TABLE, isFromTabel);

                        if (sharedPreferencesClass.getPostalCode() == null || sharedPreferencesClass.getPostalCode().equals("")) {
                            sharedPreferencesClass.setPostalCode(postcode);

                            //updateAccountDetail(); // Update Postal code

                            updatePostCodeOnServer(postcode);

                        } else {
                            dialog.hide();

                            sharedPreferencesClass.setPostalCode(postcode);

                            // updateAccountDetail();
                            Intent i = new Intent(SearchPostCodeActivity.this, DashboardActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                            finish();
                        }

                    } else {
                        alertDialogNoRestaurant();

                        //errorDialog(getResources().getString(R.string.sorry_we_don_t_currently_deliver_in_your_area), getResources().getString(R.string.we_are_expanding_fast));
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
    }*/

   /* private void updatePostCodeOnServer(String postalCode) {
        RestaurantsDealsInterface apiInterface = ApiClient.getClient(this).create(RestaurantsDealsInterface.class);
        UpdateCustomerPostcodeRequest request = new UpdateCustomerPostcodeRequest(sharedPreferencesClass.getString(sharedPreferencesClass.USER_ID), postalCode);

        Call<UpdateCustomerPostcodeResponse> call3 = apiInterface.updateCustomerPostcode(PrefManager.getInstance(SearchPostCodeActivity.this).getPreference(AUTH_TOKEN, ""), request);
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
    }*/


    public void alertDialogNoRestaurant() {
        View dialogView = LayoutInflater.from(SearchPostCodeActivity.this).inflate(R.layout.layout_dialog_norestaurant, null);
        LayoutDialogNorestaurantBinding dialogBinding = DataBindingUtil.bind(dialogView);
        final Dialog dialog = new Dialog(SearchPostCodeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogBinding.getRoot());
        dialog.setCancelable(false);


        dialogBinding.tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                validPostcode = false;
            }
        });

        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        if (dialog.getWindow() != null) {
            dialog.getWindow().setAttributes(lp);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(SearchPostCodeActivity.this, R.color.seme_transparent)));
            // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.seme_transparent)));
        }
    }


    public void serveStylePopup(String deliverOption) {
        View dialogView = LayoutInflater.from(SearchPostCodeActivity.this).inflate(R.layout.layout_servestyle_dialog, null);
        LayoutServestyleDialogBinding dialogBinding = DataBindingUtil.bind(dialogView);
        final Dialog dialog = new Dialog(SearchPostCodeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogBinding.getRoot());
        dialog.setCancelable(false);
        String[] serve_styles = deliverOption.split(",");

        if (Arrays.asList(serve_styles).contains("collection")) {
            dialogBinding.tvCollection.setVisibility(View.VISIBLE);
        } else {
            dialogBinding.tvCollection.setVisibility(View.GONE);
        }
        if (Arrays.asList(serve_styles).contains("delivery")) {
            dialogBinding.tvDelivery.setVisibility(View.VISIBLE);

        } else {
            dialogBinding.tvDelivery.setVisibility(View.GONE);
        }
        if (Arrays.asList(serve_styles).contains("dine_in")) {
            dialogBinding.tvDineIn.setVisibility(View.VISIBLE);
        } else {
            dialogBinding.tvDineIn.setVisibility(View.GONE);
        }


        dialogBinding.tvDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                PrefManager.getInstance(SearchPostCodeActivity.this).savePreference(IS_FROM_TABLE, 0);
                sharedPreferencesClass.setInt(SharedPreferencesClass.IS_FOR_TABLE, 0);
                Intent intent = new Intent(SearchPostCodeActivity.this, RestaurantDetailsActivity.class);
                intent.putExtra("IS_FROM_LOGIN", false);
                intent.putExtra("RESTAURANTID", restaurantId);
                intent.putExtra("ServeStyle", "delivery");
                startActivity(intent);

            }
        });

        dialogBinding.tvCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                PrefManager.getInstance(SearchPostCodeActivity.this).savePreference(IS_FROM_TABLE, 0);
                sharedPreferencesClass.setInt(SharedPreferencesClass.IS_FOR_TABLE, 0);
                Intent intent = new Intent(SearchPostCodeActivity.this, RestaurantDetailsActivity.class);
                intent.putExtra("IS_FROM_LOGIN", false);
                intent.putExtra("RESTAURANTID", restaurantId);
                intent.putExtra("ServeStyle", "collection");

                startActivity(intent);
            }
        });
        dialogBinding.tvDineIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                PrefManager.getInstance(SearchPostCodeActivity.this).savePreference(IS_FROM_TABLE, 1);
                sharedPreferencesClass.setInt(SharedPreferencesClass.IS_FOR_TABLE, 1);
                Intent intent = new Intent(SearchPostCodeActivity.this, RestaurantDetailsActivity.class);
                intent.putExtra("IS_FROM_LOGIN", false);
                intent.putExtra("RESTAURANTID", restaurantId);
                intent.putExtra("ServeStyle", "dine_in");
                startActivity(intent);
            }
        });

        dialogBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(SearchPostCodeActivity.this, R.color.seme_transparent)));
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.seme_transparent)));
    }

}
