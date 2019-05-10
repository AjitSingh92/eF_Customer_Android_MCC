package com.lexxdigital.easyfooduserapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.menu_category.MenuProduct;

import java.util.ArrayList;
import java.util.List;

public class ProductChooseAdapter extends RecyclerView.Adapter<ProductChooseAdapter.MyViewHolder> {

    // private ArrayList<Arraylist> dataSet;
    Context mContext;
    MenuProduct mProduct;
    ArrayList<String> check;
    ArrayList<String> allReadyCheck;
    int position,lPosition, maxCount=0;
    TextView popupTotalPrice;
    private List<String> arrayListIndex = new ArrayList<>();
    private Double totalPrice,totalPrice2=0.0;
    ProductChooseAdapter.PositionInterface mPositionInterface;
    ArrayList<com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.ModifierProduct> showCartModifierProduct;
    public  interface PositionInterface{
        void onClickPos(int pos, ArrayList<String> check);
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtModifier,txtPrice,proCount;
        ImageView rightImg,not_right_tv;
        CheckBox checkbox;
        LinearLayout lyAddRemove,btnAdd,btnRemove;



        public MyViewHolder(View itemView) {
            super(itemView);

//            this.rightImg = (ImageView) itemView.findViewById(R.id.rightImg);
//            this.not_right_tv = (ImageView) itemView.findViewById(R.id.not_right_tv);
            this.txtModifier = (TextView) itemView.findViewById(R.id.modifier);
            this.txtPrice = (TextView) itemView.findViewById(R.id.price);
            this.checkbox = (CheckBox) itemView.findViewById(R.id.checkBox);
            this.lyAddRemove = (LinearLayout) itemView.findViewById(R.id.add_remove);
            this.btnAdd = (LinearLayout) itemView.findViewById(R.id.btn_add);
            this.btnRemove = (LinearLayout) itemView.findViewById(R.id.btn_remove);
            this.proCount = (TextView) itemView.findViewById(R.id.p_count);
            this.setIsRecyclable(false);
        }
    }

    public ProductChooseAdapter(Context mContext, ProductChooseAdapter.PositionInterface mPositionInterface, ArrayList<String> check, MenuProduct product, int pos,ArrayList<com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.ModifierProduct> cartModifierProduct, TextView price, double tPrice) {

        this.mContext= mContext;
        this.mPositionInterface= mPositionInterface;
        this.check= check;
        this.position = pos;
        this.mProduct = product;
        this.showCartModifierProduct = cartModifierProduct;
        this.totalPrice = tPrice;
        this.popupTotalPrice = price;
    }

    @Override
    public ProductChooseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.choose_multi_item_checkbox, parent, false);

        ProductChooseAdapter.MyViewHolder myViewHolder = new ProductChooseAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ProductChooseAdapter.MyViewHolder holder, final int listPosition) {
//
//        ImageView rightImg = holder.rightImg;
//        ImageView not_right_tv = holder.not_right_tv;

        lPosition = listPosition;
        holder.txtModifier.setText(mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getProductName());

        if(mProduct.getProductModifiers().get(position).getMaxAllowedQuantity() == 0){
            holder.txtPrice.setText("+ £"+mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getModifierProductPrice());
        }else{
            holder.txtPrice.setText("");
        }
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //set your object's last status
                if(isChecked){

                    if(maxCount < mProduct.getProductModifiers().get(position).getMaxAllowedQuantity()){
                        holder.txtPrice.setText("£0.0");
                    }else{
                        holder.txtPrice.setText("£"+mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getModifierProductPrice());
                    }
                    if(mProduct.getProductModifiers().get(position).getMaxAllowedQuantity() > 0){
                        holder.lyAddRemove.setVisibility(View.GONE);
                    }else {
                        holder.lyAddRemove.setVisibility(View.VISIBLE);
                    }
                    holder.proCount.setText("1");
                 //   showCartModifierProduct.add(new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.ModifierProduct(mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getProductId(),mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getUnit(),mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getModifierProductPrice(),mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getProductName()));

                    if(maxCount < mProduct.getProductModifiers().get(position).getMaxAllowedQuantity()) {
                        totalPrice2 = Double.parseDouble(popupTotalPrice.getText().toString()) + 0.0;

                    }else{
                        totalPrice2 = Double.parseDouble(popupTotalPrice.getText().toString()) + Double.parseDouble(mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getModifierProductPrice());

                    }
                         popupTotalPrice.setText(String.valueOf(totalPrice2));
                    maxCount = maxCount + 1;
                }else{
                    if(mProduct.getProductModifiers().get(position).getMaxAllowedQuantity() > 0){
                        holder.txtPrice.setText("");
                    }else{
                        holder.txtPrice.setText("+ £"+mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getModifierProductPrice());

                    }

                    holder.lyAddRemove.setVisibility(View.GONE);
                    if(maxCount < mProduct.getProductModifiers().get(position).getMaxAllowedQuantity()) {
                        totalPrice2 = Double.parseDouble(popupTotalPrice.getText().toString()) + 0.0;

                    }else {
                        totalPrice2 = Double.parseDouble(popupTotalPrice.getText().toString()) - (Double.parseDouble(mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getModifierProductPrice()) * Integer.parseInt(holder.proCount.getText().toString()));
                    }
                    popupTotalPrice.setText(String.valueOf(totalPrice2));
                    holder.proCount.setText("1");
                    maxCount = maxCount - 1;
                }
            }
        });
      //  showCartModifierProduct.clear();
        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.checkbox.isChecked()) {

                    arrayListIndex.add(mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getProductId());

                    if(maxCount <= mProduct.getProductModifiers().get(position).getMaxAllowedQuantity()) {
                        showCartModifierProduct.add(new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.ModifierProduct(mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getProductId(),mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getUnit(),mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getModifierProductPrice(),mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getProductName(),Integer.parseInt(holder.proCount.getText().toString()),0.0));

                    }else{
                        showCartModifierProduct.add(new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.ModifierProduct(mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getProductId(),mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getUnit(),mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getModifierProductPrice(),mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getProductName(),Integer.parseInt(holder.proCount.getText().toString()),Double.parseDouble(mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getModifierProductPrice())));

                    }
                    Log.e("LEN 12 >>",""+showCartModifierProduct.size());
                }else{
                    showCartModifierProduct.remove(getlistIndex(mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getProductId()));
                    arrayListIndex.remove(getlistIndex(mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getProductId()));
                }
            }
        });

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.proCount.setText(String.valueOf(Integer.parseInt(holder.proCount.getText().toString())+1));
                com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.ModifierProduct mp = showCartModifierProduct.get(getlistIndex(mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getProductId()));
                showCartModifierProduct.set(getlistIndex(mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getProductId()),new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.ModifierProduct(mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getProductId(),mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getUnit(), String.valueOf( Double.parseDouble(mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getModifierProductPrice())),mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getProductName(),Integer.parseInt(holder.proCount.getText().toString()),mp.getTotalPrice()+Double.parseDouble(mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getModifierProductPrice())));
                if(maxCount < mProduct.getProductModifiers().get(position).getMaxAllowedQuantity()) {
                    totalPrice2 = totalPrice2 + 0.0;

                }else{
                    totalPrice2 = totalPrice2 + Double.parseDouble(mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getModifierProductPrice());

                }
                       popupTotalPrice.setText(String.valueOf(totalPrice2));
                holder.txtPrice.setText("+ £"+String.valueOf(mp.getTotalPrice() + Double.parseDouble(mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getModifierProductPrice())));
            }
        });
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Integer.parseInt(holder.proCount.getText().toString()) > 1) {

                    com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.ModifierProduct mp = showCartModifierProduct.get(getlistIndex(mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getProductId()));
                    showCartModifierProduct.set(getlistIndex(mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getProductId()), new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.ModifierProduct(mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getProductId(), mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getUnit(), String.valueOf(Double.parseDouble(mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getModifierProductPrice())), mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getProductName(),Integer.parseInt(holder.proCount.getText().toString()),mp.getTotalPrice() - Double.parseDouble(mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getModifierProductPrice())));
                    holder.txtPrice.setText("+ £"+String.valueOf(mp.getTotalPrice() - Double.parseDouble(mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getModifierProductPrice())));

                    if(maxCount < mProduct.getProductModifiers().get(position).getMaxAllowedQuantity()) {
                        totalPrice2 = totalPrice2 + 0.0;

                    }else {
                        totalPrice2 = totalPrice2 - Double.parseDouble(mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getModifierProductPrice());

                    }
                    popupTotalPrice.setText(String.valueOf(totalPrice2));
                }
                holder.proCount.setText(String.valueOf(Integer.parseInt(holder.proCount.getText().toString()) - 1));
                if(Integer.parseInt(holder.proCount.getText().toString()) == 0){
                    showCartModifierProduct.remove(getlistIndex(mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getProductId()));
                    arrayListIndex.remove(getlistIndex(mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getProductId()));
                    holder.checkbox.setChecked(false);
                    holder.lyAddRemove.setVisibility(View.GONE);
                    holder.proCount.setText("1");
                    if(maxCount < mProduct.getProductModifiers().get(position).getMaxAllowedQuantity()) {
                        totalPrice2 = totalPrice2 + 0.0;

                    }else {
                        totalPrice2 = totalPrice2 - Double.parseDouble(mProduct.getProductModifiers().get(position).getModifierProducts().get(listPosition).getModifierProductPrice());

                    }
                    popupTotalPrice.setText(String.valueOf(totalPrice2));
                }
            }
        });
//        if(check.get(listPosition).equals("1")){
//            rightImg.setVisibility(View.VISIBLE);
//            not_right_tv.setVisibility(View.GONE);
//        }else{
//            not_right_tv.setVisibility(View.VISIBLE);
//
//            rightImg.setVisibility(View.GONE);
//        }
//        holder.rightImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                check.set(listPosition,"0");
//                //allReadyCheck.set(listPosition,"0");
//                //  allReadyCheck.set(0,"0");
//                mPositionInterface.onClickPos(listPosition,check);
//
//                holder.rightImg.setVisibility(View.VISIBLE);
//                holder.not_right_tv.setVisibility(View.GONE);
//            }
//        });
//
//
//        holder.not_right_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                check.set(listPosition,"1");
//                //  allReadyCheck.set(0,"1");
//                mPositionInterface.onClickPos(listPosition,check);
//                holder.not_right_tv.setVisibility(View.GONE);
//
//                holder.rightImg.setVisibility(View.VISIBLE);
//            }
//        });



    }
    public int getlistIndex(String id){
        for(int i=0; i<arrayListIndex.size(); i++){
            if(id.equalsIgnoreCase(arrayListIndex.get(i))){
                return i;
            }
        }
        return -1;
    }
    @Override
    public int getItemCount() {
        return mProduct.getProductModifiers().get(position).getModifierProducts().size();
    }}