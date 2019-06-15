package com.lexxdigital.easyfooduserapps.adapters.menu_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MealProduct;

import java.util.ArrayList;
import java.util.List;

public class MealProductModifierAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private final LayoutInflater inflater;
    List<MealProduct> mItem;
    int lastSelectedPosition = -1;
    CheckBox lastSelectedItem;
    int selectSize;

    public MealProductModifierAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        mItem = new ArrayList<>();
    }

    public void clearData() {
        this.mItem.clear();
        notifyDataSetChanged();
    }

    public void addItem(List<MealProduct> mItem) {
        this.mItem.addAll(mItem);
        notifyItemChanged(this.mItem.size());
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new MealProductModifierAdapter.MealProductViewHolder(inflater.inflate(R.layout.meal_product_modifier_row, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        MealProductViewHolder categoryViewHolder = (MealProductViewHolder) viewHolder;
        categoryViewHolder.bindData(position);

    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }

    class MealProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvItemTitle;
        private CheckBox itemSelected;

        public MealProductViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvItemTitle = itemView.findViewById(R.id.tv_title);
            itemSelected = itemView.findViewById(R.id.cb_itemSelected);

        }

        private void bindData(int position) {


        }

        @Override
        public void onClick(View v) {


        }
    }
}
