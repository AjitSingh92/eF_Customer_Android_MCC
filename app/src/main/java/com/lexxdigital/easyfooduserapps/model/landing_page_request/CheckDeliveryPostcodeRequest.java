package com.lexxdigital.easyfooduserapps.model.landing_page_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckDeliveryPostcodeRequest {

    @SerializedName("post_code")
    @Expose
    private String postCode;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "CheckDeliveryPostcodeRequest{" +
                "postCode='" + postCode + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
