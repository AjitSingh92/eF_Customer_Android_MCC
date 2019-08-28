
package com.lexxdigital.easyfooduserapps.restaurant_details.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rating {

    @SerializedName("review_rating_id")
    @Expose
    private String reviewRatingId;
    @SerializedName("overall_rating")
    @Expose
    private String overallRating;
    @SerializedName("overall_review")
    @Expose
    private String overallReview;
    @SerializedName("review_date")
    @Expose
    private String reviewDate;
    @SerializedName("user_name")
    @Expose
    private String userName;

    public String getReviewRatingId() {
        return reviewRatingId;
    }

    public void setReviewRatingId(String reviewRatingId) {
        this.reviewRatingId = reviewRatingId;
    }

    public String getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(String overallRating) {
        this.overallRating = overallRating;
    }

    public String getOverallReview() {
        return overallReview;
    }

    public void setOverallReview(String overallReview) {
        this.overallReview = overallReview;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
