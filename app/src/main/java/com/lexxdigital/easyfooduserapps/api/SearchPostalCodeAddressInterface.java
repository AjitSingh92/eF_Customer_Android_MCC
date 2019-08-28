package com.lexxdigital.easyfooduserapps.api;

import com.lexxdigital.easyfooduserapps.model.postal_code_address.PostalCodeAddRes;
import com.lexxdigital.easyfooduserapps.model.postal_code_address.PostalCodeAddressReq;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SearchPostalCodeAddressInterface {
    @Headers("Content-Type: application/json")
    @POST("get_post_code_addresses")
    Call<PostalCodeAddRes> mLogin(@Body PostalCodeAddressReq request);

}
