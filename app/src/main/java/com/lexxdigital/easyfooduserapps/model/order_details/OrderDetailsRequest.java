package com.lexxdigital.easyfooduserapps.model.order_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetailsRequest {
    @SerializedName("order_number")
    @Expose
    private String orderNumber;

    @SerializedName("customer_id")
    String customer_id;

    public OrderDetailsRequest(String orderNumber, String customer_id) {
        this.orderNumber = orderNumber;
        this.customer_id = customer_id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }
}
