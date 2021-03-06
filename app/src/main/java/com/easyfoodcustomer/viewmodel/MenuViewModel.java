package com.easyfoodcustomer.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import androidx.annotation.NonNull;

import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.Menu;
import com.easyfoodcustomer.utility.GlobalValues;

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
