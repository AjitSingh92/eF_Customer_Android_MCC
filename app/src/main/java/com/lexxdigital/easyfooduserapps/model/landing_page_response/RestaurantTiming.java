
package com.lexxdigital.easyfooduserapps.model.landing_page_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantTiming {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("opening_start_time")
    @Expose
    private String openingStartTime;
    @SerializedName("opening_end_time")
    @Expose
    private String openingEndTime;
    @SerializedName("collection_start_time")
    @Expose
    private String collectionStartTime;
    @SerializedName("collection_end_time")
    @Expose
    private String collectionEndTime;
    @SerializedName("delivery_start_time")
    @Expose
    private String deliveryStartTime;
    @SerializedName("delivery_end_time")
    @Expose
    private String deliveryEndTime;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getOpeningStartTime() {
        return openingStartTime;
    }

    public void setOpeningStartTime(String openingStartTime) {
        this.openingStartTime = openingStartTime;
    }

    public String getOpeningEndTime() {
        return openingEndTime;
    }

    public void setOpeningEndTime(String openingEndTime) {
        this.openingEndTime = openingEndTime;
    }

    public String getCollectionStartTime() {
        return collectionStartTime;
    }

    public void setCollectionStartTime(String collectionStartTime) {
        this.collectionStartTime = collectionStartTime;
    }

    public String getCollectionEndTime() {
        return collectionEndTime;
    }

    public void setCollectionEndTime(String collectionEndTime) {
        this.collectionEndTime = collectionEndTime;
    }

    public String getDeliveryStartTime() {
        return deliveryStartTime;
    }

    public void setDeliveryStartTime(String deliveryStartTime) {
        this.deliveryStartTime = deliveryStartTime;
    }

    public String getDeliveryEndTime() {
        return deliveryEndTime;
    }

    public void setDeliveryEndTime(String deliveryEndTime) {
        this.deliveryEndTime = deliveryEndTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
