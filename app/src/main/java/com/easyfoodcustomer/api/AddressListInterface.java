package com.easyfoodcustomer.api;

import com.easyfoodcustomer.model.address_list_request.AddressDeliveryListRequest;
import com.easyfoodcustomer.model.address_list_request.AddressListRequest;
import com.easyfoodcustomer.model.address_list_response.AddressListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AddressListInterface {
    @Headers("Content-Type: application/json")
    @POST("listCustomerAddress")
    Call<AddressListResponse> mLogin(@Header("Authorization") String Authorization, @Body AddressListRequest request);

    @Headers("Content-Type: application/json")
    @POST("listCustomerAddressDelivery")
    Call<AddressListResponse> mLogin(@Header("Authorization") String Authorization,@Body AddressDeliveryListRequest request);
}
