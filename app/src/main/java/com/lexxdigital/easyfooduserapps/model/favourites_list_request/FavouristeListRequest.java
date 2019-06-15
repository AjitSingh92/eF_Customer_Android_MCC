
package com.lexxdigital.easyfooduserapps.model.favourites_list_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavouristeListRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("post_code")
    @Expose
    private String postCode;
    @SerializedName("favourite_type")
    @Expose
    private String favouriteType;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("offset")
    @Expose
    private Integer offset;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getFavouriteType() {
        return favouriteType;
    }

    public void setFavouriteType(String favouriteType) {
        this.favouriteType = favouriteType;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

}
