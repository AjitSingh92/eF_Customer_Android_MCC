package com.easyfoodcustomer.fragments;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.easyfoodcustomer.utility.PrefManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.easyfoodcustomer.R;
import com.easyfoodcustomer.adapters.AdapterCardList;
import com.easyfoodcustomer.add_card.AddNewCardActivity;
import com.easyfoodcustomer.api.CardListInterface;
import com.easyfoodcustomer.api.MakeCardDefaultInterface;
import com.easyfoodcustomer.model.card_list_request.CardListRequest;
import com.easyfoodcustomer.model.card_list_response.Card;
import com.easyfoodcustomer.model.card_list_response.CardListResponse;
import com.easyfoodcustomer.model.makeCardDefault.MakeCardDefReq;
import com.easyfoodcustomer.model.makeCardDefault.MakeCardDefaultRes;
import com.easyfoodcustomer.utility.ApiClient;
import com.easyfoodcustomer.utility.GlobalValues;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.easyfoodcustomer.utility.UserContants.AUTH_TOKEN;
import static com.facebook.AccessTokenManager.TAG;


@SuppressLint("ValidFragment")
public class CardsListFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener, AdapterCardList.PositionSortInterface {
    @BindView(R.id.cardList)
    RecyclerView cardList;
    Unbinder unbinder;
    @BindView(R.id.add_new_cardll)
    LinearLayout addNewCardll;
    @BindView(R.id.scroll)
    ScrollView scroll;
    @BindView(R.id.swipreferesh)
    SwipeRefreshLayout swipreferesh;
    private AdapterCardList mAdapter;
    private Context mContext;
    private GlobalValues val;
    private Dialog dialog;
    List<Card> dataList = new ArrayList<>();
    AdapterCardList.PositionSortInterface positionSortInterface;
    FirebaseAnalytics mFirebaseAnalytics;

    @SuppressLint("ValidFragment")

    public CardsListFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cards_list, container, false);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
        unbinder = ButterKnife.bind(this, view);
        val = (GlobalValues) mContext;
        positionSortInterface = this;
        dialog = new Dialog(mContext);
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
                if (swipreferesh != null) {
                    swipreferesh.setRefreshing(true);
                }

                getCardList();
            }
        });
        return view;
    }

    private void initView(List<Card> dataList) {

        mAdapter = new AdapterCardList(getActivity(), dataList, positionSortInterface);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        cardList.setLayoutManager(horizontalLayoutManagaer);
        cardList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.add_new_cardll)
    public void onViewClicked() {
        Intent i = new Intent(getContext(), AddNewCardActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    public void getCardList() {
        swipreferesh.setRefreshing(true);
        CardListInterface apiInterface = ApiClient.getClient(getContext()).create(CardListInterface.class);
        CardListRequest request = new CardListRequest();
        request.setUserId(val.getLoginResponse().getData().getUserId());
        Call<CardListResponse> call3 = apiInterface.mLogin(PrefManager.getInstance(getActivity()).getPreference(AUTH_TOKEN, ""),request);
        call3.enqueue(new Callback<CardListResponse>() {
            @Override
            public void onResponse(Call<CardListResponse> call, Response<CardListResponse> response) {
                try {
                    dialog.hide();
                    if (response.body().getSuccess()) {
                        dataList = response.body().getData().getCards();
                        initView(dataList);
                        if (dataList.size() > 0) {
                            cardList.setVisibility(View.VISIBLE);
                            scroll.setVisibility(View.GONE);
                        } else {
                            cardList.setVisibility(View.GONE);
                            scroll.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (Exception e) {
                    dialog.hide();
                }
                swipreferesh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<CardListResponse> call, Throwable t) {
                swipreferesh.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getCardList();
    }

    @Override
    public void onRefresh() {
        getCardList();
    }

    @Override
    public void onClickSortBy(int pos, List<Card> data) {
        dataList = data;
        // onResume();
        getCardList();
    }

    @Override
    public void onMakeDefaultByButton(int position, Card dataList) {
        makeDefaultCard(dataList.getCardId());
        if (dataList.getIsDefault() == 1) {
            Toast.makeText(mContext, "Card has been unselected as default.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, "Card has been set as default.", Toast.LENGTH_LONG).show();
        }
    }


    void makeDefaultCard(String id) {
        MakeCardDefaultInterface apiInterface = ApiClient.getClient(mContext).create(MakeCardDefaultInterface.class);
        final MakeCardDefReq req = new MakeCardDefReq();
        req.setCardId(id);
        Call<MakeCardDefaultRes> call = apiInterface.mMakeDefault(PrefManager.getInstance(getActivity()).getPreference(AUTH_TOKEN, ""),req);
        call.enqueue(new Callback<MakeCardDefaultRes>() {
            @Override
            public void onResponse(Call<MakeCardDefaultRes> call, Response<MakeCardDefaultRes> response) {
                try {
                    if (response.body().getSuccess()) {
                        getCardList();

                    } else {
                        Toast.makeText(mContext, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }

                } catch (Exception ex) {
                    Log.e(TAG, "onResponse Exception: " + ex.getMessage());
                }
            }

            @Override
            public void onFailure(Call<MakeCardDefaultRes> call, Throwable t) {

            }
        });
    }

}
