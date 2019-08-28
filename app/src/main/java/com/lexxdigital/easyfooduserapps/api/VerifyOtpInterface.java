package com.lexxdigital.easyfooduserapps.api;

import com.google.gson.JsonObject;
import com.lexxdigital.easyfooduserapps.model.edit_account_response.EditAccountResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface VerifyOtpInterface {
    @Headers("Content-Type: application/json")
    @POST("profile_phone_otp_auth")
    Call<EditAccountResponse> verifyOtp(@Body JsonObject request);
}
