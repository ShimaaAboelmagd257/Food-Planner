package com.example.foodplanner.mealPlans.view;

import com.example.foodplanner.dataBaseHandling.Model.firebase.UserPojo;
import com.example.foodplanner.model.Pojos.ProductsPOJO;

import java.util.List;

public interface MealsPlanViewInterface {

    void showPlanedMeals(int i,List<ProductsPOJO> plans);

    void viewPlan(int i, String[] days);

    void removeMealFromPlaned(ProductsPOJO meal);

     void ViewData(String day);

     void uploadPlanInFirebase(UserPojo userModel);

}
