package com.example.keepintouch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.Document;

import java.util.ArrayList;
import java.util.List;

public class GroupsActivity extends Fragment implements CreateGroupDialogBox.DialogBoxListener {

    private static final String TAG = "";
    private ArrayList<GroupItem> mGroupList;
    private RecyclerView mRecyclerView;
    private GroupAdapter mAdapter;

    private FirebaseFirestore    mFirebaseFirestore = FirebaseFirestore.getInstance();
    //private FloatingActionButton fab;
    private FloatingActionButton mJoinButton, mCreateGroupButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("GroupsActivity");


        createGroupList();
        View rootview = inflater.inflate(R.layout.fragment_groups, container, false);
        mRecyclerView = rootview.findViewById(R.id.recyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new GroupAdapter(mGroupList);
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mJoinButton = rootview.findViewById(R.id.join_button);
        mCreateGroupButton = rootview.findViewById(R.id.create_button);




        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mCreateGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDialog();
            }
        });


        mAdapter.setOnItemClickListener(new GroupAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });


        return rootview;
    }

    private void OpenDialog() {
        CreateGroupDialogBox createGroupDialogBox = new CreateGroupDialogBox();
        createGroupDialogBox.show(getFragmentManager(), "Create Group");
        mAdapter.notifyDataSetChanged();



    }

    private void createGroupList() {
        mGroupList = new ArrayList<>();
//        mGroupList.add(new GroupItem("1","esd","1","23","23"));
//        mGroupList.add(new GroupItem("1","esd","1","23","23"));
            mFirebaseFirestore.collection("Groups").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(queryDocumentSnapshots.isEmpty())
                    {
                        Log.d(TAG,"onSuccess : No Grpup Found!");
                        return;
                    }
                    else
                    {
                        List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : dsList)
                        {
                            GroupItem group=d.toObject(GroupItem.class);
                            mGroupList.add(group);
                        }
                        mAdapter.notifyDataSetChanged();

                    }

                }
            });




    }


    @SuppressLint("ResourceType")
    @Override
    public void createGroup(AppCompatDialogFragment dialogFragment) {

        String groupname, adminname;
        groupname = dialogFragment.getString(R.id.group_name);
        adminname = dialogFragment.getString(R.id.admin_email);

        // mGroupList.add(new GroupItem(groupname,adminname));

    }
}
