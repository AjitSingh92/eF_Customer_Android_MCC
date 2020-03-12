package com.lexxdigital.easyfooduserapps.fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.adapters.DeliveryAreaAdapter;
import com.lexxdigital.easyfooduserapps.restaurant_details.HygieneRatingModel;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.new_restaurant_response.NewRestaurantsDetailsResponse;
import com.lexxdigital.easyfooduserapps.utility.Constants;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.rv_delivery_areas)
    RecyclerView rvDeliverAreas;
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

    @BindView(R.id.iv_hygiene)
    ImageView iv_hygiene;

    @BindView(R.id.hygine_text)
    TextView hygine_text;

    FirebaseAnalytics mFirebaseAnalytics;

    private List<String> areCodeList;
    private HygieneRatingModel hygieneRatingModel;

    public InfoFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public InfoFragment(Activity activity, Context context, NewRestaurantsDetailsResponse res, HygieneRatingModel hygieneRatingModel) {
        // Required empty public constructor
        this.mContext = context;
        this.mActivity = activity;
        this.response = res;
        this.hygieneRatingModel = hygieneRatingModel;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
        unbinder = ButterKnife.bind(this, view);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);
        areCodeList = new ArrayList<>();
        setData();
        return view;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng restaurant = new LatLng(Double.parseDouble(response.getData().getRestaurants().getLat()), Double.parseDouble(response.getData().getRestaurants().getLng()));
        googleMap.addMarker(new MarkerOptions().position(restaurant)
                .title(response.getData().getRestaurants().getRestaurantName()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(restaurant));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurant, (float) 15));

    }


    private void setAreaAdapter() {
        DeliveryAreaAdapter deliveryAreaAdapter = new DeliveryAreaAdapter(getActivity(), areCodeList);
        rvDeliverAreas.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDeliverAreas.setAdapter(deliveryAreaAdapter);
    }

    public void setData() {
        restaurantsName.setText("About " + response.getData().getRestaurants().getRestaurantName());

        about.setText(response.getData().getRestaurants().getInfo().getAbout());

        setAreaAdapter();

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
                lyMonday.setBackground(getResources().getDrawable(R.drawable.bg_blue_slection));
            } else {
                lyMonday.setBackground(null);
            }
        } else
            lyMonday.setVisibility(View.GONE);
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
                lyTuesday.setBackground(getResources().getDrawable(R.drawable.bg_blue_slection));
            } else {
                lyTuesday.setBackground(null);
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
                lyWednesday.setBackground(getResources().getDrawable(R.drawable.bg_blue_slection));
            } else {
                lyWednesday.setBackground(null);
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
                lyThursday.setBackground(getResources().getDrawable(R.drawable.bg_blue_slection));
            } else {
                lyThursday.setBackground(null);
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
                lyFriday.setBackground(getResources().getDrawable(R.drawable.bg_blue_slection));
            } else {
                lyFriday.setBackground(null);
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
                lySaturday.setBackground(getResources().getDrawable(R.drawable.bg_blue_slection));
            } else {
                lySaturday.setBackground(null);
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
                lySunday.setBackground(getResources().getDrawable(R.drawable.bg_blue_slection));
            } else {
                lySunday.setBackground(null);
            }
        } else
            lySunday.setVisibility(View.GONE);
// shakti make a change check object

        if (response.getData().getRestaurants().getDeliveryAreas() != null && response.getData().getRestaurants().getDeliveryAreas().size() > 0) {
            String dArea = "";
            for (int i = 0; i < response.getData().getRestaurants().getDeliveryAreas().size(); i++) {
                {
                    dArea = dArea + "\n" + response.getData().getRestaurants().getDeliveryAreas().get(i).getPostcode();
                }
            }
            listDeliveryArea.setText(dArea);
        }
        if (hygieneRatingModel != null ) {
            if (hygieneRatingModel.getData() != null) {
                iv_hygiene.setVisibility(View.VISIBLE);
                hygine_text.setText(mActivity.getString(R.string.hygiene_rating));
                Glide.with(mActivity).load(hygieneRatingModel.getData()).apply(new RequestOptions()
                        .placeholder(R.drawable.easy_food_image))
                        .into(iv_hygiene);
            }
        }else {
            hygine_text.setText(mActivity.getString(R.string.hygiene_rating_is_pending));
            iv_hygiene.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
