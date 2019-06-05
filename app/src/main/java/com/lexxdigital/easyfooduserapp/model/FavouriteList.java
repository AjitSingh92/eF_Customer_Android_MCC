package com.lexxdigital.easyfooduserapp.model;

public class FavouriteList {
    private String EntityID;
    private String RestaurantName;
    private String Logo;
    private String BackImane;
    private String Cuisines;
    private String MinOrderValue;
    private String DeliveryCharge;
    private Double OverallRating;
    private String restaurantStatus;

    public FavouriteList(String entityID, String restaurantName, String logo, String backImane, String cuisines, String minOrderValue, String deliveryCharge, Double overallRating, String restaurantStatus) {
        this.EntityID = entityID;
        this.RestaurantName = restaurantName;
        this.Logo = logo;
        this.Cuisines = cuisines;
        this.MinOrderValue = minOrderValue;
        this.DeliveryCharge = deliveryCharge;
        this.OverallRating = overallRating;
        this.BackImane = backImane;
        this.restaurantStatus = restaurantStatus;
    }

    public String getEntityID() {
        return EntityID;
    }

    public void setEntityID(String entityID) {
        EntityID = entityID;
    }

    public String getRestaurantName() {
        return RestaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        RestaurantName = restaurantName;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public String getCuisines() {
        return Cuisines;
    }

    public void setCuisines(String cuisines) {
        Cuisines = cuisines;
    }

    public String getMinOrderValue() {
        return MinOrderValue;
    }

    public void setMinOrderValue(String minOrderValue) {
        MinOrderValue = minOrderValue;
    }

    public String getDeliveryCharge() {
        return DeliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        DeliveryCharge = deliveryCharge;
    }

    public Double getOverallRating() {
        return OverallRating;
    }

    public void setOverallRating(Double overallRating) {
        OverallRating = overallRating;
    }

    public String getBackImane() {
        return BackImane;
    }

    public void setBackImane(String backImane) {
        BackImane = backImane;
    }

    public String getRestaurantStatus() {
        return restaurantStatus;
    }

    public void setRestaurantStatus(String restaurantStatus) {
        this.restaurantStatus = restaurantStatus;
    }
}
