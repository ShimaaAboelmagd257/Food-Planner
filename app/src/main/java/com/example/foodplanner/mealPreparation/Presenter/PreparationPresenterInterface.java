package com.example.foodplanner.mealPreparation.Presenter;

import com.example.foodplanner.model.Pojos.ProductsPOJO;

import java.util.List;

public interface PreparationPresenterInterface {

    void getMealItem(String ItemName);

     void addToFavorite(ProductsPOJO mealModel);

     void addToPlan(ProductsPOJO mealModel);
    void insertFavInRoom(ProductsPOJO mealModel);
    void insertPlanInRoom(ProductsPOJO productsPOJO);
}
