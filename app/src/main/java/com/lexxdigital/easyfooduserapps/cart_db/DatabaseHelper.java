package com.lexxdigital.easyfooduserapps.cart_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.CartData;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MealProduct;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategoryCart;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuProductSize;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.Modifier;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.ProductModifier;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.SizeModifier;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.SpecialOffer;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.UpsellProduct;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "cart_db";
    private static final String TABLE_NAME_SPECIAL_OFFER = "special_offers";
    private static final String TABLE_NAME_MENU_CATEGORY = "menu_category";
    private static final String TABLE_NAME_MENU_CATEGORY2 = "menu_category2";
    private static final String TABLE_NAME_MENU_SUB_CATEGORY = "menu_sub_category";
    private static final String TABLE_NAME_MENU_PRODUCT = "menu_products";
    private static final String TABLE_NAME_MENU_PRODUCT_SIZE = "menu_product_size";
    private static final String TABLE_NAME_SIZE_MODIFIER = "size_modifiers";
    private static final String TABLE_NAME_SIZE_MODIFIER_PRODUCT = "size_modifier_products";
    private static final String TABLE_NAME_PRODUCT_MODIFIER = "product_modifiers";
    private static final String TABLE_NAME_MODIFIER_PRODUCT = "modifier_products";
    private static final String TABLE_NAME_UPSELL_PRODUCT = "upsell_products";

    private static final String TABLE_NAME_MEAL_PRODUCT = "meal_products";
    private static final String TABLE_NAME_MEAL_CATEGORY = "meal_category";


    public static final String COLUMN_ID = "id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String TABLE_UPSELL_PRODUCT =
            "CREATE TABLE " + TABLE_NAME_UPSELL_PRODUCT + "("
                    + "id TEXT ,"
                    + "groupId TEXT,"
                    + "productId TEXT PRIMARY KEY,"
                    + "categoryId TEXT,"
                    + "productName TEXT,"
                    + "description TEXT,"
                    + "originalAmount TEXT,"
                    + "originalAmount1 TEXT,"
                    + "quantity TEXT,"
                    + "unit TEXT,"
                    + "productPrice TEXT"
                    + ")";

    public static final String TABLE_SPECIAL_OFFER =
            "CREATE TABLE " + TABLE_NAME_SPECIAL_OFFER + "("
                    + "offer_id TEXT PRIMARY KEY,"
                    + "offer_title TEXT,"
                    + "offer_available TEXT,"
                    + "spend_amount_to_avaliable_offer TEXT,"
                    + "offer_discount_percentage TEXT,"
                    + "offer_delivery_option TEXT,"
                    + "offer_details TEXT,"
                    + "offer_price TEXT,"
                    + "original_price TEXT,"
                    + "total_discount TEXT,"
                    + "quantity INTEGER"
                    + ")";

    public static final String TABLE_MENU_CATEGORY =
            "CREATE TABLE " + TABLE_NAME_MENU_CATEGORY + "("
                    + "menu_category_id TEXT PRIMARY KEY,"
                    + "menu_category_name TEXT"
                    + ")";
    public static final String TABLE_MENU_CATEGORY2 =
            "CREATE TABLE " + TABLE_NAME_MENU_CATEGORY2 + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "menu_category_id TEXT,"
                    + "menu_category_name TEXT,"
                    + "menu_sub_category TEXT,"
                    + "menu_products TEXT"
                    + ")";
    public static final String TABLE_MENU_SUB_CATEGORY =
            "CREATE TABLE " + TABLE_NAME_MENU_SUB_CATEGORY + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "menuId INTEGER,"
                    + "main_menu_category_id TEXT,"
                    + "menu_category_id TEXT,"
                    + "menu_category_name TEXT"
                    + ")";

    public static final String TABLE_MENU_PRODUCT =
            "CREATE TABLE " + TABLE_NAME_MENU_PRODUCT + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "menuId INTEGER,"
                    + "menuSubCatId INTEGER,"
                    + "menu_category_id TEXT,"
                    + "menu_product_id TEXT,"
                    + "product_name TEXT,"
                    + "veg_type TEXT,"
                    + "menu_product_price TEXT,"
                    + "userapp_product_image TEXT,"
                    + "ecom_product_image TEXT,"
                    + "product_overall_rating TEXT,"
                    + "quantity INTEGER,"
                    + "menu_product_size TEXT,"
                    + "product_modifiers TEXT,"
                    + "meal_products TEXT,"
                    + "originalQuantity INTEGER,"
                    + "amount TEXT,"
                    + "originalAmount REAL,"
                    + "originalAmount1 REAL,"
                    + "upsells TEXT"
                    + ")";

    public static final String TABLE_MENU_PRODUCT_SIZE =
            "CREATE TABLE " + TABLE_NAME_MENU_PRODUCT_SIZE + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "menu_product_id TEXT,"
                    + "product_size_id TEXT,"
                    + "product_size_name TEXT,"
                    + "product_size_price TEXT"
                    + ")";


    public static final String TABLE_PRODUCT_MODIFIER =
            "CREATE TABLE " + TABLE_NAME_PRODUCT_MODIFIER + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "menu_product_id TEXT,"
                    + "modifier_id TEXT,"
                    + "modifier_type TEXT,"
                    + "modifier_name TEXT,"
                    + "min_allowed_quantity TEXT,"
                    + "max_allowed_quantity TEXT"
                    + ")";


    public static final String TABLE_MODIFIER_PRODUCT =
            "CREATE TABLE " + TABLE_NAME_MODIFIER_PRODUCT + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "modifier_id TEXT,"
                    + "product_id TEXT,"
                    + "unit TEXT,"
                    + "product_name TEXT,"
                    + "modifier_product_price TEXT"
                    + ")";


    public static final String TABLE_SIZE_MODIFIER =
            "CREATE TABLE " + TABLE_NAME_SIZE_MODIFIER + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "product_size_id TEXT ,"
                    + "modifier_id TEXT,"
                    + "modifier_name TEXT,"
                    + "modifier_type TEXT,"
                    + "max_allowed_quantity TEXT,"
                    + "min_allowed_quantity TEXT"
                    + ")";
    public static final String TABLE_SIZE_MODIFIER_PRODUCT =
            "CREATE TABLE " + TABLE_NAME_SIZE_MODIFIER_PRODUCT + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "modifier_id TEXT ,"
                    + "product_id TEXT,"
                    + "product_name TEXT,"
                    + "unit TEXT,"
                    + "modifier_product_price TEXT"
                    + ")";


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create table
        db.execSQL(TABLE_UPSELL_PRODUCT);
        db.execSQL(TABLE_SPECIAL_OFFER);
        db.execSQL(TABLE_MENU_CATEGORY);
        db.execSQL(TABLE_MENU_CATEGORY2);
        db.execSQL(TABLE_MENU_SUB_CATEGORY);
        db.execSQL(TABLE_MENU_PRODUCT);
        db.execSQL(TABLE_MENU_PRODUCT_SIZE);
        db.execSQL(TABLE_SIZE_MODIFIER);
        db.execSQL(TABLE_SIZE_MODIFIER_PRODUCT);
        db.execSQL(TABLE_PRODUCT_MODIFIER);
        db.execSQL(TABLE_MODIFIER_PRODUCT);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_UPSELL_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SPECIAL_OFFER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MENU_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MENU_CATEGORY2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MENU_SUB_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MENU_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MENU_PRODUCT_SIZE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SIZE_MODIFIER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SIZE_MODIFIER_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PRODUCT_MODIFIER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MODIFIER_PRODUCT);

        // Create tables again
        onCreate(db);
    }

    public CartData getCartData() {
        List<CartData> cartData = new ArrayList<>();

        CartData cartData1 = new CartData();
        cartData1.setMenuCategoryCarts(getMenuCategory());
        cartData1.setSpecialOffers(getSpecialOffer());
        cartData1.setUpsellProducts(getUpSellProducts());
        cartData.add(cartData1);
        // return notes list
        return cartData1;
    }

    public long insertSpecialOffer(SpecialOffer specialItem) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("offer_id", specialItem.getOfferId());
        values.put("offer_title", specialItem.getOfferTitle());
        values.put("offer_available", specialItem.getOfferAvailable());
        values.put("spend_amount_to_avaliable_offer", specialItem.getSpendAmountToAvaliableOffer());
        values.put("offer_discount_percentage", specialItem.getOfferDiscountPercentage());
        values.put("offer_delivery_option", specialItem.getOfferDeliveryOption());
        values.put("offer_details", specialItem.getOfferDetails());
        values.put("offer_price", specialItem.getOfferPrice());
        values.put("original_price", specialItem.getOriginalPrice());
        values.put("total_discount", specialItem.getTotalDiscount());
        values.put("quantity", specialItem.getQuantity());

        // insert row
        long id = db.insert(TABLE_NAME_SPECIAL_OFFER, null, values);
        // close db connection
        db.close();
        // return newly inserted row id
        return id;
    }

    public List<SpecialOffer> getSpecialOffer() {
        List<SpecialOffer> specialOfferItems = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_SPECIAL_OFFER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SpecialOffer specialOffer = new SpecialOffer();

                specialOffer.setOfferId(cursor.getString(cursor.getColumnIndex("offer_id")));
                specialOffer.setOfferTitle(cursor.getString(cursor.getColumnIndex("offer_title")));
                specialOffer.setOfferAvailable(cursor.getString(cursor.getColumnIndex("offer_available")));
                specialOffer.setSpendAmountToAvaliableOffer(cursor.getString(cursor.getColumnIndex("spend_amount_to_avaliable_offer")));
                specialOffer.setOfferDiscountPercentage(cursor.getString(cursor.getColumnIndex("offer_discount_percentage")));
                specialOffer.setOfferDeliveryOption(cursor.getString(cursor.getColumnIndex("offer_delivery_option")));
                specialOffer.setOfferDetails(cursor.getString(cursor.getColumnIndex("offer_details")));
                specialOffer.setOfferPrice(cursor.getString(cursor.getColumnIndex("offer_price")));
                specialOffer.setOriginalPrice(cursor.getString(cursor.getColumnIndex("original_price")));
                specialOffer.setTotalDiscount(cursor.getString(cursor.getColumnIndex("total_discount")));
                specialOffer.setQuantity(cursor.getInt(cursor.getColumnIndex("quantity")));

                specialOfferItems.add(specialOffer);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return specialOfferItems;
    }

    public long insertUpsellProducts(UpsellProduct upsellProduct) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", upsellProduct.getId());
        values.put("groupId", upsellProduct.getGroupId());
        values.put("productId", upsellProduct.getProductId());
        values.put("categoryId", upsellProduct.getCategoryId());
        values.put("productName", upsellProduct.getProductName());
        values.put("description", upsellProduct.getDescription());
        values.put("originalAmount", upsellProduct.getOriginalAmount());
        values.put("originalAmount1", upsellProduct.getOriginalAmount1());
        values.put("quantity", upsellProduct.getQuantity());
        values.put("unit", upsellProduct.getUnit());
        values.put("productPrice", upsellProduct.getProductPrice());

        // insert row
        long id = db.insert(TABLE_NAME_UPSELL_PRODUCT, null, values);
        db.close();
        return id;
    }


    public List<UpsellProduct> getUpSellProducts() {
        List<UpsellProduct> upsellProductItem = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_UPSELL_PRODUCT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UpsellProduct upsellProduct = new UpsellProduct();

                upsellProduct.setId(cursor.getString(cursor.getColumnIndex("id")));
                upsellProduct.setGroupId(cursor.getString(cursor.getColumnIndex("groupId")));
                upsellProduct.setProductId(cursor.getString(cursor.getColumnIndex("productId")));
                upsellProduct.setCategoryId(cursor.getString(cursor.getColumnIndex("categoryId")));
                upsellProduct.setProductName(cursor.getString(cursor.getColumnIndex("productName")));
                upsellProduct.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                upsellProduct.setOriginalAmount(cursor.getString(cursor.getColumnIndex("originalAmount")));
                upsellProduct.setOriginalAmount1(cursor.getString(cursor.getColumnIndex("originalAmount1")));
                upsellProduct.setQuantity(cursor.getString(cursor.getColumnIndex("quantity")));
                upsellProduct.setUnit(cursor.getString(cursor.getColumnIndex("unit")));
                upsellProduct.setProductPrice(cursor.getDouble(cursor.getColumnIndex("productPrice")));

                upsellProductItem.add(upsellProduct);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return upsellProductItem;
    }

    public UpsellProduct getUpSellProducts(String productId) {
        UpsellProduct upsellProduct = null;

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_UPSELL_PRODUCT + " WHERE  productId ='" + productId + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                upsellProduct = new UpsellProduct();
                upsellProduct.setId(cursor.getString(cursor.getColumnIndex("id")));
                upsellProduct.setGroupId(cursor.getString(cursor.getColumnIndex("groupId")));
                upsellProduct.setProductId(cursor.getString(cursor.getColumnIndex("productId")));
                upsellProduct.setCategoryId(cursor.getString(cursor.getColumnIndex("categoryId")));
                upsellProduct.setProductName(cursor.getString(cursor.getColumnIndex("productName")));
                upsellProduct.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                upsellProduct.setOriginalAmount(cursor.getString(cursor.getColumnIndex("originalAmount")));
                upsellProduct.setOriginalAmount1(cursor.getString(cursor.getColumnIndex("originalAmount1")));
                upsellProduct.setQuantity(cursor.getString(cursor.getColumnIndex("quantity")));
                upsellProduct.setUnit(cursor.getString(cursor.getColumnIndex("unit")));
                upsellProduct.setProductPrice(cursor.getDouble(cursor.getColumnIndex("productPrice")));

            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return upsellProduct;
    }

    public String getCategoryName(int id) {

        String menuCategoryName = "";
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_MENU_CATEGORY2 + " WHERE  id ='" + id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Gson gson = new Gson();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                menuCategoryName = cursor.getString(cursor.getColumnIndex("menu_category_name"));
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        return menuCategoryName;
    }

    public long insertMenuCategory(String menu_category_id, String menu_category_name, String menu_sub_category, String menu_products) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("menu_category_id", menu_category_id);
        values.put("menu_category_name", menu_category_name);
        values.put("menu_sub_category", menu_sub_category);
//        values.put("menu_products", menu_products);
        // insert row
        long id = db.insert(TABLE_NAME_MENU_CATEGORY2, null, values);
        // close db connection
        db.close();
        // return newly inserted row id
        return id;
    }

    public long insertMenuSubCategory(long menuId, String main_menu_category_id, String menu_category_id, String menu_category_name) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("menuId", menuId);
        values.put("main_menu_category_id", main_menu_category_id);
        values.put("menu_category_id", menu_category_id);
        values.put("menu_category_name", menu_category_name);

        // insert row
        long id = db.insert(TABLE_NAME_MENU_SUB_CATEGORY, null, values);
        // close db connection
        db.close();
        // return newly inserted row id
        return id;
    }

    public int getMenuCategoryIfExit(String menu_category_id) {
        int columnId = -1;
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_MENU_CATEGORY2 + " WHERE  menu_category_id ='" + menu_category_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Gson gson = new Gson();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                columnId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return columnId;

    }

    public int getMenuSubCategoryIfExit(String menu_category_id) {
        int columnId = -1;
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_MENU_SUB_CATEGORY + " WHERE  menu_category_id ='" + menu_category_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Gson gson = new Gson();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                columnId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return columnId;

    }

    public long insertMenuProduct(long menuId, long menuSubCatId, String menu_category_id, String menu_product_id, String product_name, String veg_type, String menu_product_price, String userapp_product_image,
                                  String ecom_product_image,
                                  String product_overall_rating,
                                  int quantity,
                                  String menu_product_size,
                                  String product_modifiers,
                                  String meal_products,
                                  Integer originalQuantity,
                                  Double originalAmount,
                                  String amount
            /*  String upsells*/) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("menuId", menuId);
        values.put("menuSubCatId", menuSubCatId);
        values.put("menu_category_id", menu_category_id);
        values.put("menu_product_id", menu_product_id);
        values.put("product_name", product_name);
        values.put("veg_type", veg_type);
        values.put("menu_product_price", menu_product_price);
        values.put("userapp_product_image", userapp_product_image);
        values.put("ecom_product_image", ecom_product_image);
        values.put("product_overall_rating", product_overall_rating);
        values.put("quantity", quantity);
        values.put("menu_product_size", menu_product_size);
        values.put("product_modifiers", product_modifiers);
        values.put("meal_products", meal_products);
        values.put("originalQuantity", originalQuantity);
        values.put("originalAmount", originalAmount);
        values.put("originalAmount1", originalAmount);
        values.put("amount", amount);
//        values.put("upsells", upsells);
//        values.put("menu_products", menu_products);
        // insert row
        long id = db.insert(TABLE_NAME_MENU_PRODUCT, null, values);
        // close db connection
        db.close();
        // return newly inserted row id
        return id;
    }

    public int updateProductQuantity(int columnId, int quantity, Double price) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("originalQuantity", quantity);
        values.put("quantity", quantity);

        if (price != -1) {
            values.put("amount", (quantity * price));
            values.put("originalAmount", (quantity * price));
        }
        updateMenuProductSizeQuantity(columnId, quantity);
        // updating row
        int id = db.update(TABLE_NAME_MENU_PRODUCT, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(columnId)});
        db.close();
        return id;
    }

    public int updateMenuProductSizeQuantity(int columnId, int quantity) {
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME_MENU_PRODUCT + " WHERE  " + COLUMN_ID + " ='" + columnId + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Gson gson = new Gson();
        // looping through all rows and adding to list

        List<MenuProductSize> mMenuProductSize = null;
        List<ProductModifier> productModifiers1 = null;
        List<MealProduct> mMealProducts = null;

        if (cursor.moveToFirst()) {
            do {

                MenuProduct data = new MenuProduct();

                List<MenuProductSize> menuProductSize = gson.fromJson(cursor.getString(cursor.getColumnIndex("menu_product_size")), new TypeToken<List<MenuProductSize>>() {
                }.getType());
                mMenuProductSize = new ArrayList<>();
                if (menuProductSize != null && menuProductSize.size() > 0) {
                    for (MenuProductSize menuProductSize1 : menuProductSize) {
                        MenuProductSize productSize = new MenuProductSize();
                        productSize.setProductSizeId(menuProductSize1.getProductSizeId());
                        productSize.setProductSizeName(menuProductSize1.getProductSizeName());
                        productSize.setProductSizePrice(menuProductSize1.getProductSizePrice());
                        productSize.setAmount(String.format("%.2f", (quantity * Double.parseDouble(menuProductSize1.getProductSizePrice()))));
                        productSize.setQuantity(quantity);
                        productSize.setSelected(menuProductSize1.getSelected());
                        productSize.setOriginalAmount((quantity * Double.parseDouble(menuProductSize1.getProductSizePrice())));
                        productSize.setOriginalAmount1(Double.parseDouble(menuProductSize1.getProductSizePrice()));
                        productSize.setOriginalQuantity(menuProductSize1.getOriginalQuantity());

//                    sizeModifiers

                        List<SizeModifier> sizeModifiers = new ArrayList<>();

                        if (menuProductSize1.getSelected()) {
                            for (SizeModifier sizeModifier : menuProductSize1.getSizeModifiers()) {
                                SizeModifier sizeModifier1 = new SizeModifier();
                                sizeModifier1.setModifierName(sizeModifier.getModifierName());
                                sizeModifier1.setModifierType(sizeModifier.getModifierType());
                                sizeModifier1.setModifierId(sizeModifier.getModifierId());
                                sizeModifier1.setMinAllowedQuantity(sizeModifier.getMinAllowedQuantity());
                                sizeModifier1.setMaxAllowedQuantity(sizeModifier.getMaxAllowedQuantity());

                                List<Modifier> modifiers = new ArrayList<>();

                                if (sizeModifier.getModifierType().equalsIgnoreCase("free")) {
                                    int maxAllowFree = sizeModifier.getMaxAllowedQuantity();
                                    int free = 0;
                                    for (int i = 0; i < sizeModifier.getModifier().size(); i++) {
                                        Modifier modifier = sizeModifier.getModifier().get(i);
                                        if (free == maxAllowFree) {
                                            int qty = Integer.parseInt(sizeModifier.getModifier().get(i).getOriginalQuantity());
                                            qty = (qty * quantity);
                                            modifier.setQuantity(String.valueOf(qty));
                                            modifier.setAmount((qty * Double.parseDouble(sizeModifier.getModifier().get(i).getModifierProductPrice())));
                                        } else {
                                            modifier.setQuantity(String.valueOf((Integer.parseInt(sizeModifier.getModifier().get(i).getOriginalQuantity()) * quantity)));
                                            int qty = Integer.parseInt(modifier.getOriginalQuantity());
                                            if (qty >= maxAllowFree) {
                                                int nQty = qty - maxAllowFree;
                                                free = maxAllowFree;
//                                            qty = (nQty * quantity);

                                                modifier.setAmount((nQty * Double.parseDouble(sizeModifier.getModifier().get(i).getModifierProductPrice())));
                                            } else {
                                                modifier.setQuantity(String.valueOf(qty));
                                                modifier.setAmount(0d);
                                                free++;
                                            }
                                        }
                                        modifiers.add(modifier);
                                    }

                                } else {
                                    for (Modifier modifier : sizeModifier.getModifier()) {
                                        int qty = Integer.parseInt(modifier.getOriginalQuantity());
                                        qty = (qty * quantity);
                                        modifier.setQuantity(String.valueOf(qty));
                                        modifier.setAmount((qty * Double.parseDouble(modifier.getModifierProductPrice())));

                                        modifiers.add(modifier);
                                    }
                                }
                                sizeModifier1.setModifier(modifiers);
                                sizeModifiers.add(sizeModifier1);
                            }
                        }
                        productSize.setSizeModifiers(sizeModifiers);

                        mMenuProductSize.add(productSize);
                    }
                }

                List<ProductModifier> productModifiers = gson.fromJson(cursor.getString(cursor.getColumnIndex("product_modifiers")), new TypeToken<List<ProductModifier>>() {
                }.getType());

                productModifiers1 = new ArrayList<>();

                if (productModifiers != null && productModifiers.size() > 0) {
                    for (ProductModifier productModifier : productModifiers) {
                        ProductModifier productModifier1 = new ProductModifier();

                        productModifier1.setModifierName(productModifier.getModifierName());
                        productModifier1.setModifierType(productModifier.getModifierType());
                        productModifier1.setModifierId(productModifier.getModifierId());
                        productModifier1.setMinAllowedQuantity(productModifier.getMinAllowedQuantity());
                        productModifier1.setMaxAllowedQuantity(productModifier.getMaxAllowedQuantity());
                        productModifier1.setAmount(productModifier.getAmount());
                        List<Modifier> modifiers = new ArrayList<>();
                        if (productModifier.getModifierType().equalsIgnoreCase("free")) {

                            int maxAllowFree = productModifier.getMaxAllowedQuantity();
                            int free = 0;
                            for (int i = 0; i < productModifier.getModifier().size(); i++) {
                                Modifier modifier = productModifier.getModifier().get(i);

                                if (free == maxAllowFree) {
                                    int qty = Integer.parseInt(productModifier.getModifier().get(i).getOriginalQuantity());
                                    qty = (qty * quantity);
                                    modifier.setQuantity(String.valueOf(qty));
                                    modifier.setAmount((qty * Double.parseDouble(productModifier.getModifier().get(i).getModifierProductPrice())));
                                } else {
                                    modifier.setQuantity(String.valueOf((Integer.parseInt(productModifier.getModifier().get(i).getOriginalQuantity()) * quantity)));
                                    int qty = Integer.parseInt(modifier.getOriginalQuantity());
                                    if (qty >= maxAllowFree) {
                                        int nQty = qty - maxAllowFree;
                                        free = maxAllowFree;
//                                    qty = (nQty * quantity);

                                        modifier.setAmount((nQty * Double.parseDouble(productModifier.getModifier().get(i).getModifierProductPrice())));
                                    } else {
                                        modifier.setQuantity(String.valueOf(qty));
                                        modifier.setAmount(0d);
                                        free++;
                                    }
                                }
                                modifiers.add(modifier);
                            }
                        } else {
                            for (Modifier modifier : productModifier.getModifier()) {
                                int qty = Integer.parseInt(modifier.getOriginalQuantity());
                                qty = (qty * quantity);
                                modifier.setQuantity(String.valueOf(qty));
                                modifier.setAmount((qty * Double.parseDouble(modifier.getModifierProductPrice())));

                                modifiers.add(modifier);
                            }
                        }
                        productModifier1.setModifier(modifiers);
                        productModifiers1.add(productModifier1);
                    }
                }


                // Aklesh working here......................
                List<MealProduct> mealProducts = gson.fromJson(cursor.getString(cursor.getColumnIndex("meal_products")), new TypeToken<List<MealProduct>>() {
                }.getType());

                mMealProducts = new ArrayList<>();
                if (mealProducts != null && mealProducts.size() > 0) {

                    for (MealProduct mealProduct : mealProducts) {
                        MealProduct mealProduct1 = new MealProduct();
                        mealProduct1.setId(mealProduct.getId());
                        mealProduct1.setMealId(mealProduct.getMealId());
                        mealProduct1.setProductId(mealProduct.getProductId());
                        mealProduct1.setProductSizeId(mealProduct.getProductSizeId());
                        mealProduct1.setProductName(mealProduct.getProductName());
                        mealProduct1.setProductSizeName(mealProduct.getProductSizeName());
                        mealProduct1.setProductModifiers(mealProduct.getProductModifiers());
                        mealProduct1.setQuantity(mealProduct.getQuantity());
                        mealProduct1.setSelected(mealProduct.getSelected());


                        List<MenuProductSize> mealMenuProductSizes = new ArrayList<>();
                        if (mealProduct.getMenuProductSize() != null) {
                            for (MenuProductSize menuProductSize1 : mealProduct.getMenuProductSize()) {
                                MenuProductSize menuProductSize2 = new MenuProductSize();
                                menuProductSize2.setProductSizeId(menuProductSize1.getProductSizeId());
                                menuProductSize2.setProductSizeName(menuProductSize1.getProductSizeName());
                                menuProductSize2.setProductSizePrice(menuProductSize1.getProductSizePrice());
                                menuProductSize2.setAmount(menuProductSize1.getAmount());
                                menuProductSize2.setQuantity(menuProductSize1.getQuantity());
                                menuProductSize2.setSelected(menuProductSize1.getSelected());

//----------------------------------------------------------------------

                                List<SizeModifier> sizeModifiers = new ArrayList<>();
                                if (menuProductSize1.getSelected()) {
                                    for (SizeModifier sizeModifier : menuProductSize1.getSizeModifiers()) {
                                        SizeModifier sizeModifier1 = new SizeModifier();
                                        sizeModifier1.setModifierName(sizeModifier.getModifierName());
                                        sizeModifier1.setModifierType(sizeModifier.getModifierType());
                                        sizeModifier1.setModifierId(sizeModifier.getModifierId());
                                        sizeModifier1.setMinAllowedQuantity(sizeModifier.getMinAllowedQuantity());
                                        sizeModifier1.setMaxAllowedQuantity(sizeModifier.getMaxAllowedQuantity());

                                        List<Modifier> modifiers = new ArrayList<>();

                                        if (sizeModifier.getModifierType().equalsIgnoreCase("free")) {
                                            int maxAllowFree = sizeModifier.getMaxAllowedQuantity();
                                            int free = 0;
                                            for (int i = 0; i < sizeModifier.getModifier().size(); i++) {
                                                Modifier modifier = sizeModifier.getModifier().get(i);
                                                if (free == maxAllowFree) {
                                                    int qty = Integer.parseInt(sizeModifier.getModifier().get(i).getOriginalQuantity());
                                                    qty = (qty * quantity);
                                                    modifier.setQuantity(String.valueOf(qty));
                                                    modifier.setAmount((qty * Double.parseDouble(sizeModifier.getModifier().get(i).getModifierProductPrice())));
                                                } else {
                                                    modifier.setQuantity(String.valueOf((Integer.parseInt(sizeModifier.getModifier().get(i).getOriginalQuantity()) * quantity)));
                                                    int qty = Integer.parseInt(modifier.getOriginalQuantity());
                                                    if (qty >= maxAllowFree) {
                                                        int nQty = qty - maxAllowFree;
                                                        free = maxAllowFree;
//                                            qty = (nQty * quantity);

                                                        modifier.setAmount((nQty * Double.parseDouble(sizeModifier.getModifier().get(i).getModifierProductPrice())));
                                                    } else {
                                                        modifier.setQuantity(String.valueOf(qty));
                                                        modifier.setAmount(0d);
                                                        free++;
                                                    }
                                                }
                                                modifiers.add(modifier);
                                            }

                                        } else {
                                            for (Modifier modifier : sizeModifier.getModifier()) {
                                                int qty = Integer.parseInt(modifier.getOriginalQuantity());
                                                qty = (qty * quantity);
                                                modifier.setQuantity(String.valueOf(qty));
                                                modifier.setAmount((qty * Double.parseDouble(modifier.getModifierProductPrice())));

                                                modifiers.add(modifier);
                                            }
                                        }
                                        sizeModifier1.setModifier(modifiers);
                                        sizeModifiers.add(sizeModifier1);
                                    }
                                }
                                menuProductSize2.setSizeModifiers(sizeModifiers);


//------------------------------------------------------

                                mealMenuProductSizes.add(menuProductSize2);

                            }
                        }


                        mealProduct1.setMenuProductSize(mealMenuProductSizes);
                        mMealProducts.add(mealProduct1);
                    }
                }


            } while (cursor.moveToNext());
        }

        // close db connection

        ContentValues values = new ContentValues();
        if (mMenuProductSize != null) {
            values.put("menu_product_size", new Gson().toJson(mMenuProductSize));
        }

        if (productModifiers1 != null) {
            values.put("product_modifiers", new Gson().toJson(productModifiers1));
        }

        if (mMealProducts != null) {
            values.put("meal_products", new Gson().toJson(mMealProducts));
        }

        // updating row
        int id = db.update(TABLE_NAME_MENU_PRODUCT, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(columnId)});

        return id;
    }

    public int updateSpecialOfferQuantity(String offer_id, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("quantity", quantity);

        // updating row
        return db.update(TABLE_NAME_SPECIAL_OFFER, values, "offer_id = ?",
                new String[]{offer_id});
    }

    public int updateUpsellProductQuantity(String id, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("quantity", quantity);

        // updating row
        return db.update(TABLE_NAME_UPSELL_PRODUCT, values, "productId = ?",
                new String[]{id});
    }

    public void deleteUpsellProductItem(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_UPSELL_PRODUCT, "productId = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteUpsellProductByParentId(String paentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_UPSELL_PRODUCT, "id = ?",
                new String[]{String.valueOf(paentId)});
        db.close();
    }

    public void deleteItem(int categoryColumnId, int productColumnId) {
        SQLiteDatabase db = this.getWritableDatabase();
        /*db.delete(TABLE_NAME_MENU_CATEGORY2, COLUMN_ID + " = ?",
                new String[]{String.valueOf(categoryColumnId)});*/
        db.delete(TABLE_NAME_MENU_PRODUCT, COLUMN_ID + " = ?",
                new String[]{String.valueOf(productColumnId)});
        db.close();
    }

    public int getMenuProductColumnId(String menu_category_id, String menu_product_id) {
        int columnId = -1;
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_MENU_PRODUCT + " WHERE  menu_category_id ='" + menu_category_id + "' AND menu_product_id ='" + menu_product_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Gson gson = new Gson();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                columnId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return columnId;
    }

    public List<MenuProduct> getMenuProduct(String menu_category_id, String menu_product_id) {
        List<MenuProduct> menuProducts = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_MENU_PRODUCT + " WHERE  menu_category_id ='" + menu_category_id + "' AND menu_product_id ='" + menu_product_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Gson gson = new Gson();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                MenuProduct data = new MenuProduct();
                data.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_ID))));
                data.setMenuId(String.valueOf(cursor.getInt(cursor.getColumnIndex("menuId"))));
                data.setMenuProductId(cursor.getString(cursor.getColumnIndex("menu_product_id")));
                data.setProductName(cursor.getString(cursor.getColumnIndex("product_name")));
                data.setVegType(cursor.getString(cursor.getColumnIndex("veg_type")));
                data.setMenuProductPrice(cursor.getString(cursor.getColumnIndex("menu_product_price")));
                data.setUserappProductImage(cursor.getString(cursor.getColumnIndex("userapp_product_image")));
                data.setEcomProductImage(cursor.getString(cursor.getColumnIndex("ecom_product_image")));
                data.setEcomProductImage(cursor.getString(cursor.getColumnIndex("product_overall_rating")));
                data.setQuantity(cursor.getInt(cursor.getColumnIndex("quantity")));
                data.setOriginalQuantity(cursor.getInt(cursor.getColumnIndex("originalQuantity")));
                data.setOriginalAmount1(cursor.getDouble(cursor.getColumnIndex("originalAmount1")));
                data.setAmount(cursor.getString(cursor.getColumnIndex("amount")));

                List<MealProduct> mealProducts = gson.fromJson(cursor.getString(cursor.getColumnIndex("meal_products")), new TypeToken<List<MealProduct>>() {
                }.getType());
                data.setMealProducts(mealProducts);
                List<MenuProductSize> menuProductSize = gson.fromJson(cursor.getString(cursor.getColumnIndex("menu_product_size")), new TypeToken<List<MenuProductSize>>() {
                }.getType());
                data.setMenuProductSize(menuProductSize);
                List<ProductModifier> productModifiers = gson.fromJson(cursor.getString(cursor.getColumnIndex("product_modifiers")), new TypeToken<List<ProductModifier>>() {
                }.getType());
                data.setProductModifiers(productModifiers);
               /* UpSells upsells = gson.fromJson(cursor.getString(cursor.getColumnIndex("upsells")), new TypeToken<UpSells>() {
                }.getType());
                data.setUpsells(upsells);*/

                menuProducts.add(data);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return menuProducts;
    }

    public List<MenuProduct> getMenuProductByMenuId(String menuId, String menuSubCatId) {
        List<MenuProduct> menuProducts = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_MENU_PRODUCT + " WHERE  menuId ='" + menuId + "' AND menuSubCatId = '" + menuSubCatId + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Gson gson = new Gson();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                MenuProduct data = new MenuProduct();
                data.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_ID))));
                data.setMenuId(String.valueOf(cursor.getInt(cursor.getColumnIndex("menuId"))));
                data.setMenuId(String.valueOf(cursor.getInt(cursor.getColumnIndex("menuSubCatId"))));
                data.setMenuProductId(cursor.getString(cursor.getColumnIndex("menu_product_id")));
                data.setProductName(cursor.getString(cursor.getColumnIndex("product_name")));
                data.setVegType(cursor.getString(cursor.getColumnIndex("veg_type")));
                data.setMenuProductPrice(cursor.getString(cursor.getColumnIndex("menu_product_price")));
                data.setUserappProductImage(cursor.getString(cursor.getColumnIndex("userapp_product_image")));
                data.setEcomProductImage(cursor.getString(cursor.getColumnIndex("ecom_product_image")));
                data.setProductOverallRating(cursor.getString(cursor.getColumnIndex("product_overall_rating")));
                data.setQuantity(cursor.getInt(cursor.getColumnIndex("quantity")));
                data.setOriginalQuantity(cursor.getInt(cursor.getColumnIndex("originalQuantity")));
                data.setOriginalAmount(cursor.getDouble(cursor.getColumnIndex("originalAmount")));
                data.setOriginalAmount1(cursor.getDouble(cursor.getColumnIndex("originalAmount1")));
                data.setAmount(cursor.getString(cursor.getColumnIndex("amount")));

                List<MealProduct> mealProducts = gson.fromJson(cursor.getString(cursor.getColumnIndex("meal_products")), new TypeToken<List<MealProduct>>() {
                }.getType());
                data.setMealProducts(mealProducts);

                List<MenuProductSize> menuProductSize = gson.fromJson(cursor.getString(cursor.getColumnIndex("menu_product_size")), new TypeToken<List<MenuProductSize>>() {
                }.getType());
                data.setMenuProductSize(menuProductSize);
                List<ProductModifier> productModifiers = gson.fromJson(cursor.getString(cursor.getColumnIndex("product_modifiers")), new TypeToken<List<ProductModifier>>() {
                }.getType());
                data.setProductModifiers(productModifiers);
               /* UpSells upsells = gson.fromJson(cursor.getString(cursor.getColumnIndex("upsells")), new TypeToken<UpSells>() {
                }.getType());
                data.setUpsells(upsells);*/

                menuProducts.add(data);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return menuProducts;
    }

    public List<MenuProduct> getMenuProduct() {
        List<MenuProduct> menuProducts = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_MENU_PRODUCT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Gson gson = new Gson();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                MenuProduct data = new MenuProduct();
                data.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_ID))));
                data.setMenuId(String.valueOf(cursor.getInt(cursor.getColumnIndex("menuId"))));
                data.setMenuProductId(cursor.getString(cursor.getColumnIndex("menu_product_id")));
                data.setProductName(cursor.getString(cursor.getColumnIndex("product_name")));
                data.setVegType(cursor.getString(cursor.getColumnIndex("veg_type")));
                data.setMenuProductPrice(cursor.getString(cursor.getColumnIndex("menu_product_price")));
                data.setUserappProductImage(cursor.getString(cursor.getColumnIndex("userapp_product_image")));
                data.setEcomProductImage(cursor.getString(cursor.getColumnIndex("ecom_product_image")));
                data.setProductOverallRating(cursor.getString(cursor.getColumnIndex("product_overall_rating")));
                data.setQuantity(cursor.getInt(cursor.getColumnIndex("quantity")));
                data.setOriginalQuantity(cursor.getInt(cursor.getColumnIndex("originalQuantity")));
                data.setOriginalAmount1(cursor.getDouble(cursor.getColumnIndex("originalAmount1")));
                data.setAmount(cursor.getString(cursor.getColumnIndex("amount")));

                String catName = getCategoryName(Integer.parseInt(data.getMenuId()));

                if (catName.equalsIgnoreCase("Meal")) {

                    List<MealProduct> mealProducts = gson.fromJson(cursor.getString(cursor.getColumnIndex("meal_products")), new TypeToken<List<MealProduct>>() {
                    }.getType());
                    data.setMealProducts(mealProducts);

                } else {
                    List<MealProduct> mealProducts = gson.fromJson(cursor.getString(cursor.getColumnIndex("meal_products")), new TypeToken<List<MealProduct>>() {
                    }.getType());
                    data.setMealProducts(mealProducts);

                    List<MenuProductSize> menuProductSize = gson.fromJson(cursor.getString(cursor.getColumnIndex("menu_product_size")), new TypeToken<List<MenuProductSize>>() {
                    }.getType());
                    data.setMenuProductSize(menuProductSize);
                    List<ProductModifier> productModifiers = gson.fromJson(cursor.getString(cursor.getColumnIndex("product_modifiers")), new TypeToken<List<ProductModifier>>() {
                    }.getType());
                    data.setProductModifiers(productModifiers);
                }


              /*  UpSells upsells = gson.fromJson(cursor.getString(cursor.getColumnIndex("upsells")), new TypeToken<UpSells>() {
                }.getType());
                data.setUpsells(upsells);*/

                menuProducts.add(data);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return menuProducts;
    }

    public int getMenuProductCount(String menu_category_id, String menu_product_id) {
        String countQuery = "SELECT  * FROM " + TABLE_NAME_MENU_PRODUCT + " WHERE  menu_category_id ='" + menu_category_id + "' AND menu_product_id ='" + menu_product_id + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }


    public List<MenuCategoryCart> getMenuCategory() {
        List<MenuCategoryCart> cart = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_MENU_CATEGORY2;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Gson gson = new Gson();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MenuCategoryCart data = new MenuCategoryCart();
                data.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                data.setMenuCategoryId(cursor.getString(cursor.getColumnIndex("menu_category_id")));
                data.setMenuCategoryName(cursor.getString(cursor.getColumnIndex("menu_category_name")));

                /*List<MenuCategoryCart> menuSubCategory = gson.fromJson(cursor.getString(cursor.getColumnIndex("menu_sub_category")), new TypeToken<List<MenuCategoryCart>>() {
                }.getType());*/

                data.setMenuSubCategory(getMenuSubCategory(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));

                List<MenuProduct> menuProducts = getMenuProductByMenuId(cursor.getString(cursor.getColumnIndex(COLUMN_ID)), "-1");
                data.setMenuProducts(menuProducts);

                cart.add(data);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return cart;
    }

    public List<MenuCategoryCart> getMenuSubCategory(String menuId) {
        List<MenuCategoryCart> cart = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_MENU_SUB_CATEGORY + " WHERE  menuId ='" + menuId + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Gson gson = new Gson();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MenuCategoryCart data = new MenuCategoryCart();
                data.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                data.setMenuId(cursor.getInt(cursor.getColumnIndex("menuId")));
                data.setMenuCategoryId(cursor.getString(cursor.getColumnIndex("menu_category_id")));
                data.setMenuCategoryName(cursor.getString(cursor.getColumnIndex("menu_category_name")));

                /*List<MenuCategoryCart> menuSubCategory = gson.fromJson(cursor.getString(cursor.getColumnIndex("menu_sub_category")), new TypeToken<List<MenuCategoryCart>>() {
                }.getType());*/
                data.setMenuSubCategory(new ArrayList<MenuCategoryCart>());

                List<MenuProduct> menuProducts = getMenuProductByMenuId(cursor.getString(cursor.getColumnIndex("menuId")), cursor.getString(cursor.getColumnIndex(COLUMN_ID)));

                data.setMenuProducts(menuProducts);

                cart.add(data);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return cart;
    }


    public void deleteCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_UPSELL_PRODUCT, null, null);
        db.delete(TABLE_NAME_SPECIAL_OFFER, null, null);
        db.delete(TABLE_NAME_MENU_CATEGORY2, null, null);
        db.delete(TABLE_NAME_MENU_SUB_CATEGORY, null, null);
        db.delete(TABLE_NAME_MENU_PRODUCT, null, null);
        db.close();
    }

    public Boolean deleteData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_UPSELL_PRODUCT, null, null);
        db.delete(TABLE_NAME_SPECIAL_OFFER, null, null);
        db.delete(TABLE_NAME_MENU_CATEGORY2, null, null);
        db.delete(TABLE_NAME_MENU_SUB_CATEGORY, null, null);
        db.delete(TABLE_NAME_MENU_PRODUCT, null, null);
        db.close();
        return true;
    }

}