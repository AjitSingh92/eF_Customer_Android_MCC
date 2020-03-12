package com.lexxdigital.easyfooduserapps.order_details_activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.adapters.order_details.OrderDetailsMenuProductAdapter;
import com.lexxdigital.easyfooduserapps.adapters.order_details.OrderDetailsSpecialOfferProductAdapter;
import com.lexxdigital.easyfooduserapps.adapters.order_details.OrderDetailsUpsellProductAdapter;
import com.lexxdigital.easyfooduserapps.api.CancelInterface;
import com.lexxdigital.easyfooduserapps.api.OrderDetailsInterface;
import com.lexxdigital.easyfooduserapps.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapps.customer_review.CustomerReviewProcess;
import com.lexxdigital.easyfooduserapps.dashboard.DashboardActivity;
import com.lexxdigital.easyfooduserapps.model.cancelorder.CancelOrderResponse;
import com.lexxdigital.easyfooduserapps.model.cancelorder.CancelRequest;
import com.lexxdigital.easyfooduserapps.model.myorder.OrderDetails;
import com.lexxdigital.easyfooduserapps.model.myorder.PreviousOrderDetail;
import com.lexxdigital.easyfooduserapps.model.order_again.OrderAgainRequest;
import com.lexxdigital.easyfooduserapps.model.order_again.OrderAgainResponse;
import com.lexxdigital.easyfooduserapps.model.order_details.Data;
import com.lexxdigital.easyfooduserapps.model.order_details.OrderDetailsRequest;
import com.lexxdigital.easyfooduserapps.model.order_details.OrderDetailsResponse;
import com.lexxdigital.easyfooduserapps.model.previous_order.datamodel.MealProduct;
import com.lexxdigital.easyfooduserapps.model.previous_order.datamodel.Menu;
import com.lexxdigital.easyfooduserapps.model.previous_order.datamodel.PreviousOrderDetailsModel;
import com.lexxdigital.easyfooduserapps.model.previous_order.datamodel.ProductModifier;
import com.lexxdigital.easyfooduserapps.model.previous_order.datamodel.SizeModifier;
import com.lexxdigital.easyfooduserapps.model.previous_order.datamodel.SizeModifierProduct;
import com.lexxdigital.easyfooduserapps.order_status.OrderStatusActivity;
import com.lexxdigital.easyfooduserapps.restaurant_details.api.RestaurantDetailsInterface;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.CartData;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategoryCart;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;
import com.lexxdigital.easyfooduserapps.utility.ApiClient;
import com.lexxdigital.easyfooduserapps.utility.Constants;
import com.lexxdigital.easyfooduserapps.utility.GlobalValues;
import com.lexxdigital.easyfooduserapps.utility.SharedPreferencesClass;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderDetailActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView, recyclerViewSpecialOffers, recyclerViewUpsell;
    OrderDetailsMenuProductAdapter orderDetailsAdapter;
    OrderDetailsSpecialOfferProductAdapter specialOfferProductAdapter;
    OrderDetailsUpsellProductAdapter upsellProductAdapter;
    List<MenuProduct> menuProducts = new ArrayList<>();
    LinearLayout orderItemListView;
    TextView restName, addReview, orderAgain, orderNo, orderDate, ivToolBarTitle, addressTextview, subtotal, dicsRate, delivRate, totalPrice, note, orderStatus, tvPaidBy, tvAddressType, tvAddress;
    ImageView restImage, ivToolBarbackTv;
    LinearLayout llTrack, llReview, llCacelOrder;
    CircleImageView restLogo;
    View lineBelowNotes, lineBelowAddress;
    GlobalValues val;
    String strOrderNo;
    CartData dataList = new CartData();
    Data data;
    float fltRating;
    String orderId, restoId, restoName, restoLogo, restoImage, restoAddress, OrdAvgRating;
    private Dialog dialog;
    SharedPreferencesClass sharePre;
    private DatabaseHelper db;
    Gson gson = new Gson();
    PreviousOrderDetail previousOrderDetailList;
    OrderDetails orderDetails;
    public static String menuCategory;
    private List<MenuCategoryCart> orderDetail;
    TextView reasonForCancel;
    FirebaseAnalytics mFirebaseAnalytics;

    private String orderNum, paymentMode, restaurentName, orderTotal, phoneNum, customId, deliveryTime, delevOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constants.setStatusBarGradiant(this);
        setContentView(R.layout.activity_order_details);
        IntentFilter intentFilter = new IntentFilter("status");
        LocalBroadcastManager.getInstance(OrderDetailActivity.this).registerReceiver(broadcastReceiver, intentFilter);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        db = new DatabaseHelper(this);
        sharePre = new SharedPreferencesClass(this);
        addReview = findViewById(R.id.add_review);
        addReview.setOnClickListener(this);
        orderAgain = findViewById(R.id.order_again);
        orderAgain.setOnClickListener(this);
        restName = findViewById(R.id.rest_name);
        orderNo = findViewById(R.id.order_no);
        orderDate = findViewById(R.id.order_date);
        subtotal = findViewById(R.id.sub_total_price);
        note = findViewById(R.id.notes);
        dicsRate = findViewById(R.id.disc_);
        delivRate = findViewById(R.id.delivery_rate);
        restImage = findViewById(R.id.rest_image);
        restLogo = findViewById(R.id.rest_logo);
        totalPrice = findViewById(R.id.total_price);
        ivToolBarbackTv = findViewById(R.id.ivToolBarbackTv);
        ivToolBarTitle = findViewById(R.id.tvToolbarTitle);
        lineBelowNotes = findViewById(R.id.viewline_below_notes);
        lineBelowAddress = findViewById(R.id.viewline_below_address);
        orderStatus = findViewById(R.id.order_status);
        tvPaidBy = findViewById(R.id.tv_PaidBy);
        llTrack = findViewById(R.id.ll_track);
        llTrack.setOnClickListener(this);
        llReview = findViewById(R.id.ll_review);
        llCacelOrder = findViewById(R.id.ll_cancel);
        llCacelOrder.setOnClickListener(this);
        tvAddressType = findViewById(R.id.tv_AddressType);
        tvAddress = findViewById(R.id.tv_Address);
        reasonForCancel = findViewById(R.id.tv_reasonForCancel);
        orderItemListView = findViewById(R.id.orderItemList);
        Bundle b = new Bundle();
        b = getIntent().getExtras();
        strOrderNo = b.getString("order_no");


        orderNum = getIntent().getStringExtra("order_no");
        paymentMode = getIntent().getStringExtra(Constants.PAYMENT_MODE);
        restaurentName = getIntent().getStringExtra(Constants.RESTAURENT_NAME);
        orderTotal = getIntent().getStringExtra(Constants.TOTAL_COST);
        phoneNum = getIntent().getStringExtra(Constants.PHONE_NUMBER);
        customId = getIntent().getStringExtra(Constants.CUSTOMER_ID);
        deliveryTime = getIntent().getStringExtra(Constants.ORDER_TIME);
        delevOption = getIntent().getStringExtra(Constants.ORDER_TYPE);


        val = (GlobalValues) getApplicationContext();
        Constants.setStatusBarGradiant(OrderDetailActivity.this);

        dialog = new Dialog(this);
        dialog.setTitle("");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        ivToolBarbackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });

        getCardList();
    }


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constants.isInternetConnectionAvailable(300)) {
                getCardList();

            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {
            if (data.hasExtra("done")) {
                if (data.getBooleanExtra("done", false)) {
                    addReview.setVisibility(View.GONE);
                }
            }
        }
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycler_order_details_sub);
        orderDetailsAdapter = new OrderDetailsMenuProductAdapter(this);
        @SuppressLint("WrongConstant")
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        recyclerView.setAdapter(orderDetailsAdapter);

        /* ================================================================================================*/
        recyclerViewSpecialOffers = findViewById(R.id.recycler_special_offers);
        specialOfferProductAdapter = new OrderDetailsSpecialOfferProductAdapter(this);
        @SuppressLint("WrongConstant")
        LinearLayoutManager horizontalLayoutManagaer2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewSpecialOffers.setLayoutManager(horizontalLayoutManagaer2);
        recyclerViewSpecialOffers.setAdapter(specialOfferProductAdapter);

        /* ================================================================================================*/
        recyclerViewUpsell = findViewById(R.id.recycler_upsell);
        upsellProductAdapter = new OrderDetailsUpsellProductAdapter(this);
        @SuppressLint("WrongConstant")
        LinearLayoutManager horizontalLayoutManagaer3 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewUpsell.setLayoutManager(horizontalLayoutManagaer3);
        recyclerViewUpsell.setAdapter(upsellProductAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_track:
                try {
                    if (Constants.isInternetConnectionAvailable(3000)) {
                        Intent intent = new Intent(this, OrderStatusActivity.class);
                        intent.putExtra("order_no", orderNum);
                        intent.putExtra(Constants.PAYMENT_MODE, paymentMode);
                        intent.putExtra(Constants.RESTAURENT_NAME, restaurentName);
                        intent.putExtra(Constants.TOTAL_COST, orderTotal);
                        intent.putExtra(Constants.PHONE_NUMBER, phoneNum);
                        intent.putExtra(Constants.CUSTOMER_ID, customId);
                        intent.putExtra(Constants.ORDER_TIME, deliveryTime);
                        intent.putExtra(Constants.ORDER_TYPE, delevOption);

                        sharePre.setOrderIDKey(strOrderNo);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        this.startActivity(intent);
                        this.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                    } else {
                        Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.add_review:
                Intent intent = new Intent(getApplicationContext(), CustomerReviewProcess.class);
                intent.putExtra("orderId", orderId);
                intent.putExtra("restoId", restoId);
                intent.putExtra("orderNo", strOrderNo);
                intent.putExtra("restologo", restoLogo);
                intent.putExtra("restoname", restoName);
                intent.putExtra("restoimage", restoImage);
                intent.putExtra("restoAdd", restoAddress);
                startActivityForResult(intent, 200);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

                break;

            case R.id.order_again:

                getOrderAgain(previousOrderDetailList.getOrderNum());

                break;
            case R.id.ll_cancel:
                showDialog("Are you sure Cancel order!");
                break;

        }

    }

    public void getCardList() {

        OrderDetailsInterface apiInterface = ApiClient.getClient(this).create(OrderDetailsInterface.class);
        OrderDetailsRequest request = new OrderDetailsRequest(strOrderNo, val.getLoginResponse().getData().getUserId());

        Call<PreviousOrderDetailsModel> call = apiInterface.mOrderDetailsNew(request);
        call.enqueue(new Callback<PreviousOrderDetailsModel>() {

            @Override
            public void onResponse(Call<PreviousOrderDetailsModel> call, Response<PreviousOrderDetailsModel> response) {
                try {
                    dialog.hide();
                    if (response.body().getSuccess()) {
                        initOrder(response.body().getData());

                    } else {
                        dialog.hide();
                    }
                } catch (Exception e) {
                    dialog.hide();

                }
            }

            @Override
            public void onFailure(Call<PreviousOrderDetailsModel> call, Throwable t) {
                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                dialog.hide();
            }

        });
    }

    private void initOrder(PreviousOrderDetailsModel.Data data) throws JSONException {
        Glide.with(OrderDetailActivity.this).load(data.getRestaurantImage()).apply(new RequestOptions()
                .placeholder(R.drawable.default_restaurant_image))
                .into(restImage);
        Glide.with(OrderDetailActivity.this).load(data.getRestaurantLogo()).apply(new RequestOptions()
                .placeholder(R.drawable.default_restaurant_image))
                .into(restLogo);

        orderId = data.getOrderId();
        restoId = data.getRestaurantId();
        restoLogo = data.getRestaurantLogo();
        restoName = data.getRestaurantName();
        restoImage = data.getRestaurantImage();
        if (data.getCustomerDeliveryAddress().getCustomerDeliveryAddress1() != null) {
            String address1, address2, city, postal, country, addressType;
            String address = "";
            address1 = data.getCustomerDeliveryAddress().getCustomerDeliveryAddress1();
            address2 = data.getCustomerDeliveryAddress().getCustomerDeliveryAddress2();
            city = data.getCustomerDeliveryAddress().getCustomerDeliveryCity();
            postal = data.getCustomerDeliveryAddress().getCustomerDeliveryPostCode();
            country = data.getCustomerDeliveryAddress().getCustomerDeliveryCountry();
            // addressType=response.body().getData().getCustomerDeliveryAddress().getCustomerDeliveryAddressType();
            Log.e("order details activity", "onBindViewHolder: " + address1 + " " + address2 + " " + city + " " + postal + " " + country + " address: " + address);
            if (address1 != null && !address1.trim().equals("")) {
                address = address + address1;
            } else if (address2 != null && !address2.trim().equals("")) {
                address = address + ", " + address2;
            } else if (city != null && !city.trim().equals("")) {
                address = address + ", " + city;
            } else if (postal != null && !postal.trim().equals("")) {
                address = address + ", " + postal;
            } else if (country != null && !country.trim().equals("")) {
                address = address + ", " + country;

            } else if (address != null && !address.trim().equalsIgnoreCase("")) {
                restoAddress = address;
                tvAddress.setText(restoAddress);
                tvAddress.setVisibility(View.VISIBLE);
                lineBelowAddress.setVisibility(View.VISIBLE);
            } else {
                tvAddress.setVisibility(View.GONE);
                lineBelowAddress.setVisibility(View.GONE);
            }
        }


        restName.setText(data.getRestaurantName());
        ivToolBarTitle.setText(data.getRestaurantName());
        orderNo.setText(data.getOrderNum());
        if (data.getOrderStatus() != null && data.getOrderStatus().trim().length() > 0) {
            String _orderStatus = data.getOrderStatus();
            String firstChat = String.valueOf(_orderStatus.charAt(0)).toUpperCase();
            if (_orderStatus.equalsIgnoreCase("pending")) {
                if (delevOption.equalsIgnoreCase("collection")) {
                    orderStatus.setText("New");
                } else {
                    orderStatus.setText("New");
                }
            } else if (_orderStatus.equalsIgnoreCase("accepted")) {
                if (delevOption.equalsIgnoreCase("collection")) {
                    orderStatus.setText("Accepted");
                } else {
                    orderStatus.setText("Accepted");
                }
            } else if (_orderStatus.equalsIgnoreCase("preparing")) {
                if (delevOption.equalsIgnoreCase("collection")) {
                    orderStatus.setText("Preparing");
                } else {
                    orderStatus.setText("Preparing");
                }
            } else if (_orderStatus.equalsIgnoreCase("out_for_delivery")) {
                if (delevOption.equalsIgnoreCase("collection")) {
                    orderStatus.setText("Ready To Collect");
                } else {
                    orderStatus.setText("Out for Delivery");
                }
            } else if (_orderStatus.equalsIgnoreCase("rejected")) {
                if (delevOption.equalsIgnoreCase("collection")) {
                    orderStatus.setText("Rejected");
                } else {
                    orderStatus.setText("Rejected");
                }
            } else if (_orderStatus.equalsIgnoreCase("delivered")) {
                if (delevOption.equalsIgnoreCase("collection")) {
                    orderStatus.setText("Collected");
                } else {
                    orderStatus.setText("Delivered");
                }
            } else {
                if (delevOption.equalsIgnoreCase("collection")) {
                    orderStatus.setText("New");
                } else {
                    orderStatus.setText("New");
                }
            }
        }

        if (data.getOrderStatus().equalsIgnoreCase("accepted") || data.getOrderStatus().equalsIgnoreCase("preparing") || data.getOrderStatus().equalsIgnoreCase("out_for_delivery")) {
            llTrack.setVisibility(View.VISIBLE);
            llReview.setVisibility(View.GONE);
            llCacelOrder.setVisibility(View.GONE);
        }

        orderDate.setText(data.getOrderDateTime());
        subtotal.setText(getString(R.string.currency) + data.getOrderSubtotal());
        dicsRate.setText(getString(R.string.currency) + data.getDiscountAmount());
        delivRate.setText(getString(R.string.currency) + data.getDeliveryCharge());
        if (data.getPaymentMode() != null && data.getPaymentMode().trim().length() > 0) {
            String _paymentMode = data.getPaymentMode();
            String firstChat = String.valueOf(_paymentMode.charAt(0)).toUpperCase();
            tvPaidBy.setText("Paid by " + firstChat + _paymentMode.substring(1, _paymentMode.length()));
        }
        totalPrice.setText(getString(R.string.currency) + data.getOrderTotal());
//        orderItemListView
//        sub_product_order_list
        Gson gson = new Gson();
        for (int i = 0; i < data.getOrderDetails().getMenu().size(); i++) {
            LayoutInflater inflater = LayoutInflater.from(this);
            View root = inflater.inflate(R.layout.order_list_item, null);
            TextView title = root.findViewById(R.id.title);
            TextView price = root.findViewById(R.id.price);
            Menu menu = data.getOrderDetails().getMenu().get(i);
            title.setText(menu.getQty() + "x " + menu.getName());
            price.setText(getString(R.string.currency) + menu.getPrice());
            orderItemListView.addView(root);

            if (menu.getOptions().getProductModifiers() != null) {
                List<ProductModifier> productModifiers = menu.getOptions().getProductModifiers();
                for (int j = 0; j < menu.getOptions().getProductModifiers().size(); j++) {
                    for (int k = 0; k < productModifiers.get(j).getModifierProducts().size(); k++) {
                        LayoutInflater inflaterModi = LayoutInflater.from(this);
                        View rootModi = inflaterModi.inflate(R.layout.order_list_item_modifiers, null);
                        TextView titleModi = rootModi.findViewById(R.id.title);
                        TextView priceModi = rootModi.findViewById(R.id.price);

                        titleModi.setText(productModifiers.get(j).getModifierProducts().get(k).getQuantity() + "x " + productModifiers.get(j).getModifierProducts().get(k).getProductName());
                        priceModi.setText(getString(R.string.currency) + productModifiers.get(j).getModifierProducts().get(k).getModifierProductPrice());
                        orderItemListView.addView(rootModi);
                    }
                }
            }
            if (menu.getOptions().getMealProducts() != null) {
                List<MealProduct> mealProducts = menu.getOptions().getMealProducts();
                for (int j = 0; j < mealProducts.size(); j++) {
                    List<SizeModifier> sizeModifiers = mealProducts.get(j).getSizeModifiers();
                    for (int k = 0; k < sizeModifiers.size(); k++) {
                        List<SizeModifierProduct> sizeModifierProducts = sizeModifiers.get(k).getSizeModifierProducts();
                        for (int l = 0; l < sizeModifierProducts.size(); l++) {
                            LayoutInflater inflaterSizeModi = LayoutInflater.from(this);
                            View rootSizeModi = inflaterSizeModi.inflate(R.layout.order_list_item_modifiers, null);
                            TextView titleModi = rootSizeModi.findViewById(R.id.title);
                            TextView priceModi = rootSizeModi.findViewById(R.id.price);

                            titleModi.setText(sizeModifierProducts.get(l).getQuantity() + "x " + sizeModifierProducts.get(l).getProductName());
                            priceModi.setText(getString(R.string.currency) + sizeModifierProducts.get(l).getAmount());
                            orderItemListView.addView(rootSizeModi);
                        }
                    }
                }
            }
            if (menu.getOptions().getSize() != null && menu.getOptions().getSize().getProductSizeName() != null) {

                LayoutInflater sizeModiInflater = LayoutInflater.from(this);
                View rootSize = sizeModiInflater.inflate(R.layout.order_list_item_modifiers, null);
                TextView titleSizeModi = rootSize.findViewById(R.id.title);
                TextView priceSizeModi = rootSize.findViewById(R.id.price);

                titleSizeModi.setText(menu.getOptions().getSize().getQuantity() + "x " + menu.getOptions().getSize().getProductSizeName());
                priceSizeModi.setText(getString(R.string.currency) + menu.getOptions().getSize().getProductSizePrice());
                orderItemListView.addView(rootSize);

                for (int j = 0; j < menu.getOptions().getSize().getSizeModifiers().size(); j++) {
                    List<SizeModifierProduct> sizeModifierProducts = menu.getOptions().getSize().getSizeModifiers().get(j).getSizeModifierProducts();
                    for (int k = 0; k < sizeModifierProducts.size(); k++) {

                        LayoutInflater inflaterSizeModi = LayoutInflater.from(this);
                        View rootSizeModi = inflaterSizeModi.inflate(R.layout.order_list_item_modifiers, null);
                        TextView titleModi = rootSizeModi.findViewById(R.id.title);
                        TextView priceModi = rootSizeModi.findViewById(R.id.price);

                        titleModi.setText(sizeModifierProducts.get(k).getQuantity() + "x " + sizeModifierProducts.get(k).getProductName());
                        priceModi.setText(getString(R.string.currency) + sizeModifierProducts.get(k).getAmount());
                        orderItemListView.addView(rootSizeModi);
                    }


                }
            }
        }

    }



    public void cancelOrder() {
        dialog.show();
        CancelInterface apiInterface = ApiClient.getClient(this).create(CancelInterface.class);
        Log.e("canorder", "cancelOrder:  order no:" + strOrderNo);
        CancelRequest request = new CancelRequest();
        request.setOrderNumber(strOrderNo);
        Call<CancelOrderResponse> call = apiInterface.mCancelOrder(request);
        call.enqueue(new Callback<CancelOrderResponse>() {
            @Override
            public void onResponse(Call<CancelOrderResponse> call, Response<CancelOrderResponse> response) {
                if (response.body().getSuccess()) {
                    dialog.hide();
                    getCardList();

                } else {
                    dialog.hide();
                }
            }

            @Override
            public void onFailure(Call<CancelOrderResponse> call, Throwable t) {
                dialog.hide();
            }
        });
    }

    public void showDialog(String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(OrderDetailActivity.this);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Confirm",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog2, int id) {
                        cancelOrder();
                        dialog2.cancel();


                    }
                });
        builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
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



    void addOrderOnCart(String restaurantId, String restaurantName) {
        long id = -1;
        long subCatId = -1;
        for (int i = 0; i < orderDetail.size(); i++) {
            id = db.getMenuCategoryIfExit(orderDetail.get(i).getMenuCategoryId());
            if (id == -1) {
                id = db.insertMenuCategory(orderDetail.get(i).getMenuCategoryId(), orderDetail.get(i).getMenuCategoryName(), "", "");
            }
            if (orderDetail.get(i).getMenuSubCategory() != null && orderDetail.get(i).getMenuSubCategory().size() > 0) {
                for (int j = 0; j < orderDetail.get(i).getMenuSubCategory().size(); j++) {
                    subCatId = db.getMenuSubCategoryIfExit(orderDetail.get(i).getMenuSubCategory().get(j).getMenuCategoryId());
                    if (subCatId == -1) {
                        subCatId = db.insertMenuSubCategory(id,
                                orderDetail.get(i).getMenuCategoryId(),
                                orderDetail.get(i).getMenuSubCategory().get(j).getMenuCategoryId(),
                                orderDetail.get(i).getMenuSubCategory().get(j).getMenuCategoryName());
                    }

                    for (int k = 0; k < orderDetail.get(i).getMenuSubCategory().get(j).getMenuProducts().size(); k++) {
                        MenuProduct product = orderDetail.get(i).getMenuSubCategory().get(j).getMenuProducts().get(k);
                        db.insertMenuProduct(id, subCatId, orderDetail.get(i).getMenuSubCategory().get(j).getMenuCategoryId(),
                                product.getMenuProductId(),
                                product.getProductName(),
                                product.getVegType(),
                                product.getMenuProductPrice(),
                                product.getUserappProductImage(),
                                product.getEcomProductImage(),
                                product.getProductOverallRating(),
                                product.getQuantity(),
                                gson.toJson(product.getMenuProductSize()),
                                gson.toJson(product.getProductModifiers()),
                                gson.toJson(product.getMealProducts()),
                                product.getOriginalQuantity(),
                                product.getOriginalAmount1(),
                                product.getAmount());


                    }
                }

            }
            if (orderDetail.get(i).getMenuProducts() != null && orderDetail.get(i).getMenuProducts().size() > 0) {
                List<MenuProduct> products = orderDetail.get(i).getMenuProducts();
                for (MenuProduct product : products) {

                    if (product.getMealProducts() != null && product.getMealProducts().size() > 0) {

                        db.insertMenuProduct(id, subCatId, orderDetail.get(i).getMenuCategoryId(),
                                product.getMenuProductId(),
                                product.getProductName(),
                                product.getVegType(),
                                product.getMenuProductPrice(),
                                "",
                                "",
                                "",
                                product.getQuantity(),
                                null,
                                null,
                                gson.toJson(product.getMealProducts()),
                                product.getQuantity(),
                                product.getOriginalAmount1(),
                                product.getAmount());

                    } else {
                        db.insertMenuProduct(id, subCatId, orderDetail.get(i).getMenuCategoryId(),
                                product.getMenuProductId(),
                                product.getProductName(),
                                product.getVegType(),
                                product.getMenuProductPrice(),
                                product.getUserappProductImage(),
                                product.getEcomProductImage(),
                                product.getProductOverallRating(),
                                product.getQuantity(),
                                gson.toJson(product.getMenuProductSize()),
                                gson.toJson(product.getProductModifiers()),
                                gson.toJson(product.getMealProducts()),
                                product.getOriginalQuantity(),
                                product.getOriginalAmount1(),
                                product.getAmount());
                    }
                }
            }


        }
        Intent i = new Intent(this, DashboardActivity.class);
        i.putExtra("FROMMENU", "YES");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setAction("custom");
        sharePre.setString(sharePre.RESTUARANT_ID, restaurantId);
        sharePre.setString(sharePre.RESTUARANT_NAME, restaurantName);
        startActivity(i);
    }


    public void getOrderAgain(final String orderNumber) {

        dialog.show();
        RestaurantDetailsInterface apiInterface = ApiClient.getClient(this).create(RestaurantDetailsInterface.class);
        OrderAgainRequest request = new OrderAgainRequest();
        request.setOrderNumber(orderNumber);

        Call<OrderAgainResponse> call3 = apiInterface.getOrderAgain(request);
        call3.enqueue(new Callback<OrderAgainResponse>() {
            @Override
            public void onResponse(Call<OrderAgainResponse> call, Response<OrderAgainResponse> response) {
                try {
                    dialog.dismiss();
                    String status = response.body().getData().getRestaurantStatus();
                    if (response.body().getSuccess()) {

                        if (status != null && status.trim().length() > 0) {

                            if (status.equalsIgnoreCase("open") || status.equalsIgnoreCase("closed")) {

                                if (db.getCartData() == null) {
                                    //insertData(previousOrderDetailList.get(position));
                                } else {
                                    db.deleteCart();
                                    addOrderOnCart(previousOrderDetailList.getRestaurantId(), previousOrderDetailList.getRestaurantName());
                                }

                            } else {
                                getOrderAgainDailog(status, response.body().getMessage());
                            }
                        } else {

                        }
                    } else {
                        getOrderAgainDailog(status, response.body().getMessage());
                    }
                } catch (Exception e) {
                    dialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<OrderAgainResponse> call, Throwable t) {
                dialog.dismiss();
            }
        });


    }

    public void getOrderAgainDailog(String status, String message) {

        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.addnote_success_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setView(mDialogView);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        TextView tvMessage = mDialogView.findViewById(R.id.message);
        tvMessage.setText(message);

        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });

        alertDialog.show();

    }

}
