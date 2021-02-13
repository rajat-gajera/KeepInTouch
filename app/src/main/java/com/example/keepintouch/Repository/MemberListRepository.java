package com.example.keepintouch.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.keepintouch.Model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MemberListRepository {
    private String TAG="mem_rps_tager";
    private FirebaseFirestore mFirebaseFirestore;
    private FirebaseAuth mFirebaseAuth;
    public MutableLiveData<List<User>> mutableMemberListLiveData;
    private List<User> mMemberList;

    public MemberListRepository()
    {
        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mMemberList=new ArrayList<User>();
        mutableMemberListLiveData= new MutableLiveData<List<User>>();
    }
    public void getMutableMemberListLiveData(String currentgroupid )
    {
        final ArrayList<String>[] memberids = new ArrayList[]{new ArrayList<>()};

        mFirebaseFirestore.collection("Zone").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> dslist = queryDocumentSnapshots.getDocuments();
                memberids[0].clear();
                for (DocumentSnapshot d : dslist) {
                    if (d.getId().equals(currentgroupid) ){
                        Log.d(TAG,currentgroupid);
                        Object o = d.get("memberList");
                        memberids[0] = (ArrayList<String>) o;
                        //System.out.println(memberids[0]+"+++++++++++++++++");
                    }
                }

                mFirebaseFirestore.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
                        mMemberList.clear();
                        for (DocumentSnapshot d : dsList) {
                            User memberuser = d.toObject(User.class);
                            String id = memberuser.getUserId();
                            if(memberids[0].contains(id))
                            {
                                 mMemberList.add(memberuser);
                                Log.d(TAG,memberuser.getEmail());
                            }
                        }

                        Log.d(TAG,mMemberList.toString());
                        mutableMemberListLiveData.postValue(mMemberList);
                    }

                });

            }

        });
        Log.d(TAG,"memberlist size"+mMemberList.size());
    }
}
