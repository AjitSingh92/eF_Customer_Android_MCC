package com.easyfoodcustomer.model.restuarant_time_slot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;





public class TimeSlotRequest {
    @SerializedName("restaurant_id")
    @Expose
    private String restaurant_id;

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getServe_style() {
        return serve_style;
    }

    public void setServe_style(String serve_style) {
        this.serve_style = serve_style;
    }

    @SerializedName("customer_id")
    @Expose
    private String customer_id;
    @SerializedName("serve_style")
    @Expose
    private String serve_style;


    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }


}
