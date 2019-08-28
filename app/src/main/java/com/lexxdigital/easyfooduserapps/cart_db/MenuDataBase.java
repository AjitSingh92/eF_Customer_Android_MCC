package com.lexxdigital.easyfooduserapps.cart_db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.lexxdigital.easyfooduserapps.cart_db.dao.MenuDao;
import com.lexxdigital.easyfooduserapps.cart_db.dao.MenuProductDao;
import com.lexxdigital.easyfooduserapps.cart_db.dao.ProductSizeAndModifierDao;
import com.lexxdigital.easyfooduserapps.cart_db.tables.MenuProducts;
import com.lexxdigital.easyfooduserapps.cart_db.tables.ProductSizeAndModifier;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.Menu;

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
