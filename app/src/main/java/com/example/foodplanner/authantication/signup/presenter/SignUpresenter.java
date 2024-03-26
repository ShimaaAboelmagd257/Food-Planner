package com.example.foodplanner.authantication.signup.presenter;

import android.util.Log;

import com.example.foodplanner.authantication.signup.view.SignUpInterface;
import com.example.foodplanner.dataBaseHandling.Model.Reposatory.FirebaseRepo;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserExistCallback;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserPojo;

public class SignUpresenter implements SignUpresenrterInterface {

    private FirebaseRepo repo;

    private SignUpInterface view;

    public SignUpresenter(SignUpInterface view,FirebaseRepo repo){
        this.view = view;
        this.repo = repo;

    }

    @Override
    public void onSuccessSignUpWithGoogle() {

    }

    @Override
    public void onFailureSignUpWithGoogle(String error) {

    }

    @Override
    public void addUserData(UserPojo userPojo) {
        repo.insertUserCreateEmail(userPojo);
        Log.d("SigUpPresenter", "addUserData:  " + userPojo.getEmail() );

    }


    @Override
    public void saveUserData(UserPojo userPojo) {
        repo.saveUserData(userPojo);
        Log.d("SigUpPresenter", "saveUserData:  " + userPojo.getEmail() );
    }


    @Override
    public boolean checkUserExists(UserPojo userPojo , UserExistCallback callback) {
        Log.d("SigUpPresenter", "checkUserExists:  " + repo.checkUserExists(userPojo,callback ));

        return  repo.checkUserExists(userPojo,callback);
    }
}


