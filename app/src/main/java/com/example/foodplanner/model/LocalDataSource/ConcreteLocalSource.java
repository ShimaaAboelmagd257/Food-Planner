package com.example.foodplanner.model.LocalDataSource;


import android.content.Context;
import android.util.Log;

import com.example.foodplanner.R;
import com.example.foodplanner.model.Pojos.ProductsPOJO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class ConcreteLocalSource implements LocalSource {

   // LiveData<List<ProductsPOJO>> allProducts ;
   private  String [] days;
    private DataAcessObjectDAO Dao ;
    Single<List<ProductsPOJO>> mealFavorites;
    Single<List<ProductsPOJO>> mealplans;

    public static ConcreteLocalSource concreteLocalSource = null;


    private ConcreteLocalSource(Context context ){
        ProductDataBase productDataBase = ProductDataBase.getInstance(context.getApplicationContext());
        Dao = productDataBase.dataAcessObjectDAO();
        mealFavorites = Dao.getFavorites();
        days = context.getResources().getStringArray(R.array.weekdays);
       //  mealplans = Dao.getAllplans(days[0]);

    }

    public static ConcreteLocalSource getInstance(Context context ){
        if(concreteLocalSource == null){
            concreteLocalSource  = new ConcreteLocalSource(context );
        }
        Log.d("ConcreteLocalSource", " singleton successed " );

        return concreteLocalSource;
    }

   @Override
    public void insertFavorite(ProductsPOJO productpojo) {
       Log.d("ConcreteLocalSource", " insertFavorite successed "  + productpojo.getStrMeal() );

      Dao.insertFavorite(productpojo).subscribeOn(Schedulers.computation()).subscribe(new CompletableObserver() {
          @Override
          public void onSubscribe(Disposable d) {

          }

          @Override
          public void onComplete() {
              Log.d("ConcreteLocalSource", " insertFavorite  successed " + productpojo.getStrMeal());


          }

          @Override
          public void onError(Throwable e) {

              Log.d("ConcreteLocalSource", " insertFavorite  failed for  " + e.getMessage());

          }
      });

    }
    @Override
    public void removeFavorite(ProductsPOJO productpojo) {

         Dao.deleteFavorite(productpojo).subscribeOn(Schedulers.computation()).subscribe(new CompletableObserver() {
             @Override
             public void onSubscribe(Disposable d) {

             }

             @Override
             public void onComplete() {
                 Log.d("ConcreteLocalSource", " removeFavorite  successed " + productpojo.getStrMeal());


             }

             @Override
             public void onError(Throwable e) {

                 Log.d("ConcreteLocalSource", " removeFavorite  failed for  " + e.getMessage());

             }
         });
    }

    @Override
    public Single<List<ProductsPOJO >> getAllStoredFavorites() {
        Single<List<ProductsPOJO>>  favMeals = Dao.getFavorites();
        favMeals.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        productsPOJOS -> {
                            Log.d("ConcreteLocalSource", " getAllStoredFavorites  successed " + productsPOJOS.toString() + productsPOJOS.isEmpty());
                        },
                        throwable -> {
                            // Handle error
                            Log.e("ConcreteLocalSource", "Error fetching data for getAllStoredFavorites " + throwable.getMessage());
                        }
                );
        return favMeals;

    }

    @Override
    public void insertPlan(ProductsPOJO plans) {
        Log.d("ConcreteLocalSource", " insertPlan  successed " + plans.getStrMeal() + plans.getNameDay());

      Dao.insertPlan(plans).subscribeOn(Schedulers.computation()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.d("ConcreteLocalSource", " insertPlan  successed " + plans.getStrMeal() + plans.getNameDay());


            }

            @Override
            public void onError(Throwable e) {

                Log.d("ConcreteLocalSource", " insertPlan  failed for  " + e.getMessage());

            }
        });
    }
    @Override
    public void removePlan(ProductsPOJO productpojo) {

        Dao.deletePlan(productpojo).subscribeOn(Schedulers.computation()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.d("ConcreteLocalSource", " removePlan  successed " + productpojo.getStrMeal() + productpojo.getNameDay());


            }

            @Override
            public void onError(Throwable e) {

                Log.d("ConcreteLocalSource", " removePlan  failed for  " + e.getMessage());

            }
        });
    }

 @Override
 public Single<List<ProductsPOJO>> getAllPlans(String day) {
     Log.d("ConcreteLocalSource", "getAllPlans called for day: " + day);

     List<Single<List<ProductsPOJO>>> observables = new ArrayList<>();

     for (int i = 0; i < 7; i++) {
         Single<List<ProductsPOJO>> mealsplan = Dao.getAllplans(days[i]);
         observables.add(mealsplan.subscribeOn(Schedulers.io()));
     }

     return Single.zip(observables, arrays -> {
                 List<ProductsPOJO> resultList = new ArrayList<>();

                 for (int i = 0; i < arrays.length; i++) {
                     Object array = arrays[i];
                     if (array instanceof List<?>) {
                         List<ProductsPOJO> dayList = (List<ProductsPOJO>) array;
                         resultList.addAll(dayList);
                         Log.d("ConcreteLocalSource", "Results for " + days[i] + ": " + dayList.toString());
                     }
                 }

                 Log.d("ConcreteLocalSource", "Results of all plans fetched = " + resultList.size());
                 Log.d("ConcreteLocalSource", "Results added plans is empty ?  " + resultList.isEmpty());

                 return resultList;
             })
             .observeOn(AndroidSchedulers.mainThread());
 }

    @Override
    public void deleteAllMeals() {

        Dao.deleteAllMeals().subscribeOn(Schedulers.computation()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.d("ConcreteLocalSource", " deleteAllMeals  successed " );


            }

            @Override
            public void onError(Throwable e) {

                Log.d("ConcreteLocalSource", " deleteAllMeals  failed for  " + e.getMessage());

            }
        });


    }
}
