package com.easyfoodcustomer.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.easyfoodcustomer.R;
import com.easyfoodcustomer.fragments.MenuFragment;
import com.easyfoodcustomer.modelsNew.MealDetailsModel;
import com.easyfoodcustomer.restaurant_details.RestaurantDetailsActivity;
import com.easyfoodcustomer.roomData.AppDatabase;
import com.easyfoodcustomer.roomData.AppExecutors;
import com.easyfoodcustomer.roomData.OrderSaveModel;
import com.easyfoodcustomer.ui_new.MenuMealListAdapter;
import com.easyfoodcustomer.ui_new.interfaces.MenuProductModifierInterface;
import com.google.gson.Gson;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ChooseOrderTypeDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private TextView tv_heading;
    private TextView last_item_name;
    private TextView repeat_last;
    private TextView choose_again;

    private ImageView cross_tv;

    private AppDatabase mDb;
    private List<OrderSaveModel> mealDetailsModel;


    public ChooseOrderTypeDialog(@NonNull Context context, List<OrderSaveModel> mealDetailsModel) {
        super(context);
        this.context = context;
        this.mealDetailsModel = mealDetailsModel;
        mDb = AppDatabase.getInstance(getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.color.transprent);
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflate.inflate(R.layout.dialog_choose_order_type, null);
        setContentView(layout);
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.windowAnimations = R.style.dialogue_error;
        wlmp.gravity = Gravity.CENTER;
        wlmp.dimAmount = 0.8f;
        wlmp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        setTitle(null);
        // setCancelable(false);
        setCanceledOnTouchOutside(true);

        cross_tv = layout.findViewById(R.id.cross_tv);
        tv_heading = layout.findViewById(R.id.tv_heading);
        last_item_name = layout.findViewById(R.id.last_item_name);
        repeat_last = layout.findViewById(R.id.repeat_last);
        choose_again = layout.findViewById(R.id.choose_again);

        if (mealDetailsModel!=null && mealDetailsModel.size()>0)
        {
            last_item_name.setText(mealDetailsModel.get(0).getMealName());
        }


        cross_tv.setOnClickListener(this);
        repeat_last.setOnClickListener(this);
        choose_again.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cross_tv:
                dismiss();
                break;


            case R.id.repeat_last:
                if (mealDetailsModel.size()>1)
                {
                    new SingleOrderCountDialog(context,mealDetailsModel,true).show();
                }else
                {
                   OrderSaveModel orderSaveModel = new OrderSaveModel(mealDetailsModel.get(0).getId(),
                           mealDetailsModel.get(0).getItemCount() + 1,
                           mealDetailsModel.get(0).getMealID(),
                           mealDetailsModel.get(0).getRestaurantID(),
                           mealDetailsModel.get(0).getMealName(),
                           mealDetailsModel.get(0).getMealPrice(),
                           mealDetailsModel.get(0).getVegType(),
                           mealDetailsModel.get(0).getMenuCategoryId(),
                           mealDetailsModel.get(0).getDescription(),
                        String.valueOf((Double.parseDouble(mealDetailsModel.get(0).getTotalAmoutOfMeal()) / mealDetailsModel.get(0).getItemCount()) * (mealDetailsModel.get(0).getItemCount() + 1)),
                           true,
                           mealDetailsModel.get(0).getData());
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDb.saveOrderHistry().insertOrUpdate(orderSaveModel);
                        if (MenuFragment.class!=null && MenuFragment.notifyMenuListOnFeagmentInterface!=null)
                        {
                            MenuFragment.notifyMenuListOnFeagmentInterface.onNotify();
                        }
                    }
                });
                }


                dismiss();
                break;

            case R.id.choose_again:
                if (RestaurantDetailsActivity.class!=null && RestaurantDetailsActivity.callMenueDialogInterface!=null)
                {
                    RestaurantDetailsActivity.callMenueDialogInterface.onNotify(mealDetailsModel.get(0).getMealID());
                }
                dismiss();
                break;


            default:
                break;
        }
    }
}
