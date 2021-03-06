
package com.easyfoodcustomer.restaurant_details.model.review_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("review_rating_id")
    @Expose
    private String reviewRatingId;
    @SerializedName("overall_rating")
    @Expose
    private String overallRating;
    @SerializedName("overall_review")
    @Expose
    private String overallReview;

    @SerializedName("userRating")
    @Expose
    private String userRating;

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

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
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
