package com.lexxdigital.easyfooduserapps.order_status.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderStatusRequestModel {
    @Expose
    @SerializedName("customer_id")
    String customer_id;

    @Expose
    @SerializedName("order_number")
    String order_number;

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    @Override
    public String toString() {
        return "OrderStatusRequestModel{" +
                "customer_id='" + customer_id + '\'' +
                ", order_number='" + order_number + '\'' +
                '}';
    }
}
