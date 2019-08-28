
package com.lexxdigital.easyfooduserapps.cart_model.menu_shows;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModifierProduct_ {

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("modifier_product_price")
    @Expose
    private String modifierProductPrice;
    @SerializedName("product_name")
    @Expose
    private String productName;

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
