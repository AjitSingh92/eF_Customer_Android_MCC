package com.lexxdigital.easyfooduserapp.adapters.menu_adapter;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapp.cart_db.tables.MenuProducts;
import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.Menu;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.RestaurantMenuList;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.SpecialOffer;
import com.lexxdigital.easyfooduserapp.utility.GlobalValues;
import com.lexxdigital.easyfooduserapp.viewmodel.MenuProductViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RestaurantMenuListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    MenuProductViewModel menuProductViewModel;
    private List<RestaurantMenuList> mMenuDataItem;
    private Context context;
    ItemClickListener menuItemClickListener;
    int posMinus = 0;
    DatabaseHelper db;
    List<RestaurantCategoryAdapter> restaurantCategoryAdapters;
    Activity activity;
    HashMap<Integer, Boolean> isItemVisible;

    public RestaurantMenuListAdapter(Activity activity, Context context, MenuProductViewModel menuProductViewModel, ItemClickListener menuItemClickListener) {
        this.activity = activity;
        this.context = context;
        this.menuProductViewModel = menuProductViewModel;
        this.menuItemClickListener = menuItemClickListener;
        mMenuDataItem = new ArrayList<>();
        db = new DatabaseHelper(context);
        restaurantCategoryAdapters = new ArrayList<>();
        isItemVisible = new HashMap<>();
//        menuProductViewModel = ViewModelProviders.of((FragmentActivity)context).get(MenuProductViewModel.class);
    }

    public void addItem(List<RestaurantMenuList> mMenuDataItem) {
        this.mMenuDataItem.addAll(mMenuDataItem);
        notifyItemInserted(this.mMenuDataItem.size());
    }

    public void addItem(RestaurantMenuList mMenuDataItem) {
        this.mMenuDataItem.add(mMenuDataItem);
        notifyItemInserted(this.mMenuDataItem.size());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                OfferItemViewHolder currentOrderViewHolder = (OfferItemViewHolder) holder;
                currentOrderViewHolder.bindData(position);
                break;
            case 1:
                MenuItemViewHolder comboProductListingViewHolder = (MenuItemViewHolder) holder;
                comboProductListingViewHolder.bindData(position);
                break;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {
        switch (viewtype) {
            case 0:
                return new OfferItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_parent_child_listing, viewGroup, false));
            case 1:
                return new MenuItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_parent_child_listing, viewGroup, false));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (mMenuDataItem.get(position).getMenuCategories() == null) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return mMenuDataItem.size();
    }

    class OfferItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView catName;
        RecyclerView childItemView;
        private final ImageView dropdownImg;

        public OfferItemViewHolder(@NonNull View itemView) {
            super(itemView);
            catName = itemView.findViewById(R.id.tv_catName);
            dropdownImg = itemView.findViewById(R.id.dropdownImg);
            childItemView = itemView.findViewById(R.id.list_childItemView);
            childItemView.setVisibility(View.GONE);
            posMinus++;
            itemView.setOnClickListener(this);
        }

        private void bindData(int position) {

            catName.setText("Special Offer");
            List<SpecialOffer> specialOffers = mMenuDataItem.get(position).getSpecialOffers();
            RecyclerLayoutManager layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
            layoutManager.setScrollEnabled(false);
            childItemView.setLayoutManager(layoutManager);
            SpecialOffersMenuAdapter specialOffersMenuAdapter = new SpecialOffersMenuAdapter(context, getLayoutPosition(), menuItemClickListener);
            specialOffersMenuAdapter.setHideDetail(false);
            childItemView.setAdapter(specialOffersMenuAdapter);
            specialOffersMenuAdapter.addItem(specialOffers);
        }

        @Override
        public void onClick(View view) {
            if (childItemView.getVisibility() == View.VISIBLE) {
                childItemView.setVisibility(View.GONE);
                dropdownImg.setRotation(270f);
            } else {
                childItemView.setVisibility(View.VISIBLE);
                dropdownImg.setRotation(360f);
            }
        }
    }

    class MenuItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView catName;
        private final ImageView dropdownImg;
        private RecyclerView childItemView;
        ProgressBar progressBar;

        public MenuItemViewHolder(@NonNull View itemView) {
            super(itemView);
            catName = itemView.findViewById(R.id.tv_catName);
            dropdownImg = itemView.findViewById(R.id.dropdownImg);
            progressBar = itemView.findViewById(R.id.progressBar);
            childItemView = itemView.findViewById(R.id.list_childItemView);
            childItemView.setVisibility(View.GONE);
            itemView.setOnClickListener(this);
        }

        private void bindData(int position) {
            final int mPosition = position;
            final MenuCategory dataItem = mMenuDataItem.get(position).getMenuCategories();
            catName.setText(dataItem.getMenuCategoryName());

            RecyclerLayoutManager layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
            layoutManager.setScrollEnabled(false);
            childItemView.setLayoutManager(layoutManager);

            RestaurantCategoryAdapter restaurantCategoryAdapter = new RestaurantCategoryAdapter(context, getLayoutPosition(), menuItemClickListener);
            restaurantCategoryAdapter.setHideDetail(true);
            childItemView.setAdapter(restaurantCategoryAdapter);
            final RestaurantCategoryAdapter mRestaurantCategoryAdapter = restaurantCategoryAdapter;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    MenuProducts.MenuProductsTable menuProducts = GlobalValues.getInstance().getDb().menuProductMaster().getMenuProduct(mMenuDataItem.get(mPosition).getMenuCategories().getMenuCategoryId());
                    if (menuProducts != null) {
                        dataItem.setMenuProducts(menuProducts.getMenuProducts());

                        /*----------------------------------*/
                        if (dataItem.getMenuSubCategory() != null) {
                            for (int i = 0; i < dataItem.getMenuSubCategory().size(); i++) {
                                MenuProducts.MenuProductsTable menuSubCatProducts = GlobalValues.getInstance().getDb().menuProductMaster().getMenuProduct(dataItem.getMenuSubCategory().get(i).getMenuCategoryId());
                                if (menuSubCatProducts != null) {
                                    dataItem.getMenuSubCategory().get(i).setMenuProducts(menuSubCatProducts.getMenuProducts());
                                }
                            }
                        }
                        /*----------------------------------*/

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mRestaurantCategoryAdapter.addItem(dataItem);
                            }
                        });
                    }
                }
            }).start();



           /* menuProductViewModel.getMenuProductList(mMenuDataItem.get(position).getMenuCategories().getMenuCategoryId()).observe((FragmentActivity)context, new Observer<MenuProducts.MenuProductsTable>() {
                @Override
                public void onChanged(@Nullable MenuProducts.MenuProductsTable menuProducts) {
                    dataItem.setMenuProducts(menuProducts.getMenuProducts());
                    RestaurantCategoryAdapter restaurantCategoryAdapter = new RestaurantCategoryAdapter(context, getLayoutPosition(), dataItem, menuItemClickListener);
                    restaurantCategoryAdapter.setHideDetail(true);
                    childItemView.setAdapter(restaurantCategoryAdapter);
                }
            });*/


//            restaurantCategoryAdapter.addItem(dataItem.getMenuProducts());
            progressBar.setVisibility(View.GONE);
            long id = db.getMenuCategoryIfExit(dataItem.getMenuCategoryId());
            if (id != -1) {
                childItemView.setVisibility(View.VISIBLE);
                dropdownImg.setRotation(360f);
            } else {
//                childItemView.setVisibility(View.GONE);
//                dropdownImg.setRotation(270f);

                if (isItemVisible.get(mPosition) != null && isItemVisible.get(mPosition)) {
                    childItemView.setVisibility(View.VISIBLE);
                    dropdownImg.setRotation(360f);
                } else {
                    childItemView.setVisibility(View.GONE);
                    dropdownImg.setRotation(270f);
                }

            }
        }

        @Override
        public void onClick(View view) {
            if (menuItemClickListener != null) {
                menuItemClickListener.LoadMenuProduct(getLayoutPosition(), mMenuDataItem.get(getLayoutPosition()).getMenuCategories().getMenuCategoryId(), progressBar);

                if (childItemView.getVisibility() == View.VISIBLE) {
                    childItemView.setVisibility(View.GONE);
                    dropdownImg.setRotation(270f);
                    isItemVisible.put(getLayoutPosition(), false);
                } else {
                    childItemView.setVisibility(View.VISIBLE);
                    dropdownImg.setRotation(360f);
                    isItemVisible.put(getLayoutPosition(), true);

                }
            }
        }
    }
}