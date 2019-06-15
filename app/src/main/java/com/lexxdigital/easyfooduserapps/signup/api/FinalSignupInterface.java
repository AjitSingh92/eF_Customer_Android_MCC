package com.lexxdigital.easyfooduserapps.signup.api;

import com.lexxdigital.easyfooduserapps.login.model.response.LoginResponse;
import com.lexxdigital.easyfooduserapps.signup.model.final_request.SignupFinalRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FinalSignupInterface {
    @Headers("Content-Type: application/json")
    @POST("signup")
    Call<LoginResponse> mLogin(@Body SignupFinalRequest request);
}
