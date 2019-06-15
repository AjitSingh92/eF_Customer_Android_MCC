package com.lexxdigital.easyfooduserapps.signup.api;

import com.lexxdigital.easyfooduserapps.signup.model.send_again_request.SendAgainRequest;
import com.lexxdigital.easyfooduserapps.signup.model.send_again_response.SendAgainResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SendAgainInterface {
    @Headers("Content-Type: application/json")
    @POST("send_otp")
    Call<SendAgainResponse> mSendOTP(@Body SendAgainRequest request);
}
