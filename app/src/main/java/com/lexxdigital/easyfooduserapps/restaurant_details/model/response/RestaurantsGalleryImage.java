
package com.lexxdigital.easyfooduserapps.restaurant_details.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantsGalleryImage {

    @SerializedName("file_path")
    @Expose
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
