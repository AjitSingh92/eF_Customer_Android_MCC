package com.lexxdigital.easyfooduserapp.dashboard;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.add_card.AddNewCardActivity;
import com.lexxdigital.easyfooduserapp.api.FilterSortInterface;
import com.lexxdigital.easyfooduserapp.api.LogoutApiInterface;
import com.lexxdigital.easyfooduserapp.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapp.fragments.CardsListFragment;
import com.lexxdigital.easyfooduserapp.fragments.DealsFragment;
import com.lexxdigital.easyfooduserapp.fragments.FavouritesFragment;
import com.lexxdigital.easyfooduserapp.fragments.ManageAddressFragment;
import com.lexxdigital.easyfooduserapp.fragments.MyAccountFragment;
import com.lexxdigital.easyfooduserapp.fragments.MyBasketFragment;
import com.lexxdigital.easyfooduserapp.fragments.PreviousOrderFragment;
import com.lexxdigital.easyfooduserapp.login.LoginActivity;
import com.lexxdigital.easyfooduserapp.login.model.response.LoginResponse;
import com.lexxdigital.easyfooduserapp.model.filter_request.FilterSortRequest;
import com.lexxdigital.easyfooduserapp.model.filter_response.FilterSortResponse;
import com.lexxdigital.easyfooduserapp.model.logout.LogoutRequest;
import com.lexxdigital.easyfooduserapp.model.logout.LogoutResponse;
import com.lexxdigital.easyfooduserapp.order_status.OrderStatusActivity;
import com.lexxdigital.easyfooduserapp.search_post_code.SearchPostCodeActivity;
import com.lexxdigital.easyfooduserapp.utility.ApiClient;
import com.lexxdigital.easyfooduserapp.utility.ApiConstants;
import com.lexxdigital.easyfooduserapp.utility.Constants;
import com.lexxdigital.easyfooduserapp.utility.GlobalValues;
import com.lexxdigital.easyfooduserapp.utility.SharedPreferencesClass;

import java.util.ArrayList;

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
    @BindView(R.id.my_basket_id)
    LinearLayout myBasketId;
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

    // FilterSortByAdapter.PositionInterface mPositionInterface2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_with_drawer);
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

        Log.e("NAMD >>> ANAND >", "" + val.getUserName());


        if (val.getUserName() == null) {
            loginResponse = (LoginResponse) sharedPreferencesClass.getObject(sharedPreferencesClass.LoginResponseKey, LoginResponse.class);

            String postc = sharedPreferencesClass.getPostalCode();
            Log.e("", "onCreate: " + loginResponse.getData().getEmail());
            // val.setFirstName(loginResponse.getData().getFirstName());
            // Log.e("", "onCreate: "+loginResponse.getFirstName() );
            // val.setPostCode(loginResponse);
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
        Log.e("EXTRA>>>", "//" + extras);
        if (extras != null) {

            if (extras.getString("FROMMENU").equalsIgnoreCase("YES")) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().addToBackStack(null);
                transaction.replace(R.id.frameLayout, new MyBasketFragment(DashboardActivity.this, getApplicationContext()));
                //  transaction.commit();
                transaction.commitAllowingStateLoss();
            } else if (val.getPostCode() != null) {
                postCode = val.getPostCode();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().addToBackStack(null);
                transaction.replace(R.id.frameLayout, new DealsFragment(getApplicationContext(), DashboardActivity.this, tvToolbarTitle, postCode));
                //  transaction.commit();
                transaction.commitAllowingStateLoss();
            }

        } else if (val.getPostCode() != null) {
            postCode = val.getPostCode();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().addToBackStack(null);
            transaction.replace(R.id.frameLayout, new DealsFragment(getApplicationContext(), DashboardActivity.this, tvToolbarTitle, postCode));
            //  transaction.commit();
            transaction.commitAllowingStateLoss();
        }
// else {
//            postCode = val.getPostCode();
//            Constants.fragmentCall(new DealsFragment(getApplicationContext(), DashboardActivity.this, tvToolbarTitle, postCode), getSupportFragmentManager());
//        }


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
        Glide.with(this).load(val.getProfileImage()).placeholder(R.drawable.avatar).into(drawer_profile_pic);

        if (val.getPostCode() == null) {
            val.setIsFromDealPage(true);
            Intent i = new Intent(this, SearchPostCodeActivity.class);
            startActivity(i);
//            finish();
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        }
    }

    public void slidefromRightToLeft() {

        TranslateAnimation animate;
        if (menuIdRl.getHeight() == 0) {
            menuIdRl.getHeight(); // parent layout
            animate = new TranslateAnimation(menuIdRl.getWidth() / 2,
                    0, 0, 0);
        } else {
            animate = new TranslateAnimation(menuIdRl.getWidth(), 0, 0, 0); // View for animation
        }

        animate.setDuration(500);
        animate.setFillAfter(true);
        menuIdRl.startAnimation(animate);
        menuIdRl.setVisibility(View.VISIBLE); // Change visibility VISIBLE or GONE
    }

    public void slidefromLeftToRight() {
        TranslateAnimation animate;
        if (menuIdRl.getHeight() == 0) {
            menuIdRl.getHeight(); // parent layout
            animate = new TranslateAnimation(0,
                    menuIdRl.getWidth() / 2, 0, 0);
        } else {
            animate = new TranslateAnimation(0, menuIdRl.getWidth(), 0, 0); // View for animation
        }

        animate.setDuration(500);
        animate.setFillAfter(true);
        menuIdRl.startAnimation(animate);
        menuIdRl.setVisibility(View.GONE); // Change visibility VISIBLE or GONE
    }


    @OnClick({R.id.top_track_order, R.id.txt_trackorder, R.id.menuId, R.id.my_account, R.id.top_ac, R.id.manageAddressId, R.id.list_of_address, R.id.home, R.id.add_new_address, R.id.new_address, R.id.payments, R.id.paymentId, R.id.my_credit_debit_card, R.id.creditCardId, R.id.add_credit_debit_card, R.id.new_card, R.id.my_basket, R.id.my_basket_id, R.id.my_orders, R.id.my_orderId, R.id.favourites, R.id.myfevId, R.id.privacy_policy, R.id.privacyId, R.id.faq, R.id.fapId, R.id.help, R.id.helpId, R.id.profileId, R.id.logout, R.id.menuIdRl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_track_order:
                startActivity(new Intent(DashboardActivity.this, OrderStatusActivity.class));
                //  Constants.fragmentCall(new MyAccountFragment(getApplicationContext(), tvToolbarTitle), getSupportFragmentManager());
//                Animation RightSwipe = AnimationUtils.loadAnimation(DashboardActivity.this, R.anim.left_slide);
//                menuIdRl.startAnimation(RightSwipe);
//                menuIdRl.setVisibility(View.VISIBLE);
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.txt_trackorder:
                startActivity(new Intent(DashboardActivity.this, OrderStatusActivity.class));
//                Animation RightSwipe = AnimationUtils.loadAnimation(DashboardActivity.this, R.anim.left_slide);
//                menuIdRl.startAnimation(RightSwipe);
//                menuIdRl.setVisibility(View.VISIBLE);
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.menuId:
//                Animation RightSwipe = AnimationUtils.loadAnimation(DashboardActivity.this, R.anim.left_slide);
//                menuIdRl.startAnimation(RightSwipe);
//                menuIdRl.setVisibility(View.VISIBLE);
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                Glide.with(this).load(val.getProfileImage()).placeholder(R.drawable.avatar).into(drawer_profile_pic);
                break;
            case R.id.my_account:

                break;
            case R.id.top_ac:
                tvToolbarTitle.setText("My Account");
                Constants.fragmentCall(new MyAccountFragment(getApplicationContext(), tvToolbarTitle), getSupportFragmentManager());
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;

            case R.id.manageAddressId:
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
                Constants.fragmentCall(new DealsFragment(getApplicationContext(), DashboardActivity.this, tvToolbarTitle, postCode), getSupportFragmentManager());

                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.home:
                tvToolbarTitle.setText("Restaurants");
                Constants.fragmentCall(new DealsFragment(getApplicationContext(), DashboardActivity.this, tvToolbarTitle, postCode), getSupportFragmentManager());
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.add_new_address:
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.new_address:
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.payments:
                tvToolbarTitle.setText("My Saved Cards");
                Constants.fragmentCall(new CardsListFragment(getApplicationContext()), getSupportFragmentManager());
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.paymentId:
                tvToolbarTitle.setText("My Saved Cards");
                Constants.fragmentCall(new CardsListFragment(getApplicationContext()), getSupportFragmentManager());

                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.my_credit_debit_card:
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.creditCardId:
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.add_credit_debit_card:
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.new_card:
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.my_basket:
                tvToolbarTitle.setText("My Basket");
                Constants.fragmentCall(new MyBasketFragment(DashboardActivity.this, getApplicationContext()), getSupportFragmentManager());
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.my_basket_id:
                tvToolbarTitle.setText("My Basket");
                Constants.fragmentCall(new MyBasketFragment(DashboardActivity.this, getApplicationContext()), getSupportFragmentManager());
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.my_orders:
                tvToolbarTitle.setText("My Orders");
                Constants.fragmentCall(new PreviousOrderFragment(getApplicationContext(), tvToolbarTitle), getSupportFragmentManager());
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.my_orderId:
                tvToolbarTitle.setText("My Orders");
                Constants.fragmentCall(new PreviousOrderFragment(getApplicationContext(), tvToolbarTitle), getSupportFragmentManager());
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.favourites:
                tvToolbarTitle.setText("Favourites");
                Constants.fragmentCall(new FavouritesFragment(getApplicationContext()), getSupportFragmentManager());
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.myfevId:
                tvToolbarTitle.setText("Favourites");
                Constants.fragmentCall(new FavouritesFragment(getApplicationContext()), getSupportFragmentManager());

                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.privacy_policy:
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
                callWebviewFaqs();
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.fapId:
                callWebviewFaqs();
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.help:
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.helpId:
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.profileId:
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.logout:
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
                break;
        }
    }

    void callWebviewPrivacy() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.privacy_dialog, null);
        final AlertDialog mDialog = new AlertDialog.Builder(this).create();
        mDialog.setView(mDialogView);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final TextView privacyDesc = mDialogView.findViewById(R.id.desc_privacy);
        final TextView privacyPolc = mDialogView.findViewById(R.id.privacy_polc);
        btnContinnue = mDialogView.findViewById(R.id.btn_continue);
        progress = mDialogView.findViewById(R.id.progress);
        final WebView webView = mDialogView.findViewById(R.id.web_privacy_policy);
        webView.setVisibility(View.GONE);

        mDialogView.findViewById(R.id.cross_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                mDialog.dismiss();
            }
        });
        btnContinnue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                privacyDesc.setVisibility(View.GONE);
                btnContinnue.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                // privacyPolc.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                webView.setWebViewClient(new MyWebViewClient());
                webView.loadUrl(ApiConstants.PRIVACY_POLICY);

            }

        });

        mDialog.show();

    }

    void callWebviewFaqs() {

        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.faqs_dialog, null);
        final AlertDialog mDialog = new AlertDialog.Builder(this).create();
        mDialog.setView(mDialogView);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final TextView faqDesc = mDialogView.findViewById(R.id.faqs_desc);
        final TextView faq = mDialogView.findViewById(R.id.faq);
        final WebView webView = mDialogView.findViewById(R.id.web_faqs);
        prDialog = mDialogView.findViewById(R.id.progressBar);
        //  webView.loadUrl("http://13.233.171.105/easyfood_backend_api/public/api/v1/restaurant_faq");
        prDialog.setVisibility(View.VISIBLE);
        webView.setVisibility(View.VISIBLE);
        webView.setWebViewClient(new MyWebViewClient());

        String url = ApiConstants.RESTAURANT_FAQ;
        // webView.getSettings().setJavaScriptEnabled(true);
        //  webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
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
        if (intent.getAction() != null) {
            if (intent.getAction().equals("custom")) {
                tvToolbarTitle.setText("My Basket");
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().addToBackStack(null);
                transaction.replace(R.id.frameLayout, new MyBasketFragment(DashboardActivity.this, getApplicationContext()));
                //  transaction.commit();
                transaction.commitAllowingStateLoss();
            }
        } else {
            postCode = val.getPostCode();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().addToBackStack(null);
            transaction.replace(R.id.frameLayout, new DealsFragment(getApplicationContext(), DashboardActivity.this, tvToolbarTitle, postCode));
            //  transaction.commit();
            transaction.commitAllowingStateLoss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(this).load(val.getProfileImage()).placeholder(R.drawable.avatar).into(drawer_profile_pic);
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

    public void alertDialogFilter() {
        LayoutInflater factory = LayoutInflater.from(DashboardActivity.this);
        final View mDialogView = factory.inflate(R.layout.popup_filter, null);
        final AlertDialog filterDialog = new AlertDialog.Builder(DashboardActivity.this).create();
        filterDialog.setView(mDialogView);
        filterDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final EditText email = (EditText) mDialogView.findViewById(R.id.email);
        RecyclerView sortList = (RecyclerView) mDialogView.findViewById(R.id.list_sort_by);
        //FilterSortByAdapter sortAdapter = new FilterSortByAdapter(DashboardActivity.this);
        @SuppressLint("WrongConstant")
        LinearLayoutManager horizontalLayoutManagaer2
                = new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.VERTICAL, false);
        sortList.setLayoutManager(horizontalLayoutManagaer2);
        // sortList.setAdapter(sortAdapter);
        mDialogView.findViewById(R.id.sign_up_btn_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                filterDialog.dismiss();

            }
        });
        mDialogView.findViewById(R.id.cross_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                filterDialog.dismiss();
            }
        });

        filterDialog.show();
    }


    public void getFilters(String postCode) {
        FilterSortInterface apiInterface = ApiClient.getClient(DashboardActivity.this).create(FilterSortInterface.class);
        FilterSortRequest request = new FilterSortRequest();
        request.setPostCode(postCode);

        Call<FilterSortResponse> call3 = apiInterface.mGetFilters(request);
        call3.enqueue(new Callback<FilterSortResponse>() {
            @Override
            public void onResponse(Call<FilterSortResponse> call, Response<FilterSortResponse> response) {
                try {
                    if (response.body().getSuccess()) {

                    }
                } catch (Exception e) {

                    Log.e("Error11 <>>>", ">>>>>" + e.getMessage());
                    //    showDialog("Please try again.");
//                       Toast.makeText(LoginActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilterSortResponse> call, Throwable t) {
                Log.e("Error12 <>>>", ">>>>>" + t.getMessage());
//                showDialog("Please try again.");
                //    Toast.makeText(LoginActivity.this, "Please try again 2."+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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


    /*@Override
    public void onClickPos2(int pos, ArrayList<String> check) {
        if(check.contains("1")){
            for(int i=0;i<3;i++){
                check.set(i,"0");
            }
            check.set(pos,"1");
        }else{
            check.set(pos,"0");
        }
    }*/
}
