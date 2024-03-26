package com.example.foodplanner.search.searchresults.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.foodplanner.R;
import com.example.foodplanner.dataBaseHandling.Model.Reposatory.Repository;
import com.example.foodplanner.model.LocalDataSource.ConcreteLocalSource;
import com.example.foodplanner.model.Pojos.AreasPojo;
import com.example.foodplanner.model.Pojos.CategoriesPojo;
import com.example.foodplanner.model.Pojos.IngeriedientPojo;
import com.example.foodplanner.model.Pojos.ProductsPOJO;
import com.example.foodplanner.model.RemoteDataSource.ApiClient;
import com.example.foodplanner.search.searchPage.view.InspirationAdapter;
import com.example.foodplanner.search.searchPage.view.IngredientSearchAdapter;
import com.example.foodplanner.search.searchPage.view.CategoryAdapter;
import com.example.foodplanner.search.searchPage.view.CountryAdapter;
import com.example.foodplanner.search.searchresults.presenter.SRPresenterInterface;
import com.example.foodplanner.search.searchresults.presenter.SearchResultPresenter;

import java.util.ArrayList;
import java.util.List;

public class SearchResult extends Fragment implements SRViewInterface {
    private static final int MEALS = 0;
    private static final int COUNTRIES = 1;
    private static final int CATEGORIES = 2;
    private static final int INGREDIENTS = 3;
    private int selectedCategory;
    SearchView searchView;
    RecyclerView recyclerView;
    SRPresenterInterface searchPresenterInterface;
    CategoryAdapter categoryAdapter;
    CountryAdapter countryAdapter;
    IngredientSearchAdapter ingredientAdapter;
    InspirationAdapter inspirationAdapter;
    String ingredient , country , category ;
    List<ProductsPOJO> productsPOJOS;
    List<CategoriesPojo> categoriesPojos;
    List<AreasPojo> areasPojos;
    List<IngeriedientPojo> ingeriedientPojos;
    public SearchResult() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search_result, container, false);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchPresenterInterface =new SearchResultPresenter(this, Repository.getInstance(ApiClient.getInstance(getContext())
                , ConcreteLocalSource.getInstance(getContext()), view.getContext()));

        //searchPresenterInterface.getMealsByName(s);
        recyclerView = view.findViewById(R.id.srRecyclerView);
        searchView = view.findViewById(R.id.SRsearchView);


        inspirationAdapter = new InspirationAdapter(getContext(),new ArrayList<>());
        recyclerView.setAdapter(inspirationAdapter);
        ingredientAdapter = new IngredientSearchAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(ingredientAdapter);
        categoryAdapter = new CategoryAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(categoryAdapter);
        countryAdapter = new CountryAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(countryAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


         ingredient = getArguments().getString("ingredient");
         searchPresenterInterface.getMealsByIngredients(ingredient);
         category = getArguments().getString("category");
        searchPresenterInterface.getMealsByCategories(category);
         country = getArguments().getString("country");
         searchPresenterInterface.getMealsByCountries(country);

        // Set a layout manager
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (searchView.getQuery().toString().equals("")) {
                    clearSearchResults();
                }

                switch (selectedCategory) {
                   case MEALS:
                        performMealSearch(newText);
                        searchPresenterInterface.getMealsByName(newText);

                        break;
                   /* case COUNTRIES:
                        performCountrySearch(newText);
                        searchPresenterInterface.getMealsByCountries(newText);

                        break;
                    case CATEGORIES:
                        performCategorySearch(newText);
                        searchPresenterInterface.getMealsByCategories(newText);

                        break;
                    case INGREDIENTS:
                        performIngredientSearch(newText);
                        searchPresenterInterface.getMealsByIngredients(newText);
                        break;*/
                }

                return false;
            }

        });
    }


        private void clearSearchResults() {
            productsPOJOS = null;
            recyclerView.setAdapter(null);
            categoriesPojos = null;
            areasPojos = null;
            ingeriedientPojos = null;
        }

        public void performMealSearch(String newText) {
            inspirationAdapter.performSearch(newText);
        }

      /*  public void performCountrySearch(String newText) {
        countryAdapter.performSearch(newText);
        }

        public void performCategorySearch(String newText) {
           categoryAdapter.performSearch(newText);

        }
        public void performIngredientSearch(String newText) {
        ingredientAdapter.performSearch(newText);

        }*/

    @Override
       public void showMealResults(List<ProductsPOJO> meals){

        inspirationAdapter.setList(meals);
        recyclerView.setAdapter(inspirationAdapter);

    }
    @Override
    public void showCountryResults(List<AreasPojo > countries){
        countryAdapter.setList(countries);
        recyclerView.setAdapter(countryAdapter);

    }
    @Override
    public void showCategoryResults(List<CategoriesPojo> categories){

        categoryAdapter.setList(categories);
        recyclerView.setAdapter(categoryAdapter);
    }
     @Override
    public void showIngredientResults(List<IngeriedientPojo > ingeriedient){
        ingredientAdapter.setList(ingeriedient);
        recyclerView.setAdapter(ingredientAdapter);
    }



}