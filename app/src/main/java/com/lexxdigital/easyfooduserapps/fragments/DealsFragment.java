package com.lexxdigital.easyfooduserapps.fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.adapters.DealAdapter;
import com.lexxdigital.easyfooduserapps.adapters.DealCardAdapter;
import com.lexxdigital.easyfooduserapps.adapters.FilterByCuisinerAdapter;
import com.lexxdigital.easyfooduserapps.adapters.FilterByOfferAdapter;
import com.lexxdigital.easyfooduserapps.adapters.FilterSortByAdapter;
import com.lexxdigital.easyfooduserapps.adapters.RecyclerLayoutManager;
import com.lexxdigital.easyfooduserapps.api.FilterSortInterface;
import com.lexxdigital.easyfooduserapps.api.OnBottomReachedListener;
import com.lexxdigital.easyfooduserapps.api.RestaurantsDealsInterface;
import com.lexxdigital.easyfooduserapps.dashboard.DashboardActivity;
import com.lexxdigital.easyfooduserapps.model.filter_request.FilterSortRequest;
import com.lexxdigital.easyfooduserapps.model.filter_response.Cuisine;
import com.lexxdigital.easyfooduserapps.model.filter_response.FilterSortResponse;
import com.lexxdigital.easyfooduserapps.model.filter_response.Offer;
import com.lexxdigital.easyfooduserapps.model.filter_response.SortBy;
import com.lexxdigital.easyfooduserapps.model.landing_page_lists.DiscountOffer;
import com.lexxdigital.easyfooduserapps.model.landing_page_lists.LandingPageLists;
import com.lexxdigital.easyfooduserapps.model.landing_page_lists.RestaurantTiming;
import com.lexxdigital.easyfooduserapps.model.landing_page_lists.RestaurantsGallery;
import com.lexxdigital.easyfooduserapps.model.landing_page_request.RestaurantsDealRequest;
import com.lexxdigital.easyfooduserapps.model.landing_page_response.RestaurantsDealResponse;
import com.lexxdigital.easyfooduserapps.search_post_code.SearchPostCodeActivity;
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

import static com.facebook.AccessTokenManager.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 *
 */
@SuppressLint("ValidFragment")
public class DealsFragment extends Fragment implements FilterSortByAdapter.PositionSortInterface, FilterByOfferAdapter.PositionByOfferInterface, FilterByCuisinerAdapter.PositionInterface {
    @BindView(R.id.restaurant_list)
    RecyclerView restaurantList;
    Unbinder unbinder;
    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.nsv_deal)
    NestedScrollView nsvDeal;
    @BindView(R.id.txt_postcode)
    TextView txtPostcode;
    @BindView(R.id.btn_change)
    Button btnChange;
    @BindView(R.id.restauraunt_count)
    TextView restaurauntCount;
    @BindView(R.id.btn_filter)
    Button btnFilter;
    @BindView(R.id.ll_filter)
    LinearLayout llFilter;
    SwipeRefreshLayout swipreferesh;
    @BindView(R.id.oops_layout)
    LinearLayout oopsLayout;
    private Dialog dialog;
    private Handler handler;
    private GlobalValues val;
    private DealAdapter mDealAdapter;
    private DealAdapter.PositionInterface mPositionInterface;
    private FilterSortByAdapter.PositionSortInterface positionSortInterface;
    private FilterByOfferAdapter.PositionByOfferInterface positionByOfferInterface;
    private FilterByCuisinerAdapter.PositionInterface positionInterfaceCoisine;
    private DealCardAdapter mDealCardAdapter;
    private int pageIndex = 0;
    private Context mContext;
    private Activity mActivity;
    boolean isLoading = false;
    private TextView txtToolbarTitle;
    private String postCode = "";
    int limit = 50, offset = 0;
    String sortedByValue = "", filterOfferValue = "";
    List<SortBy> sortByList;
    List<LandingPageLists> listRestaurants = new ArrayList<>();
    List<Offer> filterByList;
    List<Cuisine> cuisineList;
    ArrayList<String> checksort = new ArrayList<>();
    ArrayList<String> checkOffer = new ArrayList<>();
    ArrayList<String> checkCuisine = new ArrayList<>();
    ArrayList<String> cuisinId = new ArrayList<>();
    ArrayList<String> arrayCuisine = new ArrayList<>();
    RestaurantsDealResponse resp;
    FilterSortByAdapter sortAdapter;
    FilterByOfferAdapter filterByOfferAdapter;
    FilterByCuisinerAdapter filterByCuisinerAdapter;
    RecyclerView sortList, sortListByOffer, sortListCousin;
    public static ArrayList<Boolean> isCheck = new ArrayList<>();
    List<String> restFilter;
    List<String> filterRestaurantTyped = new ArrayList<>();
    RestaurantsDealResponse.Data data;
    ImageView clear;
    private Runnable callback;
    RecyclerLayoutManager layoutManager;
    SharedPreferencesClass sharedPreferencesClass;
    //*******************************************
    FirebaseAnalytics mFirebaseAnalytics;
    static DealsFragment _dealsFragment;
    private static DealsFragment instance = null;

    public static DealsFragment getFragment() {
        return _dealsFragment;
    }

    boolean isDeliverySelected = true, isDineSelected = true, isCollectionSelected = true, isFirstOpen = false;

    public DealsFragment() {
    }

    @SuppressLint("ValidFragment")
    public DealsFragment(Context mContext, Activity activity, TextView title, String post) {
        this.mContext = mContext;
        this.mActivity = activity;
        this.txtToolbarTitle = title;
        this.postCode = post;
    }

    public DealsFragment(Context mContext, TextView title, String post) {
        this.mContext = mContext;
        this.txtToolbarTitle = title;
        this.postCode = post;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this._dealsFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getView() != null ? getView() : inflater.inflate(R.layout.fragment_deals, container, false);
        handler = new Handler();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
        instance = this;
        sortByList = new ArrayList<>();
        cuisineList = new ArrayList<>();
        filterByList = new ArrayList<Offer>();
        val = (GlobalValues) mContext;
        positionSortInterface = this;
        positionByOfferInterface = this;
        positionInterfaceCoisine = this;

        ((TextView) getActivity().findViewById(R.id.tvToolbarTitle)).setText("Restaurants");

        restFilter = new ArrayList<>();
        restFilter.add("delivery");
        //restFilter.add("dinein");
        restFilter.add("collection");
        filterRestaurantTyped.addAll(restFilter);
        clear = view.findViewById(R.id.clear);
        swipreferesh = view.findViewById(R.id.swipreferesh);

        unbinder = ButterKnife.bind(this, view);
        txtPostcode.setText(postCode);
        dialog = new Dialog(getActivity());
        dialog.setTitle("");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        arrayCuisine.add("all");

        initView();
        DashboardActivity.getInstance().locationVisibility(false, txtPostcode.getText().toString().trim());
        swipreferesh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mDealAdapter != null) {
                    mDealAdapter.notifyDataSetChanged();


                    if (Constants.isInternetConnectionAvailable(300)) {
                        swipreferesh.setRefreshing(true);
                        editSearch.setText("");
                        getDeals(val.getPostCode(), limit, offset, filterRestaurantTyped, sortedByValue, filterOfferValue);


                    } else {
                        swipreferesh.setRefreshing(false);
                        dialogNoInternetConnection("Please check internet connection.");
                    }
                }
            }
        });

        nsvDeal.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {
                if (i1 > 150) {
                    DashboardActivity.getInstance().locationVisibility(true, txtPostcode.getText().toString().trim());

                } else {
                    DashboardActivity.getInstance().locationVisibility(false, txtPostcode.getText().toString().trim());

                }
            }
        });

        txtPostcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DashboardActivity.getInstance().setLocation(txtPostcode.getText().toString().trim());
            }
        });


        try {
            if (val.getPostCode() != null) {
                if (Constants.isInternetConnectionAvailable(300)) {
                    dialog.show();
                    getDeals(val.getPostCode(), limit, offset, filterRestaurantTyped, sortedByValue, filterOfferValue);
                    getFilters(val.getPostCode());

                } else {
                    dialogNoInternetConnection("Please check internet connection.");
                }

            } else {
                val.setIsFromDealPage(true);
                Intent i = new Intent(mContext, SearchPostCodeActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        } catch (Exception e) {
            Log.e("Exception", e.getLocalizedMessage());
        }

        return view;
    }


    public static DealsFragment getInstance() {
        return instance;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
    }

    @Override
    public void onPause() {
        super.onPause();
        dialog.dismiss();
    }

    private void initView() {
        sharedPreferencesClass = new SharedPreferencesClass(getApplicationContext());
        layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
        layoutManager.setScrollEnabled(false);
        restaurantList.setLayoutManager(layoutManager);
        restaurantList.setNestedScrollingEnabled(false);
        mDealAdapter = new DealAdapter(getContext(), mPositionInterface, sharedPreferencesClass.getString(sharedPreferencesClass.USER_ID), (DashboardActivity) getActivity());

        restaurantList.setAdapter(mDealAdapter);
        mDealAdapter.notifyDataSetChanged();


        mDealAdapter.setOnBottomReachedListener(new OnBottomReachedListener() {
            @Override
            public void onBottomReached(int position) {
                //your code goes here

                if (position == data.getRestaurants().size() - 1) {
                    int offset;
                    offset = position + 1;
                    dialog.show();
                    getDealsLazyLoad(val.getPostCode(), limit, offset, filterRestaurantTyped, sortedByValue, filterOfferValue);

                }

            }
        });

        editSearch.addTextChangedListener(new

                                                  TextWatcher() {
                                                      @Override
                                                      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                      }

                                                      @Override
                                                      public void onTextChanged(CharSequence s, int start, int before, int count) {


                                                      }

                                                      @Override
                                                      public void afterTextChanged(Editable s) {

                                                          mDealAdapter.getFilter().filter(s.toString());

                                                          int SPLASH_TIME_OUT = 300;

                                                          handler.postDelayed(callback = new Runnable() {
                                                              @Override
                                                              public void run() {
                                                                  try {

                                                                      if (mDealAdapter.getItemCount() > 1) {
                                                                          restaurauntCount.setText(mDealAdapter.getItemCount() + " Restaurants delivering to");
                                                                      } else {
                                                                          restaurauntCount.setText(mDealAdapter.getItemCount() + " Restaurant delivering to");
                                                                      }
                                                                  } catch (Exception e) {
                                                                      e.printStackTrace();
                                                                  }
                                                              }
                                                          }, SPLASH_TIME_OUT);


                                                          if (s.toString().equals("")) {
                                                              clear.setVisibility(View.GONE);

                                                          } else {
                                                              clear.setVisibility(View.VISIBLE);
                                                          }
                                                      }
                                                  });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSearch.setText("");
                mDealAdapter.getFilter().filter("");
                clear.setVisibility(View.GONE);
                oopsLayout.setVisibility(View.GONE);
                restaurantList.setVisibility(View.VISIBLE);
            }
        });


    }

    public void updateUi(boolean showOops) {
        if (showOops) {
            oopsLayout.setVisibility(View.VISIBLE);
            restaurantList.setVisibility(View.GONE);
        } else {
            oopsLayout.setVisibility(View.GONE);
            restaurantList.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public void getDeals(String postCode, int limit, int offset, List<String> restFilter, String sortedVal, String offerVal) {

        RestaurantsDealsInterface apiInterface = ApiClient.getClient(getContext()).create(RestaurantsDealsInterface.class);
        RestaurantsDealRequest request = new RestaurantsDealRequest();
        request.setUserId(val.getLoginResponse().getData().getUserId());
        request.setPostCode(postCode);
        request.setLimit(limit);
        request.setOffset(offset);
        request.setSortBy(sortedVal);
        request.setFilterByOffer(offerVal);
        request.setFilterByServeStyle(restFilter);
        request.setFilterByCuisine(arrayCuisine);

        Call<RestaurantsDealResponse> call3 = apiInterface.mLogin(request);

        call3.enqueue(new Callback<RestaurantsDealResponse>() {
            @Override
            public void onResponse(Call<RestaurantsDealResponse> call, Response<RestaurantsDealResponse> response) {
                try {

                    if (response.body().getSuccess()) {

                        data = response.body().getData();
                        if (data.getRestaurants().size() > 0) {
                            mDealAdapter.clearItems();
                            mDealAdapter.addItem(data.getRestaurants());
                            oopsLayout.setVisibility(View.GONE);
                            restaurantList.setVisibility(View.VISIBLE);
                            if (data.getTotalRecords() == 1) {
                                restaurauntCount.setText(data.getTotalRecords() + " Restaurant delivering to");
                            } else if (data.getTotalRecords() > 1) {
                                restaurauntCount.setText(data.getTotalRecords() + " Restaurants delivering to");
                            }

                        } else {
                            restaurauntCount.setText(0 + " Restaurant delivering to");
                            oopsLayout.setVisibility(View.VISIBLE);
                            restaurantList.setVisibility(View.GONE);
                        }
                        if (response.body().getData().getTotalRecords() == 1) {
                            restaurauntCount.setText(response.body().getData().getTotalRecords() + " Restaurant delivering to");
                        } else if (response.body().getData().getTotalRecords() > 1) {
                            restaurauntCount.setText(response.body().getData().getTotalRecords() + " Restaurants delivering to");
                        }

                        isLoading = false;

                        if (swipreferesh != null)
                            swipreferesh.setRefreshing(false);
                        dialog.hide();

                    } else {
                        if (swipreferesh != null)
                            swipreferesh.setRefreshing(false);
                        dialog.hide();
                        restaurauntCount.setText("Unable to load, Swipe down to reload.");
                    }
                } catch (Exception e) {
                    if (swipreferesh != null)
                        swipreferesh.setRefreshing(false);
                    dialog.hide();
                    if (restaurauntCount != null) {
                        restaurauntCount.setText("Restaurants delivering to");
                    }

                }
            }

            @Override
            public void onFailure(Call<RestaurantsDealResponse> call, Throwable t) {
                if (swipreferesh != null)
                    swipreferesh.setRefreshing(false);
                dialog.hide();
                if (restaurauntCount != null) {
                    restaurauntCount.setText("Server not responding");
                }

            }
        });
    }

    public void getDealsLazyLoad(String postCode, int limit, final int offset, List<String> restFilter, String sortedVal, String offerVal) {

        RestaurantsDealsInterface apiInterface = ApiClient.getClient(getContext()).create(RestaurantsDealsInterface.class);
        RestaurantsDealRequest request = new RestaurantsDealRequest();
        request.setUserId(val.getLoginResponse().getData().getUserId());
        request.setPostCode(postCode);
        request.setLimit(limit);
        request.setOffset(offset);
        request.setSortBy(sortedVal);
        request.setFilterByOffer(offerVal);
        request.setFilterByServeStyle(restFilter);
        request.setFilterByCuisine(arrayCuisine);
        Call<RestaurantsDealResponse> call3 = apiInterface.mLogin(request);

        call3.enqueue(new Callback<RestaurantsDealResponse>() {
            @Override
            public void onResponse(Call<RestaurantsDealResponse> call, Response<RestaurantsDealResponse> response) {
                try {

                    dialog.hide();
                    if (response.body().getSuccess()) {
                        data = response.body().getData();
                        mDealAdapter.addLazyLoadedData(data.getRestaurants(), offset);
                    }

                } catch (Exception e) {


                }
            }

            @Override
            public void onFailure(Call<RestaurantsDealResponse> call, Throwable t) {
                dialog.hide();

            }
        });
    }

    public void showDialog(String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(mActivity);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog2, int id) {
                        dialog2.cancel();

                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    public void alertDialogFilter() {
        dialog.show();
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View mDialogView = factory.inflate(R.layout.popup_filter, null);
        final AlertDialog filterDialog = new AlertDialog.Builder(getActivity()).create();
        filterDialog.setView(mDialogView);
        filterDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sortList = (RecyclerView) mDialogView.findViewById(R.id.list_sort_by);
        sortListCousin = (RecyclerView) mDialogView.findViewById(R.id.list_by_cuisins);
        sortListByOffer = (RecyclerView) mDialogView.findViewById(R.id.list_by_offers);
        final Boolean filter[] = new Boolean[]{true, true};
        //TODO Restaurant filter

        final LinearLayout lldelivery = mDialogView.findViewById(R.id.lldeivery);
        // final LinearLayout lldinin = mDialogView.findViewById(R.id.lldinein);
        final LinearLayout llcollection = mDialogView.findViewById(R.id.llcollection);
        final ImageView delivering = mDialogView.findViewById(R.id.delivering);
        // final ImageView dine_in = mDialogView.findViewById(R.id.dine_in);
        final ImageView collectionl = mDialogView.findViewById(R.id.collection);
        final ImageView not_delivering = mDialogView.findViewById(R.id.not_delivery);
        final ImageView not_dine_in = mDialogView.findViewById(R.id.not_dine_in);
        final ImageView not_collectionl = mDialogView.findViewById(R.id.not_collection);
        final LinearLayout restautantType = mDialogView.findViewById(R.id.ll_restaurantType);


        sortAdapter = new FilterSortByAdapter(getActivity(), sortByList, checksort, positionSortInterface);
        @SuppressLint("WrongConstant")
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        sortList.setLayoutManager(linearLayoutManager);
        sortList.setAdapter(sortAdapter);


        filterByOfferAdapter = new FilterByOfferAdapter(getActivity(), checkOffer, filterByList, positionByOfferInterface);
        @SuppressLint("WrongConstant")
        LinearLayoutManager linearLayoutManageroffer = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        sortListByOffer.setLayoutManager(linearLayoutManageroffer);
        sortListByOffer.setAdapter(filterByOfferAdapter);

        filterByCuisinerAdapter = new FilterByCuisinerAdapter(getActivity(), checkCuisine, cuisineList, positionInterfaceCoisine);
        @SuppressLint("WrongConstant")
        LinearLayoutManager linearLayoutManagercuisine
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        sortListCousin.setLayoutManager(linearLayoutManagercuisine);
        sortListCousin.setAdapter(filterByCuisinerAdapter);

        if (cuisineList.size() > 0) {
            dialog.dismiss();
            restautantType.setVisibility(View.VISIBLE);
        } else {
            restautantType.setVisibility(View.GONE);
        }

        if (isFirstOpen) {

            if (isDeliverySelected) {
                delivering.setVisibility(View.VISIBLE);
                not_delivering.setVisibility(View.GONE);
                filter[0] = true;
                isDeliverySelected = true;
                lldelivery.setTag("enable");

            } else {
                delivering.setVisibility(View.GONE);
                not_delivering.setVisibility(View.VISIBLE);
                filter[0] = false;
                isDeliverySelected = false;
                lldelivery.setTag("disable");
            }
            if (isCollectionSelected) {
                collectionl.setVisibility(View.VISIBLE);
                not_collectionl.setVisibility(View.GONE);
                isCollectionSelected = true;
                filter[1] = true;
                llcollection.setTag("enable");

            } else {
                collectionl.setVisibility(View.GONE);
                not_collectionl.setVisibility(View.VISIBLE);
                filter[1] = false;
                isCollectionSelected = false;
                llcollection.setTag("disable");
            }
            if (isDineSelected) {
                //  dine_in.setVisibility(View.VISIBLE);
                //  not_dine_in.setVisibility(View.GONE);
                filter[1] = true;
                isDineSelected = true;
                //  lldinin.setTag("enable");
            } else {
                // dine_in.setVisibility(View.GONE);
                //not_dine_in.setVisibility(View.VISIBLE);
                filter[1] = false;
                isDineSelected = false;
                //  lldinin.setTag("disable");
            }
        }
        isFirstOpen = true;

        lldelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!llcollection.getTag().equals("enable") /*&& !lldinin.getTag().equals("enable")*/) {

                } else {
                    if (lldelivery.getTag().equals("enable")) {
                        delivering.setVisibility(View.GONE);
                        not_delivering.setVisibility(View.VISIBLE);
                        filter[0] = false;
                        isDeliverySelected = false;
                        lldelivery.setTag("disable");
                    } else {
                        delivering.setVisibility(View.VISIBLE);
                        not_delivering.setVisibility(View.GONE);
                        filter[0] = true;
                        isDeliverySelected = true;
                        lldelivery.setTag("enable");
                    }
                }

            }
        });
        llcollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!lldelivery.getTag().equals("enable") /*&& !lldinin.getTag().equals("enable")*/) {
                    //Nothing will change
                } else {
                    if (llcollection.getTag().equals("enable")) {
                        collectionl.setVisibility(View.GONE);
                        not_collectionl.setVisibility(View.VISIBLE);
                        filter[1] = false;
                        isCollectionSelected = false;
                        llcollection.setTag("disable");
                    } else {
                        collectionl.setVisibility(View.VISIBLE);
                        not_collectionl.setVisibility(View.GONE);
                        isCollectionSelected = true;
                        filter[1] = true;
                        llcollection.setTag("enable");
                    }
                }

            }
        });/*
        lldinin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!llcollection.getTag().equals("enable") && !lldelivery.getTag().equals("enable")) {
                    //Nothing will change
                } else {
                    if (lldinin.getTag().equals("enable")) {
                        dine_in.setVisibility(View.GONE);
                        not_dine_in.setVisibility(View.VISIBLE);
                        filter[1] = false;
                        isDineSelected = false;
                        lldinin.setTag("disable");
                    } else {
                        dine_in.setVisibility(View.VISIBLE);
                        not_dine_in.setVisibility(View.GONE);
                        filter[1] = true;
                        isDineSelected = true;
                        lldinin.setTag("enable");
                    }
                }

            }


        });
*/

        mDialogView.findViewById(R.id.apply_filter_btn_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayCuisine = filterByCuisinerAdapter.getCuisineArray();
                if (arrayCuisine.contains("all")) {
                    arrayCuisine.clear();
                    arrayCuisine.add("all");
                }


                listRestaurants.clear();
                mDealAdapter.notifyDataSetChanged();
                restaurantList.removeAllViews();

                filterRestaurantTyped.clear();
                for (int i = 0; i < restFilter.size(); i++) {
                    if (filter[i])
                        filterRestaurantTyped.add(restFilter.get(i));
                }

                if (Constants.isInternetConnectionAvailable(300)) {
                    dialog.show();
                    getDeals(val.getPostCode(), limit, offset, filterRestaurantTyped, sortedByValue, filterOfferValue);
                    filterDialog.dismiss();
                } else {
                    filterDialog.dismiss();
                    dialogNoInternetConnection("Please check internet connection.");
                }

            }
        });

        mDialogView.findViewById(R.id.cross_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDialog.dismiss();
            }
        });


        filterDialog.show();
    }

    public void getFilters(String postCode) {
        FilterSortInterface apiInterface = ApiClient.getClient(getContext()).create(FilterSortInterface.class);
        FilterSortRequest request = new FilterSortRequest();
        request.setPostCode(postCode);

        Call<FilterSortResponse> call3 = apiInterface.mGetFilters(request);
        call3.enqueue(new Callback<FilterSortResponse>() {
            @Override
            public void onResponse(Call<FilterSortResponse> call, Response<FilterSortResponse> response) {
                try {

                    if (response.body().getSuccess()) {
                        /* Todo: Start remove distace */
                        for (int i = 0; i < response.body().getData().getSortBy().size(); i++) {
                            sortByList.add(response.body().getData().getSortBy().get(i));
                        }
                        /* Todo: End remove distance*/
                        filterByList = response.body().getData().getFilterBy().getOffers();
                        cuisineList = response.body().getData().getFilterBy().getCuisine();
                        for (int i = 0; i < response.body().getData().getSortBy().size(); i++) {
                            if (i == 0) {
                                checksort.add("1");
                            } else
                                checksort.add("0");
                        }

                        for (int i = 0; i < response.body().getData().getFilterBy().getOffers().size(); i++) {
                            if (i == 0) {
                                checkOffer.add("1");
                            } else
                                checkOffer.add("0");
                        }


                        for (int i = 0; i < response.body().getData().getFilterBy().getCuisine().size(); i++) {

                            if (i == 0)
                                checkCuisine.add("1");
                            else
                                checkCuisine.add("0");
                        }

                    }
                } catch (Exception e) {
                    Log.e("Error11 <>>>", ">>>>>" + e.getMessage());

                }
            }

            @Override
            public void onFailure(Call<FilterSortResponse> call, Throwable t) {

                dialog.hide();
            }
        });
    }

    @OnClick({R.id.btn_filter, R.id.btn_change, R.id.ll_filter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_filter:
                editSearch.setText("");
                alertDialogFilter();
                break;
            case R.id.ll_filter:
                editSearch.setText("");
                alertDialogFilter();
                break;
            case R.id.btn_change:
                val.setIsFromDealPage(true);
                Intent i = new Intent(mContext, SearchPostCodeActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;
        }
    }

    @Override
    public void onClickSortBy(int pos, ArrayList<String> check, List<SortBy> sortByList) {
        SortBy sBy = sortByList.get(pos);
        if (check.contains("1")) {
            for (int i = 0; i < sortByList.size(); i++) {
                check.set(i, "0");
            }
            check.set(pos, "1");
            sortedByValue = sBy.getValue();
        } else {
            sortedByValue = "";
            check.set(pos, "0");
        }

        try {
            sortAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e(TAG, "onClickSortBy: " + e.getMessage());
        }
    }

    @Override
    public void onClickPosOffer(int pos, ArrayList<String> check, List<Offer> offerList) {
        Offer sBy = offerList.get(pos);
        if (check.contains("1")) {
            for (int i = 0; i < offerList.size(); i++) {
                check.set(i, "0");
            }
            check.set(pos, "1");
            filterOfferValue = sBy.getValue();
        } else {

            filterOfferValue = "";
            check.set(pos, "0");
        }

        try {
            filterByOfferAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e(TAG, "onClickPosOffer: " + e.getMessage());
        }
    }

    @Override
    public void onClickPosCoisine(int pos, ArrayList<String> check, List<Cuisine> cuisineList) {
        checkCuisine = check;
        if (check.get(pos).equalsIgnoreCase("1")) {
            isCheck.set(pos, true);
        } else isCheck.set(pos, false);


    }

    public void dialogNoInternetConnection(String message) {
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View mDialogView = factory.inflate(R.layout.addnote_success_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setView(mDialogView);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        final Animation animShake = AnimationUtils.loadAnimation(mContext, R.anim.shake);

        TextView tvMessage = mDialogView.findViewById(R.id.message);
        tvMessage.setText(message);
        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constants.isInternetConnectionAvailable(300)) {
                    alertDialog.dismiss();
                    startActivity(new Intent(mContext, DashboardActivity.class));
                } else mDialogView.findViewById(R.id.okTv).startAnimation(animShake);

            }
        });

        alertDialog.show();
    }
}
