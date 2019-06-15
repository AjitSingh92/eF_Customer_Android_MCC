
package com.lexxdigital.easyfooduserapps.cart_model.menu_shows;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuProductSize_ {

    @SerializedName("product_size_id")
    @Expose
    private String productSizeId;
    @SerializedName("product_size_name")
    @Expose
    private String productSizeName;
    @SerializedName("product_size_price")
    @Expose
    private String productSizePrice;
    @SerializedName("size_modifiers")
    @Expose
    private List<SizeModifier_> sizeModifiers = null;

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

    public List<SizeModifier_> getSizeModifiers() {
        return sizeModifiers;
    }

    public void setSizeModifiers(List<SizeModifier_> sizeModifiers) {
        this.sizeModifiers = sizeModifiers;
    }

}
