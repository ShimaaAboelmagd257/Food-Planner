package com.example.foodplanner.dataBaseHandling.Model.firebase;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.foodplanner.dataBaseHandling.Model.Reposatory.AuthCallback;
import com.example.foodplanner.databinding.ActivityEntrancePageBinding;
import com.example.foodplanner.homeforyou.view.HomeViewModel;
import com.example.foodplanner.model.Pojos.ProductsPOJO;
import com.example.foodplanner.model.sharedprefrence.SharedPreferencesource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class FireBaseDataHandle implements FirbaseSourceInterface {
   // HomeViewModel viewModel;
    private boolean exists;
    private boolean isSucced = false;
    private static FireBaseDataHandle fireBaseDataHandle = null; // to apply the singleton
    private DatabaseReference databaseReference; // a class in firebase extends Query to handle the Querys
    FirebaseDatabase database;
    private final FirebaseAuth mAuth;
    ActivityEntrancePageBinding binding;
    Context context;
    private String previousValue = null;

    public FireBaseDataHandle(Context context) {
        mAuth = FirebaseAuth.getInstance();
        this.context = context;

        //viewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(HomeViewModel.class);

    }


    public static FireBaseDataHandle getInstance(Context context) {
        if (fireBaseDataHandle == null) {

            fireBaseDataHandle = new FireBaseDataHandle(context);
        }
        Log.d("FirebaseDataHandle", "singleton instance is made ");

        return fireBaseDataHandle;
    }

    @Override
    public void insertUser(UserPojo userPojo) {
        try {
            List<String> route = Arrays.asList(userPojo.getEmail().split("\\."));
            String key = route.get(0);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(key);
            // Set the user data
            setUserValues(databaseReference, userPojo);



            Log.d("FirebaseDataHandle", "Value inserted is: " + userPojo.getEmail());
        } catch (Exception e) {
            Log.e("FirebaseDataHandle", "Error inserting user: " + e.getMessage());
        }
    }
    private void setUserValues(DatabaseReference reference, UserPojo userPojo) {
        reference.child("email").setValue(userPojo.getEmail());
        reference.child("passWord").setValue(userPojo.getPassWord());
        reference.child("userName").setValue(userPojo.getUserName());
        reference.child("image").setValue(userPojo.getImage());
    }
    private void setList(DatabaseReference reference, List<ProductsPOJO> list) {
        if (list != null && !list.isEmpty()) {
            ProductsPOJO[] array = list.toArray(new ProductsPOJO[0]);
            // Set the entire array to the Firebase reference
            reference.setValue(list);

            Log.d("FirebaseInsertUser", "setList status: " + list.size() + " items added");
        } else {
            Log.d("FirebaseInsertUser", "setList: List is null or empty");
        }
    }
    @Override

    public boolean checkUserExists(UserPojo userModel, final UserExistCallback callback) {
        exists = false;
        /*if (userModel == null || userModel.getEmail() == null) {
            callback.onUserExistsCheckFailure("UserModel or email is null");
            return exists;
        }*/
        List<String> route = Arrays.asList(userModel.getEmail().split("\\."));
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserPojo userModel1 = dataSnapshot.child(route.get(0)).getValue(UserPojo.class);
                Log.d("FirebaseDataHandle", "Value is: " + userModel1);
                if (userModel1 != null && userModel1.getEmail().equals(userModel.getEmail())) {
                    callback.onUserExists(true);
                    Log.e("FirebaseRead", "Data read success: " + dataSnapshot.getValue());

                } else {
                    callback.onUserExists(false);
                }
                Log.e("FirebaseRead", "Data read failed: " + dataSnapshot.getValue());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onUserExistsCheckFailure("Database error occurred.");
                Log.e("FirebaseRead", "Data read failed: " + databaseError.getMessage());
            }
        });
        return exists;
    }

    @Override
    public void signInWithEmailAndPassword(String email, String password, AuthCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Authentication successful, notify the callback.
                        callback.onAuthSuccess();

                    } else {
                        // Authentication failed, notify the callback with an error message.
                        Exception exception = task.getException();
                        if (exception != null) {
                            callback.onAuthFailure(exception.getMessage());
                        }
                    }
                });
    }

    @Override
    public void fetchUserData(String email, UserDataCallback callback) {
        String[] splitEmail = email.split("\\.");
        String root = splitEmail[0];

        databaseReference.child(root).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // User data exists, notify the callback with the retrieved data.
                    UserPojo userModel = snapshot.getValue(UserPojo.class);
                    if (userModel != null) {
                        if (userModel.getEmail().equals(email)) {
                            showDataFromFirebase(userModel);
                            SharedPreferencesource.getInstance(context).saveUserData(userModel);
                            callback.onUserDataReceived(userModel);
                        } else {
                            // Handle case where user data is null.
                        }
                    } else {
                        // User node does not exist in the database, notify the callback.
                        callback.onUserDataNotFound();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle database error.
                callback.onDatabaseError(error.getMessage());
            }
        });

    }

    @Override
    public boolean login(Context context, String email, String pass) {
        String[] splitEmail = email.split("\\.");
        String root = splitEmail[0];

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DataSnapshot userSnapshot = snapshot.child(root);
                    if (userSnapshot.exists()) {
                        try {
                            UserPojo userModel1 = userSnapshot.getValue(UserPojo.class);

                            if (userModel1 != null && userModel1.getPassWord().equals(pass) && userModel1.getEmail().equals(email)) {
                                isSucced = true;

                                showDataFromFirebase(userModel1);
                                SharedPreferencesource.getInstance(context).saveUserData(userModel1);
                                Log.d("FirebaseData", "Login success");

                            } else {
                                isSucced = false;
                                Log.d("FirebaseData", "Invalid credentials");
                            }
                        } catch (DatabaseException e) {
                            Log.e("FirebaseData", "Error converting to UserPojo: " + e.getMessage());
                            isSucced = false;
                        }
                    } else {
                        Log.d("FirebaseData", "User data not found");
                        isSucced = false;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseData", "Database error: " + error.getMessage());
                isSucced = false;
            }
        });

        return isSucced;
    }






















    private void handleUserDataSnapshot(DataSnapshot userDataSnapshot, String pass, String email, Context context) {
        Object userData = userDataSnapshot.getValue();

        if (userData instanceof String) {
            handleStringValue((String) userData);
        } else if (userData instanceof UserPojo) {
            handleUserPojoValue((UserPojo) userData, pass, email, context);
            isSucced = true;
        } else {
            Log.d("FirebaseData", "Snapshot: not exists or invalid data");
            isSucced = false;
        }
    }

    private void handleStringValue(String stringValue) {
        Log.d("FirebaseData", "Snapshot: " + stringValue);
    }

    private void handleUserPojoValue(UserPojo userModel1, String pass, String email, Context context) {
        if (userModel1 != null && userModel1.getPassWord().equals(pass) && userModel1.getEmail().equals(email)) {
            isSucced = true;
            showDataFromFirebase(userModel1);
            SharedPreferencesource.getInstance(context).saveUserData(userModel1);
            Log.d("FirebaseData", "Snapshot:exists " + userModel1.getEmail());
        } else {
            isSucced = false;
        }
    }






    public void showDataFromFirebase(UserPojo userModel) {
        /*if (viewModel != null) {
            if (userModel.getFavorites() != null) {
                viewModel.updateFavorites(userModel.getFavorites());
            }

            List<List<ProductsPOJO>> plans = new ArrayList<>();

            if (userModel.getFriday() != null) {
                plans.add(userModel.getFriday());
            }

            if (userModel.getSaturday() != null) {
                plans.add(userModel.getSaturday());
            }

            if (userModel.getSunday() != null) {
                plans.add(userModel.getSunday());
            }

            if (userModel.getMonday() != null) {
                plans.add(userModel.getMonday());
            }

            if (userModel.getTuesday() != null) {
                plans.add(userModel.getTuesday());
            }

            if (userModel.getWednesday() != null) {
                plans.add(userModel.getWednesday());
            }

            if (userModel.getThursday() != null) {
                plans.add(userModel.getThursday());
            }

         //   viewModel.updatePlans(plans);


        } else {
            Log.e("FirebaseDataHandle", "ViewModel is null");
        }*/

    }

}
// Set the lists
     /*       Log.d("FirebaseInsertUser", "Favorites list size: " + userPojo.getFavorites().size());
            setList(databaseReference.child("Favorites"), userPojo.getFavorites());
            Log.d("FirebaseInsertUser", "Saturday list size: " + userPojo.getSaturday().size());
            setList(databaseReference.child("Saturday"), userPojo.getSaturday());
            setList(databaseReference.child("Sunday"), userPojo.getSunday());
            setList(databaseReference.child("Monday"), userPojo.getMonday());
            setList(databaseReference.child("Tuesday"), userPojo.getTuesday());
            setList(databaseReference.child("Wednesday"), userPojo.getWednesday());
            setList(databaseReference.child("Thursday"), userPojo.getThursday());
            setList(databaseReference.child("Friday"), userPojo.getFriday());*/


//databaseReference.setValue(userPojo);