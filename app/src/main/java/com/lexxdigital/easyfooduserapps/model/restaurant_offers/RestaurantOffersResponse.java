package com.lexxdigital.easyfooduserapps.model.restaurant_offers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantOffersResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;


    public static class Data {
        @SerializedName("specialOffers")
        @Expose
        private List<RestaurantSpecialOffers> restaurantSpecialOffers = null;

        public Data(List<RestaurantSpecialOffers> restaurantSpecialOffers) {
            this.restaurantSpecialOffers = restaurantSpecialOffers;
        }

        public List<RestaurantSpecialOffers> getRestaurantSpecialOffers() {
            return restaurantSpecialOffers;
        }

        public void setRestaurantSpecialOffers(List<RestaurantSpecialOffers> restaurantSpecialOffers) {
            this.restaurantSpecialOffers = restaurantSpecialOffers;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "restaurantSpecialOffers=" + restaurantSpecialOffers +
                    '}';
        }
    }

    public RestaurantOffersResponse(Boolean success, String message, Data data) {
        this.success = success;
        this.message = message;
        this.data = data;
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

    @Override
    public String toString() {
        return "RestaurantOffersResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
