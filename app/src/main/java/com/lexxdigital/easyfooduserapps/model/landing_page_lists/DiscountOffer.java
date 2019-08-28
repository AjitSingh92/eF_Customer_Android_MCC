
package com.lexxdigital.easyfooduserapps.model.landing_page_lists;


public class DiscountOffer {

    private String offerId;
    private String restaurantId;
    private String offerType;
    private String offerTitle;
    private String detail;
    private String offerPrice;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DiscountOffer() {
    }

    /**
     * 
     * @param detail
     * @param offerType
     * @param offerTitle
     * @param offerPrice
     * @param restaurantId
     * @param offerId
     */
    public DiscountOffer(String offerId, String restaurantId, String offerType, String offerTitle, String detail, String offerPrice) {
        super();
        this.offerId = offerId;
        this.restaurantId = restaurantId;
        this.offerType = offerType;
        this.offerTitle = offerTitle;
        this.detail = detail;
        this.offerPrice = offerPrice;
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

}
