package com.easyfoodcustomer.dialogs;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.easyfoodcustomer.utility.PrefManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.easyfoodcustomer.R;
import com.easyfoodcustomer.adapters.BillingAddressDialogAdapter;
import com.easyfoodcustomer.adapters.RecyclerLayoutManager;
import com.easyfoodcustomer.add_manual_address.AddAddressManualActivity;
import com.easyfoodcustomer.api.AddressListInterface;
import com.easyfoodcustomer.model.AddressList;
import com.easyfoodcustomer.model.address_list_request.AddressListRequest;
import com.easyfoodcustomer.model.address_list_response.AddressListResponse;
import com.easyfoodcustomer.utility.ApiClient;
import com.easyfoodcustomer.utility.GlobalValues;
import com.easyfoodcustomer.utility.SharedPreferencesClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.easyfoodcustomer.utility.UserContants.AUTH_TOKEN;

public class BillingAddressDialogFragment extends DialogFragment implements
        View.OnClickListener, BillingAddressDialogAdapter.OnAddressSelected {
    Context context;
    private GlobalValues val;
    private List<AddressList> addressList = new ArrayList<AddressList>();
    BillingAddressDialogAdapter addressDialogAdapter;
    ImageView ivClose;
    RecyclerView recyclerViewList;
    RecyclerLayoutManager recyclerLayoutManager;
    SharedPreferencesClass sharePre;
    Activity activity;
    OnAddressDialogListener onAddressDialogListener;
    LinearLayout llAddAddress;
    ProgressBar progressBar;
    Boolean isDelivery = true;
    Boolean isAddressSelected = false;
    TextView tvEmpty;
    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onAddressSelect(int position, AddressList address) {
        val.setAddress1(address.getAddressOne());
        val.setAddress2(address.getAddressTwo());
        val.setCity(address.getCity());
        val.setPostalCode(address.getPostCode());
        dismiss();
    }

    public interface OnAddressDialogListener {
        void onAddressDialogDismiss(Boolean isItem);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (onAddressDialogListener != null) {
            onAddressDialogListener.onAddressDialogDismiss(isAddressSelected);
        }
        super.onDismiss(dialog);
    }

    public BillingAddressDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    // TODO: Rename and change types and number of parameters
    public static BillingAddressDialogFragment newInstance(Context context, Boolean isDelivery, OnAddressDialogListener onAddressDialogListener) {
        BillingAddressDialogFragment f = new BillingAddressDialogFragment();
        f.context = context;
        f.isDelivery = isDelivery;
        f.onAddressDialogListener = onAddressDialogListener;

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.address_dailog_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        val = (GlobalValues) getActivity().getApplication();
        sharePre = new SharedPreferencesClass(context);
        progressBar = view.findViewById(R.id.progressBar);
        initView(view);


    }

    private void initView(View view) {
        ivClose = view.findViewById(R.id.cross_tv);
        tvEmpty = view.findViewById(R.id.tv_Empty);
        ivClose.setOnClickListener(this);
        llAddAddress = view.findViewById(R.id.doneLL);
        llAddAddress.setOnClickListener(this);

        recyclerViewList = view.findViewById(R.id.addreshList);

        recyclerLayoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
        recyclerLayoutManager.setScrollEnabled(true);
        recyclerViewList.setLayoutManager(recyclerLayoutManager);

        getAddressList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cross_tv:
                dismiss();
                break;
            case R.id.doneLL:
                startActivityForResult(new Intent(context, AddAddressManualActivity.class).putExtra("callfrom", "Basket"), 105);

                break;


        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 105 && resultCode == ((AppCompatActivity) context).RESULT_OK) {
            recyclerViewList.setVisibility(View.GONE);
            getAddressList();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();


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


    public void getAddressList() {
        progressBar.setVisibility(View.VISIBLE);
        AddressListInterface apiInterface = ApiClient.getClient(context).create(AddressListInterface.class);
        AddressListRequest request = new AddressListRequest();
        request.setCustomerId(val.getLoginResponse().getData().getUserId());

        Call<AddressListResponse> call3 = apiInterface.mLogin(PrefManager.getInstance(context).getPreference(AUTH_TOKEN, ""),request);
        call3.enqueue(new Callback<AddressListResponse>() {
            @Override
            public void onResponse(Call<AddressListResponse> call, Response<AddressListResponse> response) {
                progressBar.setVisibility(View.GONE);

                try {


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
                            addressDialogAdapter = new BillingAddressDialogAdapter(context, addressList, BillingAddressDialogFragment.this);
                            recyclerViewList.setVisibility(View.VISIBLE);
                            tvEmpty.setVisibility(View.GONE);
                            recyclerViewList.setAdapter(addressDialogAdapter);
                        } else {
                            recyclerViewList.setVisibility(View.GONE);
                            tvEmpty.setVisibility(View.VISIBLE);

                        }

                    } else {

                    }
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());

                }

            }

            @Override
            public void onFailure(Call<AddressListResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

            }
        });
    }


}