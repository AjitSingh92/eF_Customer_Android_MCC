
package com.lexxdigital.easyfooduserapps.restaurant_details.model.show_menu_category;


public class UpsellProduct {

    private String productId;
    private String productName;
    private String unit;
    private Integer productPrice;


    public UpsellProduct() {
    }

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
