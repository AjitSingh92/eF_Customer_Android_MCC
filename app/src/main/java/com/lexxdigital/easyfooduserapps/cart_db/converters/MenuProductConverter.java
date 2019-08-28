package com.lexxdigital.easyfooduserapps.cart_db.converters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;

import java.lang.reflect.Type;
import java.util.List;

public class MenuProductConverter {
    @TypeConverter
    public static List<MenuProduct> fromString(String value) {
        Type listType = new TypeToken<List<MenuProduct>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayLisr(List<MenuProduct> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
