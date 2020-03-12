package com.lexxdigital.easyfooduserapps.fragments;


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
import android.support.v7.widget.ListPopupWindow;
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
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.adapters.AdapterBasketOrderItems;
import com.lexxdigital.easyfooduserapps.adapters.RecyclerLayoutManager;
import com.lexxdigital.easyfooduserapps.adapters.RoomOrderAdapter;
import com.lexxdigital.easyfooduserapps.adapters.menu_adapter.MenuCartAdapter;
import com.lexxdigital.easyfooduserapps.adapters.menu_adapter.MenuSpecialOfferAdapter;
import com.lexxdigital.easyfooduserapps.adapters.menu_adapter.OnUpsellProductItemClick;
import com.lexxdigital.easyfooduserapps.adapters.menu_adapter.UpSellProductAdapter;
import com.lexxdigital.easyfooduserapps.add_address.AddAddressActivity;
import com.lexxdigital.easyfooduserapps.api.AddFavouritesInterface;
import com.lexxdigital.easyfooduserapps.api.AddressListInterface;
import com.lexxdigital.easyfooduserapps.api.VoucherApplyInterface;
import com.lexxdigital.easyfooduserapps.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapps.cart_model.final_cart.FinalNewCartDetails;
import com.lexxdigital.easyfooduserapps.dashboard.DashboardActivity;
import com.lexxdigital.easyfooduserapps.dialogs.RestaurantOffersDialogFragment;
import com.lexxdigital.easyfooduserapps.dialogs.TimeSlotDialogFragment;
import com.lexxdigital.easyfooduserapps.model.AddressList;
import com.lexxdigital.easyfooduserapps.model.VoucherApplyRequest;
import com.lexxdigital.easyfooduserapps.model.VoucherApplyResponse;
import com.lexxdigital.easyfooduserapps.model.add_favourites_request.AddFavouristeResquest;
import com.lexxdigital.easyfooduserapps.model.add_favourites_response.AddFavouristeResponse;
import com.lexxdigital.easyfooduserapps.model.address_list_request.AddressListRequest;
import com.lexxdigital.easyfooduserapps.model.address_list_response.AddressListResponse;
import com.lexxdigital.easyfooduserapps.model.landing_page_response.DiscountOffer;
import com.lexxdigital.easyfooduserapps.model.restaurant_offers.RestaurantOffersRequest;
import com.lexxdigital.easyfooduserapps.model.restaurant_offers.RestaurantOffersResponse;
import com.lexxdigital.easyfooduserapps.model.restaurant_offers.RestaurantSpecialOffers;
import com.lexxdigital.easyfooduserapps.order_status.OrderStatusActivity;
import com.lexxdigital.easyfooduserapps.restaurant_details.RestaurantDetailsActivity;
import com.lexxdigital.easyfooduserapps.restaurant_details.api.RestaurantDetailsInterface;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.new_restaurant_response.NewRestaurantsDetailsResponse;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.request.RestaurantDetailsRequest;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.CartData;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MealProduct;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategoryCart;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuProductSize;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.Modifier;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.ProductModifier;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.SizeModifier;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.SpecialOffer;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.UpSells;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.UpsellProduct;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.request.UpSellsRequest;
import com.lexxdigital.easyfooduserapps.select_payment_method.SelectPaymentMethodActivity;
import com.lexxdigital.easyfooduserapps.utility.ApiClient;
import com.lexxdigital.easyfooduserapps.utility.Constants;
import com.lexxdigital.easyfooduserapps.utility.GlobalValues;
import com.lexxdigital.easyfooduserapps.utility.SharedPreferencesClass;

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

import static com.lexxdigital.easyfooduserapps.utility.SharedPreferencesClass.OFFERR_DETAL_DFG;

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
    @BindView(R.id.tv_distance)
    TextView tvDistance;
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

    @BindView(R.id.ll_DeliveryTimeSlot)
    LinearLayout llDeliveryTimeSlot;
    @BindView(R.id.btn_checkout)
    LinearLayout btnCheckout;
    @BindView(R.id.scroll)
    NestedScrollView scroll;
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

    @BindView(R.id.tv_currency)
    TextView tvCurrency;

    @BindView(R.id.rl_cat)
    RelativeLayout rlCat;
    @BindView(R.id.tv_cat)
    TextView tvCat;
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

    @BindView(R.id.favourites)
    ImageView favourites;

    @BindView(R.id.ll_Address)
    LinearLayout llDeliveryAddress;
    @BindView(R.id.tv_DeliveryAdddress)
    TextView tvDeliveryAddress;
    @BindView(R.id.tv_ChangeAddress)
    TextView tvChange;

    @BindView(R.id.llCount)
    LinearLayout llCount;


    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    @BindView(R.id.ll_BillingAddress)
    LinearLayout llBillingAddress;
    @BindView(R.id.tv_BillingAdddress)
    TextView tvBillingAdddress;
    @BindView(R.id.tv_ChangeBillingAddress)
    TextView tvChangeBillingAddress;
    @BindView(R.id.ch_billAddress)
    CheckBox chDeliverySameBilling;
    @BindView(R.id.tv_SeeOffers)
    TextView tvSeeOffers;

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

    private boolean isPopup;
    private ListPopupWindow popupWindow;
    private List<AddressList> addressList = new ArrayList<AddressList>();
    SharedPreferencesClass sharePre;
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
    private double minimumValue = 0.0;

    Double totalPrice = 0d;
    int totalCartIterm;
    Boolean voucherApplyStatus = false;
    String voucherCodeUsed = "";
    int deliveryPosition = -1;
    Double updateSubTotal = 0.0d;
    private List<String> productIdForUpsell;
    TimeSlotDialogFragment timeSlotDialogFragment;
    RestaurantOffersDialogFragment offersDialogFragment;
    Boolean isPreOrder = false;
    String restuarantOpenStatus;
    List<RestaurantSpecialOffers> restaurantSpecialOffers = null;
    FirebaseAnalytics mFirebaseAnalytics;
    private DiscountOffer discountOffer;
    private String PERCENTAGE_OFFERS = "discount_percentage";
    private String FLAT_OFFERS = "flat_offer";
    private boolean isFavorite;
    private boolean orderId;

    public MyBasketFragment(Activity mActivity, Context mContext, boolean isFavorite) {
        this.mActivity = mActivity;
        this.mContext = mContext;
        this.isFavorite = isFavorite;
    }

    public MyBasketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_basket, container, false);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
        unbinder = ButterKnife.bind(this, view);
        val = (GlobalValues) getActivity().getApplication();
        sharePre = new SharedPreferencesClass(getActivity());

        ///////////////////// get if any offer are selected from restaurant....
        String offer_detail = sharePre.getString(OFFERR_DETAL_DFG);
        discountOffer = new Gson().fromJson(offer_detail, DiscountOffer.class);

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
                    getAddressList();
                } else {
                    dialogNoInternetConnection("Please check internet connection.", 1);
                }
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


        DashboardActivity.getInstance().locationVisibility(false, "");

        if (isFavorite) {
            favourites.setBackground(mContext.getResources().getDrawable(R.drawable.favourite_active));
        } else {
            favourites.setBackground(mContext.getResources().getDrawable(R.drawable.favourite_white));
        }
        favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFavourites();
            }
        });
        return view;
    }


    public void setSpinnerForAddressList() {
        popupWindow = new ListPopupWindow(getActivity());
        final List<String> itemList = new ArrayList<>();
        for (int i = 0; i < addressList.size(); i++) {
            itemList.add(addressList.get(i).getAddressOne());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, itemList);
        popupWindow.setAdapter(adapter);
        popupWindow.setAnchorView(tvCat);

        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                tvCat.setText(itemList.get(position));

                setAddress(addressList.get(position));
                popupWindow.dismiss();
                isPopup = false;
            }
        });
        popupWindow.show();
    }


    public void setAddress(AddressList address) {
        if (address.getAddressTwo() != null && address.getAddressTwo().trim().length() > 0) {
            sharePre.setString(sharePre.DEFAULT_ADDRESS, address.getAddressOne() + ", " + address.getAddressTwo() + ", " + address.getCity() + "\n" + address.getPostCode());
        } else {
            sharePre.setString(sharePre.DEFAULT_ADDRESS, address.getAddressOne() + ", " + address.getCity() + "\n" + address.getPostCode());
        }
        sharePre.setString(sharePre.DELIVERY_ADDRESS_ID, address.getID());

    }


    void spinnerCall(int deliveryPosition) {
        List<String> wordList = new ArrayList<String>(Arrays.asList(paths));
        for (int i = 0; i < wordList.size(); i++) {
            if (wordList.get(i).equalsIgnoreCase("Dinein")) {
                wordList.remove(i);
            }
        }

        String[] newPaths = new String[wordList.size()];
        newPaths = wordList.toArray(newPaths);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, newPaths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (deliveryPosition != -1)
            spinner.setSelection(deliveryPosition);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                orderType = (String) parent.getItemAtPosition(position);
                sharedPreferencesClass.setString(sharedPreferencesClass.ORDER_TYPE, orderType.toLowerCase());
                sharedPreferencesClass.setString(sharedPreferencesClass.CUSTOMER_ID, orderType.toLowerCase());
                setPriceCalculation(totalCartIterm);
                if (voucherApplicableOn != null) {
                    if (voucherApplicableOn.contains(orderType.toLowerCase())) {
                        if (voucherType.equalsIgnoreCase(PERCENTAGE_OFFERS)) {
                            if (totalPrice >= minOrderValue) {
                                Double voucherCal = (netAmount * voucherValue) / 100;
                                alertDailogVoucher("Voucher code has been accepted", "Congratulations!" + "\n" + getString(R.string.currency) + " " + String.format("%.2f", netAmount) + " has been applied to your order.");
                            } else {
                                alertDailogVoucher("Voucher code has been accepted", "This voucher is applicable on minimum spend of " + getString(R.string.currency) + " " + String.format("%.2f", minOrderValue));
                            }
                        } else if (voucherType.equalsIgnoreCase(FLAT_OFFERS)) {
                            if (totalPrice >= minOrderValue) {
                                alertDailogVoucher("Voucher code has been accepted", "Congratulations!" + "\nDiscount of " + getString(R.string.currency) + "" + String.format("%.2f", voucherValue) + " has been applied to your order.");
                            } else {
                                alertDailogVoucher("Voucher code has been accepted", "This voucher is applicable on minimum spend of " + getString(R.string.currency) + " " + String.format("%.2f", minOrderValue));
                            }
                        }
                    } else {
                        alertDailogVoucher("Validate voucher", "Voucher applicable on " + voucherApplicableOn);
                    }
                }

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

        alertDialog.show();
    }


    private void initViewRoom() {
        mRoomOrderAdapter = new RoomOrderAdapter(mContext, this);
        RecyclerLayoutManager horizontalLayoutManagaer = new RecyclerLayoutManager(1, RecyclerLayoutManager.HORIZONTAL);
        roomorderListId.setLayoutManager(horizontalLayoutManagaer);
        roomorderListId.setAdapter(mRoomOrderAdapter);
    }


    private void initViewCart() {
        db = new DatabaseHelper(mContext);

        CartData data = db.getCartData();

        List<MenuProduct> cartMenu = new ArrayList<>();

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

    private void init() {
        if (Double.parseDouble(footerTotalAmount.getText().toString()) < minimumValue) {
            llBottom.setBackgroundColor(getResources().getColor(R.color.gray));
            llCount.setVisibility(View.GONE);
            tvCurrency.setVisibility(View.GONE);
            footerTotalAmount.setVisibility(View.GONE);
            checkOutTv.setText("Spend £" + String.format("%.2f", minimumValue - Double.parseDouble(footerTotalAmount.getText().toString())) + " more to checkout");

        } else {
            llBottom.setBackgroundColor(getResources().getColor(R.color.orange));
            llCount.setVisibility(View.VISIBLE);
            tvCurrency.setVisibility(View.VISIBLE);
            footerTotalAmount.setVisibility(View.VISIBLE);
            checkOutTv.setText(getResources().getString(R.string.checkout_now));

        }

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


    @OnClick({R.id.allergy_click, R.id.click_delivery_time_change, /*R.id.add_note,*/
            R.id.btn_addNoteEdit, R.id.add_more_item, R.id.btn_checkout, R.id.tv_viewMap,
            R.id.btn_ApplyVoucherCode, R.id.tv_ChangeAddress, R.id.tv_ChangeBillingAddress,
            R.id.ch_billAddress, R.id.ll_DeliveryTimeSlot, R.id.tv_SeeOffers, R.id.rl_cat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.allergy_click:
                alertDialogAllergy();
                break;
            case R.id.click_delivery_time_change:
                changeTime();
                break;
            case R.id.btn_addNoteEdit:
                alertDialogNote();
                break;
            case R.id.rl_cat:
                setSpinnerForAddressList();
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
                checkOut();
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
                    voucherApplicableOn = null;
                    setPriceCalculation(totalCartIterm);
                }
                break;

            case R.id.tv_ChangeAddress:
                startActivity(new Intent(getActivity(), AddAddressActivity.class));
            case R.id.ch_billAddress:
                if (chDeliverySameBilling.isChecked()) {
                    tvChangeBillingAddress.setVisibility(View.GONE);
                    tvBillingAdddress.setText(tvCat.getText().toString());
                    sharedPreferencesClass.setString(sharedPreferencesClass.BILLING_ADDRESS, tvCat.getText().toString());
                } else {
                    tvChangeBillingAddress.setVisibility(View.VISIBLE);
                    tvChangeBillingAddress.setText("Add Address");
                    tvBillingAdddress.setText("");
                }
                break;

            case R.id.ll_DeliveryTimeSlot:
                timeSlotDialogFragment = TimeSlotDialogFragment.newInstance(mContext, this, res.getData().getRestaurants().getDeliveryOptions(), false);
                timeSlotDialogFragment.show(getFragmentManager(), "timeSlot");
                break;

            case R.id.tv_SeeOffers:
                offersDialogFragment = RestaurantOffersDialogFragment.newInstance(mContext, restaurantSpecialOffers);
                offersDialogFragment.show(getFragmentManager(), "offers");
                break;
        }
    }

    private void checkOut() {

        if (deliveryTime.getText().toString().contains("min")) {
            timeSlotDialogFragment = TimeSlotDialogFragment.newInstance(mContext, this, res.getData().getRestaurants().getDeliveryOptions(), true);
            timeSlotDialogFragment.show(getFragmentManager(), "timeSlot");


        } else {
            try {

                if (orderType == null || orderType.equalsIgnoreCase("Please Select")) {
                    scroll.fullScroll(ScrollView.FOCUS_UP);
                    spinner.performClick();

                } else {
                    if (orderType.equalsIgnoreCase("Delivery")) {
                        if (totalPrice >= Double.parseDouble(res.getData().getRestaurants().getMinOrderValue())) {
                            if (!isPreOrder) {
                                if (tvCat.getText().toString().trim().length() != 0 && sharedPreferencesClass.getString(sharedPreferencesClass.DEFAULT_ADDRESS) != null && !sharedPreferencesClass.getString(sharedPreferencesClass.DEFAULT_ADDRESS).equals("")) {
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
                                    intent.putExtra(Constants.ORDER_TIME, deliveryTime.getText().toString());
                                    startActivity(intent);
                                    getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                                } else {
                                    alertDailogConfirm("Please select or add delivery address");

                                }
                            } else {
                                timeSlotDialogFragment = TimeSlotDialogFragment.newInstance(mContext, this, res.getData().getRestaurants().getDeliveryOptions(), true);
                                timeSlotDialogFragment.show(getFragmentManager(), "timeSlot");
                            }
                        } else {
                            alertDailogConfirm("Order value must be greater than minimum order value.");
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
                Constants.switchActivity(getActivity(), DashboardActivity.class);
                getActivity().overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                emptyDialog.dismiss();
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
        final LinearLayout llNotePad = mDialogView.findViewById(R.id.llNotePad);
        final EditText notePadDetails = mDialogView.findViewById(R.id.desIdEt);
        final TextView tvCountText = mDialogView.findViewById(R.id.tv_countText);
        tvCountText.setText(notePadDetails.length() + "/" + "240");
        llNotePad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.hideKeyboard(getActivity(), v);
            }
        });

        if (sharedPreferencesClass.getString(sharedPreferencesClass.NOTEPAD) != null) {

            notePadDetails.setText(sharedPreferencesClass.getString(sharedPreferencesClass.NOTEPAD));
            tvCountText.setText(notePadDetails.getText().toString().length() + "/" + "240");
        }
        notePadDetails.setSelection(notePadDetails.getText().length());

        notePadDetails.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvCountText.setText(s.length() + "/" + "240");
                if (s.length() == 240) {

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferencesClass.setString(sharedPreferencesClass.NOTEPAD, notePadDetails.getText().toString());
                if (sharedPreferencesClass.getString(sharedPreferencesClass.NOTEPAD) != null && sharedPreferencesClass.getString(sharedPreferencesClass.NOTEPAD).length() > 0) {
                    btnaddNotePadEdit.setText("Edit");
                    tvAddNoteData.setVisibility(View.VISIBLE);
                    tvAddNoteData.setText(sharedPreferencesClass.getString(sharedPreferencesClass.NOTEPAD));

                } else {
                    btnaddNotePadEdit.setText("Add");
                    tvAddNoteData.setVisibility(View.GONE);
                }
                noteDialog.dismiss();


            }
        });
        mDialogView.findViewById(R.id.cross_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteDialog.dismiss();
            }
        });

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
                                                    if (sizeModifier.getMaxAllowedQuantity() != 1) {
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


            if (val.getRestaurantDetailsResponse().getData().getRestaurants().getStatus().trim().equalsIgnoreCase("closed")) {
                llDeliveryTimeSlot.setEnabled(true);

            } else {
                llDeliveryTimeSlot.setEnabled(true);
            }


            if (sharedPreferencesClass.getString(sharedPreferencesClass.DEFAULT_ADDRESS) != null && !sharedPreferencesClass.getString(sharedPreferencesClass.DEFAULT_ADDRESS).isEmpty()) {
                tvChange.setText("Add new delivery address");
                tvDeliveryAddress.setText(sharedPreferencesClass.getString(sharedPreferencesClass.DEFAULT_ADDRESS));
            } else {
                tvChange.setText("Add new delivery address");
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

            llDeliveryTimeSlot.setEnabled(true);
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

            if (voucherApplicableOn != null && voucherApplicableOn.contains(orderType.toLowerCase())) {
                if (voucherApplicableOn.contains(orderType.toLowerCase())) {
                    if (voucherType.equalsIgnoreCase(PERCENTAGE_OFFERS)) {
                        if (totalPrice >= minOrderValue) {
                            Double voucherCal = (netAmount * voucherValue) / 100;
                            appliedVoucherAmount = voucherCal;
                            netAmount = netAmount - voucherCal;
                            appliedVoucherCode = voucherCode;
                            appliedVoucherPaymentType = voucherValidOn;
                            tvVoucherStatus.setVisibility(View.GONE);
                            tvVoucherStatus.setText(getString(R.string.currency) + " " + String.format("%.2f", voucherCal) + " voucher has been applied on " + appliedVoucherPaymentType + " payment.");
                            tvdiscount.setText(mContext.getResources().getString(R.string.currency) + " " + String.format("%.2f", voucherCal));
                            voucherDiscount = voucherCal;
                        } else {
                            tvdiscount.setText(mContext.getResources().getString(R.string.currency) + " " + String.format("%.2f", 0f));
                            tvVoucherStatus.setVisibility(View.GONE);
                            tvVoucherStatus.setText("Voucher applicable on minimum order value " + getString(R.string.currency) + String.format("%.2f", minOrderValue));

                        }
                    } else if (voucherType.equalsIgnoreCase(FLAT_OFFERS)) {
                        if (totalPrice >= minOrderValue) {
                            Double voucherCal = netAmount - voucherValue;
                            appliedVoucherAmount = voucherCal;
                            appliedVoucherCode = voucherCode;
                            appliedVoucherPaymentType = voucherValidOn;
                            netAmount = voucherCal;
                            tvVoucherStatus.setVisibility(View.GONE);
                            tvVoucherStatus.setText(getString(R.string.currency) + " " + String.format("%.2f", voucherValue) + " voucher has been applied on " + appliedVoucherPaymentType + " payment.");
                            tvdiscount.setText(mContext.getResources().getString(R.string.currency) + " " + String.format("%.2f", voucherValue));
                            voucherDiscount = voucherValue;
                        } else {
                            tvdiscount.setText(mContext.getResources().getString(R.string.currency) + " " + String.format("%.2f", 0f));
                            tvVoucherStatus.setVisibility(View.GONE);
                            tvVoucherStatus.setText("Voucher is applicable on minimum spend of " + getString(R.string.currency) + " " + minOrderValue);
                        }
                    }

                } else {
                    tvVoucherStatus.setVisibility(View.GONE);
                    tvVoucherStatus.setText("Voucher applicable on " + voucherApplicableOn);
                }

            } else {
                tvVoucherStatus.setVisibility(View.GONE);
                tvVoucherStatus.setText("Voucher applicable on " + voucherApplicableOn);
            }

        } else {
            tvVoucherStatus.setVisibility(View.GONE);
        }

        if (discountOffer != null) {

            if (totalPrice > Double.parseDouble(discountOffer.getMin_value())) {

                if (discountOffer.getOfferType().equalsIgnoreCase(PERCENTAGE_OFFERS)) {
                    double percentDisc = (netAmount * Double.parseDouble(discountOffer.getOfferPrice())) / 100;
                    netAmount = netAmount - percentDisc;
                    tvdiscount.setText(getString(R.string.pound) + String.format("%.2f", percentDisc));
                } else if (discountOffer.getOfferType().equalsIgnoreCase(FLAT_OFFERS) && totalPrice > Double.parseDouble(discountOffer.getMin_value())) {
                    double flatDisc = Double.parseDouble(discountOffer.getOfferPrice());
                    netAmount = netAmount - flatDisc;
                    tvdiscount.setText(getString(R.string.pound) + String.format("%.2f", flatDisc));
                }
            } else {
                tvdiscount.setText(getString(R.string.pound) + String.format("%.2f", 0f));
            }
        }
        totalCount.setText(String.valueOf(totalCartIterm));
        footerTotalCount.setText(String.valueOf(totalCartIterm));
        totalAmmount.setText(String.format("%.2f", netAmount));
        footerTotalAmount.setText(String.format("%.2f", netAmount));
        init();
    }

    VoucherApplyResponse.Data voucherData;

    public void getVoucherApply(final String voucher_code) {
        dialog.show();
        VoucherApplyInterface apiInterface = ApiClient.getClient(mContext).create(VoucherApplyInterface.class);
        VoucherApplyRequest request = new VoucherApplyRequest();
        request.setVoucher_code(voucher_code);
        request.setCustomer_id(sharedPreferencesClass.getString(sharedPreferencesClass.USER_ID));
        request.setRestaurant_id(res.getData().getRestaurants().getRestaurantId());
        request.setCart_subTotal(String.valueOf(netAmount));  //// check this key if not work

        Call<VoucherApplyResponse> call3 = apiInterface.voucherApply(request);
        call3.enqueue(new Callback<VoucherApplyResponse>() {
            @Override
            public void onResponse(Call<VoucherApplyResponse> call, Response<VoucherApplyResponse> response) {
                try {
                    dialog.hide();
                    if (response.body().getSuccess()) {
                        discountOffer = new DiscountOffer();
                        voucherData = response.body().getData();
                        /*TODO: Voucher Apply Calculation*/
                        coponcode.setEnabled(false);
                        btnApplyVoucherCode.setTag("remove");
                        btnApplyVoucherCode.setText("Remove");

                        minOrderValue = Double.parseDouble(response.body().getData().getMinimum_order_value());
                        voucherType = response.body().getData().getVoucher_type();
                        voucherValue = Double.parseDouble(response.body().getData().getVoucher_value());
                        voucherCode = voucher_code;
                        voucherValidOn = response.body().getData().getVoucher_valid_on();


                        if (voucherType.equalsIgnoreCase(PERCENTAGE_OFFERS)) {
                            if (totalPrice >= minOrderValue) {
                                Double voucherCal = (netAmount * voucherValue) / 100;
                                appliedVoucherAmount = voucherCal;
                                netAmount = netAmount - voucherCal;
                                appliedVoucherCode = voucherCode;
                                appliedVoucherPaymentType = voucherValidOn;
                                alertDailogVoucher("Voucher code has been accepted", "You got " + voucherValue + "% Off");
                                tvVoucherStatus.setVisibility(View.VISIBLE);
                                tvVoucherStatus.setText("Voucher Applied " + getString(R.string.currency) + " " + String.format("%.2f", voucherCal));
                                voucherCodeUsed = voucher_code;

                                discountOffer.setOfferType(voucherType);
                                discountOffer.setMin_value(String.valueOf(minOrderValue));
                                discountOffer.setOfferPrice(String.valueOf(voucherValue));

                                showPriceAndView();
                            } else {
                                alertDailogVoucher("Voucher code has been accepted", "This voucher is applicable on minimum spend of " + getString(R.string.currency) + " " + minOrderValue);
                                tvVoucherStatus.setVisibility(View.VISIBLE);
                                tvVoucherStatus.setText("Voucher applicable on minimum order value " + getString(R.string.currency) + String.format("%.2f", minOrderValue));
                            }
                        } else if (voucherType.equalsIgnoreCase(FLAT_OFFERS)) {

                            if (totalPrice >= minOrderValue) {
                                Double voucherCal = netAmount - voucherValue;
                                appliedVoucherAmount = voucherCal;
                                appliedVoucherCode = voucherCode;
                                appliedVoucherPaymentType = voucherValidOn;
                                netAmount = voucherCal;
                                alertDailogVoucher("Voucher code has been accepted", "You got  " + getString(R.string.currency) + "" + String.format("%.2f", voucherValue) + " Off.");
                                tvVoucherStatus.setVisibility(View.VISIBLE);
                                tvVoucherStatus.setText("Voucher Applied " + getString(R.string.currency) + " " + String.format("%.2f", voucherValue));
                                voucherCodeUsed = voucher_code;


                                discountOffer.setOfferType(voucherType);
                                discountOffer.setMin_value(String.valueOf(minOrderValue));
                                discountOffer.setOfferPrice(String.valueOf(voucherValue));

                                showPriceAndView();
                            } else {
                                alertDailogVoucher("Voucher code has been accepted", "This voucher is applicable on minimum spend of " + getString(R.string.currency) + " " + String.format("%.2f", minOrderValue));
                                tvVoucherStatus.setVisibility(View.VISIBLE);
                                tvVoucherStatus.setText("Voucher is applicable on minimum spend of " + voucherApplicableOn);
                            }
                        }


                        setPriceCalculation(totalCartIterm);
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

                            addressList.add(new AddressList(response.body().getData().getAddresses().get(i).getId(),
                                    response.body().getData().getAddresses().get(i).getCustomerId(),
                                    response.body().getData().getAddresses().get(i).getAddress1(),
                                    response.body().getData().getAddresses().get(i).getAddress2(),
                                    response.body().getData().getAddresses().get(i).getCity(),
                                    response.body().getData().getAddresses().get(i).getPostCode(),
                                    response.body().getData().getAddresses().get(i).getCountry(),
                                    ((response.body().getData().getAddresses().get(i).getAddressType().equals("")) ? "" : (response.body().getData().getAddresses().get(i).getAddressType().substring(0, 1).toUpperCase() + response.body().getData().getAddresses().get(i).getAddressType().substring(1))),
                                    response.body().getData().getAddresses().get(i).getIsDefault(),
                                    response.body().getData().getAddresses().get(i).getIsDelivering()));


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
                    db.deleteCart();
                    sharedPreferencesClass.setString(sharedPreferencesClass.RESTUARANT_ID, "");
                    sharedPreferencesClass.setString(sharedPreferencesClass.RESTUARANT_NAME, "");
                    sharedPreferencesClass.setString(sharedPreferencesClass.NOTEPAD, "");
                    sharedPreferencesClass.setString(sharedPreferencesClass.DEFAULT_ADDRESS, null);
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

                            Glide.with(getActivity()).load(res.getData().getRestaurants().getRestaurantLogo()).apply(new RequestOptions())
                                    .into(logo);
                        }
                        if (res.getData().getRestaurants().getRestaurantImage() != null) {
                            Glide.with(getActivity()).load(res.getData().getRestaurants().getRestaurantImage()).apply(new RequestOptions())

                                    .into(backImage);
                        }

                        restuarantOpenStatus = response.body().getData().getRestaurants().getStatus();
                        if (restuarantOpenStatus.equalsIgnoreCase("closed")) {
                            isPreOrder = true;
                        }


                        sharedPreferencesClass.setString(sharedPreferencesClass.RESTAURANT_NAME_SLUG, response.body().getData().getRestaurants().getRestaurantSlug());
                        restaurantName.setText(res.getData().getRestaurants().getRestaurantName());
                        tvDistance.setText(String.valueOf(res.getData().getRestaurants().getDistanceInMiles()) + " miles");
                        tvRestaurantAdddress.setText(res.getData().getRestaurants().getAddress());
                        mlat = Double.parseDouble(res.getData().getRestaurants().getLat());
                        mlong = Double.parseDouble(res.getData().getRestaurants().getLng());
                        restaurantCuisines.setText(res.getData().getRestaurants().getRestaurantCuisines());

                        restaurantDeliveryMinOrder.setText("£" + res.getData().getRestaurants().getDeliveryCharge() + " delivery  •  £" + res.getData().getRestaurants().getMinOrderValue() + " min order");
                        minimumValue = Double.parseDouble(res.getData().getRestaurants().getMinOrderValue());


                        init();

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
                        if (val.getRestaurantDetailsResponse().getData().getRestaurants().getAvgPreparationTime() != null && val.getRestaurantDetailsResponse().getData().getRestaurants().getAvgPreparationTime() > 0) {

                            sharedPreferencesClass.setString(sharedPreferencesClass.AVG_COLLECTION_TIME, String.valueOf(val.getRestaurantDetailsResponse().getData().getRestaurants().getAvgPreparationTime()));

                        } else {
                            sharedPreferencesClass.setString(sharedPreferencesClass.AVG_COLLECTION_TIME, null);
                        }

                        if (val.getRestaurantDetailsResponse() != null && !String.valueOf(val.getRestaurantDetailsResponse().getData().getRestaurants().getAvgDeliveryTime()).equalsIgnoreCase("")) {
                            deliveryTime.setText(val.getRestaurantDetailsResponse().getData().getRestaurants().getAvgDeliveryTime() + " min");
                            sharedPreferencesClass.setString(sharedPreferencesClass.DELIVERY_DATE_TIME, deliveryTime.getText().toString().trim());
                            restaurantPhoneNumber = res.getData().getRestaurants().getPhoneNumber();

                            if (res.getData().getRestaurants().getDeliveryOptions() != null || !res.getData().getRestaurants().getDeliveryOptions().equals("")) {
                                String[] serve_styles = res.getData().getRestaurants().getDeliveryOptions().split(",");

                                int count = serve_styles.length;
                                paths = new String[count];
                                for (int i = 0; i < (serve_styles.length); i++) {
                                    paths[i] = serve_styles[(i)].substring(0, 1).toUpperCase() + serve_styles[(i)].substring(1);
                                    if (serve_styles[i].equalsIgnoreCase("delivery")) {
                                        deliveryPosition = i;
                                    }
                                }

                                if (Arrays.asList(serve_styles).contains("collection")) {
                                    ll_collection.setVisibility(View.VISIBLE);
                                    collection.setImageDrawable(getResources().getDrawable(R.drawable.ic_orage_tick));
                                }
                                if (Arrays.asList(serve_styles).contains("delivery")) {
                                    ll_delivery.setVisibility(View.VISIBLE);
                                    delivery.setImageDrawable(getResources().getDrawable(R.drawable.ic_orage_tick));
                                }
                                if (Arrays.asList(serve_styles).contains("dinein")) {
                                    ll_dinein.setVisibility(View.VISIBLE);
                                    dine_in.setImageDrawable(getResources().getDrawable(R.drawable.ic_orage_tick));
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
                        /* Todo: Restaurant Offers API Call*/
                        getRestaurantOffers(rId);
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<NewRestaurantsDetailsResponse> call, Throwable t) {

                dialog.dismiss();
            }
        });


    }

    @Override
    public void onDeliveryTimeSelect(String time, String isTomorrow, String dateTimeString, String CollectionType, boolean isCheckOut, int orderTypePos) {


        if (!time.equalsIgnoreCase("")) {
            deliveryTime.setText(time);
            orderType = CollectionType;
            sharedPreferencesClass.setString(sharedPreferencesClass.ORDER_TYPE, CollectionType);
            sharedPreferencesClass.setString(sharedPreferencesClass.DELIVERY_DATE_TIME, dateTimeString);
            sharedPreferencesClass.setString(sharedPreferencesClass.IS_TOMORROW, isTomorrow);
            Log.e("Time Datestr", "" + dateTimeString);
            spinner.setSelection(orderTypePos);
            isPreOrder = false;
            if (isCheckOut) {
                checkOut();
            }
        } else {
            sharedPreferencesClass.setString(sharedPreferencesClass.DELIVERY_DATE_TIME, "");
            sharedPreferencesClass.setString(sharedPreferencesClass.IS_TOMORROW, isTomorrow);
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


    private void getRestaurantOffers(String restaurantId) {

        RestaurantDetailsInterface apiInterface = ApiClient.getClient(mContext).create(RestaurantDetailsInterface.class);
        RestaurantOffersRequest request = new RestaurantOffersRequest(restaurantId);


        Call<RestaurantOffersResponse> call3 = apiInterface.getRestaurantOffers(request);
        call3.enqueue(new Callback<RestaurantOffersResponse>() {
            @Override
            public void onResponse(Call<RestaurantOffersResponse> call, Response<RestaurantOffersResponse> response) {
                if (response.isSuccessful() && response.body().getSuccess()) {

                    restaurantSpecialOffers = response.body().getData().getRestaurantSpecialOffers();
                }

            }

            @Override
            public void onFailure(Call<RestaurantOffersResponse> call, Throwable t) {
                Log.e("ERROR 2>>", t.getMessage());

            }
        });
    }

    public void addFavourites() {

        AddFavouritesInterface apiInterface = ApiClient.getClient(mContext).create(AddFavouritesInterface.class);
        final AddFavouristeResquest request = new AddFavouristeResquest();
        request.setUserId(sharedPreferencesClass.getString(sharedPreferencesClass.USER_ID));
        request.setEntityId(val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantId());
        request.setEntityType("restaurant");
        Call<AddFavouristeResponse> call3 = apiInterface.mAddFavourites(request);
        call3.enqueue(new Callback<AddFavouristeResponse>() {
            @Override
            public void onResponse(Call<AddFavouristeResponse> call, Response<AddFavouristeResponse> response) {
                try {
                    if (response.body().getSuccess()) {
                        if (response.body().getData().getFavouriteStatus() == 1) {
                            favourites.setBackground(mContext.getResources().getDrawable(R.drawable.favourite_active));
                        } else if (response.body().getData().getFavouriteStatus() == 0) {
                            favourites.setBackground(mContext.getResources().getDrawable(R.drawable.favourite_white));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AddFavouristeResponse> call, Throwable t) {
            }
        });
    }
}
