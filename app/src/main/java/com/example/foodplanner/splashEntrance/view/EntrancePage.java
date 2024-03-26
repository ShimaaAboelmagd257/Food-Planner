package com.example.foodplanner.splashEntrance.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodplanner.R;
import com.example.foodplanner.authantication.signin.view.SignInActivity;
import com.example.foodplanner.authantication.signup.view.SignUpActivity;
import com.example.foodplanner.dataBaseHandling.Model.Reposatory.FirebaseRepository;
import com.example.foodplanner.dataBaseHandling.Model.firebase.FireBaseDataHandle;
import com.example.foodplanner.dataBaseHandling.Model.firebase.LoginCallBack;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserExistCallback;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserPojo;
import com.example.foodplanner.homeforyou.view.MainActivity;
import com.example.foodplanner.model.sharedprefrence.SharedPreferencesource;
import com.example.foodplanner.splashEntrance.presenter.EntrancePresentInterface;
import com.example.foodplanner.splashEntrance.presenter.EntrancePresenter;
import com.example.foodplanner.utility.Helper.Helper;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.annotations.Nullable;

public class EntrancePage extends AppCompatActivity {

    Button skipBtn  , signupBtn , signinBtn ;
    ImageButton facebookBtn , googleBtn ;
    FirebaseAuth mAuth;
    GoogleSignInClient googleSignInClient;
    GoogleSignInResult signInRequest;
    private static final int RC_SIGN_IN=1;
    EntrancePresentInterface entrancePresentInterface;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance_page);

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        context = this;
        skipBtn = findViewById(R.id.skipBtnID);
//        facebookBtn = findViewById(R.id.facbookID);
        googleBtn = findViewById(R.id.googleID);
        signupBtn = findViewById(R.id.signUpBtnID);
        signinBtn = findViewById(R.id.logInID);

        entrancePresentInterface =  new EntrancePresenter(FirebaseRepository.getInstance(FireBaseDataHandle.getInstance(this)
                , SharedPreferencesource.getInstance(this),this));
        intiGoogle ();
        googleBtn.setOnClickListener(v -> {
                Intent intent = googleSignInClient.getSignInIntent();
            startActivityForResult(intent,RC_SIGN_IN);
        });



        signupBtn.setOnClickListener( v -> {
            Intent intent = new Intent(EntrancePage.this, SignUpActivity.class);
            startActivity(intent);
        });

        signinBtn.setOnClickListener(v ->{
            Intent intent = new Intent(EntrancePage.this, SignInActivity.class);
            startActivity(intent);
        });

        skipBtn.setOnClickListener( v -> {
            Helper.SKIP = "skip";
            showSignUpAlertDialog();
           // navigateToHomeActivity();
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    handleSignInResult(account);
                }
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }

    }


    public void handleSignInResult(@NonNull GoogleSignInAccount account) throws ApiException {
        System.out.println("Hello from handleSignInResult Auth........");
       // GoogleSignInAccount acc=completedTask.getResult(ApiException.class);
        // System.out.println("SignIn Successfully ");
        Toast.makeText(this, "SignIn Successfully", Toast.LENGTH_SHORT).show();
        SignUpWithGoogle(account);

    }



    public  void SignUpWithGoogle(@androidx.annotation.NonNull GoogleSignInAccount account){
        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(firebaseCredential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(EntrancePage.this, " Signin with Google succeded", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        if(user!=null){
                            UserPojo userPojo = createUserPojoFromFirebaseUser(user);
                            checkUserExists(userPojo, new UserExistCallback() {
                                        @Override
                                        public void onUserExists(boolean exists) {
                                            if (exists) {
                                                Log.d("EntrancePage", "User already exists");
                                            } else {
                                                insertUserData(userPojo);
                                                Log.d("EntrancePage", "insertUserData: " + userPojo.getEmail());
                                                saveUserData(userPojo);
                                                Log.d("EntrancePage", "saveUserData: " + userPojo.getEmail());
                                                navigateToHomeActivity();
                                            }
////////////////////////////////////////////////////////////////
                                              /*  String userEmail ;
                                            isLoginSuccessed(EntrancePage.this, userEmail, userPassword, new LoginCallBack() {

                                                @Override
                                                public void onLoginSuccess() {
                                                    FirebaseUser user = mAuth.getCurrentUser();
                                                    insertUserData(createUserPojoFromFirebaseUser(user));
                                                    Intent intent = new Intent(EntrancePage.this, MainActivity.class);
                                                    startActivity(intent);
                                                    finish();

                                                }

                                                @Override
                                                public void onLoginFailure(String error) {

                                                    Toast.makeText(EntrancePage.this, "login failed.",
                                                            Toast.LENGTH_SHORT).show();
                                                    Log.d("EntrancePage", "addUserDataToShered:  " + error );

                                                }
                                            });*/

                                           //////////////////////////////////////////////////////////////////////////////

                                        }
                                        @Override
                                        public void onUserExistsCheckFailure(String errorMessage) {

                                        }
                                    });
                        }
                    }
                    else {
                        Toast.makeText(EntrancePage.this, "Sign-in with Google failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }



    public void insertUserData(UserPojo userPojo) {
        entrancePresentInterface.addUserData(userPojo);
    }

    public boolean checkUserExists(UserPojo userPojo , UserExistCallback callback) {
        return entrancePresentInterface.checkUserExists(userPojo , callback);
    }
    public void saveUserData(UserPojo userPojo) {
        entrancePresentInterface.saveUserData(userPojo);
        Log.d("EntrancePage", "saveUserData: " + userPojo.getEmail()  );
    }

    public boolean isLoginSuccessed(Context context, String email, String pass) {
        return entrancePresentInterface.isLoginSuccessed(context,email,pass);
    }
    private UserPojo createUserPojoFromFirebaseUser(FirebaseUser user) {
        UserPojo userPojo = new UserPojo();
        userPojo.setUserName(user.getDisplayName());
        userPojo.setEmail(user.getEmail());
        if (user.getPhotoUrl() != null) {
            userPojo.setImage(user.getPhotoUrl().toString());
        }else {
            // Log a message or provide a default image URL
            userPojo.setImage("default_image_url");
        }
        userPojo.setFavorites(null);
        Log.d("EntrancePage", "createUserPojoFromFirebaseUser: " + userPojo.getEmail()  );

        return userPojo;
    }

    private void  navigateToHomeActivity() {
        Intent intent = new Intent(EntrancePage.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void intiGoogle (){
        try {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            googleSignInClient = GoogleSignIn.getClient(this, gso);
            Log.d("EntrancePage", "intiGoogle: " + getString(R.string.default_web_client_id) );

        } catch (Exception e) {
            Log.e("InitGoogle", "Error initializing Google Sign-In", e);
        }


    }

     private void showSignUpAlertDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Do you want to sign up in the application?");
            builder.setTitle("Alert!");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes, Signup", (dialog, which) -> {
                Intent intent = new Intent(context, SignUpActivity.class);
                context.startActivity(intent);
                ((Activity) context).finish();
            });

            builder.setNegativeButton("No, thanks", (dialog, which) -> {
                Intent intent = new Intent(EntrancePage.this, MainActivity.class);
                context.startActivity(intent);
                ((Activity) context).finish();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }


}