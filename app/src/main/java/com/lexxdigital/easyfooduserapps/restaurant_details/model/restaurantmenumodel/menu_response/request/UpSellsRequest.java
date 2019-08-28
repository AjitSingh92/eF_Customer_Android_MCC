package com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpSellsRequest {
    @SerializedName("product_id")
    @Expose
    List<String> product_id;


    public List<String> getProduct_id() {
        return product_id;
    }

    public void setProduct_id(List<String> product_id) {
        this.product_id = product_id;
    }

    @Override
    public String toString() {
        return "UpSellsRequest{" +
                "product_id=" + product_id +
                '}';
    }
}
