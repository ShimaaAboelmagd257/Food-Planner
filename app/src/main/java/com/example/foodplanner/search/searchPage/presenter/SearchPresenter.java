package com.example.foodplanner.search.searchPage.presenter;

import android.util.Log;

import com.example.foodplanner.dataBaseHandling.Model.Reposatory.RepoInterface;
import com.example.foodplanner.model.Pojos.AreasPojo;
import com.example.foodplanner.model.Pojos.CategoriesPojo;
import com.example.foodplanner.model.Pojos.IngeriedientPojo;
import com.example.foodplanner.model.Pojos.ProductsPOJO;
import com.example.foodplanner.model.RemoteDataSource.NetworkDelegate;
import com.example.foodplanner.search.searchPage.view.SearchViewInterface;

import java.util.List;

public class SearchPresenter implements SearchPresenterInterface, NetworkDelegate {

    RepoInterface repo;
    SearchViewInterface view;

    public SearchPresenter( SearchViewInterface view, RepoInterface repo) {
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

    }

    @Override
    public void onSuccessCategories(List<CategoriesPojo> categoriesPojo) {
        view.ViewCategories(categoriesPojo);

    }

    @Override
    public void onSuccessArea(List<AreasPojo> areasPojo) {
        view.ViewCountries(areasPojo);

    }

    @Override
    public void onSuccessIngeriedient(List<IngeriedientPojo> ingeriedientPojo) {
        view.ViewIngredient(ingeriedientPojo);
        Log.d("SearchPresenter", "onSuccessIngeriedient: view IngredientIngredient meals");


    }

    @Override
    public void onfailure(String message) {

    }

    @Override
    public void getIngredient() {
        repo.getAllIngredient(this);
        Log.d("SearchPresenter", "getIngredient: Fetching Ingredient meals");

    }

    @Override
    public void getCategoriesList() {
        repo.getAllCategories(this);

    }

    @Override
    public void getCountriesList() {
        repo.getAllAreas(this);

    }
}
