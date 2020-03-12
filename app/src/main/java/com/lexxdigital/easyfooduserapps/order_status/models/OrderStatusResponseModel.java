package com.lexxdigital.easyfooduserapps.order_status.models;

import java.io.Serializable;

public class OrderStatusResponseModel implements Serializable {
    boolean success;
    String message;
    Data data;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
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
        return "OrderStatusResponseModel{" +
                "success='" + success + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public class Data implements Serializable {
        String order_number;
        String order_num;
        String message;
        String order_status;
        String delivery_date_time;
        String order_date_time;
        String order_total;
        String restaurant_name;
        String prepare_time;
        String average_delivery_time;
        String phone_number;
        String payment_mode;
        String order_type;
        String order_delivery_collection_time;


        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }
        public String getOrder_num() {
            return order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getDelivery_date_time() {
            return delivery_date_time;
        }

        public void setDelivery_date_time(String delivery_date_time) {
            this.delivery_date_time = delivery_date_time;
        }

        public String getOrder_date_time() {
            return order_date_time;
        }

        public void setOrder_date_time(String order_date_time) {
            this.order_date_time = order_date_time;
        }

        public String getOrder_total() {
            return order_total;
        }

        public void setOrder_total(String order_total) {
            this.order_total = order_total;
        }

        public String getRestaurant_name() {
            return restaurant_name;
        }

        public void setRestaurant_name(String restaurant_name) {
            this.restaurant_name = restaurant_name;
        }

        public String getPrepare_time() {
            return prepare_time;
        }

        public void setPrepare_time(String prepare_time) {
            this.prepare_time = prepare_time;
        }

        public String getAverage_delivery_time() {
            return average_delivery_time;
        }

        public void setAverage_delivery_time(String average_delivery_time) {
            this.average_delivery_time = average_delivery_time;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getPayment_mode() {
            return payment_mode;
        }

        public void setPayment_mode(String payment_mode) {
            this.payment_mode = payment_mode;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }

        public String getOrder_delivery_collection_time() {
            return order_delivery_collection_time;
        }

        public void setOrder_delivery_collection_time(String order_delivery_collection_time) {
            this.order_delivery_collection_time = order_delivery_collection_time;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "order_number='" + order_number + '\'' +
                    ", order_status=" + order_status +
                    ", delivery_date_time='" + delivery_date_time + '\'' +
                    ", order_date_time='" + order_date_time + '\'' +
                    ", order_total='" + order_total + '\'' +
                    ", restaurant_name='" + restaurant_name + '\'' +
                    ", prepare_time='" + prepare_time + '\'' +
                    ", average_delivery_time='" + average_delivery_time + '\'' +
                    ", phone_number='" + phone_number + '\'' +
                    ", payment_mode='" + payment_mode + '\'' +
                    ", order_type='" + order_type + '\'' +
                    ", order_delivery_collection_time='" + order_delivery_collection_time + '\'' +
                    '}';
        }
    }
}

