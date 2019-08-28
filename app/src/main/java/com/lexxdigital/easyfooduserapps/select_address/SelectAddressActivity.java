package com.lexxdigital.easyfooduserapps.select_address;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.adapters.SelectAddressAdapter;
import com.lexxdigital.easyfooduserapps.api.AddressListInterface;
import com.lexxdigital.easyfooduserapps.model.AddressList;
import com.lexxdigital.easyfooduserapps.model.address_list_request.AddressListRequest;
import com.lexxdigital.easyfooduserapps.model.address_list_response.AddressListResponse;
import com.lexxdigital.easyfooduserapps.utility.ApiClient;
import com.lexxdigital.easyfooduserapps.utility.GlobalValues;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectAddressActivity extends AppCompatActivity implements SelectAddressAdapter.PositionInterface {

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

    SelectAddressAdapter mSelectAddressAdapter;
    ArrayList<String> check = new ArrayList<>();
    SelectAddressAdapter.PositionInterface mPositionInterface;
    ArrayList<String> allReadyCheck = new ArrayList<>();
    private GlobalValues val;
    private Dialog dialog;
    private List<AddressList> addressList = new ArrayList<AddressList>();
    int position = 0;
    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        ButterKnife.bind(this);
        initView();
        getAddressList();
    }

    public void initView() {
        for (int i = 0; i < addressList.size(); i++) {
            check.add("0");
        }
        allReadyCheck.add("0");

        mPositionInterface = this;
        mSelectAddressAdapter = new SelectAddressAdapter(getApplicationContext(), mPositionInterface, check, addressList);
        @SuppressLint("WrongConstant")
        LinearLayoutManager horizontalLayoutManagaer2 = new LinearLayoutManager(SelectAddressActivity.this, LinearLayoutManager.VERTICAL, false);
        addreshList.setLayoutManager(horizontalLayoutManagaer2);
        addreshList.setAdapter(mSelectAddressAdapter);
    }

    @Override
    public void onClickPos(int pos, ArrayList<String> check) {
        if (check.contains("1")) {
            for (int i = 0; i < addressList.size(); i++) {
                check.set(i, "0");
            }
        }
        check.set(pos, "1");
        position = pos;
        mSelectAddressAdapter.notifyDataSetChanged();

    }

    public void getAddressList() {

        AddressListInterface apiInterface = ApiClient.getClient(SelectAddressActivity.this).create(AddressListInterface.class);
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
                        mSelectAddressAdapter.notifyDataSetChanged();

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

    @Override
    protected void onResume() {
        super.onResume();
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
