package com.example.foodplanner.authantication.signup.presenter;

import com.example.foodplanner.dataBaseHandling.Model.firebase.UserExistCallback;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserPojo;

public interface SignUpresenrterInterface {
    void onSuccessSignUpWithGoogle();

    void onFailureSignUpWithGoogle(String error);

    void addUserData(UserPojo userPojo);

    void saveUserData(UserPojo userPojo);

    boolean checkUserExists(UserPojo userPojo , UserExistCallback callback);
}
