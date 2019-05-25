package com.lexxdigital.easyfooduserapp.adapters.previous_order;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapp.model.myorder.OrderDetails;
import com.lexxdigital.easyfooduserapp.model.myorder.PreviousOrderDetail;
import com.lexxdigital.easyfooduserapp.model.myorder.PreviousOrderResponse;
import com.lexxdigital.easyfooduserapp.order_details_activity.OrderDetailActivity;
import com.lexxdigital.easyfooduserapp.order_status.OrderStatusActivity;
import com.lexxdigital.easyfooduserapp.restaurant_details.RestaurantDetailsActivity;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategoryCart;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.MenuProductSize;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.ProductModifier;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.SpecialOffer;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.restaurantmenumodel.menu_response.UpsellProduct;
import com.lexxdigital.easyfooduserapp.utility.Constants;
import com.lexxdigital.easyfooduserapp.utility.SharedPreferencesClass;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.AccessTokenManager.TAG;


public class MyorderAdapter extends RecyclerView.Adapter<MyorderAdapter.MyViewHolder> {

    Context context;
    Activity activity;
    PreviousOrderResponse previousOrder;
    OrderDetails orderDetailsRes;
    SharedPreferencesClass sharePre;
    private List<PreviousOrderDetail> previousOrderDetailList;
    OrderPositionListner orderPositionListner;
    private DatabaseHelper db;
    Gson gson = new Gson();

    public MyorderAdapter(List<PreviousOrderDetail> previousOrderDetailList, Context context, Activity activity, OrderPositionListner orderPositionListner) {
        this.previousOrderDetailList = previousOrderDetailList;
        this.context = context;
        this.activity = activity;
        this.orderPositionListner = orderPositionListner;
        db = new DatabaseHelper(context);
        // this.previousOrder = previousOrder;
    }

    @Override
    public MyorderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorderder_design, parent, false);
        //view.setOnClickListener(MainActivity.myOnClickListener);
        MyorderAdapter.MyViewHolder myViewHolder = new MyorderAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final int listPosition = position;
        final PreviousOrderDetail dataList = previousOrderDetailList.get(listPosition);
        sharePre = new SharedPreferencesClass(context);
        holder.restName.setText(dataList.getRestaurantName());
        holder.orderNo.setText("Order No." + dataList.getOrderNum());
        holder.orderDate.setText(dataList.getOrderDateTime());
        holder.total.setText("\u00a3" + String.valueOf(dataList.getOrderTotal()));
        orderDetailsRes = (OrderDetails) dataList.getOrderDetails();

        try {
            if (orderDetailsRes.getData().getMenuCategoryCarts().size() > 0) {
                String items = "";
                for (int i = 0; i < orderDetailsRes.getData().getMenuCategoryCarts().size(); i++) {

                    if (orderDetailsRes.getData().getMenuCategoryCarts().get(i).getMenuProducts() != null) {
                        for (int j = 0; j < orderDetailsRes.getData().getMenuCategoryCarts().get(i).getMenuProducts().size(); j++) {

                            String itemOrder = orderDetailsRes.getData().getMenuCategoryCarts().get(i).getMenuProducts().get(j).getProductName();
                            if (itemOrder != null) {
                                items = items + "," + itemOrder;
                            }
                        }
                    }
                }
                if (items != null) {
                    Log.e("data", items);
                    String itemdetails = items.substring(1);
                    if (itemdetails.length() > 50) {
                        itemdetails.substring(0, 50);
                        holder.orderItemDetails.setText(itemdetails + "...");
                    } else {
                        holder.orderItemDetails.setText(itemdetails);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        String strOrderStatus = dataList.getOrderStatus();
        Log.e(TAG, "onBindViewHolder: order status: " + strOrderStatus);
        // status will be 'new','pending','rejected','accepted','out_of_delivery','delivered','preparing'-------------
        if (strOrderStatus.equalsIgnoreCase("new") || strOrderStatus.equalsIgnoreCase("pending")) {
            holder.orderStatus.setText("Pending");
            holder.cancelOrder.setVisibility(View.GONE);
            holder.layoutTrackOrder.setVisibility(View.GONE);
            holder.layoutReapetOrder.setVisibility(View.GONE);
        } else if (strOrderStatus.equalsIgnoreCase("accepted") || strOrderStatus.equalsIgnoreCase("preparing") || strOrderStatus.equalsIgnoreCase("out_for_delivery")) {
            String ordStatus = strOrderStatus;
            String status = ordStatus.substring(0, 1).toUpperCase() + ordStatus.substring(1);
            holder.orderStatus.setText(status);
            holder.layoutTrackOrder.setVisibility(View.VISIBLE);
            holder.layoutReapetOrder.setVisibility(View.GONE);
            holder.cancelOrder.setVisibility(View.GONE);
        } else if (strOrderStatus.equalsIgnoreCase("delivered")) {
            String ordStatus = strOrderStatus;
            String status = ordStatus.substring(0, 1).toUpperCase() + ordStatus.substring(1);
            holder.orderStatus.setText(status);
            holder.orderStatus.setTextColor(Color.GREEN);

            holder.cancelOrder.setVisibility(View.GONE);
            holder.layoutTrackOrder.setVisibility(View.GONE);
            holder.layoutReapetOrder.setVisibility(View.VISIBLE);

        } else if (strOrderStatus.equalsIgnoreCase("rejected")) {
            String ordStatus = strOrderStatus;
            String status = ordStatus.substring(0, 1).toUpperCase() + ordStatus.substring(1);
            holder.orderStatus.setText(status);
            holder.orderStatus.setTextColor(Color.RED);
            holder.cancelOrder.setVisibility(View.GONE);
            holder.layoutTrackOrder.setVisibility(View.GONE);
            holder.layoutReapetOrder.setVisibility(View.VISIBLE);
        } else {
            String ordStatus = strOrderStatus;
            String status = ordStatus.substring(0, 1).toUpperCase() + ordStatus.substring(1);
            holder.orderStatus.setText(status);
            holder.cancelOrder.setVisibility(View.GONE);
            holder.layoutTrackOrder.setVisibility(View.GONE);
            holder.layoutReapetOrder.setVisibility(View.VISIBLE);
        }

        //----- below code for hide track order option


        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (dataList.getOrderDetails().getData().getMenuCategoryCarts() != null) {
                        if (Constants.isInternetConnectionAvailable(300)) {
                            Intent intent = new Intent(context, OrderDetailActivity.class);
                            intent.putExtra("order_no", dataList.getOrderNum());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                        } else {
                            dialogNoInternetConnection("Please check internet connection.");
                        }

                    }

                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        holder.trackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Constants.isInternetConnectionAvailable(300)) {
                        Intent intent = new Intent(context, OrderStatusActivity.class);
                        intent.putExtra("order_no", dataList.getOrderNum());
                        sharePre.setOrderIDKey(dataList.getOrderNum());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                    } else {
                        dialogNoInternetConnection("Please check internet connection.");
                    }
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.repeatOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.getCartData() == null) {
                    //insertData(previousOrderDetailList.get(position));
                } else {
                    db.deleteCart();
                    //insertData(previousOrderDetailList.get(position));
                    Intent i = new Intent(context, RestaurantDetailsActivity.class);
                    i.putExtra("RESTAURANTID", dataList.getRestaurantId());
                    i.putExtra("RESTAURANTNAME", dataList.getRestaurantName());
                    sharePre.setString(sharePre.RESTUARANT_ID, dataList.getRestaurantId());
                    sharePre.setString(sharePre.RESTUARANT_NAME, dataList.getRestaurantName());
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                    activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
//                    alertDailogConfirm("Alert message", dataList.getRestaurantId(), dataList.getRestaurantName(), position);
                }
            }
        });
        holder.cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderPositionListner.onClickPosition(listPosition, dataList.getOrderNum());
            }
        });
        holder.detailsArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (Constants.isInternetConnectionAvailable(300)) {
                        Intent intent = new Intent(context, OrderDetailActivity.class);
                        intent.putExtra("order_no", dataList.getOrderNum());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                    } else {
                        dialogNoInternetConnection("Please check internet connection.");
                    }
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
        // Picasso.with(context).load(dataList.getRestaurantImage()).into(holder.restImage);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView restImage;
        CircleImageView restLogo;
        TextView restName, orderNo, orderDate, total, trackOrder, orderItemDetails, detailsArrow, orderStatus;
        TextView repeatOrder;
        RecyclerView subProductRecycler;
        SubProductListAdapter subProductListAdapter;
        LinearLayout layoutTrackOrder, layoutReapetOrder, llMain, cancelOrder;

        // ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.restName = (TextView) itemView.findViewById(R.id.rest_name);
            this.orderNo = (TextView) itemView.findViewById(R.id.order_no);
            this.orderDate = (TextView) itemView.findViewById(R.id.order_time);
            this.total = (TextView) itemView.findViewById(R.id.price);
            this.trackOrder = (TextView) itemView.findViewById(R.id.track_order);
            this.repeatOrder = (TextView) itemView.findViewById(R.id.tv_repeatOrder);
            this.orderItemDetails = (TextView) itemView.findViewById(R.id.order_item_details);
            this.detailsArrow = (TextView) itemView.findViewById(R.id.detail_arraw);
            this.orderStatus = (TextView) itemView.findViewById(R.id.order_status);
            this.layoutTrackOrder = itemView.findViewById(R.id.layout_track_order);
            this.layoutReapetOrder = itemView.findViewById(R.id.layout_repeat_order);
            this.llMain = itemView.findViewById(R.id.ll_main);
            this.cancelOrder = itemView.findViewById(R.id.cancel_order);

        }

       /* private void initView() {
            subProductListAdapter = new SubProductListAdapter(orderDetailsRes, context);
            @SuppressLint("WrongConstant")
            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            subProductRecycler.setLayoutManager(horizontalLayoutManagaer);
            subProductRecycler.setAdapter(subProductListAdapter);
        }*/

    }

    @Override
    public int getItemCount() {
        Log.e(TAG, "getItemCount:list size: " + previousOrderDetailList.size());
        // Log.e("count", "getItemCount: previousOrderDetailList.size()"+ previousOrder.getData().getPreviousOrderDetails().size() );
        return previousOrderDetailList.size();
    }


    public void alertDailogConfirm(String message, final String restuarantId, final String restuarantName, int positionList) {
        final int position = positionList;
        LayoutInflater factory = LayoutInflater.from(this.activity);
        final View mDialogView = factory.inflate(R.layout.pop_alert, null);
        final AlertDialog noteDialog = new AlertDialog.Builder(this.activity).create();
        noteDialog.setView(mDialogView);
        noteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        noteDialog.setCanceledOnTouchOutside(false);
        noteDialog.setCancelable(false);

        TextView tvMessage = mDialogView.findViewById(R.id.message);
        tvMessage.setText(message);

        TextView btnOK = mDialogView.findViewById(R.id.btn_ok);


        mDialogView.findViewById(R.id.cross_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteDialog.dismiss();
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteCart();
                insertData(previousOrderDetailList.get(position));
                Intent i = new Intent(context, RestaurantDetailsActivity.class);
                i.putExtra("RESTAURANTID", restuarantId);
                i.putExtra("RESTAURANTNAME", restuarantName);
                sharePre.setString(sharePre.RESTUARANT_ID, restuarantId);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
                activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                noteDialog.dismiss();
            }
        });

        noteDialog.show();
    }

    public void insertData(PreviousOrderDetail previousOrderDetailList) {

        List<ProductModifier> productModifiers = null;
        List<MenuProductSize> menuProductSize = null;
        /* menu product*/
        for (int i = 0; i < previousOrderDetailList.getOrderDetails().getData().getMenuCategoryCarts().size(); i++) {
            long id = db.getMenuCategoryIfExit(previousOrderDetailList.getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuCategoryId());
            if (id == -1) {
                id = db.insertMenuCategory(previousOrderDetailList.getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuCategoryId(), previousOrderDetailList.getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuCategoryName(), "", "");
            }


            List<MenuProduct> menuProducts = previousOrderDetailList.getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuProducts();

            for (int j = 0; j < menuProducts.size(); j++) {

                menuProductSize = menuProducts.get(j).getMenuProductSize();

                if (menuProductSize.size() > 0) {
                    for (int k = 0; k < menuProductSize.size(); k++) {

                    }
                }
                productModifiers = menuProducts.get(j).getProductModifiers();

                if (productModifiers.size() > 0) {
                    for (int k = 0; k < productModifiers.size(); k++) {

                    }
                }

                db.insertMenuProduct(id, menuProducts.get(j).getMenuSubCatId(), previousOrderDetailList.getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuCategoryId(),
                        menuProducts.get(j).getMenuProductId(),
                        menuProducts.get(j).getProductName(),
                        menuProducts.get(j).getVegType(),
                        menuProducts.get(j).getMenuProductPrice(),
                        menuProducts.get(j).getUserappProductImage(),
                        menuProducts.get(j).getEcomProductImage(),
                        menuProducts.get(j).getProductOverallRating(),
                        menuProducts.get(j).getOriginalQuantity(),
                        gson.toJson(menuProductSize),
                        gson.toJson(productModifiers),
                        null,
                        menuProducts.get(j).getOriginalQuantity(),
                        Double.parseDouble(menuProducts.get(j).getMenuProductPrice()),
                        menuProducts.get(j).getMenuProductPrice());
            }

            List<MenuCategoryCart> menuSubCategory = previousOrderDetailList.getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuSubCategory();
            if (menuSubCategory.size() > 0) {
                for (int j = 0; j < menuSubCategory.size(); j++) {

                   /* db.insertMenuProduct(id, menuProducts.get(j).getMenuSubCatId(), previousOrderDetailList.getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuCategoryId(),
                            menuSubCategory.get(i).getMenuProducts().get(j).getMenuProductId(),
                            menuSubCategory.get(i).getMenuProducts().get(j).getProductName(),
                            menuSubCategory.get(i).getMenuProducts().get(j).getVegType(),
                            menuSubCategory.get(i).getMenuProducts().get(j).getMenuProductPrice(),
                            menuSubCategory.get(i).getMenuProducts().get(j).getUserappProductImage(),
                            menuSubCategory.get(i).getMenuProducts().get(j).getEcomProductImage(),
                            menuSubCategory.get(i).getMenuProducts().get(j).getProductOverallRating(),
                            menuSubCategory.get(i).getMenuProducts().get(j).getOriginalQuantity(),
                            gson.toJson(menuProductSize),
                            gson.toJson(productModifiers),
                            menuSubCategory.get(i).getMenuProducts().get(j).getOriginalQuantity(),
                            Double.parseDouble( menuSubCategory.get(i).getMenuProducts().get(j).getMenuProductPrice()),
                            menuSubCategory.get(i).getMenuProducts().get(j).getMenuProductPrice());*/
                }
            }

        }

        /*  Special offers*/
        List<SpecialOffer> specialOfferList = previousOrderDetailList.getOrderDetails().getData().getSpecialOffers();
        if (specialOfferList.size() > 0) {
            for (int i = 0; i < specialOfferList.size(); i++) {
                db.insertSpecialOffer(specialOfferList.get(i));
            }
        }

        /* Upsell product*/
        List<UpsellProduct> upsellProductList = previousOrderDetailList.getOrderDetails().getData().getUpsellProducts();
        if (upsellProductList.size() > 0) {
            for (int i = 0; i < upsellProductList.size(); i++) {
                db.insertUpsellProducts(upsellProductList.get(i));
            }
        }
    }

    public void dialogNoInternetConnection(String message) {
        LayoutInflater factory = LayoutInflater.from(this.activity);
        final View mDialogView = factory.inflate(R.layout.addnote_success_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this.activity).create();
        alertDialog.setView(mDialogView);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final Animation animShake = AnimationUtils.loadAnimation(this.activity, R.anim.shake);

        TextView tvMessage = mDialogView.findViewById(R.id.message);
        tvMessage.setText(message);
        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constants.isInternetConnectionAvailable(300)) {
                    alertDialog.dismiss();
                } else mDialogView.findViewById(R.id.okTv).startAnimation(animShake);

            }
        });

        alertDialog.show();
    }
}
