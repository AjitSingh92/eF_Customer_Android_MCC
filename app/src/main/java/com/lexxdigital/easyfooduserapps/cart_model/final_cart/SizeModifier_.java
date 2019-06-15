
package com.lexxdigital.easyfooduserapps.cart_model.final_cart;

import java.util.List;

public class SizeModifier_ {

    private String maxAllowedQuantity;
    private String minAllowedQuantity;
    private String modifierId;
    private String modifierName;
    private String modifierType;
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
    public SizeModifier_(String maxAllowedQuantity, String minAllowedQuantity, String modifierId, String modifierName, String modifierType, List<SizeModifierProduct_> sizeModifierProducts) {
        super();
        this.maxAllowedQuantity = maxAllowedQuantity;
        this.minAllowedQuantity = minAllowedQuantity;
        this.modifierId = modifierId;
        this.modifierName = modifierName;
        this.modifierType = modifierType;
        this.sizeModifierProducts = sizeModifierProducts;
    }

    public String getMaxAllowedQuantity() {
        return maxAllowedQuantity;
    }

    public void setMaxAllowedQuantity(String maxAllowedQuantity) {
        this.maxAllowedQuantity = maxAllowedQuantity;
    }

    public String getMinAllowedQuantity() {
        return minAllowedQuantity;
    }

    public void setMinAllowedQuantity(String minAllowedQuantity) {
        this.minAllowedQuantity = minAllowedQuantity;
    }

    public String getModifierId() {
        return modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
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

    public List<SizeModifierProduct_> getSizeModifierProducts() {
        return sizeModifierProducts;
    }

    public void setSizeModifierProducts(List<SizeModifierProduct_> sizeModifierProducts) {
        this.sizeModifierProducts = sizeModifierProducts;
    }

}