package com.example.foodplanner.authantication.signin.presenter;

import android.content.Context;
import android.util.Log;

import com.example.foodplanner.authantication.signin.view.SignInInterface;
import com.example.foodplanner.dataBaseHandling.Model.Reposatory.AuthCallback;
import com.example.foodplanner.dataBaseHandling.Model.Reposatory.FirebaseRepo;
import com.example.foodplanner.dataBaseHandling.Model.firebase.LoginCallBack;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserDataCallback;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserPojo;

public class SignInPresenter implements SignInPresenterInterface {

    private FirebaseRepo repo;
    private SignInInterface view;

    public SignInPresenter(SignInInterface view, FirebaseRepo repo){
        this.repo=repo;
       this.view=view;
    }

    @Override
    public void addUserDataToShered(UserPojo userPojo) {
        repo.saveUserData(userPojo);
        Log.d("SignInPresenter", "addUserDataToShered:  " +userPojo.getEmail() );

    }

    @Override
    public boolean isLoginSuccessed(Context context, String email, String pass ) {
        return repo.isLoginSuccessed(context,email,pass );
    }





}
