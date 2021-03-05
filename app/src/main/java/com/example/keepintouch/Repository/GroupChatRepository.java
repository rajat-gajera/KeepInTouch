package com.example.keepintouch.Repository;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.keepintouch.Model.GroupItem;
import com.example.keepintouch.Model.Message;
import com.example.keepintouch.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GroupChatRepository {
    private static final String TAG ="grp_chat_rps_tager" ;
    private String name;
    DatabaseReference mDatabaseReference;
    private FirebaseFirestore mFirebaseFirestore;
    private FirebaseAuth mFirebaseAuth;
    public MutableLiveData<List<Message>> mutableMessageLiveData;
    private List<Message> mMessageList;
    ArrayList<Message> messages;
    public GroupChatRepository() {
        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mMessageList=new ArrayList<>();
        mutableMessageLiveData= new MutableLiveData<>();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("GroupChat");
        getName();

    }
    private void getName()
    {
        final String[] Name = new String[1];
        FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    Name[0] = documentSnapshot.toObject(User.class).getName();
                    name = Name[0];
                }
            }

        });
     }

    public  void sendMessage(Message message,String currentGroupId)
    {
        message.setName(name);
        mDatabaseReference.child(currentGroupId).push().setValue(message);

    }
    public void getMutableLiveMessages(String currentGroupId)
    {
       // mMessageList.clear();
        mMessageList.add(new Message("Hiiii","KeepInTOuch"));
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                System.out.println(TAG + snapshot.getValue().toString());
                     mMessageList.add((Message) snapshot.getValue(Message.class));

                mutableMessageLiveData.postValue(mMessageList);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//
//        mDatabaseReference.addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                    // Log.d(TAG,dataSnapshot.toString());
//                        System.out.println(TAG + dataSnapshot.getValue().toString());
//                        Map<String ,Object> hmap = ((Map<String,Object>) dataSnapshot.getValue());
//                        for(Map.Entry<String,Object> h:hmap.entrySet())
//                        {
//                           //System.out.println(TAG + h.getValue().toString());
//                           // mMessageList.add((Message) h.getValue());
//                        }
//                        mutableMessageLiveData.postValue(mMessageList);
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        //handle databaseError
//                    }
//                });
    }


}
