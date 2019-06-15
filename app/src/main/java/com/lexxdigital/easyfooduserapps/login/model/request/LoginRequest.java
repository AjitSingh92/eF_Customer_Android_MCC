
package com.lexxdigital.easyfooduserapps.login.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("login_via")
    @Expose
    private String loginVia;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("fb_id")
    @Expose
    private String fbId;
    @SerializedName("google_id")
    @Expose
    private String googleId;
    @SerializedName("device_type")
    @Expose
    private String deviceType;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("fb_device_id")
    @Expose
    private String fb_device_id;


    public String getLoginVia() {
        return loginVia;
    }

    public void setLoginVia(String loginVia) {
        this.loginVia = loginVia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFb_device_id() {
        return fb_device_id;
    }

    public void setFb_device_id(String fb_device_id) {
        this.fb_device_id = fb_device_id;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "loginVia='" + loginVia + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fbId='" + fbId + '\'' +
                ", googleId='" + googleId + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", name='" + name + '\'' +
                ", fb_device_id='" + fb_device_id + '\'' +
                '}';
    }
}
