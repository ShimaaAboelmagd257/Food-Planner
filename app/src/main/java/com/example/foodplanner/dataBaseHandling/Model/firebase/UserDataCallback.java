package com.example.foodplanner.dataBaseHandling.Model.firebase;

public interface UserDataCallback {
    void onUserDataReceived(UserPojo userModel);

    void onUserDataNotFound();

    void onDatabaseError(String message);
}
