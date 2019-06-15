
package com.lexxdigital.easyfooduserapps.cart_model.menu;

import java.util.List;

public class MenuCategory {

    private String menuCategoryId;
    private String menuCategoryName;
    private List<MenuSubCategory> menuSubCategory = null;
    private List<MenuProduct_> menuProducts = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MenuCategory() {
    }

    /**
     * 
     * @param menuCategoryId
     * @param menuCategoryName
     * @param menuSubCategory
     * @param menuProducts
     */
    public MenuCategory(String menuCategoryId, String menuCategoryName, List<MenuSubCategory> menuSubCategory, List<MenuProduct_> menuProducts) {
        super();
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
