package com.lexxdigital.easyfooduserapps.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.model.postal_code_address.PostalCodeAddRes;

import java.util.ArrayList;
import java.util.List;

public class SearchPostalAddressAdapter extends RecyclerView.Adapter<SearchPostalAddressAdapter.MyViewHolder> {
    private List<PostalCodeAddRes.Datum> postalAddres = new ArrayList<>();
    Context context;
    private OnAddressSelected onAddressSelected;

    public SearchPostalAddressAdapter(Context context, List<PostalCodeAddRes.Datum> postalAddres,
                                      OnAddressSelected onAddressSelected) {
        this.context = context;
        this.postalAddres = postalAddres;
        this.onAddressSelected = onAddressSelected;
    }

    public interface OnAddressSelected {
        void onAddressSelect(int position, List<PostalCodeAddRes.Datum> postalAddres);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvAddress;
        LinearLayout llAddressRow;

        public MyViewHolder(View view) {
            super(view);
            tvAddress = view.findViewById(R.id.tv_address);
            llAddressRow = view.findViewById(R.id.ll_address_row);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.postal_address_list_row, viewGroup, false);
        SearchPostalAddressAdapter.MyViewHolder myViewHolder = new SearchPostalAddressAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int i) {
        String address = postalAddres.get(i).getLine1() + ", " + postalAddres.get(i).getLine2() + ", "
                + postalAddres.get(i).getPostcode() + ", " + postalAddres.get(i).getPostalCounty();
        holder.tvAddress.setText(address);
        holder.llAddressRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddressSelected.onAddressSelect(i, postalAddres);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postalAddres.size();
    }
}
