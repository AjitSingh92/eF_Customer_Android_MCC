
package com.lexxdigital.easyfooduserapps.model.myorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.CartData;

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
