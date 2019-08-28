package com.lexxdigital.easyfooduserapps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VoucherApplyResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("errors")
    @Expose
    private Errors errors;

    @SerializedName("data")
    @Expose
    private Data data;

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

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "VoucherApplyResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", errors='" + errors + '\'' +
                ", data=" + data +
                '}';
    }

    public class Data {
        @SerializedName("voucher_id")
        @Expose
        private String voucher_id;
        @SerializedName("restaurant_id")
        @Expose
        private String restaurant_id;
        @SerializedName("voucher_code")
        @Expose
        private String voucher_code;
        @SerializedName("terms_conditions")
        @Expose
        private String terms_conditions;
        @SerializedName("voucher_type")
        @Expose
        private String voucher_type;
        @SerializedName("voucher_value")
        @Expose
        private String voucher_value;
        @SerializedName("minimum_order_value")
        @Expose
        private String minimum_order_value;
        @SerializedName("voucher_applicable_on")
        @Expose
        private String voucher_applicable_on;
        @SerializedName("voucher_valid_on")
        @Expose
        private String voucher_valid_on;
        @SerializedName("days_available")
        @Expose
        private String days_available;

        @SerializedName("menu_items")
        @Expose
        private List<String> menu_items;

        public String getVoucher_id() {
            return voucher_id;
        }

        public void setVoucher_id(String voucher_id) {
            this.voucher_id = voucher_id;
        }

        public String getRestaurant_id() {
            return restaurant_id;
        }

        public void setRestaurant_id(String restaurant_id) {
            this.restaurant_id = restaurant_id;
        }

        public String getVoucher_code() {
            return voucher_code;
        }

        public void setVoucher_code(String voucher_code) {
            this.voucher_code = voucher_code;
        }

        public String getTerms_conditions() {
            return terms_conditions;
        }

        public void setTerms_conditions(String terms_conditions) {
            this.terms_conditions = terms_conditions;
        }

        public String getVoucher_type() {
            return voucher_type;
        }

        public void setVoucher_type(String voucher_type) {
            this.voucher_type = voucher_type;
        }

        public String getVoucher_value() {
            return voucher_value;
        }

        public void setVoucher_value(String voucher_value) {
            this.voucher_value = voucher_value;
        }

        public String getMinimum_order_value() {
            return minimum_order_value;
        }

        public void setMinimum_order_value(String minimum_order_value) {
            this.minimum_order_value = minimum_order_value;
        }

        public String getVoucher_applicable_on() {
            return voucher_applicable_on;
        }

        public void setVoucher_applicable_on(String voucher_applicable_on) {
            this.voucher_applicable_on = voucher_applicable_on;
        }

        public String getVoucher_valid_on() {
            return voucher_valid_on;
        }

        public void setVoucher_valid_on(String voucher_valid_on) {
            this.voucher_valid_on = voucher_valid_on;
        }

        public String getDays_available() {
            return days_available;
        }

        public void setDays_available(String days_available) {
            this.days_available = days_available;
        }

        public List<String> getMenu_items() {
            return menu_items;
        }

        public void setMenu_items(List<String> menu_items) {
            this.menu_items = menu_items;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "voucher_id='" + voucher_id + '\'' +
                    ", restaurant_id='" + restaurant_id + '\'' +
                    ", voucher_code='" + voucher_code + '\'' +
                    ", terms_conditions='" + terms_conditions + '\'' +
                    ", voucher_type='" + voucher_type + '\'' +
                    ", voucher_value='" + voucher_value + '\'' +
                    ", minimum_order_value='" + minimum_order_value + '\'' +
                    ", voucher_applicable_on='" + voucher_applicable_on + '\'' +
                    ", voucher_valid_on='" + voucher_valid_on + '\'' +
                    ", days_available='" + days_available + '\'' +
                    ", menu_items=" + menu_items +
                    '}';
        }
    }

    public class Errors {
        @SerializedName("voucher_code")
        @Expose
        List<String> voucher_code;

        public List<String> getVoucher_code() {
            return voucher_code;
        }

        public void setVoucher_code(List<String> voucher_code) {
            this.voucher_code = voucher_code;
        }

        @Override
        public String toString() {
            return "Errors{" +
                    "voucher_code=" + voucher_code +
                    '}';
        }
    }

}
