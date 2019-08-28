
package com.lexxdigital.easyfooduserapps.model.address_list_response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Errors {

    @SerializedName("customer_id")
    @Expose
    private List<String> customerId = null;

    public List<String> getCustomerId() {
        return customerId;
    }

    public void setCustomerId(List<String> customerId) {
        this.customerId = customerId;
    }

}
