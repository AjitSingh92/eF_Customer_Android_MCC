
package com.lexxdigital.easyfooduserapps.restaurant_details.model.review_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewRequest {

    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("offset")
    @Expose
    private Integer offset;

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }


}
