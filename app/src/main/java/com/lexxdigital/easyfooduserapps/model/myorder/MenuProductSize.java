
package com.lexxdigital.easyfooduserapps.model.myorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MenuProductSize {

    @SerializedName("productSizeId")
    @Expose
    private String productSizeId;
    @SerializedName("productSizeName")
    @Expose
    private String productSizeName;
    @SerializedName("productSizePrice")
    @Expose
    private String productSizePrice;
    @SerializedName("sizeModifiers")
    @Expose
    private List<SizeModifier> sizeModifiers = null;

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
