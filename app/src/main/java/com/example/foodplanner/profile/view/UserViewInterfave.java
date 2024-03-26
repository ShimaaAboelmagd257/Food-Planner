package com.example.foodplanner.profile.view;

import com.example.foodplanner.dataBaseHandling.Model.firebase.UserPojo;

public interface UserViewInterfave {

    UserPojo getSavedUserData();

    void updateUserData(UserPojo userModel);

    void updateUserFirebaseData(UserPojo userModel);

    void showData(UserPojo userModel);

    void logoutOnClick();

    void  saveNewNameOnClick();

    void saveNewPasswordOnClick();

    void editImageOnClick();

    void editProfile();
}
