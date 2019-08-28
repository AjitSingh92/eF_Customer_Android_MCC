package com.lexxdigital.easyfooduserapps.select_payment_method.api;

import com.lexxdigital.easyfooduserapps.select_payment_method.model.checkout_request.CheckoutRequest;
import com.lexxdigital.easyfooduserapps.select_payment_method.model.checkout_response.CheckoutResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CheckoutRequestInterface {
    @Headers("Content-Type: application/json")
    @POST("order_checkout")
    Call<CheckoutResponse> mCheckout(@Body CheckoutRequest request);
}
