
package com.lexxdigital.easyfooduserapp.cart_model.final_cart;

import java.util.List;

public class ProductModifier_ {

    private String modifierId;
    private String minAllowedQuantity;
    private String maxAllowedQuantity;
    private String modifierType;
    private List<ModifierProduct_> modifierProducts = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ProductModifier_() {
    }

    /**
     * 
     * @param maxAllowedQuantity
     * @param modifierId
     * @param modifierProducts
     * @param modifierType
     * @param minAllowedQuantity
     */
    public ProductModifier_(String modifierId, String minAllowedQuantity, String maxAllowedQuantity, String modifierType, List<ModifierProduct_> modifierProducts) {
        super();
        this.modifierId = modifierId;
        this.minAllowedQuantity = minAllowedQuantity;
        this.maxAllowedQuantity = maxAllowedQuantity;
        this.modifierType = modifierType;
        this.modifierProducts = modifierProducts;
    }

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

    public List<ModifierProduct_> getModifierProducts() {
        return modifierProducts;
    }

    public void setModifierProducts(List<ModifierProduct_> modifierProducts) {
        this.modifierProducts = modifierProducts;
    }

}
