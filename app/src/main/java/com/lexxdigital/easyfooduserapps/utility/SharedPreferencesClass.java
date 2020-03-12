package com.lexxdigital.easyfooduserapps.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.lexxdigital.easyfooduserapps.login.model.response.Data;

public class SharedPreferencesClass {

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public Context mContext;
    GlobalValues val;

    public static final String SHARED_PREF_NAME = "SHARED_PREF_NAME";
    public static final String CartDetailsKey = "cartDetailsKey";
    public static final String PreRestaurantIDKey = "preRestaurantIDKey";
    public static final String CartRestaurantDeatilKey = "cartRestaurantDeatilKey";
    public static final String postalCode = "postalcode";
    public static final String introSlideHideKEY = "introSlideHideKEY";
    public static final String loginKEY = "loginKEY";
    public static final String LoginResponseKey = "LoginResponseKey";
    public static final String OrderIDKey = "orderIDKey";
    public static final String IS_FB_TOKEN_UPDATE = "IS_FB_TOKEN_UPDATE";
    public static final String FB_TOKEN_ID = "FB_TOKEN_ID";
    public static final String NOTIFICATION_TYPE = "NOTIFICATION_TYPE";
    public static final String USER_ID = "USER_ID";
    public static final String RESTUARANT_ID = "RESTUARANT_ID";
    public static final String RESTUARANT_NAME = "RESTUARANT_NAME";
    public static final String PAYMENT_MODE = "PAYMENT_MODE";
    public static final String NOTEPAD = "NOTEPAD";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_PROFILE_IMAGE = "USER_PROFILE_IMAGE";
    public static final String LOGIN_VIA = "LOGIN_VIA";
    public static final String NUMBER_OF_ORDERS = "NUMBER_OF_ORDERS";
    public static final String DELIVERY_ADDRESS_ID = "DELIVERY_ADDRESS_ID";
    public static final String DEFAULT_ADDRESS = "DEFAULT_ADDRESS";
    public static final String BILLING_ADDRESS = "BILLING_ADDRESS";
    public static final String RESTAURANT_NAME_SLUG = "RESTAURANT_NAME_SLUG";
    public static final String DELIVERY_DATE_TIME = "DELIVERY_DATE_TIME";
    public static final String IS_TOMORROW = "IS_TOMORROW";
    public static final String AVG_COLLECTION_TIME = "AVG_COLLECTION_TIME";
    public static final String ORDER_TYPE = "ORDER_TYPE";
    public static final String CUSTOMER_ID = "CUSTOMER_ID";


   /* public static final String OFFERR_ID_DFG            = "OFFERR_ID_DFG";
    public static final String RESTUARANT_ID_DFG        = "RESTUARANT_ID_DFG";
    public static final String OFFERR_TYPE_DFG          = "OFFERR_TYPE_DFG";
    public static final String OFFERR_TITLE_DFG         = "OFFERR_TITLE_DFG";*/
    public static final String OFFERR_DETAL_DFG         = "OFFERR_DETAL_DFG";
  /*  public static final String OFFERR_PRICE_LEBEL_DFG  = "OFFERR_PRICE_LEBEL_DFG";
    public static final String OFFERR_PRICE_DFG         = "OFFERR_PRICE_DFG";
*/

    public SharedPreferencesClass(Context mContext) {
        this.mContext = mContext;
    }

    public void initPreferences() {
        sharedpreferences = mContext.getSharedPreferences(CartDetailsKey, mContext.MODE_PRIVATE);
        sharedpreferences = mContext.getSharedPreferences(PreRestaurantIDKey, mContext.MODE_PRIVATE);
        sharedpreferences = mContext.getSharedPreferences(CartRestaurantDeatilKey, mContext.MODE_PRIVATE);
        sharedpreferences = mContext.getSharedPreferences(postalCode, mContext.MODE_PRIVATE);
        sharedpreferences = mContext.getSharedPreferences(introSlideHideKEY, mContext.MODE_PRIVATE);
        sharedpreferences = mContext.getSharedPreferences(loginKEY, mContext.MODE_PRIVATE);
        sharedpreferences = mContext.getSharedPreferences(OrderIDKey, mContext.MODE_PRIVATE);
    }

    private static SharedPreferences getUserSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setCartDetailsKey(String json) {
        initPreferences();
        editor = sharedpreferences.edit();
        editor.putString(CartDetailsKey, json);
        editor.commit();
        editor.apply();
    }

    public String getCartDetailsKey() {
        initPreferences();
        String str = sharedpreferences.getString(CartDetailsKey, null);

        return str;
    }

    public void clearCartDetailsKey() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(CartDetailsKey);
        editor.commit();
        editor.apply();
    }

    public void clearDataAfterChechout() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(CartDetailsKey);
        editor.remove(NOTEPAD);
        editor.commit();
        editor.apply();
    }

    public void setInt(String key, int value) {
        SharedPreferences.Editor editor = getUserSharedPreferences(mContext).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(String key) {
        return getUserSharedPreferences(mContext).getInt(key, 0);
    }

    public void setOrderIDKey(String id) {
        initPreferences();
        editor = sharedpreferences.edit();
        editor.putString(OrderIDKey, id);
        editor.commit();
        editor.apply();
    }

    public String getOrderIDKey() {
        initPreferences();
        String id = sharedpreferences.getString(OrderIDKey, null);

        return id;
    }


    public void setCartRestaurantDeatilKey(String json) {
        initPreferences();
        editor = sharedpreferences.edit();
        editor.putString(CartRestaurantDeatilKey, json);
        editor.commit();
        editor.apply();
    }

    public String getCartRestaurantDeatilKey() {
        initPreferences();
        String str = sharedpreferences.getString(CartRestaurantDeatilKey, null);

        return str;
    }

    public void setPreRestaurantIDKey(String json) {
        initPreferences();
        editor = sharedpreferences.edit();
        editor.putString(PreRestaurantIDKey, json);
        editor.commit();
        editor.apply();
    }

    public String getPreRestaurantIDKey() {
        initPreferences();
        String str = sharedpreferences.getString(PreRestaurantIDKey, null);
        return str;
    }

    public void setPostalCode(String postal) {
        initPreferences();
        editor = sharedpreferences.edit();
        editor.putString(postalCode, postal);
        editor.commit();
        editor.apply();
    }

    public String getPostalCode() {
        initPreferences();
        String retPostal = sharedpreferences.getString(postalCode, null);
        return retPostal;
    }

    public void setloginpref(String login) {
        initPreferences();
        editor = sharedpreferences.edit();
        editor.putString(loginKEY, login);
        editor.commit();
        editor.apply();
    }

    public String isloginpref() {
        initPreferences();
        String loginkey = sharedpreferences.getString(loginKEY, null);
        return loginkey;
    }

    public void setFirstTimeLaunch(String isFirstTime) {
        initPreferences();
        editor = sharedpreferences.edit();
        editor.putString(introSlideHideKEY, isFirstTime);
        editor.commit();
        editor.apply();
    }

    public String isFirstTimeLaunch() {
        initPreferences();
        return sharedpreferences.getString(introSlideHideKEY, null);
    }

    public void clear(String key) {
        sharedpreferences = mContext.getSharedPreferences(key, mContext.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.remove(key);
        editor.clear();
        editor.apply();
    }

    public void logout() {
        initPreferences();
        String lonch = isFirstTimeLaunch();
        SharedPreferences sharedpreferences1 = mContext.getSharedPreferences(SHARED_PREF_NAME, mContext.MODE_PRIVATE);
        editor = sharedpreferences1.edit();
        editor.clear();
        editor.apply();

        SharedPreferences sharedpreferences2 = mContext.getSharedPreferences(OrderIDKey, mContext.MODE_PRIVATE);
        editor = sharedpreferences2.edit();
        editor.clear();
        editor.apply();

        SharedPreferences sharedpreferences3 = mContext.getSharedPreferences(RESTUARANT_ID, mContext.MODE_PRIVATE);
        editor = sharedpreferences3.edit();
        editor.clear();
        editor.apply();
        SharedPreferences sharedpreferences4 = mContext.getSharedPreferences(RESTUARANT_NAME, mContext.MODE_PRIVATE);
        editor = sharedpreferences4.edit();
        editor.clear();
        editor.apply();

        SharedPreferences sharedpreferences5 = mContext.getSharedPreferences(FB_TOKEN_ID, mContext.MODE_PRIVATE);
        editor = sharedpreferences5.edit();
        editor.clear();
        editor.apply();

        SharedPreferences sharedpreferences6 = mContext.getSharedPreferences(DEFAULT_ADDRESS, mContext.MODE_PRIVATE);
        editor = sharedpreferences6.edit();
        editor.clear();
        editor.apply();

        setFirstTimeLaunch(lonch);

    }

    public void setString(String key, String value) {
        SharedPreferences.Editor editor = getUserSharedPreferences(mContext).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        return getUserSharedPreferences(mContext).getString(key, null);
    }

    public void setObject(String key, String value) {
        SharedPreferences.Editor editor = getUserSharedPreferences(mContext).edit();
//        editor.putString(key, new Gson().toJson(value));
        editor.putString(key, value);
        editor.commit();
    }

    public Object getObject(String key, final Class<?> aClass) {
        return new Gson().fromJson(getString(key), aClass);
    }

    public void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getUserSharedPreferences(mContext).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key) {
        return getUserSharedPreferences(mContext).getBoolean(key, false);
    }



    public void setLoginResponseDataKey(Data resp) {
        initPreferences();
        editor = sharedpreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(resp);
        editor.putString(LoginResponseKey, json);
        editor.commit();
        editor.apply();
    }

    public Data getLoginResponseDataKey() {
        initPreferences();
        val = (GlobalValues) mContext;
        Gson gson = new Gson();
        String json = sharedpreferences.getString(LoginResponseKey, "");
        Data objResp = gson.fromJson(json, Data.class);
        //val.setLoginUserMobile(objResp.getHostDescription().getMobile());
        //val.setLoginResponse(objResp);
        Log.e("", "getLoginResponseDataKey: " + objResp);

        val.setLoginResponseData(objResp);
        Log.e("", "getLoginResponseDataKey: " + objResp.toString());
        return objResp;
    }
}