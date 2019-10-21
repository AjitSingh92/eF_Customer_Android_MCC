package com.lexxdigital.easyfooduserapps.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapps.R;

public class SpicalOfferAdapter extends RecyclerView.Adapter<SpicalOfferAdapter.MyViewHolder> {

    public static class

    MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    public SpicalOfferAdapter() {

    }

    @Override
    public SpicalOfferAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offer_item_list, parent, false);

        SpicalOfferAdapter.MyViewHolder myViewHolder = new SpicalOfferAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final SpicalOfferAdapter.MyViewHolder holder, final int listPosition) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}