package com.example.keepintouch.Repository;


import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.keepintouch.Model.User;
import com.example.keepintouch.Model.sUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AuthRepository {
    private Application application;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore mFirebaseFirestore;
    private String Email;
    String token;

    public AuthRepository(Application application) {
        this.application = application;
        userMutableLiveData = new MutableLiveData<>();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseFirestore = FirebaseFirestore.getInstance();

    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public void setUserMutableLiveData(MutableLiveData<FirebaseUser> userMutableLiveData) {
        this.userMutableLiveData = userMutableLiveData;
    }

    public void logIn(String email, String password) {

        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    userMutableLiveData.postValue(mFirebaseAuth.getCurrentUser());
                    Email = email;

                    saveToken();
                } else {
                    Toast.makeText(application, "Log In Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void Register(String email, String name, String phonenumber, String password) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                                                @Override
                                                                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                                                                    if (task.isSuccessful()) {
                                                                                                        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                                                                                                        String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

                                                                                                        User user = new User(mFirebaseAuth.getCurrentUser().getUid(), name, phonenumber, email, password, date, time, "0", "0");
                                                                                                        mFirebaseFirestore.collection("Users").document(user.getUserId()).set(user);

                                                                                                        Map<String, Object> mp = new HashMap<>();
                                                                                                        ArrayList<String> c = new ArrayList<>();
                                                                                                        mp.put("CodeList", c);
                                                                                                        mFirebaseFirestore.collection("Group'sCode").document(mFirebaseAuth.getCurrentUser().getUid()).set(mp);
                                                                                                        userMutableLiveData.postValue(mFirebaseAuth.getCurrentUser());
                                                                                                        Email = email;

                                                                                                        saveToken();

                                                                                                    } else {

                                                                                                        Toast.makeText(application, "Failed to Register!", Toast.LENGTH_SHORT).show();
                                                                                                    }
                                                                                                }
                                                                                            }
        );

    }

    public boolean isLoggedIn() {
        if (mFirebaseAuth.getCurrentUser() == null) {
            return false;
        } else return true;
    }

    private String getToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (task.isSuccessful()) {
                    String token = task.getResult().getToken();
                    //  Toast.makeText(application,token,Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(application, "Some Problem Occurred to get Token!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return token;
    }

    private void saveToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (task.isSuccessful()) {
                    token = task.getResult().getToken();
                    sUser user = new sUser(Email, token);
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                    databaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                            } else {
                                Toast.makeText(application, "Some Problem Occurred to Store Token!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(application, "Some Problem Occurred to get Token!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
