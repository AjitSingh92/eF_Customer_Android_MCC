
package com.lexxdigital.easyfooduserapps.model.edit_account_response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Errors {

    @SerializedName("customer_id")
    @Expose
    private List<String> customerId = null;
    @SerializedName("first_name")
    @Expose
    private List<String> firstName = null;
    @SerializedName("last_name")
    @Expose
    private List<String> lastName = null;
    @SerializedName("phone_number")
    @Expose
    private List<String> phoneNumber = null;

    public List<String> getCustomerId() {
        return customerId;
    }

    public void setCustomerId(List<String> customerId) {
        this.customerId = customerId;
    }

    public List<String> getFirstName() {
        return firstName;
    }

    public void setFirstName(List<String> firstName) {
        this.firstName = firstName;
    }

    public List<String> getLastName() {
        return lastName;
    }

    public void setLastName(List<String> lastName) {
        this.lastName = lastName;
    }

    public List<String> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(List<String> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
