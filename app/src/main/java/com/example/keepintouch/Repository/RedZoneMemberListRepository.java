package com.example.keepintouch.Repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.keepintouch.Model.MyLocation;
import com.example.keepintouch.Model.User;
import com.example.keepintouch.Model.Zone;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RedZoneMemberListRepository {
    private String TAG="red_mem_rps_tager";
    private FirebaseFirestore mFirebaseFirestore;
    private FirebaseAuth mFirebaseAuth;
    public MutableLiveData<List<User>> mutableRedMemberListLiveData;
    private List<User> mRedMemberList;

    public RedZoneMemberListRepository(){
        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mRedMemberList=new ArrayList<User>();
        mutableRedMemberListLiveData= new MutableLiveData<List<User>>();
    }
    public void getMutableMemberListLiveData(String currentgroupid) {


        final Zone[] currentZone = new Zone[1];
        mFirebaseFirestore.collection("Zone").document(currentgroupid).collection("memberList").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<String>  memberCodeList  = new ArrayList<>();
                List<DocumentSnapshot> documentSnapshots = value.getDocuments();
                for(DocumentSnapshot d: documentSnapshots )
                {
                    MyLocation myLocation = d.toObject(MyLocation.class);
                    if(!myLocation.isSafe())
                    {
                        memberCodeList.add(myLocation.getUserId());
                    }
                }
                mFirebaseFirestore.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            List<DocumentSnapshot> documentSnapshots = task.getResult().getDocuments();
                            mRedMemberList.clear();
                            for(DocumentSnapshot d:documentSnapshots)
                            {
                                if(memberCodeList.contains(d.toObject(User.class).getUserId()))
                                {
                                    mRedMemberList.add(d.toObject(User.class));
                                }
                            }
                            mutableRedMemberListLiveData.postValue(mRedMemberList);
                        }
                    }
                });
            }
        });


    }



}
