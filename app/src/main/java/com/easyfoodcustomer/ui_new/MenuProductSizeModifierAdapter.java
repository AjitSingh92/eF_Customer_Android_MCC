package com.easyfoodcustomer.ui_new;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.easyfoodcustomer.R;
import com.easyfoodcustomer.modelsNew.MealDetailsModel;
import com.easyfoodcustomer.ui_new.interfaces.MenuProductSizeModifierInterface;

import java.util.List;

public class MenuProductSizeModifierAdapter extends RecyclerView.Adapter<MenuProductSizeModifierAdapter.MyViewHolder> {


    private Context context;
    private View.OnClickListener onClickListener;
    private MealDetailsModel detailsModel;
    private MenuProductSizeModifierInterface menuProductSizeModifierInterface;
    private List<MealDetailsModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean> menu_product_size;


    public MenuProductSizeModifierAdapter(Context context, View.OnClickListener onClickListener, MealDetailsModel detailsModel,
                                          List<MealDetailsModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean> menu_product_size,
                                          MenuProductSizeModifierInterface menuProductSizeModifierInterface) {
        this.context = context;
        this.onClickListener = onClickListener;
        this.detailsModel = detailsModel;
        this.menu_product_size = menu_product_size;
        this.menuProductSizeModifierInterface = menuProductSizeModifierInterface;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_size_modifier, viewGroup, false);
        return new MenuProductSizeModifierAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuProductSizeModifierAdapter.MyViewHolder holder, int position) {

        if (menu_product_size.get(position).getModifier_type().equalsIgnoreCase("free")) {
            holder.title.setText(menu_product_size.get(position).getModifier_name() + " (Choose " + menu_product_size.get(position).getMax_allowed_quantity() + " Free. Additional items will be chargeable.)");
        } else {
            if (menu_product_size.get(position).getFree_qty_limit() > 0)
                holder.title.setText(menu_product_size.get(position).getModifier_name() + " (Choose " + menu_product_size.get(position).getFree_qty_limit() + " Free.)");
            else {
                //  menu_product_size.get(position).getSize_modifier_products()
                boolean isShow = true;
                for (int m = 0; m < menu_product_size.get(position).getSize_modifier_products().size(); m++) {
                    if (menu_product_size.get(position).getSize_modifier_products().get(m).getModifier_product_price() != null && !menu_product_size.get(position).getSize_modifier_products().get(m).getModifier_product_price().isEmpty()
                            && Double.parseDouble(menu_product_size.get(position).getSize_modifier_products().get(m).getModifier_product_price()) > 0) {
                        isShow = true;
                    } else {
                        isShow = false;
                        break;
                    }
                }
                if (isShow) {
                    if (menu_product_size.get(position).getMin_allowed_quantity() > 0) {
                        // holder.title.setText("Select "+menu_product_size.get(position).getModifier_name() + " (All items will be chargeable.)");
                        holder.title.setText("Select " + menu_product_size.get(position).getModifier_name());

                    } else {
                        //holder.title.setText("Optional "+menu_product_size.get(position).getModifier_name() + " (All items will be chargeable.)");
                        holder.title.setText("Optional " + menu_product_size.get(position).getModifier_name());

                    }
                } else {
                    if (menu_product_size.get(position).getMin_allowed_quantity() > 0) {
                        holder.title.setText("Select " + menu_product_size.get(position).getModifier_name());
                    } else {
                        holder.title.setText("Optional " + menu_product_size.get(position).getModifier_name());
                    }
                }
            }
        }

        if (menu_product_size.get(position).getMax_allowed_quantity() > 1) {
            ProductModifierMultiAdapter productModifierMultiAdapter = new ProductModifierMultiAdapter(context, onClickListener, menu_product_size.get(position).getSize_modifier_products(), position, menuProductSizeModifierInterface, menu_product_size.get(position));
            holder.recy_productModifier.setAdapter(productModifierMultiAdapter);
        } else {
            ProductModifierSingleAdapter productModifierSingleAdapter = new ProductModifierSingleAdapter(context, onClickListener, menu_product_size.get(position).getSize_modifier_products(), position, menuProductSizeModifierInterface);
            holder.recy_productModifier.setAdapter(productModifierSingleAdapter);
        }

    }

    @Override
    public int getItemCount() {
        return menu_product_size.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private RecyclerView recy_productModifier;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            recy_productModifier = itemView.findViewById(R.id.recy_productModifier);
            recy_productModifier.setLayoutManager(new LinearLayoutManager(context));

        }
    }
}
