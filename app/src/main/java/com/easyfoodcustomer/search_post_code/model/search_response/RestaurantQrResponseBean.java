package com.easyfoodcustomer.search_post_code.model.search_response;

public class RestaurantQrResponseBean {
    private boolean success;
    private String message;
    private int iscovid;
    private DataBean data;
    private ErrorBean error;

    public boolean isSuccess() {
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

    public int getIscovid() {
        return iscovid;
    }

    public void setIscovid(int iscovid) {
        this.iscovid = iscovid;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public static class DataBean {
        /**
         * id : d8d42dd8-5e2a-11ea-a49f-0657952ed75a
         * restaurant_name : Sausage Factory  (do not order)
         * delivery_options : collection,delivery,dine_in
         */

        private String id;
        private String restaurant_name;
        private String delivery_options;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRestaurant_name() {
            return restaurant_name;
        }

        public void setRestaurant_name(String restaurant_name) {
            this.restaurant_name = restaurant_name;
        }

        public String getDelivery_options() {
            return delivery_options;
        }

        public void setDelivery_options(String delivery_options) {
            this.delivery_options = delivery_options;
        }
    }

    public static class ErrorBean {
    }
}
