package com.lexxdigital.easyfooduserapps.select_address;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.model.AddressList;

import java.util.ArrayList;
import java.util.List;

public class SelectAddressAdapter extends RecyclerView.Adapter<SelectAddressAdapter.MyViewHolder> {


    Context mContext;
    private List<AddressList> addressList = new ArrayList<AddressList>();
    ArrayList<String> check;
    ArrayList<String> allReadyCheck;

    SelectAddressAdapter.PositionInterface mPositionInterface;

    public  interface PositionInterface{
        void onClickPos(int pos, ArrayList<String> check);
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView edit_details_tv, txtAddreddType, txtAddress;
        RelativeLayout holeItemClick;
        ImageView rightImg,not_right_tv,iconAddressList;
        public MyViewHolder(View itemView) {
            super(itemView);

            this.rightImg = (ImageView) itemView.findViewById(R.id.rightImg);
            this.not_right_tv = (ImageView) itemView.findViewById(R.id.not_right_tv);
            this.holeItemClick = (RelativeLayout) itemView.findViewById(R.id.holeItemClick);
            this.edit_details_tv = (TextView) itemView.findViewById(R.id.edit_details_tv);
            this.txtAddreddType = (TextView) itemView.findViewById(R.id.addressType);
            this.txtAddress = (TextView) itemView.findViewById(R.id.address);
            this.iconAddressList = itemView.findViewById(R.id.icon_home_address_list);
        }
    }

    public SelectAddressAdapter(Context mContext, PositionInterface mPositionInterface, ArrayList<String> check, List<AddressList> address) {

        this.mContext= mContext;
        this.mPositionInterface= mPositionInterface;
        this.check= check;

    }

    @Override
    public SelectAddressAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_select_address, parent, false);

        SelectAddressAdapter.MyViewHolder myViewHolder = new SelectAddressAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final SelectAddressAdapter.MyViewHolder holder, final int listPosition) {

        ImageView rightImg = holder.rightImg;
        ImageView not_right_tv = holder.not_right_tv;


        final AddressList add = addressList.get(listPosition);
        String addressType = add.getAddressType();
        if (addressType.equals("home")||addressType.equals("Home")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.iconAddressList.setImageDrawable(mContext.getDrawable(R.drawable.home_5));
            }
            holder.txtAddreddType.setText(addressType);


        }
        else if(addressType.equals("work")||addressType.equals("Work")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.iconAddressList.setImageDrawable(mContext.getDrawable(R.drawable.ic_work_briefcase));
            }
            holder.txtAddreddType.setText(addressType);
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.iconAddressList.setImageDrawable(mContext.getDrawable(R.drawable.other_location));
            }
            holder.txtAddreddType.setText(addressType);

        }
        holder.txtAddress.setText(add.getAddressOne() + " " + add.getAddressTwo()+","+add.getCity()+"\n"+add.getPostCode());
        RelativeLayout holeItemClick = holder.holeItemClick;
        TextView edit_details_tv = holder.edit_details_tv;

        if(check.get(listPosition).equals("1")){
            rightImg.setVisibility(View.VISIBLE);
            not_right_tv.setVisibility(View.GONE);
        }else{
            not_right_tv.setVisibility(View.VISIBLE);

            rightImg.setVisibility(View.GONE);
        }
        holder.rightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPositionInterface.onClickPos(listPosition,check);
                holder.rightImg.setVisibility(View.VISIBLE);
                holder.not_right_tv.setVisibility(View.GONE);
            }
        });


        holder.not_right_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPositionInterface.onClickPos(listPosition,check);
                holder.not_right_tv.setVisibility(View.GONE);

                holder.rightImg.setVisibility(View.VISIBLE);
            }
        });

        holder.holeItemClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.rightImg.getVisibility() == View.VISIBLE){
                    mPositionInterface.onClickPos(listPosition,check);

                    holder.rightImg.setVisibility(View.VISIBLE);
                    holder.not_right_tv.setVisibility(View.GONE);
                }else{
                    mPositionInterface.onClickPos(listPosition,check);
                    holder.not_right_tv.setVisibility(View.GONE);

                    holder.rightImg.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }}