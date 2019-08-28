package com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryProductsRequest {
    @SerializedName("restaurant_id")
    @Expose
    String restaurant_id;
    @SerializedName("category_id")
    @Expose
    String category_id;
    @SerializedName("offset")
    @Expose
    int offset;

    public CategoryProductsRequest(String restaurant_id, String category_id, int offset) {
        this.restaurant_id = restaurant_id;
        this.category_id = category_id;
        this.offset = offset;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "CategoryProductsRequest{" +
                "restaurant_id='" + restaurant_id + '\'' +
                ", category_id='" + category_id + '\'' +
                ", offset='" + offset + '\'' +
                '}';
    }
}
