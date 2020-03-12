package com.lexxdigital.easyfooduserapps.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapps.model.FavouriteList;
import com.lexxdigital.easyfooduserapps.restaurant_details.RestaurantDetailsActivity;
import com.lexxdigital.easyfooduserapps.utility.Constants;
import com.lexxdigital.easyfooduserapps.utility.GlobalValues;
import com.lexxdigital.easyfooduserapps.utility.SharedPreferencesClass;

import java.util.ArrayList;
import java.util.List;


public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.MyViewHolder> {

    private List<FavouriteList> listFavourites = new ArrayList<>();
    Context mContext;
    FavouritesAdapter.PostionInterface postionInterface;
    SharedPreferencesClass sharePre;
    DatabaseHelper db;
    FavouriteList fav;
    Activity activity;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView restaurantName, cuisines, minOrder, restaurantFavRemove, rating, preOrder, tvPreOrderMsg, tv_distance;
        ImageView restaurantImage, logo;
        LinearLayout llDelivery, llDinein, llCollection, lyClick;
        ImageView delivery, dine_in, collection;
        ImageView imRatingImage;
        ImageView favIcon;
        LinearLayout llClosed, btnPreOrder;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.restaurantName = (TextView) itemView.findViewById(R.id.restaurant_name);
            this.restaurantImage = (ImageView) itemView.findViewById(R.id.restaurant_image);
            this.logo = (ImageView) itemView.findViewById(R.id.restaurant_logo);
            this.cuisines = (TextView) itemView.findViewById(R.id.restaurant_cuisines);
            this.minOrder = (TextView) itemView.findViewById(R.id.restaurant_delivery_min_order);
            this.rating = (TextView) itemView.findViewById(R.id.restaurant_rating);
            this.restaurantFavRemove = (TextView) itemView.findViewById(R.id.restaurant_fav_remove);
            this.favIcon = itemView.findViewById(R.id.favourites);
            this.tv_distance = itemView.findViewById(R.id.tv_distance);
            this.imRatingImage = itemView.findViewById(R.id.im_ratingImage);
            this.llDelivery = itemView.findViewById(R.id.ll_delivery);
            this.lyClick = itemView.findViewById(R.id.ly_click);
            this.delivery = itemView.findViewById(R.id.delivery);
            this.llDinein = itemView.findViewById(R.id.ll_dinein);
            this.dine_in = itemView.findViewById(R.id.dine_in);
            this.llCollection = itemView.findViewById(R.id.ll_collection);
            this.collection = itemView.findViewById(R.id.collection);
            this.preOrder = (TextView) itemView.findViewById(R.id.pre_order);
            this.btnPreOrder = (LinearLayout) itemView.findViewById(R.id.layout_btnPreOrder);
            this.tvPreOrderMsg = (TextView) itemView.findViewById(R.id.tv_PreOrderMsg);
            this.llClosed = (LinearLayout) itemView.findViewById(R.id.closed_design);

        }
    }

    public interface PostionInterface {
        void onclickedFav(int pos);
    }

    public FavouritesAdapter(Context context, List<FavouriteList> list, FavouritesAdapter.PostionInterface postionInterface, Activity activity) {
        this.listFavourites = list;
        this.mContext = context;
        this.postionInterface = postionInterface;
        this.activity = activity;
    }

    @Override
    public FavouritesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_favourites_list, parent, false);
        FavouritesAdapter.MyViewHolder myViewHolder = new FavouritesAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final FavouritesAdapter.MyViewHolder holder, int mListPosition) {
        final int listPosition = mListPosition;
        try {
            sharePre = new SharedPreferencesClass(mContext);
            db = new DatabaseHelper(mContext);
            fav = listFavourites.get(listPosition);
            holder.restaurantName.setText(fav.getRestaurantName());
            holder.cuisines.setText(fav.getCuisines());
            if (fav.getDistance_in_miles() != null && !fav.getDistance_in_miles().trim().isEmpty()) {
                holder.tv_distance.setText(fav.getDistance_in_miles() + " miles");
            }
            if (fav.getMinOrderValue() != null) {
                holder.minOrder.setText(mContext.getResources().getString(R.string.currency) + fav.getDeliveryCharge() + " delivery  •  " + mContext.getResources().getString(R.string.currency) + fav.getMinOrderValue() + " min order");
            } else {
                holder.minOrder.setText(mContext.getResources().getString(R.string.currency) + fav.getDeliveryCharge() + " delivery");

            }
            if (fav.getOverallRating() != null) {
                if (fav.getOverallRating() == 0) {
                    holder.rating.setText("New");
                    holder.imRatingImage.setVisibility(View.GONE);
                } else {
                    holder.imRatingImage.setVisibility(View.VISIBLE);
                    holder.rating.setText(String.format("%.1f", fav.getOverallRating()));
                }
            } else {
                holder.rating.setText("New");
                holder.imRatingImage.setVisibility(View.GONE);
            }

            String status = listFavourites.get(listPosition).getRestaurantStatus();
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


            Glide.with(activity).load(fav.getLogo()).apply(new RequestOptions()
                    .placeholder(R.drawable.easy_food_image))
                    .into(holder.logo);


            Glide.with(mContext).load(fav.getBackImane()).apply(new RequestOptions())
                    .into(holder.restaurantImage);


            holder.favIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postionInterface.onclickedFav(listPosition);
                }
            });
            holder.restaurantFavRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postionInterface.onclickedFav(listPosition);
                }
            });

            holder.lyClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (sharePre.getString(sharePre.RESTUARANT_ID) != null && !sharePre.getString(sharePre.RESTUARANT_ID).equals("")) {
                            if (sharePre.getString(sharePre.RESTUARANT_ID).equalsIgnoreCase(listFavourites.get(listPosition).getEntityID())) {
                                Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                                i.putExtra("RESTAURANTID", listFavourites.get(listPosition).getEntityID());
                                i.putExtra("RESTAURANTNAME", listFavourites.get(listPosition).getRestaurantName());
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(i);
                                activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                            } else {
                                String msg = "You have items in your basket from \"" + sharePre.getString(sharePre.RESTUARANT_NAME) + "\" would you like to disregard and move to \"" + listFavourites.get(listPosition).getRestaurantName() + "\"";
                                alertDialogNoRestaurant(msg, sharePre.getString(sharePre.RESTUARANT_NAME), listFavourites.get(listPosition).getRestaurantName(), listFavourites.get(listPosition).getEntityID());
                            }
                        } else {
                            Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                            i.putExtra("RESTAURANTID", listFavourites.get(listPosition).getEntityID());
                            i.putExtra("RESTAURANTNAME", listFavourites.get(listPosition).getRestaurantName());
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(i);
                        }

                    } catch (Exception e) {

                        Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                        i.putExtra("RESTAURANTID", listFavourites.get(listPosition).getEntityID());
                        i.putExtra("RESTAURANTNAME", listFavourites.get(listPosition).getRestaurantName());
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                    }

                }
            });


            holder.preOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView deliveryTime, minOrderForDelivery, collectionTime, preOrderForLetter, tvDay;
                    ImageView im_cross;
                    LayoutInflater inflater = LayoutInflater.from(mContext);
                    final View view = inflater.inflate(R.layout.popup_preorder, null);
                    final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
                    dialog.setView(view);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    deliveryTime = view.findViewById(R.id.delivery_time);
                    collectionTime = view.findViewById(R.id.collection_time);
                    minOrderForDelivery = view.findViewById(R.id.min_order_for_delivery);
                    preOrderForLetter = view.findViewById(R.id.tv_pre_order_for_later);
                    im_cross = view.findViewById(R.id.cross_tv);
                    tvDay = view.findViewById(R.id.tv_day);
                    String startDelTime = "", endDelTime = "", startCollTime = "", endCollTime = "";
                    String todayDay = Constants.getTodayDay();
                    String upperString = listFavourites.get(listPosition).getRestaurantTimingLists().get(0).getDay().substring(0, 1).toUpperCase() + listFavourites.get(listPosition).getRestaurantTimingLists().get(0).getDay().substring(1);
                    tvDay.setText(upperString);
                    startDelTime = listFavourites.get(listPosition).getRestaurantTimingLists().get(0).getDelivery_start_time();
                    endDelTime = listFavourites.get(listPosition).getRestaurantTimingLists().get(0).getDelivery_end_time();
                    startCollTime = listFavourites.get(listPosition).getRestaurantTimingLists().get(0).getCollection_start_time();
                    endCollTime = listFavourites.get(listPosition).getRestaurantTimingLists().get(0).getCollection_end_time();
                    deliveryTime.setText(startDelTime + " - " + endDelTime);
                    collectionTime.setText(startCollTime + " - " + endCollTime);

                    minOrderForDelivery.setText(mContext.getResources().getString(R.string.currency) + listFavourites.get(listPosition).getMinOrderValue() + " min order");
                    im_cross.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    preOrderForLetter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (listFavourites.get(listPosition).getRestaurantStatus().equalsIgnoreCase("not_serving")) {
                                return;
                            }
                            try {
                                if (sharePre.getString(sharePre.RESTUARANT_ID) != null && !sharePre.getString(sharePre.RESTUARANT_ID).equals("")) {
                                    if (sharePre.getString(sharePre.RESTUARANT_ID).equalsIgnoreCase(listFavourites.get(listPosition).getEntityID())) {
                                        Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                                        i.putExtra("RESTAURANTID", listFavourites.get(listPosition).getEntityID());
                                        i.putExtra("RESTAURANTNAME", listFavourites.get(listPosition).getRestaurantName());
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        mContext.startActivity(i);
                                        activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                                    } else {
                                        String msg = "You have items in your basket from \"" + sharePre.getString(sharePre.RESTUARANT_NAME) + "\" would you like to disregard and move to \"" + listFavourites.get(listPosition).getRestaurantName() + "\"";
                                        alertDialogNoRestaurant(msg, sharePre.getString(sharePre.RESTUARANT_NAME), listFavourites.get(listPosition).getRestaurantName(), listFavourites.get(listPosition).getEntityID());
                                    }
                                } else {
                                    Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                                    i.putExtra("RESTAURANTID", listFavourites.get(listPosition).getEntityID());
                                    i.putExtra("RESTAURANTNAME", listFavourites.get(listPosition).getRestaurantName());
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    mContext.startActivity(i);
                                }

                            } catch (Exception e) {

                                Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                                i.putExtra("RESTAURANTID", listFavourites.get(listPosition).getEntityID());
                                i.putExtra("RESTAURANTNAME", listFavourites.get(listPosition).getRestaurantName());
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(i);
                            }
                            dialog.dismiss();
                        }

                    });

                    dialog.show();

                }

            });

        } catch (Exception e) {

        }


    }

    @Override
    public int getItemCount() {
        return listFavourites.size();
    }

    public void alertDialogNoRestaurant(String message, String oldRest, final String currentRestuarant, final String currentRestId) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(true);

        alertDialogBuilder.setPositiveButton("Continue with " + oldRest, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                i.putExtra("RESTAURANTID", sharePre.getString(sharePre.RESTUARANT_ID));
                i.putExtra("RESTAURANTNAME", sharePre.getString(sharePre.RESTUARANT_NAME));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
                dialog.dismiss();
            }
        });
        alertDialogBuilder.setNegativeButton("Start new order with" + currentRestuarant, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GlobalValues.getInstance().getDb().menuMaster().nuke();
                        GlobalValues.getInstance().getDb().menuProductMaster().nuke();
                        GlobalValues.getInstance().getDb().productSizeAndModifierMaster().nuke();
                        db.deleteCart();
                        sharePre.setString(sharePre.RESTUARANT_ID, "");
                        sharePre.setString(sharePre.RESTUARANT_NAME, "");
                        Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                        i.putExtra("RESTAURANTID", currentRestId);
                        i.putExtra("RESTAURANTNAME", currentRestuarant);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                    }
                }).start();


            }
        });


        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
