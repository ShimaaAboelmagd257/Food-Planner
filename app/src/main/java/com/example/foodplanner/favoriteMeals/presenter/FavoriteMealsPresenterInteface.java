package com.example.foodplanner.favoriteMeals.presenter;

import com.example.foodplanner.dataBaseHandling.Model.firebase.UserPojo;
import com.example.foodplanner.model.Pojos.ProductsPOJO;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface FavoriteMealsPresenterInteface {


    Single<List<ProductsPOJO>> getAllStoredFavorites();

    void removeFromFavorite(ProductsPOJO mealModel);

    void updateFavoriteInFirebase(UserPojo userModel);

    UserPojo getSavedData();
}
