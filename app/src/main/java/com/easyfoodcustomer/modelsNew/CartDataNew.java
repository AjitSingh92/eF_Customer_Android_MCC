package com.easyfoodcustomer.modelsNew;

import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.SpecialOffer;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.UpsellProduct;
import com.google.gson.annotations.Expose;

import java.util.List;

public class CartDataNew {
    @Expose
    List<SpecialOffer> specialOffers;
    @Expose
    List<CheckoutModel> menuCategory;
    @Expose
    List<UpsellProduct> upsellProducts;

    public CartDataNew() {
    }

    public CartDataNew(List<SpecialOffer> specialOffers, List<CheckoutModel> menuCategoryCarts, List<UpsellProduct> upsellProducts) {
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

    public List<CheckoutModel> getMenuCategoryCarts() {
        return menuCategory;
    }

    public void setMenuCategoryCarts(List<CheckoutModel> menuCategoryCarts) {
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