
package com.lexxdigital.easyfooduserapps.cart_model.menu_shows;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuCategory {

    @SerializedName("menu_category_id")
    @Expose
    private String menuCategoryId;
    @SerializedName("menu_category_name")
    @Expose
    private String menuCategoryName;
    @SerializedName("menu_sub_category")
    @Expose
    private List<MenuSubCategory> menuSubCategory = null;
    @SerializedName("menu_products")
    @Expose
    private List<MenuProduct_> menuProducts = null;

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

    public List<MenuSubCategory> getMenuSubCategory() {
        return menuSubCategory;
    }

    public void setMenuSubCategory(List<MenuSubCategory> menuSubCategory) {
        this.menuSubCategory = menuSubCategory;
    }

    public List<MenuProduct_> getMenuProducts() {
        return menuProducts;
    }

    public void setMenuProducts(List<MenuProduct_> menuProducts) {
        this.menuProducts = menuProducts;
    }

}
