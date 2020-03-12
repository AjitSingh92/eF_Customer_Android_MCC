package com.lexxdigital.easyfooduserapps.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
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
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.dashboard.DashboardActivity;
import com.lexxdigital.easyfooduserapps.login.api.ForgotRequestInterface;
import com.lexxdigital.easyfooduserapps.login.api.LoginRequestInterface;
import com.lexxdigital.easyfooduserapps.login.model.forgot_request.ForgotRequest;
import com.lexxdigital.easyfooduserapps.login.model.forgot_response.ForgotResponse;
import com.lexxdigital.easyfooduserapps.login.model.request.LoginRequest;
import com.lexxdigital.easyfooduserapps.login.model.response.LoginResponse;
import com.lexxdigital.easyfooduserapps.search_post_code.SearchPostCodeActivity;
import com.lexxdigital.easyfooduserapps.signup.SignupActivity;
import com.lexxdigital.easyfooduserapps.utility.ApiClient;
import com.lexxdigital.easyfooduserapps.utility.Constants;
import com.lexxdigital.easyfooduserapps.utility.GlobalValues;
import com.lexxdigital.easyfooduserapps.utility.SharedPreferencesClass;
import com.newrelic.agent.android.NewRelic;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
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
    int count = 0;
    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        NewRelic.withApplicationToken(
                "eu01xxae9ccb44aafd9f746b5862b2dcb19769290d"
        ).start(this.getApplicationContext());
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
        findViewById(R.id.tv_headline).setOnClickListener(this);
        if (sharedPreferencesClass.getString(sharedPreferencesClass.FB_TOKEN_ID) == null) {
            sharedPreferencesClass.setString(sharedPreferencesClass.FB_TOKEN_ID, FirebaseInstanceId.getInstance().getToken());

        }
        callFacebookLogin();
        googlePlusSignin();


    }

    public void callFacebookLogin() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        getUserDetails(loginResult);
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException exception) {
                    }
                });


    }

    protected synchronized void googlePlusSignin() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PROFILE))
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .requestProfile()
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Plus.API)
                .build();

    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        signIn();
                    }
                });
    }

    private void handleSignInResult(GoogleSignInResult result) {
        dialog.show();

        if (result.isSuccess()) {
            try {

                GoogleSignInAccount acct = result.getSignInAccount();
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
                    if (person != null && person.getImage().getUrl() != null) {
                        profilePic = person.getImage().getUrl();
                    }
                    callAPI(email, "        ", Constants.LOGIN_WITH_GPLUS, personName);
                } else {
                    Toast.makeText(this, "Some error occurred. Please try again.", Toast.LENGTH_LONG).show();
                    dialog.hide();

                }

            } catch (Exception e) {
                dialog.hide();
                Log.e("Exception", e.toString());

            }

        } else {
            dialog.hide();
            Toast.makeText(LoginActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

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

            editEmail.requestFocus();
        } else if (!Constants.isValidEmail(editEmail.getText().toString())) {

            showDialog("Please enter valid email address.");
            editEmail.requestFocus();
        } else if (TextUtils.isEmpty(editPassword.getText().toString().trim())) {
            showDialog("Please enter password.");

            editPassword.requestFocus();
        } else if (editPassword.getText().toString().trim().length() < 6 || editPassword.getText().toString().trim().length() > 20) {
            showDialog("Password must contain 6 to 20 characters.");
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

            editEmail.requestFocus();
        } else if (!Constants.isValidEmail(editEmail.getText().toString())) {

            showDialog("Please enter valid email address.");
            editEmail.requestFocus();
        } else {
            if (ApiClient.isConnected(getApplicationContext())) {
                dialog.show();
                callAPI(email, "       ", Constants.LOGIN_WITH_FB, userName);
            } else {
                showDialog("Please check internet connection.");
            }
        }

    }

    private void validationForgot(String email, AlertDialog fDialog) {

        if (TextUtils.isEmpty(email.trim())) {
            showDialog("Please enter email address.");
            editEmail.requestFocus();
        } else if (!Constants.isValidEmail(email)) {
            showDialog("Please enter valid email address.");
            editEmail.requestFocus();
        } else {
            if (ApiClient.isConnected(getApplicationContext())) {

                callAPIForgotPassword(email, fDialog);
            } else {
                showDialog("Please check internet connection.");

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

                validationForgot(email.getText().toString(), forgotDialog);
            }
        });
        mDialogView.findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        forgotDialog.getWindow().setAttributes(lp);
        forgotDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final EditText email = (EditText) mDialogView.findViewById(R.id.email);
        mDialogView.findViewById(R.id.submit_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validationEmail(email.getText().toString(), forgotDialog);
            }
        });
        mDialogView.findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        Call<LoginResponse> call3 = apiInterface.mLogin(request);
        call3.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                try {
                    val.setLoginResponse(response.body());
                    Log.e("Success ><<<<<<<", ">>>>> Success" + response.code() + "//" + response.body().getSuccess() + "//" + response.body().getMessage());
                    if (response.code() == 200 && response.body().getSuccess()) {
                        dialog.hide();
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

                    } else {
                        dialog.hide();
                        showDialog(response.body().getMessage());
                    }

                } catch (Exception e) {
                    dialog.hide();
                    showDialog(response.message());

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                dialog.hide();
                showDialog("Please try again.");

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
                    if (response.code() == 200 && response.body().getSuccess()) {
                        showDialog(response.body().getMessage());
                        forgotDialog.dismiss();
                        dialog2.hide();
                    } else if (response.code() == 200 && !response.body().getSuccess()) {
                        dialog2.hide();
                        showDialog(response.body().getErrors().getEmail().get(0).toString());
                    } else {
                        dialog2.hide();
                        showDialog(response.body().getMessage());
                    }
                } catch (Exception e) {
                    dialog2.hide();
                    showDialog("Server error!");

                }
            }

            @Override
            public void onFailure(Call<ForgotResponse> call, Throwable t) {
                dialog.hide();
                showDialog("Please try again.");
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
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dialog.dismiss();
        AppEventsLogger.deactivateApp(this);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_headline:
                if (count == 7) {
                    editEmail.setText("maniacpraveen@gmail.com");
                    editPassword.setText("Praveen@123");
                } else {
                    count++;
                }
                break;
        }
    }
}
