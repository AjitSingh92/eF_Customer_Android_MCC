package com.lexxdigital.easyfooduserapps.add_card;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.api.AddCardInterface;
import com.lexxdigital.easyfooduserapps.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapps.dialogs.AddressDialogFragment;
import com.lexxdigital.easyfooduserapps.dialogs.BillingAddressDialogFragment;
import com.lexxdigital.easyfooduserapps.model.add_card_request.CardAddRequest;
import com.lexxdigital.easyfooduserapps.model.add_card_response.CardAddResponse;
import com.lexxdigital.easyfooduserapps.order_status.OrderStatusActivity;
import com.lexxdigital.easyfooduserapps.restaurant_details.RestaurantDetailsActivity;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.CartDatRequest;
import com.lexxdigital.easyfooduserapps.select_payment_method.api.CheckoutRequestInterface;
import com.lexxdigital.easyfooduserapps.select_payment_method.model.checkout_request.CheckoutRequest;
import com.lexxdigital.easyfooduserapps.select_payment_method.model.checkout_response.CheckoutResponse;
import com.lexxdigital.easyfooduserapps.utility.ApiClient;
import com.lexxdigital.easyfooduserapps.utility.Constants;
import com.lexxdigital.easyfooduserapps.utility.GlobalValues;
import com.lexxdigital.easyfooduserapps.utility.SharedPreferencesClass;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewCardActivity extends AppCompatActivity {

    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.menuId)
    ImageView menuId;
    @BindView(R.id.toolbarhide)
    RelativeLayout toolbarhide;
    @BindView(R.id.top)
    LinearLayout top;
    @BindView(R.id.nameId)
    TextView nameId;
    @BindView(R.id.cardholder_name)
    EditText cardholderName;
    @BindView(R.id.card_number)
    EditText cardNumber;
    @BindView(R.id.ccv_et)
    EditText ccvEt;
    @BindView(R.id.expire_date)
    EditText expireDate;
    @BindView(R.id.expire_year)
    EditText expireYear;
    @BindView(R.id.lll)
    LinearLayout lll;
    @BindView(R.id.billing_post)
    EditText billingPost;
    @BindView(R.id.conformTv)
    TextView conformTv;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.cardNo)
    TextView cardNo;
    @BindView(R.id.holdernametv)
    TextView holdernametv;
    @BindView(R.id.exp_date)
    TextView expDate;
    @BindView(R.id.fm)
    FrameLayout fm;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.store_card)
    CheckBox storeCard;
    @BindView(R.id.cvv_no)
    EditText cvvNo;
    private GlobalValues val;
    private Dialog dialog;
    SharedPreferencesClass sharedPreferencesClass;
    private Card card;
    private ProgressDialog mProgressDialog;

    private Double totalAmount = 0.0d;
    private Double subTotalAmount = 0.0d;
    private Double deliveryFee = 0.0d;
    private String orderType;
    private Double voucherDiscount = 0.0d;
    private String notes;
    private DatabaseHelper db;
    private String voucherCode;
    private Double voucherAmount = 0.0d;
    private String voucherPaymentType;
    private String address1 = "", address2 = "", city = "", postalcode = "", country = "";
    String isSaveCard = "off";
    String billingAddress = "";
    boolean isFromCheckout = false;
    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_card);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        ButterKnife.bind(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Constants.setStatusBarGradiant(AddNewCardActivity.this);
        sharedPreferencesClass = new SharedPreferencesClass(getApplicationContext());
        db = new DatabaseHelper(this);
        val = (GlobalValues) getApplicationContext();
        dialog = new Dialog(AddNewCardActivity.this);
        dialog.setTitle("");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Bundle extras = getIntent().getExtras();
        Log.e("EXTRA>>>", "//" + extras);
        if (extras != null) {
            totalAmount = extras.getDouble("ORDER_TOTAL");
            subTotalAmount = extras.getDouble("ORDER_SUB_TOTAL");
            deliveryFee = extras.getDouble("deliveryCharge");
            orderType = extras.getString("orderType");
            voucherDiscount = extras.getDouble("voucherDiscount");
            notes = extras.getString("notes");
            voucherCode = extras.getString("appliedVoucherCode");
            voucherAmount = extras.getDouble("appliedVoucherAmount");
            voucherPaymentType = extras.getString("appliedVoucherPaymentType");

            storeCard.setVisibility(View.VISIBLE);
            isFromCheckout = true;
        } else {
            storeCard.setVisibility(View.GONE);
            isFromCheckout = false;
        }
        billingPost.setLongClickable(false);

        storeCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    isSaveCard = "on";
                } else {
                    isSaveCard = "off";
                }
            }
        });

        cardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() == 19) {
                    expireDate.requestFocus();
                }
                // TODO Auto-generated method stub
            }
        });

        expireDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().trim().equalsIgnoreCase("")) {
                    int value = Integer.parseInt(s.toString().trim());
                    if (value >= 1 && value <= 12) {
                        if (s.length() == 2) {
                            expireYear.requestFocus();
                        }
                    } else {
                        expireDate.requestFocus();
                        expireDate.setError("Please enter valid expiry month(1 to 12)");

                    }
                }
                // TODO Auto-generated method stub
            }
        });

        expireYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() == 4) {
                    cvvNo.requestFocus();
                }
                // TODO Auto-generated method stub
            }
        });

        cvvNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 3) {
                    billingPost.requestFocus();
                }
                // TODO Auto-generated method stub
            }
        });
    }

    //
    public void saveCardDetail(String token) {
        AddCardInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(AddCardInterface.class);
        CardAddRequest request = new CardAddRequest();
        request.setCustomerId(val.getLoginResponse().getData().getUserId());
        request.setStripeToken(token);
        request.setLast4CardDigit(cardNumber.getText().toString().trim().substring(cardNumber.length() - 4));

        Call<CardAddResponse> call3 = apiInterface.mLogin(request);
        call3.enqueue(new Callback<CardAddResponse>() {
            @Override
            public void onResponse(Call<CardAddResponse> call, Response<CardAddResponse> response) {
                try {
                    dialog.hide();
                    if (response.body().getSuccess()) {
                        //showDialog("Card added successfully.");
                        Toast.makeText(val, "Card added successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                    } else {
                        alertDialogOrderPlaced(response.body().getMessage());
                    }
                } catch (Exception e) {
                    dialog.hide();
                    alertDialogOrderPlaced("Failed to add new card. Please try again.");
                    Log.e("Error11 <>>>", ">>>>>" + e.getMessage());
                    //    showDialog("Please try again.");
//                       Toast.makeText(LoginActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CardAddResponse> call, Throwable t) {
                Log.e("Error12 <>>>", ">>>>>" + t.getMessage());
                alertDialogOrderPlaced("Failed to add new card. Please try again.");
//                dialog.hide();
//                showDialog("Please try again.");
                //    Toast.makeText(LoginActivity.this, "Please try again 2."+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void showDialog(String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
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

    public void alertDialogOrderPlaced(final String msg) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.popoup_order_fail_success, null);
        final AlertDialog cardDialog = new AlertDialog.Builder(this).create();
        TextView txtMsg = mDialogView.findViewById(R.id.txt_msg);
        txtMsg.setText(msg);
        cardDialog.setView(mDialogView);
        cardDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                cardDialog.dismiss();
            }
        });
        cardDialog.show();
    }


    @OnClick({R.id.back, R.id.conformTv, R.id.billing_post})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                Constants.back(AddNewCardActivity.this);
                break;
            case R.id.billing_post:
                //getBillingAddress();
                getBillingAddrs();
                break;
            case R.id.conformTv:
                String strExpDate = expireDate.getText().toString().trim();
                int intExpDate = 0;
                if (!strExpDate.equalsIgnoreCase("")) {
                    intExpDate = Integer.valueOf(strExpDate);
                }
                if (cardholderName.getText().toString().trim().length() <= 0) {
                    cardholderName.requestFocus();
                    cardholderName.setError("Please enter card holder name");
                    //showDialog("Please enter card holder name.");
                } else if (cardNumber.getText().toString().trim().length() < 12) {
                    cardNumber.setError("Please enter valid card number");
                    cardNumber.requestFocus();
                    //showDialog("Please enter 16 digit card number.");

                } else if (strExpDate.length() <= 0) {
                    expireDate.requestFocus();
                    expireDate.setError("Please enter card expiry month");
                    //showDialog("Please enter card expiry month.");
                } else if (intExpDate > 12 || intExpDate < 1) {
                    expireDate.requestFocus();
                    expireDate.setError("Please enter valid expiry month");
                    //showDialog("Please enter card expiry month.");
                } else if (expireYear.getText().toString().trim().length() < 4) {
                    expireYear.setError("Please enter card expiry year");
                    expireYear.requestFocus();
                    //showDialog("Please enter card expiry year.");
                } else if (cvvNo.getText().toString().trim().length() < 3) {
                    cvvNo.setError("Please enter cvc/cvv number");
                    cvvNo.requestFocus();
                    //showDialog("Please enter card expiry year.");
                } else if (billingAddress.equalsIgnoreCase("")) {
                    Toast.makeText(this, "Please enter billing address post code", Toast.LENGTH_SHORT).show();
                    // billingPost.setError("Please enter billing address post code");
                    billingPost.requestFocus();
                    return;
                    //showDialog("Please enter billing address post code.");
                } else {
//                    dialog.show();
//                    saveCardDetail();

                    paymentStart(cardNumber.getText().toString().trim(), Integer.parseInt(expireDate.getText().toString().trim()), Integer.parseInt(expireYear.getText().toString().trim()), cvvNo.getText().toString().trim());
                }
                break;
        }
    }

    void getBillingAddress() {

        AddressDialogFragment addressDialogFragment = AddressDialogFragment.newInstance(this, true, new AddressDialogFragment.OnAddressDialogListener() {
            @Override
            public void onAddressDialogDismiss(Boolean isItem) {
                if (isItem) {
                    billingAddress = sharedPreferencesClass.getString(sharedPreferencesClass.DEFAULT_ADDRESS);
                    billingPost.setText(billingAddress);

                }
            }
        });
        addressDialogFragment.show(getSupportFragmentManager(), "addressDialog");
        addressDialogFragment.setCancelable(false);
    }

    void getBillingAddrs() {
        BillingAddressDialogFragment billingAddressDialogFragment =
                BillingAddressDialogFragment.newInstance(this, true, new BillingAddressDialogFragment.OnAddressDialogListener() {
                    @Override
                    public void onAddressDialogDismiss(Boolean isItem) {
                        address1 = val.getAddress1();
                        address2 = val.getAddress2();
                        city = val.getCity();
                        postalcode = val.getPostalCode();


                        if (address2 != null && address2.trim().length() > 0) {

                            billingAddress = address1 + ", " + address2 + ", " + city + ", " + postalcode;
                        } else {
                            billingAddress = address1 + ", " + city + ", " + postalcode;
                        }

                        if (address1 != null && address1.trim().length() > 0) {
                            billingPost.setText(billingAddress);
                        } else {
                            billingAddress = "";
                            billingPost.setText(billingAddress);
                        }

                        // billingPost.setText(sharedPreferencesClass.getString(sharedPreferencesClass.BILLING_ADDRESS));
                    }
                });
        billingAddressDialogFragment.show(getSupportFragmentManager(), "Billing Address");
        billingAddressDialogFragment.setCancelable(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        // Toast.makeText(val, "", Toast.LENGTH_SHORT).show();
    }

    public void paymentStart(String cardNumber, int cardExpMonth, int cardExpYear,
                             String cardCVC) {
        Log.e("AdddNewCard", "paymentStart: " + address1 + "//>" + address2 + "//>" + city + "//>" + postalcode);
        card = new Card(
                cardNumber,
                cardExpMonth,
                cardExpYear,
                cardCVC,
                cardholderName.getText().toString().trim(),
                address1,
                address2,
                city,
                null,
                postalcode,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null

        );

        if (!card.validateCard()) {
            //Toast.makeText(AddNewCardActivity.this, "Enter valid card number", Toast.LENGTH_SHORT).show();
            showDialog("Enter valid card number");
        } else if (!card.validateExpiryDate()) {
            // Toast.makeText(AddNewCardActivity.this, "Enter valid expiry date", Toast.LENGTH_SHORT).show();
            showDialog("Enter valid expiry date");
        } else if (!card.validateExpMonth()) {
            // Toast.makeText(AddNewCardActivity.this, "Enter valid expiry month", Toast.LENGTH_SHORT).show();
            showDialog("Enter valid expiry date");
        } else if (!card.validateCVC()) {
            // Toast.makeText(AddNewCardActivity.this, "Enter valid cvv/cvc number", Toast.LENGTH_SHORT).show();
            showDialog("Enter valid cvv/cvc number");
        } else {
            mProgressDialog = new ProgressDialog(AddNewCardActivity.this);
            mProgressDialog.requestWindowFeature(1);
            mProgressDialog.setMessage("Please wait");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
            paymentProcess();
        }
    }

    public void paymentProcess() {


        Stripe stripe = new Stripe(getApplicationContext(), Constants.STRIPE_PUBLISH_KEY);
        stripe.createToken(
                card,
                new TokenCallback() {
                    public void onSuccess(Token token) {
                        // Send token to your server
//                        Toast.makeText(getApplicationContext(),
//                                "Success",
//                                Toast.LENGTH_LONG
//                        ).show();
                        // Log.e("TOKEN", "" + token.getCard().getCVC());
                        if (isFromCheckout)
                            callAPI(token.getId(), "card");
                        else
                            saveCardDetail(token.getId());
                        mProgressDialog.dismiss();
                    }

                    public void onError(Exception error) {
                        Log.e("Error", "" + error);
                        // Show localized error message
                        mProgressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Please try again!", Toast.LENGTH_LONG).show();

                    }
                }
        );


    }

    private CartDatRequest makeData() {
        CartDatRequest cartDatRequest = new CartDatRequest();
        try {

            cartDatRequest.setCartData(db.getCartData());
            cartDatRequest.setRestaurantId(val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantId());
            cartDatRequest.setRestaurantName(val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantName());
            cartDatRequest.setPostCode(val.getRestaurantDetailsResponse().getData().getRestaurants().getPostCode());
            cartDatRequest.setTotalCartPrice(totalAmount);
            cartDatRequest.setOrderType(orderType.toLowerCase());
            cartDatRequest.setDeliveryCharge(deliveryFee);
            cartDatRequest.setMaxLength(String.valueOf(Constants.MAX_LENGTH));
            cartDatRequest.setVoucherDiscount(voucherDiscount);
            cartDatRequest.setVoucherCode(voucherCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartDatRequest;
    }

    public void callAPI(String token, String paymentType) {
        CheckoutRequestInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(CheckoutRequestInterface.class);


        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        CheckoutRequest request = new CheckoutRequest();
        request.setRestaurantId(val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantId());
        request.setCustomerId(val.getLoginResponse().getData().getUserId());
        request.setPaymentMode(paymentType);
        request.setDeliveryOption(orderType.toLowerCase());
        request.setIsTomorrow(sharedPreferencesClass.getString(sharedPreferencesClass.IS_TOMORROW));
        request.setDeliveryCharge(deliveryFee);
        request.setDiscountAmount(voucherDiscount);
        request.setOrderTotal(totalAmount);
        request.setOrderSubtotal(subTotalAmount);
        request.setVoucherId("");
        request.setOfferId("");
        request.setBillingAddress(sharedPreferencesClass.getString(sharedPreferencesClass.DELIVERY_ADDRESS_ID));
        request.setDeliveryAddress(sharedPreferencesClass.getString(sharedPreferencesClass.DELIVERY_ADDRESS_ID));
        request.setOrderVia("android");
        request.setOrderNotes(notes);
        request.setExpMonth(Integer.parseInt(expireDate.getText().toString()));
        request.setExpYear(Integer.parseInt(expireYear.getText().toString()));
        request.setLast4CardDigit(cardNumber.getText().toString().trim().substring(cardNumber.length() - 4));
        request.setStripeToken(token);
        request.setEmailId(val.getLoginResponse().getData().getEmail());
        request.setSaveCard(isSaveCard);
        request.setCardData(makeData());
        request.setDeliveryDateTime(sharedPreferencesClass.getString(sharedPreferencesClass.DELIVERY_DATE_TIME));
        Call<CheckoutResponse> call3 = apiInterface.mCheckout(request);
        call3.enqueue(new Callback<CheckoutResponse>() {
            @Override
            public void onResponse(Call<CheckoutResponse> call, Response<CheckoutResponse> response) {
                dialog.dismiss();
                try {

                    Log.e("Success ><<<<<<<", ">>>>> Success" + response.code() + "//" + response.body().getSuccess() + "//" + response.body().getData().getOrderId());
                    if (response.code() == 200 && response.body().getSuccess()) {


                        try {
                            Log.e("Success ><<<<<<<", ">>>>> Success" + response.code() + "//" + response.body().getSuccess() + "//" + response.body().getData().getOrder_number());
                            if (response.code() == 200 && response.body().getSuccess()) {
                                sharedPreferencesClass.setOrderIDKey(response.body().getData().getOrder_number());
                                Log.e("order id", response.body().getData().getOrder_number());
                                alertDialogOrderPlaced(response.body().getMessage(), true);
                            } else if (response.code() == 200 && !response.body().getSuccess()) {
                                alertDialogOrderPlaced(response.body().getMessage(), false);
                            } else {
                                alertDialogOrderPlaced("Transaction Failed\n" +
                                        "Your Order could not be processed", false);

                            }
                        } catch (Exception e) {
                            mProgressDialog.dismiss();
                            alertDialogOrderPlaced("Transaction Failed\n" +
                                    "Your Order could not be processed", false);
                            Log.e("Error1 <>>>", ">>>>>" + e.getMessage());

                        }
                    } else {
                        finish();
                        Toast.makeText(AddNewCardActivity.this, "Please try again." + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    finish();
                    Log.e("Error1 <>>>", ">>>>>" + e.getMessage());

                    Toast.makeText(AddNewCardActivity.this, "Please try again." + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CheckoutResponse> call, Throwable t) {
                dialog.dismiss();
                Log.e("Error 2 <>>>", ">>>>>" + t.getMessage());
                finish();
                alertDialogOrderPlaced("Transaction Failed\n" +
                        "Your Order could not be processed", false);
            }
        });
    }

    public void alertDialogOrderPlaced(final String msg, final boolean isSuccess) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.popoup_order_fail_success, null);
        final AlertDialog cardDialog = new AlertDialog.Builder(this).create();
        cardDialog.setCanceledOnTouchOutside(false);
        TextView txtMsg = mDialogView.findViewById(R.id.txt_msg);
        txtMsg.setText(msg);
        cardDialog.setView(mDialogView);
        cardDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                cardDialog.dismiss();
                if (isSuccess) {
                    int fragSize = getSupportFragmentManager().getBackStackEntryCount() - 1;
                    for (int i = 0; i < fragSize; i++) {
                        getSupportFragmentManager().popBackStack();
                    }
                    db.deleteCart();
                    sharedPreferencesClass.setString(sharedPreferencesClass.RESTUARANT_ID, "");
                    sharedPreferencesClass.setString(sharedPreferencesClass.RESTUARANT_NAME, "");
                    sharedPreferencesClass.setString(sharedPreferencesClass.NOTEPAD, "");
                    Constants.ORDER_STATUS = 1;

                    Constants.switchActivity(AddNewCardActivity.this, OrderStatusActivity.class);
                    if (RestaurantDetailsActivity.restaurantDetailsActivity != null) {
                        RestaurantDetailsActivity.restaurantDetailsActivity.finish();
                    }
                    finish();
                } else {
                    dialog.dismiss();
                    msg.equalsIgnoreCase("Must provide correct expiry date");
                    cardDialog.dismiss();
                }

            }
        });
        cardDialog.show();
    }

    public void logLargeString(String str) {
        if (str.length() > 3000) {
            Log.e("CART Final>>", str.substring(0, 3000));
            logLargeString(str.substring(3000));
        } else {
            Log.e("CART Final>>", str); // continuation
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        dialog.dismiss();
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
}
