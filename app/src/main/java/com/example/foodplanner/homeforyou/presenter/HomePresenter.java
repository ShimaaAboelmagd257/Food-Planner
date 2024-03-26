package com.example.foodplanner.homeforyou.presenter;

import android.util.Log;

import com.example.foodplanner.dataBaseHandling.Model.Reposatory.RepoInterface;
import com.example.foodplanner.homeforyou.view.HomeInterface;
import com.example.foodplanner.model.Pojos.AreasPojo;
import com.example.foodplanner.model.Pojos.CategoriesPojo;
import com.example.foodplanner.model.Pojos.IngeriedientPojo;
import com.example.foodplanner.model.Pojos.ProductsPOJO;
import com.example.foodplanner.model.RemoteDataSource.NetworkDelegate;

import java.util.List;

public class HomePresenter implements  HomePresenterInterface , NetworkDelegate {

HomeInterface view;
RepoInterface repo;

    public HomePresenter(HomeInterface view, RepoInterface repo) {
        this.repo = repo;
        this.view= view;
    }


    @Override
    public void getRandomMeal() {
        repo.getRandomMeal(this);
        Log.d("HomePagePresenter", "getRandomMeal: Fetching random meals");
    }
    @Override
    public void getRandomMeal2() {
        repo.getRandomMeal2(this);
        Log.d("HomePagePresenter", "getRandomMeal: Fetching random meals");
    }

    @Override
    public void getRandomMeal3() {
        repo.getRandomMeal3(this);
        Log.d("HomePagePresenter", "getRandomMeal: Fetching random meals");
    }

    @Override
    public void getCategoriesList() {
        repo.getAllCategories(this);
        Log.d("HomePagePresenter", "getCategoryMeal: Fetching Category meals");

    }
    @Override
    public void getCountriesList() {
        repo.getAllAreas(this);
        Log.d("HomePagePresenter", "getCountryMeal: Fetching Country meals");
    }
    @Override

    public void addToFavorite(ProductsPOJO mealModel) {
        repo.insertFavorite(mealModel);
    }
    @Override
    public void insertFavInRoom(ProductsPOJO mealModel) {
        repo.insertFavorite(mealModel);
    }

    @Override
    public void insertPlanInRoom(ProductsPOJO productsPOJO) {
        repo.insertPlans(productsPOJO);
    }

    @Override
    public void getMealsByName(String name) {
        repo.getMealsByName(this,name);
    }

    @Override
    public void onSuccessMeals2(List<ProductsPOJO> productsPOJOS) {
        view.ViewRandomMeal2(productsPOJOS);
    }
    @Override
    public void onSuccessMeals3(List<ProductsPOJO> productsPOJOS) {
        view.ViewRandomMeal3(productsPOJOS);
    }
    @Override
    public void onSuccessMeals(List<ProductsPOJO> productsPOJO) {
      view.ViewRandomMeal(productsPOJO);
    }
    @Override
    public void onSuccessCategories(List<CategoriesPojo> categoriesPojo) {
        view.ViewCategoriesList(categoriesPojo);

    }

    @Override
    public void onSuccessArea(List<AreasPojo> areasPojo) {
        view.ViewCountriesList(areasPojo);

    }

    @Override
    public void onSuccessIngeriedient(List<IngeriedientPojo> ingeriedientPojo) {

    }

    @Override
    public void onfailure(String message) {


    }
}
