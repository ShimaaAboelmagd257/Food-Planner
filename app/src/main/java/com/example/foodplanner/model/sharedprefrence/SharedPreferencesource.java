package com.example.foodplanner.model.sharedprefrence;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.foodplanner.dataBaseHandling.Model.firebase.UserPojo;
import com.example.foodplanner.utility.Helper.Helper;

public class SharedPreferencesource implements  SharedPrefrenceInterface{



    private static SharedPreferencesource sharedPreferencesourceInstance = null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private UserPojo userPojo ;



    public SharedPreferencesource(Context context) {

        sharedPreferences = context.getSharedPreferences(Helper.SHARDPREFERENCE , context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
         userPojo = new UserPojo();


    }

    public static SharedPreferencesource getInstance(Context context){

    if(sharedPreferencesourceInstance == null){
        sharedPreferencesourceInstance =  new SharedPreferencesource(context);

    }
    return sharedPreferencesourceInstance;

}



    @Override
    public void saveUserData(UserPojo userPojo) {
        editor.putString(Helper.USERNAME,userPojo.getUserName());
        editor.putString(Helper.PASSWORD,userPojo.getPassWord());
        editor.putString(Helper.EMAIL,userPojo.getEmail());
        editor.commit();
        Log.d("SHAREDPREFRENCE", "saveUserData succeeded" + userPojo.getEmail());


    }


    @Override
    public UserPojo getSavedUserData() {
        userPojo.setUserName(sharedPreferences.getString(Helper.USERNAME,"Null"));
        userPojo.setEmail(sharedPreferences.getString(Helper.EMAIL,"Null"));
        userPojo.setPassWord(sharedPreferences.getString(Helper.PASSWORD,"Null"));
        userPojo.setImage(sharedPreferences.getString(Helper.IMAGE,"null"));
        Log.d("SHAREDPREFRENCE", "getSavedUserData succeeded" + userPojo.getEmail());

        return userPojo;


    }

    @Override
    public void updateUserData( UserPojo userPojo) {
        editor.putString(Helper.USERNAME,userPojo.getUserName());
        editor.putString(Helper.PASSWORD,userPojo.getPassWord());
        editor.putString(Helper.EMAIL,userPojo.getEmail());
        editor.putString(Helper.IMAGE,userPojo.getImage());
        editor.commit();
        Log.d("SHAREDPREFRENCE", "updateUserData succeeded" + userPojo.getEmail());

    }

}
