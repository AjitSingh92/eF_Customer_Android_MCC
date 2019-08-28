
package com.lexxdigital.easyfooduserapps.model.address_list_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressListRequest {

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
