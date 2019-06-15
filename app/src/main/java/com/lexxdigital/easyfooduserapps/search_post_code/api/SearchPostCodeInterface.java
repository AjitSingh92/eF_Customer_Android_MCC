package com.lexxdigital.easyfooduserapps.search_post_code.api;

import com.lexxdigital.easyfooduserapps.search_post_code.model.search_request.SearchPostCodeRequest;
import com.lexxdigital.easyfooduserapps.search_post_code.model.search_response.SearchPostCodeResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SearchPostCodeInterface {
    @Headers("Content-Type: application/json")
    @POST("PostCodeDetails")
    Call<SearchPostCodeResponse> mSearchPost(@Body SearchPostCodeRequest request);
}
