
package com.lexxdigital.easyfooduserapps.restaurant_details.model.new_restaurant_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("timings")
    @Expose
    private Timings timings;

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Timings getTimings() {
        return timings;
    }

    public void setTimings(Timings timings) {
        this.timings = timings;
    }

}
