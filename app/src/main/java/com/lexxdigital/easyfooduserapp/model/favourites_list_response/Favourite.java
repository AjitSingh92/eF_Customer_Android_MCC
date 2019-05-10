
package com.lexxdigital.easyfooduserapp.model.favourites_list_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Favourite {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("entity_id")
    @Expose
    private String entityId;
    @SerializedName("entity_type")
    @Expose
    private String entityType;
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("cuisines")
    @Expose
    private String cuisines;
    @SerializedName("min_oder_value")
    @Expose
    private String minOderValue;
    @SerializedName("delivery_charge")
    @Expose
    private String deliveryCharge;
    @SerializedName("overall_rating")
    @Expose
    private Double overallRating;
    @SerializedName("background_image")
    @Expose
    private String backgroundImage;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCuisines() {
        return cuisines;
    }

    public void setCuisines(String cuisines) {
        this.cuisines = cuisines;
    }

    public String getMinOderValue() {
        return minOderValue;
    }

    public void setMinOderValue(String minOderValue) {
        this.minOderValue = minOderValue;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public Double getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(Double overallRating) {
        this.overallRating = overallRating;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

}
