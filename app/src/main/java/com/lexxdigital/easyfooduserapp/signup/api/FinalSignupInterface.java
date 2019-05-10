package com.lexxdigital.easyfooduserapp.signup.api;

import com.lexxdigital.easyfooduserapp.login.model.response.LoginResponse;
import com.lexxdigital.easyfooduserapp.signup.model.final_request.SignupFinalRequest;
import com.lexxdigital.easyfooduserapp.signup.model.final_response.SignupFinalResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FinalSignupInterface {
    @Headers("Content-Type: application/json")
    @POST("signup")
    Call<LoginResponse> mLogin(@Body SignupFinalRequest request);
}
