
package com.lexxdigital.easyfooduserapps.cart_model.final_cart;


public class SizeModifierProduct {

    private String modifierProductPrice;
    private String amount;
    private String productId;
    private String productName;
    private String unit;
    private String quantity;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SizeModifierProduct() {
    }

    /**
     * 
     * @param amount
     * @param unit
     * @param quantity
     * @param modifierProductPrice
     * @param productName
     * @param productId
     */
    public SizeModifierProduct(String modifierProductPrice, String amount, String productId, String productName, String unit, String quantity) {
        super();
        this.modifierProductPrice = modifierProductPrice;
        this.amount = amount;
        this.productId = productId;
        this.productName = productName;
        this.unit = unit;
        this.quantity = quantity;
    }

    public String getModifierProductPrice() {
        return modifierProductPrice;
    }

    public void setModifierProductPrice(String modifierProductPrice) {
        this.modifierProductPrice = modifierProductPrice;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}
