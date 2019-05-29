package com.lexxdigital.easyfooduserapp.adapters.menu_adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MenuProductSize;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.Modifier;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.ProductModifier;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.SizeModifier;

import java.util.ArrayList;
import java.util.List;

public class AgainAdapter extends RecyclerView.Adapter<AgainAdapter.AgaincategoryViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private List<MenuProduct> data;
    int action;
    DatabaseHelper db;
    OnItemUpdate onItemUpdate;

    public interface OnItemUpdate {
        void onItemUpdate();
    }

    public AgainAdapter(Context context, int action, OnItemUpdate onItemUpdate) {
        this.context = context;
        this.action = action;
        this.onItemUpdate = onItemUpdate;
        inflater = LayoutInflater.from(context);
        this.data = new ArrayList<>();
        db = new DatabaseHelper(context);
    }

    public void clearData() {
        this.data.clear();
        notifyDataSetChanged();
    }

    public void addItem(List<MenuProduct> mItem) {
        this.data.addAll(mItem);
        notifyItemChanged(this.data.size());
        Log.e("Aklesh>>>>", "" + data.size());
    }

    public void addItem(MenuProduct mItem) {
        this.data.add(mItem);
        notifyItemChanged(this.data.size());
    }

    @NonNull
    @Override
    public AgaincategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AgaincategoryViewHolder(inflater.inflate(R.layout.again_row, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AgaincategoryViewHolder againcategoryViewHolder, int position) {
        againcategoryViewHolder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class AgaincategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvCategory, itemPrice;
        private final LinearLayout llAdd;
        private final LinearLayout llMinus, llModifier;
        private final TextView tvQty;

        public AgaincategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.item);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            llAdd = itemView.findViewById(R.id.item_add);
            llAdd.setOnClickListener(this);
            llMinus = itemView.findViewById(R.id.item_remove);
            llMinus.setOnClickListener(this);
            llModifier = itemView.findViewById(R.id.llModifier);
            tvQty = itemView.findViewById(R.id.item_count);
        }

        private void bindData(int position) {
            Log.e("Action", action + "");
            if (action == 2) {
                llAdd.setVisibility(View.VISIBLE);
                llMinus.setVisibility(View.GONE);
            } else if (action == 1) {
                llAdd.setVisibility(View.GONE);
                llMinus.setVisibility(View.VISIBLE);
            } else {
                llAdd.setVisibility(View.GONE);
                llMinus.setVisibility(View.GONE);
            }

            tvCategory.setText(data.get(position).getProductName());
            //tvCategory.setText(data.get(position).getQuantity() + "x" + data.get(position).getProductName());

            int itemQty = data.get(position).getOriginalQuantity();
            tvQty.setText(String.valueOf(itemQty));
            llModifier.removeAllViews();
            Double itemTotalPrice = 0d;
            if (data.get(position).getMenuProductSize() != null && data.get(position).getMenuProductSize().size() > 0) {
                for (MenuProductSize menuProductSize : data.get(position).getMenuProductSize()) {
                    itemTotalPrice += (itemQty * Double.parseDouble(menuProductSize.getProductSizePrice()));
                    View child1 = ((AppCompatActivity) context).getLayoutInflater().inflate(R.layout.again_modifier_row, null);
                    ((TextView) child1.findViewById(R.id.tv_name)).setTypeface(null, Typeface.BOLD);
                    ((TextView) child1.findViewById(R.id.tv_name)).setText(itemQty + "x" + menuProductSize.getProductSizeName());
                    ((TextView) child1.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format("%.2f", (itemQty * Double.parseDouble(menuProductSize.getProductSizePrice()))));
                    llModifier.addView(child1);
                    if (menuProductSize.getSizeModifiers().size() > 0) {
                        for (SizeModifier sizeModifier : menuProductSize.getSizeModifiers()) {
                            if (sizeModifier.getModifier().size() > 0) {
                                View child2 = ((AppCompatActivity) context).getLayoutInflater().inflate(R.layout.again_modifier_row, null);
                                ((TextView) child2.findViewById(R.id.tv_name)).setPadding(23, 0, 0, 0);
                                ((TextView) child2.findViewById(R.id.tv_name)).setTypeface(null, Typeface.BOLD);
                                //((TextView) child2.findViewById(R.id.tv_name)).setText(itemQty + "x" + ((sizeModifier.getModifierName().trim().length() == 0) ? " Modifier" : sizeModifier.getModifierName()));
                                ((TextView) child2.findViewById(R.id.tv_name)).setText(((sizeModifier.getModifierName().trim().length() == 0) ? "Modifier" : sizeModifier.getModifierName()));
                                ((TextView) child2.findViewById(R.id.tv_price)).setVisibility(View.GONE);
//                            ((TextView) child2.findViewById(R.id.tv_price)).setText("FREE :"+sizeModifier.getMaxAllowedQuantity());
                                llModifier.addView(child2);
                                int maxAllowFree = sizeModifier.getMaxAllowedQuantity();
                                int free = 0;
                                for (Modifier modifier : sizeModifier.getModifier()) {
                                    View child = ((AppCompatActivity) context).getLayoutInflater().inflate(R.layout.again_modifier_row, null);
                                    int qtyy = (itemQty * Integer.parseInt(modifier.getOriginalQuantity()));
                                    ((TextView) child.findViewById(R.id.tv_name)).setPadding(20, 0, 0, 0);
                                    ((TextView) child.findViewById(R.id.tv_name)).setText(qtyy + "x" + modifier.getProductName());


                                    if (sizeModifier.getModifierType().equalsIgnoreCase("free")) {
                                        if (free == maxAllowFree) {
                                            ((TextView) child.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format("%.2f", (qtyy * Double.parseDouble(modifier.getModifierProductPrice()))));
                                            itemTotalPrice += (qtyy * Double.parseDouble(modifier.getModifierProductPrice()));
                                        } else {
                                            int qty = Integer.parseInt(modifier.getOriginalQuantity());
                                            if (qty >= maxAllowFree) {
                                                int nQty = qty - maxAllowFree;
                                                free = maxAllowFree;
                                                qty = (nQty * itemQty);
                                                ((TextView) child.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format("%.2f", (qty * Double.parseDouble(modifier.getModifierProductPrice()))));
                                                itemTotalPrice += (qty * Double.parseDouble(modifier.getModifierProductPrice()));

                                            } else {
                                                ((TextView) child.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + "0.00");
                                                free++;
                                            }
                                        }
                                    } else {

                                        ((TextView) child.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format("%.2f", (qtyy * Double.parseDouble(modifier.getModifierProductPrice()))));
                                        itemTotalPrice += (qtyy * Double.parseDouble(modifier.getModifierProductPrice()));
                                    }

                                    llModifier.addView(child);
                                }
                            }

                        }
                    }
                }
            } else {
                itemTotalPrice += (itemQty * Double.parseDouble(data.get(position).getMenuProductPrice()));
            }
            if (data.get(position).getProductModifiers() != null) {
                for (ProductModifier productModifier : data.get(position).getProductModifiers()) {
                    if (productModifier.getModifier().size() > 0) {
                        View child1 = ((AppCompatActivity) context).getLayoutInflater().inflate(R.layout.again_modifier_row, null);
//                int qty = (itemQty * productModifier.getQuantity());
                        ((TextView) child1.findViewById(R.id.tv_name)).setTypeface(null, Typeface.BOLD);
                        //((TextView) child1.findViewById(R.id.tv_name)).setText(itemQty + "x" + productModifier.getModifierName());
                        ((TextView) child1.findViewById(R.id.tv_name)).setText(productModifier.getModifierName());
                        ((TextView) child1.findViewById(R.id.tv_price)).setVisibility(View.GONE);
                        llModifier.addView(child1);

                        int maxAllowFree = productModifier.getMaxAllowedQuantity();
                        int free = 0;

                        for (Modifier modifier : productModifier.getModifier()) {

                            View child = ((AppCompatActivity) context).getLayoutInflater().inflate(R.layout.again_modifier_row, null);
                            int qty = (itemQty * Integer.parseInt(modifier.getOriginalQuantity()));
                            //((TextView) child.findViewById(R.id.tv_name)).setPadding(25, 0, 0, 0);
                            ((TextView) child.findViewById(R.id.tv_name)).setPadding(0, 0, 0, 0);
                            ((TextView) child.findViewById(R.id.tv_name)).setText(qty + "x" + modifier.getProductName());

                            if (productModifier.getModifierType().equalsIgnoreCase("free")) {

                                if (free == maxAllowFree) {

                                    ((TextView) child.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format("%.2f", (qty * Double.parseDouble(modifier.getModifierProductPrice()))));
                                    itemTotalPrice += (qty * Double.parseDouble(modifier.getModifierProductPrice()));
                                } else {
                                    int qtyy = Integer.parseInt(modifier.getOriginalQuantity());
                                    if (qtyy > maxAllowFree) {
                                        int nQty = qtyy - maxAllowFree;
                                        free = maxAllowFree;
                                        qtyy = (nQty * itemQty);
                                        ((TextView) child.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format("%.2f", (qtyy * Double.parseDouble(modifier.getModifierProductPrice()))));
                                        itemTotalPrice += (qtyy * Double.parseDouble(modifier.getModifierProductPrice()));
                                    } else {
                                        ((TextView) child.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + "0.00");
                                        free++;
                                    }
                                }

                            } else {

                                ((TextView) child.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format("%.2f", (qty * Double.parseDouble(modifier.getModifierProductPrice()))));
                                itemTotalPrice += (qty * Double.parseDouble(modifier.getModifierProductPrice()));
                            }


                            llModifier.addView(child);
                        }
                    }
                }
            }
            itemPrice.setText(context.getResources().

                    getString(R.string.currency) + String.format("%.2f", itemTotalPrice));
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.item_add:
                    tvQty.setText(String.valueOf((Integer.parseInt(tvQty.getText().toString()) + 1)));
                    data.get(getLayoutPosition()).setOriginalQuantity(Integer.parseInt(tvQty.getText().toString()));

                    db.updateProductQuantity(data.get(getLayoutPosition()).getId(), Integer.parseInt(tvQty.getText().toString()));
                    notifyItemChanged(getLayoutPosition());
                    break;
                case R.id.item_remove:
                    if (Integer.parseInt(tvQty.getText().toString()) > 1) {
                        tvQty.setText(String.valueOf((Integer.parseInt(tvQty.getText().toString()) - 1)));
                        data.get(getLayoutPosition()).setOriginalQuantity(Integer.parseInt(tvQty.getText().toString()));
                        notifyItemChanged(getLayoutPosition());

                        db.updateProductQuantity(data.get(getLayoutPosition()).getId(), Integer.parseInt(tvQty.getText().toString()));
                    } else {
                        db.deleteItem(data.get(getLayoutPosition()).getMenuId(), data.get(getLayoutPosition()).getId());
                        data.remove(getLayoutPosition());
                        notifyItemRemoved(getLayoutPosition());
                    }

                    break;

            }
            if (onItemUpdate != null) {
                onItemUpdate.onItemUpdate();
            }
        }
    }
}
