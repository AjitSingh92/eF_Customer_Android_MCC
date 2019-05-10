package com.lexxdigital.easyfooduserapp.model.order_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetailsRequest {
    @SerializedName("order_number")
    @Expose
    private String orderNumber;

    public OrderDetailsRequest(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
