package com.lexxdigital.easyfooduserapps.model;

import java.util.List;

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
    private String distance_in_miles;
    private List<RestaurantTimingList> restaurantTimingLists;

    public FavouriteList(String entityID, String restaurantName, String logo, String backImane, String cuisines, String minOrderValue, String deliveryCharge, Double overallRating, String restaurantStatus, String distance_in_miles, List<RestaurantTimingList> restaurantTimingLists) {
        EntityID = entityID;
        RestaurantName = restaurantName;
        Logo = logo;
        BackImane = backImane;
        Cuisines = cuisines;
        this.MinOrderValue = minOrderValue;
        DeliveryCharge = deliveryCharge;
        OverallRating = overallRating;
        this.restaurantStatus = restaurantStatus;
        this.distance_in_miles = distance_in_miles;
        this.restaurantTimingLists = restaurantTimingLists;
    }

    public static class RestaurantTimingList {

        private String id;
        private String restaurant_id;
        private String day;
        private String opening_start_time;
        private String opening_end_time;
        private String collection_start_time;
        private String collection_end_time;
        private String delivery_start_time;
        private String delivery_end_time;
        private String status;

        public RestaurantTimingList(String id, String restaurant_id, String day, String opening_start_time, String opening_end_time, String collection_start_time, String collection_end_time, String delivery_start_time, String delivery_end_time, String status) {
            this.id = id;
            this.restaurant_id = restaurant_id;
            this.day = day;
            this.opening_start_time = opening_start_time;
            this.opening_end_time = opening_end_time;
            this.collection_start_time = collection_start_time;
            this.collection_end_time = collection_end_time;
            this.delivery_start_time = delivery_start_time;
            this.delivery_end_time = delivery_end_time;
            this.status = status;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRestaurant_id() {
            return restaurant_id;
        }

        public void setRestaurant_id(String restaurant_id) {
            this.restaurant_id = restaurant_id;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getOpening_start_time() {
            return opening_start_time;
        }

        public void setOpening_start_time(String opening_start_time) {
            this.opening_start_time = opening_start_time;
        }

        public String getOpening_end_time() {
            return opening_end_time;
        }

        public void setOpening_end_time(String opening_end_time) {
            this.opening_end_time = opening_end_time;
        }

        public String getCollection_start_time() {
            return collection_start_time;
        }

        public void setCollection_start_time(String collection_start_time) {
            this.collection_start_time = collection_start_time;
        }

        public String getCollection_end_time() {
            return collection_end_time;
        }

        public void setCollection_end_time(String collection_end_time) {
            this.collection_end_time = collection_end_time;
        }

        public String getDelivery_start_time() {
            return delivery_start_time;
        }

        public void setDelivery_start_time(String delivery_start_time) {
            this.delivery_start_time = delivery_start_time;
        }

        public String getDelivery_end_time() {
            return delivery_end_time;
        }

        public void setDelivery_end_time(String delivery_end_time) {
            this.delivery_end_time = delivery_end_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
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


    public String getDistance_in_miles() {
        return distance_in_miles;
    }

    public void setDistance_in_miles(String distance_in_miles) {
        this.distance_in_miles = distance_in_miles;
    }

    public List<RestaurantTimingList> getRestaurantTimingLists() {
        return restaurantTimingLists;
    }

    public void setRestaurantTimingLists(List<RestaurantTimingList> restaurantTimingLists) {
        this.restaurantTimingLists = restaurantTimingLists;
    }
}
