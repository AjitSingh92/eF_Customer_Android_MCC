package com.easyfoodcustomer.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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
import com.easyfoodcustomer.fragments.MenuFragment;
import com.easyfoodcustomer.modelsNew.MealDetailsModel;
import com.easyfoodcustomer.modelsNew.MealDetailsSaveModel;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MealCategory;
import com.easyfoodcustomer.roomData.AppDatabase;
import com.easyfoodcustomer.roomData.AppExecutors;
import com.easyfoodcustomer.roomData.OrderSaveModel;
import com.easyfoodcustomer.ui_new.MenuMealListAdapter;
import com.easyfoodcustomer.ui_new.interfaces.MenuProductModifierInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MenuDetailDialog extends Dialog implements View.OnClickListener, MenuProductModifierInterface {

    private Context context;
    private TextView txt_category;
    private TextView BasePrice;
    private TextView AmountToPay;
    private TextView validationError;
    private TextView size_lable;
    private TextView sign_up_btn_dialog;
    private TextView total_price;
    private ImageView cross_tv;
    private RecyclerView list_meal_category;
    private LinearLayout add_items;
    public MealDetailsModel mealDetailsModel;
    private MealDetailsSaveModel mealDetailsModelSave;
    private MenuMealListAdapter menuMealListAdapter;

    private double finalPriceAll;
    private AppDatabase mDb;

    private List<MealDetailsModel.MealConfigBean> meal_config;


    public MenuDetailDialog(@NonNull Context context, MealDetailsModel mealDetailsModel) {
        super(context);
        this.context = context;
        this.mealDetailsModel = mealDetailsModel;
        mDb = AppDatabase.getInstance(getApplicationContext());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.color.transprent);
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflate.inflate(R.layout.dialog_menu_details, null);
        setContentView(layout);
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.windowAnimations = R.style.dialogue_error;
        wlmp.gravity = Gravity.CENTER;
        wlmp.dimAmount = 0.8f;
        wlmp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        setTitle(null);
        // setCancelable(false);
        setCanceledOnTouchOutside(true);

        txt_category = layout.findViewById(R.id.txt_category);
        BasePrice = layout.findViewById(R.id.BasePrice);
        AmountToPay = layout.findViewById(R.id.AmountToPay);
        validationError = layout.findViewById(R.id.validationError);
        size_lable = layout.findViewById(R.id.size_lable);
        sign_up_btn_dialog = layout.findViewById(R.id.sign_up_btn_dialog);
        total_price = layout.findViewById(R.id.total_price);
        cross_tv = layout.findViewById(R.id.cross_tv);
        list_meal_category = layout.findViewById(R.id.list_meal_category);
        add_items = layout.findViewById(R.id.add_items);

        list_meal_category.setLayoutManager(new LinearLayoutManager(context));

        txt_category.setText(mealDetailsModel.getMeal().getMeal_name());
        String title = null;
        for (int i = 0; i < mealDetailsModel.getMeal_config().size(); i++) {
            MealDetailsModel.MealConfigBean mItem = mealDetailsModel.getMeal_config().get(i);
            if (title == null) {
                title = mItem.getAllowed_quantity() + " " + mItem.getName();
            } else {
                title += ", " + mItem.getAllowed_quantity() + " " + mItem.getName();
            }
        }
        BasePrice.setText(title);
        AmountToPay.setText("Amount to pay\n" + context.getResources().getString(R.string.currency) + String.format("%.2f", Double.parseDouble(mealDetailsModel.getMeal().getMeal_price())));
        total_price.setText(String.format("%.2f", Double.parseDouble(mealDetailsModel.getMeal().getMeal_price())));
        finalPriceAll = Double.parseDouble(mealDetailsModel.getMeal().getMeal_price());
        meal_config = mealDetailsModel.getMeal_config();

        menuMealListAdapter = new MenuMealListAdapter(context, this, meal_config, mealDetailsModel, this, finalPriceAll);
        list_meal_category.setAdapter(menuMealListAdapter);

        cross_tv.setOnClickListener(this);
        add_items.setOnClickListener(this);

        copyValueInDiffrentModel();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cross_tv:
                dismiss();
                break;

            case R.id.add_items:
                if (checkorderStatus()) {
                    OrderSaveModel orderSaveModel = new OrderSaveModel(1, mealDetailsModelSave.getMeal().getId(),
                            mealDetailsModelSave.getMeal().getRestaurant_id(),
                            mealDetailsModelSave.getMeal().getMeal_name(),
                            mealDetailsModelSave.getMeal().getMeal_price(),
                            mealDetailsModelSave.getMeal().getVeg_type(),
                            mealDetailsModelSave.getMeal().getMenu_category_id(),
                            mealDetailsModelSave.getMeal().getDescription(),
                            String.valueOf(finalPriceAll),
                            true,
                            new Gson().toJson(mealDetailsModelSave));
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mDb.saveOrderHistry().insertOrUpdate(orderSaveModel);
                            if (MenuFragment.class != null && MenuFragment.notifyMenuListOnFeagmentInterface != null) {
                                MenuFragment.notifyMenuListOnFeagmentInterface.onNotify();
                            }
                        }
                    });

                    dismiss();
                } else {
                    Toast.makeText(context, "Please select all items", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    private boolean checkorderStatus() {
        boolean isOrderReady = false;
        for (int i = 0; i < mealDetailsModel.getMeal_config().size(); i++) {
            if (mealDetailsModel.getMeal_config().get(i).getAllowed_quantity() == getCurrentnumberOfCount(mealDetailsModel.getMeal_config().get(i).getProducts())) {
                isOrderReady = true;
            } else {
                isOrderReady = false;
            }
        }
        return isOrderReady;
    }

    private int getCurrentnumberOfCount(List<MealDetailsModel.MealConfigBean.ProductsBean> productsBean) {
        int nofoCount = 0;
        for (int i = 0; i < productsBean.size(); i++) {
            nofoCount = nofoCount + productsBean.get(i).getNoOfCount();
        }
        return nofoCount;
    }

    @Override
    public void updateMeanProductSizeModifier(int positionParent, int childPosition, boolean isIncrease, int previousCount, MealDetailsModel.MealConfigBean.ProductsBean mealDetailsModels) {
        if (isIncrease) {
            mealDetailsModels.setNoOfCount(previousCount + 1);
            addIntoCopyModel(mealDetailsModels, positionParent);

            for (int i=0;i<mealDetailsModels.getMenu_product_size().size();i++)
            {
                for (int j=0;j<mealDetailsModels.getMenu_product_size().get(i).getSize_modifiers().size();j++)
                {
                    for (int k=0;k<mealDetailsModels.getMenu_product_size().get(i).getSize_modifiers().get(j).getSize_modifier_products().size();k++)
                    {
                        mealDetailsModels.getMenu_product_size().get(i).getSize_modifiers().get(j).getSize_modifier_products().get(k).setNoOfCount(0);
                    }
                }
            }

            meal_config.get(positionParent).getProducts().set(childPosition, mealDetailsModels);
            mealDetailsModel.getMeal_config().get(positionParent).getProducts().set(childPosition, mealDetailsModels);



        } else {
            mealDetailsModels.setNoOfCount(previousCount - 1);//
            mealDetailsModelSave.getMeal_config().get(positionParent).getProducts().remove(mealDetailsModelSave.getMeal_config().get(positionParent).getProducts().size()-1);


            for (int i=0;i<mealDetailsModels.getMenu_product_size().size();i++)
            {
                for (int j=0;j<mealDetailsModels.getMenu_product_size().get(i).getSize_modifiers().size();j++)
                {
                    for (int k=0;k<mealDetailsModels.getMenu_product_size().get(i).getSize_modifiers().get(j).getSize_modifier_products().size();k++)
                    {
                        mealDetailsModels.getMenu_product_size().get(i).getSize_modifiers().get(j).getSize_modifier_products().get(k).setNoOfCount(0);
                    }
                }
            }

            meal_config.get(positionParent).getProducts().set(childPosition, mealDetailsModels);
            mealDetailsModel.getMeal_config().get(positionParent).getProducts().set(childPosition, mealDetailsModels);
        }

        updateTotoalPrice();
    }

    private void addIntoCopyModel(MealDetailsModel.MealConfigBean.ProductsBean mealDetailsModels, int positionParent) {
        //List<MealDetailsSaveModel.MealConfigBean.ProductsBean> productsBeanList=new ArrayList<>();

        MealDetailsSaveModel.MealConfigBean.ProductsBean productsBean = new MealDetailsSaveModel.MealConfigBean.ProductsBean();
        productsBean.setAllowed_quantity(mealDetailsModels.getAllowed_quantity());
        productsBean.setCategory_name(mealDetailsModels.getCategory_name());
        productsBean.setCategory_name_slug(mealDetailsModels.getCategory_name_slug());
        productsBean.setDescription(mealDetailsModels.getDescription());
        productsBean.setEat_in(mealDetailsModels.getEat_in());
        productsBean.setId(mealDetailsModels.getId());
        productsBean.setEcom_product_image(mealDetailsModels.getEcom_product_image());
        productsBean.setIs_customizable(mealDetailsModels.getIs_customizable());
        productsBean.setMenu_product_id(mealDetailsModels.getMenu_product_id());
        productsBean.setMenu_product_price(mealDetailsModels.getMenu_product_price());
        List<MealDetailsSaveModel.MealConfigBean.ProductsBean.MenuProductSizeBean> menuProductSizeBeanList = new ArrayList<>();
        for (int k = 0; k < mealDetailsModels.getMenu_product_size().size(); k++) {
            MealDetailsSaveModel.MealConfigBean.ProductsBean.MenuProductSizeBean menuProductSizeBean = new MealDetailsSaveModel.MealConfigBean.ProductsBean.MenuProductSizeBean();
            menuProductSizeBean.setProduct_size_id(mealDetailsModels.getMenu_product_size().get(k).getProduct_size_id());
            menuProductSizeBean.setProduct_size_name(mealDetailsModels.getMenu_product_size().get(k).getProduct_size_name());
            menuProductSizeBean.setProduct_size_price(mealDetailsModels.getMenu_product_size().get(k).getProduct_size_price());
            List<MealDetailsSaveModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean> sizeModifiersBeanArrayList = new ArrayList<>();
            for (int l = 0; l < mealDetailsModels.getMenu_product_size().get(k).getSize_modifiers().size(); l++) {
                MealDetailsSaveModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean sizeModifiersBean = new MealDetailsSaveModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean();
                sizeModifiersBean.setFree_qty_limit(mealDetailsModels.getMenu_product_size().get(k).getSize_modifiers().get(l).getFree_qty_limit());
                sizeModifiersBean.setMax_allowed_quantity(mealDetailsModels.getMenu_product_size().get(k).getSize_modifiers().get(l).getFree_qty_limit());
                sizeModifiersBean.setMin_allowed_quantity(mealDetailsModels.getMenu_product_size().get(k).getSize_modifiers().get(l).getMin_allowed_quantity());
                sizeModifiersBean.setModifier_id(mealDetailsModels.getMenu_product_size().get(k).getSize_modifiers().get(l).getModifier_id());
                sizeModifiersBean.setModifier_name(mealDetailsModels.getMenu_product_size().get(k).getSize_modifiers().get(l).getModifier_name());
                sizeModifiersBean.setModifier_type(mealDetailsModels.getMenu_product_size().get(k).getSize_modifiers().get(l).getModifier_type());
                sizeModifiersBean.setNoOfCount(mealDetailsModels.getMenu_product_size().get(k).getSize_modifiers().get(l).getNoOfCount());
                List<MealDetailsSaveModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean.SizeModifierProductsBean> sizeModifierProductsBeanList = new ArrayList<>();
                for (int m = 0; m < mealDetailsModels.getMenu_product_size().get(k).getSize_modifiers().get(l).getSize_modifier_products().size(); m++) {
                    MealDetailsSaveModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean.SizeModifierProductsBean sizeModifierProductsBean = new MealDetailsSaveModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean.SizeModifierProductsBean();
                    sizeModifierProductsBean.setDescription(mealDetailsModels.getMenu_product_size().get(k).getSize_modifiers().get(l).getSize_modifier_products().get(m).getDescription());
                    sizeModifierProductsBean.setModifier_product_price(mealDetailsModels.getMenu_product_size().get(k).getSize_modifiers().get(l).getSize_modifier_products().get(m).getModifier_product_price());
                    sizeModifierProductsBean.setNoOfCount(mealDetailsModels.getMenu_product_size().get(k).getSize_modifiers().get(l).getSize_modifier_products().get(m).getNoOfCount());
                    sizeModifierProductsBean.setProduct_id(mealDetailsModels.getMenu_product_size().get(k).getSize_modifiers().get(l).getSize_modifier_products().get(m).getProduct_id());
                    sizeModifierProductsBean.setProduct_name(mealDetailsModels.getMenu_product_size().get(k).getSize_modifiers().get(l).getSize_modifier_products().get(m).getProduct_name());
                    sizeModifierProductsBean.setUnit(mealDetailsModels.getMenu_product_size().get(k).getSize_modifiers().get(l).getSize_modifier_products().get(m).getUnit());
                    sizeModifierProductsBeanList.add(sizeModifierProductsBean);
                }

                sizeModifiersBean.setSize_modifier_products(sizeModifierProductsBeanList);
                sizeModifiersBeanArrayList.add(sizeModifiersBean);
            }
            menuProductSizeBean.setSize_modifiers(sizeModifiersBeanArrayList);
            menuProductSizeBeanList.add(menuProductSizeBean);
        }
        productsBean.setMenu_product_size(menuProductSizeBeanList);
        productsBean.setNoOfCount(1);
        productsBean.setProduct_id(mealDetailsModels.getProduct_id());
        productsBean.setProduct_modifiers(mealDetailsModels.getProduct_modifiers());
        productsBean.setProduct_name(mealDetailsModels.getProduct_name());
        productsBean.setProduct_overall_rating(mealDetailsModels.getProduct_overall_rating());
        productsBean.setProduct_size_id(mealDetailsModels.getProduct_size_id());
        productsBean.setProduct_size_name(mealDetailsModels.getProduct_size_name());
        productsBean.setProudct_delivery(mealDetailsModels.getProudct_delivery());
        productsBean.setSelling_price(mealDetailsModels.getSelling_price());
        productsBean.setSize_title(mealDetailsModels.getSize_title());
        productsBean.setTake_away(mealDetailsModels.getTake_away());
        productsBean.setUserapp_product_image(mealDetailsModels.getUserapp_product_image());
        productsBean.setVeg_type(mealDetailsModels.getVeg_type());
        //productsBeanList.add(productsBean);

        mealDetailsModelSave.getMeal_config().get(positionParent).getProducts().add(productsBean);
    }

    private void updateTotoalPrice() {
        double finalPrice = Double.parseDouble(mealDetailsModelSave.getMeal().getMeal_price());
        for (int b = 0; b < mealDetailsModelSave.getMeal_config().size(); b++) {
            for (int c = 0; c < mealDetailsModelSave.getMeal_config().get(b).getProducts().size(); c++) {
                for (int d = 0; d < mealDetailsModelSave.getMeal_config().get(b).getProducts().get(c).getNoOfCount(); d++) {
                    if (mealDetailsModelSave.getMeal_config().get(b).getProducts().get(c).getMenu_product_size() != null && mealDetailsModelSave.getMeal_config().get(b).getProducts().get(c).getMenu_product_size().size() > 0) {
                        for (int i = 0; i < mealDetailsModelSave.getMeal_config().get(b).getProducts().get(c).getMenu_product_size().get(0).getSize_modifiers().size(); i++) {
                            int noOfFree = mealDetailsModelSave.getMeal_config().get(b).getProducts().get(c).getMenu_product_size().get(0).getSize_modifiers().get(i).getFree_qty_limit();
                            for (int j = 0; j < mealDetailsModelSave.getMeal_config().get(b).getProducts().get(c).getMenu_product_size().get(0).getSize_modifiers().get(i).getSize_modifier_products().size(); j++) {
                                for (int a = 0; a < mealDetailsModelSave.getMeal_config().get(b).getProducts().get(c).getMenu_product_size().get(0).getSize_modifiers().get(i).getSize_modifier_products().get(j).getNoOfCount(); a++) {
                                    if (noOfFree == 0) {
                                        finalPrice = finalPrice + Double.parseDouble(mealDetailsModelSave.getMeal_config().get(b).getProducts().get(c).getMenu_product_size().get(0).getSize_modifiers().get(i).getSize_modifier_products().get(j).getModifier_product_price());

                                    } else {
                                        noOfFree = noOfFree - 1;
                                    }
                                }

                            }
                        }

                    }
                }
            }

        }
        finalPriceAll = finalPrice;
        total_price.setText(String.format("%.2f", finalPrice));
        menuMealListAdapter.notifyDataSetChanged();

    }


    public void copyValueInDiffrentModel() {
        mealDetailsModelSave = new MealDetailsSaveModel();
        MealDetailsSaveModel.MealBean mealBean = new MealDetailsSaveModel.MealBean();
        List<MealDetailsSaveModel.MealConfigBean> mealConfigBean = new ArrayList<>();

        for (int i = 0; i < mealDetailsModel.getMeal_config().size(); i++) {
            MealDetailsSaveModel.MealConfigBean mealConfigBean1 = new MealDetailsSaveModel.MealConfigBean();
            mealConfigBean1.setAllowed_quantity(mealDetailsModel.getMeal_config().get(i).getAllowed_quantity());
            mealConfigBean1.setCategoryslugname(mealDetailsModel.getMeal_config().get(i).getCategoryslugname());
            mealConfigBean1.setIs_customizable(mealDetailsModel.getMeal_config().get(i).getIs_customizable());
            mealConfigBean1.setName(mealDetailsModel.getMeal_config().get(i).getName());
            mealConfigBean1.setProduct_quantity(mealDetailsModel.getMeal_config().get(i).getProduct_quantity());
            mealConfigBean1.setProduct_size_name(mealDetailsModel.getMeal_config().get(i).getProduct_size_name());
            List<MealDetailsSaveModel.MealConfigBean.ProductsBean> productsBeanList = new ArrayList<>();
            for (int j = 0; j < mealDetailsModel.getMeal_config().get(i).getProducts().size(); j++) {
                MealDetailsSaveModel.MealConfigBean.ProductsBean productsBean = new MealDetailsSaveModel.MealConfigBean.ProductsBean();
                productsBean.setAllowed_quantity(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getAllowed_quantity());
                productsBean.setCategory_name(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getCategory_name());
                productsBean.setCategory_name_slug(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getCategory_name_slug());
                productsBean.setDescription(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getDescription());
                productsBean.setEat_in(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getEat_in());
                productsBean.setId(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getId());
                productsBean.setEcom_product_image(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getEcom_product_image());
                productsBean.setIs_customizable(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getIs_customizable());
                productsBean.setMenu_product_id(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_id());
                productsBean.setMenu_product_price(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_price());
                List<MealDetailsSaveModel.MealConfigBean.ProductsBean.MenuProductSizeBean> menuProductSizeBeanList = new ArrayList<>();
                for (int k = 0; k < mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().size(); k++) {
                    MealDetailsSaveModel.MealConfigBean.ProductsBean.MenuProductSizeBean menuProductSizeBean = new MealDetailsSaveModel.MealConfigBean.ProductsBean.MenuProductSizeBean();
                    menuProductSizeBean.setProduct_size_id(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getProduct_size_id());
                    menuProductSizeBean.setProduct_size_name(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getProduct_size_name());
                    menuProductSizeBean.setProduct_size_price(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getProduct_size_price());
                    List<MealDetailsSaveModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean> sizeModifiersBeanArrayList = new ArrayList<>();
                    for (int l = 0; l < mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().size(); l++) {
                        MealDetailsSaveModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean sizeModifiersBean = new MealDetailsSaveModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean();
                        sizeModifiersBean.setFree_qty_limit(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().get(l).getFree_qty_limit());
                        sizeModifiersBean.setMax_allowed_quantity(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().get(l).getFree_qty_limit());
                        sizeModifiersBean.setMin_allowed_quantity(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().get(l).getMin_allowed_quantity());
                        sizeModifiersBean.setModifier_id(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().get(l).getModifier_id());
                        sizeModifiersBean.setModifier_name(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().get(l).getModifier_name());
                        sizeModifiersBean.setModifier_type(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().get(l).getModifier_type());
                        sizeModifiersBean.setNoOfCount(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().get(l).getNoOfCount());
                        List<MealDetailsSaveModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean.SizeModifierProductsBean> sizeModifierProductsBeanList = new ArrayList<>();
                        for (int m = 0; m < mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().get(l).getSize_modifier_products().size(); m++) {
                            MealDetailsSaveModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean.SizeModifierProductsBean sizeModifierProductsBean = new MealDetailsSaveModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean.SizeModifierProductsBean();
                            sizeModifierProductsBean.setDescription(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().get(l).getSize_modifier_products().get(m).getDescription());
                            sizeModifierProductsBean.setModifier_product_price(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().get(l).getSize_modifier_products().get(m).getModifier_product_price());
                            sizeModifierProductsBean.setNoOfCount(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().get(l).getSize_modifier_products().get(m).getNoOfCount());
                            sizeModifierProductsBean.setProduct_id(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().get(l).getSize_modifier_products().get(m).getProduct_id());
                            sizeModifierProductsBean.setProduct_name(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().get(l).getSize_modifier_products().get(m).getProduct_name());
                            sizeModifierProductsBean.setUnit(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getMenu_product_size().get(k).getSize_modifiers().get(l).getSize_modifier_products().get(m).getUnit());
                            sizeModifierProductsBeanList.add(sizeModifierProductsBean);
                        }

                        sizeModifiersBean.setSize_modifier_products(sizeModifierProductsBeanList);
                        sizeModifiersBeanArrayList.add(sizeModifiersBean);
                    }
                    menuProductSizeBean.setSize_modifiers(sizeModifiersBeanArrayList);
                    menuProductSizeBeanList.add(menuProductSizeBean);
                }
                productsBean.setMenu_product_size(menuProductSizeBeanList);
                productsBean.setNoOfCount(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getNoOfCount());
                productsBean.setProduct_id(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getProduct_id());
                productsBean.setProduct_modifiers(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getProduct_modifiers());
                productsBean.setProduct_name(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getProduct_name());
                productsBean.setProduct_overall_rating(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getProduct_overall_rating());
                productsBean.setProduct_size_id(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getProduct_size_id());
                productsBean.setProduct_size_name(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getProduct_size_name());
                productsBean.setProudct_delivery(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getProudct_delivery());
                productsBean.setSelling_price(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getSelling_price());
                productsBean.setSize_title(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getSize_title());
                productsBean.setTake_away(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getTake_away());
                productsBean.setUserapp_product_image(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getUserapp_product_image());
                productsBean.setVeg_type(mealDetailsModel.getMeal_config().get(i).getProducts().get(j).getVeg_type());
                productsBeanList.add(productsBean);
            }
            mealConfigBean1.setProducts(productsBeanList);
            mealConfigBean1.setSelling_price(mealDetailsModel.getMeal_config().get(i).getSelling_price());
            mealConfigBean.add(mealConfigBean1);
        }
        mealDetailsModelSave.setMeal_config(mealConfigBean);
        mealBean.setVeg_type(mealDetailsModel.getMeal().getVeg_type());
        mealBean.setRestaurant_id(mealDetailsModel.getMeal().getRestaurant_id());
        mealBean.setMenu_category_id(mealDetailsModel.getMeal().getMenu_category_id());
        mealBean.setMeal_price(mealDetailsModel.getMeal().getMeal_price());
        mealBean.setMeal_name(mealDetailsModel.getMeal().getMeal_name());
        mealBean.setId(mealDetailsModel.getMeal().getId());
        mealBean.setDescription(mealDetailsModel.getMeal().getDescription());
        mealDetailsModelSave.setMeal(mealBean);


    }
}
