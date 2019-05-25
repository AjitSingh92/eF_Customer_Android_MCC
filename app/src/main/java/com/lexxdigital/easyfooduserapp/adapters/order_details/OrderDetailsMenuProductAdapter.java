package com.lexxdigital.easyfooduserapp.adapters.order_details;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapp.model.myorder.OrderDetails;
import com.lexxdigital.easyfooduserapp.model.order_details.Data;
import com.lexxdigital.easyfooduserapp.order_details_activity.OrderDetailActivity;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.CartData;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MenuProductSize;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.Modifier;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.ProductModifier;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.SizeModifier;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.UpsellProduct;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsMenuProductAdapter extends RecyclerView.Adapter<OrderDetailsMenuProductAdapter.MyViewHohlder> {
    Context context;
    private List<MenuProduct> mItem;
    DatabaseHelper db;

    public OrderDetailsMenuProductAdapter(Context context) {
        this.context = context;
        this.mItem = new ArrayList<>();
        db = new DatabaseHelper(context);
    }

    public void clearData() {
        this.mItem.clear();
        notifyDataSetChanged();
    }

    public void addItem(List<MenuProduct> mItem) {
        this.mItem.addAll(mItem);
        notifyItemChanged(this.mItem.size());
    }

    public void addItem(MenuProduct mItem) {
        this.mItem.add(mItem);
        notifyItemChanged(this.mItem.size());
    }

    public class MyViewHohlder extends RecyclerView.ViewHolder {
        TextView subProductName, subProductPrice;
        LinearLayout lySubItems, mealModifiers, lySubProdModf;

        public MyViewHohlder(View view) {
            super(view);
            this.subProductName = (TextView) itemView.findViewById(R.id.subprod_name);
            this.subProductPrice = (TextView) itemView.findViewById(R.id.sub_prod_price);
            this.lySubItems = itemView.findViewById(R.id.sub_product);
            this.mealModifiers = itemView.findViewById(R.id.layout_meal_modifiers);

        }
    }

    @NonNull
    @Override
    public MyViewHohlder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sub_product_order_list, viewGroup, false);
        OrderDetailsMenuProductAdapter.MyViewHohlder myViewHohlder = new OrderDetailsMenuProductAdapter.MyViewHohlder(view);
        return myViewHohlder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHohlder holder, int position) {
        /*CartData dataList = datumList;
        Log.e("SubProductListAdapter", "cartList.size():" + dataList.getMenuCategoryCarts().size());
        for (int i = 0; i < dataList.getMenuCategoryCarts().size(); i++) {
            for (int j = 0; j < dataList.getMenuCategoryCarts().get(i).getMenuProducts().size(); j++) {

                holder.subProductName.setText(dataList.getMenuCategoryCarts().get(i).getMenuProducts().get(j).getProductName());
                String prodAmount = dataList.getMenuCategoryCarts().get(i).getMenuProducts().get(j).getMenuProductPrice();
                if (prodAmount != null && !prodAmount.trim().equalsIgnoreCase("")) {
                    holder.subProductPrice.setText("£" + prodAmount);
                } else {
                    holder.subProductPrice.setText("£" + 0);

                }


                for (int k = 0; k < dataList.getMenuCategoryCarts().get(i).getMenuProducts().get(j).getMenuProductSize().size(); k++) {
                    LinearLayout parent = new LinearLayout(context);

                    parent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    parent.setOrientation(LinearLayout.HORIZONTAL);
                    Log.e(TAG, "onBindViewHolder: " + dataList.getMenuCategoryCarts().get(i).getMenuProducts().get(j).getMenuProductSize().get(k).getProductSizeName());
                    TextView tv = new TextView(context); // Prepare textview object programmatically
                    tv.setText(dataList.getMenuCategoryCarts().get(i).getMenuProducts().get(j).getMenuProductSize().get(k).getProductSizeName());
                    // tv.setTextColor(context.getResources().getColor(R.color.orange));
                    tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

                    TextView tv2 = new TextView(context); // Prepare textview object programmatically
                    String prodSizeAmount = dataList.getMenuCategoryCarts().get(i).getMenuProducts().get(j).getMenuProductSize().get(k).getProductSizePrice();
                    if (prodSizeAmount != null && !prodSizeAmount.trim().equalsIgnoreCase("")) {
                        tv2.setText("£" + prodSizeAmount);
                    } else {
                        tv2.setText("£" + 0);
                    }
                    tv2.setGravity(Gravity.END);
                    //  tv2.setTextColor(context.getResources().getColor(R.color.orange));
                    tv2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

                    // totalItemPrice = totalItemPrice + orderDetailsRes.getData().get(p).getMenuCategory().get(listPosition).getMenuProducts().get(i).getMenuProductSize().get(j).getSizeModifiers().get(k).getSizeModifierProducts().get(l).getTotalPrice();
                    parent.addView(tv);
                    parent.addView(tv2);
                    holder.lySubItems.addView(parent); // Add to your ViewGroup using this method
                    try {
                        if (dataList.getMenuCategoryCarts().size() > 0) {
                            for (int l = 0; l < dataList.getMenuCategoryCarts().get(i).getMenuProducts().get(j).getMenuProductSize().get(k).getSizeModifiers().size(); l++) {
                                if (dataList.getMenuCategoryCarts().get(i).getMenuProducts().size() > 0) {

                                    for (int m = 0; m < dataList.getMenuCategoryCarts().get(i).getMenuProducts().get(j).getMenuProductSize().get(k).getSizeModifiers().get(l).getModifier().size(); m++) {
                                        LinearLayout parent2 = new LinearLayout(context);

                                        parent2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        parent2.setOrientation(LinearLayout.HORIZONTAL);
                                        TextView prodModifName = new TextView(context); // Prepare textview object programmatically
                                        Log.e(TAG, "onBindViewHolder: modif namemmmmmmmmmmmmm: " + dataList.getMenuCategoryCarts().get(i).getMenuProducts().get(j).getMenuProductSize().get(k).getSizeModifiers().get(l).getModifier().size());
                                        prodModifName.setText(dataList.getMenuCategoryCarts().get(i).getMenuProducts().get(j).getMenuProductSize().get(k).getSizeModifiers().get(l).getModifier().get(m).getProductName());
                                        //  prodModifName.setTextColor(context.getResources().getColor(R.color.orange));
                                        prodModifName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

                                        TextView prodModifprice = new TextView(context); // Prepare textview object programmatically
                                        prodModifprice.setText("£" + dataList.getMenuCategoryCarts().get(i).getMenuProducts().get(j).getMenuProductSize().get(k).getSizeModifiers().get(l).getModifier().get(m).getModifierProductPrice());
                                        //  prodModifprice.setTextColor(context.getResources().getColor(R.color.orange));
                                        prodModifprice.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
                                        prodModifprice.setGravity(Gravity.END);
                                        parent2.addView(prodModifName);
                                        parent2.addView(prodModifprice);
                                        holder.lySubItems.addView(parent2); // Add to your ViewGroup using this method

                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "onBindViewHolder: " + e.getMessage());
                    }
                }
            }
        }*/

        int itemQty = mItem.get(position).getOriginalQuantity();
        holder.lySubItems.removeAllViews();
        Double totalPrice = 0d;
            /*if (mItem.get(position).getMenuProductSize().size() > 0 && mItem.get(position).getProductModifiers().size() == 0) {
                totalPrice += (itemQty * Double.parseDouble(mItem.get(position).getMenuProductPrice()));
            } else {
                if (mItem.get(position).getMenuProductSize().size() > 0) {
                    totalPrice += (itemQty * Double.parseDouble(mItem.get(position).getMenuProductSize().get(0).getProductSizePrice()));
                }
            }*/

        if ((OrderDetailActivity.menuCategory).equalsIgnoreCase("MEAL")) {

            holder.subProductName.setText(itemQty + "x " + mItem.get(position).getProductName());
            holder.subProductPrice.setText(context.getResources().getString(R.string.currency) + "" + mItem.get(position).getMenuProductPrice());
            totalPrice += (itemQty * Double.parseDouble(mItem.get(position).getMenuProductPrice()));

            for (int i = 0; i < mItem.get(position).getMealProducts().size(); i++) {
                View view = LayoutInflater.from(context).inflate(R.layout.item_modifier, null);
                ((TextView) view.findViewById(R.id.tv_title)).setText(mItem.get(position).getMealProducts().get(i).getQuantity() + "x " + mItem.get(position).getMealProducts().get(i).getProductSizeName() + " " + mItem.get(position).getMealProducts().get(i).getProductName());
                ((TextView) view.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + "0.00");


                if (mItem.get(position).getMealProducts().get(i).getMenuProductSize() != null) {
                    for (int j = 0; j < mItem.get(position).getMealProducts().get(i).getMenuProductSize().size(); j++) {

                        for (int k = 0; k < mItem.get(position).getMealProducts().get(i).getMenuProductSize().get(j).getSizeModifiers().size(); k++) {

                            int maxAllowFree = mItem.get(position).getMealProducts().get(i).getMenuProductSize().get(j).getSizeModifiers().get(k).getMaxAllowedQuantity();
                            int free = 0;
                            if (mItem.get(position).getMealProducts().get(i).getMenuProductSize().get(j).getSizeModifiers().get(k).getModifierType().equalsIgnoreCase("free")) {

                                for (int l = 0; l < mItem.get(position).getMealProducts().get(i).getMenuProductSize().get(j).getSizeModifiers().get(k).getModifier().size(); l++) {

                                    View view1 = LayoutInflater.from(context).inflate(R.layout.item_meal_modifier, null);
                                    ((TextView) view1.findViewById(R.id.tv_title)).setText(mItem.get(position).getMealProducts().get(i).getMenuProductSize().get(j).getSizeModifiers().get(k).getModifier().get(l).getOriginalQuantity() + "x " + mItem.get(position).getMealProducts().get(i).getMenuProductSize().get(j).getSizeModifiers().get(k).getModifier().get(l).getProductName());

                                    if (free == maxAllowFree) {
                                        ((TextView) view1.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format(mItem.get(position).getMealProducts().get(i).getMenuProductSize().get(j).getSizeModifiers().get(k).getModifier().get(l).getModifierProductPrice()));

                                    } else {
                                        ((TextView) view1.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + "0.00");
                                        free++;

                                    }
                                    holder.mealModifiers.addView(view1);

                                }
                            } else {

                                for (int l = 0; l < mItem.get(position).getMealProducts().get(i).getMenuProductSize().get(j).getSizeModifiers().get(k).getModifier().size(); l++) {

                                    View view1 = LayoutInflater.from(context).inflate(R.layout.item_meal_modifier, null);
                                    ((TextView) view1.findViewById(R.id.tv_title)).setText(mItem.get(position).getMealProducts().get(i).getMenuProductSize().get(j).getSizeModifiers().get(k).getModifier().get(l).getOriginalQuantity() + "x " + mItem.get(position).getMealProducts().get(i).getMenuProductSize().get(j).getSizeModifiers().get(k).getModifier().get(l).getProductName());
                                    ((TextView) view1.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format(mItem.get(position).getMealProducts().get(i).getMenuProductSize().get(j).getSizeModifiers().get(k).getModifier().get(l).getModifierProductPrice()));
                                    holder.mealModifiers.addView(view1);

                                }
                            }

                        }

                    }
                }
                holder.lySubItems.addView(view);
            }


        } else {

            if (mItem.get(position).getMenuProductSize().size() > 0) {
                holder.subProductName.setText(itemQty + "x " + mItem.get(position).getMenuProductSize().get(0).getProductSizeName() + " " + mItem.get(position).getProductName());

                totalPrice += (itemQty * Double.parseDouble(mItem.get(position).getMenuProductSize().get(0).getProductSizePrice()));
            } else {
                holder.subProductName.setText(itemQty + "x " + mItem.get(position).getProductName());
                totalPrice += (itemQty * Double.parseDouble(mItem.get(position).getMenuProductPrice()));
            }
            holder.subProductPrice.setText("£" + String.format("%.2f", totalPrice));

            if (mItem.get(position).getMenuProductSize().size() > 0) {

                for (MenuProductSize menuProductSize1 : mItem.get(position).getMenuProductSize()) {
                    if (menuProductSize1.getSelected()) {
                        for (SizeModifier sizeModifier : menuProductSize1.getSizeModifiers()) {

                            if (sizeModifier.getModifierType().equalsIgnoreCase("free")) {
                                int maxAllowFree = sizeModifier.getMaxAllowedQuantity();
                                int free = 0;
                                for (int i = 0; i < sizeModifier.getModifier().size(); i++) {
                                    int qty = Integer.parseInt(sizeModifier.getModifier().get(i).getOriginalQuantity());
                                    qty = (qty * itemQty);

                                    View view = LayoutInflater.from(context).inflate(R.layout.item_modifier, null);
                                    ((TextView) view.findViewById(R.id.tv_title)).setText(qty + "x " + sizeModifier.getModifier().get(i).getProductName());

                                    if (free == maxAllowFree) {
                                        ((TextView) view.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format("%.2f", (qty * Double.parseDouble(sizeModifier.getModifier().get(i).getModifierProductPrice()))));
                                    } else {
                                        int qtyy = Integer.parseInt(sizeModifier.getModifier().get(i).getOriginalQuantity());
                                        if (qtyy >= maxAllowFree) {
                                            int nQty = qtyy - maxAllowFree;
                                            free = maxAllowFree;
                                            qtyy = (nQty * itemQty);
                                            ((TextView) view.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format("%.2f", (qtyy * Double.parseDouble(sizeModifier.getModifier().get(i).getModifierProductPrice()))));
                                        } else {
                                            ((TextView) view.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + "0.00");
                                            free++;
                                        }
                                    }
                                    holder.lySubItems.addView(view);
                                }
                            } else {
                                for (Modifier modifier : sizeModifier.getModifier()) {
                                    int qty = Integer.parseInt(modifier.getOriginalQuantity());
                                    qty = (qty * itemQty);

                                    View view = LayoutInflater.from(context).inflate(R.layout.item_modifier, null);
                                    ((TextView) view.findViewById(R.id.tv_title)).setText(qty + "x " + modifier.getProductName());
                                    ((TextView) view.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format("%.2f", (qty * Double.parseDouble(modifier.getModifierProductPrice()))));
                                    holder.lySubItems.addView(view);
                                }
                            }
                        }
                    }
                }
            }

            if (mItem.get(position).getProductModifiers().size() > 0) {
                for (ProductModifier productModifier : mItem.get(position).getProductModifiers()) {
                    if (productModifier.getModifierType().equalsIgnoreCase("free")) {
                        int maxAllowFree = productModifier.getMaxAllowedQuantity();
                        int free = 0;
                        for (int i = 0; i < productModifier.getModifier().size(); i++) {
                            int qty = Integer.parseInt(productModifier.getModifier().get(i).getOriginalQuantity());
                            qty = (qty * itemQty);

                            View view = LayoutInflater.from(context).inflate(R.layout.item_modifier, null);
                            ((TextView) view.findViewById(R.id.tv_title)).setText(qty + "x " + productModifier.getModifier().get(i).getProductName());

                            if (free == maxAllowFree) {
                                ((TextView) view.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format("%.2f", (qty * Double.parseDouble(productModifier.getModifier().get(i).getModifierProductPrice()))));

                            } else {
                                int qtyy = Integer.parseInt(productModifier.getModifier().get(i).getOriginalQuantity());
                                if (qtyy > maxAllowFree) {
                                    int nQty = qtyy - maxAllowFree;
                                    free = maxAllowFree;
                                    qtyy = (nQty * itemQty);
                                    ((TextView) view.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format("%.2f", (qtyy * Double.parseDouble(productModifier.getModifier().get(i).getModifierProductPrice()))));
                                } else {
                                    ((TextView) view.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + "0.00");
                                    free++;
                                }
                            }
                            holder.lySubItems.addView(view);
                        }
                    } else {
                        for (Modifier modifier : productModifier.getModifier()) {
                            int qty = Integer.parseInt(modifier.getOriginalQuantity());
                            qty = (qty * itemQty);


                            View view = LayoutInflater.from(context).inflate(R.layout.item_modifier, null);
                            ((TextView) view.findViewById(R.id.tv_title)).setText(qty + "x " + modifier.getProductName());
                            ((TextView) view.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format("%.2f", (qty * Double.parseDouble(modifier.getModifierProductPrice()))));
                            holder.lySubItems.addView(view);
                        }
                    }
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }


}
