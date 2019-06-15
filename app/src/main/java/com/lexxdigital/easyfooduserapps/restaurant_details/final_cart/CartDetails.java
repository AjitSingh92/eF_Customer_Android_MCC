
package com.lexxdigital.easyfooduserapps.restaurant_details.final_cart;

import com.lexxdigital.easyfooduserapps.restaurant_details.CartDetailsModel;

import java.util.List;

public class CartDetails {

    private List<CartDetailsModel> data = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CartDetails() {
    }

    /**
     * 
     * @param data
     */
    public CartDetails(List<CartDetailsModel> data) {
        super();
        this.data = data;
    }

    public List<CartDetailsModel> getData() {
        return data;
    }

    public void setData(List<CartDetailsModel> data) {
        this.data = data;
    }

}
