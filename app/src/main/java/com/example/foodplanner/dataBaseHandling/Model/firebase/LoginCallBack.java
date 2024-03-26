package com.example.foodplanner.dataBaseHandling.Model.firebase;

public interface LoginCallBack {
    void onLoginSuccess();
    void onLoginFailure(String errorMessage);
}