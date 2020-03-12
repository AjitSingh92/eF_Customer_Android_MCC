package com.lexxdigital.easyfooduserapps.fragments;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.google.firebase.analytics.FirebaseAnalytics;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.utility.GlobalValues;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
    FirebaseAnalytics mFirebaseAnalytics;

    @SuppressLint("ValidFragment")
    public AddCardFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_card, container, false);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
        val = (GlobalValues) mContext;
        dialog = new Dialog(mContext);
        dialog.setTitle("");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        unbinder = ButterKnife.bind(this, view);
        return view;
    }



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
