
package com.lexxdigital.easyfooduserapp.model.myorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.CartData;

import java.util.List;

public class OrderDetails {

    @SerializedName("menu")
    @Expose
    private CartData data = null;

    public CartData getData() {
        return data;
    }

    public void setData(CartData data) {
        this.data = data;
    }
}
