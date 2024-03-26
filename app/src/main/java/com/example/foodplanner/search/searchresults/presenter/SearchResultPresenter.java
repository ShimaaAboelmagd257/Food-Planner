package com.example.foodplanner.search.searchresults.presenter;

import com.example.foodplanner.dataBaseHandling.Model.Reposatory.RepoInterface;
import com.example.foodplanner.model.Pojos.AreasPojo;
import com.example.foodplanner.model.Pojos.CategoriesPojo;
import com.example.foodplanner.model.Pojos.IngeriedientPojo;
import com.example.foodplanner.model.Pojos.ProductsPOJO;
import com.example.foodplanner.model.RemoteDataSource.NetworkDelegate;
import com.example.foodplanner.search.searchresults.view.SRViewInterface;

import java.util.List;

public class SearchResultPresenter implements SRPresenterInterface , NetworkDelegate {

    RepoInterface repo;
    SRViewInterface view;
    public SearchResultPresenter(SRViewInterface view, RepoInterface repo) {
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
        view.showMealResults(productsPOJO);

    }

    @Override
    public void onSuccessCategories(List<CategoriesPojo> categoriesPojo) {
        view.showCategoryResults(categoriesPojo);

    }

    @Override
    public void onSuccessArea(List<AreasPojo> areasPojo) {
     view.showCountryResults(areasPojo);
    }

    @Override
    public void onSuccessIngeriedient(List<IngeriedientPojo> ingeriedientPojo) {
         view.showIngredientResults(ingeriedientPojo);
    }

    @Override
    public void onfailure(String message) {

    }

    @Override
    public void getMealsByName(String name) {
        repo.getMealsByFirstChar(this,name);
    }

    @Override
    public void getMealsByCategories(String category) {
        repo.getMealsByCategories(this,category);

    }

    @Override
    public void getMealsByCountries(String country) {
       repo.getMealsByCountries(this,country);
    }

    @Override
    public void getMealsByIngredients(String ingredient) {
        repo.getMealsByIngredients(this,ingredient);

    }
}
