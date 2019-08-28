
package com.lexxdigital.easyfooduserapps.restaurant_details.model.menu_category;

import java.util.List;

public class MenuCategory {

    private String menuCategoryId;
    private String menuCategoryName;
    private List<Object> menuSubCategory = null;
    private List<MenuProduct> menuProducts = null;

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
    public MenuCategory(String menuCategoryId, String menuCategoryName, List<Object> menuSubCategory, List<MenuProduct> menuProducts) {
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
