package com.lexxdigital.easyfooduserapp.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.api.AddFavouritesInterface;
import com.lexxdigital.easyfooduserapp.api.OnBottomReachedListener;
import com.lexxdigital.easyfooduserapp.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapp.fragments.DealsFragment;
import com.lexxdigital.easyfooduserapp.model.add_favourites_request.AddFavouristeResquest;
import com.lexxdigital.easyfooduserapp.model.add_favourites_response.AddFavouristeResponse;
import com.lexxdigital.easyfooduserapp.model.landing_page_lists.LandingPageLists;
import com.lexxdigital.easyfooduserapp.model.landing_page_response.RestaurantsDealResponse;
import com.lexxdigital.easyfooduserapp.restaurant_details.RestaurantDetailsActivity;
import com.lexxdigital.easyfooduserapp.utility.ApiClient;
import com.lexxdigital.easyfooduserapp.utility.Constants;
import com.lexxdigital.easyfooduserapp.utility.GlobalValues;
import com.lexxdigital.easyfooduserapp.utility.SharedPreferencesClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.share.internal.DeviceShareDialogFragment.TAG;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.MyViewHolder> implements Filterable {

    // private ArrayList<Arraylist> dataSet;

    DealCardAdapter mDealCardAdapter;
    Context mContext;
    List<RestaurantsDealResponse.Data.Restaurant> responseDeals;
    List<RestaurantsDealResponse.Data.Restaurant> respNameFilter;
    PositionInterface mPositionInterface;
    String userID = "";
    private int lastPosition = -1;
    OnBottomReachedListener onBottomReachedListener;
    Activity activity;
    SharedPreferencesClass sharePre;
    DatabaseHelper db;
    int mListPosition;

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener) {

        this.onBottomReachedListener = onBottomReachedListener;
    }

    public DealAdapter(Context mContext, PositionInterface mPositionInterface, List<RestaurantsDealResponse.Data.Restaurant> res, String userid, Activity activity) {

        this.mContext = mContext;
        this.mPositionInterface = mPositionInterface;
        this.responseDeals = res;
        this.respNameFilter = res;
        Log.e("Adapter data ", res.toString());
        this.userID = userid;
        this.activity = activity;
    }

    public interface PositionInterface {
        void onClickPos(int pos);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RecyclerView dealcard_list_id;
        TextView name, cuisines, rating, deliveryMin, deliveryVal, deliveryTime, preOrder;
        ImageView delivery, dine_in, collection;
        LinearLayout llMain, llClosed;
        LinearLayout llDelivery, llDinein, llCollection;
        ImageView imRatingImage;


        public MyViewHolder(View itemView) {
            super(itemView);

            this.dealcard_list_id = (RecyclerView) itemView.findViewById(R.id.dealcard_list_id);
            this.name = (TextView) itemView.findViewById(R.id.restaurant_name);
            this.cuisines = (TextView) itemView.findViewById(R.id.restaurant_cuisines);
            this.rating = (TextView) itemView.findViewById(R.id.restaurant_rating);
            this.deliveryMin = (TextView) itemView.findViewById(R.id.restaurant_delivery_min_order);
            this.deliveryTime = (TextView) itemView.findViewById(R.id.restaurant_delivery_time);
            this.deliveryVal = (TextView) itemView.findViewById(R.id.restaurant_delivery_value);
            this.preOrder = (TextView) itemView.findViewById(R.id.pre_order);
            this.llMain = (LinearLayout) itemView.findViewById(R.id.ll_main);
            this.llClosed = (LinearLayout) itemView.findViewById(R.id.closed_design);

            this.imRatingImage = itemView.findViewById(R.id.im_ratingImage);
            this.llDelivery = itemView.findViewById(R.id.ll_delivery);
            this.delivery = itemView.findViewById(R.id.delivery);
            this.llDinein = itemView.findViewById(R.id.ll_dinein);
            this.dine_in = itemView.findViewById(R.id.dine_in);
            this.llCollection = itemView.findViewById(R.id.ll_collection);
            this.collection = itemView.findViewById(R.id.collection);

        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    respNameFilter = responseDeals;
                } else {
                    List<RestaurantsDealResponse.Data.Restaurant> filteredList = new ArrayList<>();
                    for (RestaurantsDealResponse.Data.Restaurant row : responseDeals) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getRestaurantName() != null) {
                            if (row.getCuisines().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            } else if (row.getRestaurantName().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }

                    }

                    respNameFilter = filteredList;

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = respNameFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                respNameFilter = (ArrayList<RestaurantsDealResponse.Data.Restaurant>) filterResults.values;
                notifyDataSetChanged();
                if (DealsFragment.getFragment() != null)
                    DealsFragment.getFragment().updateUi((respNameFilter.size() > 0) ? false : true);
            }
        };
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dealrecycleviewitem, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        sharePre = new SharedPreferencesClass(mContext);
        db = new DatabaseHelper(mContext);
        mListPosition = listPosition;
        if (listPosition == respNameFilter.size() - 1) {
            onBottomReachedListener.onBottomReached(listPosition);
        }
        RecyclerView dealcard_list_id = holder.dealcard_list_id;
        holder.name.setText(respNameFilter.get(listPosition).getRestaurantName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.cuisines.setText(Html.fromHtml(respNameFilter.get(listPosition).getCuisines(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.cuisines.setText(Html.fromHtml(respNameFilter.get(listPosition).getCuisines()));
        }
//        holder.cuisines.setText(respNameFilter.get(listPosition).getCuisines());

        Log.e("rating", respNameFilter.get(listPosition).getOverallRating());
        if (respNameFilter.get(listPosition).getOverallRating() != null) {
            if (respNameFilter.get(listPosition).getOverallRating().equalsIgnoreCase("0")) {
                holder.rating.setText("New");
                holder.imRatingImage.setVisibility(View.GONE);
            } else {
                holder.imRatingImage.setVisibility(View.VISIBLE);
                holder.rating.setText(String.format("%.1f", Double.parseDouble(respNameFilter.get(listPosition).getOverallRating())));
            }
        } else {
            holder.rating.setText("New");
            holder.imRatingImage.setVisibility(View.GONE);
        }

        String status = responseDeals.get(listPosition).getStatus();
        try {
            if (status.trim().equalsIgnoreCase("closed")) {
                holder.llClosed.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Log.e("DealAdapter", "onBindViewHolder<<Exception>>: " + e.getMessage());
        }
        Log.e("deal Adapter", "onBindViewHolder:status " + status);
        if (responseDeals.get(listPosition).getServe_style() != null || !responseDeals.get(listPosition).getServe_style().equals("")) {
            String[] serve_styles = responseDeals.get(listPosition).getServe_style().split(",");

            if (Arrays.asList(serve_styles).contains("collection")) {
                holder.llCollection.setVisibility(View.VISIBLE);
                holder.collection.setImageDrawable(mContext.getResources().getDrawable(R.drawable.open));
            }
            if (Arrays.asList(serve_styles).contains("delivery")) {
                holder.llDelivery.setVisibility(View.VISIBLE);
                holder.delivery.setImageDrawable(mContext.getResources().getDrawable(R.drawable.open));
            }
            if (Arrays.asList(serve_styles).contains("dine_in")) {
                holder.llDinein.setVisibility(View.VISIBLE);
                holder.dine_in.setImageDrawable(mContext.getResources().getDrawable(R.drawable.open));
            }
        }
        holder.preOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView deliveryTime, minOrderForDelivery, collectionTime, preOrderForLetter;
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

                String startDelTime, endDelTime, startCollTime, endCollTime;
                startDelTime = responseDeals.get(listPosition).getRestaurantTiming().get(0).getDeliveryStartTime();
                endDelTime = responseDeals.get(listPosition).getRestaurantTiming().get(0).getDeliveryEndTime();
                startCollTime = responseDeals.get(listPosition).getRestaurantTiming().get(0).getCollectionStartTime();
                endCollTime = responseDeals.get(listPosition).getRestaurantTiming().get(0).getCollectionEndTime();
                Log.e("DealAdapter", "onClick: preorder popup " + startDelTime + "//>" + endDelTime + "//>" + startCollTime + "///>" + endCollTime);
                deliveryTime.setText(startDelTime + " - " + endDelTime);
                collectionTime.setText(startCollTime + " - " + endCollTime);
                minOrderForDelivery.setText("£" + respNameFilter.get(listPosition).getMin_order_value() + " min order");
                im_cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                preOrderForLetter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            dialog.dismiss();
                            Log.e("data>>>", responseDeals.get(listPosition).getRestaurantTiming().toString());
                            if (sharePre.getString(sharePre.RESTUARANT_ID) != null && !sharePre.getString(sharePre.RESTUARANT_ID).equals("")) {

                                if (sharePre.getString(sharePre.RESTUARANT_ID).equalsIgnoreCase(responseDeals.get(listPosition).getId())) {
                                    Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                                    i.putExtra("RESTAURANTID", responseDeals.get(listPosition).getId());
                                    i.putExtra("RESTAURANTNAME", responseDeals.get(listPosition).getRestaurantName());
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    mContext.startActivity(i);
                                    activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                                } else {
                                    String msg = "You are already placing an order with \" " + sharePre.getString(sharePre.RESTUARANT_NAME) + "\". \nDo you want to? ";
                                    alertDialogNoRestaurant(msg, sharePre.getString(sharePre.RESTUARANT_NAME), responseDeals.get(listPosition).getRestaurantName(), responseDeals.get(listPosition).getId());
                                }
                            } else {
                                Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                                i.putExtra("RESTAURANTID", responseDeals.get(listPosition).getId());
                                i.putExtra("RESTAURANTNAME", responseDeals.get(listPosition).getRestaurantName());
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(i);
                            }

                        } catch (Exception e) {

                            Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                            i.putExtra("RESTAURANTID", responseDeals.get(listPosition).getId());
                            i.putExtra("RESTAURANTNAME", responseDeals.get(listPosition).getRestaurantName());
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(i);
                        }
                    }
                });

                dialog.show();
            }
        });


        // if (respNameFilter.get(listPosition).getRestaurantDeliveryCharge().size() > 0)
        //  holder.deliveryMin.setText("£" + respNameFilter.get(listPosition).getRestaurantDeliveryCharge().get(0).getDeliveryCharge() + " delivery  •  £" + respNameFilter.get(listPosition).getRestaurantDeliveryCharge().get(0).getMinOrderValue() + " min order");
        holder.deliveryMin.setText("£" + respNameFilter.get(listPosition).getDelivery_charge() + " delivery ");
        holder.deliveryVal.setText("£" + respNameFilter.get(listPosition).getMin_order_value() + " min order");
        holder.deliveryTime.setText(respNameFilter.get(listPosition).getAvgDeliveryTime() + " min");

        mDealCardAdapter = new DealCardAdapter(mContext, respNameFilter, respNameFilter.get(listPosition).getDiscountOffers().size(), listPosition, userID, activity);
        @SuppressLint("WrongConstant")
        LinearLayoutManager horizontalLayoutManagaer2 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        dealcard_list_id.setLayoutManager(horizontalLayoutManagaer2);
        dealcard_list_id.setAdapter(mDealCardAdapter);
        setAnimation(holder.itemView, listPosition);
    }

    @Override
    public int getItemCount() {
        return respNameFilter.size();
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    public void addLazyLoadedData(List<RestaurantsDealResponse.Data.Restaurant> res, int offset) {
        for (int i = 0; i < res.size(); i++) {
            this.responseDeals.add(res.get(i));
        }
        this.notifyItemRangeInserted(offset, res.size());
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
                i.putExtra("RESTAURANTNAME", currentRestuarant);
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

