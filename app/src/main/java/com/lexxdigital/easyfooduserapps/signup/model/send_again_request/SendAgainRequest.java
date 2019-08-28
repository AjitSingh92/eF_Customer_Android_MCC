
package com.lexxdigital.easyfooduserapps.signup.model.send_again_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendAgainRequest {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
