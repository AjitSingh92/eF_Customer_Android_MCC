package com.easyfoodcustomer.ui_new;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.easyfoodcustomer.R;
import com.easyfoodcustomer.cart_db.DatabaseHelper;
import com.easyfoodcustomer.fragments.MyCartFragment;
import com.easyfoodcustomer.modelsNew.CartProdctListModel;
import com.easyfoodcustomer.modelsNew.MealDetailsModel;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuProductSize;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.Modifier;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.ProductModifier;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.SizeModifier;
import com.easyfoodcustomer.roomData.OrderSaveModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MealCartAdapter extends RecyclerView.Adapter<MealCartAdapter.MyViewHolder> {

    private Context context;
    private View.OnClickListener onClickListener;
    private List<CartProdctListModel> orderSaveModelList;


    public MealCartAdapter(Context context, View.OnClickListener onClickListener,
                           ArrayList<CartProdctListModel> orderSaveModelList) {
        this.context = context;
        this.onClickListener = onClickListener;
        this.orderSaveModelList = orderSaveModelList;

    }

    @NonNull
    @Override
    public MealCartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_meal_cart, viewGroup, false);
        return new MealCartAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MealCartAdapter.MyViewHolder holder, int position) {

        holder.tv_itemcount.setText(String.valueOf(orderSaveModelList.get(position).getItemCount()));
        holder.title.setText(orderSaveModelList.get(position).getMealName());
        if (orderSaveModelList.get(position).getData() != null && !orderSaveModelList.get(position).getData().equalsIgnoreCase("")) {
            holder.price.setText("£" + String.format("%.2f", Double.parseDouble(orderSaveModelList.get(position).getMealPrice())));
        } else {
            holder.price.setText("£" + String.format("%.2f", Double.parseDouble(orderSaveModelList.get(position).getTotalAmoutOfMeal())));

        }
        holder.qty.setText(String.valueOf(orderSaveModelList.get(position).getItemCount()));

        if (orderSaveModelList.get(position).isOpen()) {
            holder.ll_counter.setVisibility(View.VISIBLE);
        } else {
            holder.ll_counter.setVisibility(View.GONE);
        }

        holder.btn_remove.setOnClickListener(onClickListener);
        holder.btn_remove.setTag(position);
        holder.btn_add.setOnClickListener(onClickListener);
        holder.btn_add.setTag(position);
        holder.ly_item.setOnClickListener(onClickListener);
        holder.ly_item.setTag(position);

        if (orderSaveModelList.get(position).getData() != null && !orderSaveModelList.get(position).getData().equalsIgnoreCase("")
                && !orderSaveModelList.get(position).getData().equalsIgnoreCase("")) {
            MealDetailsModel mealDetailsModel = new Gson().fromJson(orderSaveModelList.get(position).getData(), MealDetailsModel.class);
            /*if (mealDetailsModel!=null && mealDetailsModel.getMeal_config().get(0).getIs_customizable()!=0)
            {
                holder.price.setVisibility(View.VISIBLE);
            }else
            {
                holder.price.setVisibility(View.GONE);
            }*/
            holder.modifiers.removeAllViews();
            for (int b = 0; b < mealDetailsModel.getMeal_config().size(); b++) {
                for (int c = 0; c < mealDetailsModel.getMeal_config().get(b).getProducts().size(); c++) {
                    if (mealDetailsModel.getMeal_config().get(b).getProducts().get(c).getNoOfCount() > 0) {
                        View _view = LayoutInflater.from(context).inflate(R.layout.item_modifier, null);
                        ((TextView) _view.findViewById(R.id.tv_title)).setText(mealDetailsModel.getMeal_config().get(b).getProducts().get(c).getNoOfCount() + "x " + mealDetailsModel.getMeal_config().get(b).getProducts().get(c).getProduct_size_name() + " " + mealDetailsModel.getMeal_config().get(b).getProducts().get(c).getProduct_name());
                        ((TextView) _view.findViewById(R.id.tv_title)).setTextColor(context.getResources().getColor(R.color.black));
                        ((TextView) _view.findViewById(R.id.tv_price)).setVisibility(View.VISIBLE);
                        if (mealDetailsModel.getMeal_config().get(b).getProducts().get(c).getSelling_price() != null && !mealDetailsModel.getMeal_config().get(b).getProducts().get(c).getSelling_price().trim().isEmpty())
                            ((TextView) _view.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format("%.2f", Double.parseDouble(mealDetailsModel.getMeal_config().get(b).getProducts().get(c).getSelling_price())));
                        else {
                            if (mealDetailsModel.getMeal_config().get(b).getProducts().get(c).getMenu_product_price() != null && !mealDetailsModel.getMeal_config().get(b).getProducts().get(c).getMenu_product_price().trim().isEmpty()) {
                                Double price = Double.parseDouble(mealDetailsModel.getMeal_config().get(b).getProducts().get(c).getMenu_product_price()) * mealDetailsModel.getMeal_config().get(b).getProducts().get(c).getNoOfCount();
                                ((TextView) _view.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format("%.2f", price));

                            } else {
                                ((TextView) _view.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + "0.00");

                            }
                        }

                        holder.modifiers.addView(_view);
                    }

                    for (int d = 0; d < mealDetailsModel.getMeal_config().get(b).getProducts().get(c).getNoOfCount(); d++) {
                        if (mealDetailsModel.getMeal_config().get(b).getProducts().get(c).getMenu_product_size() != null && mealDetailsModel.getMeal_config().get(b).getProducts().get(c).getMenu_product_size().size() > 0) {
                            for (int i = 0; i < mealDetailsModel.getMeal_config().get(b).getProducts().get(c).getMenu_product_size().get(0).getSize_modifiers().size(); i++) {
                                int noOfFree = mealDetailsModel.getMeal_config().get(b).getProducts().get(c).getMenu_product_size().get(0).getSize_modifiers().get(i).getFree_qty_limit();
                                for (int j = 0; j < mealDetailsModel.getMeal_config().get(b).getProducts().get(c).getMenu_product_size().get(0).getSize_modifiers().get(i).getSize_modifier_products().size(); j++) {
                                    if (mealDetailsModel.getMeal_config().get(b).getProducts().get(c).getMenu_product_size().get(0).getSize_modifiers().get(i).getSize_modifier_products().get(j).getNoOfCount() > 0) {
                                        View view = LayoutInflater.from(context).inflate(R.layout.item_modifier, null);
                                        ((TextView) view.findViewById(R.id.tv_title)).setText(mealDetailsModel.getMeal_config().get(b).getProducts().get(c).getMenu_product_size().get(0).getSize_modifiers().get(i).getSize_modifier_products().get(j).getNoOfCount() + "x " + mealDetailsModel.getMeal_config().get(b).getProducts().get(c).getMenu_product_size().get(0).getSize_modifiers().get(i).getSize_modifier_products().get(j).getProduct_name());
                                        //((TextView) view.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format("%.2f", (mealDetailsModel.getMeal_config().get(b).getProducts().get(c).getMenu_product_size().get(0).getSize_modifiers().get(i).getSize_modifier_products().get(j).getNoOfCount() * Double.parseDouble(mealDetailsModel.getMeal_config().get(b).getProducts().get(c).getMenu_product_size().get(0).getSize_modifiers().get(i).getSize_modifier_products().get(j).getModifier_product_price()))));

                                        Double price = mealDetailsModel.getMeal_config().get(b).getProducts().get(c).getMenu_product_size().get(0).getSize_modifiers().get(i).getSize_modifier_products().get(j).getNoOfCount() * Double.parseDouble(mealDetailsModel.getMeal_config().get(b).getProducts().get(c).getMenu_product_size().get(0).getSize_modifiers().get(i).getSize_modifier_products().get(j).getModifier_product_price());

                                        ((TextView) view.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format("%.2f", price));


                                        holder.modifiers.addView(view);
                                    }


                                }
                            }

                        }
                    }
                }

            }
        }


    }

    @Override
    public int getItemCount() {
        return orderSaveModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout ly_item;
        private TextView tv_itemcount;
        private TextView title;
        private TextView price;
        private LinearLayout modifiers;
        private TextView qty;
        private LinearLayout ll_counter;
        private LinearLayout btn_remove;
        private LinearLayout btn_add;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ly_item = itemView.findViewById(R.id.ly_item);
            tv_itemcount = itemView.findViewById(R.id.tv_itemcount);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            modifiers = itemView.findViewById(R.id.modifiers);
            qty = itemView.findViewById(R.id.qty);
            ll_counter = itemView.findViewById(R.id.ll_counter);
            btn_remove = itemView.findViewById(R.id.btn_remove);
            btn_add = itemView.findViewById(R.id.btn_add);


        }
    }
}