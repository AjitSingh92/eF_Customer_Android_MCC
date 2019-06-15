package com.lexxdigital.easyfooduserapps.model.restaurant_offers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantSpecialOffers {

    @SerializedName("offer_id")
    @Expose
    private String offerId;

    @SerializedName("offer_title")
    @Expose
    private String offerTitle;

    @SerializedName("offer_description")
    @Expose
    private String offerDescription;

    @SerializedName("offer_delivery_options")
    @Expose
    private String offerDeliveryOptions;

    @SerializedName("offer_type")
    @Expose
    private String offerType;
    @SerializedName("minimum_order_amount")
    @Expose
    private String minimumOrderAmount;
    @SerializedName("spendx_get_product_offer_detail")
    @Expose
    private List<SpendxGetProductOfferDetail> spendxGetProductOfferDetails = null;

    @SerializedName("combo_offer_detail")
    @Expose
    private List<ComboOfferDetail> comboOfferDetails;
    @SerializedName("original_price")
    @Expose
    private String originalPrice;
    @SerializedName("total_discount")
    @Expose
    private String totalDiscount;

    @SerializedName("offer_price")
    @Expose
    private String offer_price;

    public RestaurantSpecialOffers() {
    }

    public RestaurantSpecialOffers(String offerId, String offerTitle, String offerDescription, String offerDeliveryOptions, String offerType, String minimumOrderAmount, List<SpendxGetProductOfferDetail> spendxGetProductOfferDetails, List<ComboOfferDetail> comboOfferDetails, String originalPrice, String totalDiscount, String offer_price) {
        this.offerId = offerId;
        this.offerTitle = offerTitle;
        this.offerDescription = offerDescription;
        this.offerDeliveryOptions = offerDeliveryOptions;
        this.offerType = offerType;
        this.minimumOrderAmount = minimumOrderAmount;
        this.spendxGetProductOfferDetails = spendxGetProductOfferDetails;
        this.comboOfferDetails = comboOfferDetails;
        this.originalPrice = originalPrice;
        this.totalDiscount = totalDiscount;
        this.offer_price = offer_price;
    }

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

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public String getOfferDeliveryOptions() {
        return offerDeliveryOptions;
    }

    public void setOfferDeliveryOptions(String offerDeliveryOptions) {
        this.offerDeliveryOptions = offerDeliveryOptions;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public String getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    public void setMinimumOrderAmount(String minimumOrderAmount) {
        this.minimumOrderAmount = minimumOrderAmount;
    }

    public List<SpendxGetProductOfferDetail> getSpendxGetProductOfferDetails() {
        return spendxGetProductOfferDetails;
    }

    public void setSpendxGetProductOfferDetails(List<SpendxGetProductOfferDetail> spendxGetProductOfferDetails) {
        this.spendxGetProductOfferDetails = spendxGetProductOfferDetails;
    }

    public List<ComboOfferDetail> getComboOfferDetails() {
        return comboOfferDetails;
    }

    public void setComboOfferDetails(List<ComboOfferDetail> comboOfferDetails) {
        this.comboOfferDetails = comboOfferDetails;
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

    public String getOffer_price() {
        return offer_price;
    }

    public void setOffer_price(String offer_price) {
        this.offer_price = offer_price;
    }

    @Override
    public String toString() {
        return "SpecialOffers{" +
                "offerId='" + offerId + '\'' +
                ", offerTitle='" + offerTitle + '\'' +
                ", offerDescription='" + offerDescription + '\'' +
                ", offerDeliveryOptions='" + offerDeliveryOptions + '\'' +
                ", offerType='" + offerType + '\'' +
                ", minimumOrderAmount='" + minimumOrderAmount + '\'' +
                ", spendxGetProductOfferDetails=" + spendxGetProductOfferDetails +
                ", comboOfferDetails=" + comboOfferDetails +
                ", originalPrice='" + originalPrice + '\'' +
                ", totalDiscount='" + totalDiscount + '\'' +
                ", offer_price='" + offer_price + '\'' +
                '}';
    }
}
