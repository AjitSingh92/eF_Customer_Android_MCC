package com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response;

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


    @SerializedName("dine_in")
    @Expose
    private int dine_in;
    @SerializedName("delivery")
    @Expose
    private int delivery;
    @SerializedName("collection")
    @Expose
    private int collection;


    @SerializedName("menuSubCategory")
    @Expose
    private List<MenuCategory> menuSubCategory = null;
    @SerializedName("menuProducts")
    @Expose
    private List<MenuProduct> menuProducts = null;
    @SerializedName("meal")
    @Expose
    List<Meal> meal;


    public MenuCategory(String menuCategoryId, String menuCategoryName,int dine_in,int delivery,int collection, List<MenuCategory> menuSubCategory, List<MenuProduct> menuProducts, List<Meal> meal) {
        this.menuCategoryId = menuCategoryId;
        this.menuCategoryName = menuCategoryName;
        this.menuSubCategory = menuSubCategory;
        this.menuProducts = menuProducts;
        this.meal = meal;
        this.dine_in=dine_in;
        this.delivery=delivery;
        this.collection=collection;
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


    public int getDine_in() {
        return dine_in;
    }

    public void setDine_in(int dine_in) {
        this.dine_in = dine_in;
    }

    public int getDelivery() {
        return delivery;
    }

    public void setDelivery(int delivery) {
        this.delivery = delivery;
    }

    public int getCollection() {
        return collection;
    }

    public void setCollection(int collection) {
        this.collection = collection;
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

    public List<Meal> getMeal() {
        return meal;
    }

    public void setMeal(List<Meal> meal) {
        this.meal = meal;
    }

    @Override
    public String toString() {
        return "MenuCategory{" +
                "menuCategoryId='" + menuCategoryId + '\'' +
                ", menuCategoryName='" + menuCategoryName + '\'' +
                ", menuSubCategory=" + menuSubCategory +
                ", menuProducts=" + menuProducts +
                ", meal=" + meal +
                ", dine_in=" + dine_in +
                ", delivery=" + delivery +
                ", collection=" + collection +
                '}';
    }
}