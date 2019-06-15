
package com.lexxdigital.easyfooduserapps.cart_model.menu_shows;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Upsells_ {

    @SerializedName("upsell_group_name")
    @Expose
    private String upsellGroupName;
    @SerializedName("upsell_group_id")
    @Expose
    private String upsellGroupId;
    @SerializedName("upsell_products")
    @Expose
    private List<Object> upsellProducts = null;

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
