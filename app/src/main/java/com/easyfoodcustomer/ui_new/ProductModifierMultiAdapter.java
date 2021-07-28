package com.easyfoodcustomer.ui_new;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.easyfoodcustomer.R;
import com.easyfoodcustomer.adapters.menu_adapter.MealProductAdapter;
import com.easyfoodcustomer.adapters.menu_adapter.RecyclerLayoutManager;
import com.easyfoodcustomer.modelsNew.MealDetailsModel;
import com.easyfoodcustomer.ui_new.interfaces.MenuProductSizeModifierInterface;

import java.util.List;

public class ProductModifierMultiAdapter extends RecyclerView.Adapter<ProductModifierMultiAdapter.MyViewHolder> {

    private Context context;
    private View.OnClickListener onClickListener;
    private List<MealDetailsModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean.SizeModifierProductsBean> size_modifier_products;
    private int positionParent;
    private MenuProductSizeModifierInterface menuProductSizeModifierInterface;
    private MealDetailsModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean sizeModifiersBean;

    public ProductModifierMultiAdapter(Context context, View.OnClickListener onClickListener,
                                       List<MealDetailsModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean.SizeModifierProductsBean> size_modifier_products,
                                       int positionParent, MenuProductSizeModifierInterface menuProductSizeModifierInterface, MealDetailsModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean sizeModifiersBean) {

        this.context = context;
        this.onClickListener = onClickListener;
        this.size_modifier_products = size_modifier_products;
        this.positionParent = positionParent;
        this.menuProductSizeModifierInterface = menuProductSizeModifierInterface;
        this.sizeModifiersBean = sizeModifiersBean;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_modifier_multi, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.title.setText(size_modifier_products.get(position).getProduct_name());
        holder.price.setText(context.getResources().getString(R.string.currency) + "" + size_modifier_products.get(position).getModifier_product_price());

        holder.item_count.setText(String.valueOf(size_modifier_products.get(position).getNoOfCount()));

        holder.item_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (size_modifier_products.get(position).getNoOfCount()>0)
                menuProductSizeModifierInterface.updateMeanProductSizeModifier(positionParent,position,false,size_modifier_products.get(position).getNoOfCount(),false);
            }
        });

        holder.item_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getNumberOfCurrentCount(size_modifier_products)<sizeModifiersBean.getMax_allowed_quantity())
                {
                    menuProductSizeModifierInterface.updateMeanProductSizeModifier(positionParent,position,true,size_modifier_products.get(position).getNoOfCount(),false);

                }else
                {
                    Toast.makeText(context,"You reached out to your maximum Limit",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private int getNumberOfCurrentCount(List<MealDetailsModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean.SizeModifierProductsBean> size_modifier_products) {
        int maxAllowedSize=0;
        for (int i=0;i<size_modifier_products.size();i++)
        {
            maxAllowedSize=maxAllowedSize+size_modifier_products.get(i).getNoOfCount();
        }
        return maxAllowedSize;
    }

    @Override
    public int getItemCount() {
        return size_modifier_products.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView price;
        private TextView item_count;
        private LinearLayout item_remove;
        private LinearLayout item_add;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            item_count = itemView.findViewById(R.id.item_count);
            item_remove = itemView.findViewById(R.id.item_remove);
            item_add = itemView.findViewById(R.id.item_add);

        }
    }


}
