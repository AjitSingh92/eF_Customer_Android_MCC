
package com.lexxdigital.easyfooduserapps.select_payment_method.model.checkout_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("order_id")
    @Expose
    private String orderId;

    @SerializedName("order_number")
    @Expose
    private String order_number;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    @Override
    public String toString() {
        return "Data{" +
                "orderId='" + orderId + '\'' +
                ", order_number='" + order_number + '\'' +
                '}';
    }
}
