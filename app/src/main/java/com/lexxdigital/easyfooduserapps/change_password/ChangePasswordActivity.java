package com.lexxdigital.easyfooduserapps.change_password;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.api.ChangePasswordInterface;
import com.lexxdigital.easyfooduserapps.api.MyAccountInterface;
import com.lexxdigital.easyfooduserapps.model.changepassword.ChangePasswordRequest;
import com.lexxdigital.easyfooduserapps.model.changepassword.ChangePasswordResponse;
import com.lexxdigital.easyfooduserapps.model.my_account_request.MyAccountRequest;
import com.lexxdigital.easyfooduserapps.model.my_account_response.MyAccountResponse;
import com.lexxdigital.easyfooduserapps.utility.ApiClient;
import com.lexxdigital.easyfooduserapps.utility.Constants;
import com.lexxdigital.easyfooduserapps.utility.GlobalValues;
import com.lexxdigital.easyfooduserapps.utility.SharedPreferencesClass;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    @BindView(R.id.edit_current_password)
    EditText editCurrentPassword;
    @BindView(R.id.edit_new_password)
    EditText editNewPassword;
    @BindView(R.id.edit_conf_password)
    EditText editConfPassword;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_address)
    TextView userAddress;
    @BindView(R.id.user_phone)
    TextView userPhone;
    @BindView(R.id.profileImg)
    CircleImageView profileImg;
    @BindView(R.id.profileImg2)
    CircularImageView profileImg2;
    private Dialog dialog;
    String customerid = "";
    String name = "";
    String pic = "";
    private GlobalValues val;
    SharedPreferencesClass sharePre;
    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        ButterKnife.bind(this);
        Constants.setStatusBarGradiant(this);
        Bundle extras = getIntent().getExtras();
        val = (GlobalValues) getApplicationContext();
        dialog = new Dialog(ChangePasswordActivity.this);
        dialog.setTitle("");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        sharePre = new SharedPreferencesClass(ChangePasswordActivity.this);

        pic = sharePre.getString(sharePre.USER_PROFILE_IMAGE);
        String strname = sharePre.getString(sharePre.USER_NAME);

        String strAddress = val.getDefaltAddress();
        String strMobile = val.getMobileNo();


        if (strname != null && !strname.equals("")) {
            userName.setText(strname);
            userName.setVisibility(View.VISIBLE);
        } else {
            userName.setVisibility(View.GONE);
        }

        if (strAddress != null && !strAddress.trim().equals("")) {
            userAddress.setText(strAddress);
            userAddress.setVisibility(View.VISIBLE);
        } else {
            userAddress.setVisibility(View.GONE);
        }
        if (strMobile != null && !strMobile.equals("")) {
            userPhone.setText(strMobile);
        } else {
            userPhone.setVisibility(View.GONE);
        }

        if (sharePre.getString(sharePre.USER_PROFILE_IMAGE) != null) {



            Glide.with(ChangePasswordActivity.this).load(sharePre.getString(sharePre.USER_PROFILE_IMAGE)).apply(new RequestOptions()
                    .placeholder(R.mipmap.avatar_profile))
                    .into(profileImg2);

        }

        if (extras != null) {
            customerid = extras.getString("customer_id");


        }

    }

    void validField() {
        if (TextUtils.isEmpty(editCurrentPassword.getText().toString().trim())) {
            editCurrentPassword.setError("Please enter current password.");
            editCurrentPassword.requestFocus();
        } else if (editCurrentPassword.getText().toString().trim().length() < 6 || editCurrentPassword.getText().toString().trim().length() > 20) {
            editCurrentPassword.setError("Password must contain 6 to 20  characters.");
            editCurrentPassword.requestFocus();
        } else if (TextUtils.isEmpty(editNewPassword.getText().toString().trim())) {
            editNewPassword.setError("Please enter New password.");
            editNewPassword.requestFocus();
        } else if (editNewPassword.getText().toString().trim().length() < 6 || editNewPassword.getText().toString().trim().length() > 20) {
            editNewPassword.setError("New Password must contain 6 to 20  characters.");
            editNewPassword.requestFocus();
        } else if (TextUtils.isEmpty(editConfPassword.getText().toString().trim())) {
            editConfPassword.setError("Please enter Confirm password.");
            editConfPassword.requestFocus();
        } else if (editConfPassword.getText().toString().trim().length() < 6 || editConfPassword.getText().toString().trim().length() > 20) {
            editConfPassword.setError("Confirm Password must contain 6 to 20  characters.");
            editConfPassword.requestFocus();
        } else if (!editNewPassword.getText().toString().trim().equals(editConfPassword.getText().toString().trim())) {
            editConfPassword.setError("Password mismatch.");
            editConfPassword.requestFocus();
        } else {
            if (ApiClient.isConnected(getApplicationContext())) {
                dialog.show();
                callAPI(editCurrentPassword.getText().toString(), editNewPassword.getText().toString(), editConfPassword.getText().toString());
            } else {
                Toast.makeText(ChangePasswordActivity.this, "Please check internet connection.", Toast.LENGTH_LONG).show();
                //      Toast.makeText(LoginActivity.this, "Please check internet connection.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    void callAPI(String curPass, String newPass, String confPassword) {
        ChangePasswordInterface changePasswordInterface = ApiClient.getClient(this).create(ChangePasswordInterface.class);
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setCustomerId(customerid);
        request.setCurrentPassword(curPass);
        request.setPassword(newPass);
        request.setPasswordConfirmation(confPassword);

        Call<ChangePasswordResponse> call = changePasswordInterface.changePassword(request);
        call.enqueue(new Callback<ChangePasswordResponse>() {

            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                dialog.dismiss();
                if (response.body().getSuccess()) {
                    String message = response.body().getMessage();
                    Toast.makeText(ChangePasswordActivity.this, "" + message, Toast.LENGTH_LONG).show();
                    finish();
                    overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                } else {

                    Toast.makeText(ChangePasswordActivity.this, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                Log.e("Change password", "onFailure: " + t.getMessage());
                dialog.dismiss();
            }
        });
    }



    @OnClick({R.id.back, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                break;
            case R.id.btn_confirm:
                validField();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    @Override
    protected void onResume() {
        super.onResume();

        userName.setText(val.getUserName());
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
