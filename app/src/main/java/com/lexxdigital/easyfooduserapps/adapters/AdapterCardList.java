package com.lexxdigital.easyfooduserapps.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lexxdigital.easyfooduserapps.R;
import com.lexxdigital.easyfooduserapps.api.CardListInterface;
import com.lexxdigital.easyfooduserapps.api.MakeCardDefaultInterface;
import com.lexxdigital.easyfooduserapps.model.card_list_request.CardDeleteReq;
import com.lexxdigital.easyfooduserapps.model.card_list_response.Card;
import com.lexxdigital.easyfooduserapps.model.card_list_response.CardListResponse;
import com.lexxdigital.easyfooduserapps.model.card_list_response.DeleteCardResponse;
import com.lexxdigital.easyfooduserapps.model.makeCardDefault.MakeCardDefReq;
import com.lexxdigital.easyfooduserapps.model.makeCardDefault.MakeCardDefaultRes;
import com.lexxdigital.easyfooduserapps.utility.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.AccessTokenManager.TAG;

public class AdapterCardList extends RecyclerView.Adapter<AdapterCardList.MyViewHolder> {

    // private ArrayList<Arraylist> dataSet;

    Context mContext;
    private CardListResponse response;
    String id = "";
    String strBrand = "", strBillingAddress = "", strMakeDefault = "Make Default", strDefault = "Default";
    int isDefault = 0;
    Card cardlist;
    List<Card> dataList = new ArrayList<>();
    Animation moveItem;
    PositionSortInterface positionSortInterface;

    public interface PositionSortInterface {
        void onClickSortBy(int pos, List<Card> dataList);

        void onMakeDefaultByButton(int position, Card dataList);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView cardNoOne, cardNoTwo, cardNoThree, cardNofour, cardHolderName,
                expDate, tvBrand, tvMakeDefaultCard, tvBillingAddress;
        ImageView deleteCard, ivBrand;
        LinearLayout layoutcard;
        FrameLayout fm;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.cardNoOne = (TextView) itemView.findViewById(R.id.cardNo_one);
            this.cardNoTwo = (TextView) itemView.findViewById(R.id.cardNo_two);
            this.cardNoThree = (TextView) itemView.findViewById(R.id.cardNo_three);
            this.cardNofour = (TextView) itemView.findViewById(R.id.cardNo_foure);
            this.cardHolderName = (TextView) itemView.findViewById(R.id.holdername);
            this.expDate = (TextView) itemView.findViewById(R.id.exp_date);
            this.layoutcard = itemView.findViewById(R.id.listlayout);
            this.deleteCard = itemView.findViewById(R.id.delete_card);
            this.ivBrand = itemView.findViewById(R.id.brand);
            this.tvBrand = itemView.findViewById(R.id.tv_brand);
            this.tvMakeDefaultCard = itemView.findViewById(R.id.tv_make_default);
            this.tvMakeDefaultCard.setOnClickListener(this);
            this.tvBillingAddress = itemView.findViewById(R.id.tv_billing_address);
            this.fm = itemView.findViewById(R.id.fm);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_make_default:
                    if (positionSortInterface != null) {
                        positionSortInterface.onMakeDefaultByButton(getLayoutPosition(), dataList.get(getLayoutPosition()));
                    }
                    break;
            }
        }
    }

    public AdapterCardList(Context mContext, List<Card> dataList, AdapterCardList.PositionSortInterface positionSortInterface) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.positionSortInterface = positionSortInterface;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_card_list, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        cardlist = dataList.get(listPosition);
        holder.cardNofour.setText(cardlist.getLast4CardNo());
        holder.cardHolderName.setText(cardlist.getCustomerNameOnCard());
        strBrand = cardlist.getBrand();

        String address1 = "", address2 = "", city = "", postal = "", country = "";
        address1 = cardlist.getAddressLine1();
        address2 = cardlist.getAddressLine2();
        city = cardlist.getAddressCity();
        postal = cardlist.getAddressPostCode();

        if (address2 != null && address2.trim().length() > 0) {

            strBillingAddress = address1 + ", " + address2 + (city.equals("") ? "" : ", ") + city + (postal.equals("") ? "" : ", ") + postal;
        } else {
            strBillingAddress = address1 + (city.equals("") ? "" : ", ") + city + (postal.equals("") ? "" : ", ") + postal;
        }


        if (!strBillingAddress.trim().equalsIgnoreCase("")) {

            holder.tvBillingAddress.setText(strBillingAddress);
            holder.tvBillingAddress.setVisibility(View.VISIBLE);
        } else {
            holder.tvBillingAddress.setVisibility(View.GONE);
        }
        Log.e(TAG, "onBindViewHolder: Address:" + address1 + "//>" + address2 + "//>"
                + city + "//>" + postal + "?strBillingAddress//>" + strBillingAddress);

        setBrand(holder, strBrand);
        id = cardlist.getCardId();
        isDefault = cardlist.getIsDefault();

        if (isDefault == 1) {
            holder.tvMakeDefaultCard.setText(strDefault);

        } else {
            holder.tvMakeDefaultCard.setText(strMakeDefault);

        }

        holder.deleteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpDeleteAddress(holder, listPosition, dataList.get(listPosition).getCardId());
                Log.e(TAG, "onClick: cardlist.getCardId():  " + id + "//>>" + dataList.get(listPosition).getCardId() + ", Position:" + listPosition);
                // holder.cardHolderName.startAnimation(moveItem);
            }
        });

    }

    void makeDefaultCard(final MyViewHolder holder, final int pos, String id) {
        MakeCardDefaultInterface apiInterface = ApiClient.getClient(mContext).create(MakeCardDefaultInterface.class);
        final MakeCardDefReq req = new MakeCardDefReq();
        req.setCardId(id);
        Call<MakeCardDefaultRes> call = apiInterface.mMakeDefault(req);
        call.enqueue(new Callback<MakeCardDefaultRes>() {
            @Override
            public void onResponse(Call<MakeCardDefaultRes> call, Response<MakeCardDefaultRes> response) {
                try {
                    if (response.body().getSuccess()) {
                        Toast.makeText(mContext, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();
                        // moveItem= AnimationUtils.loadAnimation(mContext,R.anim.pull_in_left);
                        notifyDataSetChanged();
                        //holder.layoutcard.startAnimation(moveItem);
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

    void deleteCard(final MyViewHolder holder, final int pos, String id) {
        CardListInterface apiInterface = ApiClient.getClient(mContext).create(CardListInterface.class);
        final CardDeleteReq request = new CardDeleteReq();
        request.setCardId(id);

        //request.setCardNo(response.getData().getCards().get(pos).getCardNo());
        Call<DeleteCardResponse> call = apiInterface.deleteCard(request);
        call.enqueue(new Callback<DeleteCardResponse>() {
            @Override
            public void onResponse(Call<DeleteCardResponse> call, Response<DeleteCardResponse> response) {
                try {
                    if (response.body().getSuccess()) {
                        Toast.makeText(mContext, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();
                        // moveItem= AnimationUtils.loadAnimation(mContext,R.anim.pull_in_left);
                        notifyDataSetChanged();
                        //holder.layoutcard.startAnimation(moveItem);
                        dataList.remove(pos);
                        positionSortInterface.onClickSortBy(pos, dataList);
                        //notifyItemRemoved(pos);
                    } else
                        Toast.makeText(mContext, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<DeleteCardResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setBrand(final MyViewHolder holder, String brand) {
        if (brand.trim().equalsIgnoreCase("Visa")) {
            //Log.e(TAG, "setBrand: " + brand);
            holder.fm.setBackgroundResource(R.drawable.bg_visa_card);
            //Glide.with(mContext).load(R.drawable.ic_visa).placeholder(R.drawable.ic_visa).into(holder.ivBrand);
        } else if (brand.trim().equalsIgnoreCase("MasterCard")) {
            //Log.e(TAG, "setBrand: " + brand);
            holder.fm.setBackgroundResource(R.drawable.bg_master_card);
        } else if (brand.trim().equalsIgnoreCase("American Express")) {
            holder.fm.setBackgroundResource(R.drawable.bg_americanexp_card);
        } else if (brand.trim().equalsIgnoreCase("Discover")) {
            holder.fm.setBackgroundResource(R.drawable.db_discover_card);
        } else if (brand.trim().equalsIgnoreCase("Diners Club")) {
            holder.fm.setBackgroundResource(R.drawable.bg_dinner_club_card);
        } else if (brand.trim().equalsIgnoreCase("JCB")) {
            holder.fm.setBackgroundResource(R.drawable.bg_jcb_card);
        } else if (brand.trim().equalsIgnoreCase("UnionPay")) {
            holder.fm.setBackgroundResource(R.drawable.bg_union_pay_card);
        } else if (brand.trim().equalsIgnoreCase("Western Union")) {
            holder.fm.setBackgroundResource(R.drawable.bg_western_union_card);
        } else {
            holder.fm.setBackgroundResource(R.drawable.bg_card);
        }

    }

    public void popUpDeleteAddress(final MyViewHolder holder, final int pos, final String id) {
        final Dialog dialog = new Dialog(mContext);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.address_delete_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView confirm = dialog.findViewById(R.id.btn_confirm);
        TextView msg = dialog.findViewById(R.id.btn_continue);
        TextView cancel = dialog.findViewById(R.id.btn_cancel);
        msg.setText("Are you sure you \n want to remove this card?");
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCard(holder, pos, id);
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
}
