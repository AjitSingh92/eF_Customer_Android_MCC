
package com.lexxdigital.easyfooduserapps.signup.model.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Errors {
    @SerializedName("first_name")
    @Expose
    private List<Object> firstName = null;
    @SerializedName("last_name")
    @Expose
    private List<Object> lastName = null;
    @SerializedName("email")
    @Expose
    private List<Object> email = null;
    @SerializedName("phone_number")
    @Expose
    private List<Object> phoneNumber = null;

    public List<Object> getFirstName() {
        return firstName;
    }

    public void setFirstName(List<Object> firstName) {
        this.firstName = firstName;
    }

    public List<Object> getLastName() {
        return lastName;
    }

    public void setLastName(List<Object> lastName) {
        this.lastName = lastName;
    }

    public List<Object> getEmail() {
        return email;
    }

    public void setEmail(List<Object> email) {
        this.email = email;
    }

    public List<Object> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(List<Object> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
