package com.easyfoodcustomer.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.easyfoodcustomer.R;
import com.easyfoodcustomer.fragments.MenuFragment;
import com.easyfoodcustomer.modelsNew.CartProdctListModel;
import com.easyfoodcustomer.restaurant_details.RestaurantDetailsActivity;
import com.easyfoodcustomer.roomData.AppDatabase;
import com.easyfoodcustomer.roomData.AppExecutors;
import com.easyfoodcustomer.roomData.OrderSaveModel;
import com.easyfoodcustomer.ui_new.SingleOrderCountAdapter;
import com.easyfoodcustomer.utility.DialogUtils;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SingleOrderCountDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private LinearLayout ll_action;
    private TextView tv_to_delete;
    private TextView msg;
    private TextView Ok;
    private TextView Close;
    private RecyclerView list;
    private List<OrderSaveModel> mealDetailsModel;

    private AppDatabase mDb;
    private boolean isIncrease;

    ArrayList<CartProdctListModel> cartProdctList;
    private SingleOrderCountAdapter singleOrderCountAdapter;

    private DialogUtils dialogUtils;

    public SingleOrderCountDialog(@NonNull Context context, List<OrderSaveModel> mealDetailsModel, boolean isIncrease) {
        super(context);
        this.context = context;
        this.mealDetailsModel = mealDetailsModel;
        this.isIncrease = isIncrease;
        dialogUtils=new DialogUtils(context);

        mDb = AppDatabase.getInstance(getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.color.transprent);
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflate.inflate(R.layout.dialog_single_order_count, null);
        setContentView(layout);
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.windowAnimations = R.style.dialogue_error;
        wlmp.gravity = Gravity.CENTER;
        wlmp.dimAmount = 0.8f;
        wlmp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        setTitle(null);
        // setCancelable(false);
        setCanceledOnTouchOutside(true);

        ll_action = layout.findViewById(R.id.ll_action);
        tv_to_delete = layout.findViewById(R.id.tv_to_delete);
        msg = layout.findViewById(R.id.msg);
        Ok = layout.findViewById(R.id.Ok);
        Close = layout.findViewById(R.id.Close);
        list = layout.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(context));

        Close.setOnClickListener(this);
        Ok.setOnClickListener(this);

        if (isIncrease)
        {
            tv_to_delete.setText(" + to add the product");
        }else
        {
            tv_to_delete.setText(" - to delete the product");
        }


        cartProdctList = new ArrayList<>();
        for (int i = 0; i < mealDetailsModel.size(); i++) {
            cartProdctList.add(new CartProdctListModel(mealDetailsModel.get(i).getId(),
                    mealDetailsModel.get(i).getItemCount(),
                    mealDetailsModel.get(i).getMealID(),
                    mealDetailsModel.get(i).getRestaurantID(),
                    mealDetailsModel.get(i).getMealName(),
                    mealDetailsModel.get(i).getMealPrice(),
                    mealDetailsModel.get(i).getVegType(),
                    mealDetailsModel.get(i).getMenuCategoryId(),
                    mealDetailsModel.get(i).getDescription(),
                    mealDetailsModel.get(i).getTotalAmoutOfMeal(),
                    mealDetailsModel.get(i).getData(),
                    false
            ));
        }
        singleOrderCountAdapter=new SingleOrderCountAdapter(context,SingleOrderCountDialog.this,cartProdctList);
        list.setAdapter(singleOrderCountAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Close:
                dismiss();
                break;

   case R.id.Ok:
                dismiss();
                break;

            case R.id.btn_remove:
                int removePos= (int) v.getTag();
                dialogUtils.showDialog("Please Wait..");
                OrderSaveModel orderSaveModels=new OrderSaveModel(cartProdctList.get(removePos).getId(),
                        cartProdctList.get(removePos).getItemCount()-1,
                        cartProdctList.get(removePos).getMealID(),
                        cartProdctList.get(removePos).getRestaurantID(),
                        cartProdctList.get(removePos).getMealName(),
                        cartProdctList.get(removePos).getMealPrice(),
                        cartProdctList.get(removePos).getVegType(),
                        cartProdctList.get(removePos).getMenuCategoryId(),
                        cartProdctList.get(removePos).getDescription(),
                        String.valueOf((Double.parseDouble(cartProdctList.get(removePos).getTotalAmoutOfMeal())/cartProdctList.get(removePos).getItemCount())*(cartProdctList.get(removePos).getItemCount()-1)),
                        true,
                        cartProdctList.get(removePos).getData());
                if (cartProdctList.get(removePos).getItemCount()>1)
                {
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mDb.saveOrderHistry().insertOrUpdate(orderSaveModels);
                            cartProdctList.get(removePos).setItemCount(cartProdctList.get(removePos).getItemCount()-1);
                            ((Activity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            singleOrderCountAdapter.notifyDataSetChanged();
                                            if (MenuFragment.class != null && MenuFragment.notifyMenuListOnFeagmentInterface != null) {
                                                MenuFragment.notifyMenuListOnFeagmentInterface.onNotify();
                                            dialogUtils.closeDialog();
                                            }
                                        }
                                    }, 2000);

                                }
                            });

                        }
                    });
                }else
                {
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mDb.saveOrderHistry().delete(orderSaveModels);
                            cartProdctList.remove(removePos);
                            ((Activity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            singleOrderCountAdapter.notifyDataSetChanged();
                                            if (MenuFragment.class != null && MenuFragment.notifyMenuListOnFeagmentInterface != null) {
                                                MenuFragment.notifyMenuListOnFeagmentInterface.onNotify();
                                                dialogUtils.closeDialog();
                                            }
                                        }
                                    }, 2000);
                                }
                            });
                        }
                    });

                }


                break;

            case R.id.btn_add:
                int addPos= (int) v.getTag();
                dialogUtils.showDialog("Please Wait..");
                OrderSaveModel orderSaveModel=new OrderSaveModel(cartProdctList.get(addPos).getId(),
                        cartProdctList.get(addPos).getItemCount()+1,
                        cartProdctList.get(addPos).getMealID(),
                        cartProdctList.get(addPos).getRestaurantID(),
                        cartProdctList.get(addPos).getMealName(),
                        cartProdctList.get(addPos).getMealPrice(),
                        cartProdctList.get(addPos).getVegType(),
                        cartProdctList.get(addPos).getMenuCategoryId(),
                        cartProdctList.get(addPos).getDescription(),
                        String.valueOf((Double.parseDouble(cartProdctList.get(addPos).getTotalAmoutOfMeal())/cartProdctList.get(addPos).getItemCount())*(cartProdctList.get(addPos).getItemCount()+1)),
                        true,
                        cartProdctList.get(addPos).getData());
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDb.saveOrderHistry().insertOrUpdate(orderSaveModel);
                        cartProdctList.get(addPos).setItemCount(cartProdctList.get(addPos).getItemCount()+1);
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        singleOrderCountAdapter.notifyDataSetChanged();
                                        if (MenuFragment.class != null && MenuFragment.notifyMenuListOnFeagmentInterface != null) {
                                            MenuFragment.notifyMenuListOnFeagmentInterface.onNotify();
                                            dialogUtils.closeDialog();
                                        }
                                    }
                                }, 2000);
                            }
                        });
                    }
                });

                break;

            case R.id.ly_item:
                int counterPos= (int) v.getTag();
                if (cartProdctList!=null && cartProdctList.size()>0)
                {
                    if (cartProdctList.get(counterPos).isOpen())
                    {
                        cartProdctList.get(counterPos).setOpen(false);
                    }else
                    {
                        cartProdctList.get(counterPos).setOpen(true);
                    }

                }
                singleOrderCountAdapter.notifyDataSetChanged();
                break;

            default:
                break;
        }
    }
}
