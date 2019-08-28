
package com.lexxdigital.easyfooduserapps.restaurant_details.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

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
    @SerializedName("distance_in_miles")
    @Expose
    private String distanceInMiles;
    @SerializedName("delivery_in_miles")
    @Expose
    private String deliveryInMiles;
    @SerializedName("delivery_options")
    @Expose
    private String deliveryOptions;
    @SerializedName("serve_style")
    @Expose
    private String serveStyle;
    @SerializedName("restaurant_cuisines")
    @Expose
    private String restaurantCuisines;
    @SerializedName("min_order_value")
    @Expose
    private String minOrderValue;
    @SerializedName("delivery_charge")
    @Expose
    private String deliveryCharge;
    @SerializedName("avg_rating")
    @Expose
    private String avgRating;
    @SerializedName("avg_delivery_time")
    @Expose
    private String avgDeliveryTime;
    @SerializedName("free_delivery")
    @Expose
    private String freeDelivery;
    @SerializedName("delivery_label")
    @Expose
    private String deliveryLabel;
    @SerializedName("favourite")
    @Expose
    private Integer favourite;
    @SerializedName("info")
    @Expose
    private Info info;
    @SerializedName("menu")
    @Expose
    private Menu menu;
    @SerializedName("reviews")
    @Expose
    private Reviews reviews;

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

    public String getDistanceInMiles() {
        return distanceInMiles;
    }

    public void setDistanceInMiles(String distanceInMiles) {
        this.distanceInMiles = distanceInMiles;
    }

    public String getDeliveryInMiles() {
        return deliveryInMiles;
    }

    public void setDeliveryInMiles(String deliveryInMiles) {
        this.deliveryInMiles = deliveryInMiles;
    }

    public String getDeliveryOptions() {
        return deliveryOptions;
    }

    public void setDeliveryOptions(String deliveryOptions) {
        this.deliveryOptions = deliveryOptions;
    }

    public String getServeStyle() {
        return serveStyle;
    }

    public void setServeStyle(String serveStyle) {
        this.serveStyle = serveStyle;
    }

    public String getRestaurantCuisines() {
        return restaurantCuisines;
    }

    public void setRestaurantCuisines(String restaurantCuisines) {
        this.restaurantCuisines = restaurantCuisines;
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

    public String getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(String avgRating) {
        this.avgRating = avgRating;
    }

    public String getAvgDeliveryTime() {
        return avgDeliveryTime;
    }

    public void setAvgDeliveryTime(String avgDeliveryTime) {
        this.avgDeliveryTime = avgDeliveryTime;
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

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Reviews getReviews() {
        return reviews;
    }

    public void setReviews(Reviews reviews) {
        this.reviews = reviews;
    }

}
