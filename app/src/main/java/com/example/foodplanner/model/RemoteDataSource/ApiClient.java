package com.example.foodplanner.model.RemoteDataSource;

import android.content.Context;
import android.util.Log;

import com.example.foodplanner.model.Pojos.AreasPojoResponce;
import com.example.foodplanner.model.Pojos.CategoryPojoResponce ;
import com.example.foodplanner.model.Pojos.IngriendientPojoResponce;
import com.example.foodplanner.model.Pojos.ProductPojoResponce;
import com.example.foodplanner.model.Pojos.ProductsPOJO;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient implements RemoteSource{


    public static final String url="https://www.themealdb.com/api/json/v1/1/";
    UserService  userService;

    private static ApiClient apiClient = null;

    public ApiClient (Context context) {

        File cacheDirectory = new File(context.getCacheDir(), "offline_cache_directory");
        Cache cache = new Cache(cacheDirectory,100 *1024 * 1024);

        OkHttpClient okHttpClient = new OkHttpClient
                .Builder().cache(cache).build();

        Retrofit.Builder retrofitB = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create());

        Retrofit retrofit = retrofitB.build();

        userService = retrofit.create(UserService.class);


    }

    public static ApiClient getInstance(Context context){

        if (apiClient == null){
            apiClient = new ApiClient(context);
        }

        return apiClient;
    }

    @Override
    public void getRandomMeal(NetworkDelegate networkDelegate) {
        List<ProductsPOJO> list = new ArrayList<>();

        Flowable<ProductPojoResponce> mealModelResponseSingle = userService.getRandomMeal();

        mealModelResponseSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.e("HomeForYou", "Error fetching random meals", throwable))
                .onErrorComplete()
                .repeat(10)
                .doOnNext(e -> {
                    if (e != null && e.getMeals() != null) {
                        list.addAll(e.getMeals());
                    }
                })
                .doOnComplete(() -> {
                    networkDelegate.onSuccessMeals(list);
                    Log.d("HomeForYou", "getRandomMeal: Completed successfully");
                })
                .subscribe();
    }

    @Override
    public void getRandomMealWithTimestamp(NetworkDelegate networkDelegate) {
        List<ProductsPOJO> list = new ArrayList<>();

        // Generate a unique timestamp for each request
        long timestamp = System.currentTimeMillis();
        int randomNumber = new Random().nextInt(300000); // You can adjust the range as needed
        int uniqueParam = (int) (timestamp + randomNumber);

        Flowable<ProductPojoResponce> mealModelResponseSingle = userService.getRandomMealWithTimestamp(uniqueParam);

        mealModelResponseSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> {
                    Log.e("HomeForYou", "Error fetching random meals", throwable);
                    networkDelegate.onfailure(throwable.getMessage());
                })
                .onErrorComplete()
                .repeat(10) // You can adjust the number of repeats as needed
                .doOnNext(e -> {
                    if (e != null && e.getMeals() != null) {
                        list.addAll(e.getMeals());
                    }
                })
                .doOnComplete(() -> {
                    networkDelegate.onSuccessMeals2(list);
                    Log.d("HomeForYou", "getRandomMeal: Completed successfully");
                })
                .subscribe();
    }

    @Override
    public void getRandomMealWithTimetamp(NetworkDelegate networkDelegate) {
        List<ProductsPOJO> list = new ArrayList<>();

        // Generate a unique timestamp for each request
        long timestamp = System.currentTimeMillis();
        int randomNumber = new Random().nextInt(200000); // You can adjust the range as needed
        int uniqueParam = (int) (timestamp + randomNumber);

        Flowable<ProductPojoResponce> mealModelResponseSingle = userService.getRandomMealWithTimetamp(uniqueParam);

        mealModelResponseSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> {
                    Log.e("HomeForYou", "Error fetching random meals", throwable);
                    networkDelegate.onfailure(throwable.getMessage());
                })
                .onErrorComplete()
                .repeat(10) // You can adjust the number of repeats as needed
                .doOnNext(e -> {
                    if (e != null && e.getMeals() != null) {
                        list.addAll(e.getMeals());
                    }
                })
                .doOnComplete(() -> {
                    networkDelegate.onSuccessMeals3(list);
                    Log.d("HomeForYou", "getRandomMeal: Completed successfully");
                })
                .subscribe();
    }

   /* @Override
    public void getRandomMeal(NetworkDelegate networkDelegate) {
        List<ProductsPOJO> list = new ArrayList<>();

        Flowable<ProductPojoResponce> mealModelResponseFlowable = userService.getRandomMeal();

        mealModelResponseFlowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(errors -> errors
                        .zipWith(Flowable.range(1, 10), (throwable, attempt) -> attempt)
                        .flatMap(retryAttempt -> Flowable.timer(retryAttempt, TimeUnit.SECONDS))
                )
                .doOnNext(response -> {
                    if (response != null && response.getMeals() != null) {
                        list.addAll(response.getMeals());
                    }
                })
                .doOnComplete(() -> networkDelegate.onSuccessMeals(list))
                .doOnError(throwable -> {
                    // Handle the error, log it, or take appropriate action
                    Log.e("ApiClient", "Error fetching random meals", throwable);
                })
                .subscribe();
    }
*/

 /*   @Override
    public void getRandomMeal(NetworkDelegate networkDelegate) {
        List<ProductsPOJO> list=new ArrayList<>(); //Creates an empty list to store the fetched meals.
        Flowable<ProductPojoResponce> mealModelResponseSingle= userService.getRandomMeal(); // Using the mealService instance which returns a Flowable emitting MealModelResponse objects.
        mealModelResponseSingle.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                .repeat(10) //Repeats the API call 10 times. This may be useful if you want to retry the request multiple times.
                .doOnNext(e -> {
                    if (e != null && e.getMeals() != null) {
                        list.addAll(e.getMeals());
                    }
                })
                .doOnComplete(()->networkDelegate.onSuccessMeals(list))
                .subscribe();
    }*/

    @Override
    public void getAllCategories(NetworkDelegate networkDelegate) {
        Single<CategoryPojoResponce> categoryResponseSingle= userService.getAllCategories();
        categoryResponseSingle.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                .subscribe(item -> networkDelegate.onSuccessCategories(item.getCategories()));
    }
    @Override
    public void getAllAreas(NetworkDelegate networkDelegate) {
        Single<AreasPojoResponce> areaResponseSingle= userService.getAllAreas();
        areaResponseSingle.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                .subscribe(item -> networkDelegate.onSuccessArea(item.getAreas()));
    }

   /* @Override
    public void getAllAreas(NetworkDelegate networkDelegate) {
        Single<AreasPojoResponce> countryResponseSingle = userService.getAllAreas();
        countryResponseSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> {
                            networkDelegate.onSuccessArea(item.getArea());
                        }
                );
    }*/

    @Override
    public void getAllIngredients(NetworkDelegate networkDelegate) {
        Single<IngriendientPojoResponce> ingeriedientPojoSingle= userService.getAllIngredients();
        ingeriedientPojoSingle.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                .subscribe(item -> networkDelegate.onSuccessIngeriedient(item.getIngerdiants()));
    }

    @Override
    public void getMealByFirstChar(NetworkDelegate networkDelegate, String name) {
        Single<ProductPojoResponce> mealModelResponseSingle= userService.getMealByFirstChar(name);
        mealModelResponseSingle.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                .subscribe(item -> networkDelegate.onSuccessMeals(item.getMeals()));

    }

    @Override
    public void getMealsByCategory(NetworkDelegate networkDelegate, String category) {
        Single<ProductPojoResponce>  productsPOJOSingle= userService.getMealsByCategory(category);
        productsPOJOSingle.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                .subscribe(item -> networkDelegate.onSuccessMeals(item.getMeals()));
    }

    @Override
    public void getMealsByArea(NetworkDelegate networkDelegate, String country) {
        Single<ProductPojoResponce> countryResponseSingle= userService.getMealsByArea(country);
        countryResponseSingle.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                .subscribe(item -> networkDelegate.onSuccessMeals(item.getMeals()));


    }

    @Override
    public void getMealsByIngredient(NetworkDelegate networkDelegate, String ingredient) {
        Single<ProductPojoResponce> ingeriedientPojoSingle= userService.getMealsByIngredient(ingredient);
        ingeriedientPojoSingle.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                .subscribe(item -> networkDelegate.onSuccessMeals(item.getMeals()));

    }

    @Override
    public void getMealByName(NetworkDelegate networkDelegate, String name) {
        Single<ProductPojoResponce> mealModelResponseSingle= userService.getMealByName(name);
        mealModelResponseSingle.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                .subscribe(item -> networkDelegate.onSuccessMeals(item.getMeals()));

    }
}
