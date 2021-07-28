package com.easyfoodcustomer.signup.api;

import com.easyfoodcustomer.signup.model.request.SignupRequest;
import com.easyfoodcustomer.signup.model.response.SignupResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SignupRequestInterface {
    @Headers("Content-Type: application/json")
    @POST("signup_verification")
    Call<SignupResponse> mLogin(@Header("Authorization") String Authorization, @Body SignupRequest request);
}
