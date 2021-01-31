package com.example.keepintouch.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.keepintouch.Repository.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

public class AuthViewModel extends AndroidViewModel {
    private AuthRepository authRepository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    public AuthViewModel(@NonNull Application application) {
        super(application);

        authRepository = new AuthRepository(application);
        userMutableLiveData = authRepository.getUserMutableLiveData();
    }
    public void  LogIn(String email,String password)
    {
        authRepository.logIn(email,password);
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public boolean isLoggedIn(){ return authRepository.isLoggedIn();}
    public void RegisterUser(String email,String name,String phonenumber ,String password)
    {
        authRepository.Register(email,name,phonenumber,password);
    }
}
