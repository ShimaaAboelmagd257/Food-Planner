package com.example.foodplanner.homeforyou.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.R;
import com.example.foodplanner.dataBaseHandling.Model.Reposatory.Repository;
import com.example.foodplanner.homeforyou.presenter.HomePresenter;
import com.example.foodplanner.homeforyou.presenter.HomePresenterInterface;
import com.example.foodplanner.mealPreparation.view.MealPreparation;
import com.example.foodplanner.model.LocalDataSource.ConcreteLocalSource;
import com.example.foodplanner.model.Pojos.AreasPojo;
import com.example.foodplanner.model.Pojos.CategoriesPojo;
import com.example.foodplanner.model.Pojos.ProductsPOJO;
import com.example.foodplanner.model.RemoteDataSource.ApiClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Home extends Fragment implements HomeInterface , HomeOnClickListner {
     Context context;
    private LottieAnimationView lottieAnimationView;
    private ExecutorService executor ;
    RecyclerView  categoryRecyclerView , countryRecyclerView;

    public static List<ProductsPOJO> favorite;
    public static List<List<ProductsPOJO>> plans;


    HomePresenterInterface homePagePresenterInterface;
    LinearLayoutManager mealLayoutManager, CategorieLayoutManager, countryLayoutManager;
    //managing the layout of the RecyclerViews.

    RecycleCountryAdepter recycleCountryAdepter;
    RecycleCategoryAdepter  recycleCategoryAdepter;
    RecyclerRandomAdapter recyclerRandomAdapter;
    RecyclerRandomAdapter recyclerRandomAdapter2;
    RecyclerRandomAdapter recyclerRandomAdapter3;

    //for binding data to the RecyclerViews.



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lottieAnimationView = view.findViewById(R.id.animationViewhome);
        ViewPager2 mealRecyclerView = view.findViewById(R.id.viewpagerhome);
        ViewPager2 mealRecyclerView2 = view.findViewById(R.id.viewpagerhome2);
        ViewPager2 mealRecyclerView3 = view.findViewById(R.id.viewpagerhome3);
        executor = Executors.newSingleThreadExecutor();
        //startAnimation();
        mealRecyclerView.setAdapter(new RecyclerRandomAdapter(view.getContext(), new ArrayList<>() ));
        mealRecyclerView.setOffscreenPageLimit(2);
        mealRecyclerView.setPageTransformer(new SliderTransformer(2));

        mealRecyclerView2.setAdapter(new RecyclerRandomAdapter(view.getContext(), new ArrayList<>() ));
        mealRecyclerView2.setOffscreenPageLimit(2);
        mealRecyclerView2.setPageTransformer(new SliderTransformer(2));

        mealRecyclerView3.setAdapter(new RecyclerRandomAdapter(view.getContext(), new ArrayList<>() ));
        mealRecyclerView3.setOffscreenPageLimit(2);
        mealRecyclerView3.setPageTransformer(new SliderTransformer(2));

        //mealRecyclerView = view.findViewById(R.id.viewpagerhome);
       // categoryRecyclerView = view.findViewById(R.id.recyclerCategoryhome);
       // countryRecyclerView = view.findViewById(R.id.recyclerCountryhome);


        //mealLayoutManager = new LinearLayoutManager(view.getContext());
      //  CategorieLayoutManager = new LinearLayoutManager(view.getContext());
     //   countryLayoutManager = new LinearLayoutManager(view.getContext());
       // recyclerRandomAdapter = new RecyclerRandomAdapter(getContext(),favorite,this);
      //  recycleCountryAdepter = new RecycleCountryAdepter(view.getContext(), new ArrayList<>() , this);
      //  recycleCategoryAdepter = new RecycleCategoryAdepter(view.getContext(), new ArrayList<>(),this);
        recyclerRandomAdapter = new RecyclerRandomAdapter(view.getContext(), new ArrayList<>() , this );
        recyclerRandomAdapter2 = new RecyclerRandomAdapter(view.getContext(), new ArrayList<>() , this );
        recyclerRandomAdapter3 = new RecyclerRandomAdapter(view.getContext(), new ArrayList<>() , this );




        homePagePresenterInterface = new HomePresenter(this, Repository.getInstance(ApiClient.getInstance(getContext()
        ), ConcreteLocalSource.getInstance(getContext()), view.getContext()));


      //  mealRecyclerView.setLayoutManager(mealLayoutManager);
        mealRecyclerView.setAdapter(recyclerRandomAdapter);
        mealRecyclerView2.setAdapter(recyclerRandomAdapter2);
        mealRecyclerView3.setAdapter(recyclerRandomAdapter3);

     /*   categoryRecyclerView.setLayoutManager(CategorieLayoutManager);
        categoryRecyclerView.setAdapter(recycleCategoryAdepter);

        countryRecyclerView.setLayoutManager(countryLayoutManager);
        countryRecyclerView.setAdapter(recycleCountryAdepter);*/



      //  mealRecyclerView.setHasFixedSize(true);
     /*   categoryRecyclerView.setHasFixedSize(true);
        countryRecyclerView.setHasFixedSize(true);*/

      //  mealLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
//        CategorieLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
       // countryLayoutManager.setOrientation(RecyclerView.HORIZONTAL);


        homePagePresenterInterface.getRandomMeal3();

        homePagePresenterInterface.getRandomMeal();
      /*  homePagePresenterInterface.getCategoriesList();
        homePagePresenterInterface.getCountriesList();*/
        homePagePresenterInterface.getRandomMeal2();

       if (favorite != null && favorite.size() != 0) {
            for (int i = 0; i < favorite.size(); i++) {
                insertFavInRoom(favorite.get(i));
                Log.e("HomeMealView", "sending data to room fav is empty ??  " + favorite.isEmpty());

            }
            favorite.clear();
        }

        if (plans != null && plans.size() > 0) {
            for (int i = 0; i < plans.size(); i++) {
                for (int j = 0; j < plans.get(i).size(); j++) {
                    insertPlanInRoom(plans.get(i).get(j));
                    Log.e("HomeMealView", "sending data to room plan is empty ??  " + plans.isEmpty());

                }
            }
            plans.clear();
        }
        ViewRandomMeal(new ArrayList<>());

    }



    @Override
    public void ViewRandomMeal(List<ProductsPOJO> models) {
        executor.submit(() -> {
            getActivity().runOnUiThread(() -> {
                if (models.size() == 0) {
                    startAnimation();
                } else {
                    onDataLoaded();
                    recyclerRandomAdapter.setViewPagerAdepterList(models);
                    recyclerRandomAdapter.notifyDataSetChanged();
                    Log.d("HomeForYou", "ViewRandomMeal: Data received - " + models.size() + " items");
                }
            });
        });
    }

    private void startAnimation() {
        // Ensure that the LottieAnimationView is not null
        if (lottieAnimationView != null) {
            lottieAnimationView.setVisibility(View.VISIBLE);
            lottieAnimationView.setAnimation("loadingpizza.json"); // Set the JSON file
            lottieAnimationView.playAnimation(); // Start the animation
        }
    }

    // Call this method when data is fully loaded
    private void onDataLoaded() {
        getActivity().runOnUiThread(() -> {
            lottieAnimationView.setVisibility(View.GONE);
        });
    }

    @Override
    public void ViewRandomMeal2(List<ProductsPOJO> models) {
        recyclerRandomAdapter2.setViewPagerAdepterList(models);
        recyclerRandomAdapter2.notifyDataSetChanged();
        Log.d("HomeForYou", "ViewRandomMeal: Data received - " + models.size() + " items");
        System.out.println("All meals :"+models.get(0).getStrMeal());

    }
    @Override
    public void ViewRandomMeal3(List<ProductsPOJO> models) {
        recyclerRandomAdapter3.setViewPagerAdepterList(models);
        recyclerRandomAdapter3.notifyDataSetChanged();
        Log.d("HomeForYou", "ViewRandomMeal: Data received - " + models.size() + " items");
        System.out.println("All meals :"+models.get(0).getStrMeal());

    }

    @Override
    public void ViewCountriesList(List<AreasPojo> models) {
       // recycleCountryAdepter.setRecycleCountryAdepterList(models);
      //  recycleCountryAdepter.notifyDataSetChanged();
//        System.out.println("All Categories :"+models.get(0).getStrArea());

        //Log.d("HomeForYou", "ViewCountriesList: Data received - " + models.size() + " items");

    }

    @Override
    public void ViewCategoriesList(List<CategoriesPojo> models) {

       // recycleCategoryAdepter.setCategoryModelList(models);
      //  recycleCategoryAdepter.notifyDataSetChanged();
        Log.d("HomeForYou", "ViewCategoriesList: Data received - " + models.size() + " items");

    }

    @Override
    public void addToFavorite(ProductsPOJO mealModel) {
        //homePagePresenterInterface.addToFavorite(mealModel);

    }

    @Override
    public void insertFavInRoom(ProductsPOJO mealModel) {
        homePagePresenterInterface.insertFavInRoom(mealModel);
    }
    @Override
    public void insertPlanInRoom(ProductsPOJO productsPOJO) {
        homePagePresenterInterface.insertPlanInRoom(productsPOJO);
    }

    @Override
    public void addToFavoriteOnClick(ProductsPOJO mealModel) {
     // homePagePresenterInterface.addToFavorite(mealModel);
    }

    @Override
    public void nevToItemPage(ProductsPOJO model) {
        Intent intent = new Intent( context, MealPreparation.class);

        // Pass necessary data to the next activity using Intent extras
        intent.putExtra("mealName", model.getStrMeal());
        intent.putExtra("mealID", model.getIdMeal());
        intent.putExtra("mealthumb", model.getStrMealThumb());

        // Start the new activity
        context.startActivity(intent);
    }

    @Override
    public void categoryItemOnClick(String category) {
        NavController navController = Navigation.findNavController(requireView());
        Bundle args = new Bundle();
        args.putString("categoryhome", category);
        navController.navigate(R.id.action_home_to_searchresult, args);

    }

    @Override
    public void countryItemOnClick(String country) {
        NavController navController = Navigation.findNavController(requireView());
        Bundle args = new Bundle();
        args.putString("countryhome", country);
        navController.navigate(R.id.action_home_to_searchresult, args);    }

    @Override
    public void mealItemOnclick(String meal) {
         homePagePresenterInterface.getMealsByName(meal);
    }
}