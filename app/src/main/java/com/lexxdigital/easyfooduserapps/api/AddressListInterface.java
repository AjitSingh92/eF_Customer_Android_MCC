package com.lexxdigital.easyfooduserapps.api;

import com.lexxdigital.easyfooduserapps.model.address_list_request.AddressDeliveryListRequest;
import com.lexxdigital.easyfooduserapps.model.address_list_request.AddressListRequest;
import com.lexxdigital.easyfooduserapps.model.address_list_response.AddressListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AddressListInterface {
    @Headers("Content-Type: application/json")
    @POST("listCustomerAddress")
    Call<AddressListResponse> mLogin(@Body AddressListRequest request);

    @Headers("Content-Type: application/json")
    @POST("listCustomerAddressDelivery")
    Call<AddressListResponse> mLogin(@Body AddressDeliveryListRequest request);
}
