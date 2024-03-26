package com.example.foodplanner.utility.NetworkConnectionWIFI;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.example.foodplanner.splashEntrance.view.EntrancePage;
import com.example.foodplanner.splashEntrance.view.SplashActivity;

import java.util.ArrayList;
import java.util.List;

public class NetworkStateManager extends ConnectivityManager.NetworkCallback {
    private Handler handler;
    private ConnectivityManager connectivityManager;
    private List<NetworkStateListner> networkStateListeners;
    Context context;
    public NetworkStateManager(Context context) {
        this.context = context;
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkStateListeners = new ArrayList<>();
         handler = new Handler(Looper.getMainLooper()); // Initialize a handler with the main UI thread's Looper
    }
    public void addListener(NetworkStateListner listener) {
        networkStateListeners.add(listener);
    }

    public void removeListener(NetworkStateListner listener) {
        networkStateListeners.remove(listener);
    }

    public void startMonitoring() {
        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build();
        connectivityManager.registerNetworkCallback(networkRequest, this);
    }

    public void stopMonitoring() {
        connectivityManager.unregisterNetworkCallback(this);
    }

    @Override
    public void onAvailable(Network network) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                // This code runs on the main UI thread

                // Perform network-related operations, if needed
                notifyNetworkAvailable();

              /*  // Start a new activity
                Intent intent = new Intent(context, SplashActivity.class);
                context.startActivity(intent);*/
            }
        });

    }

    @Override
    public void onLost(Network network) {
        // Start an AsyncTask to handle network unavailability
        new NetworkAvailabilityTask(false).execute();
    }

    private class NetworkAvailabilityTask extends AsyncTask<Void, Void, Boolean> {
        private boolean isAvailable;

        NetworkAvailabilityTask(boolean available) {
            isAvailable = available;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            // Perform network-related operations in the background
            // Return true if successful, false if there's an error
            return performNetworkOperations();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                // Handle network operation success (e.g., update UI, show data)
                if (isAvailable) {
                    notifyNetworkAvailable();
                }
            } else {
                // Handle network operation failure (e.g., show an error message)
                if (!isAvailable) {
                    notifyNetworkUnavailable();
                }
            }
        }

        private Boolean performNetworkOperations() {
            try {
                // Perform your network operations here
                // Return true if successful, false if there's an error
                return true;
            } catch (Exception e) {
                // Handle any exceptions or errors
                e.printStackTrace();
                return false;
            }
        }
    }

    private void notifyNetworkAvailable() {
        for (NetworkStateListner listener : networkStateListeners) {
            listener.onNetworkAvailable();
        }
    }

    private void notifyNetworkUnavailable() {
        for (NetworkStateListner listener : networkStateListeners) {
            listener.onNetworkUnavailable();

        }
    }
}

