package com.easyfoodcustomer.signup.api;

import com.easyfoodcustomer.login.model.response.LoginResponse;
import com.easyfoodcustomer.signup.model.final_request.SignupFinalRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FinalSignupInterface {
    @Headers("Content-Type: application/json")
    @POST("signup")
    Call<LoginResponse> mLogin( @Header("Authorization") String Authorization, @Body SignupFinalRequest request);
}
