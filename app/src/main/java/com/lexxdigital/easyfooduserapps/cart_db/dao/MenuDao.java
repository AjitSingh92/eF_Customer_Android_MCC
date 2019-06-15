package com.lexxdigital.easyfooduserapps.cart_db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.Menu;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

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
