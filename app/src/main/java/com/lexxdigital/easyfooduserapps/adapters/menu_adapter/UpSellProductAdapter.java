package com.lexxdigital.easyfooduserapps.adapters.menu_adapter;

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
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.UpsellProduct;

import java.util.ArrayList;
import java.util.List;

public class UpSellProductAdapter extends RecyclerView.Adapter<UpSellProductAdapter.CategoryViewHolder> {

    private final Context context;
    private List<UpsellProduct> mItem;
    OnUpsellItemListClick onUpsellProductItemClick;
    DatabaseHelper db;

    public interface OnUpsellItemListClick {
        void OnUpsellItemQuantityBtnClick();
    }

    public UpSellProductAdapter(Context context, OnUpsellItemListClick onUpsellProductItemClick) {
        this.context = context;
        this.mItem = new ArrayList<>();
        this.onUpsellProductItemClick = onUpsellProductItemClick;
        db = new DatabaseHelper(context);
    }

    public void clearData() {
        this.mItem.clear();
        notifyDataSetChanged();
    }

    public void addItem(List<UpsellProduct> mItem) {
        this.mItem.addAll(mItem);
        notifyItemChanged(this.mItem.size());
    }

    public void addItem(UpsellProduct mItem) {
        this.mItem.add(mItem);
        notifyItemChanged(this.mItem.size());
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.menu_special_offer_list_item, viewGroup, false));
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
        private final LinearLayout btnRemove, btnAdd;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_title);
            price = itemView.findViewById(R.id.tv_price);

            btnRemove = itemView.findViewById(R.id.btn_remove);
            btnAdd = itemView.findViewById(R.id.btn_add);
            qty = itemView.findViewById(R.id.tv_qty);

            itemView.setOnClickListener(this);
            btnAdd.setOnClickListener(this);
            btnRemove.setOnClickListener(this);
        }

        private void bindData(final int position) {
            int itemQty = Integer.parseInt(mItem.get(position).getQuantity());
            qty.setText(String.valueOf(itemQty));
            Double totalPrice = 0d;

            title.setText(itemQty + "x " + mItem.get(position).getProductName());
            totalPrice += (itemQty * mItem.get(position).getProductPrice());
            price.setText("£" + String.format("%.2f", totalPrice));

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_add:
                    qty.setText(String.valueOf((Integer.parseInt(qty.getText().toString()) + 1)));
                    mItem.get(getLayoutPosition()).setQuantity(qty.getText().toString());
                    notifyItemChanged(getLayoutPosition());

                    db.updateUpsellProductQuantity(mItem.get(getLayoutPosition()).getProductId(), Integer.parseInt(qty.getText().toString()));
                    if (onUpsellProductItemClick != null) {
                        onUpsellProductItemClick.OnUpsellItemQuantityBtnClick();
                    }
                    break;
                case R.id.btn_remove:
                    if (Integer.parseInt(qty.getText().toString()) > 0) {
                        qty.setText(String.valueOf((Integer.parseInt(qty.getText().toString()) - 1)));
                        mItem.get(getLayoutPosition()).setQuantity(qty.getText().toString());
                        notifyItemChanged(getLayoutPosition());

                        if (Integer.parseInt(qty.getText().toString()) == 0) {
                            db.deleteUpsellProductItem(mItem.get(getLayoutPosition()).getProductId());
                            mItem.remove(getLayoutPosition());
                            notifyItemRemoved(getLayoutPosition());
                        } else {
                            db.updateUpsellProductQuantity(mItem.get(getLayoutPosition()).getProductId(), Integer.parseInt(qty.getText().toString()));
                        }
                        if (onUpsellProductItemClick != null) {
                            onUpsellProductItemClick.OnUpsellItemQuantityBtnClick();
                        }
                    }
                    break;
                default:

                    break;
            }
        }
    }
}