package com.lexxdigital.easyfooduserapp.api;

import com.lexxdigital.easyfooduserapp.model.order_details.OrderDetailsRequest;
import com.lexxdigital.easyfooduserapp.model.order_details.OrderDetailsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OrderDetailsInterface {
    @Headers("Content-Type: application/json")
    @POST("order_details")
    Call<OrderDetailsResponse> mOrderDetails(@Body OrderDetailsRequest request);
}
