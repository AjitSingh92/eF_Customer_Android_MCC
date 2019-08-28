package com.lexxdigital.easyfooduserapps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateCustomerPostcodeRequest {
    @SerializedName("customer_id")
    @Expose
    String customerId;
    @SerializedName("post_code")
    @Expose
    String postCode;


    public UpdateCustomerPostcodeRequest(String customerId, String postCode) {
        this.customerId = customerId;
        this.postCode = postCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
