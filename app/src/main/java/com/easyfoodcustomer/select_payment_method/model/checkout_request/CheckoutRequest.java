
package com.easyfoodcustomer.select_payment_method.model.checkout_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.CartDatRequest;

public class CheckoutRequest {
    //make_default(on/off)
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("customer_id")
    @Expose
    private String customerId;

    @SerializedName("userMobile")
    @Expose
    private String userMobile;

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
    @SerializedName("deliveryPartner")
    @Expose
    private String deliveryPartner;

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

    @SerializedName("make_default")
    @Expose
    private String makeDefault;

    @SerializedName("save_card_id")
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

    public Integer getIsTableBooking() {
        return isTableBooking;
    }

    public void setIsTableBooking(Integer isTableBooking) {
        this.isTableBooking = isTableBooking;
    }

    @SerializedName("isTableBooking")
    @Expose
    Integer isTableBooking;


    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    @SerializedName("unitType")
    @Expose
    private String unitType;

    @SerializedName("unitNumber")
    @Expose
    private String unitNumber;

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    @SerializedName("unitId")
    @Expose
    private String unitId;

    @SerializedName("useragent")
    @Expose
    private String useragent;

    @SerializedName("order_time_postcode")
    @Expose
    private String order_time_postcode;

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


    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
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


    public String getDeliveryPartner() {
        return deliveryPartner;
    }

    public void setDeliveryPartner(String deliveryPartner) {
        this.deliveryPartner = deliveryPartner;
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

    public String getMakeDefault() {
        return makeDefault;
    }

    public void setMakeDefault(String makeDefault) {
        this.makeDefault = makeDefault;
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

    public String getUseragent() {
        return useragent;
    }

    public void setUseragent(String useragent) {
        this.useragent = useragent;
    }

    public String getOrder_time_postcode() {
        return order_time_postcode;
    }

    public void setOrder_time_postcode(String order_time_postcode) {
        this.order_time_postcode = order_time_postcode;
    }
}
