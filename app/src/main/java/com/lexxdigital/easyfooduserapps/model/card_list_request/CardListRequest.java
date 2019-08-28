
package com.lexxdigital.easyfooduserapps.model.card_list_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardListRequest {

    @SerializedName("user_id")
    @Expose
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
