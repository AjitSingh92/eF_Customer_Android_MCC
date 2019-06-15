
package com.lexxdigital.easyfooduserapps.search_post_code.model.search_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchPostCodeRequest {

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
