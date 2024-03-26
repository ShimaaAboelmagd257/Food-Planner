package com.example.foodplanner.search.searchresults.presenter;

public interface SRPresenterInterface {


    void getMealsByName(String name);

    void getMealsByCategories(String category);

    void getMealsByCountries(String country);

    void getMealsByIngredients(String ingredient);
}
