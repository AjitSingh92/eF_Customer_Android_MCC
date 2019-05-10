package com.lexxdigital.easyfooduserapp.api;

import com.lexxdigital.easyfooduserapp.model.changepassword.ChangePasswordRequest;
import com.lexxdigital.easyfooduserapp.model.changepassword.ChangePasswordResponse;
import com.lexxdigital.easyfooduserapp.model.my_account_request.MyAccountRequest;
import com.lexxdigital.easyfooduserapp.model.my_account_response.MyAccountResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ChangePasswordInterface {
    @Headers("Content-Type: application/json")
    @POST("change_password")
    Call<ChangePasswordResponse> changePassword(@Body ChangePasswordRequest request);
}
