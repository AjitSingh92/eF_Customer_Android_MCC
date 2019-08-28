package com.lexxdigital.easyfooduserapps.api;

import com.lexxdigital.easyfooduserapps.model.makeCardDefault.MakeCardDefReq;
import com.lexxdigital.easyfooduserapps.model.makeCardDefault.MakeCardDefaultRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MakeCardDefaultInterface {
    @Headers("Content-Type: application/json")
    @POST("make_card_default")
    Call<MakeCardDefaultRes> mMakeDefault(@Body MakeCardDefReq request);
}
