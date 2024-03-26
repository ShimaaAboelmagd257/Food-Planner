package com.example.foodplanner.dataBaseHandling.Model.Reposatory;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.foodplanner.dataBaseHandling.Model.firebase.FirbaseSourceInterface;
import com.example.foodplanner.dataBaseHandling.Model.firebase.FireBaseDataHandle;
import com.example.foodplanner.dataBaseHandling.Model.firebase.LoginCallBack;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserDataCallback;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserExistCallback;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserPojo;
import com.example.foodplanner.model.sharedprefrence.SharedPreferencesource;
import com.example.foodplanner.utility.Helper.Helper;

public class FirebaseRepository implements FirebaseRepo {


    private SharedPreferencesource sharedPreferencesource;
    private Context context;
    private FirbaseSourceInterface firebaseSource;
    LoginCallBack loginCallBack;
    UserExistCallback userExistCallback;
    SharedPreferences sharedPreferences;
   private static FirebaseRepository firebaseRepository = null;

    public FirebaseRepository(FireBaseDataHandle firebaseSource, SharedPreferencesource sharedPreferencesource, Context context) {
        this.context = context;
        this.sharedPreferencesource = sharedPreferencesource;
        this.firebaseSource = firebaseSource;
        sharedPreferences   = context.getSharedPreferences(Helper.SHARDPREFERENCE,context.MODE_PRIVATE);

    }



    public static FirebaseRepository getInstance(FireBaseDataHandle firebaseSource, SharedPreferencesource sharedPreferencesource, Context context){
        if (firebaseRepository == null){
            firebaseRepository = new FirebaseRepository(firebaseSource , sharedPreferencesource, context);
        }
        Log.d("FirebaseRepository", "Singleton is made "  );

        return  firebaseRepository;
    }
  //  SharedPreferences sharedPreferences = context.getSharedPreferences(Helper.SHARDPREFERENCE,context.MODE_PRIVATE);

    @Override
    public void insertUserGoogle(UserPojo userPojo) {
          firebaseSource.insertUser(userPojo);
        Log.d("FirebaseRepository", "insertUserGoogle successed "  );

    }

    @Override
    public void insertUserCreateEmail(UserPojo userPojo) {
        firebaseSource.insertUser(userPojo);
        Log.d("FirebaseRepository", "insertUserCreateEmail successed "  );

    }



    @Override
    public void saveUserData(UserPojo userPojo) {
        sharedPreferencesource.saveUserData(userPojo);
        Log.d("FirebaseRepository", "saveUserData successed "  );

    }

    @Override
    public boolean checkUserExists(UserPojo userPojo, UserExistCallback callback) {

        return firebaseSource.checkUserExists(userPojo,callback );
    }



    /*@Override
    public boolean isUserExists(UserPojo userPojo) {
        return false;
    }

    @Override
    public boolean isUserExists(boolean exists) {

        return false;
    }*/

    @Override
    public boolean isLoginSuccessed(Context context, String email, String pass ) {

        return firebaseSource.login(context,email,pass);
    }

    @Override
    public UserPojo  getSavedUserData() {
        Log.d("FirebaseRepository", "insertUser is made " + sharedPreferencesource.getSavedUserData().getEmail() );
        Log.d("SplashScreen", "loging :  Sending  data succesfully  " + sharedPreferences.getString(Helper.EMAIL,null));

        return sharedPreferencesource.getSavedUserData();
    }

    @Override
    public void updateUserData(UserPojo userPojo) {
        sharedPreferencesource.updateUserData(userPojo);
        Log.d("FirebaseRepository", "insertUser is made " +userPojo.getEmail() );

    }

    @Override
    public void updateUserFirebaseData(UserPojo userPojo) {
        firebaseSource.insertUser(userPojo);
        Log.d("FirebaseRepository", "insertUser is made " + userPojo.getEmail() );

    }

    @Override
    public void updateFavoriteInFirebase(UserPojo userPojo) {
        firebaseSource.insertUser(userPojo);
        Log.d("FirebaseRepository", "insertUser is made " + userPojo.toString() );

    }

    @Override
    public void uploadPlanInFirebase(UserPojo userPojo) {
        firebaseSource.insertUser(userPojo);
        Log.d("FirebaseRepository", "insertUser is made " + userPojo.toString() );

    }

    @Override
    public void signInWithEmailAndPassword(String email, String password, AuthCallback callback){
      //  firebaseSource.signInWithEmailAndPassword(email,password,callback);
    }

    @Override
    public void fetchUserData(String email, UserDataCallback callback){
      //  firebaseSource.fetchUserData(email,callback);
    }

}
