package com.easyfoodcustomer.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.easyfoodcustomer.R;
import com.easyfoodcustomer.modelsNew.MealDetailsModel;
import com.easyfoodcustomer.ui_new.MenuMealListAdapter;
import com.easyfoodcustomer.ui_new.MenuProductSizeAdapter;
import com.easyfoodcustomer.ui_new.interfaces.MenuProductModifierInterface;
import com.easyfoodcustomer.ui_new.interfaces.MenuProductSizeModifierInterface;

import java.text.DecimalFormat;

public class MenuMealModifyDialog extends Dialog implements View.OnClickListener, MenuProductSizeModifierInterface {

    private Context context;
    private TextView txt_category;
    private TextView BasePrice;
    private TextView AmountToPay;
    private TextView Option;
    private TextView title;
    private TextView sign_up_btn_dialog;
    private TextView total_price;
    private ImageView cross_tv;
    private RecyclerView list_meal_category;
    private RecyclerView product_modifier;
    private LinearLayout titleLayout;
    private LinearLayout add_items;
    private CheckBox itemSelected;
    private MealDetailsModel.MealConfigBean.ProductsBean mealDetailsModel;
    private MealDetailsModel detailsModel;
    private MealDetailsModel.MealConfigBean mealConfigBean;

    private MenuProductSizeAdapter menuProductSizeAdapter;
    private MenuProductModifierInterface menuProductModifierInterface;
    private int positionParent;
    private int positionChild;

    private boolean isSelected;
    private double finalPriceAll;


    public MenuMealModifyDialog(@NonNull Context context, MealDetailsModel.MealConfigBean.ProductsBean mealDetailsModel,
                                MealDetailsModel detailsModel, MealDetailsModel.MealConfigBean mealConfigBean,
                                MenuProductModifierInterface menuProductModifierInterface, int positionChild,
                                int positionParent, double finalPriceAll) {
        super(context);
        this.context = context;
        this.mealDetailsModel = mealDetailsModel;
        this.detailsModel = detailsModel;
        this.mealConfigBean = mealConfigBean;
        this.menuProductModifierInterface = menuProductModifierInterface;
        this.positionParent = positionParent;
        this.positionChild = positionChild;
        this.finalPriceAll = finalPriceAll;
        isSelected = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.color.transprent);
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflate.inflate(R.layout.dialog_menu_product_modifire, null);
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
        Option = layout.findViewById(R.id.Option);
        title = layout.findViewById(R.id.title);
        sign_up_btn_dialog = layout.findViewById(R.id.sign_up_btn_dialog);
        total_price = layout.findViewById(R.id.total_price);
        cross_tv = layout.findViewById(R.id.cross_tv);
        list_meal_category = layout.findViewById(R.id.list_meal_category);
        product_modifier = layout.findViewById(R.id.product_modifier);
        add_items = layout.findViewById(R.id.add_items);
        titleLayout = layout.findViewById(R.id.titleLayout);
        itemSelected = layout.findViewById(R.id.itemSelected);

        list_meal_category.setLayoutManager(new LinearLayoutManager(context));
        product_modifier.setLayoutManager(new LinearLayoutManager(context));


        BasePrice.setText("Base Price\n" + context.getResources().getString(R.string.currency) + String.format("%.2f", Double.parseDouble(detailsModel.getMeal().getMeal_price())));
        AmountToPay.setText("Amount to pay\n" + context.getResources().getString(R.string.currency) + String.format("%.2f", Double.parseDouble(detailsModel.getMeal().getMeal_price())));

        Option.setText("Option for " + mealDetailsModel.getProduct_name());
        txt_category.setText(detailsModel.getMeal().getMeal_name());

        title.setText(mealConfigBean.getProduct_size_name());

        cross_tv.setOnClickListener(this);
        add_items.setOnClickListener(this);

        menuProductSizeAdapter = new MenuProductSizeAdapter(context, this, detailsModel, mealDetailsModel.getMenu_product_size().get(0).getSize_modifiers(), this);
        list_meal_category.setAdapter(menuProductSizeAdapter);

        setUpdatedPrice();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cross_tv:
                dismiss();
                break;

            case R.id.add_items:
                if (isSelected) {
                    menuProductModifierInterface.updateMeanProductSizeModifier(positionParent, positionChild, true, mealDetailsModel.getNoOfCount(), mealDetailsModel);
                    dismiss();
                } else {
                    Toast.makeText(context, "Please select One item", Toast.LENGTH_LONG).show();
                }

                break;


            default:
                break;
        }

    }

    @Override
    public void updateMeanProductSizeModifier(int positionParentL, int positionChildL, boolean isIncrease, int previousCount, boolean isSingle) {
        if (isIncrease) {
            if (isSingle) {
                for (int i = 0; i < mealDetailsModel.getMenu_product_size().get(0).getSize_modifiers().get(positionParentL).getSize_modifier_products().size(); i++) {
                    mealDetailsModel.getMenu_product_size().get(0).getSize_modifiers().get(positionParentL).getSize_modifier_products().get(i).setNoOfCount(0);

                }
            }
            mealDetailsModel.getMenu_product_size().get(0).getSize_modifiers().get(positionParentL).getSize_modifier_products().get(positionChildL).setNoOfCount(previousCount + 1);
        } else {
            mealDetailsModel.getMenu_product_size().get(0).getSize_modifiers().get(positionParentL).getSize_modifier_products().get(positionChildL).setNoOfCount(previousCount - 1);

        }
        menuProductSizeAdapter.notifyDataSetChanged();

        detailsModel.getMeal_config().get(positionParent).getProducts().set(positionChild, mealDetailsModel);

        setUpdatedPrice();
    }

    private void setUpdatedPrice() {
       /* double finalPrice = Double.parseDouble(detailsModel.getMeal().getMeal_price());
        for (int b= 0; b<detailsModel.getMeal_config().size();b++)
        {
            for (int c=0;c<detailsModel.getMeal_config().get(b).getProducts().size();c++)
            {
                for (int d=0; d<detailsModel.getMeal_config().get(b).getProducts().get(c).getNoOfCount();d++)
                {
                    if (detailsModel.getMeal_config().get(b).getProducts().get(c).getMenu_product_size()!=null && detailsModel.getMeal_config().get(b).getProducts().get(c).getMenu_product_size().size()>0)
                    {
                        for (int i = 0; i < detailsModel.getMeal_config().get(b).getProducts().get(c).getMenu_product_size().get(0).getSize_modifiers().size(); i++)
                        {
                            int noOfFree = detailsModel.getMeal_config().get(b).getProducts().get(c).getMenu_product_size().get(0).getSize_modifiers().get(i).getFree_qty_limit();
                            for (int j = 0; j < detailsModel.getMeal_config().get(b).getProducts().get(c).getMenu_product_size().get(0).getSize_modifiers().get(i).getSize_modifier_products().size(); j++) {
                                for (int a = 0; a < detailsModel.getMeal_config().get(b).getProducts().get(c).getMenu_product_size().get(0).getSize_modifiers().get(i).getSize_modifier_products().get(j).getNoOfCount(); a++) {
                                    if (noOfFree == 0) {
                                        finalPrice = finalPrice + Double.parseDouble(detailsModel.getMeal_config().get(b).getProducts().get(c).getMenu_product_size().get(0).getSize_modifiers().get(i).getSize_modifier_products().get(j).getModifier_product_price());

                                    } else {
                                        noOfFree = noOfFree - 1;
                                    }
                                }

                            }
                        }

                    }
                }
            }

        }*/

        double finalPrice = finalPriceAll;
        for (int i = 0; i < mealDetailsModel.getMenu_product_size().get(0).getSize_modifiers().size(); i++) {
            int noOfFree = mealDetailsModel.getMenu_product_size().get(0).getSize_modifiers().get(i).getFree_qty_limit();
            for (int j = 0; j < mealDetailsModel.getMenu_product_size().get(0).getSize_modifiers().get(i).getSize_modifier_products().size(); j++) {
                for (int a = 0; a < mealDetailsModel.getMenu_product_size().get(0).getSize_modifiers().get(i).getSize_modifier_products().get(j).getNoOfCount(); a++) {
                    isSelected = true;
                    if (noOfFree == 0) {
                        finalPrice = finalPrice + Double.parseDouble(mealDetailsModel.getMenu_product_size().get(0).getSize_modifiers().get(i).getSize_modifier_products().get(j).getModifier_product_price());

                    } else {
                        noOfFree = noOfFree - 1;
                    }
                }

            }
        }
        total_price.setText(String.valueOf(new DecimalFormat("##.##").format(finalPrice)));
    }
}
