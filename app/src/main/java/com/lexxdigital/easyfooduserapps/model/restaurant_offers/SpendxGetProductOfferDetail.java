package com.lexxdigital.easyfooduserapps.model.restaurant_offers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpendxGetProductOfferDetail {

    @SerializedName("start_price")
    @Expose
    private String startPrice;
    @SerializedName("end_price")
    @Expose
    private String endPrice;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("offer_detail")
    @Expose
    private String offerDetail;
    @SerializedName("product")
    @Expose
    private RestaurantOffersProduct restaurantOffersProduct;

    public static class RestaurantOffersProduct {
        @SerializedName("product_name")
        @Expose
        private String productName;

        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("product_size_name")
        @Expose
        private String productSizeName;
        @SerializedName("product_size_id")
        @Expose
        private String productSizeid;

        public RestaurantOffersProduct() {
        }

        public RestaurantOffersProduct(String productName, String productId, String productSizeName, String productSizeid) {
            this.productName = productName;
            this.productId = productId;
            this.productSizeName = productSizeName;
            this.productSizeid = productSizeid;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductSizeName() {
            return productSizeName;
        }

        public void setProductSizeName(String productSizeName) {
            this.productSizeName = productSizeName;
        }

        public String getProductSizeid() {
            return productSizeid;
        }

        public void setProductSizeid(String productSizeid) {
            this.productSizeid = productSizeid;
        }

        @Override
        public String toString() {
            return "RestaurantOffersProduct{" +
                    "productName='" + productName + '\'' +
                    ", productId='" + productId + '\'' +
                    ", productSizeName='" + productSizeName + '\'' +
                    ", productSizeid='" + productSizeid + '\'' +
                    '}';
        }
    }

    public SpendxGetProductOfferDetail() {
    }

    public SpendxGetProductOfferDetail(String startPrice, String endPrice, String productName, String productId, String offerDetail, RestaurantOffersProduct restaurantOffersProduct) {
        this.startPrice = startPrice;
        this.endPrice = endPrice;
        this.productName = productName;
        this.productId = productId;
        this.offerDetail = offerDetail;
        this.restaurantOffersProduct = restaurantOffersProduct;
    }

    public String getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(String startPrice) {
        this.startPrice = startPrice;
    }

    public String getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(String endPrice) {
        this.endPrice = endPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOfferDetail() {
        return offerDetail;
    }

    public void setOfferDetail(String offerDetail) {
        this.offerDetail = offerDetail;
    }

    public RestaurantOffersProduct getRestaurantOffersProduct() {
        return restaurantOffersProduct;
    }

    public void setRestaurantOffersProduct(RestaurantOffersProduct restaurantOffersProduct) {
        this.restaurantOffersProduct = restaurantOffersProduct;
    }

    @Override
    public String toString() {
        return "SpendxGetProductOfferDetail{" +
                "startPrice='" + startPrice + '\'' +
                ", endPrice='" + endPrice + '\'' +
                ", productName='" + productName + '\'' +
                ", productId='" + productId + '\'' +
                ", offerDetail='" + offerDetail + '\'' +
                ", restaurantOffersProduct=" + restaurantOffersProduct +
                '}';
    }
}
