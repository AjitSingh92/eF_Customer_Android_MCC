package com.lexxdigital.easyfooduserapps.api;

import com.lexxdigital.easyfooduserapps.model.cancelorder.CancelOrderResponse;
import com.lexxdigital.easyfooduserapps.model.cancelorder.CancelRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CancelInterface {
    @Headers("Content-Type: application/json")
    @POST("order_cancel_by_customer")
    Call<CancelOrderResponse> mCancelOrder(@Body CancelRequest order_number);

}
