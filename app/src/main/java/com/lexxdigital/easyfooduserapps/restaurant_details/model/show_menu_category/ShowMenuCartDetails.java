
package com.lexxdigital.easyfooduserapps.restaurant_details.model.show_menu_category;

import java.util.List;

public class ShowMenuCartDetails {

    private List<SpecialOffer> specialOffers = null;
    private List<MenuCategory> menuCategory = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ShowMenuCartDetails() {
    }

    /**
     * 
     * @param specialOffers
     * @param menuCategory
     */
    public ShowMenuCartDetails(List<SpecialOffer> specialOffers, List<MenuCategory> menuCategory) {
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
