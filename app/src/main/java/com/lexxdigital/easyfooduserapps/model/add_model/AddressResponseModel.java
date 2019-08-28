package com.lexxdigital.easyfooduserapps.model.add_model;

public class AddressResponseModel {
    boolean success;
    String message;
    Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AddressResponseModel{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    class Data{
        String customer_id;
        String city;
        String post_code;
        String country;
        String address_1;
        String address_type;
        String is_default;
        String id;
        String updated_at;
        String created_at;

        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPost_code() {
            return post_code;
        }

        public void setPost_code(String post_code) {
            this.post_code = post_code;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getAddress_1() {
            return address_1;
        }

        public void setAddress_1(String address_1) {
            this.address_1 = address_1;
        }

        public String getAddress_type() {
            return address_type;
        }

        public void setAddress_type(String address_type) {
            this.address_type = address_type;
        }

        public String getIs_default() {
            return is_default;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "customer_id='" + customer_id + '\'' +
                    ", city='" + city + '\'' +
                    ", post_code='" + post_code + '\'' +
                    ", country='" + country + '\'' +
                    ", address_1='" + address_1 + '\'' +
                    ", address_type='" + address_type + '\'' +
                    ", is_default='" + is_default + '\'' +
                    ", id='" + id + '\'' +
                    ", updated_at='" + updated_at + '\'' +
                    ", created_at='" + created_at + '\'' +
                    '}';
        }
    }







}
