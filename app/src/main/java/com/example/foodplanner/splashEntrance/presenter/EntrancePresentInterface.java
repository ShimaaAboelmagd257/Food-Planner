package com.example.foodplanner.splashEntrance.presenter;

import android.content.Context;

import com.example.foodplanner.dataBaseHandling.Model.firebase.LoginCallBack;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserExistCallback;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserPojo;

public interface EntrancePresentInterface {
    void onSuccessSignUpWithGoogle();

    void onFailureSignUpWithGoogle(String error);

    boolean isLoginSuccessed(Context context, String email, String pass);

    void addUserData(UserPojo userPojo);

    void saveUserData(UserPojo userPojo);

    boolean checkUserExists (UserPojo userPojo, UserExistCallback callback);
}
