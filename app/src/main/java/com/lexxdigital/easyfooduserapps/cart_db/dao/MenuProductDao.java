package com.lexxdigital.easyfooduserapps.cart_db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.lexxdigital.easyfooduserapps.cart_db.tables.MenuProducts;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface MenuProductDao {
    @Query("select * from MenuProductsTable WHERE categoryId=:categoryId")
    LiveData<MenuProducts.MenuProductsTable> getMenuProductList(String categoryId);

    @Query("select * from MenuProductsTable WHERE categoryId=:categoryId")
    MenuProducts.MenuProductsTable getMenuProduct(String categoryId);

    @Insert(onConflict = REPLACE)
    Long insert(MenuProducts.MenuProductsTable menuProduct);

    @Insert(onConflict = REPLACE)
    void insert(List<MenuProducts.MenuProductsTable> menuProduct);

    @Query("DELETE FROM MenuProductsTable")
    void nuke();
}
