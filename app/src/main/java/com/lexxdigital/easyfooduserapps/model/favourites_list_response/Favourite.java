
package com.lexxdigital.easyfooduserapps.model.favourites_list_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
    @SerializedName("min_order_value")
    @Expose
    private String min_order_value;
    @SerializedName("delivery_charge")
    @Expose
    private String deliveryCharge;
    @SerializedName("overall_rating")
    @Expose
    private Double overallRating;
    @SerializedName("background_image")
    @Expose
    private String backgroundImage;
    @SerializedName("restaurant_status")
    @Expose
    private String restaurantStatus;
    @SerializedName("distance_in_miles")
    @Expose
    private String distance_in_miles;

    @SerializedName("restaurant_timing")
    @Expose
    private List<RestaurantTiming> restaurantTiming = null;


    public static class RestaurantTiming {
        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("restaurant_id")
        @Expose
        private String restaurant_id;

        @SerializedName("day")
        @Expose
        private String day;

        @SerializedName("opening_start_time")
        @Expose
        private String opening_start_time;

        @SerializedName("opening_end_time")
        @Expose
        private String opening_end_time;

        @SerializedName("collection_start_time")
        @Expose
        private String collection_start_time;

        @SerializedName("collection_end_time")
        @Expose
        private String collection_end_time;

        @SerializedName("delivery_start_time")
        @Expose
        private String delivery_start_time;

        @SerializedName("delivery_end_time")
        @Expose
        private String delivery_end_time;

        @SerializedName("status")
        @Expose
        private String status;

        public RestaurantTiming() {
        }

        public RestaurantTiming(String id, String restaurant_id, String day, String opening_start_time, String opening_end_time, String collection_start_time, String collection_end_time, String delivery_start_time, String delivery_end_time, String status) {
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

        @Override
        public String toString() {
            return "RestaurantTiming{" +
                    "id='" + id + '\'' +
                    ", restaurant_id='" + restaurant_id + '\'' +
                    ", day='" + day + '\'' +
                    ", opening_start_time='" + opening_start_time + '\'' +
                    ", opening_end_time='" + opening_end_time + '\'' +
                    ", collection_start_time='" + collection_start_time + '\'' +
                    ", collection_end_time='" + collection_end_time + '\'' +
                    ", delivery_start_time='" + delivery_start_time + '\'' +
                    ", delivery_end_time='" + delivery_end_time + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }

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
        return min_order_value;
    }

    public void setMinOderValue(String minOderValue) {
        this.min_order_value = minOderValue;
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

    public List<RestaurantTiming> getRestaurantTiming() {
        return restaurantTiming;
    }

    public void setRestaurantTiming(List<RestaurantTiming> restaurantTiming) {
        this.restaurantTiming = restaurantTiming;
    }

    @Override
    public String toString() {
        return "Favourite{" +
                "userId='" + userId + '\'' +
                ", entityId='" + entityId + '\'' +
                ", entityType='" + entityType + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                ", logo='" + logo + '\'' +
                ", cuisines='" + cuisines + '\'' +
                ", minOderValue='" + min_order_value + '\'' +
                ", deliveryCharge='" + deliveryCharge + '\'' +
                ", overallRating=" + overallRating +
                ", backgroundImage='" + backgroundImage + '\'' +
                ", restaurantStatus='" + restaurantStatus + '\'' +
                ", restaurantTiming=" + restaurantTiming +
                '}';
    }
}
