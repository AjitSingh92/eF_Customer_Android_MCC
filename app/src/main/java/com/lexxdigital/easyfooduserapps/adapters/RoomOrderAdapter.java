package com.lexxdigital.easyfooduserapps.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.adapters.menu_adapter.OnUpsellProductItemClick;
import com.lexxdigital.easyfooduserapps.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.UpsellProduct;
import java.util.ArrayList;
import java.util.List;

public class RoomOrderAdapter extends RecyclerView.Adapter<RoomOrderAdapter.MyViewHolder> {
    private Context context;
    private List<UpsellProduct> mItem;
    DatabaseHelper db;
    OnUpsellProductItemClick onUpsellProductItemClick;


    public RoomOrderAdapter(Context context, OnUpsellProductItemClick onUpsellProductItemClick) {
        this.context = context;
        this.mItem = new ArrayList<>();
        this.onUpsellProductItemClick = onUpsellProductItemClick;
        db = new DatabaseHelper(context);

    }

    public void clearData() {
        this.mItem.clear();
        notifyDataSetChanged();
    }

    public void addItem(List<UpsellProduct> mItem) {
        this.mItem.addAll(mItem);
        notifyItemChanged(this.mItem.size());
    }

    public void addItem(UpsellProduct mItem) {
        this.mItem.add(mItem);
        notifyItemChanged(this.mItem.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        holder.bindData(listPosition);
    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewname, tvQty, tvPrice;
        LinearLayout llMinus, llPlus;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewname = (TextView) itemView.findViewById(R.id.textViewname);
            this.tvQty = itemView.findViewById(R.id.tv_qty);
            this.tvPrice = itemView.findViewById(R.id.tv_price);
            this.llMinus = itemView.findViewById(R.id.ll_Minus);
            this.llPlus = itemView.findViewById(R.id.ll_Plus);
            itemView.setOnClickListener(this);
            llMinus.setOnClickListener(this);
            llPlus.setOnClickListener(this);
        }

        private void bindData(int position) {
            textViewname.setText(Html.fromHtml("<b>" + mItem.get(position).getProductName() + "</b>" + "<p><p>" + mItem.get(position).getDescription()));
            tvPrice.setText("£" + String.format("%.2f", mItem.get(position).getProductPrice()));
            UpsellProduct itemData = db.getUpSellProducts(mItem.get(position).getProductId());
            if (itemData != null) {
                tvQty.setText(itemData.getQuantity());
            } else {
                tvQty.setText("0");
            }

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.ll_Plus:
                    tvQty.setText(String.valueOf((Integer.parseInt(tvQty.getText().toString()) + 1)));
                    if (Integer.parseInt(tvQty.getText().toString()) == 1) {
                        db.insertUpsellProducts(mItem.get(getLayoutPosition()));
                    } else if (Integer.parseInt(tvQty.getText().toString()) > 1) {
                        db.updateUpsellProductQuantity(mItem.get(getLayoutPosition()).getProductId(), Integer.parseInt(tvQty.getText().toString()));
                    }
                    if (onUpsellProductItemClick != null) {
                        onUpsellProductItemClick.OnUpSellQuantityBtnClick();
                    }
                    break;
                case R.id.ll_Minus:
                    if (Integer.parseInt(tvQty.getText().toString()) > 0) {
                        tvQty.setText(String.valueOf((Integer.parseInt(tvQty.getText().toString()) - 1)));
                        if (Integer.parseInt(tvQty.getText().toString()) == 0) {
                            db.deleteUpsellProductItem(mItem.get(getLayoutPosition()).getProductId());

                        } else {
                            db.updateUpsellProductQuantity(mItem.get(getLayoutPosition()).getProductId(), Integer.parseInt(tvQty.getText().toString()));
                        }
                        if (onUpsellProductItemClick != null) {
                            onUpsellProductItemClick.OnUpSellQuantityBtnClick();
                        }
                    }
                    break;
            }

        }
    }
}