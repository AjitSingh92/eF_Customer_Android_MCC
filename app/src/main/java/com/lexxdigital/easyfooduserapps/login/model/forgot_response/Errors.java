
package com.lexxdigital.easyfooduserapps.login.model.forgot_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Errors {

    @SerializedName("email")
    @Expose
    private List<Object> email = null;

    public List<Object> getEmail() {
        return email;
    }

    public void setEmail(List<Object> email) {
        this.email = email;
    }

}
