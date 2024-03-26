package com.example.foodplanner.authantication.signup.view;

import androidx.annotation.NonNull;

import com.example.foodplanner.dataBaseHandling.Model.firebase.UserExistCallback;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserPojo;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public interface SignUpInterface {


    void insertUserData(UserPojo userPojo);

    boolean checkUserExists(UserPojo userPojo , UserExistCallback callback);

    void saveUserData(UserPojo userPojo);




    void addUserData(UserPojo userPojo);
}

