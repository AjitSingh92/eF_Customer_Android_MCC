package com.easyfoodcustomer.api;

import com.easyfoodcustomer.model.myorder.PreviousOrderResponse;
import com.easyfoodcustomer.model.myorder.ReqstPrevOrder;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PreviousOrderInterface {
    @Headers("Content-Type: application/json")
    @POST("previous_order_history")
    Call<PreviousOrderResponse> mLogin(@Header("Authorization") String Authorization, @Body ReqstPrevOrder previousOrder);
}
