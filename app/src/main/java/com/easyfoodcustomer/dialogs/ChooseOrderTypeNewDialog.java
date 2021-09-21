package com.easyfoodcustomer.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.easyfoodcustomer.R;
import com.easyfoodcustomer.cart_db.tables.ProductSizeAndModifier;
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

public class ChooseOrderTypeNewDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private TextView tv_heading;
    private TextView last_item_name;
    private TextView repeat_last;
    private TextView choose_again;

    private ImageView cross_tv;

    private AppDatabase mDb;
    private List<OrderSaveModel> mealDetailsModel;

    private String menuProduct;
    private ProgressBar progressBar;
    private int layoutPosition;
    private MenuProduct product;
    private MenuCategory menuCategory;
    private DialogUtils dialogUtils;
    private int itemCount;


    public ChooseOrderTypeNewDialog(@NonNull Context context, List<OrderSaveModel> mealDetailsModel,
                                    String menuProduct, ProgressBar progressBar, int layoutPosition,
                                    MenuProduct product, MenuCategory menuCategory, int itemCount) {
        super(context);
        this.context = context;
        this.mealDetailsModel = mealDetailsModel;
        this.progressBar = progressBar;
        this.layoutPosition = layoutPosition;
        this.menuProduct = menuProduct;
        this.menuCategory = menuCategory;
        this.product = product;
        this.itemCount = itemCount;
        dialogUtils = new DialogUtils(context);
        mDb = AppDatabase.getInstance(getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.color.transprent);
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflate.inflate(R.layout.dialog_choose_order_type, null);
        setContentView(layout);
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.windowAnimations = R.style.dialogue_error;
        wlmp.gravity = Gravity.CENTER;
        wlmp.dimAmount = 0.8f;
        wlmp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        setTitle(null);
        // setCancelable(false);
        setCanceledOnTouchOutside(true);

        cross_tv = layout.findViewById(R.id.cross_tv);
        tv_heading = layout.findViewById(R.id.tv_heading);
        last_item_name = layout.findViewById(R.id.last_item_name);
        repeat_last = layout.findViewById(R.id.repeat_last);
        choose_again = layout.findViewById(R.id.choose_again);

        if (mealDetailsModel!=null && mealDetailsModel.size()>0)
        {
            last_item_name.setText(mealDetailsModel.get(0).getMealName());
        }


        cross_tv.setOnClickListener(this);
        repeat_last.setOnClickListener(this);
        choose_again.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cross_tv:
                dismiss();
                break;


            case R.id.repeat_last:
                if (mealDetailsModel.size()>1)
                {
                    new SingleOrderCountDialog(context,mealDetailsModel,true).show();
                }else
                {
                   OrderSaveModel orderSaveModel = new OrderSaveModel(mealDetailsModel.get(0).getId(),
                           mealDetailsModel.get(0).getItemCount() + 1,
                           mealDetailsModel.get(0).getMealID(),
                           mealDetailsModel.get(0).getRestaurantID(),
                           mealDetailsModel.get(0).getMealName(),
                           mealDetailsModel.get(0).getMealPrice(),
                           mealDetailsModel.get(0).getVegType(),
                           mealDetailsModel.get(0).getMenuCategoryId(),
                           mealDetailsModel.get(0).getDescription(),
                        String.valueOf((Double.parseDouble(mealDetailsModel.get(0).getTotalAmoutOfMeal()) / mealDetailsModel.get(0).getItemCount()) * (mealDetailsModel.get(0).getItemCount() + 1)),
                           true,
                           mealDetailsModel.get(0).getData());
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDb.saveOrderHistry().insertOrUpdate(orderSaveModel);
                        if (MenuFragment.class!=null && MenuFragment.notifyMenuListOnFeagmentInterface!=null)
                        {
                            MenuFragment.notifyMenuListOnFeagmentInterface.onNotify();
                        }
                    }
                });
                }


                dismiss();
                break;

            case R.id.choose_again:
                loafSizeAndModifiers(menuCategory.getMenuProducts().get(layoutPosition).getMenuProductId(), progressBar, itemCount, layoutPosition, menuCategory.getMenuProducts().get(layoutPosition));
                dismiss();
                break;


            default:
                break;
        }
    }

    private void loafSizeAndModifiers(final String productId, final ProgressBar progressBar, int itemQty, int layoutPosition, MenuProduct menuProduct) {
        //final MenuCategory menuCategory = mMenuCategory;
        final ProgressBar mProgressBar = progressBar;
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

                        if (mResponse.body()!=null && mResponse.body().getSuccess()) {
                            mResponse.body().getProductSizeAndModifier().setProductId(productId);
                            final Long id = GlobalValues.getInstance().getDb().productSizeAndModifierMaster().insert(mResponse.body().getProductSizeAndModifier());

                            setDatIntoMeal(mResponse.body(),menuProduct,   progressBar,  itemQty,  layoutPosition);

                        }else
                        {
                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    OrderSaveModel orderSaveModels = new OrderSaveModel(1,menuCategory.getMenuProducts().get(layoutPosition).getMenuProductId(),
                                            menuCategory.getMenuProducts().get(layoutPosition).getMenuProductId(),
                                            menuCategory.getMenuProducts().get(layoutPosition).getProductName(),
                                            menuCategory.getMenuProducts().get(layoutPosition).getMenuProductPrice(),
                                            menuCategory.getMenuProducts().get(layoutPosition).getVegType(),
                                            menuCategory.getMenuCategoryId(),
                                            menuCategory.getMenuProducts().get(layoutPosition).getDescription(),
                                            String.valueOf(Double.parseDouble(menuCategory.getMenuProducts().get(layoutPosition).getMenuProductPrice())* itemQty),
                                            true,
                                            "");
                                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            mDb.saveOrderHistry().insertOrUpdate(orderSaveModels);
                                            ((Activity)context).runOnUiThread(new Runnable() {
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

    private void setDatIntoMeal(ProductSizeAndModifier body, MenuProduct menuProduct, ProgressBar progressBar, int itemQty, int layoutPosition) {

        MealDetailsModel mealDetailsModel = new MealDetailsModel();

        MealDetailsModel.MealBean mealBean = new MealDetailsModel.MealBean();
        mealBean.setDescription(menuProduct.getDescription());
        mealBean.setId(menuProduct.getId());
        mealBean.setMeal_name(menuProduct.getProductName());
        mealBean.setMenu_category_id(menuProduct.getMenuId());
        mealBean.setRestaurant_id(menuProduct.getMenuProductId());
        mealBean.setMeal_price(menuProduct.getMenuProductPrice());
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

                                        dialogUtils.showDialog("Please Wait..");
                                        OrderSaveModel orderSaveModels = new OrderSaveModel(1, menuCategory.getMenuProducts().get(layoutPosition).getMenuProductId(),
                                                menuCategory.getMenuProducts().get(layoutPosition).getMenuProductId(),
                                                menuCategory.getMenuProducts().get(layoutPosition).getProductName(),
                                                menuCategory.getMenuProducts().get(layoutPosition).getMenuProductPrice(),
                                                menuCategory.getMenuProducts().get(layoutPosition).getVegType(),
                                                menuCategory.getMenuCategoryId(),
                                                menuCategory.getMenuProducts().get(layoutPosition).getDescription(),
                                                String.valueOf(Double.parseDouble(menuCategory.getMenuProducts().get(layoutPosition).getMenuProductPrice()) * itemQty),
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

                                } else {

                                    OrderSaveModel orderSaveModels = new OrderSaveModel(1, menuCategory.getMenuProducts().get(layoutPosition).getMenuProductId(),
                                            menuCategory.getMenuProducts().get(layoutPosition).getMenuProductId(),
                                            menuCategory.getMenuProducts().get(layoutPosition).getProductName(),
                                            menuCategory.getMenuProducts().get(layoutPosition).getMenuProductPrice(),
                                            menuCategory.getMenuProducts().get(layoutPosition).getVegType(),
                                            menuCategory.getMenuCategoryId(),
                                            menuCategory.getMenuProducts().get(layoutPosition).getDescription(),
                                            String.valueOf(Double.parseDouble(menuCategory.getMenuProducts().get(layoutPosition).getMenuProductPrice()) * itemQty),
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
                            }
                        });

                    }
                }).start();
            }
        });
    }


}
