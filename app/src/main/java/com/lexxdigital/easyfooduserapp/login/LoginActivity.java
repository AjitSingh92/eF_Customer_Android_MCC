package com.lexxdigital.easyfooduserapp.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.dashboard.DashboardActivity;
import com.lexxdigital.easyfooduserapp.login.api.ForgotRequestInterface;
import com.lexxdigital.easyfooduserapp.login.api.LoginRequestInterface;
import com.lexxdigital.easyfooduserapp.login.model.forgot_request.ForgotRequest;
import com.lexxdigital.easyfooduserapp.login.model.forgot_response.ForgotResponse;
import com.lexxdigital.easyfooduserapp.login.model.request.LoginRequest;
import com.lexxdigital.easyfooduserapp.login.model.response.LoginResponse;
import com.lexxdigital.easyfooduserapp.search_post_code.SearchPostCodeActivity;
import com.lexxdigital.easyfooduserapp.signup.SignupActivity;
import com.lexxdigital.easyfooduserapp.utility.ApiClient;
import com.lexxdigital.easyfooduserapp.utility.Constants;
import com.lexxdigital.easyfooduserapp.utility.GlobalValues;
import com.lexxdigital.easyfooduserapp.utility.SharedPreferencesClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    @BindView(R.id.edit_email)
    EditText editEmail;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.btn_signin)
    TextView btnSignin;
    @BindView(R.id.btn_forgot_one)
    TextView btnForgotOne;
    @BindView(R.id.btn_forgot_two)
    TextView btnForgotTwo;
    @BindView(R.id.btn_facebook)
    LinearLayout btnFacebook;
    @BindView(R.id.btn_google_plus)
    LinearLayout btnGooglePlus;
    @BindView(R.id.btn_create_account_one)
    TextView btnCreateAccountOne;
    @BindView(R.id.btn_create_account_two)
    TextView btnCreateAccountTwo;
    SharedPreferencesClass sharedPreferencesClass;
    private boolean doubleBackToExitPressedOnce = false;
    //google -- 708648033257-ac4vu9acq04bc865021qbriq9817rvbj.apps.googleusercontent.com // RH9Qfz9RC0KZiOPRpry7fMGK
    private Dialog dialog;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private GlobalValues val;
    private AlertDialog forgotDialog;
    private String googleID = "", facebookId = "";
    private CallbackManager callbackManager;
    private String profilePic = "", firstName = "", lastName = "";
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 101;
    String userName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ButterKnife.bind(this);

        Constants.setStatusBarGradiant(LoginActivity.this);
        val = (GlobalValues) getApplicationContext();
        dialog = new Dialog(LoginActivity.this);
        dialog.setTitle("");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sharedPreferencesClass = new SharedPreferencesClass(getApplicationContext());

       editEmail.setText("maniacpraveen@gmail.com");
        editPassword.setText("Praveen@123");

        if (sharedPreferencesClass.getString(sharedPreferencesClass.FB_TOKEN_ID) == null) {
            sharedPreferencesClass.setString(sharedPreferencesClass.FB_TOKEN_ID, FirebaseInstanceId.getInstance().getToken());

        }
        callFacebookLogin();
        googlePlusSignin();


        // Add code to print out the key hash
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.lexxdigital.easyfooduserapp",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }


    }

    public void callFacebookLogin() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");
                        //    Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_LONG).show();
                        getUserDetails(loginResult);
                    }

                    @Override
                    public void onCancel() {
                        //  Toast.makeText(LoginActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        //  Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


//        btnFacebook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "user_friends"));
//            }
//        });

    }

    protected synchronized void googlePlusSignin() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PROFILE))
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .requestProfile()
                .requestEmail()
                .build();

//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Plus.API)
                .build();

    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//        sharedPreferencesClass.setloginpref("1");
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // updateUI(false);
                    }
                });
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Log.e("Status", "display : " + status);
                        signIn();
                    }
                });
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.e("DD", "handleSignInResult:" + result.isSuccess());


        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            try {

                GoogleSignInAccount acct = result.getSignInAccount();

                Log.e("DD", "display name: " + acct.getDisplayName());

                String personName = acct.getDisplayName();
                String id = acct.getId();
                String email = acct.getEmail();
                googleID = acct.getId();

                String[] arrName = personName.split(" ");

                if (arrName.length == 3) {
                    firstName = arrName[0] + " " + arrName[1];
                    lastName = arrName[2];
                }
                if (arrName.length == 2) {
                    firstName = arrName[0];
                    lastName = arrName[1];
                }

                if (mGoogleApiClient.hasConnectedApi(Plus.API)) {
                    Person person = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                    profilePic = person.getImage().getUrl();
                    dialog.show();
                    callAPI(email, "        ", Constants.LOGIN_WITH_GPLUS, personName);
                } else {
                    Toast.makeText(this, "Some error occurred. Please try again.", Toast.LENGTH_SHORT).show();
                }


//
//                Log.e("DD", "Name: " + personName + ", email: " + email
//                        + ", Image: , Name : " + profilePic);
                // signOut();
            } catch (Exception e) {

            }

        } else {
            // Signed out, show unauthenticated UI.
            //updateUI(false);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    //{"id":"101360704342719","name":"Manoj Kumar","email":"manoj.kumar@lexxdigital.com","picture":{"data":{"height":120,"is_silhouette":false,"url":"https:\/\/platform-lookaside.fbsbx.com\/platform\/profilepic\/?asid=101360704342719&height=120&width=120&ext=1552718540&hash=AeR0nvEHZ2sTpd7Q","width":120}},"birthday":"05\/04\/1990"}
    protected void getUserDetails(LoginResult loginResult) {
        GraphRequest data_request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject json_object,
                            GraphResponse response) {
                        Log.e("userData", json_object.toString());
                        try {
                            String id = "", email = "";
                            if (json_object.has("id")) {
                                id = json_object.getString("id");
                                facebookId = id;
                            }

                            if (json_object.has("first_name")) {
                                firstName = json_object.getString("first_name");
                            }

                            if (json_object.has("last_name")) {
                                lastName = json_object.getString("last_name");
                            }

                            if (json_object.has("name")) {
                                userName = json_object.getString("name");
                                Log.d("----->", userName);
                            }
                            if (json_object.has("email")) {
                                email = json_object.getString("email");
                                Log.d("----->", email);
                            }
                            if (json_object.has("picture")) {
                                profilePic = json_object.getJSONObject("picture").getJSONObject("data").getString("url").toString();
                                Log.e("PIC >>", "//" + profilePic);
                            }

                            if (!id.equalsIgnoreCase("")) {

                                if (!email.equalsIgnoreCase("")) {
                                    dialog.show();
                                    callAPI(email, "", Constants.LOGIN_WITH_FB, userName);

                                } else {
                                    alertDialogEmail();
                                }

                            }

//                            String dob = json_object.getString("birthday");

                            Log.e("userData", id + "//" + userName + "//" + email + "////");


                            //   callAPI(email,"       ","fb");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                });
        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,first_name,last_name,email,picture.width(120).height(120),link,birthday,gender");
        data_request.setParameters(permission_param);
        data_request.executeAsync();

    }

    private void validation() {

        if (TextUtils.isEmpty(editEmail.getText().toString().trim())) {
            showDialog("Please enter email address.");
            //  Toast.makeText(LoginActivity.this, "Please enter email id.", Toast.LENGTH_SHORT).show();
            editEmail.requestFocus();
        } else if (!Constants.isValidEmail(editEmail.getText().toString())) {
            //  Toast.makeText(LoginActivity.this, "Please enter valid email id.", Toast.LENGTH_SHORT).show();
            showDialog("Please enter valid email address.");
            editEmail.requestFocus();
        } else if (TextUtils.isEmpty(editPassword.getText().toString().trim())) {
            showDialog("Please enter password.");
            // Toast.makeText(LoginActivity.this, "Please enter password.", Toast.LENGTH_SHORT).show();
            editPassword.requestFocus();
        } else if (editPassword.getText().toString().trim().length() < 6 || editPassword.getText().toString().trim().length() > 20) {
            showDialog("Password must contain 6 to 20 characters.");
            // Toast.makeText(LoginActivity.this, "Please enter password.", Toast.LENGTH_SHORT).show();
            editPassword.requestFocus();
        } else {
            dialog.show();
            if (Constants.isInternetConnectionAvailable(3000)) {

                callAPI(editEmail.getText().toString(), editPassword.getText().toString(), Constants.LOGIN_WITH_OTHER, "");
            } else {
                showDialog("Please check internet connection.");
                dialog.dismiss();
            }
        }

    }

    private void validationEmail(String email, AlertDialog fDialog) {

        if (TextUtils.isEmpty(email.trim())) {
            showDialog("Please enter email address.");
            //  Toast.makeText(LoginActivity.this, "Please enter email id.", Toast.LENGTH_SHORT).show();
            editEmail.requestFocus();
        } else if (!Constants.isValidEmail(editEmail.getText().toString())) {
            //  Toast.makeText(LoginActivity.this, "Please enter valid email id.", Toast.LENGTH_SHORT).show();
            showDialog("Please enter valid email address.");
            editEmail.requestFocus();
        } else {
            if (ApiClient.isConnected(getApplicationContext())) {
                dialog.show();
                callAPI(email, "       ", Constants.LOGIN_WITH_FB, userName);
            } else {
                showDialog("Please check internet connection.");
                //      Toast.makeText(LoginActivity.this, "Please check internet connection.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void validationForgot(String email, AlertDialog fDialog) {

        if (TextUtils.isEmpty(email.trim())) {
            showDialog("Please enter email address.");
            //  Toast.makeText(LoginActivity.this, "Please enter email id.", Toast.LENGTH_SHORT).show();
            editEmail.requestFocus();
        } else if (!Constants.isValidEmail(email)) {
            showDialog("Please enter valid email address.");
            editEmail.requestFocus();
        } else {
            if (ApiClient.isConnected(getApplicationContext())) {
                // dialog.show();
                callAPIForgotPassword(email, fDialog);
            } else {
                showDialog("Please check internet connection.");
                //      Toast.makeText(LoginActivity.this, "Please check internet connection.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @OnClick({R.id.btn_signin, R.id.btn_forgot_one, R.id.btn_forgot_two, R.id.btn_facebook, R.id.btn_google_plus, R.id.btn_create_account_one, R.id.btn_create_account_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_signin:
                validation();
                break;
            case R.id.btn_forgot_one:
                alertDialogforgotPassword();
                break;
            case R.id.btn_forgot_two:
                alertDialogforgotPassword();
                break;
            case R.id.btn_facebook:
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email"));
                break;
            case R.id.btn_google_plus:
                // signIn();
                revokeAccess();
                break;
            case R.id.btn_create_account_one:
                Constants.switchActivity(LoginActivity.this, SignupActivity.class);
                break;
            case R.id.btn_create_account_two:
                Constants.switchActivity(LoginActivity.this, SignupActivity.class);
                break;
        }
    }

    public void alertDialogforgotPassword() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.forgat_password_dilog, null);
        forgotDialog = new AlertDialog.Builder(this).create();
        forgotDialog.setView(mDialogView);
        forgotDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final EditText email = (EditText) mDialogView.findViewById(R.id.email);
        mDialogView.findViewById(R.id.submit_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                //   forgotDialog.dismiss();
                //    dialog2.show();
                validationForgot(email.getText().toString(), forgotDialog);
            }
        });
        mDialogView.findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                forgotDialog.dismiss();
            }
        });

        forgotDialog.show();
    }

    public void alertDialogEmail() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.popup_email, null);
        forgotDialog = new AlertDialog.Builder(this).create();
        forgotDialog.setView(mDialogView);
        forgotDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final EditText email = (EditText) mDialogView.findViewById(R.id.email);
        mDialogView.findViewById(R.id.submit_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                //   forgotDialog.dismiss();
                //    dialog2.show();
                validationEmail(email.getText().toString(), forgotDialog);
            }
        });
        mDialogView.findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                forgotDialog.dismiss();
            }
        });

        forgotDialog.show();
    }

    public void callAPI(String email, String password, final String other, String name) {
        LoginRequestInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(LoginRequestInterface.class);

        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        String[] array = name.split("");

        LoginRequest request = new LoginRequest();
        request.setEmail(email);
        request.setPassword(password);
        request.setDeviceId(androidId);
        request.setDeviceType("Android");
        request.setFbId(facebookId);
        request.setGoogleId(googleID);
        request.setLoginVia(other);
        request.setProfilePic(profilePic);
        request.setName(name);
        request.setLastName(lastName);
        request.setFirstName(firstName);
        request.setFb_device_id(sharedPreferencesClass.getString(sharedPreferencesClass.FB_TOKEN_ID));
        Log.e("Data 1234567//", "" + email + "//" + password + "//" + androidId + "//Android//" + facebookId + "//" + googleID + "//" + other);

//        Gson gson2 = new Gson();
//        String json2 = gson2.toJson(request);
        Log.e("LOGIN RES >>", "//" + profilePic);

        Call<LoginResponse> call3 = apiInterface.mLogin(request);
        call3.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                try {
                    val.setLoginResponse(response.body());
                    Log.e("Success ><<<<<<<", ">>>>> Success" + response.code() + "//" + response.body().getSuccess() + "//" + response.body().getMessage());
                    if (response.code() == 200 && response.body().getSuccess()) {
                        dialog.hide();
//                        forgotDialog.dismiss();
//                        forgotDialog.cancel();
//                        forgotDialog.hide();
                        val.setProfileImage(response.body().getData().getProfilePic());
                        val.setFirstName(response.body().getData().getFirstName());
                        val.setLastName(response.body().getData().getLastName());
                        val.setUserName(response.body().getData().getName());
                        val.setMobileNo(response.body().getData().getPhoneNumber());

                        sharedPreferencesClass.setloginpref("1"); // isLogin
                        sharedPreferencesClass.setObject(sharedPreferencesClass.LoginResponseKey, new Gson().toJson(response.body()));
                        sharedPreferencesClass.setloginpref(response.body().getData().getUserId());
                        sharedPreferencesClass.setString(sharedPreferencesClass.USER_ID, response.body().getData().getUserId());
                        sharedPreferencesClass.setString(sharedPreferencesClass.USER_NAME, response.body().getData().getName());
                        sharedPreferencesClass.setString(sharedPreferencesClass.USER_PROFILE_IMAGE, response.body().getData().getProfilePic());
                        sharedPreferencesClass.setString(sharedPreferencesClass.LOGIN_VIA, other);
                        sharedPreferencesClass.setInt(sharedPreferencesClass.NUMBER_OF_ORDERS, response.body().getData().getPrevious_orders());


                        //sharedPreferencesClass.setLoginResponseDataKey(response.body().getData());
                        //sharedPreferencesClass.setLoginResponseKey(response.body());
                        // Log.e("login Activity", "onResponse: "+ sharedPreferencesClass.getObject(sharedPreferencesClass.LoginResponseKey, Data.class));
                        if (!response.body().getData().getPost_code().equalsIgnoreCase("") || response.body().getData().getPost_code() != null) {
                            sharedPreferencesClass.setPostalCode(response.body().getData().getPost_code());
                            val.setPostCode(response.body().getData().getPost_code());
                            Log.e("loginActivity", ">>>>>>>>>>: " + response.body().getData().getPost_code());
                        }
                        String postcode = sharedPreferencesClass.getPostalCode();
                        Log.e("loginActivity", "onResponse: poscodeeeee: " + postcode);
                        if (!postcode.equalsIgnoreCase("") && postcode != null) {
                            Constants.switchActivity(LoginActivity.this, DashboardActivity.class);
                            finish();
                        } else {
                            Constants.switchActivity(LoginActivity.this, SearchPostCodeActivity.class);
                            finish();
                        }
                    } else if (response.code() == 200 && !response.body().getSuccess()) {
                        dialog.hide();
                        showDialog(response.body().getMessage());
                        //    Toast.makeText(LoginActivity.this, response.body().getResponseMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.hide();
                        showDialog(response.body().getMessage());
                        //         Toast.makeText(LoginActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    dialog.hide();
                    Log.e("Error1 <>>>", ">>>>>" + e.getMessage() + ", " + response.message());
                    showDialog(response.message());
                    //    Toast.makeText(LoginActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("Error <>>>", ">>>>>" + t.getMessage());
                dialog.hide();
                showDialog("Please try again.");
                //    Toast.makeText(LoginActivity.this, "Please try again 2."+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void callAPIForgotPassword(String email, final AlertDialog fDialog) {
        ForgotRequestInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(ForgotRequestInterface.class);
        ForgotRequest request = new ForgotRequest();
        request.setEmail(email);
        final Dialog dialog2 = new Dialog(LoginActivity.this);
        dialog2.setTitle("");
        dialog2.setCancelable(false);
        dialog2.setContentView(R.layout.progress_dialog);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.show();
        Call<ForgotResponse> call3 = apiInterface.mLogin(request);
        call3.enqueue(new Callback<ForgotResponse>() {
            @Override
            public void onResponse(Call<ForgotResponse> call, Response<ForgotResponse> response) {
                try {
                    val.setForgotResponse(response.body());

                    //                  //Log.e("Success ><<<<<<<", ">>>>> Success" + response.code()+"//"+response.body().getResponseCode()+"//"+response.body().getResponseMsg());
                    if (response.code() == 200 && response.body().getSuccess()) {
                        //  dialog.hide();
                        showDialog(response.body().getMessage());
                        forgotDialog.dismiss();
                        dialog2.hide();
                        //  fDialog.dismiss();
//                        Constants.switchActivity(LoginActivity.this, DashboardActivity.class);
//                        finish();
                    } else if (response.code() == 200 && !response.body().getSuccess()) {
                        dialog2.hide();
                        showDialog(response.body().getErrors().getEmail().get(0).toString());
                        //    Toast.makeText(LoginActivity.this, response.body().getResponseMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        dialog2.hide();
                        showDialog(response.body().getMessage());
                        //         Toast.makeText(LoginActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    dialog2.hide();
                    //showDialog("Please try again.");
                    showDialog("Server error!");
                    //    Toast.makeText(LoginActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ForgotResponse> call, Throwable t) {
                //Log.e("Error <>>>",">>>>>"+t.getMessage());
                dialog.hide();
                showDialog("Please try again.");
                //    Toast.makeText(LoginActivity.this, "Please try again 2."+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showDialog(String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

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

    protected void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.

        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dialog.dismiss();
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("DD", "onConnectionFailed:" + connectionResult);
    }

    @Override
    public void onStart() {
        super.onStart();

//        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
//        if (opr.isDone()) {
//            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
//            // and the GoogleSignInResult will be available instantly.
//            Log.d("DD", "Got cached sign-in");
//            GoogleSignInResult result = opr.get();
//            handleSignInResult(result);
//        } else {
//            // If the user has not previously signed in on this device or the sign-in has expired,
//            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
//            // single sign-on will occur in this branch.
//            dialog.show();
//            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
//                @Override
//                public void onResult(GoogleSignInResult googleSignInResult) {
//                    dialog.hide();
//                    handleSignInResult(googleSignInResult);
//                }
//            });
//        }
    }


}
