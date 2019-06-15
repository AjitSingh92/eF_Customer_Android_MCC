
package com.lexxdigital.easyfooduserapps.restaurant_details.model.new_restaurant_response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Timings {

    @SerializedName("monday")
    @Expose
    private List<Monday> monday = null;
    @SerializedName("tuesday")
    @Expose
    private List<Tuesday> tuesday = null;
    @SerializedName("wednesday")
    @Expose
    private List<Wednesday> wednesday = null;
    @SerializedName("thursday")
    @Expose
    private List<Thursday> thursday = null;
    @SerializedName("friday")
    @Expose
    private List<Friday> friday = null;
    @SerializedName("saturday")
    @Expose
    private List<Saturday> saturday = null;
    @SerializedName("sunday")
    @Expose
    private List<Sunday> sunday = null;

    public List<Monday> getMonday() {
        return monday;
    }

    public void setMonday(List<Monday> monday) {
        this.monday = monday;
    }

    public List<Tuesday> getTuesday() {
        return tuesday;
    }

    public void setTuesday(List<Tuesday> tuesday) {
        this.tuesday = tuesday;
    }

    public List<Wednesday> getWednesday() {
        return wednesday;
    }

    public void setWednesday(List<Wednesday> wednesday) {
        this.wednesday = wednesday;
    }

    public List<Thursday> getThursday() {
        return thursday;
    }

    public void setThursday(List<Thursday> thursday) {
        this.thursday = thursday;
    }

    public List<Friday> getFriday() {
        return friday;
    }

    public void setFriday(List<Friday> friday) {
        this.friday = friday;
    }

    public List<Saturday> getSaturday() {
        return saturday;
    }

    public void setSaturday(List<Saturday> saturday) {
        this.saturday = saturday;
    }

    public List<Sunday> getSunday() {
        return sunday;
    }

    public void setSunday(List<Sunday> sunday) {
        this.sunday = sunday;
    }

}
