package com.example.foodplanner.splashEntrance.presenter;

import android.content.Context;
import android.util.Log;

import com.example.foodplanner.authantication.signup.view.SignUpInterface;
import com.example.foodplanner.dataBaseHandling.Model.Reposatory.FirebaseRepo;
import com.example.foodplanner.dataBaseHandling.Model.firebase.LoginCallBack;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserExistCallback;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserPojo;

public class EntrancePresenter implements EntrancePresentInterface {
    private FirebaseRepo repo;

    private SignUpInterface view;

    public EntrancePresenter(FirebaseRepo repo){
        this.repo = repo;

    }

    @Override
    public void onSuccessSignUpWithGoogle() {


    }

    @Override
    public void onFailureSignUpWithGoogle(String error) {

    }



    @Override
    public boolean isLoginSuccessed(Context context, String email, String pass ) {
        return repo.isLoginSuccessed(context,email,pass );
    }

    @Override
    public void addUserData(UserPojo userPojo) {
        repo.insertUserGoogle(userPojo);
        Log.d("EntrancePagePresenter", "addUserData: " + userPojo.getEmail()  );

    }

    @Override
    public void saveUserData(UserPojo userPojo) {
        repo.saveUserData(userPojo);
        Log.d("EntrancePagePresenter", "saveUserData: " + userPojo.getEmail()  );


    }

    @Override
    public boolean checkUserExists(UserPojo userPojo, UserExistCallback callback) {
        Log.d("EntrancePagePresenter", "checkUserExists: " + userPojo.getEmail()  );

        return  repo.checkUserExists(userPojo ,callback);
    }
}
