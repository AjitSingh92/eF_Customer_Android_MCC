package com.lexxdigital.easyfooduserapps.api;

import com.lexxdigital.easyfooduserapps.model.myorder.PreviousOrderResponse;
import com.lexxdigital.easyfooduserapps.model.myorder.ReqstPrevOrder;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PreviousOrderInterface {
    @Headers("Content-Type: application/json")
    @POST("previous_order_history")
    Call<PreviousOrderResponse> mLogin(@Body ReqstPrevOrder previousOrder);
}
