package com.lexxdigital.easyfooduserapps.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.adapters.AddressDialogAdapter;
import com.lexxdigital.easyfooduserapps.adapters.RecyclerLayoutManager;
import com.lexxdigital.easyfooduserapps.api.VoucherApplyInterface;
import com.lexxdigital.easyfooduserapps.fragments.InfoFragment;
import com.lexxdigital.easyfooduserapps.fragments.MenuFragment;
import com.lexxdigital.easyfooduserapps.fragments.ReviwesFragment;
import com.lexxdigital.easyfooduserapps.fragments.TodayFragment;
import com.lexxdigital.easyfooduserapps.fragments.TommorowFragment;
import com.lexxdigital.easyfooduserapps.model.AddressList;
import com.lexxdigital.easyfooduserapps.model.restuarant_time_slot.TimeSlotRequest;
import com.lexxdigital.easyfooduserapps.model.restuarant_time_slot.TimeSlotResponse;
import com.lexxdigital.easyfooduserapps.restaurant_details.RestaurantDetailsActivity;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.new_restaurant_response.NewRestaurantsDetailsResponse;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.Menu;
import com.lexxdigital.easyfooduserapps.utility.ApiClient;
import com.lexxdigital.easyfooduserapps.utility.Constants;
import com.lexxdigital.easyfooduserapps.utility.HeightWrappingViewPager;
import com.lexxdigital.easyfooduserapps.utility.SharedPreferencesClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class TimeSlotDialogFragment extends DialogFragment implements View.OnClickListener {
    Context context;
    private List<AddressList> addressList = new ArrayList<AddressList>();
    AddressDialogAdapter addressDialogAdapter;
    RecyclerView recyclerViewList;
    RecyclerLayoutManager recyclerLayoutManager;
    ProgressBar progressBar;
    TabLayout tabs;
    ImageView ivLcose;
    HeightWrappingViewPager pager;
    String dayOfTheWeek;
    String nextDay;
    private List<TimeSlotResponse.Data> timeSlotList = new ArrayList<>();
    private List<String> todayList = new ArrayList<>();
    private List<String> tommorowList = new ArrayList<>();


    private static String[] toDayList;
    private static String[] tomorrowList;
    private static List<String> toDayDataList;
    private static List<String> tomorrowDataList;
    private static List<String> dateTimeDataList;
    String toDay, tomorrow;
    Spinner todaySpinner, tomorrowSpinner;
    SharedPreferencesClass sharePre;
    OnDeliveryTimeSelectedListener onDeliveryTimeSelectedListener;
    FirebaseAnalytics mFirebaseAnalytics;
    private String finalSlot = "";
    private String isTommorow = "0";
    private String dateString = "";
    private static TimeSlotDialogFragment instance = null;

    private String todayDate, tomorrowDate, nowDate;

    public interface OnDeliveryTimeSelectedListener {
        void onDeliveryTimeSelect(String time, String isTomorrow, String timeDate);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {

        super.onDismiss(dialog);
    }

    public TimeSlotDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    // TODO: Rename and change types and number of parameters
    public static TimeSlotDialogFragment newInstance(Context context, OnDeliveryTimeSelectedListener onDeliveryTimeSelectedListener) {
        TimeSlotDialogFragment f = new TimeSlotDialogFragment();
        f.context = context;
        f.onDeliveryTimeSelectedListener = onDeliveryTimeSelectedListener;
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
        instance = this;
        return inflater.inflate(R.layout.time_slot_popup, container, false);

    }

    public static TimeSlotDialogFragment getInstance() {
        return instance;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabs = view.findViewById(R.id.tabs);
        pager = view.findViewById(R.id.pager);
        progressBar = view.findViewById(R.id.progressBar);
        sharePre = new SharedPreferencesClass(context);
        view.findViewById(R.id.iv_close).setOnClickListener(this);
        view.findViewById(R.id.tv_confirm).setOnClickListener(this);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE");
        Calendar calendar = Calendar.getInstance();
        Date d = new Date();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date nd = calendar.getTime();
        dayOfTheWeek = sdf.format(d);
        nextDay = sdf.format(nd);
        tabs.setupWithViewPager(pager);
        pager.getCurrentItem();
        if (Constants.isInternetConnectionAvailable(300)) {
            getTimeSlot();
        } else {

            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
            dismiss();

        }


        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e("IREMS COUNT", "" + tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }


    public void setTimeSlot(String timeSlotttt, String isTommoroww) {
        finalSlot = timeSlotttt;
        isTommorow = isTommoroww;

        // 2019-09-23 10:15:20


        if (isTommoroww.equalsIgnoreCase("0") && timeSlotttt.equalsIgnoreCase("Now")) {
            dateString = todayDate + " " + nowDate;
        } else if (isTommoroww.equalsIgnoreCase("1") && timeSlotttt.equalsIgnoreCase("Now")) {
            dateString = tomorrowDate + " " + nowDate;
        } else if (isTommoroww.equalsIgnoreCase("0") && !timeSlotttt.equalsIgnoreCase("Now")) {
            int n = timeSlotttt.indexOf("-");
            dateString = todayDate + " " + timeSlotttt.substring(0, n).trim();
        } else if (isTommoroww.equalsIgnoreCase("1") && !timeSlotttt.equalsIgnoreCase("Now")) {
            int n = timeSlotttt.indexOf("-");
            dateString = tomorrowDate + " " + timeSlotttt.substring(0, n).trim();
        }


    }

    private void setupViewPager(ViewPager viewPager, String currentDay, String nextDay) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(TodayFragment.newInstance(getActivity(), getApplicationContext(), todayList), currentDay + "(ASAP)");
        adapter.addFrag(TommorowFragment.newInstance(getActivity(), getApplicationContext(), tommorowList), nextDay + "(Tomorrow)");
        pager.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            // Toast.makeText(RestaurantDetailsActivity.this, "POS " + position, Toast.LENGTH_SHORT).show();
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            //    Toast.makeText(RestaurantDetailsActivity.this, "Len "+mFragmentList.size(), Toast.LENGTH_SHORT).show();
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }


    private void initView() {

        if (toDayList == null) {
            toDayList = new String[1];
            toDayList[0] = "Select Delivery Time";
        }
        if (tomorrowList == null) {
            tomorrowList = new String[1];
            tomorrowList[0] = "Tomorrow";
        }

        ArrayAdapter<String> adapterToday = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, toDayList);
        ArrayAdapter<String> adapterTomorrow = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, tomorrowList);

        adapterToday.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        todaySpinner.setAdapter(adapterToday);


        adapterTomorrow.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tomorrowSpinner.setAdapter(adapterTomorrow);

        todaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toDay = (String) parent.getItemAtPosition(position);
                Log.e("item", toDay);
                if (!toDay.equalsIgnoreCase("Select Delivery Time")) {
                    tomorrowSpinner.setSelection(0);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        tomorrowSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tomorrow = (String) parent.getItemAtPosition(position);
                Log.e("item", tomorrow);
                if (!tomorrow.equalsIgnoreCase("Tomorrow")) {
                    todaySpinner.setSelection(0);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.tv_confirm:

                if (!finalSlot.isEmpty() && finalSlot != "") {
                    onDeliveryTimeSelectedListener.onDeliveryTimeSelect(finalSlot, isTommorow, dateString);
                }

/*

                if (toDay != null && !toDay.equalsIgnoreCase("Select Delivery Time")) {
                    if (todaySpinner != null && todaySpinner.getSelectedItemPosition() != 0) {
                        if (dateTimeDataList != null) {
                            if (onDeliveryTimeSelectedListener != null) {
                                onDeliveryTimeSelectedListener.onDeliveryTimeSelect(dateTimeDataList.get((todaySpinner.getSelectedItemPosition() - 1)));
                            }
                        }
                    } else if (tomorrowSpinner != null && tomorrowSpinner.getSelectedItemPosition() != 0) {
                        if (tomorrowList != null) {
                            if (onDeliveryTimeSelectedListener != null) {
                                onDeliveryTimeSelectedListener.onDeliveryTimeSelect(tomorrowDataList.get((tomorrowSpinner.getSelectedItemPosition() - 1)));
                            }
                        }
                    }
                }*/

                dismiss();

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

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        getDialog().getWindow().setAttributes(lp);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



    /*    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(getDialog().getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.90f);
        int dialogWindowHeight = (int) (displayHeight * 0.90f);
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;

        getDialog().getWindow().setAttributes(layoutParams);

        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);*/
    }


    public void getTimeSlot() {
        progressBar.setVisibility(View.VISIBLE);
        VoucherApplyInterface apiInterface = ApiClient.getClient(context).create(VoucherApplyInterface.class);
        TimeSlotRequest request = new TimeSlotRequest();
        request.setRestaurant_id(sharePre.getString(sharePre.RESTUARANT_ID));
        request.setOrder_type(sharePre.getString(sharePre.ORDER_TYPE));

        Call<TimeSlotResponse> call3 = apiInterface.restuarantTimeSlot(request);
        call3.enqueue(new Callback<TimeSlotResponse>() {
            @Override
            public void onResponse(Call<TimeSlotResponse> call, Response<TimeSlotResponse> response) {
                progressBar.setVisibility(View.GONE);

                try {
                    if (response.body().getSuccess()) {
                        todayDate = response.body().getTodaydate();
                        tomorrowDate = response.body().getTodaydate();
                        nowDate = response.body().getTimenow();

                        if (response.body().getData().getToday() != null && response.body().getData().getToday().size() > 0) {
                            toDayDataList = response.body().getData().getToday();
                            for (int i = 0; i < toDayDataList.size(); i++) {
                                todayList.add(toDayDataList.get(i));
                                // dateString = response.body().getTodaydate();
                            }
                        }
                        if (response.body().getData().getTomorrow() != null && response.body().getData().getTomorrow().size() > 0) {
                            tomorrowDataList = response.body().getData().getTomorrow();
                            for (int i = 0; i < response.body().getData().getTomorrow().size(); i++) {
                                tommorowList.add(tomorrowDataList.get(i));
                                // dateString = response.body().getTodaydate();
                            }
                        }

                        setupViewPager(pager, dayOfTheWeek, nextDay);


                        /* Start========================================*/
                      /*  todaySpinner.setVisibility(View.VISIBLE);
                        int size = 0;
                        if (response.body().getData().getToday() != null && response.body().getData().getToday().size() > 0) {
                            size = response.body().getData().getToday().size();
                        }
                        if (response.body().getData().getTomorrow() != null && response.body().getData().getTomorrow().size() > 0) {
                            size += response.body().getData().getTomorrow().size();
                        }

                        toDayDataList = response.body().getData().getToday();
                        tomorrowDataList = response.body().getData().getTomorrow();

                        dateTimeDataList = new ArrayList<>();
                        dateTimeDataList.addAll(response.body().getData().getToday());
                        dateTimeDataList.addAll(response.body().getData().getTomorrow());


                        toDayList = new String[(size + 1)];
                        toDayList[0] = "Select Delivery Time";

                        int i;
                        for (i = 0; i < response.body().getData().getToday().size(); i++) {
                            String data = response.body().getData().getToday().get(i);
                            if (data != null) {

                                String dateString = data.substring(0, 10).replace("-", "/");


                                String[] str = data.split(" ");
                                if (str.length >= 2) {
                                    toDayList[(i + 1)] = str[1] + " (" + Constants.getDayMonth(dateString) + " )";
                                }
                            }
                        }


                        for (int j = 0; j < response.body().getData().getTomorrow().size(); j++) {
                            String data1 = response.body().getData().getTomorrow().get(j);
                            if (data1 != null) {

                                String dateString = data1.substring(0, 10).replace("-", "/");

                                String[] str = data1.split(" ");
                                if (str.length >= 2) {
                                    toDayList[(i + j + 1)] = str[1] + " (" + Constants.getDayMonth(dateString) + " )";
                                }
                            }
                        }

                        *//*End*//*

                         *//*
                        if (response.body().getData().getToday() != null && response.body().getData().getToday().size() > 0) {
                            todaySpinner.setVisibility(View.VISIBLE);
                            toDayList = new String[(response.body().getData().getToday().size() + 1)];
                            toDayList[0] = "Select Delivery Time";

                            toDayDataList = response.body().getData().getToday();

                            for (int i = 0; i < response.body().getData().getToday().size(); i++) {
                                String data = response.body().getData().getToday().get(i);
                                if (data != null) {

                                    String dateString = data.substring(0, 10).replace("-", "/");


                                    String[] str = data.split(" ");
                                    if (str.length >= 2) {
                                        toDayList[(i + 1)] = str[1] + " (" + Constants.getDayMonth(dateString) + " )";
                                    }
                                }
                            }


                        } else {
                            todaySpinner.setVisibility(View.GONE);
                        }

                        if (response.body().getData().getTomorrow() != null && response.body().getData().getTomorrow().size() > 0) {
                            tomorrowSpinner.setVisibility(View.VISIBLE);
                            tomorrowList = new String[(response.body().getData().getTomorrow().size() + 1)];
                            tomorrowList[0] = "Tomorrow";

                            tomorrowDataList = response.body().getData().getTomorrow();

                            for (int i = 0; i < response.body().getData().getTomorrow().size(); i++) {
                                String data = response.body().getData().getTomorrow().get(i);
                                if (data != null) {

                                    String dateString = data.substring(0, 10).replace("-", "/");

                                    String[] str = data.split(" ");
                                    if (str.length >= 2) {
                                        tomorrowList[(i + 1)] = str[1] + " (" + Constants.getDayMonth(dateString) + " )";
                                    }
                                }
                            }
                        } else {
                            tomorrowSpinner.setVisibility(View.GONE);
                        }*//*

                        if (response.body().getData().getToday() != null && response.body().getData().getToday().size() == 0 && response.body().getData().getTomorrow() != null && response.body().getData().getTomorrow().size() == 0) {
                            dismiss();
                        }
                        initView();*/

                    } else {


                    }
                } catch (Exception e) {
                    Log.e("Exception", e.getLocalizedMessage());

                }

            }

            @Override
            public void onFailure(Call<TimeSlotResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

            }
        });
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