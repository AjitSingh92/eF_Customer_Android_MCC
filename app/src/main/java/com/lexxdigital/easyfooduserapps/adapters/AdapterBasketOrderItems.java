package com.lexxdigital.easyfooduserapps.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.cart_model.final_cart.FinalNewCartDetails;

public class AdapterBasketOrderItems extends RecyclerView.Adapter<AdapterBasketOrderItems.MyViewHolder> {

    // private ArrayList<Arraylist> dataSet;

    Context mContext;
    FinalNewCartDetails cartList;
    int total_count = 0, num = 0;
    Double deliveryFees = 0.0, discountAmt = 0.0;
    TextView discount, subTotal, totalCount, totalAmmount, footerTotalCount, footerTotalAmount;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itemCount, itemTitle, itemPrice, totalCount;
        LinearLayout btnAdd, btnRemove;
        RecyclerView listItem;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.itemCount = (TextView) itemView.findViewById(R.id.items_count);
            this.itemTitle = (TextView) itemView.findViewById(R.id.item_title);
            this.itemPrice = (TextView) itemView.findViewById(R.id.item_price);
            this.totalCount = (TextView) itemView.findViewById(R.id.item_count_all);
            this.listItem = (RecyclerView) itemView.findViewById(R.id.items_list);
            this.btnAdd = (LinearLayout) itemView.findViewById(R.id.item_add);
            this.btnRemove = (LinearLayout) itemView.findViewById(R.id.item_remove);

        }
    }

    public AdapterBasketOrderItems(Context mContext, FinalNewCartDetails cartList, TextView discount, TextView subTotal, TextView totalCount, TextView totalAmmount, TextView footerTotalCount, TextView footerTotalAmount, int lcount) {
        this.mContext = mContext;
        this.cartList = cartList;
        this.discount = discount;
        this.subTotal = subTotal;
        this.totalCount = totalCount;
        this.totalAmmount = totalAmmount;
        this.footerTotalCount = footerTotalCount;
        this.footerTotalAmount = footerTotalAmount;
        this.num = lcount;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_basket_order_list, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        com.lexxdigital.easyfooduserapps.cart_model.final_cart.Datum cart;
        if (cartList.getData().size() > 0) {
            if (cartList.getData().get(listPosition).getSpecialOffer() != null) {

                cart = (com.lexxdigital.easyfooduserapps.cart_model.final_cart.Datum) cartList.getData().get(listPosition);
                holder.listItem.setVisibility(View.GONE);
                holder.itemCount.setText(cart.getSpecialOffer().getProductQty());
                holder.itemTitle.setText(cart.getSpecialOffer().getOfferTitle());
                holder.itemPrice.setText("£" + cart.getSpecialOffer().getTotalAmmount());
                holder.totalCount.setText(cart.getSpecialOffer().getProductQty());
                total_count = Integer.parseInt(cart.getSpecialOffer().getProductQty());
                subTotal.setText(String.valueOf(Double.parseDouble(subTotal.getText().toString()) + Double.parseDouble(cart.getSpecialOffer().getTotalAmmount())));
                totalAmmount.setText(String.valueOf(Double.parseDouble(totalAmmount.getText().toString()) + Double.parseDouble(cart.getSpecialOffer().getTotalAmmount())));
                footerTotalAmount.setText(String.valueOf(Double.parseDouble(footerTotalAmount.getText().toString()) + Double.parseDouble(cart.getSpecialOffer().getTotalAmmount())));
                totalCount.setText(String.valueOf(Integer.parseInt(totalCount.getText().toString()) + Integer.parseInt(cart.getSpecialOffer().getProductQty())));
                footerTotalCount.setText(String.valueOf(Integer.parseInt(footerTotalCount.getText().toString()) + Integer.parseInt(cart.getSpecialOffer().getProductQty())));
                holder.btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                holder.btnRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }

        }


    }

    @Override
    public int getItemCount() {
        return num;
    }
}
