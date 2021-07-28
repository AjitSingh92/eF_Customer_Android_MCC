package com.easyfoodcustomer.fragments;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easyfoodcustomer.ui_new.interfaces.NotifyMenuListOnFeagmentInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.easyfoodcustomer.R;
import com.easyfoodcustomer.adapters.RecyclerLayoutManager;
import com.easyfoodcustomer.adapters.menu_adapter.ItemClickListener;
import com.easyfoodcustomer.adapters.menu_adapter.RestaurantMenuListAdapter;
import com.easyfoodcustomer.cart_db.DatabaseHelper;
import com.easyfoodcustomer.restaurant_details.model.response.RestaurantDetailsResponse;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.Menu;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.RestaurantMenuList;
import com.easyfoodcustomer.viewmodel.MenuProductViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class MenuFragment extends Fragment implements NotifyMenuListOnFeagmentInterface {
    static MenuFragment menuFragment;
    RestaurantDetailsResponse respose;
    Context mContext;
    Activity mActivity;
    Menu DATA;
    ItemClickListener menuItemClickListener;
    RecyclerLayoutManager layoutManager;
    @BindView(R.id.list_restaurantMenuList)
    RecyclerView mainMenu;
    RestaurantMenuListAdapter mMenuAdapter;
    Gson gson = new Gson();
    private DatabaseHelper db;
    MenuProductViewModel menuProductViewModel;
    FirebaseAnalytics mFirebaseAnalytics;
    private boolean isClosed;

    public static NotifyMenuListOnFeagmentInterface notifyMenuListOnFeagmentInterface;

    public static MenuFragment newInstance(Activity activity, Context context, Menu restaurantMenuData, ItemClickListener menuItemClickListener, boolean isClosed) {
        MenuFragment fragment = new MenuFragment();
        fragment.mActivity = activity;
        fragment.menuItemClickListener = menuItemClickListener;
        fragment.mContext = context;
        fragment.DATA = restaurantMenuData;
        fragment.isClosed = isClosed;
        return fragment;
    }


    @SuppressLint("ValidFragment")
    public MenuFragment() {
        // Required empty public constructor

    }

    @Override
    public void onStop() {
        super.onStop();
        notifyMenuListOnFeagmentInterface=null;
    }

    @Override
    public void onStart() {
        super.onStart();
        notifyMenuListOnFeagmentInterface=this;
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
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
        notifyMenuListOnFeagmentInterface=this;
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


    }

    private void initMenu() {
        layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
        layoutManager.setScrollEnabled(false);
        mainMenu.setLayoutManager(layoutManager);
        mainMenu.setNestedScrollingEnabled(false);
        mMenuAdapter = new RestaurantMenuListAdapter(mActivity, mContext, menuProductViewModel, menuItemClickListener, isClosed);
        mainMenu.setAdapter(mMenuAdapter);

        if (DATA != null) {
            if (DATA.getSpecialOffers() != null && DATA.getSpecialOffers().size() > 0) {
                mMenuAdapter.addItem(new RestaurantMenuList(DATA.getSpecialOffers(), null));
            }

            if (DATA.getMenuCategory() != null && DATA.getMenuCategory().size() > 0) {
                for (MenuCategory item : DATA.getMenuCategory()) {
                    mMenuAdapter.addItem(new RestaurantMenuList(null, item));
                }
            }
        }

    }

    public void menuAdapterNotifyItem(int position) {
        mainMenu.smoothScrollToPosition(0);
        mMenuAdapter.notifyItemChanged(position);

    }

    @Override
    public void onNotify() {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {

                if (mMenuAdapter!=null)
                {
                    mMenuAdapter.notifyDataSetChanged();
                }

            }
        });

    }
}