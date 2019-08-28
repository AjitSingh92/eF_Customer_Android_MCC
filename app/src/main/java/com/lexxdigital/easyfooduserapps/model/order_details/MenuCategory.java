
package com.lexxdigital.easyfooduserapps.model.order_details;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuCategory {

    @SerializedName("menuCategoryId")
    @Expose
    private String menuCategoryId;
    @SerializedName("menuCategoryName")
    @Expose
    private String menuCategoryName;
    @SerializedName("item_id")
    @Expose
    private Integer itemId;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("menuProducts")
    @Expose
    private List<MenuProduct> menuProducts = null;
    @SerializedName("productModifiers")
    @Expose
    private List<ProductModifier> productModifiers = null;

    public String getMenuCategoryId() {
        return menuCategoryId;
    }

    public void setMenuCategoryId(String menuCategoryId) {
        this.menuCategoryId = menuCategoryId;
    }

    public String getMenuCategoryName() {
        return menuCategoryName;
    }

    public void setMenuCategoryName(String menuCategoryName) {
        this.menuCategoryName = menuCategoryName;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public void setMenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public List<ProductModifier> getProductModifiers() {
        return productModifiers;
    }

    public void setProductModifiers(List<ProductModifier> productModifiers) {
        this.productModifiers = productModifiers;
    }

}
