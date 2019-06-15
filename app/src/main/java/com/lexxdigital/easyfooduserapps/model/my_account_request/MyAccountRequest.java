
package com.lexxdigital.easyfooduserapps.model.my_account_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyAccountRequest {

    @SerializedName("customer_id")
    @Expose
    private String customerId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

}
