package com.easyfoodcustomer.cart_db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.Menu;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MenuDao {
    @Query("select * from Menu WHERE restaurantId=:restaurantId")
    LiveData<Menu> getMenuCategoryList(String restaurantId);

    @Insert(onConflict = REPLACE)
    void insert(Menu menu);

    @Insert(onConflict = REPLACE)
    void insert(List<Menu> menus);

    @Query("DELETE FROM Menu")
    void nuke();
}
