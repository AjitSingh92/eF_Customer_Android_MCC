
package com.lexxdigital.easyfooduserapps.model.landing_page_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Errors {

    @SerializedName("user_id")
    @Expose
    private List<String> userId = null;
    @SerializedName("post_code")
    @Expose
    private List<String> postCode = null;
    @SerializedName("limit")
    @Expose
    private List<String> limit = null;
    @SerializedName("offset")
    @Expose
    private List<String> offset = null;

    public List<String> getUserId() {
        return userId;
    }

    public void setUserId(List<String> userId) {
        this.userId = userId;
    }

    public List<String> getPostCode() {
        return postCode;
    }

    public void setPostCode(List<String> postCode) {
        this.postCode = postCode;
    }

    public List<String> getLimit() {
        return limit;
    }

    public void setLimit(List<String> limit) {
        this.limit = limit;
    }

    public List<String> getOffset() {
        return offset;
    }

    public void setOffset(List<String> offset) {
        this.offset = offset;
    }

}
