package com.easyfoodcustomer.search_post_code;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.easyfoodcustomer.R;
import com.easyfoodcustomer.databinding.ActivityRestaurantQrBinding;
import com.easyfoodcustomer.databinding.LayoutDialogNorestaurantBinding;
import com.easyfoodcustomer.order_details_activity.OrderDetailActivity;
import com.easyfoodcustomer.search_post_code.api.SearchPostCodeInterface;
import com.easyfoodcustomer.search_post_code.model.search_response.RestaurantQrResponseBean;
import com.easyfoodcustomer.utility.ApiClient;
import com.easyfoodcustomer.utility.Constants;
import com.easyfoodcustomer.utility.GlobalValues;
import com.easyfoodcustomer.utility.PrefManager;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.easyfoodcustomer.utility.Helper.isInternetOn;
import static com.easyfoodcustomer.utility.UserContants.AUTH_TOKEN;

public class RestaurantQrActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {
    private ActivityRestaurantQrBinding binding;
    private String postCode;
    private Dialog dialog;
    private GlobalValues val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_restaurant_qr);
        val = (GlobalValues) getApplication();
        dialog = new Dialog(RestaurantQrActivity.this);
        dialog.setTitle("");
        dialog.setCancelable(false);
        getDataFromIntent();

        scanQRcode();
    }

    private void getDataFromIntent() {
        postCode = getIntent().getStringExtra("POST_CODE");
    }

    private void scanQRcode() {
        binding.camerapreview.setOnQRCodeReadListener(this);
        binding.camerapreview.setQRDecodingEnabled(true);
        // Use this function to change the autofocus interval (default is 5 secs)
        binding.camerapreview.setAutofocusInterval(2000L);
        //  binding.camerapreview.setTorchEnabled(true);
        //  binding.camerapreview.setFrontCamera();
        binding.camerapreview.setBackCamera();
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        //Toast.makeText(this, "" + text, Toast.LENGTH_SHORT).show();
        Log.e("print url", "" + text);
        binding.camerapreview.stopCamera();
        if (isInternetOn(RestaurantQrActivity.this)) {
            // if (ApiClient.isConnected(getApplicationContext())) {
            dialog.show();
            getRestaurantId(text);
            /*} else {
                dialogNoInternetConnection("Please check internet connection.");
            }*/

        } else {
            dialogNoInternetConnection("Please check internet connection.");
        }


    }


    public void getRestaurantId(final String url) {
        SearchPostCodeInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(SearchPostCodeInterface.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("url", url);
        jsonObject.addProperty("post_code", postCode);

        Call<RestaurantQrResponseBean> call3 = apiInterface.getRestaurantIdbyQRCode(PrefManager.getInstance(RestaurantQrActivity.this).getPreference(AUTH_TOKEN, ""), jsonObject);
        call3.enqueue(new Callback<RestaurantQrResponseBean>() {
            @Override
            public void onResponse(Call<RestaurantQrResponseBean> call, Response<RestaurantQrResponseBean> response) {
                try {

                    if (response.body().isSuccess()) {
                        dialog.hide();
                        if (response.body().getIscovid() == 1) {
                            finish();
                            Intent i = new Intent(RestaurantQrActivity.this, TrackTraceActivity.class);
                            i.putExtra("RESTAURANTID", response.body().getData().getId());
                            i.putExtra("RESTAURANTNAME", response.body().getData().getRestaurant_name());
                            i.putExtra("DELIVERY_OPTIONS", response.body().getData().getDelivery_options());
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

                        } else {
                            finishAffinity();
                            Intent intent = new Intent(RestaurantQrActivity.this, SearchPostCodeActivity.class);
                            intent.putExtra("IS_FROM_TRACK", true);
                            intent.putExtra("RESTAURANTID", response.body().getData().getId());
                            intent.putExtra("RESTAURANTNAME", response.body().getData().getRestaurant_name());
                            intent.putExtra("DELIVERY_OPTIONS", response.body().getData().getDelivery_options());
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                        }


                    } else {
                        dialog.hide();
                        alertDialogNoRestaurant();
                    }
                } catch (Exception e) {
                    dialog.hide();
                    alertDialogNoRestaurant();
                }
            }

            @Override
            public void onFailure(Call<RestaurantQrResponseBean> call, Throwable t) {
                Log.e("Error <>>>", ">>>>>" + t.getMessage());
                dialog.hide();
                alertDialogNoRestaurant();
            }
        });
    }


    public void alertDialogNoRestaurant() {
        View dialogView = LayoutInflater.from(RestaurantQrActivity.this).inflate(R.layout.layout_dialog_norestaurant, null);
        LayoutDialogNorestaurantBinding dialogBinding = DataBindingUtil.bind(dialogView);
        final Dialog dialogg = new Dialog(RestaurantQrActivity.this);
        dialogg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogg.setContentView(dialogBinding.getRoot());
        dialogg.setCancelable(false);


        dialogBinding.tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogg.dismiss();
                finish();

            }
        });

        dialogg.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        dialogg.getWindow().setAttributes(lp);
        dialogg.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(RestaurantQrActivity.this, R.color.seme_transparent)));
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.seme_transparent)));
    }

    public void dialogNoInternetConnection(String message) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.addnote_success_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setView(mDialogView);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);

        TextView tvMessage = mDialogView.findViewById(R.id.message);
        tvMessage.setText(message);
        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetOn(RestaurantQrActivity.this)) {
                    alertDialog.dismiss();
                } else mDialogView.findViewById(R.id.okTv).startAnimation(animShake);

            }
        });

        alertDialog.show();
    }
}
