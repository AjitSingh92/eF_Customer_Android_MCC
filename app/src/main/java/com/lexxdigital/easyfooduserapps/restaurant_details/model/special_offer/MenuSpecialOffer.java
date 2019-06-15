
package com.lexxdigital.easyfooduserapps.restaurant_details.model.special_offer;


public class MenuSpecialOffer {

    private String offerId;
    private String offerTitle;
    private Integer offerAvailable;
    private Integer offerDiscountPercentage;
    private String offerDeliveryOption;
    private String offerDetails;
    private String offerPrice;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MenuSpecialOffer() {
    }

    /**
     * 
     * @param offerDeliveryOption
     * @param offerDetails
     * @param offerTitle
     * @param offerAvailable
     * @param offerPrice
     * @param offerDiscountPercentage
     * @param offerId
     */
    public MenuSpecialOffer(String offerId, String offerTitle, Integer offerAvailable, Integer offerDiscountPercentage, String offerDeliveryOption, String offerDetails, String offerPrice) {
        super();
        this.offerId = offerId;
        this.offerTitle = offerTitle;
        this.offerAvailable = offerAvailable;
        this.offerDiscountPercentage = offerDiscountPercentage;
        this.offerDeliveryOption = offerDeliveryOption;
        this.offerDetails = offerDetails;
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

    public Integer getOfferAvailable() {
        return offerAvailable;
    }

    public void setOfferAvailable(Integer offerAvailable) {
        this.offerAvailable = offerAvailable;
    }

    public Integer getOfferDiscountPercentage() {
        return offerDiscountPercentage;
    }

    public void setOfferDiscountPercentage(Integer offerDiscountPercentage) {
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

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

}
