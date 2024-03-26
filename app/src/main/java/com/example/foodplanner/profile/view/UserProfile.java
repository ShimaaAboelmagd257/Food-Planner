package com.example.foodplanner.profile.view;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.dataBaseHandling.Model.Reposatory.FirebaseRepository;
import com.example.foodplanner.dataBaseHandling.Model.Reposatory.Repository;
import com.example.foodplanner.dataBaseHandling.Model.firebase.FireBaseDataHandle;
import com.example.foodplanner.dataBaseHandling.Model.firebase.UserPojo;
import com.example.foodplanner.model.LocalDataSource.ConcreteLocalSource;
import com.example.foodplanner.model.RemoteDataSource.ApiClient;
import com.example.foodplanner.model.sharedprefrence.SharedPreferencesource;
import com.example.foodplanner.profile.presenter.UserPresenterInterface;
import com.example.foodplanner.profile.presenter.UserProfilePresenter;
import com.example.foodplanner.splashEntrance.view.EntrancePage;
import com.example.foodplanner.utility.Helper.Helper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class UserProfile extends Fragment implements  UserViewInterfave{

    ImageView profileImage, editImage;
    TextView userName, email, editProfile , userDisplayName ;
    EditText currentPassword, newPassword, confirmPassword;
    ImageButton  saveNewName, saveNewPassword;
    Button signOutBtn;
    String image = null;
    Group group;
    UserPresenterInterface userPresenterInterface;
    boolean edit = false;
    UserPojo userModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

      //  updateUI(); // Call this method to update UI elements based on sign-in state
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        userPresenterInterface = new UserProfilePresenter(FirebaseRepository.getInstance(FireBaseDataHandle.getInstance(getContext())
                , SharedPreferencesource.getInstance(getContext()), getContext()), Repository.getInstance(ApiClient.getInstance(getContext()),
                ConcreteLocalSource.getInstance(getContext()),getContext()));
      /*  FirebaseAuth.getInstance().addAuthStateListener(auth -> {
            updateUI(auth.getCurrentUser());
        });*/
        editProfile.setOnClickListener(event -> editProfile());
        editImage.setOnClickListener(event -> editImageOnClick());
        signOutBtn.setOnClickListener(event -> logoutOnClick());
        saveNewName.setOnClickListener(event -> {saveNewNameOnClick();});
        saveNewPassword.setOnClickListener(event -> saveNewPasswordOnClick());
        showData(getSavedUserData());
        //updateUI();

    }

    public void init(View view) {
        profileImage = view.findViewById(R.id.profileUserImage);
        editImage = view.findViewById(R.id.editProfileImage);
        userName = view.findViewById(R.id.profileName);
        email = view.findViewById(R.id.profileEmail);
        currentPassword = view.findViewById(R.id.current_password_edt);
        newPassword = view.findViewById(R.id.new_password_edt);
        confirmPassword = view.findViewById(R.id.confirm_new_password);
        signOutBtn = view.findViewById(R.id.signOutButton);
        saveNewName = view.findViewById(R.id.edit_name_Imagebutton);
        saveNewPassword = view.findViewById(R.id.saved_change_password);
        editProfile = view.findViewById(R.id.editProfile);
         group = view.findViewById(R.id.groupEdit);
        userDisplayName = view.findViewById(R.id.displayName);

        userModel= new UserPojo();
    }
    private void updateUI(FirebaseUser currentUser) {
      //  FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null) {
            Log.d("UserProfileView", String.valueOf("current user is null " + currentUser == null));

            // User is not signed in, update UI accordingly (e.g., hide sign-out button)
            getView().findViewById(R.id.signOutButton).setVisibility(View.GONE);
        } else {
            // User is signed in, update UI accordingly (e.g., show sign-out button)
            getView().findViewById(R.id.signOutButton).setVisibility(View.VISIBLE);
        }
    }
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(requireContext(),
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build());
        googleSignInClient.signOut().addOnCompleteListener(requireActivity(), task -> {
            Toast.makeText(requireContext(), "Sign out successful", Toast.LENGTH_SHORT).show();

        });
    }
    @Override
    public UserPojo getSavedUserData() {
        Log.d("UserProfileView", "Data received for " +userPresenterInterface.getSavedUserData().getEmail() );

        return userPresenterInterface.getSavedUserData();
    }

    @Override
    public void updateUserData(UserPojo userModel) {
        userPresenterInterface.updateUserData(userModel);
        Log.d("UserProfileView", "updateUserData for " + userModel.getEmail() );

    }

    @Override
    public void updateUserFirebaseData(UserPojo userModel) {
        userPresenterInterface.updateUserFirebaseData(userModel);
        Log.d("UserProfileView", "updateUserFirebaseData for " + userModel.getEmail() );

    }

    @Override
    public void showData(UserPojo userModel) {
        userName.setText(userModel.getUserName());
        email.setText(userModel.getEmail());
        userName.setText(userModel.getUserName());
        userDisplayName.setText(userModel.getUserName());
        image = userModel.getImage();
        if (image.equals("null")) {
            profileImage.setImageResource(R.drawable.ic_baseline_person_24);
        } else {
            Glide.with(getContext()).load(Uri.parse(userModel.getImage())).into(profileImage);
        }
    }

    public void imageChooser() {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), 200);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 200) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    profileImage.setImageURI(selectedImageUri);
                    image = selectedImageUri.toString();
                    userModel = getSavedUserData();
                    userModel.setImage(image);
                    updateUserData(userModel);
                    updateUserFirebaseData(userModel);
                    Glide.with(getView()).load(Uri.parse(userModel.getImage())).into(profileImage);
                    Toast.makeText(getContext(), "Your picture is updated", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }


    @Override
    public void logoutOnClick() {
        UserPojo userModel = new UserPojo();
        userModel.setUserName(null);
        userModel.setEmail(null);
        userModel.setPassWord(null);
        userModel.setImage(null);
        updateUserData(userModel);
        userPresenterInterface.deleteAllMeals();
        signOut();
        Intent intent = new Intent(getContext(), EntrancePage.class);
        startActivity(intent);
        Toast.makeText(getContext(), "you logged out", Toast.LENGTH_SHORT).show();
        getActivity().finish();

    }
    @Override
    public void saveNewNameOnClick() {
        userModel = getSavedUserData();
        String newUserName = userName.getText().toString();

        if (!userModel.getUserName().equals(newUserName)) {
            userModel.setUserName(newUserName);
            userName.setText(newUserName);
            userDisplayName.setText(newUserName);
            updateUserData(userModel);
            updateUserFirebaseData(userModel);
            Toast.makeText(getContext(), "Your userName is updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Your userName is already set to this value", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void saveNewPasswordOnClick() {
        userModel = getSavedUserData();
        String currentpass = currentPassword.getText().toString();
        String newpass = newPassword.getText().toString();
        String confirmNewPass = confirmPassword.getText().toString();
        if(!currentpass.equals(userModel.getPassWord())){
            Toast.makeText(getContext(), "You password is wrong", Toast.LENGTH_SHORT).show();
        }
        else if (!newpass.equals(confirmNewPass)){
            Toast.makeText(getContext(), "New password and confirm password are not matched", Toast.LENGTH_SHORT).show();
        }else{
            userModel.setPassWord(confirmPassword.getText().toString());
            updateUserData(userModel);
            updateUserFirebaseData(userModel);
            group.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Your password is updated", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void editImageOnClick() {
        if (Helper.SKIP == "skip") {
            skipAlert();
        } else {
            ArrayList<String> MemberList = new ArrayList<>();
            MemberList.add("Upload Image");
            MemberList.add("Delete Image");
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setItems(MemberList.toArray(new String[2]), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    if (i == 0) {
                        imageChooser();

                    } else {
                        image = null;
                        userModel = getSavedUserData();
                        userModel.setImage(image);
                        updateUserData(userModel);
                        updateUserFirebaseData(userModel);
                        profileImage.setImageResource(R.drawable.ic_baseline_person_24);
                        Toast.makeText(getContext(), "Your picture is deleted", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
    }


    @Override
    public void editProfile() {
        if(Helper.SKIP == "skip"){
            skipAlert();
        }else {
            if (edit) {
                edit = false;
                group.setVisibility(View.GONE);
            } else {
                edit = true;
                group.setVisibility(View.VISIBLE);
            }
        }
    }


    private void skipAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Do you want to signup in application?");
        builder.setTitle("Alert !");
        builder.setCancelable(false);
        builder.setPositiveButton("yes, Signup", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getContext(), EntrancePage.class);
                getContext().startActivity(intent);
                ((Activity) getContext()).finish();
            }
        });

        builder.setNegativeButton("No, thanks", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}