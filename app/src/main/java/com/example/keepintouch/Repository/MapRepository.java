package com.example.keepintouch.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.keepintouch.Model.User;
import com.example.keepintouch.Model.Zone;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MapRepository{
   private FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();
   private String currentGroupId;
    public MutableLiveData<List<User>> mapMemberListMutableLiveData;
   public   List<User> mapMemberlltList;
   //MapThread mapThread;

    public MapRepository(String currentGroupId) {
    this.currentGroupId = currentGroupId;
    mapMemberlltList = new ArrayList<>();
    mapMemberListMutableLiveData = new MutableLiveData<List<User>>();

        Log.d("map_rps_cnst_tager","map Repo Contstructor: thread starting:");
    }

        public void run() {
        Log.d("RUn_tager","thread Starting:");

            final ArrayList<String> memberIds = new ArrayList<>();
                mFirebaseFirestore.collection("Zone").document(currentGroupId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("RUN_tager","isSuccressfull");
                            Zone zone = task.getResult().toObject(Zone.class);
                            ArrayList<String> ids= zone.getMemberList();
                            for(String i:ids)
                                memberIds.add(i);
                            Log.d("RUN_tager",memberIds.toString()+"");

                        }
                    }
                });
            for(String Id : memberIds)
            {
                mFirebaseFirestore.collection("Users").document(Id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful())
                        {Log.d("RUN_tager","isSuccressfull:2");
                            User mem = task.getResult().toObject(User.class);

                            mapMemberlltList.add(mem);
                        }
                        mapMemberListMutableLiveData.postValue(mapMemberlltList);
                    }
                });
            }
            Log.d("RUn_tager","thread completing");
        }


}
