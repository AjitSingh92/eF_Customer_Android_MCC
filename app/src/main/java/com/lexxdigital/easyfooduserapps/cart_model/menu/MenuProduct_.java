
package com.lexxdigital.easyfooduserapps.cart_model.menu;

import java.util.List;

public class MenuProduct_ {

    private String menuProductId;
    private String productName;
    private String vegType;
    private String menuProductPrice;
    private String userappProductImage;
    private String ecomProductImage;
    private String productOverallRating;
    private List<MenuProductSize_> menuProductSize = null;
    private List<ProductModifier_> productModifiers = null;
    private Upsells_ upsells;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MenuProduct_() {
    }

    /**
     * 
     * @param menuProductId
     * @param productModifiers
     * @param vegType
     * @param menuProductPrice
     * @param upsells
     * @param userappProductImage
     * @param ecomProductImage
     * @param menuProductSize
     * @param productName
     * @param productOverallRating
     */
    public MenuProduct_(String menuProductId, String productName, String vegType, String menuProductPrice, String userappProductImage, String ecomProductImage, String productOverallRating, List<MenuProductSize_> menuProductSize, List<ProductModifier_> productModifiers, Upsells_ upsells) {
        super();
        this.menuProductId = menuProductId;
        this.productName = productName;
        this.vegType = vegType;
        this.menuProductPrice = menuProductPrice;
        this.userappProductImage = userappProductImage;
        this.ecomProductImage = ecomProductImage;
        this.productOverallRating = productOverallRating;
        this.menuProductSize = menuProductSize;
        this.productModifiers = productModifiers;
        this.upsells = upsells;
    }

    public String getMenuProductId() {
        return menuProductId;
    }

    public void setMenuProductId(String menuProductId) {
        this.menuProductId = menuProductId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVegType() {
        return vegType;
    }

    public void setVegType(String vegType) {
        this.vegType = vegType;
    }

    public String getMenuProductPrice() {
        return menuProductPrice;
    }

    public void setMenuProductPrice(String menuProductPrice) {
        this.menuProductPrice = menuProductPrice;
    }

    public String getUserappProductImage() {
        return userappProductImage;
    }

    public void setUserappProductImage(String userappProductImage) {
        this.userappProductImage = userappProductImage;
    }

    public String getEcomProductImage() {
        return ecomProductImage;
    }

    public void setEcomProductImage(String ecomProductImage) {
        this.ecomProductImage = ecomProductImage;
    }

    public String getProductOverallRating() {
        return productOverallRating;
    }

    public void setProductOverallRating(String productOverallRating) {
        this.productOverallRating = productOverallRating;
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

    public Upsells_ getUpsells() {
        return upsells;
    }

    public void setUpsells(Upsells_ upsells) {
        this.upsells = upsells;
    }

}
