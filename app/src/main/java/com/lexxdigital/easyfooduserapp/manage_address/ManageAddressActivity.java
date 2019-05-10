package com.lexxdigital.easyfooduserapp.manage_address;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.adapters.AddressSaveAdapter;
import com.lexxdigital.easyfooduserapp.add_address.AddAddressActivity;
import com.lexxdigital.easyfooduserapp.add_manual_address.AddAddressManualActivity;
import com.lexxdigital.easyfooduserapp.api.AddressListInterface;
import com.lexxdigital.easyfooduserapp.api.DelAddressListInterface;
import com.lexxdigital.easyfooduserapp.dashboard.DashboardActivity;
import com.lexxdigital.easyfooduserapp.model.AddressList;
import com.lexxdigital.easyfooduserapp.model.address_list_request.AddressListRequest;
import com.lexxdigital.easyfooduserapp.model.address_list_response.AddressListResponse;
import com.lexxdigital.easyfooduserapp.model.del_address_list_request.DelAddressListRequest;
import com.lexxdigital.easyfooduserapp.model.del_address_list_response.DelAddressListResponse;
import com.lexxdigital.easyfooduserapp.utility.ApiClient;
import com.lexxdigital.easyfooduserapp.utility.Constants;
import com.lexxdigital.easyfooduserapp.utility.GlobalValues;
import com.lexxdigital.easyfooduserapp.utility.SharedPreferencesClass;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageAddressActivity extends AppCompatActivity implements AddressSaveAdapter.PositionInterface, AddressSaveAdapter.DeletePositionInterface {

    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarhide)
    RelativeLayout toolbarhide;
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
    private GlobalValues val;
    private Dialog dialog;
    private List<AddressList> addressList = new ArrayList<AddressList>();
    AddressSaveAdapter.PositionInterface mPositionInterface;
    AddressSaveAdapter.DeletePositionInterface mDeletePositionInterface;
    AddressSaveAdapter mAddressSaveAdapter;
    SharedPreferencesClass sharePre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_address);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Constants.setStatusBarGradiant(ManageAddressActivity.this);
        sharePre = new SharedPreferencesClass(this);
        mPositionInterface = this;
        mDeletePositionInterface = this;
        val = (GlobalValues) getApplicationContext();
        dialog = new Dialog(ManageAddressActivity.this);
        dialog.setTitle("");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ButterKnife.bind(this);
        initView();
        dialog.show();
        getAddressList();
    }

    @OnClick({R.id.doneLL, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                break;
            case R.id.doneLL:
                Constants.switchActivity(ManageAddressActivity.this, AddAddressActivity.class);
                break;
        }

    }

    private void initView() {

        mAddressSaveAdapter = new AddressSaveAdapter(getApplicationContext(), mPositionInterface, mDeletePositionInterface, addressList);


        @SuppressLint("WrongConstant")
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        addreshList.setLayoutManager(horizontalLayoutManagaer);
        addreshList.setAdapter(mAddressSaveAdapter);
    }


    @Override
    public void onClickPos(int pos) {
        Intent intent = new Intent(ManageAddressActivity.this, AddAddressManualActivity.class);
        intent.putExtra(Constants.EDIT_ADDRESS, Constants.EDIT_ADDRESS);
        intent.putExtra(Constants.ADDRESS, (Serializable) addressList.get(pos));
        Constants.switchActivityWithIntent(ManageAddressActivity.this, intent);

    }

    public void getAddressList() {
        AddressListInterface apiInterface = ApiClient.getClient(this).create(AddressListInterface.class);
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
                            addressList.add(new AddressList(response.body().getData().getAddresses().get(i).getId(), response.body().getData().getAddresses().get(i).getCustomerId(), response.body().getData().getAddresses().get(i).getAddress1(), response.body().getData().getAddresses().get(i).getAddress2(), response.body().getData().getAddresses().get(i).getCity(), response.body().getData().getAddresses().get(i).getPostCode(), response.body().getData().getAddresses().get(i).getCountry(), response.body().getData().getAddresses().get(i).getAddressType(), response.body().getData().getAddresses().get(i).getIsDefault(), response.body().getData().getAddresses().get(i).getIsDelivering()));
                        }
                        mAddressSaveAdapter.notifyDataSetChanged();
                        mAddressSaveAdapter.notify();
                    } else {

                    }
                } catch (Exception e) {
                    dialog.hide();

                }
            }

            @Override
            public void onFailure(Call<AddressListResponse> call, Throwable t) {
                dialog.hide();
            }
        });
    }

    public void delAddressList(String id, final int pos) {
        DelAddressListInterface apiInterface = ApiClient.getClient(this).create(DelAddressListInterface.class);
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
//                        Toast.makeText(ManageAddressActivity.this,
//                                "Your Message deleted" + pos, Toast.LENGTH_LONG).show();
                        sharePre.clear(sharePre.DEFAULT_ADDRESS);
                        sharePre.clear(sharePre.BILLING_ADDRESS);

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


        AddressList list = addressList.get(pos);
        showDialog("Do you want to delete " + list.getAddressType() + " address?", "\n" + list.getAddressOne() + " " + list.getAddressTwo(), list.getID(), pos);
    }

    public void showDialog(String title, String msg, final String addressId, final int pos) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(ManageAddressActivity.this);
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }


    @Override
    protected void onResume() {
        super.onResume();
        dialog.show();
        getAddressList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dialog.dismiss();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dialog.dismiss();
    }
}
