package com.easyfoodcustomer.fragments;


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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.easyfoodcustomer.R;
import com.easyfoodcustomer.adapters.AdapterBasketOrderItems;
import com.easyfoodcustomer.adapters.RecyclerLayoutManager;
import com.easyfoodcustomer.adapters.RoomOrderAdapter;
import com.easyfoodcustomer.adapters.TableTopAdapter;
import com.easyfoodcustomer.adapters.menu_adapter.MenuCartAdapter;
import com.easyfoodcustomer.adapters.menu_adapter.MenuSpecialOfferAdapter;
import com.easyfoodcustomer.adapters.menu_adapter.OnUpsellProductItemClick;
import com.easyfoodcustomer.adapters.menu_adapter.UpSellProductAdapter;
import com.easyfoodcustomer.add_address.AddAddressActivity;
import com.easyfoodcustomer.api.AddFavouritesInterface;
import com.easyfoodcustomer.api.AddressListInterface;
import com.easyfoodcustomer.api.VoucherApplyInterface;
import com.easyfoodcustomer.cart_db.DatabaseHelper;
import com.easyfoodcustomer.cart_model.final_cart.FinalNewCartDetails;
import com.easyfoodcustomer.dashboard.DashboardActivity;
import com.easyfoodcustomer.databinding.LayoutConfirmationDialogBinding;
import com.easyfoodcustomer.databinding.LayoutRestaurentDialogBinding;
import com.easyfoodcustomer.databinding.LayoutServestyleDialogBinding;
import com.easyfoodcustomer.dialogs.RestaurantOffersDialogFragment;
import com.easyfoodcustomer.dialogs.TimeSlotDialogFragment;
import com.easyfoodcustomer.model.AddressList;
import com.easyfoodcustomer.model.VoucherApplyRequest;
import com.easyfoodcustomer.model.VoucherApplyResponse;
import com.easyfoodcustomer.model.add_favourites_request.AddFavouristeResquest;
import com.easyfoodcustomer.model.add_favourites_response.AddFavouristeResponse;
import com.easyfoodcustomer.model.address_list_request.AddressDeliveryListRequest;
import com.easyfoodcustomer.model.address_list_response.AddressListResponse;
import com.easyfoodcustomer.model.landing_page_response.DiscountOffer;
import com.easyfoodcustomer.model.restaurant_offers.RestaurantOffersRequest;
import com.easyfoodcustomer.model.restaurant_offers.RestaurantOffersResponse;
import com.easyfoodcustomer.model.restaurant_offers.RestaurantSpecialOffers;
import com.easyfoodcustomer.modelsNew.CartProdctListModel;
import com.easyfoodcustomer.restaurant_details.RestaurantDetailsActivity;
import com.easyfoodcustomer.restaurant_details.api.RestaurantDetailsInterface;
import com.easyfoodcustomer.restaurant_details.model.new_restaurant_response.NewRestaurantsDetailsResponse;
import com.easyfoodcustomer.restaurant_details.model.request.RestaurantDetailsRequest;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.CartData;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MealProduct;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategoryCart;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuProductSize;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.Modifier;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.ProductModifier;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.SizeModifier;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.SpecialOffer;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.UpSells;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.UpsellProduct;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.request.UpSellsRequest;
import com.easyfoodcustomer.roomData.AppDatabase;
import com.easyfoodcustomer.roomData.AppExecutors;
import com.easyfoodcustomer.roomData.OrderSaveModel;
import com.easyfoodcustomer.select_payment_method.SelectPaymentMethodActivity;
import com.easyfoodcustomer.ui_new.MealCartAdapter;
import com.easyfoodcustomer.utility.ApiClient;
import com.easyfoodcustomer.utility.ApiInterface;
import com.easyfoodcustomer.utility.Constants;
import com.easyfoodcustomer.utility.GlobalValues;
import com.easyfoodcustomer.utility.Helper;
import com.easyfoodcustomer.utility.PrefManager;
import com.easyfoodcustomer.utility.SharedPreferencesClass;
import com.easyfoodcustomer.utility.TableInfoBean;
import com.easyfoodcustomer.utility.TableListListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

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

import static com.easyfoodcustomer.utility.Helper.isInternetOn;
import static com.easyfoodcustomer.utility.Helper.showSnackBar;
import static com.easyfoodcustomer.utility.SharedPreferencesClass.DELIVERY_MOBILE_NUMBER;
import static com.easyfoodcustomer.utility.SharedPreferencesClass.IS_FOR_TABLE;
import static com.easyfoodcustomer.utility.SharedPreferencesClass.OFFERR_DETAL_DFG;
import static com.easyfoodcustomer.utility.SharedPreferencesClass.SERVE_STYLE;
import static com.easyfoodcustomer.utility.SharedPreferencesClass.TABLE_NO;
import static com.easyfoodcustomer.utility.SharedPreferencesClass.TABLE_TYPE;
import static com.easyfoodcustomer.utility.SharedPreferencesClass.UNIT_ID;
import static com.easyfoodcustomer.utility.SharedPreferencesClass.UNIT_TYPE;
import static com.easyfoodcustomer.utility.UserContants.AUTH_TOKEN;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class MyCartFragment extends Fragment implements MenuCartAdapter.OnMenuCartItemClick, MenuSpecialOfferAdapter.OnMenuSpecialOfferItemClick, OnUpsellProductItemClick, UpSellProductAdapter.OnUpsellItemListClick, TimeSlotDialogFragment.OnDeliveryTimeSelectedListener, TableListListener, View.OnClickListener {

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
    @BindView(R.id.ll_dev)
    LinearLayout llDev;

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
    @BindView(R.id.ll_del)
    LinearLayout llDel;

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

    @BindView(R.id.ll_fortable)
    LinearLayout llForTable;

    @BindView(R.id.et_mobile)
    EditText etMobile;

    @BindView(R.id.ll_notable)
    LinearLayout llNotable;

    @BindView(R.id.table_ll)
    TextView tvTableLabel;
    @BindView(R.id.tbl_no)
    TextView tableno;

    @BindView(R.id.tv_change_table)
    TextView tvChangeTable;

    @BindView(R.id.serve_type)
    TextView serveType;

    @BindView(R.id.btn_change_serve)
    TextView btnChangeServe;


    @BindView(R.id.btn_change_tb_serve)
    TextView btnChangeServeTable;


    NewRestaurantsDetailsResponse res;
    private AdapterBasketOrderItems oAdapter;
    private GlobalValues val;
    private String selectString;
    private String addString;
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
    MealCartAdapter mealCartAdapter;
    MenuSpecialOfferAdapter mOfferAdapter;
    UpSellProductAdapter mUpSellProductAdapter;
    private String unitId, unitType, tableType, tableNumber;


    private List<TableInfoBean.DataBean.ServiceUnitsBean.UnitsBean> dropDownList;


    private boolean isPopup;
    private boolean isRoomPopup;
    private ListPopupWindow popupWindow;
    private ListPopupWindow roomPopupWindow;
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
    private String restaurentId;
    Double totalPrice = 0d;
    int totalCartIterm;
    int totalCartItermNew;
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
    List<TableInfoBean.DataBean> dataBeanList;
    //LayoutRestaurentDialogBinding dialogBinding;
    LayoutRestaurentDialogBinding dialogBinding;
    private String partnerID;

    private AppDatabase mDb;
    ArrayList<CartProdctListModel> cartProdctList;

    public MyCartFragment(Activity mActivity, Context mContext, boolean isFavorite) {
        this.mActivity = mActivity;
        this.mContext = mContext;
        this.isFavorite = isFavorite;
    }

    public MyCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_basket, container, false);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
        unbinder = ButterKnife.bind(this, view);
        mDb = AppDatabase.getInstance(getApplicationContext());
        recyclerviewOrderItems=view.findViewById(R.id.recyclerview_order_items);
        val = (GlobalValues) getActivity().getApplication();
        sharePre = new SharedPreferencesClass(getActivity());
        if (sharePre.getInt(IS_FOR_TABLE) == 1)
            orderType = "table";

        dataBeanList = new ArrayList<>();
        dropDownList = new ArrayList<>();

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


        if (sharedPreferencesClass.getString(DELIVERY_MOBILE_NUMBER) != null && !sharedPreferencesClass.getString(DELIVERY_MOBILE_NUMBER).isEmpty())
            etMobile.setText(sharedPreferencesClass.getString(DELIVERY_MOBILE_NUMBER));
        else
            etMobile.setText(val.getMobileNo());
       /* if (isInternetOn(getActivity()))
            getTables(false);*/


        etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                sharedPreferencesClass.setString(sharedPreferencesClass.DELIVERY_MOBILE_NUMBER, s.toString());
            }
        });

        scroll.fullScroll(ScrollView.FOCUS_UP);

        initViewCart();
        try {

        } catch (NullPointerException e) {
            lyContainer.setVisibility(View.GONE);
            alertDialogEmptyBasket();
        }
        // tvChangeTable

        DashboardActivity.getInstance().locationVisibility(false, "");

        // etMobile.setText();

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

        tvChangeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTables(false, false);
            }
        });
        return view;
    }


    public void setSpinnerForAddressList() {
        popupWindow = new ListPopupWindow(getActivity());
        final List<String> itemList = new ArrayList<>();
        for (int i = 0; i < addressList.size(); i++) {
            String address = addressList.get(i).getAddressOne() + " " + addressList.get(i).getAddressTwo() + " " + addressList.get(i).getCity() + " " + addressList.get(i).getPostCode() + " " + addressList.get(i).getCountry();
            itemList.add(address);
        }

        //Toast.makeText(val, "No deliverable address found!", Toast.LENGTH_SHORT).show();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, itemList);
        popupWindow.setAdapter(adapter);
        popupWindow.setAnchorView(rlCat);

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

        orderType = serveType.getText().toString().trim();
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

        mDb.saveOrderHistry().loadAllHistory().observe(getActivity(), new androidx.lifecycle.Observer<List<OrderSaveModel>>() {
            @Override
            public void onChanged(@Nullable List<OrderSaveModel> orderSaveModelList) {

                if (orderSaveModelList.size() > 0) {
                    cartProdctList = new ArrayList<>();
                    for (int i = 0; i < orderSaveModelList.size(); i++) {
                        totalCartItermNew=totalCartItermNew+orderSaveModelList.get(i).getItemCount();
                        cartProdctList.add(new CartProdctListModel(orderSaveModelList.get(i).getId(),
                                orderSaveModelList.get(i).getItemCount(),
                                orderSaveModelList.get(i).getMealID(),
                                orderSaveModelList.get(i).getRestaurantID(),
                                orderSaveModelList.get(i).getMealName(),
                                orderSaveModelList.get(i).getMealPrice(),
                                orderSaveModelList.get(i).getVegType(),
                                orderSaveModelList.get(i).getMenuCategoryId(),
                                orderSaveModelList.get(i).getDescription(),
                                orderSaveModelList.get(i).getTotalAmoutOfMeal(),
                                orderSaveModelList.get(i).getData(),
                                false
                        ));
                    }


                    RecyclerLayoutManager layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
                    recyclerviewOrderItems.setLayoutManager(layoutManager);
                    mealCartAdapter = new MealCartAdapter(getApplicationContext(), MyCartFragment.this, cartProdctList);
                    recyclerviewOrderItems.setAdapter(mealCartAdapter);

                    getTotalPrice(orderSaveModelList);

                }else
                {
                    cartProdctList = new ArrayList<>();
                    RecyclerLayoutManager layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
                    recyclerviewOrderItems.setLayoutManager(layoutManager);
                    mealCartAdapter = new MealCartAdapter(getApplicationContext(), MyCartFragment.this, cartProdctList);
                    recyclerviewOrderItems.setAdapter(mealCartAdapter);
                }
                if (orderSaveModelList.size() > 0) {
                    dialog.show();
                    if (isInternetOn(getActivity())) {
                        getRestaurantDetails(sharedPreferencesClass.getString(sharedPreferencesClass.RESTUARANT_ID));

                        //getAddressList();
                    } else {
                        dialogNoInternetConnection("Please check internet connection.", 1);
                    }
                    if (coponcode.getText().toString().trim() == null && coponcode.getText().toString().equalsIgnoreCase("")) {
                        tvVoucherStatus.setVisibility(View.GONE);
                    }
                    lyContainer.setVisibility(View.VISIBLE);
                    if (sharedPreferencesClass.getString(SERVE_STYLE) != null) {
                        if (sharedPreferencesClass.getString(SERVE_STYLE).equals("delivery"))
                            serveType.setText("Delivery");
                        if (sharedPreferencesClass.getString(SERVE_STYLE).equals("collection"))
                            serveType.setText("Collection");
                        if (sharedPreferencesClass.getString(SERVE_STYLE).equals("dine_in")) {
                            sharedPreferencesClass.setInt(IS_FOR_TABLE, 1);
                            serveType.setText("Dine In");
                            getTables(false, true);
                        }
                    }

                } else {
                    lyContainer.setVisibility(View.GONE);
                    alertDialogEmptyBasket();
                }

                productIdForUpsell = new ArrayList<>();
                for (int i = 0; i < orderSaveModelList.size(); i++) {
                    productIdForUpsell.add(orderSaveModelList.get(i).getMealID());
                }
                if (productIdForUpsell.size() > 0) {
                    if (isInternetOn(getActivity())) {
                        getUpSellProducts(productIdForUpsell);
                    } else {
                        dialogNoInternetConnection("Please check internet connection.", 2);
                    }

                }
                initViewSpecialOffer();


                initViewUpsell();
                showPriceAndView();
                if (sharedPreferencesClass.getInt(IS_FOR_TABLE) == 1) {
                    // getTables(false);

                    llDev.setVisibility(View.GONE);
                    //favourites.setVisibility(View.GONE);
                    restaurantDeliveryMinOrder.setVisibility(View.GONE);
                    tvDistance.setVisibility(View.GONE);
                    llNotable.setVisibility(View.GONE);
                    llDel.setVisibility(View.GONE);


                    if (sharedPreferencesClass.getString(TABLE_NO) != null) {
                        tvTableLabel.setText(sharedPreferencesClass.getString(TABLE_TYPE));
                        tvChangeTable.setText("Change");
                        tableno.setText(sharedPreferencesClass.getString(TABLE_NO));
                        tableno.setVisibility(View.VISIBLE);
                        llForTable.setVisibility(View.VISIBLE);
                    } else {
                        tvTableLabel.setText("Add Table");
                        tvChangeTable.setText("ADD");
                        tableno.setVisibility(View.GONE);
                        llForTable.setVisibility(View.GONE);
                        // getTables(false);
                    }


                }

            }
        });


    }
    private double getTotalPrice(List<OrderSaveModel> orderSaveModelList) {

        double finalPrice = 0;

        for (int i=0;i<orderSaveModelList.size();i++)
        {
            finalPrice=finalPrice+Double.parseDouble(orderSaveModelList.get(i).getTotalAmoutOfMeal());
        }
        subTotal.setText(String.format("%.2f", finalPrice));
        totalPrice=finalPrice;
        setPriceCalculation(orderSaveModelList.size());
        return finalPrice;

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

      /*  if (sharedPreferencesClass.getInt(IS_FOR_TABLE) == 1 || sharedPreferencesClass.getString(SERVE_STYLE).equals("dine_in")) {
            if (sharedPreferencesClass.getString(TABLE_NO) == null || sharedPreferencesClass.getString(TABLE_NO).isEmpty())
                getTables(false, true);
        }
*/
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
            R.id.ch_billAddress, R.id.ll_DeliveryTimeSlot, R.id.tv_SeeOffers, R.id.rl_cat, R.id.btn_change_serve, R.id.btn_change_tb_serve})
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
                if (addressList != null && addressList.size() > 0) {
                    setSpinnerForAddressList();
                } else {
                    Toast.makeText(val, "No deliverable address found!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.add_more_item:
                try {
                    Intent i = new Intent(getContext(), RestaurantDetailsActivity.class);
                    if (val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantId() != null && !val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantId().equalsIgnoreCase("")) {
                        i.putExtra("RESTAURANTID", val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantId());
                        i.putExtra("IS_FROM_LOGIN", false);
                        i.putExtra("ServeStyle", sharedPreferencesClass.getString(SERVE_STYLE));
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                    }
                } catch (NullPointerException e) {
                    Log.e("NullPointerException", e.getLocalizedMessage());

                }
                break;
            case R.id.btn_checkout:
                if (TextUtils.isEmpty(etMobile.getText().toString().trim())) {
                    etMobile.setError("Please enter mobile number.");
                    etMobile.requestFocus();
                } else if (etMobile.getText().toString().trim().length() < 8) {
                    etMobile.setError("Please enter valid mobile number.");
                    etMobile.requestFocus();
                } else {
                    checkOut();
                }

                break;
            case R.id.tv_viewMap:
                try {
                    String strUri = "http://maps.google.com/maps?q=loc:" + mlat + "," + mlong + " (" + tvRestaurantAdddress.getText().toString() + ")";
                    Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(strUri));
                    intent1.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent1);
                } catch (Exception e) {

                }
                break;
            case R.id.btn_ApplyVoucherCode:
                if (btnApplyVoucherCode.getTag().equals("apply")) {
                    if (coponcode.getText().toString().trim() != null && !coponcode.getText().toString().equalsIgnoreCase("")) {
                        tvVoucherStatus.setVisibility(View.VISIBLE);
                        if (isInternetOn(getActivity())) {
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
                    totalCartIterm=totalCartItermNew;
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
                timeSlotDialogFragment = TimeSlotDialogFragment.newInstance(mContext, this, res.getData().getRestaurants().getDeliveryOptions(), false, serveType.getText().toString().trim());
                timeSlotDialogFragment.show(getFragmentManager(), "timeSlot");
                break;

            case R.id.tv_SeeOffers:
                offersDialogFragment = RestaurantOffersDialogFragment.newInstance(mContext, restaurantSpecialOffers);
                offersDialogFragment.show(getFragmentManager(), "offers");
                break;

            case R.id.btn_change_serve:
                confirmationDialog();
                //serveStylePopup(res.getData().getRestaurants().getDeliveryOptions(), restaurentId);

                break;

            case R.id.btn_change_tb_serve:
                confirmationDialog();
                //serveStylePopup(res.getData().getRestaurants().getDeliveryOptions(), restaurentId);

                break;
        }
    }

    private void checkOut() {

        if (sharedPreferencesClass.getInt(IS_FOR_TABLE) == 1) {
            if (sharedPreferencesClass.getString(TABLE_NO) != null) {
                Intent intent = new Intent(getContext(), SelectPaymentMethodActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("orderType", "table");
                intent.putExtra("PATNER_ID", partnerID);
                intent.putExtra("MOBILE_NUMBER", etMobile.getText().toString().trim());
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
                getTables(true, false);
            }


        } else {
            if (deliveryTime.getText().toString().contains("min")) {
                serveType.getText().toString().trim().toLowerCase();
                timeSlotDialogFragment = TimeSlotDialogFragment.newInstance(mContext, this, res.getData().getRestaurants().getDeliveryOptions(), true, serveType.getText().toString().trim());
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

                                        if (sharedPreferencesClass.getInt(IS_FOR_TABLE) == 1)
                                            intent.putExtra("orderType", "table");
                                        else
                                            intent.putExtra("orderType", serveType.getText().toString().trim().toLowerCase());

                                        intent.putExtra("deliveryCharge", deliveryFeesAmt);
                                        intent.putExtra("MOBILE_NUMBER", etMobile.getText().toString().trim());
                                        intent.putExtra("ORDER_TOTAL", netAmount);
                                        intent.putExtra("PATNER_ID", partnerID);

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

                                    timeSlotDialogFragment = TimeSlotDialogFragment.newInstance(mContext, this, res.getData().getRestaurants().getDeliveryOptions(), true, serveType.getText().toString().trim());
                                    timeSlotDialogFragment.show(getFragmentManager(), "timeSlot");
                                }
                            } else {
                                alertDailogConfirm("Order value must be greater than minimum order value.");
                            }
                        } else {
                            Intent intent = new Intent(getContext(), SelectPaymentMethodActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            if (sharedPreferencesClass.getInt(IS_FOR_TABLE) == 1)
                                intent.putExtra("orderType", "table");
                            else
                                intent.putExtra("orderType", orderType);
                            intent.putExtra("PATNER_ID", partnerID);
                            intent.putExtra("deliveryCharge", deliveryFeesAmt);
                            intent.putExtra("MOBILE_NUMBER", etMobile.getText().toString().trim());
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
        addressList.clear();
        getAddressList();


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
        totalCartIterm=totalCartItermNew;
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
            totalCartIterm=totalCartItermNew;
            setPriceCalculation(totalCartIterm);

        }

    }

    public void setPriceCalculation(int totalCartIterm) {
     //   subTotal.setText(String.format("%.2f", totalPrice));
        // numberOfQty = mAdapter.getItemCount();
        orderType = serveType.getText().toString().trim();
        if (subTotal.getText().toString()!=null && !subTotal.getText().toString().isEmpty() && !subTotal.getText().toString().equalsIgnoreCase(""))
        {
            totalPrice=Double.parseDouble(subTotal.getText().toString());
        }


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


           /* if (val.getRestaurantDetailsResponse().getData().getRestaurants().getStatus().trim().equalsIgnoreCase("closed")) {
                llDeliveryTimeSlot.setEnabled(true);

            } else {*/
            llDeliveryTimeSlot.setEnabled(true);
            //}


            if (sharedPreferencesClass.getString(sharedPreferencesClass.DEFAULT_ADDRESS) != null && !sharedPreferencesClass.getString(sharedPreferencesClass.DEFAULT_ADDRESS).isEmpty()) {
                tvChange.setText("Add a new delivery address");
                tvDeliveryAddress.setText(sharedPreferencesClass.getString(sharedPreferencesClass.DEFAULT_ADDRESS));
            } else {
                tvChange.setText("Add a new delivery address");
            }
            if (chDeliverySameBilling.isChecked()) {
                tvBillingAdddress.setText(sharedPreferencesClass.getString(sharedPreferencesClass.DEFAULT_ADDRESS));
                tvChangeBillingAddress.setVisibility(View.GONE);
            } else {
                tvChangeBillingAddress.setVisibility(View.VISIBLE);
                tvBillingAdddress.setText("");
            }

            if (res != null) {
                ////
                if (totalPrice > Double.parseDouble(res.getData().getRestaurants().getFreeDelivery())) {
                    deliveryFees.setText("£" + String.format("%.2f", 0.00));
                    netAmount = totalPrice;
                } else {
                    deliveryFees.setText("£" + String.format("%.2f", Double.parseDouble(res.getData().getRestaurants().getDeliveryCharge())));
                    deliveryFeesAmt = Double.parseDouble(res.getData().getRestaurants().getDeliveryCharge());
                    netAmount = totalPrice + Double.parseDouble(res.getData().getRestaurants().getDeliveryCharge());
                }
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

        Call<VoucherApplyResponse> call3 = apiInterface.voucherApply(PrefManager.getInstance(getActivity()).getPreference(AUTH_TOKEN, ""), request);
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
        AddressDeliveryListRequest request = new AddressDeliveryListRequest();
        request.setCustomerId(val.getLoginResponse().getData().getUserId());
        request.setRestaurant_id(sharedPreferencesClass.getString(sharedPreferencesClass.RESTUARANT_ID));

        Call<AddressListResponse> call3 = apiInterface.mLogin(PrefManager.getInstance(getActivity()).getPreference(AUTH_TOKEN, ""), request);
        call3.enqueue(new Callback<AddressListResponse>() {
            @Override
            public void onResponse(Call<AddressListResponse> call, Response<AddressListResponse> response) {
                try {
                    if (response.body().getSuccess()) {


                        for (int i = 0; i < response.body().getData().getAddresses().size(); i++) {
                            if (response.body().getData().getAddresses().get(i).getIsDelivering() == 1) {

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
                            }


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
                Log.e("print", "" + t.getMessage());
            }
        });
    }

    private void getUpSellProducts(List<String> product_id) {

        RestaurantDetailsInterface apiInterface = ApiClient.getClient(mContext).create(RestaurantDetailsInterface.class);
        UpSellsRequest request = new UpSellsRequest();
        request.setProduct_id(product_id);

        Call<UpSells> call3 = apiInterface.getUpsellProducts(PrefManager.getInstance(getActivity()).getPreference(AUTH_TOKEN, ""), request);
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

        Call<NewRestaurantsDetailsResponse> call3 = apiInterface.mGetDetails(PrefManager.getInstance(getActivity()).getPreference(AUTH_TOKEN, ""), request);
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
                        if (res.getData().getRestaurants().getDeliveryPartner() != null && !res.getData().getRestaurants().getDeliveryPartner().isEmpty())
                            partnerID = res.getData().getRestaurants().getDeliveryPartner();
                        else
                            partnerID = "";

                        sharedPreferencesClass.setString(sharedPreferencesClass.RESTAURANT_NAME_SLUG, response.body().getData().getRestaurants().getRestaurantSlug());
                        restaurantName.setText(res.getData().getRestaurants().getRestaurantName());
                        restaurentId = res.getData().getRestaurants().getRestaurantId();
                        if (res.getData().getRestaurants().getDistanceInMiles() == 0) {
                            tvDistance.setText("0 miles");
                        } else {
                            tvDistance.setText(String.valueOf(res.getData().getRestaurants().getDistanceInMiles()) + " miles");
                        }
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
                                } else {
                                    ll_collection.setVisibility(View.VISIBLE);
                                    collection.setImageDrawable(getResources().getDrawable(R.drawable.closed));
                                }
                                if (Arrays.asList(serve_styles).contains("delivery")) {
                                    ll_delivery.setVisibility(View.VISIBLE);
                                    delivery.setImageDrawable(getResources().getDrawable(R.drawable.ic_orage_tick));
                                } else {
                                    ll_delivery.setVisibility(View.VISIBLE);
                                    delivery.setImageDrawable(getResources().getDrawable(R.drawable.closed));
                                }
                                if (Arrays.asList(serve_styles).contains("dine_in")) {
                                    ll_dinein.setVisibility(View.VISIBLE);
                                    dine_in.setImageDrawable(getResources().getDrawable(R.drawable.ic_orage_tick));
                                } else {
                                    ll_dinein.setVisibility(View.VISIBLE);
                                    dine_in.setImageDrawable(getResources().getDrawable(R.drawable.closed));
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
                        if (sharedPreferencesClass.getInt(IS_FOR_TABLE) == 0)
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
                if (isInternetOn(getActivity())) {
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


        Call<RestaurantOffersResponse> call3 = apiInterface.getRestaurantOffers(PrefManager.getInstance(getActivity()).getPreference(AUTH_TOKEN, ""), request);
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
        Call<AddFavouristeResponse> call3 = apiInterface.mAddFavourites(PrefManager.getInstance(getActivity()).getPreference(AUTH_TOKEN, ""), request);
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

    private void getTables(final boolean isCheckout, final boolean isFirst) {
        if (isInternetOn(getActivity())) {

            if (dialog != null)
                dialog.show();

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("restaurant_id", sharedPreferencesClass.getString(sharedPreferencesClass.RESTUARANT_ID));
            ApiInterface apiInterface = ApiClient.getClient(getActivity()).create(ApiInterface.class);
            Call<TableInfoBean> call = apiInterface.getTableList(PrefManager.getInstance(getActivity()).getPreference(AUTH_TOKEN, ""), jsonObject);

            call.enqueue(new Callback<TableInfoBean>() {
                @Override
                public void onResponse(@NonNull Call<TableInfoBean> call, @NonNull Response<TableInfoBean> response) {
                    try {

                        //if (dialog != null)
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            TableInfoBean tableInfoBean = response.body();
                            if (tableInfoBean.isSuccess()) {
                                if (tableInfoBean.getData() != null && tableInfoBean.getData().size() > 0) {
                                    dataBeanList = tableInfoBean.getData();
                                    dropDownList = tableInfoBean.getData().get(0).getService_units().get(0).getUnits();
                                    openRestaurantSelection(isCheckout, isFirst);
                                } else {
                                    openRestaurantSelection(isCheckout, isFirst);
                                }
                            } else {
                                showSnackBar(getView(), tableInfoBean.getMessage());
                            }

                        }
                    } catch (Exception e) {
                        Log.e("exception", "" + e.getMessage().toString());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TableInfoBean> call, @NonNull Throwable throwable) {
                    Log.e("prit", "" + throwable.getMessage().toString());
                    if (dialog != null)
                        dialog.dismiss();
                    showSnackBar(getView(), getString(R.string.msg_please_try_later));
                }
            });
        } else {
            showSnackBar(getView(), getString(R.string.no));

        }
    }


    public void openRestaurantSelection(final boolean isCheckout, final boolean isFirst) {
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_restaurent_dialog, null);
        dialogBinding = DataBindingUtil.bind(dialogView);
        final Dialog dialogg = new Dialog(getActivity());
        dialogg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogg.setContentView(dialogBinding.getRoot());
        dialogg.setCancelable(false);

        /*TableTopAdapter topAdapter = new TableTopAdapter(getActivity(), true, dataBeanList, null, this);
        dialogBinding.rvTop.setLayoutManager(new LinearLayoutManager(getActivity()));
        dialogBinding.rvTop.setAdapter(topAdapter);*/
        if (dataBeanList != null && dataBeanList.size() > 0) {
            selectString = "Select " + dataBeanList.get(0).getService_units().get(0).getServiceable_units();
            changeString();
            tableType = "Your " + dataBeanList.get(0).getService_units().get(0).getServiceable_units().trim() + " number is:";
            unitType = dataBeanList.get(0).getService_units().get(0).getServiceable_units().trim();
            setTopAdapter();
            setBottomAdapter(0);
            dialogBinding.rlRooms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isRoomPopup) {
                        roomPopupWindow.dismiss();
                        isRoomPopup = false;
                    } else {
                        setSpinnerForRoom();
                        isRoomPopup = true;
                    }
                }
            });
        }

        dialogBinding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogBinding.tvRooms.getText().toString().trim().isEmpty()) {
                    showSnackBar(dialogBinding.getRoot(), "Please select service");
                } else {
                    sharedPreferencesClass.setString(TABLE_NO, tableNumber);
                    sharedPreferencesClass.setString(TABLE_TYPE, tableType);
                    sharedPreferencesClass.setString(UNIT_ID, unitId);
                    sharedPreferencesClass.setString(UNIT_TYPE, unitType);
                    if (isCheckout) {
                        Intent intent = new Intent(getContext(), SelectPaymentMethodActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (sharedPreferencesClass.getInt(IS_FOR_TABLE) == 1)
                            intent.putExtra("orderType", "table");
                        else
                            intent.putExtra("orderType", orderType);
                        intent.putExtra("PATNER_ID", partnerID);
                        intent.putExtra("deliveryCharge", deliveryFeesAmt);
                        intent.putExtra("MOBILE_NUMBER", etMobile.getText().toString().trim());
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
                        changeTableInfo();

                    }
                    llForTable.setVisibility(View.VISIBLE);
                    llNotable.setVisibility(View.GONE);
                    dialogg.dismiss();
                }
            }
        });

        dialogBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogg.dismiss();
                if (isFirst) {
                    Intent intent = new Intent(getActivity(), RestaurantDetailsActivity.class);
                    intent.putExtra("RESTAURANTID", restaurentId);
                    intent.putExtra("ServeStyle", "dine_in");
                    startActivity(intent);
                }


            }
        });

        dialogg.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        dialogg.getWindow().setAttributes(lp);
        dialogg.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.seme_transparent)));
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.seme_transparent)));
    }

    @Override
    public void onTopItemClick(int position) {
        if (dataBeanList.get(position).getService_units() != null && dataBeanList.get(position).getService_units().size() > 0) {
            selectString = "Select " + dataBeanList.get(position).getService_units().get(0).getServiceable_units();
            tableType = "Your " + dataBeanList.get(position).getService_units().get(0).getServiceable_units().trim() + " number is:";
            unitType = dataBeanList.get(position).getService_units().get(0).getServiceable_units().trim();

            dropDownList = dataBeanList.get(position).getService_units().get(0).getUnits();
        } else {

        }
        changeString();
        setBottomAdapter(position);
        if (isRoomPopup) {
            roomPopupWindow.dismiss();
            isRoomPopup = false;
        }
    }

    @Override
    public void onBottomItemClick(int parentPos, int position) {
        selectString = "Select " + dataBeanList.get(parentPos).getService_units().get(position).getServiceable_units();
        tableType = "Your " + dataBeanList.get(parentPos).getService_units().get(position).getServiceable_units().trim() + " number is:";
        unitType = dataBeanList.get(parentPos).getService_units().get(position).getServiceable_units().trim();
        dropDownList = dataBeanList.get(parentPos).getService_units().get(position).getUnits();
        changeString();
        if (isRoomPopup) {
            roomPopupWindow.dismiss();
            isRoomPopup = false;
        }
    }

    private void setTopAdapter() {

        TableTopAdapter topAdapter = new TableTopAdapter(getActivity(), true, dataBeanList, null, this, 0);
        dialogBinding.rvTop.setLayoutManager(new LinearLayoutManager(getActivity()));
        dialogBinding.rvTop.setAdapter(topAdapter);
    }

    private void setBottomAdapter(int position) {
        TableTopAdapter bottomAdapter = new TableTopAdapter(getActivity(), false, null, dataBeanList.get(position).getService_units(), this, position);
        dialogBinding.rvBottom.setLayoutManager(new LinearLayoutManager(getActivity()));
        dialogBinding.rvBottom.setAdapter(bottomAdapter);
    }

    public void setSpinnerForRoom() {
        roomPopupWindow = new ListPopupWindow(getActivity());
        final List<String> itemList = new ArrayList<>();
        for (int i = 0; i < dropDownList.size(); i++) {
            itemList.add(dropDownList.get(i).getUnits_data());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, itemList);
        roomPopupWindow.setAdapter(adapter);
        roomPopupWindow.setAnchorView(dialogBinding.rlRooms);


        roomPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                unitId = dropDownList.get(position).getId();
                tableNumber = itemList.get(position);

                dialogBinding.tvRooms.setText(itemList.get(position));
                roomPopupWindow.dismiss();
                isRoomPopup = false;
            }
        });
        roomPopupWindow.show();
    }

    private void changeString() {

        dialogBinding.tvRooms.setHint(selectString);

    }

    private void changeTableInfo() {
        tvTableLabel.setText(tableType);
        tvChangeTable.setText("Change");
        tableno.setText(tableNumber);
        tableno.setVisibility(View.VISIBLE);
    }


    public void serveStylePopup(String deliverOption, final String currentRestId) {
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_servestyle_dialog, null);
        LayoutServestyleDialogBinding serveBinding = DataBindingUtil.bind(dialogView);
        final Dialog servedDialog = new Dialog(getActivity());
        servedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        servedDialog.setContentView(serveBinding.getRoot());
        servedDialog.setCancelable(false);
        final String[] serve_styles = deliverOption.split(",");

        if (Arrays.asList(serve_styles).contains("collection")) {
            if (getCollectionRestTimingInfo()) {
                serveBinding.tvCollection.setText("Collection (Pre Order)");
            }
            //res.getData().getRestaurants().getInfo().getTimings().
            //serveBinding.tvCollection.setVisibility(View.VISIBLE);
            serveBinding.rlCollection.setVisibility(View.VISIBLE);
        } else {
            serveBinding.rlCollection.setVisibility(View.GONE);
        }
        if (Arrays.asList(serve_styles).contains("delivery")) {
            if (getDeliveryRestTimingInfo()) {
                serveBinding.tvDelivery.setText("Delivery (Pre Order)");
            }
            serveBinding.rlDelivery.setVisibility(View.VISIBLE);

        } else {
            serveBinding.rlDelivery.setVisibility(View.GONE);
        }
        if (Arrays.asList(serve_styles).contains("dine_in")) {
            if (getDineRestTimingInfo()) {
                serveBinding.tvDineIn.setText("Dine In (Pre Order)");
            }
            serveBinding.rlDineIn.setVisibility(View.VISIBLE);
        } else {
            serveBinding.rlDineIn.setVisibility(View.GONE);
        }


        serveBinding.rlDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                servedDialog.dismiss();
                /*Intent intent = new Intent(getActivity(), RestaurantDetailsActivity.class);
                intent.putExtra("IS_FROM_LOGIN", false);
                intent.putExtra("RESTAURANTID", restaurentId);
                intent.putExtra("ServeStyle", "delivery");
               startActivity(intent);*/
                if (sharedPreferencesClass.getString(SERVE_STYLE).equals("delivery")) {
                    Intent i = new Intent(getActivity(), RestaurantDetailsActivity.class);
                    i.putExtra("RESTAURANTID", restaurentId);
                    i.putExtra("RESTAURANTNAME", res.getData().getRestaurants().getRestaurantName());
                    i.putExtra("ServeStyle", "delivery");
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            GlobalValues.getInstance().getDb().menuMaster().nuke();
                            GlobalValues.getInstance().getDb().menuProductMaster().nuke();
                            GlobalValues.getInstance().getDb().productSizeAndModifierMaster().nuke();
                            db.deleteCart();
                            sharePre.setString(sharePre.DEFAULT_ADDRESS, null);
                            sharePre.setString(sharePre.RESTUARANT_ID, restaurentId);
                            sharePre.setString(sharePre.DELIVERY_MOBILE_NUMBER, val.getMobileNo());
                            sharePre.setString(sharePre.RESTUARANT_NAME, res.getData().getRestaurants().getRestaurantName());
                            sharePre.setString(SERVE_STYLE, "delivery");
                            sharePre.setString(sharePre.NOTEPAD, "");
                            sharePre.setInt(IS_FOR_TABLE, 0);
                            Intent i = new Intent(getActivity(), RestaurantDetailsActivity.class);
                            i.putExtra("RESTAURANTID", restaurentId);
                            i.putExtra("RESTAURANTNAME", res.getData().getRestaurants().getRestaurantName());
                            i.putExtra("ServeStyle", "delivery");
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }
                    }).start();
                }
                 /*   serveType.setText("Delivery");
                if (sharedPreferencesClass.getString(SERVE_STYLE).equals("collection"))
                    serveType.setText("Collection");
                if (sharedPreferencesClass.getString(SERVE_STYLE).equals("dine_in")) {
                    serveType.setText("Dine In");
                *//*llNotable.setVisibility(View.GONE);
               // getTables(false);
                llForTable.setVisibility(View.VISIBLE);*//*
                }*/


            }
        });

        serveBinding.rlCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                servedDialog.dismiss();

                if (sharedPreferencesClass.getString(SERVE_STYLE).equals("collection")) {
                    Intent i = new Intent(getActivity(), RestaurantDetailsActivity.class);
                    i.putExtra("RESTAURANTID", restaurentId);
                    i.putExtra("RESTAURANTNAME", res.getData().getRestaurants().getRestaurantName());
                    i.putExtra("ServeStyle", "collection");
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            GlobalValues.getInstance().getDb().menuMaster().nuke();
                            GlobalValues.getInstance().getDb().menuProductMaster().nuke();
                            GlobalValues.getInstance().getDb().productSizeAndModifierMaster().nuke();
                            db.deleteCart();
                            sharePre.setString(sharePre.DEFAULT_ADDRESS, null);
                            sharePre.setString(sharePre.RESTUARANT_ID, restaurentId);
                            sharePre.setString(sharePre.DELIVERY_MOBILE_NUMBER, val.getMobileNo());
                            sharePre.setString(sharePre.RESTUARANT_NAME, res.getData().getRestaurants().getRestaurantName());
                            sharePre.setString(SERVE_STYLE, "collection");
                            sharePre.setString(sharePre.NOTEPAD, "");
                            sharePre.setInt(IS_FOR_TABLE, 0);
                            Intent i = new Intent(getActivity(), RestaurantDetailsActivity.class);
                            i.putExtra("RESTAURANTID", restaurentId);
                            i.putExtra("RESTAURANTNAME", res.getData().getRestaurants().getRestaurantName());
                            i.putExtra("ServeStyle", "collection");
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }
                    }).start();
                }
            }
        });
        serveBinding.rlDineIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                servedDialog.dismiss();
                if (sharedPreferencesClass.getString(SERVE_STYLE).equals("dine_in")) {
                    Intent i = new Intent(getActivity(), RestaurantDetailsActivity.class);
                    i.putExtra("RESTAURANTID", restaurentId);
                    i.putExtra("RESTAURANTNAME", res.getData().getRestaurants().getRestaurantName());
                    i.putExtra("ServeStyle", "dine_in");
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            GlobalValues.getInstance().getDb().menuMaster().nuke();
                            GlobalValues.getInstance().getDb().menuProductMaster().nuke();
                            GlobalValues.getInstance().getDb().productSizeAndModifierMaster().nuke();
                            db.deleteCart();
                            sharePre.setString(sharePre.DEFAULT_ADDRESS, null);
                            sharePre.setString(sharePre.RESTUARANT_ID, restaurentId);
                            sharePre.setString(sharePre.DELIVERY_MOBILE_NUMBER, val.getMobileNo());
                            sharePre.setString(sharePre.RESTUARANT_NAME, res.getData().getRestaurants().getRestaurantName());
                            sharePre.setString(SERVE_STYLE, "dine_in");
                            sharePre.setString(sharePre.NOTEPAD, "");
                            sharePre.setInt(IS_FOR_TABLE, 1);

                            Intent i = new Intent(getActivity(), RestaurantDetailsActivity.class);
                            i.putExtra("RESTAURANTID", restaurentId);
                            i.putExtra("RESTAURANTNAME", res.getData().getRestaurants().getRestaurantName());
                            i.putExtra("ServeStyle", "dine_in");
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }
                    }).start();
                }
            }
        });

        serveBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                servedDialog.dismiss();

            }
        });

        servedDialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        servedDialog.getWindow().setAttributes(lp);
        servedDialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.seme_transparent)));
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.seme_transparent)));
    }

    public void confirmationDialog() {
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_confirmation_dialog, null);
        LayoutConfirmationDialogBinding confirmBinding = DataBindingUtil.bind(dialogView);
        final Dialog confirmDialog = new Dialog(getActivity());
        confirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirmDialog.setContentView(confirmBinding.getRoot());
        confirmDialog.setCancelable(false);

        confirmBinding.tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog.dismiss();
            }
        });

        confirmBinding.tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog.dismiss();
                serveStylePopup(res.getData().getRestaurants().getDeliveryOptions(), restaurentId);
            }
        });


        confirmDialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        confirmDialog.getWindow().setAttributes(lp);
        confirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.seme_transparent)));
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.seme_transparent)));
    }


    private boolean getDineRestTimingInfo() {
        switch (Helper.getCurrentDay()) {
            case "Monday":
                return Helper.isPreOrder(res.getData().getRestaurants().getInfo().getTimings().getMonday().get(0).getOpeningStartTime(), res.getData().getRestaurants().getInfo().getTimings().getMonday().get(0).getOpeningEndTime());

            case "Tuesday":
                return Helper.isPreOrder(res.getData().getRestaurants().getInfo().getTimings().getTuesday().get(0).getOpeningStartTime(), res.getData().getRestaurants().getInfo().getTimings().getTuesday().get(0).getOpeningEndTime());

            case "Wednesday":
                return Helper.isPreOrder(res.getData().getRestaurants().getInfo().getTimings().getWednesday().get(0).getOpeningStartTime(), res.getData().getRestaurants().getInfo().getTimings().getWednesday().get(0).getOpeningEndTime());

            case "Thursday":
                return Helper.isPreOrder(res.getData().getRestaurants().getInfo().getTimings().getThursday().get(0).getOpeningStartTime(), res.getData().getRestaurants().getInfo().getTimings().getThursday().get(0).getOpeningEndTime());

            case "Friday":
                return Helper.isPreOrder(res.getData().getRestaurants().getInfo().getTimings().getFriday().get(0).getOpeningStartTime(), res.getData().getRestaurants().getInfo().getTimings().getFriday().get(0).getOpeningEndTime());

            case "Saturday":
                return Helper.isPreOrder(res.getData().getRestaurants().getInfo().getTimings().getSaturday().get(0).getOpeningStartTime(), res.getData().getRestaurants().getInfo().getTimings().getSaturday().get(0).getOpeningEndTime());

            case "Sunday":
                return Helper.isPreOrder(res.getData().getRestaurants().getInfo().getTimings().getSunday().get(0).getOpeningStartTime(), res.getData().getRestaurants().getInfo().getTimings().getSunday().get(0).getOpeningEndTime());

            default:
                return true;


        }


    }

    private boolean getDeliveryRestTimingInfo() {
        switch (Helper.getCurrentDay()) {
            case "Monday":
                return Helper.isPreOrder(res.getData().getRestaurants().getInfo().getTimings().getMonday().get(0).getDeliveryStartTime(), res.getData().getRestaurants().getInfo().getTimings().getMonday().get(0).getDeliveryEndTime());

            case "Tuesday":
                return Helper.isPreOrder(res.getData().getRestaurants().getInfo().getTimings().getTuesday().get(0).getDeliveryStartTime(), res.getData().getRestaurants().getInfo().getTimings().getTuesday().get(0).getDeliveryEndTime());

            case "Wednesday":
                return Helper.isPreOrder(res.getData().getRestaurants().getInfo().getTimings().getWednesday().get(0).getDeliveryStartTime(), res.getData().getRestaurants().getInfo().getTimings().getWednesday().get(0).getDeliveryEndTime());

            case "Thursday":
                return Helper.isPreOrder(res.getData().getRestaurants().getInfo().getTimings().getThursday().get(0).getDeliveryStartTime(), res.getData().getRestaurants().getInfo().getTimings().getThursday().get(0).getDeliveryEndTime());

            case "Friday":
                return Helper.isPreOrder(res.getData().getRestaurants().getInfo().getTimings().getFriday().get(0).getDeliveryStartTime(), res.getData().getRestaurants().getInfo().getTimings().getFriday().get(0).getDeliveryEndTime());

            case "Saturday":
                return Helper.isPreOrder(res.getData().getRestaurants().getInfo().getTimings().getSaturday().get(0).getDeliveryStartTime(), res.getData().getRestaurants().getInfo().getTimings().getSaturday().get(0).getDeliveryEndTime());

            case "Sunday":
                return Helper.isPreOrder(res.getData().getRestaurants().getInfo().getTimings().getSunday().get(0).getDeliveryStartTime(), res.getData().getRestaurants().getInfo().getTimings().getSunday().get(0).getDeliveryEndTime());

            default:
                return true;


        }
    }

    private boolean getCollectionRestTimingInfo() {
        switch (Helper.getCurrentDay()) {
            case "Monday":
                return Helper.isPreOrder(res.getData().getRestaurants().getInfo().getTimings().getMonday().get(0).getCollectionStartTime(), res.getData().getRestaurants().getInfo().getTimings().getMonday().get(0).getCollectionEndTime());

            case "Tuesday":
                return Helper.isPreOrder(res.getData().getRestaurants().getInfo().getTimings().getTuesday().get(0).getCollectionStartTime(), res.getData().getRestaurants().getInfo().getTimings().getTuesday().get(0).getCollectionEndTime());

            case "Wednesday":
                return Helper.isPreOrder(res.getData().getRestaurants().getInfo().getTimings().getWednesday().get(0).getCollectionStartTime(), res.getData().getRestaurants().getInfo().getTimings().getWednesday().get(0).getCollectionEndTime());

            case "Thursday":
                return Helper.isPreOrder(res.getData().getRestaurants().getInfo().getTimings().getThursday().get(0).getCollectionStartTime(), res.getData().getRestaurants().getInfo().getTimings().getThursday().get(0).getCollectionEndTime());

            case "Friday":
                return Helper.isPreOrder(res.getData().getRestaurants().getInfo().getTimings().getFriday().get(0).getCollectionStartTime(), res.getData().getRestaurants().getInfo().getTimings().getFriday().get(0).getCollectionEndTime());

            case "Saturday":
                return Helper.isPreOrder(res.getData().getRestaurants().getInfo().getTimings().getSaturday().get(0).getCollectionStartTime(), res.getData().getRestaurants().getInfo().getTimings().getSaturday().get(0).getCollectionEndTime());

            case "Sunday":
                return Helper.isPreOrder(res.getData().getRestaurants().getInfo().getTimings().getSunday().get(0).getCollectionStartTime(), res.getData().getRestaurants().getInfo().getTimings().getSunday().get(0).getCollectionEndTime());

            default:
                return true;


        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_remove:
                int removePos= (int) v.getTag();
                if (cartProdctList.get(removePos).getItemCount()>1)
                {
                    OrderSaveModel orderSaveModel=new OrderSaveModel(cartProdctList.get(removePos).getId(),
                            cartProdctList.get(removePos).getItemCount()-1,
                            cartProdctList.get(removePos).getMealID(),
                            cartProdctList.get(removePos).getRestaurantID(),
                            cartProdctList.get(removePos).getMealName(),
                            cartProdctList.get(removePos).getMealPrice(),
                            cartProdctList.get(removePos).getVegType(),
                            cartProdctList.get(removePos).getMenuCategoryId(),
                            cartProdctList.get(removePos).getDescription(),
                            String.valueOf((Double.parseDouble(cartProdctList.get(removePos).getTotalAmoutOfMeal())/cartProdctList.get(removePos).getItemCount())*(cartProdctList.get(removePos).getItemCount()-1)),
                            true,
                            cartProdctList.get(removePos).getData());
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mDb.saveOrderHistry().insertOrUpdate(orderSaveModel);
                        }
                    });
                }else
                {
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mDb.saveOrderHistry().deleteAllMessage(cartProdctList.get(removePos).getMealID());
                        }
                    });
                }

                break;

            case R.id.btn_add:
                int addPos= (int) v.getTag();
                OrderSaveModel orderSaveModel=new OrderSaveModel(cartProdctList.get(addPos).getId(),
                        cartProdctList.get(addPos).getItemCount()+1,
                        cartProdctList.get(addPos).getMealID(),
                        cartProdctList.get(addPos).getRestaurantID(),
                        cartProdctList.get(addPos).getMealName(),
                        cartProdctList.get(addPos).getMealPrice(),
                        cartProdctList.get(addPos).getVegType(),
                        cartProdctList.get(addPos).getMenuCategoryId(),
                        cartProdctList.get(addPos).getDescription(),
                        String.valueOf((Double.parseDouble(cartProdctList.get(addPos).getTotalAmoutOfMeal())/cartProdctList.get(addPos).getItemCount())*(cartProdctList.get(addPos).getItemCount()+1)),
                        true,
                        cartProdctList.get(addPos).getData());
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDb.saveOrderHistry().insertOrUpdate(orderSaveModel);
                    }
                });
                break;

            case R.id.ly_item:
                int counterPos= (int) v.getTag();
                if (cartProdctList!=null && cartProdctList.size()>0)
                {
                    if (cartProdctList.get(counterPos).isOpen())
                    {
                        cartProdctList.get(counterPos).setOpen(false);
                    }else
                    {
                        cartProdctList.get(counterPos).setOpen(true);
                    }

                }
                mealCartAdapter.notifyDataSetChanged();
                break;

            default:
                break;
        }
    }
}
