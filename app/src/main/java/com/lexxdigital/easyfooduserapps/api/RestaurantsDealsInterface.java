package com.lexxdigital.easyfooduserapps.api;

import com.lexxdigital.easyfooduserapps.model.UpdateCustomerPostcodeRequest;
import com.lexxdigital.easyfooduserapps.model.UpdateCustomerPostcodeResponse;
import com.lexxdigital.easyfooduserapps.model.landing_page_request.CheckDeliveryPostcodeRequest;
import com.lexxdigital.easyfooduserapps.model.landing_page_request.RestaurantsDealRequest;
import com.lexxdigital.easyfooduserapps.model.landing_page_response.CheckDeliveryPostcodeResponse;
import com.lexxdigital.easyfooduserapps.model.landing_page_response.RestaurantsDealResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RestaurantsDealsInterface {

    @Headers("Content-Type: application/json")
    @POST("getRestauratList")
    Call<RestaurantsDealResponse> mLogin(@Body RestaurantsDealRequest request);

    @Headers("Content-Type: application/json")
    @POST("check_delivery_in_postcode")
    Call<CheckDeliveryPostcodeResponse> getCheckDeliveryPostcode(@Body CheckDeliveryPostcodeRequest request);


    @Headers("Content-Type: application/json")
    @POST("update_customer_postcode")
    Call<UpdateCustomerPostcodeResponse> updateCustomerPostcode(@Body UpdateCustomerPostcodeRequest request);


}
