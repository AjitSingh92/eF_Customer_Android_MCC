package com.lexxdigital.easyfooduserapp.model.restuarant_time_slot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeSlotRequest {
    @SerializedName("restaurant_id")
    @Expose
    private String restaurant_id;

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    @Override
    public String toString() {
        return "TimeSlotRequest{" +
                "restaurant_id='" + restaurant_id + '\'' +
                '}';
    }
}
