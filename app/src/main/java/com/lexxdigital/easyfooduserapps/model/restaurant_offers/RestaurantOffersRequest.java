package com.lexxdigital.easyfooduserapps.model.restaurant_offers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantOffersRequest {
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;

    public RestaurantOffersRequest(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}
