package com.easyfoodcustomer.order_status;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easyfoodcustomer.order_details_activity.OrderDetailActivity;
import com.easyfoodcustomer.utility.Helper;
import com.easyfoodcustomer.utility.PrefManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.easyfoodcustomer.R;
import com.easyfoodcustomer.dashboard.DashboardActivity;
import com.easyfoodcustomer.order_status.models.OrderStatusRequestModel;
import com.easyfoodcustomer.order_status.models.OrderStatusResponseModel;
import com.easyfoodcustomer.utility.ApiClient;
import com.easyfoodcustomer.utility.ApiInterface;
import com.easyfoodcustomer.utility.Constants;
import com.easyfoodcustomer.utility.GlobalValues;
import com.easyfoodcustomer.utility.SharedPreferencesClass;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import pl.droidsonroids.gif.GifImageView;

import static com.easyfoodcustomer.utility.Helper.isInternetOn;
import static com.easyfoodcustomer.utility.UserContants.AUTH_TOKEN;

public class OrderStatusActivity extends AppCompatActivity {
    static OrderStatusActivity orderStatusActivity;
    boolean isFirstTime = true;
    GlobalValues val;
    Handler handler;
    LinearLayout linearLayout_1, linearLayout_2, linearLayout_3, linearLayout_4;
    RelativeLayout rlStats;
    ImageView acceptImg, preparedImg, onwayImg, deliveredImg, mainImg;
    TextView callRestaurant, acceptTv, preparedTv, onwayTv, deliveredTv;
    TextView tvAcceptedText, tvPreparedText, tvOnTheWayText, tvDeliveredText;
    TextView tvTitileText;
    View view1, view2, view3;
    LinearLayout mainRL_;
    TextView tvOrderId, tvOrderTimeStamp, tvOrderAmount, tvDetailsMsg, tvPaymentMode;
    SharedPreferencesClass sharedPreferencesClass;
    String order_type;
    String order_id;
    String customerId;
    String restaurant_name;
    String phone_number;
    String payment_mode;
    BroadcastReceiver broadcastReceiver;
    String OrderId = null;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressDialog dialog;
    FirebaseAnalytics mFirebaseAnalytics;
    private String OrderAmount, time;

    private GifImageView ivLoader;

    public static OrderStatusActivity getActivity() {
        return orderStatusActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        orderStatusActivity = this;
        val = (GlobalValues) getApplication();
        dialog = new ProgressDialog(this);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equalsIgnoreCase("status")) ;
                {
                    if (getIntent().hasExtra("or")) {
                        OrderId = getIntent().getStringExtra("or");
                        getStatus(OrderId);

                    } else {
                        getDataFromIntent();
                        OrderId = sharedPreferencesClass.getOrderIDKey();
                        restaurant_name = getIntent().getStringExtra(Constants.RESTAURENT_NAME);
                        payment_mode = getIntent().getStringExtra(Constants.PAYMENT_MODE);

                        getStatus(OrderId);
                    }
                    getStatus(OrderId);
                    if (intent.getStringExtra("order_id") != null && OrderId != null && (intent.getStringExtra("order_id").equals(OrderId))) {
                        getStatus(OrderId);
                    } else {
                        if (intent.getStringExtra("order_id") != null) {
                            OrderId = intent.getStringExtra("order_id");
                            getStatus(OrderId);
                        }
                    }
                }
            }
        };

        handler = new Handler();

        swipeRefreshLayout = findViewById(R.id.swipreferesh);
        mainImg = findViewById(R.id.bikeimg);
        ivLoader = (GifImageView) findViewById(R.id.iv_loader);
        tvTitileText = findViewById(R.id.disTv);
        tvDetailsMsg = findViewById(R.id.tv_DetailsMsg);
        rlStats = findViewById(R.id.rl_stats);
        linearLayout_1 = findViewById(R.id.tv_1);
        acceptImg = findViewById(R.id.acceptImg);
        acceptTv = findViewById(R.id.acceptTv);
        tvAcceptedText = findViewById(R.id.tv_AcceptedText);
        view1 = findViewById(R.id.view1);

        linearLayout_2 = findViewById(R.id.tv_2);
        preparedImg = findViewById(R.id.preparedImg);
        preparedTv = findViewById(R.id.preparedTv);
        tvPreparedText = findViewById(R.id.tv_PreparedText);
        view2 = findViewById(R.id.view2);

        linearLayout_3 = findViewById(R.id.tv_3);
        onwayImg = findViewById(R.id.onwayImg);
        onwayTv = findViewById(R.id.onwayTv);
        tvOnTheWayText = findViewById(R.id.tv_OnTheWayText);
        view3 = findViewById(R.id.view3);

        linearLayout_4 = findViewById(R.id.tv_4);
        deliveredImg = findViewById(R.id.deliveredImg);
        deliveredTv = findViewById(R.id.deliveredTv);
        tvDeliveredText = findViewById(R.id.tv_DeliveredText);

        mainRL_ = findViewById(R.id.mainRl);
        callRestaurant = findViewById(R.id.callRestaurant);
        tvOrderId = findViewById(R.id.tv_orderId);
        tvOrderTimeStamp = findViewById(R.id.tv_timeStamp);
        tvOrderAmount = findViewById(R.id.tv_OrderAmount);
        tvPaymentMode = findViewById(R.id.tv_payMode);

        sharedPreferencesClass = new SharedPreferencesClass(getApplicationContext());

        tvOrderId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent intent = new Intent(OrderStatusActivity.this, OrderDetailActivity.class);
                intent.putExtra("order_no", OrderId);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);*/
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constants.ORDER_STATUS == 1) {
                    startActivity(new Intent(OrderStatusActivity.this, DashboardActivity.class));
                    finish();
                    overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                } else {
                    finish();
                    overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

                }
            }
        });
        findViewById(R.id.close_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderStatusActivity.this, DashboardActivity.class));
                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });

        if (getIntent().hasExtra(Constants.NOTIFICATION_ORDER_ID)) {
            OrderId = getIntent().getStringExtra(Constants.NOTIFICATION_ORDER_ID);
            getStatus(OrderId);

        } else {
            getDataFromIntent();
            OrderId = sharedPreferencesClass.getOrderIDKey();
            restaurant_name = getIntent().getStringExtra(Constants.RESTAURENT_NAME);
            payment_mode = getIntent().getStringExtra(Constants.PAYMENT_MODE);

            getStatus(OrderId);
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (isInternetOn(OrderStatusActivity.this)) {
                    swipeRefreshLayout.setRefreshing(true);

                    if (getIntent().hasExtra("or")) {
                        OrderId = getIntent().getStringExtra("or");
                        getStatus(OrderId);

                    } else {
                        getDataFromIntent();
                        OrderId = sharedPreferencesClass.getOrderIDKey();

                        getStatus(OrderId);
                    }

                } else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });


        callRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ContextCompat.checkSelfPermission(OrderStatusActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(OrderStatusActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone_number, null));
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    Toast.makeText(OrderStatusActivity.this, "Call not available", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public void getDataFromIntent() {
        Intent intent = getIntent();
        try {
            if (intent != null) {
                order_type = intent.getStringExtra(Constants.NOTIFICATION_TYPE);
                order_id = intent.getStringExtra(Constants.NOTIFICATION_ORDER_ID);
                payment_mode = intent.getStringExtra(Constants.PAYMENT_MODE);
                order_type = intent.getStringExtra(Constants.ORDER_TYPE);
                OrderAmount = intent.getStringExtra(Constants.TOTAL_COST);
                tvOrderAmount.setText("£" + String.format("%.2f", Double.parseDouble(OrderAmount)));
                restaurant_name = intent.getStringExtra(Constants.RESTAURENT_NAME);
                customerId = intent.getStringExtra(Constants.CUSTOMER_ID);
                phone_number = intent.getStringExtra(Constants.PHONE_NUMBER);

                time = intent.getStringExtra(Constants.ORDER_TIME);
                if (time.length() > 5) {
                    tvOrderTimeStamp.setText(Helper.formatDateTime(time).toUpperCase());
                } else {
                    if (time.equalsIgnoreCase("now")) {
                        tvOrderTimeStamp.setText("01:00" + " min");
                    } else {
                        tvOrderTimeStamp.setText(Helper.formatDateTime(time).toUpperCase());
                    }
                }

                if (order_type.equalsIgnoreCase("table"))
                    callRestaurant.setVisibility(View.GONE);

                if (payment_mode != null && !payment_mode.equals("cash")) {
                    tvPaymentMode.setText(payment_mode.toUpperCase());
                } else {
                    if (order_type.equalsIgnoreCase("table")) {
                        tvPaymentMode.setText("Pay at Restaurant");
                    } else {
                        tvPaymentMode.setText("Cash");
                    }
                }
            }

        } catch (NullPointerException e) {
            e.getLocalizedMessage();
        }
    }


    public void getStatus(String orderIDKey) {

        if (isFirstTime) {
            dialog.setMessage("Updating status...");
            dialog.show();
            isFirstTime = false;
        }
        try {
            OrderStatusRequestModel requestModel = new OrderStatusRequestModel();
            requestModel.setOrder_number(orderIDKey);
            requestModel.setCustomer_id(customerId);
            ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
            CompositeDisposable disposable = new CompositeDisposable();
            disposable.add(apiService.getOrderStatus(PrefManager.getInstance(OrderStatusActivity.this).getPreference(AUTH_TOKEN, ""), requestModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<OrderStatusResponseModel>() {
                        @Override
                        public void onSuccess(OrderStatusResponseModel data) {

                            if (swipeRefreshLayout != null) {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                            dialog.dismiss();
                            try {

                                if (data.getSuccess()) {
                                    mainRL_.setVisibility(View.VISIBLE);
                                    tvOrderId.setText("Order ID: " + data.getData().getOrder_num());
                                    payment_mode = data.getData().getPayment_mode();
                                    order_type = data.getData().getOrder_type();
                                    OrderAmount = data.getData().getOrder_total();
                                    tvOrderAmount.setText("£" + String.format("%.2f", Double.parseDouble(OrderAmount)));
                                    restaurant_name = data.getData().getRestaurant_name();
                                    if (data.getData().getPhone_number() != null && !data.getData().getPhone_number().isEmpty())
                                        phone_number = data.getData().getPhone_number();
                                    else
                                        phone_number = data.getData().getLandline_number();
                                    time = data.getData().getDelivery_date_time();

                                    if (time.length() > 5) {
                                        tvOrderTimeStamp.setText(Helper.newformatDateTime(time).toUpperCase());
                                    } else {
                                        if (time.equalsIgnoreCase("now")) {
                                            tvOrderTimeStamp.setText("01:00" + " min");
                                        } else {
                                            tvOrderTimeStamp.setText(Helper.newformatDateTime(time).toUpperCase());
                                        }
                                    }

                                    if (data.getData().getIsTableBooking() == 1 || order_type.equalsIgnoreCase("table") || data.getData().getOrder_type().equalsIgnoreCase("dine_in"))
                                        callRestaurant.setVisibility(View.GONE);

                                    if (payment_mode != null && !payment_mode.equals("cash")) {
                                        tvPaymentMode.setText(payment_mode.toUpperCase());
                                    } else {
                                        if (data.getData().getIsTableBooking() == 1 || order_type.equalsIgnoreCase("table") || data.getData().getOrder_type().equalsIgnoreCase("dine_in")) {
                                            tvPaymentMode.setText("Pay at Restaurant");
                                        } else {
                                            tvPaymentMode.setText("Cash");
                                        }
                                    }


                                    if (data.getData().getOrder_type().equalsIgnoreCase("dine_in") || data.getData().getIsTableBooking() == 1) {
                                        rlStats.setVisibility(View.GONE);

                                    } else {
                                        rlStats.setVisibility(View.VISIBLE);
                                        if (data.getData().getOrder_type().equalsIgnoreCase("collection")) {
                                            view2.setVisibility(View.VISIBLE);
                                            view3.setVisibility(View.VISIBLE);
                                            linearLayout_3.setVisibility(View.VISIBLE);
                                            linearLayout_4.setVisibility(View.VISIBLE);
                                            tvOnTheWayText.setVisibility(View.VISIBLE);
                                            tvDeliveredText.setVisibility(View.VISIBLE);
                                            tvAcceptedText.setText("Order Accepted");
                                            tvPreparedText.setText("Being Prepared");
                                            tvOnTheWayText.setText("Ready for Collection");
                                            tvDeliveredText.setText("Collected");


                                        } else {
                                            view2.setVisibility(View.VISIBLE);
                                            view3.setVisibility(View.VISIBLE);
                                            linearLayout_3.setVisibility(View.VISIBLE);
                                            linearLayout_4.setVisibility(View.VISIBLE);

                                            tvOnTheWayText.setVisibility(View.VISIBLE);
                                            tvDeliveredText.setVisibility(View.VISIBLE);
                                        }
                                    }
                                    setUi(data.getData().getOrder_status(), data.getData().getMessage());
                                } else {

                                }
                            } catch (
                                    Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            dialog.dismiss();
                        }
                    }));

        } catch (
                Exception e) {
            dialog.dismiss();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("status");
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);

    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onPause();

    }


    private void setUi(String status, final String orderTitle) {
        tvTitileText.setText(orderTitle);

        switch (status) {
            case "pending":
                ivLoader.setVisibility(View.VISIBLE);
                mainImg.setVisibility(View.GONE);
                mainImg.setImageResource(R.drawable.ic_order_status_0);
                tvDetailsMsg.setText("Thank you for placing your order. We will update you once " + restaurant_name + " accepts your order.");
                linearLayout_1.setBackground(getResources().getDrawable(R.drawable.border_circle_white));
                tvAcceptedText.setTextColor(getResources().getColor(R.color.gray));
                tvPreparedText.setTextColor(getResources().getColor(R.color.gray));
                tvOnTheWayText.setTextColor(getResources().getColor(R.color.gray));
                tvDeliveredText.setTextColor(getResources().getColor(R.color.gray));

                acceptTv.setVisibility(View.VISIBLE);
                preparedTv.setVisibility(View.VISIBLE);
                onwayTv.setVisibility(View.VISIBLE);
                deliveredTv.setVisibility(View.VISIBLE);

                acceptImg.setVisibility(View.GONE);
                preparedImg.setVisibility(View.GONE);
                onwayImg.setVisibility(View.GONE);
                deliveredImg.setVisibility(View.GONE);

                view1.setBackgroundColor(getResources().getColor(R.color.gray_light));
                view2.setBackgroundColor(getResources().getColor(R.color.gray_light));
                view3.setBackgroundColor(getResources().getColor(R.color.gray_light));

                break;
            case "accepted":
                if (order_type.equalsIgnoreCase("collection")) {
                    mainImg.setImageResource(R.drawable.ic_colll_accept);
                    tvDetailsMsg.setText("The restaurant has accepted your order.\nYour meal is now being prepared.");
                    mainImg.setBackground(null);
                    mainImg.setPadding(0, 0, 0, 0);
                } else {
                    mainImg.setImageResource(R.drawable.ic_order_status_1);
                    mainImg.setBackground(getResources().getDrawable(R.drawable.circle_shape));
                    tvDetailsMsg.setText("Your order has been accepted and is being prepared");
                }
                ivLoader.setVisibility(View.GONE);
                mainImg.setVisibility(View.VISIBLE);
                linearLayout_1.setBackground(getResources().getDrawable(R.drawable.circle_orange));
                tvAcceptedText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tvPreparedText.setTextColor(getResources().getColor(R.color.gray));
                tvOnTheWayText.setTextColor(getResources().getColor(R.color.gray));
                tvDeliveredText.setTextColor(getResources().getColor(R.color.gray));

                acceptTv.setVisibility(View.GONE);
                preparedTv.setVisibility(View.VISIBLE);
                onwayTv.setVisibility(View.VISIBLE);
                deliveredTv.setVisibility(View.VISIBLE);

                acceptImg.setVisibility(View.VISIBLE);
                preparedImg.setVisibility(View.GONE);
                onwayImg.setVisibility(View.GONE);
                deliveredImg.setVisibility(View.GONE);

                view1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                view2.setBackgroundColor(getResources().getColor(R.color.gray_light));
                view3.setBackgroundColor(getResources().getColor(R.color.gray_light));
                break;
            case "preparing":
                if (order_type.equalsIgnoreCase("collection")) {
                    mainImg.setImageResource(R.drawable.ic_col_prepare);
                    mainImg.setBackground(null);
                    mainImg.setPadding(0, 0, 0, 0);
                    tvDetailsMsg.setText("Your meal is now being prepared.\nIt will be on its way very soon.");
                } else {
                    mainImg.setImageResource(R.drawable.ic_order_status_2);
                    mainImg.setBackground(getResources().getDrawable(R.drawable.circle_shape));
                    if (order_type.equals("table"))
                        tvDetailsMsg.setText("Your order is being prepared and will be on your table soon.");
                    else
                        tvDetailsMsg.setText("Your order is being prepared and will be delivered soon.");
                }
                ivLoader.setVisibility(View.GONE);
                mainImg.setVisibility(View.VISIBLE);
                linearLayout_1.setBackground(getResources().getDrawable(R.drawable.circle_orange));

                tvAcceptedText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tvPreparedText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tvOnTheWayText.setTextColor(getResources().getColor(R.color.gray));
                tvDeliveredText.setTextColor(getResources().getColor(R.color.gray));

                acceptTv.setVisibility(View.GONE);
                preparedTv.setVisibility(View.GONE);
                onwayTv.setVisibility(View.VISIBLE);
                deliveredTv.setVisibility(View.VISIBLE);

                acceptImg.setVisibility(View.VISIBLE);
                preparedImg.setVisibility(View.VISIBLE);
                onwayImg.setVisibility(View.GONE);
                deliveredImg.setVisibility(View.GONE);

                view1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                view2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                view3.setBackgroundColor(getResources().getColor(R.color.gray_light));

                break;
            case "out_for_delivery":
                if (order_type.equalsIgnoreCase("collection")) {
                    mainImg.setImageResource(R.drawable.ic_coll_ready);
                    mainImg.setBackground(null);
                    mainImg.setPadding(0, 0, 0, 0);
                    tvDetailsMsg.setText("You can now collect your\nfood from the restaurant.");
                } else {
                    mainImg.setImageResource(R.drawable.bike);
                    mainImg.setBackground(getResources().getDrawable(R.drawable.circle_shape));
                    tvDetailsMsg.setText("Your order is with our driver and will be with you shortly.");
                }
                ivLoader.setVisibility(View.GONE);
                mainImg.setVisibility(View.VISIBLE);
                linearLayout_1.setBackground(getResources().getDrawable(R.drawable.circle_orange));

                tvAcceptedText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tvPreparedText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tvOnTheWayText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tvDeliveredText.setTextColor(getResources().getColor(R.color.gray));

                acceptTv.setVisibility(View.GONE);
                preparedTv.setVisibility(View.GONE);
                onwayTv.setVisibility(View.GONE);
                deliveredTv.setVisibility(View.VISIBLE);

                acceptImg.setVisibility(View.VISIBLE);
                preparedImg.setVisibility(View.VISIBLE);
                onwayImg.setVisibility(View.VISIBLE);
                deliveredImg.setVisibility(View.GONE);

                view1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                view2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                view3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));


                break;
            case "delivered":
                if (order_type.equalsIgnoreCase("collection")) {
                    mainImg.setImageResource(R.drawable.ic_coll_collected);
                    mainImg.setBackground(null);
                    mainImg.setPadding(0, 0, 0, 0);
                    tvDetailsMsg.setText("You have collected your food.\nEnjoy your meal.");
                } else {
                    mainImg.setImageResource(R.drawable.ic_order_status_3);
                    mainImg.setBackground(getResources().getDrawable(R.drawable.circle_shape));
                    tvDetailsMsg.setText(getResources().getString(R.string.order_title_Details_4));
                }
                ivLoader.setVisibility(View.GONE);
                mainImg.setVisibility(View.VISIBLE);
                linearLayout_1.setBackground(getResources().getDrawable(R.drawable.circle_orange));
                tvAcceptedText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tvPreparedText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tvOnTheWayText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tvDeliveredText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                acceptTv.setVisibility(View.GONE);
                preparedTv.setVisibility(View.GONE);
                onwayTv.setVisibility(View.GONE);
                deliveredTv.setVisibility(View.GONE);

                acceptImg.setVisibility(View.VISIBLE);
                preparedImg.setVisibility(View.VISIBLE);
                onwayImg.setVisibility(View.VISIBLE);
                deliveredImg.setVisibility(View.VISIBLE);

                view1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                view2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                view3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                break;
            case "served":
                if (order_type.equalsIgnoreCase("collection")) {
                    mainImg.setImageResource(R.drawable.ic_coll_collected);
                    mainImg.setBackground(null);
                    mainImg.setPadding(0, 0, 0, 0);
                    tvDetailsMsg.setText("You have collected your food.\nEnjoy your meal.");
                } else {
                    mainImg.setImageResource(R.drawable.ic_order_status_3);
                    mainImg.setBackground(getResources().getDrawable(R.drawable.circle_shape));
                    tvDetailsMsg.setText("You have collected your food.\nEnjoy your meal.");
                }
                ivLoader.setVisibility(View.GONE);
                mainImg.setVisibility(View.VISIBLE);
                linearLayout_1.setBackground(getResources().getDrawable(R.drawable.circle_orange));
                tvAcceptedText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tvPreparedText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tvOnTheWayText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tvDeliveredText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                acceptTv.setVisibility(View.GONE);
                preparedTv.setVisibility(View.GONE);
                onwayTv.setVisibility(View.GONE);
                deliveredTv.setVisibility(View.GONE);

                acceptImg.setVisibility(View.VISIBLE);
                preparedImg.setVisibility(View.VISIBLE);
                onwayImg.setVisibility(View.VISIBLE);
                deliveredImg.setVisibility(View.VISIBLE);

                view1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                view2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                view3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                break;
            case "rejected":
                mainImg.setImageResource(R.drawable.img_new_rejected);
                ivLoader.setVisibility(View.GONE);
                mainImg.setVisibility(View.VISIBLE);
                tvDetailsMsg.setText("You haven't been charged for this.\nPlease try again with a new restaurant.");

                break;
            default:
                mainImg.setImageResource(R.drawable.ic_order_status_0);
                tvDetailsMsg.setText("Thank you for placing your order. We will update you once " + restaurant_name + " accepts your order.");
                linearLayout_1.setBackground(getResources().getDrawable(R.drawable.border_circle_white));
                tvAcceptedText.setTextColor(getResources().getColor(R.color.gray));
                tvPreparedText.setTextColor(getResources().getColor(R.color.gray));
                tvOnTheWayText.setTextColor(getResources().getColor(R.color.gray));
                tvDeliveredText.setTextColor(getResources().getColor(R.color.gray));
                acceptTv.setVisibility(View.VISIBLE);
                preparedTv.setVisibility(View.VISIBLE);
                onwayTv.setVisibility(View.VISIBLE);
                deliveredTv.setVisibility(View.VISIBLE);
                acceptImg.setVisibility(View.GONE);
                preparedImg.setVisibility(View.GONE);
                onwayImg.setVisibility(View.GONE);
                deliveredImg.setVisibility(View.GONE);
                view1.setBackgroundColor(getResources().getColor(R.color.gray_light));
                view2.setBackgroundColor(getResources().getColor(R.color.gray_light));
                view3.setBackgroundColor(getResources().getColor(R.color.gray_light));
                break;

        }
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        orderStatusActivity = null;
        super.onDestroy();
    }
}
