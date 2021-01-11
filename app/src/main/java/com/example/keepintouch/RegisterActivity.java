package com.example.keepintouch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    public static final String PHONENUMBER  = "";
    EditText mName,mEmail,mPassword,mConfirmPassword,mPhoneNumber;
    TextView mRegistered;
    Button mRegisterButton;
    private FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mName = findViewById(R.id.name);
        mPhoneNumber = findViewById(R.id.phonenumber);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mConfirmPassword = findViewById(R.id.confirm_password);
        mRegisterButton = findViewById(R.id.Register_btn);
        mRegistered= findViewById(R.id.registered);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,password,email,confirmpassword,phonenumber;
                name = mName.getText().toString().trim();
                email =mEmail.getText().toString().trim();
                phonenumber = mPhoneNumber.getText().toString().trim();
                password = mPassword.getText().toString().trim();
                confirmpassword = mConfirmPassword.getText().toString().trim();

                if(email.length()==0)
                {
                    //Toast.makeText(getApplicationContext(),"Email Is Requiered!",Toast.LENGTH_SHORT).show();
                    mEmail.setError("Email is Requiered!");

                    return;
                }
                if(name.length()==0)
                {
                    //Toast.makeText(getApplicationContext(),"Name Is Requiered!",Toast.LENGTH_SHORT).show();
                    mName.setError("Name is Requiered!");

                    return;
                }
                if(phonenumber.length()==0)
                {
                    //Toast.makeText(getApplicationContext(),"Phone Is Requiered!",Toast.LENGTH_SHORT).show();
                    mPhoneNumber.setError("Phone is Requiered!");

                    return;
                }
                if(password.length()==0)
                {
                    //Toast.makeText(getApplicationContext(),"Password Is Requiered!",Toast.LENGTH_SHORT).show();
                    mPassword.setError("Password is Requiered!");

                    return;
                }
                if(password.length()<6)
                {
                    //Toast.makeText(getApplicationContext(),"Password Is Requiered!",Toast.LENGTH_SHORT).show();
                    mPassword.setError("Password is too sort!");

                    return;
                }
                if(!password.equals(confirmpassword))
                {
                    Toast.makeText(getApplicationContext(),"Confirm Password isn't same!",Toast.LENGTH_SHORT).show();
                    /// Toast.makeText(getApplicationContext(),""+password + " "+confirmpassword ,Toast.LENGTH_SHORT).show();

                    mPassword.setError(""+password);
                    mConfirmPassword.setError(""+confirmpassword);

                    return;
                }

                mFirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(getApplicationContext(),"User Created",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);

                        intent.putExtra(PHONENUMBER,phonenumber);
                        startActivity(intent);
                         finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Failed to Register!",Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
    mRegistered.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            finish();
        }
    });
    }
}