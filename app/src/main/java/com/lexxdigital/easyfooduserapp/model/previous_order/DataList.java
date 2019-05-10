package com.lexxdigital.easyfooduserapp.model.previous_order;

import java.io.Serializable;
import java.util.List;

public class DataList implements Serializable {
    private String orderId;
    private String restaurantId;
    private String restaurantName;
    private String restaurantLogo;
    private String restaurantImage;
    private String avgRating;
    private String customerId;
    private String cartId;
    private String orderNum;
    private String orderTotal;
    private String orderDateTime;
    private Integer isPaid;
    private String paymentMode;
    private String paymentStatus;
    private Integer isDelivered;
    private String deliveryTime;
    private String deliveryOption;
    private String deliveryDateTime;
    private String deliveryCharge;
    private String discountAmount;
    private String orderSubtotal;
    private String voucherId;
    private String offerId;
    private String orderNotes;
    private List<Cart> cart = null;
    private Integer total;


    public DataList(String orderId, String restaurantId, String restaurantName, String restaurantLogo, String restaurantImage, String avgRating, String customerId, String cartId, String orderNum, String orderTotal, String orderDateTime, Integer isPaid, String paymentMode, String paymentStatus, Integer isDelivered, String deliveryTime, String deliveryOption, String deliveryDateTime, String deliveryCharge, String discountAmount, String orderSubtotal, String voucherId, String offerId, String orderNotes, List<Cart> cart, Integer total) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.restaurantLogo = restaurantLogo;
        this.restaurantImage = restaurantImage;
        this.avgRating = avgRating;
        this.customerId = customerId;
        this.cartId = cartId;
        this.orderNum = orderNum;
        this.orderTotal = orderTotal;
        this.orderDateTime = orderDateTime;
        this.isPaid = isPaid;
        this.paymentMode = paymentMode;
        this.paymentStatus = paymentStatus;
        this.isDelivered = isDelivered;
        this.deliveryTime = deliveryTime;
        this.deliveryOption = deliveryOption;
        this.deliveryDateTime = deliveryDateTime;
        this.deliveryCharge = deliveryCharge;
        this.discountAmount = discountAmount;
        this.orderSubtotal = orderSubtotal;
        this.voucherId = voucherId;
        this.offerId = offerId;
        this.orderNotes = orderNotes;
        this.cart = cart;
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getRestaurantLogo() {
        return restaurantLogo;
    }

    public void setRestaurantLogo(String restaurantLogo) {
        this.restaurantLogo = restaurantLogo;
    }

    public String getRestaurantImage() {
        return restaurantImage;
    }

    public void setRestaurantImage(String restaurantImage) {
        this.restaurantImage = restaurantImage;
    }

    public String getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(String avgRating) {
        this.avgRating = avgRating;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(String orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public Integer getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Integer isPaid) {
        this.isPaid = isPaid;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getIsDelivered() {
        return isDelivered;
    }

    public void setIsDelivered(Integer isDelivered) {
        this.isDelivered = isDelivered;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryOption() {
        return deliveryOption;
    }

    public void setDeliveryOption(String deliveryOption) {
        this.deliveryOption = deliveryOption;
    }

    public String getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public void setDeliveryDateTime(String deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getOrderSubtotal() {
        return orderSubtotal;
    }

    public void setOrderSubtotal(String orderSubtotal) {
        this.orderSubtotal = orderSubtotal;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getOrderNotes() {
        return orderNotes;
    }

    public void setOrderNotes(String orderNotes) {
        this.orderNotes = orderNotes;
    }

    public List<Cart> getCart() {
        return cart;
    }

    public void setCart(List<Cart> cart) {
        this.cart = cart;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
