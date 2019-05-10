package com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response;

import com.google.gson.annotations.Expose;

import java.util.List;

public class CartData {
    @Expose
    List<SpecialOffer> specialOffers;
    @Expose
    List<MenuCategoryCart> menuCategoryCarts;
    @Expose
    List<UpsellProduct> upsellProducts;

    public CartData() {
    }

    public CartData(List<SpecialOffer> specialOffers, List<MenuCategoryCart> menuCategoryCarts, List<UpsellProduct> upsellProducts) {
        this.specialOffers = specialOffers;
        this.menuCategoryCarts = menuCategoryCarts;
        this.upsellProducts = upsellProducts;
    }

    public List<SpecialOffer> getSpecialOffers() {
        return specialOffers;
    }

    public void setSpecialOffers(List<SpecialOffer> specialOffers) {
        this.specialOffers = specialOffers;
    }

    public List<MenuCategoryCart> getMenuCategoryCarts() {
        return menuCategoryCarts;
    }

    public void setMenuCategoryCarts(List<MenuCategoryCart> menuCategoryCarts) {
        this.menuCategoryCarts = menuCategoryCarts;
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
                ", menuCategoryCarts=" + menuCategoryCarts +
                ", upsellProducts=" + upsellProducts +
                '}';
    }
}