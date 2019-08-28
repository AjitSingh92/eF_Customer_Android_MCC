package com.lexxdigital.easyfooduserapps.api;

import com.lexxdigital.easyfooduserapps.model.add_card_request.CardAddRequest;
import com.lexxdigital.easyfooduserapps.model.add_card_response.CardAddResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AddCardInterface {
    @Headers("Content-Type: application/json")
    @POST("storeCustomerCard")
    Call<CardAddResponse> mLogin(@Body CardAddRequest request);

}
