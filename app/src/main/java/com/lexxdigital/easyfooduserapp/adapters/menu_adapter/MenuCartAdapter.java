package com.lexxdigital.easyfooduserapp.adapters.menu_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
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

public class MenuCartAdapter extends RecyclerView.Adapter<MenuCartAdapter.CategoryViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private List<MenuProduct> mItem;
    OnMenuCartItemClick onMenuCartItemClick;
    DatabaseHelper db;

    public interface OnMenuCartItemClick {
        void OnQuantityBtnClick();

        void OnUpSellItemRemove();
    }

    public MenuCartAdapter(Context context, OnMenuCartItemClick onMenuCartItemClick) {
        this.context = context;
        this.onMenuCartItemClick = onMenuCartItemClick;
        inflater = LayoutInflater.from(context);
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

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {
        return new CategoryViewHolder(inflater.inflate(R.layout.cart_list_item, viewGroup, false));
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
        private final LinearLayout modifiers, btnRemove, btnAdd;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_title);
            price = itemView.findViewById(R.id.tv_price);
            modifiers = itemView.findViewById(R.id.layout_modifiers);
            btnRemove = itemView.findViewById(R.id.btn_remove);
            btnAdd = itemView.findViewById(R.id.btn_add);
            qty = itemView.findViewById(R.id.tv_qty);

            itemView.setOnClickListener(this);
            btnAdd.setOnClickListener(this);
            btnRemove.setOnClickListener(this);
        }

        private void bindData(final int position) {

            int itemQty = mItem.get(position).getOriginalQuantity();
            qty.setText(String.valueOf(itemQty));
            modifiers.removeAllViews();
            Double totalPrice = 0d;
            /*if (mItem.get(position).getMenuProductSize().size() > 0 && mItem.get(position).getProductModifiers().size() == 0) {
                totalPrice += (itemQty * Double.parseDouble(mItem.get(position).getMenuProductPrice()));
            } else {
                if (mItem.get(position).getMenuProductSize().size() > 0) {
                    totalPrice += (itemQty * Double.parseDouble(mItem.get(position).getMenuProductSize().get(0).getProductSizePrice()));
                }
            }*/
            if (db.getCategoryName(mItem.get(position).getId()).equalsIgnoreCase("MEAL") || db.getCategoryName(mItem.get(position).getId()).equalsIgnoreCase("")) {
                title.setText(itemQty + "x " + mItem.get(position).getProductName());
                totalPrice += mItem.get(position).getOriginalAmount1();
            } else {
                if (mItem.get(position).getMenuProductSize().size() > 0) {
                    title.setText(itemQty + "x " + mItem.get(position).getMenuProductSize().get(0).getProductSizeName() + " " + mItem.get(position).getProductName());

                    totalPrice += (itemQty * Double.parseDouble(mItem.get(position).getMenuProductSize().get(0).getProductSizePrice()));
                } else {
                    title.setText(itemQty + "x " + mItem.get(position).getProductName());
                    totalPrice += (itemQty * Double.parseDouble(mItem.get(position).getMenuProductPrice()));
                }
            }
            price.setText("£" + String.format("%.2f", totalPrice));

            if (mItem.get(position).getMenuProductSize() != null) {
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
                                                    modifiers.addView(viewFree);

                                                }

                                            } else {
                                                ((TextView) view.findViewById(R.id.tv_title)).setText(qty + "x " + sizeModifier.getModifier().get(i).getProductName());

                                                ((TextView) view.findViewById(R.id.tv_price)).setText("free");
                                                free++;
                                            }
                                        }
                                        modifiers.addView(view);
                                    }
                                } else {
                                    for (Modifier modifier : sizeModifier.getModifier()) {
                                        int qty = Integer.parseInt(modifier.getOriginalQuantity());
                                        qty = (qty * itemQty);

                                        View view = LayoutInflater.from(context).inflate(R.layout.item_modifier, null);
                                        ((TextView) view.findViewById(R.id.tv_title)).setText(qty + "x " + modifier.getProductName());
                                        ((TextView) view.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format("%.2f", (qty * Double.parseDouble(modifier.getModifierProductPrice()))));
                                        modifiers.addView(view);
                                    }
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
                        ((TextView) _view.findViewById(R.id.tv_title)).setText(mItem.get(position).getMealProducts().get(p).getProductName() + " " + mItem.get(position).getMealProducts().get(p).getProductSizeName());
                        ((TextView) _view.findViewById(R.id.tv_price)).setVisibility(View.GONE);
                        modifiers.addView(_view);
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
                                                            modifiers.addView(viewFree);

                                                        }

                                                    } else {
//                                            ((TextView) view.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + "0.00");
                                                        ((TextView) view.findViewById(R.id.tv_price)).setText("free");
                                                        free++;
                                                    }
                                                }
                                                modifiers.addView(view);
                                            }
                                        } else {
                                            for (Modifier modifier : sizeModifier.getModifier()) {
                                                int qty = Integer.parseInt(modifier.getOriginalQuantity());
                                                qty = (qty * itemQty);

                                                View view = LayoutInflater.from(context).inflate(R.layout.item_modifier, null);
                                                ((TextView) view.findViewById(R.id.tv_title)).setText(qty + "x " + modifier.getProductName());
                                                ((TextView) view.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format("%.2f", (qty * Double.parseDouble(modifier.getModifierProductPrice()))));
                                                modifiers.addView(view);
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
                                modifiers.addView(view);
                            }
                        } else {
                            for (Modifier modifier : productModifier.getModifier()) {
                                int qty = Integer.parseInt(modifier.getOriginalQuantity());
                                qty = (qty * itemQty);


                                View view = LayoutInflater.from(context).inflate(R.layout.item_modifier, null);
                                ((TextView) view.findViewById(R.id.tv_title)).setText(qty + "x " + modifier.getProductName());
                                ((TextView) view.findViewById(R.id.tv_price)).setText(context.getResources().getString(R.string.currency) + String.format("%.2f", (qty * Double.parseDouble(modifier.getModifierProductPrice()))));
                                modifiers.addView(view);
                            }
                        }
                    }
                }
            }

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_add:
                    qty.setText(String.valueOf((Integer.parseInt(qty.getText().toString()) + 1)));
                    mItem.get(getLayoutPosition()).setOriginalQuantity(Integer.parseInt(qty.getText().toString()));
//                    if (mItem.get(getLayoutPosition()).getMealProducts() != null)

                    int id = db.updateProductQuantity(mItem.get(getLayoutPosition()).getId(), Integer.parseInt(qty.getText().toString()));

                    if (onMenuCartItemClick != null) {
                        onMenuCartItemClick.OnQuantityBtnClick();
                    }
                    if (id != -1) {
                        notifyItemChanged(getLayoutPosition());
                    }
                    break;
                case R.id.btn_remove:
                    if (Integer.parseInt(qty.getText().toString()) > 0) {
                        qty.setText(String.valueOf((Integer.parseInt(qty.getText().toString()) - 1)));
                        mItem.get(getLayoutPosition()).setOriginalQuantity(Integer.parseInt(qty.getText().toString()));
                        int id1 = -1;

                        if (Integer.parseInt(qty.getText().toString()) == 0) {
                            db.deleteItem(mItem.get(getLayoutPosition()).getId(), mItem.get(getLayoutPosition()).getId());
                            if (mItem.get(getLayoutPosition()).getMealProducts() != null) {
                            } else {
                                db.deleteUpsellProductByParentId(mItem.get(getLayoutPosition()).getMenuProductId());
                            }
                            mItem.remove(getLayoutPosition());
                            notifyItemRemoved(getLayoutPosition());
                            if (onMenuCartItemClick != null) {
                                onMenuCartItemClick.OnUpSellItemRemove();
                            }
                        } else {
                            id1 = db.updateProductQuantity(mItem.get(getLayoutPosition()).getId(), Integer.parseInt(qty.getText().toString()));
                        }
                        if (onMenuCartItemClick != null) {
                            onMenuCartItemClick.OnQuantityBtnClick();
                        }
                        if (id1 != -1) {
                            notifyItemChanged(getLayoutPosition());
                        }
                    }

                    break;
                default:

                    break;
            }
        }
    }
}