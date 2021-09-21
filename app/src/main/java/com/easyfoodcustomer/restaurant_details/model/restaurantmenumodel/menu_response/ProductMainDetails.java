package com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductMainDetails {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurant_id;
    @SerializedName("product_category_id")
    @Expose
    private String product_category_id;
    @SerializedName("menu_category_id")
    @Expose
    private String menu_category_id;
    @SerializedName("cuisine_id")
    @Expose
    private String cuisine_id;
    @SerializedName("product_name")
    @Expose
    private String product_name;
    @SerializedName("wet_type")
    @Expose
    private String wet_type;
    @SerializedName("veg_type")
    @Expose
    private String veg_type;
    @SerializedName("brand_id")
    @Expose
    private Object brand_id;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("wastage_percentage")
    @Expose
    private String wastage_percentage;
    @SerializedName("kitchen_name")
    @Expose
    private String kitchen_name;
    @SerializedName("quantity")
    @Expose
    private int quantity;
    @SerializedName("buy_price")
    @Expose
    private Object buy_price;
    @SerializedName("product_price")
    @Expose
    private String product_price;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("service_notes")
    @Expose
    private Object service_notes;
    @SerializedName("method")
    @Expose
    private Object method;
    @SerializedName("critical_control")
    @Expose
    private Object critical_control;
    @SerializedName("allergen")
    @Expose
    private String allergen;
    @SerializedName("measurement_unit")
    @Expose
    private Object measurement_unit;
    @SerializedName("product_type")
    @Expose
    private String product_type;
    @SerializedName("eat_in")
    @Expose
    private int eat_in;
    @SerializedName("take_away")
    @Expose
    private int take_away;
    @SerializedName("delivery")
    @Expose
    private int delivery;
    @SerializedName("hot_cold_type")
    @Expose
    private String hot_cold_type;
    @SerializedName("position")
    @Expose
    private int position;
    @SerializedName("size_title")
    @Expose
    private String size_title;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("sort_order")
    @Expose
    private int sort_order;
    @SerializedName("created_by")
    @Expose
    private String created_by;
    @SerializedName("updated_by")
    @Expose
    private String updated_by;
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("updated_at")
    @Expose
    private String updated_at;
    @SerializedName("deleted_at")
    @Expose
    private Object deleted_at;

    public ProductMainDetails() {
    }

    public ProductMainDetails(String id, String restaurant_id, String product_category_id, String menu_category_id, String cuisine_id, String product_name, String wet_type, String veg_type, Object brand_id, String unit, String wastage_percentage, String kitchen_name, int quantity, Object buy_price, String product_price, Object description, Object service_notes, Object method, Object critical_control, String allergen, Object measurement_unit, String product_type, int eat_in, int take_away, int delivery, String hot_cold_type, int position, String size_title, int status, int sort_order, String created_by, String updated_by, String created_at, String updated_at, Object deleted_at) {
        this.id = id;
        this.restaurant_id = restaurant_id;
        this.product_category_id = product_category_id;
        this.menu_category_id = menu_category_id;
        this.cuisine_id = cuisine_id;
        this.product_name = product_name;
        this.wet_type = wet_type;
        this.veg_type = veg_type;
        this.brand_id = brand_id;
        this.unit = unit;
        this.wastage_percentage = wastage_percentage;
        this.kitchen_name = kitchen_name;
        this.quantity = quantity;
        this.buy_price = buy_price;
        this.product_price = product_price;
        this.description = description;
        this.service_notes = service_notes;
        this.method = method;
        this.critical_control = critical_control;
        this.allergen = allergen;
        this.measurement_unit = measurement_unit;
        this.product_type = product_type;
        this.eat_in = eat_in;
        this.take_away = take_away;
        this.delivery = delivery;
        this.hot_cold_type = hot_cold_type;
        this.position = position;
        this.size_title = size_title;
        this.status = status;
        this.sort_order = sort_order;
        this.created_by = created_by;
        this.updated_by = updated_by;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getProduct_category_id() {
        return product_category_id;
    }

    public void setProduct_category_id(String product_category_id) {
        this.product_category_id = product_category_id;
    }

    public String getMenu_category_id() {
        return menu_category_id;
    }

    public void setMenu_category_id(String menu_category_id) {
        this.menu_category_id = menu_category_id;
    }

    public String getCuisine_id() {
        return cuisine_id;
    }

    public void setCuisine_id(String cuisine_id) {
        this.cuisine_id = cuisine_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getWet_type() {
        return wet_type;
    }

    public void setWet_type(String wet_type) {
        this.wet_type = wet_type;
    }

    public String getVeg_type() {
        return veg_type;
    }

    public void setVeg_type(String veg_type) {
        this.veg_type = veg_type;
    }

    public Object getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(Object brand_id) {
        this.brand_id = brand_id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getWastage_percentage() {
        return wastage_percentage;
    }

    public void setWastage_percentage(String wastage_percentage) {
        this.wastage_percentage = wastage_percentage;
    }

    public String getKitchen_name() {
        return kitchen_name;
    }

    public void setKitchen_name(String kitchen_name) {
        this.kitchen_name = kitchen_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Object getBuy_price() {
        return buy_price;
    }

    public void setBuy_price(Object buy_price) {
        this.buy_price = buy_price;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getService_notes() {
        return service_notes;
    }

    public void setService_notes(Object service_notes) {
        this.service_notes = service_notes;
    }

    public Object getMethod() {
        return method;
    }

    public void setMethod(Object method) {
        this.method = method;
    }

    public Object getCritical_control() {
        return critical_control;
    }

    public void setCritical_control(Object critical_control) {
        this.critical_control = critical_control;
    }

    public String getAllergen() {
        return allergen;
    }

    public void setAllergen(String allergen) {
        this.allergen = allergen;
    }

    public Object getMeasurement_unit() {
        return measurement_unit;
    }

    public void setMeasurement_unit(Object measurement_unit) {
        this.measurement_unit = measurement_unit;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public int getEat_in() {
        return eat_in;
    }

    public void setEat_in(int eat_in) {
        this.eat_in = eat_in;
    }

    public int getTake_away() {
        return take_away;
    }

    public void setTake_away(int take_away) {
        this.take_away = take_away;
    }

    public int getDelivery() {
        return delivery;
    }

    public void setDelivery(int delivery) {
        this.delivery = delivery;
    }

    public String getHot_cold_type() {
        return hot_cold_type;
    }

    public void setHot_cold_type(String hot_cold_type) {
        this.hot_cold_type = hot_cold_type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getSize_title() {
        return size_title;
    }

    public void setSize_title(String size_title) {
        this.size_title = size_title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSort_order() {
        return sort_order;
    }

    public void setSort_order(int sort_order) {
        this.sort_order = sort_order;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Object getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(Object deleted_at) {
        this.deleted_at = deleted_at;
    }

    @Override
    public String toString() {
        return "ProductMainDetails{" +
                "id='" + id + '\'' +
                ", restaurant_id='" + restaurant_id + '\'' +
                ", product_category_id='" + product_category_id + '\'' +
                ", menu_category_id='" + menu_category_id + '\'' +
                ", cuisine_id='" + cuisine_id + '\'' +
                ", product_name='" + product_name + '\'' +
                ", wet_type='" + wet_type + '\'' +
                ", veg_type='" + veg_type + '\'' +
                ", brand_id=" + brand_id +
                ", unit='" + unit + '\'' +
                ", wastage_percentage='" + wastage_percentage + '\'' +
                ", kitchen_name='" + kitchen_name + '\'' +
                ", quantity=" + quantity +
                ", buy_price=" + buy_price +
                ", product_price='" + product_price + '\'' +
                ", description=" + description +
                ", service_notes=" + service_notes +
                ", method=" + method +
                ", critical_control=" + critical_control +
                ", allergen='" + allergen + '\'' +
                ", measurement_unit=" + measurement_unit +
                ", product_type='" + product_type + '\'' +
                ", eat_in=" + eat_in +
                ", take_away=" + take_away +
                ", delivery=" + delivery +
                ", hot_cold_type='" + hot_cold_type + '\'' +
                ", position=" + position +
                ", size_title='" + size_title + '\'' +
                ", status=" + status +
                ", sort_order=" + sort_order +
                ", created_by='" + created_by + '\'' +
                ", updated_by='" + updated_by + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", deleted_at=" + deleted_at +
                '}';
    }
}
