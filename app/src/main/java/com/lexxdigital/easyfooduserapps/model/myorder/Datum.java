
package com.lexxdigital.easyfooduserapps.model.myorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum {

    @SerializedName("menuCategory")
    @Expose
    private List<MenuCategory> menuCategory = null;

    public List<MenuCategory> getMenuCategory() {
        return menuCategory;
    }

    public void setMenuCategory(List<MenuCategory> menuCategory) {
        this.menuCategory = menuCategory;
    }


}
