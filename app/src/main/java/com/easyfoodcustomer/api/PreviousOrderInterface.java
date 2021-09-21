package com.easyfoodcustomer.api;

import com.easyfoodcustomer.adapters.previous_order.DeleteResponse;
import com.easyfoodcustomer.model.myorder.PreviousOrderResponse;
import com.easyfoodcustomer.model.myorder.ReqstPrevOrder;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PreviousOrderInterface {
    @Headers("Content-Type: application/json")
    @POST("previous_order_history")
    Call<PreviousOrderResponse> mLogin(@Header("Authorization") String Authorization, @Body ReqstPrevOrder previousOrder);

    @Headers("Content-Type: application/json")
    @POST("customer_order_delete")
    Call<DeleteResponse> deleteOrder(@Header("Authorization") String Authorization, @Body JsonObject jsonObject);

}
