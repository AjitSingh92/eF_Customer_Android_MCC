package com.lexxdigital.easyfooduserapps.adapters.menu_adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.restaurant_details.RestaurantDetailsActivity;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.SpecialOffer;

import java.util.ArrayList;
import java.util.List;

public class SpecialOffersMenuAdapter extends RecyclerView.Adapter<SpecialOffersMenuAdapter.CategoryViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private List<SpecialOffer> mItem;
    private Boolean hideDetail = false;
    ItemClickListener menuItemClickListener;
    private boolean isClosed;

    int parentPosition;

    public SpecialOffersMenuAdapter(Context context, int parentPosition, ItemClickListener menuItemClickListener, boolean isClosed) {
        this.context = context;
        this.menuItemClickListener = menuItemClickListener;
        inflater = LayoutInflater.from(context);
        this.mItem = new ArrayList<>();
        this.isClosed = isClosed;

    }

    public void setHideDetail(Boolean hideDetail) {
        this.hideDetail = hideDetail;
    }

    public void clearData() {
        this.mItem.clear();
        notifyDataSetChanged();
    }

    public void addItem(List<SpecialOffer> mItem) {
        this.mItem.addAll(mItem);
        notifyItemChanged(this.mItem.size());
    }

    public void addItem(SpecialOffer mItem) {
        this.mItem.add(mItem);
        notifyItemChanged(this.mItem.size());
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {
        return new CategoryViewHolder(inflater.inflate(R.layout.offer_item_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder pdqListingViewHolder, int position) {
        pdqListingViewHolder.bindData(position);

    }


    @Override
    public int getItemCount() {
        return mItem.size();
    }


    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView txt_menu_title, txt_price, txt_items_detail, item_count,txt_Count;
        private final LinearLayout clickCount, item_remove, item_add;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            //  txtProductCount = (TextView) itemView.findViewById(R.id.txt_product_count);
            txt_Count = itemView.findViewById(R.id.txt_count);
            txt_menu_title = itemView.findViewById(R.id.txt_menu_title);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_items_detail = itemView.findViewById(R.id.txt_items_detail);
            clickCount = itemView.findViewById(R.id.clickCount);
            item_remove = itemView.findViewById(R.id.item_remove);
            item_add = itemView.findViewById(R.id.item_add);
            item_count = itemView.findViewById(R.id.item_count);

            item_add.setOnClickListener(this);
            item_remove.setOnClickListener(this);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isClosed) {
                        restaurantClosedDialog();

                    } else {
                       /// txt_Count.setVisibility(View.VISIBLE);
                        if (clickCount.getVisibility() == View.GONE) {
                            clickCount.setVisibility(View.VISIBLE);
                           // txt_Count.setVisibility(View.VISIBLE);
                            item_count.setText("1");
                           // txt_Count.setText("1");


                            if (menuItemClickListener != null) {
                                menuItemClickListener.OnSpecialOfferClick(parentPosition, getLayoutPosition(), item_count, Integer.parseInt(item_count.getText().toString()), 2, mItem.get(getLayoutPosition()));
                            }
                        }
                    }
                }
            });
        }

        private void bindData(final int position) {
            txt_menu_title.setText(mItem.get(position).getOfferTitle());
            txt_price.setText("£" + mItem.get(position).getOfferPrice());


            if (hideDetail) {
                txt_items_detail.setVisibility(View.GONE);
            } else {
                txt_items_detail.setVisibility(View.VISIBLE);
                txt_items_detail.setText(mItem.get(position).getOfferDetails());
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_add:
                    if (isClosed) {
                        restaurantClosedDialog();
                    } else {
                        item_count.setText(String.valueOf((Integer.parseInt(item_count.getText().toString()) + 1)));
                        //txt_Count.setText(String.valueOf((Integer.parseInt(txt_Count.getText().toString()) + 1)));
                        //   txtProductCount.setText(String.valueOf((Integer.parseInt(item_count.getText().toString()) + 1)));
                        if (menuItemClickListener != null) {
                            menuItemClickListener.OnSpecialOfferClick(parentPosition, getLayoutPosition(), item_count, Integer.parseInt(item_count.getText().toString()), 2, mItem.get(getLayoutPosition()));
                        }
                    }
                    break;
                case R.id.item_remove:
                    if (isClosed) {
                        restaurantClosedDialog();
                    } else {

                        item_count.setText(String.valueOf((Integer.parseInt(item_count.getText().toString()) - 1)));
                       // txt_Count.setText(String.valueOf((Integer.parseInt(txt_Count.getText().toString()) - 1)));
                        if (item_count.getText().equals("0")) {
                            clickCount.setVisibility(View.GONE);

                            //  txtProductCount.setVisibility(View.GONE);
                            //txt_Count.setVisibility(View.GONE);

                        }
                        if (menuItemClickListener != null) {
                            menuItemClickListener.OnSpecialOfferClick(parentPosition, getLayoutPosition(), item_count, Integer.parseInt(item_count.getText().toString()), 1, mItem.get(getLayoutPosition()));
                        }
                    }
                    break;

                case R.id.item:
                    if (isClosed) {
                        restaurantClosedDialog();
                    } else {
                        txt_Count.setVisibility(View.VISIBLE);
                        if (clickCount.getVisibility() == View.GONE) {
                            clickCount.setVisibility(View.VISIBLE);
                            //txt_Count.setVisibility(View.VISIBLE);
                            item_count.setText("1");
                            //txt_Count.setText("1");
                            if (menuItemClickListener != null) {
                                menuItemClickListener.OnSpecialOfferClick(parentPosition, getLayoutPosition(), item_count, Integer.parseInt(item_count.getText().toString()), 2, mItem.get(getLayoutPosition()));
                            }
                        }
                    }
                    break;
               /* default:
                    if (isClosed) {
                        restaurantClosedDialog();
                    } else {
                        if (clickCount.getVisibility() == View.GONE) {
                            clickCount.setVisibility(View.VISIBLE);
                            item_count.setText("1");
                            if (menuItemClickListener != null) {
                                menuItemClickListener.OnSpecialOfferClick(parentPosition, getLayoutPosition(), item_count, Integer.parseInt(item_count.getText().toString()), 2, mItem.get(getLayoutPosition()));
                            }
                        }
                    }
                    break;*/
            }

        }
    }


    public void restaurantClosedDialog() {
        LayoutInflater factory = LayoutInflater.from(RestaurantDetailsActivity.restaurantDetailsActivity);
        final View mDialogVieww = factory.inflate(R.layout.layout_closed_dialog, null);
        final AlertDialog alertClodseDialog = new AlertDialog.Builder(RestaurantDetailsActivity.restaurantDetailsActivity).create();
        alertClodseDialog.setView(mDialogVieww);
        alertClodseDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //   final TextView ok_tv = (TextView)  mDialogView.findViewById(R.id.okTv);

        mDialogVieww.findViewById(R.id.tv_btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                alertClodseDialog.dismiss();
            }
        });


        alertClodseDialog.show();
    }
}