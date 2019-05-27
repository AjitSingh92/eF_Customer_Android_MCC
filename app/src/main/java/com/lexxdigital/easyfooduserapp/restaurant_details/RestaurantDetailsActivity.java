package com.lexxdigital.easyfooduserapp.restaurant_details;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.google.gson.Gson;
import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.adapters.menu_adapter.ItemClickListener;
import com.lexxdigital.easyfooduserapp.adapters.menu_adapter.OneItemMultiTimeListener;
import com.lexxdigital.easyfooduserapp.adapters.menu_adapter.RecyclerLayoutManager;
import com.lexxdigital.easyfooduserapp.adapters.menu_adapter.RestaurantMenuListAdapter;
import com.lexxdigital.easyfooduserapp.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapp.cart_db.tables.ProductSizeAndModifier;
import com.lexxdigital.easyfooduserapp.cart_model.final_cart.FinalNewCartDetails;
import com.lexxdigital.easyfooduserapp.dashboard.DashboardActivity;
import com.lexxdigital.easyfooduserapp.dialogs.AgainFragment;
import com.lexxdigital.easyfooduserapp.dialogs.ChooseLastCustnizationDialog;
import com.lexxdigital.easyfooduserapp.dialogs.MealProductModifierDialog;
import com.lexxdigital.easyfooduserapp.dialogs.MenuDialogNew;
import com.lexxdigital.easyfooduserapp.dialogs.MenuMealDialog;
import com.lexxdigital.easyfooduserapp.fragments.InfoFragment;
import com.lexxdigital.easyfooduserapp.fragments.MenuFragment;
import com.lexxdigital.easyfooduserapp.fragments.ReviwesFragment;
import com.lexxdigital.easyfooduserapp.restaurant_details.api.RestaurantDetailsInterface;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.new_restaurant_response.NewRestaurantsDetailsResponse;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.request.RestaurantDetailsRequest;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MealProduct;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.Menu;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategoryCart;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;
import com.lexxdigital.easyfooduserapp.cart_db.tables.MenuProducts;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MenuProductSize;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.Modifier;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.ProductModifier;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.Rough;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.SizeModifier;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.SpecialOffer;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.request.CategoryProductsRequest;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.request.MenuProductSizeModifierRequest;
import com.lexxdigital.easyfooduserapp.utility.ApiClient;
import com.lexxdigital.easyfooduserapp.utility.Constants;
import com.lexxdigital.easyfooduserapp.utility.GlobalValues;
import com.lexxdigital.easyfooduserapp.utility.HeightWrappingViewPager;
import com.lexxdigital.easyfooduserapp.utility.SharedPreferencesClass;
import com.lexxdigital.easyfooduserapp.viewmodel.MenuProductViewModel;
import com.lexxdigital.easyfooduserapp.viewmodel.MenuViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantDetailsActivity extends AppCompatActivity implements ItemClickListener, ChooseLastCustnizationDialog.OnChooseLastCustnizationListener, OneItemMultiTimeListener {

    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarhide)
    RelativeLayout toolbarhide;
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
    @BindView(R.id.footer_count)
    TextView footerCount;
    @BindView(R.id.txt_empty_basket)
    TextView txtEmptyBasket;
    @BindView(R.id.tv_status)
    TextView tvStatus;

    @BindView(R.id.delivery_val)
    TextView deliveryVal;
    @BindView(R.id.txt_minutes)
    TextView txtMinutes;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.pager)
    HeightWrappingViewPager pager;
    @BindView(R.id.container_restaurants_details)
    ScrollView containerRestaurantsDetails;
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
    //    @BindView(R.id.container_restaurants_details)
//    ScrollView containerRestaurantsDetails;
//    @BindView(R.id.observable_scrollview)
//    ObservableScrollView observableScrollview;
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
    public static RestaurantDetailsActivity restaurantDetailsActivity;
    private MenuViewModel menuViewModel;
    private MenuProductViewModel menuProductViewModel;
    private String restaurantId;
    private String restaurantName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        ButterKnife.bind(this);
        restaurantDetailsActivity = this;
        menu = new Menu();
        db = new DatabaseHelper(this);
        cartData = new FinalNewCartDetails();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Constants.setStatusBarGradiant(RestaurantDetailsActivity.this);
        ObservableScrollView observableScrollView = (ObservableScrollView) findViewById(R.id.observable_scrollview);
        //    observableScrollView.setScrollViewCallbacks(this);
        val = (GlobalValues) getApplicationContext();
        sharePre = new SharedPreferencesClass(this);
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
        //  SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(this, getSupportFragmentManager());

      /*  Bundle extras = getIntent().getExtras();
        if (extras != null) {
            dialog.show();
            getRestaurantDetails(extras.getString("RESTAURANTID"));

        }*/

        // pager.setAdapter(adapter);
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

        llbotom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Gson gson = new Gson();
//                String json = gson.toJson(cartData);
//                Log.e("menuCategory >>", json);

//                db.deleteCart();

                SharedPreferencesClass preferencesClass = new SharedPreferencesClass(RestaurantDetailsActivity.this);

                Gson gson2 = new Gson();
                String json2 = gson2.toJson(val.getRestaurantDetailsResponse());
                Log.e("CART DATA>>", gson2.toJson(db.getCartData()));
                preferencesClass.setCartRestaurantDeatilKey(json2);
                if (Integer.parseInt(footerCount.getText().toString()) > 0) {
                    Intent i = new Intent(RestaurantDetailsActivity.this, DashboardActivity.class);
                    i.putExtra("FROMMENU", "YES");
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setAction("custom");
                    startActivity(i);
                } else {

                }
            }
        });

        menuViewModel = ViewModelProviders.of(this).get(MenuViewModel.class);
        menuProductViewModel = ViewModelProviders.of(this).get(MenuProductViewModel.class);
    }

    private void setupViewPager(ViewPager viewPager, Menu restaurantMenuData, NewRestaurantsDetailsResponse data, TextView footer) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        // adapter.addFrag(new MenuFragment(RestaurantDetailsActivity.this, getApplicationContext(), res, footer, llbotom, footerCount, txtEmptyBasket), "Menu");
//        adapter.addFrag(new MenuFragment(RestaurantDetailsActivity.this, getApplicationContext()), "Menu");
        adapter.addFrag(MenuFragment.newInstance(RestaurantDetailsActivity.this, getApplicationContext(), restaurantMenuData, this), "Menu");
        adapter.addFrag(new ReviwesFragment(getApplicationContext()), "Reviews");
        // adapter.addFrag(new ReviwesFragment(getApplicationContext()), "Reviwes");

        adapter.addFrag(new InfoFragment(RestaurantDetailsActivity.this, getApplicationContext(), data), "Info");

//        adapter.addFrag(new InfoFragment(), "Info");
        pager.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onBackPressed() {
        // Constants.switchActivity(RestaurantDetailsActivity.this, DashboardActivity.class);
        finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

    }

    @OnClick({R.id.back, R.id.allergy_txt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                Constants.switchActivity(RestaurantDetailsActivity.this, DashboardActivity.class);
                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                break;
            case R.id.allergy_txt:
                alertDialogAllergy();
                break;
        }
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            // Toast.makeText(RestaurantDetailsActivity.this, "POS " + position, Toast.LENGTH_SHORT).show();
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            //    Toast.makeText(RestaurantDetailsActivity.this, "Len "+mFragmentList.size(), Toast.LENGTH_SHORT).show();
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
        showPriceAndView(null, null, 0);
        extras = getIntent().getExtras();
        if (extras != null) {
            if (Constants.isInternetConnectionAvailable(300)) {
                getRestaurantDetails(extras.getString("RESTAURANTID"));
            } else {
                dialogNoInternetConnection("Please check internet connection.");
            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        dialog.dismiss();
    }

    @Override
    protected void onStop() {
//        db.deleteCart();
        super.onStop();
        dialog.dismiss();
    }

    public void getRestaurantDetails(final String rId) {
        dialog.show();
        final String resID = rId;
        RestaurantDetailsInterface apiInterface = ApiClient.getClient(this).create(RestaurantDetailsInterface.class);
        RestaurantDetailsRequest request = new RestaurantDetailsRequest();
//        request.setUserId(val.getLoginResponse().getData().getUserId());
        request.setUserId(sharePre.getString(sharePre.USER_ID));
        request.setPostCode(sharePre.getPostalCode());
        request.setRestaurantId(resID);

        Call<NewRestaurantsDetailsResponse> call3 = apiInterface.mGetDetails(request);
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

                        mGetRestaurantMenu(resID, response.body());
                        name.setText(response.body().getData().getRestaurants().getRestaurantName());
                        tvToolbarTitle.setText(response.body().getData().getRestaurants().getRestaurantName());

                        sharePre.setInt(sharePre.NUMBER_OF_ORDERS, response.body().getData().getRestaurants().getUserHaveOrdered());
                        Log.e("rating", response.body().getData().getRestaurants().getAvgRating() + "");
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
                        deliveryMinorder.setText("£" + String.valueOf(response.body().getData().getRestaurants().getAvgDeliveryTime()) + " delivery");
                        deliveryVal.setText("£" + String.valueOf(response.body().getData().getRestaurants().getMinOrderValue()) + " min order");
                        txtMinutes.setText(response.body().getData().getRestaurants().getAvgDeliveryTime() + " min");
                        restaurantPhoneNumber = response.body().getData().getRestaurants().getPhoneNumber();

                        if (response.body().getData().getRestaurants().getDeliveryOptions() != null || !response.body().getData().getRestaurants().getDeliveryOptions().equals("")) {
                            String[] serve_styles = response.body().getData().getRestaurants().getDeliveryOptions().split(",");

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

                       /* name.setText(response.body().getData().getRestaurantName());
                        tvToolbarTitle.setText(response.body().getData().getRestaurantName());
                        txtxRating.setText(String.format("%.1f", Double.parseDouble(response.body().getData().getAvgRating())));
                        itemCuisines.setText(response.body().getData().getRestaurantCuisines());
                        deliveryMinorder.setText("£" + String.valueOf(response.body().getData().getAvgDeliveryTime()) + " delivery");
                        deliveryVal.setText("£" + String.valueOf(response.body().getData().getMinOrderValue()) + " min order");
                        txtMinutes.setText(response.body().getData().getAvgDeliveryTime() + " min");
                        */
                        //     Log.e("LOGO>>", "" + response.body().getData().getRestaurantLogo() + "//" + response.body().getData().getRestaurantImage());
                        Glide.with(RestaurantDetailsActivity.this)
                                .load(response.body().getData().getRestaurants().getRestaurantLogo())
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(logo);
                        Glide.with(RestaurantDetailsActivity.this)
                                .load(response.body().getData().getRestaurants().getRestaurantImage())
                                .diskCacheStrategy(DiskCacheStrategy.ALL)

                                .into(backImage);
                        llbotom.setVisibility(View.VISIBLE);
                        containerRestaurantsDetails.setVisibility(View.VISIBLE);
                    } else {
                        dialog.dismiss();
                        llbotom.setVisibility(View.GONE);
                        containerRestaurantsDetails.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                    Log.e("ERROR 345351>>", e.getMessage());
                    llbotom.setVisibility(View.GONE);
                    containerRestaurantsDetails.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<NewRestaurantsDetailsResponse> call, Throwable t) {
                Log.e("ERROR 2>>", t.getMessage());
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
/*
    public void mGetRestaurantMenu(String resID, final NewRestaurantsDetailsResponse data) {
        dialog.show();
        RestaurantDetailsInterface apiInterface = ApiClient.getClient(this).create(RestaurantDetailsInterface.class);
        RestaurantDetailsRequest request = new RestaurantDetailsRequest();
        request.setUserId(val.getLoginResponse().getData().getUserId());
        request.setPostCode(val.getPostCode());
        request.setRestaurantId(resID);

        Call<Rough> call3 = apiInterface.mGetRestaurantMenu(request);
        call3.enqueue(new Callback<Rough>() {
            @Override
            public void onResponse(Call<Rough> call, Response<Rough> response) {
                try {
                    dialog.dismiss();
                    if (response.body().getSuccess()) {
                        setupViewPager(pager, response.body().getData().getMenu(), data, footerDetails);

                        *//*val.setRestaurantDetailsResponse(response.body());
                        setupViewPager(pager, response.body(), footerDetails);
                        name.setText(response.body().getData().getRestaurantName());
                        tvToolbarTitle.setText(response.body().getData().getRestaurantName());
                        txtxRating.setText(response.body().getData().getAvgRating());
                        itemCuisines.setText(response.body().getData().getRestaurantCuisines());
                        deliveryMinorder.setText("£" + String.valueOf(response.body().getData().getAvgDeliveryTime()) + " delivery");
                        deliveryVal.setText("£" + String.valueOf(response.body().getData().getMinOrderValue()) + " min order");
                        txtMinutes.setText(response.body().getData().getAvgDeliveryTime() + " min");

                        Log.e("LOGO>>", "" + response.body().getData().getRestaurantLogo() + "//" + response.body().getData().getRestaurantImage());
                        Glide.with(RestaurantDetailsActivity.this)
                                .load(response.body().getData().getRestaurantLogo())
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(logo);
                        Glide.with(RestaurantDetailsActivity.this)
                                .load(response.body().getData().getRestaurantImage())
                                .diskCacheStrategy(DiskCacheStrategy.ALL)

                                .into(backImage);
                        llbotom.setVisibility(View.VISIBLE);
                        containerRestaurantsDetails.setVisibility(View.VISIBLE);*//*

                    } else {
                        dialog.dismiss();
                        llbotom.setVisibility(View.GONE);
                        containerRestaurantsDetails.setVisibility(View.GONE);
                        Toast.makeText(RestaurantDetailsActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                    Log.e("ERROR 1>>", e.getMessage());
                    llbotom.setVisibility(View.GONE);
                    containerRestaurantsDetails.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Rough> call, Throwable t) {
                Log.e("ERROR 2>>", t.getMessage());
                dialog.dismiss();
                llbotom.setVisibility(View.GONE);
                containerRestaurantsDetails.setVisibility(View.GONE);
            }
        });
    }
    */


    public void mGetRestaurantMenu(final String resID, final NewRestaurantsDetailsResponse data) {

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
        request.setUserId(val.getLoginResponse().getData().getUserId());
        request.setPostCode(val.getPostCode());
        request.setRestaurantId(resID);

        Call<Rough> call3 = apiInterface.mRestaurantCategory(request);
        call3.enqueue(new Callback<Rough>() {
            @Override
            public void onResponse(Call<Rough> call, Response<Rough> response) {

                try {
                    dialog.dismiss();
                    if (response.body().getSuccess()) {
                        response.body().getData().getMenu().setRestaurantId(resID);
                        menuViewModel.insertMenu(response.body().getData().getMenu());
//                        setupViewPager(pager, response.body().getData().getMenu(), data, footerDetails);

                    } else {
                        dialog.dismiss();
                        llbotom.setVisibility(View.GONE);
                        containerRestaurantsDetails.setVisibility(View.GONE);
                        Toast.makeText(RestaurantDetailsActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                    Log.e("ERROR 1>>", e.getMessage());
                    llbotom.setVisibility(View.GONE);
                    containerRestaurantsDetails.setVisibility(View.GONE);


                }
            }

            @Override
            public void onFailure(Call<Rough> call, Throwable t) {
                Log.e("ERROR 2>>", t.getMessage());
                dialog.dismiss();
                llbotom.setVisibility(View.GONE);
                containerRestaurantsDetails.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void LoadMenuProduct(int parentPosition, String category_id, ProgressBar progressBar) {
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

        /*menuProductViewModel.getMenuProductList(category_id).observe(this, new Observer<MenuProducts.MenuProductsTable>() {
            @Override
            public void onChanged(@Nullable MenuProducts.MenuProductsTable menuProducts) {
                if (menuProducts == null) {
                    getMenuCategoryProducts(mParentPosition, categoryId, mProgressBar);
                }
            }
        });*/
    }

    private void loafSizeAndModifiers(final Boolean isCategory, final String productId, final int parentPosition, final int childPosition, final View qtyLayout, final TextView itemQtyView, final int itemCount, final int action, MenuCategory mMenuCategory, final ProgressBar progressBar) {
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


        Call<ProductSizeAndModifier> call3 = apiInterface.getMenuProductSizeModifier(request);
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
                Log.e("ERROR 2>>", t.getMessage());
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
        CategoryProductsRequest request = new CategoryProductsRequest(extras.getString("RESTAURANTID"), category_id, 0);


        Call<MenuProducts> call3 = apiInterface.mRestaurantCategoryProduct(request);
        call3.enqueue(new Callback<MenuProducts>() {
            @Override
            public void onResponse(Call<MenuProducts> call, Response<MenuProducts> response) {
                final Response<MenuProducts> mResponse = response;
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
                Log.e("ERROR 2>>", t.getMessage());
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        dialog.dismiss();
    }

    @Override
    public void OnSpecialOfferClick(int parentPosition, int childPosition, TextView itemQtyView, int itemCount, int action, SpecialOffer item) {
        Log.e("Spacial offer >>", parentPosition + " > " + childPosition + " > " + itemCount + " > " + item.toString());
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

    @Override
    public void OnCategoryClick(int parentPosition, int childPosition, View qtyLayout, TextView itemQtyView, int itemCount, int action, MenuCategory menuCategory, ProgressBar progressBar) {
        if (menuCategory.getMenuCategoryName().equalsIgnoreCase("MEAL")) {

            MenuMealDialog menuMealDialog = MenuMealDialog.newInstance(this, -1, -1, parentPosition, childPosition, qtyLayout, itemQtyView, itemCount, action, menuCategory, false, this);
            menuMealDialog.show(getSupportFragmentManager(), "menuMealDailog");

        } else {
            Log.e("OnCategoryClick >>", parentPosition + " > " + childPosition + " > " + itemCount + " > " + menuCategory.getMenuCategoryName() + " > " + menuCategory.getMenuProducts().get(childPosition).toString());
        /*if (menuCategory.getMenuProducts().get(childPosition).getProductModifiers() != null && menuCategory.getMenuProducts().get(childPosition).getMenuProductSize() != null) {
            updateUi(parentPosition, childPosition, qtyLayout, itemQtyView, itemCount, action, menuCategory);
        } else {
            loafSizeAndModifiers(parentPosition, childPosition, qtyLayout, itemQtyView, itemCount, action, menuCategory, progressBar);
        }*/
            checkModifierAndSizeInDb(true, menuCategory.getMenuProducts().get(childPosition).getMenuProductId(), parentPosition, childPosition, qtyLayout, itemQtyView, itemCount, action, menuCategory, progressBar);
        }

    }

    private void checkModifierAndSizeInDb(final Boolean isCategory, final String productId, final int parentPosition, final int childPosition, final View qtyLayout, final TextView itemQtyView, final int itemCount, final int action, final MenuCategory menuCategory, final ProgressBar progressBar) {
        if (isCategory) {
            if (menuCategory.getMenuProducts().get(childPosition).getProductModifiers() != null && menuCategory.getMenuProducts().get(childPosition).getMenuProductSize() != null) {
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

    private void updateSubCategoryUi(int parentPosition, int childPosition, View qtyLayout, TextView itemQtyView, int itemCount, int action, MenuCategory menuCategory) {
        if (db.getMenuProductCount(menuCategory.getMenuSubCategory().get(parentPosition).getMenuCategoryId(),
                menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getMenuProductId()) > 0) {
            List<MenuProduct> menuProduct = db.getMenuProduct(menuCategory.getMenuSubCategory().get(parentPosition).getMenuCategoryId(), menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getMenuProductId());

            if (menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getProductModifiers().size() > 0 || menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getMenuProductSize().size() > 0) {
                if (action == 1) {
                    if (Integer.parseInt(itemQtyView.getText().toString()) == 1) {
                        db.deleteItem(menuProduct.get(0).getMenuId(), menuProduct.get(0).getId());
                        qtyLayout.setVisibility(View.GONE);
                        itemQtyView.setText(String.valueOf(itemCount));
                        showPriceAndView(null, null, 0);
                    } else {
                        if (menuProduct.size() > 1) {
                            AgainFragment againFragment = AgainFragment.newInstance(this, childPosition, parentPosition, menuProduct, qtyLayout, itemQtyView, menuCategory, itemCount, action);
                            againFragment.show(getSupportFragmentManager(), "againDailog");
                        } else {
                            db.updateProductQuantity(menuProduct.get(0).getId(), itemCount);
                            itemQtyView.setText(String.valueOf(itemCount));
                            showPriceAndView(null, null, 0);
                        }
                    }

                } else {
                    ChooseLastCustnizationDialog chooseLastCustnizationDialog = ChooseLastCustnizationDialog.newInstance(this, menuProduct, itemCount, qtyLayout, itemQtyView, parentPosition, childPosition, menuCategory, true, action, this);
                    chooseLastCustnizationDialog.show(getSupportFragmentManager(), "chooseLastCustnizationDialog");
                }
            } else {
                if (itemCount == 0) {
                    db.deleteItem(menuProduct.get(0).getMenuId(), menuProduct.get(0).getId());
                    qtyLayout.setVisibility(View.GONE);
                    itemQtyView.setText(String.valueOf(itemCount));
                } else {
                    db.updateProductQuantity(menuProduct.get(0).getId(), itemCount);
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

    private void updateCategoryUi(int parentPosition, int childPosition, View qtyLayout, TextView itemQtyView, int itemCount, int action, MenuCategory menuCategory) {
        if (db.getMenuProductCount(menuCategory.getMenuCategoryId(), menuCategory.getMenuProducts().get(childPosition).getMenuProductId()) > 0) {
            List<MenuProduct> menuProduct = db.getMenuProduct(menuCategory.getMenuCategoryId(), menuCategory.getMenuProducts().get(childPosition).getMenuProductId());
            if (menuCategory.getMenuProducts().get(childPosition).getProductModifiers().size() > 0 || menuCategory.getMenuProducts().get(childPosition).getMenuProductSize().size() > 0) {

                if (action == 1) {
                    if (Integer.parseInt(itemQtyView.getText().toString()) == 1) {
                        db.deleteItem(menuProduct.get(0).getMenuId(), menuProduct.get(0).getId());
                        qtyLayout.setVisibility(View.GONE);
                        itemQtyView.setText(String.valueOf(itemCount));
                        showPriceAndView(null, null, 0);
                    } else {
                        if (menuProduct.size() > 1) {
                            AgainFragment againFragment = AgainFragment.newInstance(this, childPosition, parentPosition, menuProduct, qtyLayout, itemQtyView, menuCategory, itemCount, action);
                            againFragment.show(getSupportFragmentManager(), "againDailog");
                        } else {
                            db.updateProductQuantity(menuProduct.get(0).getId(), itemCount);
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
                    db.deleteItem(menuProduct.get(0).getMenuId(), menuProduct.get(0).getId());
                    qtyLayout.setVisibility(View.GONE);
                    itemQtyView.setText(String.valueOf(itemCount));
                } else {
                    db.updateProductQuantity(menuProduct.get(0).getId(), itemCount);
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

    @Override
    public void onMultiTimeItemChange(int childPosition, int parentPosition, List<
            MenuProduct> menuProduct, View view, TextView qtyTextView, MenuCategory menuCategory,
                                      int itemCount, int action) {

        if (db.getMenuProductCount(menuCategory.getMenuCategoryId(), menuCategory.getMenuProducts().get(childPosition).getMenuProductId()) > 0) {
            List<MenuProduct> mProduct = db.getMenuProduct(menuCategory.getMenuCategoryId(), menuCategory.getMenuProducts().get(childPosition).getMenuProductId());
            int itemQty = 0;
            for (MenuProduct product : mProduct) {
                itemQty += product.getOriginalQuantity();
            }
            if (itemQty == 0) {
                view.setVisibility(View.GONE);
            }
            qtyTextView.setText(String.valueOf(itemQty));


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
                        view.setVisibility(View.GONE);
                    }
                    qtyTextView.setText(String.valueOf(itemQty));


                    showPriceAndView(null, null, 0);
                    /*if (menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getProductModifiers().size() > 0 || menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getMenuProductSize().size() > 0) {
                        if (action == 1) {
                            AgainFragment againFragment = AgainFragment.newInstance(this, childPosition, parentPosition, mProduct, view, qtyTextView, menuCategory, itemCount, action);
                            againFragment.show(getSupportFragmentManager(), "againDailog");
                        } else {
                            ChooseLastCustnizationDialog chooseLastCustnizationDialog = ChooseLastCustnizationDialog.newInstance(this, mProduct, itemCount, view, qtyTextView, parentPosition, childPosition, menuCategory, true, action, this);
                            chooseLastCustnizationDialog.show(getSupportFragmentManager(), "chooseLastCustnizationDialog");
                        }
                    } else {
                        if (itemCount == 0) {
                            db.deleteItem(mProduct.get(0).getMenuId(), mProduct.get(0).getId());
                            view.setVisibility(View.GONE);
                            qtyTextView.setText(String.valueOf(itemCount));
                        } else {
                            db.updateProductQuantity(mProduct.get(0).getId(), itemCount);
                            qtyTextView.setText(String.valueOf(itemCount));
                        }
                        showPriceAndView(null, null, 0);
                    }*/
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

    @Override
    public void OnSubCategoryClick(int parentPosition, int childPosition, View qtyLayout, TextView itemQtyView, int itemCount, int action, MenuCategory menuCategory, ProgressBar progressBar) {

        checkModifierAndSizeInDb(false, menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getMenuProductId(), parentPosition, childPosition, qtyLayout, itemQtyView, itemCount, action, menuCategory, progressBar);


//        createCardData(parentPosition, childPosition, qtyLayout,itemQtyView, itemCount, action, menuCategory);

    }

    @Override
    public void onRepeatLast(int position, int parentPosition, List<
            MenuProduct> menuProduct, View view, TextView qtyTextView, MenuCategory menuCategory,
                             int itemCount, Boolean isSubCat, int action) {
        if (menuProduct.size() > 1) {
            AgainFragment againFragment = AgainFragment.newInstance(this, position, parentPosition, menuProduct, view, qtyTextView, menuCategory, itemCount, action);
            againFragment.show(getSupportFragmentManager(), "againDailog");
        } else {
            if (action == 2) {
                int qty = (Integer.parseInt(qtyTextView.getText().toString()) + 1);
                db.updateProductQuantity(menuProduct.get(0).getId(), qty);
            } else {
                int qty = (Integer.parseInt(qtyTextView.getText().toString()) - 1);
                if (qty == 0) {
                    db.deleteItem(menuProduct.get(0).getMenuId(), menuProduct.get(0).getId());
                } else {
                    db.updateProductQuantity(menuProduct.get(0).getId(), qty);
                }
            }
            showPriceAndView(view, qtyTextView, itemCount);
        }
    }

    @Override
    public void onChooseAgain(int position, int parentPosition, List<
            MenuProduct> menuProduct, View view, TextView qtyTextView, MenuCategory menuCategory,
                              int itemCount, Boolean isSubCat, int action) {
        MenuDialogNew menuDialogNew = MenuDialogNew.newInstance(this, parentPosition, position, view, qtyTextView, itemCount, action, menuCategory, isSubCat, this);
//            FragmentTransaction ft = getFragmentManager().beginTransaction();
        menuDialogNew.show(getSupportFragmentManager(), "menuDialogNew");
    }

    @Override
    public void OnAddItem(int parentPosition, int childPosition, View qtyLayout, TextView
            itemQtyView, int itemCount, int action, MenuCategory menuCategory) {

        if (menuCategory.getMenuCategoryName().equalsIgnoreCase("MEAL")) {

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
                    /*if (menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getProductModifiers().size() > 0 || menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getMenuProductSize().size() > 0) {
                        if (action == 1) {
                            AgainFragment againFragment = AgainFragment.newInstance(this, childPosition, parentPosition, mProduct, view, qtyTextView, menuCategory, itemCount, action);
                            againFragment.show(getSupportFragmentManager(), "againDailog");
                        } else {
                            ChooseLastCustnizationDialog chooseLastCustnizationDialog = ChooseLastCustnizationDialog.newInstance(this, mProduct, itemCount, view, qtyTextView, parentPosition, childPosition, menuCategory, true, action, this);
                            chooseLastCustnizationDialog.show(getSupportFragmentManager(), "chooseLastCustnizationDialog");
                        }
                    } else {
                        if (itemCount == 0) {
                            db.deleteItem(mProduct.get(0).getMenuId(), mProduct.get(0).getId());
                            view.setVisibility(View.GONE);
                            qtyTextView.setText(String.valueOf(itemCount));
                        } else {
                            db.updateProductQuantity(mProduct.get(0).getId(), itemCount);
                            qtyTextView.setText(String.valueOf(itemCount));
                        }
                        showPriceAndView(null, null, 0);
                    }*/
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
                    /*if (menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getProductModifiers().size() > 0 || menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getMenuProductSize().size() > 0) {
                        if (action == 1) {
                            AgainFragment againFragment = AgainFragment.newInstance(this, childPosition, parentPosition, mProduct, view, qtyTextView, menuCategory, itemCount, action);
                            againFragment.show(getSupportFragmentManager(), "againDailog");
                        } else {
                            ChooseLastCustnizationDialog chooseLastCustnizationDialog = ChooseLastCustnizationDialog.newInstance(this, mProduct, itemCount, view, qtyTextView, parentPosition, childPosition, menuCategory, true, action, this);
                            chooseLastCustnizationDialog.show(getSupportFragmentManager(), "chooseLastCustnizationDialog");
                        }
                    } else {
                        if (itemCount == 0) {
                            db.deleteItem(mProduct.get(0).getMenuId(), mProduct.get(0).getId());
                            view.setVisibility(View.GONE);
                            qtyTextView.setText(String.valueOf(itemCount));
                        } else {
                            db.updateProductQuantity(mProduct.get(0).getId(), itemCount);
                            qtyTextView.setText(String.valueOf(itemCount));
                        }
                        showPriceAndView(null, null, 0);
                    }*/
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
    public void OnMealProductClick(Dialog dialog, int childParentPosition, int selectedChildPosition, int parentPosition, int childPosition, View qtyLayout, TextView item_count, int itemCount, int action, MenuCategory menuCategory, ProductSizeAndModifier.ProductSizeAndModifierTable productSizeAndModifierTable, Boolean isSubCat) {

        openMealProductModifierDialog(childParentPosition, selectedChildPosition, parentPosition, childPosition, qtyLayout, item_count, itemCount, action, menuCategory, productSizeAndModifierTable, isSubCat);

        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void loadMealProductData(Dialog dialog, String productId, String productSizeId, ProgressBar progressBar, int childParentPosition, int selectedChildPosition, int parentPosition, int childPosition, View qtyLayout, TextView item_count, int itemCount, int action, MenuCategory menuCategory, Boolean isSubCat) {
        loadMealProductDataFromApi(dialog, productId, productSizeId, progressBar, childParentPosition, selectedChildPosition, parentPosition, childPosition, qtyLayout, item_count, itemCount, action, menuCategory, isSubCat);

    }

    @Override
    public void OnMealProductModifierSelected(Boolean onDone, int childParentPosition, int selectedChildPosition, int parentPosition, int childPosition, View qtyLayout, TextView item_count, int itemCount, int action, MenuCategory menuCategory, Boolean isSubCat) {
        if (onDone) {
            MenuMealDialog menuMealDialog = MenuMealDialog.newInstance(this, childParentPosition, selectedChildPosition, parentPosition, childPosition, qtyLayout, item_count, itemCount, action, menuCategory, false, this);
            menuMealDialog.show(getSupportFragmentManager(), "menuMealDailog");
        }
    }


    private void openMealProductModifierDialog(int childParentPosition, int selectedChildPosition, int parentPosition, int childPosition, View qtyLayout, TextView item_count, int itemCount, int action, MenuCategory menuCategory, ProductSizeAndModifier.ProductSizeAndModifierTable productSizeAndModifierTable, Boolean isSubCat) {
        MealProductModifierDialog mealProductModifierDialog = MealProductModifierDialog.newInstance(this, childParentPosition, selectedChildPosition, parentPosition, childPosition, qtyLayout, item_count, itemCount, action, menuCategory, true, productSizeAndModifierTable, this);
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

//        String json = gson.toJson(menuProducts);


        //  sharePre.setCartDetailsKey(json);
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


                                /*int allCount = 0;
                                for (int j = 0; j < productModifier.getModifier().size(); j++) {
                                    allCount = allCount + Integer.parseInt(productModifier.getModifier().get(j).getQuantity());
                                }
                                if (allCount > productModifier.getMaxAllowedQuantity()) {
                               *//* for (int i = 0; i < productModifier.getModifier().size(); i++) {
                                    int qty = Integer.parseInt(productModifier.getModifier().get(i).getQuantity());
                                    qty = (qty * itemQty);
                                    totalPrice += (qty * Double.parseDouble(productModifier.getModifier().get(i).getModifierProductPrice()));
                                }*//*
                                    totalPrice += ((allCount - productModifier.getMaxAllowedQuantity()) * Double.parseDouble(productModifier.getModifier().get(0).getModifierProductPrice()));
                                }*/
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
//            int totalCartIterm = specialOffers.size() + menuProducts.size();
            Log.e("TOTAL PRICE >>", totalCartIterm + "," + totalPrice);
            if (totalCartIterm > 0) {
                llbotom.setBackground(getResources().getDrawable(R.color.orange));
                txtEmptyBasket.setText("View Basket");
                footerCount.setText(String.valueOf(totalCartIterm));
                Log.e(">>>>>>>>>>", totalCartIterm + "" + totalPrice);
                footerDetails.setText(String.format("%.2f", totalPrice));
                if (extras != null) {
                    sharePre.setString(sharePre.RESTUARANT_ID, extras.getString("RESTAURANTID"));
                    sharePre.setString(sharePre.RESTUARANT_NAME, extras.getString("RESTAURANTNAME"));
                }

            } else {
                llbotom.setBackground(getResources().getDrawable(R.color.gray_light));
                txtEmptyBasket.setText(getString(R.string.your_basket_is_empty));
                footerCount.setText("0");
                footerDetails.setText("0.00");
                sharePre.setString(sharePre.RESTUARANT_ID, "");
                sharePre.setString(sharePre.RESTUARANT_NAME, "");
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
                if (Constants.isInternetConnectionAvailable(300)) {
                    alertDialog.dismiss();
                    startActivity(new Intent(RestaurantDetailsActivity.this, RestaurantDetailsActivity.class));
                } else mDialogView.findViewById(R.id.okTv).startAnimation(animShake);

            }
        });

        alertDialog.show();
    }


    private void loadMealProductDataFromApi(Dialog dialog, final String productId, final String productSizeId, ProgressBar progressBar, final int childParentPosition, final int selectedChildPosition, final int parentPosition, final int childPosition, final View qtyLayout, final TextView item_count, final int itemCount, final int action, final MenuCategory menuCategory, final Boolean isSubCat) {
        final ProgressBar mProgressBar = progressBar;
        final Dialog mDialog = dialog;
        if (mProgressBar == null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }


        RestaurantDetailsInterface apiInterface = ApiClient.getClient(this).create(RestaurantDetailsInterface.class);
        MenuProductSizeModifierRequest request = new MenuProductSizeModifierRequest(productId, productSizeId);


        Call<ProductSizeAndModifier> call3 = apiInterface.getMenuProductSizeModifier(request);
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
                Log.e("ERROR 2>>", t.getMessage());
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

}