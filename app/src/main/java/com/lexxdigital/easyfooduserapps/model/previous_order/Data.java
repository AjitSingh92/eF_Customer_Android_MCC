
package com.lexxdigital.easyfooduserapps.model.previous_order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("previous_order_details")
    @Expose
    private List<PreviousOrderDetail> previousOrderDetails = null;
    @SerializedName("total_records")
    @Expose
    private Integer totalRecords;

    public List<PreviousOrderDetail> getPreviousOrderDetails() {
        return previousOrderDetails;
    }

    public void setPreviousOrderDetails(List<PreviousOrderDetail> previousOrderDetails) {
        this.previousOrderDetails = previousOrderDetails;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

}
