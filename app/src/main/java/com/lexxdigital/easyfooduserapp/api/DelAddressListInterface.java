package com.lexxdigital.easyfooduserapp.api;

import com.lexxdigital.easyfooduserapp.model.del_address_list_request.DelAddressListRequest;
import com.lexxdigital.easyfooduserapp.model.del_address_list_response.DelAddressListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DelAddressListInterface {
    @Headers("Content-Type: application/json")
    @POST("deleteCustomerAddress")
    Call<DelAddressListResponse> mDeleteList(@Body DelAddressListRequest request);
}
