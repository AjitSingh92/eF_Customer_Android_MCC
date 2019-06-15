
package com.lexxdigital.easyfooduserapps.cart_model.final_cart;

public class Datum {

    private MenuCategory menuCategory = null;
    private SpecialOffer specialOffer = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Datum() {
    }

    /**
     * 
     * @param menuCategory
     * @param specialOffer
     */
    public Datum(MenuCategory menuCategory, SpecialOffer specialOffer) {
        super();
        this.menuCategory = menuCategory;
        this.specialOffer = specialOffer;
    }

    public MenuCategory getMenuCategory() {
        return menuCategory;
    }

    public void setMenuCategory(MenuCategory menuCategory) {
        this.menuCategory = menuCategory;
    }

    public SpecialOffer getSpecialOffer() {
        return specialOffer;
    }

    public void setSpecialOffer(SpecialOffer specialOffer) {
        this.specialOffer = specialOffer;
    }

}
