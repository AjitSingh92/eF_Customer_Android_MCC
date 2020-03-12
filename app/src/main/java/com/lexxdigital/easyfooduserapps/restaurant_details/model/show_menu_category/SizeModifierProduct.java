
package com.lexxdigital.easyfooduserapps.restaurant_details.model.show_menu_category;


public class SizeModifierProduct {

    private String productId;
    private String unit;
    private String modifierProductPrice;
    private String productName;
    private Integer quantity;
    private Double totalPrice;

    public SizeModifierProduct() {
    }


    public SizeModifierProduct(String productId, String unit, String modifierProductPrice, String productName, Integer qty, Double tPrice) {
        super();
        this.productId = productId;
        this.unit = unit;
        this.modifierProductPrice = modifierProductPrice;
        this.productName = productName;
        this.quantity = qty;
        this.totalPrice = tPrice;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
