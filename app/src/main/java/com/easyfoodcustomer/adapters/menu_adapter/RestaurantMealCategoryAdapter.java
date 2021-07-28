package com.easyfoodcustomer.adapters.menu_adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.easyfoodcustomer.R;
import com.easyfoodcustomer.cart_db.DatabaseHelper;
import com.easyfoodcustomer.dialogs.ChooseOrderTypeDialog;
import com.easyfoodcustomer.dialogs.SingleOrderCountDialog;
import com.easyfoodcustomer.restaurant_details.RestaurantDetailsActivity;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.Meal;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;
import com.easyfoodcustomer.roomData.AppDatabase;
import com.easyfoodcustomer.roomData.AppExecutors;
import com.easyfoodcustomer.roomData.OrderSaveModel;

import java.util.ArrayList;
import java.util.List;

import static com.easyfoodcustomer.order_status.OrderStatusActivity.getActivity;
import static com.facebook.FacebookSdk.getApplicationContext;

public class RestaurantMealCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private List<Meal> mItem;
    private Boolean hideDetail = false;
    private List<MenuCategory> menuSubCategory;
    ItemClickListener menuItemClickListener;
    int parentPosition;
    MenuCategory menuCategory;
    DatabaseHelper db;
    private boolean isClosed;
    private AppDatabase mDb;

    public RestaurantMealCategoryAdapter(Context context, int parentPosition, ItemClickListener menuItemClickListener, boolean isClosed) {
        this.context = context;
        this.menuItemClickListener = menuItemClickListener;
        mDb = AppDatabase.getInstance(getApplicationContext());
//        this.menuSubCategory = RestaurantDetailsActivity.completeData.getMenu().getMenuCategory().get(parentPosition).getMenuSubCategory();
        this.parentPosition = parentPosition;

        inflater = LayoutInflater.from(context);
        menuSubCategory = new ArrayList<>();
        mItem = new ArrayList<>();
//        this.mItem = RestaurantDetailsActivity.completeData.getMenu().getMenuCategory().get(parentPosition).getMenuProducts();
        db = new DatabaseHelper(context);
        this.isClosed = isClosed;
    }

    public void setHideDetail(Boolean hideDetail) {
        this.hideDetail = hideDetail;
    }

    public void clearData() {
        this.mItem.clear();
        notifyDataSetChanged();
    }

    public void addItem(MenuCategory menuCategory) {
        this.menuSubCategory.addAll(menuCategory.getMenuSubCategory());
        this.menuCategory = menuCategory;
        this.mItem.addAll(menuCategory.getMeal());
        notifyItemChanged(this.mItem.size());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                CategoryViewHolder categoryViewHolder = (CategoryViewHolder) holder;
                categoryViewHolder.bindData(position);
                break;
            case 1:
                SubCategoryViewHolder subCategoryViewHolder = (SubCategoryViewHolder) holder;
                subCategoryViewHolder.bindData(position);
                break;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {
        switch (viewtype) {
            case 0:
                return new CategoryViewHolder(inflater.inflate(R.layout.offer_item_list, viewGroup, false));
            case 1:
                return new SubCategoryViewHolder(inflater.inflate(R.layout.product_with_sub_category_view, viewGroup, false));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (menuSubCategory == null || menuSubCategory.size() == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return (menuSubCategory == null || menuSubCategory.size() == 0) ? mItem.size() : 1;
    }


    class SubCategoryViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerView categoryItemView, subCategoryView;

        public SubCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryItemView = itemView.findViewById(R.id.list_productItemView);
            subCategoryView = itemView.findViewById(R.id.list_subCategoryItemView);
        }

        private void bindData(int position) {
            RecyclerLayoutManager layoutManager1 = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
            layoutManager1.setScrollEnabled(false);
            categoryItemView.setLayoutManager(layoutManager1);

            RestaurantCategoryAdapter2 restaurantCategoryAdapter = new RestaurantCategoryAdapter2(context, parentPosition, menuCategory, menuItemClickListener);
            restaurantCategoryAdapter.setHideDetail(true);
            categoryItemView.setAdapter(restaurantCategoryAdapter);
//            restaurantCategoryAdapter.addItem(mItem);

//            if (position==(getItemCount()-1)) {
            if (menuSubCategory != null && menuSubCategory.size() > 0) {
                subCategoryView.setVisibility(View.VISIBLE);

                RestaurantSubCategoryAdapter restaurantSubCategoryAdapter = new RestaurantSubCategoryAdapter(context, parentPosition, menuCategory, menuItemClickListener);
                RecyclerLayoutManager layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
                subCategoryView.setLayoutManager(layoutManager);
                subCategoryView.setAdapter(restaurantSubCategoryAdapter);
                restaurantSubCategoryAdapter.addItem(menuSubCategory);
            }
        }

    }

    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView txt_menu_title, txt_price, txt_items_detail, item_count/*, txt_Count*/;
        private final LinearLayout clickCount, item_remove, item_add;
        private final ProgressBar progressBar;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
            txt_menu_title = itemView.findViewById(R.id.txt_menu_title);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_items_detail = itemView.findViewById(R.id.txt_items_detail);
            //  txt_Count = (TextView) itemView.findViewById(R.id.txt_count);
            clickCount = itemView.findViewById(R.id.clickCount);
            item_remove = itemView.findViewById(R.id.item_remove);
            item_add = itemView.findViewById(R.id.item_add);
            item_count = itemView.findViewById(R.id.item_count);
           // item_add.setOnClickListener(this);
          //  item_remove.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        private void bindData(int position) {
            txt_menu_title.setText(mItem.get(position).getMealName());
            txt_price.setText(context.getResources().getString(R.string.currency) + mItem.get(position).getMealPrice());
            item_count.setText("0");
            Log.e("Menu Description", "" + mItem.get(position).getDescription());
            // txt_Count.setText("0");
            if (hideDetail) {
                txt_items_detail.setVisibility(View.GONE);
            } else {
                txt_items_detail.setVisibility(View.VISIBLE);
                txt_items_detail.setText(mItem.get(position).getDescription());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (mItem.get(position).getDescription() != null && !mItem.get(position).getDescription().isEmpty())
                        txt_items_detail.setText(Html.fromHtml(mItem.get(position).getDescription(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    if (mItem.get(position).getDescription() != null && !mItem.get(position).getDescription().isEmpty())
                        txt_items_detail.setText(Html.fromHtml(mItem.get(position).getDescription()));
                }

            }
//            List<MenuProduct> products = db.getMenuProduct(menuCategory.getMenuCategoryId(), mItem.get(position).getMealId());
            int qtyCount = 0;
            mDb = AppDatabase.getInstance(getApplicationContext());
            //List<OrderSaveModel> orderSaveModelList= mDb.saveOrderHistry().loadAllHistoryOfOrder();
            List<OrderSaveModel> orderSaveModelList= mDb.saveOrderHistry().loadAllOrderForID(mItem.get(position).getMealId());
            if (orderSaveModelList.size()>0)
            {
                for (int i=0;i<orderSaveModelList.size();i++)
                {
                    qtyCount=qtyCount+orderSaveModelList.get(i).getItemCount();
                }

            }
            /*mDb.saveOrderHistry().loadAllOrderForID(mItem.get(position).getMenuId()).observe(context, new androidx.lifecycle.Observer<List<OrderSaveModel>>() {
                @Override
                public void onChanged(@Nullable List<OrderSaveModel> orderSaveModelList) {

                }
            });*/

//
//            for (MenuProduct itemOnCart : products) {
//                qtyCount += itemOnCart.getOriginalQuantity();
//            }

            if (qtyCount == 0) {
                clickCount.setVisibility(View.GONE);
                // txt_Count.setVisibility(View.GONE);
                //txt_Count.setText(String.valueOf(qtyCount));
                item_count.setText(String.valueOf(qtyCount));
            } else {
                clickCount.setVisibility(View.VISIBLE);
                // txt_Count.setVisibility(View.VISIBLE);
                //txt_Count.setText(String.valueOf(qtyCount));
                item_count.setText(String.valueOf(qtyCount));
            }

            item_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ChooseOrderTypeDialog(context,orderSaveModelList).show();

                }
            });

            item_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int  itemQty = (Integer.parseInt(item_count.getText().toString()) - 1);
                    //  txt_Count.setText(Integer.parseInt(item_count.getText().toString()) - 1);
                    if (itemQty == 0) {
                        clickCount.setVisibility(View.GONE);
                        //  txt_Count.setVisibility(View.GONE);
                    }
                    if (Integer.parseInt(item_count.getText().toString())>1)
                    {
                        if (orderSaveModelList.size()>1)
                        {
                            new SingleOrderCountDialog(context,orderSaveModelList,false).show();
                        }else
                        {
                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    mDb.saveOrderHistry().delete(orderSaveModelList.get(0));
                                }
                            });
                        }
                        /*if (orderSaveModelList.get(0).getItemCount()>1)
                        {
                            OrderSaveModel orderSaveModel=new OrderSaveModel(orderSaveModelList.get(0).getId(),
                                    orderSaveModelList.get(0).getItemCount()-1,
                                    orderSaveModelList.get(0).getMealID(),
                                    orderSaveModelList.get(0).getRestaurantID(),
                                    orderSaveModelList.get(0).getMealName(),
                                    orderSaveModelList.get(0).getMealPrice(),
                                    orderSaveModelList.get(0).getVegType(),
                                    orderSaveModelList.get(0).getMenuCategoryId(),
                                    orderSaveModelList.get(0).getDescription(),
                                    String.valueOf((Double.parseDouble(orderSaveModelList.get(0).getTotalAmoutOfMeal())/orderSaveModelList.get(0).getItemCount())*(orderSaveModelList.get(0).getItemCount()-1)),
                                    orderSaveModelList.get(0).getData());
                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    mDb.saveOrderHistry().insertOrUpdate(orderSaveModel);
                                }
                            });
                        }else
                        {

                        }
*/
                    }else
                    {
                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                mDb.saveOrderHistry().deleteAllMessage(orderSaveModelList.get(0).getMealID());
                            }
                        });
                    }

                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public void onClick(View v) {
            int itemQty;

            switch (v.getId()) {
                case R.id.item_add:
                    itemQty = (Integer.parseInt(item_count.getText().toString()) + 1);
                    //  txt_Count.setText(Integer.parseInt(item_count.getText().toString()) + 1);
                    if (menuItemClickListener != null) {
                        menuItemClickListener.OnCategoryClick(parentPosition, getLayoutPosition(), clickCount, item_count, itemQty, 2, menuCategory, progressBar);
                    }
                    break;
                case R.id.item_remove:

                    break;
                default:
                    if (isClosed) {
                        restaurantClosedDialog();
                    } else {
                        // txt_Count.setVisibility(View.VISIBLE);
                        if (clickCount.getVisibility() == View.GONE) {
                            //txt_Count.setVisibility(View.VISIBLE);
                            itemQty = (Integer.parseInt(item_count.getText().toString()) + 1);
                            // txt_Count.setText(Integer.parseInt(item_count.getText().toString()) + 1);
                            if (menuItemClickListener != null) {
                                List<Meal> mItemNew = mItem;

                                menuItemClickListener.OnCategoryClick(parentPosition, getLayoutPosition(), clickCount, item_count, itemQty, 2, menuCategory, progressBar);
                            }
                        }
                    }
                    break;
            }


        }
    }


    public void restaurantClosedDialog() {
        LayoutInflater factory = LayoutInflater.from(RestaurantDetailsActivity.restaurantDetailsActivity);
        final View mDialogVieww = factory.inflate(R.layout.layout_closed_dialog, null);
        final AlertDialog alertClodseDialog = new AlertDialog.Builder(RestaurantDetailsActivity.restaurantDetailsActivity).create();
        alertClodseDialog.setView(mDialogVieww);
        alertClodseDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //   final TextView ok_tv = (TextView)  mDialogView.findViewById(R.id.okTv);

        mDialogVieww.findViewById(R.id.tv_btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                alertClodseDialog.dismiss();
            }
        });


        alertClodseDialog.show();
    }

}