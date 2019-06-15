package com.lexxdigital.easyfooduserapps.model.makeCardDefault;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MakeCardDefReq {
    @SerializedName("card_id")
    @Expose
    private String cardId;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

}
