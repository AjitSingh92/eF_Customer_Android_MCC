package com.lexxdigital.easyfooduserapps.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;

import java.util.List;

public class ChooseLastCustnizationDialog extends DialogFragment implements View.OnClickListener {

    Context context;
    int childPosition;
    int parentPosition;
    MenuCategory menuCategory;
    int action;
    View view;
    OnChooseLastCustnizationListener onChooseLastCustnizationListener;
    int itemCount;
    TextView qtyTextView;
    List<MenuProduct> menuProduct;
    Boolean isSubCat;
    FirebaseAnalytics mFirebaseAnalytics;

    public interface OnChooseLastCustnizationListener {
        void onRepeatLast(int position, int parentPosition, List<MenuProduct> menuProduct, View view, TextView qtyTextView, MenuCategory menuCategory, int itemCount, Boolean isSubCat, int action);

        void onChooseAgain(int position, int parentPosition, List<MenuProduct> menuProduct, View view, TextView qtyTextView, MenuCategory menuCategory, int itemCount, Boolean isSubCat, int action);
    }

    public static ChooseLastCustnizationDialog newInstance(Context context, List<MenuProduct> menuProduct, int itemCount, View view, TextView qtyTextView, int parentPosition, int childPosition, MenuCategory menuCategory, Boolean isSubCat, int action, OnChooseLastCustnizationListener onChooseLastCustnizationListener) {
        ChooseLastCustnizationDialog c = new ChooseLastCustnizationDialog();
        c.context = context;
        c.view = view;
        c.childPosition = childPosition;
        c.parentPosition = parentPosition;
        c.menuProduct = menuProduct;
        c.itemCount = itemCount;
        c.menuCategory = menuCategory;
        c.isSubCat = isSubCat;
        c.action = action;
        c.qtyTextView = qtyTextView;
        c.onChooseLastCustnizationListener = onChooseLastCustnizationListener;
        return c;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.choose_last_customization, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        TextView lastItemName = (TextView) view.findViewById(R.id.last_item_name);
        if (isSubCat) {
            lastItemName.setText(menuCategory.getMenuSubCategory().get(parentPosition).getMenuProducts().get(childPosition).getProductName());
        } else {
            if (menuCategory.getMeal() != null) {
                lastItemName.setText(menuCategory.getMeal().get(childPosition).getMealName());

            } else {
                lastItemName.setText(menuCategory.getMenuProducts().get(childPosition).getProductName());
            }
        }
        view.findViewById(R.id.cross_tv).setOnClickListener(this);
        view.findViewById(R.id.repeat_last).setOnClickListener(this);
        view.findViewById(R.id.choose_again).setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(getDialog().getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.85f);
        int dialogWindowHeight = (int) (displayHeight * 0.90f);
        layoutParams.width = dialogWindowWidth;
        getDialog().getWindow().setAttributes(layoutParams);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cross_tv:
                dismiss();
                break;
            case R.id.repeat_last:
                if (onChooseLastCustnizationListener != null) {
                    onChooseLastCustnizationListener.onRepeatLast(childPosition, parentPosition, menuProduct, view, qtyTextView, menuCategory, itemCount, isSubCat, action);
                }
                dismiss();
                break;
            case R.id.choose_again:
                if (onChooseLastCustnizationListener != null) {
                    onChooseLastCustnizationListener.onChooseAgain(childPosition, parentPosition, menuProduct, view, qtyTextView, menuCategory, itemCount, isSubCat, action);
                }
                dismiss();
                break;
            default:

                break;
        }
    }

}