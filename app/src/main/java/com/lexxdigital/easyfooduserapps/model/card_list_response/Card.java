
package com.lexxdigital.easyfooduserapps.model.card_list_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Card {

    @SerializedName("card_id")
    @Expose
    private String cardId;
    @SerializedName("last_4_card_no")
    @Expose
    private String last4CardNo;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("stripe_customer_id")
    @Expose
    private String stripeCustomerId;
    @SerializedName("customer_name_on_card")
    @Expose
    private String customerNameOnCard;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("is_default")
    @Expose
    private int isDefault;
    @SerializedName("address_line1")
    @Expose
    private String addressLine1;
    @SerializedName("address_line2")
    @Expose
    private String addressLine2;
    @SerializedName("address_city")
    @Expose
    private String addressCity;
    @SerializedName("address_post_code")
    @Expose
    private String addressPostCode;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getLast4CardNo() {
        return last4CardNo;
    }

    public void setLast4CardNo(String last4CardNo) {
        this.last4CardNo = last4CardNo;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getStripeCustomerId() {
        return stripeCustomerId;
    }

    public void setStripeCustomerId(String stripeCustomerId) {
        this.stripeCustomerId = stripeCustomerId;
    }

    public String getCustomerNameOnCard() {
        return customerNameOnCard;
    }

    public void setCustomerNameOnCard(String customerNameOnCard) {
        this.customerNameOnCard = customerNameOnCard;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressPostCode() {
        return addressPostCode;
    }

    public void setAddressPostCode(String addressPostCode) {
        this.addressPostCode = addressPostCode;
    }
}
