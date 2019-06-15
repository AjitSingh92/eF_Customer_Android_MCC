
package com.lexxdigital.easyfooduserapps.model.add_card_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardAddRequest {

    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("stripe_token")
    @Expose
    private String stripeToken;

    @SerializedName("last4_card_digit")
    @Expose
    private String last4CardDigit;

    @SerializedName("exp_month")

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStripeToken() {
        return stripeToken;
    }

    public void setStripeToken(String stripeToken) {
        this.stripeToken = stripeToken;
    }

    public String getLast4CardDigit() {
        return last4CardDigit;
    }

    public void setLast4CardDigit(String last4CardDigit) {
        this.last4CardDigit = last4CardDigit;
    }
}
