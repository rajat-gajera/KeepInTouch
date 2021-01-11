package com.example.keepintouch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    EditText mEmail,mPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail= findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mFirebaseAuth  = FirebaseAuth.getInstance();
        if(mFirebaseAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }

    }

    public void logIn(View view) {
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
         if(email.length()==0)
         {
             Toast.makeText(getApplicationContext(),"Email Is Requiered!",Toast.LENGTH_SHORT).show();
             mEmail.setError("Email is Requiered!");

             return;
         }
         if(password.length()==0)
         {
             Toast.makeText(getApplicationContext(),"Password Is Requiered!",Toast.LENGTH_SHORT).show();
             mPassword.setError("Password is Required!");

             return;
         }

         mFirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
                 if(task.isSuccessful()) {
                     Toast.makeText(getApplicationContext(), "Successfully Logged In", Toast.LENGTH_LONG).show();
                     startActivity(new Intent(getApplicationContext(),MainActivity.class));
                     finish();

                 }
                 else
                 {
                     Toast.makeText(getApplicationContext(), "Logged In Failed", Toast.LENGTH_LONG).show();

                 }
             }
         });
    }

    public void regtisterNewUser(View view) {
        startActivity(new Intent(this ,RegisterActivity.class));
       // finish();

    }
}