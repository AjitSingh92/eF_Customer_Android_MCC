
package com.lexxdigital.easyfooduserapps.restaurant_details.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Friday {

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
    @SerializedName("restaurant_open_close_status")
    @Expose
    private Boolean restaurantOpenCloseStatus;

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

    public Boolean getRestaurantOpenCloseStatus() {
        return restaurantOpenCloseStatus;
    }

    public void setRestaurantOpenCloseStatus(Boolean restaurantOpenCloseStatus) {
        this.restaurantOpenCloseStatus = restaurantOpenCloseStatus;
    }

}
