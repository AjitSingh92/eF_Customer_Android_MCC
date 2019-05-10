package com.lexxdigital.easyfooduserapp.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lexxdigital.easyfooduserapp.R;
import com.lexxdigital.easyfooduserapp.adapters.RecyclerLayoutManager;
import com.lexxdigital.easyfooduserapp.adapters.SearchPostalAddressAdapter;
import com.lexxdigital.easyfooduserapp.model.postal_code_address.PostalCodeAddRes;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class PostalAddressListDialogFragment extends DialogFragment implements
        View.OnClickListener, SearchPostalAddressAdapter.OnAddressSelected {
    private List<PostalCodeAddRes.Datum> postalAddres = new ArrayList<>();
    Context context;
    RecyclerView recyclerView;
    SearchPostalAddressAdapter adapter;
    ImageView tvCross;
    int position;
    OnFragmentInteractionListener listener;
    Boolean isAddressSelected = false;
    public PostalAddressListDialogFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PostalAddressListDialogFragment newInstance(Context context, List<PostalCodeAddRes.Datum> postalAddres, OnFragmentInteractionListener listener) {
        PostalAddressListDialogFragment fragment = new PostalAddressListDialogFragment();
        fragment.context = context;
        fragment.listener = listener;
        fragment.postalAddres = postalAddres;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_postal_address_list_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_postal_address);
        tvCross = view.findViewById(R.id.cross_tv);
        tvCross.setOnClickListener(this);
        initView();
    }

    void initView() {
        adapter= new SearchPostalAddressAdapter(context,postalAddres,PostalAddressListDialogFragment.this);
        RecyclerLayoutManager recyclerLayoutManager;
        recyclerLayoutManager = new RecyclerLayoutManager(1,RecyclerLayoutManager.VERTICAL);
        recyclerLayoutManager.setScrollEnabled(true);
        recyclerView.setLayoutManager(recyclerLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }



    @Override
    public void onClick(View v) {
        dismiss();
    }

    @Override
    public void onAddressSelect(int position, List<PostalCodeAddRes.Datum> postalAddres) {

        this.position = position;
        this.postalAddres = postalAddres;
       // Log.e(TAG, "onAddressSelect fragment: "+postalAddres.get(position).getPostcode() );
        String selectAddress = postalAddres.get(position).getLine1()+","+postalAddres.get(position).getPostcode();

        Log.e("postAddressFragment", "onFragmentInteraction: "+ postalAddres.size()+"//> "+selectAddress);

        dismiss();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int position,List<PostalCodeAddRes.Datum> postalAddres,Boolean isItem);
    }
    @Override
    public void onDismiss(DialogInterface dialog) {
        if (listener != null){
            listener.onFragmentInteraction(position,postalAddres,isAddressSelected);
        }
        super.onDismiss(dialog);

    }
}
