package com.easyfoodcustomer.modelsNew;

public class CartProdctListModel {

    int id;
    int itemCount;
    String mealID;
    String restaurantID;
    String mealName;
    String mealPrice;
    String vegType;
    String menuCategoryId;
    String description;
    String totalAmoutOfMeal;
    String data;
    boolean isOpen;

    public CartProdctListModel(int id, int itemCount, String mealID, String restaurantID, String mealName, String mealPrice, String vegType, String menuCategoryId, String description, String totalAmoutOfMeal, String data, boolean isOpen) {
        this.id = id;
        this.itemCount = itemCount;
        this.mealID = mealID;
        this.restaurantID = restaurantID;
        this.mealName = mealName;
        this.mealPrice = mealPrice;
        this.vegType = vegType;
        this.menuCategoryId = menuCategoryId;
        this.description = description;
        this.totalAmoutOfMeal = totalAmoutOfMeal;
        this.data = data;
        this.isOpen = isOpen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getMealID() {
        return mealID;
    }

    public void setMealID(String mealID) {
        this.mealID = mealID;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealPrice() {
        return mealPrice;
    }

    public void setMealPrice(String mealPrice) {
        this.mealPrice = mealPrice;
    }

    public String getVegType() {
        return vegType;
    }

    public void setVegType(String vegType) {
        this.vegType = vegType;
    }

    public String getMenuCategoryId() {
        return menuCategoryId;
    }

    public void setMenuCategoryId(String menuCategoryId) {
        this.menuCategoryId = menuCategoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTotalAmoutOfMeal() {
        return totalAmoutOfMeal;
    }

    public void setTotalAmoutOfMeal(String totalAmoutOfMeal) {
        this.totalAmoutOfMeal = totalAmoutOfMeal;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
