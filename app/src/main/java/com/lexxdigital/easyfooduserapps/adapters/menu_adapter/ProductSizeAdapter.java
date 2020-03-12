package com.lexxdigital.easyfooduserapps.adapters.menu_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuProductSize;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.Modifier;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.SizeModifier;

import java.util.ArrayList;
import java.util.List;

public class ProductSizeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private List<MenuProductSize> mItem;
    CheckBox lastSelectedItem;
    int lastSelectedPosition = -1;
    SizeModifierAdapter sizeModifierAdapter;
    //    List<ModifierProductAdapter> productAdaptersList;
    OnProductModifierSelected onProductModifierSelected;
    private Boolean checkedFirstItem = false;
    private Boolean showAll = false;

    public void setCheckedFirstItem(Boolean checked) {
        this.checkedFirstItem = checked;
    }

    public ProductSizeAdapter(Context context, OnProductModifierSelected onProductModifierSelected) {
        this.context = context;
        this.onProductModifierSelected = onProductModifierSelected;
        inflater = LayoutInflater.from(context);
        this.mItem = new ArrayList<>();
    }

    public List<MenuProductSize> getSelectedItem(boolean isSelect) {
        List<MenuProductSize> menuProductSizeList = new ArrayList<>();
        if (lastSelectedPosition == -1) {
            return menuProductSizeList;
        }
        for (int i = 0; i < mItem.size(); i++) {
            if (mItem.get(i).getSelected() != null && mItem.get(i).getSelected()) {
                MenuProductSize menuProductSize = new MenuProductSize();
                menuProductSize.setSelected(mItem.get(i).getSelected());
                menuProductSize.setAmount(mItem.get(i).getAmount());
                menuProductSize.setProductSizeId(mItem.get(i).getProductSizeId());
                menuProductSize.setProductSizeName(mItem.get(i).getProductSizeName());
                menuProductSize.setProductSizePrice(mItem.get(i).getProductSizePrice());
                menuProductSize.setQuantity(1);
                menuProductSize.setOriginalQuantity("1");
                menuProductSize.setOriginalAmount(Double.parseDouble(mItem.get(i).getProductSizePrice()));
                menuProductSize.setOriginalAmount1(Double.parseDouble(mItem.get(i).getProductSizePrice()));

                if (sizeModifierAdapter != null && !isSelect) {
                    List<SizeModifier> sizeModifiers = new ArrayList<>();
                    for (int j = 0; j < sizeModifierAdapter.getSelectedList().size(); j++) {

                        List<Modifier> modifiers = new ArrayList<>();
                        if (sizeModifierAdapter.getSelectedList().get(j).getModifierType().equalsIgnoreCase("free")) {
                            int maxAllowFree = sizeModifierAdapter.getSelectedList().get(j).getMaxAllowedQuantity();
                            int free = 0;
                            for (Modifier modifier : sizeModifierAdapter.getSelectedList().get(j).getModifier()) {

                                if (modifier.getOriginalQuantity() != null && !modifier.getOriginalQuantity().equals("0")) {
                                    /*Modifier modifier1 = new Modifier(modifier.getProductId(), modifier.getUnit(), modifier.getModifierProductPrice(), modifier.getProductName(), modifier.getQuantity(), modifier.getOriginalQuantity(), modifier.getAmount(), modifier.getOriginalAmount1());
                                    modifiers.add(modifier1);*/


                                    /*-------------------------------------------------*/
                                    Modifier modifier1;
                                    if (free == maxAllowFree) {
                                        int qty = Integer.parseInt(modifier.getOriginalQuantity());

                                        modifier1 = new Modifier(modifier.getProductId(), modifier.getUnit(), modifier.getModifierProductPrice(), modifier.getProductName(), modifier.getOriginalQuantity(), modifier.getOriginalQuantity(), (qty * Double.parseDouble(modifier.getModifierProductPrice())), (qty * Double.parseDouble(modifier.getModifierProductPrice())));
                                        modifiers.add(modifier1);

                                    } else {
                                        int qty = Integer.parseInt(modifier.getOriginalQuantity());
                                        if (qty >= maxAllowFree) {
                                            int nQty = qty - maxAllowFree;
                                            free = maxAllowFree;
                                            modifier1 = new Modifier(modifier.getProductId(),
                                                    modifier.getUnit(), modifier.getModifierProductPrice(),
                                                    modifier.getProductName(), modifier.getOriginalQuantity(),
                                                    modifier.getOriginalQuantity(),
                                                    (nQty * Double.parseDouble(modifier.getModifierProductPrice())),
                                                    (nQty * Double.parseDouble(modifier.getModifierProductPrice())));
                                            modifiers.add(modifier1);
                                        } else {
                                            modifier1 = new Modifier(modifier.getProductId(),
                                                    modifier.getUnit(), modifier.getModifierProductPrice(),
                                                    modifier.getProductName(), modifier.getOriginalQuantity(),
                                                    modifier.getOriginalQuantity(),
                                                    0d,
                                                    0d);
                                            modifiers.add(modifier1);
                                            free++;
                                        }
                                    }

                                    /*-------------------------------------------------*/


                                }
                            }
                        } else {
                            for (Modifier modifier : sizeModifierAdapter.getSelectedList().get(j).getModifier()) {
                                if (modifier.getOriginalQuantity() != null && !modifier.getOriginalQuantity().equals("0")) {
                                    int qty = Integer.parseInt(modifier.getOriginalQuantity());

                                    Modifier modifier1 = new Modifier(modifier.getProductId(), modifier.getUnit(), modifier.getModifierProductPrice(), modifier.getProductName(), modifier.getOriginalQuantity(), modifier.getOriginalQuantity(), (qty * Double.parseDouble(modifier.getModifierProductPrice())), (qty * Double.parseDouble(modifier.getModifierProductPrice())));
                                    modifiers.add(modifier1);
                                }
                            }
                        }
                        SizeModifier sizeModifier = new SizeModifier(sizeModifierAdapter.getSelectedList().get(j).getModifierName(),
                                sizeModifierAdapter.getSelectedList().get(j).getModifierType(), sizeModifierAdapter.getSelectedList().get(j).getModifierId(), sizeModifierAdapter.getSelectedList().get(j).getMinAllowedQuantity(), sizeModifierAdapter.getSelectedList().get(j).getMaxAllowedQuantity(), modifiers);

                        sizeModifiers.add(sizeModifier);
                    }
                    menuProductSize.setSizeModifiers(sizeModifiers);
                }
                menuProductSizeList.add(menuProductSize);
            }
        }

/*
        for (MenuProductSize item : mItem) {
            if (item.getSelected() != null && item.getSelected()) {
                return item;
            }
        }*/
        return menuProductSizeList;
    }

    public void clearData() {
        this.mItem.clear();
        notifyDataSetChanged();
    }

    public void addItem(List<MenuProductSize> mItem) {
        this.mItem.addAll(mItem);
        notifyItemChanged(this.mItem.size());
    }

    public void addItem(MenuProductSize mItem) {
        this.mItem.add(mItem);
        notifyItemChanged(this.mItem.size());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                CategoryViewHolder categoryViewHolder = (CategoryViewHolder) holder;
                categoryViewHolder.bindData(position);
                break;
            case 1:
                SizeModifierViewHolder sizeModifierViewHolder = (SizeModifierViewHolder) holder;
                sizeModifierViewHolder.bindData(position);
                break;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {
        switch (viewtype) {
            case 0:
                return new CategoryViewHolder(inflater.inflate(R.layout.product_size_item_list, viewGroup, false));
            case 1:
                return new SizeModifierViewHolder(inflater.inflate(R.layout.product_size_item_list, viewGroup, false));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() == (position + 1)) {
            return 1;
        } else {
            return 0;

        }
    }

    @Override
    public int getItemCount() {
        return (mItem.size() + 1);
    }


    class SizeModifierViewHolder extends RecyclerView.ViewHolder implements SizeModifierAdapter.SizeModifierSelectListener {
        private final RecyclerView sizeModifierList;
        private final LinearLayout titleLayout;

        public SizeModifierViewHolder(@NonNull View itemView) {
            super(itemView);
            titleLayout = itemView.findViewById(R.id.layout_titleLayout);
            sizeModifierList = itemView.findViewById(R.id.list_sizeModifierList);


        }

        private void bindData(int position) {
            titleLayout.setVisibility(View.GONE);
            RecyclerLayoutManager layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
            layoutManager.setScrollEnabled(false);
            sizeModifierList.setLayoutManager(layoutManager);
            sizeModifierAdapter = new SizeModifierAdapter(context, this);

            sizeModifierList.setAdapter(sizeModifierAdapter);
//            sizeModifierAdapter.clearData();
            if (checkedFirstItem) {
                sizeModifierAdapter.addItem(mItem.get(lastSelectedPosition).getSizeModifiers());

            } else {
                if (lastSelectedPosition != -1) {
                    Log.e("SizeModifiers", lastSelectedPosition + " >> " + mItem.get(lastSelectedPosition).getSizeModifiers());
                    sizeModifierAdapter.addItem(mItem.get(lastSelectedPosition).getSizeModifiers());
                }
            }

        }

        @Override
        public void onSizeSelected(List<SizeModifier> mItemList) {
            mItem.get(lastSelectedPosition).setSizeModifiers(mItemList);
            if (onProductModifierSelected != null) {
                onProductModifierSelected.OnSizeSelected();
            }
        }
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ModifierProductAdapter.ModifierItemSelectListener {

        private final TextView title, price;
        private final LinearLayout titleLayout;
        private final CheckBox itemSelected;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            titleLayout = itemView.findViewById(R.id.layout_titleLayout);

            title = itemView.findViewById(R.id.tv_title);
            price = itemView.findViewById(R.id.tv_price);
            itemSelected = itemView.findViewById(R.id.cb_itemSelected);
            titleLayout.setVisibility(View.VISIBLE);
            itemView.setOnClickListener(this);
        }

        private void bindData(int position) {

            title.setText(mItem.get(position).getProductSizeName());
            price.setText(context.getResources().getString(R.string.currency) + "" + mItem.get(position).getProductSizePrice());
            if (mItem.get(position).getSelected() != null) {
                mItem.get(position).setSelected(false);
            }
            if (checkedFirstItem && position == 0) {
                titleLayout.setVisibility(View.GONE);
                mItem.get(0).setSelected(true);
                itemSelected.setChecked(true);
                lastSelectedPosition = 0;
            } else {
                if (lastSelectedPosition != -1 && lastSelectedPosition == position) {
                    mItem.get(lastSelectedPosition).setSelected(true);
                    itemSelected.setChecked(true);
                } else {
                    mItem.get(getLayoutPosition()).setSelected(false);
                    itemSelected.setChecked(false);
                }
            }
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (lastSelectedItem != null) {
                lastSelectedItem.setChecked(false);
                itemSelected.setChecked(true);
                mItem.get(lastSelectedPosition).setSelected(false);


                mItem.get(lastSelectedPosition).setAmount(mItem.get(lastSelectedPosition).getProductSizePrice());
                mItem.get(lastSelectedPosition).setOriginalAmount(Double.parseDouble(mItem.get(lastSelectedPosition).getProductSizePrice()));
                mItem.get(lastSelectedPosition).setOriginalAmount1(Double.parseDouble(mItem.get(lastSelectedPosition).getProductSizePrice()));
                mItem.get(lastSelectedPosition).setOriginalQuantity("0");
                mItem.get(lastSelectedPosition).setQuantity(0);


                lastSelectedItem = itemSelected;
                lastSelectedPosition = getLayoutPosition();
                mItem.get(getLayoutPosition()).setSelected(true);
//                sizeModifierAdapter =null;
                notifyDataSetChanged();
            } else {
                itemSelected.setChecked(true);
                lastSelectedItem = itemSelected;
                lastSelectedPosition = getLayoutPosition();
                mItem.get(getLayoutPosition()).setSelected(true);

                mItem.get(lastSelectedPosition).setAmount(mItem.get(lastSelectedPosition).getProductSizePrice());
                mItem.get(lastSelectedPosition).setOriginalAmount(Double.parseDouble(mItem.get(lastSelectedPosition).getProductSizePrice()));
                mItem.get(lastSelectedPosition).setOriginalAmount1(Double.parseDouble(mItem.get(lastSelectedPosition).getProductSizePrice()));
                mItem.get(lastSelectedPosition).setOriginalQuantity("1");
                mItem.get(lastSelectedPosition).setQuantity(1);

//                sizeModifierAdapter =null;
                notifyDataSetChanged();
            }

            if (onProductModifierSelected != null) {
                onProductModifierSelected.OnSizeModifierSelected(true);
            }
        }

        @Override
        public void onModifierItemSelected(int parentPosition, List<Modifier> mModifiers) {
            /*if (mFinalItem.size() > 0 && mFinalItem.size()!=parentPosition)
                mFinalItem.remove(parentPosition);

            mFinalItem.add(parentPosition, mItem.get(parentPosition));
            mFinalItem.get(parentPosition).setModifier(mModifiers);*/
        }
    }
}