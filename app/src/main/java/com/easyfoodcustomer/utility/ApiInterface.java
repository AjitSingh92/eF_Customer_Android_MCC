package com.easyfoodcustomer.utility;

import com.google.gson.JsonObject;
import com.easyfoodcustomer.model.add_model.AddressRequestModel;
import com.easyfoodcustomer.model.add_model.AddressResponseModel;
import com.easyfoodcustomer.model.add_model.EditAddressRequest;
import com.easyfoodcustomer.order_status.models.OrderStatusRequestModel;
import com.easyfoodcustomer.order_status.models.OrderStatusResponseModel;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface
{
    @POST("addCustomerAddress")
    Single<AddressResponseModel> manageAddress(@Header("Authorization") String Authorization, @Body AddressRequestModel address);
    @POST("editCustomerAddress")
    Single<AddressResponseModel> editAddress(@Header("Authorization") String Authorization,@Body EditAddressRequest address);
   /* @POST("order_payment_status")
    Single<OrderStatusResponseModel> getOrderStatus(@Body OrderStatusRequestModel orderStatusRequestModel);*/

    @POST("check_order_status")
    Single<OrderStatusResponseModel> getOrderStatus(@Header("Authorization") String Authorization,@Body OrderStatusRequestModel orderStatusRequestModel);

    @Headers("Content-Type: application/json")
    @POST("getTableInfo")
    Call<TableInfoBean> getTableList(@Header("Authorization") String Authorization,@Body JsonObject jsonObject);
}
