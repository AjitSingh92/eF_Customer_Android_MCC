
package com.easyfoodcustomer.model.landing_page_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantsDealResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("android_version")
    @Expose
    private String android_version;
    @SerializedName("is_hard_update")
    @Expose
    private String is_hard_update;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAndroid_version() {
        return android_version;
    }

    public void setAndroid_version(String android_version) {
        this.android_version = android_version;
    }

    public String getIs_hard_update() {
        return is_hard_update;
    }

    public void setIs_hard_update(String is_hard_update) {
        this.is_hard_update = is_hard_update;
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

        @SerializedName("app_restaurants")
        @Expose
        private List<Restaurant> restaurants;
        @SerializedName("total_records")
        @Expose
        private Integer totalRecords;


        @SerializedName("limit")
        @Expose
        private Integer limit;



        @SerializedName("current_records")
        @Expose
        private Integer current_records;


        @SerializedName("offset")
        @Expose
        private Integer offset;

        public Integer getLimit() {
            return limit;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        public Integer getCurrent_records() {
            return current_records;
        }

        public void setCurrent_records(Integer current_records) {
            this.current_records = current_records;
        }

        public Integer getOffset() {
            return offset;
        }

        public void setOffset(Integer offset) {
            this.offset = offset;
        }

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

            @SerializedName("ispartnerdelivery")
            @Expose
            private Integer ispartnerdelivery;

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
            @SerializedName("mode")
            @Expose
            private String mode;
            @SerializedName("favourite")
            @Expose
            private Integer favourite;
            @SerializedName("status")
            @Expose
            private String status;

            @SerializedName("delivery_label")
            @Expose
            private String delivery_label;

            @SerializedName("service_charge")
            @Expose
            private String service_charge;



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

            public String getService_charge() {
                return service_charge;
            }

            public void setService_charge(String service_charge) {
                this.service_charge = service_charge;
            }

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

            public String getDelivery_label() {
                return delivery_label;
            }

            public void setDelivery_label(String delivery_label) {
                this.delivery_label = delivery_label;
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

            public Integer getIspartnerdelivery() {
                return ispartnerdelivery;
            }

            public void setIspartnerdelivery(Integer ispartnerdelivery) {
                this.ispartnerdelivery = ispartnerdelivery;
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

            public String getMode() {
                return mode;
            }

            public void setMode(String mode) {
                this.mode = mode;
            }
        }


    }


}
