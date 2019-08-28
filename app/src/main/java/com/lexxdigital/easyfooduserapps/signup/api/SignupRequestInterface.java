package com.lexxdigital.easyfooduserapps.signup.api;

import com.lexxdigital.easyfooduserapps.signup.model.request.SignupRequest;
import com.lexxdigital.easyfooduserapps.signup.model.response.SignupResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SignupRequestInterface {
    @Headers("Content-Type: application/json")
    @POST("signup_verification")
    Call<SignupResponse> mLogin(@Body SignupRequest request);
}
