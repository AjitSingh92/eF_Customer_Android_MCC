package com.easyfoodcustomer.api;

import com.easyfoodcustomer.model.my_account_request.MyAccountRequest;
import com.easyfoodcustomer.model.my_account_response.MyAccountResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MyAccountInterface {
    @Headers("Content-Type: application/json")
    @POST("getUserProfile")
    Call<MyAccountResponse> mGetProfile(@Header("Authorization") String Authorization, @Body MyAccountRequest request);
}
