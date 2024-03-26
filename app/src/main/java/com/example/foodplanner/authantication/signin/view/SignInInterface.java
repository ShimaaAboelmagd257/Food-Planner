package com.example.foodplanner.authantication.signin.view;

import android.content.Context;

import com.example.foodplanner.dataBaseHandling.Model.Reposatory.AuthCallback;
import com.example.foodplanner.dataBaseHandling.Model.firebase.LoginCallBack;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserPojo;

public interface SignInInterface {
      void signInWithEmailAndPassword(String email, String password , AuthCallback callback);


    void addUserData(UserPojo userModel);

    boolean isLoginSuccessed(Context context, String email, String pass);

}
