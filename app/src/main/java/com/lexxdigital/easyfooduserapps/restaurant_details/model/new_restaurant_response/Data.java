
package com.lexxdigital.easyfooduserapps.restaurant_details.model.new_restaurant_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("restaurants")
    @Expose
    private Restaurants restaurants;

    public Restaurants getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Restaurants restaurants) {
        this.restaurants = restaurants;
    }

    @Override
    public String toString() {
        return "Data{" +
                "restaurants=" + restaurants +
                '}';
    }
}
