
package com.lexxdigital.easyfooduserapps.model.order_details;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuProduct {

    @SerializedName("menuProductId")
    @Expose
    private String menuProductId;
    @SerializedName("menuProductPrice")
    @Expose
    private String menuProductPrice;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("menuProductSize")
    @Expose
    private List<MenuProductSize> menuProductSize = null;

    public String getMenuProductId() {
        return menuProductId;
    }

    public void setMenuProductId(String menuProductId) {
        this.menuProductId = menuProductId;
    }

    public String getMenuProductPrice() {
        return menuProductPrice;
    }

    public void setMenuProductPrice(String menuProductPrice) {
        this.menuProductPrice = menuProductPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<MenuProductSize> getMenuProductSize() {
        return menuProductSize;
    }

    public void setMenuProductSize(List<MenuProductSize> menuProductSize) {
        this.menuProductSize = menuProductSize;
    }

}
