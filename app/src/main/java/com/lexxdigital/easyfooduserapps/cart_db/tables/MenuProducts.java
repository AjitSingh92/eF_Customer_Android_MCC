package com.lexxdigital.easyfooduserapps.cart_db.tables;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.lexxdigital.easyfooduserapps.cart_db.converters.MealConverter;
import com.lexxdigital.easyfooduserapps.cart_db.converters.MenuProductConverter;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.Meal;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;

import java.util.List;


public class MenuProducts {
    @Ignore
    @SerializedName("success")
    Boolean success;

    @SerializedName("data")
    MenuProductsTable data;

    public MenuProducts(Boolean success, MenuProductsTable data) {
        this.success = success;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public MenuProductsTable getData() {
        return data;
    }

    public void setData(MenuProductsTable data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MenuProducts{" +
                "success=" + success +
                ", data=" + data +
                '}';
    }

    @Entity//(indices = {@Index(value = "categoryId", unique = true)})
    public static class MenuProductsTable {
        @PrimaryKey
        @NonNull
        String categoryId;
        @TypeConverters({MenuProductConverter.class})
        @SerializedName("menuProducts")
        List<MenuProduct> menuProducts;
        @TypeConverters({MealConverter.class})
        @SerializedName("meal")
        List<Meal> meal;

        @Ignore
        public MenuProductsTable() {
        }

        public MenuProductsTable(@NonNull String categoryId, List<MenuProduct> menuProducts, List<Meal> meal) {
            this.categoryId = categoryId;
            this.menuProducts = menuProducts;
            this.meal = meal;
        }

        @NonNull
        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(@NonNull String categoryId) {
            this.categoryId = categoryId;
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
            return "MenuProductsTable{" +
                    "categoryId='" + categoryId + '\'' +
                    ", menuProducts=" + menuProducts +
                    ", meal=" + meal +
                    '}';
        }
    }
}
