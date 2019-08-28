
package com.lexxdigital.easyfooduserapps.model.order_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SizeModifierProduct {

    @SerializedName("modifierProductPrice")
    @Expose
    private String modifierProductPrice;
    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;

    public String getModifierProductPrice() {
        return modifierProductPrice;
    }

    public void setModifierProductPrice(String modifierProductPrice) {
        this.modifierProductPrice = modifierProductPrice;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
