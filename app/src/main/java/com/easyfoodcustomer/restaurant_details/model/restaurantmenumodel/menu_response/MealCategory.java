package com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.easyfoodcustomer.cart_db.tables.ProductSizeAndModifier;

import java.util.List;

public class MealCategory {

    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("quantity")
    @Expose
    private int quantity;

    @SerializedName("allowed_quantity")
    @Expose
    private int allowed_quantity;

    @SerializedName("customizable")
    @Expose
    private int customizable;
    private int selectedCount;
    @SerializedName("products")
    @Expose
    private List<MealProduct> mealProducts = null;

    public MealCategory() {
    }

    ProductSizeAndModifier.ProductSizeAndModifierTable productSizeAndModifierTable;

    public MealCategory(String categoryName, int quantity, int customizable, List<MealProduct> mealProducts) {
        this.categoryName = categoryName;
        this.quantity = quantity;
        this.customizable = customizable;
        this.mealProducts = mealProducts;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCustomizable() {
        return customizable;
    }

    public void setCustomizable(int customizable) {
        this.customizable = customizable;
    }

    public List<MealProduct> getMealProducts() {
        return mealProducts;
    }

    public int getSelectedCount() {
        return selectedCount;
    }

    public void setSelectedCount(int selectedCount) {
        this.selectedCount = selectedCount;
    }

    public int getAllowed_quantity() {
        return allowed_quantity;
    }

    public void setAllowed_quantity(int allowed_quantity) {
        this.allowed_quantity = allowed_quantity;
    }

    public void setMealProducts(List<MealProduct> mealProducts) {
        this.mealProducts = mealProducts;
    }

    @Override
    public String toString() {
        return "MealCategory{" +
                "categoryName='" + categoryName + '\'' +
                ", quantity=" + quantity +
                ", customizable=" + customizable +
                ", selectedCount=" + selectedCount +
                ", allowed_quantity=" + allowed_quantity +
                ", mealProducts=" + mealProducts +
                '}';
    }
}
