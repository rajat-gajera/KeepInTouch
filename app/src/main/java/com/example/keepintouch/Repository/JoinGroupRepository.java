package com.example.keepintouch.Repository;

import android.app.Application;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.example.keepintouch.Model.GroupItem;
import com.example.keepintouch.Model.MyLocation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class JoinGroupRepository {
    Application application;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirebaseFirestore;

    public JoinGroupRepository(Application application) {
        this.application = application;
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseFirestore= FirebaseFirestore.getInstance();
    }
    public void JoinGroup(String Code)
    {

        ArrayList<String> codes = new ArrayList<>();
        mFirebaseFirestore.collection("Groups").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                   // Log.d("", "onSuccess : No Grpup Found!");
                    return;
                }
                String groupId = null;
                List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot d : dsList) {
                    GroupItem group = d.toObject(GroupItem.class);
                    String groupCode = group.getCode();
                    if (groupCode.equals(Code)) {
                        groupId = group.getGroupId();
                        break;
                    }

                }
                if (groupId == null) {
                    Toast.makeText(application, "Group Not Found! ", Toast.LENGTH_SHORT).show();
                    return;
                }
                String userId = mFirebaseAuth.getCurrentUser().getUid();
                mFirebaseFirestore.collection("Group'sCode").document(userId).update("CodeList", FieldValue.arrayUnion(Code));
                mFirebaseFirestore.collection("Zone").document(groupId).update("memberList",FieldValue.arrayUnion(userId));
                MyLocation location = new MyLocation(userId,new Location(""),true);
                mFirebaseFirestore.collection("Zone").document(groupId).collection("memberList").document(userId).set(location);


            }

        });

    }
}
