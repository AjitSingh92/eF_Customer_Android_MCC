
package com.lexxdigital.easyfooduserapps.select_payment_method.model.checkout_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MenuProduct {

    @SerializedName("menuProductId")
    @Expose
    private String menuProductId;
    @SerializedName("menuProductPrice")
    @Expose
    private String menuProductPrice;
    @SerializedName("menuProductSize")
    @Expose
    private List<MenuProductSize> menuProductSize = null;
    @SerializedName("productModifiers")
    @Expose
    private List<ProductModifier> productModifiers = null;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("upsells")
    @Expose
    private Upsells upsells;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Upsells getUpsells() {
        return upsells;
    }

    public void setUpsells(Upsells upsells) {
        this.upsells = upsells;
    }

}
