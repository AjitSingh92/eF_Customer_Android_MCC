package com.easyfoodcustomer.select_payment_method.api;

import com.easyfoodcustomer.select_payment_method.model.checkout_request.CheckoutRequest;
import com.easyfoodcustomer.select_payment_method.model.checkout_request.CreatePaymentRequest;
import com.easyfoodcustomer.select_payment_method.model.checkout_response.CheckoutResponse;
import com.easyfoodcustomer.select_payment_method.model.checkout_response.CreatePaymentResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CheckoutRequestInterface {
    /*@Headers("Content-Type: application/json")
    @POST("order_checkout")
    Call<CheckoutResponse> mCheckout(@Header("Authorization") String Authorization, @Body CheckoutRequest request);
*/
    @Headers("Content-Type: application/json")
    @POST("order_app_checkout")
    Call<CheckoutResponse> mCheckout(@Header("Authorization") String Authorization, @Body CheckoutRequest request);

    @Headers("Content-Type: application/json")
    @POST("create_payment_method")
    Call<CreatePaymentResponse> createPayment(@Header("Authorization") String Authorization, @Body CreatePaymentRequest request);
}
