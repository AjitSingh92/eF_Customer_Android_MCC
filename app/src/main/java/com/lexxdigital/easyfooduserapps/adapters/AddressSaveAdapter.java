package com.lexxdigital.easyfooduserapps.adapters;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.model.AddressList;
import com.lexxdigital.easyfooduserapps.utility.SharedPreferencesClass;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.GraphRequest.TAG;


public class AddressSaveAdapter extends RecyclerView.Adapter<AddressSaveAdapter.MyViewHolder> {

    // private ArrayList<Arraylist> dataSet;

    DealCardAdapter mDealCardAdapter;
    Context mContext;
    private List<AddressList> addressList = new ArrayList<AddressList>();
    AddressSaveAdapter.PositionInterface mPositionInterface;
    AddressSaveAdapter.DeletePositionInterface mDeletePositionInterface;
    SharedPreferencesClass sharePre;

    public interface PositionInterface {
        void onClickPos(int pos);
    }

    public interface DeletePositionInterface {
        void onClickDel(int pos);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView edit_details_tv, txtAddreddType, txtAddress, tvDefault;
        ImageView cross_tv, iconAddressList;
        RelativeLayout holeItemClick;


        public MyViewHolder(View itemView) {
            super(itemView);

            this.cross_tv = (ImageView) itemView.findViewById(R.id.cross_tv);
            this.holeItemClick = (RelativeLayout) itemView.findViewById(R.id.holeItemClick);
            this.edit_details_tv = (TextView) itemView.findViewById(R.id.edit_details_tv);
            this.txtAddreddType = (TextView) itemView.findViewById(R.id.addressType);
            this.txtAddress = (TextView) itemView.findViewById(R.id.address);
            this.iconAddressList = itemView.findViewById(R.id.icon_home_address_list);
            this.tvDefault = itemView.findViewById(R.id.tv_default);


        }
    }

    public AddressSaveAdapter(Context mContext, AddressSaveAdapter.PositionInterface mPositionInterface, AddressSaveAdapter.DeletePositionInterface mDeletePositionInterface, List<AddressList> address) {

        this.mContext = mContext;
        this.mPositionInterface = mPositionInterface;
        this.mDeletePositionInterface = mDeletePositionInterface;
        this.addressList = address;
    }

    @Override
    public AddressSaveAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_list_row, parent, false);

        AddressSaveAdapter.MyViewHolder myViewHolder = new AddressSaveAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        sharePre = new SharedPreferencesClass(mContext);
        final AddressList add = addressList.get(listPosition);
        String addressType = add.getAddressType();
        if (addressType.equals("home") || addressType.equals("Home")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.iconAddressList.setImageDrawable(mContext.getDrawable(R.drawable.home_5));
            }
            holder.txtAddreddType.setText(addressType);


        } else if (addressType.equals("work") || addressType.equals("Work")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.iconAddressList.setImageDrawable(mContext.getDrawable(R.drawable.office_location));
            }
            holder.txtAddreddType.setText(addressType);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.iconAddressList.setImageDrawable(mContext.getDrawable(R.drawable.other_location));
            }
            holder.txtAddreddType.setText(addressType);

        }
        String address;
        if (add.getAddressTwo() != null && add.getAddressTwo().trim().length() > 0) {

            address = add.getAddressOne() + ", " + add.getAddressTwo() + ", " + add.getCity() + "\n" + add.getPostCode() + ", " + add.getCountry();

        } else {
            address = add.getAddressOne() + ", " + add.getCity() + "\n" + add.getPostCode() + ", " + add.getCountry();
        }


        holder.txtAddress.setText(address);
        Log.e(TAG, "onBindViewHolder: address id: " + add.getID());

        if (add.getIsDefault() == 1) {
            holder.tvDefault.setVisibility(View.VISIBLE); // hide default default option
            holder.tvDefault.setText("Default");
            sharePre.setString(sharePre.DEFAULT_ADDRESS, address);
        } else {
            holder.tvDefault.setVisibility(View.GONE);
        }

        ImageView cross_tv = holder.cross_tv;
        RelativeLayout holeItemClick = holder.holeItemClick;
        TextView edit_details_tv = holder.edit_details_tv;
        edit_details_tv.setText("Edit");

        holder.cross_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //popUpDeleteAddress(add.getID(),listPosition);
                mDeletePositionInterface.onClickDel(listPosition);
            }
        });


        holder.edit_details_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPositionInterface.onClickPos(listPosition);
            }
        });

    }

    public void removeAt(int position) {
        addressList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, addressList.size());
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }
}
