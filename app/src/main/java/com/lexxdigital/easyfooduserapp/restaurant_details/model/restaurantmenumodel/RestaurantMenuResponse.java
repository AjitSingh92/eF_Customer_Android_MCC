package com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RestaurantMenuResponse implements Serializable {
    @SerializedName("success")
    private Boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Data data;

    public RestaurantMenuResponse() {
    }

    public RestaurantMenuResponse(Boolean success, String message, Data data) {
        this.success = success;
        this.message = message;
        this.data = data;
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RestaurantMenuResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static class Data {
        @SerializedName("menu")
        private Menu menu;

        public Data() {
        }

        public Data(Menu menu) {
            this.menu = menu;
        }

        public Menu getMenu() {
            return menu;
        }

        public void setMenu(Menu menu) {
            this.menu = menu;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "menu=" + menu +
                    '}';
        }
    }

    public class Menu {

        @SerializedName("special_offers")
        private List<SpecialOffer> specialOffers = null;
        @SerializedName("menu_category")
        private List<MenuCategory> menuCategory = null;

        public List<SpecialOffer> getSpecialOffers() {
            return specialOffers;
        }

        public void setSpecialOffers(List<SpecialOffer> specialOffers) {
            this.specialOffers = specialOffers;
        }

        public List<MenuCategory> getMenuCategory() {
            return menuCategory;
        }

        public void setMenuCategory(List<MenuCategory> menuCategory) {
            this.menuCategory = menuCategory;
        }

    }


    public class MenuCategory {

        @SerializedName("menu_category_id")
        private String menuCategoryId;
        @SerializedName("menu_category_name")
        private String menuCategoryName;
        @SerializedName("menu_sub_category")
        private List<MenuSubCategory> menuSubCategory = null;
        @SerializedName("menu_products")
        private List<MenuProduct_> menuProducts = null;

        public String getMenuCategoryId() {
            return menuCategoryId;
        }

        public void setMenuCategoryId(String menuCategoryId) {
            this.menuCategoryId = menuCategoryId;
        }

        public String getMenuCategoryName() {
            return menuCategoryName;
        }

        public void setMenuCategoryName(String menuCategoryName) {
            this.menuCategoryName = menuCategoryName;
        }

        public List<MenuSubCategory> getMenuSubCategory() {
            return menuSubCategory;
        }

        public void setMenuSubCategory(List<MenuSubCategory> menuSubCategory) {
            this.menuSubCategory = menuSubCategory;
        }

        public List<MenuProduct_> getMenuProducts() {
            return menuProducts;
        }

        public void setMenuProducts(List<MenuProduct_> menuProducts) {
            this.menuProducts = menuProducts;
        }

    }

    public class MenuProduct {

        @SerializedName("menu_product_id")
        private String menuProductId;
        @SerializedName("product_name")
        private String productName;
        @SerializedName("veg_type")
        private String vegType;
        @SerializedName("menu_product_price")
        private String menuProductPrice;
        @SerializedName("userapp_product_image")
        private String userappProductImage;
        @SerializedName("ecom_product_image")
        private String ecomProductImage;
        @SerializedName("product_overall_rating")
        private String productOverallRating;
        @SerializedName("menu_product_size")
        private List<Object> menuProductSize = null;
        @SerializedName("product_modifiers")
        private List<Object> productModifiers = null;
        @SerializedName("upsells")
        private Upsells upsells;

        public String getMenuProductId() {
            return menuProductId;
        }

        public void setMenuProductId(String menuProductId) {
            this.menuProductId = menuProductId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getVegType() {
            return vegType;
        }

        public void setVegType(String vegType) {
            this.vegType = vegType;
        }

        public String getMenuProductPrice() {
            return menuProductPrice;
        }

        public void setMenuProductPrice(String menuProductPrice) {
            this.menuProductPrice = menuProductPrice;
        }

        public String getUserappProductImage() {
            return userappProductImage;
        }

        public void setUserappProductImage(String userappProductImage) {
            this.userappProductImage = userappProductImage;
        }

        public String getEcomProductImage() {
            return ecomProductImage;
        }

        public void setEcomProductImage(String ecomProductImage) {
            this.ecomProductImage = ecomProductImage;
        }

        public String getProductOverallRating() {
            return productOverallRating;
        }

        public void setProductOverallRating(String productOverallRating) {
            this.productOverallRating = productOverallRating;
        }

        public List<Object> getMenuProductSize() {
            return menuProductSize;
        }

        public void setMenuProductSize(List<Object> menuProductSize) {
            this.menuProductSize = menuProductSize;
        }

        public List<Object> getProductModifiers() {
            return productModifiers;
        }

        public void setProductModifiers(List<Object> productModifiers) {
            this.productModifiers = productModifiers;
        }

        public Upsells getUpsells() {
            return upsells;
        }

        public void setUpsells(Upsells upsells) {
            this.upsells = upsells;
        }

    }

    public class MenuProductSize {

        @SerializedName("product_size_id")
        private String productSizeId;
        @SerializedName("product_size_name")
        private String productSizeName;
        @SerializedName("product_size_price")
        private String productSizePrice;
        @SerializedName("size_modifiers")
        private List<SizeModifier> sizeModifiers = null;

        public String getProductSizeId() {
            return productSizeId;
        }

        public void setProductSizeId(String productSizeId) {
            this.productSizeId = productSizeId;
        }

        public String getProductSizeName() {
            return productSizeName;
        }

        public void setProductSizeName(String productSizeName) {
            this.productSizeName = productSizeName;
        }

        public String getProductSizePrice() {
            return productSizePrice;
        }

        public void setProductSizePrice(String productSizePrice) {
            this.productSizePrice = productSizePrice;
        }

        public List<SizeModifier> getSizeModifiers() {
            return sizeModifiers;
        }

        public void setSizeModifiers(List<SizeModifier> sizeModifiers) {
            this.sizeModifiers = sizeModifiers;
        }

    }

    public class MenuProduct_ {

        @SerializedName("menu_product_id")
        private String menuProductId;
        @SerializedName("product_name")
        private String productName;
        @SerializedName("veg_type")
        private String vegType;
        @SerializedName("menu_product_price")
        private String menuProductPrice;
        @SerializedName("userapp_product_image")
        private String userappProductImage;
        @SerializedName("ecom_product_image")
        private String ecomProductImage;
        @SerializedName("product_overall_rating")
        private String productOverallRating;
        @SerializedName("menu_product_size")
        private List<MenuProductSize> menuProductSize = null;
        @SerializedName("product_modifiers")
        private List<ProductModifier> productModifiers = null;
        @SerializedName("upsells")
        private Upsells_ upsells;

        public String getMenuProductId() {
            return menuProductId;
        }

        public void setMenuProductId(String menuProductId) {
            this.menuProductId = menuProductId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getVegType() {
            return vegType;
        }

        public void setVegType(String vegType) {
            this.vegType = vegType;
        }

        public String getMenuProductPrice() {
            return menuProductPrice;
        }

        public void setMenuProductPrice(String menuProductPrice) {
            this.menuProductPrice = menuProductPrice;
        }

        public String getUserappProductImage() {
            return userappProductImage;
        }

        public void setUserappProductImage(String userappProductImage) {
            this.userappProductImage = userappProductImage;
        }

        public String getEcomProductImage() {
            return ecomProductImage;
        }

        public void setEcomProductImage(String ecomProductImage) {
            this.ecomProductImage = ecomProductImage;
        }

        public String getProductOverallRating() {
            return productOverallRating;
        }

        public void setProductOverallRating(String productOverallRating) {
            this.productOverallRating = productOverallRating;
        }

        public List<MenuProductSize> getMenuProductSize() {
            return menuProductSize;
        }

        public void setMenuProductSize(List<MenuProductSize> menuProductSize) {
            this.menuProductSize = menuProductSize;
        }

        public List<ProductModifier> getProductModifiers() {
            return productModifiers;
        }

        public void setProductModifiers(List<ProductModifier> productModifiers) {
            this.productModifiers = productModifiers;
        }

        public Upsells_ getUpsells() {
            return upsells;
        }

        public void setUpsells(Upsells_ upsells) {
            this.upsells = upsells;
        }

    }

    public class MenuSubCategory {

        @SerializedName("menu_category_id")
        private String menuCategoryId;
        @SerializedName("menu_category_name")
        private String menuCategoryName;
        @SerializedName("menu_sub_category")
        private List<MenuSubCategory> menuSubCategory = null;
        @SerializedName("menu_products")
        private List<MenuProduct> menuProducts = null;

        public String getMenuCategoryId() {
            return menuCategoryId;
        }

        public void setMenuCategoryId(String menuCategoryId) {
            this.menuCategoryId = menuCategoryId;
        }

        public String getMenuCategoryName() {
            return menuCategoryName;
        }

        public void setMenuCategoryName(String menuCategoryName) {
            this.menuCategoryName = menuCategoryName;
        }

        public List<MenuSubCategory> getMenuSubCategory() {
            return menuSubCategory;
        }

        public void setMenuSubCategory(List<MenuSubCategory> menuSubCategory) {
            this.menuSubCategory = menuSubCategory;
        }

        public List<MenuProduct> getMenuProducts() {
            return menuProducts;
        }

        public void setMenuProducts(List<MenuProduct> menuProducts) {
            this.menuProducts = menuProducts;
        }

    }

    public class ModifierProduct {

        @SerializedName("product_id")
        private String productId;
        @SerializedName("unit")
        private String unit;
        @SerializedName("modifier_product_price")
        private String modifierProductPrice;
        @SerializedName("product_name")
        private String productName;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getModifierProductPrice() {
            return modifierProductPrice;
        }

        public void setModifierProductPrice(String modifierProductPrice) {
            this.modifierProductPrice = modifierProductPrice;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

    }

    public class ProductModifier {

        @SerializedName("modifier_name")
        private String modifierName;
        @SerializedName("modifier_type")
        private String modifierType;
        @SerializedName("modifier_id")
        private String modifierId;
        @SerializedName("min_allowed_quantity")
        private Integer minAllowedQuantity;
        @SerializedName("max_allowed_quantity")
        private Integer maxAllowedQuantity;
        @SerializedName("modifier_products")
        private List<ModifierProduct> modifierProducts = null;

        public String getModifierName() {
            return modifierName;
        }

        public void setModifierName(String modifierName) {
            this.modifierName = modifierName;
        }

        public String getModifierType() {
            return modifierType;
        }

        public void setModifierType(String modifierType) {
            this.modifierType = modifierType;
        }

        public String getModifierId() {
            return modifierId;
        }

        public void setModifierId(String modifierId) {
            this.modifierId = modifierId;
        }

        public Integer getMinAllowedQuantity() {
            return minAllowedQuantity;
        }

        public void setMinAllowedQuantity(Integer minAllowedQuantity) {
            this.minAllowedQuantity = minAllowedQuantity;
        }

        public Integer getMaxAllowedQuantity() {
            return maxAllowedQuantity;
        }

        public void setMaxAllowedQuantity(Integer maxAllowedQuantity) {
            this.maxAllowedQuantity = maxAllowedQuantity;
        }

        public List<ModifierProduct> getModifierProducts() {
            return modifierProducts;
        }

        public void setModifierProducts(List<ModifierProduct> modifierProducts) {
            this.modifierProducts = modifierProducts;
        }

    }

    public class SizeModifier {

        @SerializedName("modifier_name")
        private String modifierName;
        @SerializedName("modifier_type")
        private String modifierType;
        @SerializedName("modifier_id")
        private String modifierId;
        @SerializedName("min_allowed_quantity")
        private Integer minAllowedQuantity;
        @SerializedName("max_allowed_quantity")
        private Integer maxAllowedQuantity;
        @SerializedName("size_modifier_products")
        private List<SizeModifierProduct> sizeModifierProducts = null;

        public String getModifierName() {
            return modifierName;
        }

        public void setModifierName(String modifierName) {
            this.modifierName = modifierName;
        }

        public String getModifierType() {
            return modifierType;
        }

        public void setModifierType(String modifierType) {
            this.modifierType = modifierType;
        }

        public String getModifierId() {
            return modifierId;
        }

        public void setModifierId(String modifierId) {
            this.modifierId = modifierId;
        }

        public Integer getMinAllowedQuantity() {
            return minAllowedQuantity;
        }

        public void setMinAllowedQuantity(Integer minAllowedQuantity) {
            this.minAllowedQuantity = minAllowedQuantity;
        }

        public Integer getMaxAllowedQuantity() {
            return maxAllowedQuantity;
        }

        public void setMaxAllowedQuantity(Integer maxAllowedQuantity) {
            this.maxAllowedQuantity = maxAllowedQuantity;
        }

        public List<SizeModifierProduct> getSizeModifierProducts() {
            return sizeModifierProducts;
        }

        public void setSizeModifierProducts(List<SizeModifierProduct> sizeModifierProducts) {
            this.sizeModifierProducts = sizeModifierProducts;
        }

    }

    public class SizeModifierProduct {

        @SerializedName("product_id")
        private String productId;
        @SerializedName("unit")
        private String unit;
        @SerializedName("modifier_product_price")
        private String modifierProductPrice;
        @SerializedName("product_name")
        private String productName;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getModifierProductPrice() {
            return modifierProductPrice;
        }

        public void setModifierProductPrice(String modifierProductPrice) {
            this.modifierProductPrice = modifierProductPrice;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

    }

    public class SpecialOffer {

        @SerializedName("offer_id")
        private String offerId;
        @SerializedName("offer_title")
        private String offerTitle;
        @SerializedName("offer_available")
        private String offerAvailable;
        @SerializedName("spend_amount_to_avaliable_offer")
        private String spendAmountToAvaliableOffer;
        @SerializedName("offer_discount_percentage")
        private String offerDiscountPercentage;
        @SerializedName("offer_delivery_option")
        private String offerDeliveryOption;
        @SerializedName("offer_details")
        private String offerDetails;
        @SerializedName("original_price")
        private String originalPrice;
        @SerializedName("total_discount")
        private String totalDiscount;
        @SerializedName("offer_price")
        private String offerPrice;

        public String getOfferId() {
            return offerId;
        }

        public void setOfferId(String offerId) {
            this.offerId = offerId;
        }

        public String getOfferTitle() {
            return offerTitle;
        }

        public void setOfferTitle(String offerTitle) {
            this.offerTitle = offerTitle;
        }

        public String getOfferAvailable() {
            return offerAvailable;
        }

        public void setOfferAvailable(String offerAvailable) {
            this.offerAvailable = offerAvailable;
        }

        public String getSpendAmountToAvaliableOffer() {
            return spendAmountToAvaliableOffer;
        }

        public void setSpendAmountToAvaliableOffer(String spendAmountToAvaliableOffer) {
            this.spendAmountToAvaliableOffer = spendAmountToAvaliableOffer;
        }

        public String getOfferDiscountPercentage() {
            return offerDiscountPercentage;
        }

        public void setOfferDiscountPercentage(String offerDiscountPercentage) {
            this.offerDiscountPercentage = offerDiscountPercentage;
        }

        public String getOfferDeliveryOption() {
            return offerDeliveryOption;
        }

        public void setOfferDeliveryOption(String offerDeliveryOption) {
            this.offerDeliveryOption = offerDeliveryOption;
        }

        public String getOfferDetails() {
            return offerDetails;
        }

        public void setOfferDetails(String offerDetails) {
            this.offerDetails = offerDetails;
        }

        public String getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
        }

        public String getTotalDiscount() {
            return totalDiscount;
        }

        public void setTotalDiscount(String totalDiscount) {
            this.totalDiscount = totalDiscount;
        }

        public String getOfferPrice() {
            return offerPrice;
        }

        public void setOfferPrice(String offerPrice) {
            this.offerPrice = offerPrice;
        }

    }

    public class Upsells {

        @SerializedName("upsell_group_name")
        private String upsellGroupName;
        @SerializedName("upsell_group_id")
        private String upsellGroupId;
        @SerializedName("upsell_products")
        private List<Object> upsellProducts = null;

        public String getUpsellGroupName() {
            return upsellGroupName;
        }

        public void setUpsellGroupName(String upsellGroupName) {
            this.upsellGroupName = upsellGroupName;
        }

        public String getUpsellGroupId() {
            return upsellGroupId;
        }

        public void setUpsellGroupId(String upsellGroupId) {
            this.upsellGroupId = upsellGroupId;
        }

        public List<Object> getUpsellProducts() {
            return upsellProducts;
        }

        public void setUpsellProducts(List<Object> upsellProducts) {
            this.upsellProducts = upsellProducts;
        }

    }

    public class Upsells_ {

        @SerializedName("upsell_group_name")
        private String upsellGroupName;
        @SerializedName("upsell_group_id")
        private String upsellGroupId;
        @SerializedName("upsell_products")
        private List<Object> upsellProducts = null;

        public String getUpsellGroupName() {
            return upsellGroupName;
        }

        public void setUpsellGroupName(String upsellGroupName) {
            this.upsellGroupName = upsellGroupName;
        }

        public String getUpsellGroupId() {
            return upsellGroupId;
        }

        public void setUpsellGroupId(String upsellGroupId) {
            this.upsellGroupId = upsellGroupId;
        }

        public List<Object> getUpsellProducts() {
            return upsellProducts;
        }

        public void setUpsellProducts(List<Object> upsellProducts) {
            this.upsellProducts = upsellProducts;
        }

    }
}


