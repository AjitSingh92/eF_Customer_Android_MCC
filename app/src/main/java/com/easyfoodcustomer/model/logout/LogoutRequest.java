package com.easyfoodcustomer.model.logout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LogoutRequest {
    @SerializedName("customer_id")
    @Expose
    private String customerId;


    public String getFcm_id() {
        return fcm_id;
    }

    public void setFcm_id(String fcm_id) {
        this.fcm_id = fcm_id;
    }

    @SerializedName("fcm_id")
    @Expose
    private String fcm_id;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "LogoutRequest{" +
                "customerId='" + customerId +
                "fcm_id='" + fcm_id +
                '\'' +
                '}';
    }
}
