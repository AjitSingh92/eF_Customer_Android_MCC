package com.lexxdigital.easyfooduserapps.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.adapters.RecyclerLayoutManager;
import com.lexxdigital.easyfooduserapps.adapters.menu_adapter.AgainAdapter;
import com.lexxdigital.easyfooduserapps.adapters.menu_adapter.OneItemMultiTimeListener;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;

import java.util.List;

public class AgainFragment extends DialogFragment implements View.OnClickListener, AgainAdapter.OnItemUpdate {
    Context context;
    int againParentPosition;
    int againChildPosition;
    List<MenuProduct> menuProduct;
    View view;
    TextView qtyTetView, tvToDelete;
    LinearLayout llAction;
    MenuCategory menuCategory;
    int itemCount;
    int action;
    RecyclerView productModifierView;
    RecyclerLayoutManager productModifierLayoutManager;
    AgainAdapter againAdapter;
    TextView msg;
    OneItemMultiTimeListener oneItemMultiTimeListener;
    FirebaseAnalytics mFirebaseAnalytics;

    public AgainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        oneItemMultiTimeListener = (OneItemMultiTimeListener) context;
    }

    // TODO: Rename and change types and number of parameters
    public static AgainFragment newInstance(Context context, int childPosition, int parentPosition, List<MenuProduct> menuProduct, View view, TextView qtyTextView, MenuCategory menuCategory, int itemCount, int action) {
        AgainFragment f = new AgainFragment();
        f.context = context;
        f.againParentPosition = parentPosition;
        f.againChildPosition = childPosition;
        f.menuProduct = menuProduct;
        f.view = view;
        f.qtyTetView = qtyTextView;
        f.menuCategory = menuCategory;
        f.itemCount = itemCount;
        f.action = action;


        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_again, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        msg = view.findViewById(R.id.tv_msg);
        productModifierView = view.findViewById(R.id.list);
        tvToDelete = view.findViewById(R.id.tv_to_delete);
        llAction = view.findViewById(R.id.ll_action);
        productModifierLayoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
        productModifierLayoutManager.setScrollEnabled(true);
        productModifierView.setLayoutManager(productModifierLayoutManager);
        againAdapter = new AgainAdapter(context, action, this);
        againAdapter.clearData();
        productModifierView.setAdapter(againAdapter);
        againAdapter.addItem(menuProduct);

        if (againAdapter.getItemCount() == 0) {
            msg.setVisibility(View.VISIBLE);
        } else {
            msg.setVisibility(View.GONE);
        }
        if (action == 1) {
            tvToDelete.setText(" - to delete the product.");
        } else if (action == 2) {
            tvToDelete.setText(" + to add the product.");
        } else {
            llAction.setVisibility(View.GONE);
        }

        view.findViewById(R.id.tv_Close).setOnClickListener(this);
        view.findViewById(R.id.tv_Ok).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_Close:
                dismiss();
                break;
            case R.id.tv_Ok:
                if (oneItemMultiTimeListener != null) {
                    oneItemMultiTimeListener.onMultiTimeItemChange(againChildPosition, againParentPosition, menuProduct, view, qtyTetView, menuCategory, itemCount, action);
                }
                dismiss();
                break;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();


    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (oneItemMultiTimeListener != null) {
            oneItemMultiTimeListener.onMultiTimeItemChange(againChildPosition, againParentPosition, menuProduct, view, qtyTetView, menuCategory, itemCount, action);
        }
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
        int dialogWindowWidth = (int) (displayWidth * 0.95f);
        int dialogWindowHeight = (int) (displayHeight * 0.95f);
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        getDialog().getWindow().setAttributes(layoutParams);
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
    }

    @Override
    public void onItemUpdate() {
        if (againAdapter != null) {
            if (againAdapter.getItemCount() == 0) {
                msg.setVisibility(View.VISIBLE);
            }
        }
    }
}