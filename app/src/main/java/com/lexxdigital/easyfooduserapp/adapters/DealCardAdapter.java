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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.api.AddFavouritesInterface;
import com.lexxdigital.easyfooduserapp.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapp.model.add_favourites_request.AddFavouristeResquest;
import com.lexxdigital.easyfooduserapp.model.add_favourites_response.AddFavouristeResponse;
import com.lexxdigital.easyfooduserapp.model.landing_page_lists.LandingPageLists;
import com.lexxdigital.easyfooduserapp.model.landing_page_response.RestaurantsDealResponse;
import com.lexxdigital.easyfooduserapp.restaurant_details.RestaurantDetailsActivity;
import com.lexxdigital.easyfooduserapp.restaurants_detail_new.RestaurantDetailNewActivity;
import com.lexxdigital.easyfooduserapp.utility.ApiClient;
import com.lexxdigital.easyfooduserapp.utility.Constants;
import com.lexxdigital.easyfooduserapp.utility.GlobalValues;
import com.lexxdigital.easyfooduserapp.utility.SharedPreferencesClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealCardAdapter extends RecyclerView.Adapter<DealCardAdapter.MyViewHolder> {

    List<RestaurantsDealResponse.Data.Restaurant> response;
    private Context mContext;
    int mSize = 0, mListPosition;
    int st;
    String userID = "";
    Activity activity;
    MyViewHolder mHolder;
    SharedPreferencesClass sharePre;
    DatabaseHelper db;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView logo, bgImage, menuLogo, favIcon, arraowAnimation;
        TextView name, cuisines, rating, deliveryMin, deliveryTime, offerTitle, offerItems, offerPrice;
        LinearLayout clickRestaurant;
        Button btnSeeMenu;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.logo = (ImageView) itemView.findViewById(R.id.restaurant_logo);
            this.bgImage = (ImageView) itemView.findViewById(R.id.restaurant_image);
            this.favIcon = (ImageView) itemView.findViewById(R.id.favourites);
            this.arraowAnimation = (ImageView) itemView.findViewById(R.id.detail_arraw);
            this.offerItems = (TextView) itemView.findViewById(R.id.txt_offer_items);
            this.offerPrice = (TextView) itemView.findViewById(R.id.txt_price);
            this.btnSeeMenu = (Button) itemView.findViewById(R.id.btn_see_full_menu);
//            this.deliveryMin = (TextView) itemView.findViewById(R.id.restaurant_delivery_min_order);
//            this.deliveryTime = (TextView) itemView.findViewById(R.id.restaurant_delivery_time);
            this.offerTitle = (TextView) itemView.findViewById(R.id.dealNameId);
            this.clickRestaurant = (LinearLayout) itemView.findViewById(R.id.layout_restaurant);
            this.menuLogo = (ImageView) itemView.findViewById(R.id.image_menu_logo);
        }
    }

    public DealCardAdapter(Context context, List<RestaurantsDealResponse.Data.Restaurant> res, int count, int lPos, String userid, Activity activity) {
        this.response = res;
        this.mContext = context;
        this.mSize = count;
        this.mListPosition = lPos;
        this.userID = userid;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
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

    //£2.50 delivery  •  £15.00 min order
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        Log.e("POS", "//" + listPosition + "//" + mSize);
        mHolder = holder;
        sharePre = new SharedPreferencesClass(mContext);
        db = new DatabaseHelper(mContext);
        if (listPosition == 0) {
//            holder.name.setText(response.get(mListPosition).getRestaurantName());
//            holder.cuisines.setText(response.get(mListPosition).getCuisines());
//            holder.rating.setText(String.valueOf(response.get(mListPosition).getOverallRating()));
//            holder.deliveryMin.setText("£" + response.get(mListPosition).getRestaurantDeliveryCharge().get(0).getDeliveryCharge() + " delivery  •  £" + response.get(listPosition).getRestaurantDeliveryCharge().get(0).getMinOrderValue() + " min order");
//            holder.deliveryTime.setText(response.get(mListPosition).getAvgDeliveryTime() + " min");

            if (response.get(mListPosition).getFavourite() == 1) {
                holder.favIcon.setBackground(mContext.getResources().getDrawable(R.drawable.favourite_active));
            } else {
                holder.favIcon.setBackground(mContext.getResources().getDrawable(R.drawable.favourite_white));
            }

            Glide.with(mContext).load(R.drawable.animated_arrow2).asGif().into(holder.arraowAnimation);

            holder.favIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addFavourites(response.get(mListPosition).getId(), holder.favIcon, response.get(mListPosition).getFavourite());
                }
            });
            Glide.with(mContext)
                    .load(response.get(mListPosition).getLogo())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.logo);
            if (response.get(mListPosition).getRestaurantsGallery().size() > 0)
                Glide.with(mContext)
                        .load(response.get(mListPosition).getRestaurantsGallery().get(0).getFilePath())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.bgImage);

            holder.clickRestaurant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        if (sharePre.getString(sharePre.RESTUARANT_ID) != null && !sharePre.getString(sharePre.RESTUARANT_ID).equals("") && db.getCartData() != null) {

                            if (sharePre.getString(sharePre.RESTUARANT_ID).equalsIgnoreCase(response.get(mListPosition).getId())) {
                                Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                                i.putExtra("RESTAURANTID", response.get(mListPosition).getId());
                                i.putExtra("RESTAURANTNAME", response.get(mListPosition).getRestaurantName());
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(i);
                                activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                            } else {
                                String msg = "You are already placing an order with \" " + sharePre.getString(sharePre.RESTUARANT_NAME) + "\". \nDo you want to? ";
                                alertDialogNoRestaurant(msg, sharePre.getString(sharePre.RESTUARANT_NAME), response.get(mListPosition).getRestaurantName(), response.get(mListPosition).getId());
                            }
                        } else {
                            Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                            i.putExtra("RESTAURANTID", response.get(mListPosition).getId());
                            i.putExtra("RESTAURANTNAME", response.get(mListPosition).getRestaurantName());
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(i);
                        }

                    } catch (Exception e) {

                        Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                        i.putExtra("RESTAURANTID", response.get(mListPosition).getId());
                        i.putExtra("RESTAURANTNAME", response.get(mListPosition).getRestaurantName());
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                    }
                }

            });

        } else if (listPosition > 0 && listPosition <= mSize) {
            if (response.get(mListPosition).getDiscountOffers().size() > 0) {
                holder.offerItems.setText(response.get(mListPosition).getDiscountOffers().get(listPosition - 1).getDetail());
                holder.offerPrice.setText(response.get(mListPosition).getDiscountOffers().get(listPosition - 1).getOfferPriceLabel());
                holder.offerTitle.setText(response.get(mListPosition).getDiscountOffers().get(listPosition - 1).getOfferTitle());
            }

        } else if (listPosition > mSize) {
            Glide.with(mContext)
                    .load(response.get(mListPosition).getLogo())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.menuLogo);


            holder.btnSeeMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (sharePre.getString(sharePre.RESTUARANT_ID) != null && !sharePre.getString(sharePre.RESTUARANT_ID).equals("")) {
                            Log.e("restaurant", response.get(mListPosition).getRestaurantName() + "," + response.get(mListPosition).getId());
                            if (sharePre.getString(sharePre.RESTUARANT_ID).equalsIgnoreCase(response.get(mListPosition).getId())) {
                                Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                                i.putExtra("RESTAURANTID", response.get(mListPosition).getId());
                                i.putExtra("RESTAURANTNAME", response.get(mListPosition).getRestaurantName());
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(i);
                                activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                            } else {
                                String msg = "You are already placing an order with \" " + sharePre.getString(sharePre.RESTUARANT_NAME) + "\". \nDo you want to? ";
                                alertDialogNoRestaurant(msg, sharePre.getString(sharePre.RESTUARANT_NAME), response.get(mListPosition).getRestaurantName(), response.get(mListPosition).getId());
                            }
                        } else {
                            Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                            i.putExtra("RESTAURANTID", response.get(mListPosition).getId());
                            i.putExtra("RESTAURANTNAME", response.get(mListPosition).getRestaurantName());
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(i);
                        }

                    } catch (Exception e) {

                        Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                        i.putExtra("RESTAURANTID", response.get(mListPosition).getId());
                        i.putExtra("RESTAURANTNAME", response.get(mListPosition).getRestaurantName());
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
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
                    //    Log.e("Error11 <>>>",">>>>>"+response.body().getMessage()+"//"+response.body().getData().getFavouriteStatus());
                    if (response.body().getSuccess()) {
                        if (response.body().getData().getFavouriteStatus() == 1) {
                            fav.setBackground(mContext.getResources().getDrawable(R.drawable.favourite_active));
                        } else if (response.body().getData().getFavouriteStatus() == 0) {
                            fav.setBackground(mContext.getResources().getDrawable(R.drawable.favourite_white));
                        }
                    }
                } catch (Exception e) {
                    //    Log.e("Error11 <>>>",">>>>>"+e.getMessage());
                    //    showDialog("Please try again.");
//                       Toast.makeText(LoginActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddFavouristeResponse> call, Throwable t) {
                //   Log.e("Error12 <>>>",">>>>>"+t.getMessage());
//                dialog.hide();
//                showDialog("Please try again.");
                //    Toast.makeText(LoginActivity.this, "Please try again 2."+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
                        db.deleteCart();
                        sharePre.setString(sharePre.RESTUARANT_ID, "");
                        sharePre.setString(sharePre.RESTUARANT_NAME, "");
                        Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                        i.putExtra("RESTAURANTID", currentRestId);
                        i.putExtra("RESTAURANTNAME", response.get(mListPosition).getRestaurantName());
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
