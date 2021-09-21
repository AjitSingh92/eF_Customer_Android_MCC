package com.easyfoodcustomer.cart_db.converters;

import androidx.room.TypeConverter;

import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.ProductMainDetails;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class ProductMainDetailConverter {
    @TypeConverter
    public static ProductMainDetails fromString(String value) {
        Type listType = new TypeToken<ProductMainDetails>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayLisr(ProductMainDetails list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
