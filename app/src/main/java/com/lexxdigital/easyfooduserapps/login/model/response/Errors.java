
package com.lexxdigital.easyfooduserapps.login.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Errors {

    @SerializedName("login_via")
    @Expose
    private List<Object> loginVia = null;
    @SerializedName("email")
    @Expose
    private List<Object> email = null;
    @SerializedName("password")
    @Expose
    private List<Object> password = null;
    @SerializedName("fb_id")
    @Expose
    private List<Object> fbId = null;
    @SerializedName("google_id")
    @Expose
    private List<Object> googleId = null;
    @SerializedName("device_type")
    @Expose
    private List<Object> deviceType = null;
    @SerializedName("device_id")
    @Expose
    private List<Object> deviceId = null;

    public List<Object> getLoginVia() {
        return loginVia;
    }

    public void setLoginVia(List<Object> loginVia) {
        this.loginVia = loginVia;
    }

    public List<Object> getEmail() {
        return email;
    }

    public void setEmail(List<Object> email) {
        this.email = email;
    }

    public List<Object> getPassword() {
        return password;
    }

    public void setPassword(List<Object> password) {
        this.password = password;
    }

    public List<Object> getFbId() {
        return fbId;
    }

    public void setFbId(List<Object> fbId) {
        this.fbId = fbId;
    }

    public List<Object> getGoogleId() {
        return googleId;
    }

    public void setGoogleId(List<Object> googleId) {
        this.googleId = googleId;
    }

    public List<Object> getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(List<Object> deviceType) {
        this.deviceType = deviceType;
    }

    public List<Object> getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(List<Object> deviceId) {
        this.deviceId = deviceId;
    }

}
