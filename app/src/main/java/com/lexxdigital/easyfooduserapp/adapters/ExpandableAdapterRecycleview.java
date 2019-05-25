package com.lexxdigital.easyfooduserapp.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.dashboard.DashboardActivity;
import com.lexxdigital.easyfooduserapp.restaurant_details.CartDetailsModel;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.menu_category.FinalMenuCartDetails;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.menu_category.MenuProduct;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.response.RestaurantDetailsResponse;
import com.lexxdigital.easyfooduserapp.utility.GlobalValues;
import com.lexxdigital.easyfooduserapp.utility.SharedPreferencesClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableAdapterRecycleview extends BaseExpandableListAdapter implements WhatLikeAdapter.PositionInterface{

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
    ArrayList<com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuProductSize> showCartProductSize = new ArrayList<>();
    ArrayList<com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.SizeModifier> showCartSizeModifier= new ArrayList<>();
    ArrayList<com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.ProductModifier>  showCartProductModifier = new ArrayList<>();
    ArrayList<com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuProduct> showCartMenuProduct = new ArrayList<>();
    ArrayList<com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuCategory> showMenuCategory = new ArrayList<>();
    ArrayList<com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.ShowMenuCartDetails> ShowMenuCartDetails;
    ArrayList<com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.SpecialOffer> showSpecialOffer = new ArrayList<>();
    ArrayList<String> check = new ArrayList<>();
    ArrayList<String> check2 = new ArrayList<>();

    WhatLikeAdapter.PositionInterface mPositionInterface2;
    TextView lblSize;
    WhatLikeAdapter mWhatLikeAdapter;
    AdapterProductModifier productModifierAdapter;
    RecyclerView productModifier;
    public ExpandableAdapterRecycleview() {
    }

    public ExpandableAdapterRecycleview(Activity activity, Context context, List<String> listDataHeader,
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
        ShowMenuCartDetails = new ArrayList<>();
        showCartProductSize = new ArrayList<>();
        showCartProductModifier = new ArrayList<>();
        if(mFCart.getSpecialOffers().size() > 0 && groupPosition == 0){
            TextView title = (TextView) convertView.findViewById(R.id.txt_menu_title);
            TextView price = (TextView) convertView.findViewById(R.id.txt_price);
            RelativeLayout subCategory = (RelativeLayout) convertView.findViewById(R.id.txt_subcategory);
            TextView details = (TextView) convertView.findViewById(R.id.txt_items_detail);
            TextView suTitle = (TextView) convertView.findViewById(R.id.txt_sub_title);
            RelativeLayout itemClick = convertView.findViewById(R.id.click_items);
            LinearLayout itemRemove = (LinearLayout) convertView.findViewById(R.id.item_remove);
            LinearLayout itemAdd = (LinearLayout) convertView.findViewById(R.id.item_add);
            final TextView itemCount = (TextView) convertView.findViewById(R.id.item_count);
            final LinearLayout countClick = (LinearLayout) convertView.findViewById(R.id.clickCount);
            details.setVisibility(View.VISIBLE);
            title.setText(respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferTitle());
            price.setText("£"+respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferPrice());
            details.setText(respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferDetails());
            subCategory.setVisibility(View.GONE);

            itemClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(countClick.getVisibility() == View.GONE){
                        countClick.setVisibility(View.VISIBLE);
                        itemCount.setText("1");
                        totalCount = totalCount+1;
                        totalPrice = totalPrice + Double.parseDouble(respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferPrice());
                        prePrice = prePrice +totalPrice;
                        footerDetails.setText(String.format("%.2f", prePrice));
                        footerCount.setText(String.valueOf(totalCount));
                        listAddCartDetails.add(new CartDetailsModel(respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferId(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferTitle(), "1",respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferPrice(), respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferPrice(),"offer"));

                        showSpecialOffer.add(new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.SpecialOffer(respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferId(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferTitle(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferAvailable(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getSpendAmountToAvaliableOffer(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferDiscountPercentage(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferDeliveryOption(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferDetails(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getOriginalPrice(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getTotalDiscount(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferPrice(),"1",respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferPrice()));


                        arrayListIndex.add(childPosition);
                        llBotom.setBackgroundColor(_context.getResources().getColor(R.color.orange));
                        emptyBasket.setText("View basket");

                    }

                }
            });

            itemAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Position add >>",""+getlistIndex(childPosition));

                    if(Integer.parseInt(itemCount.getText().toString()) < 20) {
                        int count = Integer.parseInt(itemCount.getText().toString());
                        itemCount.setText(String.valueOf(count + 1));
                        totalCount = totalCount + 1;
                        totalPrice = totalPrice + Double.parseDouble(respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferPrice());
                       // prePrice = prePrice +totalPrice;
                        footerDetails.setText(String.format("%.2f", totalPrice));
                        footerCount.setText(String.valueOf(totalCount));

                        CartDetailsModel cart = listAddCartDetails.get(getlistIndex(childPosition));

                        com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.SpecialOffer spO = showSpecialOffer.get(getlistIndex(childPosition));
                        showSpecialOffer.set(getlistIndex(childPosition),new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.SpecialOffer(respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferId(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferTitle(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferAvailable(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getSpendAmountToAvaliableOffer(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferDiscountPercentage(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferDeliveryOption(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferDetails(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getOriginalPrice(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getTotalDiscount(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferPrice(),String.valueOf(Math.round((Double.parseDouble(cart.getTotalAmmount())+Double.parseDouble(respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferPrice()))/Double.parseDouble(respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferPrice()))),String.format("%.2f",Double.parseDouble(spO.getTotalAmmount())+Double.parseDouble(respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferPrice()))));

                        Log.e("AMMOUNT>>",""+cart.getTotalAmmount());
                        listAddCartDetails.set(getlistIndex(childPosition),new CartDetailsModel(respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferId(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferTitle(),  String.valueOf(Math.round((Double.parseDouble(cart.getTotalAmmount())+Double.parseDouble(respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferPrice()))/Double.parseDouble(respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferPrice()))),respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferPrice(), String.valueOf(Double.parseDouble(cart.getTotalAmmount())+Double.parseDouble(respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferPrice())),"offer"));
                        llBotom.setBackgroundColor(_context.getResources().getColor(R.color.orange));
                        emptyBasket.setText("View basket");
                    }


                }
            });

            itemRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.e("Position remove >>",""+getlistIndex(childPosition));
                    if(Integer.parseInt(itemCount.getText().toString()) > 0){
                        int count = Integer.parseInt(itemCount.getText().toString());
                        itemCount.setText(String.valueOf(count-1));
                        totalCount = totalCount -1;
                        totalPrice = totalPrice - Double.parseDouble(respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferPrice());
                      //  prePrice = prePrice +totalPrice;
                        footerDetails.setText(String.format("%.2f", totalPrice));

                        footerCount.setText(String.valueOf(totalCount));
                        Log.e("MAN>>>","///"+listAddCartDetails.size()+"//"+getlistIndex(childPosition));
                        if(listAddCartDetails.size() > 0){
                            CartDetailsModel cart = listAddCartDetails.get(getlistIndex(childPosition));
                            listAddCartDetails.set(getlistIndex(childPosition),new CartDetailsModel(respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferId(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferTitle(), String.valueOf(Math.round((Double.parseDouble(cart.getTotalAmmount())-Double.parseDouble(respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferPrice()))/Double.parseDouble(respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferPrice()))),respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferPrice(), String.valueOf(Double.parseDouble(cart.getTotalAmmount()) - Double.parseDouble(respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferPrice())),"offer"));

                            com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.SpecialOffer spO = showSpecialOffer.get(getlistIndex(childPosition));
                            showSpecialOffer.set(getlistIndex(childPosition),new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.SpecialOffer(respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferId(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferTitle(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferAvailable(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getSpendAmountToAvaliableOffer(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferDiscountPercentage(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferDeliveryOption(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferDetails(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getOriginalPrice(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getTotalDiscount(),respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferPrice(),String.valueOf(Math.round((Double.parseDouble(cart.getTotalAmmount())-Double.parseDouble(respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferPrice()))/Double.parseDouble(respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferPrice()))),String.format("%.2f",Double.parseDouble(spO.getTotalAmmount())-Double.parseDouble(respose.getData().getMenu().getSpecialOffers().get(childPosition).getOfferPrice()))));

                            llBotom.setBackgroundColor(_context.getResources().getColor(R.color.orange));
                            emptyBasket.setText("View basket");
                        }

                    }
                    if(Integer.parseInt(itemCount.getText().toString()) == 0){
                        countClick.setVisibility(View.GONE);
                        listAddCartDetails.remove(getlistIndex(childPosition));
                        arrayListIndex.remove(getlistIndex(childPosition));
                    }
                    if(totalCount == 0){
                        emptyBasket.setText("Your basket is empty.");
                        llBotom.setBackgroundColor(_context.getResources().getColor(R.color.gray_light));
                    }
                }
            });

        }else {
//            int pPos = 0;
//            if(respose.getData().getMenu().getSpecialOffers().size() <= 0){
//                pPos = groupPosition;
//            }else if(respose.getData().getMenu().getSpecialOffers().size() > 0){
//                pPos = groupPosition-1;
//            }
//            Log.e("LEN MANOJ>>",pPos+"//"+respose.getData().getMenu().getMenuCategory().get(pPos).getMenuSubCategory().size());
//            LayoutInflater infalInflater = (LayoutInflater) this._context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = infalInflater.inflate(R.layout.row_sub_category, null);
//            final RelativeLayout subCategory = (RelativeLayout) convertView.findViewById(R.id.txt_subcategory);
//            TextView suTitle = (TextView) convertView.findViewById(R.id.txt_sub_title);
//            RecyclerView subList = (RecyclerView) convertView.findViewById(R.id.sub_items);
//            if (respose.getData().getMenu().getMenuCategory().get(pPos).getMenuSubCategory().size() > 0) {
//                suTitle.setText(respose.getData().getMenu().getMenuCategory().get(pPos).getMenuSubCategory().get(childPosition).getMenuCategoryName());
//                for(int i=0;i<respose.getData().getMenu().getMenuCategory().get(pPos).getMenuSubCategory().size();i++){
//                    suTitle.setText(respose.getData().getMenu().getMenuCategory().get(pPos).getMenuSubCategory().get(i).getMenuCategoryName());
//                }
//            }else{
//
//            }


            if(convertView == null){
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.offer_item_list, null);
            }
                TextView childItemName = (TextView) convertView.findViewById(R.id.txt_menu_title);
                TextView childItemPrice = (TextView) convertView.findViewById(R.id.txt_price);
                LinearLayout itemClick = (LinearLayout) convertView.findViewById(R.id.ly_item);
                RelativeLayout lyItemClick = convertView.findViewById(R.id.click_items);
                TextView details = (TextView) convertView.findViewById(R.id.txt_items_detail);

                LinearLayout itemRemove = (LinearLayout) convertView.findViewById(R.id.item_remove);
                LinearLayout itemAdd = (LinearLayout) convertView.findViewById(R.id.item_add);

                final TextView itemTotalCount = (TextView) convertView.findViewById(R.id.item_count);
                details.setVisibility(View.GONE);
                final LinearLayout itemCount = (LinearLayout) convertView.findViewById(R.id.clickCount);
                final MenuProduct mProduct = (MenuProduct) _listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosition);
                //  final int ppp = this._listDataHeader.get(groupPosition);
                childItemName.setText(mProduct.getProductName());
                childItemPrice.setText("£" + mProduct.getMenuProductPrice());

                lyItemClick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.e("LEN1", "" + mProduct.getMenuProductSize().size());
                        Log.e("LEN2", "" + mProduct.getProductModifiers().size());


                        if (mProduct.getMenuProductSize().size() <= 0 && mProduct.getProductModifiers().size() <= 0) {
                            itemCount.setVisibility(View.VISIBLE);
                            itemTotalCount.setText("1");
                            footerDetails.setText(String.format("%.2f", Double.parseDouble(footerDetails.getText().toString()) + Double.parseDouble(mProduct.getMenuProductPrice())));
                            footerCount.setText(String.valueOf(Integer.parseInt(footerCount.getText().toString()) + 1));
                            llBotom.setBackgroundColor(_context.getResources().getColor(R.color.orange));
                            itemCount.setVisibility(View.VISIBLE);
                            emptyBasket.setText("View basket");
                            showCartMenuProduct = new ArrayList<>();
                            arrayListIndextwo.add(mProduct.getMenuProductId());
                            Log.e("MANOJ", "KUMAR");

                            showCartMenuProduct.add(new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuProduct(mProduct.getMenuProductId(), mProduct.getProductName(), mProduct.getVegType(), mProduct.getMenuProductPrice(), mProduct.getUserappProductImage(), mProduct.getEcomProductImage(), mProduct.getProductOverallRating(), showCartProductSize, showCartProductModifier, new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.Upsells(), Double.parseDouble(mProduct.getMenuProductPrice()), 1));

                            FinalMenuCartDetails mCat = (FinalMenuCartDetails) finalMenuCartDetails.get(0);

                            showMenuCategory.add(new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuCategory(mCat.getMenuCategory().get(childPosition).getMenuCategoryId(), mCat.getMenuCategory().get(childPosition).getMenuCategoryName(), showCartMenuProduct));

                            ShowMenuCartDetails.add(new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.ShowMenuCartDetails(showSpecialOffer, showMenuCategory));

                        } else {
                            check.clear();
                            if (showMenuCategory.size() <= 0) {
                                alertDialogItems(mProduct, childPosition, groupPosition, itemCount, itemTotalCount);
                            } else {
                                if (!getIsProductAdded(mProduct.getMenuProductId())) {
                                    alertDialogItems(mProduct, childPosition, groupPosition, itemCount, itemTotalCount);
                                } else {
                                    alertDialogRepeat(groupPosition, itemTotalCount, mProduct, childPosition, itemCount);
                                }
                            }

                        }

                        Log.e("LEN MP >>", "\\" + showCartMenuProduct.size() + "//" + mProduct.getMenuProductId() + "//" + arrayListIndextwo.size());
                    }
                });

                itemAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mProduct.getMenuProductSize().size() > 0 || mProduct.getProductModifiers().size() > 0) {
                            alertDialogRepeat(groupPosition, itemTotalCount, mProduct, childPosition, itemCount);
                        } else {
                            itemTotalCount.setText(String.valueOf(Integer.parseInt(itemTotalCount.getText().toString()) + 1));
                            footerDetails.setText(String.format("%.2f", Double.parseDouble(footerDetails.getText().toString()) + Double.parseDouble(mProduct.getMenuProductPrice())));
                            footerCount.setText(String.valueOf(Integer.parseInt(footerCount.getText().toString()) + 1));
                            Log.e("LEN MP 2>>", "\\" + showCartMenuProduct.size() + "//" + getlistIndextwo(mProduct.getMenuProductId()) + "//" + mProduct.getMenuProductId());


//                        com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuProduct smp = showCartMenuProduct.get(getlistIndextwo(mProduct.getMenuProductId()));
//
//
//                        showCartMenuProduct.set(getlistIndextwo(mProduct.getMenuProductId()),new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuProduct(mProduct.getMenuProductId(),mProduct.getProductName(), mProduct.getVegType(),mProduct.getMenuProductPrice(),mProduct.getUserappProductImage(),mProduct.getEcomProductImage(),mProduct.getProductOverallRating(),showCartProductSize,showCartProductModifier,new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.Upsells(),smp.getTotalAmmount()+Double.parseDouble(mProduct.getMenuProductPrice()),smp.getQuantity()+1));
//                        ShowMenuCartDetails.add(new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.ShowMenuCartDetails(showSpecialOffer,showMenuCategory));

                        }
                    }
                });

                itemRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Integer.parseInt(itemTotalCount.getText().toString()) > 0) {
                            itemTotalCount.setText(String.valueOf(Integer.parseInt(itemTotalCount.getText().toString()) - 1));
                            footerCount.setText(String.valueOf(Integer.parseInt(footerCount.getText().toString()) - 1));
                            footerDetails.setText(String.format("%.2f", Double.parseDouble(footerDetails.getText().toString()) - Double.parseDouble(mProduct.getMenuProductPrice())));
                            com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuProduct smp = showCartMenuProduct.get(getlistIndextwo(mProduct.getMenuProductId()));
                            showCartMenuProduct.set(getlistIndextwo(mProduct.getMenuProductId()), new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuProduct(mProduct.getMenuProductId(), mProduct.getProductName(), mProduct.getVegType(), mProduct.getMenuProductPrice(), mProduct.getUserappProductImage(), mProduct.getEcomProductImage(), mProduct.getProductOverallRating(), showCartProductSize, showCartProductModifier, new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.Upsells(), smp.getTotalAmmount() - Double.parseDouble(mProduct.getMenuProductPrice()), smp.getQuantity() - 1));


                        }
                        if (Integer.parseInt(itemTotalCount.getText().toString()) == 0) {
                            emptyBasket.setText("Your basket is empty.");
                            llBotom.setBackgroundColor(_context.getResources().getColor(R.color.gray_light));
                            itemCount.setVisibility(View.GONE);
                            footerDetails.setText("0.00");
                            arrayListIndextwo.remove(getlistIndextwo(mProduct.getMenuProductId()));
                            showCartMenuProduct.remove(getlistIndextwo(mProduct.getMenuProductId()));
                        }
                    }
                });



        }
       ShowMenuCartDetails = new ArrayList<>();
        llBotom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0; i < listAddCartDetails.size(); i++){
                    Log.e("COUNT 3 >>","//"+listAddCartDetails.size()+"//"+listAddCartDetails.get(i).getProductPrice());
                }
                ShowMenuCartDetails.add(new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.ShowMenuCartDetails(showSpecialOffer,showMenuCategory));

                Gson gson = new Gson();
                String json = gson.toJson(ShowMenuCartDetails); //convert
                //   System.out.println(json);
                Log.e("JSON 22>>",""+json);
                logLargeString(json);
                Gson gson2 = new Gson();
                String jsonDeatil = gson.toJson(val.getRestaurantDetailsResponse()); //convert

                sharedPreferencesClass = new SharedPreferencesClass(_context);
                sharedPreferencesClass.setCartDetailsKey(json);
                sharedPreferencesClass.setCartRestaurantDeatilKey(jsonDeatil);
                if(Integer.parseInt(footerCount.getText().toString()) > 0) {
                    Intent i = new Intent(_context, DashboardActivity.class);
                    i.putExtra("FROMMENU", "YES");
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setAction("custom");
                    _context.startActivity(i);
                }else{

                }


            }
        });

        return convertView;
    }


    public int getlistIndex(int num){
        for(int i=0; i<arrayListIndex.size(); i++){
            if(num == arrayListIndex.get(i)){
                return i;
            }
        }
        return -1;
    }

    public boolean getIsProductAdded(String id){
        for(int i=0; i<showMenuCategory.size(); i++){
            if(showMenuCategory.get(i).getMenuProducts().get(0).getMenuProductId().equalsIgnoreCase(id)){
                return true;
            }
        }
        return false;
    }

    public int getlistIndextwo(String num){
        for(int i=0; i<arrayListIndextwo.size(); i++){
            if(num.equalsIgnoreCase(arrayListIndextwo.get(i))){
                return i;
            }
        }
        return -1;
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
        showCartSizeModifier = new ArrayList<>();
      //  showCartMenuProduct = new ArrayList<>();
        showCartProductModifier = new ArrayList<>();
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


        ShowMenuCartDetails = new ArrayList<>();

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
                //  showMenuCategory = new ArrayList<>();
                showCartMenuProduct.add(new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuProduct(product.getMenuProductId(),product.getProductName(), product.getVegType(),product.getMenuProductPrice(),product.getUserappProductImage(),product.getEcomProductImage(),product.getProductOverallRating(),showCartProductSize,showCartProductModifier,new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.Upsells(),Double.parseDouble(popupTotalPrice.getText().toString()),1));
                Log.e("MODI >> LEN 12 >>",""+showCartProductModifier.size());
                showMenuCategory.add(new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuCategory(mCat.getMenuCategory().get(pPos).getMenuCategoryId(),mCat.getMenuCategory().get(pPos).getMenuCategoryName(),showCartMenuProduct));
                prePrice = prePrice + Double.parseDouble(popupTotalPrice.getText().toString());
                ShowMenuCartDetails.add(new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.ShowMenuCartDetails(showSpecialOffer,showMenuCategory));
                 String json = gson.toJson(ShowMenuCartDetails); //convert
                Log.e("LEN MP 12344 >>","\\"+showCartMenuProduct.size()+"//"+arrayListIndextwo.size());

                arrayListIndextwo.add(product.getMenuProductId());
                footerDetails.setText(String.format("%.2f",Double.parseDouble(popupTotalPrice.getText().toString())+Double.parseDouble(footerDetails.getText().toString())));
                footerCount.setText(String.valueOf(Integer.parseInt(footerCount.getText().toString())+1));
                llBotom.setBackgroundColor(_context.getResources().getColor(R.color.orange));
                itemCount.setVisibility(View.VISIBLE);
                emptyBasket.setText("View basket");
               // System.out.print(json.toString());
                logLargeString(json);
                Log.e("LEN MP 123 >>","\\"+showCartMenuProduct.size()+"//"+arrayListIndextwo.size());
              //  Log.i("CART>>",">>>"+json+"//"+showMenuCategory.size());
                //    dialog2.show();0



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

        productModifierAdapter = new AdapterProductModifier(_context,product,childPos,showCartProductModifier, popupTotalPrice,totalPrice2);
        @SuppressLint("WrongConstant")
        LinearLayoutManager horizontalLayoutManagaer2
                = new LinearLayoutManager(_context, LinearLayoutManager.VERTICAL, false);
        productModifier.setLayoutManager(horizontalLayoutManagaer2);
        productModifier.setAdapter(productModifierAdapter);
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
            showCartMenuProduct = new ArrayList<>();
            showCartProductSize = new ArrayList<>();
           // showCartProductModifier = new ArrayList<>();
            showCartProductSize.add(new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuProductSize(qProduct.getMenuProductSize().get(pos).getProductSizeId(),qProduct.getMenuProductSize().get(pos).getProductSizeName(),qProduct.getMenuProductSize().get(pos).getProductSizePrice(),showCartSizeModifier));
//            showCartMenuProduct.add(new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuProduct(qProduct.getMenuProductId(),qProduct.getProductName(),qProduct.getMenuProductPrice(),showCartProductSize,showCartProductModifier,new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.Upsells(),Double.parseDouble(popupTotalPrice.getText().toString())));

        }

    //    Toast.makeText(_context, "Clicked > "+pos, Toast.LENGTH_SHORT).show();
        mWhatLikeAdapter.notifyDataSetChanged();
        if(qProduct.getMenuProductSize().size() > 0) {

            if (qProduct.getMenuProductSize().get(pos).getSizeModifiers().size() > 0) {
                lblSize.setVisibility(View.VISIBLE);
               // showCartSizeModifier.clear();
//                showCartSizeModifier = new ArryLiast<>();
                sizeModifierAdapter = new AdapterSizeModifier(_context, qProduct, pos, popupTotalPrice, showCartSizeModifier,totalPrice2);
                @SuppressLint("WrongConstant")
                LinearLayoutManager horizontalLayoutManagaer2
                        = new LinearLayoutManager(_context, LinearLayoutManager.VERTICAL, false);
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
           //     showMenuCategory = new ArrayList<>();
               // com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.ShowMenuCartDetails preCartRepeated = ShowMenuCartDetails.get(pPos);
              //  Log.e("POSITION >>",""+pPos);
//                Log.e("LEN MP 256>>","\\"+showCartMenuProduct.size()+"//"+getlistIndextwo(mProduct.getMenuProductId())+"//"+mProduct.getMenuProductId());
////
//                for(int i=0;i<arrayListIndextwo.size();i++){
//                    Log.e("LEN ID>>",""+arrayListIndextwo.get(i));
//                }
////
//                com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuProduct smp = showCartMenuProduct.get(getlistIndextwo(mProduct.getMenuProductId()));
//
//                showCartMenuProduct.set(getlistIndextwo(mProduct.getMenuProductId()),new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuProduct(mProduct.getMenuProductId(),mProduct.getProductName(), mProduct.getVegType(),mProduct.getMenuProductPrice(),mProduct.getUserappProductImage(),mProduct.getEcomProductImage(),mProduct.getProductOverallRating(),showCartProductSize,showCartProductModifier,new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.Upsells(),smp.getTotalAmmount()+Double.parseDouble(mProduct.getMenuProductPrice()),smp.getQuantity()+1));
//                Log.e("LEN MP 257>>","//"+showCartMenuProduct.size());
              showMenuCategory.add(new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.MenuCategory(mCat.getMenuCategory().get(pPos).getMenuCategoryId(),mCat.getMenuCategory().get(pPos).getMenuCategoryName(),showCartMenuProduct));
//
             ShowMenuCartDetails.add(new com.lexxdigital.easyfooduserapp.restaurant_details.model.show_menu_category.ShowMenuCartDetails(showSpecialOffer,showMenuCategory));


                Gson gson = new Gson();
                String json = gson.toJson(ShowMenuCartDetails); //convert
                logLargeString(json);
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