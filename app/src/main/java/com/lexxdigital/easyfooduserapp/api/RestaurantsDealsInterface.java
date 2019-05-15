package com.lexxdigital.easyfooduserapp.api;

import com.lexxdigital.easyfooduserapp.model.landing_page_request.CheckDeliveryPostcodeRequest;
import com.lexxdigital.easyfooduserapp.model.landing_page_request.RestaurantsDealRequest;
import com.lexxdigital.easyfooduserapp.model.landing_page_response.CheckDeliveryPostcodeResponse;
import com.lexxdigital.easyfooduserapp.model.landing_page_response.RestaurantsDealResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RestaurantsDealsInterface {

    @Headers("Content-Type: application/json")
    @POST("landingPage")
    Call<RestaurantsDealResponse> mLogin(@Body RestaurantsDealRequest request);

    @Headers("Content-Type: application/json")
    @POST("check_delivery_in_postcode")
    Call<CheckDeliveryPostcodeResponse> getCheckDeliveryPostcode(@Body CheckDeliveryPostcodeRequest request);


}
