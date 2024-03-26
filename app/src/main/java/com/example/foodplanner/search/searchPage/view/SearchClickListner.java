package com.example.foodplanner.search.searchPage.view;

import com.example.foodplanner.model.Pojos.ProductsPOJO;

public interface SearchClickListner {
    void addToFavoriteOnClick(ProductsPOJO item);
    void categoryItemOnClick(String category);
    void countryItemOnClick(String country);
    void ingredientItemOnclick(String ingredient);


}
