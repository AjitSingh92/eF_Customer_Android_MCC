package com.easyfoodcustomer.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MealProduct;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.SizeModifier;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.easyfoodcustomer.R;
import com.easyfoodcustomer.adapters.menu_adapter.ItemClickListener;
import com.easyfoodcustomer.adapters.menu_adapter.OnProductModifierSelected;
import com.easyfoodcustomer.adapters.menu_adapter.ProductModifierAdapter;
import com.easyfoodcustomer.adapters.menu_adapter.ProductSizeAdapter;
import com.easyfoodcustomer.adapters.menu_adapter.RecyclerLayoutManager;
import com.easyfoodcustomer.cart_db.DatabaseHelper;
import com.easyfoodcustomer.cart_db.tables.ProductSizeAndModifier;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuProductSize;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.Modifier;
import com.easyfoodcustomer.utility.GlobalValues;

import java.util.List;

public class MealProductModifierDialog extends DialogFragment implements View.OnClickListener, OnProductModifierSelected {
    private DatabaseHelper db;
    Context context;
    ItemClickListener itemClickListener;
    int childParentPosition;
    int selectedChildPosition;

    int parentPosition;
    int childPosition;
    //    MenuProduct menuProduct;
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
    TextView tvBasePrice, tvAmountToPay, tvOption, tvTitle;
    TextView totalPriceView, categoryName;
    View qtyLayout;
    Boolean isSubCat;
    ProductSizeAndModifier.ProductSizeAndModifierTable productSizeAndModifierTable;
    Double modifierPrice = 0d;
    FirebaseAnalytics mFirebaseAnalytics;

    public static MealProductModifierDialog newInstance(Context context, int childParentPosition, int selectedChildPosition, int parentPosition, int childPosition, View qtyLayout, TextView item_count, int itemCount, int action, MenuCategory menuCategory, Boolean isSubCat, ProductSizeAndModifier.ProductSizeAndModifierTable productSizeAndModifierTable, ItemClickListener itemClickListener) {
        MealProductModifierDialog c = new MealProductModifierDialog();
        c.context = context;
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
        c.productSizeAndModifierTable = productSizeAndModifierTable;
        c.itemClickListener = itemClickListener;
        return c;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(context);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.popup_meal_product_modifier, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        totalPriceView = view.findViewById(R.id.total_price);
        view.findViewById(R.id.sign_up_btn_dialog).setOnClickListener(this);
        view.findViewById(R.id.cross_tv).setOnClickListener(this);


        categoryName = view.findViewById(R.id.txt_category);
        categoryName.setText(menuCategory.getMenuCategoryName());

        tvBasePrice = view.findViewById(R.id.tv_BasePrice);
        tvAmountToPay = view.findViewById(R.id.tv_AmountToPay);
        tvBasePrice.setText("Base Price\n" + context.getResources().getString(R.string.currency) + String.format("%.2f", Double.parseDouble(menuCategory.getMeal().get(childPosition).getMealPrice())));
        tvAmountToPay.setText("Amount to pay\n" + context.getResources().getString(R.string.currency) + String.format("%.2f", Double.parseDouble(menuCategory.getMeal().get(childPosition).getMealPrice())));

        tvOption = view.findViewById(R.id.tv_Option);
        tvOption.setText("Option for " + menuCategory.getMeal().get(childPosition).getMealCategories().get(childParentPosition).getMealProducts().get(selectedChildPosition).getProductName());

        tvTitle = view.findViewById(R.id.tv_title);


        if (menuCategory.getMeal().get(childPosition) != null)
            categoryName.setText(menuCategory.getMeal().get(childPosition).getMealName());

        productSizeListView = view.findViewById(R.id.list_meal_category);
        productSizeLayoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
        productSizeLayoutManager.setScrollEnabled(false);
        productSizeListView.setLayoutManager(productSizeLayoutManager);
        productSizeAdapter = new ProductSizeAdapter(context, this);
        productSizeListView.setAdapter(productSizeAdapter);


        if (productSizeAndModifierTable == null) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    productSizeAndModifierTable = GlobalValues.getInstance().getDb().productSizeAndModifierMaster().getProductSizeAndModifierList(menuCategory.getMeal().get(childPosition).getMealCategories().get(childParentPosition).getMealProducts().get(selectedChildPosition).getProductId());


                }
            }).start();
        }


        if (productSizeAndModifierTable != null) {
            tvTitle.setText(productSizeAndModifierTable.getMenuProductSize().get(0).getProductSizeName());

        }

        if (productSizeAndModifierTable != null) {
            productSizeAdapter.setCheckedFirstItem(true);
            productSizeAdapter.addItem(productSizeAndModifierTable.getMenuProductSize());
        }

        productModifierView = view.findViewById(R.id.product_modifier);
        productModifierLayoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
        productModifierLayoutManager.setScrollEnabled(false);
        productModifierView.setLayoutManager(productModifierLayoutManager);
        productModifierAdapter = new ProductModifierAdapter(context, parentPosition, this);
        productModifierView.setAdapter(productModifierAdapter);

        if (productSizeAndModifierTable != null) {
            productModifierAdapter.addItem(productSizeAndModifierTable.getProductModifiers());
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


                                menuCategory.getMeal().get(childPosition).getMealCategories().get(childParentPosition).getMealProducts().get(selectedChildPosition).setMenuProductSize(productSizeAdapter.getSelectedItem(false));
                                menuCategory.getMeal().get(childPosition).getMealCategories().get(childParentPosition).getMealProducts().get(selectedChildPosition).getMenuProductSize().get(0).setAmount(String.valueOf(modifierPrice));
                        }
                    }
                }
                if (productModifierAdapter != null) {
                    menuCategory.getMeal().get(childPosition).getMealCategories().get(childParentPosition).getMealProducts().get(selectedChildPosition).setProductModifiers(productModifierAdapter.getSelectedProductModifier());
                }
                if (productSizeAdapter != null && isValid(menuCategory.getMeal().get(childPosition).getMealCategories().get(childParentPosition).getMealProducts().get(selectedChildPosition))) {
                    if (itemClickListener != null) {
                        itemClickListener.OnMealProductModifierSelected(true, childParentPosition, selectedChildPosition, parentPosition, childPosition, qtyLayout, item_count, itemCount, action, menuCategory, isSubCat, true);
                    }
                    dismiss();
                }
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


    private void updatePrice(boolean isSelect) {
        List<MenuProduct> menuProducts = null;
        Double netPrice = 0d;
        Double basePrice = 0d;
        if (productSizeAdapter != null) {
            if (productSizeAdapter.getItemCount() > 1) {
                netPrice += Double.parseDouble(menuCategory.getMeal().get(childPosition).getMealPrice());
                List<MenuProductSize> menuProductSizes = productSizeAdapter.getSelectedItem(isSelect);
                if (menuProductSizes.size() != 0) {
                    basePrice += Double.parseDouble(menuCategory.getMeal().get(childPosition).getMealPrice());
                    for (int i = 0; i < menuProductSizes.size(); i++) {
                        if (menuProductSizes.get(i).getSizeModifiers() != null && menuProductSizes.get(i).getSizeModifiers().size() > 0) {
                            for (int j = 0; j < menuProductSizes.get(i).getSizeModifiers().size(); j++) {

                                if (menuProductSizes.get(i).getSizeModifiers().get(j).getModifierType().equalsIgnoreCase("free")) {

                                    int allCount = 0;

                                    for (int k = 0; k < menuProductSizes.get(i).getSizeModifiers().get(j).getModifier().size(); k++) {
                                        allCount = allCount + Integer.parseInt(menuProductSizes.get(i).getSizeModifiers().get(j).getModifier().get(k).getOriginalQuantity());
                                    }

                                    if (allCount > menuProductSizes.get(i).getSizeModifiers().get(j).getMaxAllowedQuantity()) {

                                        netPrice += ((allCount - menuProductSizes.get(i).getSizeModifiers().get(j).getMaxAllowedQuantity()) * Double.parseDouble(menuProductSizes.get(i).getSizeModifiers().get(j).getModifier().get(0).getModifierProductPrice()));
                                        modifierPrice = netPrice - basePrice;
                                    }
                                } else {


                                    if (menuProductSizes.get(i).getSizeModifiers().get(j).getFreeAllowedQuantity() > 0) {
                                        int qtt = 0;
                                        double maxVal = 0.0;
                                        double curentval = 0.0;
                                        double totalModi = 0.0;
                                        int currentQuantity = 0;

                                        for (int k = 0; k < menuProductSizes.get(i).getSizeModifiers().get(j).getModifier().size(); k++) {

                                            int qty = Integer.parseInt(menuProductSizes.get(i).getSizeModifiers().get(j).getModifier().get(k).getOriginalQuantity());
                                            //qtt=qtt+qty;
                                            if (maxVal < Double.parseDouble(menuProductSizes.get(i).getSizeModifiers().get(j).getModifier().get(k).getModifierProductPrice())) {
                                                maxVal = Double.parseDouble(menuProductSizes.get(i).getSizeModifiers().get(j).getModifier().get(k).getModifierProductPrice());
                                            }
                                            currentQuantity = Integer.parseInt(menuProductSizes.get(i).getSizeModifiers().get(j).getModifier().get(k).getOriginalQuantity());
                                            curentval = Double.parseDouble(menuProductSizes.get(i).getSizeModifiers().get(j).getModifier().get(k).getModifierProductPrice());


                                            qtt = qtt + qty;

                                       /* if (qtt > menuProductSizes.get(i).getSizeModifiers().get(j).getFreeAllowedQuantity()) {

                                            if (qtt == menuProductSizes.get(i).getSizeModifiers().get(j).getFreeAllowedQuantity() + 1) {
                                                netPrice = netPrice + maxVal;
                                                //totalModi = totalModi + maxVal;
                                            } else {

                                                netPrice = netPrice + Integer.parseInt(menuProductSizes.get(i).getSizeModifiers().get(j).getModifier().get(k).getOriginalQuantity()) * Double.parseDouble(menuProductSizes.get(i).getSizeModifiers().get(j).getModifier().get(k).getModifierProductPrice());
                                                // totalModi = totalModi +  Double.parseDouble(menuProductSizes.get(i).getSizeModifiers().get(j).getModifier().get(k).getModifierProductPrice());
                                            }
                                            //qty = qty - sizeModifier.getFreeAllowedQuantity();
                                        *//*  totalModi=totalModi+  Double.parseDouble(modifier.getModifierProductPrice());

                                            qty = qtt - menuProductSizes.get(i).getSizeModifiers().get(j).getFreeAllowedQuantity();*//*
                                            // netPrice += totalModi;
                                            //netPrice += (qty * Double.parseDouble(modifier.getModifierProductPrice()));
                                            // totalPrice += (qty * maxVal);

                                        }*/
                                        }
                                        if (qtt > menuProductSizes.get(i).getSizeModifiers().get(j).getFreeAllowedQuantity()) {
                                            if (qtt == menuProductSizes.get(i).getSizeModifiers().get(j).getFreeAllowedQuantity() + 1) {
                                                netPrice = netPrice + maxVal;
                                                //totalModi = totalModi + maxVal;
                                            } else {
                                                netPrice = netPrice + (qtt - menuProductSizes.get(i).getSizeModifiers().get(j).getFreeAllowedQuantity()) * maxVal;
                                            }
                                       /* if (qtt == menuProductSizes.get(i).getSizeModifiers().get(j).getFreeAllowedQuantity() + 1) {
                                            netPrice = netPrice + maxVal;
                                            //totalModi = totalModi + maxVal;
                                        } else {

                                            netPrice = netPrice + Integer.parseInt(menuProductSizes.get(i).getSizeModifiers().get(j).getModifier().get(k).getOriginalQuantity()) * Double.parseDouble(menuProductSizes.get(i).getSizeModifiers().get(j).getModifier().get(k).getModifierProductPrice());
                                            // totalModi = totalModi +  Double.parseDouble(menuProductSizes.get(i).getSizeModifiers().get(j).getModifier().get(k).getModifierProductPrice());
                                        }*/
                                            //qty = qty - sizeModifier.getFreeAllowedQuantity();
                                        /*  totalModi=totalModi+  Double.parseDouble(modifier.getModifierProductPrice());

                                            qty = qtt - menuProductSizes.get(i).getSizeModifiers().get(j).getFreeAllowedQuantity();*/
                                            // netPrice += totalModi;
                                            //netPrice += (qty * Double.parseDouble(modifier.getModifierProductPrice()));
                                            // totalPrice += (qty * maxVal);

                                        }


                                    /*for (Modifier modifier : menuProductSizes.get(i).getSizeModifiers().get(j).getModifier()) {
                                        //  if(modifier.getOriginalQuantity())
                                        int qty = Integer.parseInt(modifier.getOriginalQuantity());
                                        //qtt=qtt+qty;
                                        if (maxVal < Double.parseDouble(modifier.getModifierProductPrice())) {
                                            maxVal = Double.parseDouble(modifier.getModifierProductPrice());
                                        }

                                        qtt = qtt + qty;

                                        if (qtt > menuProductSizes.get(i).getSizeModifiers().get(j).getFreeAllowedQuantity()) {

                                            if (qtt == menuProductSizes.get(i).getSizeModifiers().get(j).getFreeAllowedQuantity() + 1) {
                                                totalModi = totalModi + maxVal;
                                            } else {
                                                totalModi = totalModi +  Double.parseDouble(modifier.getModifierProductPrice());
                                            }
                                            //qty = qty - sizeModifier.getFreeAllowedQuantity();
                                        *//*  totalModi=totalModi+  Double.parseDouble(modifier.getModifierProductPrice());

                                            qty = qtt - menuProductSizes.get(i).getSizeModifiers().get(j).getFreeAllowedQuantity();*//*
                                            netPrice += totalModi;
                                            //netPrice += (qty * Double.parseDouble(modifier.getModifierProductPrice()));
                                            // totalPrice += (qty * maxVal);

                                        }
                                    }*/
                                    } else {
                                        for (Modifier modifier : menuProductSizes.get(i).getSizeModifiers().get(j).getModifier()) {
                                            int qty = Integer.parseInt(modifier.getOriginalQuantity());
                                            //qty = (qty * itemQty);
                                            netPrice += (qty * Double.parseDouble(modifier.getModifierProductPrice()));
                                        }
                                    }






                               /* for (Modifier modifier : menuProductSizes.get(i).getSizeModifiers().get(j).getModifier()) {
                                    int qty = Integer.parseInt(modifier.getOriginalQuantity());
                                    if (menuProductSizes.get(i).getSizeModifiers().get(j).getMaxAllowedQuantity() != 1) {
                                        netPrice += (qty * Double.parseDouble(modifier.getModifierProductPrice()));
                                        modifierPrice = netPrice - basePrice;
                                    }
                                }*/

                                }
                            }
                        }
                    }
                }
                totalPriceView.setText(String.format("%.2f", netPrice));
            } else {
                try {
                    if (productModifierAdapter != null) {

                    }
                } catch (Exception e) {
                    Log.e("Exception", e.getLocalizedMessage());
                }
            }
        }


    }


    private boolean isValid(MealProduct menuCategory) {


            if (menuCategory.getMenuProductSize() != null) {

                for (MenuProductSize menuProductSize1 : menuCategory.getMenuProductSize()) {
                    if (menuProductSize1.getSelected()) {

                        if (menuProductSize1.getSizeModifiers() != null) {
                            for (SizeModifier sizeModifier : menuProductSize1.getSizeModifiers()) {
                                int qty = 0;
                                for (Modifier modifier : sizeModifier.getModifier()) {
                                    if (modifier.getOriginalQuantity() != null || !modifier.getOriginalQuantity().isEmpty())
                                        qty = qty + Integer.parseInt(modifier.getOriginalQuantity());

                                }
                                if (qty < sizeModifier.getMinAllowedQuantity()) {
                                    Toast.makeText(context, "Please Select " + sizeModifier.getModifierName() + " First", Toast.LENGTH_SHORT).show();
                                    return false;
                                }

                            }
                        }
                    }
                }

            }







       /* for (MenuProductSize menuProductSize1 : menuProduct.getMenuProductSize()) {
            if (menuProductSize1.getSelected()) {

                if (menuProductSize1.getSizeModifiers() != null) {
                    for (SizeModifier sizeModifier : menuProductSize1.getSizeModifiers()) {
                        int qty = 0;
                        for (Modifier modifier : sizeModifier.getModifier()) {
                            qty = Integer.parseInt(modifier.getOriginalQuantity());

                        }
                        if (qty < sizeModifier.getMinAllowedQuantity()) {
                            Toast.makeText(context, "Please Select Base First", Toast.LENGTH_SHORT).show();
                            return false;
                        } else {
                            return true;
                        }

                    }
                }
            }
        }*/


        return true;
    }
}