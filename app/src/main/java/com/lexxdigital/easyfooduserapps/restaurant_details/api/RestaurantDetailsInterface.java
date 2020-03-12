package com.lexxdigital.easyfooduserapps.restaurant_details.api;

import com.lexxdigital.easyfooduserapps.cart_db.tables.MenuProducts;
import com.lexxdigital.easyfooduserapps.cart_db.tables.ProductSizeAndModifier;
import com.lexxdigital.easyfooduserapps.model.order_again.OrderAgainRequest;
import com.lexxdigital.easyfooduserapps.model.order_again.OrderAgainResponse;
import com.lexxdigital.easyfooduserapps.model.restaurant_offers.RestaurantOffersRequest;
import com.lexxdigital.easyfooduserapps.model.restaurant_offers.RestaurantOffersResponse;
import com.lexxdigital.easyfooduserapps.restaurant_details.HygieneRatingModel;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.new_restaurant_response.NewRestaurantsDetailsResponse;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.request.RestaurantDetailsRequest;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.Rough;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.UpSells;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.request.CategoryProductsRequest;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.request.MenuProductSizeModifierRequest;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.restaurantmenumodel.menu_response.request.UpSellsRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RestaurantDetailsInterface {
    @Headers("Content-Type: application/json")
    @POST("updated_restaurant_basic_details")
    Call<NewRestaurantsDetailsResponse> mGetDetails(@Body RestaurantDetailsRequest request);

    @Headers("Content-Type: application/json")
    @POST("restaurant_menu_category_details")
    Call<Rough> mGetRestaurantMenu(@Body RestaurantDetailsRequest request);

    @Headers("Content-Type: application/json")
    @POST("get_restaurant_menu_category")
    Call<Rough> mRestaurantCategory(@Body RestaurantDetailsRequest request);

    @Headers("Content-Type: application/json")
    @POST("restaurant_hygiene_rating")
    Call<HygieneRatingModel> getRestaurantHygieneRating(@Body RestaurantDetailsRequest request);       ///    Get Hygiene rating


    @Headers("Content-Type: application/json")
    @POST("get_menu_category_products")
    Call<MenuProducts> mRestaurantCategoryProduct(@Body CategoryProductsRequest request);

    @Headers("Content-Type: application/json")
    @POST("get_menu_product_size_modifier")
    Call<ProductSizeAndModifier> getMenuProductSizeModifier(@Body MenuProductSizeModifierRequest request);

    @Headers("Content-Type: application/json")
    @POST("get_menu_product_upsell")
    Call<UpSells> getUpsellProducts(@Body UpSellsRequest request);

    @Headers("Content-Type: application/json")
    @POST("order_again")
    Call<OrderAgainResponse> getOrderAgain(@Body OrderAgainRequest request);

    @Headers("Content-Type: application/json")
    @POST("get_restaurant_offers")
    Call<RestaurantOffersResponse> getRestaurantOffers(@Body RestaurantOffersRequest request);

}