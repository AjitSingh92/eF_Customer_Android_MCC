package com.lexxdigital.easyfooduserapps.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.lexxdigital.easyfooduserapps.cart_db.tables.MenuProducts;
import com.lexxdigital.easyfooduserapps.utility.GlobalValues;

public class MenuProductViewModel extends AndroidViewModel {


    public MenuProductViewModel(@NonNull Application application) {
        super(application);

    }

    public LiveData<MenuProducts.MenuProductsTable> getMenuProductList(String categoryId) {
        return GlobalValues.getInstance().getDb().menuProductMaster().getMenuProductList(categoryId);
    }

    public void insertMenu(MenuProducts.MenuProductsTable menuProduct) {
        new insertAsyncTask(menuProduct).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Void, Void, Void> {
        MenuProducts.MenuProductsTable menuProduct;

        public insertAsyncTask(MenuProducts.MenuProductsTable menuProduct) {
            this.menuProduct = menuProduct;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Long id = GlobalValues.getInstance().getDb().menuProductMaster().insert(menuProduct);

            return null;
        }
    }
}
