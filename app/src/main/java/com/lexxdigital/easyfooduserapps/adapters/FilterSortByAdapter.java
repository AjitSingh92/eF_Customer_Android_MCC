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
import com.lexxdigital.easyfooduserapps.model.filter_response.SortBy;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.AccessTokenManager.TAG;

public class FilterSortByAdapter extends RecyclerView.Adapter<FilterSortByAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<String> check;
    List<SortBy> sortByList;
    FilterSortByAdapter.PositionSortInterface positionSortInterface;

    int totalSelectedPosition = -1;

    public FilterSortByAdapter(Context mContext, List<SortBy> sortByList, ArrayList<String> check, FilterSortByAdapter.PositionSortInterface positionSortInterface) {
        this.sortByList = sortByList;
        this.mContext = mContext;
        this.check = check;
        this.positionSortInterface = positionSortInterface;
    }

    public interface PositionSortInterface {
        void onClickSortBy(int pos, ArrayList<String> check, List<SortBy> sortByList);
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
    public FilterSortByAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_filter_sort_by, parent, false);

        FilterSortByAdapter.MyViewHolder myViewHolder = new FilterSortByAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final FilterSortByAdapter.MyViewHolder holder, int position) {
        final int listPosition = position;
        SortBy sortBy = sortByList.get(listPosition);
        ImageView rightImg = holder.rightImg;
        ImageView not_right_tv = holder.not_right_tv;


        holder.sizeName.setText(sortBy.getLabel());

        if (check.get(listPosition).equals("1")) {
            totalSelectedPosition = listPosition;
            rightImg.setVisibility(View.VISIBLE);
            not_right_tv.setVisibility(View.GONE);
            positionSortInterface.onClickSortBy(listPosition, check, sortByList);
        } else {
            not_right_tv.setVisibility(View.VISIBLE);
            rightImg.setVisibility(View.GONE);
        }

        holder.rightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalSelectedPosition != listPosition) {
                    totalSelectedPosition = listPosition;
                    check.set(listPosition, "0");
                    positionSortInterface.onClickSortBy(listPosition, check, sortByList);
                    holder.rightImg.setVisibility(View.GONE);
                    holder.not_right_tv.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.not_right_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalSelectedPosition != listPosition) {
                    totalSelectedPosition = listPosition;
                    check.set(listPosition, "1");
                    positionSortInterface.onClickSortBy(listPosition, check, sortByList);
                    holder.not_right_tv.setVisibility(View.GONE);
                    holder.rightImg.setVisibility(View.VISIBLE);
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return sortByList.size();
    }
}