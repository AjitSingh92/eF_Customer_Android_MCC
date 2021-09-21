package com.easyfoodcustomer.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.easyfoodcustomer.R;
import com.easyfoodcustomer.cart_db.tables.ProductSizeAndModifier;
import com.easyfoodcustomer.fragments.MenuFragment;
import com.easyfoodcustomer.modelsNew.MealDetailsModel;
import com.easyfoodcustomer.modelsNew.MealDetailsSaveModel;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuProductSize;
import com.easyfoodcustomer.roomData.AppDatabase;
import com.easyfoodcustomer.roomData.AppExecutors;
import com.easyfoodcustomer.roomData.OrderSaveModel;
import com.easyfoodcustomer.ui_new.MenuMealListAdapter;
import com.easyfoodcustomer.ui_new.MenuMealNewListAdapter;
import com.easyfoodcustomer.ui_new.MenuProductSizeAdapter;
import com.easyfoodcustomer.ui_new.MenuProductSizeModifierAdapter;
import com.easyfoodcustomer.ui_new.interfaces.MenuProductModifierInterface;
import com.easyfoodcustomer.ui_new.interfaces.MenuProductSizeModifierInterface;
import com.easyfoodcustomer.utility.DialogUtils;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MenuDetailNewDialog extends Dialog implements View.OnClickListener, MenuProductSizeModifierInterface {

    private Context context;
    private TextView txt_category;
    private TextView BasePrice;
    private TextView AmountToPay;
    private TextView validationError;

    private TextView sign_up_btn_dialog;
    private TextView total_price;
    private ImageView cross_tv;
    private RecyclerView list_meal_category;
    private RecyclerView list_meal_Modifier;
    private LinearLayout add_items;

    private MenuMealNewListAdapter menuMealNewListAdapter;

    private double finalPriceAll;
    private AppDatabase mDb;

    private List<MealDetailsModel.MealConfigBean> meal_config;

    private MenuProduct menuProduct;
    private ProductSizeAndModifier.ProductSizeAndModifierTable productSizeAndModifierTable;
    // private List<MenuProductSize> menuProductSize;
    private DialogUtils dialogUtils;
    private String menuCategoryId;
    private MealDetailsModel mealDetailsModel;
    private MenuProductSizeModifierAdapter menuProductSizeModifierAdapter;
    boolean isSelected = false;
    boolean isCrustSelected = false;
    private List<MealDetailsModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean> sizeModifiersBeans = new ArrayList<>();

    private int clickPosition;

    private MealDetailsSaveModel mealDetailsModelSave;


    public MenuDetailNewDialog(@NonNull Context context, MenuProduct menuProduct,
                               ProductSizeAndModifier.ProductSizeAndModifierTable productSizeAndModifierTable,
                               String menuCategoryId, MealDetailsModel mealDetailsModel) {
        super(context);
        this.context = context;
        this.menuProduct = menuProduct;
        this.productSizeAndModifierTable = productSizeAndModifierTable;
        this.mealDetailsModel = mealDetailsModel;
        mDb = AppDatabase.getInstance(getApplicationContext());
        dialogUtils = new DialogUtils(context);
        isSelected = false;
        isCrustSelected = false;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.color.transprent);
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflate.inflate(R.layout.dialog_menu_details_new, null);
        setContentView(layout);
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.windowAnimations = R.style.dialogue_error;
        wlmp.gravity = Gravity.CENTER;
        wlmp.dimAmount = 0.8f;
        wlmp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wlmp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        setTitle(null);
        // setCancelable(false);
        setCanceledOnTouchOutside(true);

        txt_category = layout.findViewById(R.id.txt_category);
        BasePrice = layout.findViewById(R.id.BasePrice);
        AmountToPay = layout.findViewById(R.id.AmountToPay);
        validationError = layout.findViewById(R.id.validationError);
        sign_up_btn_dialog = layout.findViewById(R.id.sign_up_btn_dialog);
        total_price = layout.findViewById(R.id.total_price);
        cross_tv = layout.findViewById(R.id.cross_tv);
        list_meal_category = layout.findViewById(R.id.list_meal_category);
        list_meal_Modifier = layout.findViewById(R.id.list_meal_Modifier);
        add_items = layout.findViewById(R.id.add_items);

        list_meal_Modifier.setLayoutManager(new LinearLayoutManager(context));
        list_meal_category.setLayoutManager(new LinearLayoutManager(context));

        txt_category.setText(menuProduct.getProductName());
        String title = menuProduct.getProductName();

        if (productSizeAndModifierTable.getProductMainDetails().getSize_title() != null && !productSizeAndModifierTable.getProductMainDetails().getSize_title().isEmpty()) {
            BasePrice.setText("Select " + productSizeAndModifierTable.getProductMainDetails().getSize_title());
        } else {
            BasePrice.setText("Select Size");
        }
        //  BasePrice.setText("What size would you like?");
        AmountToPay.setText("Amount to pay\n" + context.getResources().getString(R.string.currency) + String.format("%.2f", Double.parseDouble(menuProduct.getMenuProductPrice())));
        total_price.setText(String.format("%.2f", Double.parseDouble(menuProduct.getMenuProductPrice())));
        finalPriceAll = Double.parseDouble(menuProduct.getMenuProductPrice());
        meal_config = mealDetailsModel.getMeal_config();
        menuMealNewListAdapter = new MenuMealNewListAdapter(context, this, meal_config);
        list_meal_category.setAdapter(menuMealNewListAdapter);

        cross_tv.setOnClickListener(this);
        add_items.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cross_tv:
                dismiss();
                break;

            case R.id.add_items:

                if (checkValidation().equalsIgnoreCase("ALL")) {

                    dialogUtils.showDialog("Please Wait..");
                    OrderSaveModel orderSaveModels = new OrderSaveModel(1, menuProduct.getMenuProductId(),
                            menuProduct.getMenuProductId(),
                            menuProduct.getProductName(),
                            menuProduct.getMenuProductPrice(),
                            menuProduct.getVegType(),
                            menuCategoryId,
                            menuProduct.getDescription(),
                            String.valueOf(finalPriceAll),
                            true,
                            new Gson().toJson(mealDetailsModel));
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mDb.saveOrderHistry().insertOrUpdate(orderSaveModels);
                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (MenuFragment.class != null && MenuFragment.notifyMenuListOnFeagmentInterface != null) {
                                                MenuFragment.notifyMenuListOnFeagmentInterface.onNotify();
                                                dialogUtils.closeDialog();
                                                dismiss();
                                            }
                                        }
                                    }, 2000);

                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(context, checkValidation(), Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.titleLayout:
                int pos = (int) v.getTag();
                clickPosition = pos;
                for (int i = 0; i < meal_config.size(); i++) {
                    meal_config.get(i).setNoOfCount(0);
                    meal_config.get(pos).getProducts().get(0).setNoOfCount(0);
                }
                for (int i = 0; i < meal_config.size(); i++) {
                    for (int X = 0; X < meal_config.get(i).getProducts().size(); X++) {
                        meal_config.get(i).getProducts().get(X).setNoOfCount(0);
                        for (int j = 0; j < meal_config.get(i).getProducts().get(X).getMenu_product_size().size(); j++) {
                            for (int k = 0; k < meal_config.get(i).getProducts().get(X).getMenu_product_size().get(j).getSize_modifiers().size(); k++) {
                                meal_config.get(i).getProducts().get(X).getMenu_product_size().get(j).getSize_modifiers().get(k).setNoOfCount(0);
                                for (int m = 0; m < meal_config.get(i).getProducts().get(X).getMenu_product_size().get(j).getSize_modifiers().get(k).getSize_modifier_products().size(); m++) {
                                    meal_config.get(i).getProducts().get(X).getMenu_product_size().get(j).getSize_modifiers().get(k).getSize_modifier_products().get(m).setNoOfCount(0);
                                }
                            }
                        }
                    }

                }
                meal_config.get(pos).setNoOfCount(1);
                meal_config.get(pos).getProducts().get(0).setNoOfCount(1);
                menuMealNewListAdapter.notifyDataSetChanged();
                if (meal_config.get(pos).getProducts().get(0).getMenu_product_size().get(0).getSize_modifiers().size() > 0) {
                    menuProductSizeModifierAdapter = new MenuProductSizeModifierAdapter(context, this, mealDetailsModel, mealDetailsModel.getMeal_config().get(pos).getProducts().get(0).getMenu_product_size().get(0).getSize_modifiers(), this);
                    list_meal_Modifier.setAdapter(menuProductSizeModifierAdapter);
                } else {
                    menuProductSizeModifierAdapter = new MenuProductSizeModifierAdapter(context, this, mealDetailsModel, sizeModifiersBeans, this);
                    list_meal_Modifier.setAdapter(menuProductSizeModifierAdapter);
                }


                setUpdatedPrice();
                break;
            default:
                break;
        }
    }


    /*private int getCurrentnumberOfCount(List<MealDetailsModel.MealConfigBean> productsBean) {
        int nofoCount = 0;
        int crustCount = 0;
        for (int i = 0; i < productsBean.size(); i++) {
            nofoCount = nofoCount + productsBean.get(i).getNoOfCount();
            if (nofoCount > 0) {
                for (int k = 0; k < productsBean.get(i).getProducts().size(); k++) {
                    for (int l = 0; l < productsBean.get(i).getProducts().get(k).getMenu_product_size().size(); l++) {
                        for (int m = 0; m < productsBean.get(i).getProducts().get(k).getMenu_product_size().get(l).getSize_modifiers().size(); m++) {
                            for (int n = 0; n < productsBean.get(i).getProducts().get(k).getMenu_product_size().get(l).getSize_modifiers().get(m).getSize_modifier_products().size(); n++) {
                                crustCount = crustCount + productsBean.get(i).getProducts().get(k).getMenu_product_size().get(l).getSize_modifiers().get(m).getSize_modifier_products().get(n).getNoOfCount();
                            }
                        }
                    }


                }
            }
        }
        return crustCount;
    }*/

    private int getCurrentnumberOfCountt
            (List<MealDetailsModel.MealConfigBean.ProductsBean> productsBean) {
        int nofoCount = 0;
        for (int i = 0; i < productsBean.size(); i++) {
            nofoCount = nofoCount + productsBean.get(i).getNoOfCount();
        }
        return nofoCount;
    }


    @Override
    public void updateMeanProductSizeModifier(int positionParent, int positionChild,
                                              boolean isIncrease, int previousCount, boolean isSingle) {
        if (isIncrease) {
            if (isSingle) {
                for (int i = 0; i < mealDetailsModel.getMeal_config().get(clickPosition).getProducts().get(0).getMenu_product_size().get(0).getSize_modifiers().get(positionParent).getSize_modifier_products().size(); i++) {
                    mealDetailsModel.getMeal_config().get(clickPosition).getProducts().get(0).getMenu_product_size().get(0).getSize_modifiers().get(positionParent).getSize_modifier_products().get(i).setNoOfCount(0);

                }
            }
            mealDetailsModel.getMeal_config().get(clickPosition).getProducts().get(0).getMenu_product_size().get(0).getSize_modifiers().get(positionParent).getSize_modifier_products().get(positionChild).setNoOfCount(previousCount + 1);
        } else {
            mealDetailsModel.getMeal_config().get(clickPosition).getProducts().get(0).getMenu_product_size().get(0).getSize_modifiers().get(positionParent).getSize_modifier_products().get(positionChild).setNoOfCount(previousCount - 1);

        }
        menuProductSizeModifierAdapter.notifyDataSetChanged();

        mealDetailsModel.getMeal_config().get(clickPosition).getProducts().set(0, mealDetailsModel.getMeal_config().get(clickPosition).getProducts().get(0));

        setUpdatedPrice();
    }

    private void setUpdatedPrice() {
        Double finalPrice = Double.parseDouble(mealDetailsModel.getMeal().getMeal_price());

        for (int k = 0; k < mealDetailsModel.getMeal_config().size(); k++) {
            if (mealDetailsModel.getMeal_config().get(k).getNoOfCount() > 0) {
                if (mealDetailsModel.getMeal_config().get(k).getSelling_price() != null && !mealDetailsModel.getMeal_config().get(k).getSelling_price().isEmpty()) {
                    finalPrice = Double.parseDouble(mealDetailsModel.getMeal_config().get(k).getSelling_price());
                }
            }
        }
        for (int i = 0; i < mealDetailsModel.getMeal_config().get(clickPosition).getProducts().get(0).getMenu_product_size().get(0).getSize_modifiers().size(); i++) {
            int noOfFree = mealDetailsModel.getMeal_config().get(clickPosition).getProducts().get(0).getMenu_product_size().get(0).getSize_modifiers().get(i).getFree_qty_limit();
            for (int j = 0; j < mealDetailsModel.getMeal_config().get(clickPosition).getProducts().get(0).getMenu_product_size().get(0).getSize_modifiers().get(i).getSize_modifier_products().size(); j++) {
                for (int a = 0; a < mealDetailsModel.getMeal_config().get(clickPosition).getProducts().get(0).getMenu_product_size().get(0).getSize_modifiers().get(i).getSize_modifier_products().get(j).getNoOfCount(); a++) {
                    isSelected = true;
                    if (noOfFree == 0) {
                        finalPrice = finalPrice + Double.parseDouble(mealDetailsModel.getMeal_config().get(clickPosition).getProducts().get(0).getMenu_product_size().get(0).getSize_modifiers().get(i).getSize_modifier_products().get(j).getModifier_product_price());

                    } else {
                        noOfFree = noOfFree - 1;
                    }
                }

            }
        }
        finalPriceAll = finalPrice;

        total_price.setText(String.format("%.2f",finalPrice));
        //total_price.setText(String.valueOf(new DecimalFormat("##.##").format(finalPrice)));
    }


    private boolean checkorderStatus() {

        boolean isOrderReady = false;
        for (int i = 0; i < mealDetailsModel.getMeal_config().size(); i++) {

            if (mealDetailsModel.getMeal_config().get(i).getAllowed_quantity() == getCurrentnumberOfCount(mealDetailsModel.getMeal_config())) {
                isOrderReady = true;

            } else {
                isOrderReady = false;
            }
        }
        return isOrderReady;
    }


    private int getCurrentnumberOfCount(List<MealDetailsModel.MealConfigBean> productsBean) {
        int nofoCount = 0;
        int crustCount = 0;
        for (int i = 0; i < productsBean.size(); i++) {
            nofoCount = nofoCount + productsBean.get(i).getNoOfCount();
            if (nofoCount > 0) {
                for (int k = 0; k < productsBean.get(i).getProducts().size(); k++) {
                    for (int l = 0; l < productsBean.get(i).getProducts().get(k).getMenu_product_size().size(); l++) {

                        for (int m = 0; m < productsBean.get(i).getProducts().get(k).getMenu_product_size().get(l).getSize_modifiers().size(); m++) {
                            for (int n = 0; n < productsBean.get(i).getProducts().get(k).getMenu_product_size().get(l).getSize_modifiers().get(m).getSize_modifier_products().size(); n++) {
                                crustCount = crustCount + productsBean.get(i).getProducts().get(k).getMenu_product_size().get(l).getSize_modifiers().get(m).getSize_modifier_products().get(n).getNoOfCount();
                            }
                        }
                    }


                }
            }
        }
        return crustCount;
    }


    private int getCrustCount(List<MealDetailsModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean.SizeModifierProductsBean> productsBean) {

        int crustCount = 0;

        for (int n = 0; n < productsBean.size(); n++) {
            crustCount = crustCount + productsBean.get(n).getNoOfCount();
        }


        return crustCount;
    }

/*    private String checkValidation() {
        String isOrderReady = "";
        for (int i = 0; i < mealDetailsModel.getMeal_config().size(); i++) {
            if (mealDetailsModel.getMeal_config().get(i).getAllowed_quantity() == mealDetailsModel.getMeal_config().get(i).getNoOfCount()) {
                for (int j = 0; j < mealDetailsModel.getMeal_config().get(i).getProducts().size(); j++) {
                    for (int k = 0; k < mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().size(); k++) {
                        for (int l = 0; l < mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().size(); l++) {
                            boolean isReturn = false;
                            for (int m = 0; m < mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().get(l).getSize_modifier_products().size(); m++) {

                                if (mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().get(l).getSize_modifier_products().get(m).getNoOfCount() != 0 && mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().get(l).getMin_allowed_quantity() <= mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().get(l).getSize_modifier_products().get(m).getNoOfCount()) {

                                    isReturn = true;
                                    isOrderReady = "ALL";

                                     return isOrderReady;

                                } else {
                                    if (isReturn) {
                                        isOrderReady = "ALL";
                                    } else {

                                        isOrderReady = "Please select a " + mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().get(l).getModifier_name();

                                    return isOrderReady;
                                    }
                                }
                            }


                        }

                    }
                }

            } else {
                if (!isOrderReady.equals("ALL")) {

                    isOrderReady = "Please select a size";
                }

            }

        }

        return isOrderReady;
    }*/

    private String checkValidation() {
        int crustCount = 0;
        String isOrderReady = "";
        for (int i = 0; i < mealDetailsModel.getMeal_config().size(); i++) {
            if (mealDetailsModel.getMeal_config().get(i).getAllowed_quantity() == getSizeCount(mealDetailsModel.getMeal_config())) {
                if (!isCrustSelected) {

                    for (int j = 0; j < mealDetailsModel.getMeal_config().get(i).getProducts().size(); j++) {
                        for (int k = 0; k < mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().size(); k++) {

                            if (mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers() != null && mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().size() > 0) {
                                for (int l = 0; l < mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().size(); l++) {
                                    crustCount = crustCount + getCrustCount(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().get(l).getSize_modifier_products());

                                    if (i == mealDetailsModel.getMeal_config().size() - 1 && l == mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().size() - 1) {
                                        if (crustCount > 0) {
                                            return "ALL";
                                        } else {
                                            return "Please select a crust";
                                            //  return "Please select a " + mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().get(l).getModifier_name();
                                        }
                                    }
                               /* if (mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().get(l).getMin_allowed_quantity() <= getCrustCount(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().get(l).getSize_modifier_products())) {
                                    isCrustSelected = true;
                                    return "ALL";
                                } else {
                                    isCrustSelected = false;
                                    return "Please select a " + mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().get(l).getModifier_name();


                                }*/

                                }
                            } else {
                                isOrderReady = "ALL";
                            }

                        }
                    }

                }

            } else {

                return "Please select a size";
            }

        }

        return isOrderReady;
    }


    private int getSizeCount(List<MealDetailsModel.MealConfigBean> productsBean) {
        int nofoCount = 0;
        for (int i = 0; i < productsBean.size(); i++) {
            nofoCount = nofoCount + productsBean.get(i).getNoOfCount();

        }
        return nofoCount;
    }
}
