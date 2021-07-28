package com.easyfoodcustomer.api;

import com.easyfoodcustomer.model.logout.LogoutRequest;
import com.easyfoodcustomer.model.logout.LogoutResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LogoutApiInterface {

    @Headers("Content-Type: application/json")
    @POST("customer_logout")
    Call<LogoutResponse> logout(@Header("Authorization") String Authorization, @Body LogoutRequest request);
}
