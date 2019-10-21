package com.lexxdigital.easyfooduserapps.model.restuarant_time_slot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimeSlotResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;


    @SerializedName("todaydate")
    @Expose
    private String todaydate;

    @SerializedName("tomorrowdate")
    @Expose
    private String tomorrowdate;

    @SerializedName("timenow")
    @Expose
    private String timenow;

    @SerializedName("data")
    @Expose
    private Data data;

    @SerializedName("error")
    @Expose
    private Errors errors;

    public class Data {

        @SerializedName("today")
        @Expose
        private List<String> today = null;
        @SerializedName("tomorrow")
        @Expose
        private List<String> tomorrow = null;

        public List<String> getToday() {
            return today;
        }

        public void setToday(List<String> today) {
            this.today = today;
        }

        public List<String> getTomorrow() {
            return tomorrow;
        }

        public void setTomorrow(List<String> tomorrow) {
            this.tomorrow = tomorrow;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "today=" + today +
                    ", tomorrow=" + tomorrow +
                    '}';
        }
    }

    public class Errors {

    }

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

    public String getTodaydate() {
        return todaydate;
    }

    public void setTodaydate(String todaydate) {
        this.todaydate = todaydate;
    }

    public String getTomorrowdate() {
        return tomorrowdate;
    }

    public void setTomorrowdate(String tomorrowdate) {
        this.tomorrowdate = tomorrowdate;
    }

    public String getTimenow() {
        return timenow;
    }

    public void setTimenow(String timenow) {
        this.timenow = timenow;
    }


    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "TimeSlotResponse{" +
                "success=" + success +
                ", message='" + message +
                ", todaydate='" + todaydate +
                ", tomorrowdate='" + tomorrowdate +
                ", timenow='" + timenow + '\'' +
                ", data=" + data +
                ", errors=" + errors +
                '}';
    }
}
