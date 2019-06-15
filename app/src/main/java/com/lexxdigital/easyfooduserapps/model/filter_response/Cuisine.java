
package com.lexxdigital.easyfooduserapps.model.filter_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cuisine {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("count")
    @Expose
    private Integer count;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
