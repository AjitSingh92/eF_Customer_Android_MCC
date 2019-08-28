
package com.lexxdigital.easyfooduserapps.signup.model.final_response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Errors {

    @SerializedName("first_name")
    @Expose
    private List<Object> firstName = null;
    @SerializedName("last_name")
    @Expose
    private List<Object> lastName = null;
    @SerializedName("name")
    @Expose
    private List<Object> name = null;
    @SerializedName("email")
    @Expose
    private List<Object> email = null;
    @SerializedName("phone_number")
    @Expose
    private List<Object> phoneNumber = null;
    @SerializedName("password")
    @Expose
    private List<Object> password = null;
    @SerializedName("password_confirmation")
    @Expose
    private List<Object> passwordConfirmation = null;

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

    public List<Object> getName() {
        return name;
    }

    public void setName(List<Object> name) {
        this.name = name;
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

    public List<Object> getPassword() {
        return password;
    }

    public void setPassword(List<Object> password) {
        this.password = password;
    }

    public List<Object> getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(List<Object> passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

}
