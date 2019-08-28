
package com.lexxdigital.easyfooduserapps.select_payment_method.model.checkout_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum {

    @SerializedName("menuCategory")
    @Expose
    private List<MenuCategory> menuCategory = null;
    @SerializedName("specialOffers")
    @Expose
    private List<SpecialOffer> specialOffers = null;

    public List<MenuCategory> getMenuCategory() {
        return menuCategory;
    }

    public void setMenuCategory(List<MenuCategory> menuCategory) {
        this.menuCategory = menuCategory;
    }

    public List<SpecialOffer> getSpecialOffers() {
        return specialOffers;
    }

    public void setSpecialOffers(List<SpecialOffer> specialOffers) {
        this.specialOffers = specialOffers;
    }

}
