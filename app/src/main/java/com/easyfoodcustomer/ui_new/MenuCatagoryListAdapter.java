package com.easyfoodcustomer.ui_new;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.easyfoodcustomer.R;
import com.easyfoodcustomer.adapters.menu_adapter.ItemClickListener;
import com.easyfoodcustomer.adapters.menu_adapter.MealProductAdapter;
import com.easyfoodcustomer.adapters.menu_adapter.MealProductCategoryAdapter;
import com.easyfoodcustomer.adapters.menu_adapter.OnMealProductItemSelect;
import com.easyfoodcustomer.adapters.menu_adapter.RecyclerLayoutManager;
import com.easyfoodcustomer.cart_db.tables.ProductSizeAndModifier;
import com.easyfoodcustomer.dialogs.MenuMealDialog;
import com.easyfoodcustomer.dialogs.MenuMealModifyDialog;
import com.easyfoodcustomer.modelsNew.MealDetailsModel;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MealProduct;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;
import com.easyfoodcustomer.ui_new.interfaces.MenuProductModifierInterface;
import com.easyfoodcustomer.utility.GlobalValues;

import java.util.ArrayList;
import java.util.List;

public class MenuCatagoryListAdapter extends RecyclerView.Adapter<MenuCatagoryListAdapter.MyViewHolder> {

    private Context context;
    private View.OnClickListener onClickListener;
    private List<MealDetailsModel.MealConfigBean.ProductsBean> products;
    private MealDetailsModel mealDetailsModel;
    private MealDetailsModel.MealConfigBean mealConfigBean;
    private MenuProductModifierInterface menuProductModifierInterface;
    private int parentPosition;
    private double finalPriceAll;

    public MenuCatagoryListAdapter(Context context, View.OnClickListener onClickListener,
                                   List<MealDetailsModel.MealConfigBean.ProductsBean> products, MealDetailsModel mealDetailsModel,
                                   MealDetailsModel.MealConfigBean mealConfigBean, MenuProductModifierInterface menuProductModifierInterface,
                                   int parentPosition, double finalPriceAll) {
        this.context = context;
        this.onClickListener = onClickListener;
        this.products = products;
        this.mealDetailsModel = mealDetailsModel;
        this.mealConfigBean = mealConfigBean;
        this.menuProductModifierInterface = menuProductModifierInterface;
        this.parentPosition = parentPosition;
        this.finalPriceAll = finalPriceAll;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.new_meal_product_row, viewGroup, false);
        return new MenuCatagoryListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuCatagoryListAdapter.MyViewHolder holder, int position) {

        holder.tvItemTitle.setText(products.get(position).getProduct_name() + " " + products.get(position).getProduct_size_name());
        if (!products.get(position).getSelling_price().isEmpty() && products.get(position).getSelling_price() != null) {
            if (Double.parseDouble(products.get(position).getSelling_price()) > 0) {
                holder.tvItemPrice.setText("£" + products.get(position).getSelling_price());
                holder.tvItemPrice.setVisibility(View.VISIBLE);
            }
        }

        holder.itemCountAll.setText(String.valueOf(products.get(position).getNoOfCount()));
        holder.itemRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (products.get(position).getNoOfCount() > 0) {
                    menuProductModifierInterface.updateMeanProductSizeModifier(parentPosition, position, false, products.get(position).getNoOfCount(), products.get(position));

                }
            }
        });

        holder.itemAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mealConfigBean.getAllowed_quantity() > 0) {

                    if (getCurrentnumberOfCount(products) < mealConfigBean.getAllowed_quantity()) {
                        if (products.get(position).getMenu_product_size() != null && products.get(position).getMenu_product_size().size() > 0) {
                            new MenuMealModifyDialog(context, products.get(position), mealDetailsModel, mealConfigBean, menuProductModifierInterface, position, parentPosition, finalPriceAll).show();

                        } else {
                            menuProductModifierInterface.updateMeanProductSizeModifier(parentPosition, position, true, products.get(position).getNoOfCount(), products.get(position));

                        }

                    } else {
                        Toast.makeText(context, "You reached out to your maximum Limit", Toast.LENGTH_LONG).show();
                    }
                }else {
                    if (products.get(position).getMenu_product_size() != null && products.get(position).getMenu_product_size().size() > 0) {
                        new MenuMealModifyDialog(context, products.get(position), mealDetailsModel, mealConfigBean, menuProductModifierInterface, position, parentPosition, finalPriceAll).show();

                    } else {
                        menuProductModifierInterface.updateMeanProductSizeModifier(parentPosition, position, true, products.get(position).getNoOfCount(), products.get(position));

                    }
                }


            }
        });
    }

    private int getCurrentnumberOfCount(List<MealDetailsModel.MealConfigBean.ProductsBean> productsBean) {
        int nofoCount = 0;
        for (int i = 0; i < productsBean.size(); i++) {
            nofoCount = nofoCount + productsBean.get(i).getNoOfCount();
        }


        return nofoCount;
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemTitle, tvItemPrice, itemCountAll;
        private LinearLayout itemRemove, itemAdd;
        private final ProgressBar progressBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvItemTitle = itemView.findViewById(R.id.tv_productname);
            tvItemPrice = itemView.findViewById(R.id.txtprice);
            itemCountAll = itemView.findViewById(R.id.item_count_all);

            itemRemove = itemView.findViewById(R.id.item_remove);
            itemAdd = itemView.findViewById(R.id.item_add);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }


}
