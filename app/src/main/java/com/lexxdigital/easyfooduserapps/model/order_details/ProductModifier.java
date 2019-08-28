
package com.lexxdigital.easyfooduserapps.model.order_details;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductModifier {

    @SerializedName("modifierId")
    @Expose
    private String modifierId;
    @SerializedName("minAllowedQuantity")
    @Expose
    private String minAllowedQuantity;
    @SerializedName("maxAllowedQuantity")
    @Expose
    private String maxAllowedQuantity;
    @SerializedName("modifierType")
    @Expose
    private String modifierType;
    @SerializedName("modifierProducts")
    @Expose
    private List<ModifierProduct> modifierProducts = null;

    public String getModifierId() {
        return modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }

    public String getMinAllowedQuantity() {
        return minAllowedQuantity;
    }

    public void setMinAllowedQuantity(String minAllowedQuantity) {
        this.minAllowedQuantity = minAllowedQuantity;
    }

    public String getMaxAllowedQuantity() {
        return maxAllowedQuantity;
    }

    public void setMaxAllowedQuantity(String maxAllowedQuantity) {
        this.maxAllowedQuantity = maxAllowedQuantity;
    }

    public String getModifierType() {
        return modifierType;
    }

    public void setModifierType(String modifierType) {
        this.modifierType = modifierType;
    }

    public List<ModifierProduct> getModifierProducts() {
        return modifierProducts;
    }

    public void setModifierProducts(List<ModifierProduct> modifierProducts) {
        this.modifierProducts = modifierProducts;
    }

}
