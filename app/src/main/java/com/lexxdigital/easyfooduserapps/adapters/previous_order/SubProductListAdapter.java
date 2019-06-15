package com.lexxdigital.easyfooduserapps.adapters.previous_order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.model.myorder.OrderDetails;

import static com.facebook.AccessTokenManager.TAG;

public class SubProductListAdapter extends RecyclerView.Adapter<SubProductListAdapter.MyViewHolder> {
    //List<Cart> cartList = new ArrayList<>();
    OrderDetails orderDetailsRes;
    Context context;
    Double deliveryFees = 0.0, discountAmt = 0.0, totalItemPrice = 0.0, subTotalAmount = 0.0;

    public SubProductListAdapter(OrderDetails orderDetailsRes, Context context) {
        this.orderDetailsRes = orderDetailsRes;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView subProductName, subProductPrice;
        LinearLayout lySubItems, lySubProdModf;
        // ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.subProductName = (TextView) itemView.findViewById(R.id.subprod_name);
            this.subProductPrice = (TextView) itemView.findViewById(R.id.sub_prod_price);
            this.lySubItems = itemView.findViewById(R.id.sub_product);
            //this.lySubProdModf=itemView.findViewById(R.id.sub_prod_modif);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sub_product_order_list, viewGroup, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);

        SubProductListAdapter.MyViewHolder myViewHolder = new SubProductListAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int listPosition) {
        //Cart cart=cartList.get(i);

        Log.e("SubProductListAdapter", "cartList.size():" + orderDetailsRes.getData().getMenuCategoryCarts().size());
        for (int i = 0; i < orderDetailsRes.getData().getMenuCategoryCarts().size(); i++) {
            for (int j = 0; j < orderDetailsRes.getData().getMenuCategoryCarts().get(i).getMenuProducts().size(); j++) {

                holder.subProductName.setText(orderDetailsRes.getData().getMenuCategoryCarts().get(i).getMenuProducts().get(j).getProductName());
                holder.subProductPrice.setText("£ " + orderDetailsRes.getData().getMenuCategoryCarts().get(i).getMenuProducts().get(j).getMenuProductPrice());

                for (int k = 0; k < orderDetailsRes.getData().getMenuCategoryCarts().get(i).getMenuProducts().get(j).getMenuProductSize().size(); k++) {
                    LinearLayout parent = new LinearLayout(context);

                    parent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    parent.setOrientation(LinearLayout.HORIZONTAL);
                    Log.e(TAG, "onBindViewHolder: " + orderDetailsRes.getData().getMenuCategoryCarts().get(i).getMenuProducts().get(j).getMenuProductSize().get(k).getProductSizeName());
                    TextView tv = new TextView(context); // Prepare textview object programmatically
                    tv.setText(orderDetailsRes.getData().getMenuCategoryCarts().get(i).getMenuProducts().get(j).getMenuProductSize().get(k).getProductSizeName());
                    // tv.setTextColor(context.getResources().getColor(R.color.orange));
                    tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

                    TextView tv2 = new TextView(context); // Prepare textview object programmatically
                    tv2.setText("£" + orderDetailsRes.getData().getMenuCategoryCarts().get(i).getMenuProducts().get(j).getMenuProductSize().get(k).getProductSizePrice());
                    tv2.setGravity(Gravity.END);
                    //  tv2.setTextColor(context.getResources().getColor(R.color.orange));
                    tv2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

                    // totalItemPrice = totalItemPrice + orderDetailsRes.getData().get(p).getMenuCategory().get(listPosition).getMenuProducts().get(i).getMenuProductSize().get(j).getSizeModifiers().get(k).getSizeModifierProducts().get(l).getTotalPrice();
                    parent.addView(tv);
                    parent.addView(tv2);
                    holder.lySubItems.addView(parent); // Add to your ViewGroup using this method
                    for (int l = 0; l < orderDetailsRes.getData().getMenuCategoryCarts().get(i).getMenuProducts().get(j).getMenuProductSize().get(k).getSizeModifiers().size(); l++) {
                        for (int m = 0; m < orderDetailsRes.getData().getMenuCategoryCarts().get(i).getMenuProducts().get(j).getMenuProductSize().get(k).getSizeModifiers().get(l).getModifier().size(); m++) {
                            LinearLayout parent2 = new LinearLayout(context);

                            parent2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            parent2.setOrientation(LinearLayout.HORIZONTAL);
                            TextView prodModifName = new TextView(context); // Prepare textview object programmatically
                            Log.e(TAG, "onBindViewHolder: modif namemmmmmmmmmmmmm: " + orderDetailsRes.getData().getMenuCategoryCarts().get(i).getMenuProducts().get(j).getMenuProductSize().get(k).getSizeModifiers().get(l).getModifier().size());
                            prodModifName.setText(orderDetailsRes.getData().getMenuCategoryCarts().get(i).getMenuProducts().get(j).getMenuProductSize().get(k).getSizeModifiers().get(l).getModifier().get(m).getProductName());
                            //  prodModifName.setTextColor(context.getResources().getColor(R.color.orange));
                            prodModifName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

                            TextView prodModifprice = new TextView(context); // Prepare textview object programmatically
                            prodModifprice.setText("£" + orderDetailsRes.getData().getMenuCategoryCarts().get(i).getMenuProducts().get(j).getMenuProductSize().get(k).getSizeModifiers().get(l).getModifier().get(m).getModifierProductPrice());
                            //  prodModifprice.setTextColor(context.getResources().getColor(R.color.orange));
                            prodModifprice.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
                            prodModifprice.setGravity(Gravity.END);
                            parent2.addView(prodModifName);
                            parent2.addView(prodModifprice);
                            holder.lySubItems.addView(parent2); // Add to your ViewGroup using this method

                        }
                    }
                }
            }
        }


        //myViewHolder.subProductPrice.setText("\u00a3"+cart.getProductPrice());
    }

    @Override
    public int getItemCount() {
        return orderDetailsRes.getData().getMenuCategoryCarts().size();
    }
}




/*
    MyVHolder extends RecyclerView.ViewHolder {
            ImageView restImage,restLogo;

            TextView restName,orderNo,orderDate,total,addReview,orderAgain;

// ImageView imageViewIcon;

public MyVHolder(View itemView) {
        super(itemView);

        this.restName = (TextView) itemView.findViewById(R.id.rest_name);
        this.orderNo = (TextView) itemView.findViewById(R.id.order_no);
        this.orderDate = (TextView) itemView.findViewById(R.id.order_time);
        this.total = (TextView) itemView.findViewById(R.id._total);
        this.addReview = (TextView) itemView.findViewById(R.id.add_review);
        this.orderAgain = (TextView) itemView.findViewById(R.id.order_again);
        this.restImage=itemView.findViewById(R.id.rest_image);
        this.restLogo=itemView.findViewById(R.id.rest_logo);
        }
        }

@NonNull
@Override
public SubProductListAdapter.MyVHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
        }

@Override
public void onBindViewHolder(@NonNull SubProductListAdapter.MyVHolder myViewHolder, int i) {

        }

@Override
public int getItemCount() {
        return 0;
        }*/
