package com.example.foodplanner.dataBaseHandling.Model.Reposatory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodplanner.model.RemoteDataSource.NetworkDelegate;
import com.example.foodplanner.model.Pojos.ProductsPOJO;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface RepoInterface {
    void getRandomMeal(NetworkDelegate networkDelegate);

    void getRandomMeal2(NetworkDelegate networkDelegate);

    void getRandomMeal3(NetworkDelegate networkDelegate);

    void getAllCategories(NetworkDelegate networkDelegate);

    void getAllAreas(NetworkDelegate networkDelegate);

    void getAllIngredient(NetworkDelegate networkDelegate);

    void getMealsByName(NetworkDelegate networkDelegate, String name);

    void getMealsByFirstChar(NetworkDelegate networkDelegate, String name);

    void getMealsByCategories(NetworkDelegate networkDelegate, String category);

    void getMealsByCountries(NetworkDelegate networkDelegate, String country);

    void getMealsByIngredients(NetworkDelegate networkDelegate, String ingredient);

    void insertFavorite(ProductsPOJO mealModel);

    void removeFavorite(ProductsPOJO mealModel);

    Single<List<ProductsPOJO>> getAllStoredFavorites();

    void  insertPlans(ProductsPOJO plans);

    void removePlan(ProductsPOJO mealModel);

    //LiveData<List<ProductsPOJO>> getAllStoredPlans(String day);
     Single<List<ProductsPOJO>> getAllStoredPlans(String day) ;

    void deleteAllMeals();

  /*  void insertFav(ProductsPOJO mealModel);
    void insertPlan(ProductsPOJO productsPOJO);*/
}
