
package com.lexxdigital.easyfooduserapps.restaurant_details.model.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuSubCategory {

    @SerializedName("menu_category_id")
    @Expose
    private String menuCategoryId;
    @SerializedName("menu_category_name")
    @Expose
    private String menuCategoryName;
    @SerializedName("menu_sub_category")
    @Expose
    private List<Object> menuSubCategory = null;
    @SerializedName("menu_products")
    @Expose
    private List<MenuProduct> menuProducts = null;

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

    public List<Object> getMenuSubCategory() {
        return menuSubCategory;
    }

    public void setMenuSubCategory(List<Object> menuSubCategory) {
        this.menuSubCategory = menuSubCategory;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public void setMenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

}
