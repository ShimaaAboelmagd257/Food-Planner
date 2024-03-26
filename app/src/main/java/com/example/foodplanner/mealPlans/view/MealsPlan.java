package com.example.foodplanner.mealPlans.view;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.R;
import com.example.foodplanner.dataBaseHandling.Model.Reposatory.FirebaseRepository;
import com.example.foodplanner.dataBaseHandling.Model.Reposatory.Repository;
import com.example.foodplanner.dataBaseHandling.Model.firebase.FireBaseDataHandle;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserPojo;
import com.example.foodplanner.homeforyou.view.HomeViewModel;
import com.example.foodplanner.mealPlans.presenter.MealsPlanPresenter;
import com.example.foodplanner.mealPlans.presenter.MealsPlanPresenterInteface;
import com.example.foodplanner.model.LocalDataSource.ConcreteLocalSource;
import com.example.foodplanner.model.Pojos.ProductsPOJO;
import com.example.foodplanner.model.RemoteDataSource.ApiClient;
import com.example.foodplanner.model.sharedprefrence.SharedPreferencesource;
import com.example.foodplanner.utility.Helper.Helper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MealsPlan extends Fragment implements MealsPlanViewInterface, PlanClickListner  {

    LinearLayoutManager[] linearLayoutManager=new LinearLayoutManager[7];
    RecyclerView[] recyclerView=new RecyclerView[7];
    private static final int DAYS_COUNT = 7;
    MealsPlanAdapter[] mealsPlanAdapters=new MealsPlanAdapter[7];
    MealsPlanPresenterInteface planPresenterInterface;
    String [] days;
    HomeViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //viewModel = new ViewModelProvider(this, new PlanMealsViewModel()).get(PlanMealsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

     //   mealsPlanAdapter = new MealsPlanAdapter(getContext(),pojoList,this);
        return inflater.inflate(R.layout.fragment_meals_plan, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        days = getResources().getStringArray(R.array.weekdays);
        initRecyclerViews(view);
      //  viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        planPresenterInterface = new MealsPlanPresenter((Repository.getInstance(ApiClient.getInstance(getContext()),
                ConcreteLocalSource.getInstance(getContext()),getContext()))
                , FirebaseRepository.getInstance(FireBaseDataHandle.getInstance(getContext())
                , SharedPreferencesource.getInstance(getContext()),getContext()));
        getPlan(0);
        getPlan(1);
        getPlan(2);
        getPlan(3);
        getPlan(4);
        getPlan(5);
        getPlan(6);

    }
/*    private static final int[] RECYCLER_VIEW_IDS = {
            R.id.recyclerSaturday,
            R.id.recyclerSunday,
            R.id.recyclerMonday,
            R.id.recyclerTuesday,
            R.id.recyclerWednesday,
            R.id.recyclerThursday,
            R.id.recyclerFriday
    }*/
     void initRecyclerViews(View view) {
        if (view != null){
            recyclerView[0]= view.findViewById(R.id.recyclerSaturday);
            recyclerView[1] = view.findViewById(R.id.recyclerSunday);
            recyclerView[2] = view.findViewById(R.id.recyclerMonday);
            recyclerView[3] = view.findViewById(R.id.recyclerTuesday);
            recyclerView[4] = view.findViewById(R.id.recyclerWednesday);
            recyclerView[5] = view.findViewById(R.id.recyclerThursday);
            recyclerView[6] = view.findViewById(R.id.recyclerFriday);


        for (int i = 0; i < 7; i++) {
            linearLayoutManager[i] = new LinearLayoutManager(view.getContext());
            mealsPlanAdapters[i] = new MealsPlanAdapter(view.getContext(), new ArrayList<>(), this);
            recyclerView[i].setLayoutManager(linearLayoutManager[i]);
            recyclerView[i].setAdapter(mealsPlanAdapters[i]);
            linearLayoutManager[i].setOrientation(RecyclerView.HORIZONTAL);
            recyclerView[i].setHasFixedSize(true);
        }
    }
    }
    @Override
    public void viewPlan(int i, String[] days) {
        //planPresenterInterface.getAllPlanedMeals(days[i]);
    }
    public void getPlan(int i) {
        planPresenterInterface.getAllPlanedMeals(days[i]).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                .subscribe(e -> {
                    mealsPlanAdapters[i].setList(e);
                    uploadPlansInFirebase(days[i],e);

                    Log.e("MealsPlanView", "Data received for " + days[i] + " is empty: " + e.isEmpty());
                }, throwable -> {
                    // Handle the error here
                    Log.e("MealsPlanView", "Error fetching data for " + days[i] + ": " + throwable.getMessage());
                }, () -> {

                        mealsPlanAdapters[i].notifyDataSetChanged();
                        Log.e("MealsPlanView", " notify data set changed ");


                });
    }
    @Override
    public void showPlanedMeals(int i ,List<ProductsPOJO> plans) {

        /*recyclerView[i].setLayoutManager(linearLayoutManager[i]);
        recyclerView[i].setAdapter(mealsPlanAdapters[i]);
        recyclerView[i].setHasFixedSize(true);
        linearLayoutManager[i].setOrientation(RecyclerView.HORIZONTAL);
        mealsPlanAdapters[i].setList(plans);
        mealsPlanAdapters[i].notifyDataSetChanged();
        Log.e("MealsPlan", "fetching data: " + plans);*/
    }



    void uploadPlansInFirebase(String plan,List<ProductsPOJO> planList){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(); //initializes a reference to the root of the Firebase Realtime Database.
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Helper.SHARDPREFERENCE,getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        UserPojo userModel = new UserPojo();
        //retrieves user details
        userModel.setUserName(sharedPreferences.getString(Helper.USERNAME,"Null"));
        userModel.setEmail(sharedPreferences.getString(Helper.EMAIL,"Null"));
        userModel.setPassWord(sharedPreferences.getString(Helper.PASSWORD,"Null"));
        userModel.setImage(sharedPreferences.getString(Helper.IMAGE,"null"));
        //sets the value of the child node named after the user's email
        List<String> route = Arrays.asList(userModel.getEmail().split("\\."));
        databaseReference.child("User").child(route.get(0)).child(plan).setValue(planList)

                .addOnSuccessListener(aVoid -> Log.d("FirebaseUpload", "Upload successful"))
                .addOnFailureListener(e -> Log.e("FirebaseUpload", "Upload failed: " + e.getMessage()));
    }

    @Override
    public void removeMealFromPlaned(ProductsPOJO meal) {

    }

    @Override
    public void ViewData(String day) {
       /* planPresenterInterface.getAllPlanedMeals(day).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                .subscribe(item ->{
                    pojoList = item;

                    mealsPlanAdapter.setList(item);
                    mealsPlanAdapter.notifyDataSetChanged();

                    UserPojo userModel =planPresenterInterface.getSavedData();
                    switch (day) {
                        case "Saturday":
                            userModel.setSaturday(pojoList);
                            break;
                        case "Sunday":
                            userModel.setSunday(pojoList);
                            break;
                        case "Monday" :
                             userModel.setMonday(pojoList);
                            break;
                        case "Tuesday" :
                            userModel.setTuesday(pojoList);
                            break;
                        case "Wednesday" :
                            userModel.setWednesday(pojoList);
                            break;
                        case "Thursday" :
                            userModel.setThursday(pojoList);
                            break;
                        case "Friday":
                            userModel.setFriday(pojoList);
                            break;
                    }
                    uploadPlanInFirebase(userModel);

                },throwable -> {
                    Log.e(TAG, "Error fetching data: " + throwable.getMessage());
                });*/
    }

    @Override
    public void uploadPlanInFirebase(UserPojo userModel) {
        planPresenterInterface.uploadPlanInFirebase(userModel);

    }



    @Override
    public void onRemovePlanClicked(ProductsPOJO productsPOJO) {

    }

    @Override
    public void addToFavoriteOnClick(ProductsPOJO productsPOJO) {
        planPresenterInterface.addToFavorite(productsPOJO);
    }




}