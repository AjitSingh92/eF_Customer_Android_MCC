package com.lexxdigital.easyfooduserapps.model;

import java.io.Serializable;

public class AddressList implements Serializable {
    protected String ID;
    protected String CustomerID;
    protected String AddressOne;
    protected String AddressTwo;
    protected String City;
    protected String PostCode;
    protected String Country;
    protected String AddressType;
    protected Integer IsDefault;
    protected Integer isDelivering;

    public AddressList(String ID, String customerID, String addressOne, String addressTwo, String city, String postCode, String country, String addressType, Integer isDefault, Integer isDelivered) {
        this.ID = ID;
        CustomerID = customerID;
        AddressOne = addressOne;
        AddressTwo = addressTwo;
        City = city;
        PostCode = postCode;
        Country = country;
        AddressType = addressType;
        IsDefault = isDefault;
        this.isDelivering = isDelivered;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getAddressOne() {
        return AddressOne;
    }

    public void setAddressOne(String addressOne) {
        AddressOne = addressOne;
    }

    public String getAddressTwo() {
        return AddressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        AddressTwo = addressTwo;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPostCode() {
        return PostCode;
    }

    public void setPostCode(String postCode) {
        PostCode = postCode;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getAddressType() {
        return AddressType;
    }

    public void setAddressType(String addressType) {
        AddressType = addressType;
    }

    public Integer getIsDefault() {
        return IsDefault;
    }

    public void setIsDefault(Integer isDefault) {
        IsDefault = isDefault;
    }

    public Integer getIsDelivered() {
        return isDelivering;
    }

    public void setIsDelivered(Integer isDelivered) {
        this.isDelivering = isDelivered;
    }
}
