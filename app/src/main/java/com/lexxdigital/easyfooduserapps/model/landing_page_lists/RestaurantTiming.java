
package com.lexxdigital.easyfooduserapps.model.landing_page_lists;


public class RestaurantTiming {

    private String id;
    private String restaurantId;
    private String day;
    private String openingStartTime;
    private String openingEndTime;
    private String collectionStartTime;
    private String collectionEndTime;
    private String deliveryStartTime;
    private String deliveryEndTime;
    private String status;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RestaurantTiming() {
    }

    /**
     * 
     * @param id
     * @param deliveryStartTime
     * @param status
     * @param openingEndTime
     * @param deliveryEndTime
     * @param collectionStartTime
     * @param day
     * @param openingStartTime
     * @param collectionEndTime
     * @param restaurantId
     */
    public RestaurantTiming(String id, String restaurantId, String day, String openingStartTime, String openingEndTime, String collectionStartTime, String collectionEndTime, String deliveryStartTime, String deliveryEndTime, String status) {
        super();
        this.id = id;
        this.restaurantId = restaurantId;
        this.day = day;
        this.openingStartTime = openingStartTime;
        this.openingEndTime = openingEndTime;
        this.collectionStartTime = collectionStartTime;
        this.collectionEndTime = collectionEndTime;
        this.deliveryStartTime = deliveryStartTime;
        this.deliveryEndTime = deliveryEndTime;
        this.status = status;
    }

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
