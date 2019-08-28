
package com.lexxdigital.easyfooduserapps.cart_model.menu;


public class UpsellProduct {

    private String productId;
    private String productName;
    private String unit;
    private Integer productPrice;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UpsellProduct() {
    }

    /**
     * 
     * @param unit
     * @param productPrice
     * @param productName
     * @param productId
     */
    public UpsellProduct(String productId, String productName, String unit, Integer productPrice) {
        super();
        this.productId = productId;
        this.productName = productName;
        this.unit = unit;
        this.productPrice = productPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

}
