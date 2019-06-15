package com.lexxdigital.easyfooduserapps.model.logout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LogoutRequest {
    @SerializedName("customer_id")
    @Expose
    private String customerId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "LogoutRequest{" +
                "customerId='" + customerId + '\'' +
                '}';
    }
}
