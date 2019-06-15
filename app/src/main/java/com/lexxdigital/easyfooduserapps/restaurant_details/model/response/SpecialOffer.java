
package com.lexxdigital.easyfooduserapps.restaurant_details.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpecialOffer {

    @SerializedName("offer_id")
    @Expose
    private String offerId;
    @SerializedName("offer_title")
    @Expose
    private String offerTitle;
    @SerializedName("offer_available")
    @Expose
    private String offerAvailable;
    @SerializedName("spend_amount_to_avaliable_offer")
    @Expose
    private String spendAmountToAvaliableOffer;
    @SerializedName("offer_discount_percentage")
    @Expose
    private String offerDiscountPercentage;
    @SerializedName("offer_delivery_option")
    @Expose
    private String offerDeliveryOption;
    @SerializedName("offer_details")
    @Expose
    private String offerDetails;
    @SerializedName("original_price")
    @Expose
    private String originalPrice;
    @SerializedName("total_discount")
    @Expose
    private String totalDiscount;
    @SerializedName("offer_price")
    @Expose
    private String offerPrice;

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getOfferTitle() {
        return offerTitle;
    }

    public void setOfferTitle(String offerTitle) {
        this.offerTitle = offerTitle;
    }

    public String getOfferAvailable() {
        return offerAvailable;
    }

    public void setOfferAvailable(String offerAvailable) {
        this.offerAvailable = offerAvailable;
    }

    public String getSpendAmountToAvaliableOffer() {
        return spendAmountToAvaliableOffer;
    }

    public void setSpendAmountToAvaliableOffer(String spendAmountToAvaliableOffer) {
        this.spendAmountToAvaliableOffer = spendAmountToAvaliableOffer;
    }

    public String getOfferDiscountPercentage() {
        return offerDiscountPercentage;
    }

    public void setOfferDiscountPercentage(String offerDiscountPercentage) {
        this.offerDiscountPercentage = offerDiscountPercentage;
    }

    public String getOfferDeliveryOption() {
        return offerDeliveryOption;
    }

    public void setOfferDeliveryOption(String offerDeliveryOption) {
        this.offerDeliveryOption = offerDeliveryOption;
    }

    public String getOfferDetails() {
        return offerDetails;
    }

    public void setOfferDetails(String offerDetails) {
        this.offerDetails = offerDetails;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(String totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

}
