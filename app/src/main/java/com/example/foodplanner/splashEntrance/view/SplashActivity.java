package com.example.foodplanner.splashEntrance.view;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.R;
import com.example.foodplanner.homeforyou.view.MainActivity;
import com.example.foodplanner.utility.Helper.Helper;
import com.google.firebase.FirebaseApp;

public class SplashActivity extends AppCompatActivity {
    private LottieAnimationView lottieAnimationView;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FirebaseApp.initializeApp(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        Toast.makeText(SplashActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
        lottieAnimationView = findViewById(R.id.animationViewhome);
        new Handler().postDelayed(() -> {
            lottieAnimationView.setAnimation("food.json"); // Set the JSON file
            lottieAnimationView.playAnimation(); // Start the animation

            SharedPreferences sharedPreferences = getSharedPreferences(Helper.SHARDPREFERENCE,getApplicationContext().MODE_PRIVATE);
            if(sharedPreferences.getString(Helper.EMAIL,null) == null) {
                Intent intent = new Intent(SplashActivity.this, EntrancePage.class);
                startActivity(intent);
            }else{
                System.out.println(sharedPreferences.getString(Helper.EMAIL,null));
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                Log.d("SplashScreen", "loging :  Sending  data succesfully  " + sharedPreferences.getString(Helper.EMAIL,null));

            }
            finish();
        }, 6000);


       /* try {
            // Attempt to load the Lottie animation
            LottieComposition.Factory.fromAssetFileName(this, "food.json", new OnCompositionLoadedListener() {
                @Override
                public void onCompositionLoaded(@Nullable LottieComposition composition) {
                    if (composition != null) {
                        animationView.setComposition(composition);
                        animationView.playAnimation();

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(SplashActivity.this, "error", Toast.LENGTH_SHORT).show();        }*/

    }

}