package com.easyfoodcustomer.adapters;

import android.app.Activity;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.easyfoodcustomer.R;
import com.easyfoodcustomer.databinding.LayoutCellTableBinding;
import com.easyfoodcustomer.utility.TableInfoBean;
import com.easyfoodcustomer.utility.TableListListener;

import java.util.List;


/*for setting item for the recycler view for   */
public class TableTopAdapter extends RecyclerView.Adapter<TableTopAdapter.ViewResource> {

    private Activity activity;
    private TableListListener tableListListener;
    private List<TableInfoBean.DataBean> topList;
    private List<TableInfoBean.DataBean.ServiceUnitsBean> bottomList;
    private boolean isTop;
    private int selectedPos = 0;
    private int botoomSelction = 0;
    private int parentPos;


    public TableTopAdapter(Activity activity, boolean isTop, List<TableInfoBean.DataBean> topList, List<TableInfoBean.DataBean.ServiceUnitsBean> bottomList, TableListListener tableListListener, int parentPos) {
        this.activity = activity;
        this.isTop = isTop;
        this.topList = topList;
        this.bottomList = bottomList;
        this.tableListListener = tableListListener;
        this.parentPos = parentPos;


    }

    @NonNull
    @Override
    public TableTopAdapter.ViewResource onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cell_table, parent, false);
        return new TableTopAdapter.ViewResource(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TableTopAdapter.ViewResource holder, int position) {
        final int pos = position;
        if (isTop) {
            holder.binding.cbItem.setChecked(false);
            holder.binding.cbItem.setText(topList.get(pos).getService_area());
            if (selectedPos == pos)
                holder.binding.cbItem.setChecked(true);
            else
                holder.binding.cbItem.setChecked(false);

            holder.binding.cbItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedPos = pos;
                    tableListListener.onTopItemClick(pos);
                    notifyDataSetChanged();
                }
            });
        } else {
            holder.binding.cbItem.setText(bottomList.get(pos).getServiceable_units());
            if (botoomSelction == pos)
                holder.binding.cbItem.setChecked(true);
            else
                holder.binding.cbItem.setChecked(false);

            holder.binding.cbItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    botoomSelction = pos;
                    tableListListener.onBottomItemClick(parentPos, pos);
                    notifyDataSetChanged();
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        if (isTop)
            return topList == null ? 0 : topList.size();
        else
            return bottomList == null ? 0 : bottomList.size();

    }

    public class ViewResource extends RecyclerView.ViewHolder {
        public LayoutCellTableBinding binding;

        ViewResource(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


}
