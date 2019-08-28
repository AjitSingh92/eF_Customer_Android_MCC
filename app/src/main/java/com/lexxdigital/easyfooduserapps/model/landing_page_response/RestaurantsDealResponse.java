
package com.lexxdigital.easyfooduserapps.model.landing_page_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantsDealResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    public Data data;
    @SerializedName("errors")
    @Expose
    private Errors errors;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public class Data {

        @SerializedName("restaurants")
        @Expose
        private List<Restaurant> restaurants;
        @SerializedName("total_records")
        @Expose
        private Integer totalRecords;

        public List<Restaurant> getRestaurants() {
            return restaurants;
        }

        public void setRestaurants(List<Restaurant> restaurants) {
            this.restaurants = restaurants;
        }

        public Integer getTotalRecords() {
            return totalRecords;
        }

        public void setTotalRecords(Integer totalRecords) {
            this.totalRecords = totalRecords;
        }

        public class Restaurant {

            @SerializedName("id")
            @Expose
            private String id;

            @SerializedName("delivery_options")
            @Expose
            private String delivery_options;

            @SerializedName("restaurant_name")
            @Expose
            private String restaurantName;

            public String getDistance_in_miles() {
                return distance_in_miles;
            }

            public void setDistance_in_miles(String distance_in_miles) {
                this.distance_in_miles = distance_in_miles;
            }

            @SerializedName("distance_in_miles")
            @Expose
            private String distance_in_miles;

            @SerializedName("serve_style")
            @Expose
            private String serve_style;

            @SerializedName("logo")
            @Expose
            private String logo;
            @SerializedName("cuisines")
            @Expose
            private String cuisines;
            @SerializedName("avg_delivery_time")
            @Expose
            private String avgDeliveryTime;
            @SerializedName("overall_rating")
            @Expose
            private String overallRating;
            @SerializedName("delivery_charge")
            private String delivery_charge;
            @SerializedName("min_order_value")
            @Expose
            private String min_order_value;
            @SerializedName("favourite")
            @Expose
            private Integer favourite;
            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("restaurants_gallery")
            @Expose
            private List<RestaurantsGallery> restaurantsGallery = null;
            @SerializedName("restaurant_delivery_charge")
            @Expose
            private List<RestaurantDeliveryCharge> restaurantDeliveryCharge = null;
            @SerializedName("discount_offers")
            @Expose
            private List<DiscountOffer> discountOffers = null;
            @SerializedName("restaurant_timing")
            @Expose
            private List<RestaurantTiming> restaurantTiming = null;

            public String getDelivery_charge() {
                return delivery_charge;
            }

            public void setDelivery_charge(String delivery_charge) {
                this.delivery_charge = delivery_charge;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getMin_order_value() {
                return min_order_value;
            }

            public void setMin_order_value(String min_order_value) {
                this.min_order_value = min_order_value;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getAvgDeliveryTime() {
                return avgDeliveryTime;
            }

            public void setAvgDeliveryTime(String avgDeliveryTime) {
                this.avgDeliveryTime = avgDeliveryTime;
            }

            public String getOverallRating() {
                return overallRating;
            }

            public void setOverallRating(String overallRating) {
                this.overallRating = overallRating;
            }

            public Integer getFavourite() {
                return favourite;
            }

            public void setFavourite(Integer favourite) {
                this.favourite = favourite;
            }

            public List<RestaurantsGallery> getRestaurantsGallery() {
                return restaurantsGallery;
            }

            public void setRestaurantsGallery(List<RestaurantsGallery> restaurantsGallery) {
                this.restaurantsGallery = restaurantsGallery;
            }

            public List<RestaurantDeliveryCharge> getRestaurantDeliveryCharge() {
                return restaurantDeliveryCharge;
            }

            public void setRestaurantDeliveryCharge(List<RestaurantDeliveryCharge> restaurantDeliveryCharge) {
                this.restaurantDeliveryCharge = restaurantDeliveryCharge;
            }

            public List<DiscountOffer> getDiscountOffers() {
                return discountOffers;
            }

            public void setDiscountOffers(List<DiscountOffer> discountOffers) {
                this.discountOffers = discountOffers;
            }

            public List<RestaurantTiming> getRestaurantTiming() {
                return restaurantTiming;
            }

            public void setRestaurantTiming(List<RestaurantTiming> restaurantTiming) {
                this.restaurantTiming = restaurantTiming;
            }

            public String getDelivery_options() {
                return delivery_options;
            }

            public void setDelivery_options(String delivery_options) {
                this.delivery_options = delivery_options;
            }

            public String getServe_style() {
                return serve_style;
            }

            public void setServe_style(String serve_style) {
                this.serve_style = serve_style;
            }
        }


    }


}
