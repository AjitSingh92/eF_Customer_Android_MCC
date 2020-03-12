package com.lexxdigital.easyfooduserapps.adapters.menu_adapter;

import android.app.Dialog;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapps.cart_db.tables.ProductSizeAndModifier;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.SpecialOffer;


public interface ItemClickListener {
    void LoadMenuProduct(int parentPosition, String category_id, ProgressBar progressBar);

    void OnSpecialOfferClick(int parentPosition, int childPosition, TextView itemQtyView, int itemCount, int action, SpecialOffer item);

    void OnCategoryClick(int parentPosition, int childPosition, View qtyLayout, TextView itemQtyView, int itemCount, int action, MenuCategory
            menuCategory, ProgressBar progressBar);

    void OnSubCategoryClick(int parentPosition, int childPosition, View qtyLayout, TextView itemQtyView, int itemCount, int action, MenuCategory
            menuCategory, ProgressBar progressBar);

    void OnAddItem(int parentPosition, int childPosition, View qtyLayout, TextView itemQtyView, int itemCount, int action, MenuCategory menuCategory);

    void OnMealProductClick(Dialog dialog,int childParentPosition, int selectedChildPosition, int parentPosition, int childPosition, View qtyLayout, TextView item_count, int itemCount, int action, MenuCategory menuCategory, ProductSizeAndModifier.ProductSizeAndModifierTable productSizeAndModifierTable, Boolean isSubCat);

    void loadMealProductData(Dialog dialog, String productId, String productSizeId, ProgressBar progressBar, int childParentPosition, int selectedChildPosition, int parentPosition, int childPosition, View qtyLayout, TextView item_count, int itemCount, int action, MenuCategory menuCategory, Boolean isSubCat);

    void OnMealProductModifierSelected(Boolean onDone, int childParentPosition, int selectedChildPosition, int parentPosition, int childPosition, View qtyLayout, TextView item_count, int itemCount, int action, MenuCategory menuCategory, Boolean isSubCat,Boolean enableScrollToPosition);

}
