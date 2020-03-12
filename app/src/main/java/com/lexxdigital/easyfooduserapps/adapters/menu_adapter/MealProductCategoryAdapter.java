package com.lexxdigital.easyfooduserapps.adapters.menu_adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MealCategory;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MealProductCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private final LayoutInflater inflater;
    List<MealCategory> mItem;
    private ItemClickListener itemClickListener;
    private scrollToPosition scrollToPosition;

    public interface scrollToPosition {
        void onScrollPosition(int position);
    }

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
    public HashMap<Integer, MealProductAdapter> mealProductAdapters;

    int lastGonePosition = -1;

    boolean isShowOption = false;

    Boolean openOnClick;
    private Boolean enableScrollToPosition = false;

    public void setEnableScrollToPosition(Boolean enableScrollToPosition) {
        this.enableScrollToPosition = enableScrollToPosition;
    }

    public void setLastGonePosition(int position) {
        this.lastGonePosition = position;
    }

    public int getLastGonePosition() {
        return lastGonePosition;
    }

    public MealProductCategoryAdapter(Context context, scrollToPosition scrollToPosition, Boolean openOnClick, Dialog dialog, int parentPosition, int childPosition, View qtyLayout, TextView item_count, int itemCount, int action, MenuCategory menuCategory, Boolean isSubCat, ItemClickListener itemClickListener, OnMealProductItemSelect onMealProductItemSelect) {
        this.context = context;
        this.scrollToPosition = scrollToPosition;
        this.openOnClick = openOnClick;
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
        mealProductAdapters = new HashMap<>();
    }

    public HashMap<Integer, MealProductAdapter> getMealProductAdapters() {
        return mealProductAdapters;
    }

    public String getCategoryName(int position) {
        return mItem.get(position).getCategoryName();
    }

    public int getCustomizableQuantity(int position) {
        if (mItem.get(position).getCustomizable() == 0) {
            return -1;
        }
        return mItem.get(position).getQuantity();
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
                tvItemTitle.setText(mItem.get(position).getCategoryName() + "\n" + "Pick " + mItem.get(position).getQuantity());
                mealProductAdapter = new MealProductAdapter(context, openOnClick, dialog, mItem.get(position).getQuantity(), position, parentPosition, childPosition, qtyLayout, item_count, itemCount, action, menuCategory, isSubCat, itemClickListener, onMealProductItemSelect, this);

//                menuCategory.getMeal().get(childPosition).getMealCategories().get(0).getMealProducts().get(0)

                /*int count = 0;
                for (int i = 0; i < menuCategory.getMeal().get(childPosition).getMealCategories().size(); i++) {

                    for (int j = 0; j < menuCategory.getMeal().get(childPosition).getMealCategories().get(i).getMealProducts().size(); j++) {
                        if (menuCategory.getMeal().get(childPosition).getMealCategories().get(i).getMealProducts().get(j).isSelected) {
                            count++;
                        }
                    }
                }
                mealProductAdapter.setTotalItem(count+1);*/

            } else if (mItem.get(position).getCustomizable() == 0) {
                tvItemTitle.setText(mItem.get(position).getCategoryName());
                mealProductAdapter = new MealProductAdapter(context, openOnClick, dialog, -1, position, parentPosition, childPosition, qtyLayout, item_count, itemCount, action, menuCategory, isSubCat, itemClickListener, onMealProductItemSelect, this);

                /*int count = 0;
                for (int i = 0; i < menuCategory.getMeal().get(childPosition).getMealCategories().size(); i++) {

                    for (int j = 0; j < menuCategory.getMeal().get(childPosition).getMealCategories().get(i).getMealProducts().size(); j++) {
                        if (menuCategory.getMeal().get(childPosition).getMealCategories().get(i).getMealProducts().get(j).isSelected) {
                            count++;
                        }
                    }
                }
                mealProductAdapter.setTotalItem(count+1);*/
            }
            alertMsg.setVisibility(View.GONE);
            listProduct.setVisibility(View.GONE);

            mealProductAdapters.put(position, mealProductAdapter);
            listProduct.setAdapter(mealProductAdapter);
            mealProductAdapter.addItem(mItem.get(position).getMealProducts());
//            if (enableScrollToPosition) {
                for (int j = 0; j < menuCategory.getMeal().get(childPosition).getMealCategories().get(position).getMealProducts().size(); j++) {
                    if (menuCategory.getMeal().get(childPosition).getMealCategories().get(position).getMealProducts().get(j).isSelected) {
                        listProduct.setVisibility(View.VISIBLE);
                        if (enableScrollToPosition && scrollToPosition != null) {
                            scrollToPosition.onScrollPosition(position + 1);
                        }
                        isShowOption = false;
                        lastGonePosition = getLayoutPosition();

                    }
                }
//            }
            /*if ((lastGonePosition + 1) == position) {
                listProduct.setVisibility(View.VISIBLE);
            }*/

            if (lastGonePosition != position && !isShowOption) {
                isShowOption = true;
                listProduct.setVisibility(View.VISIBLE);
            }

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
