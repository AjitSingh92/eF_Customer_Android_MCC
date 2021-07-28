package com.easyfoodcustomer.search_post_code.api;

import com.easyfoodcustomer.search_post_code.model.search_request.SearchPostCodeRequest;
import com.easyfoodcustomer.search_post_code.model.search_response.GuestTokenBean;
import com.easyfoodcustomer.search_post_code.model.search_response.PrivacyBean;
import com.easyfoodcustomer.search_post_code.model.search_response.RestaurantQrResponseBean;
import com.easyfoodcustomer.search_post_code.model.search_response.SaveQRInfoBean;
import com.easyfoodcustomer.search_post_code.model.search_response.SearchPostCodeResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SearchPostCodeInterface {
    @Headers("Content-Type: application/json")
    @POST("PostCodeDetails")
    Call<SearchPostCodeResponse> mSearchPost(@Header("Authorization") String Authorization, @Body SearchPostCodeRequest request);

    @Headers("Content-Type: application/json")
    @POST("getRestaurantIdbyQRCode")
    Call<RestaurantQrResponseBean> getRestaurantIdbyQRCode(@Header("Authorization") String Authorization,@Body JsonObject jsonObject);

    @Headers("Content-Type: application/json")
    @POST("saveQRInformation")
    Call<SaveQRInfoBean> saveQRInformation(@Header("Authorization") String Authorization,@Body JsonObject jsonObject);


    @Headers("Content-Type: application/json")
    @POST("cmspage")
    Call<PrivacyBean> getPolicy(@Header("Authorization") String Authorization);

    @Headers("Content-Type: application/json")
    @POST("getAPIToken")
    Call<GuestTokenBean> getGuestToken();
}
