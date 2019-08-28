
package com.lexxdigital.easyfooduserapps.cart_model.final_cart;

import java.util.List;

public class ProductModifier {

    private String modifierId;
    private String minAllowedQuantity;
    private String maxAllowedQuantity;
    private String modifierType;
    private List<ModifierProduct> modifierProducts = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ProductModifier() {
    }

    /**
     * 
     * @param maxAllowedQuantity
     * @param modifierId
     * @param modifierProducts
     * @param modifierType
     * @param minAllowedQuantity
     */
    public ProductModifier(String modifierId, String minAllowedQuantity, String maxAllowedQuantity, String modifierType, List<ModifierProduct> modifierProducts) {
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

    public List<ModifierProduct> getModifierProducts() {
        return modifierProducts;
    }

    public void setModifierProducts(List<ModifierProduct> modifierProducts) {
        this.modifierProducts = modifierProducts;
    }

}
