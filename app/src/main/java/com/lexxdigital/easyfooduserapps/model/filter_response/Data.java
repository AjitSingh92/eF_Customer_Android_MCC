
package com.lexxdigital.easyfooduserapps.model.filter_response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("sort_by")
    @Expose
    private List<SortBy> sortBy = null;
    @SerializedName("filter_by")
    @Expose
    private FilterBy filterBy;

    public List<SortBy> getSortBy() {
        return sortBy;
    }

    public void setSortBy(List<SortBy> sortBy) {
        this.sortBy = sortBy;
    }

    public FilterBy getFilterBy() {
        return filterBy;
    }

    public void setFilterBy(FilterBy filterBy) {
        this.filterBy = filterBy;
    }

}
