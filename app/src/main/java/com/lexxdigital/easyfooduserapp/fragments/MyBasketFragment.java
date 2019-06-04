package com.lexxdigital.easyfooduserapp.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.adapters.AdapterBasketOrderItems;
import com.lexxdigital.easyfooduserapp.adapters.MenuAdapterItems;
import com.lexxdigital.easyfooduserapp.adapters.RecyclerLayoutManager;
import com.lexxdigital.easyfooduserapp.adapters.RoomOrderAdapter;
import com.lexxdigital.easyfooduserapp.adapters.menu_adapter.MenuCartAdapter;
import com.lexxdigital.easyfooduserapp.adapters.menu_adapter.MenuSpecialOfferAdapter;
import com.lexxdigital.easyfooduserapp.adapters.menu_adapter.OnUpsellProductItemClick;
import com.lexxdigital.easyfooduserapp.adapters.menu_adapter.UpSellProductAdapter;
import com.lexxdigital.easyfooduserapp.api.AddressListInterface;
import com.lexxdigital.easyfooduserapp.api.VoucherApplyInterface;
import com.lexxdigital.easyfooduserapp.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapp.cart_model.final_cart.FinalNewCartDetails;
import com.lexxdigital.easyfooduserapp.dashboard.DashboardActivity;
import com.lexxdigital.easyfooduserapp.dialogs.AddressDialogFragment;
import com.lexxdigital.easyfooduserapp.dialogs.TimeSlotDialogFragment;
import com.lexxdigital.easyfooduserapp.model.VoucherApplyRequest;
import com.lexxdigital.easyfooduserapp.model.VoucherApplyResponse;
import com.lexxdigital.easyfooduserapp.model.address_list_request.AddressListRequest;
import com.lexxdigital.easyfooduserapp.model.address_list_response.AddressListResponse;
import com.lexxdigital.easyfooduserapp.restaurant_details.RestaurantDetailsActivity;
import com.lexxdigital.easyfooduserapp.restaurant_details.api.RestaurantDetailsInterface;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.new_restaurant_response.NewRestaurantsDetailsResponse;

import com.lexxdigital.easyfooduserapp.restaurant_details.model.request.RestaurantDetailsRequest;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.CartData;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MealProduct;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategoryCart;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MenuProductSize;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.Modifier;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.ProductModifier;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.SizeModifier;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.SpecialOffer;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.UpSells;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.UpsellProduct;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.request.UpSellsRequest;
import com.lexxdigital.easyfooduserapp.select_payment_method.SelectPaymentMethodActivity;
import com.lexxdigital.easyfooduserapp.utility.ApiClient;
import com.lexxdigital.easyfooduserapp.utility.Constants;
import com.lexxdigital.easyfooduserapp.utility.GlobalValues;
import com.lexxdigital.easyfooduserapp.utility.SharedPreferencesClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class MyBasketFragment extends Fragment implements MenuCartAdapter.OnMenuCartItemClick, MenuSpecialOfferAdapter.OnMenuSpecialOfferItemClick, OnUpsellProductItemClick, UpSellProductAdapter.OnUpsellItemListClick, TimeSlotDialogFragment.OnDeliveryTimeSelectedListener {

    TextView nameOfChekenTv22;
    @BindView(R.id.nameOfChekenTv2233)
    TextView nameOfChekenTv2233;
    @BindView(R.id.coponcode)
    EditText coponcode;
    @BindView(R.id.btn_ApplyVoucherCode)
    TextView btnApplyVoucherCode;
    @BindView(R.id.tv_voucherStatus)
    TextView tvVoucherStatus;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.checkOutTv)
    TextView checkOutTv;
    Unbinder unbinder;
    @BindView(R.id.roomorder_list_id)
    RecyclerView roomorderListId;
    RoomOrderAdapter mRoomOrderAdapter;
    @BindView(R.id.recyclerview_order_items)
    RecyclerView recyclerviewOrderItems;
    @BindView(R.id.recyclerview_upsell_item)
    RecyclerView recyclerviewUpsellItem;
    SharedPreferencesClass sharedPreferencesClass;
    @BindView(R.id.back_image)
    ImageView backImage;
    @BindView(R.id.logo)
    CircleImageView logo;
    @BindView(R.id.allergy_click)
    TextView allergyClick;
    @BindView(R.id.restaurant_name)
    TextView restaurantName;
    @BindView(R.id.restaurant_rating)
    TextView restaurantRating;
    @BindView(R.id.im_ratingImage)
    ImageView imRatingImage;
    @BindView(R.id.restaurant_cuisines)
    TextView restaurantCuisines;
    @BindView(R.id.restaurant_delivery_min_order)
    TextView restaurantDeliveryMinOrder;
    @BindView(R.id.coolection_delivery)
    TextView coolectionDelivery;
    @BindView(R.id.click_delivery_time_change)
    TextView clickDeliveryTimeChange;
    @BindView(R.id.sub_total)
    TextView subTotal;
    @BindView(R.id.delivery_fees)
    TextView deliveryFees;
    @BindView(R.id.total_ammount)
    TextView totalAmmount;
    @BindView(R.id.discount)
    TextView tvdiscount;
    @BindView(R.id.total_count)
    TextView totalCount;
    @BindView(R.id.footer_total_count)
    TextView footerTotalCount;
    @BindView(R.id.footer_total_amount)
    TextView footerTotalAmount;
    @BindView(R.id.delivery_time)
    TextView deliveryTime;
    @BindView(R.id.delivery_date)
    TextView deliveryDate;
    @BindView(R.id.ll_DeliveryTimeSlot)
    LinearLayout llDeliveryTimeSlot;
    @BindView(R.id.btn_checkout)
    LinearLayout btnCheckout;
    @BindView(R.id.scroll)
    NestedScrollView scroll;
    @BindView(R.id.add_note)
    TextView addNote;
    @BindView(R.id.ly_container)
    RelativeLayout lyContainer;
    @BindView(R.id.recyclerview_menu_items)
    RecyclerView recyclerviewMenuItems;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.RelativeLayout01)
    LinearLayout RelativeLayout01;
    @BindView(R.id.add_more_item)
    LinearLayout addMoreItem;
    @BindView(R.id.btn_addNoteEdit)
    TextView btnaddNotePadEdit;
    @BindView(R.id.tv_addNoteData)
    TextView tvAddNoteData;
    @BindView(R.id.tv_restaurantAdddress)
    TextView tvRestaurantAdddress;
    @BindView(R.id.tv_viewMap)
    TextView tvViewMap;
    @BindView(R.id.ll_delivery)
    LinearLayout ll_delivery;
    @BindView(R.id.ll_dinein)
    LinearLayout ll_dinein;
    @BindView(R.id.ll_collection)
    LinearLayout ll_collection;
    @BindView(R.id.delivery)
    ImageView delivery;
    @BindView(R.id.dine_in)
    ImageView dine_in;
    @BindView(R.id.collection)
    ImageView collection;
    @BindView(R.id.ll_CollectionFromRestaurant)
    LinearLayout llCollectionFromRestaurant;
    @BindView(R.id.ll_RoomForMore)
    LinearLayout llRoomForMore;

    @BindView(R.id.ll_Address)
    LinearLayout llDeliveryAddress;
    @BindView(R.id.tv_DeliveryAdddress)
    TextView tvDeliveryAddress;
    @BindView(R.id.tv_ChangeAddress)
    TextView tvChange;

    @BindView(R.id.ll_BillingAddress)
    LinearLayout llBillingAddress;
    @BindView(R.id.tv_BillingAdddress)
    TextView tvBillingAdddress;
    @BindView(R.id.tv_ChangeBillingAddress)
    TextView tvChangeBillingAddress;
    @BindView(R.id.ch_billAddress)
    CheckBox chDeliverySameBilling;

    NewRestaurantsDetailsResponse res;
    private AdapterBasketOrderItems oAdapter;
    private GlobalValues val;
    private Activity mActivity;
    private Context mContext;
    private static String[] paths;
    String restaurantPhoneNumber;
    double mlat = 0.d;
    double mlong = 0.d;
    FinalNewCartDetails cartList;
    MenuCartAdapter.OnMenuCartItemClick onClicklistener;
    MenuSpecialOfferAdapter.OnMenuSpecialOfferItemClick onMenuSpecialOfferItemClick;
    RecyclerLayoutManager productModifierLayoutManager;
    private DatabaseHelper db;
    MenuCartAdapter mAdapter;
    MenuSpecialOfferAdapter mOfferAdapter;
    UpSellProductAdapter mUpSellProductAdapter;

    String orderType = "Please Select";
    double netAmount = 0.d;
    int numberOfQty;
    private Dialog dialog;
    private String voucherCode;
    private Double voucherDiscount = 0.0d;
    private Double deliveryFeesAmt;
    private Double minOrderValue = 0.0d;
    private String voucherApplicableOn;
    private String voucherType;
    private Double voucherValue = 0.0d;
    private Double appliedVoucherAmount = 0.0d;
    private String appliedVoucherCode = "";
    private String appliedVoucherPaymentType = "";
    private String voucherValidOn = "";

    public MyBasketFragment(Activity mActivity, Context mContext) {
        this.mActivity = mActivity;
        this.mContext = mContext;
    }


    Double totalPrice = 0d;
    int totalCartIterm;
    Boolean voucherApplyStatus = false;
    String voucherCodeUsed = "";
    int deliveryPosition = -1;
    Double updateSubTotal = 0.0d;
    private List<String> productIdForUpsell;
    TimeSlotDialogFragment timeSlotDialogFragment;
    Boolean isPreOrder = false;
    String restuarantOpenStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_basket, container, false);
        unbinder = ButterKnife.bind(this, view);
        val = (GlobalValues) getActivity().getApplication();

        dialog = new Dialog(getActivity());
        dialog.setTitle("");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        sharedPreferencesClass = new SharedPreferencesClass(getContext());
        onClicklistener = this;
        onMenuSpecialOfferItemClick = this;

        scroll.fullScroll(ScrollView.FOCUS_UP);

        initViewCart();
        try {

            int totalItemOnCart = 0;
            totalItemOnCart = db.getMenuProduct().size();
            totalItemOnCart += db.getSpecialOffer().size();
            totalItemOnCart += db.getUpSellProducts().size();

            if (totalItemOnCart > 0) {
                dialog.show();
                if (Constants.isInternetConnectionAvailable(300)) {
                    getRestaurantDetails(sharedPreferencesClass.getString(sharedPreferencesClass.RESTUARANT_ID));
                } else {
                    dialogNoInternetConnection("Please check internet connection.", 1);
                }
                //getAddressList(); // By-default  default address

                if (coponcode.getText().toString().trim() == null && coponcode.getText().toString().equalsIgnoreCase("")) {
                    tvVoucherStatus.setVisibility(View.GONE);
                }
                lyContainer.setVisibility(View.VISIBLE);

            } else {
                lyContainer.setVisibility(View.GONE);
                alertDialogEmptyBasket();
            }

        } catch (NullPointerException e) {
            lyContainer.setVisibility(View.GONE);
            alertDialogEmptyBasket();
        }
        return view;
    }

    void spinnerCall(int deliveryPosition) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (deliveryPosition != -1)
            spinner.setSelection(deliveryPosition);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                orderType = (String) parent.getItemAtPosition(position);
                Log.e("item", (String) parent.getItemAtPosition(position));

                setPriceCalculation(totalCartIterm);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void changeTime() {

        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View mDialogView = factory.inflate(R.layout.select_time_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setView(mDialogView);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // ImageView confirm = (ImageView) alertDialog.findViewById(R.id.cross_tv);
        mDialogView.findViewById(R.id.time_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        mDialogView.findViewById(R.id.cross_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        /*confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });*/
        alertDialog.show();
    }


    private void initViewRoom() {
        mRoomOrderAdapter = new RoomOrderAdapter(mContext, this);
        RecyclerLayoutManager horizontalLayoutManagaer = new RecyclerLayoutManager(1, RecyclerLayoutManager.HORIZONTAL);
        roomorderListId.setLayoutManager(horizontalLayoutManagaer);
        roomorderListId.setAdapter(mRoomOrderAdapter);
    }

    private void initView2(FinalNewCartDetails list, TextView discount, TextView subTotal, TextView totalCount, TextView totalAmmount, TextView footerTotalCount, TextView footerTotalAmount) {

        int lCount = 0;
        for (int i = 0; i < list.getData().size(); i++) {
            if (list.getData().get(i).getSpecialOffer() != null) {
                lCount++;
            }
        }
        oAdapter = new AdapterBasketOrderItems(getContext(), list, discount, subTotal, totalCount, totalAmmount, footerTotalCount, footerTotalAmount, lCount);


        @SuppressLint("WrongConstant")
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerviewOrderItems.setLayoutManager(horizontalLayoutManagaer);
        recyclerviewOrderItems.setAdapter(oAdapter);
    }

    private void initViewCart() {
        Gson gson = new Gson();
        db = new DatabaseHelper(mContext);

        CartData data = db.getCartData();

        List<MenuProduct> cartMenu = new ArrayList<>();
//        for (CartData data : cartData) {
        for (MenuCategoryCart menuCategoryCart : data.getMenuCategoryCarts()) {
            for (MenuProduct menuProduct : menuCategoryCart.getMenuProducts()) {
                cartMenu.add(menuProduct);
            }
            for (MenuCategoryCart menuCategoryCart1 : menuCategoryCart.getMenuSubCategory()) {
                for (MenuProduct menuProduct : menuCategoryCart1.getMenuProducts()) {
                    cartMenu.add(menuProduct);
                }
            }
        }
//        }

        RecyclerLayoutManager layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
        recyclerviewOrderItems.setLayoutManager(layoutManager);

        mAdapter = new MenuCartAdapter(mContext, this);
        recyclerviewOrderItems.setAdapter(mAdapter);
        mAdapter.addItem(cartMenu);
        productIdForUpsell = new ArrayList<>();
        for (int i = 0; i < cartMenu.size(); i++) {
            productIdForUpsell.add(cartMenu.get(i).getMenuProductId());
        }
        if (productIdForUpsell.size() > 0) {
            if (Constants.isInternetConnectionAvailable(300)) {
                getUpSellProducts(productIdForUpsell);
            } else {
                dialogNoInternetConnection("Please check internet connection.", 2);
            }

        }
        initViewSpecialOffer();


        initViewUpsell();
        showPriceAndView();

    }

    private void initViewSpecialOffer() {

        List<SpecialOffer> specialOffers = db.getSpecialOffer();
        if (specialOffers.size() > 0) {

            RecyclerLayoutManager layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
            recyclerviewMenuItems.setLayoutManager(layoutManager);

            mOfferAdapter = new MenuSpecialOfferAdapter(mContext, this);
            recyclerviewMenuItems.setAdapter(mOfferAdapter);
            mOfferAdapter.addItem(specialOffers);
        }
    }

    private void initViewUpsell() {

        List<UpsellProduct> upsellProductList = db.getUpSellProducts();
        recyclerviewUpsellItem.setLayoutManager(new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL));
        mUpSellProductAdapter = new UpSellProductAdapter(mContext, this);
        recyclerviewUpsellItem.setAdapter(mUpSellProductAdapter);

        if (upsellProductList.size() > 0) {
            mUpSellProductAdapter.clearData();
            mUpSellProductAdapter.addItem(upsellProductList);

        }

    }

    private void initView3(FinalNewCartDetails list, TextView discount, TextView subTotal, TextView totalCount, TextView totalAmmount, TextView footerTotalCount, TextView footerTotalAmount, Double deliveryCharge, TextView txtDeliveryFees) {

        int llCount = 0;
        for (int i = 0; i < list.getData().size(); i++) {
            if (list.getData().get(i).getMenuCategory() != null) {
                llCount++;
            }
        }
        MenuAdapterItems iAdapter = new MenuAdapterItems(getContext(), list, discount, subTotal, totalCount, totalAmmount, footerTotalCount, footerTotalAmount, deliveryCharge, txtDeliveryFees, llCount);

        @SuppressLint("WrongConstant")
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerviewMenuItems.setLayoutManager(horizontalLayoutManagaer);
        recyclerviewMenuItems.setAdapter(iAdapter);
    }


    @OnClick({R.id.allergy_click, R.id.click_delivery_time_change, R.id.add_note,
            R.id.btn_addNoteEdit, R.id.add_more_item, R.id.btn_checkout, R.id.tv_viewMap,
            R.id.btn_ApplyVoucherCode, R.id.tv_ChangeAddress, R.id.tv_ChangeBillingAddress,
            R.id.ch_billAddress, R.id.ll_DeliveryTimeSlot})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.allergy_click:
                alertDialogAllergy();
                break;
            case R.id.click_delivery_time_change:
                changeTime();
                break;
            case R.id.add_note:
                alertDialogNote();
                break;
            case R.id.btn_addNoteEdit:
                alertDialogNote();
                break;
            case R.id.add_more_item:
                try {
                    Intent i = new Intent(getContext(), RestaurantDetailsActivity.class);
                    if (val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantId() != null && !val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantId().equalsIgnoreCase("")) {
                        i.putExtra("RESTAURANTID", val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantId());
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                    }
                } catch (NullPointerException e) {
                    Log.e("NullPointerException", e.getLocalizedMessage());

                }
                break;
            case R.id.btn_checkout:
                try {

                    if (orderType == null || orderType.equalsIgnoreCase("Please Select")) {
                        scroll.fullScroll(ScrollView.FOCUS_UP);
                        spinner.performClick();

                    } else {
                        if (orderType.equalsIgnoreCase("Delivery")) {
                            if (totalPrice >= Double.parseDouble(res.getData().getRestaurants().getMinOrderValue())) {
                                if (!isPreOrder) {
                                    if (sharedPreferencesClass.getString(sharedPreferencesClass.DEFAULT_ADDRESS) != null && !sharedPreferencesClass.getString(sharedPreferencesClass.DEFAULT_ADDRESS).equals("")) {
//                                    if (sharedPreferencesClass.getString(sharedPreferencesClass.BILLING_ADDRESS) != null) {
                                        Intent intent = new Intent(getContext(), SelectPaymentMethodActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtra("orderType", orderType);
                                        intent.putExtra("deliveryCharge", deliveryFeesAmt);
                                        intent.putExtra("ORDER_TOTAL", netAmount);
                                        intent.putExtra("ORDER_SUB_TOTAL", totalPrice);
                                        intent.putExtra("voucherDiscount", voucherDiscount);
                                        intent.putExtra("notes", tvAddNoteData.getText().toString());
                                        intent.putExtra("appliedVoucherCode", appliedVoucherCode);
                                        intent.putExtra("appliedVoucherAmount", appliedVoucherAmount);
                                        intent.putExtra("appliedVoucherPaymentType", appliedVoucherPaymentType);
                                        startActivity(intent);
                                        getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
//                                    } else {
//                                        alertDailogConfirm("Please select or add billing address");
//                                    }
                                    } else {
                                        alertDailogConfirm("Please select or add delivery address");

                                    }
                                } else {
                                    timeSlotDialogFragment = TimeSlotDialogFragment.newInstance(mContext, this);
                                    timeSlotDialogFragment.show(getFragmentManager(), "timeSlot");
                                }
                            } else {
                                alertDailogConfirm("Minimum order value " + mContext.getString(R.string.currency) + String.format("%.2f", Double.parseDouble(res.getData().getRestaurants().getMinOrderValue())));
                            }
                        } else {
                            Intent intent = new Intent(getContext(), SelectPaymentMethodActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("orderType", orderType);
                            intent.putExtra("deliveryCharge", deliveryFeesAmt);
                            intent.putExtra("ORDER_TOTAL", netAmount);
                            intent.putExtra("ORDER_SUB_TOTAL", totalPrice);
                            intent.putExtra("voucherDiscount", voucherDiscount);
                            intent.putExtra("notes", tvAddNoteData.getText().toString());
                            intent.putExtra("appliedVoucherCode", appliedVoucherCode);
                            intent.putExtra("appliedVoucherAmount", appliedVoucherAmount);
                            intent.putExtra("appliedVoucherPaymentType", appliedVoucherPaymentType);
                            startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                        }
                    }
                } catch (Exception e) {
                    Log.e("Exception", e.toString());
                }

                break;
            case R.id.tv_viewMap:
                try {
                    String strUri = "http://maps.google.com/maps?q=loc:" + mlat + "," + mlong + " (" + tvRestaurantAdddress.getText().toString() + ")";
                    Intent intent1 = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                    intent1.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent1);
                } catch (Exception e) {

                }
                break;
            case R.id.btn_ApplyVoucherCode:
                if (btnApplyVoucherCode.getTag().equals("apply")) {
                    if (coponcode.getText().toString().trim() != null && !coponcode.getText().toString().equalsIgnoreCase("")) {
                        tvVoucherStatus.setVisibility(View.VISIBLE);
                        if (Constants.isInternetConnectionAvailable(300)) {
                            getVoucherApply(coponcode.getText().toString());
                        } else {
                            dialogNoInternetConnection("Please check internet connection.", 3);
                        }


                    } else {
                        coponcode.requestFocus();
                        tvVoucherStatus.setVisibility(View.GONE);
                        Toast.makeText(mContext, "Please enter voucher code", Toast.LENGTH_SHORT).show();
                    }
                } else if (btnApplyVoucherCode.getTag().equals("remove")) {
                    coponcode.setText(null);
                    coponcode.setEnabled(true);
                    btnApplyVoucherCode.setTag("apply");
                    btnApplyVoucherCode.setText("Apply");
                    tvVoucherStatus.setVisibility(View.GONE);
                    tvdiscount.setText(mContext.getResources().getString(R.string.currency) + "0.00");
                    voucherDiscount = 0.d;
                    setPriceCalculation(totalCartIterm);
                }
                break;

            case R.id.tv_ChangeAddress:
                AddressDialogFragment addressDialogFragment = AddressDialogFragment.newInstance(mContext, true, new AddressDialogFragment.OnAddressDialogListener() {
                    @Override
                    public void onAddressDialogDismiss(Boolean isItem) {
                        if (isItem) {
                            llDeliveryAddress.setVisibility(View.VISIBLE);
                            tvDeliveryAddress.setText(sharedPreferencesClass.getString(sharedPreferencesClass.DEFAULT_ADDRESS));
                            tvChange.setText("Change Address");
                            /* Removing checkbox ....*/
                            /*if (chDeliverySameBilling.isChecked()) {
                                tvChangeBillingAddress.setVisibility(View.GONE);
                                tvBillingAdddress.setText(sharedPreferencesClass.getString(sharedPreferencesClass.DEFAULT_ADDRESS));
                            } else {
                                tvChangeBillingAddress.setVisibility(View.VISIBLE);
                                tvBillingAdddress.setText("");
                            }*/
                        }
                    }
                });
                addressDialogFragment.show(getFragmentManager(), "addressDialog");
                addressDialogFragment.setCancelable(false);
                break;
            case R.id.tv_ChangeBillingAddress:
                AddressDialogFragment addressDialogFragment2 = AddressDialogFragment.newInstance(mContext, false, new AddressDialogFragment.OnAddressDialogListener() {
                    @Override
                    public void onAddressDialogDismiss(Boolean isItem) {
                        if (isItem) {
                            tvChangeBillingAddress.setText("Change Address");
                            tvBillingAdddress.setText(sharedPreferencesClass.getString(sharedPreferencesClass.BILLING_ADDRESS));
                        }
                    }
                });
                addressDialogFragment2.show(getFragmentManager(), "addressDialog");
                addressDialogFragment2.setCancelable(false);
                break;
            case R.id.ch_billAddress:
                if (chDeliverySameBilling.isChecked()) {
                    tvChangeBillingAddress.setVisibility(View.GONE);
                    tvBillingAdddress.setText(tvDeliveryAddress.getText().toString());
                    sharedPreferencesClass.setString(sharedPreferencesClass.BILLING_ADDRESS, tvDeliveryAddress.getText().toString());
                } else {
                    tvChangeBillingAddress.setVisibility(View.VISIBLE);
                    tvChangeBillingAddress.setText("Add Address");
                    tvBillingAdddress.setText("");
                }
                break;

            case R.id.ll_DeliveryTimeSlot:
                timeSlotDialogFragment = TimeSlotDialogFragment.newInstance(mContext, this);
                timeSlotDialogFragment.show(getFragmentManager(), "timeSlot");

                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        scroll.fullScroll(ScrollView.FOCUS_UP);

        /*Todo: Note pad validate here....*/
        if (sharedPreferencesClass.getString(sharedPreferencesClass.NOTEPAD) != null && sharedPreferencesClass.getString(sharedPreferencesClass.NOTEPAD).length() > 0) {
            btnaddNotePadEdit.setText("Edit");
            tvAddNoteData.setVisibility(View.VISIBLE);
            tvAddNoteData.setText(sharedPreferencesClass.getString(sharedPreferencesClass.NOTEPAD));

        } else {
            btnaddNotePadEdit.setText("Add");
            tvAddNoteData.setVisibility(View.GONE);
        }
        Log.e("onResume", "hehe...........");
//        getAddressList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onPause() {
        super.onPause();
        dialog.dismiss();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void alertDialogAllergy() {
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View mDialogView = factory.inflate(R.layout.popup_allergy, null);
        final AlertDialog allergyDialog = new AlertDialog.Builder(getActivity()).create();
        allergyDialog.setView(mDialogView);
        allergyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //   final TextView ok_tv = (TextView)  mDialogView.findViewById(R.id.okTv);
        TextView tvRestaurantNumber = mDialogView.findViewById(R.id.tv_restaurantNumber);
        tvRestaurantNumber.setText(getString(R.string.restaurant_call_details) + " " + restaurantPhoneNumber + ".");

        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                try {
                    if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 101);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", restaurantPhoneNumber, null));
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    Toast.makeText(mContext, "Call not available", Toast.LENGTH_SHORT).show();
                }
                allergyDialog.dismiss();
                //    dialog2.show();

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

    public void alertDialogEmptyBasket() {
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View mDialogView = factory.inflate(R.layout.bucket_is_empty, null);
        final AlertDialog emptyDialog = new AlertDialog.Builder(getActivity()).create();
        emptyDialog.setView(mDialogView);
        emptyDialog.setCancelable(false);
        emptyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                Constants.switchActivity(getActivity(), DashboardActivity.class);
                //  getActivity().finish();
                getActivity().overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                emptyDialog.dismiss();
                //    dialog2.show();
            }
        });
        emptyDialog.show();
    }

    public void alertDialogNote() {
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View mDialogView = factory.inflate(R.layout.note_to_resturent, null);
        final AlertDialog noteDialog = new AlertDialog.Builder(getActivity()).create();
        noteDialog.setView(mDialogView);
        noteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //   final TextView ok_tv = (TextView)  mDialogView.findViewById(R.id.okTv);
        final LinearLayout llNotePad = mDialogView.findViewById(R.id.llNotePad);
        final EditText notePadDetails = mDialogView.findViewById(R.id.desIdEt);
        final TextView tvCountText = mDialogView.findViewById(R.id.tv_countText);
        tvCountText.setText(notePadDetails.length() + "/" + "150");
        llNotePad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.hideKeyboard(getActivity(), v);
            }
        });

        if (sharedPreferencesClass.getString(sharedPreferencesClass.NOTEPAD) != null) {

            notePadDetails.setText(sharedPreferencesClass.getString(sharedPreferencesClass.NOTEPAD));
        }
        notePadDetails.setSelection(notePadDetails.getText().length());

        notePadDetails.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvCountText.setText(s.length() + "/" + "150");
//                sharedPreferencesClass.setString(sharedPreferencesClass.NOTEPAD, s.toString());
                if (s.length() == 150) {

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                sharedPreferencesClass.setString(sharedPreferencesClass.NOTEPAD, notePadDetails.getText().toString());
                if (sharedPreferencesClass.getString(sharedPreferencesClass.NOTEPAD) != null && sharedPreferencesClass.getString(sharedPreferencesClass.NOTEPAD).length() > 0) {
                    btnaddNotePadEdit.setText("Edit");
                    tvAddNoteData.setVisibility(View.VISIBLE);
                    tvAddNoteData.setText(sharedPreferencesClass.getString(sharedPreferencesClass.NOTEPAD));

                } else {
                    btnaddNotePadEdit.setText("Add");
                    tvAddNoteData.setVisibility(View.GONE);
                }
                alertDailogConfirm(getString(R.string.note_added_successfully));

                noteDialog.dismiss();
                //    dialog2.show();

            }
        });
        mDialogView.findViewById(R.id.cross_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                noteDialog.dismiss();
            }
        });

        noteDialog.show();
    }

    public void deliveryTimeSlotDialog(String message, List<String> items) {
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View mDialogView = factory.inflate(R.layout.delivery_time_slot_dialog, null);
        final AlertDialog noteDialog = new AlertDialog.Builder(getActivity()).create();
        noteDialog.setView(mDialogView);
        noteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvMessage = mDialogView.findViewById(R.id.message);
        tvMessage.setText(message);

        RecyclerView recyclerView = mDialogView.findViewById(R.id.recycler_Deliverylist);
        RecyclerLayoutManager layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, items);
//        recyclerView.setAdapter(adapter);

        noteDialog.show();
    }

    public void alertDailogConfirm(String message) {
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View mDialogView = factory.inflate(R.layout.addnote_success_dialog, null);
        final AlertDialog noteDialog = new AlertDialog.Builder(getActivity()).create();
        noteDialog.setView(mDialogView);
        noteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvMessage = mDialogView.findViewById(R.id.message);
        tvMessage.setText(message);
        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteDialog.dismiss();
            }
        });

        noteDialog.show();
    }

    public void alertDailogVoucher(String title, String message) {
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View mDialogView = factory.inflate(R.layout.pop_alert_voucher, null);
        final AlertDialog noteDialog = new AlertDialog.Builder(getActivity()).create();
        noteDialog.setView(mDialogView);

        noteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvTitle = mDialogView.findViewById(R.id.heading);
        TextView tvMessage = mDialogView.findViewById(R.id.message);

        tvTitle.setText(title);
        tvMessage.setText(message);

        mDialogView.findViewById(R.id.cross_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteDialog.dismiss();
            }
        });
        mDialogView.findViewById(R.id.edit_details_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteDialog.dismiss();
            }
        });

        noteDialog.show();
    }

    private void showPriceAndView() {
        totalPrice = 0d;
        totalCartIterm = 0;
        List<SpecialOffer> specialOffers = db.getSpecialOffer();
        List<MenuProduct> menuProducts = db.getMenuProduct();
        List<UpsellProduct> upsellProducts = db.getUpSellProducts();

        if (menuProducts != null && menuProducts.size() > 0) {
            Log.e("ANAND >>", menuProducts.toString());

            for (MenuProduct menuProduct : menuProducts) {
                int itemQty = menuProduct.getOriginalQuantity();
                totalCartIterm += itemQty;
                if (menuProduct.getMealProducts() != null) {
                    totalPrice += (menuProduct.getOriginalAmount1() * itemQty);
                    for (MealProduct mealProduct : menuProduct.getMealProducts()) {
                        if (mealProduct.getSelected()) {
                            if (mealProduct.getMenuProductSize() != null) {
                                for (MenuProductSize menuProductSize1 : mealProduct.getMenuProductSize()) {
                                    if (menuProductSize1.getSelected()) {
                                        if (menuProductSize1.getProductSizePrice() != null)
//                                            totalPrice += (itemQty * Double.parseDouble(menuProductSize1.getProductSizePrice()));
                                            for (SizeModifier sizeModifier : menuProductSize1.getSizeModifiers()) {
                                                if (sizeModifier.getModifierType().equalsIgnoreCase("free")) {
                                                    int maxAllowFree = sizeModifier.getMaxAllowedQuantity();
                                                    int free = 0;
                                                    for (int i = 0; i < sizeModifier.getModifier().size(); i++) {
                                                        if (free == maxAllowFree) {
                                                            int qty = Integer.parseInt(sizeModifier.getModifier().get(i).getOriginalQuantity());
                                                            qty = (qty * itemQty);
                                                            totalPrice += (qty * Double.parseDouble(sizeModifier.getModifier().get(i).getModifierProductPrice()));
                                                        } else {
                                                            int qty = Integer.parseInt(sizeModifier.getModifier().get(i).getOriginalQuantity());
                                                            if (qty >= maxAllowFree) {
                                                                int nQty = qty - maxAllowFree;
                                                                free = maxAllowFree;
                                                                qty = (nQty * itemQty);
                                                                totalPrice += (qty * Double.parseDouble(sizeModifier.getModifier().get(i).getModifierProductPrice()));
                                                            } else {
                                                                free++;
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    for (Modifier modifier : sizeModifier.getModifier()) {
                                                        int qty = Integer.parseInt(modifier.getOriginalQuantity());
                                                        qty = (qty * itemQty);
                                                        totalPrice += (qty * Double.parseDouble(modifier.getModifierProductPrice()));
                                                    }
                                                }
                                            }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (menuProduct.getMenuProductSize() != null) {
                        if (menuProduct.getMenuProductSize().size() == 0 && menuProduct.getProductModifiers().size() == 0) {
                            totalPrice += (itemQty * Double.parseDouble(menuProduct.getMenuProductPrice()));
                        } else {
                            if (menuProduct.getMenuProductSize().size() > 0) {
                                for (MenuProductSize menuProductSize1 : menuProduct.getMenuProductSize()) {
                                    if (menuProductSize1.getSelected()) {
                                        if (menuProductSize1.getProductSizePrice() != null)
                                            totalPrice += (itemQty * Double.parseDouble(menuProductSize1.getProductSizePrice()));
                                        for (SizeModifier sizeModifier : menuProductSize1.getSizeModifiers()) {
                                            if (sizeModifier.getModifierType().equalsIgnoreCase("free")) {
                                                int maxAllowFree = sizeModifier.getMaxAllowedQuantity();
                                                int free = 0;
                                                for (int i = 0; i < sizeModifier.getModifier().size(); i++) {
                                                    if (free == maxAllowFree) {
                                                        int qty = Integer.parseInt(sizeModifier.getModifier().get(i).getOriginalQuantity());
                                                        qty = (qty * itemQty);
                                                        totalPrice += (qty * Double.parseDouble(sizeModifier.getModifier().get(i).getModifierProductPrice()));
                                                    } else {
                                                        int qty = Integer.parseInt(sizeModifier.getModifier().get(i).getOriginalQuantity());
                                                        if (qty >= maxAllowFree) {
                                                            int nQty = qty - maxAllowFree;
                                                            free = maxAllowFree;
                                                            qty = (nQty * itemQty);
                                                            totalPrice += (qty * Double.parseDouble(sizeModifier.getModifier().get(i).getModifierProductPrice()));
                                                        } else {
                                                            free++;
                                                        }
                                                    }
                                                }
                                            } else {
                                                for (Modifier modifier : sizeModifier.getModifier()) {
                                                    int qty = Integer.parseInt(modifier.getOriginalQuantity());
                                                    qty = (qty * itemQty);
                                                    totalPrice += (qty * Double.parseDouble(modifier.getModifierProductPrice()));
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                totalPrice += (itemQty * Double.parseDouble(menuProduct.getMenuProductPrice()));
                            }
                            if (menuProduct.getProductModifiers().size() > 0) {

                                for (ProductModifier productModifier : menuProduct.getProductModifiers()) {

                                    if (productModifier.getModifierType().equalsIgnoreCase("free")) {

                                        int maxAllowFree = productModifier.getMaxAllowedQuantity();
                                        int free = 0;
                                        for (int i = 0; i < productModifier.getModifier().size(); i++) {
                                            if (free == maxAllowFree) {
                                                int qty = Integer.parseInt(productModifier.getModifier().get(i).getOriginalQuantity());
                                                qty = (qty * itemQty);
                                                totalPrice += (qty * Double.parseDouble(productModifier.getModifier().get(i).getModifierProductPrice()));
                                            } else {
                                                int qty = Integer.parseInt(productModifier.getModifier().get(i).getOriginalQuantity());
                                                if (qty > maxAllowFree) {
                                                    int nQty = qty - maxAllowFree;
                                                    free = maxAllowFree;
                                                    qty = (nQty * itemQty);
                                                    totalPrice += (qty * Double.parseDouble(productModifier.getModifier().get(i).getModifierProductPrice()));
                                                } else {
                                                    free++;
                                                }
                                            }
                                        }
                                    } else {
                                        for (Modifier modifier : productModifier.getModifier()) {
                                            int qty = Integer.parseInt(modifier.getOriginalQuantity());
                                            qty = (qty * itemQty);
                                            totalPrice += (qty * Double.parseDouble(modifier.getModifierProductPrice()));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            for (SpecialOffer item : specialOffers) {
                totalPrice += ((item.getQuantity() * Double.parseDouble(item.getOfferPrice())));
                totalCartIterm += item.getQuantity();
            }

            for (int i = 0; i < upsellProducts.size(); i++) {
                totalPrice += (Double.parseDouble(upsellProducts.get(i).getQuantity()) * upsellProducts.get(i).getProductPrice());
                totalCartIterm += Double.parseDouble(upsellProducts.get(i).getQuantity());
            }
//            int totalCartIterm = specialOffers.size() + menuProducts.size();
            Log.e("TOTAL PRICE >>", totalCartIterm + "," + totalPrice);
//            totalCartIterm = 0;
//            totalCartIterm = specialOffers.size() + menuProducts.size();
            setPriceCalculation(totalCartIterm);

        }

    }

    public void setPriceCalculation(int totalCartIterm) {
        subTotal.setText(String.format("%.2f", totalPrice));
        numberOfQty = mAdapter.getItemCount();

        if (orderType.equalsIgnoreCase("Please Select")) {
            llCollectionFromRestaurant.setVisibility(View.GONE);
            llDeliveryAddress.setVisibility(View.GONE);
            llBillingAddress.setVisibility(View.GONE);
            deliveryFees.setText("£" + String.format("%.2f", 0.00));
            netAmount = totalPrice;
        } else if (orderType.equalsIgnoreCase("Delivery")) {
            llCollectionFromRestaurant.setVisibility(View.GONE);
            llDeliveryAddress.setVisibility(View.VISIBLE);
            llBillingAddress.setVisibility(View.GONE);
            llDeliveryTimeSlot.setEnabled(true);
            if (sharedPreferencesClass.getString(sharedPreferencesClass.DEFAULT_ADDRESS) != null && !sharedPreferencesClass.getString(sharedPreferencesClass.DEFAULT_ADDRESS).isEmpty()) {
                tvChange.setText("Change Address");
                tvDeliveryAddress.setText(sharedPreferencesClass.getString(sharedPreferencesClass.DEFAULT_ADDRESS));
            } else {
                tvChange.setText("Add Address");
            }
            if (chDeliverySameBilling.isChecked()) {
                tvBillingAdddress.setText(sharedPreferencesClass.getString(sharedPreferencesClass.DEFAULT_ADDRESS));
                tvChangeBillingAddress.setVisibility(View.GONE);
            } else {
                tvChangeBillingAddress.setVisibility(View.VISIBLE);
                tvBillingAdddress.setText("");
            }
            if (totalPrice > Double.parseDouble(res.getData().getRestaurants().getFreeDelivery())) {
                deliveryFees.setText("£" + String.format("%.2f", 0.00));
                netAmount = totalPrice;
            } else {
                deliveryFees.setText("£" + String.format("%.2f", Double.parseDouble(res.getData().getRestaurants().getDeliveryCharge())));
                deliveryFeesAmt = Double.parseDouble(res.getData().getRestaurants().getDeliveryCharge());
                netAmount = totalPrice + Double.parseDouble(res.getData().getRestaurants().getDeliveryCharge());
            }
        } else if (orderType.equalsIgnoreCase("collection")) {
            llCollectionFromRestaurant.setVisibility(View.VISIBLE);
            llDeliveryAddress.setVisibility(View.GONE);
            llBillingAddress.setVisibility(View.GONE);
            llDeliveryTimeSlot.setEnabled(false);
//            tvBillingAdddress.setText(sharedPreferencesClass.getString(sharedPreferencesClass.BILLING_ADDRESS));
            deliveryFeesAmt = 0.0d;
            deliveryFees.setText("£" + String.format("%.2f", 0.00));
            netAmount = totalPrice;
        } else {
            llCollectionFromRestaurant.setVisibility(View.GONE);
            llDeliveryAddress.setVisibility(View.GONE);
            llBillingAddress.setVisibility(View.GONE);
            llDeliveryTimeSlot.setEnabled(false);
            deliveryFees.setText("£" + String.format("%.2f", 0.00));
            netAmount = totalPrice;
        }

        /*   *//*TODO: Voucher Apply Calculation*/
        if (coponcode.getText().toString().trim() != null && !coponcode.getText().toString().equalsIgnoreCase("")) {
            Log.e("-------------,", "---------------");
            if (voucherApplicableOn != null && voucherApplicableOn.contains(orderType.toLowerCase())) {
                if (voucherApplicableOn.contains(orderType.toLowerCase())) {
                    if (voucherType.equalsIgnoreCase("percentage")) {
                        if (totalPrice > minOrderValue) {
                            Double voucherCal = (netAmount * voucherValue) / 100;
                            appliedVoucherAmount = voucherCal;
                            netAmount = netAmount - voucherCal;
                            appliedVoucherCode = voucherCode;
                            appliedVoucherPaymentType = voucherValidOn;
//                         alertDailogVoucher("Voucher code has been accepted", "Congratulations!" + "\n" + getString(R.string.currency) + " " + String.format("%.2f", voucherCal) + " has been applied to your order.");
                            tvVoucherStatus.setVisibility(View.VISIBLE);
                            tvVoucherStatus.setText("Voucher Applied " + getString(R.string.currency) + " " + String.format("%.2f", voucherCal));
                            tvdiscount.setText(mContext.getResources().getString(R.string.currency) + " " + String.format("%.2f", voucherCal));
                            voucherDiscount = voucherCal;
                        } else {
//                alertDailogVoucher("Validate voucher", "Voucher applicable on minimum order value " + getString(R.string.currency) + String.format("%.2f", minOrderValue));
                            tvVoucherStatus.setVisibility(View.VISIBLE);
                            tvVoucherStatus.setText("Voucher applicable on minimum order value " + getString(R.string.currency) + String.format("%.2f", minOrderValue));

                        }
                    } else if (voucherType.equalsIgnoreCase("flat")) {
                        if (totalPrice >= voucherValue) {
                            Double voucherCal = netAmount - voucherValue;
                            appliedVoucherAmount = voucherCal;
                            appliedVoucherCode = voucherCode;
                            appliedVoucherPaymentType = voucherValidOn;
                            netAmount = voucherCal;
//                          alertDailogVoucher("Voucher code has been accepted", "Congratulations!" + "\n" + getString(R.string.currency) + " " + String.format("%.2f", voucherCal) + " has been applied to your order.");
                            tvVoucherStatus.setVisibility(View.VISIBLE);
                            tvVoucherStatus.setText("Voucher Applied " + getString(R.string.currency) + " " + String.format("%.2f", voucherValue));
                            tvdiscount.setText(mContext.getResources().getString(R.string.currency) + " " + String.format("%.2f", voucherValue));
                            voucherDiscount = voucherValue;
                        } else {
                            tvVoucherStatus.setVisibility(View.VISIBLE);
                            tvVoucherStatus.setText("Voucher applicable on " + getString(R.string.currency) + " " + voucherValue);
                        }
                    }
                } else {
//                    alertDailogVoucher("Validate voucher", "Voucher applicable on " + voucherApplicableOn);
                    tvVoucherStatus.setVisibility(View.VISIBLE);
                    tvVoucherStatus.setText("Voucher applicable on " + voucherApplicableOn);
                }
            } else {
//                    alertDailogVoucher("Validate voucher", "Voucher applicable on " + voucherApplicableOn);
                tvVoucherStatus.setVisibility(View.VISIBLE);
                tvVoucherStatus.setText("Voucher applicable on " + voucherApplicableOn);
            }

        } else {
            tvVoucherStatus.setVisibility(View.GONE);
        }
        Constants.MAX_LENGTH = totalCartIterm;
        totalCount.setText(String.valueOf(totalCartIterm));
        footerTotalCount.setText(String.valueOf(totalCartIterm));
        totalAmmount.setText(String.format("%.2f", netAmount));
        footerTotalAmount.setText(String.format("%.2f", netAmount));
    }

    VoucherApplyResponse.Data voucherData;

    public void getVoucherApply(final String voucher_code) {
        dialog.show();
        VoucherApplyInterface apiInterface = ApiClient.getClient(mContext).create(VoucherApplyInterface.class);
        VoucherApplyRequest request = new VoucherApplyRequest();
        request.setCustomer_id(sharedPreferencesClass.getString(sharedPreferencesClass.USER_ID));
        request.setRestaurant_id(res.getData().getRestaurants().getRestaurantId());
        request.setVoucher_code(voucher_code);
        Call<VoucherApplyResponse> call3 = apiInterface.voucherApply(request);
        call3.enqueue(new Callback<VoucherApplyResponse>() {
            @Override
            public void onResponse(Call<VoucherApplyResponse> call, Response<VoucherApplyResponse> response) {
                try {
                    dialog.hide();
                    if (response.body().getSuccess()) {

                        voucherData = response.body().getData();
                        /*TODO: Voucher Apply Calculation*/
                        coponcode.setEnabled(false);
                        btnApplyVoucherCode.setTag("remove");
                        btnApplyVoucherCode.setText("Remove");


                        if (voucherData.getVoucher_applicable_on().contains(orderType.toLowerCase())) {
                            if (voucherData.getVoucher_type().equalsIgnoreCase("percentage")) {
                                if (totalPrice >= Double.parseDouble(voucherData.getMinimum_order_value())) {
                                    Double voucherCal = (totalPrice * Double.parseDouble(voucherData.getVoucher_value())) / 100;
                                    alertDailogVoucher("Voucher code has been accepted", "Congratulations!" + "\n" + getString(R.string.currency) + " " + String.format("%.2f", voucherCal) + " has been applied to your order.");
                                } else {
                                    // call when minimum order value is greater
                                    alertDailogVoucher("Voucher code has been accepted", "This voucher is apply only Amount of\n" + getString(R.string.currency) + " " + String.format("%.2f", Double.parseDouble(voucherData.getMinimum_order_value())) + " OR grater");
                                    tvVoucherStatus.setVisibility(View.VISIBLE);
                                    tvVoucherStatus.setText("Voucher applicable on minimum order value " + getString(R.string.currency) + String.format("%.2f", minOrderValue));
                                }
                            } else if (voucherData.getVoucher_type().equalsIgnoreCase("flat")) {
                                if (totalPrice >= Double.parseDouble(voucherData.getVoucher_value())) {
                                    alertDailogVoucher("Voucher code has been accepted", "Congratulations!" + "\nDiscount of " + getString(R.string.currency) + "" + String.format("%.2f", voucherValue) + " has been applied to your order.");
                                }else {
                                    alertDailogVoucher("Voucher code has been accepted", "This voucher is applicable on minimum spend of " + getString(R.string.currency) + " " + voucherValue);
                                    tvVoucherStatus.setVisibility(View.VISIBLE);
                                    tvVoucherStatus.setText("Voucher applicable on " + voucherApplicableOn);
                                }
                            } else {
                                //call when voucher type no match
                                tvVoucherStatus.setVisibility(View.VISIBLE);
                                tvVoucherStatus.setText("Voucher Type Not Match");

                            }
                        } else {
                            //call when Voucher applicable on
                            tvVoucherStatus.setVisibility(View.VISIBLE);
                            tvVoucherStatus.setText("Voucher applicable on " + voucherData.getVoucher_applicable_on());
                        }


                        minOrderValue = Double.parseDouble(response.body().getData().getMinimum_order_value());
                        voucherApplicableOn = response.body().getData().getVoucher_applicable_on();
                        voucherType = response.body().getData().getVoucher_type();
                        voucherValue = Double.parseDouble(response.body().getData().getVoucher_value());
                        voucherCode = voucher_code;
                        voucherValidOn = response.body().getData().getVoucher_valid_on();
//                        setPriceCalculation(totalCartIterm);


                        /*if (Double.parseDouble(subTotal.getText().toString()) > minOrderValue) {
                            if (voucherApplicableOn.contains(orderType.toLowerCase())) {
                                *//*Todo: "percentage" *//*
                                if (voucherApplicableOn.contains(orderType.toLowerCase())) {
                                    if (voucherType.equalsIgnoreCase("percentage")) {
                                        if (totalPrice > minOrderValue) {
                                            Double voucherCal = (netAmount * voucherValue) / 100;
                                            appliedVoucherAmount = voucherCal;
                                            netAmount = netAmount - voucherCal;
                                            appliedVoucherCode = voucherCode;
                                            appliedVoucherPaymentType = voucherValidOn;
                                            alertDailogVoucher("Voucher code has been accepted", "Congratulations!" + "\n" + getString(R.string.currency) + " " + String.format("%.2f", voucherCal) + " has been applied to your order.");
                                            tvVoucherStatus.setVisibility(View.VISIBLE);
                                            tvVoucherStatus.setText("Voucher Applied " + getString(R.string.currency) + " " + String.format("%.2f", voucherCal));
                                            voucherCodeUsed = voucher_code;
                                        } else {
                                            alertDailogVoucher("Voucher code has been accepted", "This voucher is apply only Amount of\n" + getString(R.string.currency) + " " + minOrderValue + " OR grater");
                                            tvVoucherStatus.setVisibility(View.VISIBLE);
                                            tvVoucherStatus.setText("Voucher applicable on minimum order value " + getString(R.string.currency) + String.format("%.2f", minOrderValue));

                                        }
                                    } else if (voucherType.equalsIgnoreCase("flat")) {

                                        if (totalPrice >= voucherValue) {
                                            Double voucherCal = netAmount - voucherValue;
                                            appliedVoucherAmount = voucherCal;
                                            appliedVoucherCode = voucherCode;
                                            appliedVoucherPaymentType = voucherValidOn;
                                            netAmount = voucherCal;
                                            alertDailogVoucher("Voucher code has been accepted", "Congratulations!" + "\nDiscount of " + getString(R.string.currency) + "" + String.format("%.2f", voucherValue) + " has been applied to your order.");
                                            tvVoucherStatus.setVisibility(View.VISIBLE);
                                            tvVoucherStatus.setText("Voucher Applied " + getString(R.string.currency) + " " + String.format("%.2f", voucherCal));
                                            voucherCodeUsed = voucher_code;

                                        } else {
                                            alertDailogVoucher("Voucher code has been accepted", "This voucher is applicable on minimum spend of " + getString(R.string.currency) + " " + voucherValue);
                                            tvVoucherStatus.setVisibility(View.VISIBLE);
                                            tvVoucherStatus.setText("Voucher applicable on " + voucherApplicableOn);
                                        }
                                    }
                                } else {
                                    tvVoucherStatus.setVisibility(View.VISIBLE);
                                    tvVoucherStatus.setText("Voucher applicable on " + voucherApplicableOn);
                                }
                            } else {
//                    alertDailogVoucher("Validate voucher", "Voucher applicable on " + voucherApplicableOn);
                                tvVoucherStatus.setVisibility(View.VISIBLE);
                                tvVoucherStatus.setText("Voucher applicable on " + voucherApplicableOn);
                            }
                        } else {
//                alertDailogVoucher("Validate voucher", "Voucher applicable on minimum order value " + getString(R.string.currency) + String.format("%.2f", minOrderValue));
                            tvVoucherStatus.setVisibility(View.VISIBLE);
                            tvVoucherStatus.setText("Voucher applicable on minimum order value " + getString(R.string.currency) + String.format("%.2f", minOrderValue));

                        }*/
                        setPriceCalculation(totalCartIterm);
//                        tvVoucherStatus.setText();
                        /*if (Double.parseDouble(subTotal.getText().toString()) > minOrderValue) {
                            alertDailogVoucher("Voucher code has been accepted", "Congratulations!" + "\n" + getString(R.string.currency) + " " + String.format("%.2f", netAmount) + " has been applied to your order.");
                        }*/
                    } else {
                        dialog.hide();
                        alertDailogVoucher(response.body().getMessage(), "Unfortunately " + voucher_code + " is invalid. Please try again with a valid code.");
                        tvVoucherStatus.setVisibility(View.VISIBLE);
                        tvVoucherStatus.setText(response.body().getMessage());
                    }
                } catch (Exception e) {
                    dialog.hide();
                    alertDailogVoucher(response.body().getMessage(), "Unfortunately " + voucher_code + " is invalid. Please try again with a valid code.");

                }
            }

            @Override
            public void onFailure(Call<VoucherApplyResponse> call, Throwable t) {
                Log.e("ERROR 2>>", t.getMessage());
                dialog.hide();

            }
        });

    }

    public void getAddressList() {
        AddressListInterface apiInterface = ApiClient.getClient(mContext).create(AddressListInterface.class);
        AddressListRequest request = new AddressListRequest();
        request.setCustomerId(val.getLoginResponse().getData().getUserId());

        Call<AddressListResponse> call3 = apiInterface.mLogin(request);
        call3.enqueue(new Callback<AddressListResponse>() {
            @Override
            public void onResponse(Call<AddressListResponse> call, Response<AddressListResponse> response) {
                try {
                    if (response.body().getSuccess()) {
                        for (int i = 0; i < response.body().getData().getAddresses().size(); i++) {
                            if (response.body().getData().getAddresses().get(i).getIsDefault() == 1) {
                                if (response.body().getData().getAddresses().get(i).getIsDelivering() == 1) {
                                    String address = response.body().getData().getAddresses().get(i).getAddress1() + " " + response.body().getData().getAddresses().get(i).getAddress2() + "," + response.body().getData().getAddresses().get(i).getCity() + "\n" + response.body().getData().getAddresses().get(i).getPostCode();
                                    sharedPreferencesClass.setString(sharedPreferencesClass.DEFAULT_ADDRESS, address);
                                }
                            }
                        }


                    } else {

                    }
                } catch (Exception e) {
                    dialog.hide();

                }


            }

            @Override
            public void onFailure(Call<AddressListResponse> call, Throwable t) {

            }
        });
    }

    private void getUpSellProducts(List<String> product_id) {

        RestaurantDetailsInterface apiInterface = ApiClient.getClient(mContext).create(RestaurantDetailsInterface.class);
        UpSellsRequest request = new UpSellsRequest();
        request.setProduct_id(product_id);

        Call<UpSells> call3 = apiInterface.getUpsellProducts(request);
        call3.enqueue(new Callback<UpSells>() {
            @Override
            public void onResponse(Call<UpSells> call, Response<UpSells> response) {
                final Response<UpSells> mResponse = response;
                try {
                    if (mResponse != null && mResponse.body().getData().getUpsellsProducts().size() > 0) {
                        llRoomForMore.setVisibility(View.VISIBLE);
                        initViewRoom();
                        if (mRoomOrderAdapter != null) {
                            mRoomOrderAdapter.clearData();
                        }
                        mRoomOrderAdapter.addItem(mResponse.body().getData().getUpsellsProducts());
                    } else {
                        llRoomForMore.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    Log.e("Exception", e.getLocalizedMessage());
                }


            }

            @Override
            public void onFailure(Call<UpSells> call, Throwable t) {
                Log.e("ERROR 2>>", t.getMessage());
                llRoomForMore.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public void OnQuantityBtnClick() {
        if (db.getMenuProduct().size() == 0) {
            try {
                Intent i = new Intent(getContext(), RestaurantDetailsActivity.class);
                if (val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantId() != null && !val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantId().equalsIgnoreCase("")) {
                    i.putExtra("RESTAURANTID", val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantId());
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

                }
            } catch (NullPointerException e) {
                Log.e("NullPointerException", e.getLocalizedMessage());

            }
        }
        showPriceAndView();
    }

    @Override
    public void OnUpSellItemRemove() {
//        mUpSellProductAdapter.notifyDataSetChanged();
        initViewUpsell();
        initViewCart();
    }

    @Override
    public void OnOfferQuantityBtnClick() {
        showPriceAndView();
    }

    @Override
    public void OnUpSellQuantityBtnClick() {
        showPriceAndView();
        initViewUpsell();

    }


    @Override
    public void OnUpsellItemQuantityBtnClick() {
        mRoomOrderAdapter.notifyDataSetChanged();
        showPriceAndView();
    }

    public void getRestaurantDetails(final String rId) {

        final String resID = rId;
        RestaurantDetailsInterface apiInterface = ApiClient.getClient(mContext).create(RestaurantDetailsInterface.class);
        RestaurantDetailsRequest request = new RestaurantDetailsRequest();
//        request.setUserId(val.getLoginResponse().getData().getUserId());
        request.setUserId(sharedPreferencesClass.getString(sharedPreferencesClass.USER_ID));
        request.setPostCode(sharedPreferencesClass.getPostalCode());
        request.setRestaurantId(resID);

        Call<NewRestaurantsDetailsResponse> call3 = apiInterface.mGetDetails(request);
        call3.enqueue(new Callback<NewRestaurantsDetailsResponse>() {
            @Override
            public void onResponse(Call<NewRestaurantsDetailsResponse> call, Response<NewRestaurantsDetailsResponse> response) {
                try {
                    dialog.dismiss();
                    if (response.body().getSuccess()) {
                        val.setRestaurantDetailsResponse(response.body());
                        res = response.body();

                        if (res.getData().getRestaurants().getRestaurantLogo() != null) {
                            Glide.with(getActivity())
                                    .load(res.getData().getRestaurants().getRestaurantLogo())
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(logo);
                        }
                        if (res.getData().getRestaurants().getRestaurantImage() != null) {
                            Glide.with(getActivity())
                                    .load(res.getData().getRestaurants().getRestaurantImage())
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(backImage);
                        }

                        restuarantOpenStatus = response.body().getData().getRestaurants().getStatus();
                        if (restuarantOpenStatus.equalsIgnoreCase("closed")) {
                            isPreOrder = true;
                        }

                        sharedPreferencesClass.setString(sharedPreferencesClass.RESTAURANT_NAME_SLUG, response.body().getData().getRestaurants().getRestaurantSlug());
                        restaurantName.setText(res.getData().getRestaurants().getRestaurantName());
                        tvRestaurantAdddress.setText(res.getData().getRestaurants().getAddress());
                        mlat = Double.parseDouble(res.getData().getRestaurants().getLat());
                        mlong = Double.parseDouble(res.getData().getRestaurants().getLng());
                        restaurantCuisines.setText(res.getData().getRestaurants().getRestaurantCuisines());
                        restaurantDeliveryMinOrder.setText("£" + res.getData().getRestaurants().getDeliveryCharge() + " delivery  •  £" + res.getData().getRestaurants().getMinOrderValue() + " min order");
                        if (res.getData().getRestaurants().getAvgRating() != null) {
                            if (res.getData().getRestaurants().getAvgRating() == 0) {
                                imRatingImage.setVisibility(View.GONE);
                                restaurantRating.setText("New");

                            } else {
                                imRatingImage.setVisibility(View.VISIBLE);
                                restaurantRating.setText(String.format("%.1f", res.getData().getRestaurants().getAvgRating()));
                            }
                        } else {
                            imRatingImage.setVisibility(View.GONE);
                            restaurantRating.setText("New");

                        }
                        if (val.getRestaurantDetailsResponse() != null && !String.valueOf(val.getRestaurantDetailsResponse().getData().getRestaurants().getAvgDeliveryTime()).equalsIgnoreCase("")) {
                            deliveryTime.setText(val.getRestaurantDetailsResponse().getData().getRestaurants().getAvgDeliveryTime() + " min");
                            restaurantPhoneNumber = res.getData().getRestaurants().getPhoneNumber();
                            if (val.getRestaurantDetailsResponse() != null && val.getRestaurantDetailsResponse().getData().getRestaurants().getAvgDeliveryTime() != null && !String.valueOf(val.getRestaurantDetailsResponse().getData().getRestaurants().getAvgDeliveryTime()).equalsIgnoreCase("")) {
                                deliveryTime.setText(val.getRestaurantDetailsResponse().getData().getRestaurants().getAvgDeliveryTime() + " min");
                            } else {
                                deliveryTime.setText("0 min");
                            }
                            if (res.getData().getRestaurants().getDeliveryOptions() != null || !res.getData().getRestaurants().getDeliveryOptions().equals("")) {
                                String[] serve_styles = res.getData().getRestaurants().getDeliveryOptions().split(",");

                                int count = serve_styles.length;
                                paths = new String[count];
//                        paths[0] = "Please Select";

                                for (int i = 0; i < (serve_styles.length); i++) {
                                    paths[i] = serve_styles[(i)].substring(0, 1).toUpperCase() + serve_styles[(i)].substring(1);
                                    if (serve_styles[i].equalsIgnoreCase("delivery")) {
                                        deliveryPosition = i;
                                    }
                                }

                                if (Arrays.asList(serve_styles).contains("collection")) {
                                    ll_collection.setVisibility(View.VISIBLE);
                                    collection.setImageDrawable(getResources().getDrawable(R.drawable.open));
                                }
                                if (Arrays.asList(serve_styles).contains("delivery")) {
                                    ll_delivery.setVisibility(View.VISIBLE);
                                    delivery.setImageDrawable(getResources().getDrawable(R.drawable.open));
                                }
                                if (Arrays.asList(serve_styles).contains("dinein")) {
                                    ll_dinein.setVisibility(View.VISIBLE);
                                    dine_in.setImageDrawable(getResources().getDrawable(R.drawable.open));
                                }
                            }
                            spinnerCall(deliveryPosition);
                            if (sharedPreferencesClass.getString(sharedPreferencesClass.NOTEPAD) != null && !sharedPreferencesClass.getString(sharedPreferencesClass.NOTEPAD).equalsIgnoreCase("")) {
                                btnaddNotePadEdit.setText("Edit");
                                tvAddNoteData.setVisibility(View.VISIBLE);
                                tvAddNoteData.setText(sharedPreferencesClass.getString(sharedPreferencesClass.NOTEPAD));

                            } else {
                                btnaddNotePadEdit.setText("Add");
                                tvAddNoteData.setVisibility(View.GONE);
                            }
                        }
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<NewRestaurantsDetailsResponse> call, Throwable t) {
                Log.e("ERROR 2>>", t.getMessage());
                dialog.dismiss();
            }
        });


    }

    @Override
    public void onDeliveryTimeSelect(String time) {

        if (!time.equalsIgnoreCase("")) {
            deliveryDate.setVisibility(View.VISIBLE);
            String date = time.substring(0, 10);
            String formatData = Constants.changeStringDateFormat(date, "yyyy-MM-dd", "dd-MM-yyyy");
            deliveryDate.setText((formatData != null) ? formatData : date);
            deliveryTime.setText(time.substring(11, time.length()));
            sharedPreferencesClass.setString(sharedPreferencesClass.DELIVERY_DATE_TIME, deliveryDate.getText().toString() + " " + deliveryTime.getText().toString().substring(7) + ":00");
            isPreOrder = false;
        } else {
            sharedPreferencesClass.setString(sharedPreferencesClass.DELIVERY_DATE_TIME, "");
        }
    }

    public void dialogNoInternetConnection(String message, final int status) {
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View mDialogView = factory.inflate(R.layout.addnote_success_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setView(mDialogView);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        final Animation animShake = AnimationUtils.loadAnimation(mContext, R.anim.shake);

        TextView tvMessage = mDialogView.findViewById(R.id.message);
        tvMessage.setText(message);
        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constants.isInternetConnectionAvailable(300)) {
                    alertDialog.dismiss();
                    switch (status) {
                        case 1:
                            getRestaurantDetails(sharedPreferencesClass.getString(sharedPreferencesClass.RESTUARANT_ID));
                            break;
                        case 2:
                            getUpSellProducts(productIdForUpsell);
                            break;
                        case 3:
                            getVoucherApply(coponcode.getText().toString());
                            break;
                    }

                } else mDialogView.findViewById(R.id.okTv).startAnimation(animShake);

            }
        });

        alertDialog.show();
    }
}
