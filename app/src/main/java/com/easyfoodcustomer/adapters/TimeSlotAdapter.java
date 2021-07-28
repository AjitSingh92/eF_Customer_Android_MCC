package com.easyfoodcustomer.adapters;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easyfoodcustomer.R;
import com.easyfoodcustomer.dialogs.RecyclerItemListener;


import java.util.List;


/*for setting item for the recycler view for   */
public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.ViewResource> {

    private Activity activity;
    private RecyclerItemListener recyclerItemListener;
    private List<String> timeSlots;
    private int selectedPos = -1;

    public TimeSlotAdapter(Activity activity, RecyclerItemListener recyclerItemListener, List<String> timeSlots) {
        this.activity = activity;
        this.recyclerItemListener = recyclerItemListener;
        this.timeSlots = timeSlots;

    }

    @NonNull
    @Override
    public TimeSlotAdapter.ViewResource onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_timeslot_cell, parent, false);
        return new TimeSlotAdapter.ViewResource(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TimeSlotAdapter.ViewResource holder, final int position) {

        holder.tvSlot.setText(timeSlots.get(position));
        setSlection(position, holder.rlMainSlot, holder.tvSlot);
        holder.rlMainSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPos = position;
                if (selectedPos >= 0)
                    notifyDataSetChanged();
                recyclerItemListener.onItemClick(position);

            }
        });


    }


    private void setSlection(final int pos, View view, TextView textView) {
        if (pos == selectedPos) {
            view.setBackground(activity.getResources().getDrawable(R.drawable.recatangle_orange));
            textView.setTextColor(activity.getResources().getColor(R.color.white));
        } else {
            view.setBackground(null);
            textView.setTextColor(activity.getResources().getColor(R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return timeSlots == null ? 0 : timeSlots.size();
    }

    public class ViewResource extends RecyclerView.ViewHolder {
        private RelativeLayout rlMainSlot;
        private TextView tvSlot;

        ViewResource(View itemView) {
            super(itemView);
            rlMainSlot = itemView.findViewById(R.id.rl_main_slot);
            tvSlot = itemView.findViewById(R.id.tv_slot);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


}
