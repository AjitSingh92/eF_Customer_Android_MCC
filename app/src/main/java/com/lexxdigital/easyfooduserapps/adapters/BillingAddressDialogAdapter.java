package com.lexxdigital.easyfooduserapps.adapters;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.model.AddressList;
import com.lexxdigital.easyfooduserapps.utility.SharedPreferencesClass;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.GraphRequest.TAG;


public class BillingAddressDialogAdapter extends RecyclerView.Adapter<BillingAddressDialogAdapter.MyViewHolder> {
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
        LinearLayout edit_details_ll;


        public MyViewHolder(View itemView) {
            super(itemView);

            this.cross_tv = (ImageView) itemView.findViewById(R.id.cross_tv);
            this.cross_tv.setVisibility(View.GONE);
            this.edit_details_tv = (TextView) itemView.findViewById(R.id.edit_details_tv);
            this.edit_details_ll = (LinearLayout) itemView.findViewById(R.id.edit_details_ll);
            this.holeItemClick = (RelativeLayout) itemView.findViewById(R.id.holeItemClick);
            this.txtAddreddType = (TextView) itemView.findViewById(R.id.addressType);
            this.txtAddress = (TextView) itemView.findViewById(R.id.address);
            this.iconAddressList = itemView.findViewById(R.id.icon_home_address_list);
            this.tvDefault = itemView.findViewById(R.id.tv_default);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onAddressSelected.onAddressSelect(getLayoutPosition(), addressList.get(getLayoutPosition()));
        }
    }

    public BillingAddressDialogAdapter(Context mContext, List<AddressList> address, OnAddressSelected onAddressSelected) {

        this.mContext = mContext;
        this.onAddressSelected = onAddressSelected;
        this.addressList = address;
    }

    @Override
    public BillingAddressDialogAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                       int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_list_row, parent, false);

        BillingAddressDialogAdapter.MyViewHolder myViewHolder = new BillingAddressDialogAdapter.MyViewHolder(view);
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
        String address = "";
        if (add.getAddressTwo() != null && add.getAddressTwo().trim().length() > 0) {

            address = add.getAddressOne() + ", " + add.getAddressTwo() + ", " + add.getCity() + "\n" + add.getPostCode();
        } else {
            address = add.getAddressOne() + ", " + add.getCity() + "\n" + add.getPostCode();
        }
        holder.txtAddress.setText(address);


        if (add.getIsDefault() == 1) {
            holder.tvDefault.setVisibility(View.VISIBLE);
            holder.tvDefault.setText("Default");
            sharePre.setString(sharePre.DEFAULT_ADDRESS, address);
        } else {
            holder.tvDefault.setVisibility(View.GONE);
        }

        ImageView cross_tv = holder.cross_tv;
        LinearLayout edit_details_ll = holder.edit_details_ll;
        RelativeLayout holeItemClick = holder.holeItemClick;
        TextView edit_details_tv = holder.edit_details_tv;
        edit_details_ll.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_orange));
        edit_details_tv.setText("Use this");

        holder.edit_details_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onAddressSelected.onAddressSelect(listPosition, addressList.get(listPosition));
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
