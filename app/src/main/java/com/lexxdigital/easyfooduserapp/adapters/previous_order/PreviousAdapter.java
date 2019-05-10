package com.lexxdigital.easyfooduserapp.adapters.previous_order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.model.myorder.OrderDetails;
import com.lexxdigital.easyfooduserapp.model.myorder.PreviousOrderDetail;
import com.lexxdigital.easyfooduserapp.model.myorder.PreviousOrderResponse;
import com.lexxdigital.easyfooduserapp.order_details_activity.OrderDetailActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.AccessTokenManager.TAG;


public class PreviousAdapter extends RecyclerView.Adapter<PreviousAdapter.MyViewHolder> {

    Context context;
    PreviousOrderResponse previousOrder;
    OrderDetails orderDetailsRes;
    //List<DataList> dataLists=new ArrayList<DataList>();
    //List<Cart> cartList=new ArrayList<>();
    private List<PreviousOrderDetail> previousOrderDetailList;

    public PreviousAdapter(List<PreviousOrderDetail> previousOrderDetailList, Context context) {
        this.previousOrderDetailList=previousOrderDetailList;
        this.context = context;
       // this.previousOrder = previousOrder;
    }

    @Override
    public PreviousAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.previous_order_item, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);

        PreviousAdapter.MyViewHolder myViewHolder = new PreviousAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        Log.e("listPosition", "onBindViewHolder: "+listPosition );
       final PreviousOrderDetail dataList= previousOrderDetailList.get(listPosition);
        Log.e(TAG, "onBindViewHolder:name resrto "+ dataList.getRestaurantName());
        holder.restName.setText(dataList.getRestaurantName());
        holder.orderNo.setText("Order No."+dataList.getOrderNum());
        holder.orderDate.setText(dataList.getOrderDateTime());

       // Log.d("e", "onBindViewHolder: "+dataList.getTotal());

        holder.total.setText("\u00a3 "+String.valueOf(dataList.getOrderTotal()));
        Log.e(TAG, "onBindViewHolder: logoooooooooooo"+dataList.getRestaurantLogo() );
        Glide.with(context).load(dataList.getRestaurantImage()).centerCrop().into(holder.restImage);
        Glide.with(context).load(dataList.getRestaurantLogo()).centerCrop().into(holder.restLogo);
        orderDetailsRes=dataList.getOrderDetails();

         //cartList=dataList.getCart();
        holder.initView();
        //Log.d("cartlist", "onBindViewHolder:cartList"+listPosition+":"+cartList.size());
        //Toast.makeText(context,"cartList: "+cartList.size(),Toast.LENGTH_LONG).show();
        holder.addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.orderAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, OrderDetailActivity.class);
                intent.putExtra("order_no",dataList.getRestaurantId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        // Picasso.with(context).load(dataList.getRestaurantImage()).into(holder.restImage);

         }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView restImage;
        CircleImageView restLogo;
        TextView restName,orderNo,orderDate,total,addReview,orderAgain;
        RecyclerView subProductRecycler;
        SubProductListAdapter subProductListAdapter;
        // ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.restName = (TextView) itemView.findViewById(R.id.rest_name);
            this.orderNo = (TextView) itemView.findViewById(R.id.order_no);
            this.orderDate = (TextView) itemView.findViewById(R.id.order_time);
            this.total = (TextView) itemView.findViewById(R.id._total);
            this.addReview = (TextView) itemView.findViewById(R.id.add_review);
            this.orderAgain = (TextView) itemView.findViewById(R.id.order_again);
            this.restImage=itemView.findViewById(R.id.rest_image);
            this.restLogo=itemView.findViewById(R.id.rest_logo);
            this.subProductRecycler=itemView.findViewById(R.id.sub_prodc_recyclist);
        }

        private void initView() {
            subProductListAdapter = new SubProductListAdapter(orderDetailsRes, context);
            @SuppressLint("WrongConstant")
            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            subProductRecycler.setLayoutManager(horizontalLayoutManagaer);
            subProductRecycler.setAdapter(subProductListAdapter);
        }

    }
    @Override
    public int getItemCount() {
        Log.e(TAG, "getItemCount:list size: "+previousOrderDetailList.size() );
        // Log.e("count", "getItemCount: previousOrderDetailList.size()"+ previousOrder.getData().getPreviousOrderDetails().size() );
        return previousOrderDetailList.size();
    }
}
