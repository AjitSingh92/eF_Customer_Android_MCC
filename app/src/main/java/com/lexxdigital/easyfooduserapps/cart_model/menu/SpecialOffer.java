
package com.lexxdigital.easyfooduserapps.cart_model.menu;


public class SpecialOffer {

    private String offerId;
    private String offerTitle;
    private String offerAvailable;
    private String spendAmountToAvaliableOffer;
    private String offerDiscountPercentage;
    private String offerDeliveryOption;
    private String offerDetails;
    private String originalPrice;
    private String totalDiscount;
    private String offerPrice;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SpecialOffer() {
    }

    /**
     * 
     * @param offerDeliveryOption
     * @param spendAmountToAvaliableOffer
     * @param offerDetails
     * @param offerTitle
     * @param totalDiscount
     * @param originalPrice
     * @param offerAvailable
     * @param offerPrice
     * @param offerDiscountPercentage
     * @param offerId
     */
    public SpecialOffer(String offerId, String offerTitle, String offerAvailable, String spendAmountToAvaliableOffer, String offerDiscountPercentage, String offerDeliveryOption, String offerDetails, String originalPrice, String totalDiscount, String offerPrice) {
        super();
        this.offerId = offerId;
        this.offerTitle = offerTitle;
        this.offerAvailable = offerAvailable;
        this.spendAmountToAvaliableOffer = spendAmountToAvaliableOffer;
        this.offerDiscountPercentage = offerDiscountPercentage;
        this.offerDeliveryOption = offerDeliveryOption;
        this.offerDetails = offerDetails;
        this.originalPrice = originalPrice;
        this.totalDiscount = totalDiscount;
        this.offerPrice = offerPrice;
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
