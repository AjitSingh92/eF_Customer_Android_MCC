package com.easyfoodcustomer.api;

import com.easyfoodcustomer.model.changepassword.ChangePasswordRequest;
import com.easyfoodcustomer.model.changepassword.ChangePasswordResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ChangePasswordInterface {
    @Headers("Content-Type: application/json")
    @POST("change_password")
    Call<ChangePasswordResponse> changePassword(@Header("Authorization") String Authorization, @Body ChangePasswordRequest request);
}
