package com.easyfoodcustomer.api;

import com.easyfoodcustomer.model.order_details.OrderDetailsRequest;
import com.easyfoodcustomer.model.order_details.OrderDetailsResponse;
import com.easyfoodcustomer.model.previous_order.datamodel.PreviousOrderDetailsModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OrderDetailsInterface {
    @Headers("Content-Type: application/json")
    @POST("order_details")
    Call<PreviousOrderDetailsModel> mOrderDetailsNew(@Header("Authorization") String Authorization, @Body OrderDetailsRequest request);

    @Headers("Content-Type: application/json")
    @POST("order_details")
    Call<OrderDetailsResponse> mOrderDetails(@Header("Authorization") String Authorization,@Body OrderDetailsRequest request);
}
