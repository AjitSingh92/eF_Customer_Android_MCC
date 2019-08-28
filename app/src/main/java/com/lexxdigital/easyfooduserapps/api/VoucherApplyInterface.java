package com.lexxdigital.easyfooduserapps.api;

import com.lexxdigital.easyfooduserapps.model.VoucherApplyRequest;
import com.lexxdigital.easyfooduserapps.model.VoucherApplyResponse;
import com.lexxdigital.easyfooduserapps.model.restuarant_time_slot.TimeSlotRequest;
import com.lexxdigital.easyfooduserapps.model.restuarant_time_slot.TimeSlotResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface VoucherApplyInterface {
    @Headers("Content-Type: application/json")
    @POST("voucher_code_valid_order")
    Call<VoucherApplyResponse> voucherApply(@Body VoucherApplyRequest request);

    @Headers("Content-Type: application/json")
    @POST("get_restaurant_delivery_slots")
    Call<TimeSlotResponse> restuarantTimeSlot(@Body TimeSlotRequest request);
}
