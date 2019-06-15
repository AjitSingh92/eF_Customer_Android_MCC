package com.lexxdigital.easyfooduserapps.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.menu_category.MenuProduct;

import java.util.ArrayList;

public class WhatLikeAdapter extends RecyclerView.Adapter<WhatLikeAdapter.MyViewHolder> {

    // private ArrayList<Arraylist> dataSet;

    DealCardAdapter mDealCardAdapter;
    Context mContext;
    TextView popupTotalPrice;
    ArrayList<String> check;
    ArrayList<String> allReadyCheck;
    MenuProduct cartProduct = new MenuProduct();
    WhatLikeAdapter.PositionInterface mPositionInterface2;

    public  interface PositionInterface{
        void onClickPos2(int pos, ArrayList<String> check, MenuProduct qProduct);
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {


        TextView sizeName,price,count;
        ImageView rightImg,not_right_tv;
        LinearLayout rightCount,lyAddRemove,lySizeItem;



        // ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.rightImg = (ImageView) itemView.findViewById(R.id.rightImg_1);
            this.not_right_tv = (ImageView) itemView.findViewById(R.id.not_right_tv_1);
            this.sizeName = (TextView) itemView.findViewById(R.id.size_name);
            this.price = (TextView) itemView.findViewById(R.id.rs_tv);

            this.lySizeItem = (LinearLayout) itemView.findViewById(R.id.ly_size_item);
//                this.dealIdRl = (RelativeLayout) itemView.findViewById(R.id.dealIdRl);

        }
    }

    public WhatLikeAdapter(Context mContext, WhatLikeAdapter.PositionInterface mPositionInterface2, ArrayList<String> check, MenuProduct mProduct, TextView price) {

        this.mContext= mContext;
        this.mPositionInterface2= mPositionInterface2;
        this.check= check;
        this.cartProduct = mProduct;
        this.popupTotalPrice = price;
    }

    @Override
    public WhatLikeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quantity_item, parent, false);

        WhatLikeAdapter.MyViewHolder myViewHolder = new WhatLikeAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final WhatLikeAdapter.MyViewHolder holder, final int listPosition) {

        ImageView rightImg = holder.rightImg;
        ImageView not_right_tv = holder.not_right_tv;

        holder.sizeName.setText(cartProduct.getMenuProductSize().get(listPosition).getProductSizeName());
        holder.price.setText("£"+cartProduct.getMenuProductSize().get(listPosition).getProductSizePrice());

        if(check.get(listPosition).equals("1")){
            rightImg.setVisibility(View.VISIBLE);
            not_right_tv.setVisibility(View.GONE);
          //  holder.rightCount.setVisibility(View.VISIBLE);
//            holder.lyAddRemove.setVisibility(View.VISIBLE);

        }else{
            not_right_tv.setVisibility(View.VISIBLE);
     //       holder.rightCount.setVisibility(View.INVISIBLE);
            rightImg.setVisibility(View.GONE);
//            holder.lyAddRemove.setVisibility(View.GONE);

        }

//        holder.lySizeItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(holder.rightImg.getVisibility() == View.VISIBLE){
//                    check.set(listPosition,"0");
//                    //allReadyCheck.set(listPosition,"0");
//                    //  allReadyCheck.set(0,"0");
//                    mPositionInterface2.onClickPos2(listPosition,check);
//
//                    holder.rightImg.setVisibility(View.VISIBLE);
//                    holder.not_right_tv.setVisibility(View.GONE);
//                }else if(holder.not_right_tv.getVisibility() == View.VISIBLE){
//                    check.set(listPosition,"0");
//                    //allReadyCheck.set(listPosition,"0");
//                    //  allReadyCheck.set(0,"0");
//                    mPositionInterface2.onClickPos2(listPosition,check);
//
//                    holder.rightImg.setVisibility(View.VISIBLE);
//                    holder.not_right_tv.setVisibility(View.GONE);
//                }
//
//            }
//        });
        holder.rightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  check.set(listPosition,"0");
                //allReadyCheck.set(listPosition,"0");
                //  allReadyCheck.set(0,"0");
                mPositionInterface2.onClickPos2(listPosition,check,cartProduct);

                holder.rightImg.setVisibility(View.VISIBLE);
                holder.not_right_tv.setVisibility(View.GONE);
            }
        });


        holder.not_right_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                check.set(listPosition,"1");
                //  allReadyCheck.set(0,"1");
                mPositionInterface2.onClickPos2(listPosition,check,cartProduct);
                holder.not_right_tv.setVisibility(View.GONE);

                holder.rightImg.setVisibility(View.VISIBLE);
            }
        });



    }

    @Override
    public int getItemCount() {
        return cartProduct.getMenuProductSize().size();
    }}
