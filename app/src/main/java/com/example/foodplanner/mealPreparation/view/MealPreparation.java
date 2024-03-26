package com.example.foodplanner.mealPreparation.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.R;
import com.example.foodplanner.dataBaseHandling.Model.Reposatory.Repository;
import com.example.foodplanner.homeforyou.view.HomeViewModel;
import com.example.foodplanner.mealPreparation.Presenter.MealPreparationPresenter;
import com.example.foodplanner.mealPreparation.Presenter.PreparationPresenterInterface;
import com.example.foodplanner.model.LocalDataSource.ConcreteLocalSource;
import com.example.foodplanner.model.Pojos.IngeriedientPojo;
import com.example.foodplanner.model.Pojos.MealUtility;
import com.example.foodplanner.model.Pojos.ProductsPOJO;
import com.example.foodplanner.model.RemoteDataSource.ApiClient;
import com.example.foodplanner.search.searchPage.view.IngredientAdapter;
import com.example.foodplanner.utility.Helper.Helper;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MealPreparation extends AppCompatActivity implements MealPreparationInterface , MealPrepClickListner{

    TextView mealName,mealCountry,mealSteps;
    RecyclerView ingrRecyclerView;
    IngredientAdapter ingredientAdapter;
    GridLayoutManager layoutManager;
    List<IngeriedientPojo > ingredientList= new ArrayList<>();
    ImageButton favButton ,planButton ;
    ImageView imageMeal;
    PreparationPresenterInterface preparationPresenterInterface;
    ProductsPOJO productsPOJO;
    YouTubePlayerView youTubePlayerView ;
    String[] videoSplit;
    String videoId;
    String []days;
    boolean[] checkedDays;
    List<String> selectedDays ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_preparation);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        Intent intent = getIntent();
        String mealNameItem = intent.getStringExtra("mealName");

        preparationPresenterInterface =new MealPreparationPresenter(this, Repository.getInstance(ApiClient.getInstance(this), ConcreteLocalSource.getInstance(MealPreparation.this),MealPreparation.this));
        preparationPresenterInterface.getMealItem(mealNameItem);
        days  =getResources().getStringArray(R.array.weekdays);
        checkedDays= new boolean[days.length];
        selectedDays = Arrays.asList(days);
        init();

        favButton.setOnClickListener(event -> {
            if(Helper.SKIP == "skip"){
                Toast.makeText(this, "Please, Signup", Toast.LENGTH_SHORT).show();

            }else {
                productsPOJO.setFavorite(true);
                addToFavoriteOnClick(productsPOJO);
            }
        });



        planButton.setOnClickListener(v -> {
            if ("skip".equals(Helper.SKIP)) {
                Toast.makeText(this, "Please, Signup", Toast.LENGTH_SHORT).show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(MealPreparation.this);
                builder.setSingleChoiceItems(days, -1, (dialog, which) -> checkedDays[which] = true);

                builder.setCancelable(false);
                builder.setPositiveButton("add", (dialog, which) -> {
                    // Dynamically handle selected days
                    for (int i = 0; i < days.length; i++) {
                        if (checkedDays[i]) {
                            addToPlanOnclick(i);
                        }
                    }
                });

                builder.setNegativeButton("CANCEL", (dialog, which) -> {
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });



    }


    @Override
    public void addToFavoriteOnClick(ProductsPOJO productsPOJO) {
        insertFavInRoom( productsPOJO);
        Log.e("MealPreparationView", "insertPlanInRoom for day  " + productsPOJO.getStrMeal());
    }



    public void insertFavInRoom(ProductsPOJO mealModel) {
        preparationPresenterInterface.insertFavInRoom(mealModel);
        Log.d("MealPreparationView", "insertFavInRoom " + productsPOJO.getStrMeal());
        Toast.makeText(getApplicationContext(), "Meal is added to favorite", Toast.LENGTH_SHORT).show();

    }



    @Override
    public void addToPlanOnclick(int i ) {
        if (checkedDays[i]) {
            productsPOJO.setNameDay(selectedDays.get(i));
            insertPlanInRoom(productsPOJO);
            Log.e("MealPreparationView", "insertPlanInRoom for day   " + selectedDays.get(i));
            Log.e("MealPreparationView", "how many  days checked ? " + i);


        }


    }
    public void insertPlanInRoom(ProductsPOJO productsPOJO) {
        preparationPresenterInterface.insertPlanInRoom(productsPOJO);
        Log.e("MealPreparationView", "insertPlanInRoom  " + productsPOJO.getStrMeal());
        Toast.makeText(getApplicationContext(), "Meal is added to Weekly Plan", Toast.LENGTH_SHORT).show();

    }


  public void   init(){
      youTubePlayerView =    findViewById(R.id.videoView);
      mealName=              findViewById(R.id.itemPageMealName);
      ingrRecyclerView =     findViewById(R.id.ingredientRecyclerView);
      mealCountry=           findViewById(R.id.MealCountry);
      mealSteps=             findViewById(R.id.itemPageMealSteps);
      favButton =            findViewById(R.id.add_to_favorite);
      planButton=            findViewById(R.id.addtocalender);
      imageMeal=             findViewById(R.id.mealPhoto);
  }


    @Override
    public void viewMealPreparation(List<ProductsPOJO> productsPOJOList){

        productsPOJO = productsPOJOList.get(0);
        if(!productsPOJO.getStrYoutube().equals(""))
        {
            videoSplit =productsPOJO.getStrYoutube().split("=");
            videoId =videoSplit[1];
        }else{
            videoId =" ";
        }

        mealName.setText(productsPOJO.getStrMeal());
        mealCountry.setText(productsPOJO.getStrArea());
        mealSteps.setText(productsPOJO.getStrInstructions());
        Glide.with(this).load(productsPOJO.getStrMealThumb())
                .apply(new RequestOptions().override(imageMeal.getWidth(),imageMeal.getHeight()))
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(imageMeal);

        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                youTubePlayer.loadVideo(videoId, 0);
            }
        });
        // Iterate through the ingredients
        for (int i = 1; i <= 20; i++) {
            String ingredientName = MealUtility.getStrIngredient(productsPOJO,i);

            // Check if the ingredient is not empty
            if (ingredientName != null && !ingredientName.isEmpty() ) {
                String imageUrl = "https://www.themealdb.com/images/ingredients/" + ingredientName + ".png";
                ingredientList.add(new IngeriedientPojo (ingredientName, imageUrl));
            }
        }

        // Setting up RecyclerView for ingredients
        layoutManager = new GridLayoutManager(this, 3);
        ingredientAdapter = new IngredientAdapter(this, ingredientList);
        ingredientAdapter.notifyDataSetChanged();
        ingrRecyclerView.setAdapter(ingredientAdapter);
        ingrRecyclerView.setLayoutManager(layoutManager);



    }

}
