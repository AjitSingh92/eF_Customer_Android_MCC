
package com.easyfoodcustomer.restaurant_details.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantDetailsRequest {
    /////////////////// These two keys are for hygiene rating  ///////////////////
    private String name;
    private String postcode;


    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    /////////////////////////////////////

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("post_code")
    @Expose
    private String postCode;

    @SerializedName("serve_style")
    @Expose
    private String serve_style;

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
    public String getServe_style() {
        return serve_style;
    }

    public void setServe_style(String serve_style) {
        this.serve_style = serve_style;
    }
    @Override
    public String toString() {
        return "RestaurantDetailsRequest{" +
                "userId='" + userId + '\'' +
                ", restaurantId='" + restaurantId + '\'' +
                ", postCode='" + postCode + '\'' +
                ", serve_style='" + serve_style + '\'' +
                '}';
    }
}
