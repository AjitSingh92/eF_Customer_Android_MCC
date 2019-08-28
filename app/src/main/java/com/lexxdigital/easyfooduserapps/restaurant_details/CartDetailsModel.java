package com.lexxdigital.easyfooduserapps.restaurant_details;

public class CartDetailsModel {

    protected String ProductId;
    protected String ProductName;
    protected String ProductQty;
    protected String ProductPrice;
    protected String TotalAmmount;
    protected String ProductType;

    public CartDetailsModel(String productId, String productName, String productQty, String productPrice, String totalAmmount, String productType) {
        ProductId = productId;
        ProductName = productName;
        ProductQty = productQty;
        ProductPrice = productPrice;
        TotalAmmount = totalAmmount;
        ProductType = productType;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductQty() {
        return ProductQty;
    }

    public void setProductQty(String productQty) {
        ProductQty = productQty;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getTotalAmmount() {
        return TotalAmmount;
    }

    public void setTotalAmmount(String totalAmmount) {
        TotalAmmount = totalAmmount;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String productType) {
        ProductType = productType;
    }
}
