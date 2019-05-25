package com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealProductModifierData {
    @SerializedName("success")
    Boolean success;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    Data data;

    public MealProductModifierData(Boolean success, String message, Data data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MealProductModifierData{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static class Data {

        @SerializedName("menuProductSize")
        @Expose
        private List<MenuProductSize> menuProductSize = null;
        @SerializedName("productModifiers")
        @Expose
        private List<ProductModifier> productModifiers = null;

        public Data(List<MenuProductSize> menuProductSize, List<ProductModifier> productModifiers) {
            this.menuProductSize = menuProductSize;
            this.productModifiers = productModifiers;
        }

        public List<MenuProductSize> getMenuProductSize() {
            return menuProductSize;
        }

        public void setMenuProductSize(List<MenuProductSize> menuProductSize) {
            this.menuProductSize = menuProductSize;
        }

        public List<ProductModifier> getProductModifiers() {
            return productModifiers;
        }

        public void setProductModifiers(List<ProductModifier> productModifiers) {
            this.productModifiers = productModifiers;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "menuProductSize=" + menuProductSize +
                    ", productModifiers=" + productModifiers +
                    '}';
        }
    }

}
