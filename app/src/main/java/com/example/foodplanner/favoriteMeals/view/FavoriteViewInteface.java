package com.example.foodplanner.favoriteMeals.view;

import com.example.foodplanner.dataBaseHandling.Model.firebase.UserPojo;
import com.example.foodplanner.model.Pojos.ProductsPOJO;

public interface FavoriteViewInteface {
    void removeFromFav(ProductsPOJO meal);

     void showData();

     void updateFavoriteInFirebase(UserPojo userPojo);

}
