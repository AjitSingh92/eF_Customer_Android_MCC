package com.easyfoodcustomer.fragments;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easyfoodcustomer.adapters.previous_order.DeleteOderListener;
import com.easyfoodcustomer.adapters.previous_order.DeleteResponse;
import com.easyfoodcustomer.utility.DialogUtils;
import com.easyfoodcustomer.utility.MyDialogUtils;
import com.easyfoodcustomer.utility.OnDialogClickListener;
import com.easyfoodcustomer.utility.PrefManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.easyfoodcustomer.R;
import com.easyfoodcustomer.adapters.previous_order.MyorderAdapter;
import com.easyfoodcustomer.adapters.previous_order.OrderPositionListner;
import com.easyfoodcustomer.api.CancelInterface;
import com.easyfoodcustomer.api.PreviousOrderInterface;
import com.easyfoodcustomer.dashboard.DashboardActivity;
import com.easyfoodcustomer.model.cancelorder.CancelOrderResponse;
import com.easyfoodcustomer.model.cancelorder.CancelRequest;
import com.easyfoodcustomer.model.myorder.PreviousOrderDetail;
import com.easyfoodcustomer.model.myorder.PreviousOrderResponse;
import com.easyfoodcustomer.model.myorder.ReqstPrevOrder;
import com.easyfoodcustomer.utility.ApiClient;
import com.easyfoodcustomer.utility.Constants;
import com.easyfoodcustomer.utility.GlobalValues;
import com.easyfoodcustomer.utility.SharedPreferencesClass;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.easyfoodcustomer.utility.Helper.isInternetOn;
import static com.easyfoodcustomer.utility.UserContants.AUTH_TOKEN;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreviousOrderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OrderPositionListner, DeleteOderListener {

    @BindView(R.id.previousList)
    RecyclerView previousList;
    Unbinder unbinder;
    MyorderAdapter myorderAdapter;
    @BindView(R.id.swipreferesh)
    SwipeRefreshLayout swipreferesh;
    @BindView(R.id.emptyorder)
    LinearLayout emptyorder;
    @BindView(R.id.ll_main)
    LinearLayout llMain;

    private Context mContext;
    private GlobalValues val;
    private Dialog dialog;
    private TextView txtToolbarTitle;
    private List<PreviousOrderDetail> previousOrderDetails = new ArrayList<>();
    boolean isLoading = false;
    OrderPositionListner orderPositionListner;
    SharedPreferencesClass sharePre;
    FirebaseAnalytics mFirebaseAnalytics;
    //BroadcastReceiver broadcastReceiver;

    public PreviousOrderFragment() {
        // Required empty public constructor

    }

    @SuppressLint("ValidFragment")
    public PreviousOrderFragment(Context context, TextView title) {
        this.mContext = context;
        this.txtToolbarTitle = title;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_previous_order, container, false);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
        unbinder = ButterKnife.bind(this, view);
        IntentFilter intentFilter = new IntentFilter("status");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, intentFilter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharePre = new SharedPreferencesClass(mContext);
        val = (GlobalValues) mContext;
        orderPositionListner = this;
        dialog = new Dialog(getActivity());
        dialog.setTitle("");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        swipreferesh.setOnRefreshListener(this);
        swipreferesh.setColorSchemeResources(R.color.orange,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        swipreferesh.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (isInternetOn(getActivity())) {
                        getCardList();
                    } else {
                        dialogNoInternetConnection("Please check internet connection.");
                    }

                } catch (Exception e) {
                    Toast.makeText(mContext, "Server error. Try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        previousList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == previousOrderDetails.size() - 1) {
                        if (isInternetOn(getActivity())) {
                            getCardList();
                            isLoading = true;
                        } else {
                            dialogNoInternetConnection("Please check internet connection.");
                        }

                    }
                }
            }
        });

    }


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (isInternetOn(getActivity())) {
                isLoading = false;
                previousOrderDetails.clear();
                getCardList();
            } else {
                if (swipreferesh != null)
                    swipreferesh.setRefreshing(false);
                dialogNoInternetConnection("Please check internet connection.");
            }

        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initView(List<PreviousOrderDetail> previousOrderDetaillist) {
        try {
            myorderAdapter = new MyorderAdapter(previousOrderDetaillist, mContext, (DashboardActivity) getActivity(), orderPositionListner, this);
            @SuppressLint("WrongConstant")
            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            previousList.setLayoutManager(horizontalLayoutManagaer);
            previousList.setAdapter(myorderAdapter);
            myorderAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            Log.e("Exception", "initView: " + e.getMessage());
        }
    }

    public void getCardList() {
        if (swipreferesh != null)
            swipreferesh.setRefreshing(true);
        PreviousOrderInterface apiInterface = ApiClient.getClient(getContext()).create(PreviousOrderInterface.class);
        String custId = val.getLoginResponse().getData().getUserId();
        int offset = 0, limit = 50;
        final ReqstPrevOrder request = new ReqstPrevOrder(custId, offset, limit);
        Call<PreviousOrderResponse> call = apiInterface.mLogin(PrefManager.getInstance(getActivity()).getPreference(AUTH_TOKEN, ""), request);
        call.enqueue(new Callback<PreviousOrderResponse>() {
            @Override
            public void onResponse(Call<PreviousOrderResponse> call, Response<PreviousOrderResponse> response) {
                try {
                    dialog.dismiss();
                    if (response.body().getSuccess()) {
                        for (int i = 0; i < response.body().getData().getPreviousOrderDetails().size(); i++) {
                            previousOrderDetails = response.body().getData().getPreviousOrderDetails();
                        }

                        if (response.body().getData().getTotalRecords() > 0) {
                            sharePre.setInt(sharePre.NUMBER_OF_ORDERS, response.body().getData().getTotalRecords());
                        }
                        initView(previousOrderDetails);
                        emptyScreen();
                    } else {
                        dialog.dismiss();
                        emptyScreen();
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                    emptyScreen();
                }
                if (swipreferesh != null) {
                    swipreferesh.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<PreviousOrderResponse> call, Throwable t) {
                if (swipreferesh != null) {
                    swipreferesh.setRefreshing(false);
                }
                emptyScreen();
            }

        });
    }

    public void cancelOrder(String orderNo) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle("");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        CancelInterface apiInterface = ApiClient.getClient(mContext).create(CancelInterface.class);
        CancelRequest request = new CancelRequest();
        request.setOrderNumber(orderNo);
        Call<CancelOrderResponse> call = apiInterface.mCancelOrder(PrefManager.getInstance(getActivity()).getPreference(AUTH_TOKEN, ""), request);
        call.enqueue(new Callback<CancelOrderResponse>() {
            @Override
            public void onResponse(Call<CancelOrderResponse> call, Response<CancelOrderResponse> response) {
                try {
                    if (response.body().getSuccess()) {
                        dialog.dismiss();
                        if (isInternetOn(getActivity())) {
                            getCardList();
                            //myorderAdapter.notifyDataSetChanged();
                            Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            dialogNoInternetConnection("Please check internet connection.");
                        }

                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                    Toast.makeText(mContext, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<CancelOrderResponse> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onRefresh() {
        if (isInternetOn(getActivity())) {
            getCardList();
        } else {
            if (swipreferesh != null)
                swipreferesh.setRefreshing(false);
            dialogNoInternetConnection("Please check internet connection.");
        }
    }

    public void showDialog(String msg, final String orderNo) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Confirm",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog2, int id) {
                        cancelOrder(orderNo);
                        dialog2.cancel();


                    }
                });
        builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public void onClickPosition(int pos, String order_number) {
        showDialog("Are you sure Cancel order!", order_number);
    }

    void emptyScreen() {
        try {
            if (previousOrderDetails.size() > 0) {
                if (llMain != null) {
                    llMain.setVisibility(View.VISIBLE);
                    emptyorder.setVisibility(View.GONE);
                }

            } else {
                emptyorder.setVisibility(View.VISIBLE);
                llMain.setVisibility(View.GONE);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }


    public void dialogNoInternetConnection(String message) {
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View mDialogView = factory.inflate(R.layout.addnote_success_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setView(mDialogView);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final Animation animShake = AnimationUtils.loadAnimation(mContext, R.anim.shake);

        TextView tvMessage = mDialogView.findViewById(R.id.message);
        tvMessage.setText(message);
        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetOn(getActivity())) {
                    alertDialog.dismiss();
                    getCardList();
                } else mDialogView.findViewById(R.id.okTv).startAnimation(animShake);

            }
        });

        alertDialog.show();
    }

    @Override
    public void onDelete(int pos, String order_number) {
        if (previousOrderDetails != null && previousOrderDetails.size() > pos) {
            MyDialogUtils.showAlertDialog(getActivity(), "Are you sure you want to delete this order?", new OnDialogClickListener() {
                @Override
                public void onPositiveClick() {
                    if (isInternetOn(getActivity())) {
                        deleteOrder(order_number, val.getLoginResponse().getData().getUserId(), pos);
                    } else {
                        dialogNoInternetConnection("Please check internet connection.");
                    }

                }
            });
        }
    }


    private void deleteOrder(String orderNUm, String customerId, final int position) {

        if (dialog != null)
            dialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("order_num", orderNUm);
        jsonObject.addProperty("customer_id", customerId);
        PreviousOrderInterface apiInterface = ApiClient.getClient(getContext()).create(PreviousOrderInterface.class);
        Call<DeleteResponse> call = apiInterface.deleteOrder(PrefManager.getInstance(getActivity()).getPreference(AUTH_TOKEN, ""), jsonObject);

        Log.e("Enquiry Api Call", "" + call);
        call.enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(@NonNull Call<DeleteResponse> call, @NonNull Response<DeleteResponse> response) {
                try {

                    if (dialog != null)
                        dialog.dismiss();

                    if (response.isSuccessful()) {
                        final DeleteResponse commonResponseBean = response.body();
                        if (commonResponseBean.isSuccess()) {
                            MyDialogUtils.showAlertDialogWithSingleButton(getActivity(), commonResponseBean.getMessage(), new OnDialogClickListener() {
                                @Override
                                public void onPositiveClick() {
                                    if (previousOrderDetails != null && previousOrderDetails.size() > position) {
                                        previousOrderDetails.remove(position);
                                        myorderAdapter.notifyDataSetChanged();

                                    }
                                   /* if (isInternetOn(getActivity())) {
                                        getCardList();
                                    } else {
                                        dialogNoInternetConnection("Please check internet connection.");
                                    }*/
                                }
                            });

                        } else {
                            Toast.makeText(getActivity(), commonResponseBean.getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DeleteResponse> call, @NonNull Throwable throwable) {

                if (dialog != null)
                    dialog.dismiss();
                Toast.makeText(getActivity(), getString(R.string.msg_please_try_later), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
