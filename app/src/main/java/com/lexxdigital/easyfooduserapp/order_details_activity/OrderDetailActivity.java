package com.lexxdigital.easyfooduserapp.order_details_activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lexxdigital.easyfooduserapp.R;

import com.lexxdigital.easyfooduserapp.adapters.order_details.OrderDetailsMenuProductAdapter;
import com.lexxdigital.easyfooduserapp.adapters.order_details.OrderDetailsSpecialOfferProductAdapter;
import com.lexxdigital.easyfooduserapp.adapters.order_details.OrderDetailsUpsellProductAdapter;
import com.lexxdigital.easyfooduserapp.api.CancelInterface;
import com.lexxdigital.easyfooduserapp.api.OrderDetailsInterface;
import com.lexxdigital.easyfooduserapp.customer_review.CustomerReviewProcess;
import com.lexxdigital.easyfooduserapp.model.cancelorder.CancelOrderResponse;
import com.lexxdigital.easyfooduserapp.model.cancelorder.CancelRequest;
import com.lexxdigital.easyfooduserapp.model.order_details.Data;
import com.lexxdigital.easyfooduserapp.model.order_details.OrderDetailsRequest;
import com.lexxdigital.easyfooduserapp.model.order_details.OrderDetailsResponse;
import com.lexxdigital.easyfooduserapp.order_status.OrderStatusActivity;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.CartData;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;
import com.lexxdigital.easyfooduserapp.utility.ApiClient;
import com.lexxdigital.easyfooduserapp.utility.Constants;
import com.lexxdigital.easyfooduserapp.utility.GlobalValues;
import com.lexxdigital.easyfooduserapp.utility.SharedPreferencesClass;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constants.setStatusBarGradiant(this);
        setContentView(R.layout.activity_order_details);
        sharePre = new SharedPreferencesClass(this);
        addReview = findViewById(R.id.add_review);
        orderAgain = findViewById(R.id.order_again);
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
        tvAddressType = findViewById(R.id.tv_AddressType);
        tvAddress = findViewById(R.id.tv_Address);

        Bundle b = new Bundle();
        b = getIntent().getExtras();
        strOrderNo = b.getString("order_no");
        Log.e("OrderDetails", "onCreate:strOrderNo: " + strOrderNo);

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
        Log.e("orderDetails", "onCreate:orderId " + orderId + " strOrderNo: " + strOrderNo + " restoName: " + restoName + " restoImage:" + restoImage);
        addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

//                addReview.setClickable(false);
            }
        });
        llCacelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Are you sure Cancel order!");
            }
        });
    }

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
                        intent.putExtra("order_no", strOrderNo);
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

        }

    }

    public void getCardList() {
        OrderDetailsInterface apiInterface = ApiClient.getClient(this).create(OrderDetailsInterface.class);
        OrderDetailsRequest request = new OrderDetailsRequest(strOrderNo);

        Call<OrderDetailsResponse> call = apiInterface.mOrderDetails(request);
        call.enqueue(new Callback<OrderDetailsResponse>() {

            @Override
            public void onResponse(Call<OrderDetailsResponse> call, Response<OrderDetailsResponse> response) {
                //List<Data> previousOrderList = (List<Data>) response.body();
                try {
                    dialog.hide();
                    if (response.body().getSuccess()) {
                        Log.d("prevOrder", "onResponse: orderrrrrrr sucesss");

                        dataList = response.body().getData().getOrderDetails().getData();
                        orderId = response.body().getData().getOrderId();
                        restoId = response.body().getData().getRestaurantId();
                        restoName = response.body().getData().getRestaurantName();
                        // strOrderNo = response.body().getData().getOrderNum();
                        restoImage = response.body().getData().getRestaurantImage();
                        restoLogo = response.body().getData().getRestaurantLogo();
                        OrdAvgRating = response.body().getData().getAvgRating();
                        fltRating = Float.valueOf(OrdAvgRating);
                        Glide.with(OrderDetailActivity.this).load(restoImage).placeholder(R.drawable.default_restaurant_image).centerCrop().into(restImage);
                        Glide.with(OrderDetailActivity.this).load(restoLogo).placeholder(R.drawable.restaurant_default_logo).centerCrop().into(restLogo);
                        restName.setText(restoName);
                        ivToolBarTitle.setText(restoName);
                        orderNo.setText(strOrderNo);
                        orderDate.setText(response.body().getData().getOrderDateTime());
                        subtotal.setText(getString(R.string.currency) + String.valueOf(response.body().getData().getOrderSubtotal()));
                        dicsRate.setText(getString(R.string.currency) + String.valueOf(response.body().getData().getDiscountAmount()));
                        delivRate.setText(getString(R.string.currency) + String.valueOf(response.body().getData().getDeliveryCharge()));
                        totalPrice.setText("TOTAL : " + getString(R.string.currency) + String.valueOf(response.body().getData().getOrderTotal()));
                        tvPaidBy.setText("Paid by " + response.body().getData().getPaymentMode());
                        tvAddressType.setVisibility(View.GONE);
                        tvAddress.setVisibility(View.GONE);
                       /* if (response.body().getData().getDeliveryOption().equalsIgnoreCase("delivery")) {
                            tvAddressType.setVisibility(View.VISIBLE);
                            tvAddress.setVisibility(View.VISIBLE);
                            tvAddressType.setText("Delivery Address:");
                            tvAddress.setText(response.body().getData().getCustomerDeliveryAddress().getCustomerDeliveryAddress1()
                                    + ", " + response.body().getData().getCustomerDeliveryAddress().getCustomerDeliveryAddress2()
                                    + ", " + response.body().getData().getCustomerDeliveryAddress().getCustomerDeliveryCity()
                                    + ", " + response.body().getData().getCustomerDeliveryAddress().getCustomerDeliveryPostCode()
                                    + ", " + response.body().getData().getCustomerDeliveryAddress().getCustomerDeliveryCountry());
                        }*/


                        initView();
                        List<MenuProduct> menuProducts = new ArrayList<>();

                        for (int i = 0; i < response.body().getData().getOrderDetails().getData().getMenuCategoryCarts().size(); i++) {
                            for (int j = 0; j < response.body().getData().getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuProducts().size(); j++) {

                                menuProducts.add(response.body().getData().getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuProducts().get(j));
                            }

                        }

                        if (menuProducts.size() > 0) {
                            orderDetailsAdapter.addItem(menuProducts);
                        }

                        if (response.body().getData().getOrderDetails().getData().getSpecialOffers().size() > 0) {
                            specialOfferProductAdapter.addItem(response.body().getData().getOrderDetails().getData().getSpecialOffers());
                        }
                        if (response.body().getData().getOrderDetails().getData().getUpsellProducts().size() > 0) {
                            upsellProductAdapter.addItem(response.body().getData().getOrderDetails().getData().getUpsellProducts());
                        }

//                        if(Float.valueOf(OrdAvgRating) > 0){
//                            addReview.setText(OrdAvgRating+" Rating");
//                        }
                        String strOrderStatus = response.body().getData().getOrderStatus();
                        Log.e("orderstatus Activity", "onBindViewHolder: order status" + strOrderStatus);
                        // status will be 'new','pending','rejected','accepted','out_of_delivery','delivered','preparing'-------------
                        if (strOrderStatus.equalsIgnoreCase("new") || strOrderStatus.equalsIgnoreCase("pending")) {
                            orderStatus.setText("Pending");
                            llCacelOrder.setVisibility(View.GONE);
                            llTrack.setVisibility(View.GONE);
                            llReview.setVisibility(View.GONE);
                        } else if (strOrderStatus.equalsIgnoreCase("accepted") || strOrderStatus.equalsIgnoreCase("preparing") || strOrderStatus.equalsIgnoreCase("out_for_delivery")) {
                            String ordStatus = strOrderStatus;
                            String status = ordStatus.substring(0, 1).toUpperCase() + ordStatus.substring(1);
                            orderStatus.setText(status);
                            llTrack.setVisibility(View.VISIBLE);
                            llReview.setVisibility(View.GONE);
                            llCacelOrder.setVisibility(View.GONE);
                        } else if (strOrderStatus.equalsIgnoreCase("delivered")) {
                            String ordStatus = strOrderStatus;
                            String status = ordStatus.substring(0, 1).toUpperCase() + ordStatus.substring(1);
                            orderStatus.setText(status);
                            orderStatus.setTextColor(Color.GREEN);
                            llCacelOrder.setVisibility(View.GONE);
                            llTrack.setVisibility(View.GONE);

                            if (response.body().getData().getOrder_review_rating() > 0) {
                                llReview.setVisibility(View.GONE);
                                addReview.setVisibility(View.GONE);
                            } else {
                                llReview.setVisibility(View.VISIBLE);
                                addReview.setVisibility(View.VISIBLE);
                            }

                        } else if (strOrderStatus.equalsIgnoreCase("rejected")) {
                            String ordStatus = strOrderStatus;
                            String status = ordStatus.substring(0, 1).toUpperCase() + ordStatus.substring(1);
                            orderStatus.setText(status);
                            orderStatus.setTextColor(Color.RED);
                            llCacelOrder.setVisibility(View.GONE);
                            llTrack.setVisibility(View.GONE);

                            llReview.setVisibility(View.GONE);
                            addReview.setVisibility(View.GONE);

                            /*if (fltRating > 0) {
                                llReview.setVisibility(View.VISIBLE);
                                addReview.setVisibility(View.GONE);
                            } else {
                                llReview.setVisibility(View.VISIBLE);
                            }*/
                        } else {
                            String ordStatus = strOrderStatus;
                            String status = ordStatus.substring(0, 1).toUpperCase() + ordStatus.substring(1);
                            orderStatus.setText(status);
                            llCacelOrder.setVisibility(View.GONE);
                            llTrack.setVisibility(View.GONE);
                            if (fltRating > 0) {
                                llReview.setVisibility(View.VISIBLE);
                                addReview.setVisibility(View.GONE);
                            } else {
                                llReview.setVisibility(View.VISIBLE);
                            }
                        }

                        String notes = response.body().getData().getOrderNotes();

                        if (notes != null && !notes.equals("")) {
                            note.setText("Notes:\n" + notes);
                            note.setVisibility(View.VISIBLE);
                            lineBelowNotes.setVisibility(View.VISIBLE);
                        } else {
                            note.setVisibility(View.GONE);
                            lineBelowNotes.setVisibility(View.GONE);
                        }
                        if (response.body().getData().getCustomerDeliveryAddress().getCustomerDeliveryAddress1() != null) {
                            String address1, address2, city, postal, country, addressType;
                            String address = "";
                            address1 = response.body().getData().getCustomerDeliveryAddress().getCustomerDeliveryAddress1();
                            address2 = response.body().getData().getCustomerDeliveryAddress().getCustomerDeliveryAddress2();
                            city = response.body().getData().getCustomerDeliveryAddress().getCustomerDeliveryCity();
                            postal = response.body().getData().getCustomerDeliveryAddress().getCustomerDeliveryPostCode();
                            country = response.body().getData().getCustomerDeliveryAddress().getCustomerDeliveryCountry();
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
                        //restName.setText(response.body().getData().getRestaurantName());


                        Log.e("orderagain", "onResponse:cartList.size: " + dataList.getMenuCategoryCarts().size());
                        //initView();
                    } else {
                        dialog.hide();
                        Log.d("prevOrder", "onResponse: orderrrrrrr false");

                    }
                } catch (Exception e) {
                    dialog.hide();
                    Log.e("Error11 <>>>", ">>>>>" + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<OrderDetailsResponse> call, Throwable t) {
                Log.e("Error12 <>>>", ">>>>>" + t.getMessage());
                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                dialog.hide();
            }

        });
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
//        getCardList();
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
