package com.lexxdigital.easyfooduserapps.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapps.R;

public class AdapterSubItems extends RecyclerView.Adapter<AdapterSubItems.MyViewHolder> {

    private Context mContext;
    int pos,prePos;


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itemName, txtPrice;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.itemName = (TextView) itemView.findViewById(R.id.item);
            this.txtPrice = (TextView) itemView.findViewById(R.id.price);

        }
    }

    public AdapterSubItems(Context mContext,int preposition) {

        this.mContext= mContext;
        this.prePos=preposition;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_sub_category, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }}