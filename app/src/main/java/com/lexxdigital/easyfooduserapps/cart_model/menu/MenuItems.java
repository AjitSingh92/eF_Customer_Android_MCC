
package com.lexxdigital.easyfooduserapps.cart_model.menu;

import java.util.List;

public class MenuItems {

    private List<SpecialOffer> specialOffers = null;
    private List<MenuCategory> menuCategory = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MenuItems() {
    }

    /**
     * 
     * @param specialOffers
     * @param menuCategory
     */
    public MenuItems(List<SpecialOffer> specialOffers, List<MenuCategory> menuCategory) {
        super();
        this.specialOffers = specialOffers;
        this.menuCategory = menuCategory;
    }

    public List<SpecialOffer> getSpecialOffers() {
        return specialOffers;
    }

    public void setSpecialOffers(List<SpecialOffer> specialOffers) {
        this.specialOffers = specialOffers;
    }

    public List<MenuCategory> getMenuCategory() {
        return menuCategory;
    }

    public void setMenuCategory(List<MenuCategory> menuCategory) {
        this.menuCategory = menuCategory;
    }

}
