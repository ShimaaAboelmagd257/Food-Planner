package com.example.foodplanner.authantication.signin.presenter;

import android.content.Context;

import com.example.foodplanner.dataBaseHandling.Model.firebase.LoginCallBack;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserPojo;

public interface SignInPresenterInterface {
    void addUserDataToShered(UserPojo userPojo);
    boolean isLoginSuccessed(Context context, String email, String pass );


}
