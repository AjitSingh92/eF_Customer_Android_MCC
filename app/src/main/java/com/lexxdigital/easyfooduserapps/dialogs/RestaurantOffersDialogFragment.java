package com.lexxdigital.easyfooduserapps.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.adapters.RecyclerLayoutManager;
import com.lexxdigital.easyfooduserapps.adapters.RestaurantOffersAdapter;
import com.lexxdigital.easyfooduserapps.model.restaurant_offers.RestaurantSpecialOffers;
import com.lexxdigital.easyfooduserapps.utility.Constants;

import java.util.List;

public class RestaurantOffersDialogFragment extends DialogFragment implements View.OnClickListener {
    Context context;
    RecyclerView recyclerViewList;
    RestaurantOffersAdapter restaurantOffersAdapter;
    List<RestaurantSpecialOffers> restaurantSpecialOffers;
    ImageView imClose;
    TextView btnOk;
    OnOffersSelectedListener onOffersSelectedListener;
    FirebaseAnalytics mFirebaseAnalytics;

    public interface OnOffersSelectedListener {
        void onOffersSelect(String time);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {

        super.onDismiss(dialog);
    }

    public RestaurantOffersDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    // TODO: Rename and change types and number of parameters
    public static RestaurantOffersDialogFragment newInstance(Context context, List<RestaurantSpecialOffers> restaurantSpecialOffers) {
        RestaurantOffersDialogFragment f = new RestaurantOffersDialogFragment();
        f.context = context;
        f.restaurantSpecialOffers = restaurantSpecialOffers;
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.restaurant_offers_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

    }

    private void initView(View view) {
        recyclerViewList = view.findViewById(R.id.list_offers);
        restaurantOffersAdapter = new RestaurantOffersAdapter(context);
        RecyclerLayoutManager layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
        recyclerViewList.setLayoutManager(layoutManager);
        recyclerViewList.setAdapter(restaurantOffersAdapter);
        restaurantOffersAdapter.addItem(restaurantSpecialOffers);

        imClose = view.findViewById(R.id.cross_tv);
        imClose.setOnClickListener(this);

        btnOk = view.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cross_tv:
                dismiss();
                break;
            case R.id.btn_ok:

                break;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();


    }


    @Override
    public void onResume() {
        super.onResume();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(getDialog().getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.90f);
        int dialogWindowHeight = (int) (displayHeight * 0.60f);
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;

        getDialog().getWindow().setAttributes(layoutParams);

        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
    }


    public void dialogNoInternetConnection(String message) {
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View mDialogView = factory.inflate(R.layout.addnote_success_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setView(mDialogView);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final Animation animShake = AnimationUtils.loadAnimation(context, R.anim.shake);

        TextView tvMessage = mDialogView.findViewById(R.id.message);
        tvMessage.setText(message);
        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constants.isInternetConnectionAvailable(300)) {
                    alertDialog.dismiss();
                    dismiss();
                } else mDialogView.findViewById(R.id.okTv).startAnimation(animShake);

            }
        });

        alertDialog.show();
    }

}