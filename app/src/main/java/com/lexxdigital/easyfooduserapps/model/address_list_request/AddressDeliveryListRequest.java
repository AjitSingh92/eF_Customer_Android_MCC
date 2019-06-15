
package com.lexxdigital.easyfooduserapps.model.address_list_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressDeliveryListRequest {

    @SerializedName("customer_id")
    @Expose
    private String customerId;

    @SerializedName("restaurant_id")
    @Expose
    private String restaurant_id;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }
}
