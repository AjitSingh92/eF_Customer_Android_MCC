package com.lexxdigital.easyfooduserapp.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.adapters.FilterByCuisinerAdapter;
import com.lexxdigital.easyfooduserapp.adapters.FilterByOfferAdapter;
import com.lexxdigital.easyfooduserapp.adapters.FilterSortByAdapter;
import com.lexxdigital.easyfooduserapp.api.FilterSortInterface;
import com.lexxdigital.easyfooduserapp.model.filter_request.FilterSortRequest;
import com.lexxdigital.easyfooduserapp.model.filter_response.Cuisine;
import com.lexxdigital.easyfooduserapp.model.filter_response.FilterSortResponse;
import com.lexxdigital.easyfooduserapp.model.filter_response.Offer;
import com.lexxdigital.easyfooduserapp.model.filter_response.SortBy;
import com.lexxdigital.easyfooduserapp.model.landing_page_lists.LandingPageLists;
import com.lexxdigital.easyfooduserapp.utility.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterDialog extends Dialog
{

    FilterSortByAdapter sortAdapter;
    FilterByOfferAdapter filterByOfferAdapter;
    FilterByCuisinerAdapter filterByCuisinerAdapter;
    RecyclerView sortList, sortListByOffer, sortListCousin;
    List<SortBy> sortByList;
    List<Offer> filterByList;
    List<Cuisine> cuisineList;
    ArrayList<String> checksort = new ArrayList<>();
    ArrayList<String> checkOffer = new ArrayList<>();
    ArrayList<String> checkCuisine = new ArrayList<>();
    ArrayList<String> arrayCuisine =new ArrayList<>();
    private FilterSortByAdapter.PositionSortInterface positionSortInterface;
    private FilterByOfferAdapter.PositionByOfferInterface positionByOfferInterface;
    private FilterByCuisinerAdapter.PositionInterface positionInterfaceCoisine;



    interface  OnFilterClickListener{
        void onFilerClick();
    }






    Context context;
    public FilterDialog(Context context,FilterSortByAdapter.PositionSortInterface positionSortInterface,
                        FilterByOfferAdapter.PositionByOfferInterface positionByOfferInterface,FilterByCuisinerAdapter.PositionInterface positionInterfaceCoisine)
    {
        super(context);
        this.context =context;
        this.positionSortInterface = positionSortInterface;
        this.positionByOfferInterface = positionByOfferInterface;
        this.positionInterfaceCoisine = positionInterfaceCoisine;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        setContentView(R.layout.popup_filter);




        sortList = findViewById(R.id.list_sort_by);
        sortListCousin = findViewById(R.id.list_by_cuisins);
        sortListByOffer = findViewById(R.id.list_by_offers);


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
//                    dialog.hide();
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
                        sortAdapter = new FilterSortByAdapter(context, sortByList, checksort,positionSortInterface);
                        @SuppressLint("WrongConstant")
                        LinearLayoutManager linearLayoutManager
                                = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                        sortList.setLayoutManager(linearLayoutManager);
                        sortList.setAdapter(sortAdapter);

                        for (int i = 0; i < response.body().getData().getFilterBy().getOffers().size(); i++) {
                            if (i == 0) {
                                checkOffer.add("1");
                            } else
                                checkOffer.add("0");
                        }

                        filterByOfferAdapter = new FilterByOfferAdapter(context, checkOffer, filterByList, positionByOfferInterface);
                        @SuppressLint("WrongConstant")
                        LinearLayoutManager linearLayoutManageroffer
                                = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                        sortListByOffer.setLayoutManager(linearLayoutManageroffer);
                        sortListByOffer.setAdapter(filterByOfferAdapter);

                        for (int i = 0; i < response.body().getData().getFilterBy().getCuisine().size(); i++) {

                            if (i == 0)
                                checkCuisine.add("1");
                            else
                                checkCuisine.add("0");
                        }
                        filterByCuisinerAdapter = new FilterByCuisinerAdapter(context, checkCuisine, cuisineList, positionInterfaceCoisine);
                        @SuppressLint("WrongConstant")
                        LinearLayoutManager linearLayoutManagercuisine
                                = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                        sortListCousin.setLayoutManager(linearLayoutManagercuisine);
                        sortListCousin.setAdapter(filterByCuisinerAdapter);

                    }
                } catch (Exception e) {
//                    dialog.hide();

                    Log.e("Error11 <>>>", ">>>>>" + e.getMessage());
                    //    showDialog("Please try again.");
//                       Toast.makeText(LoginActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilterSortResponse> call, Throwable t) {
                Log.e("Error12 <>>>", ">>>>>" + t.getMessage());
//                dialog.hide();
//                showDialog("Please try again.");
                //    Toast.makeText(LoginActivity.this, "Please try again 2."+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
