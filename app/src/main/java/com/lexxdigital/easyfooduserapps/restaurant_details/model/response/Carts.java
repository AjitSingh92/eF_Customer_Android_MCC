
package com.lexxdigital.easyfooduserapps.restaurant_details.model.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Carts {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("cartTotalPrice")
    @Expose
    private String cartTotalPrice;
    @SerializedName("available_items")
    @Expose
    private Integer availableItems;
    @SerializedName("delivery_charge")
    @Expose
    private String deliveryCharge;
    @SerializedName("min_order_value")
    @Expose
    private String minOrderValue;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public String getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(String cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public Integer getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(Integer availableItems) {
        this.availableItems = availableItems;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getMinOrderValue() {
        return minOrderValue;
    }

    public void setMinOrderValue(String minOrderValue) {
        this.minOrderValue = minOrderValue;
    }

}
