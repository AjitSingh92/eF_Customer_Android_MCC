package com.lexxdigital.easyfooduserapp.adapters;

import android.content.Context;
import android.content.Intent;
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

public class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.MyViewHolder> {

    // private ArrayList<Arraylist> dataSet;

    Context mContext;
    ArrayList<com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.SizeModifier> showCartSizeModifier;
    ArrayList<com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.SizeModifierProduct>  showCartSizeModifierPproduct ;
    ArrayList<String> check = new ArrayList<>();
    ArrayList<String> allReadyCheck;
    private List<String> arrayListIndex = new ArrayList<>();
    MenuProduct mProduct;
    int childposition, position, maxCount=0;
    ChooseAdapter.PositionInterface mPositionInterface;
    TextView popupTotalPrice;
    private Double totalPrice,totalPrice2=0.0;
    public  interface PositionInterface{
        void onClickPos(int pos,int lpos);
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

    public ChooseAdapter(Context mContext, ChooseAdapter.PositionInterface mPositionInterface, ArrayList<String> check, MenuProduct product, int childpos, int pos, TextView price,ArrayList<com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.SizeModifierProduct> cartSizeModifierPproduct, ArrayList<com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.SizeModifier> cartSizeModifier, double tPrice) {

        this.mContext= mContext;
        this.mPositionInterface= mPositionInterface;
        this.check= check;
        this.mProduct = product;
        this.childposition = childpos;
        this.position = pos;
        this.popupTotalPrice = price;
        this.showCartSizeModifierPproduct = cartSizeModifierPproduct;
        this.showCartSizeModifier = cartSizeModifier;
        this.totalPrice = tPrice;

    }

    @Override
    public ChooseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.choose_multi_item_checkbox, parent, false);

        ChooseAdapter.MyViewHolder myViewHolder = new ChooseAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ChooseAdapter.MyViewHolder holder, final int listPosition) {

//        ImageView rightImg = holder.rightImg;
//        ImageView not_right_tv = holder.not_right_tv;
        //totalPrice2 = totalPrice;
       // showCartSizeModifierPproduct = new ArrayList<>();
        final int llpos = listPosition;
        holder.txtModifier.setText(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(listPosition).getProductName());
        if(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getMaxAllowedQuantity() == 0){
            holder.txtPrice.setText("+ £"+mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(listPosition).getModifierProductPrice());
        }else{
            holder.txtPrice.setText("");
        }
            holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //set your object's last status
                if(isChecked){
                    Log.e("max >>>",">>>>"+maxCount);
                    if(maxCount < mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getMaxAllowedQuantity()){
                        holder.txtPrice.setText("£0.0");
                    }else{
                        holder.txtPrice.setText("£"+mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(listPosition).getModifierProductPrice());
                    }
                    if(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getMaxAllowedQuantity() > 0){
                        holder.lyAddRemove.setVisibility(View.GONE);
                    }else {
                        holder.lyAddRemove.setVisibility(View.VISIBLE);
                    }

                    holder.proCount.setText("1");
//                    double tPrice = Double.parseDouble(popupTotalPrice.getText());

                    if(maxCount < mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getMaxAllowedQuantity()) {
                        totalPrice2 = Double.parseDouble(popupTotalPrice.getText().toString()) + 0.0;

                    }else{
                        totalPrice2 = Double.parseDouble(popupTotalPrice.getText().toString()) + Double.parseDouble(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getModifierProductPrice());

                    }

                    popupTotalPrice.setText(String.valueOf(totalPrice2));
                   // if(maxCount != mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getMaxAllowedQuantity()) {
                        maxCount = maxCount + 1;
                  //  }

                   // check.set(llpos,"1");
                  //  mPositionInterface.onClickPos(llpos, position);
//                    showCartSizeModifierPproduct.add(new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.SizeModifierProduct(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getProductId(),mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getUnit(),mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getModifierProductPrice(),mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getProductName()));
                }else{
                    if(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getMaxAllowedQuantity() > 0) {

                        holder.txtPrice.setText("");
                    }else{
                        holder.txtPrice.setText("+ £"+mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(listPosition).getModifierProductPrice());
                    }
                    holder.lyAddRemove.setVisibility(View.GONE);

                    if(maxCount < mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getMaxAllowedQuantity()) {
                        totalPrice2 = Double.parseDouble(popupTotalPrice.getText().toString()) + 0.0;

                    }else {
                        totalPrice2 = Double.parseDouble(popupTotalPrice.getText().toString()) - (Double.parseDouble(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getModifierProductPrice()) * Integer.parseInt(holder.proCount.getText().toString()));
                    }
                    popupTotalPrice.setText(String.valueOf(totalPrice2));
                   // if(maxCount > 0)
                    maxCount = maxCount - 1;
                    holder.proCount.setText("1");
                    //   check.set(llpos,"0");
                  //  mPositionInterface.onClickPos(llpos, position);
                    Log.e("max 2 >>>",">>>>"+maxCount);
                }
//                mPositionInterface.onClickPos(llpos, position);
            }
        });


        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showCartSizeModifierPproduct.clear();
               // showCartSizeModifierPproduct = new ArrayList<>();
                if(holder.checkbox.isChecked()) {

                    arrayListIndex.add(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getProductId());
                    if(maxCount <= mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getMaxAllowedQuantity()) {
                        showCartSizeModifierPproduct.add(new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.SizeModifierProduct(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getProductId(), mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getUnit(), mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getModifierProductPrice(), mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getProductName(),Integer.parseInt(holder.proCount.getText().toString()),0.0));

                    }else{
                        showCartSizeModifierPproduct.add(new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.SizeModifierProduct(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getProductId(), mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getUnit(), mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getModifierProductPrice(), mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getProductName(),Integer.parseInt(holder.proCount.getText().toString()),Double.parseDouble(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getModifierProductPrice())));

                    }


                }else{
                    showCartSizeModifierPproduct.remove(getlistIndex(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getProductId()));
                    arrayListIndex.remove(getlistIndex(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getProductId()));
                }
            }
        });


//        showCartSizeModifier.add(new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.SizeModifier(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getModifierName(),mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getModifierType(),mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getModifierId(),mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getMinAllowedQuantity(),mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getMaxAllowedQuantity(),showCartSizeModifierPproduct));

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.proCount.setText(String.valueOf(Integer.parseInt(holder.proCount.getText().toString())+1));
                com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.SizeModifierProduct smp = showCartSizeModifierPproduct.get(getlistIndex(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getProductId()));

                if(maxCount < mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getMaxAllowedQuantity()) {
                    showCartSizeModifierPproduct.set(getlistIndex(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getProductId()),new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.SizeModifierProduct(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getProductId(), mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getUnit(), mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getModifierProductPrice(), mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getProductName(),Integer.parseInt(holder.proCount.getText().toString()),smp.getTotalPrice() + 0.0));
                    totalPrice2 = totalPrice2 + 0.0;

                }else{
                    showCartSizeModifierPproduct.set(getlistIndex(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getProductId()),new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.SizeModifierProduct(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getProductId(), mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getUnit(), mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getModifierProductPrice(), mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getProductName(),Integer.parseInt(holder.proCount.getText().toString()),smp.getTotalPrice() + Double.parseDouble(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getModifierProductPrice())));
                    totalPrice2 = totalPrice2 + Double.parseDouble(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getModifierProductPrice());

                }

                  popupTotalPrice.setText(String.valueOf(totalPrice2 ));
                holder.txtPrice.setText("+ £"+String.valueOf(smp.getTotalPrice() + Double.parseDouble(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getModifierProductPrice())));
            }
        });
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Integer.parseInt(holder.proCount.getText().toString()) > 1) {

                    com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.SizeModifierProduct smp = showCartSizeModifierPproduct.get(getlistIndex(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getProductId()));

                    if(maxCount < mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getMaxAllowedQuantity()) {
                        showCartSizeModifierPproduct.set(getlistIndex(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getProductId()), new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.SizeModifierProduct(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getProductId(), mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getUnit(), mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getModifierProductPrice(), mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getProductName(),Integer.parseInt(holder.proCount.getText().toString()),smp.getTotalPrice() - 0.0));
                        totalPrice2 = totalPrice2 - 0.0;

                    }else{
                        showCartSizeModifierPproduct.set(getlistIndex(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getProductId()), new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.SizeModifierProduct(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getProductId(), mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getUnit(), mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getModifierProductPrice(), mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getProductName(),Integer.parseInt(holder.proCount.getText().toString()),smp.getTotalPrice() - Double.parseDouble(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getModifierProductPrice())));
                        totalPrice2 = totalPrice2 - Double.parseDouble(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getModifierProductPrice());

                    }
                          holder.txtPrice.setText("+ £"+String.valueOf(smp.getTotalPrice() - Double.parseDouble(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getModifierProductPrice())));
                     popupTotalPrice.setText(String.valueOf(totalPrice2 ));
                }
                holder.proCount.setText(String.valueOf(Integer.parseInt(holder.proCount.getText().toString()) - 1));
                if(Integer.parseInt(holder.proCount.getText().toString()) == 0){
                    showCartSizeModifierPproduct.remove(getlistIndex(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getProductId()));
                    arrayListIndex.remove(getlistIndex(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getProductId()));
                    totalPrice2 = Double.parseDouble(popupTotalPrice.getText().toString()) - Double.parseDouble(mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().get(llpos).getModifierProductPrice());
                    popupTotalPrice.setText(String.valueOf(totalPrice2));
                    holder.checkbox.setChecked(false);
                    holder.lyAddRemove.setVisibility(View.GONE);
                    holder.proCount.setText("1");
                }

            }
        });
//        holder.checkbox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(holder.checkbox.isChecked()){
//                    holder.checkbox.setChecked(false);
//                    holder.checkbox.setBackground(mContext.getResources().getDrawable(R.drawable.untick_checkbox));
//                }else{
//                    holder.checkbox.setChecked(true);
//                    holder.checkbox.setBackground(mContext.getResources().getDrawable(R.drawable.tick_checkbox));
//                }
//            }
//        });
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
//                  check.set(listPosition,"0");
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
        return mProduct.getMenuProductSize().get(childposition).getSizeModifiers().get(position).getSizeModifierProducts().size();
    }}