package com.easyfoodcustomer.restaurant_details.api;

import com.easyfoodcustomer.cart_db.tables.MenuProducts;
import com.easyfoodcustomer.cart_db.tables.ProductSizeAndModifier;
import com.easyfoodcustomer.model.order_again.OrderAgainRequest;
import com.easyfoodcustomer.model.order_again.OrderAgainResponse;
import com.easyfoodcustomer.model.restaurant_offers.RestaurantOffersRequest;
import com.easyfoodcustomer.model.restaurant_offers.RestaurantOffersResponse;
import com.easyfoodcustomer.modelsNew.MealDetailList;
import com.easyfoodcustomer.restaurant_details.HygieneRatingModel;
import com.easyfoodcustomer.restaurant_details.model.new_restaurant_response.NewRestaurantsDetailsResponse;
import com.easyfoodcustomer.restaurant_details.model.request.RestaurantDetailsRequest;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MeaListBean;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.Rough;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.UpSells;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.request.CategoryProductsRequest;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.request.MenuProductSizeModifierRequest;
import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.request.UpSellsRequest;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RestaurantDetailsInterface {
    @Headers("Content-Type: application/json")
    @POST("updated_restaurant_basic_details")
    Call<NewRestaurantsDetailsResponse> mGetDetails(@Header("Authorization") String Authorization, @Body RestaurantDetailsRequest request);

    @Headers("Content-Type: application/json")
    @POST("restaurant_menu_category_details")
    Call<Rough> mGetRestaurantMenu(@Header("Authorization") String Authorization,@Body RestaurantDetailsRequest request);

    @Headers("Content-Type: application/json")
    @POST("get_restaurant_menu_category")
    Call<Rough> mRestaurantCategory(@Header("Authorization") String Authorization,@Body RestaurantDetailsRequest request);

    @Headers("Content-Type: application/json")
    @POST("restaurant_hygiene_rating")
    Call<HygieneRatingModel> getRestaurantHygieneRating(@Header("Authorization") String Authorization,@Body RestaurantDetailsRequest request);       ///    Get Hygiene rating


    @Headers("Content-Type: application/json")
    @POST("get_menu_category_products")
    Call<MenuProducts> mRestaurantCategoryProduct(@Header("Authorization") String Authorization,@Body CategoryProductsRequest request);

    @Headers("Content-Type: application/json")
    @POST("get_menu_product_size_modifier")
    Call<ProductSizeAndModifier> getMenuProductSizeModifier(@Header("Authorization") String Authorization,@Body MenuProductSizeModifierRequest request);
    @Headers("Content-Type: application/json")
    @POST("get_meal_products")
    Call<MealDetailList> getMealDealItems(@Header("Authorization") String Authorization, @Body JsonObject jsonObject);
    @Headers("Content-Type: application/json")
    @POST("get_menu_product_upsell")
    Call<UpSells> getUpsellProducts(@Header("Authorization") String Authorization,@Body UpSellsRequest request);

    @Headers("Content-Type: application/json")
    @POST("order_again")
    Call<OrderAgainResponse> getOrderAgain(@Header("Authorization") String Authorization,@Body OrderAgainRequest request);

    @Headers("Content-Type: application/json")
    @POST("get_restaurant_offers")
    Call<RestaurantOffersResponse> getRestaurantOffers(@Header("Authorization") String Authorization,@Body RestaurantOffersRequest request);

}