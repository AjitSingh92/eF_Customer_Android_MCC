package com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.CartData;

public class CartDatRequest {

    @SerializedName("menu")
    @Expose
    CartData cartData;
    @SerializedName("restaurantId")
    @Expose
    String restaurantId;
    @SerializedName("restaurantName")
    @Expose
    String restaurantName;
    @SerializedName("postCode")
    @Expose
    String postCode;
    @SerializedName("totalCartPrice")
    @Expose
    Double totalCartPrice;

    @SerializedName("orderType")
    @Expose
    String orderType;
    @SerializedName("deliveryCharge")
    @Expose
    Double deliveryCharge;
    @SerializedName("org_deliveryCharge")
    @Expose
    String org_delivery_charge;
    @SerializedName("maxLength")
    @Expose
    String maxLength;
    @SerializedName("voucherDiscount")
    @Expose
    Double voucherDiscount;
    @SerializedName("voucherCode")
    @Expose
    String voucherCode;

    @SerializedName("restaurantSlug")
    @Expose
    String restaurantSlug;


    public CartData getCartData() {
        return cartData;
    }

    public void setCartData(CartData cartData) {
        this.cartData = cartData;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Double getTotalCartPrice() {
        return totalCartPrice;
    }

    public void setTotalCartPrice(Double totalCartPrice) {
        this.totalCartPrice = totalCartPrice;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Double getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(Double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getOrg_delivery_charge() {
        return org_delivery_charge;
    }

    public void setOrg_delivery_charge(String org_delivery_charge) {
        this.org_delivery_charge = org_delivery_charge;
    }

    public String getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
    }

    public Double getVoucherDiscount() {
        return voucherDiscount;
    }

    public void setVoucherDiscount(Double voucherDiscount) {
        this.voucherDiscount = voucherDiscount;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getRestaurantSlug() {
        return restaurantSlug;
    }

    public void setRestaurantSlug(String restaurantSlug) {
        this.restaurantSlug = restaurantSlug;
    }

}
