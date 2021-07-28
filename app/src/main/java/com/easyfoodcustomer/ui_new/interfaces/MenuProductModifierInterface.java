package com.easyfoodcustomer.ui_new.interfaces;

import com.easyfoodcustomer.modelsNew.MealDetailsModel;

public interface MenuProductModifierInterface {

    void updateMeanProductSizeModifier(int positionParent ,int childPosition, boolean isIncrease, int previousCount, MealDetailsModel.MealConfigBean.ProductsBean mealDetailsModel);
}
