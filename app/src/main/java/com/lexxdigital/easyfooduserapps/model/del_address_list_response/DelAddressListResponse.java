
package com.lexxdigital.easyfooduserapps.model.del_address_list_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DelAddressListResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
