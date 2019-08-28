
package com.lexxdigital.easyfooduserapps.model.edit_account_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditAccountRequest {

    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;

    @SerializedName("post_code")
    @Expose
    private String post_code;

    @SerializedName("dob")
    @Expose
    private String dob;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "EditAccountRequest{" +
                "customerId='" + customerId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", post_code='" + post_code + '\'' +
                ", dob='" + dob + '\'' +
                '}';
    }
}
