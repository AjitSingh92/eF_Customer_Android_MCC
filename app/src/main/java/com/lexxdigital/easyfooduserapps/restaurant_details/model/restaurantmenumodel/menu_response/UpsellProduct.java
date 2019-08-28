package com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpsellProduct {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("groupId")
    @Expose
    private String groupId;
    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("originalAmount")
    @Expose
    private String originalAmount;
    @SerializedName("originalAmount1")
    @Expose
    private String originalAmount1;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("productPrice")
    @Expose
    private Double productPrice;

    public UpsellProduct() {
    }

    public UpsellProduct(String id, String groupId, String productId, String categoryId, String productName, String description, String originalAmount, String originalAmount1, String quantity, String unit, Double productPrice) {
        this.id = id;
        this.groupId = groupId;
        this.productId = productId;
        this.categoryId = categoryId;
        this.productName = productName;
        this.description = description;
        this.originalAmount = originalAmount;
        this.originalAmount1 = originalAmount1;
        this.quantity = quantity;
        this.unit = unit;
        this.productPrice = productPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(String originalAmount) {
        this.originalAmount = originalAmount;
    }

    public String getOriginalAmount1() {
        return originalAmount1;
    }

    public void setOriginalAmount1(String originalAmount1) {
        this.originalAmount1 = originalAmount1;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public String toString() {
        return "UpsellProduct{" +
                "id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", productId='" + productId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", originalAmount='" + originalAmount + '\'' +
                ", originalAmount1='" + originalAmount1 + '\'' +
                ", quantity='" + quantity + '\'' +
                ", unit='" + unit + '\'' +
                ", productPrice=" + productPrice +
                '}';
    }
}