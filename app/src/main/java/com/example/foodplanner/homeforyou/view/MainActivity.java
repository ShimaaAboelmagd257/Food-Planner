package com.example.foodplanner.homeforyou.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.utility.Helper.Helper;
import com.example.foodplanner.utility.NetworkConnectionWIFI.NetworkStateListner;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements NetworkStateListner {
    BottomNavigationView bottomNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_for_you);
        getSupportActionBar().hide();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        bottomNavView = findViewById(R.id.bottomNavView);
        bottomNavView.setBackground(null);

        if ("skip".equals(Helper.SKIP)) {
            bottomNavView.getMenu().getItem(2).setEnabled(false);
            bottomNavView.getMenu().getItem(3).setEnabled(false);
        } else {
            // If SKIP is not "skip," use NetworkCallback

        }

        // Register the NetworkCallback






        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavView, navController);

     /*  bottomNavView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_host_fragment);
            }
        });*/



    }


    @Override
    public void onNetworkAvailable() {
        bottomNavView.getMenu().getItem(4).setEnabled(true);
        Toast.makeText(this, "Network is available", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkUnavailable() {
        bottomNavView.getMenu().getItem(4).setEnabled(false);
        Toast.makeText(this, "Network is unavailable, please  check you Connection ", Toast.LENGTH_SHORT).show();
    }
}