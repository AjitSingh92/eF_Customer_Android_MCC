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

    // private ArrayList<Arraylist> dataSet;

    Context mContext;
    FinalNewCartDetails cartList;
    int total_count = 0, pos, num = 0;
    TextView txtDeliveryFees;
    Double deliveryFees = 0.0, discountAmt = 0.0, totalItemPrice = 0.0, subTotalAmount = 0.0;
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

//        pos =listPosition;
//        Log.e("LEN CART",""+cartList.getData().get(0).getMenuCategory());
//        if(cartList.get(0).getMenuCategory().size() > 0){
//            final com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.ShowMenuCartDetails cart2 = (com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.ShowMenuCartDetails)cartList.get(0);
//            holder.listItem.setVisibility(View.VISIBLE);
//            holder.itemTitle.setText(cart2.getMenuCategory().get(listPosition).getMenuProducts().get(0).getProductName());
//            holder.itemPrice.setText("£"+String.format("%.2f",cart2.getMenuCategory().get(listPosition).getMenuProducts().get(0).getTotalAmmount()));
//            subTotalAmount = subTotalAmount + cart2.getMenuCategory().get(listPosition).getMenuProducts().get(0).getTotalAmmount();
//            discount.setText("£0.00");
//            total_count = total_count + cart2.getMenuCategory().get(listPosition).getMenuProducts().get(0).getQuantity();
//            for(int i=0; i < cart2.getMenuCategory().get(listPosition).getMenuProducts().size();i++){
//                for(int j=0;j<cart2.getMenuCategory().get(listPosition).getMenuProducts().get(i).getMenuProductSize().size();j++){
//                    for(int k=0;k<cart2.getMenuCategory().get(listPosition).getMenuProducts().get(i).getMenuProductSize().get(j).getSizeModifiers().size();k++){
//                        for(int l=0;l<cart2.getMenuCategory().get(listPosition).getMenuProducts().get(i).getMenuProductSize().get(j).getSizeModifiers().get(k).getSizeModifierProducts().size();l++){
//                            LinearLayout parent = new LinearLayout(mContext);
//
//                            parent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                            parent.setOrientation(LinearLayout.HORIZONTAL);
//
//                            TextView tv = new TextView(mContext); // Prepare textview object programmatically
//                            tv.setText(cart2.getMenuCategory().get(listPosition).getMenuProducts().get(i).getMenuProductSize().get(j).getSizeModifiers().get(k).getSizeModifierProducts().get(l).getProductName());
//                            tv.setTextColor(mContext.getResources().getColor(R.color.orange));
//                            tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
//
//                            TextView tv2 = new TextView(mContext); // Prepare textview object programmatically
//                            tv2.setText("£"+cart2.getMenuCategory().get(listPosition).getMenuProducts().get(i).getMenuProductSize().get(j).getSizeModifiers().get(k).getSizeModifierProducts().get(l).getTotalPrice());
//                            tv2.setGravity(Gravity.END);
//                            tv2.setTextColor(mContext.getResources().getColor(R.color.orange));
//                            tv2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
//
//                            totalItemPrice =totalItemPrice  + cart2.getMenuCategory().get(listPosition).getMenuProducts().get(i).getMenuProductSize().get(j).getSizeModifiers().get(k).getSizeModifierProducts().get(l).getTotalPrice();
//                            parent.addView(tv);
//                            parent.addView(tv2);
//                            holder.lySubItems.addView(parent); // Add to your ViewGroup using this method
//                        }
//                    }
//                }
//                for(int m=0;m<cart2.getMenuCategory().get(listPosition).getMenuProducts().get(i).getProductModifiers().size();m++){
//                    for(int n=0;n<cart2.getMenuCategory().get(listPosition).getMenuProducts().get(i).getProductModifiers().get(m).getModifierProducts().size();n++){
//                        LinearLayout parent = new LinearLayout(mContext);
//
//                        parent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                        parent.setOrientation(LinearLayout.HORIZONTAL);
//
//                        TextView tv = new TextView(mContext); // Prepare textview object programmatically
//                        tv.setText(cart2.getMenuCategory().get(listPosition).getMenuProducts().get(i).getProductModifiers().get(m).getModifierProducts().get(n).getProductName());
//                        tv.setTextColor(mContext.getResources().getColor(R.color.orange));
//                        tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
//
//                        TextView tv2 = new TextView(mContext); // Prepare textview object programmatically
//                        tv2.setText("£"+cart2.getMenuCategory().get(listPosition).getMenuProducts().get(i).getProductModifiers().get(m).getModifierProducts().get(n).getTotalPrice());
//                        tv2.setGravity(Gravity.END);
//                        tv2.setTextColor(mContext.getResources().getColor(R.color.orange));
//                        tv2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
//
//                        totalItemPrice =totalItemPrice  + cart2.getMenuCategory().get(listPosition).getMenuProducts().get(i).getProductModifiers().get(m).getModifierProducts().get(n).getTotalPrice();
//                        parent.addView(tv);
//                        parent.addView(tv2);
//                        holder.lySubItems.addView(parent); // Add to your ViewGroup using this method
//                    }
//                }
//
//            }
//
//            Log.e("SUB TOTAL >>",""+subTotalAmount);
//            //    holder.itemPrice.setText("£"+String.valueOf(cart2.getMenuCategory().get(listPosition).getMenuProducts().get(0).getTotalAmmount()+totalItemPrice));
//            subTotal.setText(String.format("%.2f",subTotalAmount));
//            totalAmmount.setText(String.format("%.2f",subTotalAmount+deliveryFees));
//
//            totalCount.setText(String.valueOf(total_count));
//            footerTotalCount.setText(String.valueOf(total_count));
//
//            footerTotalAmount.setText(String.format("%.2f",subTotalAmount+deliveryFees));
//            txtDeliveryFees.setText("£"+String.format("%.2f",deliveryFees));
//
//            holder.txttotalCount.setText(String.valueOf(cart2.getMenuCategory().get(listPosition).getMenuProducts().get(0).getQuantity()));
//
//            holder.itemCount.setText(String.valueOf(cart2.getMenuCategory().get(listPosition).getMenuProducts().get(0).getQuantity()));
//
//            holder.btnAdd.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    try {
//                        holder.txttotalCount.setText(String.valueOf(Integer.parseInt(holder.txttotalCount.getText().toString()) + 1));
//                        holder.itemCount.setText(String.valueOf(Integer.parseInt(holder.itemCount.getText().toString()) + 1));
//                        subTotal.setText(String.format("%.2f", Double.parseDouble(subTotal.getText().toString()) + Double.parseDouble(cart2.getMenuCategory().get(listPosition).getMenuProducts().get(0).getMenuProductPrice())));
//                        totalAmmount.setText(String.format("%.2f", (Double.parseDouble(totalAmmount.getText().toString())) + Double.parseDouble(cart2.getMenuCategory().get(listPosition).getMenuProducts().get(0).getMenuProductPrice())));
//                        footerTotalCount.setText(String.valueOf(Integer.parseInt(footerTotalCount.getText().toString()) + 1));
//                        totalCount.setText(String.valueOf(Integer.parseInt(totalCount.getText().toString()) + 1));
//                        footerTotalAmount.setText(String.format("%.2f", (Double.parseDouble(footerTotalAmount.getText().toString())) + Double.parseDouble(cart2.getMenuCategory().get(listPosition).getMenuProducts().get(0).getMenuProductPrice())));
//
//                        com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuProduct mp = (com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuProduct) cartList.get(0).getMenuCategory().get(0).getMenuProducts().get(listPosition);
//
//                        mp.setQuantity(mp.getQuantity() + 1);
//                        mp.setTotalAmmount(mp.getTotalAmmount() + Double.parseDouble(mp.getMenuProductPrice()));
//
//                        Gson gson = new Gson();
//                        String json = gson.toJson(cartList);
//                        SharedPreferencesClass sharedPreferencesClass = new SharedPreferencesClass(mContext);
//                        sharedPreferencesClass.setCartDetailsKey(json);
//                        logLargeString(json);
//                    }catch (Exception e){
//
//                    }
//                }
//            });
//            holder.btnRemove.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(Integer.parseInt(holder.txttotalCount.getText().toString()) > 1) {
//                        try {
//                        holder.txttotalCount.setText(String.valueOf(Integer.parseInt(holder.txttotalCount.getText().toString()) - 1));
//                        holder.itemCount.setText(String.valueOf(Integer.parseInt(holder.itemCount.getText().toString())-1));
//                        footerTotalCount.setText(String.valueOf(Integer.parseInt(footerTotalCount.getText().toString())-1));
//                        totalCount.setText(String.valueOf(Integer.parseInt(totalCount.getText().toString())-1));
//                        subTotal.setText(String.format("%.2f",(Double.parseDouble(subTotal.getText().toString()))-Double.parseDouble(cart2.getMenuCategory().get(listPosition).getMenuProducts().get(0).getMenuProductPrice())));
//                        totalAmmount.setText(String.format("%.2f",(Double.parseDouble(totalAmmount.getText().toString())) - Double.parseDouble(cart2.getMenuCategory().get(listPosition).getMenuProducts().get(0).getMenuProductPrice())));
//                        footerTotalAmount.setText(String.format("%.2f",(Double.parseDouble(footerTotalAmount.getText().toString())) - Double.parseDouble(cart2.getMenuCategory().get(listPosition).getMenuProducts().get(0).getMenuProductPrice())));
//                        com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuProduct mp = (com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuProduct) cartList.get(0).getMenuCategory().get(0).getMenuProducts().get(listPosition);
//
//                        mp.setQuantity(mp.getQuantity() - 1);
//                        mp.setTotalAmmount(mp.getTotalAmmount() - Double.parseDouble(mp.getMenuProductPrice()));
//
//                        Gson gson = new Gson();
//                        String json = gson.toJson(cartList);
//                        SharedPreferencesClass sharedPreferencesClass = new SharedPreferencesClass(mContext);
//                        sharedPreferencesClass.setCartDetailsKey(json);
//                        logLargeString(json);
//                        }catch (Exception e){
//
//                        }
//                    }
//                    if(Integer.parseInt(holder.txttotalCount.getText().toString()) - 1 == 0){
////                        List<com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuProduct> mpList = (List<com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuProduct>) cartList.get(0).getMenuCategory().get(0);
////                        mpList.remove(listPosition);
//                    }
//                }
//            });
////            Adapter_basket_items productModifierAdapter = new Adapter_basket_items(mContext,cart2,listPosition);
////            @SuppressLint("WrongConstant")
////            LinearLayoutManager horizontalLayoutManagaer2
////                    = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
////            holder.listItem.setLayoutManager(horizontalLayoutManagaer2);
////            holder.listItem.setAdapter(productModifierAdapter);
//        }

        pos = listPosition;
        Log.e("LEN CART", "" + cartList.getData().size());
        final com.lexxdigital.easyfooduserapps.cart_model.final_cart.Datum cart2;
        if (cartList.getData().size() > 0) {

            if (cartList.getData().get(listPosition).getMenuCategory() != null) {
                Log.e("LEN CART", "Manoj Kumar");
                cart2 = (com.lexxdigital.easyfooduserapps.cart_model.final_cart.Datum) cartList.getData().get(listPosition);
                holder.listItem.setVisibility(View.VISIBLE);
                holder.itemTitle.setText(cart2.getMenuCategory().getMenuProducts().getProductName());
                holder.itemPrice.setText("£" + String.format("%.2f", Double.parseDouble(cart2.getMenuCategory().getAmount())));
                subTotalAmount = subTotalAmount + Double.parseDouble(cart2.getMenuCategory().getAmount());
                discount.setText("£0.00");
                total_count = cart2.getMenuCategory().getQuantity();
                //      for(int i=0; i < cart2.getMenuCategory().getMenuProducts().size();i++){
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

                //    }

                Log.e("TOTAL >>", "" + total_count);
                //    holder.itemPrice.setText("£"+String.valueOf(cart2.getMenuCategory().get(listPosition).getMenuProducts().get(0).getTotalAmmount()+totalItemPrice));
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
                        try {
//                        holder.txttotalCount.setText(String.valueOf(Integer.parseInt(holder.txttotalCount.getText().toString()) + 1));
//                        holder.itemCount.setText(String.valueOf(Integer.parseInt(holder.itemCount.getText().toString()) + 1));
//                        subTotal.setText(String.format("%.2f", Double.parseDouble(subTotal.getText().toString()) + Double.parseDouble(cart2.getMenuCategory().get(listPosition).getMenuProducts().get(0).getMenuProductPrice())));
//                        totalAmmount.setText(String.format("%.2f", (Double.parseDouble(totalAmmount.getText().toString())) + Double.parseDouble(cart2.getMenuCategory().get(listPosition).getMenuProducts().get(0).getMenuProductPrice())));
//                        footerTotalCount.setText(String.valueOf(Integer.parseInt(footerTotalCount.getText().toString()) + 1));
//                        totalCount.setText(String.valueOf(Integer.parseInt(totalCount.getText().toString()) + 1));
//                        footerTotalAmount.setText(String.format("%.2f", (Double.parseDouble(footerTotalAmount.getText().toString())) + Double.parseDouble(cart2.getMenuCategory().get(listPosition).getMenuProducts().get(0).getMenuProductPrice())));
//
//                        com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuProduct mp = (com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuProduct) cartList.get(0).getMenuCategory().get(0).getMenuProducts().get(listPosition);
//
//                        mp.setQuantity(mp.getQuantity() + 1);
//                        mp.setTotalAmmount(mp.getTotalAmmount() + Double.parseDouble(mp.getMenuProductPrice()));
//
//                        Gson gson = new Gson();
//                        String json = gson.toJson(cartList);
//                        SharedPreferencesClass sharedPreferencesClass = new SharedPreferencesClass(mContext);
//                        sharedPreferencesClass.setCartDetailsKey(json);
//                        logLargeString(json);
                        } catch (Exception e) {

                        }
                    }
                });
                holder.btnRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Integer.parseInt(holder.txttotalCount.getText().toString()) > 1) {
                            try {
//                            holder.txttotalCount.setText(String.valueOf(Integer.parseInt(holder.txttotalCount.getText().toString()) - 1));
//                            holder.itemCount.setText(String.valueOf(Integer.parseInt(holder.itemCount.getText().toString())-1));
//                            footerTotalCount.setText(String.valueOf(Integer.parseInt(footerTotalCount.getText().toString())-1));
//                            totalCount.setText(String.valueOf(Integer.parseInt(totalCount.getText().toString())-1));
//                            subTotal.setText(String.format("%.2f",(Double.parseDouble(subTotal.getText().toString()))-Double.parseDouble(cart2.getMenuCategory().get(listPosition).getMenuProducts().get(0).getMenuProductPrice())));
//                            totalAmmount.setText(String.format("%.2f",(Double.parseDouble(totalAmmount.getText().toString())) - Double.parseDouble(cart2.getMenuCategory().get(listPosition).getMenuProducts().get(0).getMenuProductPrice())));
//                            footerTotalAmount.setText(String.format("%.2f",(Double.parseDouble(footerTotalAmount.getText().toString())) - Double.parseDouble(cart2.getMenuCategory().get(listPosition).getMenuProducts().get(0).getMenuProductPrice())));
//                            com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuProduct mp = (com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuProduct) cartList.get(0).getMenuCategory().get(0).getMenuProducts().get(listPosition);
//
//                            mp.setQuantity(mp.getQuantity() - 1);
//                            mp.setTotalAmmount(mp.getTotalAmmount() - Double.parseDouble(mp.getMenuProductPrice()));
//
//                            Gson gson = new Gson();
//                            String json = gson.toJson(cartList);
//                            SharedPreferencesClass sharedPreferencesClass = new SharedPreferencesClass(mContext);
//                            sharedPreferencesClass.setCartDetailsKey(json);
//                            logLargeString(json);
                            } catch (Exception e) {

                            }
                        }
                        if (Integer.parseInt(holder.txttotalCount.getText().toString()) - 1 == 0) {
//                        List<com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuProduct> mpList = (List<com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuProduct>) cartList.get(0).getMenuCategory().get(0);
//                        mpList.remove(listPosition);
                        }
                    }
                });
//            Adapter_basket_items productModifierAdapter = new Adapter_basket_items(mContext,cart2,listPosition);
//            @SuppressLint("WrongConstant")
//            LinearLayoutManager horizontalLayoutManagaer2
//                    = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
//            holder.listItem.setLayoutManager(horizontalLayoutManagaer2);
//            holder.listItem.setAdapter(productModifierAdapter);
            }


        }

    }

    public void logLargeString(String str) {
        if (str.length() > 3000) {
            Log.e("CART After>>", str.substring(0, 3000));
            logLargeString(str.substring(3000));
        } else {
            Log.e("CART After>>", str); // continuation
        }
    }

    @Override
    public int getItemCount() {
        return num;
    }
}