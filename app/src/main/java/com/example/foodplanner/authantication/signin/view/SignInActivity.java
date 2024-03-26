package com.example.foodplanner.authantication.signin.view;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.foodplanner.R;
import com.example.foodplanner.authantication.signin.presenter.SignInPresenter;
import com.example.foodplanner.authantication.signin.presenter.SignInPresenterInterface;
import com.example.foodplanner.authantication.signup.view.SignUpActivity;
import com.example.foodplanner.dataBaseHandling.Model.Reposatory.AuthCallback;
import com.example.foodplanner.dataBaseHandling.Model.Reposatory.FirebaseRepository;
import com.example.foodplanner.dataBaseHandling.Model.firebase.FireBaseDataHandle;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserPojo;
import com.example.foodplanner.homeforyou.view.MainActivity;
import com.example.foodplanner.model.Pojos.ProductsPOJO;
import com.example.foodplanner.model.sharedprefrence.SharedPreferencesource;
import com.example.foodplanner.splashEntrance.view.EntrancePage;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;


public class SignInActivity extends AppCompatActivity  implements SignInInterface , SignInClickListner {

    EditText email,password;
    Button loginBtn , backBtn;
    String userEmail , userPassword;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    SignInPresenterInterface signInPresenterInterface;
   List<ProductsPOJO> favorites;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        email = findViewById(R.id.EmaiID);
        password = findViewById(R.id.paswordID);
        loginBtn = findViewById(R.id.loginbtnID);
        // backBtn  = findViewById(R.id.backBtnID);
        progressBar = findViewById(R.id.progressBar);

        signInPresenterInterface = new SignInPresenter(this, FirebaseRepository.getInstance(FireBaseDataHandle.getInstance(this), SharedPreferencesource.getInstance(this), this));
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userEmail = email.getText().toString();
                userPassword = password.getText().toString();
                if (TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword)) {
                    Toast.makeText(SignInActivity.this, "Please fill all data", Toast.LENGTH_SHORT).show();
                } else {
                    showProgress();
                    signInWithEmailAndPassword(userEmail, userPassword, new AuthCallback() {
                        @Override
                        public void onAuthSuccess() {
                            hideProgress();
                            Toast.makeText(SignInActivity.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onAuthFailure(String errorMessage) {
                            hideProgress();
                            Toast.makeText(SignInActivity.this, "Sorry, sign in failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    @Override
    public void signInWithEmailAndPassword(String email, String password, AuthCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onAuthSuccess();
                    } else {
                        Exception exception = task.getException();
                        if (exception != null) {
                            callback.onAuthFailure(exception.getMessage());
                        }
                    }
                });
    }



    @Override
    public void addUserData(UserPojo userModel) {
        signInPresenterInterface.addUserDataToShered(userModel);
        Log.d("SignInView", "addUserDataToShered:  " +userModel.getEmail() );

    }

    @Override
    public boolean isLoginSuccessed(Context context, String email, String pass){
        Log.d("SignInView", "isLoginSuccessed:  " + signInPresenterInterface.isLoginSuccessed(context,email,pass) );

        return signInPresenterInterface.isLoginSuccessed(context,email,pass);
    }


   /* @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
           Intent intent = new Intent(this, MainActivity.class);
           startActivity(intent);
           finish();
       }


    }*/
   /* private UserPojo createUserPojoFromFirebaseUser(FirebaseUser user) {
        UserPojo userPojo = new UserPojo();
        userPojo.setUserName(user.getDisplayName());
        userPojo.setEmail(user.getEmail());
        if (user.getPhotoUrl() != null) {
            userPojo.setImage(user.getPhotoUrl().toString());
        } else {
            userPojo.setImage("default_image_url");
        }
        if(favorites != null){
        userPojo.setFavorites(favorites);
        }
        Log.d("SignIn", "createUserPojoFromFirebaseUser: " + userPojo.getEmail()  );

        return userPojo;
    }*/


    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }


}