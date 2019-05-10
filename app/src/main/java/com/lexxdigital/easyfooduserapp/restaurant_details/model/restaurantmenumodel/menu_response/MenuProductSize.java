package com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MenuProductSize {
    @SerializedName("productSizeId")
    @Expose
    private String productSizeId;
    @SerializedName("productSizeName")
    @Expose
    private String productSizeName;
    @SerializedName("productSizePrice")
    @Expose
    private String productSizePrice;
    @SerializedName("sizeModifiers")
    @Expose
    private List<SizeModifier> sizeModifiers = null;
    @Expose
    private String amount;
    @Expose
    private Integer quantity;
    @Expose
    public Boolean isSelected = false;

    public MenuProductSize() {
    }

    public MenuProductSize(String productSizeId, String productSizeName, String productSizePrice, String amount, Integer quantity, List<SizeModifier> sizeModifiers) {
        this.productSizeId = productSizeId;
        this.productSizeName = productSizeName;
        this.productSizePrice = productSizePrice;
        this.amount = amount;
        this.quantity = quantity;
        this.sizeModifiers = sizeModifiers;
    }

    public String getProductSizeId() {
        return productSizeId;
    }

    public void setProductSizeId(String productSizeId) {
        this.productSizeId = productSizeId;
    }

    public String getProductSizeName() {
        return productSizeName;
    }

    public void setProductSizeName(String productSizeName) {
        this.productSizeName = productSizeName;
    }

    public String getProductSizePrice() {
        return productSizePrice;
    }

    public void setProductSizePrice(String productSizePrice) {
        this.productSizePrice = productSizePrice;
    }

    public List<SizeModifier> getSizeModifiers() {
        return sizeModifiers;
    }

    public void setSizeModifiers(List<SizeModifier> sizeModifiers) {
        this.sizeModifiers = sizeModifiers;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "MenuProductSize{" +
                "productSizeId='" + productSizeId + '\'' +
                ", productSizeName='" + productSizeName + '\'' +
                ", productSizePrice='" + productSizePrice + '\'' +
                ", sizeModifiers=" + sizeModifiers +
                ", amount='" + amount + '\'' +
                ", quantity=" + quantity +
                ", isSelected=" + isSelected +
                '}';
    }
}