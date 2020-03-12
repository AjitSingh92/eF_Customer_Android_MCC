
package com.lexxdigital.easyfooduserapps.restaurant_details.model.show_menu_category;

import java.util.List;

public class MenuProductSize {

    private String productSizeId;
    private String productSizeName;
    private String productSizePrice;
    private List<SizeModifier> sizeModifiers = null;


    public MenuProductSize() {
    }

    public MenuProductSize(String productSizeId, String productSizeName, String productSizePrice, List<SizeModifier> sizeModifiers) {
        super();
        this.productSizeId = productSizeId;
        this.productSizeName = productSizeName;
        this.productSizePrice = productSizePrice;
        this.sizeModifiers = sizeModifiers;
    }

    public String getProductSizeId() {
        return productSizeId;
    }

    public void setProductSizeId(String productSizeId) {
        this.productSizeId = productSizeId;
    }

    public String getProductSizeName() {
        return productSizeName;
    }

    public void setProductSizeName(String productSizeName) {
        this.productSizeName = productSizeName;
    }

    public String getProductSizePrice() {
        return productSizePrice;
    }

    public void setProductSizePrice(String productSizePrice) {
        this.productSizePrice = productSizePrice;
    }

    public List<SizeModifier> getSizeModifiers() {
        return sizeModifiers;
    }

    public void setSizeModifiers(List<SizeModifier> sizeModifiers) {
        this.sizeModifiers = sizeModifiers;
    }

}
