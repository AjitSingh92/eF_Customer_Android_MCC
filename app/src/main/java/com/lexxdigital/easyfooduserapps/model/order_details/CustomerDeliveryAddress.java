package com.lexxdigital.easyfooduserapps.model.order_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerDeliveryAddress {
    @SerializedName("customer_delivery_address_id")
    @Expose
    private String customerDeliveryAddressId;
    @SerializedName("customer_delivery_address_1")
    @Expose
    private String customerDeliveryAddress1;
    @SerializedName("customer_delivery_address_2")
    @Expose
    private String customerDeliveryAddress2;
    @SerializedName("customer_delivery_city")
    @Expose
    private String customerDeliveryCity;
    @SerializedName("customer_delivery_post_code")
    @Expose
    private String customerDeliveryPostCode;
    @SerializedName("customer_delivery_country")
    @Expose
    private String customerDeliveryCountry;
    @SerializedName("customer_delivery_address_type")
    @Expose
    private String customerDeliveryAddressType;

    public String getCustomerDeliveryAddressId() {
        return customerDeliveryAddressId;
    }

    public void setCustomerDeliveryAddressId(String customerDeliveryAddressId) {
        this.customerDeliveryAddressId = customerDeliveryAddressId;
    }

    public String getCustomerDeliveryAddress1() {
        return customerDeliveryAddress1;
    }

    public void setCustomerDeliveryAddress1(String customerDeliveryAddress1) {
        this.customerDeliveryAddress1 = customerDeliveryAddress1;
    }

    public String getCustomerDeliveryAddress2() {
        return customerDeliveryAddress2;
    }

    public void setCustomerDeliveryAddress2(String customerDeliveryAddress2) {
        this.customerDeliveryAddress2 = customerDeliveryAddress2;
    }

    public String getCustomerDeliveryCity() {
        return customerDeliveryCity;
    }

    public void setCustomerDeliveryCity(String customerDeliveryCity) {
        this.customerDeliveryCity = customerDeliveryCity;
    }

    public String getCustomerDeliveryPostCode() {
        return customerDeliveryPostCode;
    }

    public void setCustomerDeliveryPostCode(String customerDeliveryPostCode) {
        this.customerDeliveryPostCode = customerDeliveryPostCode;
    }

    public String getCustomerDeliveryCountry() {
        return customerDeliveryCountry;
    }

    public void setCustomerDeliveryCountry(String customerDeliveryCountry) {
        this.customerDeliveryCountry = customerDeliveryCountry;
    }

    public String getCustomerDeliveryAddressType() {
        return customerDeliveryAddressType;
    }

    public void setCustomerDeliveryAddressType(String customerDeliveryAddressType) {
        this.customerDeliveryAddressType = customerDeliveryAddressType;
    }
}
