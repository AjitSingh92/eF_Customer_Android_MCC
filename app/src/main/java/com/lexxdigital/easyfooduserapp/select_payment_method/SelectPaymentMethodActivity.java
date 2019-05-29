package com.lexxdigital.easyfooduserapp.select_payment_method;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.adapters.SaveCardAdapter;
import com.lexxdigital.easyfooduserapp.add_card.AddNewCardActivity;
import com.lexxdigital.easyfooduserapp.add_manual_address.AddAddressManualActivity;
import com.lexxdigital.easyfooduserapp.api.CardListInterface;
import com.lexxdigital.easyfooduserapp.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapp.dashboard.DashboardActivity;
import com.lexxdigital.easyfooduserapp.model.card_list_request.CardListRequest;
import com.lexxdigital.easyfooduserapp.model.card_list_response.Card;
import com.lexxdigital.easyfooduserapp.model.card_list_response.CardListResponse;
import com.lexxdigital.easyfooduserapp.order_status.OrderStatusActivity;
import com.lexxdigital.easyfooduserapp.restaurant_details.RestaurantDetailsActivity;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.CartDatRequest;
import com.lexxdigital.easyfooduserapp.select_payment_method.api.CheckoutRequestInterface;
import com.lexxdigital.easyfooduserapp.select_payment_method.model.checkout_request.CheckoutRequest;
import com.lexxdigital.easyfooduserapp.select_payment_method.model.checkout_response.CheckoutResponse;
import com.lexxdigital.easyfooduserapp.utility.ApiClient;
import com.lexxdigital.easyfooduserapp.utility.Constants;
import com.lexxdigital.easyfooduserapp.utility.GlobalValues;
import com.lexxdigital.easyfooduserapp.utility.SharedPreferencesClass;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Token;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectPaymentMethodActivity extends AppCompatActivity implements SaveCardAdapter.PositionInterface {
    SharedPreferencesClass sharedPreferencesClass;
    @BindView(R.id.showCardList)
    RecyclerView showCardList;
    @BindView(R.id.paywith_card_tv)
    TextView paywithCardTv;
    @BindView(R.id.add_new_card)
    TextView addNewCard;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.pay_with_cash)
    TextView payWithCash;
    ArrayList<String> check = new ArrayList<>();
    SaveCardAdapter.PositionInterface mPositionInterface;
    SaveCardAdapter mDealCardAdapter;
    ArrayList<String> allReadyCheck = new ArrayList<>();
    boolean isCardSelected = false;
    List<Card> dataList = new ArrayList<>();
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.no_cards)
    RelativeLayout noCards;
    private com.stripe.android.model.Card card;
    private ProgressDialog mProgressDialog;
    private Dialog dialog;
    int position = 0;
    Card detail;
    private GlobalValues val;
    private Double totalAmount = 0.0d;
    private Double subTotalAmount = 0.0d;
    private Double netAmount = 0.0d;
    private Double deliveryFee = 0.0d;
    private String orderType;
    private Double voucherDiscount = 0.0d;
    private String notes;
    private Context mContext;
    private DatabaseHelper db;
    private String voucherCode;
    private Double voucherAmount = 0.0d;
    private String voucherPaymentType;

    private String address1 = "";
    private String address2 = "";
    private String addressCity = "";
    private String postalCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_payment_method);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Constants.setStatusBarGradiant(SelectPaymentMethodActivity.this);
        ButterKnife.bind(this);
        mContext = this;
        db = new DatabaseHelper(this);
        val = (GlobalValues) getApplicationContext();
        dialog = new Dialog(SelectPaymentMethodActivity.this);
        dialog.setTitle("");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sharedPreferencesClass = new SharedPreferencesClass(getApplicationContext());

        mProgressDialog = new ProgressDialog(SelectPaymentMethodActivity.this);
        mProgressDialog.requestWindowFeature(1);
        mProgressDialog.setMessage("Please wait");
        mProgressDialog.setCanceledOnTouchOutside(false);
//                    mProgressDialog.show();
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

            Log.e("order data", totalAmount + "," + subTotalAmount + "," + deliveryFee);
        }
        if (Constants.isInternetConnectionAvailable(300)) {
            getCardList();
        } else {
            dialogNoInternetConnection("Please check internet connection.", 1);
        }

//        initView(dataList);

    }

    private CartDatRequest makeData() {
        /*Gson gson = new Gson();
        JSONObject data = new JSONObject();*/
        CartDatRequest cartDatRequest = new CartDatRequest();
        try {
/*
            JSONObject menu = new JSONObject();
            String sss = gson.toJson(db.getCartData());
            JSONObject ddd = new JSONObject(sss);
            data.put("menu", ddd);
*/
            cartDatRequest.setCartData(db.getCartData());
            cartDatRequest.setRestaurantId(val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantId());
            cartDatRequest.setRestaurantName(val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantName());
            cartDatRequest.setPostCode(val.getRestaurantDetailsResponse().getData().getRestaurants().getPostCode());
            cartDatRequest.setTotalCartPrice(totalAmount);
            cartDatRequest.setOrderType(orderType);
            cartDatRequest.setDeliveryCharge(deliveryFee);
            cartDatRequest.setMaxLength("");
            cartDatRequest.setVoucherDiscount(voucherDiscount);
            cartDatRequest.setVoucherCode(voucherCode);
            cartDatRequest.setRestaurantSlug(sharedPreferencesClass.getString(sharedPreferencesClass.RESTAURANT_NAME_SLUG));


//            Log.e("COMPLITE DATA", data.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartDatRequest;
    }

    public void alertDialogCVV(String msg) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.popup_cvv_number, null);
        final AlertDialog cvvDialog = new AlertDialog.Builder(this).create();
        cvvDialog.setCanceledOnTouchOutside(false);
        cvvDialog.setView(mDialogView);
        cvvDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final EditText exDate = (EditText) mDialogView.findViewById(R.id.expire_date);
        final EditText exYear = (EditText) mDialogView.findViewById(R.id.expire_year);
        final TextView errorTxt = (TextView) mDialogView.findViewById(R.id.txt_error);
        final TextView textView = mDialogView.findViewById(R.id.textView);
        final TextView errorText = mDialogView.findViewById(R.id.tv_invalid);
        errorText.setVisibility(View.GONE);
        if (msg != null) {
            textView.setVisibility(View.GONE);
            errorText.setVisibility(View.VISIBLE);
        }
        errorText.setText(msg);

        exDate.addTextChangedListener(new TextWatcher() {
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
                textView.setVisibility(View.VISIBLE);
                errorText.setVisibility(View.GONE);

                if (s.length() == 2) {
                    exYear.requestFocus();
                }
                // TODO Auto-generated method stub
            }
        });
        mDialogView.findViewById(R.id.submit_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                if (exDate.getText().toString().trim().length() <= 0) {
                    errorTxt.setText("Please enter expiry month");
                    errorTxt.setVisibility(View.VISIBLE);
                } else if (Integer.parseInt(exDate.getText().toString().trim()) > 12 && Integer.parseInt(exDate.getText().toString().trim()) < 1) {

                    errorTxt.setText("Please enter valid expiry month");
                    errorTxt.setVisibility(View.VISIBLE);

                } else if (exYear.getText().toString().trim().length() <= 0) {
                    errorTxt.setText("Please enter expiry year");
                    errorTxt.setVisibility(View.VISIBLE);
                } else {
                    errorTxt.setVisibility(View.GONE);
                  /*  mProgressDialog = new ProgressDialog(SelectPaymentMethodActivity.this);
                    mProgressDialog.requestWindowFeature(1);
                    mProgressDialog.setMessage("Please wait");
                    mProgressDialog.setCanceledOnTouchOutside(false);
//                    mProgressDialog.show();*/
//                    callAPI("", "card", exDate.getText().toString(), exYear.getText().toString());

                    if (val.getRestaurantDetailsResponse().getData().getRestaurants().getAddress() != null) {
                        if (voucherPaymentType.equalsIgnoreCase("card")) {
                            if (Constants.isInternetConnectionAvailable(300)) {
                                callAPI("", "card", exDate.getText().toString(), exYear.getText().toString());
                            } else {
                                dialogNoInternetConnection("Please check internet connection.", 0);
                            }

                        } else if (voucherPaymentType.equalsIgnoreCase("")) {
                            if (Constants.isInternetConnectionAvailable(300)) {
                                callAPI("", "card", exDate.getText().toString(), exYear.getText().toString());
                            } else {
                                dialogNoInternetConnection("Please check internet connection.", 0);
                            }

                        } else {
                            alertVoucherApply("", "card", exDate.getText().toString(), exYear.getText().toString());
                        }
                    } else {
                        Intent intent = new Intent(SelectPaymentMethodActivity.this, AddAddressManualActivity.class);
                        startActivity(intent);
                    }

                    cvvDialog.dismiss();
                }

            }
        });
        mDialogView.findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                cvvDialog.dismiss();
                address1 = "";
                address2 = "";
                addressCity = "";
                postalCode = "";
            }
        });

        cvvDialog.show();
    }

    public void alertDialogSelectCard(String msg) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.popup_select_card, null);
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
                alertDialogCVV(null);

            }
        });
        mDialogView.findViewById(R.id.cancelTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                cardDialog.dismiss();
            }
        });

        cardDialog.show();
    }

    @OnClick({R.id.add_new_card, R.id.paywith_card_tv, R.id.pay_with_cash})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_new_card:
                Intent i = new Intent(this, AddNewCardActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("ORDER_TOTAL", totalAmount);
                i.putExtra("ORDER_SUB_TOTAL", subTotalAmount);
                i.putExtra("deliveryCharge", deliveryFee);
                i.putExtra("orderType", orderType);
                i.putExtra("voucherDiscount", voucherDiscount);
                i.putExtra("notes", notes);
                i.putExtra("appliedVoucherCode", voucherCode);
                i.putExtra("appliedVoucherAmount", voucherAmount);
                i.putExtra("appliedVoucherPaymentType", voucherPaymentType);
                startActivity(i);
                this.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;
            case R.id.paywith_card_tv:
//                if(isCardSelected) {
//
//                    callAPI("", "card");
//                }else {
//                    alertDialogSelectCard();
//                }
                break;
            case R.id.pay_with_cash:
                //  Toast.makeText(SelectPaymentMethodActivity.this, "Proccess", Toast.LENGTH_SHORT).show();

                try {
//                    callAPI("", "cash", "", "");
                    if (sharedPreferencesClass.getInt(sharedPreferencesClass.NUMBER_OF_ORDERS) > 0) {

                        if (voucherPaymentType.equalsIgnoreCase("cash")) {
                            if (Constants.isInternetConnectionAvailable(300)) {
                                callAPI("", "cash", "", "");
                            } else {
                                dialogNoInternetConnection("Please check internet connection.", 0);
                            }

                        } else if (voucherPaymentType.equalsIgnoreCase("")) {
                            if (Constants.isInternetConnectionAvailable(300)) {
                                callAPI("", "cash", "", "");
                            } else {
                                dialogNoInternetConnection("Please check internet connection.", 0);
                            }

                        } else {
                            alertVoucherApply("", "cash", "", "");
                        }

                    } else {
                        alertMessage();
                    }
                } catch (Exception e) {
                    Toast.makeText(SelectPaymentMethodActivity.this, "Server Error.", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    public void initView(List<Card> dataList) {
        for (int i = 0; i < dataList.size(); i++) {
            check.add("0");
        }
        allReadyCheck.add("0");

        mPositionInterface = this;
        mDealCardAdapter = new SaveCardAdapter(getApplicationContext(), mPositionInterface, check, dataList);
        @SuppressLint("WrongConstant")
        LinearLayoutManager horizontalLayoutManagaer2 = new LinearLayoutManager(SelectPaymentMethodActivity.this, LinearLayoutManager.VERTICAL, false);
        showCardList.setLayoutManager(horizontalLayoutManagaer2);
        showCardList.setAdapter(mDealCardAdapter);
    }


    @OnClick(R.id.back)
    public void onViewClicked() {
//        startActivity(new Intent(SelectPaymentMethodActivity.this, DashboardActivity.class));
        finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        startActivity(new Intent(SelectPaymentMethodActivity.this, DashboardActivity.class));
        finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        // Toast.makeText(val, "", Toast.LENGTH_SHORT).show();
    }

    public void callAPI(String token, String paymentType, String exDate, String exYear) {
        mProgressDialog.show();
        CheckoutRequestInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(CheckoutRequestInterface.class);
//        if (!exDate.equalsIgnoreCase("")) {
//            Toast.makeText(this, "Please enter correct data", Toast.LENGTH_SHORT).show();
//            return;
//        }


        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        if (!paymentType.equalsIgnoreCase("cash"))
            detail = dataList.get(position);
        CheckoutRequest request = new CheckoutRequest();
        request.setRestaurantId(val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantId());
        request.setCustomerId(sharedPreferencesClass.getString(sharedPreferencesClass.USER_ID));
        request.setPaymentMode(paymentType);
        request.setDeliveryOption(orderType.toLowerCase());
        request.setDeliveryCharge(deliveryFee);
        request.setDiscountAmount(voucherDiscount);
        request.setOrderTotal(totalAmount);
        request.setOrderSubtotal(subTotalAmount);
        request.setVoucherId("");
        request.setOfferId("");
        request.setBillingAddress(address1 + ", " + address2 + ", " + addressCity + ", " + postalCode);
        request.setDeliveryAddress(sharedPreferencesClass.getString(sharedPreferencesClass.DELIVERY_ADDRESS_ID));
        request.setOrderVia("android");
        request.setOrderNotes(notes);
        request.setStripeToken(token);
        request.setDeliveryDateTime(sharedPreferencesClass.getString(sharedPreferencesClass.DELIVERY_DATE_TIME));

        if (!exYear.equalsIgnoreCase("") && !exDate.equalsIgnoreCase("")) {
            request.setExpMonth(Integer.parseInt(exDate));
            request.setExpYear(Integer.parseInt(exYear));
        }

        if (!paymentType.equalsIgnoreCase("cash")) {
            request.setStripeCustomerId(detail.getStripeCustomerId());
            request.setLast4CardDigit(dataList.get(position).getLast4CardNo());
            request.setSavedCardId(detail.getCardId());
        } else {
            request.setStripeCustomerId("");
            request.setLast4CardDigit("");
            request.setSavedCardId("");
        }
        request.setEmailId(val.getLoginResponse().getData().getEmail());
        request.setCardData(makeData());

        /*CartDetails detail = new CartDetails();
        Gson gson = new Gson();
        FinalNewCartDetails cartList22 = gson.fromJson(sharedPreferencesClass.getCartDetailsKey().toString(), new TypeToken<FinalNewCartDetails>() {
        }.getType());

        Gson gson2 = new Gson();
        String json22 = gson2.toJson(cartList22.getData());

        List<Datum> cartList = gson.fromJson(json22, new TypeToken<List<Datum>>() {
        }.getType());

//        Type type = new TypeToken<List<ShowMenuCartDetails>>() {
//        }.getType();


        detail.setData(cartList);
//        request.setCartDetails(detail);
        Gson gson23 = new Gson();
        String json23 = gson23.toJson(request);
        logLargeString(json23);*/
        Call<CheckoutResponse> call3 = apiInterface.mCheckout(request);
        call3.enqueue(new Callback<CheckoutResponse>() {
            @Override
            public void onResponse(Call<CheckoutResponse> call, Response<CheckoutResponse> response) {
                mProgressDialog.dismiss();
                try {
                    Log.e("Success ><<<<<<<", ">>>>> Success" + response.code() + "//" + response.body().getSuccess() + "//" + response.body().getData().getOrder_number());
                    if (response.code() == 200 && response.body().getSuccess()) {
                        sharedPreferencesClass.setOrderIDKey(response.body().getData().getOrder_number());
                        Log.e("order id", response.body().getData().getOrder_number());
                        alertDialogOrderPlaced("Your order has been placed successfully.", true);
                    } else if (response.code() == 200 && !response.body().getSuccess()) {
//                        alertDialogOrderPlaced(response.body().getMessage(), false);
                        alertDialogCVV("Please enter valid expiry date");
                    } else {
                        alertDialogOrderPlaced("Transaction Failed\n" +
                                "Your Order could not be processed", false);
                        address1 = "";
                        address2 = "";
                        addressCity = "";
                        postalCode = "";
                    }
                } catch (Exception e) {
                    mProgressDialog.dismiss();
                    alertDialogOrderPlaced("Transaction Failed\n" +
                            "Your Order could not be processed", false);
                    Log.e("Error1 <>>>", ">>>>>" + e.getMessage());
                    address1 = "";
                    address2 = "";
                    addressCity = "";
                    postalCode = "";
                }
            }

            @Override
            public void onFailure(Call<CheckoutResponse> call, Throwable t) {
                mProgressDialog.dismiss();
                Log.e("Error <>>>", ">>>>>" + t.getMessage());
                alertDialogOrderPlaced("Transaction Failed\n" +
                        "Your Order could not be processed", false);
                address1 = "";
                address2 = "";
                addressCity = "";
                postalCode = "";
            }
        });
    }

    public void logLargeString(String str) {
        if (str.length() > 3000) {
            Log.e("CART Final>>", str.substring(0, 3000));
            logLargeString(str.substring(3000));
        } else {
            Log.e("CART Final>>", str); // continuation
        }
    }


    public void getCardList() {
        //   swipreferesh.setRefreshing(true);
        CardListInterface apiInterface = ApiClient.getClient(SelectPaymentMethodActivity.this).create(CardListInterface.class);
        CardListRequest request = new CardListRequest();
        request.setUserId(sharedPreferencesClass.getString(sharedPreferencesClass.USER_ID));


        Call<CardListResponse> call3 = apiInterface.mLogin(request);
        call3.enqueue(new Callback<CardListResponse>() {
            @Override
            public void onResponse(Call<CardListResponse> call, Response<CardListResponse> response) {
                try {
                    dialog.dismiss();

                    if (response.body().getSuccess()) {
                        dataList = response.body().getData().getCards();
                        if (dataList.size() <= 0) {
                            noCards.setVisibility(View.VISIBLE);
                        } else {
                            noCards.setVisibility(View.GONE);
                        }
                        initView(dataList);
                        Log.e("Success <>>>", ">>>>>" + response.body().getMessage() + "//");
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                    noCards.setVisibility(View.VISIBLE);
                    Log.e("Error11 <>>>", ">>>>>" + e.getMessage());
                    //    showDialog("Please try again.");
//                       Toast.makeText(LoginActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<CardListResponse> call, Throwable t) {
                Log.e("Error12 <>>>", ">>>>>" + t.getMessage());
                noCards.setVisibility(View.VISIBLE);
//                dialog.dismiss();
//                showDialog("Please try again.");
                //    Toast.makeText(LoginActivity.this, "Please try again 2."+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void paymentStart(String cardNumber, int cardExpMonth, int cardExpYear, String cardCVC) {
        card = new com.stripe.android.model.Card(
                cardNumber,
                cardExpMonth,
                cardExpYear,
                cardCVC
        );

        if (!card.validateCVC()) {

        } else if (!card.validateCard()) {
            Toast.makeText(SelectPaymentMethodActivity.this, "Enter valid card number", Toast.LENGTH_SHORT).show();

        } else if (!card.validateExpiryDate()) {
            Toast.makeText(SelectPaymentMethodActivity.this, "Enter valid expiry date", Toast.LENGTH_SHORT).show();

        } else if (!card.validateExpMonth()) {
            Toast.makeText(SelectPaymentMethodActivity.this, "Enter valid expiry month", Toast.LENGTH_SHORT).show();

        } else {
            mProgressDialog = new ProgressDialog(SelectPaymentMethodActivity.this);
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
                        //   Toast.makeText(getApplicationContext(),
//                                "Success",
//                                Toast.LENGTH_LONG
//                        ).show();
                        // Log.e("TOKEN", "" + token.getCard().getCVC());
//                        callAPI(token.getId(), "card", "", "");

                        if (val.getRestaurantDetailsResponse().getData().getRestaurants().getAddress() != null) {
                            if (voucherPaymentType.equalsIgnoreCase("card")) {
                                alertVoucherApply("", "card", "", "");
                            } else if (voucherPaymentType.equalsIgnoreCase("")) {
                                alertVoucherApply("", "card", "", "");
                            } else {
                                alertVoucherApply(token.getId(), "card", "", "");
                            }
                        } else {
                            Intent intent = new Intent(SelectPaymentMethodActivity.this, AddAddressManualActivity.class);
                            startActivity(intent);
                        }
                        mProgressDialog.dismiss();
                    }

                    public void onError(Exception error) {
                        // Show localized error message
                        Toast.makeText(getApplicationContext(),
                                "Failed",
                                Toast.LENGTH_LONG
                        ).show();
                        mProgressDialog.dismiss();
                    }
                }
        );


    }

    @Override
    public void onClickPos(int pos, ArrayList<String> check, List<Card> dataList) {
        if (check.contains("1")) {
            for (int i = 0; i < dataList.size(); i++) {
                check.set(i, "0");
            }
        }
        address1 = dataList.get(pos).getAddressLine1();
        address2 = dataList.get(pos).getAddressLine2();
        addressCity = dataList.get(pos).getAddressCity();
        postalCode = dataList.get(pos).getAddressPostCode();

        isCardSelected = true;
        check.set(pos, "1");
        position = pos;
        mDealCardAdapter.notifyDataSetChanged();

        alertDialogSelectCard("Do you want to procceed\nwith card ending by\n" + dataList.get(pos).getLast4CardNo());
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
                    sharedPreferencesClass.setString(sharedPreferencesClass.DEFAULT_ADDRESS, "");
                    Constants.ORDER_STATUS = 1;

                    Constants.switchActivity(SelectPaymentMethodActivity.this, OrderStatusActivity.class);
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

    public void alertVoucherApply(final String token, final String paymentType, final String exDate, final String exYear) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.addnote_success_dialog, null);
        final AlertDialog noteDialog = new AlertDialog.Builder(this).create();
        noteDialog.setView(mDialogView);
        noteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvMessage, noTv;

        mDialogView.findViewById(R.id.view).setVisibility(View.VISIBLE);
        noTv = mDialogView.findViewById(R.id.noTv);
        noTv.setVisibility(View.VISIBLE);

        tvMessage = mDialogView.findViewById(R.id.message);
        tvMessage.setText("This voucher is valid for " + paymentType + " only." + "\nDo you want to proceed?");
        tvMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen._14sdp));


        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!voucherCode.equalsIgnoreCase(paymentType)) {
                    totalAmount = totalAmount + voucherAmount;
                }
                if (Constants.isInternetConnectionAvailable(300)) {
                    callAPI(token, paymentType, exDate, exYear);
                } else {
                    dialogNoInternetConnection("Please check internet connection.", 0);
                }

                noteDialog.dismiss();
            }
        });

        noTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                noteDialog.dismiss();
            }
        });


        noteDialog.show();
    }

    public void alertMessage() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.pop_first_time_payment, null);
        final AlertDialog cardDialog = new AlertDialog.Builder(this).create();
        TextView txtMsg = mDialogView.findViewById(R.id.txt_msg);
        cardDialog.setView(mDialogView);
        cardDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialogView.findViewById(R.id.tv_PayByCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectPaymentMethodActivity.this, AddNewCardActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("ORDER_TOTAL", totalAmount);
                i.putExtra("ORDER_SUB_TOTAL", subTotalAmount);
                i.putExtra("deliveryCharge", deliveryFee);
                i.putExtra("orderType", orderType);
                i.putExtra("voucherDiscount", voucherDiscount);
                i.putExtra("notes", notes);
                i.putExtra("appliedVoucherCode", voucherCode);
                i.putExtra("appliedVoucherAmount", voucherAmount);
                i.putExtra("appliedVoucherPaymentType", voucherPaymentType);
                startActivity(i);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                cardDialog.dismiss();
            }
        });
        mDialogView.findViewById(R.id.tv_Cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                cardDialog.dismiss();
            }
        });
        cardDialog.show();
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

    public void dialogNoInternetConnection(String message, final int status) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.addnote_success_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setView(mDialogView);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final Animation animShake = AnimationUtils.loadAnimation(mContext, R.anim.shake);

        TextView tvMessage = mDialogView.findViewById(R.id.message);
        tvMessage.setText(message);
        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constants.isInternetConnectionAvailable(300)) {
                    alertDialog.dismiss();
                    if (status == 1) {
                        getCardList();
                    }

                } else mDialogView.findViewById(R.id.okTv).startAnimation(animShake);

            }
        });

        alertDialog.show();
    }
}
