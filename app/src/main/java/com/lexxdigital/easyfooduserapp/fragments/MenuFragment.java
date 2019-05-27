package com.lexxdigital.easyfooduserapp.fragments;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.adapters.ExpandableAdapterRecycleview;
import com.lexxdigital.easyfooduserapp.adapters.RecyclerLayoutManager;
import com.lexxdigital.easyfooduserapp.adapters.menu_adapter.ItemClickListener;
import com.lexxdigital.easyfooduserapp.adapters.menu_adapter.RestaurantMenuListAdapter;
import com.lexxdigital.easyfooduserapp.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.menu_category.FinalMenuCartDetails;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.response.RestaurantDetailsResponse;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.RestaurantMenuResponse;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.Menu;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.RestaurantMenuList;
import com.lexxdigital.easyfooduserapp.viewmodel.MenuProductViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class MenuFragment extends Fragment {
    static MenuFragment menuFragment;
    //   SpicalOfferAdapter mRoomOrderAdapter;
//    @BindView(R.id.dropdownImg)
//    ImageView dropdownImg;
    ExpandableAdapterRecycleview listAdapter;
    // ExpandableListAdapter listAdapter;
    List<Object> finalMenuCartDetails = new ArrayList<Object>();
    List<String> listDataHeader;
    HashMap<String, List<Object>> listDataChild;
    Boolean isTrue = true;
    String position;
    RestaurantDetailsResponse respose;

    TextView footerDetails;

    LinearLayout llBotom;
    TextView footerCount;
    Context mContext;
    Activity mActivity;
    FinalMenuCartDetails fCart;
    TextView emptyBasket;
//    @BindView(R.id.infoList)
//    NonScrollExpandableListView infoList;

    Menu DATA;

    ItemClickListener menuItemClickListener;
    RecyclerLayoutManager layoutManager;
    @BindView(R.id.list_restaurantMenuList)
    RecyclerView mainMenu;
    RestaurantMenuListAdapter mMenuAdapter;
    Gson gson = new Gson();
    private DatabaseHelper db;
    MenuProductViewModel menuProductViewModel;

    public static MenuFragment newInstance(Activity activity, Context context, Menu restaurantMenuData, ItemClickListener menuItemClickListener) {
        MenuFragment fragment = new MenuFragment();
        fragment.mActivity = activity;
        fragment.menuItemClickListener = menuItemClickListener;
        fragment.mContext = context;
        fragment.DATA = restaurantMenuData;
        return fragment;
    }

    /*@SuppressLint("ValidFragment")
    public MenuFragment(Activity activity, Context context, RestaurantDetailsResponse res, TextView footer, LinearLayout llbotom, TextView fCount, TextView txtEmptyBasket) {
        // Required empty public constructor
        this.respose = res;
        this.footerDetails = footer;
        this.llBotom = llbotom;
        this.footerCount = fCount;
        this.mContext = context;
        this.mActivity = activity;
        this.emptyBasket = txtEmptyBasket;
    }*/
    @SuppressLint("ValidFragment")
    public MenuFragment() {
        // Required empty public constructor

    }

    public static MenuFragment getMenuFragment() {
        return menuFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menuFragment = this;
        db = new DatabaseHelper(mContext);
        menuProductViewModel = ViewModelProviders.of(this).get(MenuProductViewModel.class);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.bind(this, view);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMenu();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {

        //    infoList.setVisibility(View.VISIBLE);
        //    infoList.setNestedScrollingEnabled(true);
        // listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        //       infoList.setAdapter(listAdapter);

//        mRoomOrderAdapter = new SpicalOfferAdapter();
//
//        @SuppressLint("WrongConstant")
//        LinearLayoutManager horizontalLayoutManagaer
//                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        infoList.setLayoutManager(horizontalLayoutManagaer);
//        infoList.setAdapter(mRoomOrderAdapter);

    }

    private void initMenu() {
        layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
//        layoutManager.setScrollEnabled(false);
        mainMenu.setLayoutManager(layoutManager);

        mainMenu.setNestedScrollingEnabled(true);
        mMenuAdapter = new RestaurantMenuListAdapter(mActivity, mContext, menuProductViewModel, menuItemClickListener);
        mainMenu.setAdapter(mMenuAdapter);


        if (DATA.getSpecialOffers() != null && DATA.getSpecialOffers().size() > 0) {
            mMenuAdapter.addItem(new RestaurantMenuList(DATA.getSpecialOffers(), null));
        }

        if (DATA.getMenuCategory() != null && DATA.getMenuCategory().size() > 0) {
            for (MenuCategory item : DATA.getMenuCategory()) {
                mMenuAdapter.addItem(new RestaurantMenuList(null, item));
            }
        }
    }

    public void menuAdapterNotifyItem(int position) {
        mMenuAdapter.notifyItemChanged(position);
    }

}