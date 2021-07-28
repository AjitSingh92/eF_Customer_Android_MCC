package com.easyfoodcustomer.search_post_code;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.easyfoodcustomer.R;
import com.easyfoodcustomer.databinding.ActivityTrackTraceBinding;
import com.easyfoodcustomer.order_details_activity.OrderDetailActivity;
import com.easyfoodcustomer.search_post_code.api.SearchPostCodeInterface;
import com.easyfoodcustomer.search_post_code.model.search_response.SaveQRInfoBean;
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

public class TrackTraceActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityTrackTraceBinding binding;
    private Dialog dialog;
    private String restaurantName, restaurantId, deliveryOptions;
    private GlobalValues val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_track_trace);
        getDataFromIntent();
        init();
        setListeners();

    }


    private void init() {
        dialog = new Dialog(TrackTraceActivity.this);
        dialog.setTitle("");
        dialog.setCancelable(false);
        val = (GlobalValues) getApplication();
    }

    private void getDataFromIntent() {
        restaurantId = getIntent().getStringExtra("RESTAURANTID");
        restaurantName = getIntent().getStringExtra("RESTAURANTNAME");
        deliveryOptions = getIntent().getStringExtra("DELIVERY_OPTIONS");
        binding.tvRestaurantName.setText(restaurantName);
    }

    private void setListeners() {
        binding.ivBack.setOnClickListener(this);
        binding.tvSubmit.setOnClickListener(this);
        binding.tvSkip.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_submit:
                if (isInternetOn(TrackTraceActivity.this)) {
                    validationVerification();
                } else {
                    dialogNoInternetConnection("Please check internet connection.");
                }
                break;
            case R.id.tv_skip:
                finishAffinity();
                Intent intent = new Intent(TrackTraceActivity.this, SearchPostCodeActivity.class);
                intent.putExtra("IS_FROM_TRACK", true);
                intent.putExtra("RESTAURANTID", restaurantId);
                intent.putExtra("RESTAURANTNAME", restaurantName);
                intent.putExtra("DELIVERY_OPTIONS", deliveryOptions);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

                break;
        }


    }

    private void validationVerification() {
        if (TextUtils.isEmpty(binding.etName.getText().toString().trim())) {
            binding.etName.setError("Please enter your name.");
            binding.etName.requestFocus();
        } else if (TextUtils.isEmpty(binding.etEmail.getText().toString().trim())) {
            binding.etEmail.setError("Please enter email address.");
            binding.etEmail.requestFocus();
        } else if (!Constants.isValidEmail(binding.etEmail.getText().toString())) {
            binding.etEmail.setError("Please enter valid email address.");
            binding.etEmail.requestFocus();
        } else if (TextUtils.isEmpty(binding.etMobile.getText().toString().trim())) {
            binding.etMobile.setError("Please enter mobile number.");
            binding.etMobile.requestFocus();
        } else if (binding.etMobile.getText().toString().trim().length() < 8) {
            binding.etMobile.setError("Please enter valid mobile number.");
            binding.etMobile.requestFocus();
        } else if (!binding.cbTerms.isChecked()) {
            Toast.makeText(this, "Please agree term & condition.", Toast.LENGTH_SHORT).show();
        } else {
            if (ApiClient.isConnected(getApplicationContext())) {
                dialog.show();
                saveInformation();
            } else {
                showDialog("Please check internet connection.");
            }
        }
    }

    public void showDialog(String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(TrackTraceActivity.this);
        builder1.setMessage(msg);
        builder1.setCancelable(false);

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
                if (isInternetOn(TrackTraceActivity.this)) {
                    alertDialog.dismiss();
                } else mDialogView.findViewById(R.id.okTv).startAnimation(animShake);

            }
        });

        alertDialog.show();
    }


    public void saveInformation() {
        SearchPostCodeInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(SearchPostCodeInterface.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("restaurant_id", restaurantId);
        jsonObject.addProperty("name", binding.etName.getText().toString().trim());
        jsonObject.addProperty("email", binding.etEmail.getText().toString().trim());
        jsonObject.addProperty("phone", binding.etMobile.getText().toString().trim());

        Call<SaveQRInfoBean> call3 = apiInterface.saveQRInformation(PrefManager.getInstance(TrackTraceActivity.this).getPreference(AUTH_TOKEN, ""),jsonObject);
        call3.enqueue(new Callback<SaveQRInfoBean>() {
            @Override
            public void onResponse(Call<SaveQRInfoBean> call, Response<SaveQRInfoBean> response) {
                try {

                    if (response.body().isSuccess()) {
                        dialog.hide();
                        finishAffinity();
                        Intent intent = new Intent(TrackTraceActivity.this, SearchPostCodeActivity.class);
                        intent.putExtra("IS_FROM_TRACK", true);
                        intent.putExtra("RESTAURANTID", restaurantId);
                        intent.putExtra("RESTAURANTNAME", restaurantName);
                        intent.putExtra("DELIVERY_OPTIONS", deliveryOptions);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);


                    } else {
                        dialog.hide();
                        errorDialog(response.body().getMessage(), null);
                    }
                } catch (Exception e) {
                    dialog.hide();
                    dialogNoInternetConnection("Please check internet connection.");
                }
            }

            @Override
            public void onFailure(Call<SaveQRInfoBean> call, Throwable t) {
                Log.e("Error <>>>", ">>>>>" + t.getMessage());
                dialog.hide();
                dialogNoInternetConnection("Please check internet connection.");
            }
        });
    }


    public void errorDialog(String msg1, String msg2) {
        View dialogView = LayoutInflater.from(TrackTraceActivity.this).inflate(R.layout.no_resturent_popup, null);
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView.getRootView());
        dialog.setCancelable(false);


        TextView tvMsg1, tvMsg2;

        tvMsg1 = dialogView.findViewById(R.id.tv_msg1);
        tvMsg2 = dialogView.findViewById(R.id.tv_msg2);

        tvMsg1.setText(msg1);
        tvMsg2.setText(msg2);
        if (msg2 == null) {
            tvMsg2.setVisibility(View.GONE);
        }

        dialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();


            }
        });
        dialogView.findViewById(R.id.cross_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
