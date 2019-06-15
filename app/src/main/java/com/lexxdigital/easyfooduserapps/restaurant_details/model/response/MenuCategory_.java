
package com.lexxdigital.easyfooduserapps.restaurant_details.model.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuCategory_ {

    @SerializedName("menuCategoryId")
    @Expose
    private String menuCategoryId;
    @SerializedName("menuCategoryName")
    @Expose
    private String menuCategoryName;
    @SerializedName("itemPrice")
    @Expose
    private String itemPrice;
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("menuProducts")
    @Expose
    private List<MenuProduct_> menuProducts = null;
    @SerializedName("productModifiers")
    @Expose
    private List<ProductModifier_> productModifiers = null;

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

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public List<MenuProduct_> getMenuProducts() {
        return menuProducts;
    }

    public void setMenuProducts(List<MenuProduct_> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public List<ProductModifier_> getProductModifiers() {
        return productModifiers;
    }

    public void setProductModifiers(List<ProductModifier_> productModifiers) {
        this.productModifiers = productModifiers;
    }

}
