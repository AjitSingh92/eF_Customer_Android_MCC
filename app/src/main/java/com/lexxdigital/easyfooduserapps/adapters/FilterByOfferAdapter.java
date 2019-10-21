package com.lexxdigital.easyfooduserapps.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.model.filter_response.Offer;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.AccessTokenManager.TAG;

public class FilterByOfferAdapter extends RecyclerView.Adapter<FilterByOfferAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<String> check;
    List<Offer> offerList;
    FilterByOfferAdapter.PositionByOfferInterface mPositionInterface2;

    public FilterByOfferAdapter(Context mContext, ArrayList<String> check, List<Offer> offerList, FilterByOfferAdapter.PositionByOfferInterface mPositionInterface2) {
        this.offerList = offerList;
        this.mContext = mContext;
        this.check = check;
        this.mPositionInterface2 = mPositionInterface2;
    }

    public interface PositionByOfferInterface {
        void onClickPosOffer(int pos, ArrayList<String> check, List<Offer> offerList);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView sizeName;
        ImageView rightImg, not_right_tv;
        LinearLayout lySizeItem;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.rightImg = (ImageView) itemView.findViewById(R.id.rightImg_1);
            this.not_right_tv = (ImageView) itemView.findViewById(R.id.not_right_tv_1);
            this.sizeName = (TextView) itemView.findViewById(R.id.name);
            this.lySizeItem = (LinearLayout) itemView.findViewById(R.id.ly_size_item);
        }
    }


    @Override
    public FilterByOfferAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_filter_sort_by, parent, false);
        FilterByOfferAdapter.MyViewHolder myViewHolder = new FilterByOfferAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final FilterByOfferAdapter.MyViewHolder holder, final int listPosition) {
        Offer sortBy = offerList.get(listPosition);
        ImageView rightImg = holder.rightImg;
        ImageView not_right_tv = holder.not_right_tv;
        holder.sizeName.setText(sortBy.getLabel());
        if (check.get(listPosition).equals("1")) {
            rightImg.setVisibility(View.VISIBLE);
            not_right_tv.setVisibility(View.GONE);
            mPositionInterface2.onClickPosOffer(listPosition, check, offerList);
        } else {
            not_right_tv.setVisibility(View.VISIBLE);
            rightImg.setVisibility(View.GONE);
        }

        holder.rightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check.set(listPosition, "0");
                mPositionInterface2.onClickPosOffer(listPosition, check, offerList);
                holder.rightImg.setVisibility(View.GONE);
                holder.not_right_tv.setVisibility(View.VISIBLE);
            }
        });

        holder.not_right_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check.set(listPosition, "1");
                mPositionInterface2.onClickPosOffer(listPosition, check, offerList);
                holder.not_right_tv.setVisibility(View.GONE);
                holder.rightImg.setVisibility(View.VISIBLE);
            }
        });


    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }
}