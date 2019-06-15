package com.lexxdigital.easyfooduserapps.api;

import com.lexxdigital.easyfooduserapps.model.filter_request.FilterSortRequest;
import com.lexxdigital.easyfooduserapps.model.filter_response.FilterSortResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FilterSortInterface {
    @Headers("Content-Type: application/json")
    @POST("filters")
    Call<FilterSortResponse> mGetFilters(@Body FilterSortRequest request);
}
