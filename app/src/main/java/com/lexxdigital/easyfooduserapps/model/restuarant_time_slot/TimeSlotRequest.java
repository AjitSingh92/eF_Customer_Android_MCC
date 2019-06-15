package com.lexxdigital.easyfooduserapps.model.restuarant_time_slot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeSlotRequest {
    @SerializedName("restaurant_id")
    @Expose
    private String restaurant_id;
    @SerializedName("order_type")
    @Expose
    private String order_type;

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }
}
