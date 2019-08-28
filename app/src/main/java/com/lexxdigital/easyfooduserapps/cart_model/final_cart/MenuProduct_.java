
package com.lexxdigital.easyfooduserapps.cart_model.final_cart;

import java.util.List;

public class MenuProduct_ {

    private String menuProductId;
    private String menuProductPrice;
    private String productName;
    private String amount;
    private Integer quantity;
    private List<MenuProductSize_> menuProductSize = null;
    private List<ProductModifier_> productModifiers = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MenuProduct_() {
    }

    /**
     * 
     * @param amount
     * @param menuProductId
     * @param productModifiers
     * @param menuProductPrice
     * @param quantity
     * @param menuProductSize
     * @param productName
     */
    public MenuProduct_(String menuProductId, String menuProductPrice, String productName, String amount, Integer quantity, List<MenuProductSize_> menuProductSize, List<ProductModifier_> productModifiers) {
        super();
        this.menuProductId = menuProductId;
        this.menuProductPrice = menuProductPrice;
        this.productName = productName;
        this.amount = amount;
        this.quantity = quantity;
        this.menuProductSize = menuProductSize;
        this.productModifiers = productModifiers;
    }

    public String getMenuProductId() {
        return menuProductId;
    }

    public void setMenuProductId(String menuProductId) {
        this.menuProductId = menuProductId;
    }

    public String getMenuProductPrice() {
        return menuProductPrice;
    }

    public void setMenuProductPrice(String menuProductPrice) {
        this.menuProductPrice = menuProductPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public List<MenuProductSize_> getMenuProductSize() {
        return menuProductSize;
    }

    public void setMenuProductSize(List<MenuProductSize_> menuProductSize) {
        this.menuProductSize = menuProductSize;
    }

    public List<ProductModifier_> getProductModifiers() {
        return productModifiers;
    }

    public void setProductModifiers(List<ProductModifier_> productModifiers) {
        this.productModifiers = productModifiers;
    }

}
