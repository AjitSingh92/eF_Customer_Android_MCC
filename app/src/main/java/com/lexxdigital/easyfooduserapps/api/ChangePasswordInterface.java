package com.lexxdigital.easyfooduserapps.api;

import com.lexxdigital.easyfooduserapps.model.changepassword.ChangePasswordRequest;
import com.lexxdigital.easyfooduserapps.model.changepassword.ChangePasswordResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ChangePasswordInterface {
    @Headers("Content-Type: application/json")
    @POST("change_password")
    Call<ChangePasswordResponse> changePassword(@Body ChangePasswordRequest request);
}
