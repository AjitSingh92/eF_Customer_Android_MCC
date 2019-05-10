package com.lexxdigital.easyfooduserapp.fragments;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.api.AddCardInterface;
import com.lexxdigital.easyfooduserapp.utility.ApiClient;
import com.lexxdigital.easyfooduserapp.utility.GlobalValues;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class AddCardFragment extends Fragment {

    @BindView(R.id.cardNo)
    TextView cardNo;
    @BindView(R.id.holdernametv)
    TextView holdernametv;
    @BindView(R.id.exp_date)
    TextView expDate;
    @BindView(R.id.fm)
    FrameLayout fm;
    @BindView(R.id.nameId)
    TextView nameId;
    @BindView(R.id.name_of_card_et)
    EditText nameOfCardEt;

    @BindView(R.id.ccv_et)
    EditText ccvEt;
    @BindView(R.id.expire_date)
    EditText expireDate;
    @BindView(R.id.expire_year)
    EditText expireYear;
    @BindView(R.id.lll)
    LinearLayout lll;
    @BindView(R.id.billing_et)
    EditText billingEt;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.scroll)
    ScrollView scroll;

    Unbinder unbinder;
    @BindView(R.id.card_number)
    EditText cardNumber;
    @BindView(R.id.conformTv)
    Button conformTv;
    private GlobalValues val;
    private Dialog dialog;
    private Context mContext;


    @SuppressLint("ValidFragment")
    public AddCardFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_card, container, false);
        val = (GlobalValues) mContext;
        dialog = new Dialog(mContext);
        dialog.setTitle("");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        unbinder = ButterKnife.bind(this, view);
        return view;
    }

//    public void saveCardDetail() {
//        AddCardInterface apiInterface = ApiClient.getClient(getContext()).create(AddCardInterface.class);
//        AddCardRequest request = new AddCardRequest();
//        request.setUserId(val.getLoginResponse().getData().getUserId());
//        request.setCardNo(cardNumber.getText().toString());
//        request.setNameOnCard(nameOfCardEt.getText().toString());
//        request.setCardExpireMonth(expireDate.getText().toString());
//        request.setCardExpireYear(expireYear.getText().toString());
//        request.setBillingPostcode(billingEt.getText().toString());
//
//        Call<AddCardResponse> call3 = apiInterface.mLogin(request);
//        call3.enqueue(new Callback<AddCardResponse>() {
//            @Override
//            public void onResponse(Call<AddCardResponse> call, Response<AddCardResponse> response) {
//                try {
//                    dialog.hide();
//                    if (response.body().getSuccess()) {
//                        showDialog("Card added successfully.");
//                    } else {
//                        showDialog("Failed to add new card. Please try again.");
//                    }
//                } catch (Exception e) {
//                    dialog.hide();
//                    showDialog("Failed to add new card. Please try again.");
//                    Log.e("Error11 <>>>", ">>>>>" + e.getMessage());
//                    //    showDialog("Please try again.");
////                       Toast.makeText(LoginActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AddCardResponse> call, Throwable t) {
//                Log.e("Error12 <>>>", ">>>>>" + t.getMessage());
//                showDialog("Failed to add new card. Please try again.");
////                dialog.hide();
////                showDialog("Please try again.");
//                //    Toast.makeText(LoginActivity.this, "Please try again 2."+t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public void showDialog(String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
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

    @OnClick(R.id.conformTv)
    public void onViewClicked() {
        Toast.makeText(mContext, "clicked.", Toast.LENGTH_SHORT).show();

    }
}
