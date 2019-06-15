
package com.lexxdigital.easyfooduserapps.cart_model.menu;

import java.util.List;

public class Upsells_ {

    private String upsellGroupName;
    private String upsellGroupId;
    private List<UpsellProduct_> upsellProducts = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Upsells_() {
    }

    /**
     * 
     * @param upsellGroupName
     * @param upsellProducts
     * @param upsellGroupId
     */
    public Upsells_(String upsellGroupName, String upsellGroupId, List<UpsellProduct_> upsellProducts) {
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

    public List<UpsellProduct_> getUpsellProducts() {
        return upsellProducts;
    }

    public void setUpsellProducts(List<UpsellProduct_> upsellProducts) {
        this.upsellProducts = upsellProducts;
    }

}
