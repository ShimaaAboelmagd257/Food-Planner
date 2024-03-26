package com.example.foodplanner.splashEntrance.presenter;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodplanner.model.LocalDataSource.ProductDataBase;
import com.example.foodplanner.utility.NetworkConnectionWIFI.NetworkStateListner;
import com.example.foodplanner.utility.NetworkConnectionWIFI.NetworkStateManager;
import com.google.firebase.FirebaseApp;

public class MyApplication extends Application  {
    NetworkStateManager networkStateManager;
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        networkStateManager = new NetworkStateManager(this);
        networkStateManager.startMonitoring();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        networkStateManager.stopMonitoring();
    }

}
