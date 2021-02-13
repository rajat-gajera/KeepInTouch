package com.example.keepintouch.Repository;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.keepintouch.Model.GroupItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GroupListRepository {

    private String TAG="grp_rps_tager";
    private FirebaseFirestore mFirebaseFirestore;
    private FirebaseAuth mFirebaseAuth;
    public MutableLiveData<List<GroupItem>> mutableGroupListLiveData;
    private List<GroupItem> mGroupList;

    public GroupListRepository()
    {
        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mGroupList=new ArrayList<>();
        mutableGroupListLiveData= new MutableLiveData<>();
    }

    public void getMutableGroupListLiveData() {
        Log.d(TAG, "getMutableGroupListLiveData: ");

        final ArrayList<String> usercodes = new ArrayList<>();
        mFirebaseFirestore.collection("Group'sCode").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {

        //    }
      //  })
      //  mFirebaseFirestore.collection("Group'sCode").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
           // @Override
          //  public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> dslist = queryDocumentSnapshots.getDocuments();
                Log.d(TAG, "onSuccess: " + dslist.size());
                usercodes.clear();
                for (DocumentSnapshot d : dslist) {
                    if (d.getId().equals(mFirebaseAuth.getCurrentUser().getUid())) {
                        List<String> codeLists = (List<String>) d.get("CodeList");
                        for (String code : codeLists) {
                            usercodes.add(code);
                        }
                    }
                }

                mFirebaseFirestore.collection("Groups").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot value) {
                        mGroupList.clear();
                        List<DocumentSnapshot> dsList = value.getDocuments();
                        Log.d(TAG, "2nd: " + dsList.size());
                        for (DocumentSnapshot d : dsList) {
                            GroupItem group = d.toObject(GroupItem.class);
                            String code = group.getCode();
                            //System.out.println(code);
                            for (String c : usercodes) {
                                if (c.equals(code)) {
                                    //System.out.println(code);
                                    mGroupList.add(group);
                                }
                            }
                        }
                        Log.d(TAG, "2nd grp: " + mGroupList.size());
                        Log.d(TAG, "getMutableGroupListLiveData:" + mGroupList.size());
                        mutableGroupListLiveData.postValue(mGroupList);
                    }
                });
            }
        });


//        mFirebaseFirestore.collection("Group'sCode").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                Log.d(TAG, "onEvent: ");
//                List<DocumentSnapshot> dslist = value.getDocuments();
//                usercodes[0].clear();
//                for (DocumentSnapshot d : dslist) {
//                    if (d.getId().equals(mFirebaseAuth.getCurrentUser().getUid())) {
//                        Object o = d.get("CodeList");
//                        usercodes[0] = (ArrayList<String>) o;
//
//                    }
//                }
//                mFirebaseFirestore.collection("Groups").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot value) {
//                         mGroupList.clear();
//                        List<DocumentSnapshot> dsList = value.getDocuments();
//                        for (DocumentSnapshot d : dsList) {
//                            GroupItem group = d.toObject(GroupItem.class);
//                            String code = group.getCode();
//                            //System.out.println(code);
//                            for(String c: usercodes[0]) {
//                                if (c.equals(code)) {
//                                    //System.out.println(code);
//                                    mGroupList.add(group);
//                                }
//                            }
//                        }
//                     }
//                });
//            }
//        });


        //  for(int i=0;i<mGroupList.size();i++){System.out.println(mGroupList.get(i));}


    }


}
