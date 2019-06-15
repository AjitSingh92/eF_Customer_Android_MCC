
package com.lexxdigital.easyfooduserapps.model.landing_page_lists;

import java.util.List;

public class LandingPageLists
{

    private String id;
    private String restaurantName;
    private String logo;
    private String restaurantNameSlug;
    private String type;
    private String latitude;
    private String longitude;
    private String cuisines;
    private String deliveryOptions;
    private String avgDeliveryTime;
    private String minOrderValue;
    private String freeDelivery;
    private String deliveryCharge;
    private String deliveryLabel;
    private String distanceInMiles;
    private String overallRating;
    private Integer favourite;
    private List<RestaurantsGallery> restaurantsGallery = null;
    private List<Object> restaurantDeliveryCharge = null;
    private List<DiscountOffer> discountOffers = null;
    private List<RestaurantTiming> restaurantTiming = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LandingPageLists() {
    }

    /**
     * 
     * @param cuisines
     * @param logo
     * @param restaurantTiming
     * @param restaurantNameSlug
     * @param freeDelivery
     * @param type
     * @param deliveryOptions
     * @param deliveryLabel
     * @param deliveryCharge
     * @param discountOffers
     * @param id
     * @param distanceInMiles
     * @param minOrderValue
     * @param restaurantDeliveryCharge
     * @param longitude
     * @param latitude
     * @param avgDeliveryTime
     * @param overallRating
     * @param favourite
     * @param restaurantName
     * @param restaurantsGallery
     */
    public LandingPageLists(String id, String restaurantName, String logo, String restaurantNameSlug, String type, String latitude, String longitude, String cuisines, String deliveryOptions, String avgDeliveryTime, String minOrderValue, String freeDelivery, String deliveryCharge, String deliveryLabel, String distanceInMiles, String overallRating, Integer favourite, List<RestaurantsGallery> restaurantsGallery, List<Object> restaurantDeliveryCharge, List<DiscountOffer> discountOffers, List<RestaurantTiming> restaurantTiming) {
        super();
        this.id = id;
        this.restaurantName = restaurantName;
        this.logo = logo;
        this.restaurantNameSlug = restaurantNameSlug;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cuisines = cuisines;
        this.deliveryOptions = deliveryOptions;
        this.avgDeliveryTime = avgDeliveryTime;
        this.minOrderValue = minOrderValue;
        this.freeDelivery = freeDelivery;
        this.deliveryCharge = deliveryCharge;
        this.deliveryLabel = deliveryLabel;
        this.distanceInMiles = distanceInMiles;
        this.overallRating = overallRating;
        this.favourite = favourite;
        this.restaurantsGallery = restaurantsGallery;
        this.restaurantDeliveryCharge = restaurantDeliveryCharge;
        this.discountOffers = discountOffers;
        this.restaurantTiming = restaurantTiming;
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

    public String getRestaurantNameSlug() {
        return restaurantNameSlug;
    }

    public void setRestaurantNameSlug(String restaurantNameSlug) {
        this.restaurantNameSlug = restaurantNameSlug;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCuisines() {
        return cuisines;
    }

    public void setCuisines(String cuisines) {
        this.cuisines = cuisines;
    }

    public String getDeliveryOptions() {
        return deliveryOptions;
    }

    public void setDeliveryOptions(String deliveryOptions) {
        this.deliveryOptions = deliveryOptions;
    }

    public String getAvgDeliveryTime() {
        return avgDeliveryTime;
    }

    public void setAvgDeliveryTime(String avgDeliveryTime) {
        this.avgDeliveryTime = avgDeliveryTime;
    }

    public String getMinOrderValue() {
        return minOrderValue;
    }

    public void setMinOrderValue(String minOrderValue) {
        this.minOrderValue = minOrderValue;
    }

    public String getFreeDelivery() {
        return freeDelivery;
    }

    public void setFreeDelivery(String freeDelivery) {
        this.freeDelivery = freeDelivery;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getDeliveryLabel() {
        return deliveryLabel;
    }

    public void setDeliveryLabel(String deliveryLabel) {
        this.deliveryLabel = deliveryLabel;
    }

    public String getDistanceInMiles() {
        return distanceInMiles;
    }

    public void setDistanceInMiles(String distanceInMiles) {
        this.distanceInMiles = distanceInMiles;
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

    public List<Object> getRestaurantDeliveryCharge() {
        return restaurantDeliveryCharge;
    }

    public void setRestaurantDeliveryCharge(List<Object> restaurantDeliveryCharge) {
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

}
