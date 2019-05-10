package com.lexxdigital.easyfooduserapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.menu_category.MenuProduct;

import java.util.ArrayList;

public class AdapterSizeModifier extends RecyclerView.Adapter<AdapterSizeModifier.MyViewHolder> implements ChooseAdapter.PositionInterface {

    // private ArrayList<Arraylist> dataSet;
    ChooseAdapter mChooseAdapter;
    ArrayList<String> check2 ;
    ChooseAdapter.PositionInterface mPositionInterface;
    Context mContext;
    MenuProduct mProduct;
    int possition;
    double totalPrice;
    ArrayList<com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.SizeModifierProduct> showCartSizeModifierPproduct ;
            ;
    ArrayList<com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.SizeModifier> showCartSizeModifier;
    TextView popupTotalPrice;
    @Override
    public void onClickPos(int pos, int lpos) {
     //   Toast.makeText(mContext, "Clicked > "+pos+"//"+lpos, Toast.LENGTH_SHORT).show();
     //   popupTotalPrice.setText("+ £"+mProduct.getMenuProductSize().get(possition).getSizeModifiers().get(lpos).getSizeModifierProducts().get(pos).getModifierProductPrice());
        mChooseAdapter.notifyDataSetChanged();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView modifireName;
        RecyclerView listModifiers;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.listModifiers = itemView.findViewById(R.id.modifiers_list);
            this.modifireName = (TextView) itemView.findViewById(R.id.name_tv);
        }
    }

    public AdapterSizeModifier(Context mContext, MenuProduct product, int pos, TextView price, ArrayList<com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.SizeModifier> cartSizeModifier,double tPrice) {

        this.mContext= mContext;
        this.mProduct = product;
        this.possition = pos;
        this.popupTotalPrice = price;
        this.showCartSizeModifier = cartSizeModifier;
        this.totalPrice = tPrice;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_size_modifier, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
      //  holder.cardNoOne.setText(response.getData().getCards().get(listPosition).getCardNo().substring(0,4));
        mPositionInterface=this;
        check2 = new ArrayList<>();
        check2.clear();
        for(int i=0;i<mProduct.getMenuProductSize().get(possition).getSizeModifiers().get(listPosition).getSizeModifierProducts().size();i++){
            check2.add("0");
        }

       // showCartSizeModifier.clear();

       // showCartSizeModifier = new ArrayList<>();
        showCartSizeModifierPproduct = new ArrayList<>();
        showCartSizeModifier.add(new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.SizeModifier(mProduct.getMenuProductSize().get(possition).getSizeModifiers().get(listPosition).getModifierName(),mProduct.getMenuProductSize().get(possition).getSizeModifiers().get(listPosition).getModifierType(),mProduct.getMenuProductSize().get(possition).getSizeModifiers().get(listPosition).getModifierId(),mProduct.getMenuProductSize().get(possition).getSizeModifiers().get(listPosition).getMinAllowedQuantity(),mProduct.getMenuProductSize().get(possition).getSizeModifiers().get(listPosition).getMaxAllowedQuantity(),showCartSizeModifierPproduct));

        holder.modifireName.setText(mProduct.getMenuProductSize().get(possition).getSizeModifiers().get(listPosition).getModifierName());
//
        mChooseAdapter = new ChooseAdapter(mContext, mPositionInterface,check2,mProduct,possition,listPosition,popupTotalPrice,showCartSizeModifierPproduct,showCartSizeModifier,totalPrice);
        @SuppressLint("WrongConstant")
        LinearLayoutManager horizontalLayoutManagaer2
                = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        holder.listModifiers.setLayoutManager(horizontalLayoutManagaer2);
        holder.listModifiers.setAdapter(mChooseAdapter);

    }

    @Override
    public int getItemCount() {
        return mProduct.getMenuProductSize().get(possition).getSizeModifiers().size();
    }}