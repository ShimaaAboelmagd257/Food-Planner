package com.example.foodplanner.model.LocalDataSource;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.foodplanner.model.Pojos.ProductsPOJO;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface DataAcessObjectDAO {

    @Query("SELECT * From Meals where isFavorite = '1'")
    Single<List<ProductsPOJO>> getFavorites() ;
    @Insert
    Completable insertFavorite (ProductsPOJO productpojo);
    @Delete
    Completable deleteFavorite (ProductsPOJO productpojo);
    @Query("SELECT * From Meals where nameDay =:day")
    Single<List<ProductsPOJO>> getAllplans(String day) ;
    @Insert
    Completable insertPlan(ProductsPOJO plans);
    @Delete
    Completable deletePlan (ProductsPOJO productpojo);
    @Query("DELETE FROM Meals")
    Completable deleteAllMeals() ;


}
