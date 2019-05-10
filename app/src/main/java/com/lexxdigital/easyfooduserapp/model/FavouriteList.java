package com.lexxdigital.easyfooduserapp.model;

public class FavouriteList {
    protected String EntityID;
    protected String RestaurantName;
    protected String Logo;
    protected String BackImane;
    protected String Cuisines;
    protected String MinOrderValue;
    protected String DeliveryCharge;
    protected Double OverallRating;

    public FavouriteList(String entityID, String restaurantName, String logo, String backImane, String cuisines, String minOrderValue, String deliveryCharge, Double overallRating) {
        EntityID = entityID;
        RestaurantName = restaurantName;
        Logo = logo;
        Cuisines = cuisines;
        MinOrderValue = minOrderValue;
        DeliveryCharge = deliveryCharge;
        OverallRating = overallRating;
        BackImane = backImane;
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
}
