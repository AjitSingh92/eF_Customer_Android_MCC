package com.lexxdigital.easyfooduserapp.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.new_restaurant_response.NewRestaurantsDetailsResponse;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.response.RestaurantDetailsResponse;
import com.lexxdigital.easyfooduserapp.restaurant_details.model.review_response.ReviewResponse;


public class ReatingAdapter extends RecyclerView.Adapter<ReatingAdapter.MyViewHolder> {

    // private ArrayList<Arraylist> dataSet;
    Context mContext;
    ReviewResponse response;


    public static class

    MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtDate;

        ImageView img1, img2, img3, img4, img5;
        AppCompatRatingBar ratingBar;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.txtName = (TextView) itemView.findViewById(R.id.name);
            this.txtDate = (TextView) itemView.findViewById(R.id.date);
            this.img1 = (ImageView) itemView.findViewById(R.id.rat_1);
            this.img2 = (ImageView) itemView.findViewById(R.id.rat_2);
            this.img3 = (ImageView) itemView.findViewById(R.id.rat_3);
            this.img4 = (ImageView) itemView.findViewById(R.id.rat_4);
            this.img5 = (ImageView) itemView.findViewById(R.id.rat_5);
            this.ratingBar = itemView.findViewById(R.id.ratingBar1);

        }
    }

    public ReatingAdapter(Context mContext, ReviewResponse res) {
        this.mContext = mContext;
        this.response = res;
    }

    @Override
    public ReatingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reating_row, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);

        ReatingAdapter.MyViewHolder myViewHolder = new ReatingAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ReatingAdapter.MyViewHolder holder, final int listPosition) {

//        Log.e("SIZE >>","//"+response.getData().getReviews().getRating().size()+"////"+listPosition);
        //  holder.txtName.setText(response.getData().getReviews().getRating().get(listPosition).getUserName());

        if (response.getData().getReviews().size() > 0) {
            holder.txtName.setText(response.getData().getReviews().get(listPosition).getUserName());
            holder.txtDate.setText(response.getData().getReviews().get(listPosition).getReviewDate());


            float ratingf = (Float.parseFloat(response.getData().getReviews().get(listPosition).getOverallRating()) % 2);
            int num = 0;
            if (ratingf > 0) {
                num = (int) (Float.parseFloat(response.getData().getReviews().get(listPosition).getOverallRating()) + 0.5);
            } else {
                num = (int) (Float.parseFloat(response.getData().getReviews().get(listPosition).getOverallRating()) - 0.5);
            }
//             holder.ratingBar.setNumStars(num);
            holder.ratingBar.setNumStars(5);
            holder.ratingBar.setRating(Float.parseFloat(response.getData().getReviews().get(listPosition).getOverallRating()));



           /* if (Math.round(Double.parseDouble(response.getData().getReviews().get(listPosition).getOverallRating())) == 1) {
                holder.img1.setVisibility(View.VISIBLE);
                holder.img2.setVisibility(View.INVISIBLE);
                holder.img3.setVisibility(View.INVISIBLE);
                holder.img4.setVisibility(View.INVISIBLE);
                holder.img5.setVisibility(View.INVISIBLE);

            } else if (Math.round(Double.parseDouble(response.getData().getReviews().get(listPosition).getOverallRating())) == 2) {
                holder.img1.setVisibility(View.VISIBLE);
                holder.img2.setVisibility(View.VISIBLE);
                holder.img3.setVisibility(View.INVISIBLE);
                holder.img4.setVisibility(View.INVISIBLE);
                holder.img5.setVisibility(View.INVISIBLE);
            } else if (Math.round(Double.parseDouble(response.getData().getReviews().get(listPosition).getOverallRating())) == 3) {
                holder.img1.setVisibility(View.VISIBLE);
                holder.img2.setVisibility(View.VISIBLE);
                holder.img3.setVisibility(View.VISIBLE);
                holder.img4.setVisibility(View.INVISIBLE);
                holder.img5.setVisibility(View.INVISIBLE);
            } else if (Math.round(Double.parseDouble(response.getData().getReviews().get(listPosition).getOverallRating())) == 4) {
                holder.img1.setVisibility(View.VISIBLE);
                holder.img2.setVisibility(View.VISIBLE);
                holder.img3.setVisibility(View.VISIBLE);
                holder.img4.setVisibility(View.VISIBLE);
                holder.img5.setVisibility(View.INVISIBLE);
            } else if (Math.round(Double.parseDouble(response.getData().getReviews().get(listPosition).getOverallRating())) == 5) {
                holder.img1.setVisibility(View.VISIBLE);
                holder.img2.setVisibility(View.VISIBLE);
                holder.img3.setVisibility(View.VISIBLE);
                holder.img4.setVisibility(View.VISIBLE);
                holder.img5.setVisibility(View.VISIBLE);
            }*/
        }


    }

    @Override
    public int getItemCount() {
        return response.getData().getReviews().size();
    }
}
