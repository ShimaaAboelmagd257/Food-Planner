package com.example.foodplanner.mealPreparation.view;

import com.example.foodplanner.model.Pojos.ProductsPOJO;

import java.util.List;

public interface MealPrepClickListner {

    void addToFavoriteOnClick(ProductsPOJO productsPOJO);

    void addToPlanOnclick(int i);

}
