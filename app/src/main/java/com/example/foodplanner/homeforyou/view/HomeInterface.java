package com.example.foodplanner.homeforyou.view;

import com.example.foodplanner.model.Pojos.AreasPojo;
import com.example.foodplanner.model.Pojos.CategoriesPojo;
import com.example.foodplanner.model.Pojos.ProductsPOJO;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;

public interface HomeInterface {
    void ViewRandomMeal(List<ProductsPOJO> models);

    void ViewRandomMeal2(List<ProductsPOJO> models);

    void ViewRandomMeal3(List<ProductsPOJO> models);

    void ViewCountriesList(List<AreasPojo> models);
    void ViewCategoriesList(List<CategoriesPojo> models);
    void addToFavorite (ProductsPOJO mealModel);
    void insertFavInRoom(ProductsPOJO mealModel);
     void insertPlanInRoom(ProductsPOJO plans) ;

}
