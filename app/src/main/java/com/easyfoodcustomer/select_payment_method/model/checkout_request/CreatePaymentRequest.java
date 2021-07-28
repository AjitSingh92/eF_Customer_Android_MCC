
package com.easyfoodcustomer.select_payment_method.model.checkout_request;

import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.CartDatRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatePaymentRequest {
    @SerializedName("name_on_card")
    @Expose
    private String nameOnCard;
    @SerializedName("cvv")
    @Expose
    private String cvv;

    @SerializedName("card_no")
    @Expose
    private String cardNo;

    @SerializedName("card_expire_month")
    @Expose
    private String month;
    @SerializedName("card_expire_year")
    @Expose
    private String year;
    @SerializedName("billing_postcode")
    @Expose
    private String billingPostcode;

    @SerializedName("customer_id")
    @Expose
    private String customerId;

    @SerializedName("postcode")
    @Expose
    private String postcode;

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBillingPostcode() {
        return billingPostcode;
    }

    public void setBillingPostcode(String billingPostcode) {
        this.billingPostcode = billingPostcode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
