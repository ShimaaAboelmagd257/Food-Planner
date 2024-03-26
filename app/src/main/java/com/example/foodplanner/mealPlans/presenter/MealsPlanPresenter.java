package com.example.foodplanner.mealPlans.presenter;

import android.util.Log;
import com.example.foodplanner.dataBaseHandling.Model.Reposatory.FirebaseRepo;
import com.example.foodplanner.dataBaseHandling.Model.Reposatory.RepoInterface;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserPojo;
import com.example.foodplanner.model.Pojos.ProductsPOJO;
import java.util.List;
import io.reactivex.rxjava3.core.Single;




public class MealsPlanPresenter implements MealsPlanPresenterInteface{

    private RepoInterface repo;
    private FirebaseRepo firebaseRepo;

    public MealsPlanPresenter(RepoInterface repo,FirebaseRepo firebaseRepo){
        this.repo = repo ;
        this.firebaseRepo=firebaseRepo;
    }

    @Override
    public Single<List<ProductsPOJO>>getAllPlanedMeals(String day) {
        Log.e("MealPlanPresenter ", "getAllPlanedMeals: Retrieved data for day " + day );
        return repo.getAllStoredPlans(day);
    }


    @Override
    public void removeFromPlan(ProductsPOJO mealModel) {
     repo.removePlan(mealModel);
        Log.e("MealPlanPrep", "removeFromPlan: for meal  " + mealModel.getStrMeal());

    }

    @Override
    public void uploadPlanInFirebase(UserPojo userModel) {
       firebaseRepo.uploadPlanInFirebase(userModel);
        Log.e("MealPlanPrep", "uploadPlanInFirebase: for user " + userModel.getEmail());
    }

    @Override
    public UserPojo getSavedData() {
        Log.e("MealPlanPrep", "getSavedData: for user " + firebaseRepo.getSavedUserData().getEmail());
        return firebaseRepo.getSavedUserData();
    }


    @Override
    public void addToFavorite(ProductsPOJO mealModel) {
                 repo.insertFavorite(mealModel);
        Log.e("MealPlanPrep", "addToFavorite: for meal  " + mealModel.getStrMeal());

    }
}
