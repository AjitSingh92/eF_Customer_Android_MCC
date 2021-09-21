package com.easyfoodcustomer.adapters.menu_adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.easyfoodcustomer.R;
import com.easyfoodcustomer.cart_db.DatabaseHelper;
import com.easyfoodcustomer.cart_db.tables.ProductSizeAndModifier;
import com.easyfoodcustomer.dialogs.ChooseOrderTypeDialog;
import com.easyfoodcustomer.dialogs.ChooseOrderTypeNewDialog;
import com.easyfoodcustomer.dialogs.MenuDetailNewDialog;
import com.easyfoodcustomer.dialogs.SingleOrderCountDialog;
import com.easyfoodcustomer.fragments.MenuFragment;
import com.easyfoodcustomer.modelsNew.MealDetailsModel;
import com.easyfoodcustomer.restaurant_details.RestaurantDetailsActivity;
import com.easyfoodcustomer.restaurant_details.api.RestaurantDetailsInterface;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuCategory;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuProduct;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MenuProductSize;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.Modifier;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.SizeModifier;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.request.MenuProductSizeModifierRequest;
import com.easyfoodcustomer.roomData.AppDatabase;
import com.easyfoodcustomer.roomData.AppExecutors;
import com.easyfoodcustomer.roomData.OrderSaveModel;
import com.easyfoodcustomer.utility.ApiClient;
import com.easyfoodcustomer.utility.DialogUtils;
import com.easyfoodcustomer.utility.GlobalValues;
import com.easyfoodcustomer.utility.PrefManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.easyfoodcustomer.utility.UserContants.AUTH_TOKEN;
import static com.facebook.FacebookSdk.getApplicationContext;

public class RestaurantCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private List<MenuProduct> mItem;
    private Boolean hideDetail = false;
    private List<MenuCategory> menuSubCategory;
    ItemClickListener menuItemClickListener;
    int parentPosition;
    MenuCategory menuCategory;
    DatabaseHelper db;
    private boolean isClosed;
    private AppDatabase mDb;
    private DialogUtils dialogUtils;

    public RestaurantCategoryAdapter(Context context, int parentPosition, ItemClickListener menuItemClickListener, boolean isClosed) {
        this.context = context;
        this.menuItemClickListener = menuItemClickListener;
        this.parentPosition = parentPosition;
        mDb = AppDatabase.getInstance(getApplicationContext());
        inflater = LayoutInflater.from(context);
        menuSubCategory = new ArrayList<>();
        mItem = new ArrayList<>();
        db = new DatabaseHelper(context);
        this.isClosed = isClosed;
        dialogUtils = new DialogUtils(context);
    }

    public void setHideDetail(Boolean hideDetail) {
        this.hideDetail = hideDetail;
    }

    public void clearData() {
        this.mItem.clear();
        notifyDataSetChanged();
    }

    public void addItem(MenuCategory menuCategory) {
        this.menuSubCategory.addAll(menuCategory.getMenuSubCategory());
        this.menuCategory = menuCategory;
        this.mItem.addAll(menuCategory.getMenuProducts());
        notifyItemChanged(this.mItem.size());

    }

    public void addItem(List<MenuProduct> mItem) {
        this.mItem.addAll(mItem);
        notifyItemChanged(this.mItem.size());
    }

    public void addItem(MenuProduct mItem) {
        this.mItem.add(mItem);
        notifyItemChanged(this.mItem.size());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                CategoryViewHolder categoryViewHolder = (CategoryViewHolder) holder;
                categoryViewHolder.bindData(position);
                break;
            case 1:
                SubCategoryViewHolder subCategoryViewHolder = (SubCategoryViewHolder) holder;
                subCategoryViewHolder.bindData(position);
                break;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {
        switch (viewtype) {
            case 0:
                return new CategoryViewHolder(inflater.inflate(R.layout.offer_item_list, viewGroup, false));
            case 1:
                return new SubCategoryViewHolder(inflater.inflate(R.layout.product_with_sub_category_view, viewGroup, false));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (menuSubCategory == null || menuSubCategory.size() == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return (menuSubCategory == null || menuSubCategory.size() == 0) ? mItem.size() : 1;
    }


    class SubCategoryViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerView categoryItemView, subCategoryView;

        public SubCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryItemView = itemView.findViewById(R.id.list_productItemView);
            subCategoryView = itemView.findViewById(R.id.list_subCategoryItemView);
        }

        private void bindData(int position) {
            RecyclerLayoutManager layoutManager1 = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
            layoutManager1.setScrollEnabled(false);
            categoryItemView.setLayoutManager(layoutManager1);

            RestaurantCategoryAdapter2 restaurantCategoryAdapter = new RestaurantCategoryAdapter2(context, parentPosition, menuCategory, menuItemClickListener);
            restaurantCategoryAdapter.setHideDetail(true);
            categoryItemView.setAdapter(restaurantCategoryAdapter);
//            restaurantCategoryAdapter.addItem(mItem);

//            if (position==(getItemCount()-1)) {
            if (menuSubCategory != null && menuSubCategory.size() > 0) {
                subCategoryView.setVisibility(View.VISIBLE);

                RestaurantSubCategoryAdapter restaurantSubCategoryAdapter = new RestaurantSubCategoryAdapter(context, parentPosition, menuCategory, menuItemClickListener);
                RecyclerLayoutManager layoutManager = new RecyclerLayoutManager(1, RecyclerLayoutManager.VERTICAL);
                subCategoryView.setLayoutManager(layoutManager);
                subCategoryView.setAdapter(restaurantSubCategoryAdapter);
                restaurantSubCategoryAdapter.addItem(menuSubCategory);
            }
//            }
        }

    }

    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView txt_menu_title, txt_price, txt_items_detail, item_count, txtCount, tvReadmore;
        private final LinearLayout clickCount, item_remove, item_add, llDescription;
        private final ProgressBar progressBar;


        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
            txt_menu_title = itemView.findViewById(R.id.txt_menu_title);
            llDescription = itemView.findViewById(R.id.ll_description);
            txt_price = itemView.findViewById(R.id.txt_price);
            tvReadmore = itemView.findViewById(R.id.tv_readmore);
            txtCount = itemView.findViewById(R.id.txt_count);
            txt_items_detail = itemView.findViewById(R.id.txt_items_detail);
            clickCount = itemView.findViewById(R.id.clickCount);
            item_remove = itemView.findViewById(R.id.item_remove);
            item_add = itemView.findViewById(R.id.item_add);
            item_count = itemView.findViewById(R.id.item_count);
            // item_add.setOnClickListener(this);
            //  item_remove.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        private void bindData(int position) {
            txt_menu_title.setText(mItem.get(position).getProductName());
            txt_price.setText("£" + mItem.get(position).getMenuProductPrice());
            item_count.setText("0");
          /*  if (hideDetail) {
                txt_items_detail.setVisibility(View.VISIBLE);
                txt_items_detail.setText(mItem.get(position).getDescription());
            } else {
                txt_items_detail.setVisibility(View.VISIBLE);
                txt_items_detail.setText(mItem.get(position).getDescription());
            }*/

            if (mItem.get(position).getDescription() != null && !mItem.get(position).getDescription().isEmpty()) {
                String description=mItem.get(position).getDescription().replaceAll("</br>","\n");
                description=description.replaceAll("/r","");
                description=description.replaceAll("<br/>","\n");
                description=description.replaceAll("<br>","\n");
                SpannableString ss = new SpannableString(description);
                setDescriptionLayout(ss, txt_items_detail, tvReadmore);
                // catDescp.setText(dataItem.getMenuCategoryDescription());


                llDescription.setVisibility(View.VISIBLE);
            } else {
                llDescription.setVisibility(View.GONE);

            }
            int qtyCount = 0;

            List<OrderSaveModel> orderSaveModelList = mDb.saveOrderHistry().loadAllOrderByMenueProductID(mItem.get(position).getMenuProductId());
            if (orderSaveModelList.size() > 0) {
                for (int i = 0; i < orderSaveModelList.size(); i++) {
                    qtyCount = qtyCount + orderSaveModelList.get(i).getItemCount();
                }

            }

            if (qtyCount == 0) {
                txtCount.setVisibility(View.GONE);
                clickCount.setVisibility(View.GONE);
                item_count.setText(String.valueOf(qtyCount));
            } else {
                txtCount.setText(String.valueOf(qtyCount));
                txtCount.setVisibility(View.VISIBLE);
                clickCount.setVisibility(View.VISIBLE);
                txtCount.setText(String.valueOf(qtyCount));
                item_count.setText(String.valueOf(qtyCount));
            }

            item_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (orderSaveModelList.get(0).getData() == null || orderSaveModelList.get(0).getData().trim().isEmpty()) {

                        OrderSaveModel orderSaveModel = new OrderSaveModel(orderSaveModelList.get(0).getId(),
                                orderSaveModelList.get(0).getItemCount() + 1,
                                orderSaveModelList.get(0).getMealID(),
                                orderSaveModelList.get(0).getRestaurantID(),
                                orderSaveModelList.get(0).getMealName(),
                                orderSaveModelList.get(0).getMealPrice(),
                                orderSaveModelList.get(0).getVegType(),
                                orderSaveModelList.get(0).getMenuCategoryId(),
                                orderSaveModelList.get(0).getDescription(),
                                String.valueOf((Double.parseDouble(orderSaveModelList.get(0).getTotalAmoutOfMeal()) / orderSaveModelList.get(0).getItemCount()) * (orderSaveModelList.get(0).getItemCount() + 1)),
                                true,
                                orderSaveModelList.get(0).getData());
                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                mDb.saveOrderHistry().insertOrUpdate(orderSaveModel);
                                if (MenuFragment.class != null && MenuFragment.notifyMenuListOnFeagmentInterface != null) {
                                    MenuFragment.notifyMenuListOnFeagmentInterface.onNotify();
                                }
                            }
                        });
                    } else {
                        //current calling this
                        new ChooseOrderTypeNewDialog(context, orderSaveModelList, menuCategory.getMenuProducts().get(getLayoutPosition()).getMenuProductId(), progressBar, getLayoutPosition(), menuCategory.getMenuProducts().get(getLayoutPosition()), menuCategory, Integer.parseInt(item_count.getText().toString())).show();
                    }
                    /*dialogUtils.showDialog("Please Wait..");
                    int itemQty;
                    itemQty = (Integer.parseInt(item_count.getText().toString()) + 1);
                    txtCount.setText(String.valueOf(itemQty));
                    OrderSaveModel orderSaveModel = orderSaveModelList.get(0);
                    orderSaveModel.setItemCount(itemQty);
                    orderSaveModel.setTotalAmoutOfMeal(String.valueOf(Double.parseDouble(menuCategory.getMenuProducts().get(getLayoutPosition()).getMenuProductPrice()) * itemQty));

                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mDb.saveOrderHistry().insertOrUpdate(orderSaveModel);
                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (MenuFragment.class != null && MenuFragment.notifyMenuListOnFeagmentInterface != null) {
                                                MenuFragment.notifyMenuListOnFeagmentInterface.onNotify();
                                                dialogUtils.closeDialog();
                                            }
                                        }
                                    }, 2000);

                                }
                            });
                        }
                    });*/
                }
            });

            item_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogUtils.showDialog("Please Wait..");
                    int itemQty;
                    itemQty = (Integer.parseInt(item_count.getText().toString()) - 1);
                    if (Integer.parseInt(item_count.getText().toString()) > 1) {
                        if (orderSaveModelList.size() > 1) {
                            dialogUtils.closeDialog();
                            new SingleOrderCountDialog(context, orderSaveModelList, false).show();
                        } else {


                            if (orderSaveModelList.get(0).getItemCount() > 1) {
                                OrderSaveModel orderSaveModel = orderSaveModelList.get(0);

                                //orderSaveModel.setTotalAmoutOfMeal(String.valueOf(Double.parseDouble(orderSaveModelList.get(0).getTotalAmoutOfMeal()) * itemQty));
                                orderSaveModel.setTotalAmoutOfMeal(String.valueOf((Double.parseDouble(orderSaveModelList.get(0).getTotalAmoutOfMeal()) / orderSaveModelList.get(0).getItemCount()) * (orderSaveModelList.get(0).getItemCount() - 1)));
                                orderSaveModel.setItemCount(itemQty);
                                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDb.saveOrderHistry().insertOrUpdate(orderSaveModel);
                                        orderSaveModelList.get(0).setItemCount(orderSaveModelList.get(0).getItemCount() - 1);
                                        dialogUtils.closeDialog();

                                        if (MenuFragment.class != null && MenuFragment.notifyMenuListOnFeagmentInterface != null) {
                                            MenuFragment.notifyMenuListOnFeagmentInterface.onNotify();
                                            dialogUtils.closeDialog();
                                        }
                                    }
                                });
                            } else {
                                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDb.saveOrderHistry().delete(orderSaveModelList.get(0));
                                        dialogUtils.closeDialog();
                                        if (MenuFragment.class != null && MenuFragment.notifyMenuListOnFeagmentInterface != null) {
                                            MenuFragment.notifyMenuListOnFeagmentInterface.onNotify();
                                            dialogUtils.closeDialog();
                                        }
                                    }
                                });

                            }

                        }
                    } else {
                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                mDb.saveOrderHistry().deleteAllMessage(orderSaveModelList.get(0).getMealID());
                                dialogUtils.closeDialog();
                                if (MenuFragment.class != null && MenuFragment.notifyMenuListOnFeagmentInterface != null) {
                                    MenuFragment.notifyMenuListOnFeagmentInterface.onNotify();
                                    dialogUtils.closeDialog();
                                }
                            }
                        });
                    }

                    if (itemQty == 0) {
                        txtCount.setVisibility(View.GONE);
                        clickCount.setVisibility(View.GONE);
                    } else {
                        txtCount.setText(String.valueOf(itemQty));
                    }

                }
            });
        }

        @Override
        public void onClick(View v) {
            final int[] itemQty = new int[1];

            switch (v.getId()) {
                case R.id.item_add:


                    /*if (menuItemClickListener != null) {
                        menuItemClickListener.OnCategoryClick(parentPosition, getLayoutPosition(), clickCount, item_count, itemQty, 2, menuCategory, progressBar);
                    }*/
                    break;
                case R.id.item_remove:

                   /* if (menuItemClickListener != null) {
                        List<MenuProduct> mItemNew = mItem;
                        menuItemClickListener.OnCategoryClick(parentPosition, getLayoutPosition(), clickCount, item_count, itemQty, 1, menuCategory, progressBar);
                    }*/
                    break;
                default:
                    if (isClosed) {
                        restaurantClosedDialog();
                    } else {
                        if (clickCount.getVisibility() == View.GONE) {
                            loafSizeAndModifiers(menuCategory.getMenuProducts().get(getLayoutPosition()).getMenuProductId(), progressBar, itemQty, item_count, txtCount, getLayoutPosition(), menuCategory.getMenuProducts().get(getLayoutPosition()));


                        }
                    }
                    break;
            }


        }
    }

    private void loafSizeAndModifiers(final String productId, final ProgressBar progressBar, int[] itemQty,
                                      TextView item_count, TextView txtCount, int layoutPosition, MenuProduct menuProduct) {
        //final MenuCategory menuCategory = mMenuCategory;
        final ProgressBar mProgressBar = progressBar;
        final int mParentPosition = parentPosition;
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressBar != null)
                    mProgressBar.setVisibility(View.VISIBLE);
            }
        });
        RestaurantDetailsInterface apiInterface = ApiClient.getClient(context).create(RestaurantDetailsInterface.class);
        MenuProductSizeModifierRequest request = new MenuProductSizeModifierRequest(productId, "");


        Call<ProductSizeAndModifier> call3 = apiInterface.getMenuProductSizeModifier(PrefManager.getInstance(context).getPreference(AUTH_TOKEN, ""), request);
        call3.enqueue(new Callback<ProductSizeAndModifier>() {
            @Override
            public void onResponse(Call<ProductSizeAndModifier> call, Response<ProductSizeAndModifier> response) {
                final Response<ProductSizeAndModifier> mResponse = response;
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if (mResponse.body() != null && mResponse.body().getSuccess()) {
                            mResponse.body().getProductSizeAndModifier().setProductId(productId);
                            final Long id = GlobalValues.getInstance().getDb().productSizeAndModifierMaster().insert(mResponse.body().getProductSizeAndModifier());

                            setDatIntoMeal(mResponse.body(), menuProduct, productId, progressBar, itemQty,
                                    item_count, txtCount, layoutPosition);


                        } else {
                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    itemQty[0] = (Integer.parseInt(item_count.getText().toString()) + 1);
                                    txtCount.setVisibility(View.VISIBLE);
                                    dialogUtils.showDialog("Please Wait..");
                                    OrderSaveModel orderSaveModels = new OrderSaveModel(1, menuCategory.getMenuProducts().get(layoutPosition).getMenuProductId(),
                                            menuCategory.getMenuProducts().get(layoutPosition).getMenuProductId(),
                                            menuCategory.getMenuProducts().get(layoutPosition).getProductName(),
                                            menuCategory.getMenuProducts().get(layoutPosition).getMenuProductPrice(),
                                            menuCategory.getMenuProducts().get(layoutPosition).getVegType(),
                                            menuCategory.getMenuCategoryId(),
                                            menuCategory.getMenuProducts().get(layoutPosition).getDescription(),
                                            String.valueOf(Double.parseDouble(menuCategory.getMenuProducts().get(layoutPosition).getMenuProductPrice()) * itemQty[0]),
                                            true,
                                            "");
                                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            mDb.saveOrderHistry().insertOrUpdate(orderSaveModels);
                                            ((Activity) context).runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            if (MenuFragment.class != null && MenuFragment.notifyMenuListOnFeagmentInterface != null) {
                                                                MenuFragment.notifyMenuListOnFeagmentInterface.onNotify();
                                                                dialogUtils.closeDialog();
                                                            }
                                                        }
                                                    }, 2000);

                                                }
                                            });
                                        }
                                    });
                                }
                            });


                        }
                    }
                }).start();


            }

            @Override
            public void onFailure(Call<ProductSizeAndModifier> call, Throwable t) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mProgressBar != null)
                            mProgressBar.setVisibility(View.GONE);
                    }
                });

            }
        });
    }

    private void setDatIntoMeal(ProductSizeAndModifier body, MenuProduct menuProduct, String productId, ProgressBar progressBar, int[] itemQty, TextView item_count, TextView txtCount, int layoutPosition) {

        MealDetailsModel mealDetailsModel = new MealDetailsModel();

        MealDetailsModel.MealBean mealBean = new MealDetailsModel.MealBean();
        mealBean.setDescription(menuProduct.getDescription());
        mealBean.setId(menuProduct.getId());
        mealBean.setMeal_name(menuProduct.getProductName());
        mealBean.setMenu_category_id(menuProduct.getMenuId());
        mealBean.setRestaurant_id(menuProduct.getMenuProductId());
        mealBean.setMeal_price("0.0");
        mealBean.setVeg_type(menuProduct.getVegType());


        List<MealDetailsModel.MealConfigBean> meal_config = new ArrayList<>();

        for (int i = 0; i < body.getProductSizeAndModifier().getMenuProductSize().size(); i++) {


            MenuProductSize menuProductSize = body.getProductSizeAndModifier().getMenuProductSize().get(i);

            MealDetailsModel.MealConfigBean mealConfigBean = new MealDetailsModel.MealConfigBean();


            mealConfigBean.setAllowed_quantity(1);
            mealConfigBean.setCategoryslugname("");
            mealConfigBean.setIs_customizable(0);
            mealConfigBean.setName(menuProductSize.getProductSizeName());
            mealConfigBean.setProduct_quantity(1);
            mealConfigBean.setProduct_size_name(menuProductSize.getProductSizeName());
            mealConfigBean.setSelling_price(menuProductSize.getProductSizePrice());
            mealConfigBean.setShown(menuProductSize.getSelected());

            List<MealDetailsModel.MealConfigBean.ProductsBean> productsBeanList = new ArrayList<>();


            MealDetailsModel.MealConfigBean.ProductsBean productsBean = new MealDetailsModel.MealConfigBean.ProductsBean();

            productsBean.setShown(menuProductSize.getSelected());
            productsBean.setAllowed_quantity(1);
            productsBean.setCategory_name(menuProductSize.getProductSizeName());
            productsBean.setId(menuProductSize.getProductSizeId());
            productsBean.setMenu_product_id(menuProduct.getMenuId());
            productsBean.setMenu_product_price(menuProductSize.getProductSizePrice());
            productsBean.setProduct_id(menuProduct.getMenuId());
            productsBean.setProduct_name(menuProduct.getProductName());
            productsBean.setProduct_size_id(menuProductSize.getProductSizeId());
            productsBean.setProduct_size_name(menuProductSize.getProductSizeName());
            productsBean.setProudct_delivery(1);
            productsBean.setVeg_type(menuProduct.getVegType());


            List<MealDetailsModel.MealConfigBean.ProductsBean.MenuProductSizeBean> menuProductSizeBeanList = new ArrayList<>();


            MealDetailsModel.MealConfigBean.ProductsBean.MenuProductSizeBean menuProductSizeBean = new MealDetailsModel.MealConfigBean.ProductsBean.MenuProductSizeBean();

            menuProductSizeBean.setProduct_size_id(menuProductSize.getProductSizeId());
            menuProductSizeBean.setProduct_size_name(menuProductSize.getProductSizeName());
            menuProductSizeBean.setProduct_size_price(menuProductSize.getProductSizePrice());

            List<MealDetailsModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean> sizeModifiersBeanList = new ArrayList<>();


            for (int j = 0; j < menuProductSize.getSizeModifiers().size(); j++) {
                SizeModifier sizeModifier = menuProductSize.getSizeModifiers().get(j);
                MealDetailsModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean sizeModifiersBean = new MealDetailsModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean();
                sizeModifiersBean.setFree_qty_limit(sizeModifier.getFreeAllowedQuantity());
                sizeModifiersBean.setMax_allowed_quantity(sizeModifier.getMaxAllowedQuantity());
                sizeModifiersBean.setMin_allowed_quantity(sizeModifier.getMinAllowedQuantity());
                sizeModifiersBean.setModifier_id(sizeModifier.getModifierId());
                sizeModifiersBean.setModifier_name(sizeModifier.getModifierName());
                sizeModifiersBean.setModifier_type(sizeModifier.getModifierType());


                List<MealDetailsModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean.SizeModifierProductsBean> sizeModifierProductsBeanList = new ArrayList<>();

                for (int k = 0; k < sizeModifier.getModifier().size(); k++) {
                    Modifier modifier = sizeModifier.getModifier().get(k);
                    MealDetailsModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean.SizeModifierProductsBean sizeModifierProductsBean = new MealDetailsModel.MealConfigBean.ProductsBean.MenuProductSizeBean.SizeModifiersBean.SizeModifierProductsBean();
                    sizeModifierProductsBean.setDescription(modifier.getProductName());
                    sizeModifierProductsBean.setModifier_product_price(modifier.getModifierProductPrice());
                    sizeModifierProductsBean.setProduct_id(modifier.getProductId());
                    sizeModifierProductsBean.setProduct_name(modifier.getProductName());
                    sizeModifierProductsBean.setUnit(modifier.getUnit());
                    sizeModifierProductsBeanList.add(sizeModifierProductsBean);
                }
                sizeModifiersBean.setSize_modifier_products(sizeModifierProductsBeanList);
                sizeModifiersBeanList.add(sizeModifiersBean);
            }

            menuProductSizeBean.setSize_modifiers(sizeModifiersBeanList);
            menuProductSizeBeanList.add(menuProductSizeBean);
            productsBean.setMenu_product_size(menuProductSizeBeanList);

            productsBeanList.add(productsBean);
            mealConfigBean.setProducts(productsBeanList);
            meal_config.add(mealConfigBean);
        }

        mealDetailsModel.setMeal(mealBean);
        mealDetailsModel.setMeal_config(meal_config);


        Log.e("Product data", new Gson().toJson(mealDetailsModel));

        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressBar != null)
                    progressBar.setVisibility(View.GONE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final ProductSizeAndModifier.ProductSizeAndModifierTable productSizeAndModifierTable = GlobalValues.getInstance().getDb().productSizeAndModifierMaster().getProductSizeAndModifierList(menuCategory.getMenuProducts().get(layoutPosition).getMenuProductId());
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (productSizeAndModifierTable != null) {
                                    if (productSizeAndModifierTable.getMenuProductSize() != null && productSizeAndModifierTable.getMenuProductSize().size() > 0) {
                                        new MenuDetailNewDialog(context, menuCategory.getMenuProducts().get(layoutPosition), productSizeAndModifierTable, menuCategory.getMenuCategoryId(), mealDetailsModel).show();

                                    } else {
                                        itemQty[0] = (Integer.parseInt(item_count.getText().toString()) + 1);
                                        txtCount.setVisibility(View.VISIBLE);
                                        dialogUtils.showDialog("Please Wait..");
                                        OrderSaveModel orderSaveModels = new OrderSaveModel(1, menuCategory.getMenuProducts().get(layoutPosition).getMenuProductId(),
                                                menuCategory.getMenuProducts().get(layoutPosition).getMenuProductId(),
                                                menuCategory.getMenuProducts().get(layoutPosition).getProductName(),
                                                menuCategory.getMenuProducts().get(layoutPosition).getMenuProductPrice(),
                                                menuCategory.getMenuProducts().get(layoutPosition).getVegType(),
                                                menuCategory.getMenuCategoryId(),
                                                menuCategory.getMenuProducts().get(layoutPosition).getDescription(),
                                                String.valueOf(Double.parseDouble(menuCategory.getMenuProducts().get(layoutPosition).getMenuProductPrice()) * itemQty[0]),
                                                true,
                                                "");
                                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDb.saveOrderHistry().insertOrUpdate(orderSaveModels);
                                                ((Activity) context).runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        new Handler().postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                if (MenuFragment.class != null && MenuFragment.notifyMenuListOnFeagmentInterface != null) {
                                                                    MenuFragment.notifyMenuListOnFeagmentInterface.onNotify();
                                                                    dialogUtils.closeDialog();
                                                                }
                                                            }
                                                        }, 2000);

                                                    }
                                                });
                                            }
                                        });
                                        if (menuItemClickListener != null) {
                                            List<MenuProduct> mItemNew = mItem;

                                            //menuItemClickListener.OnCategoryClick(parentPosition, getLayoutPosition(), clickCount, item_count, itemQty, 2, menuCategory, progressBar);
                                        }
                                    }

                                } else {
                                    itemQty[0] = (Integer.parseInt(item_count.getText().toString()) + 1);
                                    txtCount.setVisibility(View.VISIBLE);
                                    dialogUtils.showDialog("Please Wait..");
                                    OrderSaveModel orderSaveModels = new OrderSaveModel(1, menuCategory.getMenuProducts().get(layoutPosition).getMenuProductId(),
                                            menuCategory.getMenuProducts().get(layoutPosition).getMenuProductId(),
                                            menuCategory.getMenuProducts().get(layoutPosition).getProductName(),
                                            menuCategory.getMenuProducts().get(layoutPosition).getMenuProductPrice(),
                                            menuCategory.getMenuProducts().get(layoutPosition).getVegType(),
                                            menuCategory.getMenuCategoryId(),
                                            menuCategory.getMenuProducts().get(layoutPosition).getDescription(),
                                            String.valueOf(Double.parseDouble(menuCategory.getMenuProducts().get(layoutPosition).getMenuProductPrice()) * itemQty[0]),
                                            true,
                                            "");
                                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            mDb.saveOrderHistry().insertOrUpdate(orderSaveModels);
                                            ((Activity) context).runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            if (MenuFragment.class != null && MenuFragment.notifyMenuListOnFeagmentInterface != null) {
                                                                MenuFragment.notifyMenuListOnFeagmentInterface.onNotify();
                                                                dialogUtils.closeDialog();
                                                            }
                                                        }
                                                    }, 2000);

                                                }
                                            });
                                        }
                                    });
                                    if (menuItemClickListener != null) {
                                        List<MenuProduct> mItemNew = mItem;

                                        //menuItemClickListener.OnCategoryClick(parentPosition, getLayoutPosition(), clickCount, item_count, itemQty, 2, menuCategory, progressBar);
                                    }
                                }
                            }
                        });

                    }
                }).start();
            }
        });
    }


    public void restaurantClosedDialog() {
        LayoutInflater factory = LayoutInflater.from(RestaurantDetailsActivity.restaurantDetailsActivity);
        final View mDialogVieww = factory.inflate(R.layout.layout_closed_dialog, null);
        final AlertDialog alertClodseDialog = new AlertDialog.Builder(RestaurantDetailsActivity.restaurantDetailsActivity).create();
        alertClodseDialog.setView(mDialogVieww);
        alertClodseDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //   final TextView ok_tv = (TextView)  mDialogView.findViewById(R.id.okTv);

        mDialogVieww.findViewById(R.id.tv_btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                alertClodseDialog.dismiss();
            }
        });


        alertClodseDialog.show();
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
                        tvReadmore.setText(context.getResources().getText(R.string.read_more));
                        tvReadmore.setSelected(true);
                    } else {
                        tvReadmore.setVisibility(View.GONE);
                    }
                }

            });
        } else {
            tvDescriptionbody.setText(context.getResources().getString(R.string.no_description_available));
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
            mReadMore.setText(context.getString(R.string.read_less));
            mDescription.setMaxLines(Integer.MAX_VALUE);
            mReadMore.setSelected(false);
        } else {
            mReadMore.setText(context.getString(R.string.read_more));
            mDescription.setMaxLines(2);
            mDescription.setEllipsize(TextUtils.TruncateAt.END);
            mReadMore.setSelected(true);
        }
    }
}