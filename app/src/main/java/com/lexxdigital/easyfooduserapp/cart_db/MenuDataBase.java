package com.lexxdigital.easyfooduserapp.cart_db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.lexxdigital.easyfooduserapp.cart_db.dao.MenuDao;
import com.lexxdigital.easyfooduserapp.cart_db.dao.MenuProductDao;
import com.lexxdigital.easyfooduserapp.cart_db.dao.ProductSizeAndModifierDao;
import com.lexxdigital.easyfooduserapp.cart_db.tables.MenuProducts;
import com.lexxdigital.easyfooduserapp.cart_db.tables.ProductSizeAndModifier;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.Menu;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;

@Database(
        entities = {
                Menu.class,
                MenuProducts.MenuProductsTable.class,
                ProductSizeAndModifier.ProductSizeAndModifierTable.class

        }, version = 1, exportSchema = false)
@TypeConverters({})
public abstract class MenuDataBase extends RoomDatabase {

    public abstract MenuDao menuMaster();

    public abstract MenuProductDao menuProductMaster();

    public abstract ProductSizeAndModifierDao productSizeAndModifierMaster();
}
