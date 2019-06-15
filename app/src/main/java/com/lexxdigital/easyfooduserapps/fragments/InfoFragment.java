package com.lexxdigital.easyfooduserapps.fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.new_restaurant_response.NewRestaurantsDetailsResponse;
import com.lexxdigital.easyfooduserapps.utility.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

//implements OnMapReadyCallback
public class InfoFragment extends Fragment implements OnMapReadyCallback {

    Context mContext;
    Activity mActivity;

    @BindView(R.id.restaurants_name)
    TextView restaurantsName;
    @BindView(R.id.about)
    TextView about;
    @BindView(R.id.time_monday)
    TextView timeMonday;
    @BindView(R.id.time_tuesday)
    TextView timeTuesday;
    @BindView(R.id.time_wednesday)
    TextView timeWednesday;
    @BindView(R.id.time_thursday)
    TextView timeThursday;
    @BindView(R.id.time_friday)
    TextView timeFriday;
    @BindView(R.id.time_saturday)
    TextView timeSaturday;
    @BindView(R.id.time_sunday)
    TextView timeSunday;
    @BindView(R.id.list_postcode)
    TextView listDeliveryArea;

    Unbinder unbinder;

    NewRestaurantsDetailsResponse response;
    @BindView(R.id.ly_monday)
    LinearLayout lyMonday;
    @BindView(R.id.ly_tuesday)
    LinearLayout lyTuesday;
    @BindView(R.id.ly_wednesday)
    LinearLayout lyWednesday;
    @BindView(R.id.ly_thursday)
    LinearLayout lyThursday;
    @BindView(R.id.ly_friday)
    LinearLayout lyFriday;
    @BindView(R.id.ly_saturday)
    LinearLayout lySaturday;
    @BindView(R.id.ly_sunday)
    LinearLayout lySunday;

    public InfoFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public InfoFragment(Activity activity, Context context, NewRestaurantsDetailsResponse res) {
        // Required empty public constructor
        this.mContext = context;
        this.mActivity = activity;
        this.response = res;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

//        mapView = (MapView) view.findViewById(R.id.mapView2);
//        mapView.onCreate(savedInstanceState);
//        mapView.setClickable(true);
//        mapView.setFocusable(true);
//        mapView.setDuplicateParentStateEnabled(false);
//        mapView.getMapAsync(this);

        setData();
        return view;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng restaurant = new LatLng(Double.parseDouble(response.getData().getRestaurants().getLat()), Double.parseDouble(response.getData().getRestaurants().getLng()));
        googleMap.addMarker(new MarkerOptions().position(restaurant)
                .title(response.getData().getRestaurants().getRestaurantName()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(restaurant));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurant, (float) 15));

    }


    public void setData() {
        restaurantsName.setText(response.getData().getRestaurants().getRestaurantName());
        about.setText(response.getData().getRestaurants().getInfo().getAbout());
        String todayDay = Constants.getTodayDay();
        Log.e("Day", todayDay);
        if (response.getData().getRestaurants().getInfo().getTimings().getMonday() != null) {
            String times = "";
            for (int i = 0; i < response.getData().getRestaurants().getInfo().getTimings().getMonday().size(); i++) {
                if (times.equalsIgnoreCase("")) {
                    times = response.getData().getRestaurants().getInfo().getTimings().getMonday().get(i).getOpeningStartTime() + " - " + response.getData().getRestaurants().getInfo().getTimings().getMonday().get(i).getOpeningEndTime();
                } else {
                    times = times + "\n" + response.getData().getRestaurants().getInfo().getTimings().getMonday().get(i).getOpeningStartTime() + " - " + response.getData().getRestaurants().getInfo().getTimings().getMonday().get(i).getOpeningEndTime();

                }
            }
            lyMonday.setVisibility(View.VISIBLE);
            timeMonday.setText(times);
            if (todayDay.equalsIgnoreCase("Monday")) {
                lyMonday.setBackground(getResources().getDrawable(R.drawable.bg_btn_forgot_two));
            } else {
                lyMonday.setBackground(getResources().getDrawable(R.drawable.bg_btn_forgot_two_2));
            }
        } else
            lyMonday.setVisibility(View.GONE);


        Log.e("MMMM", "///" + response.getData().getRestaurants().getInfo().getTimings().getTuesday());

        if (response.getData().getRestaurants().getInfo().getTimings().getTuesday() != null) {
            String times = "";
            for (int i = 0; i < response.getData().getRestaurants().getInfo().getTimings().getTuesday().size(); i++) {
                if (times.equalsIgnoreCase("")) {
                    times = response.getData().getRestaurants().getInfo().getTimings().getTuesday().get(i).getOpeningStartTime() + " - " + response.getData().getRestaurants().getInfo().getTimings().getTuesday().get(i).getOpeningEndTime();
                } else {
                    times = times + "\n" + response.getData().getRestaurants().getInfo().getTimings().getTuesday().get(i).getOpeningStartTime() + " - " + response.getData().getRestaurants().getInfo().getTimings().getTuesday().get(i).getOpeningEndTime();

                }
            }
            timeTuesday.setText(times);
            lyTuesday.setVisibility(View.VISIBLE);

            if (todayDay.equalsIgnoreCase("Tuesday")) {
                lyTuesday.setBackground(getResources().getDrawable(R.drawable.bg_btn_forgot_two));
            } else {
                lyTuesday.setBackground(getResources().getDrawable(R.drawable.bg_btn_forgot_two_2));
            }
        } else
            lyTuesday.setVisibility(View.GONE);


        if (response.getData().getRestaurants().getInfo().getTimings().getWednesday() != null) {
            String times = "";
            for (int i = 0; i < response.getData().getRestaurants().getInfo().getTimings().getWednesday().size(); i++) {
                if (times.equalsIgnoreCase("")) {
                    times = response.getData().getRestaurants().getInfo().getTimings().getWednesday().get(i).getOpeningStartTime() + " - " + response.getData().getRestaurants().getInfo().getTimings().getWednesday().get(i).getOpeningEndTime();
                } else {
                    times = times + "\n" + response.getData().getRestaurants().getInfo().getTimings().getWednesday().get(i).getOpeningStartTime() + " - " + response.getData().getRestaurants().getInfo().getTimings().getWednesday().get(i).getOpeningEndTime();

                }
            }
            timeWednesday.setText(times);
            lyWednesday.setVisibility(View.VISIBLE);

            if (todayDay.equalsIgnoreCase("Wednesday")) {
                lyWednesday.setBackground(getResources().getDrawable(R.drawable.bg_btn_forgot_two));
            } else {
                lyWednesday.setBackground(getResources().getDrawable(R.drawable.bg_btn_forgot_two_2));
            }
        } else
            lyWednesday.setVisibility(View.GONE);


        if (response.getData().getRestaurants().getInfo().getTimings().getThursday() != null) {
            String times = "";
            for (int i = 0; i < response.getData().getRestaurants().getInfo().getTimings().getThursday().size(); i++) {
                if (times.equalsIgnoreCase("")) {
                    times = response.getData().getRestaurants().getInfo().getTimings().getThursday().get(i).getOpeningStartTime() + " - " + response.getData().getRestaurants().getInfo().getTimings().getThursday().get(i).getOpeningEndTime();
                } else {
                    times = times + "\n" + response.getData().getRestaurants().getInfo().getTimings().getThursday().get(i).getOpeningStartTime() + " - " + response.getData().getRestaurants().getInfo().getTimings().getThursday().get(i).getOpeningEndTime();

                }
            }
            timeThursday.setText(times);
            lyThursday.setVisibility(View.VISIBLE);

            if (todayDay.equalsIgnoreCase("Thursday")) {
                lyThursday.setBackground(getResources().getDrawable(R.drawable.bg_btn_forgot_two));
            } else {
                lyThursday.setBackground(getResources().getDrawable(R.drawable.bg_btn_forgot_two_2));
            }

        } else
            lyThursday.setVisibility(View.GONE);


        if (response.getData().getRestaurants().getInfo().getTimings().getFriday() != null) {
            String times = "";
            for (int i = 0; i < response.getData().getRestaurants().getInfo().getTimings().getFriday().size(); i++) {
                if (times.equalsIgnoreCase("")) {
                    times = response.getData().getRestaurants().getInfo().getTimings().getFriday().get(i).getOpeningStartTime() + " - " + response.getData().getRestaurants().getInfo().getTimings().getFriday().get(i).getOpeningEndTime();
                } else {
                    times = times + "\n" + response.getData().getRestaurants().getInfo().getTimings().getFriday().get(i).getOpeningStartTime() + " - " + response.getData().getRestaurants().getInfo().getTimings().getFriday().get(i).getOpeningEndTime();

                }
            }
            timeFriday.setText(times);
            lyFriday.setVisibility(View.VISIBLE);

            if (todayDay.equalsIgnoreCase("Friday")) {
                lyFriday.setBackground(getResources().getDrawable(R.drawable.bg_btn_forgot_two));
            } else {
                lyFriday.setBackground(getResources().getDrawable(R.drawable.bg_btn_forgot_two_2));
            }
        } else
            lyFriday.setVisibility(View.GONE);


        if (response.getData().getRestaurants().getInfo().getTimings().getSaturday() != null) {
            String times = "";
            for (int i = 0; i < response.getData().getRestaurants().getInfo().getTimings().getSaturday().size(); i++) {
                if (times.equalsIgnoreCase("")) {
                    times = response.getData().getRestaurants().getInfo().getTimings().getSaturday().get(i).getOpeningStartTime() + " - " + response.getData().getRestaurants().getInfo().getTimings().getSaturday().get(i).getOpeningEndTime();
                } else {
                    times = times + "\n" + response.getData().getRestaurants().getInfo().getTimings().getSaturday().get(i).getOpeningStartTime() + " - " + response.getData().getRestaurants().getInfo().getTimings().getSaturday().get(i).getOpeningEndTime();

                }
            }
            timeSaturday.setText(times);
            lySaturday.setVisibility(View.VISIBLE);

            if (todayDay.equalsIgnoreCase("Saturday")) {
                lySaturday.setBackground(getResources().getDrawable(R.drawable.bg_btn_forgot_two));
            } else {
                lySaturday.setBackground(getResources().getDrawable(R.drawable.bg_btn_forgot_two_2));
            }
        } else
            lySaturday.setVisibility(View.GONE);


        if (response.getData().getRestaurants().getInfo().getTimings().getSunday() != null) {
            String times = "";
            for (int i = 0; i < response.getData().getRestaurants().getInfo().getTimings().getSunday().size(); i++) {
                if (times.equalsIgnoreCase("")) {
                    times = response.getData().getRestaurants().getInfo().getTimings().getSunday().get(i).getOpeningStartTime() + " - " + response.getData().getRestaurants().getInfo().getTimings().getSunday().get(i).getOpeningEndTime();
                } else {
                    times = times + "\n" + response.getData().getRestaurants().getInfo().getTimings().getSunday().get(i).getOpeningStartTime() + " - " + response.getData().getRestaurants().getInfo().getTimings().getSunday().get(i).getOpeningEndTime();

                }
            }
            timeSunday.setText(times);
            lySunday.setVisibility(View.VISIBLE);
            if (todayDay.equalsIgnoreCase("Sunday")) {
                lySunday.setBackground(getResources().getDrawable(R.drawable.bg_btn_forgot_two));
            } else {
                lySunday.setBackground(getResources().getDrawable(R.drawable.bg_btn_forgot_two_2));
            }
        } else
            lySunday.setVisibility(View.GONE);

        String[] array = response.getData().getRestaurants().getDeliveryAreas().split(",");
        String dArea = "";
        for (int i = 0; i < array.length; i++) {
            if (dArea.equalsIgnoreCase("")) {
                dArea = array[i];
            } else {
                dArea = dArea + "\n" + array[i];
            }
        }
        listDeliveryArea.setText(dArea);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
