package com.lexxdigital.easyfooduserapps.fragments;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.api.MyAccountInterface;
import com.lexxdigital.easyfooduserapps.change_password.ChangePasswordActivity;
import com.lexxdigital.easyfooduserapps.edit_my_account.EditMyAccountActivity;
import com.lexxdigital.easyfooduserapps.manage_address.ManageAddressActivity;
import com.lexxdigital.easyfooduserapps.model.my_account_request.MyAccountRequest;
import com.lexxdigital.easyfooduserapps.model.my_account_response.MyAccountResponse;
import com.lexxdigital.easyfooduserapps.utility.ApiClient;
import com.lexxdigital.easyfooduserapps.utility.Constants;
import com.lexxdigital.easyfooduserapps.utility.GlobalValues;
import com.lexxdigital.easyfooduserapps.utility.SharedPreferencesClass;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class MyAccountFragment extends Fragment {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.telephone)
    TextView telephone;
    @BindView(R.id.top_address)
    LinearLayout topAddress;
    @BindView(R.id.profileImg)
    CircleImageView profileImg;
    @BindView(R.id.fm)
    RelativeLayout fm;
    @BindView(R.id.btn_edit_profile)
    Button btnEditProfile;
    @BindView(R.id.wallet_balance)
    TextView walletBalance;
    @BindView(R.id.edit_manage_address)
    TextView editManageAddress;
    @BindView(R.id.edit_previous_payments)
    TextView editPreviousPayments;
    @BindView(R.id.btn_change_password)
    Button btn_change_password;
    Unbinder unbinder;
    private GlobalValues val;
    private Dialog dialog;
    private Context mContext;
    private TextView txtToolbarTitle;
    String nameStr, profileImageStr;
    SharedPreferencesClass sharePre;
    private boolean isFirstTime = true;
    LinearLayout lyContainer;
    SimpleDraweeView image;
    FirebaseAnalytics mFirebaseAnalytics;

    @SuppressLint("ValidFragment")
    public MyAccountFragment(Context mContext, TextView title) {
        this.mContext = mContext;
        this.txtToolbarTitle = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Fresco.initialize(mContext);
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
        unbinder = ButterKnife.bind(this, view);

        sharePre = new SharedPreferencesClass(mContext);
        lyContainer = (LinearLayout) view.findViewById(R.id.ll_container);
        image = view.findViewById(R.id.image);
        val = (GlobalValues) mContext;
        dialog = new Dialog(getActivity());
        dialog.setTitle("");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });

        lyContainer.setVisibility(View.GONE);
        try {
            if (sharePre.getString(sharePre.LOGIN_VIA).equalsIgnoreCase(Constants.LOGIN_WITH_OTHER)) {
                btn_change_password.setVisibility(View.VISIBLE);
            } else {
                btn_change_password.setVisibility(View.GONE);
            }
        } catch (NullPointerException e) {

        }


        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        dialog.dismiss();
    }

    @Override
    public void onStop() {
        super.onStop();
        dialog.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_edit_profile, R.id.edit_manage_address, R.id.edit_previous_payments, R.id.btn_change_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_edit_profile:
                Constants.switchActivity(getActivity(), EditMyAccountActivity.class);
                break;
            case R.id.edit_manage_address:
                Constants.switchActivity(getActivity(), ManageAddressActivity.class);
                break;
            case R.id.edit_previous_payments:
                break;
            case R.id.btn_change_password:
                Intent intent = new Intent(mContext, ChangePasswordActivity.class);
                intent.putExtra("customer_id", val.getLoginResponse().getData().getUserId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;
        }
    }

    public void getUserdDetail() {
        MyAccountInterface apiInterface = ApiClient.getClient(getContext()).create(MyAccountInterface.class);
        final MyAccountRequest request = new MyAccountRequest();
        request.setCustomerId(val.getLoginResponse().getData().getUserId());

        Call<MyAccountResponse> call3 = apiInterface.mGetProfile(request);
        call3.enqueue(new Callback<MyAccountResponse>() {
            @Override
            public void onResponse(Call<MyAccountResponse> call, Response<MyAccountResponse> response) {
                try {
                    if (response.body().getSuccess()) {
                        String strname = response.body().getData().getProfile().getFirstName() + " " + response.body().getData().getProfile().getLastName();
                        String strAddress = response.body().getData().getProfile().getCustomerAddress().getAddress1() + " " + response.body().getData().getProfile().getCustomerAddress().getAddress2();
                        String strMobile = response.body().getData().getProfile().getPhoneNumber();
                        if (strname != null && !strname.equals("")) {
                            name.setText(strname);
                            nameStr = strname;
                        } else {
                            name.setVisibility(View.GONE);
                        }
                        if (strAddress != null && !strAddress.trim().equals("")) {
                            address.setText(strAddress);
                            address.setVisibility(View.VISIBLE);
                        } else {
                            address.setVisibility(View.GONE);
                        }
                        if (strMobile != null && !strMobile.equals("")) {
                            telephone.setText(strMobile);
                        } else {
                            telephone.setVisibility(View.GONE);
                        }

                        profileImageStr = response.body().getData().getProfile().getProfilePic();
                        if (!profileImageStr.equalsIgnoreCase("http:\\/\\/35.177.163.219\\/easyfood_backend\\/public") && profileImageStr != null) {
                            Uri uri = Uri.parse(profileImageStr);

                            if (uri != null) {
                                image.setImageURI(uri);

                            }


                        }


                        sharePre.setString(sharePre.USER_PROFILE_IMAGE, profileImageStr);

                        val.setProfileImage(response.body().getData().getProfile().getProfilePic());
                        val.setFirstName(response.body().getData().getProfile().getFirstName());
                        val.setLastName(response.body().getData().getProfile().getLastName());
                        walletBalance.setText("£" + response.body().getData().getWalletBalance());
                        val.setMobileNo(response.body().getData().getProfile().getPhoneNumber());
                        val.setUserName(response.body().getData().getProfile().getFirstName() + " " + response.body().getData().getProfile().getLastName());
                        val.setDefaltAddress(response.body().getData().getProfile().getCustomerAddress().getAddress1() + " " + response.body().getData().getProfile().getCustomerAddress().getAddress2());
                        isFirstTime = false;
                        lyContainer.setVisibility(View.VISIBLE);
                        dialog.hide();
                    } else {

                    }
                } catch (Exception e) {
                    dialog.hide();
                    Log.e("Error", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<MyAccountResponse> call, Throwable t) {
                dialog.hide();
                Log.e("Error", t.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            if (sharePre.getString(sharePre.LOGIN_VIA).equalsIgnoreCase(Constants.LOGIN_WITH_OTHER)) {
                btn_change_password.setVisibility(View.VISIBLE);
            } else {
                btn_change_password.setVisibility(View.GONE);
            }
        } catch (NullPointerException e) {
            btn_change_password.setVisibility(View.GONE);
        }
        if (!isFirstTime) {
            Uri uri = Uri.parse(profileImageStr);
            image.setImageURI(uri);

            name.setText(val.getUserName());
            address.setText(val.getDefaltAddress());
            telephone.setText(val.getMobileNo());
        }

        dialog.show();
        getUserdDetail();

    }
}
