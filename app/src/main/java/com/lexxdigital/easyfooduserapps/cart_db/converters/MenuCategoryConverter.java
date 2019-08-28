package com.lexxdigital.easyfooduserapps.cart_db.converters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;

import java.lang.reflect.Type;
import java.util.List;

public class MenuCategoryConverter {
    @TypeConverter
    public static List<MenuCategory> fromString(String value) {
        Type listType = new TypeToken<List<MenuCategory>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayLisr(List<MenuCategory> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
