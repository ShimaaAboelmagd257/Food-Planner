package com.example.foodplanner.mealPlans.presenter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodplanner.dataBaseHandling.Model.firebase.UserPojo;
import com.example.foodplanner.model.Pojos.ProductsPOJO;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface MealsPlanPresenterInteface {

    /*@Override
     public MutableLiveData<List<ProductsPOJO>>getAllPlanedMeals(String day) {
         Log.e("MealPlanPrep", "getAllPlanedMeals: " +day);

         return f; ///repo.getAllStoredPlans(day);
     }*/
    Single<List<ProductsPOJO>>getAllPlanedMeals(String day);

    // MutableLiveData<List<ProductsPOJO>> getAllPlanedMeals(String day);
    void removeFromPlan(ProductsPOJO mealModel);
    void uploadPlanInFirebase(UserPojo userModel);
    UserPojo getSavedData();
     void addToFavorite(ProductsPOJO mealModel);
}
