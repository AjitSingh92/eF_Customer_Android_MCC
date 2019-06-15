package com.lexxdigital.easyfooduserapps.model.cancelorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CancelRequest {
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
