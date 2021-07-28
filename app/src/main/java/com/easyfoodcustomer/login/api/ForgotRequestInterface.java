package com.easyfoodcustomer.login.api;

import com.easyfoodcustomer.login.model.forgot_request.ForgotRequest;
import com.easyfoodcustomer.login.model.forgot_response.ForgotResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ForgotRequestInterface {
    @Headers("Content-Type: application/json")
    @POST("forget_password_web")
    Call<ForgotResponse> mLogin(@Header("Authorization") String Authorization, @Body ForgotRequest request);
}
