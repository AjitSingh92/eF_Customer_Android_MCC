package com.easyfoodcustomer.api;

import com.easyfoodcustomer.model.filter_request.FilterSortRequest;
import com.easyfoodcustomer.model.filter_response.FilterSortResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FilterSortInterface {
    @Headers("Content-Type: application/json")
    @POST("filters")
    Call<FilterSortResponse> mGetFilters(@Header("Authorization") String Authorization, @Body FilterSortRequest request);
}
