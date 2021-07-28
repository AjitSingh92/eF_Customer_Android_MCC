package com.easyfoodcustomer.adapters;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easyfoodcustomer.R;


import java.util.List;


/*for setting item for the recycler view for   */
public class DeliveryAreaAdapter extends RecyclerView.Adapter<DeliveryAreaAdapter.ViewResource> {

    private Activity activity;
    private List<String> areaCodeList;


    public DeliveryAreaAdapter(Activity activity, List<String> areaCodeList) {
        this.activity = activity;
        this.areaCodeList = areaCodeList;


    }

    @NonNull
    @Override
    public DeliveryAreaAdapter.ViewResource onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_cell_areas, parent, false);

        DeliveryAreaAdapter.ViewResource myViewHolder = new DeliveryAreaAdapter.ViewResource(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DeliveryAreaAdapter.ViewResource holder, int position) {
        holder.areaCode.setText(areaCodeList.get(position));


    }


    @Override
    public int getItemCount() {
        return areaCodeList == null ? 0 : areaCodeList.size();
    }

    public class ViewResource extends RecyclerView.ViewHolder {

        TextView areaCode;

        ViewResource(View itemView) {
            super(itemView);
            this.areaCode = itemView.findViewById(R.id.tv_area_code);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


}
