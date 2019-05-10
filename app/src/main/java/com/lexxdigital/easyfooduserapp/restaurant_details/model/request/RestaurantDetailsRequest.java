
package com.lexxdigital.easyfooduserapp.restaurant_details.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantDetailsRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("post_code")
    @Expose
    private String postCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Override
    public String toString() {
        return "RestaurantDetailsRequest{" +
                "userId='" + userId + '\'' +
                ", restaurantId='" + restaurantId + '\'' +
                ", postCode='" + postCode + '\'' +
                '}';
    }
}
