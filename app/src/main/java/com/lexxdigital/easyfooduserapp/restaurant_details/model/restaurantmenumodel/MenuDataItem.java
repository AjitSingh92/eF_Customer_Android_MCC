package com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel;

import java.util.ArrayList;

public class MenuDataItem {
    private String menuName;
    private ArrayList<MenuCategoryDataItem> menuCategoryDataItem;

    public MenuDataItem(String menuName, ArrayList<MenuCategoryDataItem> menuCategoryDataItem) {
        this.menuName = menuName;
        this.menuCategoryDataItem = menuCategoryDataItem;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public ArrayList<MenuCategoryDataItem> getMenuCategoryDataItem() {
        return menuCategoryDataItem;
    }

    public void setMenuCategoryDataItem(ArrayList<MenuCategoryDataItem> menuCategoryDataItem) {
        this.menuCategoryDataItem = menuCategoryDataItem;
    }
}
