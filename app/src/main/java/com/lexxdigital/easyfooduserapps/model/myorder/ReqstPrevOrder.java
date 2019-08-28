package com.lexxdigital.easyfooduserapps.model.myorder;

import com.google.gson.annotations.SerializedName;

public class ReqstPrevOrder {
    @SerializedName("customer_id")
    String customer_id;
    @SerializedName("offset")
    int offset;
    @SerializedName("limit")
    int limit;

    public ReqstPrevOrder(String customer_id, int offset, int limit) {
        this.customer_id = customer_id;
        this.offset = offset;
        this.limit = limit;
    }
}
