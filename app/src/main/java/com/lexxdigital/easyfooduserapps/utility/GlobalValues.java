package com.lexxdigital.easyfooduserapps.utility;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.lexxdigital.easyfooduserapps.cart_db.MenuDataBase;
import com.lexxdigital.easyfooduserapps.login.model.forgot_response.ForgotResponse;
import com.lexxdigital.easyfooduserapps.login.model.response.Data;
import com.lexxdigital.easyfooduserapps.login.model.response.LoginResponse;
import com.lexxdigital.easyfooduserapps.model.add_model.AddressResponseModel;
import com.lexxdigital.easyfooduserapps.model.address_list_response.AddressListResponse;
import com.lexxdigital.easyfooduserapps.restaurant_details.CartDetailsModel;
import com.lexxdigital.easyfooduserapps.restaurant_details.model.new_restaurant_response.NewRestaurantsDetailsResponse;
import com.lexxdigital.easyfooduserapps.signup.model.final_response.SignupFinalResponse;
import com.lexxdigital.easyfooduserapps.signup.model.response.SignupResponse;
import com.newrelic.agent.android.NewRelic;

import java.util.List;

public class GlobalValues extends Application {
    private Data data;
    private LoginResponse loginResponse;
    private SignupResponse signupResponse;
    private SignupFinalResponse signupFinalResponse;
    private ForgotResponse forgotResponse;
    private String defaltAddress;
    private String mobileNo;
    private String userName, firstName, lastName;
    private String profileImage;
    private boolean isFromDealPage = false;
    private AddressResponseModel addressResponseModel;
    private String postCode;
    private String address1;
    private String address2;
    private String city;
    private String postalCode;
    private AddressListResponse addressListResponse;
    private NewRestaurantsDetailsResponse restaurantDetailsResponse;

    private List<CartDetailsModel> listAddCartDetails, listRemoveCartDetails;

    private static GlobalValues mInstance;
    private MenuDataBase db;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        NewRelic.withApplicationToken(
                "eu01xxae9ccb44aafd9f746b5862b2dcb19769290d"
        ).start(this.getApplicationContext());
        db = Room.databaseBuilder(this, MenuDataBase.class, "restaurant_menu_db").build();
    }

    public static synchronized GlobalValues getInstance() {
        return mInstance;
    }

    public MenuDataBase getDb() {
        return db;
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }

    public SignupResponse getSignupResponse() {
        return signupResponse;
    }

    public void setSignupResponse(SignupResponse signupResponse) {
        this.signupResponse = signupResponse;
    }

    public SignupFinalResponse getSignupFinalResponse() {
        return signupFinalResponse;
    }

    public void setSignupFinalResponse(SignupFinalResponse signupFinalResponse) {
        this.signupFinalResponse = signupFinalResponse;
    }

    public ForgotResponse getForgotResponse() {
        return forgotResponse;
    }

    public void setForgotResponse(ForgotResponse forgotResponse) {
        this.forgotResponse = forgotResponse;
    }

    public String getDefaltAddress() {
        return defaltAddress;
    }

    public void setDefaltAddress(String defaltAddress) {
        this.defaltAddress = defaltAddress;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean getIsFromDealPage() {
        return isFromDealPage;
    }

    public void setIsFromDealPage(boolean fromDealPafe) {
        isFromDealPage = fromDealPafe;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public NewRestaurantsDetailsResponse getRestaurantDetailsResponse() {
        return restaurantDetailsResponse;
    }

    public void setRestaurantDetailsResponse(NewRestaurantsDetailsResponse restaurantDetailsResponse) {
        this.restaurantDetailsResponse = restaurantDetailsResponse;
    }

    public List<CartDetailsModel> getListAddCartDetails() {
        return listAddCartDetails;
    }

    public void setListAddCartDetails(List<CartDetailsModel> listAddCartDetails) {
        this.listAddCartDetails = listAddCartDetails;
    }

    public List<CartDetailsModel> getListRemoveCartDetails() {
        return listRemoveCartDetails;
    }

    public void setListRemoveCartDetails(List<CartDetailsModel> listRemoveCartDetails) {
        this.listRemoveCartDetails = listRemoveCartDetails;
    }

    public void setLoginResponseData(Data data) {
        this.data = data;
    }

    public Data getLoginResponseData() {
        return data;
    }

    public AddressListResponse getAddressListResponse() {
        return addressListResponse;
    }

    public void setAddressListResponse(AddressListResponse addressListResponse) {
        this.addressListResponse = addressListResponse;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
