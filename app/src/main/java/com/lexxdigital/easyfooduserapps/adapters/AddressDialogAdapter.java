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


public class AddressDialogAdapter extends RecyclerView.Adapter<AddressDialogAdapter.MyViewHolder> {
    Context mContext;
    private List<AddressList> addressList = new ArrayList<AddressList>();
    OnAddressSelected onAddressSelected;
    SharedPreferencesClass sharePre;

    public interface OnAddressSelected {
        void onAddressSelect(int position, AddressList address);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView edit_details_tv, txtAddreddType, txtAddress, tvDefault;
        ImageView cross_tv, iconAddressList;
        RelativeLayout holeItemClick;


        public MyViewHolder(View itemView) {
            super(itemView);

            this.cross_tv = (ImageView) itemView.findViewById(R.id.cross_tv);
            this.cross_tv.setVisibility(View.GONE);
            this.edit_details_tv = (TextView) itemView.findViewById(R.id.edit_details_tv);

            this.holeItemClick = (RelativeLayout) itemView.findViewById(R.id.holeItemClick);
            this.txtAddreddType = (TextView) itemView.findViewById(R.id.addressType);
            this.txtAddress = (TextView) itemView.findViewById(R.id.address);
            this.iconAddressList = itemView.findViewById(R.id.icon_home_address_list);
            this.tvDefault = itemView.findViewById(R.id.tv_default);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (addressList.get(getLayoutPosition()).getIsDelivered() == 0) {
                return;
            }

            if (onAddressSelected != null) {
                onAddressSelected.onAddressSelect(getLayoutPosition(), addressList.get(getLayoutPosition()));
            }

        }
    }

    public AddressDialogAdapter(Context mContext, List<AddressList> address, OnAddressSelected onAddressSelected) {

        this.mContext = mContext;
        this.onAddressSelected = onAddressSelected;
        this.addressList = address;
    }

    @Override
    public AddressDialogAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_list_row, parent, false);

        AddressDialogAdapter.MyViewHolder myViewHolder = new AddressDialogAdapter.MyViewHolder(view);
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
                holder.iconAddressList.setImageDrawable(mContext.getDrawable(R.drawable.ic_work_briefcase));
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
            holder.tvDefault.setVisibility(View.GONE); // Hide to default address
            holder.tvDefault.setText("Default");
//            sharePre.setString(sharePre.DEFAULT_ADDRESS, address);
        } else {
            holder.tvDefault.setVisibility(View.GONE);
        }

        ImageView cross_tv = holder.cross_tv;
        RelativeLayout holeItemClick = holder.holeItemClick;
        TextView edit_details_tv = holder.edit_details_tv;

        if (addressList.get(listPosition).getIsDelivered() == 0) {
            edit_details_tv.setBackground(mContext.getResources().getDrawable(R.drawable.rounded));
            edit_details_tv.setText("We are not here yet!");
            edit_details_tv.setAllCaps(true);


        } else {
            edit_details_tv.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_orange));
            edit_details_tv.setText("Delivery to this");
        }



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
