
package com.lexxdigital.easyfooduserapps.model.edit_account_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EditAccountResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("errors")
    @Expose
    private Errors errors;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public static class Errors {
        @SerializedName("phone_number")
        @Expose
        private List<String> phoneNumber = null;

        public List<String> getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(List<String> phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    }

}
