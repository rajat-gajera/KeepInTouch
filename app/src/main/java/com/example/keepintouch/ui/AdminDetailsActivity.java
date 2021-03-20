package com.example.keepintouch.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.keepintouch.Model.GroupItem;
import com.example.keepintouch.Model.User;
import com.example.keepintouch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.List;

public class AdminDetailsActivity extends AppCompatActivity {
    TextView name, email, mobile, groupName;
    String currentGroupId;
    String TAG = "admin_detail_tager";
    FirebaseFirestore mFirebaseFirstore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Log.d(TAG,currentGroupId+" got curent gorup id  in grup chat actviity" );
        setTitle("Admin Details");
        if (savedInstanceState == null) {
            Bundle extra = getIntent().getExtras();
            if (extra == null) {
                currentGroupId = null;
                //       System.out.println("didn't get current group id");
            } else {
                currentGroupId = extra.getString("currentGroupId");
                //     Log.d(TAG,currentGroupId+" got curent gorup id @2" );


            }
        } else {
            currentGroupId = (String) savedInstanceState.getSerializable("currentGroupId");
//            Log.d(TAG,currentGroupId+" got curent gorup id @3" );

        }
//        Log.d(TAG,currentGroupId+" got curent gorup id  in red zone actviity" );


        groupName = findViewById(R.id.admin_detail_group_name);
        name = findViewById(R.id.admin_detail_name);
        email = findViewById(R.id.admin_detail_email);
        mobile = findViewById(R.id.admin_detail_moblie);
        final String[] adminId = new String[1];
        final String[] groupname = new String[1];


        mFirebaseFirstore.collection("Groups").document(currentGroupId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                GroupItem group = task.getResult().toObject(GroupItem.class);
                groupname[0] = group.getGroupName();
                adminId[0] = group.getAdminId();
                 groupName.setText(groupname[0]);

                mFirebaseFirstore.collection("Users").document(adminId[0]).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        User Admin = task.getResult().toObject(User.class);

                            name.setText(Admin.getName());
                            mobile.setText(Admin.getPhone());
                            email.setText(Admin.getEmail());

                    }
                });



            }
        });

    }
}