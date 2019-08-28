
package com.lexxdigital.easyfooduserapps.model.favourites_list_response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("favourites")
    @Expose
    private List<Favourite> favourites = null;
    @SerializedName("total_records")
    @Expose
    private Integer totalRecords;

    public List<Favourite> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<Favourite> favourites) {
        this.favourites = favourites;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

}
