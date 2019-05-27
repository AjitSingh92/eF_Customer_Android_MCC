package com.lexxdigital.easyfooduserapp.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapp.model.FavouriteList;
import com.lexxdigital.easyfooduserapp.restaurant_details.RestaurantDetailsActivity;
import com.lexxdigital.easyfooduserapp.utility.Constants;
import com.lexxdigital.easyfooduserapp.utility.GlobalValues;
import com.lexxdigital.easyfooduserapp.utility.SharedPreferencesClass;

import java.util.ArrayList;
import java.util.Arrays;
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

        TextView restaurantName, cuisines, minOrder, restaurantFavRemove, rating;
        ImageView restaurantImage, logo;
        LinearLayout llDelivery, llDinein, llCollection, lyClick;
        ImageView delivery, dine_in, collection;
        ImageView imRatingImage;
        ImageView favIcon;

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


            this.imRatingImage = itemView.findViewById(R.id.im_ratingImage);
            this.llDelivery = itemView.findViewById(R.id.ll_delivery);
            this.lyClick = itemView.findViewById(R.id.ly_click);
            this.delivery = itemView.findViewById(R.id.delivery);
            this.llDinein = itemView.findViewById(R.id.ll_dinein);
            this.dine_in = itemView.findViewById(R.id.dine_in);
            this.llCollection = itemView.findViewById(R.id.ll_collection);
            this.collection = itemView.findViewById(R.id.collection);

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
            holder.minOrder.setText("£" + fav.getDeliveryCharge() + " delivery  •  £" + fav.getMinOrderValue() + " min order");

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

//            holder.rating.setText(String.format("%.1f", fav.getOverallRating()));

            /*TODO: Collection, Delivery, Dinein ... Right now this option not available on api*/
            /*if (fav.getOverallRating() != null || !fav.getOverallRating().equals("")) {
                String[] serve_styles = fav.getOverallRating().split(",");

                if (Arrays.asList(serve_styles).contains("collection")) {
                    holder.llCollection.setVisibility(View.VISIBLE);
                    holder.collection.setImageDrawable(mContext.getResources().getDrawable(R.drawable.open));
                }
                if (Arrays.asList(serve_styles).contains("delivery")) {
                    holder.llDelivery.setVisibility(View.VISIBLE);
                    holder.delivery.setImageDrawable(mContext.getResources().getDrawable(R.drawable.open));
                }
                if (Arrays.asList(serve_styles).contains("dinein")) {
                    holder.llDinein.setVisibility(View.VISIBLE);
                    holder.dine_in.setImageDrawable(mContext.getResources().getDrawable(R.drawable.open));
                }
            }*/
            Glide.with(mContext)
                    .load(fav.getLogo())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.logo);
            Glide.with(mContext)
                    .load(fav.getBackImane())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
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
                                String msg = "You have already placed an order with \" " + sharePre.getString(sharePre.RESTUARANT_NAME) + "\". \nDo you want to? ";
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

        alertDialogBuilder.setPositiveButton("GO TO " + oldRest, new DialogInterface.OnClickListener() {
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
        alertDialogBuilder.setNegativeButton("GO TO " + currentRestuarant, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GlobalValues.getInstance().getDb().menuMaster().nuke();
                        GlobalValues.getInstance().getDb().menuProductMaster().nuke();
                        GlobalValues.getInstance().getDb().productSizeAndModifierMaster().nuke();
                    }
                }).start();

                db.deleteCart();
                sharePre.setString(sharePre.RESTUARANT_ID, "");
                sharePre.setString(sharePre.RESTUARANT_NAME, "");
                Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                i.putExtra("RESTAURANTID", currentRestId);
                i.putExtra("RESTAURANTNAME", fav.getRestaurantName());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);

            }
        });


        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
