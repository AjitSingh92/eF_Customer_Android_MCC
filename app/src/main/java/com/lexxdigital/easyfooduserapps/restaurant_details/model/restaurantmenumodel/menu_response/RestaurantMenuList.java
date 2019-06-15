package com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response;


import com.google.gson.annotations.Expose;

import java.util.List;

public class RestaurantMenuList {
    @Expose
    List<SpecialOffer> specialOffers;
    @Expose
    MenuCategory menuCategories;

    public RestaurantMenuList() {
    }

    public RestaurantMenuList(List<SpecialOffer> specialOffers, MenuCategory menuCategories) {
        this.specialOffers = specialOffers;
        this.menuCategories = menuCategories;
    }

    public List<SpecialOffer> getSpecialOffers() {
        return specialOffers;
    }

    public void setSpecialOffers(List<SpecialOffer> specialOffers) {
        this.specialOffers = specialOffers;
    }

    public MenuCategory getMenuCategories() {
        return menuCategories;
    }

    public void setMenuCategories(MenuCategory menuCategories) {
        this.menuCategories = menuCategories;
    }
}