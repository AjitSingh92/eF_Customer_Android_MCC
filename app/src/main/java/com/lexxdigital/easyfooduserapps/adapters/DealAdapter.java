package com.lexxdigital.easyfooduserapps.adapters;

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
import android.widget.TextView;

import com.google.gson.Gson;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.api.OnBottomReachedListener;
import com.lexxdigital.easyfooduserapps.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapps.fragments.DealsFragment;
import com.lexxdigital.easyfooduserapps.model.landing_page_response.RestaurantsDealResponse;
import com.lexxdigital.easyfooduserapps.restaurant_details.RestaurantDetailsActivity;
import com.lexxdigital.easyfooduserapps.utility.Constants;
import com.lexxdigital.easyfooduserapps.utility.GlobalValues;
import com.lexxdigital.easyfooduserapps.utility.SharedPreferencesClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.lexxdigital.easyfooduserapps.utility.SharedPreferencesClass.OFFERR_DETAL_DFG;

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


    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener) {

        this.onBottomReachedListener = onBottomReachedListener;
    }

    public DealAdapter(Context mContext, PositionInterface mPositionInterface, String userid, Activity activity) {

        this.mContext = mContext;
        this.mPositionInterface = mPositionInterface;
        this.responseDeals = new ArrayList<>();
        this.respNameFilter = new ArrayList<>();
        this.userID = userid;
        this.activity = activity;
    }

    public void clearItems() {
        this.responseDeals.clear();
        this.respNameFilter.clear();
        notifyDataSetChanged();
    }

    public void addItem(List<RestaurantsDealResponse.Data.Restaurant> restaurants) {
        this.responseDeals = restaurants;
        this.respNameFilter = restaurants;
        notifyItemInserted(this.respNameFilter.size());
    }

    public interface PositionInterface {
        void onClickPos(int pos);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout btnPreOrder, layoutDeliveryPrice, layoutDeliveryTime;
        RecyclerView dealcard_list_id;
        TextView name, cuisines, rating, deliveryMin, deliveryVal, deliveryTime, preOrder, tvPreOrderMsg, tvDistance;
        ImageView delivery, dine_in, collection;
        LinearLayout llMain, llClosed;
        LinearLayout llDelivery, llDinein, llCollection;
        ImageView imRatingImage;


        public MyViewHolder(View itemView) {
            super(itemView);

            this.dealcard_list_id = (RecyclerView) itemView.findViewById(R.id.dealcard_list_id);
            this.preOrder = (TextView) itemView.findViewById(R.id.pre_order);
            this.tvPreOrderMsg = (TextView) itemView.findViewById(R.id.tv_PreOrderMsg);
            this.llClosed = (LinearLayout) itemView.findViewById(R.id.closed_design);
            /*
            this.btnPreOrder = (LinearLayout) itemView.findViewById(R.id.layout_btnPreOrder);
            this.layoutDeliveryPrice = (LinearLayout) itemView.findViewById(R.id.layout_deliveryPrice);
            this.layoutDeliveryTime = (LinearLayout) itemView.findViewById(R.id.layout_deliveryTime);

            this.name = (TextView) itemView.findViewById(R.id.restaurant_name);
            this.cuisines = (TextView) itemView.findViewById(R.id.restaurant_cuisines);
            this.rating = (TextView) itemView.findViewById(R.id.restaurant_rating);
            this.deliveryMin = (TextView) itemView.findViewById(R.id.restaurant_delivery_min_order);
            this.deliveryTime = (TextView) itemView.findViewById(R.id.restaurant_delivery_time);
            this.deliveryVal = (TextView) itemView.findViewById(R.id.restaurant_delivery_value);

            this.llMain = (LinearLayout) itemView.findViewById(R.id.ll_main);

            this.tvDistance = (TextView) itemView.findViewById(R.id.tv_distance);

            this.imRatingImage = itemView.findViewById(R.id.im_ratingImage);
            this.llDelivery = itemView.findViewById(R.id.ll_delivery);
            this.delivery = itemView.findViewById(R.id.delivery);
            this.llDinein = itemView.findViewById(R.id.ll_dinein);
            this.dine_in = itemView.findViewById(R.id.dine_in);
            this.llCollection = itemView.findViewById(R.id.ll_collection);
            this.collection = itemView.findViewById(R.id.collection);*/

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
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dealrecycleviewitem, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int listPosition) {
        sharePre = new SharedPreferencesClass(mContext);
        db = new DatabaseHelper(mContext);
        final int mListPosition = listPosition;
        if (listPosition == respNameFilter.size() - 1) {
            onBottomReachedListener.onBottomReached(listPosition);
        }
        RecyclerView dealcard_list_id = holder.dealcard_list_id;
       /* holder.name.setText(respNameFilter.get(listPosition).getRestaurantName());
        holder.tvDistance.setText(respNameFilter.get(listPosition).getDistance_in_miles() + " miles");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.cuisines.setText(Html.fromHtml(respNameFilter.get(listPosition).getCuisines(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.cuisines.setText(Html.fromHtml(respNameFilter.get(listPosition).getCuisines()));
        }
*/

        /*if (respNameFilter.get(listPosition).getOverallRating() != null) {
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
*/
//        String status = respNameFilter.get(listPosition).getStatus();

        /*try {
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
*//*
        if (respNameFilter.get(listPosition).getDelivery_options() != null || !respNameFilter.get(listPosition).getDelivery_options().equals("")) {
            String[] serve_styles = respNameFilter.get(listPosition).getDelivery_options().split(",");

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
        */
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
                String upperString = respNameFilter.get(mListPosition).getRestaurantTiming().get(0).getDay().substring(0, 1).toUpperCase() + respNameFilter.get(mListPosition).getRestaurantTiming().get(0).getDay().substring(1);
                tvDay.setText(upperString);
                startDelTime = respNameFilter.get(mListPosition).getRestaurantTiming().get(0).getDeliveryStartTime();
                endDelTime = respNameFilter.get(mListPosition).getRestaurantTiming().get(0).getDeliveryEndTime();
                startCollTime = respNameFilter.get(mListPosition).getRestaurantTiming().get(0).getCollectionStartTime();
                endCollTime = respNameFilter.get(mListPosition).getRestaurantTiming().get(0).getCollectionEndTime();
                deliveryTime.setText(startDelTime + " - " + endDelTime);
                collectionTime.setText(startCollTime + " - " + endCollTime);
                minOrderForDelivery.setText(mContext.getResources().getString(R.string.currency) + respNameFilter.get(mListPosition).getMin_order_value() + " min order");
                im_cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                preOrderForLetter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (respNameFilter.get(mListPosition).getStatus().equalsIgnoreCase("not_serving")) {
                            return;
                        }
                        dialog.dismiss();
                        if (sharePre.getString(sharePre.RESTUARANT_ID) != null && !sharePre.getString(sharePre.RESTUARANT_ID).equals("")) {
                            if (sharePre.getString(sharePre.RESTUARANT_ID).equalsIgnoreCase(respNameFilter.get(mListPosition).getId())) {
                                Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                                i.putExtra("RESTAURANTID", respNameFilter.get(mListPosition).getId());
                                i.putExtra("RESTAURANTNAME", respNameFilter.get(mListPosition).getRestaurantName());
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(i);
                                activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                            } else {
                                if (db.getCartData().getMenuCategoryCarts().size() + db.getCartData().getSpecialOffers().size() + db.getCartData().getUpsellProducts().size() > 0) {
                                    String msg = "You have item in your basket from\n" + sharePre.getString(sharePre.RESTUARANT_NAME);
                                    alreadyAlertDialog(msg, sharePre.getString(sharePre.RESTUARANT_NAME), respNameFilter.get(mListPosition).getRestaurantName(), respNameFilter.get(mListPosition).getId());
                                } else {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {

                                            sharePre.setString(OFFERR_DETAL_DFG, null);

                                            GlobalValues.getInstance().getDb().menuMaster().nuke();
                                            GlobalValues.getInstance().getDb().menuProductMaster().nuke();
                                            GlobalValues.getInstance().getDb().productSizeAndModifierMaster().nuke();
                                            sharePre.setString(sharePre.DEFAULT_ADDRESS, null);
                                            sharePre.setString(sharePre.NOTEPAD, "");
                                            db.getCartData();
                                            Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                                            sharePre.setString(sharePre.RESTUARANT_ID, respNameFilter.get(mListPosition).getId());
                                            sharePre.setString(sharePre.RESTUARANT_NAME, respNameFilter.get(mListPosition).getRestaurantName());
                                            i.putExtra("RESTAURANTID", respNameFilter.get(mListPosition).getId());
                                            i.putExtra("RESTAURANTNAME", respNameFilter.get(mListPosition).getRestaurantName());
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

                                    sharePre.setString(sharePre.RESTUARANT_ID, respNameFilter.get(mListPosition).getId());
                                    sharePre.setString(sharePre.RESTUARANT_NAME, respNameFilter.get(mListPosition).getRestaurantName());

                                    Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                                    i.putExtra("RESTAURANTID", respNameFilter.get(mListPosition).getId());
                                    i.putExtra("RESTAURANTNAME", respNameFilter.get(mListPosition).getRestaurantName());
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    mContext.startActivity(i);
                                }
                            }).start();
                        }
                    }
                });

                dialog.show();
            }
        });
        /***********************************************************************************************************************/
        holder.llClosed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharePre.getString(sharePre.RESTUARANT_ID) != null && !sharePre.getString(sharePre.RESTUARANT_ID).equals("")) {
                    if (sharePre.getString(sharePre.RESTUARANT_ID).equalsIgnoreCase(respNameFilter.get(mListPosition).getId())) {
                        Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                        i.putExtra("RESTAURANTID", respNameFilter.get(mListPosition).getId());
                        i.putExtra("RESTAURANTNAME", respNameFilter.get(mListPosition).getRestaurantName());
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                        activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                    } else {
                        if (db.getCartData().getMenuCategoryCarts().size() + db.getCartData().getSpecialOffers().size() + db.getCartData().getUpsellProducts().size() > 0) {
                            String msg = "You have item in your basket from\n" + sharePre.getString(sharePre.RESTUARANT_NAME);
                            alreadyAlertDialog(msg, sharePre.getString(sharePre.RESTUARANT_NAME), respNameFilter.get(mListPosition).getRestaurantName(), respNameFilter.get(mListPosition).getId());
                        } else {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    sharePre.setString(OFFERR_DETAL_DFG, null);
                                    GlobalValues.getInstance().getDb().menuMaster().nuke();
                                    GlobalValues.getInstance().getDb().menuProductMaster().nuke();
                                    GlobalValues.getInstance().getDb().productSizeAndModifierMaster().nuke();
                                    sharePre.setString(sharePre.DEFAULT_ADDRESS, null);
                                    sharePre.setString(sharePre.NOTEPAD, "");

                                    db.getCartData();
                                    Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                                    sharePre.setString(sharePre.RESTUARANT_ID, respNameFilter.get(mListPosition).getId());
                                    sharePre.setString(sharePre.RESTUARANT_NAME, respNameFilter.get(mListPosition).getRestaurantName());

                                    i.putExtra("RESTAURANTID", respNameFilter.get(mListPosition).getId());
                                    i.putExtra("RESTAURANTNAME", respNameFilter.get(mListPosition).getRestaurantName());
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

                            sharePre.setString(sharePre.RESTUARANT_ID, respNameFilter.get(mListPosition).getId());
                            sharePre.setString(sharePre.RESTUARANT_NAME, respNameFilter.get(mListPosition).getRestaurantName());

                            Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                            i.putExtra("RESTAURANTID", respNameFilter.get(mListPosition).getId());
                            i.putExtra("RESTAURANTNAME", respNameFilter.get(mListPosition).getRestaurantName());
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(i);
                        }
                    }).start();
                }
            }
        });


        /***********************************************************************************************************************/


       /* holder.deliveryMin.setText(mContext.getResources().getString(R.string.currency) + respNameFilter.get(listPosition).getDelivery_charge() + " delivery ");
        holder.deliveryVal.setText(mContext.getResources().getString(R.string.currency) + respNameFilter.get(listPosition).getMin_order_value() + " min order");
        holder.deliveryTime.setText(respNameFilter.get(listPosition).getAvgDeliveryTime() + " min");
*/
        mDealCardAdapter = new DealCardAdapter(mContext, respNameFilter.get(listPosition), respNameFilter.get(listPosition).getDiscountOffers().size(), listPosition, userID, activity);
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

        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public void addLazyLoadedData(List<RestaurantsDealResponse.Data.Restaurant> res, int offset) {
        for (int i = 0; i < res.size(); i++) {
            this.respNameFilter.add(res.get(i));
        }
        this.notifyItemRangeInserted(offset, res.size());
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

        tvGoOld.setText("GO TO " + oldRest);
        tvGoNew.setText("GO TO " + currentRestuarant);
        tv_do_you.setText("Do you want to remove those and start a new order with" + currentRestuarant+"?");
        tvGoOld.setOnClickListener(new View.OnClickListener() {
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

        tvGoNew.setOnClickListener(new View.OnClickListener() {
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
                    }
                }).start();
                sharePre.setString(sharePre.RESTUARANT_ID, currentRestId);
                sharePre.setString(sharePre.RESTUARANT_NAME, currentRestuarant);
                sharePre.setString(sharePre.DEFAULT_ADDRESS, null);
                sharePre.setString(sharePre.NOTEPAD, "");
                db.deleteCart();
                Intent i = new Intent(mContext, RestaurantDetailsActivity.class);
                i.putExtra("RESTAURANTID", currentRestId);
                i.putExtra("RESTAURANTNAME", currentRestuarant);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);

            }
        });
        mDialogVieww.findViewById(R.id.cross_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                alertDialog.dismiss();
            }
        });


        alertDialog.show();
    }

}

