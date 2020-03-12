package com.lexxdigital.easyfooduserapps.api;

import com.lexxdigital.easyfooduserapps.model.order_details.OrderDetailsRequest;
import com.lexxdigital.easyfooduserapps.model.order_details.OrderDetailsResponse;
import com.lexxdigital.easyfooduserapps.model.previous_order.datamodel.PreviousOrderDetailsModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OrderDetailsInterface {
    @Headers("Content-Type: application/json")
    @POST("order_details")
    Call<PreviousOrderDetailsModel> mOrderDetailsNew(@Body OrderDetailsRequest request);

    @Headers("Content-Type: application/json")
    @POST("order_details")
    Call<OrderDetailsResponse> mOrderDetails(@Body OrderDetailsRequest request);
}
