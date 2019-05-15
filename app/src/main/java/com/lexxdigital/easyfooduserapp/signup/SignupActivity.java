package com.lexxdigital.easyfooduserapp.signup;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lexxdigital.easyfooduserapp.OtpSmsAutoFetch.SmsListener;
import com.lexxdigital.easyfooduserapp.OtpSmsAutoFetch.SmsReceiver;
import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.dashboard.DashboardActivity;
import com.lexxdigital.easyfooduserapp.inrodution_slide.IntroActivity;
import com.lexxdigital.easyfooduserapp.login.LoginActivity;
import com.lexxdigital.easyfooduserapp.login.model.response.LoginResponse;
import com.lexxdigital.easyfooduserapp.search_post_code.SearchPostCodeActivity;
import com.lexxdigital.easyfooduserapp.search_post_code.api.SearchPostCodeInterface;
import com.lexxdigital.easyfooduserapp.search_post_code.model.search_request.SearchPostCodeRequest;
import com.lexxdigital.easyfooduserapp.search_post_code.model.search_response.SearchPostCodeResponse;
import com.lexxdigital.easyfooduserapp.signup.api.FinalSignupInterface;
import com.lexxdigital.easyfooduserapp.signup.api.SendAgainInterface;
import com.lexxdigital.easyfooduserapp.signup.api.SignupRequestInterface;
import com.lexxdigital.easyfooduserapp.signup.model.final_request.SignupFinalRequest;
import com.lexxdigital.easyfooduserapp.signup.model.final_response.SignupFinalResponse;
import com.lexxdigital.easyfooduserapp.signup.model.request.SignupRequest;
import com.lexxdigital.easyfooduserapp.signup.model.response.SignupResponse;
import com.lexxdigital.easyfooduserapp.signup.model.send_again_request.SendAgainRequest;
import com.lexxdigital.easyfooduserapp.signup.model.send_again_response.SendAgainResponse;
import com.lexxdigital.easyfooduserapp.utility.ApiClient;
import com.lexxdigital.easyfooduserapp.utility.ApiConstants;
import com.lexxdigital.easyfooduserapp.utility.Constants;
import com.lexxdigital.easyfooduserapp.utility.GlobalValues;
import com.lexxdigital.easyfooduserapp.utility.SharedPreferencesClass;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.ivToolBarbackTv)
    ImageView ivToolBarbackTv;
    @BindView(R.id.ivToolBarback)
    LinearLayout ivToolBarback;
    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.menuId)
    ImageView menuId;
    @BindView(R.id.toolbarhide)
    LinearLayout toolbarhide;
    @BindView(R.id.profileImg)
    CircleImageView profileImg;
    @BindView(R.id.add_image)
    LinearLayout addImage;
    @BindView(R.id.fm)
    FrameLayout fm;
    @BindView(R.id.edittextFname)
    EditText edittextFname;
    @BindView(R.id.edittextLname)
    EditText edittextLname;
    @BindView(R.id.edittextMobile)
    EditText edittextMobile;
    EditText pin;
    @BindView(R.id.edittextRefCode)
    EditText edittextRefCode;
    @BindView(R.id.disTv)
    TextView disTv;
    @BindView(R.id.sendVerivication)
    LinearLayout sendVerivication;
    @BindView(R.id.container)
    RelativeLayout container;
    @BindView(R.id.cancel_action)
    TextView cancelAction;
    @BindView(R.id.edittextemail)
    EditText edittextemail;
    @BindView(R.id.rl_main)
    RelativeLayout rlMainLayout;
    private Dialog dialog;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private GlobalValues val;
    private String imageString = "   ";
    String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE};
    private final int PICK_IMAGE_CAMERA = 101, PICK_IMAGE_GALLERY = 102;
    private File imgFile;
    private boolean isPopupVisible = false;
    SharedPreferencesClass sharedPreferencesClass;
    private String profileImageString = "";
    private Uri mCropImageUri;
    LinearLayout progress;
    boolean postcodeStatus = false;
    TextView btnContinnue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ButterKnife.bind(this);
        Constants.setStatusBarGradiant(SignupActivity.this);
        requestSmsPermission();

        val = (GlobalValues) getApplicationContext();
        dialog = new Dialog(SignupActivity.this);
        dialog.setTitle("");
        dialog.setCancelable(false);
        sharedPreferencesClass = new SharedPreferencesClass(getApplicationContext());

        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                Log.d("Text", messageText);
                Toast.makeText(SignupActivity.this, "Message: " + messageText, Toast.LENGTH_LONG).show();
            }
        });

        if (Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.LOLLIPOP) {
            String[] perms = {
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.READ_SMS,
//                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.CAMERA
            };


            if (!EasyPermissions.hasPermissions(this, perms)) {
                EasyPermissions.requestPermissions(this, "All permissions are required in oder to run this application", 101, perms);
            }


        }
        ActivityCompat.requestPermissions(SignupActivity.this, new String[]{android.Manifest.permission.RECEIVE_SMS}, 12);

    }

    private void requestSmsPermission() {
        String permission = Manifest.permission.RECEIVE_SMS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }

    @OnClick({R.id.ivToolBarbackTv, R.id.add_image, R.id.sendVerivication})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivToolBarbackTv:
                Constants.back(SignupActivity.this);
                break;
            case R.id.add_image:
//                selectImage();
                CropImage.startPickImageActivity(this);
                break;
            case R.id.sendVerivication:
                if (Constants.isInternetConnectionAvailable(300)) {
                    validationVerification();
                    //       alertDialogforgotPassword();
                } else {
                    dialogNoInternetConnection("Please check internet connection.");
                }
                break;
        }
    }

    @OnClick(R.id.cancel_action)
    public void onViewClicked() {
    }


    private void validationVerification() {

        if (TextUtils.isEmpty(edittextFname.getText().toString().trim())) {
            edittextFname.setError("Please enter first name.");
            edittextFname.requestFocus();
        } else if (TextUtils.isEmpty(edittextLname.getText().toString().trim())) {
            edittextLname.setError("Please enter last name.");
            // Toast.makeText(LoginActivity.this, "Please enter password.", Toast.LENGTH_SHORT).show();
            edittextLname.requestFocus();
        } else if (TextUtils.isEmpty(edittextemail.getText().toString().trim())) {
            edittextemail.setError("Please enter email address.");
            //  Toast.makeText(LoginActivity.this, "Please enter email id.", Toast.LENGTH_SHORT).show();
            edittextemail.requestFocus();
        } else if (!Constants.isValidEmail(edittextemail.getText().toString())) {
            //  Toast.makeText(LoginActivity.this, "Please enter valid email id.", Toast.LENGTH_SHORT).show();
            edittextemail.setError("Please enter valid email address.");
            edittextemail.requestFocus();
        } else if (TextUtils.isEmpty(edittextMobile.getText().toString().trim())) {
            edittextMobile.setError("Please enter mobile number.");
            // Toast.makeText(LoginActivity.this, "Please enter password.", Toast.LENGTH_SHORT).show();
            edittextMobile.requestFocus();
        } else if (edittextMobile.getText().toString().trim().length() < 8) {
            edittextMobile.setError("Please enter valid mobile number.");
            edittextMobile.requestFocus();
        } else if (TextUtils.isEmpty(edittextRefCode.getText().toString().trim())) {

            edittextRefCode.setError("Please enter Post Code.");
            edittextRefCode.requestFocus();

        } else {
            if (ApiClient.isConnected(getApplicationContext())) {
                dialog.show();
//                rlMainLayout.setVisibility(View.GONE);
                callSearchPostAPI(edittextRefCode.getText().toString());

            } else {
                showDialog("Please check internet connection.");
                //      Toast.makeText(LoginActivity.this, "Please check internet connection.", Toast.LENGTH_SHORT).show();
            }
        }

    }


    public void alertDialogforgotPassword(final String fname, final String lname, final String email, final String mobile, final int code) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.sign_up_dialog, null);
        final AlertDialog mDialog = new AlertDialog.Builder(this).create();
        mDialog.setView(mDialogView);
        mDialog.setCancelable(false);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvPhoneEnding = mDialogView.findViewById(R.id.tv_mobileEnding);
        tvPhoneEnding.setText("Please check your mail inbox or text messages for phone number ending " + mobile.substring(mobile.length() - 4, mobile.length()) + " and enter the verification code");
        pin = (EditText) mDialogView.findViewById(R.id.pin_tv);

        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                pin.setText(messageText);
            }
        });

        final EditText password = (EditText) mDialogView.findViewById(R.id.password_tv);
        final EditText re_password = (EditText) mDialogView.findViewById(R.id.re_password_tv);
        TextView sendOTP = (TextView) mDialogView.findViewById(R.id.send_again);
        sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(SignupActivity.this);
                dialog.setTitle("");
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.progress_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                pin.setText("");
                password.setText("");
                re_password.setText("");
                callAPIVerification();
                mDialog.dismiss();
            }
        });
        mDialogView.findViewById(R.id.cross_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                isPopupVisible = false;
                mDialog.dismiss();

            }
        });
        mDialogView.findViewById(R.id.sign_up_btn_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                //   mDialog.dismiss();
                if (pin.getText().toString().trim().length() < 4) {
                    pin.setError("Please enter 4 digit code.");
                    pin.requestFocus();
                    Toast.makeText(SignupActivity.this, "Please enter 4 digit code.", Toast.LENGTH_SHORT).show();
                } else if (!pin.getText().toString().trim().equalsIgnoreCase(String.valueOf(code))) {
                    pin.setError("Entered code is wrong");
                    pin.requestFocus();
                    Toast.makeText(SignupActivity.this, "Entered code is wrong", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password.getText().toString())) {
                    password.setError("Please enter password.");
                    password.requestFocus();
                } else if (password.getText().toString().trim().length() < 6 || password.getText().toString().trim().length() > 20) {
                    password.setError("Password must contain 6 to 20 characters.");
                    password.requestFocus();
                } else if (TextUtils.isEmpty(re_password.getText().toString())) {
                    re_password.setError("Please enter confirm password.");
                    re_password.requestFocus();

                } else if (re_password.getText().toString().trim().length() < 6 || re_password.getText().toString().trim().length() > 20) {
                    re_password.setError("Confirm password must contain 6 to 20 characters.");
                    re_password.requestFocus();

                } else if (!re_password.getText().toString().trim().equals(password.getText().toString().trim())) {
                    re_password.setError("Password mismatch.");
                    re_password.requestFocus();


                } else if (Integer.parseInt(pin.getText().toString()) == code) {
                    isPopupVisible = false;
                    mDialog.dismiss();
//                    callWebviewPrivacy();
                    callAPIFinalSignup(fname, lname, email, mobile, re_password.getText().toString(), password.getText().toString());
                }

            }
        });

        mDialog.show();
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
//        isPopupVisible = false;
        finish();
        super.onBackPressed();

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
    /*   @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        AppEventsLogger.activateApp(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);


    }*/


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
                Log.d("->>>>", " " + message);
                pin.setText(message);
                //Do whatever you want with the code here
            }
        }
    };


    public void callAPIVerification() {
        SignupRequestInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(SignupRequestInterface.class);
        SignupRequest request = new SignupRequest();
        request.setEmail(edittextemail.getText().toString());
        request.setFirstName(edittextFname.getText().toString());
        request.setLastName(edittextLname.getText().toString());
        request.setPhoneNumber(edittextMobile.getText().toString());
        request.setPostcode(edittextRefCode.getText().toString());
        request.setDevice_imei_number(getDeviceIMEI());
        Call<SignupResponse> call3 = apiInterface.mLogin(request);
        call3.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                try {
                    val.setSignupResponse(response.body());
                    //                  //Log.e("Success ><<<<<<<", ">>>>> Success" + response.code()+"//"+response.body().getResponseCode()+"//"+response.body().getResponseMsg());
                    if (response.code() == 200 && response.body().getSuccess()) {
                        dialog.hide();
                        Log.e("Code------> ", "" + response.body().getData().getOtp());
                        //    showDialog(response.body().getMessage() + response.body().getData().getOtp());
//                        if (!isPopupVisible)
                        alertDialogforgotPassword(edittextFname.getText().toString(), edittextLname.getText().toString(), edittextemail.getText().toString(), edittextMobile.getText().toString(), response.body().getData().getOtp());
//                        isPopupVisible = true;
//                        Constants.switchActivity(LoginActivity.this, DashboardActivity.class);
//                        finish();
                    } else if (response.code() == 200 && !response.body().getSuccess()) {
                        dialog.hide();
                        Log.e("signup", "response.body().getErrors().getEmail()" + response.body().getErrors().getEmail() + "onResponse:response.body().getErrors().getPhoneNumber() " + response.body().getErrors().getPhoneNumber());

                        String msg = "";

                        if (response.body().getErrors().getEmail() != null) {
                            msg = msg + response.body().getErrors().getEmail().toString().replaceAll("[\\[,\\]]", "") + "\n";
                        }
                        if (response.body().getErrors().getPhoneNumber() != null) {
                            msg = msg + response.body().getErrors().getPhoneNumber().toString().replaceAll("[\\[,\\]]", "");
                        }

                        showDialog(msg);
                        //    Toast.makeText(LoginActivity.this, response.body().getResponseMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.hide();
                        showDialog(response.body().getMessage());

                        //         Toast.makeText(LoginActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    dialog.hide();
                    showDialog("Please try again.");
                    //    Toast.makeText(LoginActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                //Log.e("Error <>>>",">>>>>"+t.getMessage());
                dialog.hide();
                showDialog("Please try again later. failed");
                //    Toast.makeText(LoginActivity.this, "Please try again 2."+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void callAPIFinalSignup(String fname, String lname, String email, String mobile, String repassword, String password) {
        dialog = new Dialog(SignupActivity.this);
        dialog.setTitle("");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        FinalSignupInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(FinalSignupInterface.class);
        SignupFinalRequest request = new SignupFinalRequest();
        request.setFirstName(fname);
        request.setLastName(lname);
        request.setName(fname + "" + lname);
        request.setEmail(email);
        request.setPhoneNumber(mobile);
        request.setPassword(password);
        request.setPasswordConfirmation(repassword);
        request.setProfilePic(imageString);
        request.setDevice_imei_number(getDeviceIMEI());
        request.setPostalcode(edittextRefCode.getText().toString().trim());

        Log.e("DATA>>", "" + fname + "//" + lname + "//" + email + "//" + mobile + "//" + password + "//" + repassword);
        Call<LoginResponse> call3 = apiInterface.mLogin(request);
        call3.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (dialog.isShowing())
                    dialog.dismiss();
                try {

                    Log.e("Success ><<<<<<<", ">>>>> Success" + response.code() + "//" + response.body().getSuccess() + "//" + response.body().getMessage());
                    if (response.code() == 200 && response.body().getSuccess()) {
                        Log.e(">>>>> ", "  " + "true");
                        // val.setSignupResponse(response.body());


                        val.setLoginResponse(response.body());
//                        if (response.code() == 200 && response.body().getSuccess()) {

//                        forgotDialog.dismiss();
//                        forgotDialog.cancel();
//                        forgotDialog.hide();
//                        val.setPostCode(response.body().getData().getPost_code());
                        val.setProfileImage(response.body().getData().getProfilePic());
                        val.setFirstName(response.body().getData().getFirstName());
                        val.setLastName(response.body().getData().getLastName());
                        val.setUserName(response.body().getData().getName());
                        val.setMobileNo(response.body().getData().getPhoneNumber());
                        val.setPostCode(edittextRefCode.getText().toString());
                        sharedPreferencesClass.setPostalCode(edittextRefCode.getText().toString());
                        sharedPreferencesClass.setObject(sharedPreferencesClass.LoginResponseKey, new Gson().toJson(response.body()));
                        sharedPreferencesClass.setloginpref(response.body().getData().getUserId());
                        String postcode = sharedPreferencesClass.getPostalCode();
                        sharedPreferencesClass.setString(sharedPreferencesClass.USER_ID, response.body().getData().getUserId());
                        sharedPreferencesClass.setString(sharedPreferencesClass.LOGIN_VIA, Constants.LOGIN_WITH_OTHER);

                        sharedPreferencesClass.setloginpref("1"); // isLogin
                        sharedPreferencesClass.setString(sharedPreferencesClass.USER_NAME, response.body().getData().getName());
                        sharedPreferencesClass.setString(sharedPreferencesClass.USER_PROFILE_IMAGE, response.body().getData().getProfilePic());
                        sharedPreferencesClass.setInt(sharedPreferencesClass.NUMBER_OF_ORDERS, response.body().getData().getPrevious_orders());


                          /*  Intent i = new Intent(SignupActivity.this, DashboardActivity.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);*/
                        dialog.dismiss();

                        //  finish();

//                        }
//                        showDialogSuccess(response.body().getMessage());
                        callWebviewPrivacy();

                        // goBack();
                    } else if (response.code() == 200 && !response.body().getSuccess()) {
                        dialog.dismiss();
                        // Log.e("signup ", "onResponse: " + response.body().getErrors().getFirstName());
                        // Log.e(">>>>> ", "  " + "false");

                        showDialog(response.body().getMessage());
                        //    Toast.makeText(LoginActivity.this, response.body().getResponseMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Log.e(">>>>> ", "  " + "after false");

                        showDialog(response.body().getMessage());
                        //         Toast.makeText(LoginActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                    showDialog("Please try again.");
                    //    Toast.makeText(LoginActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                //Log.e("Error <>>>",">>>>>"+t.getMessage());
                if (dialog.isShowing())
                    dialog.dismiss();
                showDialog("Please try again." + t.getMessage());
                //    Toast.makeText(LoginActivity.this, "Please try again 2."+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void showDialog(String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(SignupActivity.this);
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

    public void showDialogSuccess(String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(SignupActivity.this);
        builder1.setMessage(msg);
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog2, int id) {
                        callWebviewPrivacy();
                        dialog2.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();


        alert11.show();
    }

    public void goBack() {
        Constants.switchActivity(SignupActivity.this, DashboardActivity.class);
        finish();
    }

    void dialogMarketCommunication() {

        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.pop_market_communication, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setView(mDialogView);
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ((TextView) mDialogView.findViewById(R.id.tv_message)).setText(this.getString(R.string.market_communication));


        mDialogView.findViewById(R.id.tv_DontAllow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                alertDialog.dismiss();
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sharedPreferencesClass.setloginpref(null);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        mDialogView.findViewById(R.id.tv_Allow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActivity.this, DashboardActivity.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                alertDialog.dismiss();
                SignupActivity.this.finish();
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        mDialogView.findViewById(R.id.cross_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                alertDialog.dismiss();
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sharedPreferencesClass.setloginpref(null);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

        alertDialog.show();
    }

    private void selectImage() {
        try {
            PackageManager pm = getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(SignupActivity.this);
                builder.setTitle("Select Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            dialog.dismiss();
//
//                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                            startActivityForResult(intent, PICK_IMAGE_CAMERA);

                            Intent m_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
                            Uri uri = FileProvider.getUriForFile(SignupActivity.this, getApplicationContext().getPackageName() + ".provider", file);
                            m_intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            startActivityForResult(m_intent, PICK_IMAGE_CAMERA);
                        } else if (options[item].equals("Choose From Gallery")) {
                            dialog.dismiss();
//                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_GALLERY);
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else {
                EasyPermissions.requestPermissions(this, "All permissions are required in oder to run this application", 101, permissions);
                Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_CAMERA) {
            try {
                if (requestCode == 101 && resultCode == RESULT_OK) {
                    imgFile = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), bmOptions);
                    //bitmap = Bitmap.createScaledBitmap(bitmap,parent.getWidth(),parent.getHeight(),true);
                    profileImg.setImageBitmap(bitmap);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    imageString = "data:image/png;base64," + Base64.encodeToString(byteArray, Base64.DEFAULT);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (requestCode == PICK_IMAGE_GALLERY) {

            try {
                if (requestCode == 102 && resultCode == RESULT_OK) {
                    Uri imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    profileImg.setImageBitmap(bitmap);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    imageString = "data:image/png;base64," + Base64.encodeToString(byteArray, Base64.DEFAULT);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    profileImg.setImageURI(result.getUri());
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri());
                    profileImg.setImageBitmap(bitmap);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    profileImageString = "data:image/png;base64," + Base64.encodeToString(byteArray, Base64.DEFAULT);
                } catch (Exception e) {

                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // required permissions granted, start crop image activity
            startCropImageActivity(mCropImageUri);
        } else {
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
//            Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    /**
     * Start crop image activity for the given image.
     */
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    public void sendOTPAgain(String email, String phone) {
        SendAgainInterface apiInterface = ApiClient.getClient(this).create(SendAgainInterface.class);
        SendAgainRequest request = new SendAgainRequest();
        request.setEmail(email);
        request.setPhoneNumber(phone);

        Call<SendAgainResponse> call3 = apiInterface.mSendOTP(request);
        call3.enqueue(new Callback<SendAgainResponse>() {
            @Override
            public void onResponse(Call<SendAgainResponse> call, Response<SendAgainResponse> response) {
                try {
                    dialog.hide();
                    if (response.body().getSuccess()) {

                        Toast.makeText(SignupActivity.this,
                                "OPT has been sent successfully to your mail id.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SignupActivity.this,
                                "Failed to send. Please send again.", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    dialog.hide();
                    Toast.makeText(SignupActivity.this,
                            "Failed to send. Please send again." + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SendAgainResponse> call, Throwable t) {
                dialog.hide();
                Toast.makeText(SignupActivity.this,
                        "Failed to send. Please send again." + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public String getDeviceIMEI() {
        String deviceUniqueIdentifier = "";
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != tm) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling

                return null;
            }
            deviceUniqueIdentifier = tm.getDeviceId();
        }
        if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
            deviceUniqueIdentifier = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceUniqueIdentifier;
    }


    void callWebviewPrivacy() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.privacy_dialog, null);
        final AlertDialog mDialog = new AlertDialog.Builder(this).create();
        mDialog.setView(mDialogView);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final TextView privacyDesc = mDialogView.findViewById(R.id.desc_privacy);
        final TextView privacyPolc = mDialogView.findViewById(R.id.privacy_polc);
        btnContinnue = mDialogView.findViewById(R.id.btn_continue);
        progress = mDialogView.findViewById(R.id.progress);
        final WebView webView = mDialogView.findViewById(R.id.web_privacy_policy);
        webView.setVisibility(View.GONE);
        mDialogView.findViewById(R.id.cross_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                dialogMarketCommunication(); // Open market Communication Dialog
                mDialog.dismiss();
            }
        });

        btnContinnue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                privacyDesc.setVisibility(View.GONE);
                btnContinnue.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                // privacyPolc.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                webView.setWebViewClient(new MyWebViewClient());
                webView.loadUrl(ApiConstants.PRIVACY_POLICY);
            }

        });

        mDialog.show();

    }


    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            try {
                if (dialog != null)
                    dialog.dismiss();
                if (progress.getVisibility() == View.VISIBLE) {
                    progress.setVisibility(View.GONE);
                }
                view.setVisibility(View.VISIBLE);
            } catch (NullPointerException e) {
                e.getLocalizedMessage();
            }
        }
    }

    public void callSearchPostAPI(String postcode) {

        SearchPostCodeInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(SearchPostCodeInterface.class);

        SearchPostCodeRequest request = new SearchPostCodeRequest();
        request.setPostCode(postcode);

        Call<SearchPostCodeResponse> call3 = apiInterface.mSearchPost(request);
        call3.enqueue(new Callback<SearchPostCodeResponse>() {
            @Override
            public void onResponse(Call<SearchPostCodeResponse> call, Response<SearchPostCodeResponse> response) {
                try {

                    if (response.body().getSuccess()) {

                        callAPIVerification();
                    } else {
                        dialog.hide();
                        edittextRefCode.setError("Please enter valid Post Code.");
                        edittextRefCode.requestFocus();
                    }
                } catch (Exception e) {
                    dialog.hide();
                    edittextRefCode.setError("Please enter valid Post Code.");
                    edittextRefCode.requestFocus();
                    Log.e("Error <>>>", ">>>>>" + e.getMessage());

                }
            }

            @Override
            public void onFailure(Call<SearchPostCodeResponse> call, Throwable t) {
                Log.e("Error <>>>", ">>>>>" + t.getMessage());
                dialog.hide();


            }
        });
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
                if (Constants.isInternetConnectionAvailable(300)) {
                    alertDialog.dismiss();
                } else mDialogView.findViewById(R.id.okTv).startAnimation(animShake);

            }
        });

        alertDialog.show();
    }
}
