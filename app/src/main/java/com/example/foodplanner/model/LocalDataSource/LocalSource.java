package com.example.foodplanner.model.LocalDataSource;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodplanner.model.Pojos.ProductsPOJO;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface LocalSource {

    void insertFavorite(ProductsPOJO productpojo);
    void removeFavorite(ProductsPOJO productpojo);
    Single<List<ProductsPOJO >> getAllStoredFavorites();
    void insertPlan(ProductsPOJO plans);
    void removePlan(ProductsPOJO productpojo);
    Single<List<ProductsPOJO>> getAllPlans(String day);
    void deleteAllMeals();
}
