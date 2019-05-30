package com.lexxdigital.easyfooduserapp.fragments;


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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.adapters.DealAdapter;
import com.lexxdigital.easyfooduserapp.adapters.DealCardAdapter;
import com.lexxdigital.easyfooduserapp.adapters.FilterByCuisinerAdapter;
import com.lexxdigital.easyfooduserapp.adapters.FilterByOfferAdapter;
import com.lexxdigital.easyfooduserapp.adapters.FilterSortByAdapter;
import com.lexxdigital.easyfooduserapp.adapters.RecyclerLayoutManager;
import com.lexxdigital.easyfooduserapp.api.FilterSortInterface;
import com.lexxdigital.easyfooduserapp.api.OnBottomReachedListener;
import com.lexxdigital.easyfooduserapp.api.RestaurantsDealsInterface;
import com.lexxdigital.easyfooduserapp.dashboard.DashboardActivity;
import com.lexxdigital.easyfooduserapp.model.filter_request.FilterSortRequest;
import com.lexxdigital.easyfooduserapp.model.filter_response.Cuisine;
import com.lexxdigital.easyfooduserapp.model.filter_response.FilterSortResponse;
import com.lexxdigital.easyfooduserapp.model.filter_response.Offer;
import com.lexxdigital.easyfooduserapp.model.filter_response.SortBy;
import com.lexxdigital.easyfooduserapp.model.landing_page_lists.DiscountOffer;
import com.lexxdigital.easyfooduserapp.model.landing_page_lists.LandingPageLists;
import com.lexxdigital.easyfooduserapp.model.landing_page_lists.RestaurantTiming;
import com.lexxdigital.easyfooduserapp.model.landing_page_lists.RestaurantsGallery;
import com.lexxdigital.easyfooduserapp.model.landing_page_request.RestaurantsDealRequest;
import com.lexxdigital.easyfooduserapp.model.landing_page_response.RestaurantsDealResponse;
import com.lexxdigital.easyfooduserapp.search_post_code.SearchPostCodeActivity;
import com.lexxdigital.easyfooduserapp.utility.ApiClient;
import com.lexxdigital.easyfooduserapp.utility.Constants;
import com.lexxdigital.easyfooduserapp.utility.GlobalValues;

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

/**
 */
@SuppressLint("ValidFragment")
public class DealsFragment extends Fragment implements FilterSortByAdapter.PositionSortInterface, /*SwipeRefreshLayout.OnRefreshListener,*/ FilterByOfferAdapter.PositionByOfferInterface, FilterByCuisinerAdapter.PositionInterface {
    @BindView(R.id.restaurant_list)
    RecyclerView restaurantList;
    Unbinder unbinder;
    @BindView(R.id.edit_search)
    EditText editSearch;
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
    private boolean isLoaded = false;
    boolean isLoading = false;
    private TextView txtToolbarTitle;
    private String postCode = "";
    int limit = 50, offset = 0;
    String sortedByValue = "", filterOfferValue = "", strCuisineval = "all";
    List<SortBy> sortByList;
    List<LandingPageLists> listRestaurants = new ArrayList<>();
    List<RestaurantsGallery> restaurantsGallery;
    List<Object> restaurantDeliveryCharge;
    List<DiscountOffer> discountOffers;
    List<RestaurantTiming> restaurantTiming;
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
    RestaurantsDealResponse.Data data;
    ImageView clear;
    RecyclerLayoutManager layoutManager;
    //*******************************************
    static DealsFragment _dealsFragment;

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
        restFilter.add("dine_in");
        restFilter.add("collection");

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


        swipreferesh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mDealAdapter != null) {
                    mDealAdapter.notifyDataSetChanged();
                    int limitref = 50;

                    if (Constants.isInternetConnectionAvailable(300)) {
                        swipreferesh.setRefreshing(true);
                        editSearch.setText("");
                        getDeals(val.getPostCode(), limitref, offset, restFilter, sortedByValue, filterOfferValue);


                    } else {
                        swipreferesh.setRefreshing(false);
                        dialogNoInternetConnection("Please check internet connection.");
                    }

                    //  getDeals(val.getPostCode(), limit, offset, restFilter, sortedByValue, filterOfferValue);
                }
            }
        });


        /*swipreferesh.setOnRefreshListener(this);
        swipreferesh.setColorSchemeResources(R.color.orange,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipreferesh.post(new Runnable() {
            @Override
            public void run()
            {
                if (data!=null)
                data.getRestaurants().clear();
                restaurantList.removeAllViews();
                getDeals(val.getPostCode(), limit, offset, restFilter, sortedByValue, filterOfferValue);
            }
        });*/


//        restaurantList.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//
//                if (!isLoading) {
//                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == listRestaurants.size() - 1) {
//                        int offset;
//                        offset = linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1;
//                    //    Log.e("OFFSET","//"+linearLayoutManager.findLastCompletelyVisibleItemPosition());
//                        getDealsLazyLoad(val.getPostCode(), limit, offset, restFilter, sortedByValue, filterOfferValue);
//                        isLoading = true;
//                    }
//                }
//            }
//        });

        try {
            if (val.getPostCode() != null) {
                if (Constants.isInternetConnectionAvailable(300)) {
                    dialog.show();
                    getDeals(val.getPostCode(), limit, offset, restFilter, sortedByValue, filterOfferValue);
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

       /* @SuppressLint("WrongConstant")
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        restaurantList.setLayoutManager(horizontalLayoutManagaer);
        restaurantList.setNestedScrollingEnabled(false);
        mDealAdapter = new DealAdapter(getContext(), mPositionInterface, data.getRestaurants(), val.getLoginResponse().getData().getUserId(),(DashboardActivity)getActivity());
        restaurantList.setAdapter(mDealAdapter);
        mDealAdapter.notifyDataSetChanged();*/

        layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
        layoutManager.setScrollEnabled(false);
        restaurantList.setLayoutManager(layoutManager);
        restaurantList.setNestedScrollingEnabled(false);
        mDealAdapter = new DealAdapter(getContext(), mPositionInterface, data.getRestaurants(), val.getLoginResponse().getData().getUserId(), (DashboardActivity) getActivity());
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
                    getDealsLazyLoad(val.getPostCode(), limit, offset, restFilter, sortedByValue, filterOfferValue);

                }

            }
        });

        if (data.getTotalRecords() == 1) {
            restaurauntCount.setText(data.getTotalRecords() + " Restaurant delivering to");
        } else if (data.getTotalRecords() > 1) {
            restaurauntCount.setText(data.getTotalRecords() + " Restaurants delivering to");
        }


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

                                                          if (mDealAdapter.getItemCount() > 1) {
                                                              restaurauntCount.setText(mDealAdapter.getItemCount() + " Restaurants delivering to");
                                                          } else {
                                                              restaurauntCount.setText(mDealAdapter.getItemCount() + " Restaurant delivering to");
                                                          }



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
        //Gson gson = new Gson();
//        String json = gson.toJson(request); //convert
//        //   System.out.println(json);
//        Log.e("JSON 2244>>", "" + json);
        //logLargeString(json);

        Call<RestaurantsDealResponse> call3 = apiInterface.mLogin(request);

        call3.enqueue(new Callback<RestaurantsDealResponse>() {
            @Override
            public void onResponse(Call<RestaurantsDealResponse> call, Response<RestaurantsDealResponse> response) {
                try {

                    if (response.body().getSuccess()) {

                        data = response.body().getData();
                        Log.e(TAG, "onResponse: datasize>>> " + data.getRestaurants().size());
                        if (data.getRestaurants().size() > 0) {
                            initView();
                            oopsLayout.setVisibility(View.GONE);
                            restaurantList.setVisibility(View.VISIBLE);
                        } else {
                            oopsLayout.setVisibility(View.VISIBLE);
                            restaurantList.setVisibility(View.GONE);
                        }




                       /* for (int i = 0; i < response.body().getData().getRestaurants().size(); i++) {
                            restaurantDeliveryCharge = new ArrayList<>();
                            restaurantsGallery = new ArrayList<>();
                            discountOffers = new ArrayList<>();
                            restaurantTiming = new ArrayList<>();
                            for (int j = 0; j < response.body().getData().getRestaurants().get(i).getRestaurantsGallery().size(); j++) {
                                restaurantsGallery.add(new RestaurantsGallery(response.body().getData().getRestaurants().get(i).getRestaurantsGallery().get(j).getFilePath(), response.body().getData().getRestaurants().get(i).getRestaurantsGallery().get(j).getFileName(), response.body().getData().getRestaurants().get(i).getRestaurantsGallery().get(j).getRestaurantId()));
                            }
                            for (int k = 0; k < response.body().getData().getRestaurants().get(i).getDiscountOffers().size(); k++) {
                                discountOffers.add(new DiscountOffer(response.body().getData().getRestaurants().get(i).getDiscountOffers().get(k).getOfferId(), response.body().getData().getRestaurants().get(i).getDiscountOffers().get(k).getRestaurantId(), response.body().getData().getRestaurants().get(i).getDiscountOffers().get(k).getOfferType(), response.body().getData().getRestaurants().get(i).getDiscountOffers().get(k).getOfferTitle(), response.body().getData().getRestaurants().get(i).getDiscountOffers().get(k).getDetail(), response.body().getData().getRestaurants().get(i).getDiscountOffers().get(k).getOfferPrice()));
                            }
                            for (int l = 0; l < response.body().getData().getRestaurants().get(i).getRestaurantTiming().size(); l++) {
                                restaurantTiming.add(new RestaurantTiming(response.body().getData().getRestaurants().get(i).getRestaurantTiming().get(l).getId(), response.body().getData().getRestaurants().get(i).getRestaurantTiming().get(l).getRestaurantId(), response.body().getData().getRestaurants().get(i).getRestaurantTiming().get(l).getDay(), response.body().getData().getRestaurants().get(i).getRestaurantTiming().get(l).getOpeningStartTime(), response.body().getData().getRestaurants().get(i).getRestaurantTiming().get(l).getOpeningEndTime(), response.body().getData().getRestaurants().get(i).getRestaurantTiming().get(l).getCollectionStartTime(), response.body().getData().getRestaurants().get(i).getRestaurantTiming().get(l).getCollectionEndTime(), response.body().getData().getRestaurants().get(i).getRestaurantTiming().get(l).getDeliveryStartTime(), response.body().getData().getRestaurants().get(i).getRestaurantTiming().get(l).getDeliveryEndTime(), response.body().getData().getRestaurants().get(i).getRestaurantTiming().get(l).getStatus()));
                            }
                            listRestaurants.add(new LandingPageLists(response.body().getData().getRestaurants().get(i).getId(), response.body().getData().getRestaurants().get(i).getRestaurantName(), response.body().getData().getRestaurants().get(i).getLogo(), response.body().getData().getRestaurants().get(i).getRestaurantName(), response.body().getData().getRestaurants().get(i).getAvgDeliveryTime(), response.body().getData().getRestaurants().get(i).getAvgDeliveryTime(), response.body().getData().getRestaurants().get(i).getAvgDeliveryTime(), response.body().getData().getRestaurants().get(i).getCuisines(), response.body().getData().getRestaurants().get(i).getAvgDeliveryTime(), response.body().getData().getRestaurants().get(i).getAvgDeliveryTime(), response.body().getData().getRestaurants().get(i).getMin_order_value(), response.body().getData().getRestaurants().get(i).getAvgDeliveryTime(), response.body().getData().getRestaurants().get(i).getDelivery_charge(), response.body().getData().getRestaurants().get(i).getAvgDeliveryTime(), response.body().getData().getRestaurants().get(i).getAvgDeliveryTime(), response.body().getData().getRestaurants().get(i).getOverallRating(), response.body().getData().getRestaurants().get(i).getFavourite(), restaurantsGallery, restaurantDeliveryCharge, discountOffers, restaurantTiming));
                        }*/

                        if (response.body().getData().getTotalRecords() == 1) {
                            restaurauntCount.setText(response.body().getData().getTotalRecords() + " Restaurant delivering to");
                        } else if (response.body().getData().getTotalRecords() > 1) {
                            restaurauntCount.setText(response.body().getData().getTotalRecords() + " Restaurants delivering to");
                        }

                        mDealAdapter.notifyDataSetChanged();
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
                       /* if (data.getRestaurants().size() > 0) {
                            restaurantList.setVisibility(View.VISIBLE);
                            oopsLayout.setVisibility(View.GONE);
                        } else {
                            restaurantList.setVisibility(View.GONE);
                            oopsLayout.setVisibility(View.VISIBLE);
                        }*/

                    }
                    // swipreferesh.setRefreshing(false);
                } catch (Exception e) {

                    //swipreferesh.setRefreshing(false);

                }
            }

            @Override
            public void onFailure(Call<RestaurantsDealResponse> call, Throwable t) {
                dialog.hide();
                //swipreferesh.setRefreshing(false);
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
        final Boolean filter[] = new Boolean[]{true, true, true};
        //TODO Restaurant filter

        final LinearLayout lldelivery = mDialogView.findViewById(R.id.lldeivery);
        final LinearLayout lldinin = mDialogView.findViewById(R.id.lldinein);
        final LinearLayout llcollection = mDialogView.findViewById(R.id.llcollection);
        final ImageView delivering = mDialogView.findViewById(R.id.delivering);
        final ImageView dine_in = mDialogView.findViewById(R.id.dine_in);
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
        LinearLayoutManager linearLayoutManageroffer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
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
                filter[2] = true;
                llcollection.setTag("enable");

            } else {
                collectionl.setVisibility(View.GONE);
                not_collectionl.setVisibility(View.VISIBLE);
                filter[2] = false;
                isCollectionSelected = false;
                llcollection.setTag("disable");
            }
            if (isDineSelected) {
                dine_in.setVisibility(View.VISIBLE);
                not_dine_in.setVisibility(View.GONE);
                filter[1] = true;
                isDineSelected = true;
                lldinin.setTag("enable");
            } else {
                dine_in.setVisibility(View.GONE);
                not_dine_in.setVisibility(View.VISIBLE);
                filter[1] = false;
                isDineSelected = false;
                lldinin.setTag("disable");
            }
        }
        isFirstOpen = true;

        lldelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!llcollection.getTag().equals("enable") && !lldinin.getTag().equals("enable")) {
                    //Nothing will change
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

                if (!lldelivery.getTag().equals("enable") && !lldinin.getTag().equals("enable")) {
                    //Nothing will change
                } else {
                    if (llcollection.getTag().equals("enable")) {
                        collectionl.setVisibility(View.GONE);
                        not_collectionl.setVisibility(View.VISIBLE);
                        filter[2] = false;
                        isCollectionSelected = false;
                        llcollection.setTag("disable");
                    } else {
                        collectionl.setVisibility(View.VISIBLE);
                        not_collectionl.setVisibility(View.GONE);
                        isCollectionSelected = true;
                        filter[2] = true;
                        llcollection.setTag("enable");
                    }
                }

            }
        });
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


//        getFilters(val.getPostCode());
        mDialogView.findViewById(R.id.apply_filter_btn_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayCuisine = filterByCuisinerAdapter.getCuisineArray();
                if (arrayCuisine.contains("all")) {
                    arrayCuisine.clear();
                    arrayCuisine.add("all");
                }
                for (int i = 0; i < arrayCuisine.size(); i++) {
                    Log.e(TAG, "onClickcising listinggggggg: " + arrayCuisine.get(i));
                }
                int limitfilt = 50;
                listRestaurants.clear();
                mDealAdapter.notifyDataSetChanged();
                restaurantList.removeAllViews();

                List<String> temp = new ArrayList<>();
                for (int i = 0; i < restFilter.size(); i++) {
                    if (filter[i])
                        temp.add(restFilter.get(i));
                }

                if (Constants.isInternetConnectionAvailable(300)) {
                    getDeals(val.getPostCode(), limitfilt, offset, temp, sortedByValue, filterOfferValue);
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
                        sortByList = response.body().getData().getSortBy();
                        filterByList = response.body().getData().getFilterBy().getOffers();
                        cuisineList = response.body().getData().getFilterBy().getCuisine();
                        for (int i = 0; i < response.body().getData().getSortBy().size(); i++) {
                            if (i == 0) {
                                checksort.add("1");
                            } else
                                checksort.add("0");
                        }
//                        sortAdapter = new FilterSortByAdapter(getActivity(), sortByList, checksort, positionSortInterface);
//                        @SuppressLint("WrongConstant")
//                        LinearLayoutManager linearLayoutManager
//                                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//                        sortList.setLayoutManager(linearLayoutManager);
//                        sortList.setAdapter(sortAdapter);

                        for (int i = 0; i < response.body().getData().getFilterBy().getOffers().size(); i++) {
                            if (i == 0) {
                                checkOffer.add("1");
                            } else
                                checkOffer.add("0");
                        }

//                        filterByOfferAdapter = new FilterByOfferAdapter(getActivity(), checkOffer, filterByList, positionByOfferInterface);
//                        @SuppressLint("WrongConstant")
//                        LinearLayoutManager linearLayoutManageroffer
//                                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//                        sortListByOffer.setLayoutManager(linearLayoutManageroffer);
//                        sortListByOffer.setAdapter(filterByOfferAdapter);

                        for (int i = 0; i < response.body().getData().getFilterBy().getCuisine().size(); i++) {

                            if (i == 0)
                                checkCuisine.add("1");
                            else
                                checkCuisine.add("0");
                        }
//                        filterByCuisinerAdapter = new FilterByCuisinerAdapter(getActivity(), checkCuisine, cuisineList, positionInterfaceCoisine);
//                        @SuppressLint("WrongConstant")
//                        LinearLayoutManager linearLayoutManagercuisine
//                                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//                        sortListCousin.setLayoutManager(linearLayoutManagercuisine);
//                        sortListCousin.setAdapter(filterByCuisinerAdapter);

                    }
                } catch (Exception e) {
                    Log.e("Error11 <>>>", ">>>>>" + e.getMessage());
                    //    showDialog("Please try again.");
//                       Toast.makeText(LoginActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilterSortResponse> call, Throwable t) {
                Log.e("Error12 <>>>", ">>>>>" + t.getMessage());
                dialog.hide();
//                showDialog("Please try again.");
                //    Toast.makeText(LoginActivity.this, "Please try again 2."+t.getMessage(), Toast.LENGTH_SHORT).show();
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

        // sortedByValue=sBy.getValue();
        Log.e("", "onClickSortBy: sBy.getValue()" + sortedByValue);
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
        Log.e("", "onClickOffervalue: " + filterOfferValue);

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


   /* @Override
    public void onRefresh() {
        listRestaurants.clear();
        restaurantList.removeAllViews();
        Log.e(TAG, "run: list size:  " + listRestaurants.size());
        mDealAdapter.notifyDataSetChanged();
        int limitref = 2;
        getDeals(val.getPostCode(), limitref, offset, restFilter, sortedByValue, filterOfferValue);
    }*/

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
