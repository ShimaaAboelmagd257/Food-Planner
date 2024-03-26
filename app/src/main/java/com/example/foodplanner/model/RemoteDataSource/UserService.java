package com.example.foodplanner.model.RemoteDataSource;

import com.example.foodplanner.model.Pojos.AreasPojoResponce;
import com.example.foodplanner.model.Pojos.CategoryPojoResponce ;
import com.example.foodplanner.model.Pojos.IngriendientPojoResponce;
import com.example.foodplanner.model.Pojos.ProductPojoResponce;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

//Define the Endpoints
public interface UserService {

    @GET("random.php")
    Flowable<ProductPojoResponce> getRandomMeal ();
    @GET("random.php")
    Flowable<ProductPojoResponce> getRandomMealWithTimestamp(@Query("timestamp") long timestamp);
    @GET("random.php")
    Flowable<ProductPojoResponce> getRandomMealWithTimetamp(@Query("timestamp") long timestamp);
    @GET("categories.php")
    Single<CategoryPojoResponce> getAllCategories();

    @GET("list.php?a=list")
    Single<AreasPojoResponce> getAllAreas();

    @GET("list.php?i=list")
    Single<IngriendientPojoResponce> getAllIngredients();

    @GET("search.php")
    Single<ProductPojoResponce> getMealByName (@Query("s") String name);

    @GET("search.php")
    Single<ProductPojoResponce> getMealByFirstChar (@Query("f") String name);

   @GET("lookup.php")
    Single<ProductPojoResponce> getMealByID (@Query("d") String name);

    @GET("filter.php")
    Single<ProductPojoResponce> getMealsByCategory(@Query("c") String category);

    @GET("filter.php")
    Single<ProductPojoResponce> getMealsByArea(@Query("a") String country);

    @GET("filter.php")
    Single<ProductPojoResponce> getMealsByIngredient(@Query("i") String ingredient);




}
