package com.example.foodplanner.mealPlans.view;

import com.example.foodplanner.model.Pojos.ProductsPOJO;

public interface PlanClickListner {


    void onRemovePlanClicked(ProductsPOJO productsPOJO);

    void addToFavoriteOnClick(ProductsPOJO productsPOJO);
}
