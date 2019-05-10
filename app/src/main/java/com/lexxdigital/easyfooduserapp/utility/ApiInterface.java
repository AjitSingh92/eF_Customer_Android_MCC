package com.lexxdigital.easyfooduserapp.utility;

import com.lexxdigital.easyfooduserapp.model.add_model.AddressRequestModel;
import com.lexxdigital.easyfooduserapp.model.add_model.AddressResponseModel;
import com.lexxdigital.easyfooduserapp.model.add_model.EditAddressRequest;
import com.lexxdigital.easyfooduserapp.order_status.models.OrderStatusRequestModel;
import com.lexxdigital.easyfooduserapp.order_status.models.OrderStatusResponseModel;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface
{
    @POST("addCustomerAddress")
    Single<AddressResponseModel> manageAddress(@Body AddressRequestModel address);
    @POST("editCustomerAddress")
    Single<AddressResponseModel> editAddress(@Body EditAddressRequest address);
    @POST("order_payment_status")
    Single<OrderStatusResponseModel> getOrderStatus(@Body OrderStatusRequestModel orderStatusRequestModel);
}
