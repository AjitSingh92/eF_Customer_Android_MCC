package com.lexxdigital.easyfooduserapps.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.model.card_list_response.Card;

import java.util.ArrayList;
import java.util.List;

public class SaveCardAdapter extends RecyclerView.Adapter<SaveCardAdapter.MyViewHolder> {
    Context mContext;
    String strBrand = "";
    ArrayList<String> check;
    List<Card> dataList;
    SaveCardAdapter.PositionInterface mPositionInterface;
    Card cardlist;

    public interface PositionInterface {
        void onClickPos(int pos, ArrayList<String> check, List<Card> dataList);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cardNofour, cardHolderName, expDate, tvBillingAddress;
        FrameLayout lyCard;
        ImageView rightImg, not_right_tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.rightImg = (ImageView) itemView.findViewById(R.id.rightImg);
            this.not_right_tv = (ImageView) itemView.findViewById(R.id.not_right_tv);
            this.lyCard = (FrameLayout) itemView.findViewById(R.id.fram_card);
            this.cardNofour = (TextView) itemView.findViewById(R.id.cardNo_foure);
            this.cardHolderName = (TextView) itemView.findViewById(R.id.holdernametv);
            this.expDate = (TextView) itemView.findViewById(R.id.exp_date);
            this.tvBillingAddress = itemView.findViewById(R.id.tv_billing_address);
        }
    }

    public SaveCardAdapter(Context mContext, PositionInterface mPositionInterface, ArrayList<String> check, List<Card> list) {

        this.mContext = mContext;
        this.mPositionInterface = mPositionInterface;
        this.check = check;
        this.dataList = list;
    }

    @Override
    public SaveCardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item_row, parent, false);

        SaveCardAdapter.MyViewHolder myViewHolder = new SaveCardAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final SaveCardAdapter.MyViewHolder holder, final int listPosition) {

        ImageView rightImg = holder.rightImg;
        ImageView not_right_tv = holder.not_right_tv;
        cardlist = dataList.get(listPosition);
        holder.cardNofour.setText(cardlist.getLast4CardNo());
        if (cardlist.getCustomerNameOnCard().equalsIgnoreCase(null)) {
            holder.cardHolderName.setText("XXXXX XXXXX");
        } else {
            holder.cardHolderName.setText(cardlist.getCustomerNameOnCard());
        }
        strBrand = cardlist.getBrand();

        setBrand(holder, strBrand);

        String strBillingAddress = "", address1 = "", address2 = "", city = "", postal = "", country = "";
        address1 = cardlist.getAddressLine1();
        address2 = cardlist.getAddressLine2();
        city = cardlist.getAddressCity();
        postal = cardlist.getAddressPostCode();
        strBillingAddress = address1 + " " + address2 + " " + city + " " + postal;
        if (!strBillingAddress.trim().equalsIgnoreCase("")) {
            holder.tvBillingAddress.setText(strBillingAddress);
            holder.tvBillingAddress.setVisibility(View.VISIBLE);
        } else {
            holder.tvBillingAddress.setVisibility(View.GONE);
        }

        if (check.get(listPosition).equals("1")) {
            rightImg.setVisibility(View.VISIBLE);
            not_right_tv.setVisibility(View.GONE);
        } else {
            not_right_tv.setVisibility(View.VISIBLE);
            rightImg.setVisibility(View.GONE);
        }
        /*TODO: default card selected here */

        holder.rightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPositionInterface.onClickPos(listPosition, check, dataList);
                holder.rightImg.setVisibility(View.VISIBLE);
                holder.not_right_tv.setVisibility(View.GONE);
            }
        });


        holder.not_right_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPositionInterface.onClickPos(listPosition, check, dataList);
                holder.not_right_tv.setVisibility(View.GONE);
                holder.rightImg.setVisibility(View.VISIBLE);
            }
        });

        holder.lyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.rightImg.getVisibility() == View.VISIBLE) {
                    mPositionInterface.onClickPos(listPosition, check, dataList);
                    holder.rightImg.setVisibility(View.VISIBLE);
                    holder.not_right_tv.setVisibility(View.GONE);
                } else {
                    mPositionInterface.onClickPos(listPosition, check, dataList);
                    holder.not_right_tv.setVisibility(View.GONE);
                    holder.rightImg.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void setBrand(final SaveCardAdapter.MyViewHolder holder, String brand) {
        if (brand.trim().equalsIgnoreCase("Visa")) {
            holder.lyCard.setBackgroundResource(R.drawable.bg_visa_card);
        } else if (brand.trim().equalsIgnoreCase("MasterCard")) {
            holder.lyCard.setBackgroundResource(R.drawable.bg_master_card);
        } else if (brand.trim().equalsIgnoreCase("American Express")) {
            holder.lyCard.setBackgroundResource(R.drawable.bg_americanexp_card);
        } else if (brand.trim().equalsIgnoreCase("Discover")) {
            holder.lyCard.setBackgroundResource(R.drawable.db_discover_card);
        } else if (brand.trim().equalsIgnoreCase("Diners Club")) {
            holder.lyCard.setBackgroundResource(R.drawable.bg_dinner_club_card);
        } else if (brand.trim().equalsIgnoreCase("JCB")) {
            holder.lyCard.setBackgroundResource(R.drawable.bg_jcb_card);
        } else if (brand.trim().equalsIgnoreCase("UnionPay")) {
            holder.lyCard.setBackgroundResource(R.drawable.bg_union_pay_card);
        } else if (brand.trim().equalsIgnoreCase("Western Union")) {
            holder.lyCard.setBackgroundResource(R.drawable.bg_western_union_card);
        } else {
            holder.lyCard.setBackgroundResource(R.drawable.bg_card);
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
