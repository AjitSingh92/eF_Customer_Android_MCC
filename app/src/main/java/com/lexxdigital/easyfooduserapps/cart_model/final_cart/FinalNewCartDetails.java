
package com.lexxdigital.easyfooduserapps.cart_model.final_cart;

import java.util.List;

public class FinalNewCartDetails {

    private List<Datum> data = null;
    private String totalCartPrice;
    private String restaurantId;
    private String orderType;
    private String userId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public FinalNewCartDetails() {
    }

    /**
     * 
     * @param totalCartPrice
     * @param orderType
     * @param userId
     * @param data
     * @param restaurantId
     */
    public FinalNewCartDetails(List<Datum> data, String totalCartPrice, String restaurantId, String orderType, String userId) {
        super();
        this.data = data;
        this.totalCartPrice = totalCartPrice;
        this.restaurantId = restaurantId;
        this.orderType = orderType;
        this.userId = userId;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public String getTotalCartPrice() {
        return totalCartPrice;
    }

    public void setTotalCartPrice(String totalCartPrice) {
        this.totalCartPrice = totalCartPrice;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
