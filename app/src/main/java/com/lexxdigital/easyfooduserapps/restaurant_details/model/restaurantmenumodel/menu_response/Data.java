package com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("menu")
    @Expose
    private Menu menu;

    public Data() {
    }

    public Data(Menu menu) {
        this.menu = menu;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Data{" +
                "menu=" + menu +
                '}';
    }
}