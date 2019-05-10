package com.lexxdigital.easyfooduserapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.lexxdigital.easyfooduserapp.cart_db.MenuDataBase;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.Menu;
import com.lexxdigital.easyfooduserapp.utility.GlobalValues;

import java.util.List;

public class MenuViewModel extends AndroidViewModel {


    public MenuViewModel(@NonNull Application application) {
        super(application);

    }

    public LiveData<Menu> getMenuCategoryList(String restaurantId) {
        return GlobalValues.getInstance().getDb().menuMaster().getMenuCategoryList(restaurantId);
    }

    public void insertMenu(Menu menus) {
        new insertAsyncTask(menus).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Void, Void, Void> {
        Menu menus;

        public insertAsyncTask(Menu menus) {
            this.menus = menus;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            GlobalValues.getInstance().getDb().menuMaster().insert(menus);

            return null;
        }
    }
}
