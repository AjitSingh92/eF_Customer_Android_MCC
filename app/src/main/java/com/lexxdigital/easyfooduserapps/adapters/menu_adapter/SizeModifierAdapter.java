package com.lexxdigital.easyfooduserapps.adapters.menu_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.Modifier;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.SizeModifier;

import java.util.ArrayList;
import java.util.List;

public class SizeModifierAdapter extends RecyclerView.Adapter<SizeModifierAdapter.CategoryViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private List<SizeModifier> mItem;
    SizeModifierSelectListener sizeModifierSelectListener;
    List<ModifierProductAdapter> productAdaptersList;

    int showNextCount = 1;

    public interface SizeModifierSelectListener {
        void onSizeSelected(List<SizeModifier> mItemList);
    }

    public SizeModifierAdapter(Context context, SizeModifierSelectListener sizeModifierSelectListener) {
        this.context = context;
        this.sizeModifierSelectListener = sizeModifierSelectListener;
        inflater = LayoutInflater.from(context);
        this.mItem = new ArrayList<>();
        productAdaptersList = new ArrayList<>();
    }

    public void clearData() {
        this.mItem.clear();
        notifyDataSetChanged();
    }

    public List<ModifierProductAdapter> getModifierAdapter() {
        return productAdaptersList;
    }

    public List<SizeModifier> getSelectedList() {
        return this.mItem;
    }

    public void addItem(List<SizeModifier> mItem) {
        this.mItem.addAll(mItem);
        notifyItemChanged(this.mItem.size());
    }

    public void addItem(SizeModifier mItem) {
        this.mItem.add(mItem);
        notifyItemChanged(this.mItem.size());
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
        return (mItem.size()==0)?mItem.size():showNextCount;//mItem.size();
    }


    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ModifierProductAdapter.ModifierItemSelectListener {

        private final TextView title;
        private final RecyclerView modifierList;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_title);

            // note = itemView.findViewById(R.id.tv_note);
            modifierList = itemView.findViewById(R.id.list_modifierList);

            itemView.setOnClickListener(this);
        }

        private void bindData(int position) {
            // title.setText(mItem.get(position).getModifierName());

            if (mItem.get(position).getModifierType().equalsIgnoreCase("free")) {
                title.setText(mItem.get(position).getModifierName() + " (Choose " + mItem.get(position).getMaxAllowedQuantity() + " Free. Additional items will be chargeable.)");
            } else {
                //   title.setText(mItem.get(position).getModifierName() + " (All Paid)");
                title.setText(mItem.get(position).getModifierName());
            }

            RecyclerLayoutManager layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
            layoutManager.setScrollEnabled(false);
            modifierList.setLayoutManager(layoutManager);
            ModifierProductAdapter modifierProductAdapter = new ModifierProductAdapter(context, position, this);
            if (mItem.get(position).getMaxAllowedQuantity() == 1) {
                modifierProductAdapter.setViewType(R.layout.meal_product_row);
            }
            modifierList.setAdapter(modifierProductAdapter);
            productAdaptersList.add(modifierProductAdapter);
            modifierProductAdapter.addItem(mItem.get(position).getModifier());

        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public void onModifierItemSelected(int parentPosition, List<Modifier> mModifiers) {
            /*if (mFinalItem.size() > 0 && mFinalItem.size()!=parentPosition)
                mFinalItem.remove(parentPosition);

            mFinalItem.add(parentPosition, mItem.get(parentPosition));*/

            if (sizeModifierSelectListener != null) {
                sizeModifierSelectListener.onSizeSelected(mItem);
            }
            if (showNextCount < mItem.size()) {
                showNextCount++;
                notifyDataSetChanged();
            }

        }
    }
}