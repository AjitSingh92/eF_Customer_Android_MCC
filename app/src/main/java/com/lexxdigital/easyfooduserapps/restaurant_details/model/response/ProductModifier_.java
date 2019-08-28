
package com.lexxdigital.easyfooduserapps.restaurant_details.model.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductModifier_ {

    @SerializedName("modifier_name")
    @Expose
    private String modifierName;
    @SerializedName("modifier_type")
    @Expose
    private String modifierType;
    @SerializedName("modifier_id")
    @Expose
    private String modifierId;
    @SerializedName("min_allowed_quantity")
    @Expose
    private Integer minAllowedQuantity;
    @SerializedName("max_allowed_quantity")
    @Expose
    private Integer maxAllowedQuantity;
    @SerializedName("modifier_products")
    @Expose
    private List<ModifierProduct_> modifierProducts = null;

    public String getModifierName() {
        return modifierName;
    }

    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }

    public String getModifierType() {
        return modifierType;
    }

    public void setModifierType(String modifierType) {
        this.modifierType = modifierType;
    }

    public String getModifierId() {
        return modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }

    public Integer getMinAllowedQuantity() {
        return minAllowedQuantity;
    }

    public void setMinAllowedQuantity(Integer minAllowedQuantity) {
        this.minAllowedQuantity = minAllowedQuantity;
    }

    public Integer getMaxAllowedQuantity() {
        return maxAllowedQuantity;
    }

    public void setMaxAllowedQuantity(Integer maxAllowedQuantity) {
        this.maxAllowedQuantity = maxAllowedQuantity;
    }

    public List<ModifierProduct_> getModifierProducts() {
        return modifierProducts;
    }

    public void setModifierProducts(List<ModifierProduct_> modifierProducts) {
        this.modifierProducts = modifierProducts;
    }

}
