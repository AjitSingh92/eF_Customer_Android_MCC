
package com.lexxdigital.easyfooduserapps.model.card_list_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeleteCardResponse {

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


    public class Data {


    }
    public class Errors {

        @SerializedName("user_id")
        @Expose
        private List<String> userId = null;
        @SerializedName("card_no")
        @Expose
        private List<String> cardNo = null;

        public List<String> getUserId() {
            return userId;
        }

        public void setUserId(List<String> userId) {
            this.userId = userId;
        }

        public List<String> getCardNo() {
            return cardNo;
        }

        public void setCardNo(List<String> cardNo) {
            this.cardNo = cardNo;
        }

}}
