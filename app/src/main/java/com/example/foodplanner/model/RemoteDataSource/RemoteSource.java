package com.example.foodplanner.model.RemoteDataSource;

public interface RemoteSource {

    void getRandomMeal(NetworkDelegate networkDelegate);
    void getRandomMealWithTimestamp(NetworkDelegate networkDelegate);
    void getRandomMealWithTimetamp(NetworkDelegate networkDelegate);
    void getAllCategories(NetworkDelegate networkDelegate);
    void getAllAreas(NetworkDelegate networkDelegate);
    void getAllIngredients(NetworkDelegate networkDelegate);
    void getMealByFirstChar(NetworkDelegate networkDelegate, String name);
    void getMealsByCategory(NetworkDelegate networkDelegate, String category);
    void getMealsByArea(NetworkDelegate networkDelegate, String country);
    void getMealsByIngredient(NetworkDelegate networkDelegate, String ingredient);
    void  getMealByName(NetworkDelegate networkDelegate,String name);
}
