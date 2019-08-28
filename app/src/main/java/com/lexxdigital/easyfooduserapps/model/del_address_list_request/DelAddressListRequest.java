
package com.lexxdigital.easyfooduserapps.model.del_address_list_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DelAddressListRequest {

    @SerializedName("id")
    @Expose
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
