
package com.lexxdigital.easyfooduserapps.restaurant_details.model.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("timings")
    @Expose
    private Timings timings;
    @SerializedName("restaurants_gallery_images")
    @Expose
    private List<RestaurantsGalleryImage> restaurantsGalleryImages = null;
    @SerializedName("delivery_area")
    @Expose
    private List<String> deliveryArea = null;

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Timings getTimings() {
        return timings;
    }

    public void setTimings(Timings timings) {
        this.timings = timings;
    }

    public List<RestaurantsGalleryImage> getRestaurantsGalleryImages() {
        return restaurantsGalleryImages;
    }

    public void setRestaurantsGalleryImages(List<RestaurantsGalleryImage> restaurantsGalleryImages) {
        this.restaurantsGalleryImages = restaurantsGalleryImages;
    }

    public List<String> getDeliveryArea() {
        return deliveryArea;
    }

    public void setDeliveryArea(List<String> deliveryArea) {
        this.deliveryArea = deliveryArea;
    }

}
