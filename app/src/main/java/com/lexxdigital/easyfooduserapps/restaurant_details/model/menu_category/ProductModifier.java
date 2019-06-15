
package com.lexxdigital.easyfooduserapps.restaurant_details.model.menu_category;

import java.util.List;

public class ProductModifier {

    private String modifierName;
    private String modifierType;
    private String modifierId;
    private Integer minAllowedQuantity;
    private Integer maxAllowedQuantity;
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
     * @param modifierName
     * @param modifierType
     * @param minAllowedQuantity
     */
    public ProductModifier(String modifierName, String modifierType, String modifierId, Integer minAllowedQuantity, Integer maxAllowedQuantity, List<ModifierProduct> modifierProducts) {
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

    public List<ModifierProduct> getModifierProducts() {
        return modifierProducts;
    }

    public void setModifierProducts(List<ModifierProduct> modifierProducts) {
        this.modifierProducts = modifierProducts;
    }

}
