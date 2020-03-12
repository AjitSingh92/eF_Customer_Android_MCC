package com.lexxdigital.easyfooduserapps.fragments;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.adapters.AddressSaveAdapter;
import com.lexxdigital.easyfooduserapps.add_address.AddAddressActivity;
import com.lexxdigital.easyfooduserapps.add_manual_address.AddAddressManualActivity;
import com.lexxdigital.easyfooduserapps.api.AddressListInterface;
import com.lexxdigital.easyfooduserapps.api.DelAddressListInterface;
import com.lexxdigital.easyfooduserapps.model.AddressList;
import com.lexxdigital.easyfooduserapps.model.address_list_request.AddressListRequest;
import com.lexxdigital.easyfooduserapps.model.address_list_response.AddressListResponse;
import com.lexxdigital.easyfooduserapps.model.del_address_list_request.DelAddressListRequest;
import com.lexxdigital.easyfooduserapps.model.del_address_list_response.DelAddressListResponse;
import com.lexxdigital.easyfooduserapps.utility.ApiClient;
import com.lexxdigital.easyfooduserapps.utility.Constants;
import com.lexxdigital.easyfooduserapps.utility.GlobalValues;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class ManageAddressFragment extends Fragment implements AddressSaveAdapter.PositionInterface,
        AddressSaveAdapter.DeletePositionInterface, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.textheader)
    TextView textheader;
    @BindView(R.id.addreshList)
    RecyclerView addreshList;
    @BindView(R.id.addmore_add)
    TextView addmoreAdd;
    @BindView(R.id.checkOutTv)
    TextView checkOutTv;
    @BindView(R.id.doneLL)
    LinearLayout doneLL;

    @BindView(R.id.llbotom)
    LinearLayout llbotom;
    Unbinder unbinder;
    Context mContext;
    TextView txtToolbarTitle, addMoareAdd;
    @BindView(R.id.swipreferesh)
    SwipeRefreshLayout swipreferesh;
    @BindView(R.id.tv_Empty)
    TextView tvEmpty;


    private GlobalValues val;
    private Dialog dialog;
    private List<AddressList> addressList = new ArrayList<AddressList>();
    AddressSaveAdapter.PositionInterface mPositionInterface;
    AddressSaveAdapter.DeletePositionInterface mDeletePositionInterface;
    AddressSaveAdapter mAddressSaveAdapter;
    FirebaseAnalytics mFirebaseAnalytics;

    @SuppressLint("ValidFragment")
    public ManageAddressFragment(Context mContext, TextView title) {
        this.mContext = mContext;
        this.txtToolbarTitle = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage_address, container, false);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
        unbinder = ButterKnife.bind(this, view);
        val = (GlobalValues) mContext;
        dialog = new Dialog(getActivity());
        mPositionInterface = this;
        mDeletePositionInterface = this;
        dialog.setTitle("");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        addMoareAdd = (TextView) view.findViewById(R.id.addmore_add);
        swipreferesh.setOnRefreshListener(this);
        swipreferesh.setColorSchemeResources(R.color.orange,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipreferesh.post(new Runnable() {
            @Override
            public void run() {
                if (swipreferesh != null) {
                    swipreferesh.setRefreshing(true);
                }
                if (Constants.isInternetConnectionAvailable(300)) {
                    getAddressList();
                } else {
                    dialogNoInternetConnection("Please check internet connection.");
                }

            }
        });

        addMoareAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.switchActivity(getActivity(), AddAddressActivity.class);
            }
        });




        return view;
    }

    private void initView() {

        mAddressSaveAdapter = new AddressSaveAdapter(mContext, mPositionInterface, mDeletePositionInterface, addressList);

        @SuppressLint("WrongConstant")
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        addreshList.setLayoutManager(horizontalLayoutManagaer);
        addreshList.setAdapter(mAddressSaveAdapter);
    }

    /*TODO: SEND TO AddAddressManualActivity*/
    @Override
    public void onClickPos(int pos) {
        Intent intent = new Intent(mContext, AddAddressManualActivity.class);
        intent.putExtra(Constants.EDIT_ADDRESS, Constants.EDIT_ADDRESS);
        intent.putExtra(Constants.ADDRESS, (Serializable) addressList.get(pos));
        intent.putExtra("activity", "ManageAddressFragment");
        Constants.switchActivityWithIntent(getActivity(), intent);
    }

    public void getAddressList() {
        try {
            swipreferesh.setRefreshing(true);

        } catch (Exception e) {
            Log.e("swip address", "getAddressList: " + e.getMessage());
        }
        AddressListInterface apiInterface = ApiClient.getClient(mContext).create(AddressListInterface.class);
        AddressListRequest request = new AddressListRequest();
        request.setCustomerId(val.getLoginResponse().getData().getUserId());

        Call<AddressListResponse> call3 = apiInterface.mLogin(request);
        call3.enqueue(new Callback<AddressListResponse>() {
            @Override
            public void onResponse(Call<AddressListResponse> call, Response<AddressListResponse> response) {
                try {
                    dialog.hide();

                    if (response.body().getSuccess()) {
                        addressList.clear();
                        for (int i = 0; i < response.body().getData().getAddresses().size(); i++) {
                            addressList.add(new AddressList(response.body().getData().getAddresses().get(i).getId(),
                                    response.body().getData().getAddresses().get(i).getCustomerId(),
                                    response.body().getData().getAddresses().get(i).getAddress1(),
                                    response.body().getData().getAddresses().get(i).getAddress2(),
                                    response.body().getData().getAddresses().get(i).getCity(),
                                    response.body().getData().getAddresses().get(i).getPostCode(),
                                    response.body().getData().getAddresses().get(i).getCountry(),
                                    ((response.body().getData().getAddresses().get(i).getAddressType().equals("")) ? "" : (response.body().getData().getAddresses().get(i).getAddressType().substring(0, 1).toUpperCase() + response.body().getData().getAddresses().get(i).getAddressType().substring(1))),
                                    response.body().getData().getAddresses().get(i).getIsDefault(),
                                    response.body().getData().getAddresses().get(i).getIsDelivering()));

                        }

                        if (response.body().getData().getAddresses() != null && response.body().getData().getAddresses().size() > 0) {
                            addreshList.setVisibility(View.VISIBLE);
                            tvEmpty.setVisibility(View.GONE);
                            initView();


                        } else {
                            addreshList.setVisibility(View.VISIBLE);
                            tvEmpty.setVisibility(View.VISIBLE);
                        }

                        mAddressSaveAdapter.notifyDataSetChanged();

                    } else {

                    }
                } catch (Exception e) {
                    dialog.hide();

                }
                swipreferesh.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<AddressListResponse> call, Throwable t) {
                dialog.hide();
                swipreferesh.setRefreshing(false);

            }
        });
    }

    public void delAddressList(String id, final int pos) {
        DelAddressListInterface apiInterface = ApiClient.getClient(mContext).create(DelAddressListInterface.class);
        DelAddressListRequest request = new DelAddressListRequest();
        request.setId(id);

        Call<DelAddressListResponse> call3 = apiInterface.mDeleteList(request);
        call3.enqueue(new Callback<DelAddressListResponse>() {
            @Override
            public void onResponse(Call<DelAddressListResponse> call, Response<DelAddressListResponse> response) {
                try {
                    dialog.hide();
                    if (response.body().getSuccess()) {
                        mAddressSaveAdapter.removeAt(pos);
                        mAddressSaveAdapter.notifyDataSetChanged();
                        mAddressSaveAdapter.notify();
                    } else {

                    }
                } catch (Exception e) {
                    dialog.hide();

                }
            }

            @Override
            public void onFailure(Call<DelAddressListResponse> call, Throwable t) {
                dialog.hide();
            }
        });
    }


    @Override
    public void onClickDel(int pos) {

        if (addressList.get(pos).getIsDefault() == 0) {
            AddressList list = addressList.get(pos);
            popUpDeleteAddress(list.getID(), pos);
        } else {
            popUpConfirmation("Defualt address could not deleted");
        }
    }

    public void popUpDeleteAddress(final String addressId, final int pos) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.address_delete_dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView confirm = dialog.findViewById(R.id.btn_confirm);
        TextView cancel = dialog.findViewById(R.id.btn_cancel);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delAddressList(addressId, pos);
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    public void popUpConfirmation(final String message) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.pop_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvMessage = dialog.findViewById(R.id.message);
        tvMessage.setText(message);

        dialog.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.cross_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showDialog(String title, String msg, final String addressId, final int pos) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage(msg);
        builder1.setTitle(title);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog2, int id) {
                        dialog2.cancel();
                        delAddressList(addressId, pos);
                    }
                });
        builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog2, int id) {
                        dialog2.cancel();

                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (dialog != null)
            dialog.show();
        if (Constants.isInternetConnectionAvailable(300)) {
            getAddressList();
        } else {
            dialogNoInternetConnection("Please check internet connection.");
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        dialog.dismiss();
    }

    @Override
    public void onStop() {
        super.onStop();
        dialog.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }


    @Override
    public void onRefresh() {
        if (Constants.isInternetConnectionAvailable(300)) {
            getAddressList();
        } else {
            dialogNoInternetConnection("Please check internet connection.");
        }

    }

    public void dialogNoInternetConnection(String message) {
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View mDialogView = factory.inflate(R.layout.addnote_success_dialog, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setView(mDialogView);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        final Animation animShake = AnimationUtils.loadAnimation(mContext, R.anim.shake);

        TextView tvMessage = mDialogView.findViewById(R.id.message);
        tvMessage.setText(message);
        mDialogView.findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constants.isInternetConnectionAvailable(300)) {
                    alertDialog.dismiss();
                    getAddressList();

                } else mDialogView.findViewById(R.id.okTv).startAnimation(animShake);

            }
        });

        alertDialog.show();
    }
}
