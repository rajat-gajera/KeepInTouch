package com.example.keepintouch.Repository;

import android.app.Application;
import android.widget.Toast;

import com.example.keepintouch.Model.GroupItem;
import com.example.keepintouch.Model.Zone;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CreateGroupRepository {
    Application application;
    FirebaseFirestore mFirebaseFirestore;
    FirebaseAuth mFirebaseAuth;
    String Code;
    public CreateGroupRepository(Application application)
    {
        this.application=application;
        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirebaseFirestore=FirebaseFirestore.getInstance();
    }
    public void CreateGroup(String GroupName,String Code)
    {    String Date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String Radius="0";
       String  AdminEmail=mFirebaseAuth.getCurrentUser().getEmail();
        String AdminId = mFirebaseAuth.getCurrentUser().getUid();
        DocumentReference col =  mFirebaseFirestore.collection("Groups").document();
        String GroupId= col.getId();
        mFirebaseFirestore.collection("Groups").document(GroupId).set(new GroupItem(GroupName, GroupId, AdminId, AdminEmail, Code, Radius,Date));
        mFirebaseFirestore.collection("Group'sCode").document(mFirebaseAuth.getCurrentUser().getUid()).update("CodeList", FieldValue.arrayUnion(Code));
        ArrayList<String> mlist= new ArrayList<>();
        mlist.add(AdminId);
        mFirebaseFirestore.collection("Zone").document(GroupId).set(new Zone(GroupId,Code,"0","0","0",mlist));
        Toast.makeText(application,"Group Created.",Toast.LENGTH_SHORT).show();
    }
}
