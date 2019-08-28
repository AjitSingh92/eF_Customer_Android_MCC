package com.lexxdigital.easyfooduserapps.model.card_list_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardDeleteReq {
    @SerializedName("card_id")
    @Expose
    private String cartId;


    public String getCardId() {
        return cartId;
    }

    public void setCardId(String id) {
        this.cartId = id;
    }
}
