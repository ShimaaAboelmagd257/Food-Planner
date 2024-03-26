package com.example.foodplanner.dataBaseHandling.Model.firebase;

import android.content.Context;

import com.example.foodplanner.dataBaseHandling.Model.Reposatory.AuthCallback;

public interface FirbaseSourceInterface {

    boolean checkUserExists(UserPojo userModel, final UserExistCallback callback);

    void signInWithEmailAndPassword(String email, String password, AuthCallback callback);

    boolean login(Context context, String email, String password);
    void insertUser(UserPojo userPojo);

    void fetchUserData(String email, UserDataCallback callback);
}
