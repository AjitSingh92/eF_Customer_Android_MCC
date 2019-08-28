package com.lexxdigital.easyfooduserapps.restaurant_details.api;

import com.lexxdigital.easyfooduserapps.restaurant_details.model.review_request.ReviewRequest;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.review_response.ReviewResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ReviewInterface {
    @Headers("Content-Type: application/json")
    @POST("get-restaurants-all-reviews")
    Call<ReviewResponse> mGetReview(@Body ReviewRequest request);
}
