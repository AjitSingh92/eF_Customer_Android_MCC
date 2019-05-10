package com.lexxdigital.easyfooduserapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.model.filter_response.Offer;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.AccessTokenManager.TAG;

public class FilterByOfferAdapter extends RecyclerView.Adapter<FilterByOfferAdapter.MyViewHolder> {

    // private ArrayList<Arraylist> dataSet;


    DealCardAdapter mDealCardAdapter;
    Context mContext;
    TextView popupTotalPrice;
    ArrayList<String> check;
    ArrayList<String> allReadyCheck;
    List<Offer> offerList;
    FilterByOfferAdapter.PositionByOfferInterface mPositionInterface2;

    public FilterByOfferAdapter(Context mContext, ArrayList<String> check, List<Offer> offerList, FilterByOfferAdapter.PositionByOfferInterface mPositionInterface2) {
        this.offerList = offerList;
        this.mContext = mContext;
        this.check=check;
        this.mPositionInterface2=mPositionInterface2;
    }

    public interface PositionByOfferInterface {
        void onClickPosOffer(int pos, ArrayList<String> check, List<Offer> offerList);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView sizeName;
        ImageView rightImg, not_right_tv;
        LinearLayout rightCount, lyAddRemove, lySizeItem;


        // ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.rightImg = (ImageView) itemView.findViewById(R.id.rightImg_1);
            this.not_right_tv = (ImageView) itemView.findViewById(R.id.not_right_tv_1);
            this.sizeName = (TextView) itemView.findViewById(R.id.name);
            // this.price = (TextView) itemView.findViewById(R.id.rs_tv);

            this.lySizeItem = (LinearLayout) itemView.findViewById(R.id.ly_size_item);
//                this.dealIdRl = (RelativeLayout) itemView.findViewById(R.id.dealIdRl);

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
        if(check.get(listPosition).equals("1")){
            rightImg.setVisibility(View.VISIBLE);
            not_right_tv.setVisibility(View.GONE);
            mPositionInterface2.onClickPosOffer(listPosition,check,offerList);
            //  holder.rightCount.setVisibility(View.VISIBLE);
//            holder.lyAddRemove.setVisibility(View.VISIBLE);

        }else{
            not_right_tv.setVisibility(View.VISIBLE);
            //       holder.rightCount.setVisibility(View.INVISIBLE);
            rightImg.setVisibility(View.GONE);
//            holder.lyAddRemove.setVisibility(View.GONE);

        }

//        holder.lySizeItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(holder.rightImg.getVisibility() == View.VISIBLE){
//                    check.set(listPosition,"0");
//                    //allReadyCheck.set(listPosition,"0");
//                    //  allReadyCheck.set(0,"0");
//                    mPositionInterface2.onClickPos2(listPosition,check);
//
//                    holder.rightImg.setVisibility(View.VISIBLE);
//                    holder.not_right_tv.setVisibility(View.GONE);
//                }else if(holder.not_right_tv.getVisibility() == View.VISIBLE){
//                    check.set(listPosition,"0");
//                    //allReadyCheck.set(listPosition,"0");
//                    //  allReadyCheck.set(0,"0");
//                    mPositionInterface2.onClickPos2(listPosition,check);
//
//                    holder.rightImg.setVisibility(View.VISIBLE);
//                    holder.not_right_tv.setVisibility(View.GONE);
//                }
//
//            }//
//        });
        holder.rightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("", "onClickSortBy:1111111111111111111  right img" );

                check.set(listPosition,"0");
                //allReadyCheck.set(listPosition,"0");
                //  allReadyCheck.set(0,"0");
                mPositionInterface2.onClickPosOffer(listPosition,check,offerList);

                holder.rightImg.setVisibility(View.GONE);
                holder.not_right_tv.setVisibility(View.VISIBLE);
            }
        });

        holder.not_right_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("", "onClickSortBy:000000 not right tv " );

                check.set(listPosition,"1");
                //  allReadyCheck.set(0,"1");
                mPositionInterface2.onClickPosOffer(listPosition,check,offerList);
                holder.not_right_tv.setVisibility(View.GONE);

                holder.rightImg.setVisibility(View.VISIBLE);
            }
        });


    }

    @Override
    public int getItemCount() {
        Log.e(TAG, "getItemCount: offerList.size() "+offerList.size() );
        return offerList.size();
    }
}