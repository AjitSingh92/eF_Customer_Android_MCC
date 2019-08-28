
package com.lexxdigital.easyfooduserapps.cart_model.final_cart;


import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.UpSells;

public class MenuCategory {

    private String menuCategoryId;
    private String menuCategoryName;
    private Integer itemId;
    private Integer quantity;
    private String amount;
    private MenuSubCategory menuSubCategory;
    private MenuProduct menuProducts = null;
    private UpSells upsells;

    /**
     * No args constructor for use in serialization
     */
    public MenuCategory() {
    }

    /**
     * @param amount
     * @param menuCategoryId
     * @param menuCategoryName
     * @param upsells
     * @param quantity
     * @param menuSubCategory
     * @param itemId
     * @param menuProducts
     */
    public MenuCategory(String menuCategoryId, String menuCategoryName, Integer itemId, Integer quantity, String amount, MenuSubCategory menuSubCategory, MenuProduct menuProducts, UpSells upsells) {
        super();
        this.menuCategoryId = menuCategoryId;
        this.menuCategoryName = menuCategoryName;
        this.itemId = itemId;
        this.quantity = quantity;
        this.amount = amount;
        this.menuSubCategory = menuSubCategory;
        this.menuProducts = menuProducts;
        this.upsells = upsells;
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

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public MenuSubCategory getMenuSubCategory() {
        return menuSubCategory;
    }

    public void setMenuSubCategory(MenuSubCategory menuSubCategory) {
        this.menuSubCategory = menuSubCategory;
    }

    public MenuProduct getMenuProducts() {
        return menuProducts;
    }

    public void setMenuProducts(MenuProduct menuProducts) {
        this.menuProducts = menuProducts;
    }

    public UpSells getUpsells() {
        return upsells;
    }

    public void setUpsells(UpSells upsells) {
        this.upsells = upsells;
    }

    @Override
    public String toString() {
        return "MenuCategory{" +
                "menuCategoryId='" + menuCategoryId + '\'' +
                ", menuCategoryName='" + menuCategoryName + '\'' +
                ", itemId=" + itemId +
                ", quantity=" + quantity +
                ", amount='" + amount + '\'' +
                ", menuSubCategory=" + menuSubCategory +
                ", menuProducts=" + menuProducts +
                ", upsells=" + upsells +
                '}';
    }
}
