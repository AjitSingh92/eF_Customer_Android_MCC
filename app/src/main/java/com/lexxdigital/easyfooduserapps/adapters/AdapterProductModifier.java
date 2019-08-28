package com.lexxdigital.easyfooduserapps.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.menu_category.MenuProduct;

import java.util.ArrayList;

public class AdapterProductModifier extends RecyclerView.Adapter<AdapterProductModifier.MyViewHolder> implements ProductChooseAdapter.PositionInterface{

    // private ArrayList<Arraylist> dataSet;
    ProductChooseAdapter mChooseAdapter;
    ArrayList<String> check2 ;
    ProductChooseAdapter.PositionInterface mPositionInterface;
    Context mContext;
    MenuProduct mProduct;
    int childPosition;
    TextView popupTotalPrice;
    double totalPrice;
    ArrayList<com.lexxdigital.easyfooduserapps.restaurant_details.model.show_menu_category.ProductModifier> showCartProductModifier ;
    ArrayList<com.lexxdigital.easyfooduserapps.restaurant_details.model.show_menu_category.ModifierProduct> showCartModifierProduct;
    @Override
    public void onClickPos(int pos, ArrayList<String> check) {
        mChooseAdapter.notifyDataSetChanged();

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameModifier;
        RecyclerView listModifiers;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.listModifiers = itemView.findViewById(R.id.modifiers_list);
                this.nameModifier = (TextView) itemView.findViewById(R.id.name_tv);

        }
    }

    public AdapterProductModifier(Context mContext, MenuProduct product, int childPos, ArrayList<com.lexxdigital.easyfooduserapps.restaurant_details.model.show_menu_category.ProductModifier> cartProductModifier, TextView price, double tPrice) {

        this.mContext= mContext;
        this.childPosition = childPos;
        this.mProduct = product;
        this.showCartProductModifier = cartProductModifier;
        this.totalPrice = tPrice;
        this.popupTotalPrice = price;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_size_modifier, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
       mPositionInterface=this;
        check2 = new ArrayList<>();
        check2.clear();
        showCartModifierProduct = new ArrayList<>();

        holder.nameModifier.setText(mProduct.getProductModifiers().get(listPosition).getModifierName());
        for(int i=0;i<mProduct.getProductModifiers().get(listPosition).getModifierProducts().size();i++){
            check2.add("0");
        }
        mChooseAdapter = new ProductChooseAdapter(mContext, mPositionInterface,check2,mProduct,listPosition,showCartModifierProduct,popupTotalPrice,totalPrice);
        @SuppressLint("WrongConstant")
        LinearLayoutManager horizontalLayoutManagaer2
                = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        holder.listModifiers.setLayoutManager(horizontalLayoutManagaer2);
        holder.listModifiers.setAdapter(mChooseAdapter);
        showCartProductModifier.add(new com.lexxdigital.easyfooduserapps.restaurant_details.model.show_menu_category.ProductModifier(mProduct.getProductModifiers().get(listPosition).getModifierName(),mProduct.getProductModifiers().get(listPosition).getModifierType(),mProduct.getProductModifiers().get(listPosition).getModifierId(),mProduct.getProductModifiers().get(listPosition).getMinAllowedQuantity(),mProduct.getProductModifiers().get(listPosition).getMaxAllowedQuantity(),showCartModifierProduct));
//        Log.e("MODI >> LEN >>",""+showCartProductModifier.size());
//        Log.e("MODI >> LEN >>",""+showCartModifierProduct.size());

    }

    @Override
    public int getItemCount() {
        return mProduct.getProductModifiers().size();
    }}