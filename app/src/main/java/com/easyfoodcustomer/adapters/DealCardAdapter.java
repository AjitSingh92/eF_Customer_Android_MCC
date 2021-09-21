package com.easyfoodcustomer.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.easyfoodcustomer.databinding.LayoutConfirmationDialogBinding;
import com.easyfoodcustomer.databinding.LayoutServestyleDialogBinding;
import com.easyfoodcustomer.model.landing_page_response.DiscountOffer;
import com.easyfoodcustomer.roomData.AppDatabase;
import com.easyfoodcustomer.utility.Helper;
import com.easyfoodcustomer.utility.PrefManager;
import com.google.gson.Gson;
import com.easyfoodcustomer.R;
import com.easyfoodcustomer.api.AddFavouritesInterface;
import com.easyfoodcustomer.cart_db.DatabaseHelper;
import com.easyfoodcustomer.model.add_favourites_request.AddFavouristeResquest;
import com.easyfoodcustomer.model.add_favourites_response.AddFavouristeResponse;
import com.easyfoodcustomer.model.landing_page_response.RestaurantsDealResponse;
import com.easyfoodcustomer.restaurant_details.RestaurantDetailsActivity;
import com.easyfoodcustomer.utility.ApiClient;
import com.easyfoodcustomer.utility.GlobalValues;
import com.easyfoodcustomer.utility.SharedPreferencesClass;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.easyfoodcustomer.utility.SharedPreferencesClass.DELIVERY_MOBILE_NUMBER;
import static com.easyfoodcustomer.utility.SharedPreferencesClass.IS_FOR_TABLE;
import static com.easyfoodcustomer.utility.SharedPreferencesClass.SERVE_STYLE;
import static com.easyfoodcustomer.utility.UserContants.AUTH_TOKEN;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.easyfoodcustomer.utility.SharedPreferencesClass.OFFERR_DETAL_DFG;

public class DealCardAdapter extends RecyclerView.Adapter<DealCardAdapter.MyViewHolder> implements View.OnClickListener {

    RestaurantsDealResponse.Data.Restaurant response;

    private Context mContext;
    int mSize = 0, mListPosition;
    String userID = "";
    Activity activity;
    SharedPreferencesClass sharePre;
    DatabaseHelper db;
    GlobalValues val;
    AppDatabase mDB;


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.closed_design) {
            if (!response.getMode().equalsIgnoreCase("development")) {
                if (sharePre.getString(sharePre.RESTUARANT_ID) != null && !sharePre.getString(sharePre.RESTUARANT_ID).isEmpty()) {
                    sharePre.setString(OFFERR_DETAL_DFG, null);
                    if (sharePre.getString(sharePre.RESTUARANT_ID).equalsIgnoreCase(response.getId())) {
                        if (sharePre.getString(SERVE_STYLE) != null && mDB.saveOrderHistry().loadAllHistoryOfOrder().size() > 0) {
                            serveStylePopupp(response.getServe_style(), response.getId(), false, null);

                        } else {
                            serveStylePopup(response.getServe_style(), response.getId(), false, null);
                        }
//                activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                    } else {
                        if (mDB.saveOrderHistry().loadAllHistoryOfOrder().size() > 0) {
                            String msg = "You have already placing an order with " + sharePre.getString(sharePre.RESTUARANT_NAME);
                            alreadyAlertDialog(msg, sharePre.getString(sharePre.RESTUARANT_NAME), response.getRestaurantName(), response.getId(), response.getServe_style());

                        } else {
                            serveStylePopup(response.getServe_style(), response.getId(), false, null);
                    /*if (sharePre.getString(SERVE_STYLE) != null) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                GlobalValues.getInstance().getDb().menuMaster().nuke();
                                GlobalValues.getInstance().getDb().menuProductMaster().nuke();
                                GlobalValues.getInstance().getDb().productSizeAndModifierMaster().nuke();
                                sharePre.setString(sharePre.DEFAULT_ADDRESS, null);
                                sharePre.setString(sharePre.NOTEPAD, "");

                                db.getCartData();
                                Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                                sharePre.setString(sharePre.RESTUARANT_ID, response.getId());
                                sharePre.setString(sharePre.RESTUARANT_NAME, response.getRestaurantName());
                                i.putExtra("RESTAURANTID", response.getId());
                                i.putExtra("RESTAURANTNAME", response.getRestaurantName());
                                i.putExtra("ServeStyle", sharePre.getString(SERVE_STYLE));
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(i);
                                activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                            }
                        }).start();
                    } else {
                        serveStylePopup(response.getServe_style(), response.getId());
                    }*/
                        }
                    }
                } else {

                    serveStylePopup(response.getServe_style(), response.getId(), false, null);

                }
            }
        } else if (v.getId() == R.id.pre_order) {
            if (!response.getMode().equalsIgnoreCase("development")) {
                if (sharePre.getString(sharePre.RESTUARANT_ID) != null && !sharePre.getString(sharePre.RESTUARANT_ID).isEmpty()) {
                    sharePre.setString(OFFERR_DETAL_DFG, null);
                    if (sharePre.getString(sharePre.RESTUARANT_ID).equalsIgnoreCase(response.getId())) {
                        if (sharePre.getString(SERVE_STYLE) != null && mDB.saveOrderHistry().loadAllHistoryOfOrder().size() > 0) {
                            serveStylePopupp(response.getServe_style(), response.getId(), false, null);

                        } else {
                            serveStylePopup(response.getServe_style(), response.getId(), false, null);
                        }
//                activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                    } else {
                        if (mDB.saveOrderHistry().loadAllHistoryOfOrder().size() > 0) {
                            String msg = "You have already placing an order with " + sharePre.getString(sharePre.RESTUARANT_NAME);
                            alreadyAlertDialog(msg, sharePre.getString(sharePre.RESTUARANT_NAME), response.getRestaurantName(), response.getId(), response.getServe_style());

                        } else {
                            serveStylePopup(response.getServe_style(), response.getId(), false, null);
                    /*if (sharePre.getString(SERVE_STYLE) != null) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                GlobalValues.getInstance().getDb().menuMaster().nuke();
                                GlobalValues.getInstance().getDb().menuProductMaster().nuke();
                                GlobalValues.getInstance().getDb().productSizeAndModifierMaster().nuke();
                                sharePre.setString(sharePre.DEFAULT_ADDRESS, null);
                                sharePre.setString(sharePre.NOTEPAD, "");

                                db.getCartData();
                                Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                                sharePre.setString(sharePre.RESTUARANT_ID, response.getId());
                                sharePre.setString(sharePre.RESTUARANT_NAME, response.getRestaurantName());
                                i.putExtra("RESTAURANTID", response.getId());
                                i.putExtra("RESTAURANTNAME", response.getRestaurantName());
                                i.putExtra("ServeStyle", sharePre.getString(SERVE_STYLE));
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(i);
                                activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                            }
                        }).start();
                    } else {
                        serveStylePopup(response.getServe_style(), response.getId());
                    }*/
                        }
                    }
                } else {

                    serveStylePopup(response.getServe_style(), response.getId(), false, null);

                }
            }
        } else if (v.getId() == R.id.layout_restaurant) {
            if (!response.getMode().equalsIgnoreCase("development")) {
                if (sharePre.getString(sharePre.RESTUARANT_ID) != null && !sharePre.getString(sharePre.RESTUARANT_ID).isEmpty()) {
                    sharePre.setString(OFFERR_DETAL_DFG, null);
                    if (sharePre.getString(sharePre.RESTUARANT_ID).equalsIgnoreCase(response.getId())) {
                        if (sharePre.getString(SERVE_STYLE) != null && mDB.saveOrderHistry().loadAllHistoryOfOrder().size() > 0) {
                            serveStylePopupp(response.getServe_style(), response.getId(), false, null);

                        } else {
                            serveStylePopup(response.getServe_style(), response.getId(), false, null);
                        }


//                activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                    } else {
                        if (mDB.saveOrderHistry().loadAllHistoryOfOrder().size() > 0) {
                            String msg = "You have already placing an order with " + sharePre.getString(sharePre.RESTUARANT_NAME);
                            alreadyAlertDialog(msg, sharePre.getString(sharePre.RESTUARANT_NAME), response.getRestaurantName(), response.getId(), response.getServe_style());

                        } else {
                            serveStylePopup(response.getServe_style(), response.getId(), false, null);
                    /*if (sharePre.getString(SERVE_STYLE) != null) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                GlobalValues.getInstance().getDb().menuMaster().nuke();
                                GlobalValues.getInstance().getDb().menuProductMaster().nuke();
                                GlobalValues.getInstance().getDb().productSizeAndModifierMaster().nuke();
                                sharePre.setString(sharePre.DEFAULT_ADDRESS, null);
                                sharePre.setString(sharePre.NOTEPAD, "");

                                db.getCartData();
                                Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                                sharePre.setString(sharePre.RESTUARANT_ID, response.getId());
                                sharePre.setString(sharePre.RESTUARANT_NAME, response.getRestaurantName());
                                i.putExtra("RESTAURANTID", response.getId());
                                i.putExtra("RESTAURANTNAME", response.getRestaurantName());
                                i.putExtra("ServeStyle", sharePre.getString(SERVE_STYLE));
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(i);
                                activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                            }
                        }).start();
                    } else {
                        serveStylePopup(response.getServe_style(), response.getId());
                    }*/
                        }
                    }
                } else {
                    // if (sharePre.getString(SERVE_STYLE) != null) {

                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GlobalValues.getInstance().getDb().menuMaster().nuke();
                        GlobalValues.getInstance().getDb().menuProductMaster().nuke();
                        GlobalValues.getInstance().getDb().productSizeAndModifierMaster().nuke();
                        sharePre.setString(sharePre.DEFAULT_ADDRESS, null);
                        sharePre.setString(sharePre.NOTEPAD, "");
                        db.getCartData();
                        sharePre.setString(sharePre.RESTUARANT_ID, response.getId());
                        sharePre.setString(sharePre.RESTUARANT_NAME, response.getRestaurantName());
                        Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                        i.putExtra("RESTAURANTID", response.getId());
                        i.putExtra("RESTAURANTNAME", response.getRestaurantName());
                        i.putExtra("ServeStyle", sharePre.getString(SERVE_STYLE));
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                    }
                }).start();*/
                    serveStylePopup(response.getServe_style(), response.getId(), false, null);
            /*} else {
                serveStylePopup(response.getServe_style(), response.getId());
            }*/
                }
            }
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView logo, bgImage, menuLogo, favIcon, arraowAnimation;
        TextView offerTitle, offerItems/*, offerPrice*/;
        ImageView ivOffer;
        LinearLayout clickRestaurant;
        Button btnSeeMenu, btnGetDeals;


        //////////////////////////////
        LinearLayout btnPreOrder, layoutDeliveryPrice, layoutDeliveryTime, llEnd;
        RelativeLayout comingSoon;
        TextView name, cuisines, rating, deliveryMin, deliveryVal, deliveryTime, preOrder, tvPreOrderMsg, tvDistance;
        ImageView delivery, dine_in, collection;
        LinearLayout llMain, llClosed;
        LinearLayout llDelivery, llDinein, llCollection;
        ImageView imRatingImage;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.logo = (ImageView) itemView.findViewById(R.id.restaurant_logo);
            this.bgImage = (ImageView) itemView.findViewById(R.id.restaurant_image);
            this.favIcon = (ImageView) itemView.findViewById(R.id.favourites);
            this.arraowAnimation = (ImageView) itemView.findViewById(R.id.detail_arraw);
            this.offerItems = (TextView) itemView.findViewById(R.id.txt_offer_items);
            this.llEnd = (LinearLayout) itemView.findViewById(R.id.ll_end);
//            this.offerPrice = (TextView) itemView.findViewById(R.id.txt_price);
            this.btnSeeMenu = (Button) itemView.findViewById(R.id.btn_see_full_menu);
            this.btnGetDeals = (Button) itemView.findViewById(R.id.btn_get_deals);

            this.ivOffer = (ImageView) itemView.findViewById(R.id.iv_offer);
            this.offerTitle = (TextView) itemView.findViewById(R.id.dealNameId);
            this.clickRestaurant = (LinearLayout) itemView.findViewById(R.id.layout_restaurant);
            this.menuLogo = (ImageView) itemView.findViewById(R.id.image_menu_logo);


            /////////////////////////////////////////////////////
            this.tvPreOrderMsg = (TextView) itemView.findViewById(R.id.tv_PreOrderMsg);
            this.btnPreOrder = (LinearLayout) itemView.findViewById(R.id.layout_btnPreOrder);
            this.comingSoon = (RelativeLayout) itemView.findViewById(R.id.layout_comingsoon);
            this.layoutDeliveryPrice = (LinearLayout) itemView.findViewById(R.id.layout_deliveryPrice);
            this.layoutDeliveryTime = (LinearLayout) itemView.findViewById(R.id.layout_deliveryTime);

            this.name = (TextView) itemView.findViewById(R.id.restaurant_name);
            this.cuisines = (TextView) itemView.findViewById(R.id.restaurant_cuisines);
            this.rating = (TextView) itemView.findViewById(R.id.restaurant_rating);
            this.deliveryMin = (TextView) itemView.findViewById(R.id.restaurant_delivery_min_order);
            this.deliveryTime = (TextView) itemView.findViewById(R.id.restaurant_delivery_time);
            this.deliveryVal = (TextView) itemView.findViewById(R.id.restaurant_delivery_value);
            this.preOrder = (TextView) itemView.findViewById(R.id.pre_order);
            this.llMain = (LinearLayout) itemView.findViewById(R.id.ll_main);
            this.llClosed = (LinearLayout) itemView.findViewById(R.id.closed_design);
            this.tvDistance = (TextView) itemView.findViewById(R.id.tv_distance);

            this.imRatingImage = itemView.findViewById(R.id.im_ratingImage);
            this.llDelivery = itemView.findViewById(R.id.ll_delivery);
            this.delivery = itemView.findViewById(R.id.delivery);
            this.llDinein = itemView.findViewById(R.id.ll_dinein);
            this.dine_in = itemView.findViewById(R.id.dine_in);
            this.llCollection = itemView.findViewById(R.id.ll_collection);
            this.collection = itemView.findViewById(R.id.collection);
        }
    }

    public DealCardAdapter(Context context, RestaurantsDealResponse.Data.Restaurant res, int count, int lPos, String userid, Activity activity) {
        this.response = res;
        this.mContext = context;
        this.mSize = count;
        this.mListPosition = lPos;
        this.userID = userid;
        this.activity = activity;
        mDB = AppDatabase.getInstance(getApplicationContext());
        val = (GlobalValues) getApplicationContext();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.deal_item_show_menu, parent, false);

        } else if (viewType == 2) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.deal_last_item_row, parent, false);

        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.deal_item_row, parent, false);
        }


        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final MyViewHolder mHolder = holder;
        sharePre = new SharedPreferencesClass(mContext);
        db = new DatabaseHelper(mContext);


        if (position == 0) {
            if (response.getFavourite() == 1) {
                holder.favIcon.setBackground(mContext.getResources().getDrawable(R.drawable.favourite_active));
            } else {
                holder.favIcon.setBackground(mContext.getResources().getDrawable(R.drawable.favourite_white));
            }
           /* if (!response.getMode().equalsIgnoreCase("live")) {
                holder.comingSoon.setVisibility(View.VISIBLE);
                holder.btnPreOrder.setVisibility(View.GONE);
                Log.e("PrintMode", "" + response.getMode());
            } else {
                holder.comingSoon.setVisibility(View.GONE);
            }*/

            Glide.with(mContext).asGif().load(R.drawable.animated_arrow2).into(holder.arraowAnimation);
            holder.favIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addFavourites(response.getId(), mHolder.favIcon, response.getFavourite());
                }
            });

            if (response.getLogo() != null) {
                Glide.with(activity).load(response.getLogo()).apply(new RequestOptions()
                        .placeholder(R.drawable.easy_food_image))
                        .into(holder.logo);
            }
            if (response.getRestaurantsGallery().size() > 0)
                Glide.with(activity).load(response.getRestaurantsGallery().get(0).getFilePath()).apply(new RequestOptions())
                        .into(holder.bgImage);
            holder.clickRestaurant.setOnClickListener(this);
            holder.llClosed.setOnClickListener(this);
            holder.preOrder.setOnClickListener(this);
////////////////////

            //////////////////////////////******************** from deal adptr************************///////////////////////////////

            sharePre = new SharedPreferencesClass(mContext);
            db = new DatabaseHelper(mContext);
            final int mListPosition = position;
            if (position == mSize - 1) {
//                onBottomReachedListener.onBottomReached(position);
            }

            if (sharePre.isloginpref() != null)
                holder.favIcon.setVisibility(View.VISIBLE);
            else
                holder.favIcon.setVisibility(View.GONE);

            holder.name.setText(response.getRestaurantName());
            if (sharePre.getInt(SharedPreferencesClass.IS_FOR_TABLE) == 0) {
                holder.tvDistance.setText(response.getDistance_in_miles() + " miles");
            } else {
                holder.tvDistance.setVisibility(View.GONE);
                holder.llEnd.setVisibility(View.GONE);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.cuisines.setText(Html.fromHtml(response.getCuisines(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                holder.cuisines.setText(Html.fromHtml(response.getCuisines()));
            }


            if (response.getOverallRating() != null) {
                if (response.getOverallRating().equalsIgnoreCase("0")) {
                    holder.rating.setText("New");
                    holder.imRatingImage.setVisibility(View.GONE);
                } else {
                    holder.imRatingImage.setVisibility(View.VISIBLE);
                    holder.rating.setText(String.format("%.1f", Double.parseDouble(response.getOverallRating())));
                }
            } else {
                holder.rating.setText("New");
                holder.imRatingImage.setVisibility(View.GONE);
            }

            String status = response.getStatus();

            if (!response.getMode().equalsIgnoreCase("live")) {
                holder.llClosed.setVisibility(View.GONE);
                holder.btnPreOrder.setVisibility(View.GONE);
                holder.comingSoon.setVisibility(View.VISIBLE);

            } else {

                try {


                    if (status.trim().equalsIgnoreCase("closed")) {
                        holder.llClosed.setVisibility(View.VISIBLE);
                        holder.preOrder.setVisibility(View.VISIBLE);
                        holder.tvPreOrderMsg.setText(mContext.getResources().getString(R.string.restaurent_closed));
                    } else if (status.trim().equalsIgnoreCase("not_serving")) {
                        holder.preOrder.setVisibility(View.GONE);
                        holder.llClosed.setVisibility(View.VISIBLE);
                        holder.tvPreOrderMsg.setText(mContext.getResources().getString(R.string.restaurent_closed3));
                    } else {
                        holder.llClosed.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            holder.llCollection.setVisibility(View.GONE);
            holder.llDelivery.setVisibility(View.GONE);
            holder.llDinein.setVisibility(View.GONE);
            Log.e("Current Day", "" + Helper.getCurrentDay());

            if (response.getServe_style() != null || !response.getServe_style().equals("")) {
                String[] serve_styles = response.getServe_style().split(",");

                if (Arrays.asList(serve_styles).contains("collection")) {
                    holder.llCollection.setVisibility(View.VISIBLE);
                    holder.collection.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_orage_tick));
                } else {
                    holder.llCollection.setVisibility(View.VISIBLE);
                    holder.collection.setImageDrawable(mContext.getResources().getDrawable(R.drawable.closed));
                }
                if (Arrays.asList(serve_styles).contains("delivery")) {
                    holder.llDelivery.setVisibility(View.VISIBLE);
                    holder.delivery.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_orage_tick));
                } else {
                    holder.llDelivery.setVisibility(View.VISIBLE);
                    holder.delivery.setImageDrawable(mContext.getResources().getDrawable(R.drawable.closed));
                }
                if (Arrays.asList(serve_styles).contains("dine_in")) {
                    holder.llDinein.setVisibility(View.VISIBLE);
                    holder.dine_in.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_orage_tick));
                } else {
                    holder.llDinein.setVisibility(View.VISIBLE);
                    holder.dine_in.setImageDrawable(mContext.getResources().getDrawable(R.drawable.closed));
                }
            }
            Log.e("printTime", "" + response.getRestaurantTiming().get(0).getCollectionStartTime());


            if (sharePre.getInt(SharedPreferencesClass.IS_FOR_TABLE) == 0) {
               /* if (response.getDelivery_charge() != null && !response.getDelivery_charge().trim().isEmpty()) {
                    holder.deliveryMin.setText(mContext.getResources().getString(R.string.currency) + String.format("%.2f", Double.parseDouble(response.getDelivery_charge())) + " delivery ");
                } else {
                    holder.deliveryMin.setText(mContext.getResources().getString(R.string.currency) + "0.00" + " delivery ");
                }*/

                holder.deliveryMin.setText(response.getService_charge());

                if (response.getMin_order_value() != null && !response.getMin_order_value().trim().isEmpty()) {
                    holder.deliveryVal.setText(mContext.getResources().getString(R.string.currency) + String.format("%.2f", Double.parseDouble(response.getMin_order_value())) + " min order");
                } else {
                    holder.deliveryVal.setText(mContext.getResources().getString(R.string.currency) + "0.00" + " min order");
                }
                holder.deliveryTime.setText(response.getAvgDeliveryTime() + " min");
            } else {
                holder.deliveryMin.setVisibility(View.GONE);
                holder.deliveryVal.setVisibility(View.GONE);
                holder.deliveryTime.setVisibility(View.GONE);
                holder.layoutDeliveryPrice.setVisibility(View.GONE);
            }

            //////////////////////////**************************************////////////////////////////////////////

            //////////////////////////
        } else if (position > 0 && position <= mSize) {

            if (response.getDiscountOffers().size() > 0) {
                holder.offerItems.setText(response.getDiscountOffers().get(position - 1).getTerms_conditions());
//                holder.offerPrice.setText(response.getDiscountOffers().get(position - 1).getOfferPriceLabel());
                holder.offerTitle.setText(response.getDiscountOffers().get(position - 1).getDetail());


                Glide.with(activity).load(response.getDiscountOffers().get(position - 1).getOffer_image()).apply(new RequestOptions()
                        .placeholder(R.drawable.easy_food_image))
                        .into(holder.ivOffer);

            }


            holder.btnGetDeals.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                /*    //offer_price
                    offer_type
                            offer_id
                    min_value*/

                    Log.e("Discount", "" + response.getDiscountOffers().get(position - 1).getOffer_available());

                    if (sharePre.getString(sharePre.RESTUARANT_ID) != null && !sharePre.getString(sharePre.RESTUARANT_ID).isEmpty()) {
                        sharePre.setString(OFFERR_DETAL_DFG, null);
                        if (sharePre.getString(sharePre.RESTUARANT_ID).equalsIgnoreCase(response.getId())) {
                            if (sharePre.getString(SERVE_STYLE) != null && mDB.saveOrderHistry().loadAllHistoryOfOrder().size() > 0) {
                                serveStylePopupp(response.getServe_style(), response.getId(), true, response.getDiscountOffers().get(position - 1));

                            } else {
                                serveStylePopup(response.getServe_style(), response.getId(), true, response.getDiscountOffers().get(position - 1));
                            }
//                activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                        } else {
                            if (mDB.saveOrderHistry().loadAllHistoryOfOrder().size() > 0) {
                                String msg = "You have already placing an order with " + sharePre.getString(sharePre.RESTUARANT_NAME);
                                alreadyAlertDialog(msg, sharePre.getString(sharePre.RESTUARANT_NAME), response.getRestaurantName(), response.getId(), response.getServe_style());

                            } else {
                                serveStylePopup(response.getServe_style(), response.getId(), true, response.getDiscountOffers().get(position - 1));

                            }
                        }
                    } else {

                        serveStylePopup(response.getServe_style(), response.getId(), true, response.getDiscountOffers().get(position - 1));

                    }


                  /*   ,m

                    sharePre.setString(OFFERR_DETAL_DFG, null);

                    if (sharePre.getString(sharePre.RESTUARANT_ID) != null && !sharePre.getString(sharePre.RESTUARANT_ID).equals("")) {
                        if (sharePre.getString(sharePre.RESTUARANT_ID).equalsIgnoreCase(response.getId())) {
                            Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                            i.putExtra("RESTAURANTID", response.getId());
                            i.putExtra("RESTAURANTNAME", response.getRestaurantName());
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(i);
                            activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                        } else {
                            if (db.getCartData().getMenuCategoryCarts().size() + db.getCartData().getSpecialOffers().size() + db.getCartData().getUpsellProducts().size() > 0) {
                                String msg = "You have already placing an order with " + sharePre.getString(sharePre.RESTUARANT_NAME);
                                alreadyAlertDialog(msg, sharePre.getString(sharePre.RESTUARANT_NAME), response.getRestaurantName(), response.getId(), response.getServe_style());

                            } else {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        GlobalValues.getInstance().getDb().menuMaster().nuke();
                                        GlobalValues.getInstance().getDb().menuProductMaster().nuke();
                                        GlobalValues.getInstance().getDb().productSizeAndModifierMaster().nuke();
                                        sharePre.setString(sharePre.DEFAULT_ADDRESS, null);
                                        sharePre.setString(sharePre.NOTEPAD, "");
                                        db.getCartData();
                                        Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                                        sharePre.setString(sharePre.RESTUARANT_ID, response.getId());
                                        sharePre.setString(sharePre.RESTUARANT_NAME, response.getRestaurantName());
                                        i.putExtra("RESTAURANTID", response.getId());
                                        i.putExtra("RESTAURANTNAME", response.getRestaurantName());
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        mContext.startActivity(i);
                                        activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                                    }
                                }).start();
                            }
                        }
                    } else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                GlobalValues.getInstance().getDb().menuMaster().nuke();
                                GlobalValues.getInstance().getDb().menuProductMaster().nuke();
                                GlobalValues.getInstance().getDb().productSizeAndModifierMaster().nuke();
                                sharePre.setString(sharePre.DEFAULT_ADDRESS, null);
                                sharePre.setString(sharePre.NOTEPAD, "");
                                db.getCartData();
                                sharePre.setString(sharePre.RESTUARANT_ID, response.getId());
                                sharePre.setString(sharePre.RESTUARANT_NAME, response.getRestaurantName());

                                Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                                i.putExtra("RESTAURANTID", response.getId());
                                i.putExtra("RESTAURANTNAME", response.getRestaurantName());
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(i);
                            }
                        }).start();
                    }
*/


                    //// for saving offer detail and will be used when we go for checkout in MyBasketFragment
                /*    sharePre.setString(OFFERR_DETAL_DFG, new Gson().toJson(response.getDiscountOffers().get(mHolder.getAdapterPosition() - 1)));

                    if (sharePre.getString(sharePre.RESTUARANT_ID) != null && !sharePre.getString(sharePre.RESTUARANT_ID).equals("")) {
                        if (sharePre.getString(sharePre.RESTUARANT_ID).equalsIgnoreCase(response.getId())) {
                            Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                            i.putExtra("RESTAURANTID", response.getId());
                            i.putExtra("RESTAURANTNAME", response.getRestaurantName());
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(i);
                            activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                        } else {
                            if (db.getCartData().getMenuCategoryCarts().size() + db.getCartData().getSpecialOffers().size() + db.getCartData().getUpsellProducts().size() > 0) {
                                String msg = "You have already placing an order with " + sharePre.getString(sharePre.RESTUARANT_NAME);
                                alreadyAlertDialog(msg, sharePre.getString(sharePre.RESTUARANT_NAME), response.getRestaurantName(), response.getId(), response.getServe_style());

                            } else {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        GlobalValues.getInstance().getDb().menuMaster().nuke();
                                        GlobalValues.getInstance().getDb().menuProductMaster().nuke();
                                        GlobalValues.getInstance().getDb().productSizeAndModifierMaster().nuke();
                                        sharePre.setString(sharePre.DEFAULT_ADDRESS, null);
                                        sharePre.setString(sharePre.NOTEPAD, "");
                                        db.getCartData();
                                        Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                                        sharePre.setString(sharePre.RESTUARANT_ID, response.getId());
                                        sharePre.setString(sharePre.RESTUARANT_NAME, response.getRestaurantName());
                                        i.putExtra("RESTAURANTID", response.getId());
                                        i.putExtra("RESTAURANTNAME", response.getRestaurantName());
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        mContext.startActivity(i);
                                        activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                                    }
                                }).start();
                            }
                        }
                    } else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                GlobalValues.getInstance().getDb().menuMaster().nuke();
                                GlobalValues.getInstance().getDb().menuProductMaster().nuke();
                                GlobalValues.getInstance().getDb().productSizeAndModifierMaster().nuke();
                                sharePre.setString(sharePre.DEFAULT_ADDRESS, null);
                                sharePre.setString(sharePre.NOTEPAD, "");
                                db.getCartData();
                                sharePre.setString(sharePre.RESTUARANT_ID, response.getId());
                                sharePre.setString(sharePre.RESTUARANT_NAME, response.getRestaurantName());

                                Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                                i.putExtra("RESTAURANTID", response.getId());
                                i.putExtra("RESTAURANTNAME", response.getRestaurantName());
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(i);
                            }
                        }).start();
                    }*/


                }
            });


           /* if (!response.getMode().equalsIgnoreCase("live")) {
                holder.comingSoon.setVisibility(View.VISIBLE);
                holder.btnPreOrder.setVisibility(View.GONE);
                Log.e("PrintMode", "" + response.getMode());
            } else {
                holder.comingSoon.setVisibility(View.GONE);
            }*/

        } else if (position > mSize) {
            ///''
            if (response.getMode().equalsIgnoreCase("live")) {
                Glide.with(activity).load(response.getLogo()).apply(new RequestOptions()
                        .placeholder(R.drawable.easy_food_image))
                        .into(holder.menuLogo);


                holder.btnSeeMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (sharePre.getString(sharePre.RESTUARANT_ID) != null && !sharePre.getString(sharePre.RESTUARANT_ID).isEmpty()) {
                            sharePre.setString(OFFERR_DETAL_DFG, null);
                            if (sharePre.getString(sharePre.RESTUARANT_ID).equalsIgnoreCase(response.getId())) {
                                if (sharePre.getString(SERVE_STYLE) != null && mDB.saveOrderHistry().loadAllHistoryOfOrder().size() > 0) {
                                    serveStylePopupp(response.getServe_style(), response.getId(), false, null);

                                } else {
                                    serveStylePopup(response.getServe_style(), response.getId(), false, null);
                                }
//                activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                            } else {
                                if (mDB.saveOrderHistry().loadAllHistoryOfOrder().size() > 0) {
                                    String msg = "You have already placing an order with " + sharePre.getString(sharePre.RESTUARANT_NAME);
                                    alreadyAlertDialog(msg, sharePre.getString(sharePre.RESTUARANT_NAME), response.getRestaurantName(), response.getId(), response.getServe_style());

                                } else {
                                    serveStylePopup(response.getServe_style(), response.getId(), false, null);
                    /*if (sharePre.getString(SERVE_STYLE) != null) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                GlobalValues.getInstance().getDb().menuMaster().nuke();
                                GlobalValues.getInstance().getDb().menuProductMaster().nuke();
                                GlobalValues.getInstance().getDb().productSizeAndModifierMaster().nuke();
                                sharePre.setString(sharePre.DEFAULT_ADDRESS, null);
                                sharePre.setString(sharePre.NOTEPAD, "");

                                db.getCartData();
                                Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                                sharePre.setString(sharePre.RESTUARANT_ID, response.getId());
                                sharePre.setString(sharePre.RESTUARANT_NAME, response.getRestaurantName());
                                i.putExtra("RESTAURANTID", response.getId());
                                i.putExtra("RESTAURANTNAME", response.getRestaurantName());
                                i.putExtra("ServeStyle", sharePre.getString(SERVE_STYLE));
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(i);
                                activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                            }
                        }).start();
                    } else {
                        serveStylePopup(response.getServe_style(), response.getId());
                    }*/
                                }
                            }
                        } else {
                            // if (sharePre.getString(SERVE_STYLE) != null) {

                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GlobalValues.getInstance().getDb().menuMaster().nuke();
                        GlobalValues.getInstance().getDb().menuProductMaster().nuke();
                        GlobalValues.getInstance().getDb().productSizeAndModifierMaster().nuke();
                        sharePre.setString(sharePre.DEFAULT_ADDRESS, null);
                        sharePre.setString(sharePre.NOTEPAD, "");
                        db.getCartData();
                        sharePre.setString(sharePre.RESTUARANT_ID, response.getId());
                        sharePre.setString(sharePre.RESTUARANT_NAME, response.getRestaurantName());
                        Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                        i.putExtra("RESTAURANTID", response.getId());
                        i.putExtra("RESTAURANTNAME", response.getRestaurantName());
                        i.putExtra("ServeStyle", sharePre.getString(SERVE_STYLE));
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                    }
                }).start();*/
                            serveStylePopup(response.getServe_style(), response.getId(), false, null);
            /*} else {
                serveStylePopup(response.getServe_style(), response.getId());
            }*/
                        }


                  /*   ,m

                    sharePre.setString(OFFERR_DETAL_DFG, null);

                    if (sharePre.getString(sharePre.RESTUARANT_ID) != null && !sharePre.getString(sharePre.RESTUARANT_ID).equals("")) {
                        if (sharePre.getString(sharePre.RESTUARANT_ID).equalsIgnoreCase(response.getId())) {
                            Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                            i.putExtra("RESTAURANTID", response.getId());
                            i.putExtra("RESTAURANTNAME", response.getRestaurantName());
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(i);
                            activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                        } else {
                            if (db.getCartData().getMenuCategoryCarts().size() + db.getCartData().getSpecialOffers().size() + db.getCartData().getUpsellProducts().size() > 0) {
                                String msg = "You have already placing an order with " + sharePre.getString(sharePre.RESTUARANT_NAME);
                                alreadyAlertDialog(msg, sharePre.getString(sharePre.RESTUARANT_NAME), response.getRestaurantName(), response.getId(), response.getServe_style());

                            } else {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        GlobalValues.getInstance().getDb().menuMaster().nuke();
                                        GlobalValues.getInstance().getDb().menuProductMaster().nuke();
                                        GlobalValues.getInstance().getDb().productSizeAndModifierMaster().nuke();
                                        sharePre.setString(sharePre.DEFAULT_ADDRESS, null);
                                        sharePre.setString(sharePre.NOTEPAD, "");
                                        db.getCartData();
                                        Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                                        sharePre.setString(sharePre.RESTUARANT_ID, response.getId());
                                        sharePre.setString(sharePre.RESTUARANT_NAME, response.getRestaurantName());
                                        i.putExtra("RESTAURANTID", response.getId());
                                        i.putExtra("RESTAURANTNAME", response.getRestaurantName());
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        mContext.startActivity(i);
                                        activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                                    }
                                }).start();
                            }
                        }
                    } else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                GlobalValues.getInstance().getDb().menuMaster().nuke();
                                GlobalValues.getInstance().getDb().menuProductMaster().nuke();
                                GlobalValues.getInstance().getDb().productSizeAndModifierMaster().nuke();
                                sharePre.setString(sharePre.DEFAULT_ADDRESS, null);
                                sharePre.setString(sharePre.NOTEPAD, "");
                                db.getCartData();
                                sharePre.setString(sharePre.RESTUARANT_ID, response.getId());
                                sharePre.setString(sharePre.RESTUARANT_NAME, response.getRestaurantName());

                                Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                                i.putExtra("RESTAURANTID", response.getId());
                                i.putExtra("RESTAURANTNAME", response.getRestaurantName());
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(i);
                            }
                        }).start();
                    }
*/
                    }
                });

            }
        }

    }

    @Override
    public int getItemCount() {
        if (response.getMode().equalsIgnoreCase("live")) {
            return mSize + 2;
        } else {
            return mSize + 1;
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return 0;
        } else if (position == mSize + 1) {

            return 2;
        } else {
            return 1;
        }
    }

    public void addFavourites(String id, final ImageView fav, final int status) {

        AddFavouritesInterface apiInterface = ApiClient.getClient(mContext).create(AddFavouritesInterface.class);
        final AddFavouristeResquest request = new AddFavouristeResquest();
        request.setUserId(userID);
        request.setEntityId(id);
        request.setEntityType("restaurant");
        Call<AddFavouristeResponse> call3 = apiInterface.mAddFavourites(PrefManager.getInstance(mContext).getPreference(AUTH_TOKEN, ""), request);
        call3.enqueue(new Callback<AddFavouristeResponse>() {
            @Override
            public void onResponse(Call<AddFavouristeResponse> call, Response<AddFavouristeResponse> response) {
                try {
                    if (response.body().getSuccess()) {
                        if (response.body().getData().getFavouriteStatus() == 1) {
                            fav.setBackground(mContext.getResources().getDrawable(R.drawable.favourite_active));
                        } else if (response.body().getData().getFavouriteStatus() == 0) {
                            fav.setBackground(mContext.getResources().getDrawable(R.drawable.favourite_white));
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


    public void alreadyAlertDialog(String message, String oldRest, final String currentRestuarant, final String currentRestId, final String serveStyle) {
        LayoutInflater factory = LayoutInflater.from(mContext);
        final View mDialogVieww = factory.inflate(R.layout.layout_already_added_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setView(mDialogVieww);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final TextView tvTitle = (TextView) mDialogVieww.findViewById(R.id.tv_closed_title);
        final TextView tvGoOld = (TextView) mDialogVieww.findViewById(R.id.tv_go_to_old);
        final TextView tvGoNew = (TextView) mDialogVieww.findViewById(R.id.tv_go_to_new);
        final TextView tv_do_you = (TextView) mDialogVieww.findViewById(R.id.tv_do_you);
        tvTitle.setText(message);


        tv_do_you.setText(mContext.getString(R.string.do_you_want_to_remove_those_and_start_a_new_order_with) + " " + currentRestuarant + "?");

        tvGoOld.setText(/*"GO TO " + oldRest*/"OK");
        tvGoNew.setText(/*"GO TO " + currentRestuarant*/"Cancel");

        tvGoNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePre.setString(OFFERR_DETAL_DFG, null);
                if (sharePre.getString(SERVE_STYLE) != null) {
                    Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                    i.putExtra("RESTAURANTID", sharePre.getString(sharePre.RESTUARANT_ID));
                    i.putExtra("RESTAURANTNAME", sharePre.getString(sharePre.RESTUARANT_NAME));
                    i.putExtra("RESTAURANTNAME", sharePre.getString(sharePre.RESTUARANT_NAME));
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(i);
                } else {
                    serveStylePopup(response.getServe_style(), sharePre.getString(sharePre.RESTUARANT_ID), false, null);
                }
                alertDialog.dismiss();
            }
        });

        tvGoOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePre.setString(OFFERR_DETAL_DFG, null);
                alertDialog.dismiss();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GlobalValues.getInstance().getDb().menuMaster().nuke();
                        GlobalValues.getInstance().getDb().menuProductMaster().nuke();
                        GlobalValues.getInstance().getDb().productSizeAndModifierMaster().nuke();
                        db.deleteCart();
                        mDB.saveOrderHistry().deleteAll();
                        sharePre.setString(sharePre.DEFAULT_ADDRESS, null);
                        sharePre.setString(sharePre.RESTUARANT_ID, response.getId());
                        sharePre.setString(sharePre.RESTUARANT_NAME, response.getRestaurantName());
                        sharePre.setString(sharePre.NOTEPAD, "");

                      /*  Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                        i.putExtra("RESTAURANTID", currentRestId);
                        i.putExtra("RESTAURANTNAME", response.getRestaurantName());
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);*/


                    }
                }).start();

                serveStylePopup(serveStyle, currentRestId, false, null);
            }
        });
        mDialogVieww.findViewById(R.id.cross_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


        alertDialog.show();
    }


    public void serveStylePopup(String deliverOption, final String currentRestId, final boolean isOffer, final DiscountOffer discountOffer) {
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.layout_servestyle_dialog, null);
        LayoutServestyleDialogBinding dialogBinding = DataBindingUtil.bind(dialogView);
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogBinding.getRoot());
        dialog.setCancelable(false);
        String[] serve_styles = deliverOption.split(",");

        if (Arrays.asList(serve_styles).contains("collection")) {
            if (Helper.isPreOrder(response.getRestaurantTiming().get(0).getCollectionStartTime(), response.getRestaurantTiming().get(0).getCollectionEndTime())) {
                dialogBinding.tvCollection.setText("Collection (Pre Order)");
            }
            if (sharePre.getString(SERVE_STYLE) != null && sharePre.getString(SERVE_STYLE).equalsIgnoreCase("collection")) {
                dialogBinding.ivCollection.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_selected));
            } else {
                dialogBinding.ivCollection.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_unselected));
            }
            dialogBinding.rlCollection.setVisibility(View.VISIBLE);
        } else {

            dialogBinding.rlCollection.setVisibility(View.GONE);
        }
        if (Arrays.asList(serve_styles).contains("delivery")) {
            if (Helper.isPreOrder(response.getRestaurantTiming().get(0).getDeliveryStartTime(), response.getRestaurantTiming().get(0).getDeliveryEndTime())) {
                dialogBinding.tvDelivery.setText("Delivery (Pre Order)");
            }
            if (sharePre.getString(SERVE_STYLE) != null && sharePre.getString(SERVE_STYLE).equalsIgnoreCase("delivery")) {
                dialogBinding.ivDelivery.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_selected));
            } else {
                dialogBinding.ivDelivery.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_unselected));
            }
            dialogBinding.rlDelivery.setVisibility(View.VISIBLE);

        } else {
            dialogBinding.rlDelivery.setVisibility(View.GONE);
        }
        if (Arrays.asList(serve_styles).contains("dine_in")) {
            if (Helper.isPreOrder(response.getRestaurantTiming().get(0).getOpeningStartTime(), response.getRestaurantTiming().get(0).getOpeningEndTime())) {
                dialogBinding.tvDineIn.setText("Dine In (Pre Order)");
            }
            if (sharePre.getString(SERVE_STYLE) != null && sharePre.getString(SERVE_STYLE).equalsIgnoreCase("dine_in")) {
                dialogBinding.ivDineIn.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_selected));
            } else {
                dialogBinding.ivDineIn.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_unselected));
            }
            dialogBinding.rlDineIn.setVisibility(View.VISIBLE);
        } else {
            dialogBinding.rlDineIn.setVisibility(View.GONE);
        }


        dialogBinding.rlDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                /*Intent intent = new Intent(mContext, RestaurantDetailsActivity.class);
                intent.putExtra("IS_FROM_LOGIN", false);
                intent.putExtra("RESTAURANTID", restaurantId);
                intent.putExtra("ServeStyle", "delivery");
                mContext.startActivity(intent);*/


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GlobalValues.getInstance().getDb().menuMaster().nuke();
                        GlobalValues.getInstance().getDb().menuProductMaster().nuke();
                        GlobalValues.getInstance().getDb().productSizeAndModifierMaster().nuke();
                        db.deleteCart();
                        mDB.saveOrderHistry().deleteAll();
                        sharePre.setString(sharePre.DEFAULT_ADDRESS, null);
                        sharePre.setString(DELIVERY_MOBILE_NUMBER, "");
                        sharePre.setString(sharePre.RESTUARANT_ID, response.getId());
                        sharePre.setString(sharePre.RESTUARANT_NAME, response.getRestaurantName());
                        sharePre.setString(SERVE_STYLE, "delivery");
                        sharePre.setString(sharePre.NOTEPAD, "");
                        sharePre.setInt(IS_FOR_TABLE, 0);

                        Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                        i.putExtra("RESTAURANTID", currentRestId);
                        i.putExtra("RESTAURANTNAME", response.getRestaurantName());
                        i.putExtra("ServeStyle", "delivery");
                        i.putExtra("isOffer", isOffer);
                        if (isOffer) {
                            i.putExtra("offer_price", discountOffer.getOfferPrice());
                            i.putExtra("offer_type", discountOffer.getOfferType());
                            i.putExtra("offer_id", discountOffer.getOfferId());
                            i.putExtra("min_value", discountOffer.getMin_value());
                            i.putExtra("max_value", discountOffer.getMax_discounts());
                        }

                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                    }
                }).start();

            }
        });

        dialogBinding.rlCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GlobalValues.getInstance().getDb().menuMaster().nuke();
                        GlobalValues.getInstance().getDb().menuProductMaster().nuke();
                        GlobalValues.getInstance().getDb().productSizeAndModifierMaster().nuke();
                        db.deleteCart();
                        mDB.saveOrderHistry().deleteAll();
                        sharePre.setString(sharePre.DEFAULT_ADDRESS, null);
                        sharePre.setString(DELIVERY_MOBILE_NUMBER, "");
                        sharePre.setString(sharePre.RESTUARANT_ID, response.getId());
                        sharePre.setString(sharePre.RESTUARANT_NAME, response.getRestaurantName());
                        sharePre.setString(SERVE_STYLE, "collection");
                        sharePre.setString(sharePre.NOTEPAD, "");
                        sharePre.setInt(IS_FOR_TABLE, 0);

                        Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                        i.putExtra("RESTAURANTID", currentRestId);
                        i.putExtra("RESTAURANTNAME", response.getRestaurantName());
                        i.putExtra("ServeStyle", "collection");
                        i.putExtra("isOffer", isOffer);
                        if (isOffer) {
                            i.putExtra("offer_price", discountOffer.getOfferPrice());
                            i.putExtra("offer_type", discountOffer.getOfferType());
                            i.putExtra("offer_id", discountOffer.getOfferId());
                            i.putExtra("min_value", discountOffer.getMin_value());
                            i.putExtra("max_value", discountOffer.getMax_discounts());
                        }
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                    }
                }).start();
            }
        });
        dialogBinding.rlDineIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GlobalValues.getInstance().getDb().menuMaster().nuke();
                        GlobalValues.getInstance().getDb().menuProductMaster().nuke();
                        GlobalValues.getInstance().getDb().productSizeAndModifierMaster().nuke();
                        db.deleteCart();
                        mDB.saveOrderHistry().deleteAll();
                        sharePre.setString(sharePre.DEFAULT_ADDRESS, null);
                        sharePre.setString(DELIVERY_MOBILE_NUMBER, "");
                        sharePre.setString(sharePre.RESTUARANT_ID, response.getId());
                        sharePre.setString(sharePre.RESTUARANT_NAME, response.getRestaurantName());
                        sharePre.setString(SERVE_STYLE, "dine_in");
                        sharePre.setString(sharePre.NOTEPAD, "");
                        sharePre.setInt(IS_FOR_TABLE, 1);

                        Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                        i.putExtra("RESTAURANTID", currentRestId);
                        i.putExtra("RESTAURANTNAME", response.getRestaurantName());
                        i.putExtra("ServeStyle", "dine_in");
                        i.putExtra("isOffer", isOffer);
                        if (isOffer) {
                            i.putExtra("offer_price", discountOffer.getOfferPrice());
                            i.putExtra("offer_type", discountOffer.getOfferType());
                            i.putExtra("offer_id", discountOffer.getOfferId());
                            i.putExtra("min_value", discountOffer.getMin_value());
                            i.putExtra("max_value", discountOffer.getMax_discounts());
                        }
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                    }
                }).start();
            }
        });

        dialogBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });

        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(mContext, R.color.seme_transparent)));
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.seme_transparent)));
    }


    public void serveStylePopupp(String deliverOption, final String currentRestId, final boolean isOffer, final DiscountOffer discountOffer) {
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.layout_servestyle_dialog, null);
        LayoutServestyleDialogBinding dialogBinding = DataBindingUtil.bind(dialogView);
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogBinding.getRoot());
        dialog.setCancelable(false);
        String[] serve_styles = deliverOption.split(",");


        if (Arrays.asList(serve_styles).contains("collection")) {
            if (Helper.isPreOrder(response.getRestaurantTiming().get(0).getCollectionStartTime(), response.getRestaurantTiming().get(0).getCollectionEndTime())) {
                dialogBinding.tvCollection.setText("Collection (Pre Order)");
            }


            if (sharePre.getString(SERVE_STYLE) != null && sharePre.getString(SERVE_STYLE).equalsIgnoreCase("collection")) {
                dialogBinding.ivCollection.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_selected));
            } else {
                dialogBinding.ivCollection.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_unselected));
            }
            dialogBinding.rlCollection.setVisibility(View.VISIBLE);
        } else {

            dialogBinding.rlCollection.setVisibility(View.GONE);
        }
        if (Arrays.asList(serve_styles).contains("delivery")) {
            if (Helper.isPreOrder(response.getRestaurantTiming().get(0).getDeliveryStartTime(), response.getRestaurantTiming().get(0).getDeliveryEndTime())) {
                dialogBinding.tvDelivery.setText("Delivery (Pre Order)");
            }

            if (sharePre.getString(SERVE_STYLE) != null && sharePre.getString(SERVE_STYLE).equalsIgnoreCase("delivery")) {
                dialogBinding.ivDelivery.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_selected));
            } else {
                dialogBinding.ivDelivery.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_unselected));
            }
            dialogBinding.rlDelivery.setVisibility(View.VISIBLE);

        } else {
            dialogBinding.rlDelivery.setVisibility(View.GONE);
        }
        if (Arrays.asList(serve_styles).contains("dine_in")) {
            if (Helper.isPreOrder(response.getRestaurantTiming().get(0).getOpeningStartTime(), response.getRestaurantTiming().get(0).getOpeningEndTime())) {
                dialogBinding.tvDineIn.setText("Dine In (Pre Order)");
            }

            if (sharePre.getString(SERVE_STYLE) != null && sharePre.getString(SERVE_STYLE).equalsIgnoreCase("dine_in")) {
                dialogBinding.ivDineIn.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_selected));
            } else {
                dialogBinding.ivDineIn.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_unselected));
            }
            dialogBinding.rlDineIn.setVisibility(View.VISIBLE);
        } else {
            dialogBinding.rlDineIn.setVisibility(View.GONE);
        }


        dialogBinding.rlDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                // if (sharePre.getString(SERVE_STYLE) != null && sharePre.getString(SERVE_STYLE).equalsIgnoreCase("delivery") && db.getCartData().getMenuCategoryCarts().size() + db.getCartData().getSpecialOffers().size() + db.getCartData().getUpsellProducts().size() > 0) {
                if (sharePre.getString(SERVE_STYLE) != null && sharePre.getString(SERVE_STYLE).equalsIgnoreCase("delivery") && mDB.saveOrderHistry().loadAllHistoryOfOrder().size() > 0) {

                    Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                    i.putExtra("RESTAURANTID", response.getId());
                    i.putExtra("RESTAURANTNAME", response.getRestaurantName());
                    i.putExtra("ServeStyle", sharePre.getString(SERVE_STYLE));
                    i.putExtra("isOffer", isOffer);
                    if (isOffer) {
                        i.putExtra("offer_price", discountOffer.getOfferPrice());
                        i.putExtra("offer_type", discountOffer.getOfferType());
                        i.putExtra("offer_id", discountOffer.getOfferId());
                        i.putExtra("min_value", discountOffer.getMin_value());
                        i.putExtra("max_value", discountOffer.getMax_discounts());
                    }
                    mContext.startActivity(i);
                } else {
                    confirmationDialog(currentRestId, "delivery", isOffer, discountOffer);
                }


            }
        });

        dialogBinding.rlCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (sharePre.getString(SERVE_STYLE) != null && sharePre.getString(SERVE_STYLE).equalsIgnoreCase("collection") && mDB.saveOrderHistry().loadAllHistoryOfOrder().size() > 0) {

                    Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                    i.putExtra("RESTAURANTID", response.getId());
                    i.putExtra("RESTAURANTNAME", response.getRestaurantName());
                    i.putExtra("ServeStyle", sharePre.getString(SERVE_STYLE));
                    i.putExtra("isOffer", isOffer);
                    if (isOffer) {
                        i.putExtra("offer_price", discountOffer.getOfferPrice());
                        i.putExtra("offer_type", discountOffer.getOfferType());
                        i.putExtra("offer_id", discountOffer.getOfferId());
                        i.putExtra("min_value", discountOffer.getMin_value());
                        i.putExtra("max_value", discountOffer.getMax_discounts());
                    }
                    mContext.startActivity(i);
                } else {
                    confirmationDialog(currentRestId, "collection", isOffer, discountOffer);
                }

            }
        });
        dialogBinding.rlDineIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (sharePre.getString(SERVE_STYLE) != null && sharePre.getString(SERVE_STYLE).equalsIgnoreCase("dine_in") && mDB.saveOrderHistry().loadAllHistoryOfOrder().size() > 0) {

                    Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                    i.putExtra("RESTAURANTID", response.getId());
                    i.putExtra("RESTAURANTNAME", response.getRestaurantName());
                    i.putExtra("ServeStyle", sharePre.getString(SERVE_STYLE));
                    i.putExtra("isOffer", isOffer);
                    if (isOffer) {
                        i.putExtra("offer_price", discountOffer.getOfferPrice());
                        i.putExtra("offer_type", discountOffer.getOfferType());
                        i.putExtra("offer_id", discountOffer.getOfferId());
                        i.putExtra("min_value", discountOffer.getMin_value());
                        i.putExtra("max_value", discountOffer.getMax_discounts());
                    }
                    mContext.startActivity(i);
                } else {
                    confirmationDialog(currentRestId, "dine_in", isOffer, discountOffer);
                }

            }
        });

        dialogBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });

        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(mContext, R.color.seme_transparent)));
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.seme_transparent)));
    }

    public void confirmationDialog(final String currentRestId, final String serveStyle, final boolean isOffer, final DiscountOffer discountOffer) {
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.layout_confirmation_dialog, null);
        LayoutConfirmationDialogBinding confirmBinding = DataBindingUtil.bind(dialogView);
        final Dialog confirmDialog = new Dialog(mContext);
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

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GlobalValues.getInstance().getDb().menuMaster().nuke();
                        GlobalValues.getInstance().getDb().menuProductMaster().nuke();
                        GlobalValues.getInstance().getDb().productSizeAndModifierMaster().nuke();
                        db.deleteCart();
                        mDB.saveOrderHistry().deleteAll();
                        sharePre.setString(sharePre.DEFAULT_ADDRESS, null);
                        sharePre.setString(DELIVERY_MOBILE_NUMBER, "");
                        sharePre.setString(sharePre.RESTUARANT_ID, response.getId());
                        sharePre.setString(sharePre.RESTUARANT_NAME, response.getRestaurantName());
                        sharePre.setString(SERVE_STYLE, serveStyle);
                        sharePre.setString(sharePre.NOTEPAD, "");
                        sharePre.setInt(IS_FOR_TABLE, 0);

                        Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                        i.putExtra("RESTAURANTID", currentRestId);
                        i.putExtra("RESTAURANTNAME", response.getRestaurantName());
                        i.putExtra("ServeStyle", serveStyle);
                        i.putExtra("isOffer", isOffer);
                        if (isOffer) {
                            i.putExtra("offer_price", discountOffer.getOfferPrice());
                            i.putExtra("offer_type", discountOffer.getOfferType());
                            i.putExtra("offer_id", discountOffer.getOfferId());
                            i.putExtra("min_value", discountOffer.getMin_value());
                            i.putExtra("max_value", discountOffer.getMax_discounts());
                        }
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                    }
                }).start();

            }
        });


        confirmDialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        confirmDialog.getWindow().setAttributes(lp);
        confirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(mContext, R.color.seme_transparent)));
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.seme_transparent)));
    }
}
