package com.easyfoodcustomer.adapters.menu_adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.easyfoodcustomer.R;
import com.easyfoodcustomer.cart_db.DatabaseHelper;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;

import java.util.ArrayList;
import java.util.List;

public class RestaurantCategoryAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private List<MenuProduct> mItem;
    private Boolean hideDetail = false;
    ItemClickListener menuItemClickListener;
    int parentPosition;
    MenuCategory menuCategory;
    DatabaseHelper db;

    public RestaurantCategoryAdapter2(Context context, int parentPosition, MenuCategory menuCategory, ItemClickListener menuItemClickListener) {
        this.context = context;
        this.parentPosition = parentPosition;
        this.menuItemClickListener = menuItemClickListener;
        this.menuCategory = menuCategory;
        inflater = LayoutInflater.from(context);
        this.mItem = new ArrayList<>();
        this.mItem.addAll(menuCategory.getMenuProducts());
        db = new DatabaseHelper(context);
    }

    public void setHideDetail(Boolean hideDetail) {
        this.hideDetail = hideDetail;
    }

    public void clearData() {
        this.mItem.clear();
        notifyDataSetChanged();
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

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {
        switch (viewtype) {
            case 0:
                return new CategoryViewHolder(inflater.inflate(R.layout.offer_item_list, viewGroup, false));

        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }


    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView txt_menu_title, txt_price, txt_items_detail, item_count;
        private final LinearLayout clickCount, item_remove, item_add;
        private final ProgressBar progressBar;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
            txt_menu_title = itemView.findViewById(R.id.txt_menu_title);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_items_detail = itemView.findViewById(R.id.txt_items_detail);
            clickCount = itemView.findViewById(R.id.clickCount);
            item_remove = itemView.findViewById(R.id.item_remove);
            item_add = itemView.findViewById(R.id.item_add);
            item_count = itemView.findViewById(R.id.item_count);
            item_add.setOnClickListener(this);
            item_remove.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        private void bindData(final int position) {
            txt_menu_title.setText(mItem.get(position).getProductName());
            txt_price.setText("??" + mItem.get(position).getMenuProductPrice());
            item_count.setText("0");
            if (hideDetail) {
                txt_items_detail.setVisibility(View.GONE);
            } else {
                txt_items_detail.setVisibility(View.VISIBLE);
                txt_items_detail.setText(mItem.get(position).getProductName());
            }


            List<MenuProduct> products = db.getMenuProduct(menuCategory.getMenuCategoryId(), mItem.get(position).getMenuProductId());
            int qtyCount = 0;

            for (MenuProduct itemOnCart : products) {
                qtyCount += itemOnCart.getOriginalQuantity();
            }

            if (qtyCount == 0) {
                clickCount.setVisibility(View.GONE);
                item_count.setText(String.valueOf(qtyCount));
            } else {
                clickCount.setVisibility(View.VISIBLE);
                item_count.setText(String.valueOf(qtyCount));
            }
        }

        @Override
        public void onClick(View v) {
            int itemQty;
            switch (v.getId()) {
                case R.id.item_add:
//                    item_count.setText(String.valueOf((Integer.parseInt(item_count.getText().toString()) + 1)));
                    itemQty = (Integer.parseInt(item_count.getText().toString()) + 1);
                    if (menuItemClickListener != null) {
//                        getItemData(getLayoutPosition());
                        menuItemClickListener.OnCategoryClick(parentPosition, getLayoutPosition(), clickCount, item_count, itemQty, 2, menuCategory, progressBar);
                    }
                    break;
                case R.id.item_remove:
//                    item_count.setText(String.valueOf((Integer.parseInt(item_count.getText().toString()) - 1)));
                    itemQty = (Integer.parseInt(item_count.getText().toString()) - 1);
                    if (itemQty == 0) {
//                        item_count.setText(String.valueOf(itemQty));
                        clickCount.setVisibility(View.GONE);
                    }
                    if (menuItemClickListener != null) {
                        List<MenuProduct> mItemNew = mItem;
                        menuItemClickListener.OnCategoryClick(parentPosition, getLayoutPosition(), clickCount, item_count, itemQty, 1, menuCategory, progressBar);
                    }
                    break;
                default:
                    if (clickCount.getVisibility() == View.GONE) {
//                        clickCount.setVisibility(View.VISIBLE);
//                        item_count.setText("1");
                        itemQty = (Integer.parseInt(item_count.getText().toString()) + 1);
                        if (menuItemClickListener != null) {
                            menuItemClickListener.OnCategoryClick(parentPosition, getLayoutPosition(), clickCount, item_count, itemQty, 2, menuCategory, progressBar);
                        }
                    }
                    break;
            }


        }
    }

}