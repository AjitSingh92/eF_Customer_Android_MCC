
package com.lexxdigital.easyfooduserapps.restaurant_details.model.show_menu_category;

import java.util.List;

public class MenuCategory {

    private String menuCategoryId;
    private String menuCategoryName;
    private List<MenuProduct> menuProducts = null;


    public MenuCategory() {
    }


    public MenuCategory(String menuCategoryId, String menuCategoryName, List<MenuProduct> menuProducts) {
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

}
