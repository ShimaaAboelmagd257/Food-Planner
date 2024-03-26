package com.example.foodplanner.model.Pojos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductPojoResponce {

    @SerializedName("meals")
    private List<ProductsPOJO> meals;
    public ProductPojoResponce(List<ProductsPOJO> mealsModel) {
        this.meals = mealsModel;
    }
    public List<ProductsPOJO> getMeals() {
        return meals;
    }
    public void setMeals(List<ProductsPOJO> meals) {
        this.meals = meals;
    }


}