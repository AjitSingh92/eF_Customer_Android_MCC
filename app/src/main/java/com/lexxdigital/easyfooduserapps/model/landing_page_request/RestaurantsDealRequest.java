
package com.lexxdigital.easyfooduserapps.model.landing_page_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsDealRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("post_code")
    @Expose
    private String postCode;
    @SerializedName("filter_by_cuisine")
    @Expose
    private ArrayList<String> filterByCuisine;
    @SerializedName("filter_by_serve_style")
    @Expose
    private List<String> filterByServeStyle;
    @SerializedName("filter_by_offer")
    @Expose
    private String filterByOffer;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("sort_by")
    @Expose
    private String sortBy;
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

    public ArrayList<String> getFilterByCuisine() {
        return filterByCuisine;
    }

    public void setFilterByCuisine(ArrayList<String> filterByCuisine) {
        this.filterByCuisine = filterByCuisine;
    }

    public List<String> getFilterByServeStyle() {
        return filterByServeStyle;
    }

    public void setFilterByServeStyle(List<String> filterByServeStyle) {
        this.filterByServeStyle = filterByServeStyle;
    }

    public String getFilterByOffer() {
        return filterByOffer;
    }

    public void setFilterByOffer(String filterByOffer) {
        this.filterByOffer = filterByOffer;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
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
