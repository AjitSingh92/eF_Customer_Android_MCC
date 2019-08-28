package com.lexxdigital.easyfooduserapps.adapters.order_details;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuProductSize;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.Modifier;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.ProductModifier;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.SizeModifier;

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
        LinearLayout lySubItems, lySubProdModf;

        public MyViewHohlder(View view) {
            super(view);
            this.subProductName = (TextView) itemView.findViewById(R.id.subprod_name);
            this.subProductPrice = (TextView) itemView.findViewById(R.id.sub_prod_price);
            this.lySubItems = itemView.findViewById(R.id.sub_product);


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
        if (mItem.get(position).getMenuProductSize() != null && mItem.get(position).getMenuProductSize().size() > 0) {
            holder.subProductName.setText(itemQty + "x " + mItem.get(position).getMenuProductSize().get(0).getProductSizeName() + " " + mItem.get(position).getProductName());
            totalPrice += (itemQty * Double.parseDouble(mItem.get(position).getMenuProductSize().get(0).getProductSizePrice()));
        } else {
            holder.subProductName.setText(itemQty + "x " + mItem.get(position).getProductName());
            totalPrice += (itemQty * Double.parseDouble(mItem.get(position).getMenuProductPrice()));
        }
        holder.subProductPrice.setText("£" + String.format("%.2f", totalPrice));


        if (mItem.get(position).getMenuProductSize() != null && mItem.get(position).getMenuProductSize().size() > 0) {

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
//                                    ((TextView) view.findViewById(R.id.tv_title)).setText(qty + "x " + sizeModifier.getModifier().get(i).getProductName());

                                if (free == maxAllowFree) {
                                    ((TextView) view.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format("%.2f", (qty * Double.parseDouble(sizeModifier.getModifier().get(i).getModifierProductPrice()))));
                                    ((TextView) view.findViewById(R.id.tv_title)).setText(qty + "x " + sizeModifier.getModifier().get(i).getProductName());

                                } else {
                                    int qtyy = Integer.parseInt(sizeModifier.getModifier().get(i).getOriginalQuantity());
                                    if (qtyy >= maxAllowFree) {
                                        int nQty = qtyy - maxAllowFree;
                                        free = maxAllowFree;
                                        int _qtyy = (nQty * itemQty);

//                                            ((TextView) view.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format("%.2f", (_qtyy * Double.parseDouble(sizeModifier.getModifier().get(i).getModifierProductPrice()))));

                                        if (nQty == 0) {
                                            ((TextView) view.findViewById(R.id.tv_title)).setText(qty + "x " + sizeModifier.getModifier().get(i).getProductName());
                                            ((TextView) view.findViewById(R.id.tv_price)).setText("Free");

                                        } else if (nQty > 0) {
                                            ((TextView) view.findViewById(R.id.tv_title)).setText(_qtyy + "x " + sizeModifier.getModifier().get(i).getProductName());
                                            ((TextView) view.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format("%.2f", (_qtyy * Double.parseDouble(sizeModifier.getModifier().get(i).getModifierProductPrice()))));

                                            View viewFree = LayoutInflater.from(context).inflate(R.layout.item_modifier, null);
                                            ((TextView) viewFree.findViewById(R.id.tv_title)).setText(maxAllowFree + "x " + sizeModifier.getModifier().get(i).getProductName());
                                            ((TextView) viewFree.findViewById(R.id.tv_price)).setText("Free");
                                            holder.lySubItems.addView(viewFree);

                                        }

                                    } else {
                                        ((TextView) view.findViewById(R.id.tv_title)).setText(qty + "x " + sizeModifier.getModifier().get(i).getProductName());

                                        ((TextView) view.findViewById(R.id.tv_price)).setText("free");
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
        } else {

            if (mItem.get(position).getMealProducts() != null) {
                for (int p = 0; p < mItem.get(position).getMealProducts().size(); p++) {
                    View _view = LayoutInflater.from(context).inflate(R.layout.item_modifier, null);
//                        ((TextView) _view.findViewById(R.id.tv_title)).setText(mItem.get(position).getMealProducts().get(p).getQuantity() + "x " +  mItem.get(position).getMealProducts().get(p).getProductName()+" "+mItem.get(position).getMealProducts().get(p).getProductSizeName());
                    ((TextView) _view.findViewById(R.id.tv_title)).setText(itemQty + "x " + mItem.get(position).getMealProducts().get(p).getProductName() + " " + mItem.get(position).getMealProducts().get(p).getProductSizeName());
                    ((TextView) _view.findViewById(R.id.tv_price)).setVisibility(View.VISIBLE);
                    ((TextView) _view.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + "0.00");

                    holder.lySubItems.addView(_view);
                    if (mItem.get(position).getMealProducts().get(p).getMenuProductSize() != null && mItem.get(position).getMealProducts().get(p).getMenuProductSize().size() > 0) {
                        for (MenuProductSize menuProductSize1 : mItem.get(position).getMealProducts().get(p).getMenuProductSize()) {
                            if (menuProductSize1.getSelected()) {
                                for (SizeModifier sizeModifier : menuProductSize1.getSizeModifiers()) {

                                    if (sizeModifier.getModifierType().equalsIgnoreCase("free")) {
                                        int maxAllowFree = sizeModifier.getMaxAllowedQuantity();
                                        int free = 0;
                                        for (int i = 0; i < sizeModifier.getModifier().size(); i++) {
                                            int qty = Integer.parseInt(sizeModifier.getModifier().get(i).getOriginalQuantity());
                                            qty = (qty * itemQty);

                                            View view = LayoutInflater.from(context).inflate(R.layout.item_modifier, null);
//                                    ((TextView) view.findViewById(R.id.tv_title)).setText(qty + "x " + sizeModifier.getModifier().get(i).getProductName());

                                            if (free == maxAllowFree) {
                                                ((TextView) view.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format("%.2f", (qty * Double.parseDouble(sizeModifier.getModifier().get(i).getModifierProductPrice()))));
                                                ((TextView) view.findViewById(R.id.tv_title)).setText(qty + "x " + sizeModifier.getModifier().get(i).getProductName());

                                            } else {
                                                int qtyy = Integer.parseInt(sizeModifier.getModifier().get(i).getOriginalQuantity());
                                                if (qtyy >= maxAllowFree) {
                                                    int nQty = qtyy - maxAllowFree;
                                                    free = maxAllowFree;
                                                    int _qtyy = (nQty * itemQty);

//                                            ((TextView) view.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format("%.2f", (_qtyy * Double.parseDouble(sizeModifier.getModifier().get(i).getModifierProductPrice()))));

                                                    if (nQty == 0) {
                                                        ((TextView) view.findViewById(R.id.tv_title)).setText(qty + "x " + sizeModifier.getModifier().get(i).getProductName());
                                                        ((TextView) view.findViewById(R.id.tv_price)).setText("Free");

                                                    } else if (nQty > 0) {
                                                        ((TextView) view.findViewById(R.id.tv_title)).setText(_qtyy + "x " + sizeModifier.getModifier().get(i).getProductName());
                                                        ((TextView) view.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format("%.2f", (_qtyy * Double.parseDouble(sizeModifier.getModifier().get(i).getModifierProductPrice()))));

                                                        View viewFree = LayoutInflater.from(context).inflate(R.layout.item_modifier, null);
                                                        ((TextView) viewFree.findViewById(R.id.tv_title)).setText(maxAllowFree + "x " + sizeModifier.getModifier().get(i).getProductName());
                                                        ((TextView) viewFree.findViewById(R.id.tv_price)).setText("Free");
                                                        holder.lySubItems.addView(viewFree);

                                                    }

                                                } else {
//                                            ((TextView) view.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + "0.00");
                                                    ((TextView) view.findViewById(R.id.tv_price)).setText("free");
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
                }
            } else {
            }
        }

        if (mItem.get(position).

                getProductModifiers() != null) {
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
//                                    ((TextView) view.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + "0.00");
                                    ((TextView) view.findViewById(R.id.tv_price)).setText("free");
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
