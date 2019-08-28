package com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class MenuCategoryCart {
    @Expose
    int id;
    @Expose
    int menuId;
    @Expose
    private String menuCategoryId;
    @Expose
    private String menuCategoryName;
    @Expose
    private List<MenuCategoryCart> menuSubCategory = new ArrayList<>();
    @Expose
    private List<MenuProduct> menuProducts = new ArrayList<>();

    public MenuCategoryCart() {
    }

    public MenuCategoryCart(String menuCategoryId, String menuCategoryName, List<MenuCategoryCart> menuSubCategory, List<MenuProduct> menuProducts) {
        this.menuCategoryId = menuCategoryId;
        this.menuCategoryName = menuCategoryName;
        this.menuSubCategory = menuSubCategory;
        this.menuProducts = menuProducts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
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

    public List<MenuCategoryCart> getMenuSubCategory() {
        return menuSubCategory;
    }

    public void setMenuSubCategory(List<MenuCategoryCart> menuSubCategory) {
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
        return "MenuCategoryCart{" +
                "id=" + id +
                ", menuId=" + menuId +
                ", menuCategoryId='" + menuCategoryId + '\'' +
                ", menuCategoryName='" + menuCategoryName + '\'' +
                ", menuSubCategory=" + menuSubCategory +
                ", menuProducts=" + menuProducts +
                '}';
    }
}