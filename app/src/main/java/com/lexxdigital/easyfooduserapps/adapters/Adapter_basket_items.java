package com.lexxdigital.easyfooduserapps.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapps.R;


public class Adapter_basket_items extends RecyclerView.Adapter<Adapter_basket_items.MyViewHolder> {

    private Context mContext;
    int pos,prePos;


    com.lexxdigital.easyfooduserapps.restaurant_details.model.show_menu_category.MenuCategory cart2;
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itemName, txtPrice;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.itemName = (TextView) itemView.findViewById(R.id.item);
            this.txtPrice = (TextView) itemView.findViewById(R.id.price);

        }
    }

    public Adapter_basket_items(Context mContext, com.lexxdigital.easyfooduserapps.restaurant_details.model.show_menu_category.MenuCategory cart, int preposition) {

        this.mContext= mContext;
        this.prePos=preposition;
        this.cart2 = cart;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_basket_items, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        pos = listPosition;
        holder.itemName.setText(cart2.getMenuProducts().get(prePos).getMenuProductSize().get(listPosition).getProductSizeName());
        holder.txtPrice.setText(cart2.getMenuProducts().get(prePos).getMenuProductSize().get(listPosition).getProductSizePrice());

    }

    @Override
    public int getItemCount() {
        return cart2.getMenuProducts().get(pos).getMenuProductSize().size();
    }}