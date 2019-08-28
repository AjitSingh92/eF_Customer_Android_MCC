
package com.lexxdigital.easyfooduserapps.model.landing_page_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantDeliveryCharge {

    @SerializedName("delivery_charge")
    @Expose
    private String deliveryCharge;
    @SerializedName("min_order_value")
    @Expose
    private String minOrderValue;
    @SerializedName("free_delivery")
    @Expose
    private String freeDelivery;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getMinOrderValue() {
        return minOrderValue;
    }

    public void setMinOrderValue(String minOrderValue) {
        this.minOrderValue = minOrderValue;
    }

    public String getFreeDelivery() {
        return freeDelivery;
    }

    public void setFreeDelivery(String freeDelivery) {
        this.freeDelivery = freeDelivery;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

}
