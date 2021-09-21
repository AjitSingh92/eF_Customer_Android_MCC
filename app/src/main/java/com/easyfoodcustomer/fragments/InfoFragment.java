package com.easyfoodcustomer.fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.easyfoodcustomer.R;
import com.easyfoodcustomer.adapters.DeliveryAreaAdapter;
import com.easyfoodcustomer.restaurant_details.HygieneRatingModel;
import com.easyfoodcustomer.restaurant_details.model.new_restaurant_response.NewRestaurantsDetailsResponse;
import com.easyfoodcustomer.utility.Constants;

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
    @BindView(R.id.tv_mon_del)
    TextView tvDelMonday;
    @BindView(R.id.tv_mon_col)
    TextView tvColMonday;
    @BindView(R.id.tv_mon_din)
    TextView tvDinMonday;

    @BindView(R.id.tv_tue_del)
    TextView tvDelTuesday;
    @BindView(R.id.tv_tue_col)
    TextView tvColTuesday;
    @BindView(R.id.tv_tue_din)
    TextView tvDinTuesday;


    @BindView(R.id.tv_wed_del)
    TextView tvDelWednesday;
    @BindView(R.id.tv_wed_col)
    TextView tvColWednesday;
    @BindView(R.id.tv_wed_din)
    TextView tvDinWednesday;

    @BindView(R.id.tv_thu_del)
    TextView tvDelThursday;
    @BindView(R.id.tv_thu_col)
    TextView tvColThursday;
    @BindView(R.id.tv_thu_din)
    TextView tvDinThursday;

    @BindView(R.id.tv_fri_del)
    TextView tvDelFriday;
    @BindView(R.id.tv_fri_col)
    TextView tvColFriday;
    @BindView(R.id.tv_fri_din)
    TextView tvDinFriday;


    @BindView(R.id.tv_sat_del)
    TextView tvDelSaturday;
    @BindView(R.id.tv_sat_col)
    TextView tvColSaturday;
    @BindView(R.id.tv_sat_din)
    TextView tvDinSaturday;

    @BindView(R.id.tv_sun_del)
    TextView tvDelSunday;
    @BindView(R.id.tv_sun_col)
    TextView tvColSunday;
    @BindView(R.id.tv_sun_din)
    TextView tvDinSunday;

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
           /* for (int i = 0; i < response.getData().getRestaurants().getInfo().getTimings().getMonday().size(); i++) {
                if (times.equalsIgnoreCase("")) {
                    times = response.getData().getRestaurants().getInfo().getTimings().getMonday().get(i).getOpeningStartTime() + " - " + response.getData().getRestaurants().getInfo().getTimings().getMonday().get(i).getOpeningEndTime();
                } else {
                    times = times + "\n" + response.getData().getRestaurants().getInfo().getTimings().getMonday().get(i).getOpeningStartTime() + " - " + response.getData().getRestaurants().getInfo().getTimings().getMonday().get(i).getOpeningEndTime();

                }
            }*/

            tvDelMonday.setText(response.getData().getRestaurants().getInfo().getTimings().getMonday().get(0).getDeliveryStartTime() + "-" + response.getData().getRestaurants().getInfo().getTimings().getMonday().get(0).getDeliveryEndTime());
            tvColMonday.setText(response.getData().getRestaurants().getInfo().getTimings().getMonday().get(0).getCollectionStartTime() + "-" + response.getData().getRestaurants().getInfo().getTimings().getMonday().get(0).getCollectionEndTime());
            tvDinMonday.setText(response.getData().getRestaurants().getInfo().getTimings().getMonday().get(0).getOpeningStartTime() + "-" + response.getData().getRestaurants().getInfo().getTimings().getMonday().get(0).getOpeningEndTime());

            lyMonday.setVisibility(View.VISIBLE);
            if (todayDay.equalsIgnoreCase("Monday")) {
                lyMonday.setBackground(getResources().getDrawable(R.drawable.bg_blue_slection));
            } else {
                lyMonday.setBackground(null);
            }
        } else {
            tvDelMonday.setText("Closed");
            tvDelMonday.setTextSize(getResources().getDimension(R.dimen._3sdp));

            tvColMonday.setText("Closed");
            tvColMonday.setTextSize(getResources().getDimension(R.dimen._3sdp));

            tvDinMonday.setText("Closed");
            tvDinMonday.setTextSize(getResources().getDimension(R.dimen._3sdp));


           /* tvColMonday.setVisibility(View.GONE);
            tvDinMonday.setVisibility(View.GONE);*/
        }


        if (response.getData().getRestaurants().getInfo().getTimings().getTuesday() != null) {
            tvDelTuesday.setText(response.getData().getRestaurants().getInfo().getTimings().getTuesday().get(0).getDeliveryStartTime() + "-" + response.getData().getRestaurants().getInfo().getTimings().getTuesday().get(0).getDeliveryEndTime());
            tvColTuesday.setText(response.getData().getRestaurants().getInfo().getTimings().getTuesday().get(0).getCollectionStartTime() + "-" + response.getData().getRestaurants().getInfo().getTimings().getTuesday().get(0).getCollectionEndTime());
            tvDinTuesday.setText(response.getData().getRestaurants().getInfo().getTimings().getTuesday().get(0).getOpeningStartTime() + "-" + response.getData().getRestaurants().getInfo().getTimings().getTuesday().get(0).getOpeningEndTime());

            lyTuesday.setVisibility(View.VISIBLE);

            if (todayDay.equalsIgnoreCase("Tuesday")) {
                lyTuesday.setBackground(getResources().getDrawable(R.drawable.bg_blue_slection));
            } else {
                lyTuesday.setBackground(null);
            }
        } else {
            tvDelTuesday.setText("Closed");
            tvDelTuesday.setTextSize(getResources().getDimension(R.dimen._3sdp));

            tvColTuesday.setText("Closed");
            tvColTuesday.setTextSize(getResources().getDimension(R.dimen._3sdp));

            tvDinTuesday.setText("Closed");
            tvDinTuesday.setTextSize(getResources().getDimension(R.dimen._3sdp));

            /*tvColTuesday.setVisibility(View.GONE);
            tvDinTuesday.setVisibility(View.GONE);*/
        }


        if (response.getData().getRestaurants().getInfo().getTimings().getWednesday() != null) {
            tvDelWednesday.setText(response.getData().getRestaurants().getInfo().getTimings().getWednesday().get(0).getDeliveryStartTime() + "-" + response.getData().getRestaurants().getInfo().getTimings().getWednesday().get(0).getDeliveryEndTime());
            tvColWednesday.setText(response.getData().getRestaurants().getInfo().getTimings().getWednesday().get(0).getCollectionStartTime() + "-" + response.getData().getRestaurants().getInfo().getTimings().getWednesday().get(0).getCollectionEndTime());
            tvDinWednesday.setText(response.getData().getRestaurants().getInfo().getTimings().getWednesday().get(0).getOpeningStartTime() + "-" + response.getData().getRestaurants().getInfo().getTimings().getWednesday().get(0).getOpeningEndTime());

            lyWednesday.setVisibility(View.VISIBLE);

            if (todayDay.equalsIgnoreCase("Wednesday")) {
                lyWednesday.setBackground(getResources().getDrawable(R.drawable.bg_blue_slection));
            } else {
                lyWednesday.setBackground(null);
            }
        } else {
            tvDelWednesday.setText("Closed");
            tvDelWednesday.setTextSize(getResources().getDimension(R.dimen._3sdp));
            tvColWednesday.setText("Closed");
            tvColWednesday.setTextSize(getResources().getDimension(R.dimen._3sdp));
            tvDinWednesday.setText("Closed");
            tvDinWednesday.setTextSize(getResources().getDimension(R.dimen._3sdp));


            //tvColWednesday.setVisibility(View.GONE);
            //tvDinWednesday.setVisibility(View.GONE);
        }


        if (response.getData().getRestaurants().getInfo().getTimings().getThursday() != null) {
            tvDelThursday.setText(response.getData().getRestaurants().getInfo().getTimings().getThursday().get(0).getDeliveryStartTime() + "-" + response.getData().getRestaurants().getInfo().getTimings().getThursday().get(0).getDeliveryEndTime());
            tvColThursday.setText(response.getData().getRestaurants().getInfo().getTimings().getThursday().get(0).getCollectionStartTime() + "-" + response.getData().getRestaurants().getInfo().getTimings().getThursday().get(0).getCollectionEndTime());
            tvDinThursday.setText(response.getData().getRestaurants().getInfo().getTimings().getThursday().get(0).getOpeningStartTime() + "-" + response.getData().getRestaurants().getInfo().getTimings().getThursday().get(0).getOpeningEndTime());

            lyThursday.setVisibility(View.VISIBLE);

            if (todayDay.equalsIgnoreCase("Thursday")) {
                lyThursday.setBackground(getResources().getDrawable(R.drawable.bg_blue_slection));
            } else {
                lyThursday.setBackground(null);
            }

        } else {
            tvDelThursday.setText("Closed");
            tvDelThursday.setTextSize(getResources().getDimension(R.dimen._3sdp));

            tvColThursday.setText("Closed");
            tvColThursday.setTextSize(getResources().getDimension(R.dimen._3sdp));

            tvDinThursday.setText("Closed");
            tvDinThursday.setTextSize(getResources().getDimension(R.dimen._3sdp));

            //tvColThursday.setVisibility(View.GONE);
            //tvDinThursday.setVisibility(View.GONE);
        }


        if (response.getData().getRestaurants().getInfo().getTimings().getFriday() != null) {
            tvDelFriday.setText(response.getData().getRestaurants().getInfo().getTimings().getFriday().get(0).getDeliveryStartTime() + "-" + response.getData().getRestaurants().getInfo().getTimings().getFriday().get(0).getDeliveryEndTime());
            tvColFriday.setText(response.getData().getRestaurants().getInfo().getTimings().getFriday().get(0).getCollectionStartTime() + "-" + response.getData().getRestaurants().getInfo().getTimings().getFriday().get(0).getCollectionEndTime());
            tvDinFriday.setText(response.getData().getRestaurants().getInfo().getTimings().getFriday().get(0).getOpeningStartTime() + "-" + response.getData().getRestaurants().getInfo().getTimings().getFriday().get(0).getOpeningEndTime());

            lyFriday.setVisibility(View.VISIBLE);

            if (todayDay.equalsIgnoreCase("Friday")) {
                lyFriday.setBackground(getResources().getDrawable(R.drawable.bg_blue_slection));
            } else {
                lyFriday.setBackground(null);
            }
        } else {
            tvDelFriday.setText("Closed");
            tvDelFriday.setTextSize(getResources().getDimension(R.dimen._3sdp));
            tvColFriday.setText("Closed");
            tvColFriday.setTextSize(getResources().getDimension(R.dimen._3sdp));
            tvDinFriday.setText("Closed");
            tvDinFriday.setTextSize(getResources().getDimension(R.dimen._3sdp));

   /*         tvColFriday.setVisibility(View.GONE);
            tvDinFriday.setVisibility(View.GONE);*/
        }


        if (response.getData().getRestaurants().getInfo().getTimings().getSaturday() != null) {
            tvDelSaturday.setText(response.getData().getRestaurants().getInfo().getTimings().getSaturday().get(0).getDeliveryStartTime() + "-" + response.getData().getRestaurants().getInfo().getTimings().getSaturday().get(0).getDeliveryEndTime());
            tvColSaturday.setText(response.getData().getRestaurants().getInfo().getTimings().getSaturday().get(0).getCollectionStartTime() + "-" + response.getData().getRestaurants().getInfo().getTimings().getSaturday().get(0).getCollectionEndTime());
            tvDinSaturday.setText(response.getData().getRestaurants().getInfo().getTimings().getSaturday().get(0).getOpeningStartTime() + "-" + response.getData().getRestaurants().getInfo().getTimings().getSaturday().get(0).getOpeningEndTime());

            lySaturday.setVisibility(View.VISIBLE);

            if (todayDay.equalsIgnoreCase("Saturday")) {
                lySaturday.setBackground(getResources().getDrawable(R.drawable.bg_blue_slection));
            } else {
                lySaturday.setBackground(null);
            }
        } else {
            tvDelSaturday.setText("Closed");
            tvDelSaturday.setTextSize(getResources().getDimension(R.dimen._3sdp));
            tvColSaturday.setText("Closed");
            tvColSaturday.setTextSize(getResources().getDimension(R.dimen._3sdp));

            tvDinSaturday.setText("Closed");
            tvDinSaturday.setTextSize(getResources().getDimension(R.dimen._3sdp));

            //tvColSaturday.setVisibility(View.GONE);
            //tvDinSaturday.setVisibility(View.GONE);
        }


        if (response.getData().getRestaurants().getInfo().getTimings().getSunday() != null) {
            tvDelSunday.setText(response.getData().getRestaurants().getInfo().getTimings().getSunday().get(0).getDeliveryStartTime() + "-" + response.getData().getRestaurants().getInfo().getTimings().getSunday().get(0).getDeliveryEndTime());
            tvColSunday.setText(response.getData().getRestaurants().getInfo().getTimings().getSunday().get(0).getCollectionStartTime() + "-" + response.getData().getRestaurants().getInfo().getTimings().getSunday().get(0).getCollectionEndTime());
            tvDinSunday.setText(response.getData().getRestaurants().getInfo().getTimings().getSunday().get(0).getOpeningStartTime() + "-" + response.getData().getRestaurants().getInfo().getTimings().getSunday().get(0).getOpeningEndTime());

            lySunday.setVisibility(View.VISIBLE);
            if (todayDay.equalsIgnoreCase("Sunday")) {
                lySunday.setBackground(getResources().getDrawable(R.drawable.bg_blue_slection));
            } else {
                lySunday.setBackground(null);
            }
        } else {
            tvDelSunday.setText("Closed");
            tvDelSunday.setTextSize(getResources().getDimension(R.dimen._3sdp));
            tvColSunday.setText("Closed");
            tvColSunday.setTextSize(getResources().getDimension(R.dimen._3sdp));

            tvDinSunday.setText("Closed");
            tvDinSunday.setTextSize(getResources().getDimension(R.dimen._3sdp));

            //tvColSunday.setVisibility(View.GONE);
            //tvDinSunday.setVisibility(View.GONE);
        }
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
        if (hygieneRatingModel != null) {
            if (hygieneRatingModel.getData() != null) {
                iv_hygiene.setVisibility(View.VISIBLE);
                hygine_text.setText(mActivity.getString(R.string.hygiene_rating));
                Glide.with(mActivity).load(hygieneRatingModel.getData()).apply(new RequestOptions()
                        .placeholder(R.drawable.easy_food_image))
                        .into(iv_hygiene);
            }
        } else {
            hygine_text.setText(mActivity.getString(R.string.hygiene_rating_is_pending));
            iv_hygiene.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
