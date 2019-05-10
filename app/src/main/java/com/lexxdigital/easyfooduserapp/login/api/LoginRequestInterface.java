package com.lexxdigital.easyfooduserapp.login.api;

import com.lexxdigital.easyfooduserapp.login.model.request.LoginRequest;
import com.lexxdigital.easyfooduserapp.login.model.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginRequestInterface {
    @Headers("Content-Type: application/json")
    @POST("login")
    Call<LoginResponse> mLogin(@Body LoginRequest request);
}

