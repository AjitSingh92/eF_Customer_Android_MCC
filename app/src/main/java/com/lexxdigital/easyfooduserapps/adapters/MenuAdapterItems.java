package com.lexxdigital.easyfooduserapps.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.cart_model.final_cart.FinalNewCartDetails;

public class MenuAdapterItems extends RecyclerView.Adapter<MenuAdapterItems.MyViewHolder> {
    Context mContext;
    FinalNewCartDetails cartList;
    int total_count = 0, pos, num = 0;
    TextView txtDeliveryFees;
    Double deliveryFees = 0.0, totalItemPrice = 0.0, subTotalAmount = 0.0;
    TextView discount, subTotal, totalCount, totalAmmount, footerTotalCount, footerTotalAmount;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemCount, itemTitle, itemPrice, txttotalCount;
        LinearLayout btnAdd, btnRemove, lySubItems;
        RecyclerView listItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemCount = (TextView) itemView.findViewById(R.id.items_count);
            this.itemTitle = (TextView) itemView.findViewById(R.id.item_title);
            this.itemPrice = (TextView) itemView.findViewById(R.id.item_price);
            this.txttotalCount = (TextView) itemView.findViewById(R.id.item_count_all);
            this.listItem = (RecyclerView) itemView.findViewById(R.id.items_list);
            this.btnAdd = (LinearLayout) itemView.findViewById(R.id.item_add);
            this.lySubItems = (LinearLayout) itemView.findViewById(R.id.sub_items);
            this.btnRemove = (LinearLayout) itemView.findViewById(R.id.item_remove);

        }
    }

    public MenuAdapterItems(Context mContext, FinalNewCartDetails cartList, TextView discount, TextView subTotal, TextView totalCount, TextView totalAmmount, TextView footerTotalCount, TextView footerTotalAmount, Double deliveryCharge, TextView txtDeliveryFees, int llcount) {
        this.mContext = mContext;
        this.cartList = cartList;
        this.discount = discount;
        this.subTotal = subTotal;
        this.totalCount = totalCount;
        this.totalAmmount = totalAmmount;
        this.footerTotalCount = footerTotalCount;
        this.footerTotalAmount = footerTotalAmount;
        this.deliveryFees = deliveryCharge;
        this.txtDeliveryFees = txtDeliveryFees;
        this.num = llcount;
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
        pos = listPosition;
        final com.lexxdigital.easyfooduserapps.cart_model.final_cart.Datum cart2;
        if (cartList.getData().size() > 0) {
            if (cartList.getData().get(listPosition).getMenuCategory() != null) {
                cart2 = (com.lexxdigital.easyfooduserapps.cart_model.final_cart.Datum) cartList.getData().get(listPosition);
                holder.listItem.setVisibility(View.VISIBLE);
                holder.itemTitle.setText(cart2.getMenuCategory().getMenuProducts().getProductName());
                holder.itemPrice.setText("£" + String.format("%.2f", Double.parseDouble(cart2.getMenuCategory().getAmount())));
                subTotalAmount = subTotalAmount + Double.parseDouble(cart2.getMenuCategory().getAmount());
                discount.setText("£0.00");
                total_count = cart2.getMenuCategory().getQuantity();
                for (int j = 0; j < cart2.getMenuCategory().getMenuProducts().getMenuProductSize().size(); j++) {
                    for (int k = 0; k < cart2.getMenuCategory().getMenuProducts().getMenuProductSize().get(j).getSizeModifiers().size(); k++) {
                        for (int l = 0; l < cart2.getMenuCategory().getMenuProducts().getMenuProductSize().get(j).getSizeModifiers().get(k).getSizeModifierProducts().size(); l++) {
                            LinearLayout parent = new LinearLayout(mContext);

                            parent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            parent.setOrientation(LinearLayout.HORIZONTAL);

                            TextView tv = new TextView(mContext); // Prepare textview object programmatically
                            tv.setText(cart2.getMenuCategory().getMenuProducts().getMenuProductSize().get(j).getSizeModifiers().get(k).getSizeModifierProducts().get(l).getProductName());
                            tv.setTextColor(mContext.getResources().getColor(R.color.orange));
                            tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

                            TextView tv2 = new TextView(mContext); // Prepare textview object programmatically
                            tv2.setText("£" + cart2.getMenuCategory().getMenuProducts().getMenuProductSize().get(j).getSizeModifiers().get(k).getSizeModifierProducts().get(l).getAmount());
                            tv2.setGravity(Gravity.END);
                            tv2.setTextColor(mContext.getResources().getColor(R.color.orange));
                            tv2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

                            totalItemPrice = totalItemPrice + Double.parseDouble(cart2.getMenuCategory().getMenuProducts().getMenuProductSize().get(j).getSizeModifiers().get(k).getSizeModifierProducts().get(l).getAmount());
                            parent.addView(tv);
                            parent.addView(tv2);
                            holder.lySubItems.addView(parent); // Add to your ViewGroup using this method
                        }
                    }
                }
                for (int m = 0; m < cart2.getMenuCategory().getMenuProducts().getProductModifiers().size(); m++) {
                    for (int n = 0; n < cart2.getMenuCategory().getMenuProducts().getProductModifiers().get(m).getModifierProducts().size(); n++) {
                        LinearLayout parent = new LinearLayout(mContext);

                        parent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        parent.setOrientation(LinearLayout.HORIZONTAL);

                        TextView tv = new TextView(mContext); // Prepare textview object programmatically
                        tv.setText(cart2.getMenuCategory().getMenuProducts().getProductModifiers().get(m).getModifierProducts().get(n).getProductName());
                        tv.setTextColor(mContext.getResources().getColor(R.color.orange));
                        tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

                        TextView tv2 = new TextView(mContext); // Prepare textview object programmatically
                        tv2.setText("£" + cart2.getMenuCategory().getMenuProducts().getProductModifiers().get(m).getModifierProducts().get(n).getAmount());
                        tv2.setGravity(Gravity.END);
                        tv2.setTextColor(mContext.getResources().getColor(R.color.orange));
                        tv2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

                        totalItemPrice = totalItemPrice + cart2.getMenuCategory().getMenuProducts().getProductModifiers().get(m).getModifierProducts().get(n).getAmount();
                        parent.addView(tv);
                        parent.addView(tv2);
                        holder.lySubItems.addView(parent); // Add to your ViewGroup using this method
                    }
                }

                subTotal.setText(String.format("%.2f", subTotalAmount));
                totalAmmount.setText(String.format("%.2f", subTotalAmount + deliveryFees));

                totalCount.setText(String.valueOf(total_count));
                footerTotalCount.setText(String.valueOf(total_count));

                footerTotalAmount.setText(String.format("%.2f", subTotalAmount + deliveryFees));
                txtDeliveryFees.setText("£" + String.format("%.2f", deliveryFees));

                holder.txttotalCount.setText(String.valueOf(cart2.getMenuCategory().getQuantity()));
                holder.itemCount.setText(String.valueOf(cart2.getMenuCategory().getQuantity()));

                holder.btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                holder.btnRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Integer.parseInt(holder.txttotalCount.getText().toString()) > 1) {

                        }
                        if (Integer.parseInt(holder.txttotalCount.getText().toString()) - 1 == 0) {

                        }
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