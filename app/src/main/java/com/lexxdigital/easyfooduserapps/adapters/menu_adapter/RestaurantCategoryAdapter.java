package com.lexxdigital.easyfooduserapps.adapters.menu_adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapps.restaurant_details.RestaurantDetailsActivity;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;

import java.util.ArrayList;
import java.util.List;

public class RestaurantCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private List<MenuProduct> mItem;
    private Boolean hideDetail = false;
    private List<MenuCategory> menuSubCategory;
    ItemClickListener menuItemClickListener;
    int parentPosition;
    MenuCategory menuCategory;
    DatabaseHelper db;
    private boolean isClosed;

    public RestaurantCategoryAdapter(Context context, int parentPosition, ItemClickListener menuItemClickListener, boolean isClosed) {
        this.context = context;
        this.menuItemClickListener = menuItemClickListener;
        this.parentPosition = parentPosition;

        inflater = LayoutInflater.from(context);
        menuSubCategory = new ArrayList<>();
        mItem = new ArrayList<>();
        db = new DatabaseHelper(context);
        this.isClosed = isClosed;
    }

    public void setHideDetail(Boolean hideDetail) {
        this.hideDetail = hideDetail;
    }

    public void clearData() {
        this.mItem.clear();
        notifyDataSetChanged();
    }

    public void addItem(MenuCategory menuCategory) {
        this.menuSubCategory.addAll(menuCategory.getMenuSubCategory());
        this.menuCategory = menuCategory;
        this.mItem.addAll(menuCategory.getMenuProducts());
        notifyItemChanged(this.mItem.size());

    }

    public void addItem(List<MenuProduct> mItem) {
        this.mItem.addAll(mItem);
        notifyItemChanged(this.mItem.size());
    }

    public void addItem(MenuProduct mItem) {
        this.mItem.add(mItem);
        notifyItemChanged(this.mItem.size());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                CategoryViewHolder categoryViewHolder = (CategoryViewHolder) holder;
                categoryViewHolder.bindData(position);
                break;
            case 1:
                SubCategoryViewHolder subCategoryViewHolder = (SubCategoryViewHolder) holder;
                subCategoryViewHolder.bindData(position);
                break;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {
        switch (viewtype) {
            case 0:
                return new CategoryViewHolder(inflater.inflate(R.layout.offer_item_list, viewGroup, false));
            case 1:
                return new SubCategoryViewHolder(inflater.inflate(R.layout.product_with_sub_category_view, viewGroup, false));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (menuSubCategory == null || menuSubCategory.size() == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return (menuSubCategory == null || menuSubCategory.size() == 0) ? mItem.size() : 1;
    }


    class SubCategoryViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerView categoryItemView, subCategoryView;

        public SubCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryItemView = itemView.findViewById(R.id.list_productItemView);
            subCategoryView = itemView.findViewById(R.id.list_subCategoryItemView);
        }

        private void bindData(int position) {
            RecyclerLayoutManager layoutManager1 = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
            layoutManager1.setScrollEnabled(false);
            categoryItemView.setLayoutManager(layoutManager1);

            RestaurantCategoryAdapter2 restaurantCategoryAdapter = new RestaurantCategoryAdapter2(context, parentPosition, menuCategory, menuItemClickListener);
            restaurantCategoryAdapter.setHideDetail(true);
            categoryItemView.setAdapter(restaurantCategoryAdapter);
//            restaurantCategoryAdapter.addItem(mItem);

//            if (position==(getItemCount()-1)) {
            if (menuSubCategory != null && menuSubCategory.size() > 0) {
                subCategoryView.setVisibility(View.VISIBLE);

                RestaurantSubCategoryAdapter restaurantSubCategoryAdapter = new RestaurantSubCategoryAdapter(context, parentPosition, menuCategory, menuItemClickListener);
                RecyclerLayoutManager layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
                subCategoryView.setLayoutManager(layoutManager);
                subCategoryView.setAdapter(restaurantSubCategoryAdapter);
                restaurantSubCategoryAdapter.addItem(menuSubCategory);
            }
//            }
        }

    }

    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView txt_menu_title, txt_price, txt_items_detail, item_count, txtCount;
        private final LinearLayout clickCount, item_remove, item_add;
        private final ProgressBar progressBar;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
            txt_menu_title = itemView.findViewById(R.id.txt_menu_title);
            txt_price = itemView.findViewById(R.id.txt_price);
            txtCount = itemView.findViewById(R.id.txt_count);
            txt_items_detail = itemView.findViewById(R.id.txt_items_detail);
            clickCount = itemView.findViewById(R.id.clickCount);
            item_remove = itemView.findViewById(R.id.item_remove);
            item_add = itemView.findViewById(R.id.item_add);
            item_count = itemView.findViewById(R.id.item_count);
            item_add.setOnClickListener(this);
            item_remove.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        private void bindData(int position) {
            txt_menu_title.setText(mItem.get(position).getProductName());
            txt_price.setText("£" + mItem.get(position).getMenuProductPrice());
            item_count.setText("0");
            if (hideDetail) {
                txt_items_detail.setVisibility(View.VISIBLE);
                txt_items_detail.setText(mItem.get(position).getDescription());
            } else {
                txt_items_detail.setVisibility(View.VISIBLE);
                txt_items_detail.setText(mItem.get(position).getDescription());
            }
            List<MenuProduct> products = db.getMenuProduct(menuCategory.getMenuCategoryId(), mItem.get(position).getMenuProductId());
            int qtyCount = 0;

            for (MenuProduct itemOnCart : products) {
                qtyCount += itemOnCart.getOriginalQuantity();
            }

            if (qtyCount == 0) {
                txtCount.setVisibility(View.GONE);
                clickCount.setVisibility(View.GONE);
                item_count.setText(String.valueOf(qtyCount));
            } else {
                txtCount.setText(String.valueOf(qtyCount));
                txtCount.setVisibility(View.VISIBLE);
                clickCount.setVisibility(View.VISIBLE);
                txtCount.setText(String.valueOf(qtyCount));
                item_count.setText(String.valueOf(qtyCount));
            }
        }

        @Override
        public void onClick(View v) {
            int itemQty;

            switch (v.getId()) {
                case R.id.item_add:
                    itemQty = (Integer.parseInt(item_count.getText().toString()) + 1);
                    txtCount.setText(String.valueOf(itemQty));
                    if (menuItemClickListener != null) {
                        menuItemClickListener.OnCategoryClick(parentPosition, getLayoutPosition(), clickCount, item_count, itemQty, 2, menuCategory, progressBar);
                    }
                    break;
                case R.id.item_remove:
                    itemQty = (Integer.parseInt(item_count.getText().toString()) - 1);
                    if (itemQty == 0) {
                        txtCount.setVisibility(View.GONE);
                        clickCount.setVisibility(View.GONE);
                    } else {
                        txtCount.setText(String.valueOf(itemQty));
                    }
                    if (menuItemClickListener != null) {
                        List<MenuProduct> mItemNew = mItem;
                        menuItemClickListener.OnCategoryClick(parentPosition, getLayoutPosition(), clickCount, item_count, itemQty, 1, menuCategory, progressBar);
                    }
                    break;
                default:
                    if (isClosed) {
                        restaurantClosedDialog();
                    } else {
                        if (clickCount.getVisibility() == View.GONE) {
                            itemQty = (Integer.parseInt(item_count.getText().toString()) + 1);
                            txtCount.setVisibility(View.VISIBLE);
                            if (menuItemClickListener != null) {
                                List<MenuProduct> mItemNew = mItem;

                                menuItemClickListener.OnCategoryClick(parentPosition, getLayoutPosition(), clickCount, item_count, itemQty, 2, menuCategory, progressBar);
                            }
                        }
                    }
                    break;
            }


        }
    }


    public void restaurantClosedDialog() {
        LayoutInflater factory = LayoutInflater.from(RestaurantDetailsActivity.restaurantDetailsActivity);
        final View mDialogVieww = factory.inflate(R.layout.layout_closed_dialog, null);
        final AlertDialog alertClodseDialog = new AlertDialog.Builder(RestaurantDetailsActivity.restaurantDetailsActivity).create();
        alertClodseDialog.setView(mDialogVieww);
        alertClodseDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //   final TextView ok_tv = (TextView)  mDialogView.findViewById(R.id.okTv);

        mDialogVieww.findViewById(R.id.tv_btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                alertClodseDialog.dismiss();
            }
        });


        alertClodseDialog.show();
    }
}