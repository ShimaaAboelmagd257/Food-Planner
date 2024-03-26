package com.example.foodplanner.search.searchPage.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.foodplanner.R;
import com.example.foodplanner.dataBaseHandling.Model.Reposatory.Repository;
import com.example.foodplanner.model.LocalDataSource.ConcreteLocalSource;
import com.example.foodplanner.model.Pojos.AreasPojo;
import com.example.foodplanner.model.Pojos.CategoriesPojo;
import com.example.foodplanner.model.Pojos.IngeriedientPojo;
import com.example.foodplanner.model.Pojos.ProductsPOJO;
import com.example.foodplanner.model.RemoteDataSource.ApiClient;
import com.example.foodplanner.search.searchPage.presenter.SearchPresenter;
import com.example.foodplanner.search.searchPage.presenter.SearchPresenterInterface;

import java.util.List;

public class SearchPage extends Fragment implements SearchViewInterface , SearchClickListner{

    CategoryAdapter categoryAdapter;
    CountryAdapter countryAdapter;
    IngredientSearchAdapter ingredientAdapter;

    SearchPresenterInterface searchPresenterInterface;

    List<ProductsPOJO> productsPOJOS;
    List<CategoriesPojo> categoriesPojos;
    List<AreasPojo> areasPojos;
    List<IngeriedientPojo> ingeriedientPojos;

    RecyclerView IngRecyclerView, catRecyclerView, conRecyclerView;
    LinearLayoutManager IngGridLayoutManager, catGridLayoutManager, conGridLayoutManager;

    ImageView searchView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchPresenterInterface = new SearchPresenter(this, Repository.getInstance(ApiClient.getInstance(getContext())
                , ConcreteLocalSource.getInstance(getContext()), view.getContext()));

        IngRecyclerView = view.findViewById(R.id.recyclerIngrediant);
        catRecyclerView = view.findViewById(R.id.recyclerCategory);
        conRecyclerView = view.findViewById(R.id.recyclerCountry);
        searchView = view.findViewById(R.id.searchimage);

        ingredientAdapter = new IngredientSearchAdapter(getContext(), ingeriedientPojos, this);
        categoryAdapter = new CategoryAdapter(getContext(), categoriesPojos, this);
        countryAdapter = new CountryAdapter(getContext(), areasPojos, this);

        IngGridLayoutManager = new GridLayoutManager(getContext(), 1);
        catGridLayoutManager = new GridLayoutManager(getContext(), 1);
        conGridLayoutManager = new GridLayoutManager(getContext(), 1);

        IngRecyclerView.setAdapter(ingredientAdapter);
        IngRecyclerView.setLayoutManager(IngGridLayoutManager);
        IngRecyclerView.setHasFixedSize(true);
        IngGridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        catRecyclerView.setAdapter(categoryAdapter);
        catRecyclerView.setLayoutManager(catGridLayoutManager);
        catRecyclerView.setHasFixedSize(true);
        catGridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);


        conRecyclerView.setAdapter(countryAdapter);
        conRecyclerView.setLayoutManager(conGridLayoutManager);
        conRecyclerView.setHasFixedSize(true);
        conGridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        //


        searchPresenterInterface.getIngredient();
        searchPresenterInterface.getCategoriesList();
        searchPresenterInterface.getCountriesList();

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              /*  SearchResult searchResult = new SearchResult();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.searchpage, searchResult);
                transaction.commit();*/
                Navigation.findNavController(view).navigate(R.id.action_searchpage_to_searchresult);
            }
        });

    }
    @Override
    public void addToFavoriteOnClick(ProductsPOJO item) {

    }

    @Override
    public void categoryItemOnClick(String category) {
        NavController navController = Navigation.findNavController(requireView());
        Bundle args = new Bundle();
        args.putString("category", category);
        navController.navigate(R.id.action_searchpage_to_searchresult, args);
    }

    @Override
    public void countryItemOnClick(String country) {
        NavController navController = Navigation.findNavController(requireView());
        Bundle args = new Bundle();
        args.putString("country", country);
        navController.navigate(R.id.action_searchpage_to_searchresult, args);
    }

    @Override
    public void ingredientItemOnclick(String ingredient) {
        NavController navController = Navigation.findNavController(requireView());
        Bundle args = new Bundle();
        args.putString("ingredient", ingredient);
        navController.navigate(R.id.action_searchpage_to_searchresult, args);
    }



    @Override
    public void ViewCategories(List<CategoriesPojo> categoriesPojos) {
        categoryAdapter.setList(categoriesPojos);
        categoryAdapter.notifyDataSetChanged();

    }

    @Override
    public void ViewCountries(List<AreasPojo> areasPojos) {
        countryAdapter.setList(areasPojos);
        countryAdapter.notifyDataSetChanged();

    }

    @Override
    public void ViewIngredient(List<IngeriedientPojo> ingeriedientPojos) {
        ingredientAdapter.setList(ingeriedientPojos);
        ingredientAdapter.notifyDataSetChanged();
        Log.d("Search", "ViewIngredient: Data received - " + ingeriedientPojos.size() + " items");


    }

    @Override
    public void getMeals(List<ProductsPOJO> productsPOJOS) {

    }

    @Override
    public void addToFavorite(ProductsPOJO productsPOJO) {

    }
}