package com.lexxdigital.easyfooduserapps.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.adapters.TimeSlotAdapter;
import com.lexxdigital.easyfooduserapps.dialogs.RecyclerItemListener;
import com.lexxdigital.easyfooduserapps.dialogs.TimeSlotDialogFragment;

import java.util.List;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TommorowFragment extends Fragment implements RecyclerItemListener {
    static TommorowFragment tommorowFragment;
    Context mContext;
    Activity mActivity;
    List<String> tomorrowTimeSlot;
    RecyclerView rvTomorrowTime;

    public static TommorowFragment newInstance(Activity activity, Context context, List<String> tomorrowTimeSlot) {
        TommorowFragment fragment = new TommorowFragment();
        fragment.mActivity = activity;
        fragment.mContext = context;
        fragment.tomorrowTimeSlot = tomorrowTimeSlot;
        return fragment;
    }


    public TommorowFragment() {

    }

    public static TommorowFragment getTommorowFragment() {
        return tommorowFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tommorowFragment = this;


    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_tommorow, container, false);
        rvTomorrowTime = (RecyclerView) view.findViewById(R.id.rv_tomorrow_time);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter();
        initMenu();
    }


    private void initMenu() {


    }

    private void setAdapter() {
        TimeSlotAdapter timeSlotAdapter = new TimeSlotAdapter(getActivity(), this, tomorrowTimeSlot);
        rvTomorrowTime.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTomorrowTime.setAdapter(timeSlotAdapter);
    }


    @Override
    public void onItemClick(int position) {
        TimeSlotDialogFragment.getInstance().setTimeSlot(tomorrowTimeSlot.get(position), "1");
    }
}