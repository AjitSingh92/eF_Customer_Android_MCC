package com.lexxdigital.easyfooduserapps.api;

import com.google.gson.JsonObject;
import com.lexxdigital.easyfooduserapps.model.edit_account_request.EditAccountRequest;
import com.lexxdigital.easyfooduserapps.model.edit_account_response.EditAccountResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UpdateMobileInterface {
    @Headers("Content-Type: application/json")
    @POST("profileupdateapi")
    Call<EditAccountResponse> updateMobile(@Body JsonObject request);
}
