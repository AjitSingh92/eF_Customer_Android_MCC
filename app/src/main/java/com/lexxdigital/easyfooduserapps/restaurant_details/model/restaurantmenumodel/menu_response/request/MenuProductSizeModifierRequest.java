package com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuProductSizeModifierRequest {
    @SerializedName("product_id")
    @Expose
    String productId;

    @SerializedName("size_id")
    @Expose
    String sizeId;

    public MenuProductSizeModifierRequest(String productId, String sizeId) {
        this.productId = productId;
        this.sizeId = sizeId;
    }
}
