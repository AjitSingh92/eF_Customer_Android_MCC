package com.easyfoodcustomer.cart_db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.easyfoodcustomer.cart_db.dao.MenuDao;
import com.easyfoodcustomer.cart_db.dao.MenuProductDao;
import com.easyfoodcustomer.cart_db.dao.ProductSizeAndModifierDao;
import com.easyfoodcustomer.cart_db.tables.MenuProducts;
import com.easyfoodcustomer.cart_db.tables.ProductSizeAndModifier;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.Menu;

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
