
package com.lexxdigital.easyfooduserapps.model.add_favourites_response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {


    @SerializedName("favourite_status")
    @Expose
    private Integer favouriteStatus;

    public Integer getFavouriteStatus() {
        return favouriteStatus;
    }

    public void setFavouriteStatus(Integer favouriteStatus) {
        this.favouriteStatus = favouriteStatus;
    }
}
