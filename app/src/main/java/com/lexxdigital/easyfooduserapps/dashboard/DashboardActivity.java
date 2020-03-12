package com.lexxdigital.easyfooduserapps.dashboard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.api.LogoutApiInterface;
import com.lexxdigital.easyfooduserapps.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapps.fragments.CardsListFragment;
import com.lexxdigital.easyfooduserapps.fragments.DealsFragment;
import com.lexxdigital.easyfooduserapps.fragments.FavouritesFragment;
import com.lexxdigital.easyfooduserapps.fragments.ManageAddressFragment;
import com.lexxdigital.easyfooduserapps.fragments.MyAccountFragment;
import com.lexxdigital.easyfooduserapps.fragments.MyBasketFragment;
import com.lexxdigital.easyfooduserapps.fragments.PreviousOrderFragment;
import com.lexxdigital.easyfooduserapps.login.LoginActivity;
import com.lexxdigital.easyfooduserapps.login.model.response.LoginResponse;
import com.lexxdigital.easyfooduserapps.model.logout.LogoutRequest;
import com.lexxdigital.easyfooduserapps.model.logout.LogoutResponse;
import com.lexxdigital.easyfooduserapps.order_status.OrderStatusActivity;
import com.lexxdigital.easyfooduserapps.search_post_code.SearchPostCodeActivity;
import com.lexxdigital.easyfooduserapps.utility.ApiClient;
import com.lexxdigital.easyfooduserapps.utility.ApiConstants;
import com.lexxdigital.easyfooduserapps.utility.Constants;
import com.lexxdigital.easyfooduserapps.utility.GlobalValues;
import com.lexxdigital.easyfooduserapps.utility.SharedPreferencesClass;
import com.newrelic.agent.android.NewRelic;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {
    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.ivFilter)
    ImageView ivFilter;
    @BindView(R.id.et_location)
    EditText etLocation;
    @BindView(R.id.menuId)
    ImageView menuId;

    @BindView(R.id.toolbarhide)
    RelativeLayout toolbarhide;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.app_bar_main)
    RelativeLayout appBarMain;
    @BindView(R.id.drawer_profile_pic)
    CircleImageView drawer_profile_pic;
    @BindView(R.id.my_account)
    TextView myAccount;
    @BindView(R.id.top_ac1)
    LinearLayout topAc1;
    @BindView(R.id.list_of_address)
    TextView listOfAddress;
    @BindView(R.id.tv_my_acc)
    TextView tvMyAcc;
    @BindView(R.id.tv_manageAddress)
    TextView tvManageAddress;
    @BindView(R.id.tv_logout)
    TextView tvLogout;
    @BindView(R.id.home)
    LinearLayout home;
    @BindView(R.id.top_ac)
    LinearLayout topAc;
    @BindView(R.id.manageAddressId)
    LinearLayout manageAddressId;
    @BindView(R.id.add_new_address)
    TextView addNewAddress;
    @BindView(R.id.new_address)
    LinearLayout newAddress;
    @BindView(R.id.payments)
    TextView payments;
    @BindView(R.id.paymentId)
    LinearLayout paymentId;
    @BindView(R.id.my_basket_id)
    LinearLayout myBasketId;
    @BindView(R.id.my_credit_debit_card)
    TextView myCreditDebitCard;
    @BindView(R.id.creditCardId)
    LinearLayout creditCardId;
    @BindView(R.id.add_credit_debit_card)
    TextView addCreditDebitCard;
    @BindView(R.id.new_card)
    LinearLayout newCard;
    @BindView(R.id.my_basket)
    TextView myBasket;

    @BindView(R.id.my_orders)
    TextView myOrders;
    @BindView(R.id.my_orderId)
    LinearLayout myOrderId;
    @BindView(R.id.favourites)
    TextView favourites;
    @BindView(R.id.myfevId)
    LinearLayout myfevId;
    @BindView(R.id.privacy_policy)
    TextView privacyPolicy;
    @BindView(R.id.privacyId)
    LinearLayout privacyId;
    @BindView(R.id.faq)
    TextView faq;
    @BindView(R.id.fapId)
    LinearLayout fapId;
    @BindView(R.id.help)
    TextView help;
    @BindView(R.id.helpId)
    LinearLayout helpId;
    @BindView(R.id.logout)
    LinearLayout logout;
    @BindView(R.id.profileId)
    LinearLayout profileId;
    @BindView(R.id.menuIdRl)
    RelativeLayout menuIdRl;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    TextView btnContinnue;
    @BindView(R.id.txt_trackorder)
    TextView txtTrackorder;
    @BindView(R.id.top_track_order)
    LinearLayout topTrackOrder;
    private boolean doubleBackToExitPressedOnce = false;
    DrawerLayout drawer;
    String postCode = "";
    private GlobalValues val;
    ProgressBar prDialog;
    LoginResponse loginResponse;
    SharedPreferencesClass sharedPreferencesClass;
    LinearLayout progress;
    private DatabaseHelper db;
    private Dialog mDialog;
    FirebaseAnalytics mFirebaseAnalytics;
    private static DashboardActivity instance = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_with_drawer);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        NewRelic.withApplicationToken(
                "eu01xxae9ccb44aafd9f746b5862b2dcb19769290d"
        ).start(this.getApplicationContext());
        instance = this;
        ButterKnife.bind(this);
        val = (GlobalValues) getApplicationContext();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //  mPositionInterface2 = this;
        val = (GlobalValues) getApplicationContext();
        db = new DatabaseHelper(this);
        sharedPreferencesClass = new SharedPreferencesClass(getApplicationContext());

        mDialog = new Dialog(DashboardActivity.this);
        mDialog.setTitle("");
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setContentView(R.layout.progress_dialog);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        if (val.getUserName() == null) {
            loginResponse = (LoginResponse) sharedPreferencesClass.getObject(sharedPreferencesClass.LoginResponseKey, LoginResponse.class);


            val.setPostCode(sharedPreferencesClass.getPostalCode());
            val.setLoginResponse(loginResponse);
            val.setProfileImage(loginResponse.getData().getProfilePic());
            val.setFirstName(loginResponse.getData().getFirstName());
            val.setLastName(loginResponse.getData().getLastName());
            val.setUserName(loginResponse.getData().getName());
            val.setMobileNo(loginResponse.getData().getPhoneNumber());
            sharedPreferencesClass.setString(sharedPreferencesClass.USER_ID, loginResponse.getData().getUserId());
        }


        Constants.setStatusBarGradiant(DashboardActivity.this);
        Bundle extras = getIntent().getExtras();
        ivFilter.setVisibility(View.GONE);
        if (extras != null) {
            if (extras.getString("FROMMENU").equalsIgnoreCase("YES")) {
                boolean isFavorite = extras.getBoolean(getString(R.string.isFavorate));
                etLocation.setVisibility(View.GONE);
                setDefaultDrawer();
                myBasketId.setBackgroundColor(getResources().getColor(R.color.orange));
                myBasket.setTextColor(getResources().getColor(R.color.white));
                ivFilter.setVisibility(View.GONE);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().addToBackStack(null);
                transaction.replace(R.id.frameLayout, new MyBasketFragment(DashboardActivity.this, getApplicationContext(),isFavorite));
                transaction.commitAllowingStateLoss();
            } else if (val.getPostCode() != null) {

                setDefaultDrawer();
                etLocation.setVisibility(View.GONE);
                home.setBackgroundColor(getResources().getColor(R.color.orange));
                listOfAddress.setTextColor(getResources().getColor(R.color.white));
                tvToolbarTitle.setText("Restaurants");
                ivFilter.setVisibility(View.VISIBLE);
                postCode = val.getPostCode();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().addToBackStack(null);
                transaction.replace(R.id.frameLayout, new DealsFragment(getApplicationContext(), DashboardActivity.this, tvToolbarTitle, postCode));
                transaction.commitAllowingStateLoss();
            }

        } else if (val.getPostCode() != null) {
            ivFilter.setVisibility(View.VISIBLE);
            postCode = val.getPostCode();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().addToBackStack(null);
            transaction.replace(R.id.frameLayout, new DealsFragment(getApplicationContext(), DashboardActivity.this, tvToolbarTitle, postCode));

            transaction.commitAllowingStateLoss();
        }
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                float moveFactor = 0;
                moveFactor = (drawerView.getWidth() * slideOffset);

                appBarMain.setTranslationX(-moveFactor);
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        myAccount.setText(val.getFirstName() + " " + val.getLastName());
        String imgUrl = val.getProfileImage();
        System.out.println("imgUrl: " + imgUrl);
        Glide.with(this).load(val.getProfileImage()).apply(new RequestOptions()
                .placeholder(R.mipmap.avatar_profile))
                .into(drawer_profile_pic);
        if (val.getPostCode() == null) {
            val.setIsFromDealPage(true);
            Intent i = new Intent(this, SearchPostCodeActivity.class);
            startActivity(i);

            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        }

    }

    public void locationVisibility(boolean isVisible, String location) {
        if (isVisible) {
            etLocation.setVisibility(View.VISIBLE);
            etLocation.setText(location);
        } else {
            etLocation.setVisibility(View.GONE);
        }
    }

    public void setLocation(String postCode) {
        etLocation.setText(postCode);
    }

    public static DashboardActivity getInstance() {
        return instance;
    }


    @OnClick({R.id.top_track_order, R.id.txt_trackorder, R.id.menuId, R.id.my_account, R.id.top_ac, R.id.manageAddressId,/* R.id.list_of_address,*/ R.id.home, R.id.add_new_address, R.id.new_address, /*R.id.payments,*/ R.id.paymentId, R.id.my_credit_debit_card, R.id.creditCardId, R.id.add_credit_debit_card, R.id.new_card,/* R.id.my_basket,*/ R.id.my_basket_id, /*R.id.my_orders,*/ R.id.my_orderId, /*R.id.favourites,*/ R.id.myfevId, /*R.id.privacy_policy,*/ R.id.privacyId, /*R.id.faq,*/ R.id.fapId, R.id.help, R.id.helpId, R.id.profileId, R.id.logout, R.id.menuIdRl, R.id.ivFilter, R.id.et_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_track_order:
                ivFilter.setVisibility(View.GONE);
                etLocation.setVisibility(View.GONE);
                startActivity(new Intent(DashboardActivity.this, OrderStatusActivity.class));
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.ivFilter:
                DealsFragment.getInstance().alertDialogFilter();
                break;
            case R.id.txt_trackorder:
                ivFilter.setVisibility(View.GONE);
                etLocation.setVisibility(View.GONE);
                startActivity(new Intent(DashboardActivity.this, OrderStatusActivity.class));
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.menuId:
                etLocation.setVisibility(View.GONE);
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                Glide.with(this).load(val.getProfileImage()).apply(new RequestOptions()
                        .placeholder(R.mipmap.avatar_profile))
                        .into(drawer_profile_pic);
                myAccount.setText(val.getFirstName() + " " + val.getLastName());
                break;

            case R.id.my_account:
                etLocation.setVisibility(View.GONE);
                break;
            case R.id.top_ac:
                etLocation.setVisibility(View.GONE);
                ivFilter.setVisibility(View.GONE);
                tvToolbarTitle.setText("My Account");
                Constants.fragmentCall(new MyAccountFragment(getApplicationContext(), tvToolbarTitle), getSupportFragmentManager());
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }

                setDefaultDrawer();
                topAc.setBackgroundColor(getResources().getColor(R.color.orange));
                tvMyAcc.setTextColor(getResources().getColor(R.color.white));
                break;

            case R.id.manageAddressId:
                setDefaultDrawer();
                etLocation.setVisibility(View.GONE);
                manageAddressId.setBackgroundColor(getResources().getColor(R.color.orange));
                tvManageAddress.setTextColor(getResources().getColor(R.color.white));
                ivFilter.setVisibility(View.GONE);
                tvToolbarTitle.setText("Manage Addresses");
                Constants.fragmentCall(new ManageAddressFragment(getApplicationContext(), tvToolbarTitle), getSupportFragmentManager());
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }


                break;
            case R.id.list_of_address:
                tvToolbarTitle.setText("Restaurants");
                etLocation.setVisibility(View.GONE);
                ivFilter.setVisibility(View.VISIBLE);
                Constants.fragmentCall(new DealsFragment(getApplicationContext(), DashboardActivity.this, tvToolbarTitle, postCode), getSupportFragmentManager());

                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.home:
                setDefaultDrawer();
                etLocation.setVisibility(View.GONE);
                home.setBackgroundColor(getResources().getColor(R.color.orange));
                listOfAddress.setTextColor(getResources().getColor(R.color.white));
                tvToolbarTitle.setText("Restaurants");
                ivFilter.setVisibility(View.VISIBLE);
                Constants.fragmentCall(new DealsFragment(getApplicationContext(), DashboardActivity.this, tvToolbarTitle, postCode), getSupportFragmentManager());
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.add_new_address:
                etLocation.setVisibility(View.GONE);
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.new_address:
                etLocation.setVisibility(View.GONE);
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.payments:
                etLocation.setVisibility(View.GONE);
                ivFilter.setVisibility(View.GONE);
                tvToolbarTitle.setText("My Saved Cards");
                Constants.fragmentCall(new CardsListFragment(getApplicationContext()), getSupportFragmentManager());
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.paymentId:
                etLocation.setVisibility(View.GONE);
                setDefaultDrawer();
                paymentId.setBackgroundColor(getResources().getColor(R.color.orange));
                payments.setTextColor(getResources().getColor(R.color.white));
                ivFilter.setVisibility(View.GONE);
                tvToolbarTitle.setText("My Saved Cards");
                Constants.fragmentCall(new CardsListFragment(getApplicationContext()), getSupportFragmentManager());

                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.my_credit_debit_card:
                etLocation.setVisibility(View.GONE);
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.creditCardId:
                etLocation.setVisibility(View.GONE);
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.add_credit_debit_card:
                etLocation.setVisibility(View.GONE);
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.new_card:
                etLocation.setVisibility(View.GONE);
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.my_basket:
                etLocation.setVisibility(View.GONE);
                ivFilter.setVisibility(View.GONE);
                tvToolbarTitle.setText("Order Summary");
                Constants.fragmentCall(new MyBasketFragment(DashboardActivity.this, getApplicationContext(),false), getSupportFragmentManager());
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.my_basket_id:
                etLocation.setVisibility(View.GONE);
                setDefaultDrawer();
                myBasketId.setBackgroundColor(getResources().getColor(R.color.orange));
                myBasket.setTextColor(getResources().getColor(R.color.white));
                ivFilter.setVisibility(View.GONE);
                tvToolbarTitle.setText("Order Summary");
                Constants.fragmentCall(new MyBasketFragment(DashboardActivity.this, getApplicationContext(),false), getSupportFragmentManager());
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.my_orders:
                etLocation.setVisibility(View.GONE);
                ivFilter.setVisibility(View.GONE);
                tvToolbarTitle.setText("My Orders");
                Constants.fragmentCall(new PreviousOrderFragment(getApplicationContext(), tvToolbarTitle), getSupportFragmentManager());
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.my_orderId:
                etLocation.setVisibility(View.GONE);
                setDefaultDrawer();
                myOrderId.setBackgroundColor(getResources().getColor(R.color.orange));
                myOrders.setTextColor(getResources().getColor(R.color.white));
                ivFilter.setVisibility(View.GONE);
                tvToolbarTitle.setText("My Orders");
                Constants.fragmentCall(new PreviousOrderFragment(getApplicationContext(), tvToolbarTitle), getSupportFragmentManager());
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.favourites:
                etLocation.setVisibility(View.GONE);
                ivFilter.setVisibility(View.GONE);
                tvToolbarTitle.setText("Favourites");
                Constants.fragmentCall(new FavouritesFragment(getApplicationContext()), getSupportFragmentManager());
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.myfevId:
                etLocation.setVisibility(View.GONE);
                setDefaultDrawer();
                myfevId.setBackgroundColor(getResources().getColor(R.color.orange));
                favourites.setTextColor(getResources().getColor(R.color.white));
                ivFilter.setVisibility(View.GONE);
                tvToolbarTitle.setText("Favourites");
                Constants.fragmentCall(new FavouritesFragment(getApplicationContext()), getSupportFragmentManager());

                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.privacy_policy:
                etLocation.setVisibility(View.GONE);
                callWebviewPrivacy();
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.privacyId:
                callWebviewPrivacy();
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.faq:
                etLocation.setVisibility(View.GONE);
                callWebviewFaqs();
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.fapId:
                etLocation.setVisibility(View.GONE);
                setDefaultDrawer();
                fapId.setBackgroundColor(getResources().getColor(R.color.orange));
                faq.setTextColor(getResources().getColor(R.color.white));

                callWebviewFaqs();
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.help:
                etLocation.setVisibility(View.GONE);
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.helpId:
                etLocation.setVisibility(View.GONE);
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.profileId:
                etLocation.setVisibility(View.GONE);
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.logout:
                etLocation.setVisibility(View.GONE);
                setDefaultDrawer();
                logout.setBackgroundColor(getResources().getColor(R.color.orange));
                tvLogout.setTextColor(getResources().getColor(R.color.white));

                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }

                if (Constants.isInternetConnectionAvailable(3000)) {
                    mDialog.show();
                    getLogout(sharedPreferencesClass.getString(sharedPreferencesClass.USER_ID));
                } else {
                    Constants.showDialog(DashboardActivity.this, "Please check internet connection.");
                }
                break;
            case R.id.menuIdRl:
                etLocation.setVisibility(View.GONE);
                break;
            case R.id.et_location:
                val.setIsFromDealPage(true);
                Intent i = new Intent(DashboardActivity.this, SearchPostCodeActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;
        }
    }

    void callWebviewPrivacy() {
        View mDialogView = LayoutInflater.from(DashboardActivity.this).inflate(R.layout.privacy_dialog, null);

        final Dialog mDialog = new Dialog(this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(mDialogView.getRootView());
        mDialog.setCancelable(false);


        final TextView privacyDesc = mDialogView.findViewById(R.id.desc_privacy);
        final TextView privacyPolc = mDialogView.findViewById(R.id.privacy_polc);
        btnContinnue = mDialogView.findViewById(R.id.btn_continue);
        progress = mDialogView.findViewById(R.id.progress);
        final WebView webView = mDialogView.findViewById(R.id.web_privacy_policy);
        progress.setVisibility(View.VISIBLE);
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(ApiConstants.PRIVACY_POLICY);

        mDialogView.findViewById(R.id.cross_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                mDialog.dismiss();
            }
        });


        mDialog.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        mDialog.getWindow().setAttributes(lp);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    void callWebviewFaqs() {

        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.faqs_dialog, null);
        final AlertDialog mDialog = new AlertDialog.Builder(this).create();
        mDialog.setView(mDialogView);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final WebView webView = mDialogView.findViewById(R.id.web_faqs);
        prDialog = mDialogView.findViewById(R.id.progressBar);

        prDialog.setVisibility(View.VISIBLE);
        webView.setVisibility(View.VISIBLE);
        webView.setWebViewClient(new MyWebViewClient());

        String url = ApiConstants.CONSUMER_FAQ;
        webView.loadUrl(url);


        mDialogView.findViewById(R.id.cross_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                mDialog.dismiss();
            }
        });

        mDialog.show();

    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            try {
                if (prDialog != null)
                    prDialog.setVisibility(View.GONE);
                if (progress.getVisibility() == View.VISIBLE) {
                    progress.setVisibility(View.GONE);
                }
                view.setVisibility(View.VISIBLE);
            } catch (NullPointerException e) {
                e.getLocalizedMessage();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {


            if (doubleBackToExitPressedOnce) {
                finish();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ivFilter.setVisibility(View.GONE);
        if (intent.getAction() != null) {
            if (intent.getAction().equals("custom")) {
                etLocation.setVisibility(View.GONE);
                setDefaultDrawer();
                boolean isFavorite = intent.getBooleanExtra(getString(R.string.isFavorate),false);
                myBasketId.setBackgroundColor(getResources().getColor(R.color.orange));
                myBasket.setTextColor(getResources().getColor(R.color.white));
                ivFilter.setVisibility(View.GONE);
                tvToolbarTitle.setText("Order Summary");
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().addToBackStack(null);
                transaction.replace(R.id.frameLayout, new MyBasketFragment(DashboardActivity.this, getApplicationContext(),isFavorite));
                transaction.commitAllowingStateLoss();
            }
        } else {
            setDefaultDrawer();
            etLocation.setVisibility(View.GONE);
            home.setBackgroundColor(getResources().getColor(R.color.orange));
            listOfAddress.setTextColor(getResources().getColor(R.color.white));
            tvToolbarTitle.setText("Restaurants");
            ivFilter.setVisibility(View.VISIBLE);
            postCode = val.getPostCode();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().addToBackStack(null);
            transaction.replace(R.id.frameLayout, new DealsFragment(getApplicationContext(), DashboardActivity.this, tvToolbarTitle, postCode));
            transaction.commitAllowingStateLoss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(this).load(val.getProfileImage()).apply(new RequestOptions()
                .placeholder(R.mipmap.avatar_profile))
                .into(drawer_profile_pic);
        if (db.getCartData() == null) {
            sharedPreferencesClass.setString(sharedPreferencesClass.RESTUARANT_ID, "");
            sharedPreferencesClass.setString(sharedPreferencesClass.RESTUARANT_NAME, "");
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDialog.dismiss();

    }


    public void getLogout(String customerId) {

        LogoutApiInterface apiInterface = ApiClient.getClient(DashboardActivity.this).create(LogoutApiInterface.class);
        LogoutRequest request = new LogoutRequest();
        request.setCustomerId(customerId);
        Call<LogoutResponse> call3 = apiInterface.logout(request);
        call3.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                try {
                    mDialog.dismiss();
                    if (response.body().getSuccess()) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                GlobalValues.getInstance().getDb().menuMaster().nuke();
                                GlobalValues.getInstance().getDb().menuProductMaster().nuke();
                                GlobalValues.getInstance().getDb().productSizeAndModifierMaster().nuke();

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        db.deleteCart();
                                        sharedPreferencesClass.logout();
                                        Intent i = new Intent(DashboardActivity.this, LoginActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);
                                        finish();
                                        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                                    }
                                });

                            }
                        }).start();

                    }
                } catch (Exception e) {
                    mDialog.dismiss();
                    Log.e("Error11 <>>>", ">>>>>" + e.getMessage());

                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                mDialog.dismiss();
                Log.e("Error12 <>>>", ">>>>>" + t.getMessage());

            }
        });
    }


    private void setDefaultDrawer() {
        home.setBackgroundColor(0);
        topAc.setBackgroundColor(0);
        manageAddressId.setBackgroundColor(0);
        paymentId.setBackgroundColor(0);
        myBasketId.setBackgroundColor(0);
        myOrderId.setBackgroundColor(0);
        myfevId.setBackgroundColor(0);
        privacyId.setBackgroundColor(0);
        fapId.setBackgroundColor(0);
        logout.setBackgroundColor(0);
        listOfAddress.setTextColor(getResources().getColor(R.color.orange));
        tvMyAcc.setTextColor(getResources().getColor(R.color.orange));
        tvManageAddress.setTextColor(getResources().getColor(R.color.orange));
        payments.setTextColor(getResources().getColor(R.color.orange));
        myBasket.setTextColor(getResources().getColor(R.color.orange));
        myOrders.setTextColor(getResources().getColor(R.color.orange));
        favourites.setTextColor(getResources().getColor(R.color.orange));
        privacyPolicy.setTextColor(getResources().getColor(R.color.orange));
        faq.setTextColor(getResources().getColor(R.color.orange));
        tvLogout.setTextColor(getResources().getColor(R.color.orange));

    }

}
