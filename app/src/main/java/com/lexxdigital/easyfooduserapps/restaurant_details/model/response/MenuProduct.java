
package com.lexxdigital.easyfooduserapps.restaurant_details.model.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuProduct {

    @SerializedName("menu_product_id")
    @Expose
    private String menuProductId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("veg_type")
    @Expose
    private String vegType;
    @SerializedName("menu_product_price")
    @Expose
    private String menuProductPrice;
    @SerializedName("userapp_product_image")
    @Expose
    private String userappProductImage;
    @SerializedName("ecom_product_image")
    @Expose
    private String ecomProductImage;
    @SerializedName("product_overall_rating")
    @Expose
    private String productOverallRating;
    @SerializedName("menu_product_size")
    @Expose
    private List<MenuProductSize> menuProductSize = null;
    @SerializedName("product_modifiers")
    @Expose
    private List<ProductModifier> productModifiers = null;

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

    public List<MenuProductSize> getMenuProductSize() {
        return menuProductSize;
    }

    public void setMenuProductSize(List<MenuProductSize> menuProductSize) {
        this.menuProductSize = menuProductSize;
    }

    public List<ProductModifier> getProductModifiers() {
        return productModifiers;
    }

    public void setProductModifiers(List<ProductModifier> productModifiers) {
        this.productModifiers = productModifiers;
    }

}
