package com.lexxdigital.easyfooduserapp.fragments;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.adapters.previous_order.MyorderAdapter;
import com.lexxdigital.easyfooduserapp.adapters.previous_order.OrderPositionListner;
import com.lexxdigital.easyfooduserapp.api.CancelInterface;
import com.lexxdigital.easyfooduserapp.api.PreviousOrderInterface;
import com.lexxdigital.easyfooduserapp.dashboard.DashboardActivity;
import com.lexxdigital.easyfooduserapp.model.cancelorder.CancelOrderResponse;
import com.lexxdigital.easyfooduserapp.model.cancelorder.CancelRequest;
import com.lexxdigital.easyfooduserapp.model.myorder.PreviousOrderDetail;
import com.lexxdigital.easyfooduserapp.model.myorder.PreviousOrderResponse;
import com.lexxdigital.easyfooduserapp.model.myorder.ReqstPrevOrder;
import com.lexxdigital.easyfooduserapp.order_details_activity.OrderDetailActivity;
import com.lexxdigital.easyfooduserapp.utility.ApiClient;
import com.lexxdigital.easyfooduserapp.utility.Constants;
import com.lexxdigital.easyfooduserapp.utility.GlobalValues;
import com.lexxdigital.easyfooduserapp.utility.SharedPreferencesClass;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.AccessTokenManager.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreviousOrderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OrderPositionListner {

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
    //private List<PreviousOrderResponse.Data.PreviousOrderDetail> previousOrderDetailList=new ArrayList<>();
    // private List<DataList> previousOrderDetails = new ArrayList<DataList>();

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_previous_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
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
              /*  if (swipreferesh != null) {
                    swipreferesh.setRefreshing(true);
                }*/
                try {
                    if (Constants.isInternetConnectionAvailable(300)) {
                        getCardList();
                    } else {
                        dialogNoInternetConnection("Please check internet connection.");
                    }

                } catch (Exception e) {
                    Toast.makeText(mContext, "Server error. Try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // getCardList();
        //initView(previousOrderDetails);
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
                        //bottom of list!
                        //loadMore();
                        if (Constants.isInternetConnectionAvailable(300)) {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initView(List<PreviousOrderDetail> previousOrderDetaillist) {
        try {
            // mPreviousAdapter = new PreviousAdapter(previousOrderDetaillist, mContext);
            myorderAdapter = new MyorderAdapter(previousOrderDetaillist, mContext, (DashboardActivity) getActivity(), orderPositionListner);

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
        swipreferesh.setRefreshing(true);
        PreviousOrderInterface apiInterface = ApiClient.getClient(getContext()).create(PreviousOrderInterface.class);
        String cstomerid = "34c8b734-5455-11e9-9ec9-0657952ed75a";
        String custId = val.getLoginResponse().getData().getUserId();
        //Log.d("previous order", "getCardList: customerId:" + customerId + " custId: " + custId);
        int offset = 0, limit = 50;
        final ReqstPrevOrder request = new ReqstPrevOrder(custId, offset, limit);
        // request.setUserId(val.getLoginResponse().getData().getUserId());
        Call<PreviousOrderResponse> call = apiInterface.mLogin(request);
        call.enqueue(new Callback<PreviousOrderResponse>() {
            @Override
            public void onResponse(Call<PreviousOrderResponse> call, Response<PreviousOrderResponse> response) {
                //List<Data> previousOrderList = (List<Data>) response.body();
                try {
                    dialog.dismiss();
                    if (response.body().getSuccess()) {

                        for (int i = 0; i < response.body().getData().getPreviousOrderDetails().size(); i++) {
                            previousOrderDetails = response.body().getData().getPreviousOrderDetails();
                        }
                        Log.e(TAG, "onResponse:list sizeeeeeeee: " + previousOrderDetails.size());
                        if (response.body().getData().getTotalRecords() > 0) {
                            sharePre.setInt(sharePre.NUMBER_OF_ORDERS, response.body().getData().getTotalRecords());
                        }
                        //mPreviousAdapter.notifyDataSetChanged();
                        initView(previousOrderDetails);
                        emptyScreen();
                    } else {
                        dialog.dismiss();
                        emptyScreen();
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                    emptyScreen();
                    Log.e("Error11 <>>>", ">>>>>" + e.getMessage());
                    //    showDialog("Please try again.");
//                       Toast.makeText(LoginActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                }
                if (swipreferesh != null) {
                    swipreferesh.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<PreviousOrderResponse> call, Throwable t) {
                Log.e("Error12 <>>>", ">>>>>" + t.getMessage());
                if (swipreferesh != null) {
                    swipreferesh.setRefreshing(false);
                }
                emptyScreen();
//                dialog.dismiss();
//                showDialog("Please try again.");
                //    Toast.makeText(LoginActivity.this, "Please try again 2."+t.getMessage(), Toast.LENGTH_SHORT).show();
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
        Log.e("canorder", "cancelOrder:  order no:" + orderNo);
        CancelRequest request = new CancelRequest();
        request.setOrderNumber(orderNo);
        Call<CancelOrderResponse> call = apiInterface.mCancelOrder(request);
        call.enqueue(new Callback<CancelOrderResponse>() {
            @Override
            public void onResponse(Call<CancelOrderResponse> call, Response<CancelOrderResponse> response) {
                try {
                    if (response.body().getSuccess()) {
                        dialog.dismiss();
                        if (Constants.isInternetConnectionAvailable(300)) {
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
        if (Constants.isInternetConnectionAvailable(300)) {
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
        // cancelOrder(order_number);
    }

    void emptyScreen() {
        // below code for display empty Screen
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
            Log.e("PreviousOrderFrag", e.getLocalizedMessage());
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
                if (Constants.isInternetConnectionAvailable(300)) {
                    alertDialog.dismiss();
                    getCardList();
                } else mDialogView.findViewById(R.id.okTv).startAnimation(animShake);

            }
        });

        alertDialog.show();
    }
}
