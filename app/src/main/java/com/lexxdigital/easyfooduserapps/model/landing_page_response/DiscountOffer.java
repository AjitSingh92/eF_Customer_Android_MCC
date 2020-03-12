
package com.lexxdigital.easyfooduserapps.model.landing_page_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiscountOffer {

    @SerializedName("minvalue")
    @Expose
    private String min_value;

    @SerializedName("offer_id")
    @Expose
    private String offerId;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("offer_type")
    @Expose
    private String offerType;
    @SerializedName("offer_title")
    @Expose
    private String offerTitle;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("offer_price")
    @Expose
    private String offerPrice;
    @SerializedName("offer_price_label")
    @Expose
    private String offerPriceLabel;

    @SerializedName("combo_discount")
    @Expose
    private String combo_discount;

    @SerializedName("offer_available")
    @Expose
    private String offer_available;

     @SerializedName("offer_image")
    @Expose
    private String offer_image;

 @SerializedName("terms_conditions")
    @Expose
    private String terms_conditions;


 @SerializedName("offer_title_label")
    @Expose
    private String offer_title_label;

    public String getCombo_discount() {
        return combo_discount;
    }

    public void setCombo_discount(String combo_discount) {
        this.combo_discount = combo_discount;
    }

    public String getOffer_available() {
        return offer_available;
    }

    public void setOffer_available(String offer_available) {
        this.offer_available = offer_available;
    }

    public String getOffer_image() {
        return offer_image;
    }

    public void setOffer_image(String offer_image) {
        this.offer_image = offer_image;
    }

    public String getTerms_conditions() {
        return terms_conditions;
    }

    public void setTerms_conditions(String terms_conditions) {
        this.terms_conditions = terms_conditions;
    }

    public String getOffer_title_label() {
        return offer_title_label;
    }

    public void setOffer_title_label(String offer_title_label) {
        this.offer_title_label = offer_title_label;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public String getOfferTitle() {
        return offerTitle;
    }

    public void setOfferTitle(String offerTitle) {
        this.offerTitle = offerTitle;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getOfferPriceLabel() {
        return offerPriceLabel;
    }

    public void setOfferPriceLabel(String offerPriceLabel) {
        this.offerPriceLabel = offerPriceLabel;
    }

    public String getMin_value() {
        return min_value;
    }

    public void setMin_value(String min_value) {
        this.min_value = min_value;
    }
}
