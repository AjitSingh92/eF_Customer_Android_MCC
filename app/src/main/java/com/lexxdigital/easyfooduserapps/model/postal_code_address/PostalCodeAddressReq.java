package com.lexxdigital.easyfooduserapps.model.postal_code_address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostalCodeAddressReq {
    @SerializedName("post_code")
    @Expose
    private String postCode;

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
