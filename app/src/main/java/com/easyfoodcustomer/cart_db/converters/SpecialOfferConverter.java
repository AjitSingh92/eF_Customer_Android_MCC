package com.easyfoodcustomer.cart_db.converters;

import androidx.room.TypeConverter;

import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.SpecialOffer;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class SpecialOfferConverter {
    @TypeConverter
    public static List<SpecialOffer> fromString(String value) {
        Type listType = new TypeToken<List<SpecialOffer>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayLisr(List<SpecialOffer> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
