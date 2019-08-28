
package com.lexxdigital.easyfooduserapps.restaurant_details.model.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("menuCategory")
    @Expose
    private List<MenuCategory_> menuCategory = null;

    public List<MenuCategory_> getMenuCategory() {
        return menuCategory;
    }

    public void setMenuCategory(List<MenuCategory_> menuCategory) {
        this.menuCategory = menuCategory;
    }

}
