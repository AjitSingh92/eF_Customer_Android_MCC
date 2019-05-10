package com.lexxdigital.easyfooduserapp.api;

import com.lexxdigital.easyfooduserapp.model.favourites_list_request.FavouristeListRequest;
import com.lexxdigital.easyfooduserapp.model.favourites_list_response.FavouristeListRespose;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FavouritesListInterface {
    @Headers("Content-Type: application/json")
    @POST("UserFavouriteListing")
    Call<FavouristeListRespose> mFavouriteList(@Body FavouristeListRequest request);
}
