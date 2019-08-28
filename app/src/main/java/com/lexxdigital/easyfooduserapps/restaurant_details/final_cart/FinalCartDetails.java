
package com.lexxdigital.easyfooduserapps.restaurant_details.final_cart;


public class FinalCartDetails {

    private String restaurantId;
    private String customerId;
    private String paymentMode;
    private String deliveryOption;
    private Double deliveryCharge;
    private Double discountAmount;
    private String voucherId;
    private String offerId;
    private String orderNotes;
    private String orderVia;
    private CartDetails cartDetails;

    /**
     * No args constructor for use in serialization
     * 
     */
    public FinalCartDetails() {
    }

    /**
     * 
     * @param deliveryOption
     * @param paymentMode
     * @param orderVia
     * @param customerId
     * @param orderNotes
     * @param cartDetails
     * @param discountAmount
     * @param offerId
     * @param deliveryCharge
     * @param restaurantId
     * @param voucherId
     */
    public FinalCartDetails(String restaurantId, String customerId, String paymentMode, String deliveryOption, Double deliveryCharge, Double discountAmount, String voucherId, String offerId, String orderNotes, String orderVia, CartDetails cartDetails) {
        super();
        this.restaurantId = restaurantId;
        this.customerId = customerId;
        this.paymentMode = paymentMode;
        this.deliveryOption = deliveryOption;
        this.deliveryCharge = deliveryCharge;
        this.discountAmount = discountAmount;
        this.voucherId = voucherId;
        this.offerId = offerId;
        this.orderNotes = orderNotes;
        this.orderVia = orderVia;
        this.cartDetails = cartDetails;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getDeliveryOption() {
        return deliveryOption;
    }

    public void setDeliveryOption(String deliveryOption) {
        this.deliveryOption = deliveryOption;
    }

    public Double getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(Double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
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

    public String getOrderVia() {
        return orderVia;
    }

    public void setOrderVia(String orderVia) {
        this.orderVia = orderVia;
    }

    public CartDetails getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(CartDetails cartDetails) {
        this.cartDetails = cartDetails;
    }

}
