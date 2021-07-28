package com.easyfoodcustomer.ui_new;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.easyfoodcustomer.R;
import com.easyfoodcustomer.modelsNew.MealDetailsModel;
import com.easyfoodcustomer.ui_new.interfaces.MenuProductSizeModifierInterface;

import java.util.List;

public class ProductModifierSingleAdapter extends RecyclerView.Adapter<ProductModifierSingleAdapter.MyViewHolder> {

    private Context context;
    private View.OnClickListener onClickListener;
    private List<MealDetailsModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean.SizeModifierProductsBean> size_modifier_products;
    private int positionParent;
    private MenuProductSizeModifierInterface menuProductSizeModifierInterface;
    public ProductModifierSingleAdapter(Context context, View.OnClickListener onClickListener,
                                        List<MealDetailsModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean.SizeModifierProductsBean> size_modifier_products,
                                        int positionParent, MenuProductSizeModifierInterface menuProductSizeModifierInterface) {

        this.context=context;
        this.onClickListener=onClickListener;
        this.size_modifier_products=size_modifier_products;
        this.positionParent=positionParent;
        this.menuProductSizeModifierInterface=menuProductSizeModifierInterface;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_modifier_single, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.title.setText(size_modifier_products.get(position).getProduct_name());
        holder.price.setText(context.getResources().getString(R.string.currency) + "" +size_modifier_products.get(position).getModifier_product_price());

        if (size_modifier_products.get(position).getNoOfCount()>0)
        {
            holder.itemSelected.setChecked(true);
        }else
        {
            holder.itemSelected.setChecked(false);
        }

        holder.titleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (size_modifier_products.get(position).getNoOfCount()>0)
                {
                    menuProductSizeModifierInterface.updateMeanProductSizeModifier(positionParent,position,false,size_modifier_products.get(position).getNoOfCount(),true);

                }else
                {
                    menuProductSizeModifierInterface.updateMeanProductSizeModifier(positionParent,position,true,size_modifier_products.get(position).getNoOfCount(),true);

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return size_modifier_products.size();
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
