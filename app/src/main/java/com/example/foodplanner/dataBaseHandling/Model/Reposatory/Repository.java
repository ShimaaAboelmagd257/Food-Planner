package com.example.foodplanner.dataBaseHandling.Model.Reposatory;

import android.content.Context;
import android.util.Log;

import com.example.foodplanner.model.RemoteDataSource.ApiClient;
import com.example.foodplanner.model.RemoteDataSource.NetworkDelegate;
import com.example.foodplanner.model.RemoteDataSource.RemoteSource;
import com.example.foodplanner.model.LocalDataSource.ConcreteLocalSource;
import com.example.foodplanner.model.LocalDataSource.LocalSource;
import com.example.foodplanner.model.Pojos.ProductsPOJO;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class Repository implements RepoInterface {

    private Context context ;
    private RemoteSource remoteSource;
    private LocalSource localSource;
    private static Repository repository = null;

    public Repository(ApiClient remoteSource, ConcreteLocalSource localSource, Context context) {
        this.context = context;
        this.remoteSource = remoteSource;
        this.localSource = localSource;
    }


    public static Repository getInstance(ApiClient remoteSource, ConcreteLocalSource localSource, Context context){
        if (repository == null){
            repository = new Repository(remoteSource,localSource,context);
        }

        return  repository;
    }

    @Override
    public void getRandomMeal(NetworkDelegate networkDelegate) {
        remoteSource.getRandomMeal(networkDelegate);
    }
    @Override
    public void getRandomMeal2(NetworkDelegate networkDelegate) {
        remoteSource.getRandomMealWithTimestamp(networkDelegate);
    }
    @Override
    public void getRandomMeal3(NetworkDelegate networkDelegate) {
        remoteSource.getRandomMealWithTimetamp(networkDelegate);
    }
    @Override
    public void getAllCategories(NetworkDelegate networkDelegate) {
        remoteSource.getAllCategories(networkDelegate);
    }

    @Override
    public void getAllAreas(NetworkDelegate networkDelegate) {
        remoteSource.getAllAreas(networkDelegate);
    }

    @Override
    public void getAllIngredient(NetworkDelegate networkDelegate) {
        remoteSource.getAllIngredients(networkDelegate);
    }

    @Override
    public void getMealsByName(NetworkDelegate networkDelegate, String name) {
        remoteSource.getMealByName(networkDelegate,name);
    }

    @Override
    public void getMealsByFirstChar(NetworkDelegate networkDelegate, String name) {
        remoteSource.getMealByFirstChar(networkDelegate,name);
    }

    @Override
    public void getMealsByCategories(NetworkDelegate networkDelegate, String category) {
        remoteSource.getMealsByCategory(networkDelegate,category);
    }

    @Override
    public void getMealsByCountries(NetworkDelegate networkDelegate, String country) {
        remoteSource.getMealsByArea(networkDelegate,country);
    }

    @Override
    public void getMealsByIngredients(NetworkDelegate networkDelegate, String ingredient) {
        remoteSource.getMealsByIngredient(networkDelegate,ingredient);
    }

    @Override
    public void insertFavorite(ProductsPOJO productpojo) {
         localSource.insertFavorite(productpojo);
    }

    @Override
    public void removeFavorite(ProductsPOJO mealModel) {
        localSource.removeFavorite(mealModel);
    }

    @Override
    public Single<List<ProductsPOJO>> getAllStoredFavorites() {
        return localSource.getAllStoredFavorites();
    }
    @Override

    public void insertPlans(ProductsPOJO plans) {
         localSource.insertPlan(plans);
    }

    @Override
    public void removePlan(ProductsPOJO mealModel) {
        localSource.removePlan(mealModel);

    }

    @Override
    public Single<List<ProductsPOJO>> getAllStoredPlans(String day) {
        Log.d("Repository", "getAllStoredPlans succeeded" + localSource.getAllPlans(day));
        return localSource.getAllPlans(day);
    }

    @Override
    public void deleteAllMeals() {
        localSource.deleteAllMeals();
    }

    /*@Override
    public void insertFavInRoom(ProductsPOJO mealModel) {
        localSource.insertFavorite(mealModel);
    }

    @Override
    public void insertPlan(ProductsPOJO productsPOJO) {
        localSource.insertPlan(productsPOJO);
    }*/


}










