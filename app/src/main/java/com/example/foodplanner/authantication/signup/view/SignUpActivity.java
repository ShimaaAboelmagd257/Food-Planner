package com.example.foodplanner.authantication.signup.view;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.authantication.signin.view.SignInActivity;
import com.example.foodplanner.authantication.signup.presenter.SignUpresenrterInterface;
import com.example.foodplanner.authantication.signup.presenter.SignUpresenter;
import com.example.foodplanner.dataBaseHandling.Model.Reposatory.FirebaseRepository;
import com.example.foodplanner.dataBaseHandling.Model.firebase.FireBaseDataHandle;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserExistCallback;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserPojo;
import com.example.foodplanner.homeforyou.view.MainActivity;

import com.example.foodplanner.model.Pojos.ProductsPOJO;
import com.example.foodplanner.model.sharedprefrence.SharedPreferencesource;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;


public class SignUpActivity extends AppCompatActivity implements SignUpInterface{
  List<ProductsPOJO> productsPOJO;
    EditText name , email , password , confirmpass ;
    Button next , skip ;
    private boolean showOneTapUI = true;
    public static String SKIP ;
    FirebaseAuth mAuth;
    SignUpresenrterInterface signUpresenrterInterface;
    private boolean signUpSuccessful= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.nameID);
        email = findViewById(R.id.EmaiID);
        password = findViewById(R.id.PasswordID);
        confirmpass = findViewById(R.id.confirmPasswordID);
        next = findViewById(R.id.nextBtnID);
        //skip = findViewById(R.id.backBtnID);

        signUpresenrterInterface = new SignUpresenter(this,FirebaseRepository.getInstance(FireBaseDataHandle.getInstance(this), SharedPreferencesource.getInstance(this),this));


        next.setOnClickListener( v ->{
            signupWithCreateEmailClick();
        });

       /* skip.setOnClickListener( v -> {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
        });*/
    }
    public void signupWithCreateEmailClick() {
        String nameStr = name.getText().toString();
        String emailStr = email.getText().toString();
        String passwordStr = password.getText().toString();
        String confirmPassStr = confirmpass.getText().toString();

        if (isEmpty(nameStr) || isEmpty(emailStr) || isEmpty(passwordStr) || isEmpty(confirmPassStr)) {
            Toast.makeText(this, "You should fill all data", Toast.LENGTH_SHORT).show();
        } else if (!passwordStr.equals(confirmPassStr)) {
            Toast.makeText(this, "Password and confirmPassword don’t match", Toast.LENGTH_SHORT).show();
        } else if (!isValidEmail(emailStr)) {
            Toast.makeText(this, "Email is invalid", Toast.LENGTH_SHORT).show();
        } else if (!isValidPassword(passwordStr)) {
            Toast.makeText(this, "Password is invalid", Toast.LENGTH_SHORT).show();
        } else {
           // FirebaseUser user = mAuth.getCurrentUser();
            createUserPojo(nameStr ,emailStr, passwordStr);
        }
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

    private  UserPojo createUserPojo(String  nameStr , String emailStr , String passwordStr ) {
        UserPojo userPojo = new UserPojo();
       // if (user != null && user.getDisplayName() != null && user.getEmail() != null && user.getPhotoUrl() != null) {        userPojo.setUserName(user.getDisplayName());
        userPojo.setEmail(emailStr);
        userPojo.setPassWord(passwordStr);
        userPojo.setImage("default_image_url");
        userPojo.setUserName(nameStr);
      /*  userPojo.setFavorites(new ArrayList<>());
        userPojo.setSaturday(new ArrayList<>());
        userPojo.setSunday(new ArrayList<>());

        userPojo.setMonday(new ArrayList<>());
        userPojo.setTuesday(new ArrayList<>());
        userPojo.setWednesday(new ArrayList<>());
        userPojo.setThursday(new ArrayList<>());
        userPojo.setFriday(new ArrayList<>());*/

        checkUserExists(userPojo , new UserExistCallback() {
            @Override
            public void onUserExists(boolean exists) {
                if (exists) {
                    Toast.makeText(SignUpActivity.this, "This email already exists", Toast.LENGTH_SHORT).show();
                    signUpSuccessful = false;
                } else {
                    insertUserData(userPojo);
                    saveUserData(userPojo);
                    Toast.makeText(SignUpActivity.this, "You registered successfully", Toast.LENGTH_SHORT).show();
                    signUpSuccessful = true;
                    Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                    startActivity(intent);
                    SKIP = null;
                    finish();
                }
            }
            @Override
            public void onUserExistsCheckFailure(String errorMessage) {
                if (!signUpSuccessful) {
                    Toast.makeText(SignUpActivity.this, "Couldn't register the email account", Toast.LENGTH_SHORT).show();
                }
                signUpSuccessful = false;
            }
        });

        return userPojo;
    }

    private boolean isEmpty(String text) {
        return text.trim().isEmpty();
    }
    @Override
    public boolean checkUserExists(UserPojo userPojo , UserExistCallback callback) {
         return  signUpresenrterInterface.checkUserExists(userPojo , callback);
    }


    @Override
    public void insertUserData(UserPojo userPojo) {
        signUpresenrterInterface.addUserData(userPojo);
    }


    @Override
    public void saveUserData(UserPojo userPojo) {
        signUpresenrterInterface.saveUserData(userPojo);
    }




    @Override
    public void addUserData(UserPojo userPojo) {
       // signUpresenrterInterface.addUserData(userPojo);
    }

  /*
   @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
       FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(this, HomeForYou.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(this, EntrancePage.class);
            startActivity(intent);
            finish();
        }
    }*/

}

