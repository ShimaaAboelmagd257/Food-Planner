package com.example.foodplanner.mealPreparation.Presenter;

import android.util.Log;
import android.widget.Toast;

import com.example.foodplanner.dataBaseHandling.Model.Reposatory.FirebaseRepo;
import com.example.foodplanner.dataBaseHandling.Model.Reposatory.RepoInterface;
import com.example.foodplanner.mealPreparation.view.MealPreparation;
import com.example.foodplanner.mealPreparation.view.MealPreparationInterface;
import com.example.foodplanner.model.Pojos.AreasPojo;
import com.example.foodplanner.model.Pojos.CategoriesPojo;
import com.example.foodplanner.model.Pojos.IngeriedientPojo;
import com.example.foodplanner.model.Pojos.ProductsPOJO;
import com.example.foodplanner.model.RemoteDataSource.NetworkDelegate;
import com.google.api.Context;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealPreparationPresenter implements NetworkDelegate,PreparationPresenterInterface {
    MealPreparationInterface view;
    RepoInterface repo;
    FirebaseRepo firebaseRepo;

    public MealPreparationPresenter(MealPreparationInterface view, RepoInterface repo) {
        this.repo = repo;
        this.view = view;
    }


    @Override
    public void onSuccessMeals2(List<ProductsPOJO> productsPOJOS) {

    }

    @Override
    public void onSuccessMeals3(List<ProductsPOJO> productsPOJOS) {

    }

    @Override
    public void onSuccessMeals(List<ProductsPOJO> productsPOJO) {
        view.viewMealPreparation(productsPOJO);
        Log.d("MealPrepPresenter", "onSuccessMeals: view Meals " + productsPOJO.size() );

    }

    @Override
    public void onSuccessCategories(List<CategoriesPojo> categoriesPojo) {

    }

    @Override
    public void onSuccessArea(List<AreasPojo> areasPojo) {

    }

    @Override
    public void onSuccessIngeriedient(List<IngeriedientPojo> ingeriedientPojo) {


    }

    @Override
    public void onfailure(String message) {

    }

    @Override
    public void getMealItem(String ItemName) {
        repo.getMealsByName(this,ItemName);
        Log.d("MealPrepPresenter", "getMealsByName: fetching data  " +ItemName);

    }

    @Override
    public void addToFavorite(ProductsPOJO productsPOJO) {
        repo.insertFavorite(productsPOJO);
        Log.d("MealPrepPresenter", "insertFavorite: fetching data  " + productsPOJO.getStrMeal());

    }

    @Override
    public void addToPlan(ProductsPOJO productsPOJO) {
             repo.insertPlans(productsPOJO);
        Log.d("MealPrepPresenter", "addToPlan:  Sending  data succesfully  " + productsPOJO);
    }
    @Override
    public void insertFavInRoom(ProductsPOJO productsPOJO) {
        repo.insertFavorite(productsPOJO);
        Log.d("MealPrepPresenter", "insertFavInRoom:  Sending  data succesfully  " + productsPOJO.getStrMeal());

    }

    @Override
    public void insertPlanInRoom(ProductsPOJO productsPOJO) {
            repo.insertPlans(productsPOJO);
            Log.d("MealPrepPresenter", "insertPlanInRoom:  Sending  data succesfully  " + productsPOJO.getStrMeal() + productsPOJO.getNameDay());

    }
}
