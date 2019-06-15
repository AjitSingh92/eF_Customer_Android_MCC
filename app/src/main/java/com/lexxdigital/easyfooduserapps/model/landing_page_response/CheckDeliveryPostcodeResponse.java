package com.lexxdigital.easyfooduserapps.model.landing_page_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckDeliveryPostcodeResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    public Data data;
    @SerializedName("errors")
    @Expose
    private Errors errors;


    public class Data {
        @SerializedName("is_delivering")
        @Expose
        private int isDelivering;

        public int getIsDelivering() {
            return isDelivering;
        }

        public void setIsDelivering(int isDelivering) {
            this.isDelivering = isDelivering;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "isDelivering='" + isDelivering + '\'' +
                    '}';
        }
    }

    public class Errors {
    }

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

    @Override
    public String toString() {
        return "CheckDeliveryPostcodeResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", errors=" + errors +
                '}';
    }
}
