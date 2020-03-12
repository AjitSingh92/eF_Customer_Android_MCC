package com.lexxdigital.easyfooduserapps.model.previous_order.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetails {
    @SerializedName("menu")
    @Expose
    private List<Menu> menu = null;

    public List<Menu> getMenu() {
        return menu;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }
}
