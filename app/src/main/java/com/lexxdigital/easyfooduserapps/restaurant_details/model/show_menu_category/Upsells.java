
package com.lexxdigital.easyfooduserapps.restaurant_details.model.show_menu_category;

import java.util.List;

public class Upsells {

    private String upsellGroupName;
    private String upsellGroupId;
    private List<Object> upsellProducts = null;


    public Upsells() {
    }


    public Upsells(String upsellGroupName, String upsellGroupId, List<Object> upsellProducts) {
        super();
        this.upsellGroupName = upsellGroupName;
        this.upsellGroupId = upsellGroupId;
        this.upsellProducts = upsellProducts;
    }

    public String getUpsellGroupName() {
        return upsellGroupName;
    }

    public void setUpsellGroupName(String upsellGroupName) {
        this.upsellGroupName = upsellGroupName;
    }

    public String getUpsellGroupId() {
        return upsellGroupId;
    }

    public void setUpsellGroupId(String upsellGroupId) {
        this.upsellGroupId = upsellGroupId;
    }

    public List<Object> getUpsellProducts() {
        return upsellProducts;
    }

    public void setUpsellProducts(List<Object> upsellProducts) {
        this.upsellProducts = upsellProducts;
    }

}
