package com.easyfoodcustomer.api;

import com.easyfoodcustomer.model.edit_account_request.EditAccountRequest;
import com.easyfoodcustomer.model.edit_account_response.EditAccountResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface EditProfileInterface {
    @Headers("Content-Type: application/json")
    @POST("editUserProfile")
    Call<EditAccountResponse> mupdate(@Header("Authorization") String Authorization, @Body EditAccountRequest request);
}
