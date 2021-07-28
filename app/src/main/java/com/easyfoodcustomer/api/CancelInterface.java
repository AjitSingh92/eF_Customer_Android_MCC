package com.easyfoodcustomer.api;

import com.easyfoodcustomer.model.cancelorder.CancelOrderResponse;
import com.easyfoodcustomer.model.cancelorder.CancelRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CancelInterface {
    @Headers("Content-Type: application/json")
    @POST("order_cancel_by_customer")
    Call<CancelOrderResponse> mCancelOrder(@Header("Authorization") String Authorization, @Body CancelRequest order_number);

}
