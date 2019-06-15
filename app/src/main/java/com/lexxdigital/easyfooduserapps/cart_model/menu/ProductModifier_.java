
package com.lexxdigital.easyfooduserapps.cart_model.menu;

import java.util.List;

public class ProductModifier_ {

    private String modifierName;
    private String modifierType;
    private String modifierId;
    private Integer minAllowedQuantity;
    private Integer maxAllowedQuantity;
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
     * @param modifierName
     * @param modifierType
     * @param minAllowedQuantity
     */
    public ProductModifier_(String modifierName, String modifierType, String modifierId, Integer minAllowedQuantity, Integer maxAllowedQuantity, List<ModifierProduct_> modifierProducts) {
        super();
        this.modifierName = modifierName;
        this.modifierType = modifierType;
        this.modifierId = modifierId;
        this.minAllowedQuantity = minAllowedQuantity;
        this.maxAllowedQuantity = maxAllowedQuantity;
        this.modifierProducts = modifierProducts;
    }

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
