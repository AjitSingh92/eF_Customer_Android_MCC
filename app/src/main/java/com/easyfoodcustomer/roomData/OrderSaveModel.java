package com.easyfoodcustomer.roomData;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "OrderHistory")
public class OrderSaveModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
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
    boolean isFinal;
    String data;

    @Ignore
    public OrderSaveModel(int id,int  itemCount,String mealID, String restaurantID, String mealName, String mealPrice,
                          String vegType, String menuCategoryId, String description, String totalAmoutOfMeal,boolean isFinal, String data) {
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
        this.isFinal = isFinal;
        this.data = data;
    }

    public OrderSaveModel(int itemCount,String mealID, String restaurantID, String mealName, String mealPrice, String vegType,
                          String menuCategoryId, String description, String totalAmoutOfMeal,boolean isFinal, String data) {
        this.itemCount = itemCount;
        this.mealID = mealID;
        this.restaurantID = restaurantID;
        this.mealName = mealName;
        this.mealPrice = mealPrice;
        this.vegType = vegType;
        this.menuCategoryId = menuCategoryId;
        this.description = description;
        this.totalAmoutOfMeal = totalAmoutOfMeal;
        this.isFinal = isFinal;
        this.data = data;
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

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
