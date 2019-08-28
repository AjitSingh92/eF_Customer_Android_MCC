package com.lexxdigital.easyfooduserapps.add_address;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.add_manual_address.AddAddressManualActivity;
import com.lexxdigital.easyfooduserapps.api.SearchPostalCodeAddressInterface;
import com.lexxdigital.easyfooduserapps.dialogs.PostalAddressListDialogFragment;
import com.lexxdigital.easyfooduserapps.model.add_model.AddressRequestModel;
import com.lexxdigital.easyfooduserapps.model.add_model.AddressResponseModel;
import com.lexxdigital.easyfooduserapps.model.postal_code_address.PostalCodeAddRes;
import com.lexxdigital.easyfooduserapps.model.postal_code_address.PostalCodeAddressReq;
import com.lexxdigital.easyfooduserapps.utility.ApiClient;
import com.lexxdigital.easyfooduserapps.utility.ApiClient2;
import com.lexxdigital.easyfooduserapps.utility.ApiInterface;
import com.lexxdigital.easyfooduserapps.utility.Constants;
import com.lexxdigital.easyfooduserapps.utility.GlobalValues;
import com.lexxdigital.easyfooduserapps.utility.PlaceArrayAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        OnMapReadyCallback {
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1212;
    @BindView(R.id.autoCompleteTextView)
    AutoCompleteTextView autoCompleteTextView;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.ch_defaultAddress)
    CheckBox checkBoxDeFaultAddress;
    TextView searchAddress, addManual, tvViewAddress;
    EditText searchByPostalCode;
    TextView labels[];
    ImageView icons[];
    Button addAddress;
    LatLng latLng;
    CoordinatorLayout coordinatorLayout;

    String addressShort = "";
    String address1 = "";
    String postalCode = "";
    String cityName = "";
    String countyName = "";
    String address2 = "";
    String strTtlAddress = "";

    private GlobalValues val;
    private Dialog dialog;

    private static final String LOG_TAG = "MainActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(28.689383, 77.104171), new LatLng(28.759834, 77.116351));
    private GoogleMap mMap;
    private String latitute = "";
    private String longitude = "";
    String locationTag = "home";
    private int defaultStatus = 0;
    private List<PostalCodeAddRes.Datum> postalAddres = new ArrayList<>();
    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        searchAddress = findViewById(R.id.search);
        searchByPostalCode = findViewById(R.id.search_by_postal);
        addManual = findViewById(R.id.addManual);
        addAddress = findViewById(R.id.addAddress);
        tvViewAddress = findViewById(R.id.tv_view_address);
        coordinatorLayout = findViewById(R.id.coordinatelayout);
        labels = new TextView[]{findViewById(R.id.hometxt),
                findViewById(R.id.worktxt),
                findViewById(R.id.othertxt)};

        icons = new ImageView[]{findViewById(R.id.homeicon),
                findViewById(R.id.workicon),
                findViewById(R.id.othericon)};


        ButterKnife.bind(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Constants.setStatusBarGradiant(AddAddressActivity.this);
        val = (GlobalValues) getApplicationContext();
        dialog = new Dialog(getApplicationContext());
        dialog.setTitle("");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        searchByPostalCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchPostCode = searchByPostalCode.getText().toString().trim();
                    if (!searchPostCode.trim().equalsIgnoreCase("")) {
                        performSearchPostal(searchPostCode);
                    } else {
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "Please enter postal code", Snackbar.LENGTH_LONG);
                        snackbar.show();

                    }

                    return true;
                }
                return false;
            }
        });

        setListeners();

        if (checkBoxDeFaultAddress.isChecked()) {
            defaultStatus = 1;
        } else {
            defaultStatus = 0;
        }


        checkBoxDeFaultAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxDeFaultAddress.isChecked()) {
                    defaultStatus = 1;
                } else {
                    defaultStatus = 0;
                }
            }
        });

    }

    private void performSearchPostal(String postCode) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait!");
        dialog.show();
        SearchPostalCodeAddressInterface apiInterface = ApiClient2.getClient2(this)
                .create(SearchPostalCodeAddressInterface.class);
        PostalCodeAddressReq request = new PostalCodeAddressReq();
        request.setPostCode(postCode);
        Call<PostalCodeAddRes> call = apiInterface.mLogin(request);
        call.enqueue(new Callback<PostalCodeAddRes>() {
            @Override
            public void onResponse(Call<PostalCodeAddRes> call, Response<PostalCodeAddRes> response) {
                try {
                    Log.e("AddressAdd:", "onResponse: " + response.code() + " ");
                    if (response.code() == 200) {
                        if (response.body().getSuccess()) {
                            dialog.dismiss();
                            postalAddres = response.body().getData();
                            if (postalAddres.size() > 0) {
                                openAddressDialogFragment();
                            } else {
                                dialog.dismiss();
                                Snackbar snackbar = Snackbar
                                        .make(coordinatorLayout, response.body().getMessage(), Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }


                        } else {
                            dialog.dismiss();
                            Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout, response.body().getMessage(), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    } else {
                        dialog.dismiss();
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, response.body().getMessage(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                        Log.e("", "onResponse: " + response.body().getMessage());
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "No Such post code found!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    Log.e("Address Exception", "onResponse: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<PostalCodeAddRes> call, Throwable t) {
                dialog.dismiss();
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "No Address Found!", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

    private void openAddressDialogFragment() {
        PostalAddressListDialogFragment fragment =
                PostalAddressListDialogFragment.newInstance(this, postalAddres, new PostalAddressListDialogFragment.OnFragmentInteractionListener() {
                    @Override
                    public void onFragmentInteraction(int position, List<PostalCodeAddRes.Datum> postalAddres, Boolean isItem) {
                        address1 = postalAddres.get(position).getLine1();
                        address2 = postalAddres.get(position).getLine2();
                        cityName = postalAddres.get(position).getPostTown();
                        postalCode = postalAddres.get(position).getPostcode();
                        countyName = postalAddres.get(position).getCountry();
                        strTtlAddress = address1 + " " + address2 + " " + cityName + " " + postalCode + " " + countyName;
                        tvViewAddress.setText(strTtlAddress);
                        tvViewAddress.setVisibility(View.VISIBLE);
                        searchAddress.setText("");
                        //Log.e("addAdress", "onFragmentInteraction county: " + postalAddres.get(position).getCounty() + " country//> " + postalAddres.get(position).getCountry());
                    }
                });
        fragment.show(getSupportFragmentManager(), "Address list");
        fragment.setCancelable(false);
    }

    private void setListeners() {
        addManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAddressActivity.this, AddAddressManualActivity.class);
                intent.putExtra("activity", "AddAddressActivity");
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                finish();
            }
        });

        icons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUi(0);
                locationTag = "Home";

            }
        });
        icons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUi(1);
                locationTag = "Work";

            }
        });
        icons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUi(2);
                locationTag = "Other";

            }
        });


        searchAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startlocationDialog();
            }
        });
        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (!TextUtils.isEmpty(searchAddress.getText().toString()))
                if (!strTtlAddress.trim().equalsIgnoreCase(""))
                    if (Constants.isInternetConnectionAvailable(300)) {
                        manageAddress();
                    } else {
                        dialogNoInternetConnection("Please check internet connection.");
                    }

                else
                    Toast.makeText(val, "Please select or add location first", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.e(LOG_TAG, "Google Places API connected.");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.");
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Birmingham lat lon beow--------------
        LatLng place = new LatLng(52.4773032, -1.898653);
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                .target(place)
                .zoom(8f)
                .build()));
        mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
        mMap.setOnMyLocationClickListener(onMyLocationClickListener);
        enableMyLocationIfPermitted();

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(11);


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                getSetAddress(latLng);
            }
        });

    }

    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    private void showDefaultLocation() {
        Toast.makeText(this, "Location permission not granted, " +
                        "showing default location",
                Toast.LENGTH_SHORT).show();
        LatLng redmond = new LatLng(47.6739881, -122.121512);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(redmond));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocationIfPermitted();
                } else {
                    showDefaultLocation();
                }
                return;
            }

        }
    }

    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
            new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    mMap.setMinZoomPreference(15);
                    Location myLocation = mMap.getMyLocation();  //Nullpointer exception.........

                    try {
                        LatLng myLatLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                        Log.e(">>>>>>>>", myLatLng.toString());
                        getSetAddress(myLatLng);
                    } catch (NullPointerException e) {
                        Log.e("NullPointerException", e.getLocalizedMessage());
                    }

                    return false;
                }
            };

    private GoogleMap.OnMyLocationClickListener onMyLocationClickListener =
            new GoogleMap.OnMyLocationClickListener() {
                @Override
                public void onMyLocationClick(@NonNull Location location) {

                    mMap.setMinZoomPreference(12);

                    CircleOptions circleOptions = new CircleOptions();
                    circleOptions.center(new LatLng(location.getLatitude(),
                            location.getLongitude()));

                    circleOptions.radius(200);
                    circleOptions.fillColor(Color.RED);
                    circleOptions.strokeWidth(6);

                    mMap.addCircle(circleOptions);
                    Log.e("location>>>>..", location.getExtras().toString());
                }
            };

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                latLng = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);

                if (place == null)
                    return;

                String regex = place.toString().replace("PlaceEntity", "");
                searchAddress.setText(place.getAddress());
                getSetAddress(latLng);


            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.i("tag", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
            }
        }

    }


    public void startlocationDialog() {
        Intent intent = null;
        try {
            intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(AddAddressActivity.this);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Log.e("Exception found >>>", ex.toString());
            ex.printStackTrace();
        }
        startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
    }


    private void updateUi(int position) {
        for (int i = 0; i < labels.length; i++) {
            if (position == i) {
                labels[i].setTextColor(getResources().getColor(R.color.orange));
                icons[i].setColorFilter(getResources().getColor(R.color.orange));
            } else {
                labels[i].setTextColor(getResources().getColor(R.color.tc1));
                icons[i].setColorFilter(getResources().getColor(R.color.tc1));
            }

        }

    }


    //TODO:  Methode to call an api.....
    public void manageAddress() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Saving your address");
        dialog.show();

        try {
            AddressRequestModel requestModel = new AddressRequestModel();
            requestModel.setAddress_1(address1);
            requestModel.setAddress_2(address2);
            requestModel.setAddress_type(locationTag);
            if (cityName != null && !cityName.equalsIgnoreCase("")) {
                requestModel.setCity(cityName);
            } else {
                requestModel.setCity("");
            }
            requestModel.setCountry(countyName);
            requestModel.setCustomerId(val.getLoginResponse().getData().getUserId());
            requestModel.setPost_code(postalCode);
            requestModel.setIs_default(defaultStatus);


            ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
            CompositeDisposable disposable = new CompositeDisposable();
            disposable.add(apiService.manageAddress(requestModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<AddressResponseModel>() {
                        @Override
                        public void onSuccess(AddressResponseModel data) {
                            dialog.dismiss();
                            if (data.isSuccess()) {
                                Toast.makeText(val, data.getMessage(), Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            dialog.dismiss();
                            Log.e("Exception ", e.getMessage());
                            Toast.makeText(val, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }));

        } catch (Exception e) {
            dialog.dismiss();
        }

    }

    private void getSetAddress(LatLng latLng) {
        Geocoder mGeocoder = new Geocoder(AddAddressActivity.this, Locale.getDefault());
        Address address = null;

        if (mGeocoder != null) {

            List<Address> addresses = null;
            try {
                addresses = mGeocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

                if (addresses != null && addresses.size() > 0) {


                    for (int i = 0; i < addresses.size(); i++) {
                        address = addresses.get(i);
                        if (address.getPostalCode() != null) {
                            Log.e("AddAddrees", "getSetAddress:address.toStr:>>>>> " + address.toString());
//                            addressShort = addresses.get(0).getAddressLine(0);
//                            addressShort = addressShort.replace(address.getLocality() + ", ", "").replace(addresses.get(0).getAdminArea(), "").replace(addresses.get(0).getPostalCode() + ", ", "").replace(addresses.get(0).getCountryName(), "");
                            Log.e("Address", addressShort);
                            cityName = address.getLocality();
                            address1 = addresses.get(0).getFeatureName() + "" + ((addresses.get(0).getSubLocality() != null) ? ", " + addresses.get(0).getSubLocality() : "") + "" + ((addresses.get(0).getThoroughfare() != null) ? ", " + addresses.get(0).getThoroughfare() : "");
                            address2 = addresses.get(0).getAddressLine(1);
                            countyName = addresses.get(0).getCountryName();
                            //searchAddress.setText(address.getAddressLine(i));
                            postalCode = addresses.get(0).getPostalCode();

                            MarkerOptions markerOptions = new MarkerOptions();
                            //TODO: Setting the position for the marker
                            markerOptions.position(latLng);
                            //Todo:  Setting the title for the marker.
                            //Todo: This will be displayed on taping the marker
                            markerOptions.title(address.getAddressLine(i));
                            //Todo: Clears the previously touched position
                            mMap.clear();

                            //Todo: Animating to the touched position
                            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                            //Todo: Placing a marker on the touched position
                            mMap.addMarker(markerOptions);

                            strTtlAddress = address1 + "" + ((cityName != null) ? ", " + cityName : "") + "" + ((postalCode != null) ? ", " + postalCode : "") + ", " + countyName;

                            Log.e("Postal code", "Postal code: " + address.getPostalCode()
                                    + " address1:" + address1 + "// city:" + cityName + "//postalCode:" + postalCode + "//countyName:" + countyName
                            );

                            searchAddress.setText(strTtlAddress);
                            //tvViewAddress.setText(strTtlAddress);
                            //tvViewAddress.setVisibility(View.VISIBLE);
                            break;
                        }

                    }
                }


            } catch (IOException e) {
                Toast.makeText(this, "Please make sure GPS setting", Toast.LENGTH_SHORT).show();
            }


        }
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dialog.dismiss();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dialog.dismiss();
    }

    public void dialogNoInternetConnection(String message) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.addnote_success_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setView(mDialogView);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);

        TextView tvMessage = mDialogView.findViewById(R.id.message);
        tvMessage.setText(message);
        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constants.isInternetConnectionAvailable(300)) {
                    alertDialog.dismiss();
                    manageAddress();

                } else mDialogView.findViewById(R.id.okTv).startAnimation(animShake);

            }
        });

        alertDialog.show();
    }
}
