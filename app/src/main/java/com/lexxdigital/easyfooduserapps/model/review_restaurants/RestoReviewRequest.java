package com.lexxdigital.easyfooduserapps.model.review_restaurants;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestoReviewRequest {
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("overall_rating")
    @Expose
    private String overallRating;
    @SerializedName("food_quality_rating")
    @Expose
    private String foodQualityRating;
    @SerializedName("delivery_rating")
    @Expose
    private String deliveryRating;
    @SerializedName("order_again_rating")
    @Expose
    private String orderAgainRating;
    @SerializedName("recommendation_rating")
    @Expose
    private String recommendationRating;

    public RestoReviewRequest(String restaurantId, String customerId, String orderId, String overallRating, String foodQualityRating, String deliveryRating, String orderAgainRating, String recommendationRating) {
        this.restaurantId = restaurantId;
        this.customerId = customerId;
        this.orderId = orderId;
        this.overallRating = overallRating;
        this.foodQualityRating = foodQualityRating;
        this.deliveryRating = deliveryRating;
        this.orderAgainRating = orderAgainRating;
        this.recommendationRating = recommendationRating;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(String overallRating) {
        this.overallRating = overallRating;
    }

    public String getFoodQualityRating() {
        return foodQualityRating;
    }

    public void setFoodQualityRating(String foodQualityRating) {
        this.foodQualityRating = foodQualityRating;
    }

    public String getDeliveryRating() {
        return deliveryRating;
    }

    public void setDeliveryRating(String deliveryRating) {
        this.deliveryRating = deliveryRating;
    }

    public String getOrderAgainRating() {
        return orderAgainRating;
    }

    public void setOrderAgainRating(String orderAgainRating) {
        this.orderAgainRating = orderAgainRating;
    }

    public String getRecommendationRating() {
        return recommendationRating;
    }

    public void setRecommendationRating(String recommendationRating) {
        this.recommendationRating = recommendationRating;
    }
}
