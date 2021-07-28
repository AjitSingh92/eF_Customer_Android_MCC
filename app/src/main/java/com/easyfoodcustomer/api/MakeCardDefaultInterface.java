package com.easyfoodcustomer.api;

import com.easyfoodcustomer.model.makeCardDefault.MakeCardDefReq;
import com.easyfoodcustomer.model.makeCardDefault.MakeCardDefaultRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MakeCardDefaultInterface {
    @Headers("Content-Type: application/json")
    @POST("make_card_default")
    Call<MakeCardDefaultRes> mMakeDefault(@Header("Authorization") String Authorization, @Body MakeCardDefReq request);
}
