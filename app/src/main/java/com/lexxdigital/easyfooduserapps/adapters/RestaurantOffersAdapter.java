package com.lexxdigital.easyfooduserapps.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.model.restaurant_offers.RestaurantSpecialOffers;

import java.util.ArrayList;
import java.util.List;

public class RestaurantOffersAdapter extends RecyclerView.Adapter<RestaurantOffersAdapter.CategoryViewHolder> {

    private final Context context;
    private List<RestaurantSpecialOffers> mItem;

    public interface OnOffersItemListClick {
        void OnOffersClick();
    }

    public RestaurantOffersAdapter(Context context) {
        this.context = context;
        this.mItem = new ArrayList<>();
    }

    public void clearData() {
        this.mItem.clear();
        notifyDataSetChanged();
    }

    public void addItem(List<RestaurantSpecialOffers> mItem) {
        this.mItem.addAll(mItem);
        notifyItemChanged(this.mItem.size());
    }

    public void addItem(RestaurantSpecialOffers mItem) {
        this.mItem.add(mItem);
        notifyItemChanged(this.mItem.size());
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.restaurant_offers_row, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder pdqListingViewHolder, int position) {
        pdqListingViewHolder.bindData(position);

    }


    @Override
    public int getItemCount() {
        return mItem.size();
    }


    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView title, price, qty;
        private final CheckBox cbitemSelected;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_title);
            price = itemView.findViewById(R.id.tv_price);
            qty = itemView.findViewById(R.id.tv_qty);
            cbitemSelected = itemView.findViewById(R.id.cb_itemSelected);

            cbitemSelected.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        private void bindData(final int position) {

            title.setText(mItem.get(position).getOfferTitle());


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.cb_itemSelected:

                    break;
                default:

                    break;
            }
        }
    }
}