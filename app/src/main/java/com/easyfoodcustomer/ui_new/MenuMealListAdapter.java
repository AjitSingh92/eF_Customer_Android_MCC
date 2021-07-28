package com.easyfoodcustomer.ui_new;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.easyfoodcustomer.R;
import com.easyfoodcustomer.adapters.menu_adapter.ItemClickListener;
import com.easyfoodcustomer.adapters.menu_adapter.MealProductAdapter;
import com.easyfoodcustomer.adapters.menu_adapter.OnMealProductItemSelect;
import com.easyfoodcustomer.adapters.menu_adapter.RecyclerLayoutManager;
import com.easyfoodcustomer.dialogs.MenuDetailDialog;
import com.easyfoodcustomer.modelsNew.MealDetailsModel;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MealCategory;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;
import com.easyfoodcustomer.ui_new.interfaces.MenuProductModifierInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuMealListAdapter extends RecyclerView.Adapter<MenuMealListAdapter.MyViewHolder> {

    private Context context;
    private View.OnClickListener onClickListener;
    private List<MealDetailsModel.MealConfigBean> meal_config;
    private MealDetailsModel mealDetailsModel;
    private MenuProductModifierInterface menuProductModifierInterface;
    private double finalPriceAll;

    public MenuMealListAdapter(Context context, View.OnClickListener onClickListener,
                               List<MealDetailsModel.MealConfigBean> meal_config, MealDetailsModel mealDetailsModel,
                               MenuProductModifierInterface menuProductModifierInterface, double finalPriceAll) {
        this.context=context;
        this.onClickListener=onClickListener;
        this.meal_config=meal_config;
        this.mealDetailsModel=mealDetailsModel;
        this.menuProductModifierInterface=menuProductModifierInterface;
        this.finalPriceAll=finalPriceAll;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.meal_product_category_row, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvItemTitle.setText(meal_config.get(position).getName());
        holder.tvItemPick.setText("Pick " + meal_config.get(position).getAllowed_quantity());

        List<MealDetailsModel.MealConfigBean.ProductsBean> products =new ArrayList<>();
        for (int i=0;i<meal_config.get(position).getProducts().size();i++)
        {
            if (!meal_config.get(position).getProducts().get(i).isShown())
            {
                products.add(meal_config.get(position).getProducts().get(i));
            }
        }
        MenuCatagoryListAdapter  menuCatagoryListAdapter = new MenuCatagoryListAdapter(context, onClickListener,products,mealDetailsModel,meal_config.get(position),menuProductModifierInterface,position,finalPriceAll);
        holder.listProduct.setAdapter(menuCatagoryListAdapter);
        holder.alertMsg.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return meal_config.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemTitle, tvItemPick, alertMsg;
        private RecyclerView listProduct;
        private RecyclerLayoutManager layoutManager;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemTitle = itemView.findViewById(R.id.tv_ItemTitle);
            tvItemPick = itemView.findViewById(R.id.tv_ItemPick);
            alertMsg = itemView.findViewById(R.id.tv_alertMsg);
            listProduct = itemView.findViewById(R.id.listProduct);

            layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
            layoutManager.setScrollEnabled(false);
            listProduct.setLayoutManager(layoutManager);
        }
    }


}
