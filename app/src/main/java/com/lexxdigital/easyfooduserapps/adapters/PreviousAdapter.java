package com.lexxdigital.easyfooduserapps.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapps.R;


public class PreviousAdapter extends RecyclerView.Adapter<PreviousAdapter.MyViewHolder> {

    // private ArrayList<Arraylist> dataSet;

    public static class

    MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewname;

        // ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);

            //    this.textViewname = (TextView) itemView.findViewById(R.id.textViewname);

        }
    }

    public PreviousAdapter() {

    }

    @Override
    public PreviousAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.previous_order_item, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);

        PreviousAdapter.MyViewHolder myViewHolder = new PreviousAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final PreviousAdapter.MyViewHolder holder, final int listPosition) {

        // TextView textViewname = holder.textViewname;
        // textViewname.setText(Html.fromHtml("<b>" + "Frank’s Hot Sauce Chicken Wings  "+"</b>"+"3 x fried chicken wings in Franks hot sauce"));
        // textViewname.setText();
//        // TextView textViewVersion = holder.textViewVersion;
//        ImageView imageView = holder.imageViewIcon;
//
//        textViewName.setText(dataSet.get(listPosition).getName());
//        //textViewVersion.setText(dataSet.get(listPosition).getVersion());
//        imageView.setImageResource(dataSet.get(listPosition).getImage());
    }

    @Override
    public int getItemCount() {
        return 10;
    }}
