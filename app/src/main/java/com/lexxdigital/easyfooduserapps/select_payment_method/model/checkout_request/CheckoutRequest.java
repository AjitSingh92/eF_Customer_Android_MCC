
package com.lexxdigital.easyfooduserapps.select_payment_method.model.checkout_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.CartDatRequest;

public class CheckoutRequest {

    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("delivery_option")
    @Expose
    private String deliveryOption;
    @SerializedName("isTomorrow")
    @Expose
    private String isTomorrow;
    @SerializedName("delivery_charge")
    @Expose
    private Double deliveryCharge;
    @SerializedName("discount_amount")
    @Expose
    private Double discountAmount;
    @SerializedName("order_total")
    @Expose
    private Double orderTotal;
    @SerializedName("order_subtotal")
    @Expose
    private Double orderSubtotal;
    @SerializedName("voucher_id")
    @Expose
    private String voucherId;
    @SerializedName("offer_id")
    @Expose
    private String offerId;
    @SerializedName("billing_address")
    @Expose
    private String billingAddress;
    @SerializedName("delivery_address")
    @Expose
    private String deliveryAddress;
    @SerializedName("order_via")
    @Expose
    private String orderVia;
    @SerializedName("order_notes")
    @Expose
    private String orderNotes;
    @SerializedName("cart_details")
    @Expose
//    private CartDetails cartDetails;
            CartDatRequest cardData;
    @SerializedName("stripe_token")
    @Expose
    private String stripeToken;

    @SerializedName("email_id")
    @Expose
    private String emailId;

    @SerializedName("save_card")
    @Expose
    private String saveCard;

    @SerializedName("saved_card_id")
    @Expose
    private String savedCardId;

    @SerializedName("stripe_customer_id")
    @Expose
    private String stripeCustomerId;

    @SerializedName("last4_card_digit")
    @Expose
    private String last4CardDigit;

    @SerializedName("exp_month")
    @Expose
    private Integer expMonth;

    @SerializedName("exp_year")
    @Expose
    private Integer expYear;
    @SerializedName("delivery_date_time")
    @Expose
    String deliveryDateTime;

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


    public String getIsTomorrow() {
        return isTomorrow;
    }

    public void setIsTomorrow(String isTomorrow) {
        this.isTomorrow = isTomorrow;
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

    public Double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Double getOrderSubtotal() {
        return orderSubtotal;
    }

    public void setOrderSubtotal(Double orderSubtotal) {
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

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getOrderVia() {
        return orderVia;
    }

    public void setOrderVia(String orderVia) {
        this.orderVia = orderVia;
    }

    public String getOrderNotes() {
        return orderNotes;
    }

    public void setOrderNotes(String orderNotes) {
        this.orderNotes = orderNotes;
    }

    public CartDatRequest getCardData() {
        return cardData;
    }

    public void setCardData(CartDatRequest cardData) {
        this.cardData = cardData;
    }

    public String getStripeToken() {
        return stripeToken;
    }

    public void setStripeToken(String stripeToken) {
        this.stripeToken = stripeToken;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getSaveCard() {
        return saveCard;
    }

    public void setSaveCard(String saveCard) {
        this.saveCard = saveCard;
    }

    public String getSavedCardId() {
        return savedCardId;
    }

    public void setSavedCardId(String savedCardId) {
        this.savedCardId = savedCardId;
    }

    public String getStripeCustomerId() {
        return stripeCustomerId;
    }

    public void setStripeCustomerId(String stripeCustomerId) {
        this.stripeCustomerId = stripeCustomerId;
    }

    public String getLast4CardDigit() {
        return last4CardDigit;
    }

    public void setLast4CardDigit(String last4CardDigit) {
        this.last4CardDigit = last4CardDigit;
    }

    public Integer getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(Integer expMonth) {
        this.expMonth = expMonth;
    }

    public Integer getExpYear() {
        return expYear;
    }

    public void setExpYear(Integer expYear) {
        this.expYear = expYear;
    }

    public String getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public void setDeliveryDateTime(String deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }
}
