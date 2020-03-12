package com.lexxdigital.easyfooduserapps.add_manual_address;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.add_address.AddAddressActivity;
import com.lexxdigital.easyfooduserapps.api.SearchPostalCodeAddressInterface;
import com.lexxdigital.easyfooduserapps.model.AddressList;
import com.lexxdigital.easyfooduserapps.model.add_model.AddressRequestModel;
import com.lexxdigital.easyfooduserapps.model.add_model.AddressResponseModel;
import com.lexxdigital.easyfooduserapps.model.add_model.EditAddressRequest;
import com.lexxdigital.easyfooduserapps.model.postal_code_address.PostalCodeAddRes;
import com.lexxdigital.easyfooduserapps.model.postal_code_address.PostalCodeAddressReq;
import com.lexxdigital.easyfooduserapps.search_post_code.api.SearchPostCodeInterface;
import com.lexxdigital.easyfooduserapps.search_post_code.model.search_request.SearchPostCodeRequest;
import com.lexxdigital.easyfooduserapps.search_post_code.model.search_response.SearchPostCodeResponse;
import com.lexxdigital.easyfooduserapps.utility.ApiClient;
import com.lexxdigital.easyfooduserapps.utility.ApiClient2;
import com.lexxdigital.easyfooduserapps.utility.ApiInterface;
import com.lexxdigital.easyfooduserapps.utility.Constants;
import com.lexxdigital.easyfooduserapps.utility.GlobalValues;

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

public class AddAddressManualActivity extends AppCompatActivity {
    TextView labels[];
    ImageView icons[];
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.ch_defaultAddress)
    CheckBox checkBoxDeFaultAddress;

    private String locationTag = "Home";
    EditText address1, address2, cityName, postalCode, countyName;
    Button btn_add_address;
    private GlobalValues val;
    AddressList addressDetails;
    boolean isEditable;
    String fromActivity = "";
    int intaddType = 0;
    private int defaultStatus = 0;
    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Constants.setStatusBarGradiant(this);
        super.onCreate(savedInstanceState);

        val = (GlobalValues) getApplicationContext();
        setContentView(R.layout.activity_add_address_manual);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        ButterKnife.bind(this);
        address1 = findViewById(R.id.address1);
        address2 = findViewById(R.id.address2);
        cityName = findViewById(R.id.cityName);
        postalCode = findViewById(R.id.postalCode);
        countyName = findViewById(R.id.countryName);
        btn_add_address = findViewById(R.id.btn_add_address);
        String id, editAddress;
        isEditable = getIntent().hasExtra(Constants.EDIT_ADDRESS);
        fromActivity = getIntent().getStringExtra("activity");
        Log.e("add address", "onCreate:  boolean edit: " + isEditable);
        labels = new TextView[]{
                findViewById(R.id.hometxt),
                findViewById(R.id.worktxt),
                findViewById(R.id.othertxt)};

        icons = new ImageView[]{
                findViewById(R.id.homeicon),
                findViewById(R.id.workicon),
                findViewById(R.id.othericon)};
        if (isEditable) {
            addressDetails = (AddressList) getIntent().getSerializableExtra(Constants.ADDRESS);
            btn_add_address.setText("Update Address");
            address1.setText(addressDetails.getAddressOne());
            address2.setText(addressDetails.getAddressTwo());
            cityName.setText(addressDetails.getCity());
            postalCode.setText(addressDetails.getPostCode());
            countyName.setText(addressDetails.getCountry());
            String addressType = addressDetails.getAddressType();
            defaultStatus = addressDetails.getIsDefault();

            if (addressType.equals("home") || addressType.equals("Home")) {
                intaddType = 0;
                locationTag = "Home";
            } else if (addressType.equals("work") || addressType.equals("Work")) {
                intaddType = 1;
                locationTag = "Work";
            } else {
                intaddType = 2;
                locationTag = "Other";
            }

            if (defaultStatus == 1) {
                checkBoxDeFaultAddress.setChecked(true);
            } else {
                checkBoxDeFaultAddress.setChecked(false);
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

            updateUi(intaddType);
        }


        setOnClickListeners();
    }

    private void setOnClickListeners() {
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


        btn_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    performSearchPostal(postalCode.getText().toString().trim());
                }
            }
        });
    }

    private boolean isValid() {
        boolean isValid = true;
        if (TextUtils.isEmpty(address1.getText().toString().trim())) {
            address1.setError("Enter address");
            address1.requestFocus();
            isValid = false;

        } else if (TextUtils.isEmpty(cityName.getText().toString().trim())) {
            cityName.setError("Enter city name");
            cityName.requestFocus();
            isValid = false;
        } else if (TextUtils.isEmpty(postalCode.getText().toString().trim())) {
            postalCode.setError("Enter post code");
            postalCode.requestFocus();
            isValid = false;
        } else if (TextUtils.isEmpty(countyName.getText().toString().trim())) {
            countyName.setError("Enter country name");
            countyName.requestFocus();
            isValid = false;
        }

        return isValid;
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

    @OnClick(R.id.back)
    public void onViewClicked() {
        if (fromActivity != null && fromActivity.equalsIgnoreCase("ManageAddressFragment")) {
            finish();
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        } else if (fromActivity != null && fromActivity.equalsIgnoreCase("AddAddressActivity")) {
            startActivity(new Intent(AddAddressManualActivity.this, AddAddressActivity.class));
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            finish();
        } else {
            finish();
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        }

    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        // overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        if (fromActivity != null && fromActivity.equalsIgnoreCase("ManageAddressFragment")) {
            finish();
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        } else if (fromActivity != null && fromActivity.equalsIgnoreCase("AddAddressActivity")) {
            startActivity(new Intent(AddAddressManualActivity.this, AddAddressActivity.class));
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            finish();
        } else {
            finish();
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        }
    }

    //TODO:  Methode to call an api.....
    public void manageAddress() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Saving your address");
        dialog.show();

        try {
            AddressRequestModel requestModel = new AddressRequestModel();
            requestModel.setAddress_1(address1.getText().toString());
            requestModel.setAddress_2(address2.getText().toString());
            requestModel.setAddress_type(locationTag);
            requestModel.setCity(cityName.getText().toString());
            requestModel.setCountry(countyName.getText().toString());
            requestModel.setCustomerId(val.getLoginResponse().getData().getUserId());
            requestModel.setPost_code(postalCode.getText().toString());
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
                                Toast toast = Toast.makeText(val, data.getMessage(), Toast.LENGTH_SHORT);
                                // toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                                toast.show();

                                setResult(RESULT_OK);
//                                Intent i = new Intent(AddAddressManualActivity.this, ManageAddressActivity.class);
//                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(i);
//                                finish();
                                finish();
                                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
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

    public void editmanageAddress() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Saving your address");
        dialog.show();

        try {
            EditAddressRequest requestModel = new EditAddressRequest();
            requestModel.setAddress1(address1.getText().toString());
            requestModel.setAddress2(address2.getText().toString());
            requestModel.setAddressType(locationTag);
            requestModel.setCity(cityName.getText().toString());
            requestModel.setCountry(countyName.getText().toString());
            requestModel.setId(addressDetails.getID());
            requestModel.setPostCode(postalCode.getText().toString());
            requestModel.setIsDefault(defaultStatus);
            Log.e("addressManual", "editmanageAddress defaultStatus>>: " + defaultStatus);

            ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
            CompositeDisposable disposable = new CompositeDisposable();
            disposable.add(apiService.editAddress(requestModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<AddressResponseModel>() {
                        @Override
                        public void onSuccess(AddressResponseModel data) {
                            dialog.dismiss();
                            if (data.isSuccess()) {
                                Toast toast = Toast.makeText(val, data.getMessage(), Toast.LENGTH_SHORT);
                                // toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                                toast.show();

                                setResult(RESULT_OK);
//                                Intent i = new Intent(AddAddressManualActivity.this, ManageAddressActivity.class);
//                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(i);
//                                finish();
                                finish();
                                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
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

    public void showDialog(String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddAddressManualActivity.this);
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


    public void callSearchPostAPI(String postcode) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Check Postal Code");
        dialog.show();
        SearchPostCodeInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(SearchPostCodeInterface.class);

        SearchPostCodeRequest request = new SearchPostCodeRequest();
        request.setPostCode(postcode);

        Call<SearchPostCodeResponse> call3 = apiInterface.mSearchPost(request);
        call3.enqueue(new Callback<SearchPostCodeResponse>() {
            @Override
            public void onResponse(Call<SearchPostCodeResponse> call, Response<SearchPostCodeResponse> response) {
                try {

                    if (response.body().getSuccess()) {
                        // String pstcode = response.body().getData().getPostcode();
                        dialog.dismiss();
                        if (isEditable) {
                            editmanageAddress();
                        } else
                            manageAddress();
                    } else {
                        dialog.dismiss();
                        showDialog("We are not here yet, Please change post code");
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                    showDialog("We are not here yet, Please change post code");
                    Log.e("Error <>>>", ">>>>>" + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<SearchPostCodeResponse> call, Throwable t) {
                Log.e("Error <>>>", ">>>>>" + t.getMessage());
                dialog.dismiss();
                showDialog("We are not here yet, Please change post code");

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

                            if (response.body().getData().size() > 0) {

                                if (!cityName.getText().toString().trim().equalsIgnoreCase(response.body().getData().get(0).getPostTown())) {
                                    cityName.setText(Constants.capitalize(response.body().getData().get(0).getPostTown()));
                                }
                                if (!countyName.getText().toString().trim().equalsIgnoreCase(response.body().getData().get(0).getCountry())) {
                                    countyName.setText(Constants.capitalize(response.body().getData().get(0).getCountry()));
                                } else {

                                    if (isEditable) {
                                        editmanageAddress();
                                    } else
                                        manageAddress();
                                }

                            } else {
                                dialog.dismiss();
                                showDialog("We are not here yet, Please change postcode");

                            }


                        } else {
                            dialog.dismiss();
                            showDialog("We are not here yet, Please change postcode");

                        }
                    } else {
                        dialog.dismiss();
                        showDialog("We are not here yet, Please change postcode");

                    }
                } catch (Exception e) {
                    dialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<PostalCodeAddRes> call, Throwable t) {
                dialog.dismiss();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
