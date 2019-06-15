package com.lexxdigital.easyfooduserapps.api;

import com.lexxdigital.easyfooduserapps.model.card_list_request.CardDeleteReq;
import com.lexxdigital.easyfooduserapps.model.card_list_request.CardListRequest;
import com.lexxdigital.easyfooduserapps.model.card_list_response.CardListResponse;
import com.lexxdigital.easyfooduserapps.model.card_list_response.DeleteCardResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CardListInterface {
    @Headers("Content-Type: application/json")
    @POST("getCustomerCardDetails")
    Call<CardListResponse> mLogin(@Body CardListRequest request);

    @Headers("Content-Type: application/json")
    @POST("deleteCustomerCard")
    Call<DeleteCardResponse> deleteCard(@Body CardDeleteReq deleteReq);

}
