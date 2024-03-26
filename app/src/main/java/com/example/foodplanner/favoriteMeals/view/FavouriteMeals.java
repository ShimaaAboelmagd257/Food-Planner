package com.example.foodplanner.favoriteMeals.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.dataBaseHandling.Model.Reposatory.FirebaseRepository;
import com.example.foodplanner.dataBaseHandling.Model.Reposatory.Repository;
import com.example.foodplanner.dataBaseHandling.Model.firebase.FireBaseDataHandle;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserPojo;
import com.example.foodplanner.favoriteMeals.presenter.FavoriteMealsPresenterInteface;
import com.example.foodplanner.favoriteMeals.presenter.FavouriteMealsPresenter;
import com.example.foodplanner.model.LocalDataSource.ConcreteLocalSource;
import com.example.foodplanner.model.Pojos.ProductsPOJO;
import com.example.foodplanner.model.RemoteDataSource.ApiClient;
import com.example.foodplanner.model.sharedprefrence.SharedPreferencesource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class FavouriteMeals extends Fragment implements FavoriteViewInteface , onFavouriteClickListner{

    RecyclerView recyclerView;
    FavouriteAdapter favouriteAdapter;
    GridLayoutManager layoutManager;
    FavoriteMealsPresenterInteface favoriteMealsPresenterInteface;
    List<ProductsPOJO> favList= new ArrayList<>();
    ImageView saveicon;
    TextView saveText , saveText2 ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourite_meals, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // saveicon = view.findViewById(R.sec.saveicon);
        saveText = view.findViewById(R.id.seaveText);
        saveText2 = view.findViewById(R.id.saveText2);
        recyclerView= view.findViewById(R.id.favrecyclerView);

        recyclerView.setHasFixedSize(true);
        layoutManager=new GridLayoutManager(getContext(),1);
        favouriteAdapter=new FavouriteAdapter(getContext(),favList,this);

        favoriteMealsPresenterInteface = new FavouriteMealsPresenter((Repository.getInstance(ApiClient.getInstance(getContext()),
                ConcreteLocalSource.getInstance(getContext()),getContext()))
                , FirebaseRepository.getInstance(FireBaseDataHandle.getInstance(getContext())
                , SharedPreferencesource.getInstance(getContext()),getContext()));

        recyclerView.setAdapter(favouriteAdapter);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setHasFixedSize(true);
        showData();


    }


    @Override
    public void onRemoveFavClick(ProductsPOJO productsPOJO) {
        removeFromFav(productsPOJO);
        favouriteAdapter.removeFavorite(productsPOJO);
        favouriteAdapter.notifyDataSetChanged();
        Log.d("FavouriteMealView", "onRemoveFavClick: removing  data succesfully  " + productsPOJO.getStrMeal());

        Toast.makeText(getContext(), "Meal is deleted from favorite", Toast.LENGTH_SHORT).show();
        UserPojo userModel =favoriteMealsPresenterInteface.getSavedData();
        Log.d("FavouriteMealView", "onRemoveFavClick: removing  data succesfully  from user " + userModel.getEmail());

        userModel.setFavorites(favouriteAdapter.remove(productsPOJO));
        updateFavoriteInFirebase(userModel);
    }

    @Override
    public void removeFromFav(ProductsPOJO meal) {

        favoriteMealsPresenterInteface.removeFromFavorite(meal);

    }
    @SuppressLint("CheckResult")
    @Override
    public void showData() {
        favoriteMealsPresenterInteface.getAllStoredFavorites().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                .subscribe(item ->{
                    if(item.size() == 0){
                        recyclerView.setVisibility(View.GONE);
                        saveText.setVisibility(View.VISIBLE);
                        saveText2.setVisibility(View.VISIBLE);

                    }else {
                        recyclerView.setVisibility(View.VISIBLE);
                        saveText.setVisibility(View.GONE);
                        saveText2.setVisibility(View.GONE);
                        favList = item;
                        Log.d("FavouriteMealView", "showData : showing  data succesfully  of list  " + item.size());

                        favouriteAdapter.setList(item);
                        favouriteAdapter.notifyDataSetChanged();

                        UserPojo userModel =favoriteMealsPresenterInteface.getSavedData();
                        Log.d("FavouriteMealView", "showData : showing  data succesfully  from user " + userModel.getEmail());

                        userModel.setFavorites(favList);
                        updateFavoriteInFirebase(userModel);
                    }
                });
    }

    @Override
    public void updateFavoriteInFirebase(UserPojo userPojo) {
        favoriteMealsPresenterInteface.updateFavoriteInFirebase(userPojo);
    }
}