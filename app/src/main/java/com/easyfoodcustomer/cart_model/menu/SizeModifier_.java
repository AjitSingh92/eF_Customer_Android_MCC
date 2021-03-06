
package com.easyfoodcustomer.cart_model.menu;

import java.util.List;

public class SizeModifier_ {

    private String modifierName;
    private String modifierType;
    private String modifierId;
    private Integer minAllowedQuantity;
    private Integer maxAllowedQuantity;
    private List<SizeModifierProduct_> sizeModifierProducts = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SizeModifier_() {
    }

    /**
     * 
     * @param maxAllowedQuantity
     * @param modifierId
     * @param modifierName
     * @param modifierType
     * @param sizeModifierProducts
     * @param minAllowedQuantity
     */
    public SizeModifier_(String modifierName, String modifierType, String modifierId, Integer minAllowedQuantity, Integer maxAllowedQuantity, List<SizeModifierProduct_> sizeModifierProducts) {
        super();
        this.modifierName = modifierName;
        this.modifierType = modifierType;
        this.modifierId = modifierId;
        this.minAllowedQuantity = minAllowedQuantity;
        this.maxAllowedQuantity = maxAllowedQuantity;
        this.sizeModifierProducts = sizeModifierProducts;
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

    public List<SizeModifierProduct_> getSizeModifierProducts() {
        return sizeModifierProducts;
    }

    public void setSizeModifierProducts(List<SizeModifierProduct_> sizeModifierProducts) {
        this.sizeModifierProducts = sizeModifierProducts;
    }

}
