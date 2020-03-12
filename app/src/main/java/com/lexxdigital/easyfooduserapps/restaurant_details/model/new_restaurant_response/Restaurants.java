
package com.lexxdigital.easyfooduserapps.restaurant_details.model.new_restaurant_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Restaurants {

    @SerializedName("restaurant_cuisines")
    @Expose
    private String restaurantCuisines;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
    @SerializedName("restaurant_logo")
    @Expose
    private String restaurantLogo;
    @SerializedName("restaurant_image")
    @Expose
    private String restaurantImage;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("landline_number")
    @Expose
    private String landlineNumber;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("post_code")
    @Expose
    private String postCode;
    @SerializedName("website_url")
    @Expose
    private String websiteUrl;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("delivery_areas")
    @Expose
    private List<DeliveryAreasBean> deliveryAreas;

    @SerializedName("distance_in_miles")
    @Expose
    private double distanceInMiles;
    @SerializedName("delivery_in_miles")
    @Expose
    private Integer deliveryInMiles;
    @SerializedName("user_have_ordered")
    @Expose
    private Integer userHaveOrdered;
    @SerializedName("min_order_value")
    @Expose
    private String minOrderValue;
    @SerializedName("delivery_charge")
    @Expose
    private String deliveryCharge;
    @SerializedName("avg_delivery_time")
    @Expose
    private Integer avgDeliveryTime;
    @SerializedName("avg_preparation_time")
    @Expose
    private Integer avgPreparationTime;
    @SerializedName("free_delivery")
    @Expose
    private String freeDelivery;
    @SerializedName("delivery_label")
    @Expose
    private String deliveryLabel;
    @SerializedName("avg_rating")
    @Expose
    private Double avgRating;
    @SerializedName("favourite")
    @Expose
    private Integer favourite;
    @SerializedName("delivery_options")
    @Expose
    private String deliveryOptions;
    @SerializedName("info")
    @Expose
    private Info info;
    @SerializedName("rating_count")
    @Expose
    private Double ratingCount;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("restaurant_slug")
    @Expose
    private String restaurantSlug;


    public String getRestaurantCuisines() {
        return restaurantCuisines;
    }

    public void setRestaurantCuisines(String restaurantCuisines) {
        this.restaurantCuisines = restaurantCuisines;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantLogo() {
        return restaurantLogo;
    }

    public void setRestaurantLogo(String restaurantLogo) {
        this.restaurantLogo = restaurantLogo;
    }

    public String getRestaurantImage() {
        return restaurantImage;
    }

    public void setRestaurantImage(String restaurantImage) {
        this.restaurantImage = restaurantImage;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLandlineNumber() {
        return landlineNumber;
    }

    public void setLandlineNumber(String landlineNumber) {
        this.landlineNumber = landlineNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public List<DeliveryAreasBean> getDeliveryAreas() {
        return deliveryAreas;
    }

    public void setDeliveryAreas(List<DeliveryAreasBean> deliveryAreas) {
        this.deliveryAreas = deliveryAreas;
    }

    public double getDistanceInMiles() {
        return distanceInMiles;
    }

    public void setDistanceInMiles(double distanceInMiles) {
        this.distanceInMiles = distanceInMiles;
    }

    public Integer getDeliveryInMiles() {
        return deliveryInMiles;
    }

    public void setDeliveryInMiles(Integer deliveryInMiles) {
        this.deliveryInMiles = deliveryInMiles;
    }

    public Integer getUserHaveOrdered() {
        return userHaveOrdered;
    }

    public void setUserHaveOrdered(Integer userHaveOrdered) {
        this.userHaveOrdered = userHaveOrdered;
    }

    public String getMinOrderValue() {
        return minOrderValue;
    }

    public void setMinOrderValue(String minOrderValue) {
        this.minOrderValue = minOrderValue;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public Integer getAvgDeliveryTime() {
        return avgDeliveryTime;
    }

    public void setAvgDeliveryTime(Integer avgDeliveryTime) {
        this.avgDeliveryTime = avgDeliveryTime;
    }

    public Integer getAvgPreparationTime() {
        return avgPreparationTime;
    }

    public void setAvgPreparationTime(Integer avgPreparationTime) {
        this.avgPreparationTime = avgPreparationTime;
    }

    public String getFreeDelivery() {
        return freeDelivery;
    }

    public void setFreeDelivery(String freeDelivery) {
        this.freeDelivery = freeDelivery;
    }

    public String getDeliveryLabel() {
        return deliveryLabel;
    }

    public void setDeliveryLabel(String deliveryLabel) {
        this.deliveryLabel = deliveryLabel;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public Integer getFavourite() {
        return favourite;
    }

    public void setFavourite(Integer favourite) {
        this.favourite = favourite;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public Double getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Double ratingCount) {
        this.ratingCount = ratingCount;
    }

    public String getDeliveryOptions() {
        return deliveryOptions;
    }

    public void setDeliveryOptions(String deliveryOptions) {
        this.deliveryOptions = deliveryOptions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRestaurantSlug() {
        return restaurantSlug;
    }

    public void setRestaurantSlug(String restaurantSlug) {
        this.restaurantSlug = restaurantSlug;
    }

    @Override
    public String toString() {
        return "Restaurants{" +
                "restaurantCuisines='" + restaurantCuisines + '\'' +
                ", restaurantId='" + restaurantId + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                ", restaurantLogo='" + restaurantLogo + '\'' +
                ", restaurantImage='" + restaurantImage + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", landlineNumber='" + landlineNumber + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", postCode='" + postCode + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", deliveryAreas='" + deliveryAreas + '\'' +
                ", distanceInMiles=" + distanceInMiles +
                ", deliveryInMiles=" + deliveryInMiles +
                ", userHaveOrdered=" + userHaveOrdered +
                ", minOrderValue='" + minOrderValue + '\'' +
                ", deliveryCharge='" + deliveryCharge + '\'' +
                ", avgDeliveryTime=" + avgDeliveryTime +
                ", avgPreparationTime=" + avgPreparationTime +
                ", freeDelivery='" + freeDelivery + '\'' +
                ", deliveryLabel='" + deliveryLabel + '\'' +
                ", avgRating=" + avgRating +
                ", favourite=" + favourite +
                ", deliveryOptions='" + deliveryOptions + '\'' +
                ", info=" + info +
                ", ratingCount=" + ratingCount +
                ", status='" + status + '\'' +
                ", restaurantSlug='" + restaurantSlug + '\'' +
                '}';
    }

    public static class DeliveryAreasBean {
        /**
         * id : 1ffada62-27c7-11ea-8f25-0657952ed75a
         * parent_id : 0
         * restaurant_id : 2a46ae2e-8dda-11e9-a995-0657952ed75a
         * postcode : b13
         * delivery_min_value : 5.00
         * ship_cost : 2.00
         * free_delivery_over : 15.00
         * is_primary : 1
         * created_at : 2019-12-26 10:04:37
         * updated_at : 2019-12-26 10:04:59
         */

        private String id;
        private String parent_id;
        private String restaurant_id;
        private String postcode;
        private String delivery_min_value;
        private String ship_cost;
        private String free_delivery_over;
        private int is_primary;
        private String created_at;
        private String updated_at;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getRestaurant_id() {
            return restaurant_id;
        }

        public void setRestaurant_id(String restaurant_id) {
            this.restaurant_id = restaurant_id;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getDelivery_min_value() {
            return delivery_min_value;
        }

        public void setDelivery_min_value(String delivery_min_value) {
            this.delivery_min_value = delivery_min_value;
        }

        public String getShip_cost() {
            return ship_cost;
        }

        public void setShip_cost(String ship_cost) {
            this.ship_cost = ship_cost;
        }

        public String getFree_delivery_over() {
            return free_delivery_over;
        }

        public void setFree_delivery_over(String free_delivery_over) {
            this.free_delivery_over = free_delivery_over;
        }

        public int getIs_primary() {
            return is_primary;
        }

        public void setIs_primary(int is_primary) {
            this.is_primary = is_primary;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }
}
