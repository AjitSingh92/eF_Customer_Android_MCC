package com.easyfoodcustomer.ui_new;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.easyfoodcustomer.R;
import com.easyfoodcustomer.adapters.menu_adapter.RecyclerLayoutManager;
import com.easyfoodcustomer.modelsNew.MealDetailsModel;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuProductSize;
import com.easyfoodcustomer.ui_new.interfaces.MenuProductModifierInterface;

import java.util.ArrayList;
import java.util.List;

public class MenuMealNewListAdapter extends RecyclerView.Adapter<MenuMealNewListAdapter.MyViewHolder> {

    private Context context;
    private View.OnClickListener onClickListener;
    private List<MealDetailsModel.MealConfigBean> menuProductSize;


    public MenuMealNewListAdapter(Context context, View.OnClickListener onClickListener, List<MealDetailsModel.MealConfigBean> menuProductSize) {
        this.context=context;
        this.onClickListener=onClickListener;
        this.menuProductSize=menuProductSize;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_modifier_single, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(menuProductSize.get(position).getProduct_size_name());
        holder.price.setText(context.getResources().getString(R.string.currency) + "" +menuProductSize.get(position).getSelling_price());

        if (menuProductSize.get(position).getNoOfCount()>0)
        {
            holder.itemSelected.setChecked(true);
        }else
        {
            holder.itemSelected.setChecked(false);
        }
        holder.titleLayout.setOnClickListener(onClickListener);
        holder.titleLayout.setTag(position);


    }

    @Override
    public int getItemCount() {
        return menuProductSize.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CheckBox itemSelected;
        private TextView title;
        private TextView price;
        private TextView modifiers;
        private RelativeLayout titleLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            itemSelected = itemView.findViewById(R.id.itemSelected);
            modifiers = itemView.findViewById(R.id.modifiers);
            titleLayout = itemView.findViewById(R.id.titleLayout);

        }
    }


}
