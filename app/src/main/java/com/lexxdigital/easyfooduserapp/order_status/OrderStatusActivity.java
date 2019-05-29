package com.lexxdigital.easyfooduserapp.order_status;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.dashboard.DashboardActivity;
import com.lexxdigital.easyfooduserapp.model.add_model.AddressRequestModel;
import com.lexxdigital.easyfooduserapp.model.add_model.AddressResponseModel;
import com.lexxdigital.easyfooduserapp.order_details_activity.OrderDetailActivity;
import com.lexxdigital.easyfooduserapp.order_status.models.OrderStatusRequestModel;
import com.lexxdigital.easyfooduserapp.order_status.models.OrderStatusResponseModel;
import com.lexxdigital.easyfooduserapp.search_post_code.SearchPostCodeActivity;
import com.lexxdigital.easyfooduserapp.utility.ApiClient;
import com.lexxdigital.easyfooduserapp.utility.ApiInterface;
import com.lexxdigital.easyfooduserapp.utility.Constants;
import com.lexxdigital.easyfooduserapp.utility.GlobalValues;
import com.lexxdigital.easyfooduserapp.utility.SharedPreferencesClass;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class OrderStatusActivity extends AppCompatActivity {

    boolean isFirstTime = true;
    GlobalValues val;
    Handler handler;
    LinearLayout linearLayout_1, linearLayout_2, linearLayout_3, linearLayout_4;
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
    String restaurant_name;
    String prepared_time, average_delivery_time;
    String phone_number;
    String payment_mode;
    BroadcastReceiver broadcastReceiver;
    String OrderId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        val = (GlobalValues) getApplication();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equalsIgnoreCase("status")) ;
                {
                    if (intent.getStringExtra("order_id") != null && OrderId != null && (intent.getStringExtra("order_id").equals(OrderId))) {
                        setUi(Integer.parseInt(intent.getStringExtra("status")));
//                    getStatus(intent.getStringExtra("number"));
                    }
                }
            }
        };

        handler = new Handler();

        mainImg = findViewById(R.id.bikeimg);
        tvTitileText = findViewById(R.id.disTv);
        tvDetailsMsg = findViewById(R.id.tv_DetailsMsg);

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

                Intent intent = new Intent(OrderStatusActivity.this, OrderDetailActivity.class);
                intent.putExtra("order_no", OrderId);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
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

        if (getIntent().hasExtra("or")) {
            OrderId = getIntent().getStringExtra("or");
            getStatus(OrderId);

        } else {
            getDataFromIntent();
            OrderId = sharedPreferencesClass.getOrderIDKey();

            getStatus(OrderId);
        }
       /* handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getStatus();
            }
        }, 100000);*/


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
                payment_mode = intent.getStringExtra(Constants.NOTIFICATION_ORDER_ID);

                if (payment_mode != null) {
                    tvPaymentMode.setText("Payment Mode: " + payment_mode.toUpperCase());
                } else {
                    tvPaymentMode.setText("Payment Mode: Cash");
                }
            }

        } catch (NullPointerException e) {
            e.getLocalizedMessage();
        }
    }

    //TODO:  Methode to call an api.....
    public void getStatus(String orderIDKey) {
        final ProgressDialog dialog = new ProgressDialog(this);
        if (isFirstTime) {
            dialog.setMessage("Updating status...");
            dialog.show();
            // isFirstTime = false;
        }
        try {
            OrderStatusRequestModel requestModel = new OrderStatusRequestModel();
            requestModel.setOrder_number(orderIDKey);

            Log.e("request", requestModel.toString());
            ApiInterface apiService = ApiClient.getClient(this).create(ApiInterface.class);
            CompositeDisposable disposable = new CompositeDisposable();
            disposable.add(apiService.getOrderStatus(requestModel)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<OrderStatusResponseModel>() {
                        @Override
                        public void onSuccess(OrderStatusResponseModel data) {

                            dialog.dismiss();
                            try {


                                if (data.getSuccess()) {
                                    mainRL_.setVisibility(View.VISIBLE);
                                    tvOrderId.setText("Order Id: " + data.getData().getOrder_number());
                                    tvOrderTimeStamp.setText(data.getData().getOrder_date_time());
                                    tvOrderAmount.setText("£" + String.format("%.2f", Double.parseDouble(data.getData().getOrder_total())));
                                    restaurant_name = data.getData().getRestaurant_name();
                                    prepared_time = data.getData().getPrepare_time();
                                    average_delivery_time = data.getData().getAverage_delivery_time();
                                    phone_number = data.getData().getPhone_number();
                                    tvPaymentMode.setText(data.getData().getPayment_mode().toUpperCase());
                                    setUi(data.getData().getOrder_status());
                                    //Toast.makeText(val, data.getMessage(), Toast.LENGTH_SHORT).show();
                                    //  setUi(data.getData().getOrder_status());
                                } else {

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            dialog.dismiss();
                            Log.e("Exception ", e.getMessage());
                            ///Toast.makeText(val, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }));

        } catch (Exception e) {
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
        super.onPause();

    }
    //TODO: -------------- Notes --------------
    //TODO:    0   ---> Order Pending
    //TODO:    1   ---> Order Accepted
    //TODO:    2   ---> Order Prepared
    //TODO:    3   ---> Order Out for Delivery
    //TODO:    4   ---> Order Delivered
    //TODO:    5   ---> Order Canceled


    private void setUi(int status) {
        switch (status) {
            case 0:
                mainImg.setImageResource(R.drawable.ic_order_status_0);
//                Glide.with(OrderStatusActivity.this).load(R.drawable.ssssss).asGif().into(mainImg);
                tvTitileText.setText(getResources().getString(R.string.order_title_0));
                tvDetailsMsg.setText(getResources().getString(R.string.order_title_Details_0) + " " + restaurant_name + " accept your order.");
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
            case 1:
                mainImg.setImageResource(R.drawable.ic_order_status_1);
                mainImg.setBackground(getResources().getDrawable(R.drawable.circle_shape));
                tvTitileText.setText(getResources().getString(R.string.order_title_1));
                tvDetailsMsg.setText(getResources().getString(R.string.order_title_Details_1) + " " + prepared_time + " min " + "to prepare.");
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
            case 2:
                mainImg.setImageResource(R.drawable.ic_order_status_2);
                tvTitileText.setText(getResources().getString(R.string.order_title_2));
                tvDetailsMsg.setText(getResources().getString(R.string.order_title_Details_1) + " " + prepared_time + " min " + "to prepare.");
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
            case 3:
                mainImg.setImageResource(R.drawable.ic_order_status_3);
                tvTitileText.setText(getResources().getString(R.string.order_title_2));
                tvDetailsMsg.setText(getResources().getString(R.string.order_title_Details_1) + " " + average_delivery_time + " min " + "to delivery.");
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
            case 4:
                mainImg.setImageResource(R.drawable.bike);
                tvTitileText.setText(getResources().getString(R.string.order_title_2));
                tvDetailsMsg.setText(getResources().getString(R.string.order_title_Details_4));
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
            case 5:
                dialogOrderRejected();
                break;
            default:
                //TODO: Order cancelled..
        }
    }

    public void alertDialogEmptyBasket() {
        LayoutInflater factory = LayoutInflater.from(OrderStatusActivity.this);
        final View mDialogView = factory.inflate(R.layout.bucket_is_empty, null);
        final AlertDialog emptyDialog = new AlertDialog.Builder(OrderStatusActivity.this).create();
        emptyDialog.setView(mDialogView);
        emptyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                Constants.switchActivity(OrderStatusActivity.this, DashboardActivity.class);
                //  getActivity().finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                emptyDialog.dismiss();
                //    dialog2.show();

            }
        });
        emptyDialog.show();
    }


    public void dialogOrderRejected() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.order_rejected_popup, null);
        final AlertDialog noteDialog = new AlertDialog.Builder(this).create();
        noteDialog.setView(mDialogView);
        noteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        noteDialog.setCancelable(false);
        noteDialog.setCanceledOnTouchOutside(false);

        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderStatusActivity.this, DashboardActivity.class));
                noteDialog.dismiss();
                finish();
            }
        });

        noteDialog.show();
    }
}
