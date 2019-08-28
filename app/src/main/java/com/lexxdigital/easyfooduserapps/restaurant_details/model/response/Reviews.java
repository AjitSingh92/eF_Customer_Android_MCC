
package com.lexxdigital.easyfooduserapps.restaurant_details.model.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reviews {

    @SerializedName("rating")
    @Expose
    private List<Rating> rating = null;
    @SerializedName("based_on_review")
    @Expose
    private String basedOnReview;
    @SerializedName("restaurant_overall_rating")
    @Expose
    private String restaurantOverallRating;

    public List<Rating> getRating() {
        return rating;
    }

    public void setRating(List<Rating> rating) {
        this.rating = rating;
    }

    public String getBasedOnReview() {
        return basedOnReview;
    }

    public void setBasedOnReview(String basedOnReview) {
        this.basedOnReview = basedOnReview;
    }

    public String getRestaurantOverallRating() {
        return restaurantOverallRating;
    }

    public void setRestaurantOverallRating(String restaurantOverallRating) {
        this.restaurantOverallRating = restaurantOverallRating;
    }

}
