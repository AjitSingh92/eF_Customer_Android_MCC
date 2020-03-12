package com.lexxdigital.easyfooduserapps.edit_my_account;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.JsonObject;
import com.lexxdigital.easyfooduserapps.OtpSmsAutoFetch.SmsListener;
import com.lexxdigital.easyfooduserapps.OtpSmsAutoFetch.SmsReceiver;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.api.EditProfileInterface;
import com.lexxdigital.easyfooduserapps.api.UpdateMobileInterface;
import com.lexxdigital.easyfooduserapps.api.VerifyOtpInterface;
import com.lexxdigital.easyfooduserapps.model.edit_account_request.EditAccountRequest;
import com.lexxdigital.easyfooduserapps.model.edit_account_response.EditAccountResponse;
import com.lexxdigital.easyfooduserapps.utility.ApiClient;
import com.lexxdigital.easyfooduserapps.utility.ApiConstants;
import com.lexxdigital.easyfooduserapps.utility.Constants;
import com.lexxdigital.easyfooduserapps.utility.GlobalValues;
import com.lexxdigital.easyfooduserapps.utility.SharedPreferencesClass;
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

public class EditMyAccountActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarhide)
    RelativeLayout toolbarhide;
    @BindView(R.id.top_address)
    LinearLayout topAddress;
    @BindView(R.id.profileImg)
    CircleImageView profileImg;
    @BindView(R.id.add_image)
    LinearLayout addImage;
    @BindView(R.id.fm)
    FrameLayout fm;
    @BindView(R.id.edit_first_name)
    EditText editFirstName;
    @BindView(R.id.edit_last_name)
    EditText editLastName;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.edit_mobile)
    EditText editMobile;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.user_name)
    TextView userName;
    EditText pin;
    SimpleDraweeView image;

    @BindView(R.id.user_phone)
    TextView userPhone;
    @BindView(R.id.user_address)
    TextView userAddress;
    private GlobalValues val;
    private Dialog dialog;
    String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE};
    private final int PICK_IMAGE_CAMERA = 101, PICK_IMAGE_GALLERY = 102;
    private File imgFile;
    private String profileImageString = "";
    SharedPreferencesClass sharePre;
    private Uri mCropImageUri;
    FirebaseAnalytics mFirebaseAnalytics;
    private String previousMobileNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_account);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Constants.setStatusBarGradiant(EditMyAccountActivity.this);
        image = findViewById(R.id.image);

        sharePre = new SharedPreferencesClass(EditMyAccountActivity.this);
        val = (GlobalValues) getApplicationContext();
        dialog = new Dialog(EditMyAccountActivity.this);
        dialog.setTitle("");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ButterKnife.bind(this);
        email.setText(val.getLoginResponse().getData().getEmail());
        userName.setText(val.getUserName());
        String strAddress = val.getDefaltAddress();
        if (strAddress != null && !strAddress.trim().equals("")) {
            userAddress.setText(strAddress);

        } else {
            userAddress.setVisibility(View.GONE);
        }

        userPhone.setText("Tel: " + val.getMobileNo());
        Glide.with(EditMyAccountActivity.this).load(val.getProfileImage()).apply(new RequestOptions()
                .placeholder(R.mipmap.avatar_profile))
                .into(profileImg);

        image.setVisibility(View.GONE);
        profileImg.setVisibility(View.VISIBLE);
        editFirstName.setText(val.getFirstName());
        editLastName.setText(val.getLastName());
        previousMobileNo = val.getMobileNo();
        editMobile.setText(val.getMobileNo());


        String profileImageStr = val.getProfileImage();
        if (!profileImageStr.equalsIgnoreCase(ApiConstants.BASE_IMAGE_URL) && profileImageStr != null) {
            Uri uri = Uri.parse(profileImageStr);

            if (uri != null) {
                image.setImageURI(uri);
            }
        }

        if (Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.LOLLIPOP) {
            String[] perms = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.CAMERA
            };


            if (!EasyPermissions.hasPermissions(this, perms)) {
                EasyPermissions.requestPermissions(this, "All permissions are required in oder to run this application", 101, perms);
            }


        }
        ActivityCompat.requestPermissions(EditMyAccountActivity.this, new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 12);
    }

    @OnClick({R.id.back, R.id.add_image, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                break;
            case R.id.add_image:
                CropImage.startPickImageActivity(this);
                break;
            case R.id.btn_confirm:
                if (TextUtils.isEmpty(editFirstName.getText().toString().trim())) {
                    editFirstName.setError("Please Enter First Name");
                    editFirstName.requestFocus();

                } else if (TextUtils.isEmpty(editLastName.getText().toString().trim())) {
                    editLastName.setError("Please Enter Last Name");
                    editLastName.requestFocus();
                } else if (TextUtils.isEmpty(editMobile.getText().toString().trim())) {
                    editMobile.setError("Please Enter Mobile Number");
                    editMobile.requestFocus();
                } else if (editMobile.getText().toString().trim().length() < 8) {
                    editMobile.setError("Please Enter valid Mobile No.");
                    editMobile.requestFocus();
                } else {
                    dialog.show();
                    if (editMobile.getText().toString().equalsIgnoreCase(previousMobileNo)) {
                        updateAccountDetail();
                    } else {
                        upadteMobileNo();
                    }
                }
                break;
        }
    }

    public void updateAccountDetail() {
        EditProfileInterface apiInterface = ApiClient.getClient(this).create(EditProfileInterface.class);
        final EditAccountRequest request = new EditAccountRequest();
        request.setCustomerId(val.getLoginResponse().getData().getUserId());
        request.setFirstName(editFirstName.getText().toString());
        request.setLastName(editLastName.getText().toString());
        request.setPhoneNumber(editMobile.getText().toString());
        request.setProfilePic(profileImageString);


        Call<EditAccountResponse> call3 = apiInterface.mupdate(request);
        call3.enqueue(new Callback<EditAccountResponse>() {
            @Override
            public void onResponse(Call<EditAccountResponse> call, Response<EditAccountResponse> response) {
                try {
                    dialog.hide();
                    if (response.body().getSuccess()) {
                        successDialog();
                        val.setUserName(response.body().getData().getFirstName() + " " + response.body().getData().getLastName());
                        val.setMobileNo(response.body().getData().getPhoneNumber());
                        val.setProfileImage(response.body().getData().getProfilePic());
                        val.setFirstName(response.body().getData().getFirstName());
                        val.setLastName(response.body().getData().getLastName());
                        val.setProfileImage(response.body().getData().getProfilePic());
                        sharePre.setString(sharePre.USER_PROFILE_IMAGE, response.body().getData().getProfilePic());

                    } else {

                        showDialog(response.body().getErrors().getPhoneNumber().get(0));
                    }
                } catch (Exception e) {
                    dialog.hide();
                    showDialog("Failed to change account details. Please try again.");
                }
            }

            @Override
            public void onFailure(Call<EditAccountResponse> call, Throwable t) {
                Log.e("Error12 <>>>", ">>>>>" + t.getMessage());
                showDialog("Failed to change account details. Please try again.");
                dialog.hide();
            }
        });
    }

    public void showDialog(String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog2, int id) {
                        dialog2.cancel();
                        finish();
                        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }



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
                    image.setImageURI(result.getUri());
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
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // required permissions granted, start crop image activity
            startCropImageActivity(mCropImageUri);
        } else {
//            Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

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


    public void upadteMobileNo() {
        UpdateMobileInterface apiInterface = ApiClient.getClient(this).create(UpdateMobileInterface.class);
        final JsonObject object = new JsonObject();
        object.addProperty("userid", val.getLoginResponse().getData().getUserId());
        object.addProperty("phone", editMobile.getText().toString());
        Call<EditAccountResponse> call3 = apiInterface.updateMobile(object);
        call3.enqueue(new Callback<EditAccountResponse>() {
            @Override
            public void onResponse(Call<EditAccountResponse> call, Response<EditAccountResponse> response) {
                try {
                    dialog.hide();
                    alertOtpVerification();
                } catch (Exception e) {
                    dialog.hide();
                    showDialog("Failed to change account details. Please try again.");
                }
            }

            @Override
            public void onFailure(Call<EditAccountResponse> call, Throwable t) {
                showDialog("Failed to change account details. Please try again.");
                dialog.hide();

            }
        });
    }


    public void alertOtpVerification() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.popup_otp_verify, null);
        final AlertDialog mDialog = new AlertDialog.Builder(this).create();
        mDialog.setView(mDialogView);
        mDialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        mDialog.getWindow().setAttributes(lp);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tvPhoneEnding = mDialogView.findViewById(R.id.tv_mobileEnding);
        tvPhoneEnding.setText("For security reason we need you to verify your new mobile number. Please check your text messages on phone number ending " + editMobile.getText().toString().trim().substring(editMobile.getText().toString().trim().length() - 4, editMobile.getText().toString().trim().length()) + " and enter the verification code below");
        pin = (EditText) mDialogView.findViewById(R.id.pin_tv);

        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                pin.setText(messageText);
            }
        });


        TextView sendOTP = (TextView) mDialogView.findViewById(R.id.send_again);
        sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(EditMyAccountActivity.this);
                dialog.setTitle("");
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.progress_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                pin.setText("");
                upadteMobileNo();
                mDialog.dismiss();
            }
        });
        mDialogView.findViewById(R.id.cross_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();

            }
        });
        mDialogView.findViewById(R.id.sign_up_btn_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pin.getText().toString().trim().length() < 4) {
                    pin.setError("Please enter 4 digit code.");
                    pin.requestFocus();
                    Toast.makeText(EditMyAccountActivity.this, "Please enter 4 digit code.", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.show();
                    verifyOtp(pin.getText().toString().trim());
                    mDialog.dismiss();

                }

            }
        });

        mDialog.show();
    }


    public void verifyOtp(final String otpPin) {
        VerifyOtpInterface apiInterface = ApiClient.getClient(this).create(VerifyOtpInterface.class);
        final JsonObject object = new JsonObject();
        object.addProperty("userid", val.getLoginResponse().getData().getUserId());
        object.addProperty("otp", otpPin);
        Call<EditAccountResponse> call3 = apiInterface.verifyOtp(object);
        call3.enqueue(new Callback<EditAccountResponse>() {
            @Override
            public void onResponse(Call<EditAccountResponse> call, Response<EditAccountResponse> response) {
                try {
                    updateAccountDetail();


                } catch (Exception e) {
                    dialog.hide();
                    showDialog("Failed to change account details. Please try again.");
                }
            }

            @Override
            public void onFailure(Call<EditAccountResponse> call, Throwable t) {
                showDialog("Failed to change account details. Please try again.");
                dialog.hide();
            }
        });
    }


    public void successDialog() {
        View dialogView = LayoutInflater.from(EditMyAccountActivity.this).inflate(R.layout.layout_success_dialog, null);
        final Dialog dialog = new Dialog(EditMyAccountActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);
        dialog.setCancelable(false);
        TextView tvOk = (TextView) dialogView.findViewById(R.id.tv_ok);


        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

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

