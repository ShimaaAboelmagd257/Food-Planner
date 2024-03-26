package com.example.foodplanner.dataBaseHandling.Model.firebase;

public interface UserExistCallback {
    void onUserExists(boolean exists);
    void onUserExistsCheckFailure(String errorMessage);
}
