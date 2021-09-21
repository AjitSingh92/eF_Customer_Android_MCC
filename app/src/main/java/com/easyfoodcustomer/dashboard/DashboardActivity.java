package com.easyfoodcustomer.dashboard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;

import com.easyfoodcustomer.fragments.MyCartFragment;
import com.easyfoodcustomer.roomData.AppDatabase;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.easyfoodcustomer.search_post_code.api.SearchPostCodeInterface;
import com.easyfoodcustomer.search_post_code.model.search_response.PrivacyBean;
import com.easyfoodcustomer.utility.PrefManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.easyfoodcustomer.R;
import com.easyfoodcustomer.api.LogoutApiInterface;
import com.easyfoodcustomer.cart_db.DatabaseHelper;
import com.easyfoodcustomer.fragments.CardsListFragment;
import com.easyfoodcustomer.fragments.DealsFragment;
import com.easyfoodcustomer.fragments.FavouritesFragment;
import com.easyfoodcustomer.fragments.ManageAddressFragment;
import com.easyfoodcustomer.fragments.MyAccountFragment;
import com.easyfoodcustomer.fragments.MyBasketFragment;
import com.easyfoodcustomer.fragments.PreviousOrderFragment;
import com.easyfoodcustomer.login.LoginActivity;
import com.easyfoodcustomer.login.model.response.LoginResponse;
import com.easyfoodcustomer.model.logout.LogoutRequest;
import com.easyfoodcustomer.model.logout.LogoutResponse;
import com.easyfoodcustomer.order_status.OrderStatusActivity;
import com.easyfoodcustomer.search_post_code.SearchPostCodeActivity;
import com.easyfoodcustomer.utility.ApiClient;
import com.easyfoodcustomer.utility.ApiConstants;
import com.easyfoodcustomer.utility.Constants;
import com.easyfoodcustomer.utility.GlobalValues;
import com.easyfoodcustomer.utility.SharedPreferencesClass;
import com.newrelic.agent.android.NewRelic;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.easyfoodcustomer.utility.Helper.isInternetOn;
import static com.easyfoodcustomer.utility.UserContants.AUTH_TOKEN;
import static com.easyfoodcustomer.utility.UserContants.IS_FROM_DEAL_PAGE;
import static com.facebook.FacebookSdk.getApplicationContext;

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
    String islonch, islogin;
    private AppDatabase mDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_with_drawer);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        NewRelic.withApplicationToken(
                "eu01xxae9ccb44aafd9f746b5862b2dcb19769290d"
        ).start(this.getApplicationContext());
        instance = this;
        mDB = AppDatabase.getInstance(getApplicationContext());
        ButterKnife.bind(this);
        val = (GlobalValues) getApplicationContext();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //  mPositionInterface2 = this;
        val = (GlobalValues) getApplicationContext();
        db = new DatabaseHelper(this);
        sharedPreferencesClass = new SharedPreferencesClass(getApplicationContext());
        Double vv=59.00;
        int vllll = Integer.valueOf(vv.intValue());
        Log.e("Vll", "" + vllll);
        mDialog = new Dialog(DashboardActivity.this);
        mDialog.setTitle("");
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setContentView(R.layout.progress_dialog);
        if (mDialog.getWindow() != null)
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //Log.e("OS Version", "" + Build.VERSION.RELEASE);

        islogin = sharedPreferencesClass.isloginpref();
        if (sharedPreferencesClass.isloginpref() != null) {
            if (val.getUserName() == null) {

                islonch = sharedPreferencesClass.isFirstTimeLaunch();

                val.setPostCode(sharedPreferencesClass.getPostalCode());
                if (islogin != null) {
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

            }
        } else {
            tvLogout.setText("Login");
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
                transaction.replace(R.id.frameLayout, new MyBasketFragment(DashboardActivity.this, getApplicationContext(), isFavorite));
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
        if (sharedPreferencesClass.isloginpref() != null) {
            myAccount.setText(val.getFirstName() + " " + val.getLastName());
            String imgUrl = val.getProfileImage();
            System.out.println("imgUrl: " + imgUrl);
            Glide.with(this).load(val.getProfileImage()).apply(new RequestOptions()
                    .placeholder(R.mipmap.avatar_profile))
                    .into(drawer_profile_pic);
        } else {
            myAccount.setText("Guest User");
        }


        if (val.getPostCode() == null) {
            PrefManager.getInstance(DashboardActivity.this).savePreference(IS_FROM_DEAL_PAGE, true);
            //val.setIsFromDealPage(true);
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
                if (sharedPreferencesClass.isloginpref() != null) {
                    ivFilter.setVisibility(View.GONE);
                    etLocation.setVisibility(View.GONE);
                    startActivity(new Intent(DashboardActivity.this, OrderStatusActivity.class));
                    if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                        drawer.closeDrawer(Gravity.RIGHT);
                    } else {
                        drawer.openDrawer(Gravity.RIGHT);
                    }
                } else {
                    loginDialog();
                }
                break;
            case R.id.ivFilter:
                /* if (sharedPreferencesClass.isloginpref() != null) {*/
                DealsFragment.getInstance().alertDialogFilter();
               /* } else {
                    loginDialog();
                }*/

                break;
            case R.id.txt_trackorder:
                if (sharedPreferencesClass.isloginpref() != null) {

                    ivFilter.setVisibility(View.GONE);
                    etLocation.setVisibility(View.GONE);
                    startActivity(new Intent(DashboardActivity.this, OrderStatusActivity.class));
                    if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                        drawer.closeDrawer(Gravity.RIGHT);
                    } else {
                        drawer.openDrawer(Gravity.RIGHT);
                    }
                } else {
                    loginDialog();
                }
                break;
            case R.id.menuId:
                etLocation.setVisibility(View.GONE);
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                if (sharedPreferencesClass.isloginpref() != null) {
                    Glide.with(this).load(val.getProfileImage()).apply(new RequestOptions()
                            .placeholder(R.mipmap.avatar_profile))
                            .into(drawer_profile_pic);
                    myAccount.setText(val.getFirstName() + " " + val.getLastName());
                }
                break;

            case R.id.my_account:
                if (sharedPreferencesClass.isloginpref() != null) {

                    etLocation.setVisibility(View.GONE);
                } else {
                    loginDialog();
                }
                break;
            case R.id.top_ac:
                if (sharedPreferencesClass.isloginpref() != null) {

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
                } else {
                    loginDialog();
                }
                break;

            case R.id.manageAddressId:
                if (sharedPreferencesClass.isloginpref() != null) {

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

                } else {
                    loginDialog();
                }
                break;
            case R.id.list_of_address:
                if (sharedPreferencesClass.isloginpref() != null) {

                    tvToolbarTitle.setText("Restaurants");
                    etLocation.setVisibility(View.GONE);
                    ivFilter.setVisibility(View.VISIBLE);
                    Constants.fragmentCall(new DealsFragment(getApplicationContext(), DashboardActivity.this, tvToolbarTitle, postCode), getSupportFragmentManager());

                    if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                        drawer.closeDrawer(Gravity.RIGHT);
                    } else {
                        drawer.openDrawer(Gravity.RIGHT);
                    }
                } else {
                    loginDialog();
                }
                break;
            case R.id.home:
                setDefaultDrawer();
               /* etLocation.setVisibility(View.GONE);
                home.setBackgroundColor(getResources().getColor(R.color.orange));
                listOfAddress.setTextColor(getResources().getColor(R.color.white));
                tvToolbarTitle.setText("Restaurants");
                ivFilter.setVisibility(View.VISIBLE);
                Constants.fragmentCall(new DealsFragment(getApplicationContext(), DashboardActivity.this, tvToolbarTitle, postCode), getSupportFragmentManager());

*/
                startActivity(new Intent(DashboardActivity.this, SearchPostCodeActivity.class));
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }

                break;
            case R.id.add_new_address:
                if (sharedPreferencesClass.isloginpref() != null) {

                    etLocation.setVisibility(View.GONE);
                    if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                        drawer.closeDrawer(Gravity.RIGHT);
                    } else {
                        drawer.openDrawer(Gravity.RIGHT);
                    }
                } else {
                    loginDialog();
                }
                break;
            case R.id.new_address:
                if (sharedPreferencesClass.isloginpref() != null) {

                    etLocation.setVisibility(View.GONE);
                    if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                        drawer.closeDrawer(Gravity.RIGHT);
                    } else {
                        drawer.openDrawer(Gravity.RIGHT);
                    }
                } else {
                    loginDialog();
                }
                break;
            case R.id.payments:
                if (sharedPreferencesClass.isloginpref() != null) {

                    etLocation.setVisibility(View.GONE);
                    ivFilter.setVisibility(View.GONE);
                    tvToolbarTitle.setText("My Saved Cards");
                    Constants.fragmentCall(new CardsListFragment(DashboardActivity.this), getSupportFragmentManager());
                    if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                        drawer.closeDrawer(Gravity.RIGHT);
                    } else {
                        drawer.openDrawer(Gravity.RIGHT);
                    }
                } else {
                    loginDialog();
                }
                break;
            case R.id.paymentId:
                if (sharedPreferencesClass.isloginpref() != null) {

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
                } else {
                    loginDialog();
                }
                break;
            case R.id.my_credit_debit_card:
                if (sharedPreferencesClass.isloginpref() != null) {

                    etLocation.setVisibility(View.GONE);
                    if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                        drawer.closeDrawer(Gravity.RIGHT);
                    } else {
                        drawer.openDrawer(Gravity.RIGHT);
                    }
                } else {
                    loginDialog();
                }
                break;
            case R.id.creditCardId:
                if (sharedPreferencesClass.isloginpref() != null) {

                    etLocation.setVisibility(View.GONE);
                    if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                        drawer.closeDrawer(Gravity.RIGHT);
                    } else {
                        drawer.openDrawer(Gravity.RIGHT);
                    }
                } else {
                    loginDialog();
                }
                break;
            case R.id.add_credit_debit_card:
                if (sharedPreferencesClass.isloginpref() != null) {

                    etLocation.setVisibility(View.GONE);
                    if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                        drawer.closeDrawer(Gravity.RIGHT);
                    } else {
                        drawer.openDrawer(Gravity.RIGHT);
                    }
                } else {
                    loginDialog();
                }
                break;
            case R.id.new_card:
                if (sharedPreferencesClass.isloginpref() != null) {

                    etLocation.setVisibility(View.GONE);
                    if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                        drawer.closeDrawer(Gravity.RIGHT);
                    } else {
                        drawer.openDrawer(Gravity.RIGHT);
                    }
                } else {
                    loginDialog();
                }
                break;
            case R.id.my_basket:
                if (sharedPreferencesClass.isloginpref() != null) {

                    etLocation.setVisibility(View.GONE);
                    ivFilter.setVisibility(View.GONE);
                    tvToolbarTitle.setText("Order Summary");
                    Constants.fragmentCall(new MyBasketFragment(DashboardActivity.this, getApplicationContext(), false), getSupportFragmentManager());
                    if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                        drawer.closeDrawer(Gravity.RIGHT);
                    } else {
                        drawer.openDrawer(Gravity.RIGHT);
                    }
                } else {
                    loginDialog();
                }
                break;
            case R.id.my_basket_id:
                if (sharedPreferencesClass.isloginpref() != null) {

                    etLocation.setVisibility(View.GONE);
                    setDefaultDrawer();
                    myBasketId.setBackgroundColor(getResources().getColor(R.color.orange));
                    myBasket.setTextColor(getResources().getColor(R.color.white));
                    ivFilter.setVisibility(View.GONE);
                    tvToolbarTitle.setText("Order Summary");
                    Constants.fragmentCall(new MyCartFragment(DashboardActivity.this, getApplicationContext(), false), getSupportFragmentManager());
                    if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                        drawer.closeDrawer(Gravity.RIGHT);
                    } else {
                        drawer.openDrawer(Gravity.RIGHT);
                    }
                } else {
                    loginDialog();
                }
                break;
            case R.id.my_orders:
                if (sharedPreferencesClass.isloginpref() != null) {

                    etLocation.setVisibility(View.GONE);
                    ivFilter.setVisibility(View.GONE);
                    tvToolbarTitle.setText("My Orders");
                    Constants.fragmentCall(new PreviousOrderFragment(getApplicationContext(), tvToolbarTitle), getSupportFragmentManager());
                    if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                        drawer.closeDrawer(Gravity.RIGHT);
                    } else {
                        drawer.openDrawer(Gravity.RIGHT);
                    }
                } else {
                    loginDialog();
                }
                break;
            case R.id.my_orderId:
                if (sharedPreferencesClass.isloginpref() != null) {

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
                } else {
                    loginDialog();
                }
                break;
            case R.id.favourites:
                if (sharedPreferencesClass.isloginpref() != null) {

                    etLocation.setVisibility(View.GONE);
                    ivFilter.setVisibility(View.GONE);
                    tvToolbarTitle.setText("Favourites");
                    Constants.fragmentCall(new FavouritesFragment(getApplicationContext()), getSupportFragmentManager());
                    if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                        drawer.closeDrawer(Gravity.RIGHT);
                    } else {
                        drawer.openDrawer(Gravity.RIGHT);
                    }
                } else {
                    loginDialog();
                }
                break;
            case R.id.myfevId:
                if (sharedPreferencesClass.isloginpref() != null) {

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
                } else {
                    loginDialog();
                }
                break;
            case R.id.privacy_policy:
                etLocation.setVisibility(View.GONE);
                if (isInternetOn(DashboardActivity.this)) {
                    mDialog.show();
                    getPrivacyPolicy();
                } else {
                    showDialog("Please check internet connection.");
                }
                if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.privacyId:

                if (isInternetOn(DashboardActivity.this)) {
                    mDialog.show();
                    getPrivacyPolicy();
                } else {
                    showDialog("Please check internet connection.");
                }


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
                if (sharedPreferencesClass.isloginpref() != null) {

                    etLocation.setVisibility(View.GONE);
                    if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                        drawer.closeDrawer(Gravity.RIGHT);
                    } else {
                        drawer.openDrawer(Gravity.RIGHT);
                    }
                } else {
                    loginDialog();
                }
                break;
            case R.id.logout:
                if (sharedPreferencesClass.isloginpref() != null) {

                    etLocation.setVisibility(View.GONE);
                    setDefaultDrawer();
                    logout.setBackgroundColor(getResources().getColor(R.color.orange));
                    tvLogout.setTextColor(getResources().getColor(R.color.white));

                    if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                        drawer.closeDrawer(Gravity.RIGHT);
                    } else {
                        drawer.openDrawer(Gravity.RIGHT);
                    }

                    if (isInternetOn(DashboardActivity.this)) {
                        mDialog.show();
                        getLogout(sharedPreferencesClass.getString(sharedPreferencesClass.USER_ID));
                    } else {
                        Constants.showDialog(DashboardActivity.this, "Please check internet connection.");
                    }
                } else {


                    Intent i = new Intent(DashboardActivity.this, LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                }
                break;
            case R.id.menuIdRl:
                if (sharedPreferencesClass.isloginpref() != null) {

                    etLocation.setVisibility(View.GONE);
                } else {
                    loginDialog();
                }
                break;
            case R.id.et_location:
                if (sharedPreferencesClass.isloginpref() != null) {
                    PrefManager.getInstance(DashboardActivity.this).savePreference(IS_FROM_DEAL_PAGE, true);
                    //val.setIsFromDealPage(true);
                    Intent i = new Intent(DashboardActivity.this, SearchPostCodeActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                } else {
                    loginDialog();
                }
                break;
        }
    }

    void callWebviewPrivacy(String policy) {
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
        webView.loadDataWithBaseURL("", policy, "text/html", "UTF-8", "");
        //webView.loadUrl(ApiConstants.PRIVACY_POLICY);

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
                boolean isFavorite = intent.getBooleanExtra(getString(R.string.isFavorate), false);
                myBasketId.setBackgroundColor(getResources().getColor(R.color.orange));
                myBasket.setTextColor(getResources().getColor(R.color.white));
                ivFilter.setVisibility(View.GONE);
                tvToolbarTitle.setText("Order Summary");
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().addToBackStack(null);
                //   transaction.replace(R.id.frameLayout, new MyBasketFragment(DashboardActivity.this, getApplicationContext(), isFavorite));
                transaction.replace(R.id.frameLayout, new MyCartFragment(DashboardActivity.this, getApplicationContext(), isFavorite));
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
        if (sharedPreferencesClass.isloginpref() != null) {
            Glide.with(this).load(val.getProfileImage()).apply(new RequestOptions()
                    .placeholder(R.mipmap.avatar_profile))
                    .into(drawer_profile_pic);
            tvLogout.setText("Logout");
        } else {
            myAccount.setText("Guest User");
            tvLogout.setText("Login");

        }
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
        request.setFcm_id(sharedPreferencesClass.getString(sharedPreferencesClass.FB_TOKEN_ID));
        Log.e("PrintLogut", "" + request);
        Call<LogoutResponse> call3 = apiInterface.logout(PrefManager.getInstance(DashboardActivity.this).getPreference(AUTH_TOKEN, ""), request);
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
                                        mDB.saveOrderHistry().deleteAll();
                                        sharedPreferencesClass.setloginpref(null);
                                        sharedPreferencesClass.deleteAllPreference();

                                        sharedPreferencesClass.logout();
                                        Intent i = new Intent(DashboardActivity.this, SearchPostCodeActivity.class);
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

    public void loginDialog() {
        LayoutInflater factory = LayoutInflater.from(DashboardActivity.this);
        final View mDialogVieww = factory.inflate(R.layout.layout_login_dialog, null);
        final AlertDialog alertClodseDialog = new AlertDialog.Builder(DashboardActivity.this).create();
        alertClodseDialog.setView(mDialogVieww);
        alertClodseDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mDialogVieww.findViewById(R.id.tv_btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, LoginActivity.class);
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

    public void showDialog(String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(DashboardActivity.this);
        builder1.setMessage(msg);
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog2, int id) {
                        dialog2.cancel();

                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    public void getPrivacyPolicy() {
        SearchPostCodeInterface apiInterface = ApiClient.getClient(getApplicationContext()).create(SearchPostCodeInterface.class);


        Call<PrivacyBean> call3 = apiInterface.getPolicy(PrefManager.getInstance(DashboardActivity.this).getPreference(AUTH_TOKEN, ""));
        call3.enqueue(new Callback<PrivacyBean>() {
            @Override
            public void onResponse(Call<PrivacyBean> call, Response<PrivacyBean> response) {
                try {

                    if (response.body().isSuccess()) {
                        mDialog.hide();
                        callWebviewPrivacy(response.body().getData().getPrivacy_policy());

                    } else {
                        mDialog.hide();
                    }
                } catch (Exception e) {
                    mDialog.hide();
                    dialogNoInternetConnection("Please check internet connection.");
                }
            }

            @Override
            public void onFailure(Call<PrivacyBean> call, Throwable t) {
                Log.e("Error <>>>", ">>>>>" + t.getMessage());
                mDialog.hide();
                dialogNoInternetConnection("Please check internet connection.");
            }
        });
    }

    public void dialogNoInternetConnection(String message) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View mDialogView = factory.inflate(R.layout.addnote_success_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setView(mDialogView);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);

        TextView tvMessage = mDialogView.findViewById(R.id.message);
        tvMessage.setText(message);
        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetOn(DashboardActivity.this)) {
                    alertDialog.dismiss();
                } else mDialogView.findViewById(R.id.okTv).startAnimation(animShake);

            }
        });

        alertDialog.show();
    }

    public void errorDialog(String msg1, String msg2) {
        View dialogView = LayoutInflater.from(DashboardActivity.this).inflate(R.layout.no_resturent_popup, null);
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView.getRootView());
        dialog.setCancelable(false);


        TextView tvMsg1, tvMsg2;

        tvMsg1 = dialogView.findViewById(R.id.tv_msg1);
        tvMsg2 = dialogView.findViewById(R.id.tv_msg2);

        tvMsg1.setText(msg1);
        tvMsg2.setText(msg2);
        if (msg2 == null) {
            tvMsg2.setVisibility(View.GONE);
        }

        dialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();


            }
        });
        dialogView.findViewById(R.id.cross_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
