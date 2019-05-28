package com.lexxdigital.easyfooduserapp.adapters.menu_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.Modifier;

import java.util.ArrayList;
import java.util.List;

public class ModifierProductAdapter extends RecyclerView.Adapter<ModifierProductAdapter.CategoryViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private List<Modifier> mItem;
    ModifierItemSelectListener modifierItemSelectListener;
    int parentPosition;

    public interface ModifierItemSelectListener {
        void onModifierItemSelected(int parentPosition, List<Modifier> mModifiers);
    }

    public ModifierProductAdapter(Context context, int parentPosition, ModifierItemSelectListener modifierItemSelectListener) {
        this.context = context;
        this.parentPosition = parentPosition;
        this.modifierItemSelectListener = modifierItemSelectListener;
        inflater = LayoutInflater.from(context);
        this.mItem = new ArrayList<>();
    }

    public void clearData() {
        this.mItem.clear();
        notifyDataSetChanged();
    }

    public void addItem(List<Modifier> mItem) {
        this.mItem.addAll(mItem);
        notifyItemChanged(this.mItem.size());
    }

    public void addItem(Modifier mItem) {
        this.mItem.add(mItem);
        notifyItemChanged(this.mItem.size());
    }

    public List<Modifier> getSelectedModifier() {
        List<Modifier> itemList = new ArrayList<>();
        for (Modifier item : mItem) {
            if (item.getOriginalQuantity() != null && !item.getOriginalQuantity().equals("")) {
                if (Integer.parseInt(item.getOriginalQuantity()) > 0) {
                    Modifier modifier = new Modifier(item.getProductId(), item.getUnit(), item.getModifierProductPrice(), item.getProductName(), item.getOriginalQuantity(), item.getOriginalQuantity(), item.getAmount(), item.getOriginalAmount1());
                    itemList.add(modifier);
                }
            }
        }

        return itemList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {
        return new CategoryViewHolder(inflater.inflate(R.layout.modifier_product_item_list, viewGroup, false));
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

        private final TextView title, price, item_count;
        private final LinearLayout item_remove, item_add;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_title);
            price = itemView.findViewById(R.id.tv_price);
            item_count = itemView.findViewById(R.id.item_count);
            item_remove = itemView.findViewById(R.id.item_remove);
            item_add = itemView.findViewById(R.id.item_add);

            item_add.setOnClickListener(this);
            item_remove.setOnClickListener(this);
        }

        private void bindData(int position) {
            title.setText(mItem.get(position).getProductName());
            price.setText(context.getResources().getString(R.string.currency) + "" + mItem.get(position).getModifierProductPrice());
            mItem.get(position).setQuantity("0");

            item_count.setText("0");
            if ((Integer.parseInt(item_count.getText().toString()) < 1)) {
                item_remove.setVisibility(View.INVISIBLE);
            }



        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.item_add:
                    item_count.setText(String.valueOf((Integer.parseInt(item_count.getText().toString()) + 1)));
                    mItem.get(getLayoutPosition()).setQuantity(item_count.getText().toString());
                    mItem.get(getLayoutPosition()).setOriginalQuantity(item_count.getText().toString());
                    mItem.get(getLayoutPosition()).setAmount(((Integer.parseInt(item_count.getText().toString())) * (Double.parseDouble(mItem.get(getLayoutPosition()).getModifierProductPrice()))));
                    mItem.get(getLayoutPosition()).setOriginalAmount1(((Integer.parseInt(item_count.getText().toString())) * (Double.parseDouble(mItem.get(getLayoutPosition()).getModifierProductPrice()))));
                    if (modifierItemSelectListener != null) {
                        modifierItemSelectListener.onModifierItemSelected(parentPosition, mItem);
                    }
                    if ((Integer.parseInt(item_count.getText().toString()) > 0)) {
                        item_remove.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.item_remove:
                    if ((Integer.parseInt(item_count.getText().toString()) > 0)) {
                        item_count.setText(String.valueOf((Integer.parseInt(item_count.getText().toString()) - 1)));
                        mItem.get(getLayoutPosition()).setQuantity(item_count.getText().toString());
                        mItem.get(getLayoutPosition()).setOriginalQuantity(item_count.getText().toString());
                        mItem.get(getLayoutPosition()).setAmount(((Integer.parseInt(item_count.getText().toString())) * (Double.parseDouble(mItem.get(getLayoutPosition()).getModifierProductPrice()))));
                        mItem.get(getLayoutPosition()).setOriginalAmount1(((Integer.parseInt(item_count.getText().toString())) * (Double.parseDouble(mItem.get(getLayoutPosition()).getModifierProductPrice()))));
                        if (modifierItemSelectListener != null) {
                            modifierItemSelectListener.onModifierItemSelected(parentPosition, mItem);
                        }
                        if ((Integer.parseInt(item_count.getText().toString()) < 1)) {
                            item_remove.setVisibility(View.INVISIBLE);
                        }
                    }
                    break;
                default:

                    break;
            }


        }


    }


}