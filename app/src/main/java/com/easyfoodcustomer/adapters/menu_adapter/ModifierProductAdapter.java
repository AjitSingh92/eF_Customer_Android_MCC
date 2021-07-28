package com.easyfoodcustomer.adapters.menu_adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easyfoodcustomer.R;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.Modifier;

import java.util.ArrayList;
import java.util.List;

public class ModifierProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private List<Modifier> mItem;
    ModifierItemSelectListener modifierItemSelectListener;
    int parentPosition;
    private int viewType = 1;
    public CheckBox lastSelectedItem;
    public int lastSelectedPosition = -1;
    int maxSize;
    int checkSize = 0;
    private static ModifierProductAdapter instance = null;
    int totalSize = 0;
    boolean isFirst = true;

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public interface ModifierItemSelectListener {
        void onModifierItemSelected(int parentPosition, List<Modifier> mModifiers, boolean isChecked);
    }

    public ModifierProductAdapter(Context context, int parentPosition, ModifierItemSelectListener modifierItemSelectListener, int maxSize, int checkSize) {
        this.context = context;
        this.parentPosition = parentPosition;
        this.modifierItemSelectListener = modifierItemSelectListener;
        inflater = LayoutInflater.from(context);
        this.mItem = new ArrayList<>();

        this.maxSize = maxSize;
        instance = this;
    }

    public static ModifierProductAdapter getInstance() {
        return instance;
    }

    public void clearData() {
        this.mItem.clear();
        notifyDataSetChanged();
    }

    public void addItem(List<Modifier> mItem) {
        this.mItem.addAll(mItem);
        notifyItemChanged(this.mItem.size());
    }

    public void addItem(Modifier mItem) {
        this.mItem.add(mItem);
        notifyItemChanged(this.mItem.size());
    }

    public List<Modifier> getSelectedModifier() {
        List<Modifier> itemList = new ArrayList<>();
        for (Modifier item : mItem) {
            if (item.getOriginalQuantity() != null && !item.getOriginalQuantity().equals("")) {
                if (Integer.parseInt(item.getOriginalQuantity()) > 0) {
                    Modifier modifier = new Modifier(item.getProductId(), item.getUnit(), item.getModifierProductPrice(), item.getProductName(), item.getOriginalQuantity(), item.getOriginalQuantity(), item.getAmount(), item.getOriginalAmount1());
                    itemList.add(modifier);
                }
            }
        }

        return itemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {
        switch (viewtype) {
            case 0:
                return new CategoryViewHolder(inflater.inflate(R.layout.modifier_product_item_list, viewGroup, false));
            case 1:
                return new ViewHolder2(inflater.inflate(R.layout.meal_product_row, viewGroup, false));
        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case 0:
                CategoryViewHolder categoryViewHolder = (CategoryViewHolder) viewHolder;
                categoryViewHolder.bindData(position);
                break;
            case 1:
                ViewHolder2 viewHolder2 = (ViewHolder2) viewHolder;
                viewHolder2.bindData(position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (viewType == R.layout.meal_product_row) {
            return 1;
        } else {
            return 0;

        }
    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }

    class ViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView title, price, modifiers;
        private final CheckBox itemSelected;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_title);
            modifiers = itemView.findViewById(R.id.tv_modifiers);
            price = itemView.findViewById(R.id.tv_price);
            // note = itemView.findViewById(R.id.tv_note);
            itemSelected = itemView.findViewById(R.id.cb_itemSelected);

            itemView.setOnClickListener(this);
        }

        private void bindData(int position) {
            title.setText(mItem.get(position).getProductName());

            if (mItem.get(position).getModifierProductPrice() != null) {
                price.setText("£" + mItem.get(position).getModifierProductPrice());
            }

            // modifiers.setVisibility(View.GONE);

            if (getAdapterPosition() == lastSelectedPosition) {
                itemSelected.setChecked(true);
                lastSelectedItem = itemSelected;
                checkSize = checkSize + 1;
                mItem.get(getLayoutPosition()).setQuantity("1");
                mItem.get(getLayoutPosition()).setOriginalQuantity("1");
                mItem.get(getLayoutPosition()).setAmount((1 * (Double.parseDouble(mItem.get(getLayoutPosition()).getModifierProductPrice()))));
                mItem.get(getLayoutPosition()).setOriginalAmount1((1 * (Double.parseDouble(mItem.get(getLayoutPosition()).getModifierProductPrice()))));

            } else {
                itemSelected.setChecked(false);
                mItem.get(getLayoutPosition()).setQuantity("0");
                mItem.get(getLayoutPosition()).setOriginalQuantity("0");
                mItem.get(getLayoutPosition()).setAmount((0 * (Double.parseDouble(mItem.get(getLayoutPosition()).getModifierProductPrice()))));
                mItem.get(getLayoutPosition()).setOriginalAmount1((0 * (Double.parseDouble(mItem.get(getLayoutPosition()).getModifierProductPrice()))));
                lastSelectedItem = null;

            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (modifierItemSelectListener != null) {
                        modifierItemSelectListener.onModifierItemSelected(parentPosition, mItem,true);
                    }

                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();

                }
            });





        }

        @Override
        public void onClick(View v) {
            /*if (lastSelectedPosition == -1) {
                itemSelected.setChecked(true);
                lastSelectedItem = itemSelected;
                lastSelectedPosition = getLayoutPosition();
                mItem.get(getLayoutPosition()).setQuantity("1");
                mItem.get(getLayoutPosition()).setOriginalQuantity("1");
                mItem.get(getLayoutPosition()).setAmount((1 * (Double.parseDouble(mItem.get(getLayoutPosition()).getModifierProductPrice()))));
                mItem.get(getLayoutPosition()).setOriginalAmount1((1 * (Double.parseDouble(mItem.get(getLayoutPosition()).getModifierProductPrice()))));

            } else {
                lastSelectedItem.setChecked(false);
                itemSelected.setChecked(true);
                lastSelectedItem = itemSelected;

                mItem.get(lastSelectedPosition).setQuantity("0");
                mItem.get(lastSelectedPosition).setOriginalQuantity("0");
                mItem.get(lastSelectedPosition).setAmount((0 * (Double.parseDouble(mItem.get(lastSelectedPosition).getModifierProductPrice()))));
                mItem.get(lastSelectedPosition).setOriginalAmount1((0 * (Double.parseDouble(mItem.get(lastSelectedPosition).getModifierProductPrice()))));

                lastSelectedPosition = getLayoutPosition();

                mItem.get(getLayoutPosition()).setQuantity("1");
                mItem.get(getLayoutPosition()).setOriginalQuantity("1");
                mItem.get(getLayoutPosition()).setAmount((1 * (Double.parseDouble(mItem.get(getLayoutPosition()).getModifierProductPrice()))));
                mItem.get(getLayoutPosition()).setOriginalAmount1((1 * (Double.parseDouble(mItem.get(getLayoutPosition()).getModifierProductPrice()))));

            }*/

            // ProductModifierAdapter.getInstance().getMaxSize(parentPosition);
            // lastSelectedPosition = getAdapterPosition();
            //notifyDataSetChanged();
          /*  if (mItem.get(getLayoutPosition()).getOriginalQuantity().equals("1")) {
                mItem.get(getLayoutPosition()).setQuantity("0");
                mItem.get(getLayoutPosition()).setOriginalQuantity("0");
                mItem.get(getLayoutPosition()).setAmount((0 * (Double.parseDouble(mItem.get(getLayoutPosition()).getModifierProductPrice()))));
                mItem.get(getLayoutPosition()).setOriginalAmount1((0 * (Double.parseDouble(mItem.get(getLayoutPosition()).getModifierProductPrice()))));
                itemSelected.setChecked(false);
                lastSelectedItem = null;
                //checkSize = checkSize - 1;
            } else {
                //if (checkSize < SizeModifierAdapter.getInstance().getMaxSize(parentPosition)) {

                    itemSelected.setChecked(true);
                    lastSelectedItem = itemSelected;
                    checkSize = checkSize + 1;
                   // lastSelectedPosition = getAdapterPosition();

                    mItem.get(getLayoutPosition()).setQuantity("1");
                    mItem.get(getLayoutPosition()).setOriginalQuantity("1");
                    mItem.get(getLayoutPosition()).setAmount((1 * (Double.parseDouble(mItem.get(getLayoutPosition()).getModifierProductPrice()))));
                    mItem.get(getLayoutPosition()).setOriginalAmount1((1 * (Double.parseDouble(mItem.get(getLayoutPosition()).getModifierProductPrice()))));
                *//*} else {
                    Toast.makeText(context, "You can only select maximum " + SizeModifierAdapter.getInstance().getMaxSize(parentPosition) + " item.", Toast.LENGTH_SHORT).show();
                }*//*
            }*/


        }

    }

    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView title, price, item_count;
        private final LinearLayout item_remove, item_add;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_title);
            price = itemView.findViewById(R.id.tv_price);
            item_count = itemView.findViewById(R.id.item_count);
            item_remove = itemView.findViewById(R.id.item_remove);
            item_add = itemView.findViewById(R.id.item_add);

            item_add.setOnClickListener(this);
            item_remove.setOnClickListener(this);
        }

        private void bindData(int position) {
            title.setText(mItem.get(position).getProductName());
            price.setText(context.getResources().getString(R.string.currency) + "" + mItem.get(position).getModifierProductPrice());
            mItem.get(position).setQuantity("0");

            item_count.setText("0");
            /*if ((Integer.parseInt(item_count.getText().toString()) < 1)) {
                item_remove.setVisibility(View.INVISIBLE);
            }*/


        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.item_add:
                    if (totalSize < SizeModifierAdapter.getInstance().getMaxSize(parentPosition)) {
                        item_count.setText(String.valueOf((Integer.parseInt(item_count.getText().toString()) + 1)));
                        mItem.get(getLayoutPosition()).setQuantity(item_count.getText().toString());
                        mItem.get(getLayoutPosition()).setOriginalQuantity(item_count.getText().toString());
                        mItem.get(getLayoutPosition()).setAmount(((Integer.parseInt(item_count.getText().toString())) * (Double.parseDouble(mItem.get(getLayoutPosition()).getModifierProductPrice()))));
                        mItem.get(getLayoutPosition()).setOriginalAmount1(((Integer.parseInt(item_count.getText().toString())) * (Double.parseDouble(mItem.get(getLayoutPosition()).getModifierProductPrice()))));
                        if (modifierItemSelectListener != null) {
                            modifierItemSelectListener.onModifierItemSelected(parentPosition, mItem, true);
                        }
                        if ((Integer.parseInt(item_count.getText().toString()) > 0)) {
                            item_remove.setVisibility(View.VISIBLE);
                        }
                        totalSize = totalSize + 1;
                    }
                    break;
                case R.id.item_remove:
                    if ((Integer.parseInt(item_count.getText().toString()) > 0)) {
                        item_count.setText(String.valueOf((Integer.parseInt(item_count.getText().toString()) - 1)));
                        mItem.get(getLayoutPosition()).setQuantity(item_count.getText().toString());
                        mItem.get(getLayoutPosition()).setOriginalQuantity(item_count.getText().toString());
                        mItem.get(getLayoutPosition()).setAmount(((Integer.parseInt(item_count.getText().toString())) * (Double.parseDouble(mItem.get(getLayoutPosition()).getModifierProductPrice()))));
                        mItem.get(getLayoutPosition()).setOriginalAmount1(((Integer.parseInt(item_count.getText().toString())) * (Double.parseDouble(mItem.get(getLayoutPosition()).getModifierProductPrice()))));
                        if (modifierItemSelectListener != null) {
                            modifierItemSelectListener.onModifierItemSelected(parentPosition, mItem, false);
                        }
                        /*if ((Integer.parseInt(item_count.getText().toString()) < 1)) {
                            item_remove.setVisibility(View.INVISIBLE);
                        }*/
                        totalSize = totalSize - 1;
                    }
                    break;
                default:

                    break;
            }
        }
    }


    public int getLastSelectedPosition() {
        return lastSelectedPosition;
    }

}
