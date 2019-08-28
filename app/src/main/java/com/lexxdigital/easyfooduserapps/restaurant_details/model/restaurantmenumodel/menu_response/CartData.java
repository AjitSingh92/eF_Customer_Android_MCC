package com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response;

import com.google.gson.annotations.Expose;

import java.util.List;

public class CartData {
    @Expose
    List<SpecialOffer> specialOffers;
    @Expose
    List<MenuCategoryCart> menuCategory;
    @Expose
    List<UpsellProduct> upsellProducts;

    public CartData() {
    }

    public CartData(List<SpecialOffer> specialOffers, List<MenuCategoryCart> menuCategoryCarts, List<UpsellProduct> upsellProducts) {
        this.specialOffers = specialOffers;
        this.menuCategory = menuCategoryCarts;
        this.upsellProducts = upsellProducts;
    }

    public List<SpecialOffer> getSpecialOffers() {
        return specialOffers;
    }

    public void setSpecialOffers(List<SpecialOffer> specialOffers) {
        this.specialOffers = specialOffers;
    }

    public List<MenuCategoryCart> getMenuCategoryCarts() {
        return menuCategory;
    }

    public void setMenuCategoryCarts(List<MenuCategoryCart> menuCategoryCarts) {
        this.menuCategory = menuCategoryCarts;
    }

    public List<UpsellProduct> getUpsellProducts() {
        return upsellProducts;
    }

    public void setUpsellProducts(List<UpsellProduct> upsellProducts) {
        this.upsellProducts = upsellProducts;
    }

    @Override
    public String toString() {
        return "CartData{" +
                "specialOffers=" + specialOffers +
                ", menuCategoryCarts=" + menuCategory +
                ", upsellProducts=" + upsellProducts +
                '}';
    }
}