package com.easyfoodcustomer.select_payment_method;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easyfoodcustomer.adapters.RecyclerLayoutManager;
import com.easyfoodcustomer.fragments.MyCartFragment;
import com.easyfoodcustomer.modelsNew.CartDatRequestNew;
import com.easyfoodcustomer.modelsNew.CartDataNew;
import com.easyfoodcustomer.modelsNew.CartProdctListModel;
import com.easyfoodcustomer.modelsNew.CheckoutModel;
import com.easyfoodcustomer.modelsNew.MealDetailsModel;
import com.easyfoodcustomer.order_details_activity.OrderDetailActivity;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuProductList;
import com.easyfoodcustomer.roomData.AppDatabase;
import com.easyfoodcustomer.roomData.OrderSaveModel;
import com.easyfoodcustomer.ui_new.MealCartAdapter;
import com.easyfoodcustomer.utility.Helper;
import com.easyfoodcustomer.utility.PrefManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.easyfoodcustomer.R;
import com.easyfoodcustomer.adapters.SaveCardAdapter;
import com.easyfoodcustomer.add_card.AddNewCardActivity;
import com.easyfoodcustomer.add_manual_address.AddAddressManualActivity;
import com.easyfoodcustomer.api.CardListInterface;
import com.easyfoodcustomer.cart_db.DatabaseHelper;
import com.easyfoodcustomer.model.card_list_request.CardListRequest;
import com.easyfoodcustomer.model.card_list_response.Card;
import com.easyfoodcustomer.model.card_list_response.CardListResponse;
import com.easyfoodcustomer.order_status.OrderStatusActivity;
import com.easyfoodcustomer.restaurant_details.RestaurantDetailsActivity;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.CartDatRequest;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.CartData;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MealProduct;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategoryCart;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.Modifier;
import com.easyfoodcustomer.select_payment_method.api.CheckoutRequestInterface;
import com.easyfoodcustomer.select_payment_method.model.checkout_request.CheckoutRequest;
import com.easyfoodcustomer.select_payment_method.model.checkout_response.CheckoutResponse;
import com.easyfoodcustomer.utility.ApiClient;
import com.easyfoodcustomer.utility.Constants;
import com.easyfoodcustomer.utility.GlobalValues;
import com.easyfoodcustomer.utility.SharedPreferencesClass;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.easyfoodcustomer.utility.Helper.formatDateTime;
import static com.easyfoodcustomer.utility.Helper.isInternetOn;
import static com.easyfoodcustomer.utility.SharedPreferencesClass.IS_FOR_TABLE;
import static com.easyfoodcustomer.utility.SharedPreferencesClass.SERVE_STYLE;
import static com.easyfoodcustomer.utility.SharedPreferencesClass.TABLE_NO;
import static com.easyfoodcustomer.utility.SharedPreferencesClass.TABLE_TYPE;
import static com.easyfoodcustomer.utility.UserContants.AUTH_TOKEN;
import static com.easyfoodcustomer.utility.UserContants.IS_OFFERED;
import static com.easyfoodcustomer.utility.UserContants.MIN_VALUE;
import static com.easyfoodcustomer.utility.UserContants.OFFER_ID;
import static com.easyfoodcustomer.utility.UserContants.OFFER_PRICE;
import static com.easyfoodcustomer.utility.UserContants.OFFER_TYPE;
import static com.easyfoodcustomer.utility.UserContants.POST_CODE_NEW;
import static com.facebook.FacebookSdk.getApplicationContext;

/*Line no 396*/
public class SelectPaymentMethodActivity extends AppCompatActivity implements SaveCardAdapter.PositionInterface {
    SharedPreferencesClass sharedPreferencesClass;
    @BindView(R.id.showCardList)
    RecyclerView showCardList;
    @BindView(R.id.paywith_card_tv)
    TextView paywithCardTv;
    @BindView(R.id.add_new_card)
    TextView addNewCard;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.pay_with_cash)
    TextView payWithCash;
    ArrayList<String> check = new ArrayList<>();
    SaveCardAdapter.PositionInterface mPositionInterface;
    SaveCardAdapter mDealCardAdapter;
    ArrayList<String> allReadyCheck = new ArrayList<>();
    boolean isCardSelected = false;
    List<Card> dataList = new ArrayList<>();
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.no_cards)
    RelativeLayout noCards;
    private com.stripe.android.model.Card card;
    private ProgressDialog mProgressDialog;
    private Dialog dialog;
    int position = 0;
    Card detail;
    private GlobalValues val;
    private Double totalAmount = 0.0d;
    private Double subTotalAmount = 0.0d;
    private String mobileNo;
    private Double deliveryFee = 0.0d;
    private String orderType;
    private Double voucherDiscount = 0.0d;
    private String notes;
    private Context mContext;
    private DatabaseHelper db;
    private String voucherCode;
    private Double voucherAmount = 0.0d;
    private String voucherPaymentType;

    private String address1 = "";
    private String address2 = "";
    private String addressCity = "";
    private String postalCode = "";
    private FirebaseAnalytics mFirebaseAnalytics;
    private String deliveryTime;
    private String deliveryPartnerId = "";

    private AppDatabase mDb;

    private CartDatRequestNew cartDatRequestNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_payment_method);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mDb = AppDatabase.getInstance(getApplicationContext());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Constants.setStatusBarGradiant(SelectPaymentMethodActivity.this);
        ButterKnife.bind(this);
        mContext = this;

        db = new DatabaseHelper(this);
        val = (GlobalValues) getApplicationContext();
        dialog = new Dialog(SelectPaymentMethodActivity.this);
        dialog.setTitle("");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sharedPreferencesClass = new SharedPreferencesClass(getApplicationContext());
        mProgressDialog = new ProgressDialog(SelectPaymentMethodActivity.this);
        mProgressDialog.requestWindowFeature(1);
        mProgressDialog.setMessage("Please wait");
        mProgressDialog.setCanceledOnTouchOutside(false);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            totalAmount = extras.getDouble("ORDER_TOTAL");
            mobileNo = extras.getString("MOBILE_NUMBER", "");
            deliveryPartnerId = extras.getString("PATNER_ID", "");
            subTotalAmount = extras.getDouble("ORDER_SUB_TOTAL");
            deliveryFee = extras.getDouble("deliveryCharge");
            orderType = extras.getString("orderType");
            voucherDiscount = extras.getDouble("voucherDiscount");
            notes = extras.getString("notes");
            voucherCode = extras.getString("appliedVoucherCode");
            voucherAmount = extras.getDouble("appliedVoucherAmount");
            voucherPaymentType = extras.getString("appliedVoucherPaymentType");

            deliveryTime = extras.getString(Constants.ORDER_TIME);


        }

        if (sharedPreferencesClass.getInt(sharedPreferencesClass.NUMBER_OF_ORDERS) > 0) {
            if (sharedPreferencesClass.getInt(SharedPreferencesClass.IS_FOR_TABLE) == 1) {
                payWithCash.setText("Pay at Restaurant");
                //addNewCard.setText("Pay by Card to Server");
            }
            if (deliveryPartnerId != null && !deliveryPartnerId.trim().isEmpty()) {
                payWithCash.setVisibility(View.GONE);
            } else {
                payWithCash.setVisibility(View.GONE);
                //payWithCash.setVisibility(View.VISIBLE);
            }


        } else {
            payWithCash.setVisibility(View.GONE);
        }

        if (isInternetOn(SelectPaymentMethodActivity.this)) {
            getCardList();
        } else {
            dialogNoInternetConnection("Please check internet connection.", 1);
        }


        findViewById(R.id.btn_backToMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(SelectPaymentMethodActivity.this, RestaurantDetailsActivity.class);
                    if (val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantId() != null && !val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantId().equalsIgnoreCase("")) {
                        i.putExtra("RESTAURANTID", val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantId());
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();

                }
            }
        });

        makeData2();
    }


    private void makeData2() {
        cartDatRequestNew = new CartDatRequestNew();
        try {
            // cartDatRequest.setCartData(db.getCartData());
            cartDatRequestNew.setRestaurantId(val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantId());
            cartDatRequestNew.setRestaurantName(val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantName());
            cartDatRequestNew.setPostCode(val.getRestaurantDetailsResponse().getData().getRestaurants().getPostCode());
            cartDatRequestNew.setTotalCartPrice(totalAmount);
            cartDatRequestNew.setOrderType(orderType.toLowerCase());
            cartDatRequestNew.setDeliveryCharge(deliveryFee);
            cartDatRequestNew.setMaxLength(String.valueOf(Constants.MAX_LENGTH));
            cartDatRequestNew.setVoucherDiscount(voucherDiscount);
            cartDatRequestNew.setVoucherCode(voucherCode);
            cartDatRequestNew.setRestaurantSlug(sharedPreferencesClass.getString(sharedPreferencesClass.RESTAURANT_NAME_SLUG));
            cartDatRequestNew.setIsTableBooking(sharedPreferencesClass.getInt(SharedPreferencesClass.IS_FOR_TABLE));
            cartDatRequestNew.setUnitType(sharedPreferencesClass.getString(SharedPreferencesClass.UNIT_TYPE));
            cartDatRequestNew.setUnitNumber(sharedPreferencesClass.getString(SharedPreferencesClass.TABLE_NO));
            cartDatRequestNew.setUnitId(sharedPreferencesClass.getString(SharedPreferencesClass.UNIT_ID));

            CartDataNew cartDataForRequest = new CartDataNew();
            CartData data = db.getCartData();
            cartDataForRequest.setSpecialOffers(data.getSpecialOffers());
            cartDataForRequest.setUpsellProducts(data.getUpsellProducts());

            Log.e("SIZE MODIFIER", cartDatRequestNew.toString());

            mDb.saveOrderHistry().loadAllHistory().observe(this, new androidx.lifecycle.Observer<List<OrderSaveModel>>() {
                @Override
                public void onChanged(@Nullable List<OrderSaveModel> orderSaveModelList) {

                    List<CheckoutModel> checkoutModelList = new ArrayList<>();

                    for (int i = 0; i < orderSaveModelList.size(); i++) {
                        CheckoutModel checkoutModel = new CheckoutModel();

                        checkoutModel.setId(0);
                        checkoutModel.setMenuId(0);
                        checkoutModel.setMenuCategoryId(orderSaveModelList.get(i).getMealID());
                        checkoutModel.setMenuCategoryName(orderSaveModelList.get(i).getMealName());


                        List<CheckoutModel.MenuProductsDTO> menuProductsDTOList = new ArrayList<>();

                        CheckoutModel.MenuProductsDTO menuProductsDTO = new CheckoutModel.MenuProductsDTO();

                        menuProductsDTO.setId(orderSaveModelList.get(i).getMealID());
                        menuProductsDTO.setMenuId(0);
                        menuProductsDTO.setMenuSubCategoryId(0);
                        menuProductsDTO.setAmount(Double.parseDouble(orderSaveModelList.get(i).getMealPrice()));
                        menuProductsDTO.setMenuProductPrice(Double.parseDouble(orderSaveModelList.get(i).getMealPrice()));
                        menuProductsDTO.setOriginalAmount1(Double.parseDouble(orderSaveModelList.get(i).getMealPrice()));
                        menuProductsDTO.setOriginalAmount(Double.parseDouble(orderSaveModelList.get(i).getMealPrice()));
                        menuProductsDTO.setProductName(orderSaveModelList.get(i).getMealName());
                        menuProductsDTO.setQuantity(orderSaveModelList.get(i).getItemCount());
                        menuProductsDTO.setOriginalQuantity(orderSaveModelList.get(i).getItemCount());
                        menuProductsDTO.setVegType(orderSaveModelList.get(i).getVegType());
                        menuProductsDTO.setMenuProductId(orderSaveModelList.get(i).getMealID());


                        List<CheckoutModel.MenuProductsDTO.MealProductsDTO> mealProducts = new ArrayList<>();
                        MealDetailsModel mealDetailsModel = new Gson().fromJson(orderSaveModelList.get(i).getData(), MealDetailsModel.class);

                        List<CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO.SizeModifierProductsDTO> list = new ArrayList<>();

                        if (mealDetailsModel != null) {
                            for (int j = 0; j < mealDetailsModel.getMeal_config().size(); j++) {

                                for (int k = 0; k < mealDetailsModel.getMeal_config().get(j).getProducts().size(); k++) {

                                    if (mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getNoOfCount() > 0) {

                                        if (mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().size() > 0) {
                                            CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO.SizeModifierProductsDTO sizeModifierProductsDTO = new CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO.SizeModifierProductsDTO();
                                            sizeModifierProductsDTO.setUnit("");

                                            sizeModifierProductsDTO.setQuantity(orderSaveModelList.get(i).getItemCount());
                                            sizeModifierProductsDTO.setProductId(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_id());
                                            sizeModifierProductsDTO.setProductName(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getProduct_name());
                                            if (mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getSelling_price() != null && !mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getSelling_price().trim().isEmpty()) {
                                                sizeModifierProductsDTO.setAmount(Double.parseDouble(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getSelling_price()));
                                                sizeModifierProductsDTO.setOriginalAmount1(Double.parseDouble(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getSelling_price()));
                                                sizeModifierProductsDTO.setModifierProductPrice(Double.parseDouble(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getSelling_price()));
                                            } else {
                                                if (mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_price() != null && !mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_price().trim().isEmpty()) {
                                                    sizeModifierProductsDTO.setOriginalAmount1(Double.parseDouble(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_price()));
                                                    sizeModifierProductsDTO.setModifierProductPrice(Double.parseDouble(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_price()));
                                                    sizeModifierProductsDTO.setAmount(Double.parseDouble(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_price()));
                                                } else {
                                                    sizeModifierProductsDTO.setOriginalAmount1(0.0);
                                                    sizeModifierProductsDTO.setModifierProductPrice(0.0);
                                                    sizeModifierProductsDTO.setAmount(0.0);
                                                }
                                            }

                                            sizeModifierProductsDTO.setOriginalQuantity(orderSaveModelList.get(i).getItemCount());
                                            if (mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size() != null && mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().size() > 0) {
                                                if (mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().get(0).getSize_modifiers() != null && mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().get(0).getSize_modifiers().size() > 0) {
                                                    sizeModifierProductsDTO.setMax_allowed_quantity(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().get(0).getSize_modifiers().get(0).getMax_allowed_quantity());
                                                }
                                            } else {

                                            }
                                            list.add(sizeModifierProductsDTO);

                                            for (int m = 0; m < mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().size(); m++) {
                                                for (int n = 0; n < mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().get(m).getSize_modifiers().size(); n++) {

                                                    for (int p = 0; p < mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().get(m).getSize_modifiers().get(n).getSize_modifier_products().size(); p++) {
                                                        MealDetailsModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean.SizeModifierProductsBean sizeModifierProductsBean = mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().get(m).getSize_modifiers().get(n).getSize_modifier_products().get(p);

                                                        if (sizeModifierProductsBean.getNoOfCount() > 0) {

                                                            CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO.SizeModifierProductsDTO sizeModifierProductsDTOOne = new CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO.SizeModifierProductsDTO();
                                                            sizeModifierProductsDTOOne.setUnit("other");
                                                            sizeModifierProductsDTOOne.setAmount(Double.parseDouble(sizeModifierProductsBean.getModifier_product_price()));
                                                            sizeModifierProductsDTOOne.setQuantity(sizeModifierProductsBean.getNoOfCount());
                                                            sizeModifierProductsDTOOne.setProductId(sizeModifierProductsBean.getProduct_id());
                                                            sizeModifierProductsDTOOne.setProductName(sizeModifierProductsBean.getProduct_name());
                                                            sizeModifierProductsDTOOne.setOriginalAmount1(Double.parseDouble(sizeModifierProductsBean.getModifier_product_price()));
                                                            sizeModifierProductsDTOOne.setModifierProductPrice(Double.parseDouble(sizeModifierProductsBean.getModifier_product_price()));
                                                            sizeModifierProductsDTOOne.setOriginalQuantity(sizeModifierProductsBean.getNoOfCount());
                                                            sizeModifierProductsDTOOne.setMax_allowed_quantity(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().get(m).getSize_modifiers().get(n).getMax_allowed_quantity());
                                                            list.add(sizeModifierProductsDTOOne);
                                                        }

                                                    }
                                                }
                                            }
                                        } else {
                                            CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO.SizeModifierProductsDTO sizeModifierProductsDTO = new CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO.SizeModifierProductsDTO();
                                            sizeModifierProductsDTO.setUnit("");

                                            sizeModifierProductsDTO.setQuantity(orderSaveModelList.get(i).getItemCount());
                                            sizeModifierProductsDTO.setProductId(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_id());
                                            sizeModifierProductsDTO.setProductName(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getProduct_name());

                                            if (mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getSelling_price() != null && !mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getSelling_price().trim().isEmpty()) {
                                                sizeModifierProductsDTO.setModifierProductPrice(Double.parseDouble(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getSelling_price()));
                                                sizeModifierProductsDTO.setOriginalAmount1(Double.parseDouble(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getSelling_price()));
                                                sizeModifierProductsDTO.setAmount(Double.parseDouble(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getSelling_price()));
                                            } else {
                                                sizeModifierProductsDTO.setModifierProductPrice(0.0);
                                                sizeModifierProductsDTO.setOriginalAmount1(0.0);
                                                sizeModifierProductsDTO.setAmount(0.0);
                                            }

                                            sizeModifierProductsDTO.setOriginalQuantity(orderSaveModelList.get(i).getItemCount());
                                            // sizeModifierProductsDTO.setMax_allowed_quantity(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getAllowed_quantity());
                                            sizeModifierProductsDTO.setMax_allowed_quantity(1);
                                            list.add(sizeModifierProductsDTO);
                                        }


                                    }
                                }

                            }

                        }

                        if (mealDetailsModel != null && isMealProduect(mealDetailsModel)) {
                            for (int j = 0; j < 1; j++) {
                                List<CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO> sizeModifiersDTOS = new ArrayList<>();
                                CheckoutModel.MenuProductsDTO.MealProductsDTO mealProductsDTO = new CheckoutModel.MenuProductsDTO.MealProductsDTO();
                                mealProductsDTO.setAmount(Double.parseDouble(orderSaveModelList.get(i).getMealPrice()));
                                mealProductsDTO.setQuantity(String.valueOf(mealDetailsModel.getMeal_config().get(j).getProduct_quantity()));
                                mealProductsDTO.setOroginalQuantity(String.valueOf(mealDetailsModel.getMeal_config().get(j).getProduct_quantity()));
                                mealProductsDTO.setOriginalAmount(Double.parseDouble(orderSaveModelList.get(i).getMealPrice()));
                                mealProductsDTO.setProductSizePrice(Double.parseDouble(orderSaveModelList.get(i).getMealPrice()));
                                mealProductsDTO.setSelected(true);
                                mealProductsDTO.setProductSizeName(mealDetailsModel.getMeal_config().get(j).getProduct_size_name());
                                for (int k = 0; k < mealDetailsModel.getMeal_config().get(j).getProducts().size(); k++) {
                                    if (mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getNoOfCount() > 0) {
                                        if (mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getProduct_size_id() != null) {
                                            mealProductsDTO.setProductSizeId(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getProduct_size_id());
                                        } else {
                                            mealProductsDTO.setProductSizeId("");
                                        }
                                        break;
                                    }

                                }
                                boolean isLoopExecuted = false;
                                for (int k = 0; k < mealDetailsModel.getMeal_config().get(j).getProducts().size(); k++) {

                                    if (!isLoopExecuted && mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getNoOfCount() > 0) {
                                        /*if (mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().size() > 0) {
                                            for (int m = 0; m < mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().size(); m++) {

                                                for (int n = 0; n < mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().get(m).getSize_modifiers().size(); n++) {
                                                    CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO sizeModifiersDTO = new CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO();

                                                    sizeModifiersDTO.setModifierType(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().get(m).getSize_modifiers().get(n).getModifier_type());
                                                    sizeModifiersDTO.setMinAllowedQuantity(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().get(m).getSize_modifiers().get(n).getMin_allowed_quantity());
                                                    sizeModifiersDTO.setMaxAllowedQuantity(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().get(m).getSize_modifiers().get(n).getMax_allowed_quantity());
                                                    sizeModifiersDTO.setModifierId(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().get(m).getSize_modifiers().get(n).getModifier_id());
                                                    sizeModifiersDTO.setModifierName(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().get(m).getSize_modifiers().get(n).getModifier_name());
                                                    List<CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO.SizeModifierProductsDTO> sizeModifierProducts = new ArrayList<>();

                                                    for (int q = 0; q < list.size(); q++) {
                                                        if (list.get(q).getMax_allowed_quantity() == mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().get(m).getSize_modifiers().get(n).getMax_allowed_quantity()) {
                                                            sizeModifierProducts.add(list.get(q));
                                                        }
                                                    }
                                                    sizeModifiersDTO.setSizeModifierProducts(sizeModifierProducts);
                                                    sizeModifiersDTOS.add(sizeModifiersDTO);
                                                    isLoopExecuted = true;
                                                }
                                                break;
                                            }
                                        } else {
                                            if (list.size() > 0) {
                                                CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO sizeModifiersDTO = new CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO();

                                                sizeModifiersDTO.setModifierType("");
                                                sizeModifiersDTO.setMinAllowedQuantity(0);
                                                sizeModifiersDTO.setMaxAllowedQuantity(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getAllowed_quantity());
                                                sizeModifiersDTO.setModifierId("");
                                                sizeModifiersDTO.setModifierName(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getProduct_name());
                                                List<CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO.SizeModifierProductsDTO> sizeModifierProducts = new ArrayList<>();

                                                for (int q = 0; q < list.size(); q++) {

                                                    sizeModifierProducts.add(list.get(q));

                                                }
                                                sizeModifiersDTO.setSizeModifierProducts(sizeModifierProducts);
                                                sizeModifiersDTOS.add(sizeModifiersDTO);
                                                break;
                                            }

                                        }
*/


                                        if (list.size() > 0) {
                                            CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO sizeModifiersDTO = new CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO();

                                            sizeModifiersDTO.setModifierType("");
                                            sizeModifiersDTO.setMinAllowedQuantity(0);
                                            sizeModifiersDTO.setMaxAllowedQuantity(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getAllowed_quantity());
                                            sizeModifiersDTO.setModifierId("");
                                            sizeModifiersDTO.setModifierName(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getProduct_name());
                                            List<CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO.SizeModifierProductsDTO> sizeModifierProducts = new ArrayList<>();

                                            for (int q = 0; q < list.size(); q++) {

                                                sizeModifierProducts.add(list.get(q));

                                            }
                                            sizeModifiersDTO.setSizeModifierProducts(sizeModifierProducts);
                                            sizeModifiersDTOS.add(sizeModifiersDTO);
                                            break;
                                        }
                                    }
                                }

                                mealProductsDTO.setSizeModifiers(sizeModifiersDTOS);

                                mealProducts.add(mealProductsDTO);
                            }
                        } else {
                            if (mealDetailsModel != null) {
                                for (int j = 0; j < mealDetailsModel.getMeal_config().size(); j++) {
                                    if (mealDetailsModel.getMeal_config().get(j).getNoOfCount() > 0) {
                                        List<CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO> sizeModifiersDTOS = new ArrayList<>();
                                        CheckoutModel.MenuProductsDTO.MealProductsDTO mealProductsDTO = new CheckoutModel.MenuProductsDTO.MealProductsDTO();
                                        mealProductsDTO.setAmount(Double.parseDouble(orderSaveModelList.get(i).getMealPrice()));
                                        mealProductsDTO.setQuantity(String.valueOf(mealDetailsModel.getMeal_config().get(j).getProduct_quantity()));
                                        mealProductsDTO.setOroginalQuantity(String.valueOf(mealDetailsModel.getMeal_config().get(j).getProduct_quantity()));
                                        mealProductsDTO.setOriginalAmount(Double.parseDouble(orderSaveModelList.get(i).getMealPrice()));
                                        mealProductsDTO.setProductSizePrice(Double.parseDouble(orderSaveModelList.get(i).getMealPrice()));
                                        mealProductsDTO.setSelected(true);
                                        mealProductsDTO.setProductSizeName(mealDetailsModel.getMeal_config().get(j).getProduct_size_name());
                                        for (int k = 0; k < mealDetailsModel.getMeal_config().get(j).getProducts().size(); k++) {
                                            if (mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getNoOfCount() > 0) {
                                                if (mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getProduct_size_id() != null) {
                                                    mealProductsDTO.setProductSizeId(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getProduct_size_id());
                                                } else {
                                                    mealProductsDTO.setProductSizeId("");
                                                }
                                                break;
                                            }

                                        }
                                        boolean isLoopExecuted = false;
                                        for (int k = 0; k < mealDetailsModel.getMeal_config().get(j).getProducts().size(); k++) {

                                            if (!isLoopExecuted && mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getNoOfCount() > 0) {
                                        /*if (mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().size() > 0) {
                                            for (int m = 0; m < mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().size(); m++) {

                                                for (int n = 0; n < mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().get(m).getSize_modifiers().size(); n++) {
                                                    CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO sizeModifiersDTO = new CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO();

                                                    sizeModifiersDTO.setModifierType(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().get(m).getSize_modifiers().get(n).getModifier_type());
                                                    sizeModifiersDTO.setMinAllowedQuantity(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().get(m).getSize_modifiers().get(n).getMin_allowed_quantity());
                                                    sizeModifiersDTO.setMaxAllowedQuantity(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().get(m).getSize_modifiers().get(n).getMax_allowed_quantity());
                                                    sizeModifiersDTO.setModifierId(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().get(m).getSize_modifiers().get(n).getModifier_id());
                                                    sizeModifiersDTO.setModifierName(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().get(m).getSize_modifiers().get(n).getModifier_name());
                                                    List<CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO.SizeModifierProductsDTO> sizeModifierProducts = new ArrayList<>();

                                                    for (int q = 0; q < list.size(); q++) {
                                                        if (list.get(q).getMax_allowed_quantity() == mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getMenu_product_size().get(m).getSize_modifiers().get(n).getMax_allowed_quantity()) {
                                                            sizeModifierProducts.add(list.get(q));
                                                        }
                                                    }
                                                    sizeModifiersDTO.setSizeModifierProducts(sizeModifierProducts);
                                                    sizeModifiersDTOS.add(sizeModifiersDTO);
                                                    isLoopExecuted = true;
                                                }
                                                break;
                                            }
                                        } else {
                                            if (list.size() > 0) {
                                                CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO sizeModifiersDTO = new CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO();

                                                sizeModifiersDTO.setModifierType("");
                                                sizeModifiersDTO.setMinAllowedQuantity(0);
                                                sizeModifiersDTO.setMaxAllowedQuantity(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getAllowed_quantity());
                                                sizeModifiersDTO.setModifierId("");
                                                sizeModifiersDTO.setModifierName(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getProduct_name());
                                                List<CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO.SizeModifierProductsDTO> sizeModifierProducts = new ArrayList<>();

                                                for (int q = 0; q < list.size(); q++) {

                                                    sizeModifierProducts.add(list.get(q));

                                                }
                                                sizeModifiersDTO.setSizeModifierProducts(sizeModifierProducts);
                                                sizeModifiersDTOS.add(sizeModifiersDTO);
                                                break;
                                            }

                                        }
*/


                                                if (list.size() > 0) {
                                                    CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO sizeModifiersDTO = new CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO();

                                                    sizeModifiersDTO.setModifierType("");
                                                    sizeModifiersDTO.setMinAllowedQuantity(0);
                                                    sizeModifiersDTO.setMaxAllowedQuantity(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getAllowed_quantity());
                                                    sizeModifiersDTO.setModifierId("");
                                                    sizeModifiersDTO.setModifierName(mealDetailsModel.getMeal_config().get(j).getProducts().get(k).getProduct_name());
                                                    List<CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO.SizeModifierProductsDTO> sizeModifierProducts = new ArrayList<>();

                                                    for (int q = 0; q < list.size(); q++) {

                                                        sizeModifierProducts.add(list.get(q));

                                                    }
                                                    sizeModifiersDTO.setSizeModifierProducts(sizeModifierProducts);
                                                    sizeModifiersDTOS.add(sizeModifiersDTO);
                                                    break;
                                                }
                                            }
                                        }

                                        mealProductsDTO.setSizeModifiers(sizeModifiersDTOS);

                                        mealProducts.add(mealProductsDTO);
                                    }
                                }
                            }
                        }

                        //  List<CheckoutModel.MenuProductsDTO.MealProductsDTO.SizeModifiersDTO.SizeModifierProductsDTO>
                        menuProductsDTO.setMealProducts(mealProducts);
                        menuProductsDTOList.add(menuProductsDTO);
                        checkoutModel.setMenuProducts(menuProductsDTOList);

                        checkoutModelList.add(checkoutModel);

                    }

                    cartDataForRequest.setMenuCategoryCarts(checkoutModelList);
                    cartDatRequestNew.setCartData(cartDataForRequest);
                    Log.e("Cart Data", new Gson().toJson(cartDatRequestNew));
                    //initAssetJsonData();


                }
            });

            Log.e("Pront", "" + cartDatRequestNew);
            // cartDatRequestNew
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void alertDialogCVV(String msg) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.popup_cvv_number, null);
        final AlertDialog cvvDialog = new AlertDialog.Builder(this).create();
        cvvDialog.setCanceledOnTouchOutside(false);
        cvvDialog.setView(mDialogView);
        cvvDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final EditText exDate = (EditText) mDialogView.findViewById(R.id.expire_date);
        final EditText exYear = (EditText) mDialogView.findViewById(R.id.expire_year);
        final TextView errorTxt = (TextView) mDialogView.findViewById(R.id.txt_error);
        final TextView textView = mDialogView.findViewById(R.id.textView);
        final TextView errorText = mDialogView.findViewById(R.id.tv_invalid);
        errorText.setVisibility(View.GONE);
        if (msg != null) {
            textView.setVisibility(View.GONE);
            errorText.setVisibility(View.VISIBLE);
        }
        errorText.setText(msg);

        exDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                textView.setVisibility(View.VISIBLE);
                errorText.setVisibility(View.GONE);

                if (s.length() == 2) {
                    exYear.requestFocus();
                }

            }
        });
        mDialogView.findViewById(R.id.submit_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                if (exDate.getText().toString().trim().length() <= 0) {
                    errorTxt.setText("Please enter expiry month");
                    errorTxt.setVisibility(View.VISIBLE);
                } else if (Integer.parseInt(exDate.getText().toString().trim()) > 12 && Integer.parseInt(exDate.getText().toString().trim()) < 1) {

                    errorTxt.setText("Please enter valid expiry month");
                    errorTxt.setVisibility(View.VISIBLE);

                } else if (exYear.getText().toString().trim().length() <= 0) {
                    errorTxt.setText("Please enter expiry year");
                    errorTxt.setVisibility(View.VISIBLE);
                } else {
                    errorTxt.setVisibility(View.GONE);
                    if (val.getRestaurantDetailsResponse().getData().getRestaurants().getAddress() != null) {
                        if (voucherPaymentType != null && !voucherPaymentType.equals("") && voucherPaymentType.equalsIgnoreCase("card")) {
                            if (isInternetOn(SelectPaymentMethodActivity.this)) {
                                callAPI("", "card", exDate.getText().toString(), exYear.getText().toString());
                            } else {
                                dialogNoInternetConnection("Please check internet connection.", 0);
                            }

                        } else if (voucherPaymentType == null || voucherPaymentType.equalsIgnoreCase("")) {
                            if (isInternetOn(SelectPaymentMethodActivity.this)) {
                                callAPI("", "card", exDate.getText().toString(), exYear.getText().toString());
                            } else {
                                dialogNoInternetConnection("Please check internet connection.", 0);
                            }

                        } else {
                            alertVoucherApply(voucherPaymentType, false, exDate.getText().toString(), exYear.getText().toString());
                        }
                    } else {
                        Intent intent = new Intent(SelectPaymentMethodActivity.this, AddAddressManualActivity.class);
                        startActivity(intent);
                    }

                    cvvDialog.dismiss();
                }

            }
        });
        mDialogView.findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvvDialog.dismiss();
                address1 = "";
                address2 = "";
                addressCity = "";
                postalCode = "";
            }
        });

        cvvDialog.show();
    }

    public void alertDialogSelectCard(String msg) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.popup_select_card, null);
        final AlertDialog cardDialog = new AlertDialog.Builder(this).create();
        cardDialog.setCanceledOnTouchOutside(false);
        TextView txtMsg = mDialogView.findViewById(R.id.txt_msg);
        txtMsg.setText(msg);
        cardDialog.setView(mDialogView);
        cardDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardDialog.dismiss();
                alertDialogCVV(null);

            }
        });
        mDialogView.findViewById(R.id.cancelTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardDialog.dismiss();
            }
        });

        cardDialog.show();
    }


    @OnClick({R.id.add_new_card, R.id.paywith_card_tv, R.id.pay_with_cash})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_new_card:
                if (voucherPaymentType != null && !voucherPaymentType.equals("") && voucherPaymentType.equalsIgnoreCase("card")) {
                    Intent i = new Intent(this, AddNewCardActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("ORDER_TOTAL", totalAmount);
                    i.putExtra("ORDER_SUB_TOTAL", subTotalAmount);
                    i.putExtra("deliveryCharge", deliveryFee);
                    i.putExtra("PATNER_ID", deliveryPartnerId);
                    i.putExtra("orderType", orderType);
                    i.putExtra("MOBILE_NUMBER", mobileNo);
                    i.putExtra("voucherDiscount", voucherDiscount);
                    i.putExtra("notes", notes);
                    i.putExtra("appliedVoucherCode", voucherCode);
                    i.putExtra("appliedVoucherAmount", voucherAmount);
                    i.putExtra("appliedVoucherPaymentType", voucherPaymentType);
                    startActivity(i);
                    this.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                } else if (voucherPaymentType == null || voucherPaymentType.equalsIgnoreCase("")) {
                    Intent i = new Intent(this, AddNewCardActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("ORDER_TOTAL", totalAmount);
                    i.putExtra("ORDER_SUB_TOTAL", subTotalAmount);
                    i.putExtra("deliveryCharge", deliveryFee);
                    i.putExtra("orderType", orderType);
                    i.putExtra("MOBILE_NUMBER", mobileNo);
                    i.putExtra("PATNER_ID", deliveryPartnerId);
                    i.putExtra("voucherDiscount", voucherDiscount);
                    i.putExtra("notes", notes);
                    i.putExtra("appliedVoucherCode", voucherCode);
                    i.putExtra("appliedVoucherAmount", voucherAmount);
                    i.putExtra("appliedVoucherPaymentType", voucherPaymentType);
                    startActivity(i);
                    this.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                } else {
                    alertVoucherApply(voucherPaymentType, true, "", "");
                }
                break;
            case R.id.paywith_card_tv:
                break;
         /*   case R.id.pay_with_cash:
                try {
                    if (sharedPreferencesClass.getInt(sharedPreferencesClass.NUMBER_OF_ORDERS) > 0) {
                        if (voucherPaymentType != null && !voucherPaymentType.equals("") && voucherPaymentType.equalsIgnoreCase("cash")) {
                            if (isInternetOn(SelectPaymentMethodActivity.this)) {
                                callAPI("", "cash", "", "");
                            } else {
                                dialogNoInternetConnection("Please check internet connection.", 0);
                            }
                        } else if (voucherPaymentType == null || voucherPaymentType.equalsIgnoreCase("")) {
                            if (isInternetOn(SelectPaymentMethodActivity.this)) {
                                callAPI("", "cash", "", "");
                            } else {
                                dialogNoInternetConnection("Please check internet connection.", 0);
                            }
                        } else {
                            alertVoucherApply(voucherPaymentType, false);
                        }
                    } else {
                        alertMessage();
                    }
                } catch (Exception e) {
                    Toast.makeText(SelectPaymentMethodActivity.this, "Server Error.", Toast.LENGTH_SHORT).show();
                }
                break;*/
        }
    }

    public void initView(List<Card> dataList) {
        for (int i = 0; i < dataList.size(); i++) {
            check.add("0");
        }
        allReadyCheck.add("0");

        mPositionInterface = this;
        mDealCardAdapter = new SaveCardAdapter(getApplicationContext(), mPositionInterface, check, dataList);
        @SuppressLint("WrongConstant")
        LinearLayoutManager horizontalLayoutManagaer2 = new LinearLayoutManager(SelectPaymentMethodActivity.this, LinearLayoutManager.VERTICAL, false);
        showCardList.setLayoutManager(horizontalLayoutManagaer2);
        showCardList.setAdapter(mDealCardAdapter);

        if (orderType.length() > 0) {
            for (int i = 0; i < dataList.size() - 1; i++) {
                if (dataList.get(i).getIsDefault() == 1)
                    alertDialogSelectCard("Do you want to procceed\nwith card ending by\n" + dataList.get(i).getLast4CardNo());
            }

        }
    }


    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

  /*  @Override
    public void onBackPressed() {
        return;
    }*/

    public void callAPI(String token, String paymentType, String exDate, String exYear) {
        mProgressDialog.show();
        CheckoutRequestInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(CheckoutRequestInterface.class);
        if (!paymentType.equalsIgnoreCase("cash"))
            detail = dataList.get(position);

        final CheckoutRequest request = new CheckoutRequest();
        request.setRestaurantId(val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantId());
        request.setCustomerId(sharedPreferencesClass.getString(sharedPreferencesClass.USER_ID));
        request.setPaymentMode(paymentType);
        if (sharedPreferencesClass.getString(SharedPreferencesClass.IS_TOMORROW) != null)
            request.setIsTomorrow(sharedPreferencesClass.getString(sharedPreferencesClass.IS_TOMORROW));
        else
            request.setIsTomorrow("0");

        request.setDeliveryOption(orderType.toLowerCase());
        request.setUserMobile(mobileNo);
        request.setDeliveryCharge(deliveryFee);
        request.setDiscountAmount(voucherDiscount);
        request.setOrderTotal(totalAmount);
        request.setDeliveryPartner(deliveryPartnerId);
        request.setOrderSubtotal(subTotalAmount);
        request.setVoucherId("");
        request.setOfferId("");
        request.setBillingAddress(sharedPreferencesClass.getString(sharedPreferencesClass.DELIVERY_ADDRESS_ID));
        request.setDeliveryAddress(sharedPreferencesClass.getString(sharedPreferencesClass.DELIVERY_ADDRESS_ID));
        request.setOrderVia("android");
        request.setOrderNotes(notes);
        request.setStripeToken(token);
        request.setDeliveryDateTime(sharedPreferencesClass.getString(sharedPreferencesClass.DELIVERY_DATE_TIME));
        request.setIsTableBooking(sharedPreferencesClass.getInt(SharedPreferencesClass.IS_FOR_TABLE));
        request.setUnitType(sharedPreferencesClass.getString(SharedPreferencesClass.UNIT_TYPE));
        request.setUnitNumber(sharedPreferencesClass.getString(SharedPreferencesClass.TABLE_NO));
        request.setUnitId(sharedPreferencesClass.getString(SharedPreferencesClass.UNIT_ID));
        if (!exYear.equalsIgnoreCase("") && !exDate.equalsIgnoreCase("")) {
            request.setExpMonth(Integer.parseInt(exDate));
            request.setExpYear(Integer.parseInt(exYear));
        }

        if (!paymentType.equalsIgnoreCase("cash")) {
            request.setStripeCustomerId(detail.getStripeCustomerId());
            request.setLast4CardDigit(dataList.get(position).getLast4CardNo());
            request.setSavedCardId(detail.getCardId());
        } else {
            request.setStripeCustomerId("");
            request.setLast4CardDigit("");
            request.setSavedCardId("");
        }
        request.setUseragent(Helper.getDeviceName() + "," + Build.VERSION.RELEASE);
        request.setOrder_time_postcode(PrefManager.getInstance(SelectPaymentMethodActivity.this).getPreference(POST_CODE_NEW, ""));
        request.setEmailId(val.getLoginResponse().getData().getEmail());
        request.setCardData(cartDatRequestNew);
        Call<CheckoutResponse> call3 = apiInterface.mCheckout(PrefManager.getInstance(SelectPaymentMethodActivity.this).getPreference(AUTH_TOKEN, ""), request);
        call3.enqueue(new Callback<CheckoutResponse>() {
            @Override
            public void onResponse(Call<CheckoutResponse> call, Response<CheckoutResponse> response) {
                mProgressDialog.dismiss();
                try {
                    if (response.code() == 200 && response.body().getSuccess()) {
                        sharedPreferencesClass.setOrderIDKey(response.body().getData().getOrder_number());
                        sharedPreferencesClass.setInt(sharedPreferencesClass.NUMBER_OF_ORDERS, (sharedPreferencesClass.getInt(sharedPreferencesClass.NUMBER_OF_ORDERS) + 1));
                        Constants.MAX_LENGTH = 0;
                        alertDialogOrderPlaced(response.body().getMessage(), true, request, response.body().getData().getOrder_number());

                    } else if (response.code() == 200 && !response.body().getSuccess()) {
                        Toast.makeText(val, "", Toast.LENGTH_SHORT).show();
                        alertDialogCVV("Please enter valid expiry date");
                    } else {
                        alertDialogOrderPlaced("Transaction Failed\n" +
                                "Your Order could not be processed", false, request, "");
                        address1 = "";
                        address2 = "";
                        addressCity = "";
                        postalCode = "";
                    }
                } catch (Exception e) {
                    mProgressDialog.dismiss();
                    alertDialogOrderPlaced("Transaction Failed\n" +
                            "Your Order could not be processed", false, request, "");
                    address1 = "";
                    address2 = "";
                    addressCity = "";
                    postalCode = "";
                }
            }

            @Override
            public void onFailure(Call<CheckoutResponse> call, Throwable t) {
                mProgressDialog.dismiss();
                alertDialogOrderPlaced("Transaction Failed\n" +
                        "Your Order could not be processed", false, request, "");
                address1 = "";
                address2 = "";
                addressCity = "";
                postalCode = "";
            }
        });
    }


    public void getCardList() {
        CardListInterface apiInterface = ApiClient.getClient(SelectPaymentMethodActivity.this).create(CardListInterface.class);
        CardListRequest request = new CardListRequest();
        request.setUserId(sharedPreferencesClass.getString(sharedPreferencesClass.USER_ID));


        Call<CardListResponse> call3 = apiInterface.mLogin(PrefManager.getInstance(SelectPaymentMethodActivity.this).getPreference(AUTH_TOKEN, ""), request);
        call3.enqueue(new Callback<CardListResponse>() {
            @Override
            public void onResponse(Call<CardListResponse> call, Response<CardListResponse> response) {
                try {
                    dialog.dismiss();

                    if (response.body().getSuccess()) {
                        dataList = response.body().getData().getCards();
                        if (dataList.size() <= 0) {
                            noCards.setVisibility(View.VISIBLE);
                        } else {
                            noCards.setVisibility(View.GONE);
                        }
                        initView(dataList);
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                    noCards.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<CardListResponse> call, Throwable t) {
                noCards.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    public void onClickPos(int pos, ArrayList<String> check, List<Card> dataList) {
        if (check.contains("1")) {
            for (int i = 0; i < dataList.size(); i++) {
                check.set(i, "0");
            }
        }
        address1 = dataList.get(pos).getAddressLine1();
        address2 = dataList.get(pos).getAddressLine2();
        addressCity = dataList.get(pos).getAddressCity();
        postalCode = dataList.get(pos).getAddressPostCode();
        isCardSelected = true;
        check.set(pos, "1");
        position = pos;
        mDealCardAdapter.notifyDataSetChanged();

        alertDialogSelectCard("Do you want to procceed\nwith card ending by\n" + dataList.get(pos).getLast4CardNo());
    }


    public void alertDialogOrderPlaced(final String msg, final boolean isSuccess, final CheckoutRequest request, final String orderNum) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.popoup_order_fail_success, null);
        final AlertDialog cardDialog = new AlertDialog.Builder(this).create();
        cardDialog.setCanceledOnTouchOutside(false);
        TextView txtMsg = mDialogView.findViewById(R.id.txt_msg);
        txtMsg.setText(msg);
        cardDialog.setView(mDialogView);
        cardDialog.setCancelable(false);
        cardDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardDialog.dismiss();
                if (isSuccess) {
                    int fragSize = getSupportFragmentManager().getBackStackEntryCount() - 1;
                    for (int i = 0; i < fragSize; i++) {
                        getSupportFragmentManager().popBackStack();
                    }

                    String restaurentName = sharedPreferencesClass.getString(sharedPreferencesClass.RESTUARANT_NAME);

                    db.deleteCart();
                    mDb.saveOrderHistry().deleteAll();
                    sharedPreferencesClass.setString(sharedPreferencesClass.RESTUARANT_ID, "");
                    sharedPreferencesClass.setString(sharedPreferencesClass.RESTUARANT_NAME, "");
                    sharedPreferencesClass.setString(sharedPreferencesClass.NOTEPAD, "");
                    sharedPreferencesClass.setString(sharedPreferencesClass.DELIVERY_MOBILE_NUMBER, "");
                    sharedPreferencesClass.setString(SharedPreferencesClass.TABLE_NO, null);
                    sharedPreferencesClass.setString(SharedPreferencesClass.UNIT_ID, null);
                    sharedPreferencesClass.setString(SharedPreferencesClass.UNIT_TYPE, null);
                    sharedPreferencesClass.setString(SharedPreferencesClass.TABLE_TYPE, null);
                    sharedPreferencesClass.setString(sharedPreferencesClass.DEFAULT_ADDRESS, null);


                    PrefManager.getInstance(SelectPaymentMethodActivity.this).savePreference(IS_OFFERED, false);
                    PrefManager.getInstance(SelectPaymentMethodActivity.this).savePreference(OFFER_PRICE, null);
                    PrefManager.getInstance(SelectPaymentMethodActivity.this).savePreference(OFFER_TYPE, null);
                    PrefManager.getInstance(SelectPaymentMethodActivity.this).savePreference(OFFER_ID, null);
                    PrefManager.getInstance(SelectPaymentMethodActivity.this).savePreference(MIN_VALUE, null);

                    Constants.ORDER_STATUS = 1;
                    Intent intent = new Intent(SelectPaymentMethodActivity.this, OrderStatusActivity.class);
                    intent.putExtra("order_no", orderNum);

                    if (sharedPreferencesClass.getInt(SharedPreferencesClass.IS_FOR_TABLE) == 0) {
                        intent.putExtra(Constants.PAYMENT_MODE, request.getPaymentMode());
                        intent.putExtra(Constants.ORDER_TYPE, request.getDeliveryOption());
                        intent.putExtra(Constants.ORDER_TIME, sharedPreferencesClass.getString(sharedPreferencesClass.DELIVERY_DATE_TIME));
                    } else {
                        if (request.getPaymentMode().equalsIgnoreCase("cash")) {
                            intent.putExtra(Constants.PAYMENT_MODE, "Pay at Restaurant");
                        } else {
                            intent.putExtra(Constants.PAYMENT_MODE, request.getPaymentMode());
                        }
                        intent.putExtra(Constants.ORDER_TIME, formatDateTime(new Date()));
                        intent.putExtra(Constants.ORDER_TYPE, "table");
                    }


                    intent.putExtra(Constants.RESTAURENT_NAME, restaurentName);
                    intent.putExtra(Constants.TOTAL_COST, String.valueOf(request.getOrderTotal()));
                    intent.putExtra(Constants.PHONE_NUMBER, "9876543210");
                    intent.putExtra(Constants.CUSTOMER_ID, request.getCustomerId());

                    startActivity(intent);
                    if (RestaurantDetailsActivity.restaurantDetailsActivity != null) {
                        RestaurantDetailsActivity.restaurantDetailsActivity.finish();
                    }
                    finish();
                } else {
                    dialog.dismiss();
                    msg.equalsIgnoreCase("Must provide correct expiry date");
                    cardDialog.dismiss();
                }

            }
        });
        cardDialog.show();
    }

    public void alertVoucherApply(String paymentType, final Boolean addCard, String exDate, String exYear) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.addnote_success_dialog, null);
        final AlertDialog noteDialog = new AlertDialog.Builder(this).create();
        noteDialog.setView(mDialogView);
        noteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tvMessage, noTv;
        mDialogView.findViewById(R.id.view).setVisibility(View.VISIBLE);
        noTv = mDialogView.findViewById(R.id.noTv);
        noTv.setVisibility(View.VISIBLE);
        tvMessage = mDialogView.findViewById(R.id.message);
        tvMessage.setText("This voucher is valid for " + paymentType + " only." + "\nDo you want to proceed?");
        tvMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen._14sdp));


        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalAmount = subTotalAmount;
                voucherDiscount = 0.d;
                voucherAmount = 0.d;
                voucherCode = "";
                if (addCard) {
                    Intent i = new Intent(SelectPaymentMethodActivity.this, AddNewCardActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("ORDER_TOTAL", totalAmount);
                    i.putExtra("ORDER_SUB_TOTAL", subTotalAmount);
                    i.putExtra("deliveryCharge", deliveryFee);
                    i.putExtra("orderType", orderType);
                    i.putExtra("MOBILE_NUMBER", mobileNo);
                    i.putExtra("voucherDiscount", voucherDiscount);
                    i.putExtra("notes", notes);
                    i.putExtra("appliedVoucherCode", voucherCode);
                    i.putExtra("appliedVoucherAmount", voucherAmount);
                    i.putExtra("appliedVoucherPaymentType", voucherPaymentType);
                    startActivity(i);
                    SelectPaymentMethodActivity.this.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                } else {
                    if (isInternetOn(SelectPaymentMethodActivity.this)) {
                        callAPI("", "card", exDate, exYear);
                    } else {
                        dialogNoInternetConnection("Please check internet connection.", 0);
                    }
                }

                noteDialog.dismiss();
            }
        });

        noTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                noteDialog.dismiss();
            }
        });


        noteDialog.show();
    }

    public void alertMessage() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.pop_first_time_payment, null);
        final AlertDialog cardDialog = new AlertDialog.Builder(this).create();
        TextView txtMsg = mDialogView.findViewById(R.id.txt_msg);
        cardDialog.setView(mDialogView);
        cardDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialogView.findViewById(R.id.tv_PayByCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (voucherPaymentType != null && !voucherPaymentType.equals("") && voucherPaymentType.equalsIgnoreCase("card")) {
                    Intent i = new Intent(SelectPaymentMethodActivity.this, AddNewCardActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("ORDER_TOTAL", totalAmount);
                    i.putExtra("ORDER_SUB_TOTAL", subTotalAmount);
                    i.putExtra("deliveryCharge", deliveryFee);
                    i.putExtra("orderType", orderType);
                    i.putExtra("MOBILE_NUMBER", mobileNo);
                    i.putExtra("voucherDiscount", voucherDiscount);
                    i.putExtra("notes", notes);
                    i.putExtra("appliedVoucherCode", voucherCode);
                    i.putExtra("appliedVoucherAmount", voucherAmount);
                    i.putExtra("appliedVoucherPaymentType", voucherPaymentType);
                    startActivity(i);
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                    cardDialog.dismiss();

                } else if (voucherPaymentType == null || voucherPaymentType.equalsIgnoreCase("")) {
                    Intent i = new Intent(SelectPaymentMethodActivity.this, AddNewCardActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("ORDER_TOTAL", totalAmount);
                    i.putExtra("ORDER_SUB_TOTAL", subTotalAmount);
                    i.putExtra("deliveryCharge", deliveryFee);
                    i.putExtra("orderType", orderType);
                    i.putExtra("MOBILE_NUMBER", mobileNo);
                    i.putExtra("voucherDiscount", voucherDiscount);
                    i.putExtra("notes", notes);
                    i.putExtra("appliedVoucherCode", voucherCode);
                    i.putExtra("appliedVoucherAmount", voucherAmount);
                    i.putExtra("appliedVoucherPaymentType", voucherPaymentType);
                    startActivity(i);
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                    cardDialog.dismiss();
                } else {
                    alertVoucherApply(voucherPaymentType, true, "", "");
                }
            }
        });
        mDialogView.findViewById(R.id.tv_Cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardDialog.dismiss();
            }
        });
        cardDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dialog.dismiss();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dialog.dismiss();
    }

    public void dialogNoInternetConnection(String message, final int status) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.addnote_success_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setView(mDialogView);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final Animation animShake = AnimationUtils.loadAnimation(mContext, R.anim.shake);

        TextView tvMessage = mDialogView.findViewById(R.id.message);
        tvMessage.setText(message);
        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetOn(SelectPaymentMethodActivity.this)) {
                    alertDialog.dismiss();
                    if (status == 1) {
                        getCardList();
                    }

                } else mDialogView.findViewById(R.id.okTv).startAnimation(animShake);

            }
        });

        alertDialog.show();
    }

    private String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }


    List<JSONObject> mySizeModifierProducts = new ArrayList<>();
    List<List<JSONObject>> seprated = new ArrayList<>();

    private void initAssetJsonData() {
        String strJson = getJsonFromAssets(this, "resOriginal.json");
        try {
            seprated.removeAll(seprated);
            JSONObject mainObject = new JSONObject(strJson);
            if (mainObject != null && !mainObject.isNull("menu")) {
                JSONObject menuObject = mainObject.getJSONObject("menu");
                if (menuObject != null && !menuObject.isNull("menuCategory")) {
                    JSONArray menuCategoryArray = menuObject.getJSONArray("menuCategory");
                    //showLog("menuCategoryArray",menuCategoryArray.toString());
                    //showLog("menuCategoryArray Size : ",""+menuCategoryArray.length());
                    if (menuCategoryArray != null && menuCategoryArray.length() > 0) {
                        for (int i = 0; i < menuCategoryArray.length(); i++) {
                            JSONObject menuCategoryObj = menuCategoryArray.getJSONObject(i);
                            if (menuCategoryObj != null && !menuCategoryObj.isNull("menuProducts") && menuCategoryObj.has("menuProducts")) {
                                getMenuProducts(menuCategoryObj);
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getMenuProducts(JSONObject menuCategoryObj) {
        try {
            JSONArray menuProductsArray = menuCategoryObj.getJSONArray("menuProducts");
            //showLog("menuProductsArray",menuProductsArray.toString());
            //showLog("menuProductsArray Size : ",""+menuProductsArray.length());
            if (menuProductsArray != null && menuProductsArray.length() > 0) {
                for (int j = 0; j < menuProductsArray.length(); j++) {
                    JSONObject menuProductsObj = menuProductsArray.getJSONObject(j);
                    if (menuProductsObj != null && !menuProductsObj.isNull("mealProducts") && menuProductsObj.has("mealProducts")) {
                        JSONArray mealProductsArray = menuProductsObj.getJSONArray("mealProducts");
                        //showLog("mealProductsArray",mealProductsArray.toString());
                        //showLog("mealProductsArray Size : ",""+mealProductsArray.length());
                        getMealProducts(mealProductsArray);
                    }
                }
            }
        } catch (JSONException e) {

        }
    }

    private void getMealProducts(JSONArray mealProductsArray) {
        try {
            if (mealProductsArray != null && mealProductsArray.length() > 0) {
                for (int k = 0; k < mealProductsArray.length(); k++) {
                    JSONObject mealProductsObj = mealProductsArray.getJSONObject(k);
                    if (mealProductsObj != null && !mealProductsObj.isNull("sizeModifiers") && mealProductsObj.has("sizeModifiers")) {
                        JSONArray sizeModifiersArray = mealProductsObj.getJSONArray("sizeModifiers");
                        //showLog("sizeModifiersArray",sizeModifiersArray.toString());
                        //showLog("sizeModifiersArray Size: ",""+sizeModifiersArray.length());
                        if (sizeModifiersArray != null && sizeModifiersArray.length() > 0) {
                            mySizeModifierProducts.removeAll(mySizeModifierProducts);
                            getSizeModifiers(sizeModifiersArray);
                            seprated.add(mySizeModifierProducts);
                            Log.e("mySizeModifierProducts:", "\n" + mySizeModifierProducts.toString());
                            Log.e("seprated:", "\n" + seprated.toString());
                            Log.e("seprated.size:", "\n" + seprated.size());
                        }
                    }
                }
            }
        } catch (JSONException e) {

        }
    }

    private void getSizeModifiers(JSONArray sizeModifiersArray) {
        try {
            for (int l = 0; l < sizeModifiersArray.length(); l++) {
                JSONObject sizeModifiersObj = sizeModifiersArray.getJSONObject(l);
                if (sizeModifiersObj != null && !sizeModifiersObj.isNull("sizeModifierProducts") && sizeModifiersObj.has("sizeModifierProducts")) {
                    JSONArray sizeModifierProductsArray = sizeModifiersObj.getJSONArray("sizeModifierProducts");
                    //showLog("sizeModifierProductsArray",sizeModifierProductsArray.toString());
                    //showLog("sizeModifierProducts Size : ",""+sizeModifierProductsArray.length());
                    if (sizeModifierProductsArray != null && sizeModifierProductsArray.length() > 0) {
                        getSizeModifierProductsItems(sizeModifierProductsArray);
                    }
                }
            }
        } catch (JSONException e) {

        }
    }

    private void getSizeModifierProductsItems(JSONArray sizeModifierProductsArray) {
        try {
            for (int m = 0; m < sizeModifierProductsArray.length(); m++) {
                JSONObject sizeModifierProductsObj = sizeModifierProductsArray.getJSONObject(m);
                //showLog("sizeModifierProductsObj",sizeModifierProductsObj.toString());
                //showLog("sizeModifierProductsObj", sizeModifierProductsObj.toString());
                if (sizeModifierProductsObj != null) {
                    mySizeModifierProducts.add(sizeModifierProductsObj);
                }
            }
        } catch (JSONException e) {

        }
    }

    private void showLog(String title, String msg) {
        Log.e("Array :", "\n" + title + "--" + msg + "\n");
    }

    public static String getJsonFromAssets(Context context, String fileName) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return jsonString;
    }

    public boolean isMealProduect(MealDetailsModel mealDetailsModel) {
        boolean isMeal = false;

        for (int i = 0; i < mealDetailsModel.getMeal_config().get(0).getProducts().size(); i++) {
            if (mealDetailsModel.getMeal_config().get(0).getProducts().get(i).getNoOfCount() > 0) {
                isMeal = true;
                break;
            }
        }


        return isMeal;
    }
}
