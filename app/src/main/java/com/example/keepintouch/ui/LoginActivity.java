package com.example.keepintouch.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.keepintouch.MainActivity;
import com.example.keepintouch.R;
import com.example.keepintouch.ViewModel.AuthViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

     EditText mEmail, mPassword;
    ProgressBar progressBar;
    Button mLoginButton;
    TextView mRegisterTextViewButton;
    TextView mForgotPassword;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        getWindow().setSoftInputMode(

                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN

        );
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progress_bar_login);
        mLoginButton = findViewById(R.id.login_btn);
        mRegisterTextViewButton = findViewById(R.id.register_text_btn);
        mForgotPassword = findViewById(R.id.fgt_pass_btn);

        authViewModel = ViewModelProviders.of(this).get(AuthViewModel.class);

        if (authViewModel.isLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        authViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    Toast.makeText(getApplicationContext(), "Logged In Successsfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    //System.out.println("Logged in __________________");
                    progressBar.setVisibility(View.GONE);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Log in Failed!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                if (email.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Email Is Requiered!", Toast.LENGTH_SHORT).show();
                    mEmail.setError("Email is Requiered!");
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                if (password.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Password Is Requiered!", Toast.LENGTH_SHORT).show();
                    mPassword.setError("Password is Required!");
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                authViewModel.LogIn(email, password);
            }
        });

        mRegisterTextViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
                progressBar.setVisibility(View.GONE);

            }
        });
        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEmail.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Email Is Requiered!", Toast.LENGTH_SHORT).show();
                    mEmail.setError("Email is Requiered!");
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                FirebaseAuth.getInstance().sendPasswordResetEmail(mEmail.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                            mEmail.setError("Password Reset Link sent to Your Email");
                        else
                            mEmail.setError("Some Probmlem Occured!!");
                        progressBar.setVisibility(View.GONE);

                    }
                });
            }
        });
    }

}


