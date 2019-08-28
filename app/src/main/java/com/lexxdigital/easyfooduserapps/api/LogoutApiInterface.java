package com.lexxdigital.easyfooduserapps.api;

import com.lexxdigital.easyfooduserapps.model.logout.LogoutRequest;
import com.lexxdigital.easyfooduserapps.model.logout.LogoutResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LogoutApiInterface {

    @Headers("Content-Type: application/json")
    @POST("customer_logout")
    Call<LogoutResponse> logout(@Body LogoutRequest request);
}
