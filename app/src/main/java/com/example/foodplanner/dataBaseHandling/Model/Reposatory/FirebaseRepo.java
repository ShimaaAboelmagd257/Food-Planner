package com.example.foodplanner.dataBaseHandling.Model.Reposatory;

import android.content.Context;

import com.example.foodplanner.dataBaseHandling.Model.firebase.LoginCallBack;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserDataCallback;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserExistCallback;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserPojo;

public interface FirebaseRepo {
     void insertUserCreateEmail(UserPojo userPojo);

    void insertUserGoogle(UserPojo userPojo);

    void saveUserData(UserPojo userPojo);

    boolean checkUserExists(UserPojo userPojo, UserExistCallback callback);

    boolean isLoginSuccessed(Context context, String email, String pass );

    UserPojo  getSavedUserData();

    void updateUserData(UserPojo userPojo);

    void updateUserFirebaseData(UserPojo userPojo);

    void updateFavoriteInFirebase(UserPojo userPojo);

    void uploadPlanInFirebase(UserPojo userPojo);

    void signInWithEmailAndPassword(String email, String password, AuthCallback callback);

    void fetchUserData(String email, UserDataCallback callback);
}
