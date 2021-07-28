package com.easyfoodcustomer.api;

import com.easyfoodcustomer.model.card_list_request.CardDeleteReq;
import com.easyfoodcustomer.model.card_list_request.CardListRequest;
import com.easyfoodcustomer.model.card_list_response.CardListResponse;
import com.easyfoodcustomer.model.card_list_response.DeleteCardResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CardListInterface {
    @Headers("Content-Type: application/json")
    @POST("getCustomerCardDetails")
    Call<CardListResponse> mLogin(@Header("Authorization") String Authorization, @Body CardListRequest request);

    @Headers("Content-Type: application/json")
    @POST("deleteCustomerCard")
    Call<DeleteCardResponse> deleteCard(@Header("Authorization") String Authorization,@Body CardDeleteReq deleteReq);

}
