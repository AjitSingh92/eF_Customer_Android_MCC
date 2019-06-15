package com.lexxdigital.easyfooduserapps.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.cart_model.final_cart.FinalNewCartDetails;
import com.lexxdigital.easyfooduserapps.restaurant_details.CartDetailsModel;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.menu_category.FinalMenuCartDetails;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.menu_category.MenuProduct;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.response.RestaurantDetailsResponse;
import com.lexxdigital.easyfooduserapps.utility.GlobalValues;
import com.lexxdigital.easyfooduserapps.utility.SharedPreferencesClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewAdapterMenuRecyclerview extends BaseExpandableListAdapter implements WhatLikeAdapter.PositionInterface{

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<Object>> _listDataChild;
    private RestaurantDetailsResponse respose;
    private TextView footerDetails;
    private int totalCount=0;
    private Double prePrice=0.0;
    private Double totalPrice=0.00,totalPrice2=0.00;
    SharedPreferencesClass sharedPreferencesClass;
    private int indexList=0, addMul=1,removeMul=1;
    ArrayList<String> array = new ArrayList<>();
    private List<CartDetailsModel> listAddCartDetails = new ArrayList<>();
    TextView popupTotalPrice;
    TextView emptyBasket;
    private List<CartDetailsModel> listRemoveCartDetails = new ArrayList<>();
    FinalMenuCartDetails mFCart;
    private List<Integer> arrayListIndex = new ArrayList<>();
    private List<String> arrayListIndextwo = new ArrayList<>();
    private List<String> arrayListIndexthree = new ArrayList<>();
    LinearLayout llBotom;
    GlobalValues val;
    Activity mActivity;
    TextView footerCount;
    AdapterSizeModifier sizeModifierAdapter;
    List<Object> finalMenuCartDetails = new ArrayList<Object>();
    ArrayList<String> check = new ArrayList<>();
    ArrayList<String> check2 = new ArrayList<>();

    WhatLikeAdapter.PositionInterface mPositionInterface2;
    TextView lblSize;
    WhatLikeAdapter mWhatLikeAdapter;
    AdapterProductModifier productModifierAdapter;
    RecyclerView productModifier;

    FinalNewCartDetails finalNewCartDetails = new FinalNewCartDetails();
    ArrayList<com.lexxdigital.easyfooduserapps.cart_model.final_cart.ProductModifier> showCartProductSize = new ArrayList<>();
    ArrayList<com.lexxdigital.easyfooduserapps.cart_model.final_cart.SizeModifier> showCartSizeModifier = new ArrayList<>();
    ArrayList<com.lexxdigital.easyfooduserapps.cart_model.final_cart.ProductModifier> showCartProductModifier = new ArrayList<>();


    public NewAdapterMenuRecyclerview() {
    }

    public NewAdapterMenuRecyclerview(Activity activity, Context context, List<String> listDataHeader,
                                        HashMap<String, List<Object>> listChildData, RestaurantDetailsResponse res, TextView footer, LinearLayout llbotom, FinalMenuCartDetails fCart, List<Object> menuCartDetails, TextView fCount, TextView txtEmptyBasket) {

        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.respose = res;
        this.footerDetails = footer;
        this.llBotom = llbotom;
        this.mFCart = fCart;
        this.mActivity = activity;
        this.finalMenuCartDetails = menuCartDetails;
        this.footerCount = fCount;
        this.emptyBasket = txtEmptyBasket;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);
        val = (GlobalValues) _context;
        if(convertView == null){
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.offer_item_list, null);
        }

        TextView title = (TextView) convertView.findViewById(R.id.txt_menu_title);
        TextView price = (TextView) convertView.findViewById(R.id.txt_price);
        TextView details = (TextView) convertView.findViewById(R.id.txt_items_detail);
        RelativeLayout lyItemClick = convertView.findViewById(R.id.click_items);
        LinearLayout itemRemove = (LinearLayout) convertView.findViewById(R.id.item_remove);
        LinearLayout itemAdd = (LinearLayout) convertView.findViewById(R.id.item_add);
        final TextView itemCount = (TextView) convertView.findViewById(R.id.item_count);
        final LinearLayout countClick = (LinearLayout) convertView.findViewById(R.id.clickCount);
        details.setVisibility(View.GONE);
        final MenuProduct mProduct = (MenuProduct) _listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosition);
        lyItemClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("LEN1",""+mProduct.getMenuProductSize().size());
                Log.e("LEN2",""+mProduct.getProductModifiers().size());
                //       Log.e("LEN3",""+);

                //   showCartProductSize.clear();
//                    showCartMenuProduct.clear();
                //  showCartProductModifier.clear();
                //   showCartSizeModifier.clear();

                if(mProduct.getMenuProductSize().size() <= 0 && mProduct.getProductModifiers().size() <= 0){
                    itemCount.setVisibility(View.VISIBLE);
                    itemCount.setText("1");
                    footerDetails.setText(String.format("%.2f",Double.parseDouble(footerDetails.getText().toString())+Double.parseDouble(mProduct.getMenuProductPrice())));
                    footerCount.setText(String.valueOf(Integer.parseInt(footerCount.getText().toString())+1));
                    llBotom.setBackgroundColor(_context.getResources().getColor(R.color.orange));
                    itemCount.setVisibility(View.VISIBLE);
                    emptyBasket.setText("View basket");

                    arrayListIndextwo.add(mProduct.getMenuProductId());
                    Log.e("MANOJ","KUMAR");

                }else {
                    check.clear();
                    if(finalNewCartDetails.getData().size() <= 0){
                        alertDialogItems(mProduct, childPosition, groupPosition, countClick,itemCount);
                    }else{
                        if(!getIsProductAdded(mProduct.getMenuProductId())){
                            alertDialogItems(mProduct, childPosition, groupPosition, countClick,itemCount);
                        }else {
                            alertDialogRepeat(groupPosition, itemCount, mProduct, childPosition, countClick);
                        }
                    }

                }

            }
        });


        return convertView;
    }


    public boolean getIsProductAdded(String id){
        for(int i=0; i<finalNewCartDetails.getData().size(); i++){
            if(finalNewCartDetails.getData().get(0).getMenuCategory().getMenuProducts().getMenuProductId().equalsIgnoreCase(id)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
        // return 5;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
        // return 5;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item, null);
        }

        RelativeLayout RlClick = (RelativeLayout) convertView
                .findViewById(R.id.RlClick);
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.headerNameTv);
//        // lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        final ImageView dropImg = (ImageView) convertView.findViewById(R.id.dropdownImg);


        // Toast.makeText(_context,"GruopPosition ="+groupPosition,Toast.LENGTH_LONG).show();



        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public void alertDialogItems(final MenuProduct product, final int gPos, final int parentPos, final LinearLayout itemCount, final TextView itemTotalCount) {
        LayoutInflater factory = LayoutInflater.from(mActivity);
        final View mDialogView = factory.inflate(R.layout.popup_select_items, null);
        final AlertDialog allergyDialog = new AlertDialog.Builder(mActivity).create();
        allergyDialog.setView(mDialogView);
        allergyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        lblSize = (TextView)  mDialogView.findViewById(R.id.size_lable);
        TextView categoryName = (TextView)  mDialogView.findViewById(R.id.txt_category);
        TextView categoryPrice = (TextView)  mDialogView.findViewById(R.id.category_price);
        popupTotalPrice = (TextView)  mDialogView.findViewById(R.id.total_price);
        RecyclerView what_type_list  = (RecyclerView) mDialogView.findViewById(R.id.what_size_type_list);
        productModifier  = (RecyclerView) mDialogView.findViewById(R.id.product_modifier);
        mPositionInterface2=this;

        categoryName.setText(product.getProductName());
        categoryPrice.setText("£"+product.getMenuProductPrice());

        if(product.getMenuProductSize().size() >= 0){

            for(int i=0;i<product.getMenuProductSize().size();i++){
                check.add("0");
            }

            mWhatLikeAdapter = new WhatLikeAdapter(_context,mPositionInterface2,check,product,popupTotalPrice);



            @SuppressLint("WrongConstant")
            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(_context, LinearLayoutManager.VERTICAL, false);
            what_type_list.setLayoutManager(horizontalLayoutManagaer);
            what_type_list.setAdapter(mWhatLikeAdapter);
            Log.e("G POS >>",""+gPos);
            if(product.getMenuProductSize().size() <= 0) {
                lblSize.setVisibility(View.GONE);
            }
            if(product.getProductModifiers().size() > 0) {
                bindProductModifires(productModifier,product,gPos);
            }

//            @SuppressLint("WrongConstant")
//            LinearLayoutManager horizontalLayoutManagaer2
//                    = new LinearLayoutManager(_context, LinearLayoutManager.VERTICAL, false);
//            chooseList.setLayoutManager(horizontalLayoutManagaer2);
//            chooseList.setAdapter(mChooseAdapter);

        }

        mDialogView.findViewById(R.id.add_items).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                allergyDialog.dismiss();
                Gson gson = new Gson();
                Log.e("CART>>",">>>"+parentPos);
                itemTotalCount.setText(String.valueOf(Integer.parseInt(itemTotalCount.getText().toString())+1));
                FinalMenuCartDetails mCat = (FinalMenuCartDetails) finalMenuCartDetails.get(0);
                int pPos = 0;
                if(mCat.getSpecialOffers().size() <= 0){
                    pPos = parentPos;
                }else if(mCat.getSpecialOffers().size() > 0){
                    pPos = parentPos-1;
                }


                llBotom.setBackgroundColor(_context.getResources().getColor(R.color.orange));
                itemCount.setVisibility(View.VISIBLE);
                emptyBasket.setText("View basket");



            }
        });

        mDialogView.findViewById(R.id.cross_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                allergyDialog.dismiss();
            }
        });

        allergyDialog.show();
    }
    public void logLargeString(String str) {
        if(str.length() > 3000) {
            Log.e("CART>>", str.substring(0, 3000));
            logLargeString(str.substring(3000));
        } else {
            Log.e("CART>>", str); // continuation
        }
    }
//    public void bindSizeModifires(RecyclerView sizeModifier, MenuProduct product, int childPos){
//        sizeModifierAdapter = new AdapterSizeModifier(_context, product, childPos);
//                    @SuppressLint("WrongConstant")
//            LinearLayoutManager horizontalLayoutManagaer2
//                    = new LinearLayoutManager(_context, LinearLayoutManager.VERTICAL, false);
//        sizeModifier.setLayoutManager(horizontalLayoutManagaer2);
//        sizeModifier.setAdapter(sizeModifierAdapter);
//    }

    public void bindProductModifires(RecyclerView productModifier, MenuProduct product, int childPos){

//        productModifierAdapter = new AdapterProductModifier(_context,product,childPos,showCartProductModifier, popupTotalPrice,totalPrice2);
//        @SuppressLint("WrongConstant")
//        LinearLayoutManager horizontalLayoutManagaer2
//                = new LinearLayoutManager(_context, LinearLayoutManager.VERTICAL, false);
//        productModifier.setLayoutManager(horizontalLayoutManagaer2);
//        productModifier.setAdapter(productModifierAdapter);
        if(product.getMenuProductSize().size() > 0) {
            productModifier.setVisibility(View.GONE);
        }else{
            productModifier.setVisibility(View.VISIBLE);
        }

        //   showCartMenuProduct.add(new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuProduct(product.getMenuProductId(),product.getProductName(),product.getMenuProductPrice(), product.getMenuProductImage(), product.getUserappProductImage(), product.getEcomProductImage(),product.getProductOverallRating(),showCartProductSize,showCartProductModifier,new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.Upsells(),Double.parseDouble(popupTotalPrice.getText().toString()),1));
        //Log.e("MANOIJ","Kumar"+showCartMenuProduct.size());
        //showCartProductSize.add(new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuProductSize(product.getMenuProductSize().get(childPos).getProductSizeId(),product.getMenuProductSize().get(childPos).getProductSizeName(),product.getMenuProductSize().get(childPos).getProductSizePrice(),showCartSizeModifier));

    }

    @Override
    public void onClickPos2(int pos, ArrayList<String> check, MenuProduct qProduct) {

        if(check.contains("1")){
            for(int i=0;i<qProduct.getMenuProductSize().size();i++){
                check.set(i,"0");
            }
            check.set(pos,"1");
        }else{
            check.set(pos,"0");
        }

        popupTotalPrice.setText(qProduct.getMenuProductSize().get(pos).getProductSizePrice());
        totalPrice2 = Double.parseDouble(qProduct.getMenuProductSize().get(pos).getProductSizePrice());

        if(!check.contains("1")){
//            sizeModifier.setVisibility(View.GONE);
            lblSize.setVisibility(View.GONE);
            popupTotalPrice.setText("0.00");
            productModifier.setVisibility(View.GONE);
        }else{
//            sizeModifier.setVisibility(View.VISIBLE);
            lblSize.setVisibility(View.VISIBLE);
            productModifier.setVisibility(View.VISIBLE);

        }
        mWhatLikeAdapter.notifyDataSetChanged();
        if(qProduct.getMenuProductSize().size() > 0) {

            if (qProduct.getMenuProductSize().get(pos).getSizeModifiers().size() > 0) {
                lblSize.setVisibility(View.VISIBLE);
                // showCartSizeModifier.clear();
//                showCartSizeModifier = new ArryLiast<>();
//                sizeModifierAdapter = new AdapterSizeModifier(_context, qProduct, pos, popupTotalPrice, showCartSizeModifier,totalPrice2);
//                @SuppressLint("WrongConstant")
//                LinearLayoutManager horizontalLayoutManagaer2
//                        = new LinearLayoutManager(_context, LinearLayoutManager.VERTICAL, false);
//                sizeModifier.setLayoutManager(horizontalLayoutManagaer2);
//                sizeModifier.setAdapter(sizeModifierAdapter);
            }
        }else{
            lblSize.setVisibility(View.GONE);
        }

    }

    public void alertDialogRepeat(final int groupPosition, final TextView itemTotalCount, final MenuProduct mProduct, final int childPosition, final LinearLayout itemCount){
        LayoutInflater factory = LayoutInflater.from(mActivity);
        final View mDialogView = factory.inflate(R.layout.choose_last_customization, null);
        final AlertDialog repeatDialog = new AlertDialog.Builder(mActivity).create();
        repeatDialog.setView(mDialogView);
        repeatDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView lastItemName = (TextView)  mDialogView.findViewById(R.id.last_item_name);
        lastItemName.setText(mProduct.getProductName());
        mDialogView.findViewById(R.id.repeat_last).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                repeatDialog.dismiss();
                itemTotalCount.setText(String.valueOf(Integer.parseInt(itemTotalCount.getText().toString())+1));
                footerCount.setText(String.valueOf(Integer.parseInt(footerCount.getText().toString())+1));
                footerDetails.setText(String.format("%.2f",Double.parseDouble(popupTotalPrice.getText().toString()) + Double.parseDouble(footerDetails.getText().toString())));

                //    ShowMenuCartDetails = new ArrayList<>();

                FinalMenuCartDetails mCat = (FinalMenuCartDetails) finalMenuCartDetails.get(0);

                int pPos = 0;
                if(mCat.getSpecialOffers().size() <= 0){
                    pPos = groupPosition;
                }else if(mCat.getSpecialOffers().size() > 0){
                    pPos = groupPosition-1;
                }

            }
        });
        mDialogView.findViewById(R.id.choose_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                repeatDialog.dismiss();
                check.clear();
                alertDialogItems(mProduct, childPosition, groupPosition, itemCount,itemTotalCount);

            }
        });
        mDialogView.findViewById(R.id.cross_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                repeatDialog.dismiss();
            }
        });

        repeatDialog.show();
    }


}
