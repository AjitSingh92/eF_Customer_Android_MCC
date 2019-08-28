
package com.lexxdigital.easyfooduserapps.cart_model.final_cart;

import java.util.List;

public class MenuProductSize {

    private String productSizeId;
    private String productSizeName;
    private String productSizePrice;
    private String amount;
    private Integer quantity;
    private List<SizeModifier> sizeModifiers = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MenuProductSize() {
    }

    /**
     * 
     * @param amount
     * @param productSizeName
     * @param sizeModifiers
     * @param quantity
     * @param productSizePrice
     * @param productSizeId
     */
    public MenuProductSize(String productSizeId, String productSizeName, String productSizePrice, String amount, Integer quantity, List<SizeModifier> sizeModifiers) {
        super();
        this.productSizeId = productSizeId;
        this.productSizeName = productSizeName;
        this.productSizePrice = productSizePrice;
        this.amount = amount;
        this.quantity = quantity;
        this.sizeModifiers = sizeModifiers;
    }

    public String getProductSizeId() {
        return productSizeId;
    }

    public void setProductSizeId(String productSizeId) {
        this.productSizeId = productSizeId;
    }

    public String getProductSizeName() {
        return productSizeName;
    }

    public void setProductSizeName(String productSizeName) {
        this.productSizeName = productSizeName;
    }

    public String getProductSizePrice() {
        return productSizePrice;
    }

    public void setProductSizePrice(String productSizePrice) {
        this.productSizePrice = productSizePrice;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<SizeModifier> getSizeModifiers() {
        return sizeModifiers;
    }

    public void setSizeModifiers(List<SizeModifier> sizeModifiers) {
        this.sizeModifiers = sizeModifiers;
    }

}
