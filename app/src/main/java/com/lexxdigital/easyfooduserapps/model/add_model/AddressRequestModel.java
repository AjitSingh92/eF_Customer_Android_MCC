package com.lexxdigital.easyfooduserapps.model.add_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressRequestModel {
    @Expose
    @SerializedName("customer_id")
    String customerId;
    @Expose
    @SerializedName("address_1")
    String address_1;
    @Expose
    @SerializedName("address_2")
    String address_2;
    @Expose
    @SerializedName("city")
    String city;
    @Expose
    @SerializedName("post_code")
    String post_code;
    @Expose
    @SerializedName("country")
    String country;
    @Expose
    @SerializedName("address_type")
    String address_type;
    @Expose
    @SerializedName("is_default")
    int is_default;


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAddress_1() {
        return address_1;
    }

    public void setAddress_1(String address_1) {
        this.address_1 = address_1;
    }

    public String getAddress_2() {
        return address_2;
    }

    public void setAddress_2(String address_2) {
        this.address_2 = address_2;
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

    public String getAddress_type() {
        return address_type;
    }

    public void setAddress_type(String address_type) {
        this.address_type = address_type;
    }

    public int isIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    @Override
    public String toString() {
        return "AddressRequestModel{" +
                "customerId='" + customerId + '\'' +
                ", address_1='" + address_1 + '\'' +
                ", address_2='" + address_2 + '\'' +
                ", city='" + city + '\'' +
                ", post_code='" + post_code + '\'' +
                ", country='" + country + '\'' +
                ", address_type='" + address_type + '\'' +
                ", is_default=" + is_default +
                '}';
    }
}
