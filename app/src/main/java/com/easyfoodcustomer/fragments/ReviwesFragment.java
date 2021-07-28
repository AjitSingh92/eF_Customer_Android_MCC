package com.easyfoodcustomer.fragments;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.easyfoodcustomer.utility.PrefManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.easyfoodcustomer.R;
import com.easyfoodcustomer.adapters.ReatingAdapter;
import com.easyfoodcustomer.restaurant_details.api.ReviewInterface;
import com.easyfoodcustomer.restaurant_details.model.review_request.ReviewRequest;
import com.easyfoodcustomer.restaurant_details.model.review_response.ReviewResponse;
import com.easyfoodcustomer.utility.ApiClient;
import com.easyfoodcustomer.utility.GlobalValues;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.easyfoodcustomer.utility.UserContants.AUTH_TOKEN;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class ReviwesFragment extends Fragment {


    @BindView(R.id.reviewList)
    RecyclerView reviewList;

    ReatingAdapter mReatingAdapter;
    Context mContext;
    @BindView(R.id.overall_rating)
    TextView overallRating;
    @BindView(R.id.on_based)
    TextView onBased;
    @BindView(R.id.on_base)
    TextView onBase;
    @BindView(R.id.tv_noRating)
    TextView tvnoRating;
    @BindView(R.id.rat_5)
    ImageView rat_5;
    private GlobalValues val;
    private Dialog dialog;
    FirebaseAnalytics mFirebaseAnalytics;

    public ReviwesFragment() {
    }

    public ReviwesFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_reviwes, container, false);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
        // Inflate the layout for this fragment

        ButterKnife.bind(this, view);
        val = (GlobalValues) mContext;
        dialog = new Dialog(mContext);
        dialog.setTitle("");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_dialog);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        try {
            overallRating.setText(String.format("%.1f", val.getRestaurantDetailsResponse().getData().getRestaurants().getAvgRating()));
            if (val.getRestaurantDetailsResponse().getData().getRestaurants().getRatingCount() > 1) {
                onBase.setText("Based on " + val.getRestaurantDetailsResponse().getData().getRestaurants().getRatingCount().intValue() + " Reviews");
                onBase.setVisibility(View.VISIBLE);
                onBased.setVisibility(View.GONE);
                // onBased.setText("Based on " + val.getRestaurantDetailsResponse().getData().getRestaurants().getRatingCount().intValue() + " Reviews");
            } else {
                overallRating.setVisibility(View.GONE);
                rat_5.setVisibility(View.GONE);
                onBase.setVisibility(View.GONE);
                onBased.setText("This restaurant is yet to recieve a review. Why not be the first to review this restaurant and help other customers decide where to eat?");
                onBased.setVisibility(View.VISIBLE);

                // onBased.setText("Based On " + val.getRestaurantDetailsResponse().getData().getRestaurants().getRatingCount().intValue() + " Review");
            }

        } catch (Exception e) {

        }
        saveCardDetail(0);
        return view;

    }


    private void initView(ReviewResponse res) {

        reviewList.setVisibility(View.VISIBLE);
        if (val.getRestaurantDetailsResponse() != null) {

            if (res.getData().getReviews() != null && res.getData().getReviews().size() > 0) {
                reviewList.setVisibility(View.VISIBLE);
                tvnoRating.setVisibility(View.GONE);
                overallRating.setVisibility(View.VISIBLE);
                rat_5.setVisibility(View.VISIBLE);
                if (res.getData().getReviews().size() > 1)
                    onBase.setText("Based on " + val.getRestaurantDetailsResponse().getData().getRestaurants().getRatingCount().intValue() + " Reviews");
                else
                    onBase.setText("Based on " + val.getRestaurantDetailsResponse().getData().getRestaurants().getRatingCount().intValue() + " Review");
                onBase.setVisibility(View.VISIBLE);
                onBased.setVisibility(View.GONE);
                // onBased.setText("Based on " + val.getRestaurantDetailsResponse().getData().getRestaurants().getRatingCount().intValue() + " Reviews");

                mReatingAdapter = new ReatingAdapter(mContext, res);

                @SuppressLint("WrongConstant")
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                reviewList.setLayoutManager(horizontalLayoutManagaer);
                reviewList.setNestedScrollingEnabled(false);
                reviewList.setAdapter(mReatingAdapter);
            } else {
                reviewList.setVisibility(View.GONE);
                overallRating.setVisibility(View.GONE);
                rat_5.setVisibility(View.GONE);
                onBase.setVisibility(View.GONE);
                onBased.setText("This restaurant is yet to recieve a review. Why not be the first to review this restaurant and help other customers decide where to eat?");
                onBased.setVisibility(View.VISIBLE);
                // onBased.setText("This restaurant is yet to recieve a review. Why not be the first to review this restaurant and help other customers decide where to eat?");

            }
        }


    }

    public void saveCardDetail(int offset) {
        ReviewInterface apiInterface = ApiClient.getClient(mContext).create(ReviewInterface.class);
        ReviewRequest request = new ReviewRequest();
        request.setRestaurantId(val.getRestaurantDetailsResponse().getData().getRestaurants().getRestaurantId());
        request.setOffset(offset);

        Call<ReviewResponse> call3 = apiInterface.mGetReview(PrefManager.getInstance(getActivity()).getPreference(AUTH_TOKEN, ""), request);
        call3.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                try {
                    if (dialog != null)
                        dialog.hide();
                    if (response.body().getSuccess()) {
                        initView(response.body());
                    } else {

                    }
                } catch (Exception e) {
                    if (dialog != null)
                        dialog.hide();

                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.e("Error12 <>>>", ">>>>>" + t.getMessage());

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
