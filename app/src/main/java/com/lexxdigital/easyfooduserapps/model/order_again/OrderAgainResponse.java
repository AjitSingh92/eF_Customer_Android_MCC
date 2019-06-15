package com.lexxdigital.easyfooduserapps.model.order_again;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.lexxdigital.easyfooduserapps.restaurant_details.model.new_restaurant_response.Errors;

public class OrderAgainResponse {

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

    public static class Data {
        @SerializedName("restaurant_status")
        @Expose
        private String restaurantStatus;


        public Data() {
        }

        public Data(String restaurantStatus) {
            this.restaurantStatus = restaurantStatus;
        }

        public String getRestaurantStatus() {
            return restaurantStatus;
        }

        public void setRestaurantStatus(String restaurantStatus) {
            this.restaurantStatus = restaurantStatus;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "restaurantStatus='" + restaurantStatus + '\'' +
                    '}';
        }
    }

    public OrderAgainResponse() {
    }

    public OrderAgainResponse(Boolean success, String message, Data data, Errors errors) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.errors = errors;
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
        return "OrderAgainResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", errors=" + errors +
                '}';
    }
}
