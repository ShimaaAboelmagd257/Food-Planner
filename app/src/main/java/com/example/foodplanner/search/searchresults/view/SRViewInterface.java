package com.example.foodplanner.search.searchresults.view;

import com.example.foodplanner.model.Pojos.AreasPojo;
import com.example.foodplanner.model.Pojos.CategoriesPojo;
import com.example.foodplanner.model.Pojos.IngeriedientPojo;
import com.example.foodplanner.model.Pojos.ProductsPOJO;

import java.util.List;

public interface SRViewInterface {



    void showMealResults(List<ProductsPOJO> meals);
    void showCountryResults(List<AreasPojo> countries);
    void showCategoryResults(List<CategoriesPojo> categories);
    void showIngredientResults(List<IngeriedientPojo > ingeriedient);

}
