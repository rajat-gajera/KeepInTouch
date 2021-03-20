package com.example.keepintouch.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.keepintouch.Model.User;
import com.example.keepintouch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdatePasswordActivity extends AppCompatActivity {
    TextView UpdatePass, UpdatePassConfirm, CurrentPassword;
    Button update;
    String currentPassword, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        UpdatePass = findViewById(R.id.update_password);
        UpdatePassConfirm = findViewById(R.id.update_password_confirm);
        CurrentPassword = findViewById(R.id.current_password);
        update = findViewById(R.id.update);
        currentPassword = CurrentPassword.getText().toString().trim();
        password = UpdatePass.getText().toString().trim();
        confirmPassword = UpdatePassConfirm.getText().toString().trim();

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!password.equals(confirmPassword)){
                    Toast.makeText(getApplicationContext(),"Password and ConfirmPassword doesn't match!",Toast.LENGTH_SHORT).show();
                    return;
                }
                final String[] userpass = new String[1];
                FirebaseFirestore.getInstance().collection("Users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        User user = task.getResult().toObject(User.class);
                        userpass[0] = user.getPassword();
                        if (!currentPassword.equals(userpass[0])) {
                            Toast.makeText(getApplicationContext(),"CurrentPassword is Wrong!",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        FirebaseAuth.getInstance().getCurrentUser().updatePassword(password);
                        Toast.makeText(getApplicationContext(),"Password Updated Successfully!",Toast.LENGTH_SHORT).show();




                    }
                });


            }
        });


    }
}