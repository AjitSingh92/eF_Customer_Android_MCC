package com.lexxdigital.easyfooduserapp.adapters.menu_adapter;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.SpecialOffer;


public interface ItemClickListener {
    void LoadMenuProduct(int parentPosition, String category_id, ProgressBar progressBar);

    void OnSpecialOfferClick(int parentPosition, int childPosition, TextView itemQtyView, int itemCount, int action, SpecialOffer item);

    void OnCategoryClick(int parentPosition, int childPosition, View qtyLayout, TextView itemQtyView, int itemCount, int action, MenuCategory
            menuCategory, ProgressBar progressBar);

    void OnSubCategoryClick(int parentPosition, int childPosition, View qtyLayout, TextView itemQtyView, int itemCount, int action, MenuCategory
            menuCategory, ProgressBar progressBar);

    void OnAddItem(int parentPosition, int childPosition, View qtyLayout, TextView itemQtyView, int itemCount, int action, MenuCategory menuCategory);

}
