package com.lexxdigital.easyfooduserapps.fragments;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.adapters.ExpandableAdapterRecycleview;
import com.lexxdigital.easyfooduserapps.adapters.RecyclerLayoutManager;
import com.lexxdigital.easyfooduserapps.adapters.TimeSlotAdapter;
import com.lexxdigital.easyfooduserapps.adapters.menu_adapter.ItemClickListener;
import com.lexxdigital.easyfooduserapps.adapters.menu_adapter.RestaurantMenuListAdapter;
import com.lexxdigital.easyfooduserapps.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapps.dialogs.RecyclerItemListener;
import com.lexxdigital.easyfooduserapps.dialogs.TimeSlotDialogFragment;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.menu_category.FinalMenuCartDetails;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.response.RestaurantDetailsResponse;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.Menu;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.RestaurantMenuList;
import com.lexxdigital.easyfooduserapps.viewmodel.MenuProductViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayFragment extends Fragment implements RecyclerItemListener {

    static TodayFragment todayFragment;
    Context mContext;
    Activity mActivity;
    List<String> timeSlot;
    RecyclerView rvTodayTime;


    public static TodayFragment newInstance(Activity activity, Context context, List<String> timeSlot) {
        TodayFragment fragment = new TodayFragment();
        fragment.mActivity = activity;
        fragment.mContext = context;
        fragment.timeSlot = timeSlot;
        return fragment;
    }


    public TodayFragment() {

    }

    public static TodayFragment getTodayFragment() {
        return todayFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        todayFragment = this;


    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_today, container, false);
        rvTodayTime = (RecyclerView) view.findViewById(R.id.rv_today_time);
        setAdapter();
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMenu();
    }


    private void initMenu() {


    }

    private void setAdapter() {
        TimeSlotAdapter timeSlotAdapter = new TimeSlotAdapter(getActivity(), this, timeSlot);
        rvTodayTime.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTodayTime.setAdapter(timeSlotAdapter);
    }

    @Override
    public void onItemClick(int position) {
        TimeSlotDialogFragment.getInstance().setTimeSlot(timeSlot.get(position));
    }
}