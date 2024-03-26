package com.example.foodplanner.profile.presenter;

import com.example.foodplanner.dataBaseHandling.Model.Reposatory.FirebaseRepo;
import com.example.foodplanner.dataBaseHandling.Model.Reposatory.RepoInterface;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserPojo;

public class UserProfilePresenter implements  UserPresenterInterface{

    FirebaseRepo firebaseRepo ;
    RepoInterface repoInterface;

    public UserProfilePresenter(FirebaseRepo firebaseRepo, RepoInterface repoInterface) {
        this.firebaseRepo = firebaseRepo;
        this.repoInterface = repoInterface;
    }


    @Override
    public UserPojo getSavedUserData() {
        return firebaseRepo.getSavedUserData();
    }

    @Override
    public void updateUserData(UserPojo userModel) {
     firebaseRepo.updateUserData(userModel);
    }

    @Override
    public void updateUserFirebaseData(UserPojo userModel) {
        firebaseRepo.updateUserFirebaseData(userModel);

    }

    @Override
    public void deleteAllMeals() {
        repoInterface.deleteAllMeals();

    }
}
