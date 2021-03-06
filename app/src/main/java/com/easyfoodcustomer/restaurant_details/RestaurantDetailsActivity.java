package com.easyfoodcustomer.restaurant_details;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.easyfoodcustomer.dialogs.MenuDetailDialog;
import com.easyfoodcustomer.modelsNew.MealDetailList;
import com.easyfoodcustomer.order_details_activity.OrderDetailActivity;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MeaListBean;
import com.easyfoodcustomer.roomData.AppDatabase;
import com.easyfoodcustomer.roomData.OrderSaveModel;
import com.easyfoodcustomer.ui_new.interfaces.CallMenueDialogInterface;
import com.easyfoodcustomer.utility.DialogUtils;
import com.google.android.material.tabs.TabLayout;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.easyfoodcustomer.utility.PrefManager;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.easyfoodcustomer.R;
import com.easyfoodcustomer.adapters.menu_adapter.ItemClickListener;
import com.easyfoodcustomer.adapters.menu_adapter.OneItemMultiTimeListener;
import com.easyfoodcustomer.adapters.menu_adapter.RecyclerLayoutManager;
import com.easyfoodcustomer.adapters.menu_adapter.RestaurantMenuListAdapter;
import com.easyfoodcustomer.api.AddFavouritesInterface;
import com.easyfoodcustomer.cart_db.DatabaseHelper;
import com.easyfoodcustomer.cart_db.tables.MenuProducts;
import com.easyfoodcustomer.cart_db.tables.ProductSizeAndModifier;
import com.easyfoodcustomer.cart_model.final_cart.FinalNewCartDetails;
import com.easyfoodcustomer.dashboard.DashboardActivity;
import com.easyfoodcustomer.dialogs.AgainFragment;
import com.easyfoodcustomer.dialogs.ChooseLastCustnizationDialog;
import com.easyfoodcustomer.dialogs.MealProductModifierDialog;
import com.easyfoodcustomer.dialogs.MenuDialogNew;
import com.easyfoodcustomer.dialogs.MenuMealDialog;
import com.easyfoodcustomer.fragments.InfoFragment;
import com.easyfoodcustomer.fragments.MenuFragment;
import com.easyfoodcustomer.fragments.ReviwesFragment;
import com.easyfoodcustomer.login.LoginActivity;
import com.easyfoodcustomer.model.add_favourites_request.AddFavouristeResquest;
import com.easyfoodcustomer.model.add_favourites_response.AddFavouristeResponse;
import com.easyfoodcustomer.restaurant_details.api.RestaurantDetailsInterface;
import com.easyfoodcustomer.restaurant_details.model.new_restaurant_response.NewRestaurantsDetailsResponse;
import com.easyfoodcustomer.restaurant_details.model.request.RestaurantDetailsRequest;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MealProduct;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.Menu;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuProductSize;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.Modifier;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.ProductModifier;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.Rough;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.SizeModifier;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.SpecialOffer;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.request.CategoryProductsRequest;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.request.MenuProductSizeModifierRequest;
import com.easyfoodcustomer.utility.ApiClient;
import com.easyfoodcustomer.utility.Constants;
import com.easyfoodcustomer.utility.GlobalValues;
import com.easyfoodcustomer.utility.HeightWrappingViewPager;
import com.easyfoodcustomer.utility.SharedPreferencesClass;
import com.easyfoodcustomer.viewmodel.MenuProductViewModel;
import com.easyfoodcustomer.viewmodel.MenuViewModel;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.easyfoodcustomer.utility.Helper.isInternetOn;
import static com.easyfoodcustomer.utility.SharedPreferencesClass.SERVE_STYLE;
import static com.easyfoodcustomer.utility.UserContants.AUTH_TOKEN;
import static com.easyfoodcustomer.utility.UserContants.IS_OFFERED;
import static com.easyfoodcustomer.utility.UserContants.IS_VOUCHER_APPLIED;
import static com.easyfoodcustomer.utility.UserContants.MAX_DISCOUNTS;
import static com.easyfoodcustomer.utility.UserContants.MIN_VALUE;
import static com.easyfoodcustomer.utility.UserContants.OFFER_ID;
import static com.easyfoodcustomer.utility.UserContants.OFFER_PRICE;
import static com.easyfoodcustomer.utility.UserContants.OFFER_TYPE;
import static com.easyfoodcustomer.utility.UserContants.VOUCHER_CODE;
import static com.easyfoodcustomer.utility.UserContants.VOUCHER_MAX_DISCOUNT;
import static com.easyfoodcustomer.utility.UserContants.VOUCHER_MIN_ORDER;
import static com.easyfoodcustomer.utility.UserContants.VOUCHER_PRICE;
import static com.easyfoodcustomer.utility.UserContants.VOUCHER_TYPE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class RestaurantDetailsActivity extends AppCompatActivity implements ItemClickListener,
        ChooseLastCustnizationDialog.OnChooseLastCustnizationListener, OneItemMultiTimeListener, CallMenueDialogInterface {

    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarhide)
    FrameLayout toolbarhide;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.llbotom)
    LinearLayout llbotom;
    @BindView(R.id.footer_details)
    TextView footerDetails;
    @BindView(R.id.back_image)
    ImageView backImage;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.allergy_txt)
    TextView allergyTxt;
    @BindView(R.id.lnr_allergy)
    LinearLayout lnrAllergy;
   /* @BindView(R.id.llbotom)
    LinearLayout llBottom;*/


    @BindView(R.id.txt_empty_basket)
    TextView txtEmptyBasket;
    @BindView(R.id.footer_count)
    TextView footerCount;
    @BindView(R.id.tv_currency)
    TextView tvCurrency;


    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.txtx_rating)
    TextView txtxRating;
    @BindView(R.id.item_cuisines)
    TextView itemCuisines;
    @BindView(R.id.ly_footer_details)
    LinearLayout lyFooterDetails;
    @BindView(R.id.delivery_minorder)
    TextView deliveryMinorder;


    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_distance)
    TextView tvDistance;

    @BindView(R.id.delivery_val)
    TextView deliveryVal;
    @BindView(R.id.txt_minutes)
    TextView txtMinutes;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.pager)
    HeightWrappingViewPager pager;
    @BindView(R.id.container_restaurants_details)
    NestedScrollView containerRestaurantsDetails;
    @BindView(R.id.ll_tab)
    LinearLayout llTab;
    @BindView(R.id.im_ratingImage)
    ImageView imRatingImage;
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
    @BindView(R.id.layout_deliveryPrice)
    LinearLayout layoutDeliveryPrice;
    @BindView(R.id.layout_deliveryTime)
    LinearLayout layoutDeliveryTime;

    @BindView(R.id.ll_distance)
    LinearLayout llDistance;

    @BindView(R.id.favourites)
    ImageView favourites;
    private double minimumValue = 0.0;
    private boolean isFavorite;

    String restaurantPhoneNumber;
    SharedPreferencesClass sharePre;
    private GlobalValues val;
    private Dialog dialog;
    int totalCartIterm;
    FinalNewCartDetails cartData;

    private DatabaseHelper db;
    RecyclerLayoutManager layoutManager;
    RecyclerView mainMenu;
    RestaurantMenuListAdapter mMenuAdapter;
    Rough menuResponse;
    Gson gson = new Gson();
    Menu menu;
    Bundle extras;
    boolean isFromLogin, isOffer;
    private String offerPrice, offerType, offerId, minValue,maxValue;
    /* i.putExtra("offer_price", discountOffer.getOfferPrice());
                i.putExtra("offer_type", discountOffer.getOfferType());
                i.putExtra("offer_id", discountOffer.getOfferId());
                i.putExtra("min_value", discountOffer.getMin_value());*/

    public static RestaurantDetailsActivity restaurantDetailsActivity;
    private MenuViewModel menuViewModel;
    private MenuProductViewModel menuProductViewModel;
    private String restaurantId;
    private String serveStyle;
    private String restaurantName = "";
    FirebaseAnalytics mFirebaseAnalytics;
    private boolean isClosed = false;
    private String restaurantPostCode;
    HygieneRatingModel hygieneRatingModel;

    private AppDatabase mDb;
    public static CallMenueDialogInterface callMenueDialogInterface;

    private DialogUtils dialogUtils;
    private boolean isFromCart=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mDb = AppDatabase.getInstance(getApplicationContext());
        callMenueDialogInterface = this;
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        ButterKnife.bind(this);
        restaurantDetailsActivity = this;
        menu = new Menu();
        db = new DatabaseHelper(this);
        cartData = new FinalNewCartDetails();
        dialogUtils = new DialogUtils(RestaurantDetailsActivity.this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Constants.setStatusBarGradiant(RestaurantDetailsActivity.this);
        ObservableScrollView observableScrollView = (ObservableScrollView) findViewById(R.id.observable_scrollview);
        //    observableScrollView.setScrollViewCallbacks(this);
        val = (GlobalValues) getApplicationContext();
        sharePre = new SharedPreferencesClass(this);
        serveStyle = sharePre.getString(SERVE_STYLE);
        dialog = new Dialog(RestaurantDetailsActivity.this);
        dialog.setTitle("");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            }
        });

        tabs.setupWithViewPager(pager);
        pager.getCurrentItem();

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e("IREMS COUNT", "" + tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (sharePre.getInt(SharedPreferencesClass.IS_FOR_TABLE) == 1) {
            layoutDeliveryPrice.setVisibility(View.GONE);
            llDistance.setVisibility(View.GONE);
        }

        if (sharePre.isloginpref() != null)
            favourites.setVisibility(View.VISIBLE);
        else
            favourites.setVisibility(View.GONE);

        containerRestaurantsDetails.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {

                if (i1 > 150) {
                    containerRestaurantsDetails.stopNestedScroll();
                } else {

                }
            }
        });

        llbotom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isClosed) {
                    restaurantClosedDialog();
                } else {
                    if (sharePre.isloginpref() != null) {

                        SharedPreferencesClass preferencesClass = new SharedPreferencesClass(RestaurantDetailsActivity.this);

                        Gson gson2 = new Gson();
                        String json2 = gson2.toJson(val.getRestaurantDetailsResponse());

                        preferencesClass.setCartRestaurantDeatilKey(json2);
                        if (Integer.parseInt(footerCount.getText().toString()) > 0 && Double.parseDouble(footerDetails.getText().toString()) >= minimumValue) {

                            sharePre.setString(sharePre.RESTUARANT_ID, restaurantId);
                            Intent i = new Intent(RestaurantDetailsActivity.this, DashboardActivity.class);
                            i.putExtra("FROMMENU", "YES");
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.putExtra(getString(R.string.isFavorate), isFavorite);
                            i.setAction("custom");
                            startActivity(i);
                        }
                    } else {

                        Intent i = new Intent(RestaurantDetailsActivity.this, LoginActivity.class);
                        i.putExtra("IS_RESTAURENT", true);
                        i.putExtra("RESTAURANTID", restaurantId);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

                        //loginDialog();
                    }
                }
            }
        });

        menuViewModel = ViewModelProviders.of(this).

                get(MenuViewModel.class);

        menuProductViewModel = ViewModelProviders.of(this).

                get(MenuProductViewModel.class);


        showFinalPriceAndView();

    }

    private void setupViewPager(ViewPager viewPager, Menu
            restaurantMenuData, NewRestaurantsDetailsResponse data, TextView footer) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(MenuFragment.newInstance(RestaurantDetailsActivity.this, RestaurantDetailsActivity.this, restaurantMenuData, this, isClosed), "Menu");
        adapter.addFrag(new ReviwesFragment(getApplicationContext()), "Reviews");
        adapter.addFrag(new InfoFragment(RestaurantDetailsActivity.this, getApplicationContext(), data, hygieneRatingModel), "Info");

        pager.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        callMenueDialogInterface = this;
    }


    @Override
    public void onBackPressed() {
        Constants.switchActivity(RestaurantDetailsActivity.this, DashboardActivity.class);
    }

    @OnClick({R.id.back, R.id.lnr_allergy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                Constants.switchActivity(RestaurantDetailsActivity.this, DashboardActivity.class);
                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                break;
            case R.id.lnr_allergy:
                if (isClosed) {
                    restaurantClosedDialog();
                } else {
                    alertDialogAllergy();
                }
                break;
        }
    }

    @Override
    public void onNotify(String menueID) {
        getMelProducts(menueID);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // showPriceAndView(null, null, 0);
        extras = getIntent().getExtras();
        if (extras != null) {
            restaurantId = extras.getString("RESTAURANTID");
            isFromLogin = extras.getBoolean("IS_FROM_LOGIN", false);
            serveStyle = extras.getString("ServeStyle");

          /*  i.putExtra("isOffer", isOffer);
            if (isOffer) {
                i.putExtra("offer_price", discountOffer.getOfferPrice());
                i.putExtra("offer_type", discountOffer.getOfferType());
                i.putExtra("offer_id", discountOffer.getOfferId());
                i.putExtra("min_value", discountOffer.getMin_value());
            }*/
            isOffer = extras.getBoolean("isOffer", false);
            isFromCart=extras.getBoolean("isFromCart", false);
            if(isFromCart){




                String voucherType=extras.getString("voucherType");
                String minOrder=extras.getString("minOrder");
                String maxDiscount=extras.getString("maxDiscount");
                String voucherCode=extras.getString("voucherCode");
                String voucherValue=extras.getString("voucherValue");


                PrefManager.getInstance(RestaurantDetailsActivity.this).savePreference(IS_VOUCHER_APPLIED, true);
                PrefManager.getInstance(RestaurantDetailsActivity.this).savePreference(VOUCHER_TYPE, voucherType);
                PrefManager.getInstance(RestaurantDetailsActivity.this).savePreference(VOUCHER_MIN_ORDER, minOrder);
                PrefManager.getInstance(RestaurantDetailsActivity.this).savePreference(VOUCHER_MAX_DISCOUNT, maxDiscount);
                PrefManager.getInstance(RestaurantDetailsActivity.this).savePreference(VOUCHER_CODE, voucherCode);
                PrefManager.getInstance(RestaurantDetailsActivity.this).savePreference(VOUCHER_PRICE, voucherValue);


            }else {

                PrefManager.getInstance(RestaurantDetailsActivity.this).savePreference(IS_VOUCHER_APPLIED, false);
                PrefManager.getInstance(RestaurantDetailsActivity.this).savePreference(VOUCHER_TYPE, "");
                PrefManager.getInstance(RestaurantDetailsActivity.this).savePreference(VOUCHER_MIN_ORDER, "0.0");
                PrefManager.getInstance(RestaurantDetailsActivity.this).savePreference(VOUCHER_MAX_DISCOUNT, "0.0");
                PrefManager.getInstance(RestaurantDetailsActivity.this).savePreference(VOUCHER_CODE, "");
                PrefManager.getInstance(RestaurantDetailsActivity.this).savePreference(VOUCHER_PRICE, "0.0");

            }

            if (isOffer) {

                offerPrice = extras.getString("offer_price");
                offerType = extras.getString("offer_type");
                offerId = extras.getString("offer_id");
                minValue = extras.getString("min_value");
                maxValue = extras.getString("max_value");

                PrefManager.getInstance(RestaurantDetailsActivity.this).savePreference(IS_OFFERED, true);
                PrefManager.getInstance(RestaurantDetailsActivity.this).savePreference(OFFER_TYPE, offerType);
                PrefManager.getInstance(RestaurantDetailsActivity.this).savePreference(OFFER_ID, offerId);
                PrefManager.getInstance(RestaurantDetailsActivity.this).savePreference(MIN_VALUE, minValue);
                PrefManager.getInstance(RestaurantDetailsActivity.this).savePreference(MAX_DISCOUNTS, maxValue);
                PrefManager.getInstance(RestaurantDetailsActivity.this).savePreference(OFFER_PRICE, offerPrice);

            }else {
                PrefManager.getInstance(RestaurantDetailsActivity.this).savePreference(IS_OFFERED, false);
                PrefManager.getInstance(RestaurantDetailsActivity.this).savePreference(OFFER_TYPE, "");
                PrefManager.getInstance(RestaurantDetailsActivity.this).savePreference(OFFER_ID, "");
                PrefManager.getInstance(RestaurantDetailsActivity.this).savePreference(MIN_VALUE, "");
                PrefManager.getInstance(RestaurantDetailsActivity.this).savePreference(MAX_DISCOUNTS, "");
                PrefManager.getInstance(RestaurantDetailsActivity.this).savePreference(OFFER_PRICE, "");
            }
            if (isFromLogin) {
                //txtEmptyBasket.setText(getResources().getString(R.string.view_basket));


                SharedPreferencesClass preferencesClass = new SharedPreferencesClass(RestaurantDetailsActivity.this);

                Gson gson2 = new Gson();
                String json2 = gson2.toJson(val.getRestaurantDetailsResponse());

                preferencesClass.setCartRestaurantDeatilKey(json2);
                if (Integer.parseInt(footerCount.getText().toString()) > 0 && Double.parseDouble(footerDetails.getText().toString()) >= minimumValue) {

                    sharePre.setString(sharePre.RESTUARANT_ID, restaurantId);
                    Intent i = new Intent(RestaurantDetailsActivity.this, DashboardActivity.class);
                    i.putExtra("FROMMENU", "YES");
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(getString(R.string.isFavorate), isFavorite);
                    i.setAction("custom");
                    startActivity(i);
                }
            }
            if (isInternetOn(RestaurantDetailsActivity.this)) {
                getRestaurantDetails(restaurantId);

            } else {
                dialogNoInternetConnection("Please check internet connection.");
            }

        }

        favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInternetOn(RestaurantDetailsActivity.this)) {
                    addFavourites();

                } else {
                    dialogNoInternetConnection("Please check internet connection.");
                }
            }
        });
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
        callMenueDialogInterface = null;
    }

    public void getRestaurantDetails(final String rId) {
        dialog.show();
        final String resID = rId;
        RestaurantDetailsInterface apiInterface = ApiClient.getClient(this).create(RestaurantDetailsInterface.class);
        RestaurantDetailsRequest request = new RestaurantDetailsRequest();
        request.setUserId(sharePre.getString(sharePre.USER_ID));
        request.setPostCode(sharePre.getPostalCode());
        request.setRestaurantId(resID);

        Call<NewRestaurantsDetailsResponse> call3 = apiInterface.mGetDetails(PrefManager.getInstance(RestaurantDetailsActivity.this).getPreference(AUTH_TOKEN, ""), request);
        call3.enqueue(new Callback<NewRestaurantsDetailsResponse>() {
            @Override
            public void onResponse(Call<NewRestaurantsDetailsResponse> call, Response<NewRestaurantsDetailsResponse> response) {
                try {
                    dialog.dismiss();
                    if (response.body().getSuccess()) {

                        val.setRestaurantDetailsResponse(response.body());
//                        setupViewPager(pager, response.body(), footerDetails);
                        restaurantId = response.body().getData().getRestaurants().getRestaurantId();
                        restaurantName = response.body().getData().getRestaurants().getRestaurantName();
                        restaurantPostCode = response.body().getData().getRestaurants().getPostCode();

                        getRestaurantHygieneRating(resID, response.body());

                        name.setText(response.body().getData().getRestaurants().getRestaurantName());
                        tvToolbarTitle.setText(response.body().getData().getRestaurants().getRestaurantName());
                        if (response.body().getData().getRestaurants().getStatus().equalsIgnoreCase("not_serving")) {
                            isClosed = true;
                        } else {
                            isClosed = false;
                        }
                        sharePre.setInt(sharePre.NUMBER_OF_ORDERS, response.body().getData().getRestaurants().getUserHaveOrdered());
                        if (response.body().getData().getRestaurants().getAvgRating() != null) {
                            if (response.body().getData().getRestaurants().getAvgRating() == 0) {
                                imRatingImage.setVisibility(View.GONE);
                                txtxRating.setText("New");

                            } else {
                                imRatingImage.setVisibility(View.VISIBLE);
                                txtxRating.setText(String.format("%.1f", response.body().getData().getRestaurants().getAvgRating()));
                            }
                        } else {
                            imRatingImage.setVisibility(View.GONE);
                            txtxRating.setText("New");

                        }
                        if (response.body().getData().getRestaurants().getStatus().equalsIgnoreCase("closed")) {
                            tvStatus.setVisibility(View.VISIBLE);
                        } else {
                            tvStatus.setVisibility(View.GONE);
                        }
                        itemCuisines.setText(response.body().getData().getRestaurants().getRestaurantCuisines());
                        minimumValue = Double.parseDouble(response.body().getData().getRestaurants().getMinOrderValue());
                        if (response.body().getData().getRestaurants().getDeliveryCharge() != null && !response.body().getData().getRestaurants().getDeliveryCharge().trim().isEmpty())
                            deliveryMinorder.setText("??" + String.format("%.2f", Double.parseDouble(response.body().getData().getRestaurants().getDeliveryCharge())) + " delivery");
                        else
                            deliveryMinorder.setText("??" + "0.00" + " delivery");

                        if (response.body().getData().getRestaurants().getMinOrderValue() != null && !response.body().getData().getRestaurants().getMinOrderValue().trim().isEmpty())
                            deliveryVal.setText("??" + String.format("%.2f", Double.parseDouble(response.body().getData().getRestaurants().getMinOrderValue())) + " min order");
                        else
                            deliveryVal.setText("??" + "0.00" + " min order");

                        txtMinutes.setText(response.body().getData().getRestaurants().getAvgDeliveryTime() + " min");
                        restaurantPhoneNumber = response.body().getData().getRestaurants().getPhoneNumber();
                        if (response.body().getData().getRestaurants().getDistanceInMiles() == 0) {
                            tvDistance.setText("0 miles");
                        } else {
                            tvDistance.setText(String.valueOf(response.body().getData().getRestaurants().getDistanceInMiles()) + " miles");
                        }
                        if (response.body().getData().getRestaurants().getDeliveryOptions() != null || !response.body().getData().getRestaurants().getDeliveryOptions().equals("")) {
                            String[] serve_styles = response.body().getData().getRestaurants().getDeliveryOptions().split(",");

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

                            if (sharePre.getInt(SharedPreferencesClass.IS_FOR_TABLE) == 0) {
                                if (!Arrays.asList(serve_styles).contains("delivery")) {
                                    layoutDeliveryPrice.setVisibility(View.VISIBLE);
                                    layoutDeliveryTime.setVisibility(View.VISIBLE);
                                } else {
                                    layoutDeliveryPrice.setVisibility(View.VISIBLE);
                                    layoutDeliveryTime.setVisibility(View.VISIBLE);
                                }
                            } else {
                                //ll_delivery.setVisibility(View.GONE);
                                layoutDeliveryPrice.setVisibility(View.GONE);
                                layoutDeliveryTime.setVisibility(View.GONE);
                                // favourites.setVisibility(View.GONE);
                            }
                        }

                        Glide.with(RestaurantDetailsActivity.this).load(response.body().getData().getRestaurants().getRestaurantLogo()).apply(new RequestOptions())
                                .into(logo);

                        Glide.with(RestaurantDetailsActivity.this).load(response.body().getData().getRestaurants().getRestaurantImage()).apply(new RequestOptions()
                                .placeholder(R.drawable.easy_food_image))
                                .into(backImage);

                        if (response.body().getData().getRestaurants().getFavourite() == 1) {
                            favourites.setBackground(getResources().getDrawable(R.drawable.favourite_active));
                            isFavorite = true;
                        } else if (response.body().getData().getRestaurants().getFavourite() == 0) {
                            favourites.setBackground(getResources().getDrawable(R.drawable.favourite_white));
                            isFavorite = false;
                        }

                        containerRestaurantsDetails.setVisibility(View.VISIBLE);


                    } else {
                        dialog.dismiss();
                        llbotom.setVisibility(View.GONE);
                        containerRestaurantsDetails.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    dialog.dismiss();

                    llbotom.setVisibility(View.GONE);
                    containerRestaurantsDetails.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<NewRestaurantsDetailsResponse> call, Throwable t) {
                dialog.dismiss();
                llbotom.setVisibility(View.GONE);
                containerRestaurantsDetails.setVisibility(View.GONE);
            }
        });


    }


    public void alertDialogAllergy() {
        LayoutInflater factory = LayoutInflater.from(RestaurantDetailsActivity.this);
        final View mDialogView = factory.inflate(R.layout.popup_allergy, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(RestaurantDetailsActivity.this).create();
        alertDialog.setView(mDialogView);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //   final TextView ok_tv = (TextView)  mDialogView.findViewById(R.id.okTv);
        TextView tvRestaurantNumber = mDialogView.findViewById(R.id.tv_restaurantNumber);
        tvRestaurantNumber.setText(getString(R.string.restaurant_call_details) + " " + restaurantPhoneNumber + ".");
        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                try {
                    if (ContextCompat.checkSelfPermission(RestaurantDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(RestaurantDetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", restaurantPhoneNumber, null));
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    Toast.makeText(RestaurantDetailsActivity.this, "Call not available", Toast.LENGTH_SHORT).show();
                }
                alertDialog.dismiss();
            }
        });
        mDialogView.findViewById(R.id.cross_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    public void mGetRestaurantMenu(final String resID,
                                   final NewRestaurantsDetailsResponse data) {

        menuViewModel.getMenuCategoryList(resID).observe(this, new Observer<Menu>() {
            @Override
            public void onChanged(@Nullable Menu menu) {
                if (menu != null) {
                    setupViewPager(pager, menu, data, footerDetails);
                } else {
                    getMenuCategory(resID, data);
                }
            }
        });

    }

    private void getMenuCategory(final String resID, final NewRestaurantsDetailsResponse data) {
        dialog.show();
        RestaurantDetailsInterface apiInterface = ApiClient.getClient(this).create(RestaurantDetailsInterface.class);
        RestaurantDetailsRequest request = new RestaurantDetailsRequest();

        if (new SharedPreferencesClass(getApplicationContext()).isFirstTimeLaunch() != null && new SharedPreferencesClass(getApplicationContext()).isloginpref() != null)
            request.setUserId(val.getLoginResponse().getData().getUserId());
        else
            request.setUserId("");

        request.setPostCode(val.getPostCode());
        request.setRestaurantId(resID);
        if (serveStyle != null && !serveStyle.isEmpty())
            request.setServe_style(serveStyle);
        else
            request.setServe_style(new SharedPreferencesClass(this).getString(SERVE_STYLE));
        Call<Rough> call3 = apiInterface.mRestaurantCategory(PrefManager.getInstance(RestaurantDetailsActivity.this).getPreference(AUTH_TOKEN, ""), request);
        call3.enqueue(new Callback<Rough>() {
            @Override
            public void onResponse(Call<Rough> call, Response<Rough> response) {

                try {
                    dialog.dismiss();
                    if (response.body().getSuccess()) {
                        response.body().getData().getMenu().setRestaurantId(resID);


                        menuViewModel.insertMenu(response.body().getData().getMenu());
                    } else {
                        dialog.dismiss();
                        llbotom.setVisibility(View.GONE);
                        containerRestaurantsDetails.setVisibility(View.GONE);
                        Toast.makeText(RestaurantDetailsActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                    llbotom.setVisibility(View.GONE);
                    containerRestaurantsDetails.setVisibility(View.GONE);


                }
            }

            @Override
            public void onFailure(Call<Rough> call, Throwable t) {

                dialog.dismiss();
                llbotom.setVisibility(View.GONE);
                containerRestaurantsDetails.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void LoadMenuProduct(int parentPosition, String category_id, ProgressBar
            progressBar) {
        final String categoryId = category_id;
        final ProgressBar mProgressBar = progressBar;
        final int mParentPosition = parentPosition;

        new Thread(new Runnable() {
            @Override
            public void run() {
                MenuProducts.MenuProductsTable menuProducts = GlobalValues.getInstance().getDb().menuProductMaster().getMenuProduct(categoryId);
                if (menuProducts == null) {
                    getMenuCategoryProducts(mParentPosition, categoryId, mProgressBar);
                }
            }
        }).start();


    }

    private void loafSizeAndModifiers(final Boolean isCategory, final String productId,
                                      final int parentPosition, final int childPosition, final View qtyLayout,
                                      final TextView itemQtyView, final int itemCount, final int action, MenuCategory
                                              mMenuCategory, final ProgressBar progressBar) {
        final MenuCategory menuCategory = mMenuCategory;
        final ProgressBar mProgressBar = progressBar;
        final int mParentPosition = parentPosition;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressBar != null)
                    mProgressBar.setVisibility(View.VISIBLE);
            }
        });
        RestaurantDetailsInterface apiInterface = ApiClient.getClient(this).create(RestaurantDetailsInterface.class);
        MenuProductSizeModifierRequest request = new MenuProductSizeModifierRequest(productId, "");


        Call<ProductSizeAndModifier> call3 = apiInterface.getMenuProductSizeModifier(PrefManager.getInstance(RestaurantDetailsActivity.this).getPreference(AUTH_TOKEN, ""), request);
        call3.enqueue(new Callback<ProductSizeAndModifier>() {
            @Override
            public void onResponse(Call<ProductSizeAndModifier> call, Response<ProductSizeAndModifier> response) {
                final Response<ProductSizeAndModifier> mResponse = response;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (mResponse.body() != null && mResponse.body().getSuccess()) {
                            mResponse.body().getProductSizeAndModifier().setProductId(productId);
                            final Long id = GlobalValues.getInstance().getDb().productSizeAndModifierMaster().insert(mResponse.body().getProductSizeAndModifier());

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (mProgressBar != null)
                                        mProgressBar.setVisibility(View.GONE);
                                    if (id > 0) {
//                                        MenuFragment.getMenuFragment().menuAdapterNotifyItem(mParentPosition);
                                        if (isCategory) {
                                            checkModifierAndSizeInDb(isCategory, menuCategory.getMenuProducts().get(childPosition).getMenuProductId(), parentPosition, childPosition, qtyLayout, itemQtyView, itemCount, action, menuCategory, progressBar);
                                        } else {
                                            checkModifierAndSizeInDb(isCategory, menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getMenuProductId(), parentPosition, childPosition, qtyLayout, itemQtyView, itemCount, action, menuCategory, progressBar);
                                        }
                                    }
                                }
                            });

                        }
                    }
                }).start();


            }

            @Override
            public void onFailure(Call<ProductSizeAndModifier> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mProgressBar != null)
                            mProgressBar.setVisibility(View.GONE);
                    }
                });

            }
        });
    }

    private void getMenuCategoryProducts(int parentPosition, String category_id, ProgressBar progressBar) {
        final String categoryId = category_id;
        final ProgressBar mProgressBar = progressBar;
        final int mParentPosition = parentPosition;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(View.VISIBLE);
            }
        });
        RestaurantDetailsInterface apiInterface = ApiClient.getClient(this).create(RestaurantDetailsInterface.class);
        CategoryProductsRequest request = new CategoryProductsRequest(extras.getString("RESTAURANTID"), category_id, 0, serveStyle);
        Call<MenuProducts> call3 = apiInterface.mRestaurantCategoryProduct(PrefManager.getInstance(RestaurantDetailsActivity.this).getPreference(AUTH_TOKEN, ""), request);
        call3.enqueue(new Callback<MenuProducts>() {
            @Override
            public void onResponse(Call<MenuProducts> call, final Response<MenuProducts> response) {
                final Response<MenuProducts> mResponse = response;
                Log.e("menuSize", "" + mResponse.body().getData().getMenuProducts().size());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (mResponse.body().getSuccess()) {
                            mResponse.body().getData().setCategoryId(categoryId);
                            final Long id = GlobalValues.getInstance().getDb().menuProductMaster().insert(mResponse.body().getData());


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setVisibility(View.GONE);
                                    if (id > 0) {
                                        MenuFragment.getMenuFragment().menuAdapterNotifyItem(mParentPosition);
                                    }
                                }
                            });

                        }
                    }
                }).start();


            }

            @Override
            public void onFailure(Call<MenuProducts> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setVisibility(View.GONE);
                    }
                });

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dialog.dismiss();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        dialog.dismiss();
    }

    @Override
    public void OnSpecialOfferClick(int parentPosition, int childPosition, TextView
            itemQtyView, int itemCount, int action, SpecialOffer item) {
        if (menu.getSpecialOffers().size() > 0) {
            for (int i = 0; i < menu.getSpecialOffers().size(); i++) {
                if (menu.getSpecialOffers().get(i).getOfferId().equals(item.getOfferId())) {
                    menu.getSpecialOffers().get(i).setQuantity(itemCount);
                    return;
                }
            }
        }
        item.setQuantity(itemCount);
        db.insertSpecialOffer(item);
//        menu.getSpecialOffers().add(item);
        showPriceAndView(null, itemQtyView, itemCount);
    }


    //  (parentPosition, getLayoutPosition(), clickCount, item_count, itemQty, 2, menuCategory, progressBar);

    @Override
    public void OnCategoryClick(int parentPosition, int childPosition, View
            qtyLayout, TextView itemQtyView, int itemCount, int action, MenuCategory
                                        menuCategory, ProgressBar progressBar) {
        if (menuCategory.getMeal() != null) {
            checkModifierAndSizeInDb(true, menuCategory.getMeal().get(childPosition).getId(), parentPosition, childPosition, qtyLayout, itemQtyView, itemCount, action, menuCategory, progressBar);
        } else {
            checkModifierAndSizeInDb(true, menuCategory.getMenuProducts().get(childPosition).getMenuProductId(), parentPosition, childPosition, qtyLayout, itemQtyView, itemCount, action, menuCategory, progressBar);
        }

    }

    private void checkModifierAndSizeInDb(final Boolean isCategory, final String productId,
                                          final int parentPosition, final int childPosition, final View qtyLayout,
                                          final TextView itemQtyView, final int itemCount, final int action,
                                          final MenuCategory menuCategory, final ProgressBar progressBar) {
        if (menuCategory.getMeal() != null && menuCategory.getMeal().get(childPosition).getMealCategories() != null && menuCategory.getMeal().get(childPosition).getMealCategories().size() > 0) {
            updateCategoryUi(parentPosition, childPosition, qtyLayout, itemQtyView, itemCount, action, menuCategory);

        } else {
            if (isCategory) {
                if (menuCategory.getMenuProducts() != null && menuCategory.getMenuProducts().size() > 0
                        && menuCategory.getMenuProducts().get(childPosition).getProductModifiers() != null && menuCategory.getMenuProducts().get(childPosition).getMenuProductSize() != null) {
                    updateCategoryUi(parentPosition, childPosition, qtyLayout, itemQtyView, itemCount, action, menuCategory);
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final ProductSizeAndModifier.ProductSizeAndModifierTable productSizeAndModifierTable = GlobalValues.getInstance().getDb().productSizeAndModifierMaster().getProductSizeAndModifierList(productId);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (productSizeAndModifierTable != null) {
                                        menuCategory.getMenuProducts().get(childPosition).setProductModifiers(productSizeAndModifierTable.getProductModifiers());
                                        menuCategory.getMenuProducts().get(childPosition).setMenuProductSize(productSizeAndModifierTable.getMenuProductSize());
                                        updateCategoryUi(parentPosition, childPosition, qtyLayout, itemQtyView, itemCount, action, menuCategory);

                                    } else {
                                        loafSizeAndModifiers(isCategory, productId, parentPosition, childPosition, qtyLayout, itemQtyView, itemCount, action, menuCategory, progressBar);
                                    }
                                }
                            });

                        }
                    }).start();
                }
            } else {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final ProductSizeAndModifier.ProductSizeAndModifierTable productSizeAndModifierTable = GlobalValues.getInstance().getDb().productSizeAndModifierMaster().getProductSizeAndModifierList(productId);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (productSizeAndModifierTable != null) {
                                    menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).setProductModifiers(productSizeAndModifierTable.getProductModifiers());
                                    menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).setMenuProductSize(productSizeAndModifierTable.getMenuProductSize());

                                    updateSubCategoryUi(parentPosition, childPosition, qtyLayout, itemQtyView, itemCount, action, menuCategory);

                                } else {
                                    loafSizeAndModifiers(isCategory, productId, parentPosition, childPosition, qtyLayout, itemQtyView, itemCount, action, menuCategory, progressBar);
                                }
                            }
                        });
                    }
                }).start();
            }
        }
    }

    private void updateSubCategoryUi(int parentPosition, int childPosition, View
            qtyLayout, TextView itemQtyView, int itemCount, int action, MenuCategory menuCategory) {
        if (db.getMenuProductCount(menuCategory.getMenuSubCategory().get(parentPosition).getMenuCategoryId(),
                menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getMenuProductId()) > 0) {
            List<MenuProduct> menuProduct = db.getMenuProduct(menuCategory.getMenuSubCategory().get(parentPosition).getMenuCategoryId(), menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getMenuProductId());

            if (menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getProductModifiers().size() > 0 || menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getMenuProductSize().size() > 0) {
                if (action == 1) {
                    if (Integer.parseInt(itemQtyView.getText().toString()) == 1) {
                        db.deleteItem(Integer.parseInt(menuProduct.get(0).getMenuId()), Integer.parseInt(menuProduct.get(0).getId()));
                        qtyLayout.setVisibility(View.GONE);
                        itemQtyView.setText(String.valueOf(itemCount));
                        showPriceAndView(null, null, 0);
                    } else {
                        if (menuProduct.size() > 1) {
                            AgainFragment againFragment = AgainFragment.newInstance(this, childPosition, parentPosition, menuProduct, qtyLayout, itemQtyView, menuCategory, itemCount, action);
                            againFragment.show(getSupportFragmentManager(), "againDailog");
                        } else {
                            Double price = -1d;
                            if (menuProduct.get(0).getMenuProductSize() != null && menuProduct.get(0).getMenuProductSize().size() > 0) {
                                for (MenuProductSize item : menuProduct.get(0).getMenuProductSize()) {
                                    if (item.isSelected) {
                                        price = Double.parseDouble(item.getProductSizePrice());
                                    }
                                }
                            } else {
                                price = Double.parseDouble(menuProduct.get(0).getMenuProductPrice());
                            }
                            db.updateProductQuantity(Integer.parseInt(menuProduct.get(0).getId()), itemCount, price);
                            itemQtyView.setText(String.valueOf(itemCount));
                            showPriceAndView(null, null, 0);
                        }
                    }

                } else {
                   /* if(){

                    }*/
                    ChooseLastCustnizationDialog chooseLastCustnizationDialog = ChooseLastCustnizationDialog.newInstance(this, menuProduct, itemCount, qtyLayout, itemQtyView, parentPosition, childPosition, menuCategory, true, action, this);
                    chooseLastCustnizationDialog.show(getSupportFragmentManager(), "chooseLastCustnizationDialog");
                }
            } else {
                if (itemCount == 0) {
                    db.deleteItem(Integer.parseInt(menuProduct.get(0).getMenuId()), Integer.parseInt(menuProduct.get(0).getId()));
                    qtyLayout.setVisibility(View.GONE);
                    itemQtyView.setText(String.valueOf(itemCount));
                } else {
                    Double price = -1d;
                    if (menuProduct.get(0).getMenuProductSize() != null && menuProduct.get(0).getMenuProductSize().size() > 0) {
                        for (MenuProductSize item : menuProduct.get(0).getMenuProductSize()) {
                            if (item.isSelected) {
                                price = Double.parseDouble(item.getProductSizePrice());
                            }
                        }
                    } else {
                        price = Double.parseDouble(menuProduct.get(0).getMenuProductPrice());
                    }
                    db.updateProductQuantity(Integer.parseInt(menuProduct.get(0).getId()), itemCount, price);
                    itemQtyView.setText(String.valueOf(itemCount));
                }
                showPriceAndView(null, null, 0);
            }
        } else {
            if (menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getProductModifiers().size() > 0 || menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getMenuProductSize().size() > 0) {
                MenuDialogNew menuDialogNew = MenuDialogNew.newInstance(this, parentPosition, childPosition, qtyLayout, itemQtyView, itemCount, action, menuCategory, true, this);
                menuDialogNew.show(getSupportFragmentManager(), "menuDialogNew");
            } else {
                createCardData(parentPosition, childPosition, qtyLayout, itemQtyView, itemCount, action, true, menuCategory);
            }
        }
    }


    private void updateCategoryUi(int parentPosition, int childPosition, View
            qtyLayout, TextView itemQtyView, int itemCount, int action, MenuCategory menuCategory) {
        if (menuCategory.getMeal() != null) {
            if (db.getMenuProductCount(menuCategory.getMenuCategoryId(), menuCategory.getMeal().get(childPosition).getId()) > 0) {
                List<MenuProduct> menuProduct = db.getMenuProduct(menuCategory.getMenuCategoryId(), menuCategory.getMeal().get(childPosition).getId());

                if (action == 1) {
                    if (itemCount == 0) {
                        db.deleteItem(Integer.parseInt(menuProduct.get(0).getMenuId()), Integer.parseInt(menuProduct.get(0).getId()));
                        qtyLayout.setVisibility(View.GONE);
                        itemQtyView.setText(String.valueOf(itemCount));
                        showPriceAndView(null, null, 0);
                    } else {
                        if (menuProduct.size() > 1) {
                            AgainFragment againFragment = AgainFragment.newInstance(this, childPosition, parentPosition, menuProduct, qtyLayout, itemQtyView, menuCategory, itemCount, action);
                            againFragment.show(getSupportFragmentManager(), "againDailog");
                        } else {
                            Double price = -1d;
                            if (menuProduct.get(0).getMenuProductSize() != null && menuProduct.get(0).getMenuProductSize().size() > 0) {
                                for (MenuProductSize item : menuProduct.get(0).getMenuProductSize()) {
                                    if (item.isSelected) {
                                        price = Double.parseDouble(item.getProductSizePrice());
                                    }
                                }
                            } else {
                                price = Double.parseDouble(menuProduct.get(0).getMenuProductPrice());
                            }
                            db.updateProductQuantity(Integer.parseInt(menuProduct.get(0).getId()), itemCount, price);
                            itemQtyView.setText(String.valueOf(itemCount));
                            showPriceAndView(null, null, 0);
                        }
                    }

                } else {

                    if (qtyLayout.getVisibility() == View.GONE) {
                        qtyLayout.setVisibility(View.VISIBLE);
                        int qtyCount = 0;
                        for (MenuProduct product : menuProduct) {
                            qtyCount += product.getOriginalQuantity();
                        }
                        itemQtyView.setText(String.valueOf(qtyCount));
                    } else {
                        ChooseLastCustnizationDialog chooseLastCustnizationDialog = ChooseLastCustnizationDialog.newInstance(this, menuProduct, itemCount, qtyLayout, itemQtyView, parentPosition, childPosition, menuCategory, false, action, this);
                        chooseLastCustnizationDialog.show(getSupportFragmentManager(), "chooseLastCustnizationDialog");
                    }
                }
            } else {
                getMelProducts(menuCategory.getMeal().get(childPosition).getMealId());

                //  MenuMealDialog menuMealDialog = MenuMealDialog.newInstance(this, true, -1, -1, parentPosition, childPosition, qtyLayout, itemQtyView, itemCount, action, menuCategory, false, false, this);
                // menuMealDialog.show(getSupportFragmentManager(), "menuMealDailog");
            }
        } else {
            if (db.getMenuProductCount(menuCategory.getMenuCategoryId(), menuCategory.getMenuProducts().get(childPosition).getMenuProductId()) > 0) {
                List<MenuProduct> menuProduct = db.getMenuProduct(menuCategory.getMenuCategoryId(), menuCategory.getMenuProducts().get(childPosition).getMenuProductId());
                if (menuCategory.getMenuProducts().get(childPosition).getProductModifiers().size() > 0 || menuCategory.getMenuProducts().get(childPosition).getMenuProductSize().size() > 0) {

                    if (action == 1) {
                        if (Integer.parseInt(itemQtyView.getText().toString()) == 1) {
                            db.deleteItem(Integer.parseInt(menuProduct.get(0).getMenuId()), Integer.parseInt(menuProduct.get(0).getId()));
                            qtyLayout.setVisibility(View.GONE);
                            itemQtyView.setText(String.valueOf(itemCount));
                            showPriceAndView(null, null, 0);
                        } else {
                            if (menuProduct.size() > 1) {
                                AgainFragment againFragment = AgainFragment.newInstance(this, childPosition, parentPosition, menuProduct, qtyLayout, itemQtyView, menuCategory, itemCount, action);
                                againFragment.show(getSupportFragmentManager(), "againDailog");
                            } else {
                                Double price = -1d;
                                if (menuProduct.get(0).getMenuProductSize() != null && menuProduct.get(0).getMenuProductSize().size() > 0) {
                                    for (MenuProductSize item : menuProduct.get(0).getMenuProductSize()) {
                                        if (item.isSelected) {
                                            price = Double.parseDouble(item.getProductSizePrice());
                                        }
                                    }
                                } else {
                                    price = Double.parseDouble(menuProduct.get(0).getMenuProductPrice());
                                }
                                db.updateProductQuantity(Integer.parseInt(menuProduct.get(0).getId()), itemCount, price);
                                itemQtyView.setText(String.valueOf(itemCount));
                                showPriceAndView(null, null, 0);
                            }
                        }
                    } else {
                        if (qtyLayout.getVisibility() == View.GONE) {
                            qtyLayout.setVisibility(View.VISIBLE);
                            int qtyCount = 0;
                            for (MenuProduct product : menuProduct) {
                                qtyCount += product.getOriginalQuantity();
                            }
                            itemQtyView.setText(String.valueOf(qtyCount));
                        } else {
                            ChooseLastCustnizationDialog chooseLastCustnizationDialog = ChooseLastCustnizationDialog.newInstance(this, menuProduct, itemCount, qtyLayout, itemQtyView, parentPosition, childPosition, menuCategory, false, action, this);
                            chooseLastCustnizationDialog.show(getSupportFragmentManager(), "chooseLastCustnizationDialog");
                        }
                    }
                } else {
                    if (itemCount == 0) {
                        db.deleteItem(Integer.parseInt(menuProduct.get(0).getMenuId()), Integer.parseInt(menuProduct.get(0).getId()));
                        qtyLayout.setVisibility(View.GONE);
                        itemQtyView.setText(String.valueOf(itemCount));
                    } else {
                        Double price = -1d;
                        if (menuProduct.get(0).getMenuProductSize() != null && menuProduct.get(0).getMenuProductSize().size() > 0) {
                            for (MenuProductSize item : menuProduct.get(0).getMenuProductSize()) {
                                if (item.isSelected) {
                                    price = Double.parseDouble(item.getProductSizePrice());
                                }
                            }
                        } else {
                            price = Double.parseDouble(menuProduct.get(0).getMenuProductPrice());
                        }
                        db.updateProductQuantity(Integer.parseInt(menuProduct.get(0).getId()), itemCount, price);
                        itemQtyView.setText(String.valueOf(itemCount));
                    }
                    showPriceAndView(null, null, 0);
                }

            } else {

                if (menuCategory.getMenuProducts().get(childPosition).getProductModifiers().size() > 0 || menuCategory.getMenuProducts().get(childPosition).getMenuProductSize().size() > 0) {
                    MenuDialogNew menuDialogNew = MenuDialogNew.newInstance(this, parentPosition, childPosition, qtyLayout, itemQtyView, itemCount, action, menuCategory, false, this);
                    menuDialogNew.show(getSupportFragmentManager(), "menuDialogNew");
                } else {
                    createCardData(parentPosition, childPosition, qtyLayout, itemQtyView, itemCount, action, false, menuCategory);
                }

            }
        }

    }

    private void updateItemQty(View view, TextView qtyTextView, List<MenuProduct> mProduct) {
        int itemQty = 0;
        for (MenuProduct product : mProduct) {
            itemQty += product.getOriginalQuantity();
        }
        if (itemQty == 0) {
            view.setVisibility(View.GONE);
        }
        qtyTextView.setText(String.valueOf(itemQty));

        showPriceAndView(null, null, 0);
    }

    @Override
    public void onMultiTimeItemChange(int childPosition, int parentPosition, List<
            MenuProduct> menuProduct, View view, TextView qtyTextView, MenuCategory menuCategory,
                                      int itemCount, int action) {
        if (menuCategory.getMeal() != null && db.getMenuProductCount(menuCategory.getMenuCategoryId(), menuCategory.getMeal().get(childPosition).getId()) > 0) {
            List<MenuProduct> mProduct = db.getMenuProduct(menuCategory.getMenuCategoryId(), menuCategory.getMeal().get(childPosition).getId());
            updateItemQty(view, qtyTextView, mProduct);

        } else {

            if (menuCategory.getMenuProducts() != null && menuCategory.getMenuProducts().size() > 0 && db.getMenuProductCount(menuCategory.getMenuCategoryId(), menuCategory.getMenuProducts().get(childPosition).getMenuProductId()) > 0) {
                List<MenuProduct> mProduct = db.getMenuProduct(menuCategory.getMenuCategoryId(), menuCategory.getMenuProducts().get(childPosition).getMenuProductId());
                updateItemQty(view, qtyTextView, mProduct);

            } else {
                if (menuCategory.getMenuSubCategory().size() > 0 && menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().size() > 0) {
                    if (db.getMenuProductCount(menuCategory.getMenuSubCategory().get(parentPosition).getMenuCategoryId(), menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getMenuProductId()) > 0) {
                        List<MenuProduct> mProduct = db.getMenuProduct(menuCategory.getMenuSubCategory().get(parentPosition).getMenuCategoryId(), menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getMenuProductId());
                        updateItemQty(view, qtyTextView, mProduct);
                    } else {
                        view.setVisibility(View.GONE);
                        qtyTextView.setText("0");
                        showPriceAndView(null, null, 0);
                    }
                } else {
                    view.setVisibility(View.GONE);
                    qtyTextView.setText("0");
                    showPriceAndView(null, null, 0);
                }
            }
        }
    }

    @Override
    public void OnSubCategoryClick(int parentPosition, int childPosition, View
            qtyLayout, TextView itemQtyView, int itemCount, int action, MenuCategory
                                           menuCategory, ProgressBar progressBar) {

        checkModifierAndSizeInDb(false, menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getMenuProductId(), parentPosition, childPosition, qtyLayout, itemQtyView, itemCount, action, menuCategory, progressBar);


    }

    @Override
    public void onRepeatLast(int position, int parentPosition,
                             List<MenuProduct> menuProduct, View view, TextView qtyTextView, MenuCategory menuCategory,
                             int itemCount, Boolean isSubCat, int action) {
        if (menuProduct.size() > 1) {
            AgainFragment againFragment = AgainFragment.newInstance(this, position, parentPosition, menuProduct, view, qtyTextView, menuCategory, itemCount, action);
            againFragment.show(getSupportFragmentManager(), "againDailog");
        } else {
            if (action == 2) {
                int qty = (Integer.parseInt(qtyTextView.getText().toString()) + 1);
                Double price = -1d;
                if (menuProduct.get(0).getMenuProductSize() != null && menuProduct.get(0).getMenuProductSize().size() > 0) {
                    for (MenuProductSize item : menuProduct.get(0).getMenuProductSize()) {
                        if (item.isSelected) {
                            price = Double.parseDouble(item.getProductSizePrice());
                        }
                    }
                } else {
                    price = Double.parseDouble(menuProduct.get(0).getMenuProductPrice());
                }
                db.updateProductQuantity(Integer.parseInt(menuProduct.get(0).getId()), qty, price);
            } else {
                Double price = -1d;
                if (menuProduct.get(0).getMenuProductSize() != null && menuProduct.get(0).getMenuProductSize().size() > 0) {
                    for (MenuProductSize item : menuProduct.get(0).getMenuProductSize()) {
                        if (item.isSelected) {
                            price = Double.parseDouble(item.getProductSizePrice());
                        }
                    }
                } else {
                    price = Double.parseDouble(menuProduct.get(0).getMenuProductPrice());
                }
                int qty = (Integer.parseInt(qtyTextView.getText().toString()) - 1);
                if (qty == 0) {
                    db.deleteItem(Integer.parseInt(menuProduct.get(0).getMenuId()), Integer.parseInt(menuProduct.get(0).getId()));
                } else {
                    db.updateProductQuantity(Integer.parseInt(menuProduct.get(0).getId()), qty, price);
                }
            }
            showPriceAndView(view, qtyTextView, itemCount);
        }
    }

    @Override
    public void onChooseAgain(int position, int parentPosition, List<
            MenuProduct> menuProduct, View view, TextView qtyTextView, MenuCategory menuCategory,
                              int itemCount, Boolean isSubCat, int action) {

        if (menuCategory.getMeal() != null) {
            getMelProducts(menuCategory.getMeal().get(position).getMealId());

            // MenuMealDialog menuMealDialog = MenuMealDialog.newInstance(this, true, -1, -1, parentPosition, position, view, qtyTextView, itemCount, action, menuCategory, false, false, this);
            // menuMealDialog.show(getSupportFragmentManager(), "menuMealDailog");
        } else {
            MenuDialogNew menuDialogNew = MenuDialogNew.newInstance(this, parentPosition, position, view, qtyTextView, itemCount, action, menuCategory, isSubCat, this);
            menuDialogNew.show(getSupportFragmentManager(), "menuDialogNew");
        }
    }

    @Override
    public void OnAddItem(int parentPosition, int childPosition, View qtyLayout, TextView
            itemQtyView, int itemCount, int action, MenuCategory menuCategory) {

        if (menuCategory.getMeal() != null) {

            if (db.getMenuProductCount(menuCategory.getMenuCategoryId(), menuCategory.getMeal().get(childPosition).getId()) > 0) {
                List<MenuProduct> menuProduct = db.getMenuProduct(menuCategory.getMenuCategoryId(), menuCategory.getMeal().get(childPosition).getId());

                int itemQty = 0;
                for (MenuProduct product : menuProduct) {
                    itemQty += product.getOriginalQuantity();
                }
                if (itemQty == 0) {
                    qtyLayout.setVisibility(View.GONE);
                } else {
                    if (qtyLayout.getVisibility() == View.GONE) {
                        qtyLayout.setVisibility(View.VISIBLE);
                    }
                }
                itemQtyView.setText(String.valueOf(itemQty));
                showPriceAndView(null, null, 0);
            } else {
                if (menuCategory.getMenuSubCategory().size() > 0) {
                    if (db.getMenuProductCount(menuCategory.getMenuSubCategory().get(parentPosition).getMenuCategoryId(), menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getMenuProductId()) > 0) {
                        List<MenuProduct> mProduct = db.getMenuProduct(menuCategory.getMenuSubCategory().get(parentPosition).getMenuCategoryId(), menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getMenuProductId());

                        int itemQty = 0;
                        for (MenuProduct product : mProduct) {
                            itemQty += product.getOriginalQuantity();
                        }
                        if (itemQty == 0) {
                            qtyLayout.setVisibility(View.GONE);
                        } else {
                            qtyLayout.setVisibility(View.VISIBLE);
                        }
                        itemQtyView.setText(String.valueOf(itemQty));


                        showPriceAndView(null, null, 0);
                    } else {
                        qtyLayout.setVisibility(View.GONE);
                        itemQtyView.setText("0");
                        showPriceAndView(null, null, 0);
                    }
                } else {

                    showPriceAndView(qtyLayout, itemQtyView, itemCount);
                }

            }


        } else {

            if (db.getMenuProductCount(menuCategory.getMenuCategoryId(), menuCategory.getMenuProducts().get(childPosition).getMenuProductId()) > 0) {
                List<MenuProduct> menuProduct = db.getMenuProduct(menuCategory.getMenuCategoryId(), menuCategory.getMenuProducts().get(childPosition).getMenuProductId());

                int itemQty = 0;
                for (MenuProduct product : menuProduct) {
                    itemQty += product.getOriginalQuantity();
                }
                if (itemQty == 0) {
                    qtyLayout.setVisibility(View.GONE);
                } else {
                    if (qtyLayout.getVisibility() == View.GONE) {
                        qtyLayout.setVisibility(View.VISIBLE);
                    }
                }
                itemQtyView.setText(String.valueOf(itemQty));
                showPriceAndView(null, null, 0);
            } else {
                if (menuCategory.getMenuSubCategory().size() > 0) {
                    if (db.getMenuProductCount(menuCategory.getMenuSubCategory().get(parentPosition).getMenuCategoryId(), menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getMenuProductId()) > 0) {
                        List<MenuProduct> mProduct = db.getMenuProduct(menuCategory.getMenuSubCategory().get(parentPosition).getMenuCategoryId(), menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getMenuProductId());

                        int itemQty = 0;
                        for (MenuProduct product : mProduct) {
                            itemQty += product.getOriginalQuantity();
                        }
                        if (itemQty == 0) {
                            qtyLayout.setVisibility(View.GONE);
                        } else {
                            qtyLayout.setVisibility(View.VISIBLE);
                        }
                        itemQtyView.setText(String.valueOf(itemQty));


                        showPriceAndView(null, null, 0);
                    } else {
                        qtyLayout.setVisibility(View.GONE);
                        itemQtyView.setText("0");
                        showPriceAndView(null, null, 0);
                    }
                } else {

                    showPriceAndView(qtyLayout, itemQtyView, itemCount);
                }

            }
        }
    }

    @Override
    public void OnMealProductClick(Dialog dialog, int childParentPosition,
                                   int selectedChildPosition, int parentPosition, int childPosition, View qtyLayout, TextView
                                           item_count, int itemCount, int action, MenuCategory
                                           menuCategory, ProductSizeAndModifier.ProductSizeAndModifierTable
                                           productSizeAndModifierTable, Boolean isSubCat) {

        openMealProductModifierDialog(childParentPosition, selectedChildPosition, parentPosition, childPosition, qtyLayout, item_count, itemCount, action, menuCategory, productSizeAndModifierTable, isSubCat);

        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void loadMealProductData(Dialog dialog, String productId, String
            productSizeId, ProgressBar progressBar, int childParentPosition, int selectedChildPosition,
                                    int parentPosition, int childPosition, View qtyLayout, TextView item_count, int itemCount,
                                    int action, MenuCategory menuCategory, Boolean isSubCat) {
        loadMealProductDataFromApi(dialog, productId, productSizeId, progressBar, childParentPosition, selectedChildPosition, parentPosition, childPosition, qtyLayout, item_count, itemCount, action, menuCategory, isSubCat);

    }

    @Override
    public void OnMealProductModifierSelected(Boolean onDone, int childParentPosition,
                                              int selectedChildPosition, int parentPosition, int childPosition, View qtyLayout, TextView
                                                      item_count, int itemCount, int action, MenuCategory menuCategory, Boolean isSubCat, Boolean enableScrollToPosition) {
        // mItem.get(parentPosition).setSelectedCount(mItem.get(parentPosition).getSelectedCount() + 1);
        if (onDone) {
            MenuMealDialog menuMealDialog = MenuMealDialog.newInstance(this, false, childParentPosition, selectedChildPosition, parentPosition, childPosition, qtyLayout, item_count, itemCount, action, menuCategory, false, enableScrollToPosition, this);
            menuMealDialog.show(getSupportFragmentManager(), "menuMealDailog");
        }
    }


    private void openMealProductModifierDialog(int childParentPosition,
                                               int selectedChildPosition, int parentPosition, int childPosition, View qtyLayout, TextView
                                                       item_count, int itemCount, int action, MenuCategory
                                                       menuCategory, ProductSizeAndModifier.ProductSizeAndModifierTable
                                                       productSizeAndModifierTable, Boolean isSubCat) {
        MealProductModifierDialog mealProductModifierDialog = MealProductModifierDialog.newInstance(this, childParentPosition, selectedChildPosition, parentPosition, childPosition, qtyLayout, item_count, itemCount, action, menuCategory, true, productSizeAndModifierTable, this);
        mealProductModifierDialog.setCancelable(false);
        mealProductModifierDialog.show(getSupportFragmentManager(), "mealProductModifierDialog");
    }

    private void createCardData(int parentPosition, int childPosition, View
            qtyLayout, TextView item_count, int itemCount, int action, boolean isSubCat, MenuCategory
                                        menuCategory) {
        Gson gson = new Gson();

        long id = db.getMenuCategoryIfExit(menuCategory.getMenuCategoryId());
        if (id == -1) {
            id = db.insertMenuCategory(menuCategory.getMenuCategoryId(), menuCategory.getMenuCategoryName(), gson.toJson(menuCategory.getMenuSubCategory()), gson.toJson(menuCategory.getMenuProducts()));
        }
        long subCatId = -1;
        if (isSubCat) {
            subCatId = db.getMenuSubCategoryIfExit(menuCategory.getMenuSubCategory().get(parentPosition).getMenuCategoryId());
            if (subCatId == -1) {
                subCatId = db.insertMenuSubCategory(id, menuCategory.getMenuCategoryId(), menuCategory.getMenuSubCategory().get(parentPosition).getMenuCategoryId(), menuCategory.getMenuSubCategory().get(parentPosition).getMenuCategoryName());
            }

            for (int i = 0; i < menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().size(); i++) {
                if (i == childPosition) {
                    db.insertMenuProduct(id, subCatId, menuCategory.getMenuSubCategory().get(parentPosition).getMenuCategoryId(),
                            menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getMenuProductId(),
                            menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getProductName(),
                            menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getVegType(),
                            menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getMenuProductPrice(),
                            menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getUserappProductImage(),
                            menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getEcomProductImage(),
                            menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getProductOverallRating(),
                            1,
                            gson.toJson(menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getMenuProductSize()),
                            gson.toJson(menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getProductModifiers()),
                            null,
                            1,
                            Double.parseDouble(menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getMenuProductPrice()),
                            menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getMenuProductPrice()
                            /*gson.toJson(menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getUpsells())*/);
                }
            }
        } else {
            for (int i = 0; i < menuCategory.getMenuProducts().size(); i++) {
                if (i == childPosition) {
                    db.insertMenuProduct(id, subCatId, menuCategory.getMenuCategoryId(),
                            menuCategory.getMenuProducts().get(i).getMenuProductId(),
                            menuCategory.getMenuProducts().get(i).getProductName(),
                            menuCategory.getMenuProducts().get(i).getVegType(),
                            menuCategory.getMenuProducts().get(i).getMenuProductPrice(),
                            menuCategory.getMenuProducts().get(i).getUserappProductImage(),
                            menuCategory.getMenuProducts().get(i).getEcomProductImage(),
                            menuCategory.getMenuProducts().get(i).getProductOverallRating(),
                            itemCount,
                            gson.toJson(menuCategory.getMenuProducts().get(i).getMenuProductSize()),
                            gson.toJson(menuCategory.getMenuProducts().get(i).getProductModifiers()),
                            null,
                            1,
                            Double.parseDouble(menuCategory.getMenuProducts().get(i).getMenuProductPrice()),
                            menuCategory.getMenuProducts().get(i).getMenuProductPrice()
                            /* gson.toJson(menuCategory.getMenuProducts().get(i).getUpsells())*/);
                }
            }
        }
        if (qtyLayout.getVisibility() == View.GONE) {
            qtyLayout.setVisibility(View.VISIBLE);
        }
        item_count.setText(String.valueOf(itemCount));


        showPriceAndView(null, null, 0);
    }

    private void showPriceAndView(View qtyLayout, TextView item_count, int itemCount) {
        totalCartIterm = 0;
        if (qtyLayout != null) {
            if (itemCount == 0) {
                qtyLayout.setVisibility(View.GONE);
            } else {
                if (qtyLayout.getVisibility() == View.GONE) {
                    qtyLayout.setVisibility(View.VISIBLE);
                }
            }
            item_count.setText(String.valueOf(itemCount));
        }
        List<SpecialOffer> specialOffers = db.getSpecialOffer();

        List<MenuProduct> menuProducts = db.getMenuProduct();


        if (menuProducts != null) {
            Log.e("ANAND >>", menuProducts.toString());

            Double totalPrice = 0d;
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
//                                        if (menuProductSize1.getProductSizePrice() != null)
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
            if (totalCartIterm > 0) {
                footerCount.setText(String.valueOf(totalCartIterm));
                footerDetails.setText(String.format("%.2f", totalPrice));
                if (Double.parseDouble(footerDetails.getText().toString()) < minimumValue) {
                    llbotom.setBackgroundColor(getResources().getColor(R.color.gray));
                    footerCount.setVisibility(View.GONE);
                    tvCurrency.setVisibility(View.GONE);
                    footerDetails.setVisibility(View.GONE);
                    txtEmptyBasket.setText("Spend ??" + String.format("%.2f", minimumValue - Double.parseDouble(footerDetails.getText().toString())) + " more to proceed");
                    llbotom.setVisibility(View.VISIBLE);
                } else {

                    llbotom.setBackgroundColor(getResources().getColor(R.color.orange));
                    footerCount.setVisibility(View.VISIBLE);
                    tvCurrency.setVisibility(View.VISIBLE);
                    footerDetails.setVisibility(View.VISIBLE);

                    if (sharePre.isloginpref() != null) {
                        if (isFromLogin)
                            txtEmptyBasket.setText(getResources().getString(R.string.continue_));
                        else
                            txtEmptyBasket.setText(getResources().getString(R.string.view_basket));
                    } else {
                        txtEmptyBasket.setText(getResources().getString(R.string.continue_));
                    }


                    llbotom.setVisibility(View.VISIBLE);
                }
            } else {
                llbotom.setVisibility(View.VISIBLE);
                llbotom.setBackground(getResources().getDrawable(R.color.gray_light));
                txtEmptyBasket.setText(getString(R.string.your_basket_is_empty));
                footerCount.setText("0");
                footerDetails.setText("0.00");
            }

        }

    }

    public void dialogNoInternetConnection(String message) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.addnote_success_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setView(mDialogView);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);

        TextView tvMessage = mDialogView.findViewById(R.id.message);
        tvMessage.setText(message);
        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetOn(RestaurantDetailsActivity.this)) {
                    alertDialog.dismiss();
                    startActivity(new Intent(RestaurantDetailsActivity.this, RestaurantDetailsActivity.class));
                } else mDialogView.findViewById(R.id.okTv).startAnimation(animShake);

            }
        });

        alertDialog.show();
    }


    private void loadMealProductDataFromApi(Dialog dialog, final String productId,
                                            final String productSizeId, ProgressBar progressBar, final int childParentPosition,
                                            final int selectedChildPosition, final int parentPosition, final int childPosition,
                                            final View qtyLayout, final TextView item_count, final int itemCount, final int action,
                                            final MenuCategory menuCategory, final Boolean isSubCat) {
        final ProgressBar mProgressBar = progressBar;
        final Dialog mDialog = dialog;
       /* if (mProgressBar == null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }*/


        RestaurantDetailsInterface apiInterface = ApiClient.getClient(this).create(RestaurantDetailsInterface.class);
        MenuProductSizeModifierRequest request = new MenuProductSizeModifierRequest(productId, productSizeId);


        Call<ProductSizeAndModifier> call3 = apiInterface.getMenuProductSizeModifier(PrefManager.getInstance(RestaurantDetailsActivity.this).getPreference(AUTH_TOKEN, ""), request);
        call3.enqueue(new Callback<ProductSizeAndModifier>() {
            @Override
            public void onResponse(Call<ProductSizeAndModifier> call, Response<ProductSizeAndModifier> response) {
                final Response<ProductSizeAndModifier> mResponse = response;

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if (mResponse.body().getSuccess()) {
                            mResponse.body().getProductSizeAndModifier().setProductId(productId);
                            final Long id = GlobalValues.getInstance().getDb().productSizeAndModifierMaster().insert(mResponse.body().getProductSizeAndModifier());
                            final ProductSizeAndModifier.ProductSizeAndModifierTable productSizeAndModifierTable = GlobalValues.getInstance().getDb().productSizeAndModifierMaster().getProductSizeAndModifierList(menuCategory.getMeal().get(childPosition).getMealCategories().get(childParentPosition).getMealProducts().get(selectedChildPosition).getProductId());

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (mProgressBar != null)
                                        mProgressBar.setVisibility(View.GONE);
                                    if (mResponse.body().getProductSizeAndModifier().getMenuProductSize().size() > 0) {
                                        if (mResponse.body().getProductSizeAndModifier().getMenuProductSize().get(0).getSizeModifiers().size() > 0) {
                                            if (mDialog != null)
                                                mDialog.dismiss();
                                            if (id > 0) {
                                                openMealProductModifierDialog(childParentPosition, selectedChildPosition, parentPosition, childPosition, qtyLayout, item_count, itemCount, action, menuCategory, productSizeAndModifierTable, isSubCat);
                                            }
                                        } else {
                                            if (MenuMealDialog.getInstance().mealProductCategoryAdapter != null) {
                                                MenuMealDialog.getInstance().mealProductCategoryAdapter.notifyDataSetChanged();
//                                                MenuMealDialog.getInstance().listMealProductCategory.smoothScrollToPosition(MenuMealDialog.getInstance().mealProductCategoryAdapter.getLastGonePosition() + 2);

                                            }
                                        }
                                    } else {
                                        if (MenuMealDialog.getInstance().mealProductCategoryAdapter != null) {
                                            MenuMealDialog.getInstance().mealProductCategoryAdapter.notifyDataSetChanged();
//                                            MenuMealDialog.getInstance().listMealProductCategory.smoothScrollToPosition(MenuMealDialog.getInstance().mealProductCategoryAdapter.getLastGonePosition() + 2);
                                        }
                                    }
                                }
                            });

                        }
                    }
                }).start();

            }

            @Override
            public void onFailure(Call<ProductSizeAndModifier> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       /* if (mProgressBar != null)
                            mProgressBar.setVisibility(View.GONE);*/
                    }
                });

            }
        });

    }


    public void restaurantClosedDialog() {
        LayoutInflater factory = LayoutInflater.from(RestaurantDetailsActivity.this);
        final View mDialogVieww = factory.inflate(R.layout.layout_closed_dialog, null);
        final AlertDialog alertClodseDialog = new AlertDialog.Builder(RestaurantDetailsActivity.this).create();
        alertClodseDialog.setView(mDialogVieww);
        alertClodseDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialogVieww.findViewById(R.id.tv_btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                alertClodseDialog.dismiss();
            }
        });


        alertClodseDialog.show();
    }


    private void getRestaurantHygieneRating(final String resID, final NewRestaurantsDetailsResponse body) {
        //  dialog.show();
        RestaurantDetailsInterface apiInterface = ApiClient.getClient(this).create(RestaurantDetailsInterface.class);
        RestaurantDetailsRequest request = new RestaurantDetailsRequest();

        request.setName(restaurantName);
        //  request.setRestaurantId(restaurantId);
        request.setPostcode(restaurantPostCode);

        Call<HygieneRatingModel> call3 = apiInterface.getRestaurantHygieneRating(PrefManager.getInstance(RestaurantDetailsActivity.this).getPreference(AUTH_TOKEN, ""), request);
        call3.enqueue(new Callback<HygieneRatingModel>() {
            @Override
            public void onResponse(Call<HygieneRatingModel> call, Response<HygieneRatingModel> response) {

                try {
                    hygieneRatingModel = new HygieneRatingModel();
                    hygieneRatingModel = response.body();
                    mGetRestaurantMenu(resID, body);
                    if (response.body().isSuccess()) {

                    } else {

                    }
                } catch (Exception e) {
                    Log.d("Hygiene Error", "" + e);
                }
            }

            @Override
            public void onFailure(Call<HygieneRatingModel> call, Throwable t) {
                hygieneRatingModel = null;
                mGetRestaurantMenu(resID, body);
                Log.d("Hygiene Error", "" + t);
                // dialog.dismiss();

            }
        });
    }

    public void addFavourites() {

        AddFavouritesInterface apiInterface = ApiClient.getClient(this).create(AddFavouritesInterface.class);
        final AddFavouristeResquest request = new AddFavouristeResquest();
        request.setUserId(sharePre.getString(sharePre.USER_ID));
        request.setEntityId(restaurantId);
        request.setEntityType("restaurant");
        Call<AddFavouristeResponse> call3 = apiInterface.mAddFavourites(PrefManager.getInstance(RestaurantDetailsActivity.this).getPreference(AUTH_TOKEN, ""), request);
        call3.enqueue(new Callback<AddFavouristeResponse>() {
            @Override
            public void onResponse(Call<AddFavouristeResponse> call, Response<AddFavouristeResponse> response) {
                try {
                    if (response.body().getSuccess()) {
                        if (response.body().getData().getFavouriteStatus() == 1) {
                            favourites.setBackground(getResources().getDrawable(R.drawable.favourite_active));
                            isFavorite = true;
                        } else if (response.body().getData().getFavouriteStatus() == 0) {
                            favourites.setBackground(getResources().getDrawable(R.drawable.favourite_white));
                            isFavorite = false;
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


    public void loginDialog() {
        LayoutInflater factory = LayoutInflater.from(RestaurantDetailsActivity.this);
        final View mDialogVieww = factory.inflate(R.layout.layout_login_dialog, null);
        final AlertDialog alertClodseDialog = new AlertDialog.Builder(RestaurantDetailsActivity.this).create();
        alertClodseDialog.setView(mDialogVieww);
        alertClodseDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mDialogVieww.findViewById(R.id.tv_btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RestaurantDetailsActivity.this, LoginActivity.class);
                i.putExtra("IS_RESTAURENT", true);
                i.putExtra("RESTAURANTID", restaurantId);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                alertClodseDialog.dismiss();
            }
        });
        mDialogVieww.findViewById(R.id.tv_btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                alertClodseDialog.dismiss();
            }
        });


        alertClodseDialog.show();
    }

    /*public void openRestaurantSelection() {
        LayoutInflater factory = LayoutInflater.from(RestaurantDetailsActivity.this);
        final View mDialogVieww = factory.inflate(R.layout.layout_restaurent_dialog, null);
        final AlertDialog alertClodseDialog = new AlertDialog.Builder(RestaurantDetailsActivity.this).create();
        alertClodseDialog.setView(mDialogVieww);
        alertClodseDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mDialogVieww.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                alertClodseDialog.dismiss();
            }
        });


        alertClodseDialog.show();
    }
*/


    private void getMelProducts(String mealId) {

        dialogUtils.showDialog("Please Wait..");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("product_id", mealId);


        RestaurantDetailsInterface apiInterface = ApiClient.getClient(this).create(RestaurantDetailsInterface.class);

        Call<MealDetailList> call3 = apiInterface.getMealDealItems(PrefManager.getInstance(this).getPreference(AUTH_TOKEN, ""), jsonObject);
        call3.enqueue(new Callback<MealDetailList>() {
            @Override
            public void onResponse(Call<MealDetailList> call, final Response<MealDetailList> response) {
                final Response<MealDetailList> mResponse = response;

                if (mResponse.isSuccessful()) {
                    MealDetailList meaListBean = response.body();

                    new MenuDetailDialog(RestaurantDetailsActivity.this, meaListBean.getData()).show();
                    dialogUtils.closeDialog();
/*

                    if (meaListBean.getData().getMeal_config() != null && meaListBean.getData().getMeal_config().size() > 0) {
                        if (menuCategory.getMeal() != null && menuCategory.getMeal().size() > 0) {
                            for (int i = 0; i < menuCategory.getMeal().size(); i++) {
                                if (menuCategory.getMeal().get(i).getMealId().equalsIgnoreCase(mealId)) {
                                    for (int j = 0; j < meaListBean.getData().getMeal_config().size(); j++) {

                                       // menuCategory.getMeal().get(i).getMealCategories().get(j).setCategoryName("Testing");
                                        menuCategory.getMeal().get(i).getMealCategories().get(j).setCategoryName(meaListBean.getData().getMeal_config().get(j).getName());
                                        menuCategory.getMeal().get(i).getMealCategories().get(j).setQuantity(meaListBean.getData().getMeal_config().get(j).getAllowed_quantity());
                                        menuCategory.getMeal().get(i).getMealCategories().get(j).setAllowed_quantity(meaListBean.getData().getMeal_config().get(j).getAllowed_quantity());
                                        menuCategory.getMeal().get(i).getMealCategories().get(j).setCustomizable(meaListBean.getData().getMeal_config().get(j).getIs_customizable());
                                        for (int k = 0; k < meaListBean.getData().getMeal_config().get(j).getProducts().size(); k++) {
                                            menuCategory.getMeal().get(i).getMealCategories().get(j).getMealProducts().get(k).setId(meaListBean.getData().getMeal_config().get(j).getProducts().get(k).getId());
                                            menuCategory.getMeal().get(i).getMealCategories().get(j).getMealProducts().get(k).setMealId(meaListBean.getData().getMeal_config().get(j).getProducts().get(k).getMenu_product_id());
                                            menuCategory.getMeal().get(i).getMealCategories().get(j).getMealProducts().get(k).setProductId(meaListBean.getData().getMeal_config().get(j).getProducts().get(k).getProduct_id());
                                            menuCategory.getMeal().get(i).getMealCategories().get(j).getMealProducts().get(k).setProductSizeId(meaListBean.getData().getMeal_config().get(j).getProducts().get(k).getProduct_size_id());
                                            menuCategory.getMeal().get(i).getMealCategories().get(j).getMealProducts().get(k).setProductName(meaListBean.getData().getMeal_config().get(j).getProducts().get(k).getProduct_name());
                                            menuCategory.getMeal().get(i).getMealCategories().get(j).getMealProducts().get(k).setProductSizeName(meaListBean.getData().getMeal_config().get(j).getProducts().get(k).getProduct_size_name());
                                            menuCategory.getMeal().get(i).getMealCategories().get(j).getMealProducts().get(k).setQuantity(meaListBean.getData().getMeal_config().get(j).getProducts().get(k).getAllowed_quantity());


                                        }

                                    }
                                }
                            }


                            MenuMealDialog menuMealDialog = MenuMealDialog.newInstance(RestaurantDetailsActivity.this, true, -1, -1, parentPosition, childPosition, qtyLayout, item_count, itemCount, action, menuCategory, false, false, RestaurantDetailsActivity.this);
                            menuMealDialog.show(getSupportFragmentManager(), "menuMealDailog");

                        }
                    }
*/

                } else {
                    dialogUtils.closeDialog();
                }


            }

            @Override
            public void onFailure(Call<MealDetailList> call, Throwable t) {

                dialogUtils.closeDialog();
            }
        });
    }


    public void showFinalPriceAndView() {

        mDb.saveOrderHistry().loadAllHistory().observe(RestaurantDetailsActivity.this, new androidx.lifecycle.Observer<List<OrderSaveModel>>() {
            @Override
            public void onChanged(@Nullable List<OrderSaveModel> orderSaveModelList) {
                if (orderSaveModelList.size() > 0) {
                    footerCount.setText(getNumberOfCount(orderSaveModelList));
                    footerDetails.setText(String.format("%.2f", getTotalPrice(orderSaveModelList)));
                    if (Double.parseDouble(footerDetails.getText().toString()) < minimumValue) {
                        llbotom.setBackgroundColor(getResources().getColor(R.color.gray));
                        footerCount.setVisibility(View.GONE);
                        tvCurrency.setVisibility(View.GONE);
                        footerDetails.setVisibility(View.GONE);
                        txtEmptyBasket.setText("Spend ??" + String.format("%.2f", minimumValue - Double.parseDouble(footerDetails.getText().toString())) + " more to proceed");
                        llbotom.setVisibility(View.VISIBLE);
                    } else {

                        llbotom.setBackgroundColor(getResources().getColor(R.color.orange));
                        footerCount.setVisibility(View.VISIBLE);
                        tvCurrency.setVisibility(View.VISIBLE);
                        footerDetails.setVisibility(View.VISIBLE);

                        if (sharePre.isloginpref() != null) {
                            if (isFromLogin)
                                txtEmptyBasket.setText(getResources().getString(R.string.continue_));
                            else
                                txtEmptyBasket.setText(getResources().getString(R.string.view_basket));
                        } else {
                            txtEmptyBasket.setText(getResources().getString(R.string.continue_));
                        }


                        llbotom.setVisibility(View.VISIBLE);
                    }
                } else {
                    llbotom.setVisibility(View.VISIBLE);
                    llbotom.setBackground(getResources().getDrawable(R.color.gray_light));
                    txtEmptyBasket.setText(getString(R.string.your_basket_is_empty));
                    footerCount.setText("0");
                    footerDetails.setText("0.00");
                }

            }
        });
    }

    private String getNumberOfCount(List<OrderSaveModel> orderSaveModelList) {
        int count = 0;
        for (int i = 0; i < orderSaveModelList.size(); i++) {
            count = count + orderSaveModelList.get(i).getItemCount();
        }

        return String.valueOf(count);

    }

    private double getTotalPrice(List<OrderSaveModel> orderSaveModelList) {

        double finalPrice = 0;

        for (int i = 0; i < orderSaveModelList.size(); i++) {
            finalPrice = finalPrice + Double.parseDouble(orderSaveModelList.get(i).getTotalAmoutOfMeal());
        }

        return finalPrice;

    }


}