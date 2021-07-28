package com.easyfoodcustomer.restaurant_details.api;

import com.easyfoodcustomer.restaurant_details.model.review_request.ReviewRequest;
import com.easyfoodcustomer.restaurant_details.model.review_response.ReviewResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ReviewInterface {
    @Headers("Content-Type: application/json")
    @POST("get-restaurants-all-reviews")
    Call<ReviewResponse> mGetReview(@Header("Authorization") String Authorization, @Body ReviewRequest request);
}
