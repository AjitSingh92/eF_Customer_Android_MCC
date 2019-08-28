
package com.lexxdigital.easyfooduserapps.cart_model.final_cart;


public class ModifierProduct_ {

    private String productId;
    private String productName;
    private String quantity;
    private String productPrice;
    private Integer amount;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ModifierProduct_() {
    }

    /**
     * 
     * @param amount
     * @param quantity
     * @param productPrice
     * @param productName
     * @param productId
     */
    public ModifierProduct_(String productId, String productName, String quantity, String productPrice, Integer amount) {
        super();
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.productPrice = productPrice;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}
