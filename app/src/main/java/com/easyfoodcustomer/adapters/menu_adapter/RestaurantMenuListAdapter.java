package com.easyfoodcustomer.adapters.menu_adapter;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Paint;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.easyfoodcustomer.cart_db.tables.MenuProducts;
import com.easyfoodcustomer.R;
import com.easyfoodcustomer.cart_db.DatabaseHelper;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.RestaurantMenuList;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.SpecialOffer;
import com.easyfoodcustomer.utility.GlobalValues;
import com.easyfoodcustomer.viewmodel.MenuProductViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RestaurantMenuListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    MenuProductViewModel menuProductViewModel;
    private List<RestaurantMenuList> mMenuDataItem;
    private Context context;
    ItemClickListener menuItemClickListener;
    int posMinus = 0;
    DatabaseHelper db;
    List<RestaurantCategoryAdapter> restaurantCategoryAdapters;
    Activity activity;
    HashMap<Integer, Boolean> isItemVisible;
    private boolean isClosed;
    private int selectedPosition = -1;

    public RestaurantMenuListAdapter(Activity activity, Context context, MenuProductViewModel menuProductViewModel, ItemClickListener menuItemClickListener, boolean isClosed) {
        this.activity = activity;
        this.context = context;
        this.menuProductViewModel = menuProductViewModel;
        this.menuItemClickListener = menuItemClickListener;
        mMenuDataItem = new ArrayList<>();
        db = new DatabaseHelper(context);
        restaurantCategoryAdapters = new ArrayList<>();
        isItemVisible = new HashMap<>();
        this.isClosed = isClosed;
//        menuProductViewModel = ViewModelProviders.of((FragmentActivity)context).get(MenuProductViewModel.class);
    }

    public void addItem(List<RestaurantMenuList> mMenuDataItem) {
        this.mMenuDataItem.addAll(mMenuDataItem);
        notifyItemInserted(this.mMenuDataItem.size());
    }

    public void addItem(RestaurantMenuList mMenuDataItem) {
        this.mMenuDataItem.add(mMenuDataItem);
        notifyItemInserted(this.mMenuDataItem.size());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                OfferItemViewHolder currentOrderViewHolder = (OfferItemViewHolder) holder;
                currentOrderViewHolder.bindData(position);
                break;
            case 1:
                MenuItemViewHolder comboProductListingViewHolder = (MenuItemViewHolder) holder;
                comboProductListingViewHolder.bindData(position);
                break;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {
        switch (viewtype) {
            case 0:
                return new OfferItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_parent_child_listing, viewGroup, false));
            case 1:
                return new MenuItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_parent_child_listing, viewGroup, false));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (mMenuDataItem.get(position).getMenuCategories() == null) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return mMenuDataItem.size();
    }

    class OfferItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView catName, catDescp;
        RecyclerView childItemView;
        private final ImageView dropdownImg;

        public OfferItemViewHolder(@NonNull View itemView) {
            super(itemView);
            catName = itemView.findViewById(R.id.tv_catName);
            catDescp = itemView.findViewById(R.id.tv_catDesc);
            dropdownImg = itemView.findViewById(R.id.dropdownImg);
            childItemView = itemView.findViewById(R.id.list_childItemView);
            childItemView.setVisibility(View.GONE);
            posMinus++;
            itemView.setOnClickListener(this);
        }

        private void bindData(int position) {

            catName.setText("Special Offer");
            List<SpecialOffer> specialOffers = mMenuDataItem.get(position).getSpecialOffers();
            RecyclerLayoutManager layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
            layoutManager.setScrollEnabled(false);
            childItemView.setLayoutManager(layoutManager);
            SpecialOffersMenuAdapter specialOffersMenuAdapter = new SpecialOffersMenuAdapter(context, getLayoutPosition(), menuItemClickListener, isClosed);
            specialOffersMenuAdapter.setHideDetail(false);
            childItemView.setAdapter(specialOffersMenuAdapter);
            specialOffersMenuAdapter.addItem(specialOffers);
        }

        @Override
        public void onClick(View view) {
            if (childItemView.getVisibility() == View.VISIBLE) {
                childItemView.setVisibility(View.GONE);
                dropdownImg.setRotation(270f);
            } else {
                childItemView.setVisibility(View.VISIBLE);
                dropdownImg.setRotation(360f);
            }
        }
    }

    class MenuItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView catName, catDescp, tvReadmore;
        private final ImageView dropdownImg;
        private LinearLayout llDescription;
        private RecyclerView childItemView, childItemView2;
        ProgressBar progressBar;

        public MenuItemViewHolder(@NonNull View itemView) {
            super(itemView);
            catName = itemView.findViewById(R.id.tv_catName);
            tvReadmore = itemView.findViewById(R.id.tv_readmore);
            catDescp = itemView.findViewById(R.id.tv_catDesc);
            llDescription = itemView.findViewById(R.id.ll_description);
            dropdownImg = itemView.findViewById(R.id.dropdownImg);
            progressBar = itemView.findViewById(R.id.progressBar);
            childItemView = itemView.findViewById(R.id.list_childItemView);
            childItemView2 = itemView.findViewById(R.id.list_childItemView2);
            childItemView.setVisibility(View.GONE);
            childItemView2.setVisibility(View.GONE);
            itemView.setOnClickListener(this);
        }

        private void bindData(int position) {
            final int mPosition = position;
            final MenuCategory dataItem = mMenuDataItem.get(position).getMenuCategories();
           /* if (position == 0)
                catName.setText(String.valueOf(dataItem.getDelivery()));
            else*/
            catName.setText(dataItem.getMenuCategoryName());
           /* if (dataItem.getMenuCategoryDescription() != null && !dataItem.getMenuCategoryDescription().isEmpty()) {
                catDescp.setText(dataItem.getMenuCategoryDescription());
                catDescp.setVisibility(View.VISIBLE);
            } else {
                catDescp.setVisibility(View.GONE);
            }*/


            if (dataItem.getMenuCategoryDescription() != null && !dataItem.getMenuCategoryDescription().isEmpty()) {
                String description = dataItem.getMenuCategoryDescription().replaceAll("</br>", "\n");
                description = description.replaceAll("/r", "");
                description = description.replaceAll("<br/>", "\n");
                description = description.replaceAll("<br>", "\n");
                //catDescp.setText(description);
                SpannableString ss = new SpannableString(description);
                setDescriptionLayout(ss, catDescp, tvReadmore);
                // catDescp.setText(dataItem.getMenuCategoryDescription());


                llDescription.setVisibility(View.VISIBLE);
            } else {
                llDescription.setVisibility(View.GONE);

            }

            RecyclerLayoutManager layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
            layoutManager.setScrollEnabled(false);
            RecyclerLayoutManager layoutManager2 = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
            layoutManager2.setScrollEnabled(false);
            childItemView.setLayoutManager(layoutManager);
            childItemView2.setLayoutManager(layoutManager2);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    MenuProducts.MenuProductsTable menuProducts = GlobalValues.getInstance().getDb().menuProductMaster().getMenuProduct(mMenuDataItem.get(mPosition).getMenuCategories().getMenuCategoryId());
                    if (menuProducts != null) {
                        if (menuProducts.getMeal() != null && menuProducts.getMeal().size() > 0 && menuProducts.getMenuProducts() != null && menuProducts.getMenuProducts().size() > 0) {
                            dataItem.setMeal(menuProducts.getMeal());
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    RestaurantMealCategoryAdapter restaurantMealCategoryAdapter = new RestaurantMealCategoryAdapter(context, getLayoutPosition(), menuItemClickListener, isClosed);
                                    restaurantMealCategoryAdapter.setHideDetail(false);
                                    childItemView.setAdapter(restaurantMealCategoryAdapter);
                                    final RestaurantMealCategoryAdapter mRestaurantMealCategoryAdapter = restaurantMealCategoryAdapter;

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mRestaurantMealCategoryAdapter.addItem(dataItem);
                                        }
                                    });
                                }
                            });


                            dataItem.setMenuProducts(menuProducts.getMenuProducts());
                            /*----------------------------------*/
                            if (dataItem.getMenuSubCategory() != null) {
                                for (int i = 0; i < dataItem.getMenuSubCategory().size(); i++) {
                                    MenuProducts.MenuProductsTable menuSubCatProducts = GlobalValues.getInstance().getDb().menuProductMaster().getMenuProduct(dataItem.getMenuSubCategory().get(i).getMenuCategoryId());
                                    if (menuSubCatProducts != null) {
                                        dataItem.getMenuSubCategory().get(i).setMenuProducts(menuSubCatProducts.getMenuProducts());
                                    }
                                }
                            }
                            /*----------------------------------*/

                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    RestaurantCategoryAdapter restaurantCategoryAdapter = new RestaurantCategoryAdapter(context, getLayoutPosition(), menuItemClickListener, isClosed);
                                    restaurantCategoryAdapter.setHideDetail(true);
                                    childItemView2.setAdapter(restaurantCategoryAdapter);
                                    final RestaurantCategoryAdapter mRestaurantCategoryAdapter = restaurantCategoryAdapter;
                                    mRestaurantCategoryAdapter.addItem(dataItem);
                                }
                            });


                        } else if (menuProducts.getMeal() != null && menuProducts.getMeal().size() > 0) {
                            dataItem.setMeal(menuProducts.getMeal());
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    RestaurantMealCategoryAdapter restaurantMealCategoryAdapter = new RestaurantMealCategoryAdapter(context, getLayoutPosition(), menuItemClickListener, isClosed);
                                    restaurantMealCategoryAdapter.setHideDetail(false);
                                    childItemView.setAdapter(restaurantMealCategoryAdapter);
                                    final RestaurantMealCategoryAdapter mRestaurantMealCategoryAdapter = restaurantMealCategoryAdapter;

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mRestaurantMealCategoryAdapter.addItem(dataItem);
                                        }
                                    });
                                }
                            });


                        } else {
                            dataItem.setMenuProducts(menuProducts.getMenuProducts());
                            /*----------------------------------*/
                            if (dataItem.getMenuSubCategory() != null) {
                                for (int i = 0; i < dataItem.getMenuSubCategory().size(); i++) {
                                    MenuProducts.MenuProductsTable menuSubCatProducts = GlobalValues.getInstance().getDb().menuProductMaster().getMenuProduct(dataItem.getMenuSubCategory().get(i).getMenuCategoryId());
                                    if (menuSubCatProducts != null) {
                                        dataItem.getMenuSubCategory().get(i).setMenuProducts(menuSubCatProducts.getMenuProducts());
                                    }
                                }
                            }
                            /*----------------------------------*/

                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    RestaurantCategoryAdapter restaurantCategoryAdapter = new RestaurantCategoryAdapter(context, getLayoutPosition(), menuItemClickListener, isClosed);
                                    restaurantCategoryAdapter.setHideDetail(true);
                                    childItemView.setAdapter(restaurantCategoryAdapter);
                                    final RestaurantCategoryAdapter mRestaurantCategoryAdapter = restaurantCategoryAdapter;
                                    mRestaurantCategoryAdapter.addItem(dataItem);
                                }
                            });
                        }
                    }
                }
            }).start();

            progressBar.setVisibility(View.GONE);
            long id = db.getMenuCategoryIfExit(dataItem.getMenuCategoryId());
           /* if (id != -1) {
                childItemView.setVisibility(View.VISIBLE);
                dropdownImg.setRotation(360f);
            } else {*/
                /*if (isItemVisible.get(mPosition) != null && isItemVisible.get(mPosition)) {
                    childItemView.setVisibility(View.VISIBLE);
                    dropdownImg.setRotation(360f);
                } else {
                    childItemView.setVisibility(View.GONE);
                    dropdownImg.setRotation(270f);
                }*/

            if (position == selectedPosition) {
                childItemView.setVisibility(View.VISIBLE);
                childItemView2.setVisibility(View.VISIBLE);
                dropdownImg.setRotation(360f);
            } else {
                childItemView.setVisibility(View.GONE);
                childItemView2.setVisibility(View.GONE);
                dropdownImg.setRotation(270f);
            }

            //  }

        }

        @Override
        public void onClick(View view) {

            if (menuItemClickListener != null) {
                if (childItemView.getVisibility() == View.VISIBLE) {
                    childItemView.setVisibility(View.GONE);
                    dropdownImg.setRotation(270f);
                } else {
                    menuItemClickListener.LoadMenuProduct(getLayoutPosition(), mMenuDataItem.get(getLayoutPosition()).getMenuCategories().getMenuCategoryId(), progressBar);
                    selectedPosition = getLayoutPosition();
                /*if (childItemView.getVisibility() == View.VISIBLE) {
                    childItemView.setVisibility(View.GONE);
                    dropdownImg.setRotation(270f);
                    isItemVisible.put(getLayoutPosition(), false);

                } else {
                    childItemView.setVisibility(View.VISIBLE);
                    dropdownImg.setRotation(360f);
                    isItemVisible.put(getLayoutPosition(), true);

                }*/

                    notifyDataSetChanged();
                }
            }


        }


        private void setDescriptionLayout(SpannableString description, final TextView tvDescriptionbody, final TextView tvReadmore) {


            if (description != null && description.length() > 0) {
                tvDescriptionbody.setText(description);
                tvReadmore.setPaintFlags(tvReadmore.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                tvDescriptionbody.setMaxLines(2);
                tvDescriptionbody.post(new Runnable() {
                    @Override
                    public void run() {
                        int lineCnt = tvDescriptionbody.getLineCount();
                        Log.e("line count", String.valueOf(lineCnt));
                        if (tvDescriptionbody.getLineCount() > 2) {
                            //check = true;
                            tvReadmore.setVisibility(View.VISIBLE);
                            tvReadmore.setText(activity.getResources().getText(R.string.read_more));
                            tvReadmore.setSelected(true);
                        } else {
                            tvReadmore.setVisibility(View.GONE);
                        }
                    }

                });
            } else {
                tvDescriptionbody.setText(activity.getResources().getString(R.string.no_description_available));
            }
            tvReadmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    readMore(tvReadmore, tvDescriptionbody);
                }
            });


        }


        private void readMore(TextView mReadMore, TextView mDescription) {
            if (mReadMore.isSelected()) {
                mReadMore.setText(activity.getString(R.string.read_less));
                mDescription.setMaxLines(Integer.MAX_VALUE);
                mReadMore.setSelected(false);
            } else {
                mReadMore.setText(activity.getString(R.string.read_more));
                mDescription.setMaxLines(2);
                mDescription.setEllipsize(TextUtils.TruncateAt.END);
                mReadMore.setSelected(true);
            }
        }

    }

}