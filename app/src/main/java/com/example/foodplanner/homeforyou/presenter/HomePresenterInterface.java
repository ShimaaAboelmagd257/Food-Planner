package com.example.foodplanner.homeforyou.presenter;

import com.example.foodplanner.model.Pojos.ProductsPOJO;

import java.util.List;

public interface HomePresenterInterface {


    void getRandomMeal();

    void getCountriesList();

    void getRandomMeal2();

    void getRandomMeal3();

    void getCategoriesList();
    void addToFavorite(ProductsPOJO mealModel);
    void insertFavInRoom(ProductsPOJO mealModel);
    void getMealsByName(String name);
    void insertPlanInRoom(ProductsPOJO productsPOJO) ;

}
