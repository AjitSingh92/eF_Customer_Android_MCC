package com.easyfoodcustomer.cart_db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

import com.easyfoodcustomer.cart_db.tables.ProductSizeAndModifier;

import java.util.List;

@Dao
public interface ProductSizeAndModifierDao {
    @Query("select * from ProductSizeAndModifierTable WHERE productId=:productId")
    LiveData<ProductSizeAndModifier.ProductSizeAndModifierTable> getProductSizeAndModifier(String productId);

    @Query("select * from ProductSizeAndModifierTable WHERE productId=:productId")
    ProductSizeAndModifier.ProductSizeAndModifierTable getProductSizeAndModifierList(String productId);

    @Insert(onConflict = REPLACE)
    Long insert(ProductSizeAndModifier.ProductSizeAndModifierTable productSizeAndModifierTable);

    @Insert(onConflict = REPLACE)
    void insert(List<ProductSizeAndModifier.ProductSizeAndModifierTable> productSizeAndModifierTable);

    @Query("DELETE FROM ProductSizeAndModifierTable")
    void nuke();
}
