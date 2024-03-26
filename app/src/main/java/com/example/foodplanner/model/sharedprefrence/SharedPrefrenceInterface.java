package com.example.foodplanner.model.sharedprefrence;

import com.example.foodplanner.dataBaseHandling.Model.firebase.UserPojo;

public interface SharedPrefrenceInterface {


    void saveUserData(UserPojo userPojo);
    UserPojo getSavedUserData();
    void updateUserData(UserPojo userPojo);
}
