package com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuProductSizeModifierRequest {
    @SerializedName("product_id")
    @Expose
    String productId;

    public MenuProductSizeModifierRequest(String productId) {
        this.productId = productId;
    }
}
