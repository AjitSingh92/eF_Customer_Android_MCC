
package com.lexxdigital.easyfooduserapps.cart_model.menu_shows;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuItemsShows {

    @SerializedName("special_offers")
    @Expose
    private List<SpecialOffer> specialOffers = null;
    @SerializedName("menu_category")
    @Expose
    private List<MenuCategory> menuCategory = null;

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
