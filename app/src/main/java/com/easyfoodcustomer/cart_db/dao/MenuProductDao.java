package com.easyfoodcustomer.cart_db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.easyfoodcustomer.cart_db.tables.MenuProducts;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

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
