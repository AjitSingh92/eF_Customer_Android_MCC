package com.lexxdigital.easyfooduserapps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VoucherApplyRequest {
    @SerializedName("customer_id")
    @Expose
    private String customer_id;

    @SerializedName("voucher_code")
    @Expose
    private String voucher_code;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurant_id;

    @SerializedName("cart_subtotal")
    @Expose
    private String cart_subtotal;

    public String getCart_subTotal() {
        return cart_subtotal;
    }

    public void setCart_subTotal(String cart_subTotal) {
        this.cart_subtotal = cart_subTotal;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getVoucher_code() {
        return voucher_code;
    }

    public void setVoucher_code(String voucher_code) {
        this.voucher_code = voucher_code;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    @Override
    public String toString() {
        return "VoucherApplyRequest{" +
                "customer_id='" + customer_id + '\'' +
                ", voucher_code='" + voucher_code + '\'' +
                ", restaurant_id='" + restaurant_id + '\'' +
                '}';
    }
}
