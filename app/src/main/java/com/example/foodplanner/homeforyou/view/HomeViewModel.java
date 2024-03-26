package com.example.foodplanner.homeforyou.view;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodplanner.dataBaseHandling.Model.Reposatory.RepoInterface;
import com.example.foodplanner.dataBaseHandling.Model.Reposatory.Repository;
import com.example.foodplanner.model.LocalDataSource.ConcreteLocalSource;
import com.example.foodplanner.model.Pojos.ProductsPOJO;
import com.example.foodplanner.model.RemoteDataSource.ApiClient;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private RepoInterface repo;
    private MutableLiveData<List<ProductsPOJO>> favoritesLiveData = new MutableLiveData<>();
    private MutableLiveData<List<List<ProductsPOJO>>> plansLiveData = new MutableLiveData<>();
    String day;

    public HomeViewModel(){

    }
    public HomeViewModel(Application application) {
        repo = Repository.getInstance(ApiClient.getInstance(application), ConcreteLocalSource.getInstance(application), application);
        plansLiveData.setValue(new ArrayList<>());
    }

    // Getter methods for LiveData
    public LiveData<List<ProductsPOJO>> getFavoritesLiveData() {
        return favoritesLiveData;
    }

  /*  public LiveData<List<List<ProductsPOJO>>> getPlansLiveData(String day) {
        repo.getAllStoredPlans(day).observeForever(productsPOJOList -> {
            // Assuming productsPOJOList is a list of plans for the day
            List<List<ProductsPOJO>> plansList = new ArrayList<>();
            plansList.add(productsPOJOList);

            plansLiveData.setValue(plansList);
        });

        return plansLiveData;
    }*/

    // Methods to update LiveData
    public void updateFavorites(List<ProductsPOJO> favorites) {
        favoritesLiveData.setValue(favorites);
    }

    public void updatePlans(String day, ProductsPOJO product) {
        // Create a copy of the current plans
        List<List<ProductsPOJO>> currentPlans = new ArrayList<>(plansLiveData.getValue());

        // Update plans for the specific day
        boolean dayExists = false;
        for (List<ProductsPOJO> dayPlans : currentPlans) {
            if (day.equals(dayPlans.get(0).getNameDay())) {
                dayExists = true;
                dayPlans.add(product);
                break;
            }
        }

        // If the day doesn't exist in the plans, create a new list for that day
        if (!dayExists) {
            List<ProductsPOJO> newDayPlans = new ArrayList<>();
            newDayPlans.add(product);
            currentPlans.add(newDayPlans);
        }

        // Update the LiveData with the new plans
        plansLiveData.setValue(currentPlans);
    }
}
