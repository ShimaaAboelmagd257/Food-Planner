package com.example.foodplanner.favoriteMeals.presenter;

import android.util.Log;

import com.example.foodplanner.dataBaseHandling.Model.Reposatory.FirebaseRepo;
import com.example.foodplanner.dataBaseHandling.Model.Reposatory.FirebaseRepository;
import com.example.foodplanner.dataBaseHandling.Model.Reposatory.RepoInterface;
import com.example.foodplanner.dataBaseHandling.Model.Reposatory.Repository;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserPojo;
import com.example.foodplanner.favoriteMeals.view.FavoriteViewInteface;
import com.example.foodplanner.model.Pojos.AreasPojo;
import com.example.foodplanner.model.Pojos.CategoriesPojo;
import com.example.foodplanner.model.Pojos.IngeriedientPojo;
import com.example.foodplanner.model.Pojos.ProductsPOJO;
import com.example.foodplanner.model.RemoteDataSource.NetworkDelegate;
import com.google.firebase.database.core.Repo;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class FavouriteMealsPresenter implements  FavoriteMealsPresenterInteface  {

      RepoInterface repo;
    FirebaseRepo firebaseRepo;
    FavoriteViewInteface view ;


    public FavouriteMealsPresenter(RepoInterface repo,  FirebaseRepo firebaseRepo) {
        this.repo = repo;
        this.firebaseRepo = firebaseRepo;
    }

    @Override
    public Single<List<ProductsPOJO>> getAllStoredFavorites() {
        Log.d("FavouriteMealPresenter", "getAllStoredFavorites :  get  data succesfully  " );

        return repo.getAllStoredFavorites();
    }

    @Override
    public void removeFromFavorite(ProductsPOJO mealModel) {
        repo.removeFavorite(mealModel);

    }

    @Override
    public void updateFavoriteInFirebase(UserPojo userModel) {
        firebaseRepo.updateFavoriteInFirebase(userModel);

    }

    @Override
    public UserPojo getSavedData() {
        Log.d("FavouriteMealPresenter", "getSavedData :  get  data succesfully for user  " +firebaseRepo.getSavedUserData().getEmail() );

        return firebaseRepo.getSavedUserData();
    }


}
