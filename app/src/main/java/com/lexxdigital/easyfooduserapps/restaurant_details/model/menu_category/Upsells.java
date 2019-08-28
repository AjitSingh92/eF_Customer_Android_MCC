
package com.lexxdigital.easyfooduserapps.restaurant_details.model.menu_category;

import java.util.List;

public class Upsells {

    private String upsellGroupName;
    private String upsellGroupId;
    private List<UpsellProduct> upsellProducts = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Upsells() {
    }

    /**
     * 
     * @param upsellGroupName
     * @param upsellProducts
     * @param upsellGroupId
     */
    public Upsells(String upsellGroupName, String upsellGroupId, List<UpsellProduct> upsellProducts) {
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

    public List<UpsellProduct> getUpsellProducts() {
        return upsellProducts;
    }

    public void setUpsellProducts(List<UpsellProduct> upsellProducts) {
        this.upsellProducts = upsellProducts;
    }

}
