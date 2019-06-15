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
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.UpsellProduct;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsUpsellProductAdapter extends RecyclerView.Adapter<OrderDetailsUpsellProductAdapter.MyViewHohlder> {
    Context context;
    private List<UpsellProduct> mItem;


    public OrderDetailsUpsellProductAdapter(Context context) {
        this.context = context;
        this.mItem = new ArrayList<>();
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
        OrderDetailsUpsellProductAdapter.MyViewHohlder myViewHohlder = new OrderDetailsUpsellProductAdapter.MyViewHohlder(view);
        return myViewHohlder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHohlder holder, int position) {

        int itemQty = Integer.parseInt(mItem.get(position).getQuantity());
        Double totalPrice = 0d;

        holder.subProductName.setText(itemQty + "x " + mItem.get(position).getProductName());
        totalPrice += (itemQty * mItem.get(position).getProductPrice());
        holder.subProductPrice.setText("£" + String.format("%.2f", totalPrice));

    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }


}
