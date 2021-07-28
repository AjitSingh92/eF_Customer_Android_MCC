package com.easyfoodcustomer.adapters.menu_adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easyfoodcustomer.R;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.Modifier;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.ProductModifier;

import java.util.ArrayList;
import java.util.List;

public class ProductModifierAdapter extends RecyclerView.Adapter<ProductModifierAdapter.CategoryViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private List<ProductModifier> mItem;
    //    private List<ProductModifier> mFinalItem;
    private static ProductModifierAdapter instance = null;
    private Boolean hideDetail = false;
    List<ModifierProductAdapter> productAdaptersList;
    List<Modifier> selectedModifiers = null;
    int parentPosition;
    OnProductModifierSelected onProductModifierSelected;
    int size = 0;

    public ProductModifierAdapter(Context context, int parentPosition, OnProductModifierSelected onProductModifierSelected) {
        this.context = context;
        this.onProductModifierSelected = onProductModifierSelected;
        inflater = LayoutInflater.from(context);
        this.mItem = new ArrayList<>();
        this.parentPosition = parentPosition;
        productAdaptersList = new ArrayList<>();
        instance = this;
//        mFinalItem = new ArrayList<>();
        selectedModifiers = new ArrayList<>();
    }

    public void setHideDetail(Boolean hideDetail) {
        this.hideDetail = hideDetail;
    }

    public void clearData() {
        this.mItem.clear();
//        this.mFinalItem.clear();
        notifyDataSetChanged();
    }

    public ProductModifier getItem(int position) {
        return mItem.get(position);
    }

    public List<ModifierProductAdapter> getModifierAdapter() {
        return productAdaptersList;
    }


    public List<Modifier> getSelectedList() {
        return this.selectedModifiers;
    }

    public static ProductModifierAdapter getInstance() {
        return instance;
    }

    public List<ProductModifier> getList() {
        return this.mItem;
    }

    public void addItem(List<ProductModifier> mItem) {
        this.mItem.addAll(mItem);
//        this.mFinalItem.addAll(mItem);
        notifyItemChanged(this.mItem.size());
    }

    public void addItem(ProductModifier mItem) {
        this.mItem.add(mItem);
//        this.mFinalItem.add(mItem);
        notifyItemChanged(this.mItem.size());
    }

    public List<ProductModifier> getSelectedProductModifier() {
        List<ProductModifier> data = new ArrayList<>();
        for (int i = 0; i < mItem.size(); i++) {
            /*ProductModifier productModifier = new ProductModifier(mItem.get(i).getModifierName(),
                    mItem.get(i).getModifierType(),
                    mItem.get(i).getModifierId(),
                    mItem.get(i).getMinAllowedQuantity(),
                    mItem.get(i).getMaxAllowedQuantity(),
                    (productAdaptersList.size() > i) ? productAdaptersList.get(i).getSelectedModifier() : new ArrayList<Modifier>(),
                    mItem.get(i).getAmount(),
                    mItem.get(i).getQuantity());*/


            /*---------------------------------------------------------*/

            List<Modifier> modifier = new ArrayList<>();
            if (mItem.get(i).getModifierType().equalsIgnoreCase("free")) {
                int maxAllowFree = mItem.get(i).getMaxAllowedQuantity();
                int free = 0;

                List<Modifier> sizeModifier = productAdaptersList.get(i).getSelectedModifier();
                Modifier modifier1;
                for (int j = 0; j < sizeModifier.size(); j++) {
                    if (free == maxAllowFree) {
                        int qty = Integer.parseInt(sizeModifier.get(j).getOriginalQuantity());

                        modifier1 = new Modifier(sizeModifier.get(j).getProductId(), sizeModifier.get(j).getUnit(), sizeModifier.get(j).getModifierProductPrice(), sizeModifier.get(j).getProductName(), sizeModifier.get(j).getOriginalQuantity(), sizeModifier.get(j).getOriginalQuantity(), (qty * Double.parseDouble(sizeModifier.get(j).getModifierProductPrice())), (qty * Double.parseDouble(sizeModifier.get(j).getModifierProductPrice())));
                        modifier.add(modifier1);

                    } else {
                        int qty = Integer.parseInt(sizeModifier.get(j).getOriginalQuantity());
                        if (qty >= maxAllowFree) {
                            int nQty = qty - maxAllowFree;
                            free = maxAllowFree;
                            modifier1 = new Modifier(sizeModifier.get(j).getProductId(),
                                    sizeModifier.get(j).getUnit(), sizeModifier.get(j).getModifierProductPrice(),
                                    sizeModifier.get(j).getProductName(), sizeModifier.get(j).getOriginalQuantity(),
                                    sizeModifier.get(j).getOriginalQuantity(),
                                    (nQty * Double.parseDouble(sizeModifier.get(j).getModifierProductPrice())),
                                    (nQty * Double.parseDouble(sizeModifier.get(j).getModifierProductPrice())));
                            modifier.add(modifier1);
                        } else {
                            modifier1 = new Modifier(sizeModifier.get(j).getProductId(),
                                    sizeModifier.get(j).getUnit(), sizeModifier.get(j).getModifierProductPrice(),
                                    sizeModifier.get(j).getProductName(), sizeModifier.get(j).getOriginalQuantity(),
                                    sizeModifier.get(j).getOriginalQuantity(),
                                    0d,
                                    0d);
                            modifier.add(modifier1);
                            free++;
                        }
                    }
                }
            } else {
                if (productAdaptersList.size() > i) {
                    for (Modifier mModifier : productAdaptersList.get(i).getSelectedModifier()) {
                        int qty = Integer.parseInt(mModifier.getOriginalQuantity());
                        Modifier modifier1 = new Modifier(mModifier.getProductId(), mModifier.getUnit(), mModifier.getModifierProductPrice(), mModifier.getProductName(), mModifier.getOriginalQuantity(), mModifier.getOriginalQuantity(), (qty * Double.parseDouble(mModifier.getModifierProductPrice())), (qty * Double.parseDouble(mModifier.getModifierProductPrice())));
                        modifier.add(modifier1);
                    }
                }
            }


            ProductModifier productModifier = new ProductModifier(mItem.get(i).getModifierName(),
                    mItem.get(i).getModifierType(),
                    mItem.get(i).getModifierId(),
                    mItem.get(i).getMinAllowedQuantity(),
                    mItem.get(i).getMaxAllowedQuantity(),
                    mItem.get(i).getFreeAllowedQuantity(),
                    modifier,
                    mItem.get(i).getAmount(),
                    mItem.get(i).getQuantity());


            /*---------------------------------------------------------*/


            data.add(productModifier);
        }
        return data;
    }

    public int getMaxSize(int position) {
        return mItem.size();
    }

    public int getMinSize(int position) {
        return mItem.get(position).getMinAllowedQuantity();
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {
        return new CategoryViewHolder(inflater.inflate(R.layout.product_modifier_item_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder pdqListingViewHolder, int position) {
        pdqListingViewHolder.bindData(position);

    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }


    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ModifierProductAdapter.ModifierItemSelectListener {

        private final TextView title;
        private final RecyclerView modifierList;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_title);
//            note = itemView.findViewById(R.id.tv_note);
            modifierList = itemView.findViewById(R.id.list_modifierList);

            itemView.setOnClickListener(this);
        }

        private void bindData(int position) {
            // title.setText(mItem.get(position).getModifierName());

            if (mItem.get(position).getModifierType().equalsIgnoreCase("free")) {
                //note.setText("Choose Any " + mItem.get(position).getMaxAllowedQuantity() + " - It's Free. More Than " + mItem.get(position).getMaxAllowedQuantity() + " Will Be Charged");
                title.setText(mItem.get(position).getModifierName() + " (Choose " + String.valueOf(mItem.get(position).getMaxAllowedQuantity()) + " Free. Additional items will be chargeable.)");
            } else {
                //note.setText("Cost Of Each Will Be Added In The Price");
                //   title.setText(mItem.get(position).getModifierName() + " (All Paid)");

                title.setText(String.valueOf(mItem.get(position).getFreeAllowedQuantity()));

            }
            RecyclerLayoutManager layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
            layoutManager.setScrollEnabled(false);
            modifierList.setLayoutManager(layoutManager);
            ModifierProductAdapter modifierProductAdapter = new ModifierProductAdapter(context, position, this, mItem.get(position).getMaxAllowedQuantity(), size);
            modifierList.setAdapter(modifierProductAdapter);
            productAdaptersList.add(modifierProductAdapter);

            modifierProductAdapter.addItem(mItem.get(position).getModifier());
        }

        @Override
        public void onClick(View v) {

        }


        @Override
        public void onModifierItemSelected(int parentPosition, List<Modifier> mModifiers, boolean isSelected) {
            /*if (mFinalItem.size() > 0 && mFinalItem.size()!=parentPosition)
                mFinalItem.remove(parentPosition);

            mFinalItem.add(parentPosition, mItem.get(parentPosition));*/
//            mItem.get(parentPosition).setModifier(productAdaptersList.get(parentPosition).getSelectedModifier());

            selectedModifiers.addAll(mModifiers);
            if (isSelected) {
                size = size + 1;

            } else {
                size = size - 1;
            }
            Log.e("ANAND >>>>", selectedModifiers.toString());
            if (onProductModifierSelected != null) {
                onProductModifierSelected.OnSizeSelected();
            }
        }


    }
}
