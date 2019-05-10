package com.lexxdigital.easyfooduserapp.api;

import com.lexxdigital.easyfooduserapp.model.add_card_request.CardAddRequest;
import com.lexxdigital.easyfooduserapp.model.add_card_response.CardAddResponse;
import com.lexxdigital.easyfooduserapp.model.postal_code_address.PostalCodeAddRes;
import com.lexxdigital.easyfooduserapp.model.postal_code_address.PostalCodeAddressReq;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SearchPostalCodeAddressInterface {
    @Headers("Content-Type: application/json")
    @POST("get_post_code_addresses")
    Call<PostalCodeAddRes> mLogin(@Body PostalCodeAddressReq request);

}
