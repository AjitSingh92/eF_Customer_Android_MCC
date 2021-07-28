package com.easyfoodcustomer.api;

import com.google.gson.JsonObject;
import com.easyfoodcustomer.model.edit_account_response.EditAccountResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface VerifyOtpInterface {
    @Headers("Content-Type: application/json")
    @POST("profile_phone_otp_auth")
    Call<EditAccountResponse> verifyOtp(@Header("Authorization") String Authorization, @Body JsonObject request);
}
