package com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lexxdigital.easyfooduserapps.cart_db.converters.MenuCategoryConverter;
import com.lexxdigital.easyfooduserapps.cart_db.converters.SpecialOfferConverter;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Menu {

    @PrimaryKey
    @NonNull
    String restaurantId;
    @TypeConverters({SpecialOfferConverter.class})
    @SerializedName("specialOffers")
    @Expose
    private List<SpecialOffer> specialOffers = new ArrayList<>();
    @TypeConverters({MenuCategoryConverter.class})
    @SerializedName("menuCategory")
    @Expose
    private List<MenuCategory> menuCategory = new ArrayList<>();

    @Ignore
    public Menu() {
    }

    public Menu(@NonNull String restaurantId, List<SpecialOffer> specialOffers, List<MenuCategory> menuCategory) {
        this.restaurantId = restaurantId;
        this.specialOffers = specialOffers;
        this.menuCategory = menuCategory;
    }

    @NonNull
    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(@NonNull String restaurantId) {
        this.restaurantId = restaurantId;
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

    @Override
    public String toString() {
        return "Menu{" +
                "restaurantId='" + restaurantId + '\'' +
                ", specialOffers=" + specialOffers +
                ", menuCategory=" + menuCategory +
                '}';
    }
}