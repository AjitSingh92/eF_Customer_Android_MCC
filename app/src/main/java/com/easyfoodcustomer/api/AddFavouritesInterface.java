package com.easyfoodcustomer.api;

import com.easyfoodcustomer.model.add_favourites_request.AddFavouristeResquest;
import com.easyfoodcustomer.model.add_favourites_response.AddFavouristeResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AddFavouritesInterface {
    @Headers("Content-Type: application/json")
    @POST("UserFavourites")
    Call<AddFavouristeResponse> mAddFavourites(@Header("Authorization") String Authorization, @Body AddFavouristeResquest request);
}
