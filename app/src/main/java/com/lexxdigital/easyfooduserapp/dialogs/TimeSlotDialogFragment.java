package com.lexxdigital.easyfooduserapp.dialogs;

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
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.ArrayUtils;
import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.adapters.AddressDialogAdapter;
import com.lexxdigital.easyfooduserapp.adapters.RecyclerLayoutManager;
import com.lexxdigital.easyfooduserapp.api.VoucherApplyInterface;
import com.lexxdigital.easyfooduserapp.model.AddressList;
import com.lexxdigital.easyfooduserapp.model.restuarant_time_slot.TimeSlotRequest;
import com.lexxdigital.easyfooduserapp.model.restuarant_time_slot.TimeSlotResponse;
import com.lexxdigital.easyfooduserapp.utility.ApiClient;
import com.lexxdigital.easyfooduserapp.utility.Constants;
import com.lexxdigital.easyfooduserapp.utility.SharedPreferencesClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeSlotDialogFragment extends DialogFragment implements View.OnClickListener {
    Context context;
    private List<AddressList> addressList = new ArrayList<AddressList>();
    AddressDialogAdapter addressDialogAdapter;
    RecyclerView recyclerViewList;
    RecyclerLayoutManager recyclerLayoutManager;
    ProgressBar progressBar;
    private static String[] toDayList;
    private static String[] tomorrowList;
    private static List<String> toDayDataList;
    private static List<String> tomorrowDataList;
    private static List<String> dateTimeDataList;
    String toDay, tomorrow;
    Spinner todaySpinner, tomorrowSpinner;
    SharedPreferencesClass sharePre;
    OnDeliveryTimeSelectedListener onDeliveryTimeSelectedListener;

    public interface OnDeliveryTimeSelectedListener {
        void onDeliveryTimeSelect(String time);
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.time_slot_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progressBar);
        sharePre = new SharedPreferencesClass(context);
        todaySpinner = view.findViewById(R.id.spinner_day);
        tomorrowSpinner = view.findViewById(R.id.spinner_tomorrow);
        view.findViewById(R.id.cross_tv).setOnClickListener(this);
        view.findViewById(R.id.btn_ok).setOnClickListener(this);

        if (Constants.isInternetConnectionAvailable(300)) {
            getTimeSlot();
        } else {

            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
            dismiss();

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
            case R.id.cross_tv:
                dismiss();
                break;
            case R.id.btn_ok:

                if (todaySpinner != null && todaySpinner.getSelectedItemPosition() != 0) {
                    if (toDayDataList != null) {
                        if (onDeliveryTimeSelectedListener != null) {
                            onDeliveryTimeSelectedListener.onDeliveryTimeSelect(toDayDataList.get((todaySpinner.getSelectedItemPosition() - 1)));
                        }
                    }
                } else if (tomorrowSpinner != null && tomorrowSpinner.getSelectedItemPosition() != 0) {
                    if (tomorrowList != null) {
                        if (onDeliveryTimeSelectedListener != null) {
                            onDeliveryTimeSelectedListener.onDeliveryTimeSelect(tomorrowDataList.get((tomorrowSpinner.getSelectedItemPosition() - 1)));
                        }
                    }
                }

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


    public void getTimeSlot() {
        progressBar.setVisibility(View.VISIBLE);
        VoucherApplyInterface apiInterface = ApiClient.getClient(context).create(VoucherApplyInterface.class);
        TimeSlotRequest request = new TimeSlotRequest();
        request.setRestaurant_id(sharePre.getString(sharePre.RESTUARANT_ID));

        Call<TimeSlotResponse> call3 = apiInterface.restuarantTimeSlot(request);
        call3.enqueue(new Callback<TimeSlotResponse>() {
            @Override
            public void onResponse(Call<TimeSlotResponse> call, Response<TimeSlotResponse> response) {
                progressBar.setVisibility(View.GONE);

                try {
                    if (response.body().getSuccess()) {

                        /* Start========================================*/
                        todaySpinner.setVisibility(View.VISIBLE);
                        int size = 0;
                        if (response.body().getData().getToday() != null && response.body().getData().getToday().size() > 0) {
                            size = response.body().getData().getToday().size();
                        }
                        if (response.body().getData().getTomorrow() != null && response.body().getData().getTomorrow().size() > 0) {
                            size += response.body().getData().getTomorrow().size();
                        }

                        toDayDataList = response.body().getData().getToday();
                        tomorrowDataList = response.body().getData().getTomorrow();


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
                                    toDayList[(i+j+ 1)] = str[1] + " (" + Constants.getDayMonth(dateString) + " )";
                                }
                            }
                        }

                        /*End*/

/*
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
                        }*/

                        if (response.body().getData().getToday() != null && response.body().getData().getToday().size() == 0 && response.body().getData().getTomorrow() != null && response.body().getData().getTomorrow().size() == 0) {
                            dismiss();
                        }
                        initView();
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