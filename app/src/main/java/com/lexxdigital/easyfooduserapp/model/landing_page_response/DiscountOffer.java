
package com.lexxdigital.easyfooduserapp.model.landing_page_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiscountOffer {

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
}
