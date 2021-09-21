package com.easyfoodcustomer.add_card;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
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

import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.CartData;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MealProduct;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategoryCart;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuProductList;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.Modifier;
import com.easyfoodcustomer.roomData.AppDatabase;
import com.easyfoodcustomer.select_payment_method.SelectPaymentMethodActivity;
import com.easyfoodcustomer.select_payment_method.model.checkout_request.CreatePaymentRequest;
import com.easyfoodcustomer.select_payment_method.model.checkout_response.CreatePaymentResponse;
import com.easyfoodcustomer.utility.Helper;
import com.easyfoodcustomer.utility.PrefManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.easyfoodcustomer.R;
import com.easyfoodcustomer.api.AddCardInterface;
import com.easyfoodcustomer.cart_db.DatabaseHelper;
import com.easyfoodcustomer.dialogs.AddressDialogFragment;
import com.easyfoodcustomer.dialogs.BillingAddressDialogFragment;
import com.easyfoodcustomer.model.add_card_request.CardAddRequest;
import com.easyfoodcustomer.model.add_card_response.CardAddResponse;
import com.easyfoodcustomer.order_status.OrderStatusActivity;
import com.easyfoodcustomer.restaurant_details.RestaurantDetailsActivity;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.CartDatRequest;
import com.easyfoodcustomer.select_payment_method.api.CheckoutRequestInterface;
import com.easyfoodcustomer.select_payment_method.model.checkout_request.CheckoutRequest;
import com.easyfoodcustomer.select_payment_method.model.checkout_response.CheckoutResponse;
import com.easyfoodcustomer.utility.ApiClient;
import com.easyfoodcustomer.utility.Constants;
import com.easyfoodcustomer.utility.GlobalValues;
import com.easyfoodcustomer.utility.SharedPreferencesClass;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.easyfoodcustomer.utility.Helper.formatDateTime;
import static com.easyfoodcustomer.utility.UserContants.AUTH_TOKEN;
import static com.easyfoodcustomer.utility.UserContants.POST_CODE_NEW;

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

    @BindView(R.id.make_default)
    CheckBox makeDefault;
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
    String isMakeDefault = "off";
    String billingAddress = "";
    boolean isFromCheckout = false;
    private String mobileNo;
    FirebaseAnalytics mFirebaseAnalytics;
    private String deliveryPartnerId = "";
    private AppDatabase mDB;

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
        mDB = AppDatabase.getInstance(getApplicationContext());
        Bundle extras = getIntent().getExtras();
        Log.e("EXTRA>>>", "//" + extras);
        if (extras != null) {
            totalAmount = extras.getDouble("ORDER_TOTAL");
            mobileNo = extras.getString("MOBILE_NUMBER", "");
            deliveryPartnerId = extras.getString("PATNER_ID", "");

            subTotalAmount = extras.getDouble("ORDER_SUB_TOTAL");
            deliveryFee = extras.getDouble("deliveryCharge");
            orderType = extras.getString("orderType");
            voucherDiscount = extras.getDouble("voucherDiscount");
            notes = extras.getString("notes");
            voucherCode = extras.getString("appliedVoucherCode");
            voucherAmount = extras.getDouble("appliedVoucherAmount");
            voucherPaymentType = extras.getString("appliedVoucherPaymentType");

            storeCard.setVisibility(View.VISIBLE);
            makeDefault.setVisibility(View.VISIBLE);
            isFromCheckout = true;
        } else {
            storeCard.setVisibility(View.GONE);
            makeDefault.setVisibility(View.GONE);
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

        makeDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    isMakeDefault = "on";
                } else {
                    isMakeDefault = "off";
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

        Call<CardAddResponse> call3 = apiInterface.mLogin(PrefManager.getInstance(AddNewCardActivity.this).getPreference(AUTH_TOKEN, ""), request);
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

            //createPaymentAPI();
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
                            createPaymentAPI();
                            //callAPI(token.getId(), "card");
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


    public void createPaymentAPI() {
        CheckoutRequestInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(CheckoutRequestInterface.class);


        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        CreatePaymentRequest request = new CreatePaymentRequest();
        try {
            request.setNameOnCard(Base64.encodeToString(cardholderName.getText().toString().trim().getBytes("UTF-8"), Base64.DEFAULT));
            request.setCardNo(Base64.encodeToString(cardNumber.getText().toString().trim().getBytes("UTF-8"), Base64.DEFAULT));
            request.setCvv(Base64.encodeToString(cvvNo.getText().toString().trim().getBytes("UTF-8"), Base64.DEFAULT));
            request.setMonth(Base64.encodeToString(expireDate.getText().toString().trim().getBytes("UTF-8"), Base64.DEFAULT));
            request.setYear(Base64.encodeToString(expireYear.getText().toString().trim().getBytes("UTF-8"), Base64.DEFAULT));
            request.setBillingPostcode(billingPost.getText().toString().trim());
            request.setPostcode(postalcode);
            request.setCustomerId(val.getLoginResponse().getData().getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", "" + e.getMessage());
        }

        Log.e("CreatePayment", "" + request);


        Call<CreatePaymentResponse> call3 = apiInterface.createPayment(PrefManager.getInstance(AddNewCardActivity.this).getPreference(AUTH_TOKEN, ""), request);
        call3.enqueue(new Callback<CreatePaymentResponse>() {
            @Override
            public void onResponse(Call<CreatePaymentResponse> call, Response<CreatePaymentResponse> response) {

                try {

                    if (response.code() == 200 && response.body().getSuccess()) {
                        if (response.body().getData() != null && !response.body().getData().isEmpty()) {
                            callAPI(response.body().getData(), "card");
                        } else {
                            dialog.dismiss();
                            alertDialogOrderPlaced(response.body().getMessage(), false);
                        }


                    } else {
                        dialog.dismiss();
                        finish();
                        Toast.makeText(AddNewCardActivity.this, "Please try again." + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                    finish();
                    Log.e("Error1 <>>>", ">>>>>" + e.getMessage());

                    Toast.makeText(AddNewCardActivity.this, "Please try again." + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreatePaymentResponse> call, Throwable t) {
                dialog.dismiss();
                Log.e("Error 2 <>>>", ">>>>>" + t.getMessage());
                finish();
                alertDialogOrderPlaced("Transaction Failed\n" +
                        "Your Order could not be processed", false);
            }
        });
    }

    private CartDatRequest makeData() {
        CartDatRequest cartDatRequest = new CartDatRequest();
        try {

            //cartDatRequest.setCartData(db.getCartData());
            cartDatRequest.setRestaurantId(val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantId());
            cartDatRequest.setRestaurantName(val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantName());
            cartDatRequest.setPostCode(val.getRestaurantDetailsResponse().getData().getRestaurants().getPostCode());
            cartDatRequest.setTotalCartPrice(totalAmount);
            cartDatRequest.setOrderType(orderType.toLowerCase());
            cartDatRequest.setDeliveryCharge(deliveryFee);
            cartDatRequest.setMaxLength(String.valueOf(Constants.MAX_LENGTH));
            cartDatRequest.setVoucherDiscount(voucherDiscount);
            cartDatRequest.setVoucherCode(voucherCode);


            CartData cartDataForRequest = new CartData();
            CartData data = db.getCartData();
            cartDataForRequest.setSpecialOffers(data.getSpecialOffers());
            cartDataForRequest.setUpsellProducts(data.getUpsellProducts());
            List<MenuProduct> cartMenu = new ArrayList<>();
            List<MenuCategoryCart> menuCategoryCartsRequest = new ArrayList<>();
            List<MealProduct> mealProducts = new ArrayList<>();
            MenuProductList menuProductList = new MenuProductList();
            List<Modifier> mealModifiers = new ArrayList<>();


            for (MenuCategoryCart menuCategoryCart : data.getMenuCategoryCarts()) {

                for (MenuProduct menuProduct : menuCategoryCart.getMenuProducts()) {
                    mealProducts = new ArrayList<>();
                    mealModifiers = new ArrayList<>();
                    if (menuProduct.getMealProducts() != null && menuProduct.getMealProducts().size() > 0) {


                        for (int i = 0; i < menuProduct.getMealProducts().size(); i++) {
                            if (i > 0) {
                                Modifier modifier = new Modifier(menuProduct.getMealProducts().get(i).getProductId(),
                                        "", menuProduct.getMealProducts().get(i).getProductSizePrice(),
                                        menuProduct.getMealProducts().get(i).getProductName(),
                                        String.valueOf(menuProduct.getMealProducts().get(i).getQuantity()),
                                        String.valueOf(menuProduct.getMealProducts().get(i).getQuantity()),
                                        Double.parseDouble("0"),
                                        Double.parseDouble("0"));
                                mealModifiers.add(modifier);
                            }
                        }

                        for (MealProduct mealProduct : menuProduct.getMealProducts()) {


                            if (mealProduct.getMenuProductSize() != null && mealProduct.getMenuProductSize().size() > 0) {
                                mealProduct.setAmount(mealProduct.getMenuProductSize().get(0).getAmount());
                                mealProduct.setOriginalAmount(mealProduct.getMenuProductSize().get(0).getOriginalAmount());
                                mealProduct.setOriginalQuantity(mealProduct.getMenuProductSize().get(0).getOriginalQuantity());
                                mealProduct.setProductSizePrice(mealProduct.getMenuProductSize().get(0).getProductSizePrice());
                                mealProduct.setSizeModifiers(mealProduct.getMenuProductSize().get(0).getSizeModifiers());
                                List<Modifier> mod = mealProduct.getSizeModifiers().get(mealProduct.getSizeModifiers().size() - 1).getModifier();
                                mod.addAll(mealModifiers);
                                mealProduct.getSizeModifiers().get(mealProduct.getSizeModifiers().size() - 1).setModifier(mod);
                                mealProduct.setMenuProductSize(null);

                            }
                            if (mealProducts.size() == 0)
                                mealProducts.add(mealProduct);
                        }


                    }
                    menuProduct.setMealProducts(mealProducts);
                    cartMenu.add(menuProduct);
                    List<MenuProduct> menProd = new ArrayList<>();
                    menProd.add(menuProduct);
                    MenuCategoryCart menuCart = new MenuCategoryCart(menuCategoryCart.getMenuCategoryId(), menuProduct.getProductName(), menuCategoryCart.getMenuSubCategory(), menProd);
                    menuCategoryCartsRequest.add(menuCart);

                }

                for (MenuCategoryCart menuCategoryCart1 : menuCategoryCart.getMenuSubCategory()) {
                    for (MenuProduct menuProduct : menuCategoryCart1.getMenuProducts()) {
                        cartMenu.add(menuProduct);
                        List<MenuProduct> menProd = new ArrayList<>();
                        menProd.add(menuProduct);
                        MenuCategoryCart menuCart = new MenuCategoryCart(menuCategoryCart.getMenuCategoryId(), menuProduct.getProductName(), menuCategoryCart.getMenuSubCategory(), menProd);
                        menuCategoryCartsRequest.add(menuCart);
                    }
                }

                //menuCategoryCartsRequest.add(menuCategoryCart);

            }
            cartDataForRequest.setMenuCategoryCarts(menuCategoryCartsRequest);

            cartDatRequest.setCartData(cartDataForRequest);

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
        request.setUserMobile(mobileNo);
        request.setPaymentMode(paymentType);
        request.setDeliveryOption(orderType.toLowerCase());
        request.setIsTomorrow(sharedPreferencesClass.getString(sharedPreferencesClass.IS_TOMORROW));
        request.setDeliveryCharge(deliveryFee);
        request.setDiscountAmount(voucherDiscount);
        request.setOrderTotal(totalAmount);
        request.setOrderSubtotal(subTotalAmount);
        request.setVoucherId("");
        request.setOfferId("");
        request.setDeliveryPartner(deliveryPartnerId);
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
        request.setMakeDefault(isMakeDefault);
        //request.setCardData(makeData());
        request.setDeliveryDateTime(sharedPreferencesClass.getString(sharedPreferencesClass.DELIVERY_DATE_TIME));
        request.setUseragent(Helper.getDeviceName() + "," + Build.VERSION.RELEASE);
        request.setOrder_time_postcode(PrefManager.getInstance(AddNewCardActivity.this).getPreference(POST_CODE_NEW, ""));

        Call<CheckoutResponse> call3 = apiInterface.mCheckout(PrefManager.getInstance(AddNewCardActivity.this).getPreference(AUTH_TOKEN, ""), request);
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
                                //alertDialogOrderPlaced(response.body().getMessage(), true);
                                alertDialogOrderPlaced(response.body().getMessage(), true, request, response.body().getData().getOrder_number());

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
                    mDB.saveOrderHistry().deleteAll();
                    sharedPreferencesClass.setString(sharedPreferencesClass.RESTUARANT_ID, "");
                    sharedPreferencesClass.setString(sharedPreferencesClass.RESTUARANT_NAME, "");
                    sharedPreferencesClass.setString(sharedPreferencesClass.NOTEPAD, "");
                    sharedPreferencesClass.setString(sharedPreferencesClass.DELIVERY_MOBILE_NUMBER, "");
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



    public void alertDialogOrderPlaced(final String msg, final boolean isSuccess, final CheckoutRequest request, final String orderNum) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.popoup_order_fail_success, null);
        final AlertDialog cardDialog = new AlertDialog.Builder(this).create();
        cardDialog.setCanceledOnTouchOutside(false);
        TextView txtMsg = mDialogView.findViewById(R.id.txt_msg);
        txtMsg.setText(msg);
        cardDialog.setView(mDialogView);
        cardDialog.setCancelable(false);
        cardDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardDialog.dismiss();
                if (isSuccess) {
                    int fragSize = getSupportFragmentManager().getBackStackEntryCount() - 1;
                    for (int i = 0; i < fragSize; i++) {
                        getSupportFragmentManager().popBackStack();
                    }

                    String restaurentName = sharedPreferencesClass.getString(sharedPreferencesClass.RESTUARANT_NAME);
                    db.deleteCart();
                    mDB.saveOrderHistry().deleteAll();
                    sharedPreferencesClass.setString(sharedPreferencesClass.RESTUARANT_ID, "");
                    sharedPreferencesClass.setString(sharedPreferencesClass.RESTUARANT_NAME, "");
                    sharedPreferencesClass.setString(sharedPreferencesClass.NOTEPAD, "");
                    sharedPreferencesClass.setString(sharedPreferencesClass.DELIVERY_MOBILE_NUMBER, "");
                    sharedPreferencesClass.setString(SharedPreferencesClass.TABLE_NO, null);
                    sharedPreferencesClass.setString(SharedPreferencesClass.UNIT_ID, null);
                    sharedPreferencesClass.setString(SharedPreferencesClass.UNIT_TYPE, null);
                    sharedPreferencesClass.setString(SharedPreferencesClass.TABLE_TYPE, null);
                    sharedPreferencesClass.setString(sharedPreferencesClass.DEFAULT_ADDRESS, null);
                    Constants.ORDER_STATUS = 1;
                    Intent intent = new Intent(AddNewCardActivity.this, OrderStatusActivity.class);
                    intent.putExtra("order_no", orderNum);

                    if (sharedPreferencesClass.getInt(SharedPreferencesClass.IS_FOR_TABLE) == 0) {
                        intent.putExtra(Constants.PAYMENT_MODE, request.getPaymentMode());
                        intent.putExtra(Constants.ORDER_TYPE, request.getDeliveryOption());
                        intent.putExtra(Constants.ORDER_TIME, sharedPreferencesClass.getString(sharedPreferencesClass.DELIVERY_DATE_TIME));
                    } else {
                        if (request.getPaymentMode().equalsIgnoreCase("cash")) {
                            intent.putExtra(Constants.PAYMENT_MODE, "Pay at Restaurant");
                        } else {
                            intent.putExtra(Constants.PAYMENT_MODE, request.getPaymentMode());
                        }
                        intent.putExtra(Constants.ORDER_TIME, formatDateTime(new Date()));
                        intent.putExtra(Constants.ORDER_TYPE, "table");
                    }


                    intent.putExtra(Constants.RESTAURENT_NAME, restaurentName);
                    intent.putExtra(Constants.TOTAL_COST, String.valueOf(request.getOrderTotal()));
                    intent.putExtra(Constants.PHONE_NUMBER, "9876543210");
                    intent.putExtra(Constants.CUSTOMER_ID, request.getCustomerId());

                    startActivity(intent);
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

}
