
package com.lexxdigital.easyfooduserapps.login.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("email_verified")
    @Expose
    private Integer emailVerified;
    @SerializedName("phone_number_verified")
    @Expose
    private Integer phoneNumberVerified;
    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("dob")
    @Expose
    private String dob;

    @SerializedName("post_code")
    @Expose
    private String post_code;
    @SerializedName("previous_orders")
    @Expose
    private int previous_orders;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

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

    public Integer getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Integer emailVerified) {
        this.emailVerified = emailVerified;
    }

    public Integer getPhoneNumberVerified() {
        return phoneNumberVerified;
    }

    public void setPhoneNumberVerified(Integer phoneNumberVerified) {
        this.phoneNumberVerified = phoneNumberVerified;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public int getPrevious_orders() {
        return previous_orders;
    }

    public void setPrevious_orders(int previous_orders) {
        this.previous_orders = previous_orders;
    }

    @Override
    public String toString() {
        return "Data{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", emailVerified=" + emailVerified +
                ", phoneNumberVerified=" + phoneNumberVerified +
                ", token='" + token + '\'' +
                ", dob='" + dob + '\'' +
                ", post_code='" + post_code + '\'' +
                ", previous_orders='" + previous_orders + '\'' +
                '}';
    }
}
