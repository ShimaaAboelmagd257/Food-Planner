package com.example.foodplanner.profile.presenter;

import com.example.foodplanner.dataBaseHandling.Model.firebase.UserPojo;

public interface UserPresenterInterface  {


    UserPojo getSavedUserData();
    void updateUserData(UserPojo userModel);

    void updateUserFirebaseData(UserPojo userModel);

     void deleteAllMeals() ;

}
