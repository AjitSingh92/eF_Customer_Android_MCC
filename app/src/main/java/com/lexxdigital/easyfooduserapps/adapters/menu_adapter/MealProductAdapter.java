package com.lexxdigital.easyfooduserapps.adapters.menu_adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.cart_db.tables.ProductSizeAndModifier;
import com.lexxdigital.easyfooduserapps.dialogs.MenuMealDialog;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MealProduct;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;
import com.lexxdigital.easyfooduserapps.utility.GlobalValues;

import java.util.ArrayList;
import java.util.List;

public class MealProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private final LayoutInflater inflater;
    List<MealProduct> mItem;
    int lastSelectedPosition = -1;
    CheckBox lastSelectedItem;
    int customizableQuantity = -1;
    private ItemClickListener itemClickListener;

    int childParentPosition;

    int parentPosition;
    int childPosition;
    View qtyLayout;
    TextView item_count;
    int itemCount;
    int action;
    MenuCategory menuCategory;
    Boolean isSubCat;
    Dialog dialog;
    OnMealProductItemSelect onMealProductItemSelect;
    OnMealProductClickListener onMealProductClickListener;
    Boolean openOnClick;

    int totalItem = 1;

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public interface OnMealProductClickListener {
        void OnMealProductClick(int position, Boolean showMsg, String message);
    }

    public MealProductAdapter(Context context, Boolean openOnClick, Dialog dialog, int customizableQuantity, int childParentPosition, int parentPosition, int childPosition, View qtyLayout, TextView item_count, int itemCount, int action, MenuCategory menuCategory, Boolean isSubCat, ItemClickListener itemClickListener, OnMealProductItemSelect onMealProductItemSelect, OnMealProductClickListener onMealProductClickListener) {
        this.context = context;
        this.openOnClick = openOnClick;
        inflater = LayoutInflater.from(context);
        mItem = new ArrayList<>();
        this.customizableQuantity = customizableQuantity;
        this.itemClickListener = itemClickListener;
        this.dialog = dialog;

        this.childParentPosition = childParentPosition;

        this.parentPosition = parentPosition;
        this.childPosition = childPosition;
        this.qtyLayout = qtyLayout;
        this.item_count = item_count;
        this.itemCount = itemCount;
        this.action = action;
        this.menuCategory = menuCategory;
        this.isSubCat = isSubCat;
        this.onMealProductItemSelect = onMealProductItemSelect;
        this.onMealProductClickListener = onMealProductClickListener;
    }

    public List<MealProduct> getSelectedItem() {
        List<MealProduct> data = new ArrayList<>();
        for (MealProduct item : mItem) {
            if (item.getSelected()) {
                data.add(item);
            }
        }
        return data;
    }

    public void clearData() {
        this.mItem.clear();
        notifyDataSetChanged();
    }

    public void addItem(List<MealProduct> mItem) {

        this.mItem.addAll(mItem);
        notifyItemChanged(this.mItem.size());
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new MealProductAdapter.MealProductViewHolder(inflater.inflate(R.layout.meal_product_row, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        MealProductViewHolder categoryViewHolder = (MealProductViewHolder) viewHolder;
        categoryViewHolder.bindData(position);

    }

    @Override
    public int getItemCount() {
        return mItem.size();
//        return totalItem;
    }

    class MealProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvItemTitle, tvModifier;
        private CheckBox itemSelected;
        private final ProgressBar progressBar;

        public MealProductViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvItemTitle = itemView.findViewById(R.id.tv_title);
            tvModifier = itemView.findViewById(R.id.tv_modifiers);
            itemSelected = itemView.findViewById(R.id.cb_itemSelected);
            progressBar = itemView.findViewById(R.id.progressBar);

        }

        private void bindData(int position) {
            tvItemTitle.setText(mItem.get(position).getProductName() + " " + mItem.get(position).getProductSizeName());

//            itemSelected.setClickable(false);
            if (customizableQuantity == -1) {

                mItem.get(position).setSelected(true);
                itemSelected.setChecked(true);
                itemView.setOnClickListener(this);
                if (onMealProductItemSelect != null) {
                    onMealProductItemSelect.OnMealProductItemSelect(true);
                }
            } else {
                if (mItem.get(position).getSelected() && !openOnClick) {
                    itemSelected.setChecked(true);
                    if (onMealProductItemSelect != null) {
                        onMealProductItemSelect.OnMealProductItemSelect(true);
                    }
                } else {
                    itemSelected.setChecked(false);
                    mItem.get(position).setSelected(false);
                }
            }

            if (mItem.get(position).getMenuProductSize() != null && mItem.get(position).getSelected()) {

                if (mItem.get(position).getMenuProductSize().get(0).getSizeModifiers() != null) {

                    String strModifier = "";

                    for (int i = 0; i < mItem.get(position).getMenuProductSize().get(0).getSizeModifiers().size(); i++) {
                        for (int j = 0; j < mItem.get(position).getMenuProductSize().get(0).getSizeModifiers().get(i).getModifier().size(); j++) {

                            if (strModifier.equals("")) {
                                strModifier = mItem.get(position).getMenuProductSize().get(0).getSizeModifiers().get(i).getModifier().get(j).getOriginalQuantity() + "x" + mItem.get(position).getMenuProductSize().get(0).getSizeModifiers().get(i).getModifier().get(j).getProductName();
                            } else {
                                strModifier = strModifier + ", " + mItem.get(position).getMenuProductSize().get(0).getSizeModifiers().get(i).getModifier().get(j).getOriginalQuantity() + "x" + mItem.get(position).getMenuProductSize().get(0).getSizeModifiers().get(i).getModifier().get(j).getProductName();
                            }
                        }
                    }
                    tvModifier.setText(strModifier);
                }
            } else {
                tvModifier.setText(null);
            }
        }

        @Override
        public void onClick(View v) {
            if (customizableQuantity != -1) {
                if (getSelectedItem().size() == customizableQuantity && !mItem.get(getLayoutPosition()).getSelected()) {
                    if (onMealProductClickListener != null) {
                        onMealProductClickListener.OnMealProductClick(getLayoutPosition(), true, "You can not choose more than " + customizableQuantity);
                    }
                    return;
                }
//                onMealProductClickListener.OnMealProductClick(getLayoutPosition(), false, "You can not choose more than " + customizableQuantity);

                if (mItem.get(getLayoutPosition()).getSelected()) {
                    itemSelected.setChecked(false);
                    lastSelectedItem = itemSelected;
                    lastSelectedPosition = getLayoutPosition();
                    mItem.get(getLayoutPosition()).setSelected(false);
                    tvModifier.setText(null);
                    if (onMealProductItemSelect != null) {
                        onMealProductItemSelect.OnMealProductItemSelect(false);
                    }
//                    menuCategory.getMeal().get(parentPosition).getMealCategories().get(childPosition).getMealProducts().get(getLayoutPosition()).setSelected(false);
                    menuCategory.getMeal().get(childPosition).getMealCategories().get(childParentPosition).getMealProducts().get(getLayoutPosition()).setSelected(false);

                } else {
//                    menuCategory.getMeal().get(parentPosition).getMealCategories().get(childPosition).getMealProducts().get(getLayoutPosition()).setSelected(true);
                    menuCategory.getMeal().get(childPosition).getMealCategories().get(childParentPosition).getMealProducts().get(getLayoutPosition()).setSelected(true);
                    if (onMealProductItemSelect != null) {
                        onMealProductItemSelect.OnMealProductItemSelect(true);
                    }
                    itemSelected.setChecked(true);
                    lastSelectedItem = itemSelected;
                    lastSelectedPosition = getLayoutPosition();
                    mItem.get(getLayoutPosition()).setSelected(true);

                }
                if (mItem.get(getLayoutPosition()).getSelected()) {
                    if (onMealProductItemSelect != null) {
                        onMealProductItemSelect.OnMealProductItemSelect(true);
                    }
//                categoryName.setText(menuCategory.getMeal().get(childPosition).getMealCategories().get(childParentPosition).getMealProducts().get(selectedChildPosition).getProductId());
                   /* new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ProductSizeAndModifier.ProductSizeAndModifierTable productSizeAndModifierTable = GlobalValues.getInstance().getDb().productSizeAndModifierMaster().getProductSizeAndModifierList(mItem.get(getLayoutPosition()).getProductId());
                            if (productSizeAndModifierTable == null) {
                                if (itemClickListener != null) {
                                    itemClickListener.loadMealProductData(dialog, mItem.get(getLayoutPosition()).getProductId(), mItem.get(getLayoutPosition()).getProductSizeId(), progressBar, childParentPosition, getLayoutPosition(), parentPosition, childPosition, qtyLayout, item_count, itemCount, action, menuCategory, isSubCat);
                                }
                            } else {
                                if (productSizeAndModifierTable.getMenuProductSize().size() > 0) {
                                    if (productSizeAndModifierTable.getMenuProductSize().get(0).getSizeModifiers().size() > 0) {
                                        if (itemClickListener != null) {
                                            itemClickListener.OnMealProductClick(dialog, childParentPosition, getLayoutPosition(), parentPosition, childPosition, qtyLayout, item_count, itemCount, action, menuCategory, productSizeAndModifierTable, isSubCat);
                                        }
                                    }
                                }else{
                                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (MenuMealDialog.getInstance().mealProductCategoryAdapter != null)
                                                MenuMealDialog.getInstance().mealProductCategoryAdapter.notifyDataSetChanged();
                                        }
                                    });

                                }

                            }
                        }
                    }).start();*/
                    if (itemClickListener != null) {
                        itemClickListener.loadMealProductData(dialog, mItem.get(getLayoutPosition()).getProductId(), mItem.get(getLayoutPosition()).getProductSizeId(), progressBar, childParentPosition, getLayoutPosition(), parentPosition, childPosition, qtyLayout, item_count, itemCount, action, menuCategory, isSubCat);
                    }
                }
            }


        }
    }

}
