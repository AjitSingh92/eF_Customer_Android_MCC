package com.easyfoodcustomer.adapters.menu_adapter;

import android.view.View;
import android.widget.TextView;

import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;

import java.util.List;

public interface OneItemMultiTimeListener {
    void onMultiTimeItemChange(int childPosition, int parentPosition, List<MenuProduct> menuProduct, View view, TextView qtyTextView, MenuCategory menuCategory, int itemCount, int action);
}
