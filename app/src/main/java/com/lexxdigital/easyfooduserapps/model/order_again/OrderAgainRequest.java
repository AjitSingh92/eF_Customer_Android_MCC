package com.lexxdigital.easyfooduserapps.model.order_again;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderAgainRequest {
    @SerializedName("order_number")
    @Expose
    private String orderNumber;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
