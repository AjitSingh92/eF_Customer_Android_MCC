
package com.lexxdigital.easyfooduserapps.model.landing_page_lists;


public class RestaurantsGallery {

    private String filePath;
    private String fileName;
    private String restaurantId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RestaurantsGallery() {
    }

    /**
     * 
     * @param filePath
     * @param fileName
     * @param restaurantId
     */
    public RestaurantsGallery(String filePath, String fileName, String restaurantId) {
        super();
        this.filePath = filePath;
        this.fileName = fileName;
        this.restaurantId = restaurantId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

}
