package com.easyfoodcustomer.api;

import com.easyfoodcustomer.model.VoucherApplyRequest;
import com.easyfoodcustomer.model.VoucherApplyResponse;
import com.easyfoodcustomer.model.restuarant_time_slot.TimeSlotRequest;
import com.easyfoodcustomer.model.restuarant_time_slot.TimeSlotResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface VoucherApplyInterface {
    @Headers("Content-Type: application/json")
    @POST("voucher_code_valid_order")
    Call<VoucherApplyResponse> voucherApply(@Header("Authorization") String Authorization, @Body VoucherApplyRequest request);

    @Headers("Content-Type: application/json")
    @POST("get_restaurant_delivery_slots")
    Call<TimeSlotResponse> restuarantTimeSlot(@Header("Authorization") String Authorization,@Body TimeSlotRequest request);
}
