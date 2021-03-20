package com.example.keepintouch.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.keepintouch.Model.User;
import com.example.keepintouch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SettingsActivity extends Fragment {
    private static final String TAG = "settings_tager";
    String name, mobile, email, password ;
    FirebaseFirestore mFirbaseFirestore;
    FirebaseAuth mFirebaseAuth;
    Button save;
    TextView Name, Email, Password,   Mobile;
    TextView updatePassword;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Settings");
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        mFirbaseFirestore = FirebaseFirestore.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        getData();
        getData();
        save = rootView.findViewById(R.id.chages_save);
        Name = rootView.findViewById(R.id.change_name);
        Email = rootView.findViewById(R.id.change_email);
        Mobile = rootView.findViewById(R.id.change_mobile);
        Password = rootView.findViewById(R.id.change_password);
        updatePassword = rootView.findViewById(R.id.update_password_click);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UpdatePasswordActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void saveData() {
        String uid = mFirebaseAuth.getCurrentUser().getUid();
        name = Name.getText().toString();
        mobile = Mobile.getText().toString();
        if (name.length() == 0) {
            Toast.makeText(getContext(), "Name Should not ne Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mobile.length() < 10) {
            Toast.makeText(getContext(), "Invalid Mobile Number!!", Toast.LENGTH_SHORT).show();
            return;
        }
        String mpassword = Password.getText().toString().trim();


       // mFirebaseAuth.getCurrentUser().updatePassword(password);
        if(!password.equals(mpassword)){
            Toast.makeText(getContext(),"wrong password!",Toast.LENGTH_SHORT).show();
            return;
        }
        mFirbaseFirestore.collection("Users").document(uid).update("name",name,"phone",mobile);
        Toast.makeText(getContext(),"Changes Saved",Toast.LENGTH_SHORT).show();
        Password.setText("");
        getData();



    }

    private void getData() {
        String uid = mFirebaseAuth.getCurrentUser().getUid();
        mFirbaseFirestore.collection("Users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                User user = task.getResult().toObject(User.class);
                name = user.getName();
                email = user.getEmail();
                password = user.getPassword();
                mobile = user.getPhone();

                Name.setText(name);
                Email.setText(email);
                Mobile.setText(mobile);

            }
        });
    }
    public void updatePassword(View view)
    {
        Intent intent = new Intent(getContext(), UpdatePasswordActivity.class);
        startActivity(intent);
    }

}
