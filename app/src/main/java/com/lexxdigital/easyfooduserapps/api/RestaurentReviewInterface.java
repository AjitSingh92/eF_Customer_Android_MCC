package com.lexxdigital.easyfooduserapps.api;

import com.lexxdigital.easyfooduserapps.model.review_restaurants.RestoReviewRequest;
import com.lexxdigital.easyfooduserapps.model.review_restaurants.RestoReviewResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RestaurentReviewInterface {
    @Headers("Content-Type: application/json")
    @POST("restaurants_review_rating")
    Call<RestoReviewResponse> mRestoRiview(@Body RestoReviewRequest request);

}
