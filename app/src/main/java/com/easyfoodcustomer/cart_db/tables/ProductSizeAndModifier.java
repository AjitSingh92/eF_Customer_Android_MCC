package com.easyfoodcustomer.cart_db.tables;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import androidx.annotation.NonNull;

import com.easyfoodcustomer.cart_db.converters.ProductMainDetailConverter;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.ProductMainDetails;
import com.google.gson.annotations.SerializedName;
import com.easyfoodcustomer.cart_db.converters.MenuProductSizeConverter;
import com.easyfoodcustomer.cart_db.converters.ProductModifierConverter;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuProductSize;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.ProductModifier;

import java.util.List;

public class ProductSizeAndModifier {

    @SerializedName("success")
    Boolean success;

    @SerializedName("data")
    ProductSizeAndModifierTable productSizeAndModifier;

    public ProductSizeAndModifier(Boolean success, ProductSizeAndModifierTable productSizeAndModifier) {
        this.success = success;
        this.productSizeAndModifier = productSizeAndModifier;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ProductSizeAndModifierTable getProductSizeAndModifier() {
        return productSizeAndModifier;
    }

    public void setProductSizeAndModifier(ProductSizeAndModifierTable productSizeAndModifier) {
        this.productSizeAndModifier = productSizeAndModifier;
    }

    @Override
    public String toString() {
        return "ProductSizeAndModifier{" +
                "success=" + success +
                ", productSizeAndModifier=" + productSizeAndModifier +
                '}';
    }

    @Entity
    public static class ProductSizeAndModifierTable {
        @PrimaryKey
        @NonNull
        String productId;

        @TypeConverters({ProductModifierConverter.class})
        @SerializedName("productModifiers")
        List<ProductModifier> productModifiers;
        @TypeConverters({MenuProductSizeConverter.class})
        @SerializedName("menuProductSize")
        List<MenuProductSize> menuProductSize;

        @TypeConverters({ProductMainDetailConverter.class})
        @SerializedName("productMainDetails")
        ProductMainDetails productMainDetails;

        @Ignore
        public ProductSizeAndModifierTable() {
        }

        public ProductSizeAndModifierTable(@NonNull String productId, List<ProductModifier> productModifiers, List<MenuProductSize> menuProductSize, ProductMainDetails productMainDetails) {
            this.productId = productId;
            this.productModifiers = productModifiers;
            this.menuProductSize = menuProductSize;
            this.productMainDetails = productMainDetails;
        }

        @NonNull
        public String getProductId() {
            return productId;
        }

        public void setProductId(@NonNull String productId) {
            this.productId = productId;
        }

        public List<ProductModifier> getProductModifiers() {
            return productModifiers;
        }

        public void setProductModifiers(List<ProductModifier> productModifiers) {
            this.productModifiers = productModifiers;
        }

        public List<MenuProductSize> getMenuProductSize() {
            return menuProductSize;
        }

        public void setMenuProductSize(List<MenuProductSize> menuProductSize) {
            this.menuProductSize = menuProductSize;
        }

        public ProductMainDetails getProductMainDetails() {
            return productMainDetails;
        }

        public void setProductMainDetails(ProductMainDetails productMainDetails) {
            this.productMainDetails = productMainDetails;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "productId='" + productId + '\'' +
                    ", productModifiers=" + productModifiers +
                    ", menuProductSize=" + menuProductSize +
                    ", productMainDetails=" + productMainDetails +
                    '}';
        }
    }

}
