package com.lexxdigital.easyfooduserapps.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;

import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.adapters.menu_adapter.ItemClickListener;
import com.lexxdigital.easyfooduserapps.adapters.menu_adapter.OnProductModifierSelected;
import com.lexxdigital.easyfooduserapps.adapters.menu_adapter.ProductModifierAdapter;
import com.lexxdigital.easyfooduserapps.adapters.menu_adapter.ProductSizeAdapter;
import com.lexxdigital.easyfooduserapps.adapters.menu_adapter.RecyclerLayoutManager;
import com.lexxdigital.easyfooduserapps.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuProductSize;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.Modifier;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.ProductModifier;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.SizeModifier;

import java.util.ArrayList;
import java.util.List;

public class MenuDialogNew extends DialogFragment implements View.OnClickListener, OnProductModifierSelected {
    private DatabaseHelper db;
    Context context;
    ItemClickListener itemClickListener;
    int parentPosition;
    int childPosition;
    MenuCategory subCategory;
    int action;
    View view;

    RecyclerView productModifierView;
    RecyclerLayoutManager productModifierLayoutManager;
    ProductModifierAdapter productModifierAdapter;

    RecyclerView productSizeListView;
    RecyclerLayoutManager productSizeLayoutManager;
    ProductSizeAdapter productSizeAdapter;
    TextView item_count;
    int itemCount;
    MenuCategory menuCategory;
    Gson gson = new Gson();

    TextView totalPriceView, categoryName;
    View qtyLayout;
    Boolean isSubCat;
    LinearLayout llprice;
    FirebaseAnalytics mFirebaseAnalytics;

    public static MenuDialogNew newInstance(Context context, int parentPosition, int childPosition, View qtyLayout, TextView item_count, int itemCount, int action, MenuCategory menuCategory, Boolean isSubCat, ItemClickListener itemClickListener) {
        MenuDialogNew c = new MenuDialogNew();
        c.context = context;
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
        View v = inflater.inflate(R.layout.popup_select_items, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        totalPriceView = view.findViewById(R.id.total_price);
        llprice = (LinearLayout) view.findViewById(R.id.llprice);

        view.findViewById(R.id.sign_up_btn_dialog).setOnClickListener(this);
        view.findViewById(R.id.cross_tv).setOnClickListener(this);

        categoryName = view.findViewById(R.id.txt_category);
        if (menuCategory.getMenuProducts().get(childPosition) != null)
            categoryName.setText(menuCategory.getMenuProducts().get(childPosition).getProductName());

        productSizeListView = view.findViewById(R.id.what_size_type_list);
        productSizeLayoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
        productSizeLayoutManager.setScrollEnabled(false);
        productSizeListView.setLayoutManager(productSizeLayoutManager);
        productSizeAdapter = new ProductSizeAdapter(context, this);
        productSizeListView.setAdapter(productSizeAdapter);
        if (isSubCat) {
            productSizeAdapter.addItem(menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getMenuProductSize());

        } else {
            productSizeAdapter.addItem(menuCategory.getMenuProducts().get(childPosition).getMenuProductSize());
        }
        if (productSizeAdapter.getItemCount() > 1) {
            view.findViewById(R.id.size_lable).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.size_lable).setVisibility(View.GONE);
        }


        productModifierView = view.findViewById(R.id.product_modifier);
        productModifierLayoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
        productModifierLayoutManager.setScrollEnabled(false);
        productModifierView.setLayoutManager(productModifierLayoutManager);
        productModifierAdapter = new ProductModifierAdapter(context, parentPosition, this);
        productModifierView.setAdapter(productModifierAdapter);
        if (isSubCat) {
            productModifierAdapter.addItem(menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getProductModifiers());
        } else {
            productModifierAdapter.addItem(menuCategory.getMenuProducts().get(childPosition).getProductModifiers());
        }
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
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        updatePrice(false);
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
                if (productSizeAdapter != null) {
                    if (productSizeAdapter.getItemCount() > 1) {
                        if (productSizeAdapter.getSelectedItem(false).size() != 0) {

                            List<MenuProduct> menuProducts = new ArrayList<>();
                            long id = db.getMenuCategoryIfExit(menuCategory.getMenuCategoryId());
                            if (id == -1) {
                                id = db.insertMenuCategory(menuCategory.getMenuCategoryId(), menuCategory.getMenuCategoryName(), gson.toJson(menuCategory.getMenuSubCategory()), gson.toJson(menuProducts));
                            }
                            long subCatId = -1;
                            if (isSubCat) {
                                subCatId = db.getMenuSubCategoryIfExit(menuCategory.getMenuSubCategory().get(parentPosition).getMenuCategoryId());
                                if (subCatId == -1) {
                                    subCatId = db.insertMenuSubCategory(id, menuCategory.getMenuCategoryId(), menuCategory.getMenuSubCategory().get(parentPosition).getMenuCategoryId(), menuCategory.getMenuSubCategory().get(parentPosition).getMenuCategoryName());
                                }

                                for (int i = 0; i < menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().size(); i++) {
                                    if (i == childPosition) {
                                        db.insertMenuProduct(id, subCatId, menuCategory.getMenuSubCategory().get(parentPosition).getMenuCategoryId(),
                                                menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getMenuProductId(),
                                                menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getProductName(),
                                                menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getVegType(),
                                                menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getMenuProductPrice(),
                                                menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getUserappProductImage(),
                                                menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getEcomProductImage(),
                                                menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getProductOverallRating(),
                                                1,
                                                gson.toJson(productSizeAdapter.getSelectedItem(false)),
                                                gson.toJson(productModifierAdapter.getSelectedProductModifier()),
                                                null,
                                                1,
                                                Double.parseDouble(menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getMenuProductPrice()),
                                                menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getMenuProductPrice()
                                                /*gson.toJson(menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getUpsells())*/);
                                    }
                                }
                            } else {
                                for (int i = 0; i < menuCategory.getMenuProducts().size(); i++) {
                                    if (i == childPosition) {

                                        db.insertMenuProduct(id, subCatId, menuCategory.getMenuCategoryId(),
                                                menuCategory.getMenuProducts().get(i).getMenuProductId(),
                                                menuCategory.getMenuProducts().get(i).getProductName(),
                                                menuCategory.getMenuProducts().get(i).getVegType(),
//                                                menuCategory.getMenuProducts().get(i).getMenuProductPrice(),
                                                selectedSizePrice,
                                                menuCategory.getMenuProducts().get(i).getUserappProductImage(),
                                                menuCategory.getMenuProducts().get(i).getEcomProductImage(),
                                                menuCategory.getMenuProducts().get(i).getProductOverallRating(),
                                                1,
                                                gson.toJson(productSizeAdapter.getSelectedItem(false)),
                                                gson.toJson(productModifierAdapter.getSelectedProductModifier()),
                                                null,
                                                1,
                                                Double.parseDouble(selectedSizePrice),
                                                menuCategory.getMenuProducts().get(i).getMenuProductPrice()
                                                /*gson.toJson(menuCategory.getMenuProducts().get(i).getUpsells())*/);

                                    }
                                }
                            }


                            if (productModifierAdapter != null) {
                                if (itemClickListener != null) {
                                    itemClickListener.OnAddItem(parentPosition, childPosition, qtyLayout, item_count, 1, action, menuCategory);
                                }
                                dismiss();
                            }
                        } else {
                            Toast.makeText(context, "Please select a size.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (productModifierAdapter != null) {
                            try {
                                Log.e("MenuProduct", menuCategory.getMenuProducts().get(childPosition).toString());
                                Log.e("SelectedProductModifier", productModifierAdapter.getSelectedProductModifier().toString());
                            } catch (Exception e) {
                                Log.e("Exception", e.toString());
                            }


                            List<MenuProduct> menuProducts = new ArrayList<>();

                            long id = db.getMenuCategoryIfExit(menuCategory.getMenuCategoryId());
                            if (id == -1) {
                                id = db.insertMenuCategory(menuCategory.getMenuCategoryId(), menuCategory.getMenuCategoryName(), gson.toJson(menuCategory.getMenuSubCategory()), gson.toJson(menuProducts));
                            }
                            long subCatId = -1;
                            if (isSubCat) {
                                subCatId = db.getMenuSubCategoryIfExit(menuCategory.getMenuSubCategory().get(parentPosition).getMenuCategoryId());
                                if (subCatId == -1) {
                                    subCatId = db.insertMenuSubCategory(id, menuCategory.getMenuCategoryId(), menuCategory.getMenuSubCategory().get(parentPosition).getMenuCategoryId(), menuCategory.getMenuSubCategory().get(parentPosition).getMenuCategoryName());
                                }

                                for (int i = 0; i < menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().size(); i++) {
                                    if (i == childPosition) {
                                        db.insertMenuProduct(id, subCatId, menuCategory.getMenuSubCategory().get(parentPosition).getMenuCategoryId(),
                                                menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getMenuProductId(),
                                                menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getProductName(),
                                                menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getVegType(),
                                                menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getMenuProductPrice(),
                                                menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getUserappProductImage(),
                                                menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getEcomProductImage(),
                                                menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getProductOverallRating(),
                                                1,
                                                gson.toJson(productSizeAdapter.getSelectedItem(false)),
                                                gson.toJson(productModifierAdapter.getSelectedProductModifier()),
                                                null,
                                                1,
                                                Double.parseDouble(menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getMenuProductPrice()),
                                                menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getMenuProductPrice()
                                        );
                                    }
                                }
                            } else {
                                for (int i = 0; i < menuCategory.getMenuProducts().size(); i++) {
                                    if (i == childPosition) {
                                        db.insertMenuProduct(id, subCatId, menuCategory.getMenuCategoryId(),
                                                menuCategory.getMenuProducts().get(i).getMenuProductId(),
                                                menuCategory.getMenuProducts().get(i).getProductName(),
                                                menuCategory.getMenuProducts().get(i).getVegType(),
                                                menuCategory.getMenuProducts().get(i).getMenuProductPrice(),
                                                menuCategory.getMenuProducts().get(i).getUserappProductImage(),
                                                menuCategory.getMenuProducts().get(i).getEcomProductImage(),
                                                menuCategory.getMenuProducts().get(i).getProductOverallRating(),
                                                1,
                                                gson.toJson(productSizeAdapter.getSelectedItem(false)),
                                                gson.toJson(productModifierAdapter.getSelectedProductModifier()),
                                                null,
                                                1,
                                                Double.parseDouble(menuCategory.getMenuProducts().get(i).getMenuProductPrice()),
                                                menuCategory.getMenuProducts().get(i).getMenuProductPrice()
                                                /*gson.toJson(menuCategory.getMenuProducts().get(i).getUpsells())*/);
                                    }
                                }
                            }

                            if (itemClickListener != null) {
                                itemClickListener.OnAddItem(parentPosition, childPosition, qtyLayout, item_count, 1, action, menuCategory);
                            }
                            dismiss();
                        }
                    }
                }

                break;
            default:

                break;
        }
    }


    @Override
    public void OnSizeSelected() {
        updatePrice(false);
    }

    @Override
    public void OnSizeModifierSelected(boolean isSelect) {
        updatePrice(isSelect);
    }

    String selectedSizePrice = "0";

    private void updatePrice(boolean isSelect) {
        List<MenuProduct> menuProducts = null;
        if (productSizeAdapter != null) {
            if (productSizeAdapter.getItemCount() > 1) {
                if (productSizeAdapter.getSelectedItem(isSelect).size() != 0) {
                    menuProducts = new ArrayList<>();
                    for (int i = 0; i < menuCategory.getMenuProducts().size(); i++) {
                        if (i == childPosition) {

                            MenuProduct product = new MenuProduct(
                                    menuCategory.getMenuProducts().get(i).getMenuProductId(),
                                    menuCategory.getMenuProducts().get(i).getProductName(),
                                    menuCategory.getMenuProducts().get(i).getDescription(),
                                    menuCategory.getMenuProducts().get(i).getVegType(),
                                    menuCategory.getMenuProducts().get(i).getMenuProductPrice(),
                                    menuCategory.getMenuProducts().get(i).getUserappProductImage(),
                                    menuCategory.getMenuProducts().get(i).getEcomProductImage(),
                                    menuCategory.getMenuProducts().get(i).getProductOverallRating(),
//                                            menuCategory.getMenuProducts().get(i).getMenuProductSize(),
                                    productSizeAdapter.getSelectedItem(isSelect),
                                    productModifierAdapter.getSelectedProductModifier(),
                                    /*menuCategory.getMenuProducts().get(i).getUpsells(),*/
                                    null,
                                    menuCategory.getMenuProducts().get(i).getMenuProductPrice(),
                                    1,
                                    1,
                                    Double.parseDouble(menuCategory.getMenuProducts().get(i).getMenuProductPrice()),
                                    Double.parseDouble(menuCategory.getMenuProducts().get(i).getMenuProductPrice()));
//                                            menuCategory.getMenuProducts().get(i).getQuantity());
                            menuProducts.add(product);

                        }
                    }
                }
            } else {
                try {
                    if (productModifierAdapter != null) {
                        menuProducts = new ArrayList<>();
                        for (int i = 0; i < menuCategory.getMenuProducts().size(); i++) {
                            if (i == childPosition) {
                                MenuProduct product = new MenuProduct(menuCategory.getMenuProducts().get(i).getMenuProductId(),
                                        menuCategory.getMenuProducts().get(i).getProductName(),
                                        menuCategory.getMenuProducts().get(i).getDescription(),
                                        menuCategory.getMenuProducts().get(i).getVegType(),
                                        menuCategory.getMenuProducts().get(i).getMenuProductPrice(),
                                        menuCategory.getMenuProducts().get(i).getUserappProductImage(),
                                        menuCategory.getMenuProducts().get(i).getEcomProductImage(),
                                        menuCategory.getMenuProducts().get(i).getProductOverallRating(),
                                        menuCategory.getMenuProducts().get(i).getMenuProductSize(),
                                        productModifierAdapter.getSelectedProductModifier(),
                                        null,
                                        menuCategory.getMenuProducts().get(i).getMenuProductPrice(),
                                        1,
                                        1,
                                        Double.parseDouble(menuCategory.getMenuProducts().get(i).getMenuProductPrice()),
                                        Double.parseDouble(menuCategory.getMenuProducts().get(i).getMenuProductPrice()));
                                menuProducts.add(product);
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.e("Exception", e.getLocalizedMessage());
                }
            }


            if (Double.parseDouble(totalPriceView.getText().toString()) == 0) {
                llprice.setVisibility(View.GONE);
            } else {
                llprice.setVisibility(View.VISIBLE);
            }
        }

        if (menuProducts != null) {
            Log.e("ANAND >>", menuProducts.toString());
            Double totalPrice = 0d;
            for (MenuProduct menuProduct : menuProducts) {
                int itemQty = menuProduct.getOriginalQuantity();
                if (menuProduct.getMenuProductSize() != null) {
                    if (menuProduct.getMenuProductSize().size() > 0) {

                        for (MenuProductSize menuProductSize1 : menuProduct.getMenuProductSize()) {
                            if (menuProductSize1.getSelected()) {
                                selectedSizePrice = menuProductSize1.getProductSizePrice();
                                totalPrice += Double.parseDouble(menuProductSize1.getProductSizePrice());
                                if (menuProductSize1.getSizeModifiers() != null) {
                                    for (SizeModifier sizeModifier : menuProductSize1.getSizeModifiers()) {
                                        if (sizeModifier.getModifierType().equalsIgnoreCase("free")) {

                                            int allCount = 0;
                                            for (int j = 0; j < sizeModifier.getModifier().size(); j++) {
                                                allCount = allCount + Integer.parseInt(sizeModifier.getModifier().get(j).getOriginalQuantity());
                                            }

                                            if (allCount > sizeModifier.getMaxAllowedQuantity()) {
                                                totalPrice += (((allCount * itemQty) - sizeModifier.getMaxAllowedQuantity()) * Double.parseDouble(sizeModifier.getModifier().get(0).getModifierProductPrice()));
                                            }
                                        } else {
                                            for (Modifier modifier : sizeModifier.getModifier()) {
                                                int qty = Integer.parseInt(modifier.getOriginalQuantity());
                                                qty = (qty * itemQty);
                                                totalPrice += (qty * Double.parseDouble(modifier.getModifierProductPrice()));
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    } else {
                        totalPrice += (itemQty * Double.parseDouble(menuProduct.getMenuProductPrice()));
                    }
                }

                for (ProductModifier productModifier : menuProduct.getProductModifiers()) {
                    if (productModifier.getModifierType().equalsIgnoreCase("free")) {


                        int allCount = 0;
                        for (int j = 0; j < productModifier.getModifier().size(); j++) {
                            allCount = allCount + Integer.parseInt(productModifier.getModifier().get(j).getOriginalQuantity());
                        }
                        if (allCount > productModifier.getMaxAllowedQuantity()) {
                            totalPrice += ((allCount - productModifier.getMaxAllowedQuantity()) * Double.parseDouble(productModifier.getModifier().get(0).getModifierProductPrice()));
                        }

                    } else {
                        for (Modifier modifier : productModifier.getModifier()) {
                            int qty = Integer.parseInt(modifier.getOriginalQuantity());
                            qty = (qty * itemQty);
                            totalPrice += (qty * Double.parseDouble(modifier.getModifierProductPrice()));
                        }
                    }
                }

            }

            totalPriceView.setText(String.format("%.2f", totalPrice));
            if (Double.parseDouble(totalPriceView.getText().toString()) == 0) {
                llprice.setVisibility(View.GONE);
            } else {
                llprice.setVisibility(View.VISIBLE);
            }
        }
    }


}