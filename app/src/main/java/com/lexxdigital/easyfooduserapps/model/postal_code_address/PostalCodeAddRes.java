package com.lexxdigital.easyfooduserapps.model.postal_code_address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostalCodeAddRes {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

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

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }


    public class Datum {

        @SerializedName("postcode")
        @Expose
        private String postcode;
        @SerializedName("postcode_inward")
        @Expose
        private String postcodeInward;
        @SerializedName("postcode_outward")
        @Expose
        private String postcodeOutward;
        @SerializedName("post_town")
        @Expose
        private String postTown;
        @SerializedName("dependant_locality")
        @Expose
        private String dependantLocality;
        @SerializedName("double_dependant_locality")
        @Expose
        private String doubleDependantLocality;
        @SerializedName("thoroughfare")
        @Expose
        private String thoroughfare;
        @SerializedName("dependant_thoroughfare")
        @Expose
        private String dependantThoroughfare;
        @SerializedName("building_number")
        @Expose
        private String buildingNumber;
        @SerializedName("building_name")
        @Expose
        private String buildingName;
        @SerializedName("sub_building_name")
        @Expose
        private String subBuildingName;
        @SerializedName("po_box")
        @Expose
        private String poBox;
        @SerializedName("department_name")
        @Expose
        private String departmentName;
        @SerializedName("organisation_name")
        @Expose
        private String organisationName;
        @SerializedName("udprn")
        @Expose
        private Integer udprn;
        @SerializedName("umprn")
        @Expose
        private String umprn;
        @SerializedName("postcode_type")
        @Expose
        private String postcodeType;
        @SerializedName("su_organisation_indicator")
        @Expose
        private String suOrganisationIndicator;
        @SerializedName("delivery_point_suffix")
        @Expose
        private String deliveryPointSuffix;
        @SerializedName("line_1")
        @Expose
        private String line1;
        @SerializedName("line_2")
        @Expose
        private String line2;
        @SerializedName("line_3")
        @Expose
        private String line3;
        @SerializedName("premise")
        @Expose
        private String premise;
        @SerializedName("longitude")
        @Expose
        private Float longitude;
        @SerializedName("latitude")
        @Expose
        private Float latitude;
        @SerializedName("eastings")
        @Expose
        private Integer eastings;
        @SerializedName("northings")
        @Expose
        private Integer northings;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("traditional_county")
        @Expose
        private String traditionalCounty;
        @SerializedName("administrative_county")
        @Expose
        private String administrativeCounty;
        @SerializedName("postal_county")
        @Expose
        private String postalCounty;
        @SerializedName("county")
        @Expose
        private String county;
        @SerializedName("district")
        @Expose
        private String district;
        @SerializedName("ward")
        @Expose
        private String ward;

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getPostcodeInward() {
            return postcodeInward;
        }

        public void setPostcodeInward(String postcodeInward) {
            this.postcodeInward = postcodeInward;
        }

        public String getPostcodeOutward() {
            return postcodeOutward;
        }

        public void setPostcodeOutward(String postcodeOutward) {
            this.postcodeOutward = postcodeOutward;
        }

        public String getPostTown() {
            return postTown;
        }

        public void setPostTown(String postTown) {
            this.postTown = postTown;
        }

        public String getDependantLocality() {
            return dependantLocality;
        }

        public void setDependantLocality(String dependantLocality) {
            this.dependantLocality = dependantLocality;
        }

        public String getDoubleDependantLocality() {
            return doubleDependantLocality;
        }

        public void setDoubleDependantLocality(String doubleDependantLocality) {
            this.doubleDependantLocality = doubleDependantLocality;
        }

        public String getThoroughfare() {
            return thoroughfare;
        }

        public void setThoroughfare(String thoroughfare) {
            this.thoroughfare = thoroughfare;
        }

        public String getDependantThoroughfare() {
            return dependantThoroughfare;
        }

        public void setDependantThoroughfare(String dependantThoroughfare) {
            this.dependantThoroughfare = dependantThoroughfare;
        }

        public String getBuildingNumber() {
            return buildingNumber;
        }

        public void setBuildingNumber(String buildingNumber) {
            this.buildingNumber = buildingNumber;
        }

        public String getBuildingName() {
            return buildingName;
        }

        public void setBuildingName(String buildingName) {
            this.buildingName = buildingName;
        }

        public String getSubBuildingName() {
            return subBuildingName;
        }

        public void setSubBuildingName(String subBuildingName) {
            this.subBuildingName = subBuildingName;
        }

        public String getPoBox() {
            return poBox;
        }

        public void setPoBox(String poBox) {
            this.poBox = poBox;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public String getOrganisationName() {
            return organisationName;
        }

        public void setOrganisationName(String organisationName) {
            this.organisationName = organisationName;
        }

        public Integer getUdprn() {
            return udprn;
        }

        public void setUdprn(Integer udprn) {
            this.udprn = udprn;
        }

        public String getUmprn() {
            return umprn;
        }

        public void setUmprn(String umprn) {
            this.umprn = umprn;
        }

        public String getPostcodeType() {
            return postcodeType;
        }

        public void setPostcodeType(String postcodeType) {
            this.postcodeType = postcodeType;
        }

        public String getSuOrganisationIndicator() {
            return suOrganisationIndicator;
        }

        public void setSuOrganisationIndicator(String suOrganisationIndicator) {
            this.suOrganisationIndicator = suOrganisationIndicator;
        }

        public String getDeliveryPointSuffix() {
            return deliveryPointSuffix;
        }

        public void setDeliveryPointSuffix(String deliveryPointSuffix) {
            this.deliveryPointSuffix = deliveryPointSuffix;
        }

        public String getLine1() {
            return line1;
        }

        public void setLine1(String line1) {
            this.line1 = line1;
        }

        public String getLine2() {
            return line2;
        }

        public void setLine2(String line2) {
            this.line2 = line2;
        }

        public String getLine3() {
            return line3;
        }

        public void setLine3(String line3) {
            this.line3 = line3;
        }

        public String getPremise() {
            return premise;
        }

        public void setPremise(String premise) {
            this.premise = premise;
        }

        public Float getLongitude() {
            return longitude;
        }

        public void setLongitude(Float longitude) {
            this.longitude = longitude;
        }

        public Float getLatitude() {
            return latitude;
        }

        public void setLatitude(Float latitude) {
            this.latitude = latitude;
        }

        public Integer getEastings() {
            return eastings;
        }

        public void setEastings(Integer eastings) {
            this.eastings = eastings;
        }

        public Integer getNorthings() {
            return northings;
        }

        public void setNorthings(Integer northings) {
            this.northings = northings;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getTraditionalCounty() {
            return traditionalCounty;
        }

        public void setTraditionalCounty(String traditionalCounty) {
            this.traditionalCounty = traditionalCounty;
        }

        public String getAdministrativeCounty() {
            return administrativeCounty;
        }

        public void setAdministrativeCounty(String administrativeCounty) {
            this.administrativeCounty = administrativeCounty;
        }

        public String getPostalCounty() {
            return postalCounty;
        }

        public void setPostalCounty(String postalCounty) {
            this.postalCounty = postalCounty;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getWard() {
            return ward;
        }

        public void setWard(String ward) {
            this.ward = ward;
        }

    }
}
