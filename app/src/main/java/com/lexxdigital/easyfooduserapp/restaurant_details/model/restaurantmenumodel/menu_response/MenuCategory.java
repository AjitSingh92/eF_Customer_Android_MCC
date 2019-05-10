package com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MenuCategory {

    @SerializedName("menuCategoryId")
    @Expose
    private String menuCategoryId;
    @SerializedName("menuCategoryName")
    @Expose
    private String menuCategoryName;
    @SerializedName("menuSubCategory")
    @Expose
    private List<MenuCategory> menuSubCategory = null;
    @SerializedName("menuProducts")
    @Expose
    private List<MenuProduct> menuProducts = null;

    public MenuCategory(String menuCategoryId, String menuCategoryName, List<MenuCategory> menuSubCategory, List<MenuProduct> menuProducts) {
        this.menuCategoryId = menuCategoryId;
        this.menuCategoryName = menuCategoryName;
        this.menuSubCategory = menuSubCategory;
        this.menuProducts = menuProducts;
    }

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

    public List<MenuCategory> getMenuSubCategory() {
        return menuSubCategory;
    }

    public void setMenuSubCategory(List<MenuCategory> menuSubCategory) {
        this.menuSubCategory = menuSubCategory;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public void setMenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    @Override
    public String toString() {
        return "MenuCategory{" +
                "menuCategoryId='" + menuCategoryId + '\'' +
                ", menuCategoryName='" + menuCategoryName + '\'' +
                ", menuSubCategory=" + menuSubCategory +
                ", menuProducts=" + menuProducts +
                '}';
    }
}