
package com.lexxdigital.easyfooduserapps.model.order_details;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
