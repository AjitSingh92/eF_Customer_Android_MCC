package com.lexxdigital.easyfooduserapps.customer_review;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.api.RestaurentReviewInterface;
import com.lexxdigital.easyfooduserapps.model.review_restaurants.RestoReviewRequest;
import com.lexxdigital.easyfooduserapps.model.review_restaurants.RestoReviewResponse;
import com.lexxdigital.easyfooduserapps.order_details_activity.OrderDetailActivity;
import com.lexxdigital.easyfooduserapps.utility.ApiClient;
import com.lexxdigital.easyfooduserapps.utility.Constants;
import com.lexxdigital.easyfooduserapps.utility.GlobalValues;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerReviewProcess extends AppCompatActivity {
    RatingBar ratFoodQuality, ratDelivery, ratLikeOrderAgain, ratLikeRecom;
    String strRatfood, strRatdelv, strOrderagain, strRecommend, strRestoId, strCustomerId, strOrderId, strOverAll = "1";
    String strRestoName, strOrderNo, strRestoLogo, strRestoImage, strRestoAddress;
    GlobalValues val;
    TextView submit, txtRestoName, txtRestoAddress, txttoolBar, txtRestoOrderNo, btnOk;
    ImageView imgRestoImage, imgBackTv;
    CircleImageView crlRestoLogo;
    float food, delivery, orderAgain, recommed;
    Dialog dialog;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_customer_review_process);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Constants.setStatusBarGradiant(this);
        dialog = new Dialog(this);
        dialog.setTitle("");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ratFoodQuality = (RatingBar) findViewById(R.id.rat_food_quality);
        ratDelivery = (RatingBar) findViewById(R.id.rat_delivery);
        ratLikeOrderAgain = (RatingBar) findViewById(R.id.rat_like_orderagain);
        ratLikeRecom = (RatingBar) findViewById(R.id.like_recommend);
        val = (GlobalValues) getApplicationContext();
        strCustomerId = val.getLoginResponse().getData().getUserId();

        submit = findViewById(R.id.submit_process);
        txtRestoName = findViewById(R.id.resto_name);
        txtRestoAddress = findViewById(R.id.restoAddress);
        txttoolBar = findViewById(R.id.tvToolbarTitle);
        txtRestoOrderNo = findViewById(R.id.order_no);
        imgRestoImage = findViewById(R.id.restoimage);
        imgBackTv = findViewById(R.id.ivToolBarbackTv);
        crlRestoLogo = findViewById(R.id.restologo);
        Bundle extras = new Bundle();
        extras = getIntent().getExtras();
        strRestoId = extras.getString("restoId");
        Log.e("review: ", "onCreate: " + strOrderId);
        if (extras != null) {

            strRestoId = extras.getString("restoId");
            strOrderId = extras.getString("orderId");
            strOrderNo = extras.getString("orderNo");
            strRestoName = extras.getString("restoname");
            strRestoLogo = extras.getString("restologo");
            strRestoImage = extras.getString("restoimage");
            strRestoAddress = extras.getString("restoAdd");
            // and get whatever type user account id is
            Log.e("review:", "onCreate:strRestoId " + strRestoId + "strOrderId:" + strOrderId + " strOrderNo: " + strOrderNo + " strRestoName: " + strRestoName + "strRestoLogo:" + strRestoLogo + " strRestoImage: " + strRestoImage);

        }

        txtRestoName.setText(strRestoName);
        txttoolBar.setText(strRestoName);
        txtRestoOrderNo.setText("Order No. " + strOrderNo);

        if (strRestoAddress != null && !strRestoAddress.equalsIgnoreCase("")) {
            txtRestoAddress.setText(strRestoAddress);
        } else {
            txtRestoAddress.setVisibility(View.GONE);
        }

        try {
            Glide.with(this).load(strRestoImage).centerCrop().into(imgRestoImage);
            Glide.with(this).load(strRestoLogo).centerCrop().into(crlRestoLogo);

        } catch (Exception e) {

        }
        ratFoodQuality.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Log.e("rating", "onRatingChanged food: " + rating);
                if (rating < 1.0f) {
                    ratingBar.setRating(1.0f);
                    rating = 1.0f;
                }
                food = rating;
                checkRating();
            }
        });
        ratDelivery.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Log.e("rating", "onRatingChanged delv: " + rating);
                if (rating < 1.0f) {
                    ratingBar.setRating(1.0f);
                    rating = 1.0f;
                }
                delivery = rating;
                checkRating();
            }
        });
        ratLikeOrderAgain.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Log.e("rating", "onRatingChanged order again: " + rating);
                if (rating < 1.0f) {
                    ratingBar.setRating(1.0f);
                    rating = 1.0f;
                }
                orderAgain = rating;
                checkRating();

            }
        });
        ratLikeRecom.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Log.e("rating", "onRatingChanged recom: " + rating);
                if (rating < 1.0f) {
                    ratingBar.setRating(1.0f);
                    rating = 1.0f;
                }
                recommed = rating;
                checkRating();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float overAllRating = 0;
                overAllRating = ((food + delivery + orderAgain + recommed) / 4);
                if (overAllRating < 1.0f) {
                    overAllRating = 1.0f;
                }
                strRatfood = String.valueOf(food);
                strRatdelv = String.valueOf(delivery);
                strOrderagain = String.valueOf(orderAgain);
                strRecommend = String.valueOf(recommed);
                strOverAll = String.valueOf(overAllRating);
                Log.e("Review: ", "onClick:strOverAll " + strOverAll + " " + strRatfood + " " + strRatdelv + " " + strOrderagain + " " + strRecommend);
                if (food > 0 && delivery > 0) {
                    dialog.show();
                    ratingSubmit(strOverAll, strRatfood, strRatdelv, strOrderagain, strRecommend);

                } else {
                    Toast.makeText(CustomerReviewProcess.this, "Please Share your feed back", Toast.LENGTH_LONG).show();
                }
                // Toast.makeText(this, strRatfood, Toast.LENGTH_LONG).show();
            }
        });
        imgBackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.switchActivity(CustomerReviewProcess.this, OrderDetailActivity.class);
                // finish();
                //overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });
    }

    void checkRating() {
        if (food >= 1.0f && delivery >= 1.0f && orderAgain >= 1.0f && recommed >= 1.0f) {
            submit.setEnabled(true);
            submit.setBackgroundResource(R.color.orange);
        } else {
            submit.setBackgroundResource(R.color.gray);
            submit.setEnabled(false);
        }
    }

    void ratingSubmit(String overAll, String ratFood, String ratDelv, String ratOrdAgn, String strRecommend) {
        Log.e("Review: ", "onClick:ratingSubmit " + strRestoId + "strCustomerId " + strCustomerId + strRatfood + " " + strRatdelv + " " + strOrderagain + " " + strRecommend);

        RestaurentReviewInterface apiInterface = ApiClient.getClient(this).create(RestaurentReviewInterface.class);
        final RestoReviewRequest request = new RestoReviewRequest(strRestoId, strCustomerId, strOrderId, overAll, ratFood, ratDelv, ratOrdAgn, strRecommend);
        Call<RestoReviewResponse> call = apiInterface.mRestoRiview(request);
        call.enqueue(new Callback<RestoReviewResponse>() {
            @Override
            public void onResponse(Call<RestoReviewResponse> call, Response<RestoReviewResponse> response) {
                if (response.body().getSuccess()) {
                    dialog.hide();
                    //successDialog();
                    LayoutInflater factory = LayoutInflater.from(CustomerReviewProcess.this);
                    final View mDialogView = factory.inflate(R.layout.ratingsuccess_dialog, null);
                    final AlertDialog forgotDialog = new AlertDialog.Builder(CustomerReviewProcess.this).create();
                    forgotDialog.setView(mDialogView);
                    forgotDialog.setCancelable(false);
                    forgotDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //your business logic
                            try {
                                forgotDialog.dismiss();
                                Intent intent = new Intent();
                                intent.putExtra("done", true);
                                setResult(RESULT_OK, intent);
                                finish();
                                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                            } catch (Exception e) {
                                Log.e("Review:", "Dialog: " + e.getMessage());
                            }
                        }
                    });
                    forgotDialog.show();
//                    btnOk.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            try {
//                                aldialog.hide();
//                                Intent intent = new Intent(CustomerReviewProcess.this, OrderDetailActivity.class);
//                                intent.putExtra("order_no", strOrderNo);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(intent);
//                                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
//                            } catch (Exception e) {
//                                Log.e("Review:", "Dialog: " + e.getMessage());
//                            }
//                        }
//                    });

                    //    aldialog.show();


                    Log.e("CustomerReviewProcess", "onResponse: " + response.body().getMessage());
                } else {
                    Toast.makeText(CustomerReviewProcess.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    dialog.hide();
                }
            }

            @Override
            public void onFailure(Call<RestoReviewResponse> call, Throwable t) {
                dialog.hide();
                Toast.makeText(CustomerReviewProcess.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("Failed<<0>>>", "onResponse: " + t.getMessage());

            }
        });

    }

    void successDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View viewCustom = inflater.inflate(R.layout.ratingsuccess_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(viewCustom);
        // dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btnOk = (TextView) dialog.findViewById(R.id.okTv);
        try {
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.hide();
                    overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                }
            });
        } catch (Exception e) {
            Log.e("Review:", "successDialog: " + e.getMessage());
        }

        /* LayoutInflater factory = LayoutInflater.from(this);
    final View mDialogView = factory.inflate(R.layout.sign_up_dialog, null);
    final AlertDialog mDialog = new AlertDialog.Builder(this).create();
    mDialog.setView(mDialogView);
    mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    */


        dialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);


        // Constants.switchActivity(CustomerReviewProcess.this, OrderDetailActivity.class);

        //overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);


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
}
