package com.lexxdigital.easyfooduserapps.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.api.AddFavouritesInterface;
import com.lexxdigital.easyfooduserapps.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapps.model.add_favourites_request.AddFavouristeResquest;
import com.lexxdigital.easyfooduserapps.model.add_favourites_response.AddFavouristeResponse;
import com.lexxdigital.easyfooduserapps.model.landing_page_response.RestaurantsDealResponse;
import com.lexxdigital.easyfooduserapps.restaurant_details.RestaurantDetailsActivity;
import com.lexxdigital.easyfooduserapps.utility.ApiClient;
import com.lexxdigital.easyfooduserapps.utility.GlobalValues;
import com.lexxdigital.easyfooduserapps.utility.SharedPreferencesClass;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.lexxdigital.easyfooduserapps.utility.SharedPreferencesClass.OFFERR_DETAL_DFG;

public class DealCardAdapter extends RecyclerView.Adapter<DealCardAdapter.MyViewHolder> implements View.OnClickListener {

    RestaurantsDealResponse.Data.Restaurant response;
    private Context mContext;
    int mSize = 0, mListPosition;
    String userID = "";
    Activity activity;
    SharedPreferencesClass sharePre;
    DatabaseHelper db;


    @Override
    public void onClick(View v) {
        if (sharePre.getString(sharePre.RESTUARANT_ID) != null && !sharePre.getString(sharePre.RESTUARANT_ID).equals("")) {
            sharePre.setString(OFFERR_DETAL_DFG, null);
            if (sharePre.getString(sharePre.RESTUARANT_ID).equalsIgnoreCase(response.getId())) {
                Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                i.putExtra("RESTAURANTID", response.getId());
                i.putExtra("RESTAURANTNAME", response.getRestaurantName());
//                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
//                activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            } else {
                if (db.getCartData().getMenuCategoryCarts().size() + db.getCartData().getSpecialOffers().size() + db.getCartData().getUpsellProducts().size() > 0) {
                    String msg = "You have already placing an order with " + sharePre.getString(sharePre.RESTUARANT_NAME);
                    alreadyAlertDialog(msg, sharePre.getString(sharePre.RESTUARANT_NAME), response.getRestaurantName(), response.getId());

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
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView logo, bgImage, menuLogo, favIcon, arraowAnimation;
        TextView offerTitle, offerItems/*, offerPrice*/;
        LinearLayout clickRestaurant;
        Button btnSeeMenu, btnGetDeals;


        //////////////////////////////
        LinearLayout btnPreOrder, layoutDeliveryPrice, layoutDeliveryTime;
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
//            this.offerPrice = (TextView) itemView.findViewById(R.id.txt_price);
            this.btnSeeMenu = (Button) itemView.findViewById(R.id.btn_see_full_menu);
            this.btnGetDeals = (Button) itemView.findViewById(R.id.btn_get_deals);
            this.offerTitle = (TextView) itemView.findViewById(R.id.dealNameId);
            this.clickRestaurant = (LinearLayout) itemView.findViewById(R.id.layout_restaurant);
            this.menuLogo = (ImageView) itemView.findViewById(R.id.image_menu_logo);


            /////////////////////////////////////////////////////
            this.tvPreOrderMsg = (TextView) itemView.findViewById(R.id.tv_PreOrderMsg);
            this.btnPreOrder = (LinearLayout) itemView.findViewById(R.id.layout_btnPreOrder);
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
////////////////////

            //////////////////////////////******************** from deal adptr************************///////////////////////////////

            sharePre = new SharedPreferencesClass(mContext);
            db = new DatabaseHelper(mContext);
            final int mListPosition = position;
            if (position == mSize - 1) {
//                onBottomReachedListener.onBottomReached(position);
            }
            holder.name.setText(response.getRestaurantName());
            holder.tvDistance.setText(response.getDistance_in_miles() + " miles");

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

            holder.llCollection.setVisibility(View.GONE);
            holder.llDelivery.setVisibility(View.GONE);
            holder.llDinein.setVisibility(View.GONE);

            if (response.getServe_style() != null || !response.getServe_style().equals("")) {
                String[] serve_styles = response.getServe_style().split(",");

                if (Arrays.asList(serve_styles).contains("collection")) {
                    holder.llCollection.setVisibility(View.VISIBLE);
                    holder.collection.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_orage_tick));
                }
                if (Arrays.asList(serve_styles).contains("delivery")) {
                    holder.llDelivery.setVisibility(View.VISIBLE);
                    holder.delivery.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_orage_tick));
                }
                if (Arrays.asList(serve_styles).contains("dinein")) {
                    holder.llDinein.setVisibility(View.VISIBLE);
                    holder.dine_in.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_orage_tick));
                }
            }
            holder.deliveryMin.setText(mContext.getResources().getString(R.string.currency) + response.getDelivery_charge() + " delivery ");
            holder.deliveryVal.setText(mContext.getResources().getString(R.string.currency) + response.getMin_order_value() + " min order");
            holder.deliveryTime.setText(response.getAvgDeliveryTime() + " min");

            //////////////////////////**************************************////////////////////////////////////////

            //////////////////////////
        } else if (position > 0 && position <= mSize) {
            if (response.getDiscountOffers().size() > 0) {
                holder.offerItems.setText(response.getDiscountOffers().get(position - 1).getTerms_conditions());
//                holder.offerPrice.setText(response.getDiscountOffers().get(position - 1).getOfferPriceLabel());
                holder.offerTitle.setText(response.getDiscountOffers().get(position - 1).getDetail());
            }


            holder.btnGetDeals.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //// for saving offer detail and will be used when we go for checkout in MyBasketFragment
                    sharePre.setString(OFFERR_DETAL_DFG, new Gson().toJson(response.getDiscountOffers().get(mHolder.getAdapterPosition() - 1)));

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
                                alreadyAlertDialog(msg, sharePre.getString(sharePre.RESTUARANT_NAME), response.getRestaurantName(), response.getId());

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

                }
            });


        } else if (position > mSize) {
            Glide.with(activity).load(response.getLogo()).apply(new RequestOptions()
                    .placeholder(R.drawable.easy_food_image))
                    .into(holder.menuLogo);


            holder.btnSeeMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

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
                                alreadyAlertDialog(msg, sharePre.getString(sharePre.RESTUARANT_NAME), response.getRestaurantName(), response.getId());

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

                }
            });


        }

    }

    @Override
    public int getItemCount() {
        return mSize + 2;
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
        Call<AddFavouristeResponse> call3 = apiInterface.mAddFavourites(request);
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


    public void alreadyAlertDialog(String message, String oldRest, final String currentRestuarant, final String currentRestId) {
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
                Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                i.putExtra("RESTAURANTID", sharePre.getString(sharePre.RESTUARANT_ID));
                i.putExtra("RESTAURANTNAME", sharePre.getString(sharePre.RESTUARANT_NAME));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
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
                        sharePre.setString(sharePre.DEFAULT_ADDRESS, null);
                        sharePre.setString(sharePre.RESTUARANT_ID, response.getId());
                        sharePre.setString(sharePre.RESTUARANT_NAME, response.getRestaurantName());
                        sharePre.setString(sharePre.NOTEPAD, "");

                        Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                        i.putExtra("RESTAURANTID", currentRestId);
                        i.putExtra("RESTAURANTNAME", response.getRestaurantName());
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                    }
                }).start();
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


}
