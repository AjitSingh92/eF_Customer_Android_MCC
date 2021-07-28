package com.easyfoodcustomer.modelsNew;

import com.easyfoodcustomer.restaurant_details.model.restaurantmenumodel.menu_response.MeaListBean;

import java.util.List;

public class MealDetailList {


    private boolean success;
    private String message;


    private MealDetailsModel data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MealDetailsModel getData() {
        return data;
    }

    public void setData(MealDetailsModel data) {
        this.data = data;
    }



}
