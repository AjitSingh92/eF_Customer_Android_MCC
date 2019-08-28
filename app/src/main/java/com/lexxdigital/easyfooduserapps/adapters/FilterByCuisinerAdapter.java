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
import com.lexxdigital.easyfooduserapps.model.filter_response.Cuisine;

import java.util.ArrayList;
import java.util.List;

import static com.lexxdigital.easyfooduserapps.fragments.DealsFragment.isCheck;

public class FilterByCuisinerAdapter extends RecyclerView.Adapter<FilterByCuisinerAdapter.MyViewHolder> {

    // private ArrayList<Arraylist> dataSet;


    DealCardAdapter mDealCardAdapter;
    Context mContext;
    TextView popupTotalPrice;
    // ArrayList<Boolean> isCheck = new ArrayList<>();
    ArrayList<String> check;
    ArrayList<String> checkValue;
    List<Cuisine> cuisineList;
    PositionInterface mPositionInterface2;

    public FilterByCuisinerAdapter(Context mContext, ArrayList<String> check, List<Cuisine> cuisineList, FilterByCuisinerAdapter.PositionInterface mPositionInterface2) {
        this.cuisineList = cuisineList;
        this.mContext = mContext;
        this.check = check;
        this.mPositionInterface2 = mPositionInterface2;

        if (isCheck.size() < this.check.size()) {
            for (int i = 0; i < check.size(); i++) {
                if (i == 0) {
                    isCheck.add(true);
                } else
                    isCheck.add(false);
            }
        }
    }

    public interface PositionInterface {
        void onClickPosCoisine(int pos, ArrayList<String> check, List<Cuisine> cuisineList);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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

            rightImg.setOnClickListener(this);
            not_right_tv.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rightImg_1:
                    int isCheckedCount = 0;
                    for (Boolean isChecked : isCheck) {
                        if (isChecked) {
                            isCheckedCount++;
                        }
                    }
                    if (isCheckedCount > 1) {
                        isCheck.set(getLayoutPosition(), false);
                        check.set(getLayoutPosition(), "0");
                        rightImg.setVisibility(View.GONE);
                        not_right_tv.setVisibility(View.VISIBLE);
                        mPositionInterface2.onClickPosCoisine(getLayoutPosition(), check, cuisineList);
                    }
                    break;

                case R.id.not_right_tv_1:
                    if (getLayoutPosition() == 0) {
                        for (int i = 1; i < cuisineList.size(); i++) {
                            //check.set(i,"0");
                            // check.set(i,"0");
                            isCheck.set(i, false);
                        }

                    } else {
                        isCheck.set(0, false);
                        isCheck.set(getLayoutPosition(), true);
                        check.set(getLayoutPosition(), "1");
//                        not_right_tv.setVisibility(View.GONE);
//                        rightImg.setVisibility(View.VISIBLE);


                    }
                    mPositionInterface2.onClickPosCoisine(getLayoutPosition(), check, cuisineList);
                    notifyDataSetChanged();
                    break;
            }
        }
    }


    @Override
    public FilterByCuisinerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_filter_sort_by, parent, false);

        FilterByCuisinerAdapter.MyViewHolder myViewHolder = new FilterByCuisinerAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final FilterByCuisinerAdapter.MyViewHolder holder, int position) {

        Cuisine sortBy = cuisineList.get(position);
        ImageView rightImg = holder.rightImg;
        ImageView not_right_tv = holder.not_right_tv;
        holder.sizeName.setText(sortBy.getLabel());
        Log.e("check status", "position:" + position + "," + isCheck.get(position));
        if (isCheck.get(position)) {
            rightImg.setVisibility(View.VISIBLE);
            not_right_tv.setVisibility(View.GONE);

        } else {
            not_right_tv.setVisibility(View.VISIBLE);
            rightImg.setVisibility(View.GONE);
        }

        // mPositionInterface2.onClickPosCoisine(listPosition,check,cuisineList);
        /*for (int i = 0; i < cuisineList.size(); i++) {
            if (check.get(listPosition).equals("1")) {
                rightImg.setVisibility(View.VISIBLE);
                not_right_tv.setVisibility(View.GONE);

            } else {
                not_right_tv.setVisibility(View.VISIBLE);
                rightImg.setVisibility(View.GONE);
            }

        }*/
        /*holder.rightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               *//* if (listPosition != 0) {
                    check.set(0, "0");
                    isCheck.set(0, false);
                    notifyDataSetChanged();
                    *//**//*holder.rightImg.setVisibility(View.GONE);
                    holder.not_right_tv.setVisibility(View.VISIBLE);*//**//*
                }*//*
                // check.set(listPosition,"0");
                //  mPositionInterface2.onClickPosCoisine(listPosition,check,cuisineList);
                //check.set(listPosition,"0");
                int isCheckedCount = 0;
                for (Boolean isChecked : isCheck) {
                    if (isChecked) {
                        isCheckedCount++;
                    }
                }
                if (isCheckedCount > 1) {
                    isCheck.set(listPosition, false);
                    holder.rightImg.setVisibility(View.GONE);
                    holder.not_right_tv.setVisibility(View.VISIBLE);
                    mPositionInterface2.onClickPosCoisine(listPosition, check, cuisineList);
                }
            }
        });

        holder.not_right_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listPosition == 0) {
                    for (int i = 1; i < cuisineList.size(); i++) {
                        //check.set(i,"0");
                        // check.set(i,"0");
                        isCheck.set(i, false);
                    }

                } else {
                    isCheck.set(0, false);
                    isCheck.set(listPosition, true);
                    check.set(listPosition, "1");
                    holder.not_right_tv.setVisibility(View.GONE);
                    holder.rightImg.setVisibility(View.VISIBLE);
                    mPositionInterface2.onClickPosCoisine(listPosition, check, cuisineList);

                }
                notifyDataSetChanged();

                    *//*holder.rightImg.setVisibility(View.GONE);
                    holder.not_right_tv.setVisibility(View.VISIBLE);*//*


                //   check.set(listPosition,"1");
                *//*isCheck.set(listPosition, true);
                //   mPositionInterface2.onClickPosCoisine(listPosition,check,cuisineList);
                holder.not_right_tv.setVisibility(View.GONE);
                holder.rightImg.setVisibility(View.VISIBLE);
                mPositionInterface2.onClickPosCoisine(listPosition, check, cuisineList);*//*

            }
        });*/


    }


    public String getCuisine() {
        String selectedCuisines = "";
        for (int i = 0; i < check.size(); i++) {
            if (isCheck.get(i)) {
                if (selectedCuisines.equalsIgnoreCase("")) {
                    selectedCuisines = cuisineList.get(i).getValue();
                } else {
                    selectedCuisines = selectedCuisines + "," + cuisineList.get(i).getValue();
                    ;
                }
            }
        }
        return selectedCuisines;
    }

    public ArrayList getCuisineArray() {
        ArrayList<String> cuisineArray = new ArrayList<>();
        String selectedCuisines = "";
        //cuisineArray.clear();
        for (int i = 0; i < cuisineList.size(); i++) {
            if (isCheck.get(i)) {
                Log.e("size of :", cuisineArray.size() + "cuisineList " + cuisineList.size() + ", check:" + check.size());
               /* if (selectedCuisines.equalsIgnoreCase("")) {
                    Log.e("", "getCuisineArray: "+cuisineArray.size() );
                    cuisineArray.add(cuisineList.get(i).getValue());
                    // selectedCuisines = cuisineList.get(i).getValue();
                } else {*/
                // selectedCuisines = selectedCuisines+","+cuisineList.get(i).getValue();
                cuisineArray.add(cuisineList.get(i).getValue());
                //}
            }

        }
        return cuisineArray;
    }

    @Override
    public int getItemCount() {
        return cuisineList.size();
    }
}