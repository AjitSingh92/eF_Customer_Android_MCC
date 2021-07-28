package com.easyfoodcustomer.api;

import com.easyfoodcustomer.model.UpdateCustomerPostcodeRequest;
import com.easyfoodcustomer.model.UpdateCustomerPostcodeResponse;
import com.easyfoodcustomer.model.landing_page_request.CheckDeliveryPostcodeRequest;
import com.easyfoodcustomer.model.landing_page_request.RestaurantsDealRequest;
import com.easyfoodcustomer.model.landing_page_response.CheckDeliveryPostcodeResponse;
import com.easyfoodcustomer.model.landing_page_response.RestaurantsDealResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RestaurantsDealsInterface {

    @Headers("Content-Type: application/json")
    @POST("getRestauratList")
    Call<RestaurantsDealResponse> mLogin(@Header("Authorization") String Authorization,@Body RestaurantsDealRequest request);

    @Headers("Content-Type: application/json")
    @POST("check_delivery_in_postcode")
    Call<CheckDeliveryPostcodeResponse> getCheckDeliveryPostcode(@Header("Authorization") String Authorization,@Body CheckDeliveryPostcodeRequest request);


    @Headers("Content-Type: application/json")
    @POST("update_customer_postcode")
    Call<UpdateCustomerPostcodeResponse> updateCustomerPostcode(@Header("Authorization") String Authorization,@Body UpdateCustomerPostcodeRequest request);


}
