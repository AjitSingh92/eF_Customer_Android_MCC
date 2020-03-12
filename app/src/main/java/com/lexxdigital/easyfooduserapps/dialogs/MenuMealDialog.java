package com.lexxdigital.easyfooduserapps.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.adapters.menu_adapter.ItemClickListener;
import com.lexxdigital.easyfooduserapps.adapters.menu_adapter.MealProductAdapter;
import com.lexxdigital.easyfooduserapps.adapters.menu_adapter.MealProductCategoryAdapter;
import com.lexxdigital.easyfooduserapps.adapters.menu_adapter.OnMealProductItemSelect;
import com.lexxdigital.easyfooduserapps.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MealCategory;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MealProduct;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.Modifier;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.SizeModifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuMealDialog extends DialogFragment implements View.OnClickListener, OnMealProductItemSelect, MealProductCategoryAdapter.scrollToPosition {
    private DatabaseHelper db;
    private static MenuMealDialog menuMealDialog;
    Context context;
    ItemClickListener itemClickListener;
    int parentPosition;
    int childPosition;
    int action;
    View view;
    public RecyclerView listMealProductCategory;
    public MealProductCategoryAdapter mealProductCategoryAdapter;
    TextView item_count;
    int itemCount;
    MenuCategory menuCategory;
    Gson gson = new Gson();
    RecyclerView.SmoothScroller smoothScroller;
    TextView totalPriceView, categoryName;
    TextView tvBasePrice, tvAmountToPay;
    View qtyLayout;
    Boolean isSubCat;
    int childParentPosition;
    int selectedChildPosition;

    TextView validationError;
    Boolean openOnClick;
    FirebaseAnalytics mFirebaseAnalytics;

    public static MenuMealDialog getInstance() {
        return menuMealDialog;
    }

    private Boolean enableScrollToPosition = false;

    public static MenuMealDialog newInstance(Context context, Boolean openOnClick, int childParentPosition, int selectedChildPosition, int parentPosition, int childPosition, View qtyLayout, TextView item_count, int itemCount, int action, MenuCategory menuCategory, Boolean isSubCat, Boolean enableScrollToPosition, ItemClickListener itemClickListener) {
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
        c.enableScrollToPosition = enableScrollToPosition;
        c.itemClickListener = itemClickListener;
        menuMealDialog = c;

        return c;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(context);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        if (menuMealDialog == null) {
            menuMealDialog = this;
        }
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
        categoryName.setText(menuCategory.getMeal().get(childPosition).getMealName());
        String title = null;
        for (int i = 0; i < menuCategory.getMeal().get(childPosition).getMealCategories().size(); i++) {
            MealCategory mItem = menuCategory.getMeal().get(childPosition).getMealCategories().get(i);
            if (title == null) {
                title = mItem.getQuantity() + " " + mItem.getCategoryName();
            } else {
                title += ", " + mItem.getQuantity() + " " + mItem.getCategoryName();
            }
        }
        tvBasePrice.setText(title);

        tvAmountToPay.setText("Amount to pay\n" + context.getResources().getString(R.string.currency) + String.format("%.2f", Double.parseDouble(menuCategory.getMeal().get(childPosition).getMealPrice())));

        listMealProductCategory = view.findViewById(R.id.list_meal_category);

        listMealProductCategory.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        if (!enableScrollToPosition) {
            for (int i = 0; i < menuCategory.getMeal().get(childPosition).getMealCategories().size(); i++) {
                for (int j = 0; j < menuCategory.getMeal().get(childPosition).getMealCategories().get(i).getMealProducts().size(); j++) {
                    if (menuCategory.getMeal().get(childPosition).getMealCategories().get(i).getMealProducts().get(j).isSelected) {
                        menuCategory.getMeal().get(childPosition).getMealCategories().get(i).getMealProducts().get(j).setSelected(false);
                    }
                }
            }
        }

        mealProductCategoryAdapter = new MealProductCategoryAdapter(context, this, openOnClick, getDialog(), parentPosition, childPosition, qtyLayout, item_count, itemCount, action, menuCategory, isSubCat, itemClickListener, this);
        listMealProductCategory.setAdapter(mealProductCategoryAdapter);
        mealProductCategoryAdapter.setEnableScrollToPosition(enableScrollToPosition);
        mealProductCategoryAdapter.addItem(menuCategory.getMeal().get(childPosition).getMealCategories());
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

                HashMap<Integer, MealProductAdapter> mealProductAdapters = mealProductCategoryAdapter.getMealProductAdapters();


                for (int i = 0; i < mealProductAdapters.size(); i++) {
                    if (mealProductCategoryAdapter.getCustomizableQuantity(i) != -1 && 1 > mealProductAdapters.get(i).getSelectedItem().size()) {
                        validationError.setVisibility(View.VISIBLE);
                        validationError.setText("Choose " + (mealProductCategoryAdapter.getCustomizableQuantity(i) - mealProductAdapters.get(i).getSelectedItem().size()) + " more product(s) in " + mealProductCategoryAdapter.getCategoryName(i));
                        return;
                    }
                }

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

            HashMap<Integer, MealProductAdapter> mealProductAdapters = mealProductCategoryAdapter.getMealProductAdapters();
            Double totalPrice = Double.parseDouble(menuCategory.getMeal().get(childPosition).getMealPrice());

            for (Map.Entry<Integer, MealProductAdapter> data : mealProductAdapters.entrySet()) {
                MealProductAdapter adapter = data.getValue();
                List<MealProduct> mealProducts = adapter.getSelectedItem();

                if (mealProducts != null) {
                    for (int i = 0; i < mealProducts.size(); i++) {

                        if (mealProducts.get(i).getMenuProductSize() != null) {
                            //totalPrice += Double.parseDouble(mealProducts.get(i).getMenuProductSize().get(0).getProductSizePrice());
                            if (mealProducts.get(i).getMenuProductSize().get(0).getSizeModifiers() != null) {
                                for (SizeModifier sizeModifier : mealProducts.get(i).getMenuProductSize().get(0).getSizeModifiers()) {
                                    if (sizeModifier.getMaxAllowedQuantity() != 1) {
                                        for (Modifier modifier : sizeModifier.getModifier()) {
                                            totalPrice += (Double.parseDouble(modifier.getModifierProductPrice()) * Double.parseDouble(modifier.getOriginalQuantity()));
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
            totalPriceView.setText(String.format("%.2f", totalPrice));
        }

    }

    @Override
    public void OnMealProductItemSelect(Boolean isSelected) {
        updatePrice();

    }

    @Override
    public void onScrollPosition(int position) {
        if (mealProductCategoryAdapter != null)
            listMealProductCategory.scrollToPosition(position);
    }
}