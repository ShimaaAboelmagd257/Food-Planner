package com.example.foodplanner.dataBaseHandling.Model.Reposatory;

public interface AuthCallback {
    void onAuthSuccess();

    void onAuthFailure(String error);
}