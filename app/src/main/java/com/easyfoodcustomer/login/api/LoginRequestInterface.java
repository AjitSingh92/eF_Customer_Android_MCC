package com.easyfoodcustomer.login.api;

import com.easyfoodcustomer.login.model.request.LoginRequest;
import com.easyfoodcustomer.login.model.response.CheckAccountBean;
import com.easyfoodcustomer.login.model.response.LoginResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginRequestInterface {
    @POST("login")
    Call<LoginResponse> mLogin(@Header("Authorization") String Authorization, @Body LoginRequest request);

    @POST("accountCheck")
    Call<CheckAccountBean> checkAccount(@Header("Authorization") String Authorization, @Body JsonObject request);
}

