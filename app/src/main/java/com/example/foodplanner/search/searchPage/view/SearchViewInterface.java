package com.example.foodplanner.search.searchPage.view;

import com.example.foodplanner.model.Pojos.AreasPojo;
import com.example.foodplanner.model.Pojos.CategoriesPojo;
import com.example.foodplanner.model.Pojos.IngeriedientPojo;
import com.example.foodplanner.model.Pojos.ProductsPOJO;

import java.util.List;

public interface SearchViewInterface {

     void ViewCategories(List<CategoriesPojo> categoriesPojos);

     void ViewCountries(List<AreasPojo> areasPojos);

     void ViewIngredient(List<IngeriedientPojo> ingeriedientPojos);

     void getMeals(List<ProductsPOJO> productsPOJOS);

     void addToFavorite (ProductsPOJO productsPOJO);
}
