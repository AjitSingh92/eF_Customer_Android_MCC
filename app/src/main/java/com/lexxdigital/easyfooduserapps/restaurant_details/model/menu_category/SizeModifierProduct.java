
package com.lexxdigital.easyfooduserapps.restaurant_details.model.menu_category;


public class SizeModifierProduct {

    private String productId;
    private String unit;
    private String modifierProductPrice;
    private String productName;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SizeModifierProduct() {
    }

    /**
     * 
     * @param unit
     * @param modifierProductPrice
     * @param productName
     * @param productId
     */
    public SizeModifierProduct(String productId, String unit, String modifierProductPrice, String productName) {
        super();
        this.productId = productId;
        this.unit = unit;
        this.modifierProductPrice = modifierProductPrice;
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getModifierProductPrice() {
        return modifierProductPrice;
    }

    public void setModifierProductPrice(String modifierProductPrice) {
        this.modifierProductPrice = modifierProductPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

}
