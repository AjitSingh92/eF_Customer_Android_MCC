
package com.lexxdigital.easyfooduserapps.model.filter_response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FilterBy {

    @SerializedName("offers")
    @Expose
    private List<Offer> offers = null;
    @SerializedName("cuisine")
    @Expose
    private List<Cuisine> cuisine = null;

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public List<Cuisine> getCuisine() {
        return cuisine;
    }

    public void setCuisine(List<Cuisine> cuisine) {
        this.cuisine = cuisine;
    }

}
