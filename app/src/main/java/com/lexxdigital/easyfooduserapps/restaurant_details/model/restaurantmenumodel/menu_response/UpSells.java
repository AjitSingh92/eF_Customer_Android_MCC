package com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpSells {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private Data data = null;

    public class Data {
        @SerializedName("upsells")
        @Expose
        private List<UpsellProduct> upsellsProducts = null;

        public Data(List<UpsellProduct> upsellsProducts) {
            this.upsellsProducts = upsellsProducts;
        }

        public List<UpsellProduct> getUpsellsProducts() {
            return upsellsProducts;
        }

        public void setUpsellsProducts(List<UpsellProduct> upsellsProducts) {
            this.upsellsProducts = upsellsProducts;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "upsellsProducts=" + upsellsProducts +
                    '}';
        }
    }

    public UpSells() {
    }

    public UpSells(String success, String message, Data data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
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
        return "UpSells{" +
                "success='" + success + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}