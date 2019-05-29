package com.lexxdigital.easyfooduserapp.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.adapters.menu_adapter.ItemClickListener;
import com.lexxdigital.easyfooduserapp.adapters.menu_adapter.MealProductAdapter;
import com.lexxdigital.easyfooduserapp.adapters.menu_adapter.MealProductCategoryAdapter;
import com.lexxdigital.easyfooduserapp.adapters.menu_adapter.OnMealProductItemSelect;
import com.lexxdigital.easyfooduserapp.adapters.menu_adapter.RecyclerLayoutManager;
import com.lexxdigital.easyfooduserapp.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MealProduct;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MenuProductSize;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.Modifier;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.ProductModifier;

import java.util.ArrayList;
import java.util.List;

public class MenuMealDialog extends DialogFragment implements View.OnClickListener, OnMealProductItemSelect {
    private DatabaseHelper db;
    Context context;
    ItemClickListener itemClickListener;
    int parentPosition;
    int childPosition;
    int action;
    View view;
    RecyclerView listMealProductCategory;
    RecyclerLayoutManager layoutManager;
    MealProductCategoryAdapter mealProductCategoryAdapter;
    TextView item_count;
    int itemCount;
    MenuCategory menuCategory;
    Gson gson = new Gson();

    TextView totalPriceView, categoryName;
    TextView tvBasePrice, tvAmountToPay;
    View qtyLayout;
    Boolean isSubCat;
    int childParentPosition;
    int selectedChildPosition;

    TextView validationError;
    Boolean openOnClick;
    public static MenuMealDialog newInstance(Context context,Boolean openOnClick, int childParentPosition, int selectedChildPosition, int parentPosition, int childPosition, View qtyLayout, TextView item_count, int itemCount, int action, MenuCategory menuCategory, Boolean isSubCat, ItemClickListener itemClickListener) {
        MenuMealDialog c = new MenuMealDialog();
        c.context = context;
        c.openOnClick = openOnClick;
        c.childParentPosition = childParentPosition;
        c.selectedChildPosition = selectedChildPosition;
        c.parentPosition = parentPosition;
        c.childPosition = childPosition;
        c.item_count = item_count;
        c.itemCount = itemCount;
        c.action = action;
        c.qtyLayout = qtyLayout;
        c.menuCategory = menuCategory;
        c.isSubCat = isSubCat;
        c.itemClickListener = itemClickListener;
        return c;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.popup_meal_select_items, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        validationError = view.findViewById(R.id.tv_validationError);
        totalPriceView = view.findViewById(R.id.total_price);
        tvBasePrice = view.findViewById(R.id.tv_BasePrice);
        tvAmountToPay = view.findViewById(R.id.tv_AmountToPay);

        view.findViewById(R.id.sign_up_btn_dialog).setOnClickListener(this);
        view.findViewById(R.id.cross_tv).setOnClickListener(this);

        categoryName = view.findViewById(R.id.txt_category);
//        categoryName.setText(menuCategory.getMenuCategoryName());
//        if (menuCategory.getMeal().get(childPosition) != null)

        categoryName.setText(menuCategory.getMeal().get(childPosition).getMealName());
        tvBasePrice.setText("Base Price\n" + context.getResources().getString(R.string.currency) + String.format("%.2f", Double.parseDouble(menuCategory.getMeal().get(childPosition).getMealPrice())));
        tvAmountToPay.setText("Amount to pay\n" + context.getResources().getString(R.string.currency) + String.format("%.2f", Double.parseDouble(menuCategory.getMeal().get(childPosition).getMealPrice())));

        listMealProductCategory = view.findViewById(R.id.list_meal_category);
        layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
        layoutManager.setScrollEnabled(false);
        listMealProductCategory.setLayoutManager(layoutManager);
        mealProductCategoryAdapter = new MealProductCategoryAdapter(context,openOnClick, getDialog(), parentPosition, childPosition, qtyLayout, item_count, itemCount, action, menuCategory, isSubCat, itemClickListener, this);
        listMealProductCategory.setAdapter(mealProductCategoryAdapter);

        mealProductCategoryAdapter.addItem(menuCategory.getMeal().get(childPosition).getMealCategories());
        /*if (childParentPosition!=-1){
//            menuCategory.getMeal().get(parentPosition).getMealCategories().get(childPosition).getMealProducts().get(selectedChildPosition).setMenuProductSize(productSizeAdapter.getSelectedItem());

//            menuCategory.getMeal().get(parentPosition).getMealCategories().get(childPosition).getMealProducts().get(selectedChildPosition).setMenuProductSize(productSizeAdapter.getSelectedItem());
            mealProductCategoryAdapter.addItem(menuCategory.getMeal().get(parentPosition).getMealCategories());

        }else{

        }*/

        updatePrice();
    }

    @Override
    public void onResume() {
        super.onResume();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(getDialog().getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.95f);
        int dialogWindowHeight = (int) (displayHeight * 0.95f);
        layoutParams.width = dialogWindowWidth;
        getDialog().getWindow().setAttributes(layoutParams);

        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cross_tv:
                dismiss();
                break;
            case R.id.sign_up_btn_dialog:
                List<MenuProduct> menuProducts = new ArrayList<>();
                long id = db.getMenuCategoryIfExit(menuCategory.getMenuCategoryId());
                if (id == -1) {
                    id = db.insertMenuCategory(menuCategory.getMenuCategoryId(), menuCategory.getMenuCategoryName(), gson.toJson(menuCategory.getMenuSubCategory()), gson.toJson(menuProducts));
                }

                long subCatId = -1;

                List<MealProductAdapter> mealProductAdapters = mealProductCategoryAdapter.getMealProductAdapters();
                for (int i = 0; i < mealProductAdapters.size(); i++) {
                    if (mealProductCategoryAdapter.getCustomizableQuantity(i) != -1 && mealProductCategoryAdapter.getCustomizableQuantity(i) > mealProductAdapters.get(i).getSelectedItem().size()) {
                        validationError.setVisibility(View.VISIBLE);
                        validationError.setText("Choose " + (mealProductCategoryAdapter.getCustomizableQuantity(i) - mealProductAdapters.get(i).getSelectedItem().size()) + " more product(s) in " + mealProductCategoryAdapter.getCategoryName(i));
                        return;
                    }
                }

                  /*  db.insertMenuProduct(id, subCatId, menuCategory.getMenuCategoryId(),
                            menuCategory.getMeal().get(parentPosition).getMealId(),
                            menuCategory.getMeal().get(parentPosition).getMealName(),
                            menuCategory.getMeal().get(parentPosition).getVegType(),
                            menuCategory.getMeal().get(parentPosition).getMealPrice(),
                            "",
                            "",
                            "",
                            1,
                            gson.toJson(mealProductAdapters.get(i).getSelectedItem()),
                            gson.toJson(mealProductAdapters.get(i).getSelectedItem()),
                            1,
                            Double.parseDouble(menuCategory.getMeal().get(parentPosition).getMealPrice()),
                            menuCategory.getMeal().get(parentPosition).getMealPrice());*/

                List<MealProduct> mealProducts = new ArrayList<>();
                for (int i = 0; i < mealProductAdapters.size(); i++) {
                    mealProducts.addAll(mealProductAdapters.get(i).getSelectedItem());
                }

                db.insertMenuProduct(id, subCatId, menuCategory.getMenuCategoryId(),
                        menuCategory.getMeal().get(childPosition).getMealId(),
                        menuCategory.getMeal().get(childPosition).getMealName(),
                        menuCategory.getMeal().get(childPosition).getVegType(),
                        menuCategory.getMeal().get(childPosition).getMealPrice(),
                        "",
                        "",
                        "",
                        1,
                        null,
                        null,
                        gson.toJson(mealProducts),
                        1,
                        Double.parseDouble(menuCategory.getMeal().get(childPosition).getMealPrice()),
                        menuCategory.getMeal().get(childPosition).getMealPrice());


                if (itemClickListener != null) {
                    itemClickListener.OnAddItem(parentPosition, childPosition, qtyLayout, item_count, 1, action, menuCategory);
                }

                dismiss();

                break;
        }

    }

    private void updatePrice() {
        validationError.setVisibility(View.GONE);

        if (mealProductCategoryAdapter != null) {

            List<MealProductAdapter> mealProductAdapters = mealProductCategoryAdapter.getMealProductAdapters();
            Double totalPrice = Double.parseDouble(menuCategory.getMeal().get(childPosition).getMealPrice());

            for (MealProductAdapter adapter : mealProductAdapters) {
                List<MealProduct> mealProducts = adapter.getSelectedItem();

                if (mealProducts != null) {
                    for (int i = 0; i < mealProducts.size(); i++) {

                        if (mealProducts.get(i).getMenuProductSize() != null) {
                            totalPrice += Double.parseDouble(mealProducts.get(i).getMenuProductSize().get(0).getAmount());
                            Log.e("aklesh>>", i + ":" + mealProducts.get(i).getMenuProductSize().get(0).getAmount());
                        }

                    }
                }

            }

           /* for (MealProductAdapter adapter : mealProductAdapters) {
                List<MealProduct> mealProducts = adapter.getSelectedItem();

                if (mealProducts != null) {
                    for (int i = 0; i < mealProducts.size(); i++) {

                        if (mealProducts.get(i).getMenuProductSize() != null) {
                            for (int j = 0; j < mealProducts.get(i).getMenuProductSize().get(0).getSizeModifiers().size(); j++) {

                                for (int k = 0; k < mealProducts.get(i).getMenuProductSize().get(0).getSizeModifiers().get(j).getModifier().size(); k++) {

                                    totalPrice += Double.parseDouble(mealProducts.get(i).getMenuProductSize().get(0).getSizeModifiers().get(j).getModifier().get(k).getOriginalQuantity()) * Double.parseDouble(mealProducts.get(i).getMenuProductSize().get(0).getSizeModifiers().get(j).getModifier().get(k).getModifierProductPrice());

                                }
                            }
                        }

                    }
                }

            }*/
            totalPriceView.setText(String.format("%.2f", totalPrice));
        }

    }

    @Override
    public void OnMealProductItemSelect(Boolean isSelected) {
        updatePrice();

    }
}