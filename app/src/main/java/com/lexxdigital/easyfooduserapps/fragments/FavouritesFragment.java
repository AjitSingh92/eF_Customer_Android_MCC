package com.lexxdigital.easyfooduserapps.fragments;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.adapters.FavouritesAdapter;
import com.lexxdigital.easyfooduserapps.api.AddFavouritesInterface;
import com.lexxdigital.easyfooduserapps.api.FavouritesListInterface;
import com.lexxdigital.easyfooduserapps.dashboard.DashboardActivity;
import com.lexxdigital.easyfooduserapps.model.FavouriteList;
import com.lexxdigital.easyfooduserapps.model.add_favourites_request.AddFavouristeResquest;
import com.lexxdigital.easyfooduserapps.model.add_favourites_response.AddFavouristeResponse;
import com.lexxdigital.easyfooduserapps.model.favourites_list_request.FavouristeListRequest;
import com.lexxdigital.easyfooduserapps.model.favourites_list_response.FavouristeListRespose;
import com.lexxdigital.easyfooduserapps.utility.ApiClient;
import com.lexxdigital.easyfooduserapps.utility.Constants;
import com.lexxdigital.easyfooduserapps.utility.GlobalValues;
import com.lexxdigital.easyfooduserapps.utility.SharedPreferencesClass;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class FavouritesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, FavouritesAdapter.PostionInterface {

    @BindView(R.id.febList)
    RecyclerView febList;
    @BindView(R.id.llbotom)
    LinearLayout llbotom;
    Unbinder unbinder;
    FavouritesAdapter mFavouritesAdapter;
    @BindView(R.id.add_more_fav)
    LinearLayout addMoreFav;
    @BindView(R.id.swipreferesh)
    SwipeRefreshLayout swipreferesh;
    @BindView(R.id.add_favourite)
    TextView addFavourite;
    private Context mContext;
    private GlobalValues val;
    private Dialog dialog;
    LinearLayout emptFav;
    TextView tooltextvView;
    RelativeLayout relativeLayout;
    SharedPreferencesClass sharedPreferencesClass;
    private List<FavouriteList> listFavourites = new ArrayList<>();
    private List<FavouriteList.RestaurantTimingList> restaurantTimingLists = new ArrayList<>();
    FavouritesAdapter.PostionInterface postionInterface;
    FirebaseAnalytics mFirebaseAnalytics;

    public FavouritesFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
        unbinder = ButterKnife.bind(this, view);
        val = (GlobalValues) mContext;
        postionInterface = this;
        sharedPreferencesClass = new SharedPreferencesClass(mContext);
        dialog = new Dialog(getActivity());
        dialog.setTitle("");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        relativeLayout = view.findViewById(R.id.rel_fav_layout);
        emptFav = view.findViewById(R.id.emptyfav);
        dialog.show();

        swipreferesh.setOnRefreshListener(this);
        swipreferesh.setColorSchemeResources(R.color.orange,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipreferesh.post(new Runnable() {
            @Override
            public void run() {
                if (swipreferesh != null) {
                    swipreferesh.setRefreshing(true);
                }

                febList.removeAllViews();
                listFavourites.clear();
                getFavouritesList();
                swipreferesh.setRefreshing(false);

            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        dialog.dismiss();
    }

    @Override
    public void onStop() {
        super.onStop();
        dialog.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initView(List<FavouriteList> list_Favourites) {

        if (list_Favourites.size() <= 0) {
            emptyScreen();
        } else {

            mFavouritesAdapter = new FavouritesAdapter(getContext(), list_Favourites, postionInterface, (DashboardActivity) getActivity());

            @SuppressLint("WrongConstant")
            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            febList.setLayoutManager(horizontalLayoutManagaer);

            mFavouritesAdapter.notifyDataSetChanged();
            febList.setAdapter(mFavouritesAdapter);
        }

    }

    public void getFavouritesList() {
        swipreferesh.setRefreshing(true);
        febList.removeAllViews();
        listFavourites.clear();
        FavouritesListInterface apiInterface = ApiClient.getClient(getContext()).create(FavouritesListInterface.class);
        FavouristeListRequest request = new FavouristeListRequest();
        request.setUserId(val.getLoginResponse().getData().getUserId());
        request.setFavouriteType("restaurant");
        request.setPostCode(sharedPreferencesClass.getPostalCode());
        request.setOffset(0);
        request.setLimit(100);

        Call<FavouristeListRespose> call3 = apiInterface.mFavouriteList(request);
        call3.enqueue(new Callback<FavouristeListRespose>() {
            @Override
            public void onResponse(Call<FavouristeListRespose> call, Response<FavouristeListRespose> response) {
                try {
                    dialog.hide();
                    if (response.body().getSuccess()) {
                        for (int i = 0; i < response.body().getData().getFavourites().size(); i++) {

                            for (int j = 0; j < response.body().getData().getFavourites().get(i).getRestaurantTiming().size(); j++) {
                                restaurantTimingLists.add(new FavouriteList.RestaurantTimingList(response.body().getData().getFavourites().get(i).getRestaurantTiming().get(j).getId(), response.body().getData().getFavourites().get(i).getRestaurantTiming().get(j).getRestaurant_id(), response.body().getData().getFavourites().get(i).getRestaurantTiming().get(j).getDay(), response.body().getData().getFavourites().get(i).getRestaurantTiming().get(j).getOpening_start_time(), response.body().getData().getFavourites().get(i).getRestaurantTiming().get(j).getOpening_start_time(), response.body().getData().getFavourites().get(i).getRestaurantTiming().get(j).getCollection_start_time(), response.body().getData().getFavourites().get(i).getRestaurantTiming().get(j).getCollection_end_time(), response.body().getData().getFavourites().get(i).getRestaurantTiming().get(j).getDelivery_start_time(), response.body().getData().getFavourites().get(i).getRestaurantTiming().get(j).getDelivery_end_time(), response.body().getData().getFavourites().get(i).getRestaurantTiming().get(j).getStatus()));
                            }
                            listFavourites.add(new FavouriteList(response.body().getData().getFavourites().get(i).getEntityId(), response.body().getData().getFavourites().get(i).getRestaurantName(), response.body().getData().getFavourites().get(i).getLogo(), response.body().getData().getFavourites().get(i).getBackgroundImage(), response.body().getData().getFavourites().get(i).getCuisines(), response.body().getData().getFavourites().get(i).getMinOderValue(), response.body().getData().getFavourites().get(i).getDeliveryCharge(), response.body().getData().getFavourites().get(i).getOverallRating(), response.body().getData().getFavourites().get(i).getRestaurantStatus(), response.body().getData().getFavourites().get(i).getDistance_in_miles(), restaurantTimingLists));
                        }

                        initView(listFavourites);
                    } else {

                    }
                } catch (Exception e) {
                    dialog.hide();
                }
                if (swipreferesh != null)
                    swipreferesh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<FavouristeListRespose> call, Throwable t) {
                dialog.hide();
                if (swipreferesh != null)
                    swipreferesh.setRefreshing(false);
            }
        });
    }

    @OnClick(R.id.add_more_fav)
    public void onViewClicked() {
        Constants.switchActivity(getActivity(), DashboardActivity.class);
        getActivity().overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }


    void emptyScreen() {
        if (listFavourites.size() > 0) {
            relativeLayout.setVisibility(View.VISIBLE);
            emptFav.setVisibility(View.GONE);

        } else {

            emptFav.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
        }
        addFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.fragmentCall(new DealsFragment(getApplicationContext(), tooltextvView, val.getPostCode()), getFragmentManager());
            }
        });
    }

    @Override
    public void onRefresh() {
        febList.removeAllViews();
        listFavourites.clear();
        getFavouritesList();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onclickedFav(int pos) {
        String id = listFavourites.get(pos).getEntityID();
        popUpDeleteFavourite(pos, id, sharedPreferencesClass.getString(sharedPreferencesClass.USER_ID), listFavourites.get(pos).getRestaurantName());
    }

    public void popUpDeleteFavourite(final int pos, final String resId, final String userId, String resName) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.favourite_remove_popup);

        TextView confirm = dialog.findViewById(R.id.btn_confirm);
        TextView cancel = dialog.findViewById(R.id.btn_cancel);
        TextView txtMsg = dialog.findViewById(R.id.txt_msg);
        txtMsg.setText("Are you sure,\nyou want to remove " + resName + "?");

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFavourites(pos, resId, userId);
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }



    public void addFavourites(final int position, String id, String userID) {

        AddFavouritesInterface apiInterface = ApiClient.getClient(mContext).create(AddFavouritesInterface.class);
        final AddFavouristeResquest request = new AddFavouristeResquest();
        request.setUserId(userID);
        request.setEntityId(id);
        request.setEntityType("restaurant");
        Call<AddFavouristeResponse> call3 = apiInterface.mAddFavourites(request);
        call3.enqueue(new Callback<AddFavouristeResponse>() {
            @Override
            public void onResponse(Call<AddFavouristeResponse> call, Response<AddFavouristeResponse> response) {
                try {
                    if (response.body().getSuccess()) {
                        listFavourites.remove(position);
                        mFavouritesAdapter.notifyItemChanged(position);
                        mFavouritesAdapter.notifyItemRangeChanged(position, listFavourites.size());
                        mFavouritesAdapter.notifyDataSetChanged();
                        if (listFavourites.size() == 0) {
                            onRefresh();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AddFavouristeResponse> call, Throwable t) {
            }
        });
    }
}
