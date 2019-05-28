package com.lexxdigital.easyfooduserapp.adapters.menu_adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MealCategory;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;

import java.util.ArrayList;
import java.util.List;

public class MealProductCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private final LayoutInflater inflater;
    List<MealCategory> mItem;
    private ItemClickListener itemClickListener;

    int parentPosition;
    int childPosition;
    View qtyLayout;
    TextView item_count;
    int itemCount;
    int action;
    MenuCategory menuCategory;
    Boolean isSubCat;
    private Dialog dialog;
    OnMealProductItemSelect onMealProductItemSelect;
    List<MealProductAdapter> mealProductAdapters;


    public MealProductCategoryAdapter(Context context, Dialog dialog, int parentPosition, int childPosition, View qtyLayout, TextView item_count, int itemCount, int action, MenuCategory menuCategory, Boolean isSubCat, ItemClickListener itemClickListener, OnMealProductItemSelect onMealProductItemSelect) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        mItem = new ArrayList<>();
        this.itemClickListener = itemClickListener;
        this.dialog = dialog;
        this.parentPosition = parentPosition;
        this.childPosition = childPosition;
        this.qtyLayout = qtyLayout;
        this.item_count = item_count;
        this.itemCount = itemCount;
        this.action = action;
        this.menuCategory = menuCategory;
        this.isSubCat = isSubCat;
        this.onMealProductItemSelect = onMealProductItemSelect;
        mealProductAdapters = new ArrayList<>();
    }

    public List<MealProductAdapter> getMealProductAdapters() {

        return mealProductAdapters;
    }

    public void clearData() {
        this.mItem.clear();
        notifyDataSetChanged();
    }

    public void addItem(MealCategory mItem) {
        this.mItem.add(mItem);
        notifyItemChanged(this.mItem.size());
    }

    public void addItem(List<MealCategory> mItem) {

        this.mItem.addAll(mItem);
        notifyItemChanged(this.mItem.size());
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new MealProductCategoryAdapter.MealProductCategoryViewHolder(inflater.inflate(R.layout.meal_product_category_row, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        MealProductCategoryViewHolder categoryViewHolder = (MealProductCategoryViewHolder) viewHolder;
        categoryViewHolder.bindData(position);

    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }

    class MealProductCategoryViewHolder extends RecyclerView.ViewHolder implements MealProductAdapter.OnMealProductClickListener {
        private TextView tvItemTitle, alertMsg;
        private RecyclerView listProduct;
        private MealProductAdapter mealProductAdapter;
        private RecyclerLayoutManager layoutManager;

        public MealProductCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemTitle = itemView.findViewById(R.id.tv_ItemTitle);
            alertMsg = itemView.findViewById(R.id.tv_alertMsg);
            listProduct = itemView.findViewById(R.id.listProduct);

            layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
            layoutManager.setScrollEnabled(false);
            listProduct.setLayoutManager(layoutManager);


        }

        private void bindData(int position) {

            if (mItem.get(position).getCustomizable() == 1) {
                tvItemTitle.setText("Choose any " + mItem.get(position).getQuantity() + " out of " + mItem.get(position).getMealProducts().size() + " in " + mItem.get(position).getCategoryName());
                mealProductAdapter = new MealProductAdapter(context, dialog, mItem.get(position).getQuantity(), position, parentPosition, childPosition, qtyLayout, item_count, itemCount, action, menuCategory, isSubCat, itemClickListener, onMealProductItemSelect, this);

            } else if (mItem.get(position).getCustomizable() == 0) {
                tvItemTitle.setText(mItem.get(position).getCategoryName());
                mealProductAdapter = new MealProductAdapter(context, dialog, -1, position, parentPosition, childPosition, qtyLayout, item_count, itemCount, action, menuCategory, isSubCat, itemClickListener, onMealProductItemSelect, this);
            }
            alertMsg.setVisibility(View.GONE);
            mealProductAdapters.add(mealProductAdapter);
            listProduct.setAdapter(mealProductAdapter);
            mealProductAdapter.addItem(mItem.get(position).getMealProducts());


        }

        @Override
        public void OnMealProductClick(int position, Boolean showMsg, String message) {
            if (showMsg) {
                alertMsg.setVisibility(View.VISIBLE);
                alertMsg.setText(message);
            } else {
                alertMsg.setVisibility(View.GONE);
            }
        }
    }
}
