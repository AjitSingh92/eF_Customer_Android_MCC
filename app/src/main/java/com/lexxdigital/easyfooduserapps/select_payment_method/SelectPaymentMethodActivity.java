package com.lexxdigital.easyfooduserapps.select_payment_method;

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

import com.google.firebase.analytics.FirebaseAnalytics;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.adapters.SaveCardAdapter;
import com.lexxdigital.easyfooduserapps.add_card.AddNewCardActivity;
import com.lexxdigital.easyfooduserapps.add_manual_address.AddAddressManualActivity;
import com.lexxdigital.easyfooduserapps.api.CardListInterface;
import com.lexxdigital.easyfooduserapps.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapps.model.card_list_request.CardListRequest;
import com.lexxdigital.easyfooduserapps.model.card_list_response.Card;
import com.lexxdigital.easyfooduserapps.model.card_list_response.CardListResponse;
import com.lexxdigital.easyfooduserapps.order_status.OrderStatusActivity;
import com.lexxdigital.easyfooduserapps.restaurant_details.RestaurantDetailsActivity;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.CartDatRequest;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.CartData;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MealProduct;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategoryCart;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuProductSize;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.Modifier;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.SizeModifier;
import com.lexxdigital.easyfooduserapps.select_payment_method.api.CheckoutRequestInterface;
import com.lexxdigital.easyfooduserapps.select_payment_method.model.checkout_request.CheckoutRequest;
import com.lexxdigital.easyfooduserapps.select_payment_method.model.checkout_response.CheckoutResponse;
import com.lexxdigital.easyfooduserapps.utility.ApiClient;
import com.lexxdigital.easyfooduserapps.utility.Constants;
import com.lexxdigital.easyfooduserapps.utility.GlobalValues;
import com.lexxdigital.easyfooduserapps.utility.SharedPreferencesClass;

import java.util.ArrayList;
import java.util.HashMap;
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
    private FirebaseAnalytics mFirebaseAnalytics;
    private String deliveryTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_payment_method);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

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
        Bundle extras = getIntent().getExtras();
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

            deliveryTime = extras.getString(Constants.ORDER_TIME);
        }

        if (sharedPreferencesClass.getInt(sharedPreferencesClass.NUMBER_OF_ORDERS) > 0) {
            payWithCash.setVisibility(View.VISIBLE);
        } else {
            payWithCash.setVisibility(View.GONE);
        }

        if (Constants.isInternetConnectionAvailable(300)) {
            getCardList();
        } else {
            dialogNoInternetConnection("Please check internet connection.", 1);
        }


        findViewById(R.id.btn_backToMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(SelectPaymentMethodActivity.this, RestaurantDetailsActivity.class);
                    if (val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantId() != null && !val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantId().equalsIgnoreCase("")) {
                        i.putExtra("RESTAURANTID", val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantId());
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();

                }
            }
        });

        makeData2();
    }



    private CartDatRequest makeData2() {
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
            cartDatRequest.setRestaurantSlug(sharedPreferencesClass.getString(sharedPreferencesClass.RESTAURANT_NAME_SLUG));


            /*----------------------------------*/
            HashMap<Integer, List<SizeModifier>> sizeModifiersDataMap = new HashMap<>();
            CartData cartDataForRequest = new CartData();


            CartData cartData = db.getCartData();
            cartDataForRequest.setSpecialOffers(cartData.getSpecialOffers());
            cartDataForRequest.setUpsellProducts(cartData.getUpsellProducts());

            int count = 0;
            for (MenuCategoryCart menuCategoryCart : cartData.getMenuCategoryCarts()) {
                List<MenuProduct> menuProducts = menuCategoryCart.getMenuProducts();
                for (MenuProduct menuProduct : menuProducts) {
                    List<MealProduct> mealProducts = menuProduct.getMealProducts();
                    if (mealProducts != null) {
                        for (MealProduct mealProduct : mealProducts) {
                            List<MenuProductSize> menuProductSize = mealProduct.getMenuProductSize();
                            if (menuProductSize != null) {
                                for (MenuProductSize productSize : menuProductSize) {
                                    sizeModifiersDataMap.put(count, productSize.getSizeModifiers());
                                }
                            }

                        }
                    }
                }
                count++;
            }

            count = 0;
            List<MenuCategoryCart> menuCategoryCartsRequest = new ArrayList<>();
            for (MenuCategoryCart menuCategoryCart : cartData.getMenuCategoryCarts()) {
                MenuCategoryCart menuCategoryCartRequest = menuCategoryCart;

                List<MenuProduct> menuProductsRequest = new ArrayList<>();

                for (MenuProduct menuProduct : menuCategoryCart.getMenuProducts()) {
                    MenuProduct menuProductRequest = menuProduct;

                    List<MealProduct> mealProducts = menuProduct.getMealProducts();
                    if (mealProducts != null) {
                        List<Modifier> mealProductNoMenuProductSize = new ArrayList<>();

                        for (int i = 0; i < mealProducts.size(); i++) {
                            if (mealProducts.get(i).getMenuProductSize() == null) {
                                Modifier modifier = new Modifier();
                                modifier.setAmount(0d);
                                modifier.setModifierProductPrice("0");
                                modifier.setOriginalAmount1(0d);
                                modifier.setOriginalQuantity(String.valueOf(mealProducts.get(i).getQuantity()));
                                modifier.setProductId(mealProducts.get(i).getProductId());
                                modifier.setProductName(mealProducts.get(i).getProductName());
                                modifier.setQuantity(String.valueOf(mealProducts.get(i).getQuantity()));
                                modifier.setUnit("");

                                mealProductNoMenuProductSize.add(modifier);
                            }
                        }

                        List<SizeModifier> sizeModifiers = sizeModifiersDataMap.get(count);
                        for (int i = 0; i < sizeModifiers.size(); i++) {
                            if (i + 1 == sizeModifiers.size()) {
                                List<Modifier> modifier = sizeModifiers.get(i).getModifier();
                                modifier.addAll(mealProductNoMenuProductSize);
                                sizeModifiers.get(i).setModifier(modifier);
                            }
                        }
                        List<MealProduct> mealProductsRequest = new ArrayList<>();
                        for (int i = 0; i < mealProducts.size(); i++) {
                            if (i + 1 == mealProducts.size()) {
                                MealProduct mealProduct = mealProducts.get(0);
                                mealProduct.setSizeModifiers(sizeModifiers);
                                mealProductsRequest.add(mealProduct);
                            } else {
                                MealProduct mealProduct = mealProducts.get(i);
                                if (mealProducts.get(i).getMenuProductSize() != null && mealProducts.get(i).getMenuProductSize().size() > 0) {
                                    mealProduct.setSizeModifiers(mealProducts.get(i).getMenuProductSize().get(0).getSizeModifiers());
                                }
                                mealProductsRequest.add(mealProduct);
                            }
                        }
                        List<MealProduct> mealProductsRequests = new ArrayList<>();
                        if (mealProductsRequest.size() > 0) {
                            mealProductsRequests.add(mealProductsRequest.get(0));
                        }
                        menuProductRequest.setMealProducts(mealProductsRequests);

                    }
                    menuProductsRequest.add(menuProductRequest);
                }
                menuCategoryCartRequest.setMenuProducts(menuProductsRequest);
                menuCategoryCartsRequest.add(menuCategoryCartRequest);
                count++;
            }

            cartDataForRequest.setMenuCategoryCarts(menuCategoryCartsRequest);

            cartDatRequest.setCartData(cartDataForRequest);

            Log.e("SIZE MODIFIER", cartDatRequest.toString());
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
                    if (val.getRestaurantDetailsResponse().getData().getRestaurants().getAddress() != null) {
                        if (voucherPaymentType != null && !voucherPaymentType.equals("") && voucherPaymentType.equalsIgnoreCase("card")) {
                            if (Constants.isInternetConnectionAvailable(300)) {
                                callAPI("", "card", exDate.getText().toString(), exYear.getText().toString());
                            } else {
                                dialogNoInternetConnection("Please check internet connection.", 0);
                            }

                        } else if (voucherPaymentType == null || voucherPaymentType.equalsIgnoreCase("")) {
                            if (Constants.isInternetConnectionAvailable(300)) {
                                callAPI("", "card", exDate.getText().toString(), exYear.getText().toString());
                            } else {
                                dialogNoInternetConnection("Please check internet connection.", 0);
                            }

                        } else {
                            alertVoucherApply(voucherPaymentType, false);
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
                cardDialog.dismiss();
                alertDialogCVV(null);

            }
        });
        mDialogView.findViewById(R.id.cancelTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardDialog.dismiss();
            }
        });

        cardDialog.show();
    }


    @OnClick({R.id.add_new_card, R.id.paywith_card_tv, R.id.pay_with_cash})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_new_card:
                if (voucherPaymentType != null && !voucherPaymentType.equals("") && voucherPaymentType.equalsIgnoreCase("card")) {
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
                } else if (voucherPaymentType == null || voucherPaymentType.equalsIgnoreCase("")) {
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
                } else {
                    alertVoucherApply(voucherPaymentType, true);
                }
                break;
            case R.id.paywith_card_tv:
                break;
            case R.id.pay_with_cash:
                try {
                    if (sharedPreferencesClass.getInt(sharedPreferencesClass.NUMBER_OF_ORDERS) > 0) {
                        if (voucherPaymentType != null && !voucherPaymentType.equals("") && voucherPaymentType.equalsIgnoreCase("cash")) {
                            if (Constants.isInternetConnectionAvailable(300)) {
                                callAPI("", "cash", "", "");
                            } else {
                                dialogNoInternetConnection("Please check internet connection.", 0);
                            }
                        } else if (voucherPaymentType == null || voucherPaymentType.equalsIgnoreCase("")) {
                            if (Constants.isInternetConnectionAvailable(300)) {
                                callAPI("", "cash", "", "");
                            } else {
                                dialogNoInternetConnection("Please check internet connection.", 0);
                            }
                        } else {
                            alertVoucherApply(voucherPaymentType, false);
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

        if (orderType.length() > 0) {
            for (int i = 0; i < dataList.size() - 1; i++) {
                if (dataList.get(i).getIsDefault() == 1)
                    alertDialogSelectCard("Do you want to procceed\nwith card ending by\n" + dataList.get(i).getLast4CardNo());
            }

        }
    }


    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    @Override
    public void onBackPressed() {
        return;
    }

    public void callAPI(String token, String paymentType, String exDate, String exYear) {
        mProgressDialog.show();
        CheckoutRequestInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(CheckoutRequestInterface.class);
        if (!paymentType.equalsIgnoreCase("cash"))
            detail = dataList.get(position);

        final CheckoutRequest request = new CheckoutRequest();
        request.setRestaurantId(val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantId());
        request.setCustomerId(sharedPreferencesClass.getString(sharedPreferencesClass.USER_ID));
        request.setPaymentMode(paymentType);
        request.setIsTomorrow(sharedPreferencesClass.getString(sharedPreferencesClass.IS_TOMORROW));
        request.setDeliveryOption(orderType.toLowerCase());
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
        request.setCardData(makeData2());
        Call<CheckoutResponse> call3 = apiInterface.mCheckout(request);
        call3.enqueue(new Callback<CheckoutResponse>() {
            @Override
            public void onResponse(Call<CheckoutResponse> call, Response<CheckoutResponse> response) {
                mProgressDialog.dismiss();
                try {
                    if (response.code() == 200 && response.body().getSuccess()) {
                        sharedPreferencesClass.setOrderIDKey(response.body().getData().getOrder_number());
                        sharedPreferencesClass.setInt(sharedPreferencesClass.NUMBER_OF_ORDERS, (sharedPreferencesClass.getInt(sharedPreferencesClass.NUMBER_OF_ORDERS) + 1));
                        Constants.MAX_LENGTH = 0;
                        alertDialogOrderPlaced(response.body().getMessage(), true, request, response.body().getData().getOrder_number());

                    } else if (response.code() == 200 && !response.body().getSuccess()) {
                        Toast.makeText(val, "", Toast.LENGTH_SHORT).show();
                        alertDialogCVV("Please enter valid expiry date");
                    } else {
                        alertDialogOrderPlaced("Transaction Failed\n" +
                                "Your Order could not be processed", false, request, "");
                        address1 = "";
                        address2 = "";
                        addressCity = "";
                        postalCode = "";
                    }
                } catch (Exception e) {
                    mProgressDialog.dismiss();
                    alertDialogOrderPlaced("Transaction Failed\n" +
                            "Your Order could not be processed", false, request, "");
                    address1 = "";
                    address2 = "";
                    addressCity = "";
                    postalCode = "";
                }
            }

            @Override
            public void onFailure(Call<CheckoutResponse> call, Throwable t) {
                mProgressDialog.dismiss();
                alertDialogOrderPlaced("Transaction Failed\n" +
                        "Your Order could not be processed", false, request, "");
                address1 = "";
                address2 = "";
                addressCity = "";
                postalCode = "";
            }
        });
    }


    public void getCardList() {
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
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                    noCards.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<CardListResponse> call, Throwable t) {
                noCards.setVisibility(View.VISIBLE);
            }
        });
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
                    sharedPreferencesClass.setString(sharedPreferencesClass.RESTUARANT_ID, "");
                    sharedPreferencesClass.setString(sharedPreferencesClass.RESTUARANT_NAME, "");
                    sharedPreferencesClass.setString(sharedPreferencesClass.NOTEPAD, "");
                    sharedPreferencesClass.setString(sharedPreferencesClass.DEFAULT_ADDRESS, null);
                    Constants.ORDER_STATUS = 1;
                    Intent intent = new Intent(SelectPaymentMethodActivity.this, OrderStatusActivity.class);
                    intent.putExtra("order_no", orderNum);
                    intent.putExtra(Constants.PAYMENT_MODE, request.getPaymentMode());
                    intent.putExtra(Constants.RESTAURENT_NAME, restaurentName);
                    intent.putExtra(Constants.TOTAL_COST, String.valueOf(request.getOrderTotal()));
                    intent.putExtra(Constants.PHONE_NUMBER, "9876543210");
                    intent.putExtra(Constants.CUSTOMER_ID, request.getCustomerId());
                    intent.putExtra(Constants.ORDER_TIME, deliveryTime);
                    intent.putExtra(Constants.ORDER_TYPE, request.getDeliveryOption());
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

    public void alertVoucherApply(String paymentType, final Boolean addCard) {
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
                totalAmount = subTotalAmount;
                voucherDiscount = 0.d;
                voucherAmount = 0.d;
                voucherCode = "";
                if (addCard) {
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
                    SelectPaymentMethodActivity.this.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                } else {
                    if (Constants.isInternetConnectionAvailable(300)) {
                        callAPI("", "cash", "", "");
                    } else {
                        dialogNoInternetConnection("Please check internet connection.", 0);
                    }
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

                if (voucherPaymentType != null && !voucherPaymentType.equals("") && voucherPaymentType.equalsIgnoreCase("card")) {
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

                } else if (voucherPaymentType == null || voucherPaymentType.equalsIgnoreCase("")) {
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
                } else {
                    alertVoucherApply(voucherPaymentType, true);
                }
            }
        });
        mDialogView.findViewById(R.id.tv_Cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
