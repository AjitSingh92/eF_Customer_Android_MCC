package com.lexxdigital.easyfooduserapps.adapters.menu_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;

import java.util.ArrayList;
import java.util.List;

public class RestaurantSubCategoryAdapter extends RecyclerView.Adapter<RestaurantSubCategoryAdapter.CategoryViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private List<MenuCategory> mItem;
    private Boolean hideDetail = false;
    ItemClickListener menuItemClickListener;
    MenuCategory menuCategory;
    int parentPosition = -1;

    public RestaurantSubCategoryAdapter(Context context, int parentPosition, MenuCategory menuCategory, ItemClickListener menuItemClickListener) {
        this.context = context;
        this.parentPosition = parentPosition;
        this.menuCategory = menuCategory;
        this.menuItemClickListener = menuItemClickListener;
        inflater = LayoutInflater.from(context);
        this.mItem = new ArrayList<>();

    }

    public void setHideDetail(Boolean hideDetail) {
        this.hideDetail = hideDetail;
    }

    public void clearData() {
        this.mItem.clear();
        notifyDataSetChanged();
    }

    public void addItem(List<MenuCategory> mItem) {
        this.mItem.addAll(mItem);
        notifyItemChanged(this.mItem.size());
    }

    public void addItem(MenuCategory mItem) {
        this.mItem.add(mItem);
        notifyItemChanged(this.mItem.size());
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {
        return new CategoryViewHolder(inflater.inflate(R.layout.menu_sub_cat_item_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder pdqListingViewHolder, int position) {
        pdqListingViewHolder.bindData(position);

    }


    @Override
    public int getItemCount() {
        return mItem.size();
    }


    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView title;
        private final RecyclerView subCategoryItemView;
        private final ProgressBar progressBar;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_title);
            progressBar = itemView.findViewById(R.id.progressBar);
            subCategoryItemView = itemView.findViewById(R.id.list_subCategoryItemView);
            progressBar.setVisibility(View.GONE);
            itemView.setOnClickListener(this);
        }

        private void bindData(int position) {
            title.setText(mItem.get(position).getMenuCategoryName());

            RecyclerLayoutManager layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
            layoutManager.setScrollEnabled(false);
            subCategoryItemView.setLayoutManager(layoutManager);
            MenuProductAdapter restaurantCategoryAdapter = new MenuProductAdapter(context, getLayoutPosition(), menuCategory, menuItemClickListener);
            restaurantCategoryAdapter.setHideDetail(true);
            subCategoryItemView.setAdapter(restaurantCategoryAdapter);
            restaurantCategoryAdapter.addItem(mItem.get(position).getMenuProducts());


        }

        @Override
        public void onClick(View v) {
            if (menuItemClickListener != null) {
                menuItemClickListener.LoadMenuProduct(parentPosition, mItem.get(getLayoutPosition()).getMenuCategoryId(), progressBar);
            }
//            Toast.makeText(context, "" + mItem.get(getLayoutPosition()).getMenuCategoryName(), Toast.LENGTH_SHORT).show();
        }
    }
}