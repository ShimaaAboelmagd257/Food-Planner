package com.example.foodplanner.homeforyou.view;

import com.example.foodplanner.model.Pojos.ProductsPOJO;

public interface HomeOnClickListner {
    void addToFavoriteOnClick(ProductsPOJO mealModel);
    void nevToItemPage(ProductsPOJO model);

    void categoryItemOnClick(String category);
    void countryItemOnClick(String country);
    void mealItemOnclick(String meal);
}
