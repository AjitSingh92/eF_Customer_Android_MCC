package com.lexxdigital.easyfooduserapps.adapters.previous_order;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.google.gson.Gson;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.cart_db.DatabaseHelper;
import com.lexxdigital.easyfooduserapps.dashboard.DashboardActivity;
import com.lexxdigital.easyfooduserapps.model.myorder.OrderDetails;
import com.lexxdigital.easyfooduserapps.model.myorder.PreviousOrderDetail;
import com.lexxdigital.easyfooduserapps.model.myorder.PreviousOrderResponse;
import com.lexxdigital.easyfooduserapps.model.order_again.OrderAgainRequest;
import com.lexxdigital.easyfooduserapps.model.order_again.OrderAgainResponse;
import com.lexxdigital.easyfooduserapps.order_details_activity.OrderDetailActivity;
import com.lexxdigital.easyfooduserapps.order_status.OrderStatusActivity;
import com.lexxdigital.easyfooduserapps.restaurant_details.RestaurantDetailsActivity;
import com.lexxdigital.easyfooduserapps.restaurant_details.api.RestaurantDetailsInterface;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategoryCart;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuProductSize;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.ProductModifier;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.SpecialOffer;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.UpsellProduct;
import com.lexxdigital.easyfooduserapps.utility.ApiClient;
import com.lexxdigital.easyfooduserapps.utility.Constants;
import com.lexxdigital.easyfooduserapps.utility.SharedPreferencesClass;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private Dialog dialog;

    public MyorderAdapter(List<PreviousOrderDetail> previousOrderDetailList, Context context, Activity activity, OrderPositionListner orderPositionListner) {
        this.previousOrderDetailList = previousOrderDetailList;
        this.context = context;
        this.activity = activity;
        this.orderPositionListner = orderPositionListner;
        db = new DatabaseHelper(context);
        dialog = new Dialog(this.activity);
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final int listPosition = position;
        final PreviousOrderDetail dataList = previousOrderDetailList.get(listPosition);
        sharePre = new SharedPreferencesClass(context);
        dialog.setTitle("");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


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

        holder.reasonForCancel.setVisibility(View.GONE);
        String strOrderStatus = dataList.getOrderStatus();
        Log.e(TAG, "onBindViewHolder: order status: " + strOrderStatus);
        // status will be 'new','pending','rejected','accepted','out_of_delivery','delivered','preparing'-------------
        if (strOrderStatus.equalsIgnoreCase("new") || strOrderStatus.equalsIgnoreCase("pending")) {
            holder.orderStatus.setText("New");
            holder.orderStatus.setTextColor(context.getResources().getColor(R.color.price_color));
            holder.cancelOrder.setVisibility(View.GONE);
            holder.layoutTrackOrder.setVisibility(View.GONE);
            holder.layoutReapetOrder.setVisibility(View.GONE);
        } else if (strOrderStatus.equalsIgnoreCase("accepted") || strOrderStatus.equalsIgnoreCase("preparing") || strOrderStatus.equalsIgnoreCase("out_for_delivery")) {
            String ordStatus = strOrderStatus;
            String status = ordStatus.substring(0, 1).toUpperCase() + ordStatus.substring(1);
            if (strOrderStatus.equalsIgnoreCase("out_for_delivery")) {
                if (dataList.getDeliveryOption().equalsIgnoreCase("collection")) {
                    holder.orderStatus.setText("Ready To Collect");
                } else {
                    holder.orderStatus.setText("Out for Delivery");
                }
            } else {
                holder.orderStatus.setText(status);
            }
            holder.orderStatus.setTextColor(context.getResources().getColor(R.color.price_color));
            holder.layoutTrackOrder.setVisibility(View.VISIBLE);
            holder.layoutReapetOrder.setVisibility(View.GONE);
            holder.cancelOrder.setVisibility(View.GONE);

        } else if (strOrderStatus.equalsIgnoreCase("delivered")) {
            String ordStatus = strOrderStatus;
            String status = ordStatus.substring(0, 1).toUpperCase() + ordStatus.substring(1);
            if (dataList.getDeliveryOption().equalsIgnoreCase("collection")) {
                holder.orderStatus.setText("Collected");
            } else {
                holder.orderStatus.setText("Delivered");
            }
            // holder.orderStatus.setText(status);
            holder.orderStatus.setTextColor(Color.GREEN);
            holder.cancelOrder.setVisibility(View.GONE);
            holder.layoutTrackOrder.setVisibility(View.GONE);
            // holder.layoutReapetOrder.setVisibility(View.VISIBLE);

        } else if (strOrderStatus.equalsIgnoreCase("rejected")) {
            String ordStatus = strOrderStatus;
            String status = ordStatus.substring(0, 1).toUpperCase() + ordStatus.substring(1);
            holder.orderStatus.setText(status);
            holder.orderStatus.setTextColor(Color.RED);
            holder.cancelOrder.setVisibility(View.GONE);
            holder.layoutTrackOrder.setVisibility(View.GONE);
            //  holder.layoutReapetOrder.setVisibility(View.VISIBLE);

            if (dataList.getOrderRejectNote() != null && !dataList.getOrderRejectNote().equalsIgnoreCase("")) {
                // holder.reasonForCancel.setVisibility(View.VISIBLE);
                holder.reasonForCancel.setText(dataList.getOrderRejectNote());
            } else {
                holder.reasonForCancel.setVisibility(View.GONE);
            }
        }/*else if(strOrderStatus.equalsIgnoreCase("out_for_delivery")){

            if (dataList.getDeliveryOption().equalsIgnoreCase("collection")) {
                holder.orderStatus.setText("Ready To Collect");
            } else {
                holder.orderStatus.setText("Out for Delivery");
            }}
            String ordStatus = strOrderStatus;
            String status = ordStatus.substring(0, 1).toUpperCase() + ordStatus.substring(1);
            //holder.orderStatus.setText(status);
            holder.cancelOrder.setVisibility(View.GONE);
            holder.layoutTrackOrder.setVisibility(View.GONE);
        }*/ else {
            String ordStatus = strOrderStatus;
            String status = ordStatus.substring(0, 1).toUpperCase() + ordStatus.substring(1);
            holder.orderStatus.setText(status);
            holder.cancelOrder.setVisibility(View.GONE);
            holder.layoutTrackOrder.setVisibility(View.GONE);
            //holder.layoutReapetOrder.setVisibility(View.VISIBLE);
        }

        //----- below code for hide track order option


        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
//                    if (dataList.getOrderDetails().getData().getMenuCategoryCarts() != null) {
                    if (Constants.isInternetConnectionAvailable(300)) {
                        Intent intent = new Intent(context, OrderDetailActivity.class);
                        intent.putExtra("order_no", dataList.getOrderNum());
                        intent.putExtra(Constants.PAYMENT_MODE, dataList.getPaymentMode());
                        intent.putExtra(Constants.RESTAURENT_NAME, dataList.getRestaurantName());
                        intent.putExtra(Constants.TOTAL_COST, String.valueOf(dataList.getOrderTotal()));
                        intent.putExtra(Constants.PHONE_NUMBER, "9876543210");
                        intent.putExtra(Constants.CUSTOMER_ID, dataList.getCustomerId());
                        intent.putExtra(Constants.ORDER_TIME, dataList.getDeliveryTime());

                        //  data.getData().getPhone_number();
                        Log.e("Total Cost", "" + dataList.getTotal());
                        intent.putExtra(Constants.ORDER_TYPE, dataList.getDeliveryOption());
                        sharePre.setOrderIDKey(dataList.getOrderNum());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                    } else {
                        dialogNoInternetConnection("Please check internet connection.");
                    }

//                    }

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
                        intent.putExtra(Constants.PAYMENT_MODE, dataList.getPaymentMode());
                        intent.putExtra(Constants.RESTAURENT_NAME, dataList.getRestaurantName());
                        intent.putExtra(Constants.TOTAL_COST, String.valueOf(dataList.getOrderTotal()));
                        intent.putExtra(Constants.PHONE_NUMBER, "9876543210");
                        intent.putExtra(Constants.CUSTOMER_ID, dataList.getCustomerId());
                        intent.putExtra(Constants.ORDER_TIME, dataList.getDeliveryTime());

                        //  data.getData().getPhone_number();
                        Log.e("Total Cost", "" + dataList.getTotal());
                        intent.putExtra(Constants.ORDER_TYPE, dataList.getDeliveryOption());
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

                /*db.deleteCart();
                addOrderOnCart(listPosition, dataList.getRestaurantId(), dataList.getRestaurantName());*/
            }
        });
        holder.repeatOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getOrderAgain(dataList.getOrderNum(), listPosition, dataList.getRestaurantId(), dataList.getRestaurantName());

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

    void addOrderOnCart(int listPosition, String restaurantId, String restaurantName) {
        PreviousOrderDetail orderDetail = previousOrderDetailList.get(listPosition);

        Log.e("ANAND >>> ", orderDetail.getOrderDetails().toString());
        long id = -1;
        long subCatId = -1;
        for (int i = 0; i < orderDetail.getOrderDetails().getData().getMenuCategoryCarts().size(); i++) {
            if (orderDetail.getOrderDetails().getData().getMenuCategoryCarts() != null && orderDetail.getOrderDetails().getData().getMenuCategoryCarts().size() > 0) {
                id = db.getMenuCategoryIfExit(orderDetail.getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuCategoryId());
                if (id == -1) {
                    id = db.insertMenuCategory(orderDetail.getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuCategoryId(), orderDetail.getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuCategoryName(), "", "");
                }
                if (orderDetail.getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuSubCategory() != null && orderDetail.getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuSubCategory().size() > 0) {
                    for (int j = 0; j < orderDetail.getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuSubCategory().size(); j++) {
                        subCatId = db.getMenuSubCategoryIfExit(orderDetail.getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuSubCategory().get(j).getMenuCategoryId());
                        if (subCatId == -1) {
                            subCatId = db.insertMenuSubCategory(id,
                                    orderDetail.getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuCategoryId(),
                                    orderDetail.getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuSubCategory().get(j).getMenuCategoryId(),
                                    orderDetail.getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuSubCategory().get(j).getMenuCategoryName());
                        }

                        for (int k = 0; k < orderDetail.getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuSubCategory().get(j).getMenuProducts().size(); k++) {
                            MenuProduct product = orderDetail.getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuSubCategory().get(j).getMenuProducts().get(k);
                            db.insertMenuProduct(id, subCatId, orderDetail.getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuSubCategory().get(j).getMenuCategoryId(),
                                    product.getMenuProductId(),
                                    product.getProductName(),
                                    product.getVegType(),
                                    product.getMenuProductPrice(),
                                    product.getUserappProductImage(),
                                    product.getEcomProductImage(),
                                    product.getProductOverallRating(),
                                    product.getQuantity(),
                                    gson.toJson(product.getMenuProductSize()),
                                    gson.toJson(product.getProductModifiers()),
                                    gson.toJson(product.getMealProducts()),
                                    product.getOriginalQuantity(),
                                    product.getOriginalAmount1(),
                                    product.getAmount()
                                    /*gson.toJson(menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(i).getUpsells())*/);

                        }
                    }

                }
                if (orderDetail.getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuProducts() != null && orderDetail.getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuProducts().size() > 0) {
                    List<MenuProduct> products = orderDetail.getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuProducts();
                    for (MenuProduct product : products) {

                        if (product.getMealProducts() != null && product.getMealProducts().size() > 0) {

                            db.insertMenuProduct(id, subCatId, orderDetail.getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuCategoryId(),
                                    product.getMenuProductId(),
                                    product.getProductName(),
                                    product.getVegType(),
                                    product.getMenuProductPrice(),
                                    "",
                                    "",
                                    "",
                                    product.getQuantity(),
                                    null,
                                    null,
                                    gson.toJson(product.getMealProducts()),
                                    product.getQuantity(),
                                    product.getOriginalAmount1(),
                                    product.getAmount());
                            /*for (MealProduct mealProduct : product.getMealProducts()) {
                                Log.e("ANAND >>>", mealProduct.toString());


                            }*/
                        } else {
                            db.insertMenuProduct(id, subCatId, orderDetail.getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuCategoryId(),
                                    product.getMenuProductId(),
                                    product.getProductName(),
                                    product.getVegType(),
                                    product.getMenuProductPrice(),
                                    product.getUserappProductImage(),
                                    product.getEcomProductImage(),
                                    product.getProductOverallRating(),
                                    product.getQuantity(),
                                    gson.toJson(product.getMenuProductSize()),
                                    gson.toJson(product.getProductModifiers()),
                                    gson.toJson(product.getMealProducts()),
                                    product.getOriginalQuantity(),
                                    product.getOriginalAmount1(),
                                    product.getAmount());
                        }
                    }
                }


            }


        }

       /* Intent i = new Intent(context, RestaurantDetailsActivity.class);
        i.putExtra("RESTAURANTID", restaurantId);
        i.putExtra("RESTAURANTNAME", restaurantName);
        sharePre.setString(sharePre.RESTUARANT_ID, restaurantId);
        sharePre.setString(sharePre.RESTUARANT_NAME, restaurantName);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        activity.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);*/

        Intent i = new Intent(context, DashboardActivity.class);
        i.putExtra("FROMMENU", "YES");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setAction("custom");
        sharePre.setString(sharePre.RESTUARANT_ID, restaurantId);
        sharePre.setString(sharePre.RESTUARANT_NAME, restaurantName);
        context.startActivity(i);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView restImage;
        CircleImageView restLogo;
        TextView restName, orderNo, orderDate, total, trackOrder, orderItemDetails, detailsArrow, orderStatus, reasonForCancel;
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
            this.reasonForCancel = itemView.findViewById(R.id.tv_reasonForCancel);
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
                sharePre.setString(sharePre.RESTUARANT_NAME, restuarantName);
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

                db.insertMenuProduct(id, Long.parseLong(menuProducts.get(j).getMenuSubCatId()), previousOrderDetailList.getOrderDetails().getData().getMenuCategoryCarts().get(i).getMenuCategoryId(),
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


    public void getOrderAgain(final String orderNumber, final int position, final String restaurantId, final String restaurantName) {
        dialog.show();
        RestaurantDetailsInterface apiInterface = ApiClient.getClient(context).create(RestaurantDetailsInterface.class);
        OrderAgainRequest request = new OrderAgainRequest();
        request.setOrderNumber(orderNumber);

        Call<OrderAgainResponse> call3 = apiInterface.getOrderAgain(request);
        call3.enqueue(new Callback<OrderAgainResponse>() {
            @Override
            public void onResponse(Call<OrderAgainResponse> call, Response<OrderAgainResponse> response) {
                try {
                    dialog.dismiss();
                    String status = response.body().getData().getRestaurantStatus();
                    if (response.body().getSuccess()) {

                        if (status != null && status.trim().length() > 0) {

                            if (status.equalsIgnoreCase("open") || status.equalsIgnoreCase("closed")) {


                                if (db.getCartData() == null) {
                                    //insertData(previousOrderDetailList.get(position));
                                } else {
                                    db.deleteCart();
                                    addOrderOnCart(position, restaurantId, restaurantName);
                                }

                            } else {
                                getOrderAgainDailog(status, response.body().getMessage());
                            }
                        } else {

                        }
                        dialog.dismiss();
                    } else {
                        getOrderAgainDailog(status, response.body().getMessage());
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                    dialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<OrderAgainResponse> call, Throwable t) {
                dialog.dismiss();
            }
        });


    }

    public void getOrderAgainDailog(String status, String message) {

        LayoutInflater factory = LayoutInflater.from(this.activity);
        final View mDialogView = factory.inflate(R.layout.addnote_success_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this.activity).create();
        alertDialog.setView(mDialogView);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        TextView tvMessage = mDialogView.findViewById(R.id.message);
        tvMessage.setText(message);

        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });

        alertDialog.show();

    }

    public void getOpenCloseDailog(String status, String message) {

        LayoutInflater factory = LayoutInflater.from(this.activity);
        final View mDialogView = factory.inflate(R.layout.addnote_success_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this.activity).create();
        alertDialog.setView(mDialogView);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        TextView tvMessage = mDialogView.findViewById(R.id.message);
        tvMessage.setText(message);

        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });

        alertDialog.show();

    }
}
