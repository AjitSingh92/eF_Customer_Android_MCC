package com.easyfoodcustomer.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easyfoodcustomer.R;
import com.easyfoodcustomer.adapters.TimeSlotAdapter;
import com.easyfoodcustomer.dialogs.RecyclerItemListener;
import com.easyfoodcustomer.dialogs.TimeSlotDialogFragment;

import java.util.List;

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
        TimeSlotDialogFragment.getInstance().setTimeSlot(timeSlot.get(position), "0");
    }
}