
package com.lexxdigital.easyfooduserapps.cart_model.final_cart;

import java.util.List;

public class MenuSubCategory {

    private String menuCategoryId;
    private String menuCategoryName;
    private List<MenuProduct> menuProducts = null;

    /**
     * No args constructor for use in serialization
     */
    public MenuSubCategory() {
    }

    /**
     * @param menuCategoryId
     * @param menuCategoryName
     * @param menuProducts
     */
    public MenuSubCategory(String menuCategoryId, String menuCategoryName, List<MenuProduct> menuProducts) {
        super();
        this.menuCategoryId = menuCategoryId;
        this.menuCategoryName = menuCategoryName;
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

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public void setMenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    @Override
    public String toString() {
        return "MenuSubCategory{" +
                "menuCategoryId='" + menuCategoryId + '\'' +
                ", menuCategoryName='" + menuCategoryName + '\'' +
                ", menuProducts=" + menuProducts +
                '}';
    }
}
