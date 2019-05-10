package com.lexxdigital.easyfooduserapp.api;

import com.lexxdigital.easyfooduserapp.model.add_favourites_request.AddFavouristeResquest;
import com.lexxdigital.easyfooduserapp.model.add_favourites_response.AddFavouristeResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AddFavouritesInterface {
    @Headers("Content-Type: application/json")
    @POST("UserFavourites")
    Call<AddFavouristeResponse> mAddFavourites(@Body AddFavouristeResquest request);
}
