package com.lexxdigital.easyfooduserapps.cart_db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

import com.lexxdigital.easyfooduserapps.cart_db.tables.ProductSizeAndModifier;

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
